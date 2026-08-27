/**************************************************************************

 The SCND Genesis: Legends is a fighting game based on THE SCND GENESIS,
 a webcomic created by Ifunga Ndana ((([<a href="https://www.scndgen.com">https://www.scndgen.com</a>]))).

 The SCND Genesis: Legends RMX  © 2017 Ifunga Ndana.

 The SCND Genesis: Legends is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 The SCND Genesis: Legends is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with The SCND Genesis: Legends. If not, see <<a href="http://www.gnu.org/licenses/">http://www.gnu.org/licenses/</a>>.

 **************************************************************************/
package com.scndgen.legends.network;

import com.scndgen.legends.ScndGenLegends;
import com.scndgen.legends.constants.NetworkConstants;
import com.scndgen.legends.enums.ModeEnum;
import com.scndgen.legends.enums.PlayerType;
import com.scndgen.legends.enums.Stage;
import com.scndgen.legends.render.RenderCharacterSelection;
import com.scndgen.legends.render.RenderGamePlay;
import com.scndgen.legends.render.RenderStageSelect;
import io.github.subiyacryolite.enginev2.nuklear.NkDialogs;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static com.scndgen.legends.constants.NetworkConstants.*;

/**
 * Shared UTF string session: outbound/inbound queues, cooperative socket shutdown,
 * and game-thread {@link #drainInbound()} for protocol side effects.
 */
public abstract class NetworkBase {

    /** Enqueued by the I/O worker; drained on the game thread to show UI and close. */
    public static final String SESSION_ERROR = "\0SESSION_ERROR";

    private static final int IO_POLL_TIMEOUT_MS = 50;
    private static final long JOIN_TIMEOUT_MS = 2000L;

    protected final BlockingQueue<String> outbound = new LinkedBlockingQueue<>();
    protected final BlockingQueue<String> inbound = new LinkedBlockingQueue<>();
    protected volatile boolean running;
    protected volatile Socket socket;
    protected volatile ServerSocket serverSocket;
    protected Thread worker;

    public void sendData(String message) {
        if (message != null) {
            outbound.offer(message);
        }
    }

    /**
     * Apply pending inbound protocol messages on the game/GLFW thread.
     */
    public void drainInbound() {
        String line;
        while ((line = inbound.poll()) != null) {
            if (SESSION_ERROR.equals(line)) {
                ScndGenLegends.get().engine().ui().push(NkDialogs.message(
                        "Network Error",
                        "Something went wrong during the online session",
                        ""
                ));
                NetworkManager.get().close();
                continue;
            }
            if (line.isEmpty()) {
                continue;
            }
            readMessage(line);
        }
    }

    /**
     * Stop the session: flag, close sockets (unblocks accept/read), interrupt and join worker.
     */
    public void close() {
        running = false;
        closeSockets();
        var w = worker;
        if (w != null && w != Thread.currentThread()) {
            w.interrupt();
            try {
                w.join(JOIN_TIMEOUT_MS);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }

    protected void startWorker(String name, Runnable task) {
        worker = Thread.ofVirtual().name(name).unstarted(task);
        worker.start();
    }

    protected void closeSockets() {
        closeQuietly(socket);
        socket = null;
        closeQuietly(serverSocket);
        serverSocket = null;
    }

    protected static void closeQuietly(AutoCloseable closeable) {
        if (closeable == null) {
            return;
        }
        try {
            closeable.close();
        } catch (Exception ignored) {
        }
    }

    protected void signalSessionError() {
        inbound.offer(SESSION_ERROR);
    }

    /**
     * Shared connected-socket pump: flush outbound, read inbound onto the game-thread queue.
     */
    protected void runConnectedSocket(Socket connected) throws IOException {
        this.socket = connected;
        try {
            connected.setTcpNoDelay(true);
            connected.setSoTimeout(IO_POLL_TIMEOUT_MS);
            try (var out = new DataOutputStream(connected.getOutputStream());
                 var in = new DataInputStream(connected.getInputStream())) {
                while (running && !Thread.currentThread().isInterrupted()) {
                    flushOutbound(out);
                    try {
                        var message = in.readUTF();
                        if (!message.isEmpty()) {
                            inbound.offer(message);
                        }
                    } catch (SocketTimeoutException ignored) {
                        outbound.offer(""); // keep-alive when idle
                    }
                }
            }
        } finally {
            if (this.socket == connected) {
                this.socket = null;
            }
        }
    }

    private void flushOutbound(DataOutputStream out) throws IOException {
        String message;
        while ((message = outbound.poll()) != null) {
            out.writeUTF(message);
        }
        out.flush();
    }

    /**
     * Read incoming data stream (game thread only via {@link #drainInbound()}).
     */
    public void readMessage(String line) {
        try {
            if (line.endsWith(NetworkConstants.ATTACK_POSTFIX)) {
                var attackArray = line.split(":");
                var attackList = new ArrayList<String>(attackArray.length);
                for (var i = 0; i < attackArray.length - 1; i++) {
                    if (!attackArray[i].isEmpty()) {
                        attackList.add(attackArray[i]);
                    }
                }
                RenderGamePlay.get().opponentAttack(attackList);
            } else if (line.startsWith(HOST_TIME_CONSTANT)) {
                NetworkManager.get().hostTimeLimit = Integer.parseInt(line.replace(HOST_TIME_CONSTANT, ""));
                System.out.println("aquired time is " + NetworkManager.get().hostTimeLimit);
            } else {
                switch (line) {
                    case TO_CHARACTER_SELECT_CHANGE_SELECTION -> {
                        ScndGenLegends.get().loadMode(ModeEnum.CHAR_SELECT_SCREEN, false);
                        return;
                    }
                    case TO_CHARACTER_SELECT_NEW_MATCH -> {
                        ScndGenLegends.get().loadMode(ModeEnum.CHAR_SELECT_SCREEN, true);
                        return;
                    }
                    case CANCEL_CONNECTIVITY -> {
                        ScndGenLegends.get().engine().ui().push(NkDialogs.message(
                                "Yikes",
                                "Your opponent has terminated this network session",
                                "Well, that sucks"
                        ));
                        NetworkManager.get().close();
                    }
                    case DESELECT_OPPONENT -> {
                        RenderCharacterSelection.get().setSelectedOpponent(false);
                        return;
                    }
                    case SEL_SUBIYA -> {
                        RenderCharacterSelection.get().selSubiya(PlayerType.PLAYER2);
                        return;
                    }
                    case SEL_LYNX -> {
                        RenderCharacterSelection.get().selLynx(PlayerType.PLAYER2);
                        return;
                    }
                    case SEL_ALEX -> {
                        RenderCharacterSelection.get().selAisha(PlayerType.PLAYER2);
                        return;
                    }
                    case SEL_ADE -> {
                        RenderCharacterSelection.get().selAde(PlayerType.PLAYER2);
                        return;
                    }
                    case SEL_RAVAGE -> {
                        RenderCharacterSelection.get().selRav(PlayerType.PLAYER2);
                        return;
                    }
                    case SEL_JOHN -> {
                        RenderCharacterSelection.get().selJon(PlayerType.PLAYER2);
                        return;
                    }
                    case SEL_ADAM -> {
                        RenderCharacterSelection.get().selAdam(PlayerType.PLAYER2);
                        return;
                    }
                    case SEL_NOVA_ADAM -> {
                        RenderCharacterSelection.get().selNOVAAdam(PlayerType.PLAYER2);
                        return;
                    }
                    case SEL_AZARIA -> {
                        RenderCharacterSelection.get().selAza(PlayerType.PLAYER2);
                        return;
                    }
                    case SEL_SORROWE -> {
                        RenderCharacterSelection.get().selSorr(PlayerType.PLAYER2);
                        return;
                    }
                    case SEL_THING -> {
                        RenderCharacterSelection.get().selThing(PlayerType.PLAYER2);
                        return;
                    }
                    case STAGE_IBEX_HILL -> {
                        RenderStageSelect.get().selectStage(Stage.IBEX_HILL);
                        return;
                    }
                    case STAGE_CHELSTON_CITY_DOCKS -> {
                        RenderStageSelect.get().selectStage(Stage.CHELSTON_CITY_DOCKS);
                        return;
                    }
                    case STAGE_DESERT_RUINS -> {
                        RenderStageSelect.get().selectStage(Stage.DESERT_RUINS);
                        return;
                    }
                    case STAGE_CHELSTON_CITY_STREETS -> {
                        RenderStageSelect.get().selectStage(Stage.CHELSTON_CITY_STREETS);
                        return;
                    }
                    case STAGE_IBEX_HILL_NIGHT -> {
                        RenderStageSelect.get().selectStage(Stage.IBEX_HILL_NIGHT);
                        return;
                    }
                    case STAGE_SCORCHED_RUINS -> {
                        RenderStageSelect.get().selectStage(Stage.SCORCHED_RUINS);
                        return;
                    }
                    case STAGE_FROZEN_WILDERNESS -> {
                        RenderStageSelect.get().selectStage(Stage.FROZEN_WILDERNESS);
                        return;
                    }
                    case STAGE_DISTANT_ISLE -> {
                        RenderStageSelect.get().selectStage(Stage.DISTANT_ISLE);
                        return;
                    }
                    case STAGE_HIDDEN_CAVE -> {
                        RenderStageSelect.get().selectStage(Stage.HIDDEN_CAVE);
                        return;
                    }
                    case STAGE_HIDDEN_CAVE_NIGHT -> {
                        RenderStageSelect.get().selectStage(Stage.HIDDEN_CAVE_NIGHT);
                        return;
                    }
                    case STAGE_AFRICAN_VILLAGE -> {
                        RenderStageSelect.get().selectStage(Stage.AFRICAN_VILLAGE);
                        return;
                    }
                    case STAGE_APOCALYPTO -> {
                        RenderStageSelect.get().selectStage(Stage.APOCALYPTO);
                        return;
                    }
                    case STAGE_DISTANT_ISLE_NIGHT -> {
                        RenderStageSelect.get().selectStage(Stage.DISTANT_ISLE_NIGHT);
                        return;
                    }
                    case STAGE_RANDOM -> {
                        RenderStageSelect.get().selectStage(Stage.RANDOM);
                        return;
                    }
                    case STAGE_DESERT_RUINS_NIGHT -> {
                        RenderStageSelect.get().selectStage(Stage.DESERT_RUINS_NIGHT);
                        return;
                    }
                    case STAGE_SCORCHED_RUINS_NIGHT -> {
                        RenderStageSelect.get().selectStage(Stage.SCORCHED_RUINS_NIGHT);
                        return;
                    }
                    case TO_STAGE_SELECT -> ScndGenLegends.get().loadMode(ModeEnum.STAGE_SELECT_SCREEN);
                    case GAME_START -> RenderStageSelect.get().start();
                    case FURY_ATTACK -> {
                        RenderGamePlay.get().triggerFury(PlayerType.PLAYER2);
                        return;
                    }
                    case CONNECT_TO_HOST -> {
                        if (NetworkManager.get().isServer()) {
                            NetworkManager.get().promptServer();
                        } else {
                            NetworkManager.get().setConnectedToPartner(true);
                        }
                    }
                    case DISCONNECT_FROM_HOST -> {
                        ScndGenLegends.get().engine().ui().push(NkDialogs.message(
                                "Ouchies",
                                "HARSH!",
                                "The opponent doesnt want to fight you -_-"
                        ));
                        NetworkManager.get().close();
                    }
                    default -> {
                    }
                }
            }
        } catch (Exception ex) {
            System.err.println(ex);
            ex.printStackTrace(System.err);
            sendData("lastMess");
        }
    }
}

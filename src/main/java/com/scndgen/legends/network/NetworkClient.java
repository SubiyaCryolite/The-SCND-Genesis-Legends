package com.scndgen.legends.network;

import com.scndgen.legends.enums.CharacterState;
import com.scndgen.legends.enums.Stage;
import com.scndgen.legends.enums.SubMode;
import com.scndgen.legends.executers.CharacterAttacksOnline;
import com.scndgen.legends.executers.OpponentAttacksOnline;
import com.scndgen.legends.render.RenderCharacterSelectionScreen;
import com.scndgen.legends.render.RenderStageSelect;
import com.scndgen.legends.threads.ClashSystem;
import com.scndgen.legends.windows.JenesisPanel;
import io.github.subiyacryolite.enginev1.JenesisGlassPane;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by ifunga on 15/04/2017.
 */
public class NetworkClient implements Runnable {

    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private Socket socket;
    private Thread thread;
    private String IPaddress;
    private boolean clientIsRunning;

    /**
     * Constructor, expects name/ip address
     */
    public NetworkClient(String ip) {
        clientIsRunning = true;
        IPaddress = ip;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {

        JenesisPanel.getInstance().getInstance().ServerName = JenesisPanel.getInstance().getInstance().getServerName();
        System.out.println(JenesisPanel.getInstance().getInstance().ServerName);
        JenesisPanel.getInstance().getInstance().UserName = JenesisPanel.getInstance().getInstance().getServerUserName();
        connectToServer(IPaddress);
        while (clientIsRunning) {
            getStreams();
            readMassage();
            try {
                thread.sleep(JenesisPanel.getInstance().serverLatency);
            } catch (InterruptedException ie) {
                JOptionPane.showMessageDialog(null, ie.getMessage(), "Network ERROR", JOptionPane.ERROR_MESSAGE);
                JenesisPanel.getInstance().getInstance().backToCharSelect();
            }
        }

    }

    /**
     * Connect to a given NetworkServer
     *
     * @param hostname NetworkServer name
     */
    private void connectToServer(String hostname) {
        try {
            clientIsRunning = true;
            socket = new Socket(InetAddress.getByName(hostname), JenesisPanel.getInstance().PORT);
            System.out.println(InetAddress.getByName(hostname).getHostAddress() + " || " + InetAddress.getByName(hostname).getHostName() + " <Server> Started. \n");
        } catch (IOException ex) {
            System.err.println(ex);
            System.out.println("Address already in use, please close other instances");
        }
    }

    /**
     * Get data streams
     */
    private void getStreams() {
        try {
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.flush();
            dataInputStream = new DataInputStream(socket.getInputStream());
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace(System.err);
        }
    }

    /**
     * Read incoming data stream
     */
    public void readMassage() {
        try {
            String line = dataInputStream.readUTF();
            if (line.endsWith("attack")) {
                int back = line.length();
                int y1 = Integer.parseInt("" + line.substring(back - 15, back - 13) + "");
                int y2 = Integer.parseInt("" + line.substring(back - 13, back - 11) + "");
                int y3 = Integer.parseInt("" + line.substring(back - 11, back - 9) + "");
                int y4 = Integer.parseInt("" + line.substring(back - 9, back - 7) + "");
                if (JenesisPanel.getInstance().getGameMode() == SubMode.LAN_HOST) {
                    JenesisPanel.getInstance().playerClient2 = new CharacterAttacksOnline(y1, y2, y3, y4, 'n');
                }
                if (JenesisPanel.getInstance().getGameMode() == SubMode.LAN_CLIENT) {
                    JenesisPanel.getInstance().playerClient1 = new OpponentAttacksOnline(y1, y2, y3, y4, 'n');
                }
                System.out.println(line.charAt(back - 11) + " " + line.charAt(back - 10) + " " + line.charAt(back - 9) + " " + line.charAt(back - 8));
                System.out.println("\n");
            } else if (line.endsWith("pauseGame")) {
                //pauseMethod();
            } else if (line.endsWith(" xc_97_mb")) {
                JenesisGlassPane.getInstance().primaryNotice(line.replaceAll(" xc_97_mb", ""));
            } //CharacterEnum
            else if (line.endsWith("_jkxc")) {
                if (line.contains("selSub")) {
                    RenderCharacterSelectionScreen.getInstance().selSubiya(CharacterState.OPPONENT);
                }
                if (line.contains("selRai")) {
                    RenderCharacterSelectionScreen.getInstance().selRaila(CharacterState.OPPONENT);
                }
                if (line.contains("selAlx")) {
                    RenderCharacterSelectionScreen.getInstance().selAisha(CharacterState.OPPONENT);
                }
                if (line.contains("selLyn")) {
                    RenderCharacterSelectionScreen.getInstance().selLynx(CharacterState.OPPONENT);
                }
                if (line.contains("selRav")) {
                    RenderCharacterSelectionScreen.getInstance().selRav(CharacterState.OPPONENT);
                }
                if (line.contains("selAde")) {
                    RenderCharacterSelectionScreen.getInstance().selAde(CharacterState.OPPONENT);
                }
                if (line.contains("selJon")) {
                    RenderCharacterSelectionScreen.getInstance().selJon(CharacterState.OPPONENT);
                }
                if (line.contains("selAdam")) {
                    RenderCharacterSelectionScreen.getInstance().selAdam(CharacterState.OPPONENT);
                }
                if (line.contains("selNOVAAdam")) {
                    RenderCharacterSelectionScreen.getInstance().selNOVAAdam(CharacterState.OPPONENT);
                }
                if (line.contains("selAzaria")) {
                    RenderCharacterSelectionScreen.getInstance().selAza(CharacterState.OPPONENT);
                }
                if (line.contains("selSorr")) {
                    RenderCharacterSelectionScreen.getInstance().selSorr(CharacterState.OPPONENT);
                }
                if (line.contains("selThi")) {
                    RenderCharacterSelectionScreen.getInstance().selThing(CharacterState.OPPONENT);
                }
            } else if (line.endsWith("watchStageSel_xcbD")) {
                JenesisPanel.getInstance().selectStage();
            } else if (line.startsWith("as1wds2_")) {
                JenesisPanel.getInstance().hostTime = Integer.parseInt(line.substring(8));
                System.out.println("aquired time is " + JenesisPanel.getInstance().hostTime);
            } //stages
            else if (line.endsWith("_vgdt")) {
                if (line.equals(Stage.IBEX_HILL.shortCode())) {
                    RenderStageSelect.getInstance().selectStage(Stage.IBEX_HILL);
                }
                if (line.equals(Stage.CHELSTON_CITY_DOCKS.shortCode())) {
                    RenderStageSelect.getInstance().selectStage(Stage.CHELSTON_CITY_DOCKS);
                }
                if (line.equals(Stage.DESERT_RUINS.shortCode())) {
                    RenderStageSelect.getInstance().selectStage(Stage.DESERT_RUINS);
                }
                if (line.equals(Stage.CHELSTON_CITY_STREETS.shortCode())) {
                    RenderStageSelect.getInstance().selectStage(Stage.CHELSTON_CITY_STREETS);
                }
                if (line.equals(Stage.IBEX_HILL_NIGHT.shortCode())) {
                    RenderStageSelect.getInstance().selectStage(Stage.IBEX_HILL_NIGHT);
                }
                if (line.equals(Stage.SCORCHED_RUINS.shortCode())) {
                    RenderStageSelect.getInstance().selectStage(Stage.SCORCHED_RUINS);
                }
                if (line.equals(Stage.FROZEN_WILDERNESS.shortCode())) {
                    RenderStageSelect.getInstance().selectStage(Stage.FROZEN_WILDERNESS);
                }
                if (line.equals(Stage.DISTANT_ISLE.shortCode())) {
                    RenderStageSelect.getInstance().selectStage(Stage.DISTANT_ISLE);
                }
                if (line.equals(Stage.HIDDEN_CAVE.shortCode())) {
                    RenderStageSelect.getInstance().selectStage(Stage.HIDDEN_CAVE);
                }
                if (line.equals(Stage.HIDDEN_CAVE_NIGHT.shortCode())) {
                    RenderStageSelect.getInstance().selectStage(Stage.HIDDEN_CAVE_NIGHT);
                }
                if (line.equals(Stage.AFRICAN_VILLAGE.shortCode())) {
                    RenderStageSelect.getInstance().selectStage(Stage.AFRICAN_VILLAGE);
                }
                if (line.equals(Stage.APOCALYPTO.shortCode())) {
                    RenderStageSelect.getInstance().selectStage(Stage.APOCALYPTO);
                }
                if (line.equals(Stage.DISTANT_ISLE_NIGHT.shortCode())) {
                    RenderStageSelect.getInstance().selectStage(Stage.DISTANT_ISLE_NIGHT);
                }
                if (line.equals(Stage.RANDOM.shortCode())) {
                    RenderStageSelect.getInstance().selectStage(Stage.RANDOM);
                }
                if (line.equals(Stage.DESERT_RUINS_NIGHT.shortCode())) {
                    RenderStageSelect.getInstance().selectStage(Stage.DESERT_RUINS_NIGHT);
                }
                if (line.equals(Stage.SCORCHED_RUINS_NIGHT.shortCode())) {
                    RenderStageSelect.getInstance().selectStage(Stage.SCORCHED_RUINS_NIGHT);
                }
            } //start game
            else if (line.endsWith("gameStart7%^&")) {
                RenderStageSelect.getInstance().start();
            } else if (line.contains("loadingGVSHA")) {
                RenderStageSelect.getInstance().nowLoading();
            } //special moves
            else if (line.contains("limt_Break_Oxodia_Ownz")) {
                JenesisPanel.getInstance().triggerFury(CharacterState.OPPONENT);
            } //clashes
            else if (line.contains("oppClsh")) {
                System.out.println("THis is it " + line.substring(7));
                int val = Integer.parseInt(line.substring(7));
                ClashSystem.getInstance().setOpp(val);
            } //rejected
            else if (line.contains("getLost")) ;
            {
                JOptionPane.showMessageDialog(null, "HARSH!, The opponent doesnt want to fight you -_-" + JenesisPanel.getInstance().isMessageSent() + " " + JenesisPanel.getInstance().getGameMode(), "Ouchies", JOptionPane.ERROR_MESSAGE);
                JenesisPanel.getInstance().sendToServer("quit");
                JenesisPanel.getInstance().closeTheClient();
                JenesisPanel.getInstance().backToMenuScreen();
            }
        } catch (Exception ex) {
            System.err.println(ex);
            ex.printStackTrace();
            sendData("lastMess");
        }
    }

    /**
     * Send a data stream
     *
     * @param mess message to send
     */
    public void sendData(String mess) {
        try {
            JenesisPanel.getInstance().last = mess;
            dataOutputStream.writeUTF(mess);
            dataOutputStream.flush();
        } catch (Exception exception) {
            exception.printStackTrace(System.err);
        }
    }

    /**
     * Terminate client
     */
    public void closeClient() {
        try {
            clientIsRunning = false;
            dataOutputStream.close();
            dataInputStream.close();
            socket.close();
        } catch (Exception exception) {
            exception.printStackTrace(System.err);
        }
    }
}

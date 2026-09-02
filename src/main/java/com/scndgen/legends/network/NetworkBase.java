/**************************************************************************

 The SCND Genesis: Legends is a fighting game based on THE SCND GENESIS,
 a webcomic created by Ifunga Ndana (https://www.scndgen.com).

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
 along with The SCND Genesis: Legends. If not, see <https://www.gnu.org/licenses/>.

 **************************************************************************/
package com.scndgen.legends.network;

import com.scndgen.legends.command.GameCommand;
import com.scndgen.legends.command.GameCommandBus;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Shared UTF string session I/O. Inbound strings are decoded into {@link GameCommand}s
 * and offered to {@link GameCommandBus} for game-thread application.
 */
public abstract class NetworkBase {

    private static final int IO_POLL_TIMEOUT_MS = 50;
    private static final long JOIN_TIMEOUT_MS = 2000L;

    protected final BlockingQueue<String> outbound = new LinkedBlockingQueue<>();
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
        GameCommandBus.get().offer(new GameCommand.SessionError(""));
    }

    /**
     * Shared connected-socket pump: flush outbound, decode inbound onto the command bus.
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
                            GameCommandBus.get().offerEncoded(message);
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
}

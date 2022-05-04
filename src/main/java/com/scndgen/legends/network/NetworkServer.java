package com.scndgen.legends.network;

import com.scndgen.legends.constants.NetworkConstants;
import com.scndgen.legends.state.State;
import io.github.subiyacryolite.enginev1.FxDialogs;
import javafx.application.Platform;
import javafx.scene.control.ButtonBar;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Enumeration;
import java.util.LinkedList;

import static com.scndgen.legends.constants.NetworkConstants.CONNECT_TO_HOST;

/**
 * Created by ifunga on 15/04/2017.
 */
public class NetworkServer extends NetworkBase implements Runnable {

    private final Thread thread;
    private String hostName, hostAddress;
    private boolean running;
    private final LinkedList<String> messageQue = new LinkedList<>();

    /**
     * Basic constructor
     */
    public NetworkServer() {
        initServerDetails();
        thread = new Thread(this);
        thread.setDaemon(false);
        thread.start();
    }

    private void initServerDetails() {
        try {
            Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces();
            while (enumeration.hasMoreElements()) {
                hostName = InetAddress.getLocalHost().getHostName();
                hostAddress = InetAddress.getLocalHost().getHostAddress();
            }

        } catch (Exception ex) {
            System.out.print(ex);
        }
    }


    public void playerFound() {
        Platform.runLater(() -> {
            ButtonBar.ButtonData answer = FxDialogs.yesNo("Heads Up", "Someone wants to fight you!", "Wanna waste em?");
            switch (answer) {
                case YES:
                    NetworkManager.get().setConnectedToPartner(true);
                    sendData(CONNECT_TO_HOST);
                    sendData(NetworkConstants.connectToHost(State.get().getLogin().getTimeLimit()));
                    break;
                case NO:
                    NetworkManager.get().setConnectedToPartner(false);
                    sendData(NetworkConstants.DISCONNECT_FROM_HOST);
                    break;
            }
        });
    }


    @Override
    public void run() {
        try {
            running = true;
            System.out.printf("<Server> Started on %s.\n", InetAddress.getLocalHost());
            try (ServerSocket serverSocket = new ServerSocket(NetworkManager.PORT, 1); Socket socket = serverSocket.accept()) {
                serverSocket.setSoTimeout(NetworkManager.TIMEOUT);
                System.out.printf("Client [%s] connected to server.\n", socket);
                while (running) {
                    DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                    if (!messageQue.isEmpty()) {
                        dataOutputStream.writeUTF(messageQue.pop());
                        dataOutputStream.flush();
                    }
                    DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                    readMessage(dataInputStream.readUTF());
                    thread.sleep(NetworkManager.SERVER_LATENCY);
                    sendData("");//keep stream alive
                }
            }
        } catch (Exception ex) {
            FxDialogs.error("Network Error", "Something went wrong during the online session", "", ex);
            NetworkManager.get().close();
        } finally {
            System.out.println("Closed the server");
        }
    }

    /**
     * Send data stream
     *
     * @param message message to send
     */
    @Override
    public void sendData(String message) {
        messageQue.add(message);
    }

    public String getHostName() {
        return hostName;
    }

    public String getHostAddress() {
        return hostAddress;
    }

    public void close() {
        running = false;
    }

    public void shutdownKillServer() {
        thread.stop();
    }
}

package com.scndgen.legends.network;

import com.scndgen.legends.constants.NetworkConstants;
import io.github.subiyacryolite.enginev1.FxDialogs;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.LinkedList;

/**
 * Created by ifunga on 15/04/2017.
 */
public class NetworkClient extends NetworkBase implements Runnable {

    private Thread thread;
    private String serverIpAddress;
    private boolean running;
    private final LinkedList<String> messageQue = new LinkedList<>();

    /**
     * Constructor, expects getInfo/ip address
     */
    public NetworkClient(String ip) {
        running = true;
        serverIpAddress = ip;
        sendData(NetworkConstants.CONNECT_TO_HOST);
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        running = true;
        try {
            InetAddress inetAddress = InetAddress.getByName(serverIpAddress);
            System.out.printf("Attempting to connect to %s\n", inetAddress);
            try (Socket socket = new Socket(inetAddress, NetworkManager.PORT)) {
                System.out.println("Connected to " + socket.getInetAddress() + "\n");
                socket.setSoTimeout(NetworkManager.TIMEOUT);
                while (running) {
                    DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                    if (!messageQue.isEmpty()) {
                        dataOutputStream.writeUTF(messageQue.pop());
                        dataOutputStream.flush();
                    }
                    DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                    readMessage(dataInputStream.readUTF());
                    thread.sleep(NetworkManager.getInstance().SERVER_LATENCY);
                    sendData("");//keep stream alive
                }
            }
        } catch (Exception ex) {
            FxDialogs.error("Network Error", "Something went wrong during the online session", "", ex);
            NetworkManager.getInstance().close();
        }
    }

    /**
     * Send a data stream
     *
     * @param message message to send
     */
    @Override
    public void sendData(String message) {
        messageQue.add(message);
    }

    /**
     * Terminate client
     */
    public void close() {
        try {
            running = false;
        } catch (Exception exception) {
            exception.printStackTrace(System.err);
        }
    }
}

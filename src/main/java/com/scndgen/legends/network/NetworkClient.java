package com.scndgen.legends.network;

import com.scndgen.legends.ScndGenLegends;
import com.scndgen.legends.enums.ModeEnum;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by ifunga on 15/04/2017.
 */
public class NetworkClient extends NetworkBase implements Runnable {

    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private Socket socket;
    private Thread thread;
    private String serverIpAddress;
    private boolean running;

    /**
     * Constructor, expects getInfo/ip address
     */
    public NetworkClient(String ip) {
        running = true;
        serverIpAddress = ip;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        connectToServer(serverIpAddress);
        while (running) {
            getStreams();
            try {
                readMessage(dataInputStream.readUTF());
                thread.sleep(NetworkManager.getInstance().serverLatency);
            } catch (Exception ie) {
                JOptionPane.showMessageDialog(null, ie.getMessage(), "Network ERROR", JOptionPane.ERROR_MESSAGE);
                ScndGenLegends.getInstance().loadMode(ModeEnum.MAIN_MENU);
                NetworkManager.getInstance().closePipes();
            }
        }

    }

    /**
     * Connect to a given NetworkServer
     *
     * @param hostname NetworkServer getInfo
     */
    private void connectToServer(String hostname) {
        try {
            running = true;
            socket = new Socket(InetAddress.getByName(hostname), NetworkManager.getInstance().PORT);
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
     * Send a data stream
     *
     * @param message message to send
     */
    @Override
    public void sendData(String message) {
        try {
            dataOutputStream.writeUTF(message);
            dataOutputStream.flush();
        } catch (Exception exception) {
            exception.printStackTrace(System.err);
        }
    }

    /**
     * Terminate client
     */
    public void close() {
        try {
            running = false;
            dataOutputStream.close();
            dataInputStream.close();
            socket.close();
        } catch (Exception exception) {
            exception.printStackTrace(System.err);
        }
    }
}

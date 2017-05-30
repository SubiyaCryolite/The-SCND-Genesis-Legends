package com.scndgen.legends.network;

import com.scndgen.legends.ScndGenLegends;
import com.scndgen.legends.constants.NetworkConstants;
import com.scndgen.legends.enums.ModeEnum;
import com.scndgen.legends.render.RenderCharacterSelection;
import com.scndgen.legends.state.GameState;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by ifunga on 15/04/2017.
 */
public class NetworkServer extends NetworkBase implements Runnable {

    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private ServerSocket serverSocket;
    private Socket socket;
    private Thread thread;
    private boolean running;

    /**
     * Basic constructor
     */
    public NetworkServer() {
        InitServer();
        thread = new Thread(this);
    }

    /**
     * Start this NetworkServer
     */
    public void start() {
        thread.start();
    }

    /**
     * Initialize NetworkServer
     */
    private void InitServer() {
        try {
            running = true;
            serverSocket = new ServerSocket(NetworkManager.PORT, 1);
            System.out.println(InetAddress.getLocalHost().getHostAddress() + " || " + InetAddress.getLocalHost().getHostName() + " <Server> Started. \n");
        } catch (IOException ex) {
            System.err.println(ex);
            System.err.println("Address already in use, please close other instances");
        }
    }

    /**
     * Establish a new socket
     */
    private void establishConnection() {
        try {
            socket = serverSocket.accept();
            System.out.println(socket.getInetAddress().getHostName());
            playerFound();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void playerFound() {
        int answer = JOptionPane.showConfirmDialog(null, "Someone wants to fight you!!!!\nWanna waste em!?", "Heads Up", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        switch (answer) {
            case JOptionPane.YES_OPTION: {
                sendData(NetworkConstants.connectToHost(GameState.getInstance().getLogin().getTimeLimit()));
                RenderCharacterSelection.getInstance().newInstance();
                RenderCharacterSelection.getInstance().animateCharSelect();
                ScndGenLegends.getInstance().loadMode(ModeEnum.CHAR_SELECT_SCREEN);
                break;
            }
            case JOptionPane.NO_OPTION: {
            }
        }
    }

    /**
     * Close the NetworkServer
     */
    public void close() {
        try {
            running = false;
            dataOutputStream.close();
            dataInputStream.close();
            socket.close();
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void run() {
        establishConnection();
        while (running) {
            try {
                getStreams();
                readMessage(dataInputStream.readUTF());
                thread.sleep(NetworkManager.serverLatency);
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
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
        }
    }


    /**
     * Send data stream
     *
     * @param mess message to send
     */
    @Override
    public void sendData(String mess) {
        try {
            dataOutputStream.writeUTF(mess);
            dataOutputStream.flush();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}

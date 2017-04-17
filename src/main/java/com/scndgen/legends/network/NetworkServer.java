package com.scndgen.legends.network;

import com.scndgen.legends.enums.CharacterState;
import com.scndgen.legends.enums.SubMode;
import com.scndgen.legends.executers.OpponentAttacksOnline;
import com.scndgen.legends.render.RenderCharacterSelectionScreen;
import com.scndgen.legends.threads.ClashSystem;
import com.scndgen.legends.windows.MainWindow;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by ifunga on 15/04/2017.
 */
public class NetworkServer implements Runnable {

    private DataOutputStream output;
    private DataInputStream input;
    private ServerSocket server;
    private Socket connection;
    private Thread thread;
    private boolean serverIsRunning;

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
            serverIsRunning = true;
            server = new ServerSocket(MainWindow.PORT, 1);
            System.out.println(InetAddress.getLocalHost().getHostAddress() + " || " + InetAddress.getLocalHost().getHostName() + " <Server> Started. \n");
        } catch (IOException ex) {
            System.err.println(ex);
            System.err.println("Address already in use, please close other instances");
        }
    }

    /**
     * Establish a new connection
     */
    private void establishConnection() {
        try {
            connection = server.accept();
            System.out.println(connection.getInetAddress().getHostName());
            MainWindow.getInstance().playerFound();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Close the NetworkServer
     */
    public void closeServer() {
        try {
            serverIsRunning = false;
            output.close();
            input.close();
            connection.close();
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void run() {
        establishConnection();
        while (serverIsRunning) {
            try {
                getStreams();
                readMessage();
                thread.sleep(MainWindow.serverLatency);
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
            output = new DataOutputStream(connection.getOutputStream());
            output.flush();
            input = new DataInputStream(connection.getInputStream());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Read incoming data
     */
    public void readMessage() {
        try {
            String line = input.readUTF();
            if (line.endsWith("quit")) {
                closeServer();
            } else if (line.endsWith("player_QSLV")) {
                MainWindow.getInstance().playerFound();
            } else if (line.endsWith("attack")) {

                //1111 attack
                //01010101 attack
                int back = line.length();
                int y1 = Integer.parseInt("" + line.substring(back - 15, back - 13) + "");
                int y2 = Integer.parseInt("" + line.substring(back - 13, back - 11) + "");
                int y3 = Integer.parseInt("" + line.substring(back - 11, back - 9) + "");
                int y4 = Integer.parseInt("" + line.substring(back - 9, back - 7) + "");

                if (MainWindow.getInstance().getGameMode()== SubMode.LAN_HOST) {
                    MainWindow.getInstance().playerHost2 = new OpponentAttacksOnline(y1, y2, y3, y4, 'n');
                }

                System.out.println(line.charAt(back - 11) + " " + line.charAt(back - 10) + " " + line.charAt(back - 9) + " " + line.charAt(back - 8));
                //SendMassage(client,client.socket().getInetAddress()+" ATTACKED YOU!!!");
                System.out.println("\n");
            } else if (line.endsWith("pauseGame")) {
                //pauseMethod();
            } else if (line.endsWith(" xc_97_mb")) {
                MainWindow.getInstance().systemNotice(line.replaceAll(" xc_97_mb", ""));
            } //CharacterEnum
            else if (line.endsWith("_jkxc")) {
                System.out.println("Server mess: " + line);
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
            } else if (line.equalsIgnoreCase("lastMess")) {
                sendData(MainWindow.getInstance().last);
            } //special moves
            else if (line.contains("limt_Break_Oxodia_Ownz")) {
                MainWindow.getInstance().triggerFury(CharacterState.OPPONENT);
            } //clashes
            else if (line.contains("oppClsh")) {
                System.out.println("THis is it " + line.substring(7));
                int val = Integer.parseInt(line.substring(7));
                ClashSystem.getInstance().setOpp(val);
            }
        } catch (Exception ex) {
            System.err.println(ex);
            ex.printStackTrace();
            sendData("lastMess");
        }
    }

    /**
     * Send data stream
     *
     * @param mess message to send
     */
    public void sendData(String mess) {
        try {
            MainWindow.getInstance().last = mess;
            output.writeUTF(mess);
            output.flush();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}

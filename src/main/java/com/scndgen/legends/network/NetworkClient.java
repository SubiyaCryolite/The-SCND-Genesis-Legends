package com.scndgen.legends.network;

import com.scndgen.legends.executers.CharacterAttacksOnline;
import com.scndgen.legends.executers.OpponentAttacksOnline;
import com.scndgen.legends.render.RenderStageSelect;
import com.scndgen.legends.render.RenderCharacterSelectionScreen;
import com.scndgen.legends.threads.ClashSystem;
import com.scndgen.legends.windows.MainWindow;

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

        MainWindow.getInstance().getInstance().ServerName = MainWindow.getInstance().getInstance().getServerName();
        System.out.println(MainWindow.getInstance().getInstance().ServerName);
        MainWindow.getInstance().getInstance().UserName = MainWindow.getInstance().getInstance().getServerUserName();
        connectToServer(IPaddress);
        while (clientIsRunning) {
            getStreams();
            readMassage();
            try {
                thread.sleep(MainWindow.getInstance().serverLatency);
            } catch (InterruptedException ie) {
                JOptionPane.showMessageDialog(null, ie.getMessage(), "Network ERROR", JOptionPane.ERROR_MESSAGE);
                MainWindow.getInstance().getInstance().backToCharSelect();
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
            socket = new Socket(InetAddress.getByName(hostname), MainWindow.getInstance().PORT);
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
            e.printStackTrace();
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
                if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.getInstance().lanHost)) {
                    MainWindow.getInstance().playerClient2 = new CharacterAttacksOnline(y1, y2, y3, y4, 'n');
                }
                if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.getInstance().lanClient)) {
                    MainWindow.getInstance().playerClient1 = new OpponentAttacksOnline(y1, y2, y3, y4, 'n');
                }
                System.out.println(line.charAt(back - 11) + " " + line.charAt(back - 10) + " " + line.charAt(back - 9) + " " + line.charAt(back - 8));
                System.out.println("\n");
            } else if (line.endsWith("pauseGame")) {
                //pauseMethod();
            } else if (line.endsWith(" xc_97_mb")) {
                MainWindow.getInstance().systemNotice(line.replaceAll(" xc_97_mb", ""));
            } //Character
            else if (line.endsWith("_jkxc")) {
                if (line.contains("selSub")) {
                    RenderCharacterSelectionScreen.getInstance().selSubiya('o');
                }
                if (line.contains("selRai")) {
                    RenderCharacterSelectionScreen.getInstance().selRaila('o');
                }
                if (line.contains("selAlx")) {
                    RenderCharacterSelectionScreen.getInstance().selAisha('o');
                }
                if (line.contains("selLyn")) {
                    RenderCharacterSelectionScreen.getInstance().selLynx('o');
                }
                if (line.contains("selRav")) {
                    RenderCharacterSelectionScreen.getInstance().selRav('o');
                }
                if (line.contains("selAde")) {
                    RenderCharacterSelectionScreen.getInstance().selAde('o');
                }
                if (line.contains("selJon")) {
                    RenderCharacterSelectionScreen.getInstance().selJon('o');
                }
                if (line.contains("selAdam")) {
                    RenderCharacterSelectionScreen.getInstance().selAdam('o');
                }
                if (line.contains("selNOVAAdam")) {
                    RenderCharacterSelectionScreen.getInstance().selNOVAAdam('o');
                }
                if (line.contains("selAzaria")) {
                    RenderCharacterSelectionScreen.getInstance().selAza('o');
                }
                if (line.contains("selSorr")) {
                    RenderCharacterSelectionScreen.getInstance().selSorr('o');
                }
                if (line.contains("selThi")) {
                    RenderCharacterSelectionScreen.getInstance().selThing('o');
                }
            } else if (line.endsWith("watchStageSel_xcbD")) {
                MainWindow.getInstance().selectStage();
            } else if (line.startsWith("as1wds2_")) {
                MainWindow.getInstance().hostTime = Integer.parseInt(line.substring(8));
                System.out.println("aquired time is " + MainWindow.getInstance().hostTime);
            } //stages
            else if (line.endsWith("_vgdt")) {
                if (line.contains("stage1")) {
                    RenderStageSelect.getInstance().stage1();
                }
                if (line.contains("stage2")) {
                    RenderStageSelect.getInstance().stage2();
                }
                if (line.contains("stage3")) {
                    RenderStageSelect.getInstance().stage3();
                }
                if (line.contains("stage4")) {
                    RenderStageSelect.getInstance().stage4();
                }
                if (line.contains("stage5")) {
                    RenderStageSelect.getInstance().stage5();
                }
                if (line.contains("stage6")) {
                    RenderStageSelect.getInstance().stage6();
                }
                if (line.contains("stage7")) {
                    RenderStageSelect.getInstance().stage7();
                }
                if (line.contains("stage100")) {
                    RenderStageSelect.getInstance().stage100();
                }
                if (line.contains("stage8")) {
                    RenderStageSelect.getInstance().stage8();
                }
                if (line.contains("stage9")) {
                    RenderStageSelect.getInstance().stage9();
                }
                if (line.contains("stage10")) {
                    RenderStageSelect.getInstance().stage10();
                }
                if (line.contains("stage11")) {
                    RenderStageSelect.getInstance().stage11();
                }
                if (line.contains("stage12")) {
                    RenderStageSelect.getInstance().stage12();
                }
                if (line.contains("stage13")) {
                    RenderStageSelect.getInstance().stage13();
                }
                if (line.contains("stage14")) {
                    RenderStageSelect.getInstance().stage14();
                }
            } //start game
            else if (line.endsWith("gameStart7%^&")) {
                RenderStageSelect.getInstance().start();
            } else if (line.contains("loadingGVSHA")) {
                RenderStageSelect.getInstance().nowLoading();
            } //special moves
            else if (line.contains("limt_Break_Oxodia_Ownz")) {
                MainWindow.getInstance().triggerFury('o');
            } //clashes
            else if (line.contains("oppClsh")) {
                System.out.println("THis is it " + line.substring(7));
                int val = Integer.parseInt(line.substring(7));
                ClashSystem.getInstance().setOpp(val);
            } //rejected
            else if (line.contains("getLost")) ;
            {
                JOptionPane.showMessageDialog(null, "HARSH!, The opponent doesnt want to fight you -_-" + MainWindow.getInstance().isMessageSent() + " " + MainWindow.getInstance().getGameMode(), "Ouchies", JOptionPane.ERROR_MESSAGE);
                MainWindow.getInstance().sendToServer("quit");
                MainWindow.getInstance().closeTheClient();
                MainWindow.getInstance().backToMenuScreen();
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
            MainWindow.getInstance().last = mess;
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

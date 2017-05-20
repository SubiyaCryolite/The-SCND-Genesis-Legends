/**************************************************************************

 The SCND Genesis: Legends is a fighting game based on THE SCND GENESIS,
 a webcomic created by Ifunga Ndana (http://www.scndgen.sf.net).

 The SCND Genesis: Legends  © 2011 Ifunga Ndana.

 The SCND Genesis: Legends is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 The SCND Genesis: Legends is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with The SCND Genesis: Legends. If not, see <http://www.gnu.org/licenses/>.

 **************************************************************************/
package com.scndgen.legends.network;

import com.scndgen.legends.ScndGenLegends;
import com.scndgen.legends.drawing.LanHostWaitLobby;
import com.scndgen.legends.enums.ModeEnum;
import com.scndgen.legends.enums.SubMode;
import com.scndgen.legends.render.RenderCharacterSelection;
import com.scndgen.legends.state.GameState;
import io.github.subiyacryolite.enginev1.Mode;

import javax.swing.*;
import java.awt.*;
import java.awt.image.VolatileImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.InetAddress;
import java.net.URL;

public class NetworkManager {

    public static final int PORT = 5555;
    public static final int serverLatency = 500;
    public final static int frameRate = 60;
    private static NetworkManager instance;
    public int hostTime;
    public boolean inStoryPane;
    public int item = 0, xyzStickDir;
    public String ServerName;
    public String last, UserName;
    //sever
    private NetworkServer server;
    private InetAddress ServerAddress;
    private boolean messageSent = false, isWaiting = true;
    //client
    private NetworkClient client;
    private JTextField txtServerName = new JTextField(20);
    private JTextField txtUserName = new JTextField(20);
    private int mouseYoffset = 0;
    private SubMode gameMode = SubMode.BLANK;
    //offline, host, client
    private String gameIp = "";
    private String userName;
    private LanHostWaitLobby lanHostWaitLobby;
    private Mode mode;
    private VolatileImage volatileImage;
    private GraphicsEnvironment ge;
    private Graphics2D gc;

    private NetworkManager(String nameOfUser, SubMode subMode) {
        instance = this;
        System.out.println(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getAvailableAcceleratedMemory());
        userName = nameOfUser;
        if (getGameMode() == SubMode.LAN_CLIENT) {
            client = new NetworkClient(GameState.getInstance().getLanHostIp());
        }
        if (gameMode == SubMode.LAN_CLIENT)
            client.sendData("player_QSLV");
    }

    public static synchronized NetworkManager newInstance(String strUser, SubMode subMode) {
        if (instance == null)
            instance = new NetworkManager(strUser, subMode);
        return instance;
    }

    public static NetworkManager getInstance() {
        return instance;
    }

    public String getServerName() {
        return txtServerName.getText();
    }

    public String getServerUserName() {
        return txtUserName.getText();
    }

    public boolean isMessageSent() {
        return messageSent;
    }

    /**
     * Closes hosting NetworkServer
     */
    public void closeTheServer() {
        server.closeServer();
        System.out.println("Closed server");
    }

    /**
     * Closes client
     */
    public void closeTheClient() {
        client.closeClient();
        System.out.println("Closed client");
    }

    public SubMode getGameMode() {
        return gameMode;
    }

    public String getIP() {
        return gameIp;
    }

    public void setIP(String ip) {
        gameIp = ip;
    }

    public void playerFound() {
        int ansx = JOptionPane.showConfirmDialog(null, userName + " , someone wants to fight you!!!!\nWanna waste em!?", "Heads Up", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        switch (ansx) {
            case JOptionPane.YES_OPTION: {
                sendToClient("as1wds2_" + GameState.getInstance().getLogin().getTimeLimit());
                isWaiting = false;
                lanHostWaitLobby.stopRepaint();
                RenderCharacterSelection.getInstance().newInstance();
                RenderCharacterSelection.getInstance().animateCharSelect();
                ScndGenLegends.getInstance().loadMode(ModeEnum.CHAR_SELECT_SCREEN);
                break;
            }
        }
    }

    public NetworkServer getServer() {
        return server;
    }

    public NetworkClient getClient() {
        return client;
    }

    public void sendToClient(String thisTxt) {
        server.sendData(thisTxt);
    }

    public void sendToServer(String thisTxt) {
        client.sendData(thisTxt);
    }

    public boolean downloadFile(String source, String destination) {
        boolean managedToDownload;
        int bufferSize = 1024;
        File out = new File(destination);
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new URL(source).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(out);
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream, bufferSize)) {
            byte[] data = new byte[bufferSize];
            int x;
            while ((x = bufferedInputStream.read(data, 0, bufferSize)) >= 0) {
                bufferedOutputStream.write(data, 0, x);
            }
            bufferedOutputStream.close();
            bufferedInputStream.close();
            managedToDownload = true;
        } catch (Exception e) {
            e.printStackTrace(System.err);
            managedToDownload = false;
        }
        return managedToDownload;
    }
}
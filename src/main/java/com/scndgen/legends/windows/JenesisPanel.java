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
package com.scndgen.legends.windows;

import com.scndgen.legends.ScndGenLegends;
import com.scndgen.legends.drawing.LanHostWaitLobby;
import com.scndgen.legends.enums.Mode;
import com.scndgen.legends.enums.SubMode;
import com.scndgen.legends.executers.CharacterAttacksOnline;
import com.scndgen.legends.executers.OpponentAttacksOnline;
import com.scndgen.legends.network.NetworkClient;
import com.scndgen.legends.network.NetworkServer;
import com.scndgen.legends.render.RenderCharacterSelectionScreen;
import com.scndgen.legends.render.RenderGameplay;
import com.scndgen.legends.render.RenderStageSelect;
import com.scndgen.legends.render.RenderStoryMenu;
import com.scndgen.legends.state.GameState;
import com.scndgen.legends.threads.AudioPlayback;
import com.scndgen.legends.threads.GameInstance;
import io.github.subiyacryolite.enginev1.JenesisMode;
import io.github.subiyacryolite.enginev1.JenesisWindow;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import javax.swing.*;
import java.awt.*;
import java.awt.image.VolatileImage;
import java.net.InetAddress;

public class JenesisPanel extends Pane {

    public static final int PORT = 5555;
    public static final int serverLatency = 500;
    public final static int frameRate = 60;
    private static JenesisPanel instance;
    public int hostTime;
    public boolean inStoryPane;
    public int item = 0, xyzStickDir;
    public OpponentAttacksOnline playerHost2, playerClient1;
    public CharacterAttacksOnline playerHost1, playerClient2;
    public String ServerName;
    public String last, UserName;
    //sever
    private NetworkServer server;
    private InetAddress ServerAddress;
    private int leftyXOffset, onlineClients = 0, hatDir;
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
    private AudioPlayback backgroundMusic;
    private JenesisMode jenesisMode;
    private VolatileImage volatileImage;
    private GraphicsEnvironment ge;
    private Graphics2D gc;

    private JenesisPanel(String nameOfUser, SubMode subMode) {
        instance = this;
        backgroundMusic = new AudioPlayback(AudioPlayback.menuMus(), false);
        backgroundMusic.play();
        System.out.println(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getAvailableAcceleratedMemory());
        userName = nameOfUser;
        leftyXOffset = GameState.getInstance().getLogin().isLeftHanded() ? 548 : 0;
        if (getGameMode() == SubMode.LAN_CLIENT) {
            client = new NetworkClient(GameState.getInstance().getLanHostIp());
        }
        RenderStageSelect.getInstance().newInstance();
        RenderCharacterSelectionScreen.getInstance().newInstance();
        if (gameMode == SubMode.LAN_CLIENT)
            client.sendData("player_QSLV");
        setPrefSize(852, 480);
    }

    public static synchronized JenesisPanel newInstance(String strUser, SubMode subMode) {
        if (instance == null)
            instance = new JenesisPanel(strUser, subMode);
        return instance;
    }

    public static JenesisPanel getInstance() {
        return instance;
    }

    public void stopBackgroundMusic() {
        if (backgroundMusic == null) return;
        backgroundMusic.stop();
        backgroundMusic.close();
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
     * Goes back to main menu
     */
    public void backToMenuScreen() {
        JenesisWindow.getInstance().showModes();
        stopBackgroundMusic();

        focus();
    }

    /**
     * Closes hosting NetworkServer
     */
    public void closeTheServer() {
        server.closeServer();
        System.out.println("Closed server");
        backToMenuScreen();
    }

    /**
     * Closes client
     */
    public void closeTheClient() {
        client.closeClient();
        System.out.println("Closed client");
        backToMenuScreen();
    }

    public SubMode getGameMode() {
        return gameMode;
    }

    /**
     * Contains universal up menu/game movements
     */
    private void up() {
        if (jenesisMode != null)
            jenesisMode.onUp();
    }

    /**
     * Contains universal down menu/game movements
     */
    private void down() {
        if (jenesisMode != null)
            jenesisMode.onDown();
    }

    /**
     * Contains universal left menu/game movements
     */
    private void left() {
        if (jenesisMode != null)
            jenesisMode.onLeft();
    }

    /**
     * Contains universal right menu/game movements
     */
    private void right() {
        if (jenesisMode != null)
            jenesisMode.onRight();
    }

    /**
     * Handles universal onAccept functions
     */
    private void onAccept() {
        if (isWaiting && gameMode == SubMode.LAN_HOST) {
            closeTheServer();
        }
    }

    public void keyPressed(KeyEvent e) {
    }

    public void mouseClicked(MouseEvent mouseEvent) {

    }

    public void mouseEntered(MouseEvent m) {
    }

    public void mouseDragged(MouseEvent m) {
    }

    public void mouseMoved(MouseEvent m) {
    }

    public void mouseExited(MouseEvent m) {
    }

    public void mousePressed(MouseEvent m) {
    }

    public void mouseReleased(MouseEvent m) {
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    public String getIP() {
        return gameIp;
    }

    public void setIP(String ip) {
        gameIp = ip;
    }

    private void onTogglePause() {
        if (getGameMode() == SubMode.SINGLE_PLAYER || getGameMode() == SubMode.STORY_MODE) {
            if (GameInstance.getInstance().gamePaused == false) {
                GameInstance.getInstance().pauseGame();
                RenderGameplay.getInstance().pauseThreads();
                if (GameInstance.getInstance().storySequence == true) {
                    RenderStoryMenu.getInstance().getStoryInstance().pauseDialogue();
                }
            } else {
                RenderGameplay.getInstance().start();
                RenderGameplay.getInstance().resumeThreads();
                if (GameInstance.getInstance().storySequence == true) {
                    RenderStoryMenu.getInstance().getStoryInstance().resumeDialogue();
                }
            }
        }
    }

    public void focus() {
        setFocusTraversable(true);
        setFocused(true);
    }

    /**
     * When a player is found
     */
    public void playerFound() {
        int ansx = JOptionPane.showConfirmDialog(null, userName + " , someone wants to fight you!!!!\nWanna waste em!?", "Heads Up", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        switch (ansx) {
            case JOptionPane.YES_OPTION: {
                sendToClient("as1wds2_" + GameState.getInstance().getLogin().getTimeLimit());
                isWaiting = false;
                lanHostWaitLobby.stopRepaint();
                RenderCharacterSelectionScreen.getInstance().newInstance();
                RenderCharacterSelectionScreen.getInstance().animateCharSelect();
                ScndGenLegends.getInstance().loadMode(Mode.CHAR_SELECT_SCREEN);
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
}
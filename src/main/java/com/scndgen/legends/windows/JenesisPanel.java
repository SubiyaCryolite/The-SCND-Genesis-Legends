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

import com.scndgen.legends.drawing.LanHostWaitLobby;
import com.scndgen.legends.enums.CharacterState;
import com.scndgen.legends.enums.Mode;
import com.scndgen.legends.enums.SubMode;
import com.scndgen.legends.executers.CharacterAttacksOnline;
import com.scndgen.legends.executers.OpponentAttacksOnline;
import com.scndgen.legends.network.NetworkClient;
import com.scndgen.legends.network.NetworkServer;
import com.scndgen.legends.render.*;
import com.scndgen.legends.state.GameState;
import com.scndgen.legends.threads.AudioPlayback;
import com.scndgen.legends.threads.GameInstance;
import io.github.subiyacryolite.enginev1.JenesisMode;
import io.github.subiyacryolite.enginev1.JenesisWindow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseWheelEvent;
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
    public Mode mode = Mode.EMPTY;
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
        gameMode = subMode;
        System.out.println(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getAvailableAcceleratedMemory());
        userName = nameOfUser;
        leftyXOffset = GameState.getInstance().getLogin().isLeftHanded() ? 548 : 0;
        if (getGameMode() == SubMode.LAN_CLIENT) {
            client = new NetworkClient(GameState.getInstance().getLanHostIp());
        }
        RenderStageSelect.getInstance().newInstance();
        switch (subMode) {
            case LAN_HOST:
                server = new NetworkServer();
                server.start();
                isWaiting = true;
                lanHostWaitLobby = new LanHostWaitLobby();
                setContentPane(lanHostWaitLobby);
                break;
            case STORY_MODE:
                RenderStoryMenu.getInstance().newInstance();
                inStoryPane = true;
                this.mode = Mode.STORY_SELECT_SCREEN;
                RenderStoryMenu.getInstance().newInstance();
                setContentPane(RenderStoryMenu.getInstance());
                break;
            case SINGLE_PLAYER:
                this.mode = Mode.CHAR_SELECT_SCREEN;
                RenderCharacterSelectionScreen.getInstance().animateCharSelect();
                break;
            case MAIN_MENU:
                RenderMainMenu.getInstance().newInstance();
                setContentPane(RenderMainMenu.getInstance());
                break;
        }
        RenderCharacterSelectionScreen.getInstance().newInstance();
        setContentPane(RenderCharacterSelectionScreen.getInstance());
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
     * Kinda obvious
     */
    public void stopMenuMusic() {
        //bgMusclose();
    }

    /**
     * Back to characterEnum select screen, when match is over
     */
    public void backToCharSelect() {
        RenderCharacterSelectionScreen.getInstance().newInstance();
        setContentPane(RenderCharacterSelectionScreen.getInstance());
        mode = Mode.CHAR_SELECT_SCREEN;
        RenderGameplay.getInstance().cleanAssets();
        RenderCharacterSelectionScreen.getInstance().animateCharSelect();
        RenderCharacterSelectionScreen.getInstance().newInstance();
        backgroundMusic = new AudioPlayback(AudioPlayback.menuMus(), true);
        backgroundMusic.play();
        focus();
    }

    /**
     * Back to characterEnum select screen,, when match is cancelled
     */
    public void backToCharSelect2() {
        GameInstance.getInstance().gamePaused = false;
        GameInstance.getInstance().terminateGameplay();
        RenderCharacterSelectionScreen.getInstance().animateCharSelect();
        RenderCharacterSelectionScreen.getInstance().newInstance();
        setContentPane(RenderCharacterSelectionScreen.getInstance());
        RenderGameplay.getInstance().cleanAssets();
        RenderCharacterSelectionScreen.getInstance().primaryNotice("Canceled Match");
        mode = Mode.CHAR_SELECT_SCREEN;
        backgroundMusic = new AudioPlayback(AudioPlayback.menuMus(), true);
        backgroundMusic.play();
        focus();
        RenderStageSelect.getInstance().defaultStageValues();
        if (getGameMode() == SubMode.STORY_MODE) {
            RenderCharacterSelectionScreen.getInstance().backToMenu();
        }
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

    /**
     * Cancels match
     */
    public void cancelMatch() {
        int u = JOptionPane.showConfirmDialog(null, "Are you sure you wanna quit?", "Dude!?", JOptionPane.YES_NO_OPTION);
        if (u == JOptionPane.YES_OPTION) {
            if (getGameMode() == SubMode.STORY_MODE) {
                RenderStoryMenu.getInstance().getStoryInstance().firstRun = true;
            }
            //unpause if match was paused
            if (GameInstance.getInstance().gamePaused) {
                onTogglePause();
            }
            RenderStageSelect.getInstance().setSelectedStage(false);
            backToCharSelect2();
        }
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

    /**
     * Handles universal UI onBackCancel
     */
    private void onBackCancel() {
        if (mode == Mode.CHAR_SELECT_SCREEN || mode == Mode.STORY_SELECT_SCREEN) {
            if (getGameMode() == SubMode.SINGLE_PLAYER) {
                RenderCharacterSelectionScreen.getInstance().newInstance();
                RenderCharacterSelectionScreen.getInstance().backToMenu();
            }
        }
        if (mode == Mode.STANDARD_GAMEPLAY_START) {
            onTogglePause();
        } else if (mode == Mode.STORY_SELECT_SCREEN && mode != Mode.STANDARD_GAMEPLAY_START) {
            RenderStoryMenu.getInstance().backToMainMenu();
        }
    }

    /**
     * Global back
     */
    private void back() {
        if (mode == Mode.CHAR_SELECT_SCREEN && getGameMode() == SubMode.SINGLE_PLAYER) {
            RenderCharacterSelectionScreen.getInstance().newInstance();
        } else if (mode == Mode.STANDARD_GAMEPLAY_START) {
            RenderGameplay.getInstance().unQueMove();
        }
    }

    public void triggerFury(CharacterState characterState) {
        RenderGameplay.getInstance().triggerFury(characterState);
    }

    /**
     * Universal trigger
     */
    private void trigger() {
        if (mode == Mode.STANDARD_GAMEPLAY_START)
            RenderGameplay.getInstance().attack();

    }

    public void keyPressed(KeyEvent e) {
        KeyCode keyCode = e.getCode();
        if (keyCode == KeyCode.UP || keyCode == KeyCode.W) {
            up();
        }
        if (keyCode == KeyCode.DOWN || keyCode == KeyCode.S) {
            down();
        }
        if (keyCode == KeyCode.LEFT || keyCode == KeyCode.A) {
            left();
        }
        if (keyCode == KeyCode.RIGHT || keyCode == KeyCode.D) {
            right();
        }
        if (keyCode == KeyCode.BACK_SPACE) {
            back();
        }
        if (keyCode == KeyCode.ENTER) {
            onAccept();
        }
        if (keyCode == KeyCode.ESCAPE) {
            onBackCancel();
        }
        if (keyCode == KeyCode.F5) {
            if (mode == Mode.STANDARD_GAMEPLAY_START) {
                cancelMatch();
            }
        }
        if (keyCode == KeyCode.F12) {
            //screenshot
        } else if (mode == Mode.STANDARD_GAMEPLAY_START) {
            if (keyCode == KeyCode.L) {
                triggerFury(CharacterState.CHARACTER);
            }
            if (keyCode == KeyCode.SPACE) {
                trigger();
            }
        }
    }

    public void mouseWheelMoved(MouseWheelEvent mwe) {
        if (mode == Mode.MAIN_MENU) {
            int count = mwe.getWheelRotation();
            if (count >= 0) {
                RenderMainMenu.getInstance().onDown();
            }
            if (count < 0) {
                RenderMainMenu.getInstance().onUp();
            }
        } else {
            if (GameInstance.getInstance().gameOver == false && GameInstance.getInstance().storySequence == false) {
                int count = mwe.getWheelRotation();
                if (count >= 0) {
                    RenderGameplay.getInstance().onLeft();
                }
                if (count < 0) {
                    RenderGameplay.getInstance().onRight();
                }
            }
        }
    }

    public void mouseClicked(MouseEvent mouseEvent) {
        MouseButton mb = mouseEvent.getButton();
        if (jenesisMode != null)
            jenesisMode.mouseClicked(mouseEvent);
        if (mode == Mode.STANDARD_GAMEPLAY_START) {
            if (GameInstance.getInstance().gameOver == false && GameInstance.getInstance().storySequence == false) {
                if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                    if (mouseEvent.getX() > (29 + leftyXOffset) && mouseEvent.getX() < (220 + leftyXOffset) && (mouseEvent.getY() > 358)) {
                        RenderGameplay.getInstance().onAccept();
                    }
                    if (mouseEvent.getX() < (29 + leftyXOffset)) {
                        RenderGameplay.getInstance().onLeft();
                    }
                    if (mouseEvent.getX() > (220 + leftyXOffset) && mouseEvent.getX() < (305 + leftyXOffset)) {
                        RenderGameplay.getInstance().onRight();
                    } else if ((mouseEvent.getX() > 25 && mouseEvent.getX() < 46) && (mouseEvent.getY() > 190 && mouseEvent.getY() < 270)) {
                        triggerFury(CharacterState.CHARACTER);
                    }
                }
                if (mouseEvent.getButton() == MouseButton.MIDDLE) {
                    triggerFury(CharacterState.CHARACTER);
                }
                if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                    RenderGameplay.getInstance().unQueMove();
                }
            }
        }
        focus();
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
                setContentPane(RenderCharacterSelectionScreen.getInstance());
                mode = Mode.CHAR_SELECT_SCREEN;//RenderCharacterSelectionScreen.getInstance().animCloud();
                RenderCharacterSelectionScreen.getInstance().animateCharSelect();
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

    protected final RenderingHints renderHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //anti aliasing, kill jaggies

    private void setContentPane(final JenesisMode mode) {
        this.jenesisMode = mode;
    }
}
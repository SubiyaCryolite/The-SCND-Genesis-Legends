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

import com.scndgen.legends.Language;
import com.scndgen.legends.LoginScreen;
import com.scndgen.legends.OverWorld;
import com.scndgen.legends.drawing.DrawWaiting;
import com.scndgen.legends.enums.CharacterState;
import com.scndgen.legends.enums.Mode;
import com.scndgen.legends.enums.Overlay;
import com.scndgen.legends.enums.SubMode;
import com.scndgen.legends.executers.CharacterAttacksOnline;
import com.scndgen.legends.executers.OpponentAttacksOnline;
import com.scndgen.legends.network.NetworkClient;
import com.scndgen.legends.network.NetworkServer;
import com.scndgen.legends.render.*;
import com.scndgen.legends.threads.AudioPlayback;
import com.scndgen.legends.threads.GameInstance;
import io.github.subiyacryolite.enginev1.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.io.*;
import java.net.InetAddress;

public class JenesisPanel extends Pane {

    public static final int PORT = 5555;
    public static final int serverLatency = 500;
    public final static int frameRate = 60;
    private static JenesisPanel instance;
    public int hostTime;
    public boolean inStoryPane;
    public boolean gameRunning = false, withinMenuPanel, freeToSave = true, controller = false;
    public int item = 0, xyzStickDir;
    public Mode mode = Mode.EMPTY;
    public OpponentAttacksOnline playerHost2, playerClient1;
    public CharacterAttacksOnline playerHost1, playerClient2;
    public String ServerName;
    public String last, UserName;
    private boolean[] buttonPressed;
    //sever
    private NetworkServer server;
    private InetAddress ServerAddress;
    private int leftyXOffset, onlineClients = 0, hatDir;
    private boolean messageSent = false, isWaiting = true;
    //client
    private NetworkClient client;
    private JTextField txtServerName = new JTextField(20);
    private JTextField txtUserName = new JTextField(20);
    private OverWorld world;
    private int mouseYoffset = 0;
    private SubMode gameMode = SubMode.BLANK;
    //offline, host, client
    private String gameIp = "";
    private String userName;
    private DrawWaiting drawWait;
    private JenesisImageLoader pix;
    private AudioPlayback backgroundMusic;
    private JenesisMode jenesisMode;
    private VolatileImage volatileImage;
    private GraphicsEnvironment ge;
    private GraphicsConfiguration gc;
    private Graphics2D g2d;

    private JenesisPanel(String nameOfUser, SubMode subMode) {
        instance = this;
        try {
            if (JenesisGamePad.getInstance().NUM_BUTTONS > 0) {
                controller = true;
                buttonPressed = new boolean[JenesisGamePad.getInstance().NUM_BUTTONS];
                pollController();
            }
        } catch (Exception e) {
        }
        backgroundMusic = new AudioPlayback(AudioPlayback.menuMus(), false);
        backgroundMusic.play();
        pix = new JenesisImageLoader();
        gameMode = subMode;
        System.out.println(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getAvailableAcceleratedMemory());
        userName = nameOfUser;

        if (getGameMode() == SubMode.LAN_HOST) {
            server = new NetworkServer();
            server.start();
        }

        if (LoginScreen.getInstance().isLefty() != null && LoginScreen.getInstance().isLefty().equalsIgnoreCase("no")) {
            leftyXOffset = 548;
        } else {
            leftyXOffset = 0;
        }

        if (getGameMode() == SubMode.LAN_CLIENT) {
            client = new NetworkClient(LoginScreen.getInstance().getIP());
        }
        //seti(loadIconImage("images/GameIco.ico"));
        RenderStageSelect.getInstance().newInstance();
        if (subMode == SubMode.LAN_HOST) {
            isWaiting = true;
            drawWait = new DrawWaiting();
        } else if (subMode == SubMode.STORY_MODE) {
            RenderStoryMenu.getInstance().newInstance();
            inStoryPane = true;
            this.mode = Mode.STORY_SELECT_SCREEN;
        } else {
            this.mode = Mode.CHAR_SELECT_SCREEN;
            RenderCharacterSelectionScreen.getInstance().animateCharSelect();
        }

        //------------ JFrame properties

        if (subMode == SubMode.LAN_HOST) {
            setContentPane(drawWait);
        } else if (subMode == SubMode.STORY_MODE) {
            RenderStoryMenu.getInstance().newInstance();
            setContentPane(RenderStoryMenu.getInstance());
        } else if (subMode == SubMode.MAIN_MENU) {
            RenderMainMenu.getInstance().newInstance();
            setContentPane(RenderMainMenu.getInstance());
        } else {
            RenderCharacterSelectionScreen.getInstance().newInstance();
            setContentPane(RenderCharacterSelectionScreen.getInstance());
        }
        if (gameMode == SubMode.LAN_CLIENT) {
            client.sendData("player_QSLV");
        }
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
     * Polls game pads for dataInputStream data
     */
    private void pollController() {
        new Thread() {

            @SuppressWarnings({"SleepWhileHoldingLock", "static-access"})
            public void run() {
                try {
                    do {
                        this.sleep(33);
                        if (!JenesisGamePad.getInstance().canPoll()) continue;
                        JenesisGamePad.getInstance().poll();
                        //update bottons
                        buttonPressed = JenesisGamePad.getInstance().getButtons();
                        // get compass direction for the two analog sticks
                        xyzStickDir = JenesisGamePad.getInstance().getXYStickDir();
                        if (xyzStickDir == JenesisGamePad.getInstance().NORTH) {
                            up();
                        } else if (xyzStickDir == JenesisGamePad.getInstance().SOUTH) {
                            down();
                        } else if (xyzStickDir == JenesisGamePad.getInstance().WEST) {
                            left();
                        } else if (xyzStickDir == JenesisGamePad.getInstance().EAST) {
                            right();
                        }
                        // get POV hat compass direction
                        hatDir = JenesisGamePad.getInstance().getHatDir();
                        if (hatDir == JenesisGamePad.getInstance().SOUTH) {
                            down();
                        }
                        if (hatDir == JenesisGamePad.getInstance().NORTH) {
                            up();
                        }
                        if (hatDir == JenesisGamePad.getInstance().WEST) {
                            left();
                        }
                        if (hatDir == JenesisGamePad.EAST) {
                            right();
                        }
                        //button one
                        if (buttonPressed[0]) {
                            back();
                        }
                        if (buttonPressed[1]) {
                            trigger();
                        }
                        if (buttonPressed[2]) {
                            accept();
                        }
                        if (buttonPressed[3]) {
                            escape();
                        }
                        if (buttonPressed[5]) {
                            triggerFury(CharacterState.CHARACTER);
                        }
                        hatDir = JenesisGamePad.getInstance().getXYStickDir();
                        hatDir = JenesisGamePad.getInstance().getZRZStickDir();
                        buttonPressed = JenesisGamePad.getInstance().getButtons();
                    } while (true);
                } catch (Exception ex) {
                    ex.printStackTrace(System.err);
                }
            }
        }.start();
    }

    public void newGame() {
        mode = Mode.STANDARD_GAMEPLAY;
        stopBackgroundMusic();
        RenderGameplay.getInstance().newInstance();
        setContentPane(RenderGameplay.getInstance());
        RenderGameplay.getInstance().startFight();

    }

    public void startStoryMatch() {
        mode = Mode.STANDARD_GAMEPLAY;
        stopBackgroundMusic();
        setContentPane(RenderGameplay.getInstance());
        RenderGameplay.getInstance().startFight();
    }

    /**
     * Goes back to main menu
     */
    public void backToMenuScreen() {
        JenesisWindow.getInstance().showModes();
        stopBackgroundMusic();
        RenderGameplay.getInstance().cleanAssets();
        focus();
    }

    /**
     * Increments to next stage in story scene
     */
    public void nextStage() {
        //bgMusclose();
        mode = Mode.STANDARD_GAMEPLAY;
        stopBackgroundMusic();
        setContentPane(RenderGameplay.getInstance());
        RenderGameplay.getInstance().startFight();
    }

    /**
     * Kinda obvious
     */
    public void stopMenuMusic() {
        //bgMusclose();
    }

    /**
     * Finds out if a match is running
     *
     * @return status of match
     */
    public boolean getIsGameRunning() {
        return mode == Mode.STANDARD_GAMEPLAY;
    }

    /**
     * Sets the game to running
     */
    public void setGameRunning() {
        gameRunning = true;
    }

    /**
     * Enables player to select a stage
     */
    public void selectStage() {
        setContentPane(RenderStageSelect.getInstance());
        mode = Mode.STAGE_SELECT_SCREEN;
        focus();
    }

    /**
     * Back to characterEnum select screen, when match is over
     */
    public void backToCharSelect() {
        gameRunning = false;
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
        gameRunning = false;
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
                pause();
            }
            RenderStageSelect.getInstance().setSelectedStage(false);
            backToCharSelect2();
        }
    }

    public SubMode getGameMode() {
        return gameMode;
    }

    /**
     * Vibrate
     */
    private void quickVibrate(float strength, int length) {
        final float power = strength;
        final int time = length;
        new Thread() {
            public void run() {
                try {
                    JenesisGamePad.getInstance().setRumbler(true, power);
                    this.sleep(time);
                    JenesisGamePad.getInstance().setRumbler(false, 0.0f);
                } catch (Exception e) {
                }
            }
        }.start();
    }

    /**
     * Contains universal up menu/game movements
     */
    private void up() {
        if (mode == Mode.CHAR_SELECT_SCREEN) {
            RenderCharacterSelectionScreen.getInstance().moveUp();
        } else if (mode == Mode.STORY_SELECT_SCREEN) {
            RenderStoryMenu.getInstance().moveUp();
        } else if (mode == Mode.STAGE_SELECT_SCREEN) {
            //client should be able to meddle in stage select
            if (getGameMode() != SubMode.LAN_CLIENT) {
                RenderStageSelect.getInstance().moveUp();
            }
        } else if (getIsGameRunning()) {
            RenderGameplay.getInstance().upItem();
        }
    }

    /**
     * Contains universal down menu/game movements
     */
    private void down() {
        if (mode == Mode.CHAR_SELECT_SCREEN) {
            RenderCharacterSelectionScreen.getInstance().moveDown();
        } else if (mode == Mode.STORY_SELECT_SCREEN) {
            RenderStoryMenu.getInstance().moveDown();
        } else if (mode == Mode.STAGE_SELECT_SCREEN) {
            //client should not be able to meddle in stage select
            if (getGameMode() != SubMode.LAN_CLIENT) {
                RenderStageSelect.getInstance().moveDown();
            }
        } else if (getIsGameRunning()) {
            RenderGameplay.getInstance().downItem();
        }
    }

    /**
     * Contains universal left menu/game movements
     */
    private void left() {
        if (mode == Mode.CHAR_SELECT_SCREEN) {
            RenderCharacterSelectionScreen.getInstance().moveLeft();
        } else if (mode == Mode.STORY_SELECT_SCREEN) {
            RenderStoryMenu.getInstance().moveLeft();
        } else if (mode == Mode.STAGE_SELECT_SCREEN) {
            //client should be able to meddle in stage select
            if (getGameMode() != SubMode.LAN_CLIENT) {
                RenderStageSelect.getInstance().moveLeft();
            }
        } else if (getIsGameRunning()) {
            RenderGameplay.getInstance().prevAnimation();
        }
    }

    /**
     * Contains universal right menu/game movements
     */
    private void right() {
        if (mode == Mode.CHAR_SELECT_SCREEN) {
            RenderCharacterSelectionScreen.getInstance().moveRight();
        } else if (mode == Mode.STORY_SELECT_SCREEN) {
            RenderStoryMenu.getInstance().moveRight();
        } else if (mode == Mode.STAGE_SELECT_SCREEN) {
            RenderStageSelect.getInstance().moveRight();
        } else if (getIsGameRunning()) {
            RenderGameplay.getInstance().nextAnimation();
        }
    }

    /**
     * Handles universal accept functions
     */
    private void accept() {
        if (getIsGameRunning()) {
            if (GameInstance.getInstance().gamePaused == false) {
                //in game, no story sequence
                if (GameInstance.getInstance().gameOver == false && GameInstance.getInstance().storySequence == false) {
                    RenderGameplay.getInstance().moveSelected();
                } //in game, during story sequence
                else if (GameInstance.getInstance().gameOver == false && GameInstance.getInstance().storySequence == true) {
                    RenderStoryMenu.getInstance().getStoryInstance().skipDialogue();
                } //story scene -- after text
                else if (GameInstance.getInstance().gameOver == false && RenderStoryMenu.getInstance().getStoryInstance().doneShowingText) {
                    RenderStoryMenu.getInstance().getStoryInstance().skipDialogue();
                } else {
                    //if person presses twice the stage increments twice
                    //this prevents that
                    //it only free to save when its game over
                    //one a save is used, it not free to save (i.e null)
                    if (freeToSave) {
                        freeToSave = false;
                        if (getGameMode() == SubMode.SINGLE_PLAYER
                                || getGameMode() == SubMode.LAN_CLIENT
                                || getGameMode() == SubMode.LAN_HOST) {
                            GameInstance.getInstance().closingThread(1);
                        } else {
                            GameInstance.getInstance().closingThread(0);
                        }
                    }
                }
            }
        } //cancel hosting
        else if (isWaiting && gameMode == SubMode.LAN_HOST) {
            closeTheServer();
        } else if (mode == Mode.CHAR_SELECT_SCREEN) {
            //if both CharacterEnum are selected
            if (RenderCharacterSelectionScreen.getInstance().getCharacterSelected() && RenderCharacterSelectionScreen.getInstance().getOpponentSelected()) {
                if (getGameMode() == SubMode.SINGLE_PLAYER || getGameMode() == SubMode.LAN_HOST) {
                    quickVibrate(0.6f, 1000);
                    RenderCharacterSelectionScreen.getInstance().beginGame();
                }
            } else {
                quickVibrate(0.4f, 1000);
                RenderCharacterSelectionScreen.getInstance().selectCharacter();
            }
        } else if (mode == Mode.STORY_SELECT_SCREEN) {
            RenderStoryMenu.getInstance().selectStage();
            quickVibrate(0.4f, 1000);
        } else if (mode == Mode.STAGE_SELECT_SCREEN) {
            //client should be able to meddle in stage select
            if (getGameMode() != SubMode.LAN_CLIENT) {
                if (RenderCharacterSelectionScreen.getInstance().getCharacterSelected() && RenderCharacterSelectionScreen.getInstance().getOpponentSelected() && (getGameMode() == SubMode.SINGLE_PLAYER || getGameMode() == SubMode.LAN_HOST)) {
                    quickVibrate(0.66f, 1000);
                    RenderStageSelect.getInstance().selectStage(RenderStageSelect.getInstance().getHoveredStage());
                }
            }
        }
    }

    /**
     * Handles universal UI escape
     */
    private void escape() {
        if (mode == Mode.CHAR_SELECT_SCREEN || mode == Mode.STORY_SELECT_SCREEN) {
            if (getGameMode() == SubMode.SINGLE_PLAYER) {
                RenderCharacterSelectionScreen.getInstance().newInstance();
                RenderCharacterSelectionScreen.getInstance().backToMenu();
            }
        }
        if (getIsGameRunning()) {
            pause();
        } else if (mode == Mode.STORY_SELECT_SCREEN && !getIsGameRunning()) {
            RenderStoryMenu.getInstance().backToMainMenu();
        }
    }

    /**
     * Global back
     */
    private void back() {
        if (mode == Mode.CHAR_SELECT_SCREEN) {
            if (getGameMode() == SubMode.SINGLE_PLAYER) {
                RenderCharacterSelectionScreen.getInstance().newInstance();
            }
        } else if (getIsGameRunning()) {
            RenderGameplay.getInstance().unQueMove();
        }
    }

    public void triggerFury(CharacterState who) {
        RenderGameplay.getInstance().triggerFury(who);
    }

    /**
     * Universal trigger
     */
    private void trigger() {
        if (getIsGameRunning())
            RenderGameplay.getInstance().attack();

    }

    public void keyPressed(KeyEvent e) {
        KeyCode keyCode = e.getCode();
        if (keyCode == KeyCode.UP || keyCode == KeyCode.W) {
            up();
        }
        if (keyCode == KeyCode.M) {
            if (isWaiting && gameMode == SubMode.LAN_HOST) {
                world = new OverWorld();
            }
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
            accept();
        }
        if (keyCode == KeyCode.ESCAPE) {
            escape();
        }
        if (keyCode == KeyCode.F4) {
            LoginScreen.getInstance().getMenu().exit();
        }
        if (keyCode == KeyCode.F5) {
            if (getIsGameRunning()) {
                cancelMatch();
            }
        }
        if (keyCode == KeyCode.F12) {
            if (mode == Mode.CHAR_SELECT_SCREEN) {
                RenderCharacterSelectionScreen.getInstance().captureScreenShot();
            } else if (mode == Mode.STORY_SELECT_SCREEN) {
                RenderStoryMenu.getInstance().captureScreenShot();
            } else if (mode == Mode.STAGE_SELECT_SCREEN) {
                RenderStageSelect.getInstance().captureScreenShot();
            } else if (getIsGameRunning()) {
                RenderGameplay.getInstance().captureScreenShot();
            }
        } else if (getIsGameRunning()) {
            if (keyCode == KeyCode.L) {
                triggerFury(CharacterState.CHARACTER);
            }
            if (keyCode == KeyCode.SPACE) {
                trigger();
            }
        }
        if (mode == Mode.MAIN_MENU) {

            if (keyCode == KeyCode.DOWN || keyCode == KeyCode.S) {
                RenderMainMenu.getInstance().goDown();
            }
            if (keyCode == KeyCode.RIGHT) {
                if (RenderMainMenu.getInstance().getOverlay() == Overlay.TUTORIAL)
                    RenderMainMenu.getInstance().advanceTutorial();
            }
            if (keyCode == KeyCode.LEFT) {
                if (RenderMainMenu.getInstance().getOverlay() == Overlay.TUTORIAL)
                    RenderMainMenu.getInstance().reverseTutorial();
            }
            if (keyCode == KeyCode.DIGIT1) {
                if (RenderMainMenu.getInstance().getOverlay() == Overlay.TUTORIAL)
                    RenderMainMenu.getInstance().sktpToTut(0);
            }
            if (keyCode == KeyCode.DIGIT2) {
                if (RenderMainMenu.getInstance().getOverlay() == Overlay.TUTORIAL)
                    RenderMainMenu.getInstance().sktpToTut(3);
            }
            if (keyCode == KeyCode.DIGIT3) {
                if (RenderMainMenu.getInstance().getOverlay() == Overlay.TUTORIAL)
                    RenderMainMenu.getInstance().sktpToTut(11);
            }
            if (keyCode == KeyCode.DIGIT4) {
                if (RenderMainMenu.getInstance().getOverlay() == Overlay.TUTORIAL)
                    RenderMainMenu.getInstance().sktpToTut(20);
            }
            if (keyCode == KeyCode.DIGIT5) {
                if (RenderMainMenu.getInstance().getOverlay() == Overlay.TUTORIAL)
                    RenderMainMenu.getInstance().sktpToTut(27);
            }
            if (keyCode == KeyCode.DIGIT6) {
                if (RenderMainMenu.getInstance().getOverlay() == Overlay.TUTORIAL)
                    RenderMainMenu.getInstance().sktpToTut(32);
            }
            if (keyCode == KeyCode.F12) {
                RenderMainMenu.getInstance().captureScreenShot();
            }
        }
    }

    public void mouseWheelMoved(MouseWheelEvent mwe) {
        if (gameRunning) {
            if (GameInstance.getInstance().gameOver == false && GameInstance.getInstance().storySequence == false) {
                int count = mwe.getWheelRotation();
                if (count >= 0) {
                    RenderGameplay.getInstance().prevAnimation();
                }
                if (count < 0) {
                    RenderGameplay.getInstance().nextAnimation();
                }
            }
        }
        if (mode == Mode.MAIN_MENU) {
            int count = mwe.getWheelRotation();
            //down - positive values
            if (count >= 0) {
                RenderMainMenu.getInstance().goDown();
            }
            //up -negative values
            if (count < 0) {
                RenderMainMenu.getInstance().goUp();
            }
        }
    }

    public void mouseClicked(MouseEvent m) {
        MouseButton mb = m.getButton();
        if (mode == Mode.CHAR_SELECT_SCREEN && RenderCharacterSelectionScreen.getInstance().getWithinCharPanel()) {
            if (RenderCharacterSelectionScreen.getInstance().getCharacterSelected() && RenderCharacterSelectionScreen.getInstance().getOpponentSelected()) {
                if (getGameMode() == SubMode.SINGLE_PLAYER || getGameMode() == SubMode.LAN_HOST) {
                    quickVibrate(0.6f, 1000);
                    RenderCharacterSelectionScreen.getInstance().beginGame();
                }
            } else {
                RenderCharacterSelectionScreen.getInstance().selectCharacter();
            }
        }
        if (mode == Mode.STORY_SELECT_SCREEN && withinMenuPanel) {
            RenderStoryMenu.getInstance().selectStage();
        }
        if (mode == Mode.STAGE_SELECT_SCREEN && RenderStageSelect.getInstance().getWithinCharPanel()) {
            RenderStageSelect.getInstance().selectStage(RenderStageSelect.getInstance().getHoveredStage());
        }
        if (mode == Mode.MAIN_MENU) {
            int x = RenderMainMenu.getInstance().getXMenu();
            int y = RenderMainMenu.getInstance().getYMenu() - 14;
            int space = RenderMainMenu.getInstance().getSpacer();
            if (RenderMainMenu.getInstance().getOverlay() == Overlay.TUTORIAL) {
                if (m.getX() >= 425) {
                    RenderMainMenu.getInstance().advanceTutorial();
                } else {
                    RenderMainMenu.getInstance().reverseTutorial();
                }
            } else if ((m.getY() > y) && (m.getY() < (y + (space * 13))) && m.getX() > x) {
                if (m.getButton() == MouseButton.PRIMARY) {

                    SubMode destination = RenderMainMenu.getInstance().getMenuModeStr();
                    if (destination == SubMode.LAN_HOST) {
                        RenderMainMenu.getInstance().primaryNotice(Language.getInstance().getLine(107));
                        JenesisWindow.getInstance().setContentPane(newInstance(JenesisWindow.strUser, destination));
                    } else if (destination == SubMode.SINGLE_PLAYER) {
                        RenderMainMenu.getInstance().primaryNotice(Language.getInstance().getLine(108));
                        JenesisWindow.getInstance().setContentPane(newInstance(JenesisWindow.strUser, destination));
                    } else if (destination == SubMode.STORY_MODE) {
                        JenesisWindow.getInstance().setContentPane(newInstance(JenesisWindow.strUser, SubMode.STORY_MODE));
                    } else if (destination == SubMode.STATS) {
                        RenderMainMenu.getInstance().setOverlay(Overlay.STATISTICS);
                    } else if (destination == SubMode.ACH) {
                        RenderMainMenu.getInstance().refreshStats();
                        RenderMainMenu.getInstance().setOverlay(Overlay.ACHIEVEMENTS);
                    } else if (destination == SubMode.TUTORIAL) {
                        RenderMainMenu.getInstance().setOverlay(Overlay.TUTORIAL);
                        RenderMainMenu.getInstance().startTut();
                    }
                }
                //middle mouse
                if (m.getButton() == MouseButton.SECONDARY) {
                }

                //middle mouse
                if (m.getButton() == MouseButton.MIDDLE) {
                }
            }
        } else if (getIsGameRunning()) {
            if (GameInstance.getInstance().gameOver == false && GameInstance.getInstance().storySequence == false) {
                if (m.getButton() == MouseButton.PRIMARY) {
                    if (m.getX() > (29 + leftyXOffset) && m.getX() < (220 + leftyXOffset) && (m.getY() > 358)) {
                        RenderGameplay.getInstance().moveSelected();
                    }
                    if (m.getX() < (29 + leftyXOffset)) {
                        RenderGameplay.getInstance().prevAnimation();
                    }
                    if (m.getX() > (220 + leftyXOffset) && m.getX() < (305 + leftyXOffset)) {
                        RenderGameplay.getInstance().nextAnimation();
                    } else if ((m.getX() > 25 && m.getX() < 46) && (m.getY() > 190 && m.getY() < 270)) {
                        triggerFury(CharacterState.CHARACTER);
                    }
                }
                if (m.getButton() == MouseButton.MIDDLE) {
                    triggerFury(CharacterState.CHARACTER);
                }
                if (m.getButton() == MouseButton.SECONDARY) {
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
        if (mode == Mode.CHAR_SELECT_SCREEN) {
            RenderCharacterSelectionScreen.getInstance().mouseMoved(m.getX(), m.getY());
        }
        if (mode == Mode.STORY_SELECT_SCREEN) {
            RenderStoryMenu.getInstance().mouseMoved(m.getX(), m.getY());
        }
        if (mode == Mode.STAGE_SELECT_SCREEN) {
            //if you're a client stay still
            if (getGameMode() != SubMode.LAN_CLIENT) {
                RenderStageSelect.getInstance().mouseMoved(m.getX(), m.getY());
            }
        } else if (getIsGameRunning()) {
            RenderGameplay.getInstance().mouseMoved(m.getX(), m.getY());
        }
        if (jenesisMode != null)
            jenesisMode.mouseMoved(m);
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

    private void pause() {
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

    void savefile(String outfil, String inhalt) {
        try {
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outfil), "UTF8"));
            out.write(inhalt);
            out.close();
        } catch (UnsupportedEncodingException e) {
            System.out.println(">>> loadsave: no UTF-8 upport");
        } catch (IOException e) {
            System.out.println(">>> loadsave: Could not write " + outfil);
        }
    }

    public String getUserName() {
        return userName;
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
                sendToClient("as1wds2_" + LoginScreen.getInstance().timePref);
                isWaiting = false;
                drawWait.stopRepaint();
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

    /**
     * Hardware acceleration
     */
    protected final void createBackBuffer() {
        if (volatileImage == null) {
            ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            System.out.println("Accelerateable memory!!!!!!!!!!! " + ge.getDefaultScreenDevice().getAvailableAcceleratedMemory());
            gc = ge.getDefaultScreenDevice().getDefaultConfiguration();
            volatileImage = gc.createCompatibleVolatileImage(LoginScreen.getInstance().getGameWidth(), LoginScreen.getInstance().getGameHeight());
            volatileImage.setAccelerationPriority(1.0f);
            g2d = volatileImage.createGraphics();
            g2d.setRenderingHints(renderHints); //activate aliasing
        }
    }

    public void paintComponent(Graphics g) {
        createBackBuffer();
        if (jenesisMode == null) return;
        jenesisMode.loadAssets();
        //jenesisMode.paintComponent(g2d, this);
        //g.drawImage(volatileImage, 0, 0, this);
    }

    private void setContentPane(final JenesisMode mode) {
        this.jenesisMode = mode;
    }

    /**
     * Gets screenshot
     */
    public final void captureScreenShot() {
        try {
            BufferedImage bufferedImage = volatileImage.getSnapshot();
            File file;
            if (!new File(System.getProperty("user.home") + File.separator + ".config" + File.separator + "scndgen" + File.separator + "screenshots").exists())
                new File(System.getProperty("user.home") + File.separator + ".config" + File.separator + "scndgen" + File.separator + "screenshots").mkdirs();
            file = new File(System.getProperty("user.home") + File.separator + ".config" + File.separator + "scndgen" + File.separator + "screenshots" + File.separator + generateUID() + ".png");
            if (ImageIO.write(bufferedImage, "png", file))
                JenesisGlassPane.getInstance().primaryNotice(Language.getInstance().getLine(170));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Generates unique ID for screens
     *
     * @return unique ID
     */
    public final String generateUID() {
        String random_name = "scndgen-legends_";
        StringBuilder userIDBuff = new StringBuilder(random_name);
        String[] letters = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
        userIDBuff.append("").append(Math.round(Math.random() * 100)).append("_");
        int v1 = Integer.parseInt("" + Math.round((Math.random() * 24) + 1));
        int v2 = Integer.parseInt("" + Math.round((Math.random() * 24) + 1));
        int v3 = Integer.parseInt("" + Math.round((Math.random() * 24) + 1));
        userIDBuff.append(letters[v1]);
        userIDBuff.append(letters[v2]);
        userIDBuff.append(letters[v3]);
        userIDBuff.append("_").append(Math.round(Math.random() * 10000)).append("");
        random_name = userIDBuff.toString();

        return random_name;
    }
}
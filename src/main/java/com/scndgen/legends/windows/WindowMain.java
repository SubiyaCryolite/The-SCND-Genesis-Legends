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

import com.scndgen.legends.GamePadController;
import com.scndgen.legends.LoginScreen;
import com.scndgen.legends.attacks.AttacksOpp1;
import com.scndgen.legends.attacks.AttacksOpp2;
import com.scndgen.legends.attacks.AttacksPlyr1;
import com.scndgen.legends.attacks.AttacksPlyr2;
import com.scndgen.legends.drawing.DrawGame;
import com.scndgen.legends.drawing.DrawWaiting;
import com.scndgen.legends.engine.JenesisImage;
import com.scndgen.legends.executers.ExecuterMovesCharOnline;
import com.scndgen.legends.executers.ExecuterMovesOppOnline;
import com.scndgen.legends.menus.*;
import com.scndgen.legends.menus.CanvasCharSelect;
import com.scndgen.legends.menus.CanvasStageSelect;
import com.scndgen.legends.OverWorld;
import com.scndgen.legends.threads.ThreadGameInstance;
import com.scndgen.legends.threads.ThreadMP3;
import com.scndgen.legends.threads.ThreadClashSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class WindowMain extends JFrame implements KeyListener, WindowListener, MouseListener, MouseMotionListener, MouseWheelListener {

    public static String lanHost = "lanHost";
    public static String lanClient = "lanClient";
    public static String singlePlayer = "singlePlayer";
    public static String singlePlayer2 = "singlePlayer2";
    public static String storyMode = "StoryMode";
    public static int tTime;
    private final int PORT = 5555;
    public int hostTime;
    public JFrame daWindow;
    public boolean inStoryPane;
    public boolean isGameRunning = false, withinMenuPanel, freeToSave = true, withinCharPanel = false, controller = false;
    public int item = 0, storedX = 99, storedY = 99, compassDir2;
    public String currentScreen = "";
    RandomAccessFile rand;
    private AttacksOpp1 oppenentEntity;
    private AttacksOpp2 oppenentEntity2;
    private AttacksPlyr1 characterEntity;
    private AttacksPlyr2 characterEntity2;
    private boolean[] buttonz;
    private boolean[] buttons;
    //sever
    private jenesisServer server;
    private InetAddress ServerAddress;
    private int serverLatency;
    private int leftyXOffset, onlineClients = 0, compassDir;
    private boolean messageSent = false, isWaiting = true, isNotRepainting = true, doneChilling = true;
    private ExecuterMovesOppOnline playerHost2, playerClient1;
    private ExecuterMovesCharOnline playerHost1, playerClient2;
    //client
    private jenesisClient client;
    private JTextField Server = new JTextField(20);
    private JLabel myLabel = new JLabel("Server Name :");
    private JTextField User = new JTextField(20);
    private String ServerName;
    private String last, UserName;
    private OverWorld world;
    private CanvasGameRender newGame;
    private CanvasCharSelect charPan;
    private CanvasStorySelect storyPane;
    private CanvasStageSelect stage;
    private int mouseYoffset = 0;
    private String gameMode;
    //offline, host, client
    private String gameIp = "";
    private String userName;
    private DrawWaiting drawWait;
    private JenesisImage pix;
    private ArrayList imageList;
    private Image ico16, ico22, ico24, ico32, ico48, ico72, ico96, ico128, ico256;
    private GamePadController gpController;
    private WindowMain gameWindow;
    private ThreadMP3 bgMus;
    private int topY, topX, columns, vspacer, hspacer, rows;

    public WindowMain(String nameOfUser, String mode) {
        try {
            gpController = LoginScreen.getLoginScreen().getMenu().getController();
            if (gpController.NUM_BUTTONS > 0) {
                controller = true;
                buttonz = new boolean[gpController.NUM_BUTTONS];
                pollController();
            }
        } catch (Exception e) {
        }
        bgMus = new ThreadMP3(ThreadMP3.menuMus(), false);
        bgMus.play();
        pix = new JenesisImage();
        gameMode = mode;
        System.out.println(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getAvailableAcceleratedMemory());
        userName = nameOfUser;
        serverLatency = 500;

        if (getGameMode().equalsIgnoreCase(lanHost)) {
            server = new jenesisServer();
            server.start();
        }

        if (LoginScreen.getLoginScreen().isLefty().equalsIgnoreCase("no")) {
            leftyXOffset = 548;
        } else {
            leftyXOffset = 0;
        }

        if (getGameMode().equalsIgnoreCase(lanClient)) {
            client = new jenesisClient(LoginScreen.getLoginScreen().getIP());
        }
        daWindow = new JFrame();
        daWindow.setUndecorated(true);
        daWindow.setTitle("The SCND Genesis: Legends" + newGame.getVersionStr());
        ico16 = pix.loadImageFromToolkitNoScale("images/GameIco16.png");
        ico22 = pix.loadImageFromToolkitNoScale("images/GameIco22.png");
        ico24 = pix.loadImageFromToolkitNoScale("images/GameIco24.png");
        ico32 = pix.loadImageFromToolkitNoScale("images/GameIco32.png");
        ico48 = pix.loadImageFromToolkitNoScale("images/GameIco48.png");
        ico72 = pix.loadImageFromToolkitNoScale("images/GameIco72.png");
        ico96 = pix.loadImageFromToolkitNoScale("images/GameIco96.png");
        ico128 = pix.loadImageFromToolkitNoScale("images/GameIco128.png");
        ico256 = pix.loadImageFromToolkitNoScale("images/GameIco256.png");
        imageList = new ArrayList();
        imageList.add(ico16);
        imageList.add(ico22);
        imageList.add(ico24);
        imageList.add(ico32);
        imageList.add(ico48);
        imageList.add(ico72);
        imageList.add(ico96);
        imageList.add(ico128);
        imageList.add(ico256);
        daWindow.setIconImages(imageList);
        //daWindow.seti(loadIconImage("images/GameIco.ico"));

        charPan = new CanvasCharSelect();
        stage = new CanvasStageSelect();
        if (mode.equals(lanHost)) {
            isWaiting = true;
            drawWait = new DrawWaiting();
        } else if (mode.equals(storyMode)) {
            storyPane = new CanvasStorySelect();
            inStoryPane = true;
            currentScreen = "storySelectScreen";
        } else {
            currentScreen = "charSelectScreen";
            charPan.animateCharSelect();
        }

        //------------ JFrame properties

        if (mode.equals(lanHost)) {
            daWindow.setContentPane(drawWait);
        } else if (mode.equals(storyMode)) {
            daWindow.setContentPane(storyPane);
        } else {
            daWindow.setContentPane(charPan);
        }

        daWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        daWindow.addWindowListener(this);
        daWindow.addMouseMotionListener(this);
        daWindow.addMouseListener(this);
        daWindow.addMouseWheelListener(this);
        daWindow.requestFocusInWindow();
        daWindow.setFocusable(true);
        daWindow.addKeyListener(this);
        daWindow.pack();
        daWindow.setLocationRelativeTo(null); // Centers JFrame on screen //
        daWindow.setResizable(false);
        daWindow.setVisible(true);
        if (gameMode.equalsIgnoreCase(lanClient)) {
            client.sendData("player_QSLV");
        }
        superRepaintThread();
        packWindow();
    }

    public void stopMus() {
        bgMus.close();
    }

    /**
     * Responsible for latency in game menus(controller)
     */
    private void menuLatency() {
        /*
        new Thread() {
        
        public void run() {
        try {
        doneChilling = false;
        this.sleep(166);
        doneChilling = true;
        } catch (Exception e) {
        }
        }
        }.start();*/
    }

    public void packWindow() {
        daWindow.pack();
    }

    /**
     * Polls game pads for inputC data
     */
    private void pollController() {
        new Thread() {

            @SuppressWarnings({"SleepWhileHoldingLock", "static-access"})
            public void run() {
                try {
                    do {
                        gpController.poll();

                        //update bottons
                        buttonz = gpController.getButtons();
                        // get compass direction for the two analog sticks
                        compassDir2 = gpController.getXYStickDir();
                        if (compassDir2 == gpController.NORTH) {
                            up();
                        } else if (compassDir2 == gpController.SOUTH) {
                            down();
                        } else if (compassDir2 == gpController.WEST) {
                            left();
                        } else if (compassDir2 == gpController.EAST) {
                            right();
                        }


                        // get POV hat compass direction
                        compassDir = gpController.getHatDir();
                        {
                            if (compassDir == gpController.SOUTH) {
                                down();
                            }

                            if (compassDir == gpController.NORTH) {
                                up();
                            }

                            if (compassDir == gpController.WEST) {
                                left();
                            }

                            if (compassDir == GamePadController.EAST) {
                                right();
                            }
                        }

                        //button one
                        if (buttonz[0]) {
                            back();
                        }

                        if (buttonz[1]) {
                            trigger();
                        }

                        if (buttonz[2]) {
                            accept();
                        }

                        if (buttonz[3]) {
                            escape();
                        }

                        if (buttonz[5]) {
                            triggerFury('c');
                        }

                        // get compass direction for the two analog sticks
                        compassDir = gpController.getXYStickDir();

                        compassDir = gpController.getZRZStickDir();

                        // get button settings
                        buttons = gpController.getButtons();

                        this.sleep(33);
                    } while (true);
                } catch (Exception e) {
                }
            }
        }.start();
    }

    public CanvasStorySelect getStory() {
        return storyPane;
    }

    public CanvasCharSelect getCharSelect() {
        return charPan;
    }

    /**
     * Dude
     *
     * @return
     */
    public GamePadController getController() {
        return gpController;
    }

    public WindowMain getMain() {
        return LoginScreen.getLoginScreen().getMenu().getMain();
    }

    public void main(String[] args) {
        gameWindow = new WindowMain("Punk", singlePlayer);
    }

    public void getRidOfTitleBar() {
        daWindow.setUndecorated(true);
    }

    public void getBackTitleBar() {
        daWindow.setUndecorated(false);
    }

    @Override
    public void windowClosing(WindowEvent e) {
    }

    @Override
    public void windowOpened(WindowEvent e) {
        focus();
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
        focus();
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        focus();
    }

    @Override
    public void windowActivated(WindowEvent e) {
        focus();
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        focus();
    }

    public AttacksOpp1 getAttacksOpp() {
        return oppenentEntity;
    }

    public AttacksOpp2 getAttacksOpp2() {
        return oppenentEntity2;
    }

    public AttacksPlyr2 getAttacksChar2() {
        return characterEntity2;
    }

    public AttacksPlyr1 getAttacksChar() {
        return characterEntity;
    }

    public void newGame() {
        currentScreen = "newGame";
        //bgMusclose();
        oppenentEntity = new AttacksOpp1();
        characterEntity = new AttacksPlyr1();
        if (getGameMode().equals(singlePlayer2)) {
            oppenentEntity2 = new AttacksOpp2();
            characterEntity2 = new AttacksPlyr2();
        }
        newGame = new CanvasGameRender();
        stopMus();
        daWindow.setContentPane(newGame);
        newGame.startFight();
        reSize("game");

    }

    /**
     * Repack frame
     */
    public void reSize(String mode) {

        if (mode.equalsIgnoreCase("game")) {
            daWindow.resize(LoginScreen.getLoginScreen().getGameWidth(), LoginScreen.getLoginScreen().getGameHeight());
            //daWindow.setSize(LoginScreen.getLoginScreen().getGameWidth(), LoginScreen.getLoginScreen().getGameHeight());
        } else {
            daWindow.resize(852, 480);
            //daWindow.setSize(852, 480);
        }

        daWindow.pack();
        daWindow.setLocationRelativeTo(null);
    }

    public CanvasGameRender getGame() {
        return newGame;
    }

    public boolean isGameInstanciated() {
        return newGame != null;
    }

    public void storyGame() {
        //bgMusclose();
        oppenentEntity = new AttacksOpp1();
        if (getGameMode().equals(singlePlayer2)) {
            oppenentEntity2 = new AttacksOpp2();
            characterEntity2 = new AttacksPlyr2();
        }
        characterEntity = new AttacksPlyr1();
        newGame = new CanvasGameRender();
        currentScreen = "newGame";
        stopMus();
        daWindow.setContentPane(newGame);
        newGame.startFight();
        reSize("game");
    }

    /**
     * Goes back to main menu
     */
    public void backToMenuScreen() {
        WindowModeSelect.getMenu().showModes();
        bgMus.stop();
        bgMus.close();
        daWindow.dispose();
        if (newGame != null) {
            newGame.killGameInstance();
        }
        focus();
    }

    /**
     * Increments to next stage in story mode
     */
    public void nextStage() {
        //bgMusclose();
        oppenentEntity = new AttacksOpp1();
        characterEntity = new AttacksPlyr1();
        newGame = new CanvasGameRender();
        currentScreen = "newGame";
        stopMus();
        daWindow.setContentPane(newGame);
        newGame.startFight();
        reSize("game");

    }

    /**
     * Kinda obvious
     */
    public void stopMenuMusic() {
        //bgMusclose();
    }

    /**
     * Legacy, yet useful, code
     */
    private void selectArena() {
        String[] possibilities = {"Ibex Hill", "Chelston City - Harbour", "Royal Court", "Chelston City - Street"};
        Object s = JOptionPane.showInputDialog(
                null,
                "Select your arena:",
                "Arena Select",
                JOptionPane.OK_OPTION,
                null,
                possibilities,
                possibilities[1]);
        try {
            for (int nm = 0; nm < possibilities.length; nm++) {
                if (s.equals(possibilities[nm])) {
                    newGame.bgLocation = "images/bgBG" + (nm + 1) + ".png"; //Assign arena
                }
            }
        } catch (Exception e) {
        }
    }

    /**
     * Finds out if a match is running
     *
     * @return status of match
     */
    public boolean getIsGameRunning() {
        return currentScreen.equalsIgnoreCase("newGame");
    }

    /**
     * Sets the game to running
     */
    public void setGameRunning() {
        isGameRunning = true;
    }

    /**
     * Sets game to not running
     */
    public void setGameNotRunning() {
        isGameRunning = false;
    }

    /**
     * Enables player to select a stage
     */
    public void selectStage() {
        daWindow.setContentPane(stage);
        currentScreen = "stageSelectScreen";
        daWindow.pack();
        daWindow.setLocationRelativeTo(null);
        focus();
    }

    /**
     * Back to character select screen, when match is over
     */
    public void backToCharSelect() {
        isGameRunning = false;
        daWindow.setContentPane(charPan);
        currentScreen = "charSelectScreen";
        newGame.killGameInstance();
        charPan.animateCharSelect();
        charPan.refreshSelections();
        bgMus = new ThreadMP3(ThreadMP3.menuMus(), true);
        bgMus.play();
        reSize("menu");
        focus();
    }

    /**
     * Back to character select screen,, when match is cancelled
     */
    public void backToCharSelect2() {
        isGameRunning = false;
        getGame().getGameInstance().isPaused = false;
        getGame().getGameInstance().terminateThread();
        charPan.animateCharSelect();
        charPan.refreshSelections();
        daWindow.setContentPane(charPan);
        newGame.killGameInstance();
        charPan.systemNotice("Canceled Match");
        currentScreen = "charSelectScreen";
        bgMus = new ThreadMP3(ThreadMP3.menuMus(), true);
        bgMus.play();
        reSize("menu");
        focus();
        stage.defValue();
        if (getGameMode().equalsIgnoreCase(storyMode)) {
            charPan.backToMenu();

        }
    }

    /**
     * Closes hosting jenesisServer
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
     * Display message in sys overlay via game instance
     *
     * @param mess - the message to display
     */
    public void systemNotice(String mess) {
        newGame.systemNotice(mess);
    }

    /**
     * Display message in sys overlay via game instance
     *
     * @param mess - the message to display
     */
    public void systemNotice2(String mess) {
        newGame.systemNotice2(mess);
    }

    /**
     * Cancels match
     */
    public void cancelMatch() {
        int u = JOptionPane.showConfirmDialog(null, "Are you sure you wanna quit?", "Dude!?", JOptionPane.YES_NO_OPTION);
        if (u == JOptionPane.YES_OPTION) {
            if (getGameMode().equalsIgnoreCase(storyMode)) {
                getStory().getStoryInstance().firstRun = true;
            }
            //unpause if match was paused
            if (getGame().getGameInstance().isPaused) {
                pause();
            }
            stage.selectedStage = false;
            backToCharSelect2();
        }
    }

    public String getGameMode() {
        return gameMode;
    }

    public void setGameMode(String newMode) {
        gameMode = newMode;
    }

    /**
     * Vibrate
     */
    private void quickVibrate(float strength, int length) {
        final float power = strength;
        final int time = length;
        new Thread() {

            @SuppressWarnings("static-access")
            public void run() {
                try {
                    gpController.setRumbler(true, power);
                    this.sleep(time);
                    gpController.setRumbler(false, 0.0f);
                } catch (Exception e) {
                }
            }
        }.start();
    }

    /**
     * Contains universal up menu/game movements
     */
    private void up() {
        if (doneChilling) {
            if (currentScreen.equalsIgnoreCase("charSelectScreen")) {
                charPan.moveUp();
            } else if (currentScreen.equalsIgnoreCase("storySelectScreen")) {
                storyPane.moveUp();
            } else if (currentScreen.equalsIgnoreCase("stageSelectScreen")) {
                //client should be able to meddle in stage select
                if (getGameMode().equalsIgnoreCase(lanClient) == false) {
                    stage.moveUp();
                }
            } else if (getIsGameRunning()) {
                newGame.upItem();
            }
        }
        menuLatency();
    }

    /**
     * Contains universal down menu/game movements
     */
    private void down() {
        if (doneChilling) {
            if (currentScreen.equalsIgnoreCase("charSelectScreen")) {
                charPan.moveDown();
            } else if (currentScreen.equalsIgnoreCase("storySelectScreen")) {
                storyPane.moveDown();
            } else if (currentScreen.equalsIgnoreCase("stageSelectScreen")) {
                //client should not be able to meddle in stage select
                if (getGameMode().equalsIgnoreCase(lanClient) == false) {
                    stage.moveDown();
                }
            } else if (getIsGameRunning()) {
                newGame.downItem();
            }
        }
        menuLatency();
    }

    /**
     * Contains universal left menu/game movements
     */
    private void left() {
        if (doneChilling) {
            if (currentScreen.equalsIgnoreCase("charSelectScreen")) {
                charPan.moveLeft();
            } else if (currentScreen.equalsIgnoreCase("storySelectScreen")) {
                storyPane.moveLeft();
            } else if (currentScreen.equalsIgnoreCase("stageSelectScreen")) {
                //client should be able to meddle in stage select
                if (getGameMode().equalsIgnoreCase(lanClient) == false) {
                    stage.moveLeft();
                }
            } else if (getIsGameRunning()) {
                newGame.prevAnim();
            }
        }
        menuLatency();
    }

    /**
     * Contains universal right menu/game movements
     */
    private void right() {
        if (doneChilling) {
            if (currentScreen.equalsIgnoreCase("charSelectScreen")) {
                charPan.moveRight();
            } else if (currentScreen.equalsIgnoreCase("storySelectScreen")) {
                storyPane.moveRight();
            } else if (currentScreen.equalsIgnoreCase("stageSelectScreen")) {
                stage.moveRight();
            } else if (getIsGameRunning()) {
                newGame.nextAnim();
            }
        }
        menuLatency();
    }

    /**
     * Handles universal accept functions
     */
    private void accept() {
        if (doneChilling) {
            if (getIsGameRunning()) {
                if (ThreadGameInstance.isPaused == false) {
                    //in game, no story sequence
                    if (ThreadGameInstance.isGameOver == false && ThreadGameInstance.story == false) {
                        newGame.moveSelected();
                    } //in game, during story sequence
                    else if (ThreadGameInstance.isGameOver == false && ThreadGameInstance.story == true) {
                        getStory().getStoryInstance().skipDialogue();
                    } //story mode -- after text
                    else if (ThreadGameInstance.isGameOver == false && getStory().getStoryInstance().doneShowingText) {
                        getStory().getStoryInstance().skipDialogue();
                    } else {
                        //if person presses twice the stage increments twice
                        //this prevents that
                        //it only free to save when its game over
                        //one a save is used, it not free to save (i.e null)
                        if (freeToSave) {
                            freeToSave = false;
                            if (getGameMode().equalsIgnoreCase(singlePlayer)
                                    || getGameMode().equalsIgnoreCase(lanClient)
                                    || getGameMode().equalsIgnoreCase(lanHost)) {
                                getGame().getGameInstance().closingThread(1);
                            } else {
                                getGame().getGameInstance().closingThread(0);
                            }
                        }
                    }
                }
            } //cancel hosting
            else if (isWaiting && gameMode.equals(lanHost)) {
                closeTheServer();
            } else if (currentScreen.equalsIgnoreCase("charSelectScreen")) {
                //if both Characters are selected
                if (charPan.proceed1 && charPan.proceed2) {
                    if ((getGameMode().equalsIgnoreCase(singlePlayer) || getGameMode().equalsIgnoreCase(singlePlayer2) || getGameMode().equalsIgnoreCase(lanHost))) {
                        quickVibrate(0.6f, 1000);
                        charPan.beginGame();
                    }
                } else {
                    quickVibrate(0.4f, 1000);
                    charPan.selectChar();
                }
            } else if (currentScreen.equalsIgnoreCase("storySelectScreen")) {
                storyPane.selectStage();
                quickVibrate(0.4f, 1000);
            } else if (currentScreen.equalsIgnoreCase("stageSelectScreen")) {
                //client should be able to meddle in stage select
                if (getGameMode().equalsIgnoreCase(lanClient) == false) {
                    if (charPan.proceed1 && charPan.proceed2 && (getGameMode().equalsIgnoreCase(singlePlayer) || getGameMode().equalsIgnoreCase(singlePlayer2) || getGameMode().equalsIgnoreCase(lanHost))) {
                        quickVibrate(0.66f, 1000);
                        stage.SelectStageNow();
                    }
                }
            }
        }
        menuLatency();
    }

    /**
     * Handles universal UI escape
     */
    private void escape() {
        if (currentScreen.equalsIgnoreCase("charSelectScreen") || currentScreen.equalsIgnoreCase("storySelectScreen")) {
            if (getGameMode().equalsIgnoreCase(singlePlayer) || getGameMode().equalsIgnoreCase(singlePlayer2)) {
                {
                    charPan.refreshSelections();
                    charPan.backToMenu();
                }
            }
        }
        if (getIsGameRunning()) {
            pause();
        } else if (currentScreen.equalsIgnoreCase("storySelectScreen") && !getIsGameRunning()) {
            storyPane.backToMainMenu();
        }
        menuLatency();
    }

    /**
     * Global back
     */
    private void back() {
        if (doneChilling) {
            if (currentScreen.equalsIgnoreCase("charSelectScreen")) {
                if (getGameMode().equalsIgnoreCase(singlePlayer)) {
                    charPan.refreshSelections();
                }
            } else if (getIsGameRunning()) {
                newGame.unQueMove();
            }
        }
        menuLatency();
    }

    public void triggerFury(char who) {
        newGame.triggerFury(who);
    }

    /**
     * Universal trigger
     */
    private void trigger() {
        if (doneChilling) {
            if (getIsGameRunning()) {
                newGame.attack();
            }
        }
        menuLatency();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {
            up();
        }

        if (keyCode == KeyEvent.VK_M) {
            if (isWaiting && gameMode.equals(lanHost)) {
                world = new OverWorld();
            }
        }

        if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) {
            down();
        }

        if (keyCode == KeyEvent.VK_X) {
            packWindow();
        }

        if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) {
            left();
        }

        if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
            right();
        }

        if (keyCode == KeyEvent.VK_BACK_SPACE) {
            back();
        }

        if (keyCode == KeyEvent.VK_ENTER) {
            accept();
        }

        if (keyCode == KeyEvent.VK_ESCAPE) {
            escape();
        }

        if (keyCode == KeyEvent.VK_F4) {
            LoginScreen.getLoginScreen().getMenu().exit();
        }

        if (keyCode == KeyEvent.VK_F5) {
            if (getIsGameRunning()) {
                cancelMatch();
            }
        }
        if (keyCode == KeyEvent.VK_F12) {
            if (currentScreen.equalsIgnoreCase("charSelectScreen")) {
                charPan.getScreeny();
            } else if (currentScreen.equalsIgnoreCase("storySelectScreen")) {
                storyPane.takeScreenshotX();
            } else if (currentScreen.equalsIgnoreCase("stageSelectScreen")) {
                stage.screenShot();
            } else if (getIsGameRunning()) {
                newGame.takeScreenShot();
            }
        } else if (getIsGameRunning()) {
            //use T for testing stuff
            if (keyCode == KeyEvent.VK_T) {
                //newGame.systemNotice("Testing");
            }

            if (keyCode == KeyEvent.VK_L) {
                triggerFury('c');
            }

            if (keyCode == KeyEvent.VK_SPACE) {
                trigger();
            }
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent mwe) {
        //when fighting
        if (isGameRunning) {
            if (getGame().getGameInstance().isGameOver == false && ThreadGameInstance.story == false) {
                int count = mwe.getWheelRotation();

                //down - positive values
                if (count >= 0) {
                    newGame.prevAnim();
                }
                //up -negative values
                if (count < 0) {
                    newGame.nextAnim();
                }
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent m) {
        if (currentScreen.equalsIgnoreCase("charSelectScreen") && withinCharPanel) {
            //if both Characters are selected
            if (charPan.proceed1 && charPan.proceed2) {
                if ((getGameMode().equalsIgnoreCase(singlePlayer) || getGameMode().equalsIgnoreCase(singlePlayer2) || getGameMode().equalsIgnoreCase(lanHost))) {
                    quickVibrate(0.6f, 1000);
                    charPan.beginGame();
                }
            } else {
                charPan.selectChar();
            }
        }
        if (currentScreen.equalsIgnoreCase("storySelectScreen") && withinMenuPanel) {
            storyPane.selectStage();
        }
        if (currentScreen.equalsIgnoreCase("stageSelectScreen") && withinCharPanel) {
            stage.SelectStageNow();
        } else if (getIsGameRunning()) {
            //when fighting
            if (ThreadGameInstance.isGameOver == false && ThreadGameInstance.story == false) {
                if (m.getButton() == MouseEvent.BUTTON1) {
                    //selecting move
                    if (m.getX() > (29 + leftyXOffset) && m.getX() < (220 + leftyXOffset) && (m.getY() > 358)) {
                        newGame.moveSelected();
                    }

                    //prev
                    if (m.getX() < (29 + leftyXOffset)) {
                        newGame.prevAnim();
                    }

                    //next
                    if (m.getX() > (220 + leftyXOffset) && m.getX() < (305 + leftyXOffset)) {
                        newGame.nextAnim();
                    } else if ((m.getX() > 25 && m.getX() < 46) && (m.getY() > 190 && m.getY() < 270)) {
                        //activate fury via click
                        triggerFury('c');
                    }
                }

                //middle mouse
                if (m.getButton() == MouseEvent.BUTTON2) {
                    triggerFury('c');
                }

                //right click
                if (m.getButton() == MouseEvent.BUTTON3) {
                    newGame.unQueMove();
                }
            }
        }

        focus();
    }

    @Override
    public void mouseEntered(MouseEvent m) {
    }

    @Override
    public void mouseDragged(MouseEvent m) {
    }

    /**
     * To make sure the caption is animated once,
     * this method checks if the selected caption has changed
     *
     * @param x
     * @param y
     */
    public void animateCap(int x, int y) {
        int tmpx = x;
        int tmpy = y;

        if (tmpx == storedX && tmpy == storedY) //same vals, do nothing
        {
        } else {
            storedX = tmpx;
            storedY = tmpy;
            charPan.animateCaption();
        }
    }

    /**
     * To make sure the caption is animated once,
     * this method checks if the selected caption has changed
     *
     * @param x
     * @param y
     */
    public void animateCap2(int x, int y) {
        int tmpx = x;
        int tmpy = y;

        if (tmpx == storedX && tmpy == storedY) //same vals, do nothing
        {
        } else {
            storedX = tmpx;
            storedY = tmpy;
            charPan.animateCaption();
        }
    }

    /**
     * To make sure the caption is animated once,
     * this method checks if the selected caption has changed
     *
     * @param x
     * @param y
     */
    public void animateCap2x(int x, int y) {
        int tmpx = x;
        int tmpy = y;

        if (tmpx == storedX && tmpy == storedY) //same vals, do nothing
        {
        } else {
            storedX = tmpx;
            storedY = tmpy;
            storyPane.animateCaption();
        }
    }

    public void superRepaint() {
        daWindow.repaint();
        //daWindow.getGlassPane().repaint();
    }

    private void superRepaintThread() {
        if (isNotRepainting) {
            isNotRepainting = false;
            new Thread() {

                final int sleepTime = 1000 / tTime;

                @SuppressWarnings({"static-access", "SleepWhileHoldingLock", "CallToThreadDumpStack"})
                @Override
                public void run() {
                    do {
                        try {
                            superRepaint();
                            this.setName("Super awesome repaint thread");
                            this.sleep(sleepTime);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    } while (1 != 0);
                }
            }.start();
        }
    }

    /**
     * Attempts to map selected character using mouse
     */
    private void sortCharPosLoc(int xPos, int yPos) {
        topY = charPan.getStartY() + mouseYoffset;
        topX = charPan.getStartX();
        columns = charPan.getNumberOfCharColumns();
        vspacer = charPan.getCharHSpacer();
        hspacer = charPan.getCharVSpacer();
        rows = charPan.getCharRows();

        if (xPos > topX && xPos < (topX + (hspacer * columns)) && (yPos > topY) && (yPos < topY + (vspacer * (rows + 1)))) {
            int vIndex = (yPos - topY) / vspacer;
            int hIndex = (((xPos - topX) / hspacer) + 1);
            charPan.setHindex(hIndex);
            charPan.setVindex(vIndex);
            animateCap(hIndex, vIndex);
            withinCharPanel = true;
        } else {
            charPan.setHindex(99);
            charPan.setVindex(99);
            withinCharPanel = false;
        }
    }

    /**
     * Attemptsto map selected character using mouse
     */
    private void sortStagePosLoc(int xPos, int yPos) {
        topY = storyPane.getStartY() + mouseYoffset;
        topX = storyPane.getStartX();
        columns = storyPane.getNumberOfCharColumns();
        vspacer = storyPane.getCharHSpacer();
        hspacer = storyPane.getCharVSpacer();
        rows = storyPane.getCharRows();

        if (xPos > topX && xPos < (topX + (hspacer * columns)) && (yPos > topY) && (yPos < topY + (vspacer * (rows + 1)))) {
            int vIndex = (yPos - topY) / vspacer;
            int hIndex = (((xPos - topX) / hspacer) + 1);
            storyPane.setHindex(hIndex);
            storyPane.setVindex(vIndex);
            animateCap2x(hIndex, vIndex);
            withinMenuPanel = true;
            ;
        } else {
            storyPane.setHindex(99);
            storyPane.setVindex(99);
            withinMenuPanel = false;
        }
    }

    /**
     * Attempts to map selected character using mouse
     */
    private void sortCharPosLoc2(int xPos, int yPos) {
        topY = stage.getStartY() + mouseYoffset;
        topX = stage.getStartX();
        columns = stage.getNumberOfCharColumns();
        vspacer = stage.getCharHSpacer();
        hspacer = stage.getCharVSpacer();
        rows = stage.getCharRows();

        if (xPos > topX && xPos < (topX + (hspacer * columns)) && (yPos > topY) && (yPos < topY + (vspacer * (rows + 1)))) {
            int vIndex = (yPos - topY) / vspacer;
            int hIndex = (((xPos - topX) / hspacer) + 1);
            //System.out.println("within char pan dog");
            //System.out.println("Row "+vIndex);
            //System.out.println("Column "+hIndex);
            stage.setHindex(hIndex);
            stage.setVindex(vIndex);
            //charPan.setItem();
            animateCap2(hIndex, vIndex);
            withinCharPanel = true;
        } else {
            //System.out.println("Outa char pan dog");
            charPan.setHindex(99);
            charPan.setVindex(99);
            withinCharPanel = false;
        }
    }

    @Override
    public void mouseMoved(MouseEvent m) {
        if (currentScreen.equalsIgnoreCase("charSelectScreen")) {
            sortCharPosLoc(m.getX(), m.getY());
        }

        if (currentScreen.equalsIgnoreCase("storySelectScreen")) {
            sortStagePosLoc(m.getX(), m.getY());
        }

        if (currentScreen.equalsIgnoreCase("stageSelectScreen")) {
            //if you're a client stay still
            if (getGameMode().equalsIgnoreCase(lanClient) == false) {
                sortCharPosLoc2(m.getX(), m.getY());
            }
        } else if (getIsGameRunning()) {
            //when fighting
            if (ThreadGameInstance.isGameOver == false && ThreadGameInstance.story == false && DrawGame.dnladng) {
                //browse moves
                if (m.getX() > (29 + leftyXOffset) && m.getX() < (436 + leftyXOffset)) {
                    if (m.getY() > (int) (373 * newGame.getscaleY()) + mouseYoffset && m.getY() < (int) (390 * newGame.getscaleY()) + mouseYoffset) {
                        item = 0;
                    }

                    if (m.getY() > (int) (390 * newGame.getscaleY()) + mouseYoffset && m.getY() < (int) (407 * newGame.getscaleY()) + mouseYoffset) {
                        item = 1;
                    }

                    if (m.getY() > (int) (407 * newGame.getscaleY()) + mouseYoffset && m.getY() < (int) (420 * newGame.getscaleY()) + mouseYoffset) {
                        item = 2;
                    }

                    if (m.getY() > (int) (420 * newGame.getscaleY()) + mouseYoffset && m.getY() < (int) (435 * newGame.getscaleY()) + mouseYoffset) {
                        item = 3;
                    }

                    newGame.thisItem(item);
                }
            }
        }
        //System.out.println("Mouse X:"+m.getX());
        //System.out.println("Mouse y:"+m.getY());
    }

    @Override
    public void mouseExited(MouseEvent m) {
    }

    @Override
    public void mousePressed(MouseEvent m) {
    }

    @Override
    public void mouseReleased(MouseEvent m) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    public String getIP() {
        return gameIp;
    }

    public void setIP(String ip) {
        gameIp = ip;
    }

    private void pause() {
        if (getGameMode().equalsIgnoreCase(singlePlayer) || getGameMode().equalsIgnoreCase(storyMode) || getGameMode().equalsIgnoreCase(singlePlayer2)) {
            if (ThreadGameInstance.isPaused == false) {
                getGame().getGameInstance().pauseGame();
                newGame.pauseThreads();
                if (ThreadGameInstance.story == true) {
                    getStory().getStoryInstance().pauseDialogue();
                }
            } else {
                newGame.start();
                newGame.resumeThreads();
                if (ThreadGameInstance.story == true) {
                    getStory().getStoryInstance().resumeDialogue();
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
        daWindow.requestFocusInWindow();
        daWindow.setFocusable(true);
        //System.out.println("Window focused");
    }

    /**
     * When a player is found
     */
    public void playerFound() {
        //System.out.println("Player found");

        int ansx = JOptionPane.showConfirmDialog(null, userName + " , someone wants to fight you!!!!\nWanna waste em!?", "Heads Up", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        switch (ansx) {
            case JOptionPane.YES_OPTION: {
                sendToClient("as1wds2_" + LoginScreen.getLoginScreen().timePref);
                isWaiting = false;
                drawWait.stopRepaint();
                daWindow.setContentPane(charPan);
                currentScreen = "charSelectScreen";//charPan.animCloud();
                charPan.animateCharSelect();
                daWindow.pack();
                daWindow.setLocationRelativeTo(null); // Centers JFrame on screen //
                break;
            }

            //case JOptionPane.NO_OPTION:
            //{
            //name_of_player does not want to fight
            //close client
            //sendToClient("getLost_cde343dw3");
            //break;
            //}
        }
    }

    public jenesisServer getServer() {
        return server;
    }

    public jenesisClient getClient() {
        return client;
    }

    public void sendToClient(String thisTxt) {
        server.sendData(thisTxt);
    }

    public void sendToServer(String thisTxt) {
        client.sendData(thisTxt);
    }

    public class jenesisServer implements Runnable {

        private DataOutputStream output;
        private DataInputStream input;
        private ServerSocket server;
        private Socket connection;
        private Thread t;
        private String line;
        private boolean serverIsRunning;

        /**
         * Basic constructor
         */
        public jenesisServer() {
            InitServer();
            t = new Thread(this);
        }

        /**
         * Start this jenesisServer
         */
        public void start() {
            t.start();
        }

        /**
         * Initialize jenesisServer
         */
        private void InitServer() {
            try {
                serverIsRunning = true;
                server = new ServerSocket(PORT, 1);
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
                playerFound();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }

        /**
         * Close the jenesisServer
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
                    ReadMassage();
                    t.sleep(serverLatency);
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
        public void ReadMassage() {
            try {

                line = input.readUTF();
                if (line.endsWith("quit")) {
                    closeServer();
                } else if (line.endsWith("player_QSLV")) {
                    playerFound();
                } else if (line.endsWith("attack")) {

                    //1111 attack
                    //01010101 attack
                    int back = line.length();
                    int y1 = Integer.parseInt("" + line.substring(back - 15, back - 13) + "");
                    int y2 = Integer.parseInt("" + line.substring(back - 13, back - 11) + "");
                    int y3 = Integer.parseInt("" + line.substring(back - 11, back - 9) + "");
                    int y4 = Integer.parseInt("" + line.substring(back - 9, back - 7) + "");

                    if (getGameMode().equalsIgnoreCase(lanHost)) {
                        playerHost2 = new ExecuterMovesOppOnline(y1, y2, y3, y4, 'n');
                    }

                    System.out.println(line.charAt(back - 11) + " " + line.charAt(back - 10) + " " + line.charAt(back - 9) + " " + line.charAt(back - 8));
                    //SendMassage(client,client.socket().getInetAddress()+" ATTACKED YOU!!!");
                    System.out.println("\n");
                } else if (line.endsWith("pauseGame")) {
                    //pauseMethod();
                } else if (line.endsWith(" xc_97_mb")) {
                    systemNotice(line.replaceAll(" xc_97_mb", ""));
                } //Characters
                else if (line.endsWith("_jkxc")) {
                    System.out.println("Server mess: " + line);
                    if (line.contains("selSub")) {
                        charPan.selSubiya('o');
                    }
                    if (line.contains("selRai")) {
                        charPan.selRaila('o');
                    }
                    if (line.contains("selAlx")) {
                        charPan.selAisha('o');
                    }
                    if (line.contains("selLyn")) {
                        charPan.selLynx('o');
                    }
                    if (line.contains("selRav")) {
                        charPan.selRav('o');
                    }
                    if (line.contains("selAde")) {
                        charPan.selAde('o');
                    }
                    if (line.contains("selJon")) {
                        charPan.selJon('o');
                    }
                    if (line.contains("selAdam")) {
                        charPan.selAdam('o');
                    }
                    if (line.contains("selNOVAAdam")) {
                        charPan.selNOVAAdam('o');
                    }
                    if (line.contains("selAzaria")) {
                        charPan.selAza('o');
                    }
                    if (line.contains("selSorr")) {
                        charPan.selSorr('o');
                    }
                    if (line.contains("selThi")) {
                        charPan.selThing('o');
                    }
                } else if (line.equalsIgnoreCase("lastMess")) {
                    sendData(last);
                } //special moves
                else if (line.contains("limt_Break_Oxodia_Ownz")) {
                    triggerFury('o');
                } //clashes
                else if (line.contains("oppClsh")) {
                    System.out.println("THis is it " + line.substring(7));
                    int val = Integer.parseInt(line.substring(7));
                    ThreadClashSystem.setOpp(val);
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
                last = mess;
                output.writeUTF(mess);
                output.flush();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }


    public class jenesisClient implements Runnable {

        private DataOutputStream outputC;
        private DataInputStream inputC;
        private Socket clientx;
        private Thread t;
        private String line, IPaddress;
        private boolean clientIsRunning;

        /**
         * Constructor, expects name/ip address
         */
        public jenesisClient(String ip) {
            clientIsRunning = true;
            IPaddress = ip;
            t = new Thread(this);
            t.start();
        }

        @Override
        public void run() {

            ServerName = Server.getText();
            System.out.println(ServerName);
            UserName = User.getText();
            connectToServer(IPaddress);
            while (clientIsRunning) {
                getStreams();
                ReadMassage();
                try {
                    t.sleep(serverLatency);
                } catch (InterruptedException ie) {
                    JOptionPane.showMessageDialog(null, ie.getMessage(), "Network ERROR", JOptionPane.ERROR_MESSAGE);
                    backToCharSelect();
                }
            }

        }

        /**
         * Connect to a given jenesisServer
         *
         * @param hostname jenesisServer name
         */
        private void connectToServer(String hostname) {
            try {
                clientIsRunning = true;
                clientx = new Socket(InetAddress.getByName(hostname), PORT);
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
                outputC = new DataOutputStream(clientx.getOutputStream());
                outputC.flush();
                inputC = new DataInputStream(clientx.getInputStream());
            } catch (Exception e) {
                System.err.println(e.getMessage());
                e.printStackTrace();
            }
        }

        /**
         * Read incoming data stream
         */
        public void ReadMassage() {
            try {

                line = inputC.readUTF();

                if (line.endsWith("attack")) {
                    int back = line.length();
                    int y1 = Integer.parseInt("" + line.substring(back - 15, back - 13) + "");
                    int y2 = Integer.parseInt("" + line.substring(back - 13, back - 11) + "");
                    int y3 = Integer.parseInt("" + line.substring(back - 11, back - 9) + "");
                    int y4 = Integer.parseInt("" + line.substring(back - 9, back - 7) + "");

                    if (getGameMode().equalsIgnoreCase(lanHost)) {
                        playerClient2 = new ExecuterMovesCharOnline(y1, y2, y3, y4, 'n');
                    }

                    if (getGameMode().equalsIgnoreCase(lanClient)) {
                        playerClient1 = new ExecuterMovesOppOnline(y1, y2, y3, y4, 'n');
                    }

                    System.out.println(line.charAt(back - 11) + " " + line.charAt(back - 10) + " " + line.charAt(back - 9) + " " + line.charAt(back - 8));
                    //SendMassage(client,client.socket().getInetAddress()+" ATTACKED YOU!!!");
                    System.out.println("\n");
                } else if (line.endsWith("pauseGame")) {
                    //pauseMethod();
                } else if (line.endsWith(" xc_97_mb")) {
                    systemNotice(line.replaceAll(" xc_97_mb", ""));
                } //Characters
                else if (line.endsWith("_jkxc")) {
                    if (line.contains("selSub")) {
                        charPan.selSubiya('o');
                    }
                    if (line.contains("selRai")) {
                        charPan.selRaila('o');
                    }
                    if (line.contains("selAlx")) {
                        charPan.selAisha('o');
                    }
                    if (line.contains("selLyn")) {
                        charPan.selLynx('o');
                    }
                    if (line.contains("selRav")) {
                        charPan.selRav('o');
                    }
                    if (line.contains("selAde")) {
                        charPan.selAde('o');
                    }
                    if (line.contains("selJon")) {
                        charPan.selJon('o');
                    }
                    if (line.contains("selAdam")) {
                        charPan.selAdam('o');
                    }
                    if (line.contains("selNOVAAdam")) {
                        charPan.selNOVAAdam('o');
                    }
                    if (line.contains("selAzaria")) {
                        charPan.selAza('o');
                    }
                    if (line.contains("selSorr")) {
                        charPan.selSorr('o');
                    }
                    if (line.contains("selThi")) {
                        charPan.selThing('o');
                    }
                } else if (line.endsWith("watchStageSel_xcbD")) {
                    selectStage();
                } else if (line.startsWith("as1wds2_")) {
                    hostTime = Integer.parseInt(line.substring(8));
                    System.out.println("aquired time is " + hostTime);
                } //stages
                else if (line.endsWith("_vgdt")) {
                    if (line.contains("stage1")) {
                        stage.stage1();
                    }
                    if (line.contains("stage2")) {
                        stage.stage2();
                    }
                    if (line.contains("stage3")) {
                        stage.stage3();
                    }
                    if (line.contains("stage4")) {
                        stage.stage4();
                    }
                    if (line.contains("stage5")) {
                        stage.stage5();
                    }
                    if (line.contains("stage6")) {
                        stage.stage6();
                    }
                    if (line.contains("stage7")) {
                        stage.stage7();
                    }
                    if (line.contains("stage100")) {
                        stage.stage100();
                    }
                    if (line.contains("stage8")) {
                        stage.stage8();
                    }
                    if (line.contains("stage9")) {
                        stage.stage9();
                    }
                    if (line.contains("stage10")) {
                        stage.stage10();
                    }
                    if (line.contains("stage11")) {
                        stage.stage11();
                    }
                    if (line.contains("stage12")) {
                        stage.stage12();
                    }
                    if (line.contains("stage13")) {
                        stage.stage13();
                    }
                    if (line.contains("stage14")) {
                        stage.stage14();
                    }
                } //start game
                else if (line.endsWith("gameStart7%^&")) {
                    stage.start();
                } else if (line.contains("loadingGVSHA")) {
                    stage.nowLoading();
                } //special moves
                else if (line.contains("limt_Break_Oxodia_Ownz")) {
                    triggerFury('o');
                } //clashes
                else if (line.contains("oppClsh")) {
                    System.out.println("THis is it " + line.substring(7));
                    int val = Integer.parseInt(line.substring(7));
                    ThreadClashSystem.setOpp(val);
                } //rejected
                else if (line.contains("getLost")) ;
                {
                    //JOptionPane.showMessageDialog(null, "HARSH!, The dude doesnt want to fight you -_-"+messageSent+" "+getGameMode(),"Ouchies",JOptionPane.ERROR_MESSAGE);
                    //sendToServer("quit");
                    //closeTheClient();
                    //backToMenuScreen();
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
                last = mess;
                outputC.writeUTF(mess);
                outputC.flush();
            } catch (Exception e) {
                System.err.println(e.getMessage());
                e.printStackTrace();
            }
        }

        /**
         * Terminate client
         */
        public void closeClient() {
            try {
                clientIsRunning = false;
                outputC.close();
                inputC.close();
                clientx.close();
            } catch (Exception e) {
                System.err.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }
//AAAA
}
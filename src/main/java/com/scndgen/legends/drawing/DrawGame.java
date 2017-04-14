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
package com.scndgen.legends.drawing;

import com.scndgen.legends.Achievements;
import com.scndgen.legends.LoginScreen;
import com.scndgen.legends.engine.JenesisCanvas;
import com.scndgen.legends.engine.JenesisLanguage;
import com.scndgen.legends.engine.JenesisGlassPane;
import com.scndgen.legends.engine.JenesisImage;
import com.scndgen.legends.menus.CanvasStageSelect;
import com.scndgen.legends.menus.CanvasGameRender;
import com.scndgen.legends.threads.*;
import com.scndgen.legends.windows.WindowMain;
import com.scndgen.legends.windows.WindowOptions;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.VolatileImage;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class draws and manipulates all sprites, images and effects used in the game
 *
 * @Author: Ifunga Ndana
 * @Class: DrawGame
 */
public abstract class DrawGame extends JenesisCanvas {
    //mp3 wav

    private final static float inc = 0.05f;
    private final static float upShear = 0.005f;
    private final static float downShear = 0.005f;
    //threads only run once
    public static boolean threadsNotRunningYet = true, playATBFile = false;
    public static StringBuilder StatusText = new StringBuilder();
    public static int startDrawing = 0, menuBarY;
    public static boolean limitRunning = true, animCharFree = true;
    public static float angRot = 20;
    public static int charXcord = 10, charYcord = 10, oppYcord = 10, statIndex = 0;
    public static int amb1x = 0, amb1y = 0, amb2x = 0, amb2y = 0;
    public static int activeStage, numOfComicPics = 9;
    public static int fgx, fgy, fgxInc, fgyInc, animLoops, delay;
    public static String animDirection = "vert", verticalMove = "no";
    public static int oppXcord = 10;
    public static int playerDamageXLoc, opponentDamageXLoc, numOfAttacks = 0;
    public static VolatileImage charSpec, opp1;
    public static String fgLocation, tmpStr1;
    //public static String bgLocation="images/bgBG"+Math.round((Math.random()*2)+1)+".png"; //
    public static String bgLocation;
    public static String scenePic = "images/bgBG2.png";
    public static String attackPicSrc = "images/trans.png";
    public static String[] storyPicArrStr, charNames = LoginScreen.charNames;
    public static String attackPicOppSrc = "images/trans.png";
    public static int ambSpeed1, ambSpeed2, paneCord;
    public static StringBuilder battleInf = new StringBuilder("");
    public static int count = 0, fpsInt = 0, fpsIntStat;
    public static ThreadAnim1 upDown;
    public static ThreadAnim2 upDown2;
    public static ThreadAnim3 upDown3;
    public static String[] physical, celestia, item, special, current;
    public static String[] currentColumn;
    public static int currentCols = 0;
    public static boolean safeToSelect = true;
    public static String animLayer = "";
    public static boolean clasherOn = false, dnladng;
    private static String random_name;
    private static StringBuilder userIDBuff;
    private static Graphics2D g2d;
    private static int shakingChar = 0033, loop1 = 3, loop2 = 4;
    private static int x2 = 560, comX = 380, comY = 100;
    private static int xLocal = 470;
    private static int y2 = 435;
    private static int statIndexOpp, statIndexChar, statsPosYChar, statsPosYOpp, shakeyOffsetChar = 1, shakeyOffsetOpp = 1, basicX = 0, basicY = 0;
    private static boolean shaky1 = true;
    private static ThreadClashingOpponent oppAttack = null;
    private static int animTime = 400, itemX = 0, itemY = 0;
    private static boolean runNew = true, effectChar = false;
    private static int bgX = 0;
    private static int v1, v2, v3, numberOfStoryPix, lbx2 = 500;
    private static int lby2 = 420;
    private static String attackType = "normal", attackTYpeOpp = "normal", statusChar = "", statusOpp = "";
    private static int one, two, three, four, oneO, twoO, threeO, fourO;
    private static int comboPicArrayPosOpp = 8, oppAssSpriteStatus, charAssSpriteStatus, charMeleeSpriteStatus = 9, oppMeleeSpriteStatus = 9, charCelestiaSpriteStatus = 11, oppCelestiaSpriteStatus = 11;
    private static Image oppBar, quePic1, furyBar, counterPane, quePic2, num0, num1, num2, num3, num4, num5, num6, num7, num8, num9, numNull, bgPic, damLayer, hpHolder, hud1, hud2, win, lose, status, menuHold, fury, fury1, fury2, phys, cel, itm, curr, numInfinite, figGuiSrc10, figGuiSrc20, figGuiSrc30, figGuiSrc40, figGuiSrc1, figGuiSrc2, figGuiSrc3, figGuiSrc4, time0, time1, time2, time3, time4, time5, time6, time7, time8, time9;
    //UI images
    private static VolatileImage opp11, assSprite, assOppSprite;
    private static VolatileImage charPort, storyPic;
    private static VolatileImage charMeleeSprite, charCelestiaSprite, tmpIm, oppMeleeSprite, oppCelestiaSprite;
    //numbers are arrays
    private static Image[] charSprites, oppSprites;
    private static VolatileImage[] attackAnim2, attackAnim1;
    private static VolatileImage[] storyPicArr, stats;
    private static float statusOpChar, statusOpOpp;
    private static Image[] comboPicArray, comicPicArray, times, statsPicChar = new Image[5], statsPicOpp = new Image[5];
    //bad guys
    private static int charOp = 10, comicPicArrayPos = 0;
    private static int itemindex = 0, furyBarY = 0;
    @SuppressWarnings("StaticNonFinalUsedInInitialization")
    private static int[] fontSizes = {LoginScreen.bigTxtSize, LoginScreen.normalTxtSize, LoginScreen.normalTxtSize, LoginScreen.normalTxtSize};
    private static int attackInt = 0;
    private static String attackStr;
    private static Color CurrentColor = (Color.RED);
    private static float opacityTxt = 10, opacityPic = 0.0f;
    private static VolatileImage volatileImg;
    private static boolean lagFactor = true;
    private static float opponentDamageOpacity, playerDamageOpacity, comicBookTextOpacity, furyComboOpacity;
    private static float currentXShear = 0, currentYShear = 0;
    private static boolean isFree = true, isFree2 = true;
    private static int comicY, opponentDamageYLoc, playerDamageYLoc, yTEST = 25, yTESTinit = 25;
    private static float opac = 1.0f;
    private static float damOpInc;
    private static char dude;
    private static String sysNot = "";
    private static float sysNotOpac = 0, sysNotOpacInc = (float) 0.1;
    private static String ach1 = "", ach2 = "", ach3 = "", ach4 = "";
    private static boolean nextEnabled = true, backEnabled = true;
    private static int move = 0;
    private static WindowMain.jenesisServer server;
    private static WindowMain.jenesisClient client;
    private static Image[] moveCat, numberPix;
    private static VolatileImage[] charCaption;
    private static boolean specialEffect;
    private static ThreadClashSystem clasher;
    private static VolatileImage flashy;
    private static String[] letters;
    public boolean loadedUpdaters;
    RenderingHints renderHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //anti aliasing, kill jaggies
    private JenesisLanguage lang;
    private ThreadMP3 sound, fightMus, furySound, damageSound, hurtChar, hurtOpp, attackChar, attackOpp;
    private GraphicsEnvironment ge;
    private BufferedImage bim;
    private int InfoBarYPose, spacer = 27, randSoundIntChar, randSoundIntOpp, randSoundIntOppHurt, randSoundIntCharHurt, YOffset = 15;
    private boolean imagesNumChached = false, imagesCharChached = false;
    private VolatileImage infoPic, stat1, stat2, stat3, stat4;
    private VolatileImage ambient1, ambient2, foreGround;
    private GradientPaint queBar = new GradientPaint(0, 0, Color.YELLOW, 255, 10, Color.RED, false);
    private GradientPaint gradient1 = new GradientPaint(xLocal, 10, Color.YELLOW, 255, 10, Color.RED, true);
    private GradientPaint gradient2 = new GradientPaint(xLocal, 10, Color.white, 255, 10, Color.blue, true);
    //good guys
    private GradientPaint gradient3 = new GradientPaint(0, 0, Color.YELLOW, 100, 100, Color.RED, true);
    private int valCode, x = 2;
    private Font bigFont, normalFont;
    private Font notSelected = LoginScreen.getLoginScreen().getMyFont(12);
    private Font statusFont = LoginScreen.getLoginScreen().getMyFont(28);
    private int oppBarYOffset, leftyXOffset, y = 0;
    private Graphics g;
    private GraphicsConfiguration gc;
    private JenesisImage pix;
    private float angleRaw, charPointInc;
    private int result;

    public DrawGame() {
        lang = LoginScreen.getLoginScreen().getLangInst();
        pix = new JenesisImage();
        over1 = new JenesisGlassPane();
        newInstance();
    }

    /**
     * Set stat index
     *
     * @param dex
     */
    public static void setStatIndex(int dex) {
        statIndex = dex;
    }

    public static void setStatusPic(char who, String stat, Color c) {

        if (who == 'c' || who == 'a') {
            statusOpChar = 1.0f;
            statsPosYChar = 0;
            statIndexChar = statIndex;
        }
        if (who == 'o' || who == 'b') {
            statusOpOpp = 1.0f;
            statsPosYOpp = 0;
            statIndexOpp = statIndex;
        }
    }

    public static void setNumOfBoards(int i) {
        /*
        numberOfStoryPix = i;
        storyPicArr = new VolatileImage[numberOfStoryPix];
        storyPicArrStr = new String[numberOfStoryPix];*/
    }

    public static void setPicAt(int i, String s) {
        storyPicArrStr[i] = s;
    }

    /**
     * Generates unique ID for screens
     *
     * @return unique ID
     */
    public static String generateUID() {
        random_name = "scndgen-legends_";
        userIDBuff = new StringBuilder(random_name);

        letters = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};

        userIDBuff.append("").append(Math.round(Math.random() * 100)).append("_");
        v1 = Integer.parseInt("" + Math.round((Math.random() * 24) + 1));
        v2 = Integer.parseInt("" + Math.round((Math.random() * 24) + 1));
        v3 = Integer.parseInt("" + Math.round((Math.random() * 24) + 1));
        userIDBuff.append(letters[v1]);
        userIDBuff.append(letters[v2]);
        userIDBuff.append(letters[v3]);
        userIDBuff.append("_").append(Math.round(Math.random() * 10000)).append("");
        random_name = userIDBuff.toString();

        return random_name;
    }

    /**
     * Draws Achievements
     */
    @SuppressWarnings("SleepWhileHoldingLock")
    public static void drawAchievements() {
        try {
            int howMany = Achievements.getAcievementsTriggered();
            LoginScreen.getLoginScreen().getMenu().getMain().getGame().getGameInstance().timeOut(howMany);
            do {
                for (int u = 0; u < howMany; u++) {
                    String[] thisOne = Achievements.getName(u);
                    ach1 = thisOne[0]; //name
                    ach2 = thisOne[1]; //desc
                    ach3 = thisOne[2]; //class
                    ach4 = thisOne[3]; //points

                    System.out.println("Triggered " + ach1 + "\n" + ach2 + "\n" + ach3 + "\n" + ach4);

                    try {
                        Thread.sleep(2300);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(DrawGame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } while (LoginScreen.getLoginScreen().getMenu().getMain().getGame().getGameInstance().instance);
        } catch (Exception e) {
            //nothin
        }
    }

    /**
     * Navigate down in the menu
     */
    public static void downItem() {
        if (itemindex < 3) {
            itemindex = itemindex + 1;
        } else {
            itemindex = 0;
        }

        //default size
        for (int u = 0; u < fontSizes.length; u++) {
            fontSizes[u] = LoginScreen.normalTxtSize;
        }

        //increase font size
        fontSizes[itemindex] = LoginScreen.bigTxtSize;
    }

    /**
     * Get the Characters
     */
    private static void getCharMoveset() {
        LoginScreen.getLoginScreen().getMenu().getMain().getAttacksChar().getDude().setCharMoveset();
    }

    /**
     * Set stats
     *
     * @param physicalS
     * @param celestiaS
     * @param itemS
     */
    public static void setStats(String[] physicalS, String[] celestiaS, String[] itemS) {
        physical = physicalS;
        celestia = celestiaS;
        item = itemS;
    }

    /**
     * Resolves column names
     */
    private static void resolveText() {
        if (currentCols == 0) {
            currentColumn = physical;
        } else if (currentCols == 1) {
            currentColumn = celestia;
        } else if (currentCols == 2) {
            currentColumn = item;
        }
    }

    /**
     * Generates strings used to execute moves
     *
     * @param input move
     */
    public static String genStr(int input) {
        String thisTxt = "";

        if (input < 10) {
            thisTxt = "0" + move;
        } else {
            thisTxt = "" + move;
        }

        return thisTxt;
    }

    /**
     * Get the move selected by the player
     */
    private static String getSelMove(int move) {
        String txt = LoginScreen.getLoginScreen().getMenu().getMain().getAttacksChar().getDude().getMoveQued(move);

        return txt;
    }

    /**
     * Get hurtChar type
     *
     * @return hurtChar type
     */
    public static String getAttackType(char who) {
        String result = "";
        if (who == 'c') {
            result = attackType;
        } else if (who == 'o') {
            result = attackTYpeOpp;
        }

        return result;
    }

    /**
     * set hurtChar type, normal or fury
     */
    public static void setAttackType(String type, char who) {
        if (who == 'c') {
            attackType = type;
        }

        if (who == 'o') {
            attackTYpeOpp = type;
            System.out.println("Opponent is Pissed");
        }
    }

    /**
     * Show win pic
     */
    public static void winPic() {
        status = win;
    }

    /**
     * Show loose pic
     */
    public static void losePic() {
        status = lose;
    }

    public static ConvolveOp getGaussianBlurFilter(int radius, boolean horizontal) {
        if (radius < 1) {
            throw new IllegalArgumentException("Radius must be >= 1");
        }

        int size = radius * 2 + 1;
        float[] data = new float[size];
        float sigma = radius / 3.0f;
        float twoSigmaSquare = 2.0f * sigma * sigma;
        float sigmaRoot = (float) Math.sqrt(twoSigmaSquare * Math.PI);
        float total = 0.0f;
        for (int i = -radius; i <= radius; i++) {
            float distance = i * i;
            int index = i + radius;
            data[index] = (float) Math.exp(-distance / twoSigmaSquare) / sigmaRoot;
            total += data[index];
        }

        for (int i = 0; i < data.length; i++) {
            data[i] /= total;
        }
        Kernel kernel = null;
        if (horizontal) {
            kernel = new Kernel(size, 1, data);
        } else {
            kernel = new Kernel(1, size, data);
        }
        return new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
    }

    /**
     * Change the notification pic
     *
     * @param index - the index
     */
    public static void setNotifiationPic(int index) {
    }

    /**
     * Change storyboard pic
     *
     * @param here - scene
     */
    public static void changeStoryBoard(int here2) {
        try {
            storyPic = storyPicArr[here2 + 1];
            opacityPic = 0.0f;
        } catch (Exception e) {
            storyPic = storyPicArr[1];
        }
    }

    /**
     * Playing around with animate sprites
     *
     * @param whichOne move
     * @param loop     loop animation or not
     */
    public static void specialEffect(int whichOne, boolean loop) {
        final int thisOne = whichOne;
        final boolean isLoop = loop;

        new Thread() {

            @SuppressWarnings({"static-access", "static-access", "SleepWhileHoldingLock"})
            @Override
            public void run() {
                if (animCharFree) {
                    try {
                        animCharFree = false;


                        //anim1
                        if (thisOne == 1) {
                            for (int u = 0; u < attackAnim1.length; u++) {
                                charSpec = attackAnim1[u];
                                if (isLoop) {
                                    this.sleep(animTime / (attackAnim1.length * 2));
                                } else {
                                    this.sleep(animTime / attackAnim1.length);
                                }
                            }

                            if (isLoop) {
                                for (int u = attackAnim1.length - 1; u > -1; u--) {
                                    charSpec = attackAnim1[u];
                                    this.sleep(animTime / (attackAnim1.length * 2));
                                }
                            }
                        }

                        //anim1
                        if (thisOne == 2) {
                            for (int u = 0; u < attackAnim2.length; u++) {
                                charSpec = attackAnim2[u];
                                if (isLoop) {
                                    this.sleep(animTime / (attackAnim2.length * 2));
                                } else {
                                    this.sleep(animTime / attackAnim2.length);
                                }
                            }

                            if (isLoop) {
                                for (int u = attackAnim2.length - 1; u > -1; u--) {
                                    charSpec = attackAnim2[u];
                                    this.sleep(animTime / (attackAnim2.length * 2));
                                }
                            }
                        }


                        charSpec = opp11;
                        animCharFree = true;
                    } catch (InterruptedException ex) {
                        Logger.getLogger(DrawGame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }.start();
    }

    public static void Clash(int dude, char homie) {
        if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanClient)) {
            LoginScreen.getLoginScreen().getMenu().getMain().sendToServer("clashing^T&T^&T&^");
        } else if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanHost)) {
            LoginScreen.getLoginScreen().getMenu().getMain().sendToClient("clashing^T&T^&T&^");
        }

        if (CanvasGameRender.getBreak() == 1000 && safeToSelect && clasherOn == false) {
            clasher = new ThreadClashSystem(dude, homie);
            clasherOn = true;

            //AI if story or arcade
            if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.singlePlayer) || LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.storyMode)) {
                oppAttack = new ThreadClashingOpponent();
                System.out.println("Clash ai");
            }
        }
    }

    public static void opponetClashing() {
        ThreadClashSystem.oppClashing();
    }

    public static void sendToClient(String message) {
        LoginScreen.getLoginScreen().getMenu().getMain().sendToClient(message);
        //private static LoginScreen.getLoginScreen().getMenu().getMain().jenesisClient client;
    }

    public static void sendToServer(String message) {
        LoginScreen.getLoginScreen().getMenu().getMain().sendToServer(message);
    }

    public static void comicText() {
        if (LoginScreen.getLoginScreen().comicPicOcc > 0) {
            int well = Math.round((float) (Math.random() * LoginScreen.getLoginScreen().comicPicOcc));

            if (well == 1) {
                setRandomPic();
            }
        }
    }

    private static void setRandomPic() {
        comicPicArrayPos = Math.round((float) (numOfComicPics * Math.random()));
        comicBookTextOpacity = 1.0f;
        comicY = 0;
    }

    public static void resetComicTxt() {
        comicPicArrayPos = 0;
    }

    public static String getFavChar(int here) {
        return charNames[here];
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(getGameWidth(), getGameHeight()); //16:9
    }

    @Override
    public Dimension getSize() {
        return new Dimension(getGameWidth(), getGameHeight()); //16:9
    }

    /**
     * Get screen height
     *
     * @return height
     */
    public int getGameHeight() {
        return LoginScreen.getLoginScreen().getGameHeight();
    }

    /**
     * Get screen width
     *
     * @return width
     */
    public int getGameWidth() {
        return LoginScreen.getLoginScreen().getGameWidth();
    }

    @Override
    public void paintComponent(Graphics g) {
        /* Fixed performance issues, got rid off geometry and replaced with static VolatileImage s -----facepalm*/
        createBackBuffer();
        //Check if Image is valid
        valCode = volatileImg.validate(gc);
        //if not create new vi, only affects windows apparently
        if (valCode == VolatileImage.IMAGE_INCOMPATIBLE) {
            createBackBuffer();
        }

        if (imagesCharChached != true) {
            g2d.fillRect(0, 0, LoginScreen.getLoginScreen().getdefSpriteWidth(), LoginScreen.getLoginScreen().getdefSpriteHeight());
        } else if (ThreadGameInstance.story) {
            g2d.setColor(Color.BLACK);
            g2d.fillRect(0, 0, LoginScreen.getLoginScreen().getdefSpriteWidth(), LoginScreen.getLoginScreen().getdefSpriteHeight());

            if (opacityPic < 0.98f) {
                opacityPic = opacityPic + 0.02f;
            }
            g2d.setComposite(makeComposite(opacityPic));
            g2d.drawImage(storyPic, 0, 0, this);
            g2d.setComposite(makeComposite(10 * 0.1f));

            g2d.setComposite(makeComposite(0.5f));
            g2d.fillRoundRect(0, 424, LoginScreen.getLoginScreen().getdefSpriteWidth(), 48, 48, 48); //mid minus half the font size (430-6)
            g2d.setComposite(makeComposite(10 * 0.1f));

            g2d.setColor(Color.WHITE);
            g2d.setFont(normalFont);
            g2d.drawString(lang.getLine(146) + " >>", (852 - g2d.getFontMetrics().stringWidth(lang.getLine(146) + " >>")), 462);


            if (opacityTxt < 0.98f) {
                opacityTxt = opacityTxt + 0.02f;
            }
            g2d.setComposite(makeComposite(opacityTxt));
            g2d.drawImage(charPort, ((852 - g2d.getFontMetrics().stringWidth(battleInf.toString())) / 2) - 50, 424, this);
            g2d.drawString(battleInf.toString(), ((852 - g2d.getFontMetrics().stringWidth(battleInf.toString())) / 2), 450);
            g2d.setComposite(makeComposite(10 * 0.1f));

        } else if (ThreadGameInstance.isGameOver == false && ThreadGameInstance.story == false) {
            g2d.drawImage(bgPic, 0, 0, this);
            g2d.setFont(notSelected);
            if (LoginScreen.getLoginScreen().getMenu().getMain().getGame().getCharLife() >= 0) {
                if (animLayer.equalsIgnoreCase("both")) {
                    g2d.drawImage(ambient2, amb2x, amb2y, this);
                } else if (animLayer.equalsIgnoreCase("back")) {
                    g2d.drawImage(ambient1, amb1x, amb1y, this);
                    g2d.drawImage(ambient2, amb2x, amb2y, this);
                }

                if (LoginScreen.getLoginScreen().getMenu().getMain().getAttacksChar().isOverlayDisabled()) {
                    g2d.drawImage(charSprites[charMeleeSpriteStatus], charXcord + shakeyOffsetChar, charYcord - shakeyOffsetChar, this);
                }

                g2d.drawImage(oppSprites[oppMeleeSpriteStatus], LoginScreen.getGameWidth(), (int) (oppYcord), (int) (oppXcord), LoginScreen.getLoginScreen().getGameHeight(), (int) oppYcord, (int) (oppXcord), LoginScreen.getLoginScreen().getGameWidth(), LoginScreen.getLoginScreen().getGameHeight(), this);


                if (!LoginScreen.getLoginScreen().getMenu().getMain().getAttacksChar().isOverlayDisabled()) {
                    g2d.drawImage(charSprites[charMeleeSpriteStatus], charXcord + shakeyOffsetChar, charYcord - shakeyOffsetChar, this);
                }

                if (animLayer.equalsIgnoreCase("both")) {
                    g2d.drawImage(ambient1, amb1x, amb1y, this);
                } else if (animLayer.equalsIgnoreCase("forg")) {
                    g2d.drawImage(ambient1, amb1x, amb1y, this);
                    g2d.drawImage(ambient2, amb2x, amb2y, this);
                }

                /** character sprite on below, opponent on top
                 * "Java Tip 32: You'll flip over Java images -- literally! - JavaWorld"
                 * http://www.javaworld.com/javaworld/javatips/jw-javatip32.html?page=2
                 */
                if ((LoginScreen.getLoginScreen().getMenu().getMain().getGame().getCharLife() / LoginScreen.getLoginScreen().getMenu().getMain().getGame().getCharMaxLife()) < 0.66f) {
                    damOpInc = 6.66f - ((LoginScreen.getLoginScreen().getMenu().getMain().getGame().getCharLife() / LoginScreen.getLoginScreen().getMenu().getMain().getGame().getCharMaxLife()) * 10);
                    //BACK to transparency
                }

                if (specialEffect) {
                    g2d.drawImage(foreGround, fgx, fgy, this);
                }

                g2d.setComposite(makeComposite((float) damOpInc * 0.1f));
                g2d.drawImage(damLayer, 0, 0, this);
                g2d.setComposite(makeComposite(1.0f));

                g2d.drawImage(menuHold, leftyXOffset, menuBarY, this);


                for (int xB = 0; xB < 4; xB++) {
                    g2d.drawImage(quePic1, (xB * 70 + 5 + leftyXOffset), (int) (LoginScreen.getLoginScreen().getGameYScale() * 440), this);
                }

                if (LoginScreen.getLoginScreen().getMenu().getMain().getGame().comboCounter >= 1) {
                    for (int xV = 0; xV < LoginScreen.getLoginScreen().getMenu().getMain().getGame().comboCounter; xV++) {
                        g2d.drawImage(quePic2, (xV * 70 + 5 + leftyXOffset), (int) (LoginScreen.getLoginScreen().getGameYScale() * 440), this);
                    }
                }


                if (comicBookTextOpacity >= 0.0f) {
                    comicBookTextOpacity = comicBookTextOpacity - 0.0125f;
                }
                g2d.setComposite(makeComposite(comicBookTextOpacity));
                g2d.drawImage(comicPicArray[comicPicArrayPos], 170 + basicX, 112 + basicY + comicY, this);
                g2d.setComposite(makeComposite(1.0f));
                comicY = comicY + 3;

                if (opacityTxt < 0.98f) {
                    opacityTxt = opacityTxt + 0.02f;
                }
                g2d.setComposite(makeComposite(opacityTxt));
                g2d.setColor(Color.WHITE);
                g2d.drawString(battleInf.toString(), 32 + leftyXOffset, 470);
                g2d.setComposite(makeComposite(1.0f));

                //-----------draws battle info messages--------------


                //stats

                if (statusOpChar > 0.02f) {
                    statusOpChar = statusOpChar - 0.02f;
                }
                g2d.setComposite(makeComposite(statusOpChar));
                g2d.drawImage(statsPicChar[statIndexChar], 150 + basicX + shakeyOffsetChar, 100 + basicY - shakeyOffsetChar + statsPosYChar, this);
                g2d.setComposite(makeComposite(1.0f));
                statsPosYChar = statsPosYChar + 1;


                if (statusOpOpp > 0.02f) {
                    statusOpOpp = statusOpOpp - 0.02f;
                }
                g2d.setComposite(makeComposite(statusOpOpp));
                g2d.drawImage(statsPicOpp[statIndexOpp], 602 + basicX + shakeyOffsetOpp, 100 + basicY - shakeyOffsetOpp + statsPosYOpp, this);
                g2d.setComposite(makeComposite(1.0f));
                statsPosYOpp = statsPosYOpp + 1;

                //---opponrnt activity bar + text

                g2d.drawImage(hpHolder, (45 + 62 + x2) + shakeyOffsetOpp, (y + 4 + y2 - oppBarYOffset) - shakeyOffsetOpp, this);
                g2d.setColor(Color.WHITE);
                if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equals(WindowMain.singlePlayer2)) {
                    g2d.drawString("HP: " + Math.round((LoginScreen.getLoginScreen().getMenu().getMain().getGame().getOppLife2() + LoginScreen.getLoginScreen().getMenu().getMain().getGame().getOppLife())) + " : " + ((LoginScreen.getLoginScreen().getMenu().getMain().getGame().perCent2a + LoginScreen.getLoginScreen().getMenu().getMain().getGame().perCent2) / 2) + "%", (int) ((55 + 64 + x2)), (int) ((18 + y2 - oppBarYOffset)));
                } else {
                    g2d.drawString("HP: " + Math.round(LoginScreen.getLoginScreen().getMenu().getMain().getGame().getOppLife()) + " : " + LoginScreen.getLoginScreen().getMenu().getMain().getGame().perCent2 + "%", (55 + 64 + x2) + shakeyOffsetOpp, (18 + y2 - oppBarYOffset) - shakeyOffsetOpp);
                }

                g2d.setColor(Color.BLACK);
                g2d.drawImage(oppBar, (x2 - 20) + shakeyOffsetOpp, (y2 + 18 - oppBarYOffset) - shakeyOffsetOpp, this);
                g2d.setPaint(gradient1);
                g2d.fillRoundRect((x2 - 17) + shakeyOffsetOpp, (y2 + 22 - oppBarYOffset) - shakeyOffsetOpp, LoginScreen.getLoginScreen().getMenu().getMain().getGame().getGameInstance().getRecoveryUnitsOpp(), 6, 6, 6);
                if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equals(WindowMain.singlePlayer2)) {
                    g2d.drawImage(hpHolder, 45 + 62 + x2, (y + 4 + y2 - 40), this);
                    g2d.setColor(Color.WHITE);
                    g2d.drawString("HP: " + Math.round((LoginScreen.getLoginScreen().getMenu().getMain().getGame().getOppLife2() + LoginScreen.getLoginScreen().getMenu().getMain().getGame().getOppLife())) + " : " + ((LoginScreen.getLoginScreen().getMenu().getMain().getGame().perCent2a + LoginScreen.getLoginScreen().getMenu().getMain().getGame().perCent2) / 2) + "%", 55 + 64 + x2, 18 + y2 - 40);
                    g2d.setColor(Color.BLACK);
                    g2d.drawImage(oppBar, x2 - 20, y2 + 18 - 40, this);
                    g2d.setPaint(gradient1);
                    g2d.fillRoundRect(x2 - 17, y2 + 22 - 40, LoginScreen.getLoginScreen().getMenu().getMain().getGame().getGameInstance().getRecoveryUnitsOpp2(), 6, 6, 6);

                    g2d.drawImage(hpHolder, 45 + 62 + x2, (y + 4 + y2 - 80), this);
                    g2d.setColor(Color.WHITE);
                    g2d.drawString("HP: " + ((Math.round(LoginScreen.getLoginScreen().getMenu().getMain().getGame().getCharLife3()) + Math.round(LoginScreen.getLoginScreen().getMenu().getMain().getGame().getCharLife()))) + " : " + ((LoginScreen.getLoginScreen().getMenu().getMain().getGame().perCent3a + LoginScreen.getLoginScreen().getMenu().getMain().getGame().perCent) / 2) + "%", 55 + 64 + x2, 18 + y2 - 80);
                    g2d.setColor(Color.BLACK);
                    g2d.drawImage(oppBar, x2 - 20, y2 + 18 - 80, this);
                    g2d.setPaint(gradient2);
                    g2d.fillRoundRect(x2 - 17, y2 + 22 - 80, LoginScreen.getLoginScreen().getMenu().getMain().getGame().getGameInstance().getRecoveryUnitsChar2(), 6, 6, 6);
                }

                //------------player 1 HUD---------------------//
                g2d.drawImage(hpHolder, (lbx2 - 438) + shakeyOffsetChar, (lby2 - 410) - shakeyOffsetChar, this); // HOLDS hp
                //outline
                g2d.drawImage(hud1, (lbx2 - 498) + shakeyOffsetChar, (lby2 - 417) - shakeyOffsetChar, this);
                //inner
                //g2d.setColor(Color.RED);
                g2d.setPaint(gradient3);
                g2d.fillArc(lbx2 - 493 + shakeyOffsetChar, lby2 - 412 - shakeyOffsetChar, 90, 90, 0, phyAngle());
                //inner loop
                g2d.setColor(Color.BLACK);
                g2d.drawImage(hud2, lbx2 - 488 + shakeyOffsetChar, lby2 - 407 - shakeyOffsetChar, this);

                {
                    g2d.setColor(Color.WHITE);

                    if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.singlePlayer2)) {
                        g2d.drawString("HP: " + ((Math.round(LoginScreen.getLoginScreen().getMenu().getMain().getGame().getCharLife3()) + Math.round(LoginScreen.getLoginScreen().getMenu().getMain().getGame().getCharLife()))) + " : " + ((LoginScreen.getLoginScreen().getMenu().getMain().getGame().perCent3a + LoginScreen.getLoginScreen().getMenu().getMain().getGame().perCent) / 2) + "%", (lbx2 - 416) + shakeyOffsetChar, (lby2 - 398) - shakeyOffsetChar);
                    } else {
                        g2d.drawString("HP: " + Math.round(LoginScreen.getLoginScreen().getMenu().getMain().getGame().getCharLife()) + " : " + LoginScreen.getLoginScreen().getMenu().getMain().getGame().perCent + "%", (lbx2 - 416) + shakeyOffsetChar, (lby2 - 398) - shakeyOffsetChar);
                    }
                    g2d.setComposite(makeComposite(10 * 0.1f)); //op back to normal for other drawings
                }
            }

            g2d.drawImage(counterPane, paneCord, 0, this);

            if (LoginScreen.getLoginScreen().getMenu().getMain().getGame().getGameInstance().time > 180) {
                g2d.drawImage(numberPix[11], (int) (386), 0, this);
            } else {

                g2d.drawImage(times[LoginScreen.getLoginScreen().getMenu().getMain().getGame().getGameInstance().time1], (int) (356), 0, this);
                g2d.drawImage(times[LoginScreen.getLoginScreen().getMenu().getMain().getGame().getGameInstance().time2], (int) ((356) + 40), 0, this);
                g2d.drawImage(times[LoginScreen.getLoginScreen().getMenu().getMain().getGame().getGameInstance().time3], (int) ((356) + 80), 0, this);
            }


            {
                if (opac < 0.95f) {
                    opac = opac + 0.05f;
                }
                g2d.setComposite(makeComposite(opac));
                g2d.setColor(CurrentColor);
                g2d.drawImage(curr, itemX + leftyXOffset, itemY, this);

                if (fontSizes[0] == LoginScreen.bigTxtSize) {
                    g2d.setFont(bigFont);
                } else {
                    g2d.setFont(normalFont);
                }
                g2d.drawString(currentColumn[0], (yTEST + leftyXOffset), ((366) + fontSizes[0]));


                if (fontSizes[1] == LoginScreen.bigTxtSize) {
                    g2d.setFont(bigFont);
                } else {
                    g2d.setFont(normalFont);
                }
                g2d.drawString(currentColumn[1], (yTEST + leftyXOffset), ((366) + fontSizes[0] + 2 + fontSizes[1]));

                if (fontSizes[2] == LoginScreen.bigTxtSize) {
                    g2d.setFont(bigFont);
                } else {
                    g2d.setFont(normalFont);
                }
                g2d.drawString(currentColumn[2], (yTEST + leftyXOffset), ((366) + fontSizes[0] + 2 + fontSizes[1] + 2 + fontSizes[2]));

                if (fontSizes[3] == LoginScreen.bigTxtSize) {
                    g2d.setFont(bigFont);
                } else {
                    g2d.setFont(normalFont);
                }
                g2d.drawString(currentColumn[3], (yTEST + leftyXOffset), ((366) + fontSizes[0] + 2 + fontSizes[1] + 2 + fontSizes[2] + 2 + fontSizes[3]));

                g2d.setComposite(makeComposite(1.0f));
            }

            //limit break stuff
            g2d.drawImage(fury, 20 + ((shakeyOffsetOpp + shakeyOffsetChar) / 2), 190 - ((shakeyOffsetOpp + shakeyOffsetChar) / 2), this);
            g2d.drawImage(furyBar, 10 + ((shakeyOffsetOpp + shakeyOffsetChar) / 2), furyBarY - ((shakeyOffsetOpp + shakeyOffsetChar) / 2), this);
            g2d.setColor(Color.RED);
            g2d.fillRoundRect(12 + ((shakeyOffsetOpp + shakeyOffsetChar) / 2), 132 - ((shakeyOffsetOpp + shakeyOffsetChar) / 2), 12, CanvasGameRender.getBreak() / 5, 12, 12);
            //COMBO

            if (furyComboOpacity > 0.01f) {
                furyComboOpacity = furyComboOpacity - 0.01f;
            }
            g2d.setComposite(makeComposite(furyComboOpacity));
            g2d.drawImage(comboPicArray[comboPicArrayPosOpp], comX + ((shakeyOffsetOpp + shakeyOffsetChar) / 2), comY - ((shakeyOffsetOpp + shakeyOffsetChar) / 2), this);
            g2d.setComposite(makeComposite(1.0f));
            g2d.setFont(notSelected);


            //clash area
            if (clasherOn) {
                g2d.setColor(Color.BLACK);
                g2d.fillRoundRect(221, 395, 410, 20, 10, 10);
                g2d.drawImage(flashy, (int) (ThreadClashSystem.plyClashPerc * 4) + 226, 385, this);
                g2d.setColor(Color.RED);
                g2d.fillRect((int) (626 - (ThreadClashSystem.oppClashPerc * 4)), 400, (int) ThreadClashSystem.oppClashPerc * 4, 10);
                g2d.setColor(Color.YELLOW);
                g2d.fillRect(226, 400, (int) (ThreadClashSystem.plyClashPerc * 4), 10);
            }

            //damage digits
            {
                g2d.setComposite(makeComposite(opponentDamageOpacity));
                //opp damage pix
                g2d.drawImage(figGuiSrc1, playerDamageXLoc + shakeyOffsetChar, opponentDamageYLoc - shakeyOffsetChar, this);
                g2d.drawImage(figGuiSrc2, playerDamageXLoc + (spacer * 1) + shakeyOffsetChar, opponentDamageYLoc - shakeyOffsetChar, this);
                g2d.drawImage(figGuiSrc3, playerDamageXLoc + (spacer * 2) + shakeyOffsetChar, opponentDamageYLoc - shakeyOffsetChar, this);
                g2d.drawImage(figGuiSrc4, playerDamageXLoc + (spacer * 3) + shakeyOffsetChar, opponentDamageYLoc - shakeyOffsetChar, this);
                g2d.setComposite(makeComposite(1.0f));
                if (opponentDamageOpacity >= 0.0f) {
                    opponentDamageOpacity = opponentDamageOpacity - 0.0125f;
                }
                if (opponentDamageOpacity < 0.8f) {
                    opponentDamageYLoc = opponentDamageYLoc - 3;
                }


                g2d.setComposite(makeComposite(playerDamageOpacity));
                //char damage pix
                g2d.drawImage(figGuiSrc10, opponentDamageXLoc + shakeyOffsetOpp, playerDamageYLoc - shakeyOffsetOpp, this);
                g2d.drawImage(figGuiSrc20, opponentDamageXLoc + (spacer * 1) + shakeyOffsetOpp, playerDamageYLoc - shakeyOffsetOpp, this);
                g2d.drawImage(figGuiSrc30, opponentDamageXLoc + (spacer * 2) + shakeyOffsetOpp, playerDamageYLoc - shakeyOffsetOpp, this);
                g2d.drawImage(figGuiSrc40, opponentDamageXLoc + (spacer * 3) + shakeyOffsetOpp, playerDamageYLoc - shakeyOffsetOpp, this);
                g2d.setComposite(makeComposite(1.0f));
                if (playerDamageOpacity >= 0.0f) {
                    playerDamageOpacity = playerDamageOpacity - 0.0125f;
                }
                if (playerDamageOpacity < 0.8f) {
                    playerDamageYLoc = playerDamageYLoc - 3;
                }
            }
            checkFuryStatus();
        }

        //-----------ENDS ATTACKS QUEING UP--------------

        //when paused
        if (ThreadGameInstance.isPaused == true) {
            g2d.setColor(Color.BLACK);
            g2d.setComposite(makeComposite(5 * 0.1f));//initial val between 1 and 10
            g2d.fillRect(0, 0, getGameWidth(), getGameHeight());
            g2d.setComposite(makeComposite(10 * 0.1f));
            g2d.setColor(Color.WHITE);
            g2d.drawString(lang.getLine(148), 400, 240);
            g2d.drawString(lang.getLine(149), 400, 260);
            g2d.drawString(lang.getLine(150), 400, 280);
        }

        //when gameover
        if (ThreadGameInstance.isGameOver == true) {
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, getGameWidth(), getGameHeight());
            g2d.setColor(Color.BLACK);
            g2d.setComposite(makeComposite(8 * 0.1f));//initial val between 1 and 10
            g2d.fillRect(0, 210, getGameWidth(), 121);
            g2d.setComposite(makeComposite(10 * 0.1f));
            g2d.drawImage(status, 0, 210, this);
            g2d.setColor(Color.WHITE);
            g2d.setFont(notSelected);
            g2d.drawString(ach1, 400, 240); //+14
            g2d.drawString(ach2, 400, 254);
            g2d.drawString(ach3, 400, 268);
            g2d.drawString(ach4, 400, 282);
            g2d.drawString("<< " + lang.getLine(146) + " >>", 400, 296);
        }

        //global overlay
        over1.overlay(g2d, this);

        g.drawImage(volatileImg, 0, 0, this);
    }

    public void shakeCharLB() {
        try {
            LoginScreen.getLoginScreen().getMenu().getMain().getController().setRumbler(true, 0.4f);
        } catch (Exception e) {
        }

        for (int h = 0; h < loop1; h++) // shakes opponents LifeBar in a cool way as well as Black n White flashy Anime effect
        {
            for (int i = 0; i < loop2; i++) {
                shakeyOffsetChar = shakeyOffsetChar + 1;
                LoginScreen.getLoginScreen().getMenu().getMain().getGame().slowDown(shakingChar);
            }

            for (int i = 0; i < loop2; i++) {
                shakeyOffsetChar = shakeyOffsetChar - 1;
                LoginScreen.getLoginScreen().getMenu().getMain().getGame().slowDown(shakingChar);
            }
        }
        try {
            LoginScreen.getLoginScreen().getMenu().getMain().getController().setRumbler(false, 0.0f);
        } catch (Exception e) {
        }
    }

    public void shakeOppCharLB() {
        for (int h = 0; h < loop1; h++) // shakes opponents LifeBar in a cool way as well as Black n White flashy Anime effect
        {
            for (int i = 0; i < loop2; i++) {
                shakeyOffsetOpp = shakeyOffsetOpp + 1;
                LoginScreen.getLoginScreen().getMenu().getMain().getGame().slowDown(shakingChar);
            }

            for (int i = 0; i < loop2; i++) {
                shakeyOffsetOpp = shakeyOffsetOpp - 1;
                LoginScreen.getLoginScreen().getMenu().getMain().getGame().slowDown(shakingChar);
            }
        }
    }

    private void checkFuryStatus() {
        if (CanvasGameRender.getBreak() == 1000) {
            fury = fury1;
        } else {
            fury = fury2;
        }

        if (LoginScreen.getLoginScreen().getMenu().getMain().getGame().getGameInstance().isGameOver == true) {
            //slow mo!!!!
        }
    }

    public void setP2Damage(int oneA, int twoA, int threeA, int fourA) {
        comicText();

        nrmlDamageSound();
        attackSoundOpp();
        hurtSoundOpp();


        playerDamageYLoc = 160 + (int) (Math.random() * 100);
        playerDamageXLoc = 575 + (int) (Math.random() * 100);
        playerDamageOpacity = 1.0f;

        oneO = oneA;
        twoO = twoA;
        threeO = threeA;
        fourO = fourA;

        figGuiSrc10 = numberPix[oneO];
        figGuiSrc20 = numberPix[twoO];
        figGuiSrc30 = numberPix[threeO];
        figGuiSrc40 = numberPix[fourO];
    }

    public void setP1Damage(int oneA, int twoA, int threeA, int fourA) {
        comicText();

        nrmlDamageSound();
        attackSoundChar();
        hurtSoundChar();

        opponentDamageYLoc = 160 + (int) (Math.random() * 100);
        opponentDamageXLoc = 150 + (int) (Math.random() * 100);
        opponentDamageOpacity = 1.0f;

        one = oneA;
        two = twoA;
        three = threeA;
        four = fourA;

        figGuiSrc1 = numberPix[one];
        figGuiSrc2 = numberPix[two];
        figGuiSrc3 = numberPix[three];
        figGuiSrc4 = numberPix[four];
    }

    /**
     * assigns pic to array index
     */
    public void setSprites(char who, int oneA, int Magic) {
        if (who == 'c') {
            charMeleeSpriteStatus = oneA;
            charCelestiaSpriteStatus = Magic;
        }

        if (who == 'o') {
            oppMeleeSpriteStatus = oneA;
            oppCelestiaSpriteStatus = Magic;
        }

        if (who == 'a') {
            charAssSpriteStatus = oneA;
        }

        if (who == 'b') {
            oppAssSpriteStatus = oneA;
        }
    }

    /**
     * Caches number
     */
    private void cacheNumPix() {
        if (LoginScreen.getLoginScreen().isLefty().equalsIgnoreCase("no")) {
            leftyXOffset = 548;
        } else {
            leftyXOffset = 0;
        }
        if (imagesNumChached == false) {
            counterPane = pix.loadImageFromToolkitNoScale("images/countPane.png");
            if (activeStage != 100) {
                foreGround = pix.loadBImage2(fgLocation, 852, 480, this);
            } else {
                foreGround = pix.loadBImage2(fgLocation, 960, 480, this);
            }
            num0 = pix.loadImageFromToolkitNoScale("images/fig/0.png");
            num1 = pix.loadImageFromToolkitNoScale("images/fig/1.png");
            num2 = pix.loadImageFromToolkitNoScale("images/fig/2.png");
            num3 = pix.loadImageFromToolkitNoScale("images/fig/3.png");
            num4 = pix.loadImageFromToolkitNoScale("images/fig/4.png");
            num5 = pix.loadImageFromToolkitNoScale("images/fig/5.png");
            num6 = pix.loadImageFromToolkitNoScale("images/fig/6.png");
            num7 = pix.loadImageFromToolkitNoScale("images/fig/7.png");
            num8 = pix.loadImageFromToolkitNoScale("images/fig/8.png");
            num9 = pix.loadImageFromToolkitNoScale("images/fig/9.png");
            numInfinite = pix.loadImageFromToolkitNoScale("images/fig/infinite.png");
            numNull = pix.loadImageFromToolkitNoScale("images/trans.png");
            //flashy=pix.loadBImage2("images/flash.gif",40,40);
            flashy = null;
            numberPix = new Image[]{num0, num1, num2, num3, num4, num5, num6, num7, num8, num9, numNull, numInfinite};

            statsPicChar[0] = pix.loadImageFromToolkitNoScale("images/trans.png");
            statsPicChar[1] = pix.loadImageFromToolkitNoScale("images/stats/stat1.png");
            statsPicChar[2] = pix.loadImageFromToolkitNoScale("images/stats/stat2.png");
            statsPicChar[3] = pix.loadImageFromToolkitNoScale("images/stats/stat3.png");
            statsPicChar[4] = pix.loadImageFromToolkitNoScale("images/stats/stat4.png");

            statsPicOpp[0] = pix.loadImageFromToolkitNoScale("images/trans.png");
            statsPicOpp[1] = pix.loadImageFromToolkitNoScale("images/stats/stat1.png");
            statsPicOpp[2] = pix.loadImageFromToolkitNoScale("images/stats/stat2.png");
            statsPicOpp[3] = pix.loadImageFromToolkitNoScale("images/stats/stat3.png");
            statsPicOpp[4] = pix.loadImageFromToolkitNoScale("images/stats/stat4.png");

            System.out.println("loaded all pix");
            imagesNumChached = true;
            //ensures method is only run once
        }
    }

    /**
     * EPIC!!!! Loads har sprites
     */
    private void loadCharSpritesHigh() {
        if (imagesCharChached == false) {
            try {
                LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().getPayers().getDudeChar().loadMeHigh(this);
                LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().getPayers().getDudeOpp().loadMeHigh(this);

                charSprites = new VolatileImage[12];
                for (int i = 0; i < LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().getPayers().getDudeChar().getNumberOfSprites(); i++) {
                    charSprites[i] = LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().getPayers().getDudeChar().getMeHigh(i);
                }

                oppSprites = new VolatileImage[12];
                for (int i = 0; i < LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().getPayers().getDudeOpp().getNumberOfSprites(); i++) {
                    oppSprites[i] = LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().getPayers().getDudeOpp().getMeHigh(i);
                }

                comboPicArray = new Image[9];
                for (int u = 0; u < 6; u++) {
                    comboPicArray[u] = pix.loadImageFromToolkitNoScale("images/screenTxt/" + u + ".png");
                }
                comboPicArray[7] = pix.loadImageFromToolkitNoScale("images/screenTxt/7.png");
                comboPicArray[8] = LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().getPayers().getDudeChar().getMeHigh(11);

                comicPicArray = new Image[10];
                comicPicArray[0] = LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().getPayers().getDudeChar().getMeHigh(11);
                for (int bx = 1; bx < numOfComicPics + 1; bx++) {
                    comicPicArray[bx] = pix.loadImageFromToolkitNoScale("images/screenComic/" + (bx - 1) + ".png");
                }

                menuHold = pix.loadImageFromToolkitNoScale("images/" + LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().getPayers().getCharName() + "/menu.png");
                damLayer = pix.loadImageFromToolkit("images/damage1.png", LoginScreen.getLoginScreen().getdefSpriteWidth(), LoginScreen.getLoginScreen().getdefSpriteHeight());

                time0 = pix.loadImageFromToolkitNoScale("images/fig/0.png");
                time1 = pix.loadImageFromToolkitNoScale("images/fig/1.png");
                time2 = pix.loadImageFromToolkitNoScale("images/fig/2.png");
                time3 = pix.loadImageFromToolkitNoScale("images/fig/3.png");
                time4 = pix.loadImageFromToolkitNoScale("images/fig/4.png");
                time5 = pix.loadImageFromToolkitNoScale("images/fig/5.png");
                time6 = pix.loadImageFromToolkitNoScale("images/fig/6.png");
                time7 = pix.loadImageFromToolkitNoScale("images/fig/7.png");
                time8 = pix.loadImageFromToolkitNoScale("images/fig/8.png");
                time9 = pix.loadImageFromToolkitNoScale("images/fig/9.png");
                times = new Image[]{time0, time1, time2, time3, time4, time5, time6, time7, time8, time9, opp11};

                charCaption = new VolatileImage[charNames.length];

                if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.storyMode)) {
                    for (int p = 0; p < charNames.length; p++) {
                        charCaption[p] = pix.loadBImage2("images/" + charNames[p] + "/cap.png", 48, 48, this);
                    }
                } else {
                    for (int p = 0; p < charNames.length; p++) {
                        charCaption[p] = null;
                    }
                }
                charPort = opp11;
                Image transBuf = pix.loadImageFromToolkit("images/trans.png", 5, 5);
                hpHolder = pix.loadImageFromToolkitNoScale("images/hpHolder.png");

                bgPic = pix.loadImageFromToolkit(bgLocation, 852, 480);
                phys = pix.loadImageFromToolkitNoScale("images/t_physical.png");
                cel = pix.loadImageFromToolkitNoScale("images/t_celestia.png");
                itm = pix.loadImageFromToolkitNoScale("images/t_item.png");
                fury1 = pix.loadImageFromToolkitNoScale("images/fury.gif");
                fury2 = pix.loadImageFromToolkitNoScale("images/furyo.png");
                fury = fury2;

                ambient1 = pix.loadBImage2("images/bgBG" + activeStage + "a.png", 852, 480, this);
                ambient2 = pix.loadBImage2("images/bgBG" + activeStage + "b.png", 852, 480, this);

                if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.storyMode)) {
                    storyPicArr = new VolatileImage[13];
                    for (int u = 0; u < 11; u++) {
                        storyPicArr[u] = pix.loadBImage2("images/Story/s" + u + ".png", LoginScreen.getLoginScreen().getdefSpriteWidth(), LoginScreen.getLoginScreen().getdefSpriteHeight(), this);
                    }
                    storyPic = storyPicArr[0];
                }

                furyBar = pix.loadImageFromToolkitNoScale("images/furyBar.png");
                quePic1 = pix.loadImageFromToolkitNoScale("images/queB.png");
                quePic2 = pix.loadImageFromToolkitNoScale("images/que.gif");
                oppBar = pix.loadImageFromToolkitNoScale("images/oppBar.png");

                moveCat = new Image[]{phys, cel, itm};
                curr = moveCat[0];

                stat1 = pix.loadBImage2("images/stats/stat1.png", 90, 24, this);
                stat2 = pix.loadBImage2("images/stats/stat2.png", 90, 24, this);
                stat3 = pix.loadBImage2("images/stats/stat3.png", 90, 24, this);
                stat4 = pix.loadBImage2("images/stats/stat4.png", 90, 24, this);
                stats = new VolatileImage[]{stat1, stat2, stat3, stat4};

                hud1 = pix.loadImageFromToolkitNoScale("images/hud1.png");
                hud2 = pix.loadImageFromToolkitNoScale("images/hud2.png");

                win = pix.loadImageFromToolkitNoScale("images/win.png");
                lose = pix.loadImageFromToolkitNoScale("images/lose.png");
                status = transBuf;

                System.out.println("loaded all char sprites pix");
                imagesCharChached = true;
                //ensures method is only run once
            } catch (Exception e) {
                imagesCharChached = false;
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    /**
     * EPIC!!!! Loads char sprite
     * Uses toolkit images and less RAM
     */
    private void loadCharSpritesLow() {

        if (imagesCharChached == false) {
            try {


                LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().getPayers().getDudeChar().loadMeLow();
                LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().getPayers().getDudeOpp().loadMeLow();

                charSprites = new Image[12];
                for (int i = 0; i < LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().getPayers().getDudeChar().getNumberOfSprites(); i++) {
                    charSprites[i] = LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().getPayers().getDudeChar().getMeLow(i);
                }

                oppSprites = new Image[12];
                for (int i = 0; i < LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().getPayers().getDudeOpp().getNumberOfSprites(); i++) {
                    oppSprites[i] = LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().getPayers().getDudeOpp().getMeLow(i);
                }

                comboPicArray = new Image[9];
                for (int u = 0; u < 6; u++) {
                    comboPicArray[u] = pix.loadImageFromToolkitNoScale("images/screenTxt/" + u + ".png");
                }

                comboPicArray[7] = pix.loadImageFromToolkitNoScale("images/screenTxt/7.png");
                comboPicArray[8] = LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().getPayers().getDudeChar().getMeHigh(11);

                comicPicArray = new Image[10];
                comicPicArray[0] = LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().getPayers().getDudeChar().getMeHigh(11);
                for (int bx = 1; bx < numOfComicPics + 1; bx++) {
                    comicPicArray[bx] = pix.loadImageFromToolkitNoScale("images/screenComic/" + (bx - 1) + ".png");
                }

                menuHold = pix.loadImageFromToolkitNoScale("images/" + LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().getPayers().getCharName() + "/menu.png");
                damLayer = pix.loadImageFromToolkit("images/damage1.png", LoginScreen.getLoginScreen().getdefSpriteWidth(), LoginScreen.getLoginScreen().getdefSpriteHeight());

                time0 = pix.loadImageFromToolkitNoScale("images/fig/0.png");
                time1 = pix.loadImageFromToolkitNoScale("images/fig/1.png");
                time2 = pix.loadImageFromToolkitNoScale("images/fig/2.png");
                time3 = pix.loadImageFromToolkitNoScale("images/fig/3.png");
                time4 = pix.loadImageFromToolkitNoScale("images/fig/4.png");
                time5 = pix.loadImageFromToolkitNoScale("images/fig/5.png");
                time6 = pix.loadImageFromToolkitNoScale("images/fig/6.png");
                time7 = pix.loadImageFromToolkitNoScale("images/fig/7.png");
                time8 = pix.loadImageFromToolkitNoScale("images/fig/8.png");
                time9 = pix.loadImageFromToolkitNoScale("images/fig/9.png");
                times = new Image[]{time0, time1, time2, time3, time4, time5, time6, time7, time8, time9, opp11};

                charCaption = new VolatileImage[charNames.length];

                if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.storyMode)) {
                    for (int p = 0; p < charNames.length; p++) {
                        charCaption[p] = pix.loadBImage2("images/" + charNames[p] + "/cap.png", 48, 48, this);
                    }
                } else {
                    for (int p = 0; p < charNames.length; p++) {
                        charCaption[p] = null;
                    }
                }
                charPort = opp11;
                Image transBuf = pix.loadImageFromToolkit("images/trans.png", 5, 5);
                hpHolder = pix.loadImageFromToolkitNoScale("images/hpHolder.png");

                bgPic = pix.loadImageFromToolkit(bgLocation, 852, 480);
                phys = pix.loadImageFromToolkitNoScale("images/t_physical.png");
                cel = pix.loadImageFromToolkitNoScale("images/t_celestia.png");
                itm = pix.loadImageFromToolkitNoScale("images/t_item.png");
                fury1 = pix.loadImageFromToolkitNoScale("images/fury.gif");
                fury2 = pix.loadImageFromToolkitNoScale("images/furyo.png");
                fury = fury2;

                {
                    ambient1 = pix.loadBImage2("images/bgBG" + activeStage + "a.png", 852, 480, this);
                    ambient2 = pix.loadBImage2("images/bgBG" + activeStage + "b.png", 852, 480, this);
                }

                if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.storyMode)) {
                    storyPicArr = new VolatileImage[11];
                    for (int u = 0; u < 11; u++) {
                        storyPicArr[u] = pix.loadBImage2("images/Story/s" + u + ".png", LoginScreen.getLoginScreen().getdefSpriteWidth(), LoginScreen.getLoginScreen().getdefSpriteHeight(), this);
                    }
                    storyPic = storyPicArr[0];
                }

                furyBar = pix.loadImageFromToolkitNoScale("images/furyBar.png");
                quePic1 = pix.loadImageFromToolkitNoScale("images/queB.png");
                quePic2 = pix.loadImageFromToolkitNoScale("images/que.gif");
                oppBar = pix.loadImageFromToolkitNoScale("images/oppBar.png");

                moveCat = new Image[]{phys, cel, itm};
                curr = moveCat[0];

                stat1 = pix.loadBImage2("images/stats/stat1.png", 90, 24, this);
                stat2 = pix.loadBImage2("images/stats/stat2.png", 90, 24, this);
                stat3 = pix.loadBImage2("images/stats/stat3.png", 90, 24, this);
                stat4 = pix.loadBImage2("images/stats/stat4.png", 90, 24, this);
                stats = new VolatileImage[]{stat1, stat2, stat3, stat4};

                hud1 = pix.loadImageFromToolkitNoScale("images/hud1.png");
                hud2 = pix.loadImageFromToolkitNoScale("images/hud2.png");

                win = pix.loadImageFromToolkitNoScale("images/win.png");
                lose = pix.loadImageFromToolkitNoScale("images/lose.png");
                status = transBuf;

                System.out.println("loaded all char sprites pix");
                imagesCharChached = true;
                //ensures method is only run once
            } catch (Exception e) {
                imagesCharChached = true;
                JOptionPane.showMessageDialog(null, e);
            }
        }
        // }
        // }.start();
    }

    /**
     * Flashy text at bottom of screen
     *
     * @param thisMessage
     */
    public void flashyText(String thisMessage) {
        opacityTxt = 0.0f;
        battleInf = new StringBuilder(thisMessage);
    }

    private void createSoftBackBuffer() {
        if (runNew) {
            g2d = (Graphics2D) g; // no DB, cpu utilisation fluctuates from 49 to 73% in ubuntu 10.4 / sun jre 6_21 / Pentium E2180 / 1GB Radeon 5670HD / 1.5GB RAM
            g2d.setRenderingHints(renderHints); //activate aliasing
        }
    }

    /**
     * Hardware acceleration
     */
    private void createBackBuffer() {
        if (runNew) {
            ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            System.out.println("Accelerateable memory!!!!!!!!!!! " + ge.getDefaultScreenDevice().getAvailableAcceleratedMemory());
            gc = ge.getDefaultScreenDevice().getDefaultConfiguration();
            //optimise, clear unused memory
            if (volatileImg != null) {
                volatileImg.flush();
                volatileImg = null;
            }
            volatileImg = gc.createCompatibleVolatileImage(LoginScreen.getLoginScreen().getGameWidth(), LoginScreen.getLoginScreen().getGameHeight());
            volatileImg.setAccelerationPriority(1.0f);
            g2d = volatileImg.createGraphics();
            g2d.setRenderingHints(renderHints); //activate aliasing
            runNew = false;
        }
    }

    /**
     * Calculates angle of circle
     *
     * @return circel angle
     */
    private int phyAngle() {
        float start = LoginScreen.getLoginScreen().getMenu().getMain().getGame().getGameInstance().getRecoveryUnitsChar() / 290.0f;
        angleRaw = start * 360;
        result = Integer.parseInt("" + Math.round(angleRaw));
        if (result >= 360) {
            enableSelection();
        } else {
            disableSelection();
        }

        return result;
    }

    /**
     * Stop up down animations
     */
    public void stopAnimations() {
        pauseThreads();
    }

    /**
     * Gets screenshot
     */
    public void captureScreenShot() {
        try {
            BufferedImage dudeC = volatileImg.getSnapshot();
            File file;
            file = new File(LoginScreen.configLoc + generateUID() + ".png");
            ImageIO.write(dudeC, "png", file);
            systemNotice(lang.getLine(170));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Go to next command menu column
     */
    public void nextAnim() {
        if (nextEnabled && backEnabled) {
            backEnabled = false;
            yTEST = yTESTinit;
            if (currentCols < moveCat.length - 1) {
                currentCols++;
            } else {
                currentCols = 0;
            }
            curr = moveCat[currentCols];
            resolveText();
            opac = 0.0f;
            backEnabled = true;
        }
    }

    /**
     * Go to previous command menu column
     */
    public void prevAnim() {
        if (backEnabled && nextEnabled) {
            nextEnabled = false;
            yTEST = yTESTinit;
            opac = 0.0f;
            if (currentCols > 0) {
                currentCols = currentCols - 1;
            } else {
                currentCols = moveCat.length - 1;
            }
            curr = moveCat[currentCols];
            resolveText();
            opac = 0.0f;
            nextEnabled = true;
        }

    }

    /**
     * Navigate to specific location in the menu. Used by the mouse
     */
    public void thisItem(int item) {
        //if (itemindex<3 && itemindex>0)
        {
            itemindex = item;
        }

        //default size
        for (int u = 0; u < fontSizes.length; u++) {
            fontSizes[u] = LoginScreen.normalTxtSize;
        }

        //increase font size
        fontSizes[itemindex] = LoginScreen.bigTxtSize;
    }

    /**
     * Navigate up in the menu
     */
    public void upItem() {
        if (itemindex > 0) {
            itemindex = itemindex - 1;
        } else {
            itemindex = 3;
        }

        //default size
        for (int u = 0; u < fontSizes.length; u++) {
            fontSizes[u] = LoginScreen.normalTxtSize;
        }

        //increase font size
        fontSizes[itemindex] = LoginScreen.bigTxtSize;
    }

    /**
     * Selecting a move
     */
    public void moveSelected() {
        if (clasherOn) {
            clasher.plrClashing();
            System.out.println("Playr clashin");
        } else if (safeToSelect) {
            numOfAttacks = numOfAttacks + 1;
            sound = new ThreadMP3(ThreadMP3.selectSound(), false);
            sound.play();
            move = (currentCols * 4) + itemindex + 1;
            LoginScreen.getLoginScreen().getMenu().getMain().getGame().attackArray[LoginScreen.getLoginScreen().getMenu().getMain().getGame().comboCounter] = genStr(move); // count initially negative 1, add one to get to index 0
            LoginScreen.getLoginScreen().getMenu().getMain().getGame().incrimentComboCounter();
            checkStatus();
            LoginScreen.getLoginScreen().getMenu().getMain().getGame().showBattleMessage("Qued up " + getSelMove(move));
        } else {
            LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().errorSound();
        }
    }

    /**
     * Checks if dude has more slots
     * TRIGGERS ATTACKS
     */
    private void checkStatus() {
        if (LoginScreen.getLoginScreen().getMenu().getMain().getGame().comboCounter == 4) {
            attack();
        }
    }

    /**
     * Attacks the opponent
     */
    public void attack() {
        if (numOfAttacks > 0) {
            if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.singlePlayer) || LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.storyMode) || LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.singlePlayer2)) {
                disableSelection();
                LoginScreen.getLoginScreen().getMenu().getMain().getGame().getGameInstance().triggerCharAttack();
            } else if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanClient)) {
                LoginScreen.getLoginScreen().getMenu().getMain().getGame().comboCounter = 0;
                //clear active combos

                setSprites('c', 9, 11);
                setSprites('o', 9, 11);
                if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.singlePlayer2)) {
                    setSprites('a', 9, 11);
                    setSprites('b', 9, 11);
                }
                //LoginScreen.getLoginScreen().getMenu().getMain().getGame().DisableMenus(); disable issueing of more attacksCombatMage during execution
                // each Mattack will check if they are in the battle que.... if they are they execute

                //broadcast hurtChar on net
                attackStr = "" + LoginScreen.getLoginScreen().getMenu().getMain().getGame().attackArray[0] + LoginScreen.getLoginScreen().getMenu().getMain().getGame().attackArray[1] + LoginScreen.getLoginScreen().getMenu().getMain().getGame().attackArray[2] + LoginScreen.getLoginScreen().getMenu().getMain().getGame().attackArray[3] + " attack";
                System.out.println(attackStr);
                client.sendData(attackStr);

                //attack on local
                disableSelection();
                LoginScreen.getLoginScreen().getMenu().getMain().getGame().getGameInstance().triggerCharAttack();

                if (LoginScreen.getLoginScreen().getMenu().getMain().getGame().done != 1)// if game still running enable menus
                {
                    LoginScreen.getLoginScreen().getMenu().getMain().getAttacksChar().CharacterOverlayDisabled();
                }
            } else if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanHost)) {
                LoginScreen.getLoginScreen().getMenu().getMain().getGame().comboCounter = 0;
                //clear active combos

                setSprites('c', 9, 11);
                setSprites('o', 9, 11);
                if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.singlePlayer2)) {
                    setSprites('a', 9, 11);
                    setSprites('b', 9, 11);
                }
                //LoginScreen.getLoginScreen().getMenu().getMain().getGame().DisableMenus(); disable issueing of more attacksCombatMage during execution
                // each Mattack will check if they are in the battle que.... if they are they execute

                //broadcast hurtChar on net
                attackStr = "" + LoginScreen.getLoginScreen().getMenu().getMain().getGame().attackArray[0] + LoginScreen.getLoginScreen().getMenu().getMain().getGame().attackArray[1] + LoginScreen.getLoginScreen().getMenu().getMain().getGame().attackArray[2] + LoginScreen.getLoginScreen().getMenu().getMain().getGame().attackArray[3] + " attack";
                System.out.println(attackStr);
                server.sendData(attackStr);

                //attack on local
                disableSelection();
                LoginScreen.getLoginScreen().getMenu().getMain().getGame().getGameInstance().triggerCharAttack();

                LoginScreen.getLoginScreen().getMenu().getMain().getGame().getGameInstance().setRecoveryUnitsChar(0);
                if (LoginScreen.getLoginScreen().getMenu().getMain().getGame().done != 1)// if game still running enable menus
                {
                    LoginScreen.getLoginScreen().getMenu().getMain().getAttacksChar().CharacterOverlayDisabled();
                }
            }
        }
    }

    /**
     * Makes text white, meaning its OK to select a move
     */
    public void enableSelection() {
        CurrentColor = Color.WHITE;
        safeToSelect = true;
    }

    /**
     * Makes text red, meaning its NOT OK to select a move
     */
    public void disableSelection() {
        CurrentColor = Color.RED;
        safeToSelect = false;
    }

    /**
     * limit break, wee!!!
     */
    public void limitBreak(char who) {
        dude = who;
        new Thread() {

            @Override
            public void run() {
                if (CanvasGameRender.getBreak() == 1000) {
                    //&& LoginScreen.getLoginScreen().getMenu().getMain().getGame().getGameInstance().getRecoveryUnitsChar()>289
                    //runs on local
                    if (dude == 'c' && limitRunning && LoginScreen.getLoginScreen().getMenu().getMain().getGame().getGameInstance().getRecoveryUnitsChar() > 289) {
                        limitRunning = false;

                        //broadcast on net
                        if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanClient)) {
                            LoginScreen.getLoginScreen().getMenu().getMain().sendToServer("limt_Break_Oxodia_Ownz");
                        } else if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanHost)) {
                            LoginScreen.getLoginScreen().getMenu().getMain().sendToClient("limt_Break_Oxodia_Ownz");
                        }
                        setAttackType("fury", 'c');
                        LoginScreen.getLoginScreen().getMenu().getMain().getGame().comboCounter = 0;
                        LoginScreen.getLoginScreen().getMenu().getMain().getGame().getGameInstance().pauseActivityRegen();
                        LoginScreen.getLoginScreen().getMenu().getMain().getGame().getGameInstance().setRecoveryUnitsChar(0);
                        try {
                            LoginScreen.getLoginScreen().getMenu().getMain().getController().setRumbler(true, 0.8f);
                        } catch (Exception e) {
                        }
                        for (int i = 1; i < 9; i++) {
                            //stop attacking when game over
                            if (LoginScreen.getLoginScreen().getMenu().getMain().getGame().getGameInstance().isGameOver == false) {
                                furySound();
                                hurtSoundOpp();
                                LoginScreen.getLoginScreen().getMenu().getMain().getAttacksChar().CharacterOverlayDisabled();
                                setSprites('c', i, 11);
                                setSprites('a', i, 11);
                                setSprites('o', 0, 11);
                                shakeOppCharLB();
                                comboPicArrayPosOpp = i;
                                furyComboOpacity = 1.0f;
                                LoginScreen.getLoginScreen().getMenu().getMain().getGame().lifePhysUpdateSimple(2, 100, "");
                            }
                        }
                        LoginScreen.getLoginScreen().getMenu().getMain().getAttacksChar().CharacterOverlayEnabled();
                        try {
                            LoginScreen.getLoginScreen().getMenu().getMain().getController().setRumbler(false, 0.0f);
                        } catch (Exception e) {
                        }
                        comboPicArrayPosOpp = 8;
                        LoginScreen.getLoginScreen().getMenu().getMain().getGame().getGameInstance().resumeActivityRegen();
                        setSprites('c', 9, 11);
                        setSprites('o', 9, 11);
                        setSprites('a', 11, 11);
                        limitRunning = true;
                        LoginScreen.getLoginScreen().getMenu().getMain().getGame().resetBreak();
                        setAttackType("normal", 'c');
                    } else if (dude == 'o' && limitRunning && LoginScreen.getLoginScreen().getMenu().getMain().getGame().getGameInstance().getRecoveryUnitsOpp() > 289) {
                        setAttackType("fury", 'o');
                        limitRunning = false;
                        try {
                            LoginScreen.getLoginScreen().getMenu().getMain().getController().setRumbler(true, 0.8f);
                        } catch (Exception e) {
                        }
                        for (int i = 1; i < 9; i++) {
                            if (LoginScreen.getLoginScreen().getMenu().getMain().getGame().getGameInstance().isGameOver == false) {
                                LoginScreen.getLoginScreen().getMenu().getMain().getAttacksChar().CharacterOverlayEnabled();
                                furySound();
                                hurtSoundChar();
                                LoginScreen.getLoginScreen().getMenu().getMain().getGame().getGameInstance().setRecoveryUnitsOpp(0);
                                setSprites('o', i, 11);
                                setSprites('b', i, 11);
                                setSprites('c', 0, 11);
                                shakeCharLB();
                                comboPicArrayPosOpp = i;
                                LoginScreen.getLoginScreen().getMenu().getMain().getGame().lifePhysUpdateSimple(1, 100, "");
                            }
                        }
                        LoginScreen.getLoginScreen().getMenu().getMain().getAttacksChar().CharacterOverlayDisabled();
                        try {
                            LoginScreen.getLoginScreen().getMenu().getMain().getController().setRumbler(false, 0.0f);
                        } catch (Exception e) {
                        }
                        comboPicArrayPosOpp = 8;
                        setSprites('o', 9, 11);
                        setSprites('c', 9, 11);
                        setSprites('b', 11, 11);
                        limitRunning = true;
                        LoginScreen.getLoginScreen().getMenu().getMain().getGame().resetBreak();
                        setAttackType("normal", 'o');
                    }
                }
            }
        }.start();
    }

    /**
     * Attack sounds
     */
    private void attackSoundChar() {
        if (LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().getPayers().getDudeChar().isMale()) {
            randSoundIntChar = (int) (Math.random() * ThreadMP3.maleHurt.length * 2);
            if (randSoundIntChar < ThreadMP3.maleHurt.length) {
                attackChar = new ThreadMP3(ThreadMP3.maleAttack(randSoundIntChar), false);
                attackChar.play();
            }
        } else {
            randSoundIntChar = (int) (Math.random() * ThreadMP3.femaleHurt.length * 2);
            if (randSoundIntChar < ThreadMP3.femaleHurt.length) {
                attackChar = new ThreadMP3(ThreadMP3.femaleAttack(randSoundIntChar), false);
                attackChar.play();
            }
        }
    }

    private void attackSoundOpp() {
        if (LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().getPayers().getDudeOpp().isMale()) {
            randSoundIntOpp = (int) (Math.random() * ThreadMP3.maleHurt.length * 2);
            if (randSoundIntOpp < ThreadMP3.maleHurt.length) {
                attackOpp = new ThreadMP3(ThreadMP3.maleAttack(randSoundIntOpp), false);
                attackOpp.play();
            }
        } else {
            randSoundIntOpp = (int) (Math.random() * ThreadMP3.femaleHurt.length * 2);
            if (randSoundIntOpp < ThreadMP3.femaleHurt.length) {
                attackOpp = new ThreadMP3(ThreadMP3.femaleAttack(randSoundIntOpp), false);
                attackOpp.play();
            }
        }
    }

    private void hurtSoundChar() {
        if (LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().getPayers().getDudeOpp().isMale()) {
            randSoundIntCharHurt = (int) (Math.random() * ThreadMP3.maleAttacks.length * 2);
            if (randSoundIntCharHurt < ThreadMP3.maleAttacks.length) {
                hurtChar = new ThreadMP3(ThreadMP3.maleHurt(randSoundIntCharHurt), false);
                hurtChar.play();
            }
        } else {
            randSoundIntCharHurt = (int) (Math.random() * ThreadMP3.femaleAttacks.length * 2);
            if (randSoundIntCharHurt < ThreadMP3.femaleAttacks.length) {
                hurtChar = new ThreadMP3(ThreadMP3.femaleHurt(randSoundIntCharHurt), false);
                hurtChar.play();
            }
        }
    }

    private void hurtSoundOpp() {
        if (LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().getPayers().getDudeChar().isMale()) {
            randSoundIntOppHurt = (int) (Math.random() * ThreadMP3.maleAttacks.length * 2);
            if (randSoundIntOppHurt < ThreadMP3.maleAttacks.length) {
                hurtOpp = new ThreadMP3(ThreadMP3.maleHurt(randSoundIntOppHurt), false);
                hurtOpp.play();
            }
        } else {
            randSoundIntOppHurt = (int) (Math.random() * ThreadMP3.femaleAttacks.length * 2);
            if (randSoundIntOppHurt < ThreadMP3.femaleAttacks.length) {
                hurtOpp = new ThreadMP3(ThreadMP3.femaleHurt(randSoundIntOppHurt), false);
                hurtOpp.play();
            }
        }
    }

    private void furySound() {
        furySound = new ThreadMP3(ThreadMP3.furyAttck(), false);
        furySound.play();
    }

    private void nrmlDamageSound() {
        damageSound = new ThreadMP3(ThreadMP3.playerAttack(), false);
        damageSound.play();
    }

    public void newInstance() {
        opponentDamageYLoc = 400;
        playerDamageYLoc = 400;
        dnladng = false;

        opponentDamageXLoc = 150;
        playerDamageXLoc = 575;

        statusOpOpp = 0.0f;
        statusOpChar = 0.0f;

        statIndexChar = 0;
        statIndexOpp = 0;

        bigFont = LoginScreen.getLoginScreen().getMyFont(LoginScreen.bigTxtSize);
        normalFont = LoginScreen.getLoginScreen().getMyFont(LoginScreen.normalTxtSize);

        oppBarYOffset = 435;
        LoginScreen.getLoginScreen().defHeight = LoginScreen.getLoginScreen().getGameHeight();
        paneCord = (int) (306);
        menuBarY = (int) (360);

        upDown = new ThreadAnim1();
        if (WindowOptions.graphics.equalsIgnoreCase("High")) {
            upDown2 = new ThreadAnim2();
            upDown3 = new ThreadAnim3();
            loadedUpdaters = true;
        }

        threadsNotRunningYet = false;

        if (WindowOptions.graphics.equalsIgnoreCase("High")) {
            specialEffect = true;
        } else {
            specialEffect = false;
        }

        if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanHost)) {
            server = LoginScreen.getLoginScreen().getMenu().getMain().getServer();
        }

        if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanClient)) {
            //get ip from game
            client = LoginScreen.getLoginScreen().getMenu().getMain().getClient();
        }

        if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equals(WindowMain.singlePlayer2)) {
            charAssSpriteStatus = 9;
            oppAssSpriteStatus = 9;
        } else {
            charAssSpriteStatus = 11;
            oppAssSpriteStatus = 11;
        }
        charPointInc = LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().getPayers().getPoints();
        System.out.println("Char inc: " + charPointInc);
        getCharMoveset();
        currentColumn = physical;
        damOpInc = 0;
        cacheNumPix();
        if (WindowOptions.graphics.equalsIgnoreCase("High")) {
            loadCharSpritesHigh();
        } else {
            loadCharSpritesLow();
        }
        itemindex = 0;
        one = 10;
        two = 10;
        three = 10;
        four = 10;
        oneO = 10;
        twoO = 10;
        threeO = 10;
        fourO = 10;
        furyBarY = (int) (130);
        itemX = 215;
        itemY = (int) (360);
        dnladng = true;
        fightMus = new ThreadMP3("audio/" + CanvasStageSelect.musFiles[CanvasStageSelect.musicInt] + ".mp3", true);
        //setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
    }

    public void playBGSound() {
        fightMus.play();
    }

    /**
     * pause threads
     */
    public void pauseThreads() {
        try {
            fightMus.pause();
            if (WindowOptions.graphics.equalsIgnoreCase("High")) {
                upDown.pauseThread();
                upDown2.pauseThread();
                upDown3.pauseThread();
            }
        } catch (Exception e) {
        }
    }

    public void closeAudio() {
        try {
            fightMus.stop();
            //fightMus.close();
        } catch (Exception e) {
        }
    }

    /**
     * resume threads
     */
    public void resumeThreads() {
        try {
            fightMus.resume();
            if (WindowOptions.graphics.equalsIgnoreCase("High")) {
                upDown.resumeThread();
                upDown2.resumeThread();
                upDown3.resumeThread();
            }
        } catch (Exception e) {
        }
    }

    /**
     * Clear char port
     */
    public void charPortBlank() {
        charPort = opp1;
    }

    /**
     * Change port pic
     *
     * @param here - index of pic
     */
    public void charPortSet(int here) {
        charPort = charCaption[here];
    }

    /**
     * get scale of Y
     *
     * @return y scale
     */
    public float scaleY() {
        return LoginScreen.getLoginScreen().getGameYScale();
    }

    /**
     * get scale of X
     *
     * @return X scale
     */
    public float scaleX() {
        return LoginScreen.getLoginScreen().getGameXScale();
    }
}

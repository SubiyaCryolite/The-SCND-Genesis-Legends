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

import com.scndgen.legends.LoginScreen;
import com.scndgen.legends.characters.Raila;
import com.scndgen.legends.engine.JenesisCanvas;
import com.scndgen.legends.engine.JenesisLanguage;
import com.scndgen.legends.engine.JenesisGlassPane;
import com.scndgen.legends.engine.JenesisImage;
import com.scndgen.legends.windows.WindowMain;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.io.File;

/**
 * @author: Ifunga Ndana
 * @class: drawPrevChar
 * This class creates a graphical preview of the character and opponent
 */
public abstract class DrawCharSel extends JenesisCanvas {

    private static String[] statsChar = new String[LoginScreen.getLoginScreen().charNames.length];
    public int col, currentSlot = 0, lastRow, numOfCharacters = 12, xCordCloud = 0, xCordCloud2 = 0, charYcap = 0, charXcap = 0, charPrevLoicIndex = 0, hIndex = 1, x = 0, y = 0, vIndex = 0, hSpacer = 48, vSpacer = 48, hPos = 354, firstLine = 105, horizColumns = 3, verticalRows = 3;
    private GraphicsEnvironment ge;
    private JenesisLanguage lang;
    private GraphicsConfiguration gc;
    private boolean alreadyRunnging = false, runNew = true;
    private VolatileImage volatileImg;
    private Graphics2D g2d;
    private JenesisImage pix;
    private int charDescIndex = 0;
    private float opacInc, p1Opac, opacChar;
    private Font bigFont, normalFont;
    private String[] charDesc = new String[numOfCharacters];
    private Image[] charNorm = new Image[numOfCharacters];
    private Image[] charBlur = new Image[numOfCharacters];
    private Image[] charPrev = new Image[numOfCharacters];
    private Image[] oppPrev = new Image[numOfCharacters];
    private Image[] charNames = new Image[numOfCharacters];
    private Image fg1, fg2, fg3, bg3;
    private Image charBack, oppBack, charHold, p1, p2, fight, charDescPic, oppDescPic;
    private String[] charPrevLox = {"images/Raila/RailaPrev.png", "images/Subiya/SubiyaPrev.png", "images/Lynx/LynxPrev.png", "images/Aisha/Prev.png", "images/Ravage/Prev.png", "images/Ade/Prev.png", "images/Jonah/Prev.png", "images/Adam/Prev.png", "images/NOVA Adam/Prev.png", "images/Azaria/Prev.png", "images/Sorrowe/Prev.png", "images/The Thing/Prev.png"};
    private String[] oppPrevLox = {"images/Raila/RailaPrevO.png", "images/Subiya/SubiyaPrevO.png", "images/Lynx/LynxPrevO.png", "images/Aisha/PrevO.png", "images/Ravage/PrevO.png", "images/Ade/PrevO.png", "images/Jonah/PrevO.png", "images/Adam/PrevO.png", "images/NOVA Adam/PrevO.png", "images/Azaria/PrevO.png", "images/Sorrowe/PrevO.png", "images/The Thing/PrevO.png"};
    private RenderingHints renderHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //anti aliasing, kill jaggies

    public DrawCharSel() {
        lang = LoginScreen.getLoginScreen().getLangInst();
        loadDesc();
        opacInc = 0.025f;
        pix = new JenesisImage();
        over1 = new JenesisGlassPane();
        loadCaps();
        setBorder(BorderFactory.createEmptyBorder());
    }

    /**
     * When both playes are selected, this prevents movement.
     *
     * @return false if both Characters have been selected, true if only one is selected
     */
    public static boolean bothArentSelected() {
        boolean answer = true;
        if (LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().proceed1 && LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().proceed2) {
            answer = false;
        }
        return answer;
    }

    private void loadDesc() {
        statsChar[0] = lang.getLine(134);
        statsChar[1] = lang.getLine(135);
        statsChar[2] = lang.getLine(136);
        statsChar[3] = lang.getLine(137);
        statsChar[4] = lang.getLine(138);
        statsChar[5] = lang.getLine(139);
        statsChar[6] = lang.getLine(140);
        statsChar[7] = lang.getLine(141);
        statsChar[8] = lang.getLine(142);
        statsChar[9] = lang.getLine(143);
        statsChar[10] = lang.getLine(144);
        statsChar[11] = lang.getLine(145);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(852, 480);
    }

    public Dimension setPreferredSize() {
        return new Dimension(852, 480);
    }

    @Override
    public void paintComponent(Graphics g) {
        createBackBuffer();

        g2d.setFont(normalFont);

        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, 852, 480);


        g2d.drawImage(bg3, 0, 0, this);

        g2d.drawImage(fg1, xCordCloud, 0, this);
        g2d.drawImage(fg2, xCordCloud2, 0, this);
        g2d.drawImage(fg3, 0, 0, this);

        if (p1Opac < (1.0f - opacInc)) {
            p1Opac = p1Opac + opacInc;
        }

        if (opacChar < (1.0f - (opacInc * 2))) {
            opacChar = opacChar + (opacInc * 2);
        }

        g2d.setColor(Color.BLACK);
        g2d.setComposite(makeComposite(0.70f));
        g2d.fillRect(0, 0, 853, 480);
        g2d.setComposite(makeComposite(1.0f));

        //character preview DYNAMIC change
        if (LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().proceed1 != true) {
            g2d.setComposite(makeComposite(p1Opac));
            g2d.drawImage(charPrev[charPrevLoicIndex], charXcap + x, charYcap, this);
            g2d.setComposite(makeComposite(1.0f));
            g2d.drawImage(charNames[charPrevLoicIndex], 40 - x, 400, this);
        }

        //opponent preview DYNAMIC change, only show if quick match, should change sprites
        if (LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().proceed1 && LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().proceed2 != true && LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.singlePlayer)) {
            g2d.setComposite(makeComposite(p1Opac));
            g2d.drawImage(oppPrev[charPrevLoicIndex], 512 - x, charYcap, this);
            g2d.setComposite(makeComposite(1.0f));
            g2d.drawImage(charNames[charPrevLoicIndex], 553 + x, 400, this);
        }


        //if character selected draw FIXED prev
        if (LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().proceed1) {
            g2d.drawImage(charPrev[LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().charPrevLoc], charXcap, charYcap, this);
            g2d.drawImage(charNames[LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().selectedCharIndex], 40, 380, this);
        }

        //if opp selected, draw FIXED prev
        if (LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().proceed2) {
            g2d.drawImage(oppPrev[LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().oppPrevLoc], 512, charYcap, this);
            g2d.drawImage(charNames[LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().selectedOppIndex], 553, 380, this);
        }

        //all char caps in this segment
        {

            g2d.drawImage(charHold, 311, 0, this);

            if (well()) {
                if (LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().proceed1 != true) {
                    g2d.drawImage(charBack, (hPos - hSpacer) + (hSpacer * hIndex), firstLine + (vSpacer * vIndex), this);
                }

                if (LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().proceed1 && LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().proceed2 != true && LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.singlePlayer)) {
                    g2d.drawImage(oppBack, (hPos - hSpacer) + (hSpacer * hIndex), firstLine + (vSpacer * vIndex), this);
                }
            }

            col = 0;
            for (int i = 0; i < (charNorm.length / 3); i++) {
                {
                    g2d.drawImage(charBlur[col], hPos, firstLine + (vSpacer * i), this);
                    //normal
                    if (bothArentSelected() && hIndex == 1 && vIndex == i && LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().allPlayers[((vIndex * 3) + hIndex) - 1] == 0)//clear
                    {
                        g2d.setComposite(makeComposite(opacChar));
                        g2d.drawImage(charNorm[col], hPos, firstLine + (vSpacer * i), this);
                        charPrevLoicIndex = col;
                        g2d.setComposite(makeComposite(1.0f));
                    }
                    col++;
                }

                {
                    g2d.drawImage(charBlur[col], hPos + (hSpacer * 1), firstLine + (vSpacer * i), this);
                    //normal
                    if (bothArentSelected() && hIndex == 2 && vIndex == i && LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().allPlayers[((vIndex * 3) + hIndex) - 1] == 0)//clear
                    {
                        g2d.setComposite(makeComposite(opacChar));
                        g2d.drawImage(charNorm[col], hPos + (hSpacer * 1), firstLine + (vSpacer * i), this);
                        charPrevLoicIndex = col;
                        g2d.setComposite(makeComposite(1.0f));
                    }
                    col++;
                }

                {
                    g2d.drawImage(charBlur[col], hPos + (hSpacer * 2), firstLine + (vSpacer * i), this);
                    //normal
                    if (bothArentSelected() && hIndex == 3 && vIndex == i && LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().allPlayers[((vIndex * 3) + hIndex) - 1] == 0)//clear
                    {
                        g2d.setComposite(makeComposite(opacChar));
                        g2d.drawImage(charNorm[col], hPos + (hSpacer * 2), firstLine + (vSpacer * i), this);
                        charPrevLoicIndex = col;
                        g2d.setComposite(makeComposite(1.0f));
                    }
                    col++;
                }
                lastRow = i;
            }

            int rem = charBlur.length % 3;

            if (rem == 1) {
                g2d.drawImage(charBlur[col], hPos, firstLine, this);
                //normal
                if (bothArentSelected() && hIndex == 1 && vIndex == lastRow && LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().allPlayers[((vIndex * 3) + hIndex) - 1] == 0)//clear
                {
                    g2d.setComposite(makeComposite(opacChar));
                    g2d.drawImage(charNorm[col], hPos, firstLine, this);
                    charPrevLoicIndex = col;
                    g2d.setComposite(makeComposite(1.0f));
                }
            } else if (rem == 2) {
                {
                    g2d.drawImage(charBlur[col], hPos, firstLine, this);
                    //normal
                    if (bothArentSelected() && hIndex == 1 && vIndex == lastRow && LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().allPlayers[((vIndex * 3) + hIndex) - 1] == 0)//clear
                    {
                        g2d.setComposite(makeComposite(opacChar));
                        g2d.drawImage(charNorm[col], hPos, firstLine, this);
                        charPrevLoicIndex = col;
                        g2d.setComposite(makeComposite(1.0f));
                    }
                    col++;
                }

                {
                    g2d.drawImage(charBlur[col + 1], hPos + hSpacer, firstLine + vSpacer, this);
                    //normal
                    if (bothArentSelected() && hIndex == 2 && vIndex == lastRow && LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().allPlayers[((vIndex * 3) + hIndex) - 1] == 0)//clear
                    {
                        g2d.setComposite(makeComposite(opacChar));
                        g2d.drawImage(charNorm[col + 1], hPos + hSpacer, firstLine + vSpacer, this);
                        charPrevLoicIndex = col + 1;
                        g2d.setComposite(makeComposite(1.0f));
                    }
                    col++;
                }
            }

            if (LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().proceed1 && LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().proceed2) {
                g2d.drawImage(fight, 0, 0, this);
                g2d.setFont(bigFont);
                g2d.setColor(Color.WHITE);
                g2d.drawString("<< " + lang.getLine(146) + " >>", (852 - g2d.getFontMetrics(bigFont).stringWidth("<< " + lang.getLine(146) + " >>")) / 2, 360);
                g2d.drawString("<< " + lang.getLine(147) + " >>", (852 - g2d.getFontMetrics(bigFont).stringWidth("<< " + lang.getLine(147) + " >>")) / 2, 390);
            }
        }
        g2d.setFont(normalFont);
        g2d.setColor(Color.white);
        if (LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().proceed1 == false) {
            g2d.drawImage(charDescPic, 0, 0, this);
            g2d.drawString(statsChar[charPrevLoicIndex], 4 + x, 18);
        }
        if (LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().proceed1 && LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().proceed2 == false) {
            g2d.drawImage(oppDescPic, 452, 450, this);
            g2d.drawString(statsChar[charPrevLoicIndex], 852 - g2d.getFontMetrics(normalFont).stringWidth(statsChar[charPrevLoicIndex]) + x, 468);
        }
        g2d.drawImage(p1, 0, 180, this);
        g2d.drawImage(p2, 812, 180, this);

        if (x < 0) {
            x = x + 2;
        }

        over1.overlay(g2d, this);

        g.drawImage(volatileImg, 0, 0, this);
    }

    private void loadCaps() {
        bigFont = LoginScreen.getLoginScreen().getMyFont(LoginScreen.extraTxtSize);
        normalFont = LoginScreen.getLoginScreen().getMyFont(LoginScreen.normalTxtSize);
        oppDescPic = pix.loadImageFromToolkitNoScale("images/charInfoO.png");
        charDescPic = pix.loadImageFromToolkitNoScale("images/charInfoC.png");

        charNorm[0] = pix.loadImageFromToolkitNoScale("images/Raila/cap.png");
        charNorm[1] = pix.loadImageFromToolkitNoScale("images/Subiya/cap.png");
        charNorm[2] = pix.loadImageFromToolkitNoScale("images/Lynx/cap.png");
        charNorm[3] = pix.loadImageFromToolkitNoScale("images/Aisha/cap.png");
        charNorm[4] = pix.loadImageFromToolkitNoScale("images/Ravage/cap.png");
        charNorm[5] = pix.loadImageFromToolkitNoScale("images/Ade/cap.png");
        charNorm[6] = pix.loadImageFromToolkitNoScale("images/Jonah/cap.png");
        charNorm[7] = pix.loadImageFromToolkitNoScale("images/Adam/cap.png");
        charNorm[8] = pix.loadImageFromToolkitNoScale("images/NOVA Adam/cap.png");
        charNorm[9] = pix.loadImageFromToolkitNoScale("images/Azaria/cap.png");
        charNorm[10] = pix.loadImageFromToolkitNoScale("images/Sorrowe/cap.png");
        charNorm[11] = pix.loadImageFromToolkitNoScale("images/The Thing/cap.png");

        charBlur[0] = pix.loadImageFromToolkitNoScale("images/Raila/capB.png");
        charBlur[1] = pix.loadImageFromToolkitNoScale("images/Subiya/capB.png");
        charBlur[2] = pix.loadImageFromToolkitNoScale("images/Lynx/capB.png");
        charBlur[3] = pix.loadImageFromToolkitNoScale("images/Aisha/capB.png");
        charBlur[4] = pix.loadImageFromToolkitNoScale("images/Ravage/capB.png");
        charBlur[5] = pix.loadImageFromToolkitNoScale("images/Ade/capB.png");
        charBlur[6] = pix.loadImageFromToolkitNoScale("images/Jonah/capB.png");
        charBlur[7] = pix.loadImageFromToolkitNoScale("images/Adam/capB.png");
        charBlur[8] = pix.loadImageFromToolkitNoScale("images/NOVA Adam/capB.png");
        charBlur[9] = pix.loadImageFromToolkitNoScale("images/Azaria/capB.png");
        charBlur[10] = pix.loadImageFromToolkitNoScale("images/Sorrowe/capB.png");
        charBlur[11] = pix.loadImageFromToolkitNoScale("images/The Thing/capB.png");

        charNames[0] = pix.loadImageFromToolkitNoScale("images/Raila/name.png");
        charNames[1] = pix.loadImageFromToolkitNoScale("images/Subiya/name.png");
        charNames[2] = pix.loadImageFromToolkitNoScale("images/Lynx/name.png");
        charNames[3] = pix.loadImageFromToolkitNoScale("images/Aisha/name.png");
        charNames[4] = pix.loadImageFromToolkitNoScale("images/Ravage/name.png");
        charNames[5] = pix.loadImageFromToolkitNoScale("images/Ade/name.png");
        charNames[6] = pix.loadImageFromToolkitNoScale("images/Jonah/name.png");
        charNames[7] = pix.loadImageFromToolkitNoScale("images/Adam/name.png");
        charNames[8] = pix.loadImageFromToolkitNoScale("images/NOVA Adam/name.png");
        charNames[9] = pix.loadImageFromToolkitNoScale("images/Azaria/name.png");
        charNames[10] = pix.loadImageFromToolkitNoScale("images/Sorrowe/name.png");
        charNames[11] = pix.loadImageFromToolkitNoScale("images/The Thing/name.png");


        charBack = pix.loadImageFromToolkitNoScale("images/selChar.png");
        oppBack = pix.loadImageFromToolkitNoScale("images/selOpp.png");
        charHold = pix.loadImageFromToolkitNoScale("images/charHold.png");

        Image[] tmp = SpecialDrawModeCanvas.getPics();
        bg3 = tmp[0];
        fg1 = tmp[1];
        fg2 = tmp[2];
        fg3 = tmp[3];

        p1 = pix.loadImageFromToolkitNoScale("images/player1.png");
        p2 = pix.loadImageFromToolkitNoScale("images/player2.png");

        fight = pix.loadImageFromToolkitNoScale("images/fight.png");

        charDesc[0] = Raila.class.getName();


        for (int vd = 0; vd < charPrevLox.length; vd++) {
            charPrev[vd] = pix.loadImageFromToolkitNoScale(charPrevLox[vd]);
        }

        for (int c = 0; c < oppPrevLox.length; c++) {
            oppPrev[c] = pix.loadImageFromToolkitNoScale(oppPrevLox[c]);
        }
    }

    /**
     * Move up
     */
    public void upMove() {
        if (vIndex > 0) {
            vIndex = vIndex - 1;
        } else {
            vIndex = verticalRows;
        }

        capAnim();
    }

    /**
     * Move down
     */
    public void downMove() {
        if (vIndex < verticalRows) {
            vIndex = vIndex + 1;
        } else {
            vIndex = 0;
        }

        capAnim();
    }

    /**
     * Move right
     */
    public void rightMove() {
        if (hIndex < horizColumns) {
            hIndex = hIndex + 1;
        } else {
            hIndex = 1;
        }

        capAnim();
    }

    /**
     * Move left
     */
    public void leftMove() {
        if (hIndex > 1) {
            hIndex = hIndex - 1;
        } else {
            hIndex = horizColumns;
        }

        capAnim();
    }

    /**
     * Horizontal index
     *
     * @return hIndex
     */
    public int getHindex() {
        return hIndex;
    }

    /**
     * Set horizontal index
     */
    public void setHindex(int value) {
        hIndex = value;
    }

    /**
     * Vertical index
     *
     * @return vIndex
     */
    public int getVindex() {
        return vIndex;
    }

    /**
     * Set vertical index
     */
    public void setVindex(int value) {
        vIndex = value;
    }

    /**
     * Animates captions
     */
    public void capAnim() {
        x = -100;
        p1Opac = 0.0f;
        opacChar = 0.0f;
    }

    /**
     * Checks if within number of Characters
     */
    public boolean well() {
        boolean ans = false;
        int whichChar = ((vIndex * 3) + hIndex) - 1;
        if (whichChar <= numOfCharacters) {
            ans = true;
            //System.out.println("Dude: "+whichChar);
        }

        return ans;
    }

    /**
     * Gets screenshot
     */
    public void captureScreenShot() {
        try {
            BufferedImage dudeC = volatileImg.getSnapshot();

            //image = getGaussianBlurFilter(10, true).filter(image, null);
            //image = getGaussianBlurFilter(10, false).filter(image, null);

            File file;

            //Save the screenshot as a png

            //file = new File(generateUID() + ".png");
            if (!new File(System.getProperty("user.home") + File.separator + ".config" + File.separator + "scndgen" + File.separator + "screenshots").exists()) {
                new File(System.getProperty("user.home") + File.separator + ".config" + File.separator + "scndgen" + File.separator + "screenshots").mkdirs();
            }
            file = new File(System.getProperty("user.home") + File.separator + ".config" + File.separator + "scndgen" + File.separator + "screenshots" + File.separator + DrawGame.generateUID() + ".png");
            ImageIO.write(dudeC, "png", file);
            systemNotice(lang.getLine(170));
        } catch (Exception e) {
            System.out.println(e.getMessage());
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
}

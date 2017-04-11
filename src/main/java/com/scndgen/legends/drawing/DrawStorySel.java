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
import com.scndgen.legends.engine.JenesisImage;
import com.scndgen.legends.engine.JenesisLanguage;
import com.scndgen.legends.engine.JenesisGlassPane;
import com.scndgen.legends.engine.JenesisMenu;
import com.scndgen.legends.menus.MenuStageSelect;
import com.scndgen.legends.StoryMode;
import com.scndgen.legends.threads.ThreadMP3;
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
public abstract class DrawStorySel extends JenesisMenu {

    public int lastRow, currentSlot = 0, xCordCloud = 0, xCordCloud2 = 0, charYcap = 0, charXcap = 0, storySelIndex = 99, hIndex = 1, x = 0, y = 0, vIndex = 0, vSpacer = 52, hSpacer = 92, hPos = 299, firstLine = 105;
    public String loadTxt = "";
    public int mode, numberOfstorys,
            horizColumns = 3, verticalRows;
    protected boolean[] hiddenStage;
    protected boolean loadingNow;
    private GraphicsEnvironment ge;
    private JenesisLanguage lang;
    private GraphicsConfiguration gc;
    private boolean alreadyRunnging = false, runNew = true;
    private VolatileImage volatileImg;
    private Graphics2D g2d;
    private Font headerFont, normalFont;
    private float opacity = 1.0f;
    private int valCode, oldId = -1;
    private Image charBack, loading;
    private JenesisImage pix;
    private Image[] storyCap, storyCapUn, storyCapBlur;
    private Image storyPrev;
    private ThreadMP3 menuSound;
    private RenderingHints renderHints;

    /**
     * Teh constructorz XD
     */
    public DrawStorySel() {
        lang = LoginScreen.getLoginScreen().getLangInst();
        numberOfstorys = 12;
        renderHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //anti aliasing, kill jaggies
        headerFont = LoginScreen.getLoginScreen().getMyFont(LoginScreen.extraTxtSize);
        normalFont = LoginScreen.getLoginScreen().getMyFont(LoginScreen.normalTxtSize);
        menuSound = new ThreadMP3("audio/menu-select.mp3", true);
        verticalRows = (numberOfstorys / 3);
        storyCapBlur = new Image[numberOfstorys];
        storyCap = new Image[numberOfstorys];
        storyCapUn = new Image[numberOfstorys];
        hiddenStage = new boolean[numberOfstorys];
        mode = LoginScreen.getLoginScreen().stage;
        for (int u = 0; u < hiddenStage.length; u++) {
            if (u <= mode)//if you are higher stage enable
            {
                hiddenStage[u] = true;
            } else {
                hiddenStage[u] = false;
            }
        }


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
        //Check if Image is valid
        valCode = volatileImg.validate(gc);
        //if not create new vi, only affects windows apparently
        if (valCode == VolatileImage.IMAGE_INCOMPATIBLE) {
            createBackBuffer();
        }

        if (loadingNow) {
            g2d.setColor(Color.BLACK);

            g2d.drawImage(storyPrev, charXcap + x, charYcap, this);
            g2d.setComposite(makeComposite(0.7f));
            g2d.fillRect(0, 0, 852, 480);
            g2d.setComposite(makeComposite(1.0f));


            g2d.setComposite(makeComposite(0.5f));
            g2d.fillRect(200, 0, 452, 480);
            g2d.setComposite(makeComposite(1.0f));

            g2d.drawImage(loading, 316, 183, this); //yCord = 286 - icoHeight
            g2d.setColor(Color.WHITE);
            //g2d.drawString(lang.getLine(165), (852 - g2d.getFontMetrics().stringWidth(lang.getLine(165))) / 2, 200);

        } else if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanClient) == false) {
            g2d.setColor(Color.BLACK);
            g2d.fillRect(0, 0, 852, 480);

            g2d.drawImage(storyPrev, charXcap + x, charYcap, this);

            lastRow = 0;
            //all char caps in this segment
            {
                g2d.setComposite(makeComposite(0.7f));
                g2d.fillRect(0, 0, 852, 480);
                g2d.setComposite(makeComposite(1.0f));


                g2d.setComposite(makeComposite(0.5f));
                g2d.fillRect(200, 0, 452, 480);
                g2d.setComposite(makeComposite(1.0f));

                int col = 0;
                for (int i = 0; i < (storyCap.length / 3); i++) {
                    {
                        g2d.drawImage(storyCap[col], hPos, firstLine + (vSpacer * i), this);
                        if (!hiddenStage[col]) {
                            g2d.drawImage(storyCapBlur[col], hPos, firstLine + (vSpacer * i), this);
                        }
                        col++;
                    }

                    {
                        g2d.drawImage(storyCap[col], hPos + hSpacer, firstLine + (vSpacer * i), this);
                        if (!hiddenStage[col]) {
                            g2d.drawImage(storyCapBlur[col], hPos + hSpacer, firstLine + (vSpacer * i), this);
                        }
                        col++;
                    }

                    {
                        g2d.drawImage(storyCap[col], hPos + (hSpacer * 2), firstLine + (vSpacer * i), this);
                        if (!hiddenStage[col]) {
                            g2d.drawImage(storyCapBlur[col], hPos + (hSpacer * 2), firstLine + (vSpacer * i), this);
                        }
                        col++;
                    }
                    lastRow = i;
                }

                int rem = storyCap.length % 3;

                if (rem == 1) {
                    g2d.drawImage(storyCap[col], hPos, firstLine + (vSpacer * (lastRow + 1)), this);
                    if (!hiddenStage[col]) {
                        g2d.drawImage(storyCapBlur[col], hPos, firstLine + (vSpacer * (lastRow + 1)), this);
                    }
                } else if (rem == 2) {
                    {
                        g2d.drawImage(storyCap[col], hPos, firstLine + (vSpacer * (lastRow + 1)), this);
                        if (!hiddenStage[col]) {
                            g2d.drawImage(storyCapBlur[col], hPos, firstLine + (vSpacer * (lastRow + 1)), this);
                        }
                        col++;
                    }

                    {
                        g2d.drawImage(storyCap[col], hPos + hSpacer, firstLine + (vSpacer * (lastRow + 1)), this);
                        if (!hiddenStage[col]) {
                            g2d.drawImage(storyCapBlur[col], hPos + hSpacer, firstLine + (vSpacer * (lastRow + 1)), this);
                        }
                        col++;
                    }
                }

                g2d.setColor(Color.WHITE);
                g2d.setFont(headerFont);
                g2d.drawString(lang.getLine(307), (852 - g2d.getFontMetrics().stringWidth(lang.getLine(307))) / 2, 80);
                g2d.setFont(normalFont);
                g2d.drawString(lang.getLine(368), (852 - g2d.getFontMetrics().stringWidth(lang.getLine(368))) / 2, 380);
                showstoryName(storySelIndex);
                if (well() && hiddenStage[storySelIndex]) {
                    {
                        if (opacity < 0.98f) {
                            opacity = opacity + 0.02f;
                        }
                        g2d.setComposite(makeComposite(opacity));
                        g2d.drawImage(storyCapUn[storySelIndex], (hPos - hSpacer) + (hSpacer * hIndex), firstLine + (vSpacer * vIndex), this);
                        g2d.setComposite(makeComposite(1.0f));
                        g2d.drawImage(charBack, (hPos - hSpacer) + (hSpacer * hIndex), firstLine + (vSpacer * vIndex), this);
                    }
                }

            }
        }
        over1.overlay(g2d, this);
        g.drawImage(volatileImg, 0, 0, this);
    }

    private void showstoryName(int id) {
        if (id != oldId) {
            systemNotice("Scene " + (id + 1));
            oldId = id;
        }
    }

    private void loadCaps() {
        MenuStageSelect.selectedStage = false;
        try {
            for (int i = 0; i < numberOfstorys; i++) {
                storyCap[i] = pix.loadImageFromToolkitNoScale("images/Story/locked/x" + (i + 1) + ".png");
            }

            for (int i = 0; i < numberOfstorys; i++) {
                storyCapUn[i] = pix.loadImageFromToolkitNoScale("images/Story/x" + (i + 1) + ".png");
            }

            for (int i = 0; i < numberOfstorys; i++) {
                storyCapBlur[i] = pix.loadImageFromToolkitNoScale("images/Story/blur/t_" + i + ".png");
            }
        } catch (Exception e) {
            System.err.println(e);
        }

        //charBack = pix.loadImageFromToolkitNoScale("images/selstory.png");
        loading = pix.loadImageFromToolkitNoScale("images/appletprogress.gif");
        charBack = pix.loadImageFromToolkitNoScale("images/Story/frame.png");
        int x = (int) (Math.random() * 4);
        switch (x) {
            case 0: {
                storyPrev = pix.loadBufferedImageFromToolkit("images/Story/blur/s4.png");
            }
            break;

            case 1: {
                storyPrev = pix.loadBufferedImageFromToolkit("images/Story/blur/s5.png");
            }
            break;

            case 2: {
                storyPrev = pix.loadBufferedImageFromToolkit("images/Story/blur/s6.png");

            }
            break;

            default: {
                storyPrev = pix.loadBufferedImageFromToolkit("images/Story/blur/s6.png");

            }
            break;
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
        if (vIndex < verticalRows - 1) {
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
    public final int getHindex() {
        return hIndex;
    }

    /**
     * Set horizontal index
     */
    public final void setHindex(int value) {
        hIndex = value;
    }

    /**
     * Vertical index
     *
     * @return vIndex
     */
    public final int getVindex() {
        return vIndex;
    }

    /**
     * Set vertical index
     */
    public final void setVindex(int value) {
        vIndex = value;
    }

    /**
     * Animates captions
     */
    public void capAnim() {
        opacity = 0.0f;
    }

    public void animCloud() {
    }

    /**
     * Checks if within number of Characters
     */
    public boolean well() {
        boolean ans = false;
        int xV = (vIndex * 3) + hIndex;
        if (xV <= numberOfstorys) {
            ans = true;
            storySelIndex = xV - 1;
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
     * Sets the story
     *
     * @param where
     */
    public void setstory(int where) {
        storySelIndex = where;
    }


    public void prepareStory() {
        for (int i = 0; i <= StoryMode.max; i++) {
            if (storySelIndex == i) {
                startGame(i);
                menuSound.play();
                break;
            }
        }
    }

    public abstract void startGame(int mode);


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

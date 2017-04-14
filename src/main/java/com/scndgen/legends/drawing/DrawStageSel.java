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
import com.scndgen.legends.engine.JenesisLanguage;
import com.scndgen.legends.menus.RenderStageSelect;
import com.scndgen.legends.windows.WindowMain;
import io.github.subiyacryolite.enginev1.JenesisGlassPane;
import io.github.subiyacryolite.enginev1.JenesisImageLoader;
import io.github.subiyacryolite.enginev1.JenesisMode;

import javax.swing.*;
import java.awt.*;

/**
 * @author: Ifunga Ndana
 * @class: drawPrevChar
 * This class creates a graphical preview of the character and opponent
 */
public abstract class DrawStageSel extends JenesisMode {

    public static int lastRow, currentSlot = 0, xCordCloud = 0, xCordCloud2 = 0, charYcap = 0, charXcap = 0, stageSelIndex = 0, hIndex = 1, x = 0, y = 0, vIndex = 0, vSpacer = 52, hSpacer = 92, hPos = 288, firstLine = 105;
    public static String loadTxt = "";
    public static int mode = 1, numberOfStages;
    private static float opacity = 1.0f;
    private static String[] stagePrevLox;
    public int horizColumns = 3, verticalRows;
    private String[] stageNameStr;
    private int oldId = -1;
    private Image charBack, loading;
    private JenesisImageLoader pix;
    private Image[] stageCap = new Image[numberOfStages];
    private Image[] stagePrev = new Image[numberOfStages];
    private Font normalFont, bigFont;
    private RenderingHints renderHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //anti aliasing, kill jaggies

    /**
     * Teh constructorz XD
     */
    public DrawStageSel() {
        JenesisLanguage lang = JenesisLanguage.getInstance();
        stagePrevLox = new String[]{"bgBG1", "bgBG2", "bgBG3", "bgBG4", "bgBG5", "bgBG6", "bgBG7", "bgBG8", "bgBG9", "bgBG10", "bgBG100", "bgBG11", "bgBG13", "bgBG14", "bgBG15", "bgBG12"};
        stageNameStr = new String[]{lang.getLine(152),
                lang.getLine(153),
                lang.getLine(154),
                lang.getLine(155),
                lang.getLine(156),
                lang.getLine(157),
                lang.getLine(158),
                lang.getLine(159),
                lang.getLine(160),
                lang.getLine(161),
                lang.getLine(162),
                lang.getLine(163),
                lang.getLine(369),
                lang.getLine(370),
                lang.getLine(371),
                lang.getLine(164)};
        numberOfStages = stageNameStr.length;

        verticalRows = (numberOfStages / 3);
        stageCap = new Image[numberOfStages];
        stagePrev = new Image[numberOfStages];

        pix = new JenesisImageLoader();
        normalFont = LoginScreen.getInstance().getMyFont(LoginScreen.normalTxtSize);
        loadCaps();
        setBorder(BorderFactory.createEmptyBorder());
    }

    /**
     * When both playes are selected, this prevents movement.
     *
     * @return false if both Character have been selected, true if only one is selected
     */
    public static boolean bothArentSelected() {
        boolean answer = true;

        if (RenderCharacterSelectionScreen.getInstance().characterSelected && RenderCharacterSelectionScreen.getInstance().opponentSelected) {
            answer = false;
        }

        return answer;
    }

    /**
     * @return stage
     */
    public static String getStage() {
        return stagePrevLox[stageSelIndex];
    }

    /**
     * Sets the stage
     *
     * @param where
     */
    public static void setStage(int where) {
        stageSelIndex = where;
    }

    @Override
    public void paintComponent(Graphics g) {
        createBackBuffer();
        if (RenderStageSelect.selectedStage) {

            g2d.setColor(Color.BLACK);

            g2d.drawImage(stagePrev[stageSelIndex], charXcap + x, charYcap, this);
            g2d.setComposite(makeComposite(0.7f));
            g2d.fillRect(0, 0, 852, 480);
            g2d.setComposite(makeComposite(1.0f));


            g2d.setComposite(makeComposite(0.5f));
            g2d.fillRect(200, 0, 452, 480);
            g2d.setComposite(makeComposite(1.0f));

            g2d.drawImage(loading, 316, 183, this); //yCord = 286 - icoHeight
            g2d.setColor(Color.WHITE);
            g2d.drawString(JenesisLanguage.getInstance().getLine(165), (852 - g2d.getFontMetrics().stringWidth(JenesisLanguage.getInstance().getLine(165))) / 2, 200);
        } else if (LoginScreen.getInstance().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanClient) && RenderStageSelect.selectedStage == false) {
            g2d.setFont(normalFont);
            g2d.setColor(Color.BLACK);
            g2d.fillRect(0, 0, 852, 480);
            g2d.setColor(Color.WHITE);
            g2d.drawString(">> " + JenesisLanguage.getInstance().getLine(166) + " <<", (852 - g2d.getFontMetrics(bigFont).stringWidth(">> " + JenesisLanguage.getInstance().getLine(166) + " <<")) / 2, 300);
        } else if (LoginScreen.getInstance().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanClient) == false) {
            g2d.setFont(normalFont);
            g2d.setColor(Color.BLACK);
            g2d.fillRect(0, 0, 852, 480);

            //character preview DYNAMIC change
            if (opacity < 0.98f) {
                opacity = opacity + 0.02f;
            }
            g2d.setComposite(makeComposite(opacity));
            g2d.drawImage(stagePrev[stageSelIndex], charXcap + x, charYcap, this);
            g2d.setComposite(makeComposite(1.0f));

            lastRow = 0;
            //all char caps in this segment
            {
                g2d.setComposite(makeComposite(5 * 0.1F));
                g2d.fillRoundRect(283, 0, 285, 480, 30, 30);
                g2d.setComposite(makeComposite(10 * 0.1F));

                int col = 0;
                for (int i = 0; i < (stageCap.length / 3); i++) {
                    {
                        g2d.drawImage(stageCap[col], hPos, firstLine + (vSpacer * i), this);
                        col++;
                    }

                    {
                        g2d.drawImage(stageCap[col], hPos + hSpacer, firstLine + (vSpacer * i), this);
                        col++;
                    }

                    {
                        g2d.drawImage(stageCap[col], hPos + (hSpacer * 2), firstLine + (vSpacer * i), this);
                        col++;
                    }
                    lastRow = i;
                }

                int rem = stageCap.length % 3;

                if (rem == 1) {
                    //System.out.println("Remainder 11");
                    g2d.drawImage(stageCap[col], hPos, firstLine + (vSpacer * (lastRow + 1)), this);
                } else if (rem == 2) {
                    g2d.drawImage(stageCap[col], hPos, firstLine + (vSpacer * (lastRow + 1)), this);
                    g2d.drawImage(stageCap[col + 1], hPos + hSpacer, firstLine + (vSpacer * (lastRow + 1)), this);
                }

                if (well()) {
                    {
                        showStageName(stageSelIndex);
                        g2d.drawImage(charBack, (hPos - hSpacer) + (hSpacer * hIndex), firstLine + (vSpacer * vIndex), this);
                    }
                }
            }
        }
        JenesisGlassPane.getInstance().overlay(g2d, this);
        g.drawImage(volatileImg, 0, 0, this);
    }

    private void showStageName(int id) {
        if (id != oldId) {
            capAnim();
            systemNotice(stageNameStr[id]);
            oldId = id;
            if (oldId == stageNameStr.length - 1) {
                //random stage
                mode = 0;
            } else {
                //normal
                mode = 1;
            }
        }
    }

    private void loadCaps() {
        RenderStageSelect.selectedStage = false;
        try {
            for (int i = 0; i < stagePrevLox.length; i++) {
                stageCap[i] = pix.loadImageFromToolkitNoScale("images/t_" + stagePrevLox[i] + ".png");
            }
        } catch (Exception e) {
            System.err.println(e);
        }

        charBack = pix.loadImageFromToolkitNoScale("images/selStage.png");
        loading = pix.loadImageFromToolkitNoScale("images/appletprogress.gif");


        for (int vd = 0; vd < stagePrevLox.length; vd++) {
            stagePrev[vd] = pix.loadImageFromToolkitNoScale("images/prev/" + stagePrevLox[vd] + ".jpg");
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
        opacity = 0.0f;
    }

    public void animCloud() {
    }

    /**
     * Checks if within number of Character
     */
    public boolean well() {
        boolean ans = false;
        int xV = (vIndex * 3) + hIndex;
        if (xV <= numberOfStages) {
            ans = true;
            stageSelIndex = xV - 1;
        }

        return ans;
    }
}

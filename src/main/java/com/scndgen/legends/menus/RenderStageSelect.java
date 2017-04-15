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
package com.scndgen.legends.menus;

import com.scndgen.legends.Language;
import com.scndgen.legends.LoginScreen;
import com.scndgen.legends.drawing.StageSelect;
import com.scndgen.legends.enums.StageSelection;
import com.scndgen.legends.windows.WindowMain;
import io.github.subiyacryolite.enginev1.JenesisGlassPane;
import io.github.subiyacryolite.enginev1.JenesisImageLoader;
import io.github.subiyacryolite.enginev1.JenesisRender;

import javax.swing.*;
import java.awt.*;

public class RenderStageSelect extends StageSelect implements JenesisRender {

    //♩♪♬♫
    public static String[] trax = {"\"The King is Dead\" by \"Mattias Westlund\" from \"The Battle for Wesnoth OST\"", //0
            "\"vengeful\" by \"Jeremy Nicoll\" from \"The Battle for Wesnoth OST\"", //1
            "\"The City Falls\" by \"Doug Kaufman\" from \"The Battle for Wesnoth OST\"", //2
            "\"Suspense\" by \"Ryan Reilly\" from \"The Battle for Wesnoth OST\"", //3
            "\"Elvish theme\" by \"Doug Kaufman\" from \"The Battle for Wesnoth OST\"", //4
            "\"Breaking the Chains\" by \"Mattias Westlund\" from \"The Battle for Wesnoth OST\"", //5
            "\"Battle Music\" by \"Aleksi Aubry-Carlson\" from \"The Battle for Wesnoth OST\""}; //6
    public static String[] musFiles = {"Mattias Westlund - The King is Dead",
            "Jeremy Nicoll - Vengeful Pursuit",
            "Doug Kaufman - The City Falls",
            "Ryan Reilly - Suspense",
            "Doug Kaufman - Elvish theme",
            "Mattias Westlund - Breaking the Chains",
            "Aleksi Aubry-Carlson - Battle Music"};
    public static String charPrevLoc = "images/trans.png", oppPrevLoc = "images/trans.png";
    private Image charBack, loading;
    public int horizColumns = 3, verticalRows;
    private JenesisImageLoader pix;
    private Image[] stageCap = new Image[numberOfStages];
    private Image[] stagePrev = new Image[numberOfStages];
    private Font normalFont, bigFont;
    private String[] stageNameStr;
    private int oldId = -1;
    private static RenderStageSelect instance;

    public static synchronized RenderStageSelect getInstance() {
        if (instance == null)
            instance = new RenderStageSelect();
        return instance;
    }

    public RenderStageSelect() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.black, 1));
        Language lang = Language.getInstance();
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
    }

    @Override
    public void loadAssets() {
        if (!loadAssets) return;
        pix = new JenesisImageLoader();
        normalFont = LoginScreen.getInstance().getMyFont(LoginScreen.normalTxtSize);
        loadCaps();
        loadAssets = false;
    }

    @Override
    public void cleanAssets() {
        loadAssets = true;
    }

    @Override
    public void paintComponent(Graphics g) {
        createBackBuffer();
        loadAssets();
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
            g2d.drawString(Language.getInstance().getLine(165), (852 - g2d.getFontMetrics().stringWidth(Language.getInstance().getLine(165))) / 2, 200);
        } else if (LoginScreen.getInstance().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanClient) && RenderStageSelect.selectedStage == false) {
            g2d.setFont(normalFont);
            g2d.setColor(Color.BLACK);
            g2d.fillRect(0, 0, 852, 480);
            g2d.setColor(Color.WHITE);
            g2d.drawString(">> " + Language.getInstance().getLine(166) + " <<", (852 - g2d.getFontMetrics(bigFont).stringWidth(">> " + Language.getInstance().getLine(166) + " <<")) / 2, 300);
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
            {
                g2d.setComposite(makeComposite(5 * 0.1F));
                g2d.fillRoundRect(283, 0, 285, 480, 30, 30);
                g2d.setComposite(makeComposite(10 * 0.1F));

                int col = 0;
                for (int i = 0; i < (stageCap.length / 3); i++) {
                    g2d.drawImage(stageCap[col], hPos, firstLine + (vSpacer * i), this);
                    col++;
                    g2d.drawImage(stageCap[col], hPos + hSpacer, firstLine + (vSpacer * i), this);
                    col++;
                    g2d.drawImage(stageCap[col], hPos + (hSpacer * 2), firstLine + (vSpacer * i), this);
                    col++;
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


    private void loadCaps() {
        RenderStageSelect.selectedStage = false;
        try {
            for (int i = 0; i < stagePrevLox.length; i++) {
                stageCap[i] = pix.loadImage("images/t_" + stagePrevLox[i] + ".png");
            }
        } catch (Exception e) {
            System.err.println(e);
        }

        charBack = pix.loadImage("images/selStage.png");
        loading = pix.loadImage("images/appletprogress.gif");


        for (int vd = 0; vd < stagePrevLox.length; vd++) {
            stagePrev[vd] = pix.loadImage("images/prev/" + stagePrevLox[vd] + ".jpg");
        }
    }

    private void showStageName(int id) {
        if (id != oldId) {
            capAnim();
            systemNotice(stageNameStr[id]);
            oldId = id;
            if (oldId == stageNameStr.length - 1) {
                mode = StageSelection.RANDOM;
            } else {
                mode = StageSelection.NORMAL;
            }
        }
    }

    public static String getTrack() {
        return musFiles[musicInt];
    }

    /**
     * Move up
     */
    public void moveUp() {
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
    public void moveDown() {
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
    public void moveRight() {
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
    public void moveLeft() {
        if (hIndex > 1) {
            hIndex = hIndex - 1;
        } else {
            hIndex = horizColumns;
        }
        capAnim();
    }

    public void newInstance() {
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
     * Gets the number of columns in the character select screen
     *
     * @return number of columns
     */
    public int getNumberOfCharColumns() {
        return horizColumns;
    }

    /**
     * Gets the char caption spacer
     *
     * @return spacer
     */
    public int getCharHSpacer() {
        return vSpacer;
    }

    /**
     * Gets the char caption spacer
     *
     * @return spacer
     */
    public int getCharVSpacer() {
        return hSpacer;
    }

    /**
     * Get starting x coordinate
     *
     * @return starting x coordinate
     */
    public int getStartX() {
        return hPos;
    }

    /**
     * Returns the starting Y coordinate
     *
     * @return starting y
     */
    public int getStartY() {
        return firstLine;
    }

    /**
     * Get number of char rows
     *
     * @return number of rows
     */
    public int getCharRows() {
        return verticalRows;
    }
}

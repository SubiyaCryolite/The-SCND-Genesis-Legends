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
package com.scndgen.legends.render;

import com.scndgen.legends.Language;
import com.scndgen.legends.LoginScreen;
import com.scndgen.legends.enums.Stage;
import com.scndgen.legends.enums.StageSelection;
import com.scndgen.legends.scene.StageSelect;
import com.scndgen.legends.windows.MainWindow;
import io.github.subiyacryolite.enginev1.JenesisGlassPane;
import io.github.subiyacryolite.enginev1.JenesisImageLoader;
import io.github.subiyacryolite.enginev1.JenesisRender;

import javax.swing.*;
import java.awt.*;
import java.util.Hashtable;

public class RenderStageSelect extends StageSelect implements JenesisRender {

    private static RenderStageSelect instance;
    //♩♪♬♫
    private String[] amnientMusicMetaData = {"\"The King is Dead\" by \"Mattias Westlund\" from \"The Battle for Wesnoth OST\"", //0
            "\"vengeful\" by \"Jeremy Nicoll\" from \"The Battle for Wesnoth OST\"", //1
            "\"The City Falls\" by \"Doug Kaufman\" from \"The Battle for Wesnoth OST\"", //2
            "\"Suspense\" by \"Ryan Reilly\" from \"The Battle for Wesnoth OST\"", //3
            "\"Elvish theme\" by \"Doug Kaufman\" from \"The Battle for Wesnoth OST\"", //4
            "\"Breaking the Chains\" by \"Mattias Westlund\" from \"The Battle for Wesnoth OST\"", //5
            "\"Battle Music\" by \"Aleksi Aubry-Carlson\" from \"The Battle for Wesnoth OST\""}; //6
    private String[] ambientMusic = {"Mattias Westlund - The King is Dead",
            "Jeremy Nicoll - Vengeful Pursuit",
            "Doug Kaufman - The City Falls",
            "Ryan Reilly - Suspense",
            "Doug Kaufman - Elvish theme",
            "Mattias Westlund - Breaking the Chains",
            "Aleksi Aubry-Carlson - Battle Music"};
    private Image charBack, loading;
    private int horizColumns = 3, verticalRows;
    private JenesisImageLoader imageLoader;
    private Image[] stageCap = new Image[numberOfStages];
    private Image[] stagePrev = new Image[numberOfStages];
    private Font normalFont, bigFont;
    private final Hashtable<Stage, String> stageNameStr;
    private int hoveredStageIndex = -1;

    public RenderStageSelect() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.black, 1));
        Language lang = Language.getInstance();
        stageNameStr = new Hashtable<>();
        stageNameStr.put(Stage.IBEX_HILL, "");
        stageNameStr.put(Stage.CHELSTON_CITY_DOCKS, lang.getLine(153));
        stageNameStr.put(Stage.DESERT_RUINS, lang.getLine(154));
        stageNameStr.put(Stage.CHELSTON_CITY_STREETS, lang.getLine(155));
        stageNameStr.put(Stage.IBEX_HILL_NIGHT, lang.getLine(156));
        stageNameStr.put(Stage.SCORCHED_RUINS, lang.getLine(157));
        stageNameStr.put(Stage.FROZEN_WILDERNESS, lang.getLine(158));
        stageNameStr.put(Stage.DISTANT_ISLE, lang.getLine(162));
        stageNameStr.put(Stage.HIDDEN_CAVE, lang.getLine(159));
        stageNameStr.put(Stage.AFRICAN_VILLAGE, lang.getLine(160));
        stageNameStr.put(Stage.APOCALYPTO, lang.getLine(161));
        stageNameStr.put(Stage.DISTANT_ISLE_NIGHT, lang.getLine(163));
        stageNameStr.put(Stage.DESERT_RUINS_NIGHT, lang.getLine(369));
        stageNameStr.put(Stage.SCORCHED_RUINS_NIGHT, lang.getLine(370));
        stageNameStr.put(Stage.RANDOM, lang.getLine(164));
        stageNameStr.put(Stage.HIDDEN_CAVE_NIGHT, lang.getLine(371));
        numberOfStages = Stage.values().length;

        verticalRows = (numberOfStages / 3);
        stageCap = new Image[numberOfStages];
        stagePrev = new Image[numberOfStages];
    }

    public static synchronized RenderStageSelect getInstance() {
        if (instance == null)
            instance = new RenderStageSelect();
        return instance;
    }

    @Override
    public void loadAssets() {
        if (!loadAssets) return;
        imageLoader = new JenesisImageLoader();
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
        if (selectedStage) {
            g2d.setColor(Color.BLACK);
            g2d.drawImage(stagePrev[hoveredStage.getIndex()], charXcap + x, charYcap, this);
            g2d.setComposite(makeComposite(0.7f));
            g2d.fillRect(0, 0, 852, 480);
            g2d.setComposite(makeComposite(1.0f));
            g2d.setComposite(makeComposite(0.5f));
            g2d.fillRect(200, 0, 452, 480);
            g2d.setComposite(makeComposite(1.0f));
            g2d.drawImage(loading, 316, 183, this); //yCord = 286 - icoHeight
            g2d.setColor(Color.WHITE);
            g2d.drawString(Language.getInstance().getLine(165), (852 - g2d.getFontMetrics().stringWidth(Language.getInstance().getLine(165))) / 2, 200);
        } else if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanClient) && selectedStage == false) {
            g2d.setFont(normalFont);
            g2d.setColor(Color.BLACK);
            g2d.fillRect(0, 0, 852, 480);
            g2d.setColor(Color.WHITE);
            g2d.drawString(">> " + Language.getInstance().getLine(166) + " <<", (852 - g2d.getFontMetrics(bigFont).stringWidth(">> " + Language.getInstance().getLine(166) + " <<")) / 2, 300);
        } else if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanClient) == false) {
            g2d.setFont(normalFont);
            g2d.setColor(Color.BLACK);
            g2d.fillRect(0, 0, 852, 480);
            //characters preview DYNAMIC change
            if (opacity < 0.98f) {
                opacity = opacity + 0.02f;
            }
            g2d.setComposite(makeComposite(opacity));
            g2d.drawImage(stagePrev[hoveredStage.getIndex()], charXcap + x, charYcap, this);
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
                        showStageName(hoveredStage);
                        g2d.drawImage(charBack, (hPos - hSpacer) + (hSpacer * hIndex), firstLine + (vSpacer * vIndex), this);
                    }
                }
            }
        }
        JenesisGlassPane.getInstance().overlay(g2d, this);
        g.drawImage(volatileImg, 0, 0, this);
    }


    private void loadCaps() {
        selectedStage = false;
        try {
            charBack = imageLoader.loadImage("images/selStage.png");
            loading = imageLoader.loadImage("images/appletprogress.gif");
            for (int i = 0; i < stagePrevLox.length; i++) {
                stageCap[i] = imageLoader.loadImage("images/t_" + stagePrevLox[i] + ".png");
                stagePrev[i] = imageLoader.loadImage("images/prev/" + stagePrevLox[i] + ".jpg");
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    private void showStageName(Stage stage) {
        if (stage.getIndex() != hoveredStageIndex) {
            capAnim();
            systemNotice(stageNameStr.get(stage));
            hoveredStageIndex = stage.getIndex();
            if (hoveredStageIndex == Stage.RANDOM.getIndex()) {
                mode = StageSelection.RANDOM;
            } else {
                mode = StageSelection.NORMAL;
            }
        }
    }

    public String getTrack() {
        return ambientMusic[ambientMusicIndex];
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


    /**
     * Horizontal index
     *
     * @return columnIndex
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
     * @return rowIndex
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
     * Gets the number of columns in the characters select screen
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

    public String[] getAmbientMusic() {
        return ambientMusic;
    }

    public int getAmbientMusicIndex() {
        return ambientMusicIndex;
    }

    public boolean getSelectedStage() {
        return selectedStage;
    }

    public void setSelectedStage(boolean value) {
        selectedStage = value;
    }

    public String[] getAmnientMusicMetaData() {
        return amnientMusicMetaData;
    }

    public void newInstance() {
        super.newInstance();
    }

}

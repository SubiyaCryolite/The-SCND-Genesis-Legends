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
package com.scndgen.legends.scene;

import com.scndgen.legends.enums.Stage;
import com.scndgen.legends.enums.StageSelection;
import com.scndgen.legends.render.RenderCharacterSelectionScreen;
import com.scndgen.legends.render.RenderGameplay;
import com.scndgen.legends.windows.MainWindow;
import io.github.subiyacryolite.enginev1.JenesisMode;

/**
 * @author: Ifunga Ndana
 * @class: drawPrevChar
 * This class creates a graphical preview of the characters and opponent
 */
public abstract class StageSelect extends JenesisMode {

    protected Stage hoveredStage = Stage.IBEX_HILL;
    protected int lastRow, currentSlot = 0, xCordCloud = 0, xCordCloud2 = 0, charYcap = 0, charXcap = 0, hIndex = 1, x = 0, y = 0, vIndex = 0, vSpacer = 52, hSpacer = 92, hPos = 288, firstLine = 105;
    protected StageSelection mode = StageSelection.NORMAL;
    protected int ambientMusicIndex = 0;
    protected int numberOfStages;
    protected boolean selectedStage = false;
    protected final String[] stagePrevLox;


    public StageSelect() {
        stagePrevLox = new String[Stage.values().length];
        stagePrevLox[Stage.IBEX_HILL.getIndex()] = "bgBG1";
        stagePrevLox[Stage.CHELSTON_CITY_DOCKS.getIndex()] = "bgBG2";
        stagePrevLox[Stage.DESERT_RUINS.getIndex()] = "bgBG3";
        stagePrevLox[Stage.CHELSTON_CITY_STREETS.getIndex()] = "bgBG4";
        stagePrevLox[Stage.IBEX_HILL_NIGHT.getIndex()] = "bgBG5";
        stagePrevLox[Stage.SCORCHED_RUINS.getIndex()] = "bgBG6";
        stagePrevLox[Stage.FROZEN_WILDERNESS.getIndex()] = "bgBG7";
        stagePrevLox[Stage.DISTANT_ISLE.getIndex()] = "bgBG100";
        stagePrevLox[Stage.HIDDEN_CAVE.getIndex()] = "bgBG8";
        stagePrevLox[Stage.AFRICAN_VILLAGE.getIndex()] = "bgBG9";
        stagePrevLox[Stage.APOCALYPTO.getIndex()] = "bgBG10";
        stagePrevLox[Stage.DISTANT_ISLE_NIGHT.getIndex()] = "bgBG11";
        stagePrevLox[Stage.DESERT_RUINS_NIGHT.getIndex()] = "bgBG13";
        stagePrevLox[Stage.SCORCHED_RUINS_NIGHT.getIndex()] = "bgBG14";
        stagePrevLox[Stage.RANDOM.getIndex()] = "bgBG12";
        stagePrevLox[Stage. HIDDEN_CAVE_NIGHT.getIndex()] = "bgBG15";
    }

    /**
     * SHows loading screen
     */
    public void nowLoading() {
        selectedStage = true;
    }

    public void selectStage(Stage stage) {
        hoveredStage = stage;
        if (mode == StageSelection.NORMAL) {
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.singlePlayer) || MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                nowLoading();
                if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                    MainWindow.getInstance().sendToClient("loadingGVSHA");
                }
            }
        } else {
            hoveredStage = Stage.values()[(int) (Math.random() * (numberOfStages - 1))];
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.singlePlayer) || MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                nowLoading();
                if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                    MainWindow.getInstance().sendToClient("loadingGVSHA");
                }
            }
        }
        if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.storyMode) || MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.singlePlayer) || MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
            switch (hoveredStage) {
                case IBEX_HILL:
                    selectIbexHill();
                    break;
                case CHELSTON_CITY_DOCKS:
                    selectChelsonCityDocks();
                    break;
                case DESERT_RUINS:
                    selectDesertRuins();
                    break;
                case CHELSTON_CITY_STREETS:
                    selectChelstonCityStreets();
                    break;
                case IBEX_HILL_NIGHT:
                    selectIbexHillNight();
                    break;
                case SCORCHED_RUINS:
                    selectScorchedRuins();
                    break;
                case FROZEN_WILDERNESS:
                    selectDistantSnowField();
                    break;
                case DISTANT_ISLE:
                    selectDistantIsle();
                    break;
                case HIDDEN_CAVE:
                    selectHiddenCave();
                    break;
                case HIDDEN_CAVE_NIGHT:
                    selectHiddenCaveNight();
                    break;
                case AFRICAN_VILLAGE:
                    selectAfricanVillage();
                    break;
                case APOCALYPTO:
                    selectApocalypto();
                    break;
                case DISTANT_ISLE_NIGHT:
                    selectDistantIsleNight();
                    break;
                case RANDOM:
                    selectRandomStage();
                    break;
                case DESERT_RUINS_NIGHT:
                    selectDesertRuinsNight();
                    break;
                case SCORCHED_RUINS_NIGHT:
                    selectScorchedRuinsNight();
                    break;
            }
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                MainWindow.getInstance().sendToClient(hoveredStage.getShortCode());
            }
        }
        if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.storyMode) || MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.singlePlayer) || MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost) || MainWindow.getInstance().getGameMode().equalsIgnoreCase("watch")) {
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                MainWindow.getInstance().sendToClient("gameStart7%^&");
            }
            start();
        }
    }

    public void defaultStageValues() {
        RenderGameplay.getInstance().fgx = 0;
        RenderGameplay.getInstance().fgy = 10;
        RenderGameplay.getInstance().fgxInc = 1;
        RenderGameplay.getInstance().fgyInc = 1;
        RenderGameplay.getInstance().animLoops = 10;
        RenderGameplay.getInstance().animDirection = "vert";
        RenderGameplay.getInstance().verticalMove = "no";
        RenderGameplay.getInstance().animLayer = "both";
        RenderGameplay.getInstance().delay = 33;
        RenderGameplay.getInstance().ambSpeed1 = 4;
        RenderGameplay.getInstance().ambSpeed2 = 3;
        ambientMusicIndex = 0;
        getReady();
    }

    /**
     * Ibex Hill- day
     */
    private void selectIbexHill() //
    {
        defaultStageValues();
    }

    /**
     * Chelston City docks
     */
    private void selectChelsonCityDocks() {
        RenderGameplay.getInstance().fgx = 0;
        RenderGameplay.getInstance().fgy = 0;
        RenderGameplay.getInstance().fgxInc = 1;
        RenderGameplay.getInstance().fgyInc = 1;
        RenderGameplay.getInstance().animLoops = 20;
        RenderGameplay.getInstance().animDirection = "none";
        RenderGameplay.getInstance().verticalMove = "no";
        RenderGameplay.getInstance().animLayer = "none";
        RenderGameplay.getInstance().delay = 66;
        RenderGameplay.getInstance().ambSpeed1 = 0;
        RenderGameplay.getInstance().ambSpeed2 = 0;
        ambientMusicIndex = 1;
        getReady();
    }

    /**
     * The Ruined Hall
     */
    private void selectDesertRuins() {
        RenderGameplay.getInstance().fgx = 0;
        RenderGameplay.getInstance().fgy = 0;
        RenderGameplay.getInstance().fgxInc = 5;
        RenderGameplay.getInstance().fgyInc = 1;
        RenderGameplay.getInstance().animLoops = 4;
        RenderGameplay.getInstance().animDirection = "none";
        RenderGameplay.getInstance().verticalMove = "no";
        RenderGameplay.getInstance().animLayer = "forg";
        RenderGameplay.getInstance().delay = 33;
        RenderGameplay.getInstance().ambSpeed1 = 2;
        RenderGameplay.getInstance().ambSpeed2 = 1;
        ambientMusicIndex = 2;
        getReady();
    }

    /**
     * Chelston City - Streets
     */
    private void selectChelstonCityStreets() {
        RenderGameplay.getInstance().fgx = 0;
        RenderGameplay.getInstance().fgy = 0;
        RenderGameplay.getInstance().fgxInc = 1;
        RenderGameplay.getInstance().fgyInc = 1;
        RenderGameplay.getInstance().animLoops = 20;
        RenderGameplay.getInstance().animDirection = "none";
        RenderGameplay.getInstance().verticalMove = "no";
        RenderGameplay.getInstance().animLayer = "none";
        RenderGameplay.getInstance().delay = 66;
        RenderGameplay.getInstance().ambSpeed1 = 0;
        RenderGameplay.getInstance().ambSpeed2 = 0;
        ambientMusicIndex = 3;
        getReady();
    }

    /**
     * Ibex Hill - Night
     */
    private void selectIbexHillNight() {
        RenderGameplay.getInstance().fgx = 0;
        RenderGameplay.getInstance().fgy = 10;
        RenderGameplay.getInstance().fgxInc = 1;
        RenderGameplay.getInstance().fgyInc = 1;
        RenderGameplay.getInstance().animLoops = 10;
        RenderGameplay.getInstance().animDirection = "vert";
        RenderGameplay.getInstance().verticalMove = "no";
        RenderGameplay.getInstance().animLayer = "both";
        RenderGameplay.getInstance().delay = 33;
        RenderGameplay.getInstance().ambSpeed1 = 4;
        RenderGameplay.getInstance().ambSpeed2 = 3;
        ambientMusicIndex = 4;
        getReady();
    }

    /**
     * Scorched Ruins
     */
    private void selectScorchedRuins() {
        RenderGameplay.getInstance().fgx = 0;
        RenderGameplay.getInstance().fgy = 0;
        RenderGameplay.getInstance().fgxInc = 5;
        RenderGameplay.getInstance().fgyInc = 1;
        RenderGameplay.getInstance().animLoops = 4;
        RenderGameplay.getInstance().animDirection = "none";
        RenderGameplay.getInstance().verticalMove = "no";
        RenderGameplay.getInstance().animLayer = "forg";
        RenderGameplay.getInstance().delay = 33;
        RenderGameplay.getInstance().ambSpeed1 = 2;
        RenderGameplay.getInstance().ambSpeed2 = 1;
        ambientMusicIndex = 5;
        getReady();
    }

    /**
     * Frozen Wilderness
     */
    private void selectDistantSnowField() {
        RenderGameplay.getInstance().fgx = 0;
        RenderGameplay.getInstance().fgy = 10;
        RenderGameplay.getInstance().fgxInc = 5;
        RenderGameplay.getInstance().fgyInc = 1;
        RenderGameplay.getInstance().animLoops = 4;
        RenderGameplay.getInstance().animDirection = "none";
        RenderGameplay.getInstance().verticalMove = "yes";
        RenderGameplay.getInstance().animLayer = "both";
        RenderGameplay.getInstance().delay = 33;
        RenderGameplay.getInstance().ambSpeed1 = 2;
        RenderGameplay.getInstance().ambSpeed2 = 1;
        ambientMusicIndex = 6;
        getReady();
    }

    /**
     * Distant Isle
     */
    private void selectDistantIsle() {
        RenderGameplay.getInstance().fgx = -40;
        RenderGameplay.getInstance().fgy = 20;
        RenderGameplay.getInstance().fgxInc = 2;
        RenderGameplay.getInstance().fgyInc = 1;
        RenderGameplay.getInstance().animLoops = 20;
        RenderGameplay.getInstance().animDirection = "rot";
        RenderGameplay.getInstance().verticalMove = "no";
        RenderGameplay.getInstance().animLayer = "back";
        RenderGameplay.getInstance().delay = 25;
        RenderGameplay.getInstance().ambSpeed1 = 1;
        RenderGameplay.getInstance().ambSpeed2 = 2;
        ambientMusicIndex = 0;
        getReady();
    }

    private void selectRandomStage() {
        RenderGameplay.getInstance().fgx = 0;
        RenderGameplay.getInstance().fgy = 0;
        RenderGameplay.getInstance().fgxInc = 1;
        RenderGameplay.getInstance().fgyInc = 1;
        RenderGameplay.getInstance().animLoops = 20;
        RenderGameplay.getInstance().animDirection = "none";
        RenderGameplay.getInstance().verticalMove = "no";
        RenderGameplay.getInstance().animLayer = "none";
        RenderGameplay.getInstance().delay = 66;
        RenderGameplay.getInstance().ambSpeed1 = 0;
        RenderGameplay.getInstance().ambSpeed2 = 0;
        ambientMusicIndex = 3;
        getReady();
    }

    /**
     * Distant Isle night
     */
    private void selectDistantIsleNight() {
        RenderGameplay.getInstance().fgx = -40;
        RenderGameplay.getInstance().fgy = 20;
        RenderGameplay.getInstance().fgxInc = 2;
        RenderGameplay.getInstance().fgyInc = 1;
        RenderGameplay.getInstance().animLoops = 20;
        RenderGameplay.getInstance().animDirection = "rot";
        RenderGameplay.getInstance().verticalMove = "no";
        RenderGameplay.getInstance().animLayer = "back";
        RenderGameplay.getInstance().delay = 25;
        RenderGameplay.getInstance().ambSpeed1 = 1;
        RenderGameplay.getInstance().ambSpeed2 = 2;
        ambientMusicIndex = 1;
        getReady();
    }

    /**
     * Hidden Cave
     */
    private void selectHiddenCave() {
        RenderGameplay.getInstance().fgx = 0;
        RenderGameplay.getInstance().fgy = 0;
        RenderGameplay.getInstance().fgxInc = 1;
        RenderGameplay.getInstance().fgyInc = 1;
        RenderGameplay.getInstance().animLoops = 20;
        RenderGameplay.getInstance().animDirection = "none";
        RenderGameplay.getInstance().verticalMove = "no";
        RenderGameplay.getInstance().animLayer = "none";
        RenderGameplay.getInstance().delay = 66;
        RenderGameplay.getInstance().ambSpeed1 = 0;
        RenderGameplay.getInstance().ambSpeed2 = 0;
        ambientMusicIndex = 2;
        getReady();
    }

    private void selectHiddenCaveNight() {
        RenderGameplay.getInstance().fgx = 0;
        RenderGameplay.getInstance().fgy = 0;
        RenderGameplay.getInstance().fgxInc = 1;
        RenderGameplay.getInstance().fgyInc = 1;
        RenderGameplay.getInstance().animLoops = 20;
        RenderGameplay.getInstance().animDirection = "none";
        RenderGameplay.getInstance().verticalMove = "no";
        RenderGameplay.getInstance().animLayer = "none";
        RenderGameplay.getInstance().delay = 66;
        RenderGameplay.getInstance().ambSpeed1 = 0;
        RenderGameplay.getInstance().ambSpeed2 = 0;
        ambientMusicIndex = 2;
        getReady();
    }

    /**
     * African Village
     */
    private void selectAfricanVillage() {
        RenderGameplay.getInstance().fgx = 0;
        RenderGameplay.getInstance().fgy = 0;
        RenderGameplay.getInstance().fgxInc = 1;
        RenderGameplay.getInstance().fgyInc = 1;
        RenderGameplay.getInstance().animLoops = 20;
        RenderGameplay.getInstance().animDirection = "none";
        RenderGameplay.getInstance().verticalMove = "no";
        RenderGameplay.getInstance().animLayer = "back";
        RenderGameplay.getInstance().delay = 122;
        RenderGameplay.getInstance().ambSpeed1 = 2;
        RenderGameplay.getInstance().ambSpeed2 = 1;
        ambientMusicIndex = 3;
        getReady();
    }

    /**
     * The Apocalypse
     */
    private void selectApocalypto() {
        RenderGameplay.getInstance().fgx = 0;
        RenderGameplay.getInstance().fgy = 0;
        RenderGameplay.getInstance().fgxInc = 5;
        RenderGameplay.getInstance().fgyInc = 1;
        RenderGameplay.getInstance().animLoops = 4;
        RenderGameplay.getInstance().animDirection = "none";
        RenderGameplay.getInstance().verticalMove = "no";
        RenderGameplay.getInstance().animLayer = "both";
        RenderGameplay.getInstance().delay = 33;
        RenderGameplay.getInstance().ambSpeed1 = 2;
        RenderGameplay.getInstance().ambSpeed2 = 1;
        ambientMusicIndex = 4;
        getReady();
    }

    private void selectDesertRuinsNight() {
        RenderGameplay.getInstance().fgx = 0;
        RenderGameplay.getInstance().fgy = 0;
        RenderGameplay.getInstance().fgxInc = 5;
        RenderGameplay.getInstance().fgyInc = 1;
        RenderGameplay.getInstance().animLoops = 4;
        RenderGameplay.getInstance().animDirection = "none";
        RenderGameplay.getInstance().verticalMove = "no";
        RenderGameplay.getInstance().animLayer = "forg";
        RenderGameplay.getInstance().delay = 33;
        RenderGameplay.getInstance().ambSpeed1 = 2;
        RenderGameplay.getInstance().ambSpeed2 = 1;
        ambientMusicIndex = 1;
        getReady();
    }

    private void selectScorchedRuinsNight() {
        RenderGameplay.getInstance().fgx = 0;
        RenderGameplay.getInstance().fgy = 0;
        RenderGameplay.getInstance().fgxInc = 1;
        RenderGameplay.getInstance().fgyInc = 1;
        RenderGameplay.getInstance().animLoops = 20;
        RenderGameplay.getInstance().animDirection = "none";
        RenderGameplay.getInstance().verticalMove = "no";
        RenderGameplay.getInstance().animLayer = "none";
        RenderGameplay.getInstance().delay = 66;
        RenderGameplay.getInstance().ambSpeed1 = 0;
        RenderGameplay.getInstance().ambSpeed2 = 0;
        ambientMusicIndex = 3;
        getReady();
    }


    /**
     * @return stage
     */
    public String getStage() {
        return stagePrevLox[hoveredStage.getIndex()];
    }

    private void getReady() {
        RenderGameplay.getInstance().activeStage = Integer.parseInt(getStage().substring(4));
        RenderGameplay.getInstance().bgLocation = "images/" + getStage() + ".png";
        RenderGameplay.getInstance().fgLocation = "images/" + getStage() + "fg.png";
    }

    public void start() {
        getReady();

        new Thread() {

            @Override
            @SuppressWarnings("static-access")
            public void run() {
                try {
                    this.sleep(1000);
                    MainWindow.getInstance().newGame();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * When both playes are selected, this prevents movement.
     *
     * @return false if both Characters have been selected, true if only one is selected
     */
    public boolean bothArentSelected() {
        boolean answer = true;

        if (RenderCharacterSelectionScreen.getInstance().characterSelected && RenderCharacterSelectionScreen.getInstance().opponentSelected) {
            answer = false;
        }

        return answer;
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
        int stageToSelect = (vIndex * 3) + hIndex;
        if (stageToSelect <= numberOfStages) {
            ans = true;
            hoveredStage = Stage.values()[stageToSelect - 1];
        }
        return ans;
    }

    public Stage getHoveredStage() {
        return hoveredStage;
    }
}

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
package com.scndgen.legends.mode;

import com.scndgen.legends.Language;
import com.scndgen.legends.ScndGenLegends;
import com.scndgen.legends.enums.*;
import com.scndgen.legends.render.RenderGamePlay;
import com.scndgen.legends.network.NetworkManager;
import io.github.subiyacryolite.enginev1.Mode;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.util.Hashtable;

/**
 * @author: Ifunga Ndana
 * @class: drawPrevChar
 * This class creates a graphical preview of the characterEnum and opponent
 */
public abstract class StageSelect extends Mode {

    protected Stage hoveredStage = Stage.IBEX_HILL;
    protected Stage selectedStage = Stage.IBEX_HILL;
    protected int x = 0, y = 0, vSpacer = 52, hSpacer = 92, hPos = 288, firstLine = 105;
    protected StageSelectionMode mode;
    protected int ambientMusicIndex = 0;
    protected final int numberOfStages = Stage.values().length;
    protected final int columns = 3;
    protected final int rows = numberOfStages / columns;
    protected final Hashtable<Integer, Stage> stageLookup = new Hashtable<>();
    protected final Hashtable<Stage, String> lookupStageNames = new Hashtable<>();
    protected final String[] stagePreviews = new String[Stage.values().length];
    protected String fgLocation;
    protected String bgLocation;
    protected boolean stageSelected;

    public void newInstance() {
        loadAssets = true;
        hoveredStage = Stage.IBEX_HILL;
        mode = StageSelectionMode.NORMAL;
        stageLookup.clear();
        for (Stage stage : Stage.values()) {
            stageLookup.put(stage.index(), stage);
        }
        //===============================================================
        stagePreviews[Stage.IBEX_HILL.index()] = "bgBG1";
        stagePreviews[Stage.CHELSTON_CITY_DOCKS.index()] = "bgBG2";
        stagePreviews[Stage.DESERT_RUINS.index()] = "bgBG3";
        stagePreviews[Stage.CHELSTON_CITY_STREETS.index()] = "bgBG4";
        stagePreviews[Stage.IBEX_HILL_NIGHT.index()] = "bgBG5";
        stagePreviews[Stage.SCORCHED_RUINS.index()] = "bgBG6";
        stagePreviews[Stage.FROZEN_WILDERNESS.index()] = "bgBG7";
        stagePreviews[Stage.DISTANT_ISLE.index()] = "bgBG100";
        stagePreviews[Stage.HIDDEN_CAVE.index()] = "bgBG8";
        stagePreviews[Stage.AFRICAN_VILLAGE.index()] = "bgBG9";
        stagePreviews[Stage.APOCALYPTO.index()] = "bgBG10";
        stagePreviews[Stage.DISTANT_ISLE_NIGHT.index()] = "bgBG11";
        stagePreviews[Stage.DESERT_RUINS_NIGHT.index()] = "bgBG13";
        stagePreviews[Stage.SCORCHED_RUINS_NIGHT.index()] = "bgBG14";
        stagePreviews[Stage.RANDOM.index()] = "bgBG12";
        stagePreviews[Stage.HIDDEN_CAVE_NIGHT.index()] = "bgBG15";
        //===============================================================
        Language language = Language.getInstance();
        lookupStageNames.clear();
        lookupStageNames.put(Stage.IBEX_HILL, language.get(152));
        lookupStageNames.put(Stage.CHELSTON_CITY_DOCKS, language.get(153));
        lookupStageNames.put(Stage.DESERT_RUINS, language.get(154));
        lookupStageNames.put(Stage.CHELSTON_CITY_STREETS, language.get(155));
        lookupStageNames.put(Stage.IBEX_HILL_NIGHT, language.get(156));
        lookupStageNames.put(Stage.SCORCHED_RUINS, language.get(157));
        lookupStageNames.put(Stage.FROZEN_WILDERNESS, language.get(158));
        lookupStageNames.put(Stage.DISTANT_ISLE, language.get(162));
        lookupStageNames.put(Stage.HIDDEN_CAVE, language.get(159));
        lookupStageNames.put(Stage.AFRICAN_VILLAGE, language.get(160));
        lookupStageNames.put(Stage.APOCALYPTO, language.get(161));
        lookupStageNames.put(Stage.DISTANT_ISLE_NIGHT, language.get(163));
        lookupStageNames.put(Stage.DESERT_RUINS_NIGHT, language.get(369));
        lookupStageNames.put(Stage.SCORCHED_RUINS_NIGHT, language.get(370));
        lookupStageNames.put(Stage.RANDOM, language.get(164));
        lookupStageNames.put(Stage.HIDDEN_CAVE_NIGHT, language.get(371));
    }

    public StageSelect() {

    }

    public void selectStage(Stage stage) {
        stageSelected = true;
        if (mode == StageSelectionMode.NORMAL) {
            if (ScndGenLegends.getInstance().getSubMode() == SubMode.SINGLE_PLAYER || ScndGenLegends.getInstance().getSubMode() == SubMode.LAN_HOST) {
                if (ScndGenLegends.getInstance().getSubMode() == SubMode.LAN_HOST) {
                    NetworkManager.getInstance().sendToClient("loadingGVSHA");
                }
            }
        } else {
            stage = stageLookup.getOrDefault((int) (Math.random() * (numberOfStages - 1)), Stage.IBEX_HILL);//for this to work RANDOM SHOULD ALWAYS BE LAST
            if (ScndGenLegends.getInstance().getSubMode() == SubMode.SINGLE_PLAYER || ScndGenLegends.getInstance().getSubMode() == SubMode.LAN_HOST) {
                if (ScndGenLegends.getInstance().getSubMode() == SubMode.LAN_HOST) {
                    NetworkManager.getInstance().sendToClient("loadingGVSHA");
                }
            }
        }
        if (ScndGenLegends.getInstance().getSubMode() == SubMode.STORY_MODE || ScndGenLegends.getInstance().getSubMode() == SubMode.SINGLE_PLAYER || ScndGenLegends.getInstance().getSubMode() == SubMode.LAN_HOST) {
            selectedStage = stage;
            switch (stage) {
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
                case DESERT_RUINS_NIGHT:
                    selectDesertRuinsNight();
                    break;
                case SCORCHED_RUINS_NIGHT:
                    selectScorchedRuinsNight();
                    break;
            }
            if (ScndGenLegends.getInstance().getSubMode() == SubMode.LAN_HOST) {
                NetworkManager.getInstance().sendToClient(hoveredStage.shortCode());
            }
        }
        if (ScndGenLegends.getInstance().getSubMode() == SubMode.STORY_MODE || ScndGenLegends.getInstance().getSubMode() == SubMode.SINGLE_PLAYER || ScndGenLegends.getInstance().getSubMode() == SubMode.LAN_HOST || ScndGenLegends.getInstance().getSubMode() == SubMode.WATCH) {
            if (ScndGenLegends.getInstance().getSubMode() == SubMode.LAN_HOST) {
                NetworkManager.getInstance().sendToClient("gameStart7%^&");
            }
            start();
        }
    }

    public void defaultStageValues() {
        RenderGamePlay.getInstance().foreGroundPositionX = 0;
        RenderGamePlay.getInstance().foreGroundPositionY = 10;
        RenderGamePlay.getInstance().foreGroundXIncrement = 1;
        RenderGamePlay.getInstance().foreGroundYIncrement = 1;
        RenderGamePlay.getInstance().animationLoops = 10;
        RenderGamePlay.getInstance().animationDirection = AnimationDirection.VERTICAL;
        RenderGamePlay.getInstance().animLayer = StageAnimation.BOTH;
        RenderGamePlay.getInstance().delay = 33;
        RenderGamePlay.getInstance().ambSpeed1 = 4;
        RenderGamePlay.getInstance().ambSpeed2 = 3;
        ambientMusicIndex = 0;
    }

    protected void selectIbexHill() //
    {
        defaultStageValues();
    }

    protected void selectChelsonCityDocks() {
        RenderGamePlay.getInstance().foreGroundPositionX = 0;
        RenderGamePlay.getInstance().foreGroundPositionY = 0;
        RenderGamePlay.getInstance().foreGroundXIncrement = 1;
        RenderGamePlay.getInstance().foreGroundYIncrement = 1;
        RenderGamePlay.getInstance().animationLoops = 20;
        RenderGamePlay.getInstance().animationDirection = AnimationDirection.NONE;
        RenderGamePlay.getInstance().animLayer = StageAnimation.NONE;
        RenderGamePlay.getInstance().delay = 66;
        RenderGamePlay.getInstance().ambSpeed1 = 0;
        RenderGamePlay.getInstance().ambSpeed2 = 0;
        ambientMusicIndex = 1;
    }

    protected void selectDesertRuins() {
        RenderGamePlay.getInstance().foreGroundPositionX = 0;
        RenderGamePlay.getInstance().foreGroundPositionY = 0;
        RenderGamePlay.getInstance().foreGroundXIncrement = 5;
        RenderGamePlay.getInstance().foreGroundYIncrement = 1;
        RenderGamePlay.getInstance().animationLoops = 4;
        RenderGamePlay.getInstance().animationDirection = AnimationDirection.NONE;
        RenderGamePlay.getInstance().animLayer = StageAnimation.FOREGROUND;
        RenderGamePlay.getInstance().delay = 33;
        RenderGamePlay.getInstance().ambSpeed1 = 2;
        RenderGamePlay.getInstance().ambSpeed2 = 1;
        ambientMusicIndex = 2;
    }

    protected void selectChelstonCityStreets() {
        RenderGamePlay.getInstance().foreGroundPositionX = 0;
        RenderGamePlay.getInstance().foreGroundPositionY = 0;
        RenderGamePlay.getInstance().foreGroundXIncrement = 1;
        RenderGamePlay.getInstance().foreGroundYIncrement = 1;
        RenderGamePlay.getInstance().animationLoops = 20;
        RenderGamePlay.getInstance().animationDirection = AnimationDirection.NONE;
        RenderGamePlay.getInstance().animLayer = StageAnimation.NONE;
        RenderGamePlay.getInstance().delay = 66;
        RenderGamePlay.getInstance().ambSpeed1 = 0;
        RenderGamePlay.getInstance().ambSpeed2 = 0;
        ambientMusicIndex = 3;
    }

    protected void selectIbexHillNight() {
        RenderGamePlay.getInstance().foreGroundPositionX = 0;
        RenderGamePlay.getInstance().foreGroundPositionY = 10;
        RenderGamePlay.getInstance().foreGroundXIncrement = 1;
        RenderGamePlay.getInstance().foreGroundYIncrement = 1;
        RenderGamePlay.getInstance().animationLoops = 10;
        RenderGamePlay.getInstance().animationDirection = AnimationDirection.VERTICAL;
        RenderGamePlay.getInstance().animLayer = StageAnimation.BOTH;
        RenderGamePlay.getInstance().delay = 33;
        RenderGamePlay.getInstance().ambSpeed1 = 4;
        RenderGamePlay.getInstance().ambSpeed2 = 3;
        ambientMusicIndex = 4;
    }

    protected void selectScorchedRuins() {
        RenderGamePlay.getInstance().foreGroundPositionX = 0;
        RenderGamePlay.getInstance().foreGroundPositionY = 0;
        RenderGamePlay.getInstance().foreGroundXIncrement = 5;
        RenderGamePlay.getInstance().foreGroundYIncrement = 1;
        RenderGamePlay.getInstance().animationLoops = 4;
        RenderGamePlay.getInstance().animationDirection = AnimationDirection.NONE;
        RenderGamePlay.getInstance().animLayer = StageAnimation.FOREGROUND;
        RenderGamePlay.getInstance().delay = 33;
        RenderGamePlay.getInstance().ambSpeed1 = 2;
        RenderGamePlay.getInstance().ambSpeed2 = 1;
        ambientMusicIndex = 5;
    }

    protected void selectDistantSnowField() {
        RenderGamePlay.getInstance().foreGroundPositionX = 0;
        RenderGamePlay.getInstance().foreGroundPositionY = 10;
        RenderGamePlay.getInstance().foreGroundXIncrement = 5;
        RenderGamePlay.getInstance().foreGroundYIncrement = 1;
        RenderGamePlay.getInstance().animationLoops = 4;
        RenderGamePlay.getInstance().animationDirection = AnimationDirection.NONE;
        RenderGamePlay.getInstance().animLayer = StageAnimation.BOTH;
        RenderGamePlay.getInstance().delay = 33;
        RenderGamePlay.getInstance().ambSpeed1 = 2;
        RenderGamePlay.getInstance().ambSpeed2 = 1;
        ambientMusicIndex = 6;
    }

    protected void selectDistantIsle() {
        RenderGamePlay.getInstance().foreGroundPositionX = -40;
        RenderGamePlay.getInstance().foreGroundPositionY = 20;
        RenderGamePlay.getInstance().foreGroundXIncrement = 2;
        RenderGamePlay.getInstance().foreGroundYIncrement = 1;
        RenderGamePlay.getInstance().animationLoops = 20;
        RenderGamePlay.getInstance().animationDirection = AnimationDirection.ROTATION;
        RenderGamePlay.getInstance().animLayer = StageAnimation.BACKGROUND;
        RenderGamePlay.getInstance().delay = 25;
        RenderGamePlay.getInstance().ambSpeed1 = 1;
        RenderGamePlay.getInstance().ambSpeed2 = 2;
        ambientMusicIndex = 0;
    }

    protected void selectDistantIsleNight() {
        RenderGamePlay.getInstance().foreGroundPositionX = -40;
        RenderGamePlay.getInstance().foreGroundPositionY = 20;
        RenderGamePlay.getInstance().foreGroundXIncrement = 2;
        RenderGamePlay.getInstance().foreGroundYIncrement = 1;
        RenderGamePlay.getInstance().animationLoops = 20;
        RenderGamePlay.getInstance().animationDirection = AnimationDirection.ROTATION;
        RenderGamePlay.getInstance().animLayer = StageAnimation.BACKGROUND;
        RenderGamePlay.getInstance().delay = 25;
        RenderGamePlay.getInstance().ambSpeed1 = 1;
        RenderGamePlay.getInstance().ambSpeed2 = 2;
        ambientMusicIndex = 1;
    }

    protected void selectHiddenCave() {
        RenderGamePlay.getInstance().foreGroundPositionX = 0;
        RenderGamePlay.getInstance().foreGroundPositionY = 0;
        RenderGamePlay.getInstance().foreGroundXIncrement = 1;
        RenderGamePlay.getInstance().foreGroundYIncrement = 1;
        RenderGamePlay.getInstance().animationLoops = 20;
        RenderGamePlay.getInstance().animationDirection = AnimationDirection.NONE;
        RenderGamePlay.getInstance().animLayer = StageAnimation.NONE;
        RenderGamePlay.getInstance().delay = 66;
        RenderGamePlay.getInstance().ambSpeed1 = 0;
        RenderGamePlay.getInstance().ambSpeed2 = 0;
        ambientMusicIndex = 2;
    }

    protected void selectHiddenCaveNight() {
        RenderGamePlay.getInstance().foreGroundPositionX = 0;
        RenderGamePlay.getInstance().foreGroundPositionY = 0;
        RenderGamePlay.getInstance().foreGroundXIncrement = 1;
        RenderGamePlay.getInstance().foreGroundYIncrement = 1;
        RenderGamePlay.getInstance().animationLoops = 20;
        RenderGamePlay.getInstance().animationDirection = AnimationDirection.NONE;
        RenderGamePlay.getInstance().animLayer = StageAnimation.NONE;
        RenderGamePlay.getInstance().delay = 66;
        RenderGamePlay.getInstance().ambSpeed1 = 0;
        RenderGamePlay.getInstance().ambSpeed2 = 0;
        ambientMusicIndex = 2;
    }

    protected void selectAfricanVillage() {
        RenderGamePlay.getInstance().foreGroundPositionX = 0;
        RenderGamePlay.getInstance().foreGroundPositionY = 0;
        RenderGamePlay.getInstance().foreGroundXIncrement = 1;
        RenderGamePlay.getInstance().foreGroundYIncrement = 1;
        RenderGamePlay.getInstance().animationLoops = 20;
        RenderGamePlay.getInstance().animationDirection = AnimationDirection.NONE;
        RenderGamePlay.getInstance().animLayer = StageAnimation.BACKGROUND;
        RenderGamePlay.getInstance().delay = 122;
        RenderGamePlay.getInstance().ambSpeed1 = 2;
        RenderGamePlay.getInstance().ambSpeed2 = 1;
        ambientMusicIndex = 3;
    }

    protected void selectApocalypto() {
        RenderGamePlay.getInstance().foreGroundPositionX = 0;
        RenderGamePlay.getInstance().foreGroundPositionY = 0;
        RenderGamePlay.getInstance().foreGroundXIncrement = 5;
        RenderGamePlay.getInstance().foreGroundYIncrement = 1;
        RenderGamePlay.getInstance().animationLoops = 4;
        RenderGamePlay.getInstance().animationDirection = AnimationDirection.NONE;
        RenderGamePlay.getInstance().animLayer = StageAnimation.BOTH;
        RenderGamePlay.getInstance().delay = 33;
        RenderGamePlay.getInstance().ambSpeed1 = 2;
        RenderGamePlay.getInstance().ambSpeed2 = 1;
        ambientMusicIndex = 4;
    }

    protected void selectDesertRuinsNight() {
        RenderGamePlay.getInstance().foreGroundPositionX = 0;
        RenderGamePlay.getInstance().foreGroundPositionY = 0;
        RenderGamePlay.getInstance().foreGroundXIncrement = 5;
        RenderGamePlay.getInstance().foreGroundYIncrement = 1;
        RenderGamePlay.getInstance().animationLoops = 4;
        RenderGamePlay.getInstance().animationDirection = AnimationDirection.NONE;
        RenderGamePlay.getInstance().animLayer = StageAnimation.FOREGROUND;
        RenderGamePlay.getInstance().delay = 33;
        RenderGamePlay.getInstance().ambSpeed1 = 2;
        RenderGamePlay.getInstance().ambSpeed2 = 1;
        ambientMusicIndex = 1;
    }

    protected void selectScorchedRuinsNight() {
        RenderGamePlay.getInstance().foreGroundPositionX = 0;
        RenderGamePlay.getInstance().foreGroundPositionY = 0;
        RenderGamePlay.getInstance().foreGroundXIncrement = 1;
        RenderGamePlay.getInstance().foreGroundYIncrement = 1;
        RenderGamePlay.getInstance().animationLoops = 20;
        RenderGamePlay.getInstance().animationDirection = AnimationDirection.NONE;
        RenderGamePlay.getInstance().animLayer = StageAnimation.NONE;
        RenderGamePlay.getInstance().delay = 66;
        RenderGamePlay.getInstance().ambSpeed1 = 0;
        RenderGamePlay.getInstance().ambSpeed2 = 0;
        ambientMusicIndex = 3;
    }

    public void start() {
        bgLocation = "images/bgBG" + selectedStage.filePrefix() + ".png";
        fgLocation = "images/bgBG" + selectedStage.filePrefix() + "fg.png";
        ScndGenLegends.getInstance().loadMode(ModeEnum.STANDARD_GAMEPLAY_START);
    }

    public String getBgLocation() {
        return bgLocation;
    }

    public String getFgLocation() {
        return fgLocation;
    }

    public void animateCaption() {
        opacity = 0.0f;
    }

    public Stage getHoveredStage() {
        return hoveredStage;
    }

    public void mouseClicked(MouseEvent mouseEvent) {
        switch (mouseEvent.getButton()) {
            case PRIMARY:
                onAccept();
                break;
            case MIDDLE:
                break;
            case SECONDARY:
                break;
        }
    }

    public void keyPressed(KeyEvent keyEvent) {
        KeyCode keyCode = keyEvent.getCode();
        switch (keyCode) {
            case ENTER:
                onAccept();
                break;
            case ESCAPE:
            case BACK_SPACE:
                onBackCancel();
                break;
            case UP:
            case W:
                onUp();
                break;
            case DOWN:
            case S:
                onDown();
                break;
            case LEFT:
            case A:
                onLeft();
                break;
            case RIGHT:
            case D:
                onRight();
                break;
        }
    }

    public void mouseMoved(MouseEvent mouseEvent) {
    }

}

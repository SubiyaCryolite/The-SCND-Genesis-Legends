/**************************************************************************

 The SCND Genesis: Legends is a fighting game based on THE SCND GENESIS,
 a webcomic created by Ifunga Ndana ((([<a href="https://www.scndgen.com">https://www.scndgen.com</a>]))).

 The SCND Genesis: Legends RMX  © 2017 Ifunga Ndana.

 The SCND Genesis: Legends is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 The SCND Genesis: Legends is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with The SCND Genesis: Legends. If not, see <<a href="http://www.gnu.org/licenses/">http://www.gnu.org/licenses/</a>>.

 **************************************************************************/
package com.scndgen.legends.mode;

import com.scndgen.legends.Language;
import com.scndgen.legends.ScndGenLegends;
import com.scndgen.legends.constants.NetworkConstants;
import com.scndgen.legends.enums.*;
import com.scndgen.legends.network.NetworkManager;
import com.scndgen.legends.render.RenderGamePlay;
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
    protected StageSelectionMode stageSelectionMode;
    protected int ambientMusicIndex = 0;
    protected final int numberOfStages = Stage.values().length;
    protected final int columns = 3;
    protected final int rows = numberOfStages / columns;
    protected final Hashtable<Integer, Stage> stageLookup = new Hashtable<>();
    protected final Hashtable<Stage, String> lookupStageNames = new Hashtable<>();
    protected final String[] stagePreviews = new String[Stage.values().length];
    protected String stageForeground;
    protected String stageBackground;
    protected String stageAmbient1;
    protected String stageAmbient2;
    protected boolean stageSelected;

    public void newInstance() {
        loadAssets = true;
        hoveredStage = Stage.IBEX_HILL;
        stageSelectionMode = StageSelectionMode.NORMAL;
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
        Language language = Language.get();
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

    public void selectStage(Stage stage) {
        stageSelected = true;
        hoveredStage = stage;//seems redundant but is necessary for online mode
        if (stageSelectionMode != StageSelectionMode.NORMAL) {
            stage = stageLookup.getOrDefault((int) (Math.random() * (numberOfStages - 1)), Stage.IBEX_HILL);//for this to work RANDOM SHOULD ALWAYS BE LAST
        }
        selectedStage = stage;
        stageBackground = "images/bgBG" + stage.filePrefix() + ".png";
        stageForeground = "images/bgBG" + stage.filePrefix() + "fg.png";
        stageAmbient1 = "images/bgBG" + stage.filePrefix() + "a.png";
        stageAmbient2 = "images/bgBG" + stage.filePrefix() + "b.png";
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
                selectFrozenWilderness();
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
        if (ScndGenLegends.get().getSubMode() == SubMode.STORY_MODE || ScndGenLegends.get().getSubMode() == SubMode.SINGLE_PLAYER || ScndGenLegends.get().getSubMode() == SubMode.LAN_HOST || ScndGenLegends.get().getSubMode() == SubMode.WATCH) {
            if (NetworkManager.get().isServer()) {
                NetworkManager.get().send(hoveredStage.shortCode());
                NetworkManager.get().send(NetworkConstants.GAME_START);
            }
            start();
        }
    }

    public void defaultStageValues() {
        RenderGamePlay.get().foreGroundPositionX = 0;
        RenderGamePlay.get().foreGroundPositionY = 10;
        RenderGamePlay.get().foreGroundXIncrement = 1;
        RenderGamePlay.get().foreGroundYIncrement = 1;
        RenderGamePlay.get().animationLoops = 10;
        RenderGamePlay.get().ambientDirection = AnimationDirection.VERTICAL;
        RenderGamePlay.get().foregroundDirection = AnimationDirection.NONE;
        RenderGamePlay.get().ambientMode = AmbientMode.INDEPENDENT;
        RenderGamePlay.get().ambSpeed1 = 4;
        RenderGamePlay.get().ambSpeed2 = 3;
    }

    protected void selectIbexHill() {
        defaultStageValues();
        RenderGamePlay.get().ambientDirection = AnimationDirection.HORIZONTAL;
        RenderGamePlay.get().ambientMode = AmbientMode.BOTH_IN_FOREGROUND;
        ambientMusicIndex = 0;
    }

    protected void selectIbexHillNight() {
        defaultStageValues();
        RenderGamePlay.get().ambientDirection = AnimationDirection.HORIZONTAL;
        RenderGamePlay.get().ambientMode = AmbientMode.BOTH_IN_FOREGROUND;
        ambientMusicIndex = 4;
    }

    protected void selectChelsonCityDocks() {
        RenderGamePlay.get().foreGroundPositionX = 0;
        RenderGamePlay.get().foreGroundPositionY = 0;
        RenderGamePlay.get().foreGroundXIncrement = 1;
        RenderGamePlay.get().foreGroundYIncrement = 1;
        RenderGamePlay.get().animationLoops = 20;
        RenderGamePlay.get().ambientDirection = AnimationDirection.NONE;
        RenderGamePlay.get().foregroundDirection = AnimationDirection.NONE;
        RenderGamePlay.get().ambientMode = AmbientMode.NONE;
        RenderGamePlay.get().ambSpeed1 = 0;
        RenderGamePlay.get().ambSpeed2 = 0;
        ambientMusicIndex = 1;
    }

    protected void selectDesertRuins() {
        RenderGamePlay.get().foreGroundPositionX = 0;
        RenderGamePlay.get().foreGroundPositionY = 0;
        RenderGamePlay.get().foreGroundXIncrement = 5;
        RenderGamePlay.get().foreGroundYIncrement = 1;
        RenderGamePlay.get().animationLoops = 4;
        RenderGamePlay.get().ambientDirection = AnimationDirection.HORIZONTAL;
        RenderGamePlay.get().foregroundDirection = AnimationDirection.NONE;
        RenderGamePlay.get().ambientMode = AmbientMode.BOTH_IN_FOREGROUND;
        RenderGamePlay.get().ambSpeed1 = 2;
        RenderGamePlay.get().ambSpeed2 = 1;
        ambientMusicIndex = 2;
    }

    protected void selectChelstonCityStreets() {
        RenderGamePlay.get().foreGroundPositionX = 0;
        RenderGamePlay.get().foreGroundPositionY = 0;
        RenderGamePlay.get().foreGroundXIncrement = 1;
        RenderGamePlay.get().foreGroundYIncrement = 1;
        RenderGamePlay.get().animationLoops = 20;
        RenderGamePlay.get().ambientDirection = AnimationDirection.NONE;
        RenderGamePlay.get().foregroundDirection = AnimationDirection.NONE;
        RenderGamePlay.get().ambientMode = AmbientMode.NONE;
        RenderGamePlay.get().ambSpeed1 = 0;
        RenderGamePlay.get().ambSpeed2 = 0;
        ambientMusicIndex = 3;
    }

    protected void selectScorchedRuins() {
        RenderGamePlay.get().foreGroundPositionX = 0;
        RenderGamePlay.get().foreGroundPositionY = 0;
        RenderGamePlay.get().foreGroundXIncrement = 5;
        RenderGamePlay.get().foreGroundYIncrement = 1;
        RenderGamePlay.get().animationLoops = 4;
        RenderGamePlay.get().ambientDirection = AnimationDirection.HORIZONTAL;
        RenderGamePlay.get().foregroundDirection = AnimationDirection.NONE;
        RenderGamePlay.get().ambientMode = AmbientMode.INDEPENDENT;
        RenderGamePlay.get().ambSpeed1 = 2;
        RenderGamePlay.get().ambSpeed2 = 1;
        ambientMusicIndex = 5;
    }

    protected void selectFrozenWilderness() {
        RenderGamePlay.get().foreGroundPositionX = 0;
        RenderGamePlay.get().foreGroundPositionY = 10;
        RenderGamePlay.get().foreGroundXIncrement = 5;
        RenderGamePlay.get().foreGroundYIncrement = 1;
        RenderGamePlay.get().animationLoops = 4;
        RenderGamePlay.get().ambientDirection = AnimationDirection.VERTICAL;
        RenderGamePlay.get().foregroundDirection = AnimationDirection.NONE;
        RenderGamePlay.get().ambientMode = AmbientMode.BOTH_IN_BACKGROUND;
        RenderGamePlay.get().ambSpeed1 = 2;
        RenderGamePlay.get().ambSpeed2 = 1;
        ambientMusicIndex = 6;
    }

    protected void selectDistantIsle() {
        RenderGamePlay.get().foreGroundPositionX = -40;
        RenderGamePlay.get().foreGroundPositionY = 20;
        RenderGamePlay.get().foreGroundXIncrement = 2;
        RenderGamePlay.get().foreGroundYIncrement = 0.5f;
        RenderGamePlay.get().animationLoops = 20;
        RenderGamePlay.get().ambientDirection = AnimationDirection.HORIZONTAL;
        RenderGamePlay.get().foregroundDirection = AnimationDirection.VERTICAL;
        RenderGamePlay.get().ambientMode = AmbientMode.BOTH_IN_BACKGROUND;
        RenderGamePlay.get().ambSpeed1 = 1;
        RenderGamePlay.get().ambSpeed2 = 2;
        ambientMusicIndex = 0;
    }

    protected void selectDistantIsleNight() {
        RenderGamePlay.get().foreGroundPositionX = -40;
        RenderGamePlay.get().foreGroundPositionY = 20;
        RenderGamePlay.get().foreGroundXIncrement = 2;
        RenderGamePlay.get().foreGroundYIncrement = 0.5f;
        RenderGamePlay.get().animationLoops = 20;
        RenderGamePlay.get().ambientDirection = AnimationDirection.HORIZONTAL;
        RenderGamePlay.get().foregroundDirection = AnimationDirection.VERTICAL;
        RenderGamePlay.get().ambientMode = AmbientMode.BOTH_IN_BACKGROUND;
        RenderGamePlay.get().ambSpeed1 = 1;
        RenderGamePlay.get().ambSpeed2 = 2;
        ambientMusicIndex = 1;
    }

    protected void selectHiddenCave() {
        RenderGamePlay.get().foreGroundPositionX = 0;
        RenderGamePlay.get().foreGroundPositionY = 0;
        RenderGamePlay.get().foreGroundXIncrement = 1;
        RenderGamePlay.get().foreGroundYIncrement = 1;
        RenderGamePlay.get().animationLoops = 20;
        RenderGamePlay.get().ambientDirection = AnimationDirection.NONE;
        RenderGamePlay.get().foregroundDirection = AnimationDirection.NONE;
        RenderGamePlay.get().ambientMode = AmbientMode.NONE;
        RenderGamePlay.get().ambSpeed1 = 0;
        RenderGamePlay.get().ambSpeed2 = 0;
        ambientMusicIndex = 2;
    }

    protected void selectHiddenCaveNight() {
        RenderGamePlay.get().foreGroundPositionX = 0;
        RenderGamePlay.get().foreGroundPositionY = 0;
        RenderGamePlay.get().foreGroundXIncrement = 1;
        RenderGamePlay.get().foreGroundYIncrement = 1;
        RenderGamePlay.get().animationLoops = 20;
        RenderGamePlay.get().ambientDirection = AnimationDirection.NONE;
        RenderGamePlay.get().foregroundDirection = AnimationDirection.NONE;
        RenderGamePlay.get().ambientMode = AmbientMode.NONE;
        RenderGamePlay.get().ambSpeed1 = 0;
        RenderGamePlay.get().ambSpeed2 = 0;
        ambientMusicIndex = 2;
    }

    protected void selectAfricanVillage() {
        RenderGamePlay.get().foreGroundPositionX = 0;
        RenderGamePlay.get().foreGroundPositionY = 0;
        RenderGamePlay.get().foreGroundXIncrement = 1;
        RenderGamePlay.get().foreGroundYIncrement = 1;
        RenderGamePlay.get().animationLoops = 20;
        RenderGamePlay.get().ambientDirection = AnimationDirection.HORIZONTAL;
        RenderGamePlay.get().foregroundDirection = AnimationDirection.NONE;
        RenderGamePlay.get().ambientMode = AmbientMode.BOTH_IN_BACKGROUND;
        RenderGamePlay.get().ambSpeed1 = 2;
        RenderGamePlay.get().ambSpeed2 = 1;
        ambientMusicIndex = 3;
    }

    protected void selectApocalypto() {
        RenderGamePlay.get().foreGroundPositionX = 0;
        RenderGamePlay.get().foreGroundPositionY = 0;
        RenderGamePlay.get().foreGroundXIncrement = 5;
        RenderGamePlay.get().foreGroundYIncrement = 1;
        RenderGamePlay.get().animationLoops = 4;
        RenderGamePlay.get().ambientDirection = AnimationDirection.HORIZONTAL;
        RenderGamePlay.get().foregroundDirection = AnimationDirection.NONE;
        RenderGamePlay.get().ambientMode = AmbientMode.BOTH_IN_FOREGROUND;
        RenderGamePlay.get().ambSpeed1 = 2;
        RenderGamePlay.get().ambSpeed2 = 1;
        ambientMusicIndex = 4;
    }

    protected void selectDesertRuinsNight() {
        RenderGamePlay.get().foreGroundPositionX = 0;
        RenderGamePlay.get().foreGroundPositionY = 0;
        RenderGamePlay.get().foreGroundXIncrement = 5;
        RenderGamePlay.get().foreGroundYIncrement = 1;
        RenderGamePlay.get().animationLoops = 4;
        RenderGamePlay.get().ambientDirection = AnimationDirection.HORIZONTAL;
        RenderGamePlay.get().foregroundDirection = AnimationDirection.NONE;
        RenderGamePlay.get().ambientMode = AmbientMode.BOTH_IN_FOREGROUND;
        RenderGamePlay.get().ambSpeed1 = 2;
        RenderGamePlay.get().ambSpeed2 = 1;
        ambientMusicIndex = 1;
    }

    protected void selectScorchedRuinsNight() {
        RenderGamePlay.get().foreGroundPositionX = 0;
        RenderGamePlay.get().foreGroundPositionY = 0;
        RenderGamePlay.get().foreGroundXIncrement = 5;
        RenderGamePlay.get().foreGroundYIncrement = 1;
        RenderGamePlay.get().animationLoops = 4;
        RenderGamePlay.get().ambientDirection = AnimationDirection.HORIZONTAL;
        RenderGamePlay.get().foregroundDirection = AnimationDirection.NONE;
        RenderGamePlay.get().ambientMode = AmbientMode.INDEPENDENT;
        RenderGamePlay.get().ambSpeed1 = 2;
        RenderGamePlay.get().ambSpeed2 = 1;
        ambientMusicIndex = 3;
    }

    public void start() {
        if (ScndGenLegends.get().getSubMode() != SubMode.STORY_MODE)
            RenderGamePlay.get().playBGMusic();
        ScndGenLegends.get().loadMode(ModeEnum.STANDARD_GAMEPLAY_START);
    }

    public String getStageBackground() {
        return stageBackground;
    }

    public String getStageForeground() {
        return stageForeground;
    }

    public String getFgLocation1() {
        return stageAmbient1;
    }

    public String getFgLocation2() {
        return stageAmbient2;
    }

    public void animateCaption() {
        opacity = 0.0f;
    }

    public Stage getHoveredStage() {
        return hoveredStage;
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

    public void mouseClicked(MouseEvent mouseEvent) {
        switch (mouseEvent.getButton()) {
            case PRIMARY:
                onAccept();
                break;
            case SECONDARY:
                onBackCancel();
                break;
            case MIDDLE:
                break;
        }
    }
}

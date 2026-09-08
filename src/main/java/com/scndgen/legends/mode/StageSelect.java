/**************************************************************************

 The SCND Genesis: Legends is a fighting game based on THE SCND GENESIS,
 a webcomic created by Ifunga Ndana (https://www.scndgen.com).

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
 along with The SCND Genesis: Legends. If not, see <https://www.gnu.org/licenses/>.

 **************************************************************************/
package com.scndgen.legends.mode;

import com.scndgen.legends.Language;
import com.scndgen.legends.ScndGenLegends;
import com.scndgen.legends.command.GameCommand;
import com.scndgen.legends.command.GameCommandBus;
import com.scndgen.legends.enums.*;
import com.scndgen.legends.render.RenderGamePlay;
import io.github.subiyacryolite.enginev2.Mode;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.glfw.GLFW.*;

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
    protected final Map<Integer, Stage> stageLookup = new HashMap<>();
    protected final Map<Stage, String> lookupStageNames = new HashMap<>();
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
            stagePreviews[stage.index()] = StageCatalog.layout(stage).previewPrefix();
        }
        reloadStageNames();
        Language.get().addLocaleListener(this::reloadStageNames);
    }

    private void reloadStageNames() {
        var language = Language.get();
        lookupStageNames.clear();
        for (var stage : Stage.values()) {
            lookupStageNames.put(stage, language.get(StageCatalog.layout(stage).name()));
        }
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
        var layout = StageCatalog.layout(stage);
        if (layout != null && stage != Stage.RANDOM) {
            layout.apply(RenderGamePlay.get());
            ambientMusicIndex = layout.musicIndex();
        }
    }

    public void start() {
        if (ScndGenLegends.get().getSubMode() != SubMode.STORY_MODE)
            RenderGamePlay.get().playBGMusic();
        GameCommandBus.get().dispatch(new GameCommand.LoadMode(ModeEnum.STANDARD_GAMEPLAY_START, true));
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

    @Override
    public void keyPressed(int glfwKey) {
        switch (glfwKey) {
            case GLFW_KEY_ENTER -> onAccept();
            case GLFW_KEY_ESCAPE, GLFW_KEY_BACKSPACE -> onBackCancel();
            case GLFW_KEY_UP, GLFW_KEY_W -> onUp();
            case GLFW_KEY_DOWN, GLFW_KEY_S -> onDown();
            case GLFW_KEY_LEFT, GLFW_KEY_A -> onLeft();
            case GLFW_KEY_RIGHT, GLFW_KEY_D -> onRight();
            default -> {
            }
        }
    }

    @Override
    public void mouseClicked(float x, float y) {
        onAccept();
    }
}

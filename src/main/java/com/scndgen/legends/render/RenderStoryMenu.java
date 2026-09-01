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
package com.scndgen.legends.render;

import com.scndgen.legends.Language;
import com.scndgen.legends.ScndGenLegends;
import com.scndgen.legends.UiConstants;
import com.scndgen.legends.Utils;
import com.scndgen.legends.enums.AudioType;
import com.scndgen.legends.enums.ModeEnum;
import com.scndgen.legends.mode.StoryMenu;
import com.scndgen.legends.mode.StoryMode;
import com.scndgen.legends.ui.Event;
import com.scndgen.legends.ui.UiAction;
import com.scndgen.legends.ui.UiItem;

import static com.scndgen.legends.ui.UiAction.HOVER;
import io.github.subiyacryolite.enginev2.Audio;
import io.github.subiyacryolite.enginev2.DrawContext;
import io.github.subiyacryolite.enginev2.NvgImage;
import io.github.subiyacryolite.enginev2.Rgba;

import java.util.HashMap;


/**
 * @author: Ifunga Ndana
 * @class: drawPrevChar
 * This class creates a graphical preview of the characterEnum and opponent
 */
public class RenderStoryMenu extends StoryMenu {

    private static RenderStoryMenu instance;
    private NvgImage stageHover, loading;
    private NvgImage[] unlockedScene, unlockedCaptions, lockedScene;
    private NvgImage storyPrev;
    private int hoveredScene;
    private final HashMap<Integer, UiItem> uiElements = new HashMap<>();
    private final UiItem scene1;
    private final UiItem scene2;
    private final UiItem scene3;
    private final UiItem scene4;
    private final UiItem scene5;
    private final UiItem scene6;
    private final UiItem scene7;
    private final UiItem scene8;
    private final UiItem scene9;
    private final UiItem scene10;
    private final UiItem scene11;
    private final UiItem scene12;
    private final UiItem scene13;
    private Audio menuMusic;

    public void onEnterMode() {
        super.onEnterMode();
        menuMusic = new Audio("audio/scotty/scotty zepplin - We Are.ogg", AudioType.MUSIC, true);
        menuMusic.play();
    }

    public void onLeaveMode() {
        menuMusic.stop(2000);
        super.onLeaveMode();
    }


    public RenderStoryMenu() {
        lockedScene = new NvgImage[numberOfScenes];
        unlockedScene = new NvgImage[numberOfScenes];
        unlockedCaptions = new NvgImage[numberOfScenes];
        unlockedStage = new boolean[numberOfScenes];
        for (int u = 0; u < unlockedStage.length; u++) {
            unlockedStage[u] = u <= currentScene;
        }
        //=======================================
        Event commonEvent = new Event() {
            @Override
            public void on(UiAction action) {
                switch (action) {
                    case HOVER -> animateCaption();
                    case ACCEPT -> selectScene();
                    case BACK_CANCEL -> ScndGenLegends.get().loadMode(ModeEnum.MAIN_MENU);
                    case DOWN -> setActiveItem(source.getDown());
                    case UP -> setActiveItem(source.getUp());
                    case LEFT -> setActiveItem(source.getLeft());
                    case RIGHT -> setActiveItem(source.getRight());
                    default -> {
                    }
                }
            }
        };
        bindScene(scene1 = new UiItem(), 0, commonEvent);
        bindScene(scene2 = new UiItem(), 1, commonEvent);
        bindScene(scene3 = new UiItem(), 2, commonEvent);
        bindScene(scene4 = new UiItem(), 3, commonEvent);
        bindScene(scene5 = new UiItem(), 4, commonEvent);
        bindScene(scene6 = new UiItem(), 5, commonEvent);
        bindScene(scene7 = new UiItem(), 6, commonEvent);
        bindScene(scene8 = new UiItem(), 7, commonEvent);
        bindScene(scene9 = new UiItem(), 8, commonEvent);
        bindScene(scene10 = new UiItem(), 9, commonEvent);
        bindScene(scene11 = new UiItem(), 10, commonEvent);
        bindScene(scene12 = new UiItem(), 11, commonEvent);
        bindScene(scene13 = new UiItem(), 12, commonEvent);
        uiElements.put(0, scene1);
        uiElements.put(1, scene2);
        uiElements.put(2, scene3);
        uiElements.put(3, scene4);
        uiElements.put(4, scene5);
        uiElements.put(5, scene6);
        uiElements.put(6, scene7);
        uiElements.put(7, scene8);
        uiElements.put(8, scene9);
        uiElements.put(9, scene10);
        uiElements.put(10, scene11);
        uiElements.put(11, scene12);
        uiElements.put(12, scene13);
        setActiveItem(scene1);

        //set up down, left right
        int total = uiElements.size();
        for (int index = 0; index < total; index++) {
            if (index > 0)
                uiElements.get(index).setLeft(uiElements.get(index - 1));
            if ((index + columns) < total)
                uiElements.get(index).setDown(uiElements.get(index + columns));
        }
    }

    private void selectScene(int hoveredScene) {
        this.hoveredScene = hoveredScene;
    }

    public static synchronized RenderStoryMenu get() {
        if (instance == null)
            instance = new RenderStoryMenu();
        return instance;
    }

    @Override
    public void render(DrawContext draw) {
        loadAssets();
        draw.setFill(Rgba.BLACK);
        draw.fillRect(0, 0, 852, 480);
        draw.drawImage(storyPrev, charXcap + x, charYcap);
        draw.setGlobalAlpha(0.7f);
        draw.fillRect(0, 0, 852, 480);
        draw.setGlobalAlpha(0.5f);
        draw.fillRect(200, 0, 452, 480);
        draw.setGlobalAlpha(1.0f);
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                int computedPosition = (row * columns) + column;
                if (computedPosition >= unlockedStage.length) continue;
                UiItem currentEl = uiElements.get(computedPosition);
                drawImage(draw, unlockedStage[computedPosition] ? unlockedScene[computedPosition] : lockedScene[computedPosition], commonXCoord + (hSpacer * column), commonYCoord + (vSpacer * row), currentEl);
                if (!currentEl.isHovered()) continue;
                draw.drawImage(stageHover, commonXCoord + (hSpacer * column), commonYCoord + (vSpacer * row));
                if (!unlockedStage[hoveredScene]) continue;
                if (opacity < 0.95f)
                    opacity += 0.05f;
                draw.setGlobalAlpha(opacity);
                draw.drawImage(unlockedCaptions[hoveredScene], commonXCoord + (hSpacer * column), commonYCoord + (vSpacer * row));
                draw.setGlobalAlpha(1.0f);
            }
            draw.setFill(Rgba.WHITE);
            setFont(draw, UiConstants.EXTRA_LARGE_TXT_SIZE);
            draw.fillText(Language.get().get(307), (852 - Utils.computeStringWidth(Language.get().get(307), draw) / 2), 80);
            setFont(draw, UiConstants.NORMAL_TXT_SIZE);
            draw.fillText(Language.get().get(368), (852 - Utils.computeStringWidth(Language.get().get(368), draw) / 2), 380);
            showstoryName(hoveredScene);
        }
    }

    public void selectScene() {
        if (validIndex(hoveredScene)) {
            StoryMode.get().startStoryMode(hoveredScene);
            new Audio("audio/menu-select.oga", AudioType.SOUND, false).play();
        } else {
            new Audio("audio/menu-select.oga", AudioType.SOUND, false).play();
        }
    }

    public void loadAssetsIml() {
        RenderStageSelect.get().setStageSelected(false);
        try {
            for (int i = 0; i < numberOfScenes; i++) {
                unlockedScene[i] = bag().loadImage("images/story/locked/" + i + ".png");
                unlockedCaptions[i] = bag().loadImage("images/story/captions/" + i + ".png");
                lockedScene[i] = bag().loadImage("images/story/blur/" + i + ".png");
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        loading = bag().loadImage("images/loading.gif");
        stageHover = bag().loadImage("images/story/frame.png");
        int random = (int) (Math.random() * 4);
        storyPrev = switch (random) {
            case 0 -> bag().loadImage("images/story/blur/s4.png");
            case 1 -> bag().loadImage("images/story/blur/s5.png");
            case 2 -> bag().loadImage("images/story/blur/s6.png");
            default -> bag().loadImage("images/story/blur/s6.png");
        };
        loadAssets = false;
    }

    public void cleanAssets() {
        stageHover = null;
        loading = null;
        storyPrev = null;
        if (unlockedScene != null) {
            java.util.Arrays.fill(unlockedScene, null);
        }
        if (unlockedCaptions != null) {
            java.util.Arrays.fill(unlockedCaptions, null);
        }
        if (lockedScene != null) {
            java.util.Arrays.fill(lockedScene, null);
        }
        super.cleanAssets();
    }

    public void newInstance() {
        super.newInstance();
    }

    @Override
    public void mouseClicked(float x, float y) {
        onAccept();
    }

    private void bindScene(UiItem item, int sceneIndex, Event commonEvent) {
        item.addJenesisEvent(commonEvent);
        item.addJenesisEvent(Event.on(HOVER, () -> hoveredScene = sceneIndex));
    }
}

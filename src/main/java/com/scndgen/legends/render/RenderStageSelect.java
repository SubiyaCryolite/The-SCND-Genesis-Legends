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
package com.scndgen.legends.render;

import com.scndgen.legends.Language;
import com.scndgen.legends.ScndGenLegends;
import com.scndgen.legends.UiConstants;
import com.scndgen.legends.Utils;
import com.scndgen.legends.command.GameCommand;
import com.scndgen.legends.command.GameCommandBus;
import com.scndgen.legends.enums.*;
import com.scndgen.legends.mode.StageSelect;
import com.scndgen.legends.network.NetworkManager;
import com.scndgen.legends.ui.Event;
import com.scndgen.legends.ui.UiItem;

import static com.scndgen.legends.ui.UiAction.HOVER;
import io.github.subiyacryolite.enginev2.Audio;
import io.github.subiyacryolite.enginev2.DrawContext;
import io.github.subiyacryolite.enginev2.NvgImage;
import io.github.subiyacryolite.enginev2.Rgba;

import java.util.HashMap;


public class RenderStageSelect extends StageSelect {

    private static RenderStageSelect instance;
    private String[] amnientMusicMetaData = {
            "\"KORNIKOVA\" by \"Scotty Zepplin\" from \"Stratos Halo\"", //0
            "\"Aeon AstriX\" by \"Scotty Zepplin\" from \"Stratos Halo\"", //1
            "\"Silikon Orchestra (guardian's awakening)\" by \"Scotty Zepplin\" from \"Stratos Halo\"", //2
            "\"Sirens Octave\" by \"Scotty Zepplin\" from \"Stratos Halo\"", //3
            "\"tRAVELLING FIREFLIES\" by \"Scotty Zepplin\" from \"Magical Untold Cookies\"", //4
            "\"We Are\" by \"Scotty Zepplin\" from \"Magical Untold Cookies\"", //5
            "\"Rays\" by \"Scotty Zepplin\""}; //6
    private String[] ambientMusic = {
            "scotty/KORNIKOVA",
            "scotty/Aeon AstriX",
            "scotty/Silikon Orchestra",
            "scotty/Sirens Octave",
            "scotty/Scotty Zepplin - tRAVELLING FIREFLIES",
            "scotty/scotty zepplin - We Are",
            "scotty/Scotty Zepplin - Rays"};
    private NvgImage captionHighlight, loading;
    private final HashMap<Integer, UiItem> uiElements = new HashMap<>();
    private final NvgImage[] stageCap = new NvgImage[numberOfStages];
    private final NvgImage[] stagePrev = new NvgImage[numberOfStages];
    private int hoveredStageIndex = -1;
    private Stage hoveredStage;
    private Audio menuMusic;

    public void onEnterMode() {
        menuMusic = new Audio("audio/scotty/Scotty Zepplin - i LOST MY SOUL.ogg", AudioType.MUSIC, true);
        menuMusic.play();
    }

    public void onLeaveMode() {
        menuMusic.stop(2000);
    }


    public RenderStageSelect() {
        Event commonEvent = Event.of(action -> {
            switch (action) {
                case HOVER -> animateCaption();
                case ACCEPT -> {
                    var networkManager = NetworkManager.get();
                    if (!(networkManager.isOffline() || networkManager.isServer())) {
                        return;
                    }
                    var gameCommandBus = GameCommandBus.get();
                    gameCommandBus.dispatch(new GameCommand.SelectStage(hoveredStage));
                    var subMode = ScndGenLegends.get().getSubMode();
                    if (subMode == SubMode.STORY_MODE
                            || subMode == SubMode.SINGLE_PLAYER
                            || subMode == SubMode.LAN_HOST
                            || subMode == SubMode.WATCH) {
                        gameCommandBus.dispatch(new GameCommand.StartMatch());
                    }
                }
                case BACK_CANCEL -> {
                    var networkManager = NetworkManager.get();
                    var gameCommandBus = GameCommandBus.get();
                    if (networkManager.isOffline()) {
                        gameCommandBus.dispatch(new GameCommand.GoToCharacterSelect(true));
                    } else if (networkManager.isServer()) {
                        gameCommandBus.dispatch(new GameCommand.GoToCharacterSelect(false));
                    }
                }
                default -> {
                }
            }
        });

        UiItem ibexHill;
        bindStage(ibexHill = new UiItem(), Stage.IBEX_HILL, commonEvent);
        UiItem chelstonCityDocks;
        bindStage(chelstonCityDocks = new UiItem(), Stage.CHELSTON_CITY_DOCKS, commonEvent);
        UiItem desertRuins;
        bindStage(desertRuins = new UiItem(), Stage.DESERT_RUINS, commonEvent);
        UiItem chelstonCityStreets;
        bindStage(chelstonCityStreets = new UiItem(), Stage.CHELSTON_CITY_STREETS, commonEvent);
        UiItem ibexHillNight;
        bindStage(ibexHillNight = new UiItem(), Stage.IBEX_HILL_NIGHT, commonEvent);
        UiItem scorchedRuins;
        bindStage(scorchedRuins = new UiItem(), Stage.SCORCHED_RUINS, commonEvent);
        UiItem frozenWilderness;
        bindStage(frozenWilderness = new UiItem(), Stage.FROZEN_WILDERNESS, commonEvent);
        UiItem distantIsle;
        bindStage(distantIsle = new UiItem(), Stage.DISTANT_ISLE, commonEvent);
        UiItem hiddenCave;
        bindStage(hiddenCave = new UiItem(), Stage.HIDDEN_CAVE, commonEvent);
        UiItem africanVillage;
        bindStage(africanVillage = new UiItem(), Stage.AFRICAN_VILLAGE, commonEvent);
        UiItem apocalypto;
        bindStage(apocalypto = new UiItem(), Stage.APOCALYPTO, commonEvent);
        UiItem distantIsleNight;
        bindStage(distantIsleNight = new UiItem(), Stage.DISTANT_ISLE_NIGHT, commonEvent);
        UiItem desertRuinsNight;
        bindStage(desertRuinsNight = new UiItem(), Stage.DESERT_RUINS_NIGHT, commonEvent);
        UiItem scorchedRuinsNight;
        bindStage(scorchedRuinsNight = new UiItem(), Stage.SCORCHED_RUINS_NIGHT, commonEvent);
        UiItem hiddenCaveNight;
        bindStage(hiddenCaveNight = new UiItem(), Stage.HIDDEN_CAVE_NIGHT, commonEvent);
        UiItem random;
        bindStage(random = new UiItem(), Stage.RANDOM, commonEvent);

        uiElements.put(Stage.IBEX_HILL.index(), ibexHill);
        uiElements.put(Stage.CHELSTON_CITY_DOCKS.index(), chelstonCityDocks);
        uiElements.put(Stage.DESERT_RUINS.index(), desertRuins);
        uiElements.put(Stage.CHELSTON_CITY_STREETS.index(), chelstonCityStreets);
        uiElements.put(Stage.IBEX_HILL_NIGHT.index(), ibexHillNight);
        uiElements.put(Stage.SCORCHED_RUINS.index(), scorchedRuins);
        uiElements.put(Stage.FROZEN_WILDERNESS.index(), frozenWilderness);
        uiElements.put(Stage.DISTANT_ISLE.index(), distantIsle);
        uiElements.put(Stage.HIDDEN_CAVE.index(), hiddenCave);
        uiElements.put(Stage.AFRICAN_VILLAGE.index(), africanVillage);
        uiElements.put(Stage.APOCALYPTO.index(), apocalypto);
        uiElements.put(Stage.DISTANT_ISLE_NIGHT.index(), distantIsleNight);
        uiElements.put(Stage.DESERT_RUINS_NIGHT.index(), desertRuinsNight);
        uiElements.put(Stage.SCORCHED_RUINS_NIGHT.index(), scorchedRuinsNight);
        uiElements.put(Stage.HIDDEN_CAVE_NIGHT.index(), hiddenCaveNight);
        uiElements.put(Stage.RANDOM.index(), random);

        //set up down, left right
        int total = uiElements.size();
        for (int index = 0; index < total; index++) {
            if (index > 0)
                uiElements.get(index).setLeft(uiElements.get(index - 1));
            if ((index + columns) < total)
                uiElements.get(index).setDown(uiElements.get(index + columns));
        }
    }

    public static synchronized RenderStageSelect get() {
        if (instance == null)
            instance = new RenderStageSelect();
        return instance;
    }

    @Override
    public void loadAssetsIml() {
        loadCaps();
        setActiveItem(uiElements.get(0));
        loadAssets = false;
    }

    @Override
    public void cleanAssets() {
        captionHighlight = null;
        loading = null;
        java.util.Arrays.fill(stageCap, null);
        java.util.Arrays.fill(stagePrev, null);
        super.cleanAssets();
    }

    @Override
    public void render(DrawContext draw) {
        loadAssets();
        var scndGenLegends = ScndGenLegends.get();
        if (opacity < 0.98f) {
            opacity = opacity + 0.02f;
        }
        if (stageSelected) {
            draw.setFill(Rgba.BLACK);
            draw.drawImage(stagePrev[hoveredStage.index()], 0, 0);
            draw.setGlobalAlpha(0.7f);
            draw.fillRect(0, 0, 852, 480);
            draw.setGlobalAlpha(1.0f);
            draw.setGlobalAlpha(0.5f);
            draw.fillRect(200, 0, 452, 480);
            draw.setGlobalAlpha(1.0f);
            draw.drawImage(loading, 316, 183); //yCord = 286 - icoHeight
            draw.setFill(Rgba.WHITE);
            setFont(draw, UiConstants.NORMAL_TXT_SIZE);
            var lang165 = Language.get().get(165);
            draw.fillText(lang165, (852 - Utils.computeStringWidth(lang165, draw)) / 2, 200);
        } else if (scndGenLegends.getSubMode() == SubMode.LAN_CLIENT && !stageSelected) {
            setFont(draw, UiConstants.NORMAL_TXT_SIZE);
            draw.setFill(Rgba.BLACK);
            draw.fillRect(0, 0, 852, 480);
            draw.setFill(Rgba.WHITE);
            var waiting = ">> " + Language.get().get(166) + " <<";
            draw.fillText(waiting, (852 - Utils.computeStringWidth(waiting, draw)) / 2, 300);
        } else if (scndGenLegends.getSubMode() != SubMode.LAN_CLIENT) {
            setFont(draw, UiConstants.NORMAL_TXT_SIZE);
            draw.setFill(Rgba.BLACK);
            draw.fillRect(0, 0, 852, 480);
            draw.setGlobalAlpha(opacity);
            draw.drawImage(stagePrev[hoveredStage.index()], 0, 0);
            draw.setGlobalAlpha(1.0f);
            draw.setGlobalAlpha(0.5f);
            draw.fillRoundRect(283, 0, 285, 480, 30);
            draw.setGlobalAlpha(1.0f);
            for (int row = 0; row <= rows; row++) {
                for (int column = 0; column < columns; column++) {
                    int computedPosition = (row * columns) + column;
                    if (computedPosition >= stageCap.length) continue;
                    drawImage(draw, stageCap[computedPosition], hPos + (hSpacer * column), firstLine + (vSpacer * row), uiElements.get(computedPosition));
                    if (uiElements.get(computedPosition).isHovered()) {
                        showStageName(hoveredStage);
                        draw.drawImage(captionHighlight, hPos + (hSpacer * column), firstLine + (vSpacer * row));
                    }
                }
            }
        }
    }

    private void loadCaps() {
        try {
            captionHighlight = bag().loadImage("images/stageCaptionHighlight.png");
            loading = bag().loadImage("images/loading.gif");
            for (int index = 0; index < stagePreviews.length; index++) {
                stageCap[index] = bag().loadImage("images/t_" + stagePreviews[index] + ".png");
                stagePrev[index] = bag().loadImage("images/prev/" + stagePreviews[index] + ".jpg");
            }
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
    }

    private void showStageName(Stage stage) {
        if (stage.index() == hoveredStageIndex) return;
        animateCaption();
        primaryNotice(lookupStageNames.get(stage));
        hoveredStageIndex = stage.index();
        if (hoveredStageIndex == Stage.RANDOM.index()) {
            stageSelectionMode = StageSelectionMode.RANDOM;
        } else {
            stageSelectionMode = StageSelectionMode.NORMAL;
        }
    }

    public String getTrack() {
        return ambientMusic[ambientMusicIndex];
    }


    public String[] getAmbientMusic() {
        return ambientMusic;
    }

    public int getAmbientMusicIndex() {
        return ambientMusicIndex;
    }

    public boolean isStageSelected() {
        return stageSelected;
    }

    public void setStageSelected(boolean value) {
        stageSelected = value;
    }

    public String[] getAmbientMusicMetaData() {
        return amnientMusicMetaData;
    }

    public void newInstance() {
        super.newInstance();
        stageSelected = false;
    }

    private void bindStage(UiItem item, Stage stage, Event commonEvent) {
        item.addJenesisEvent(Event.on(HOVER, () -> hoveredStage = stage));
        item.addJenesisEvent(commonEvent);
    }

}

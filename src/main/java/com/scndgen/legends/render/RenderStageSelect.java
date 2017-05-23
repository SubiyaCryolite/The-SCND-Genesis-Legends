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
import com.scndgen.legends.ScndGenLegends;
import com.scndgen.legends.enums.ModeEnum;
import com.scndgen.legends.enums.Stage;
import com.scndgen.legends.enums.StageSelectionMode;
import com.scndgen.legends.enums.SubMode;
import com.scndgen.legends.mode.StageSelect;
import com.scndgen.legends.ui.Event;
import com.scndgen.legends.ui.UiItem;
import io.github.subiyacryolite.enginev1.Loader;
import io.github.subiyacryolite.enginev1.Overlay;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.HashMap;

import static com.sun.javafx.tk.Toolkit.getToolkit;


public class RenderStageSelect extends StageSelect {

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
    private Image captionHighlight, loading;
    private Loader loader;
    private final HashMap<Integer, UiItem> uiElements = new HashMap<>();
    private final Image[] stageCap = new Image[numberOfStages];
    private final Image[] stagePrev = new Image[numberOfStages];
    private Font normalFont;
    private int hoveredStageIndex = -1;
    private Stage hoveredStage;
    private final UiItem ibexHill;
    private final UiItem chelstonCityDocks;
    private final UiItem desertRuins;
    private final UiItem chelstonCityStreets;
    private final UiItem ibexHillNight;
    private final UiItem scorchedRuins;
    private final UiItem frozenWilderness;
    private final UiItem distantIsle;
    private final UiItem hiddenCave;
    private final UiItem africanVillage;
    private final UiItem apocalypto;
    private final UiItem distantIsleNight;
    private final UiItem desertRuinsNight;
    private final UiItem scorchedRuinsNight;
    private final UiItem hiddenCaveNight;
    private final UiItem random;

    public RenderStageSelect() {
        Event commonEvent = new Event() {
            public void onHover() {
                animateCaption();
            }

            public void onAccept() {
                selectStage(hoveredStage);
            }

            public void onBackCancel() {
                ScndGenLegends.getInstance().loadMode(ModeEnum.CHAR_SELECT_SCREEN);
            }
        };


        (ibexHill = new UiItem()).addJenesisEvent(new Event() {
            public void onHover() {
                hoveredStage = Stage.IBEX_HILL;
            }
        });
        ibexHill.addJenesisEvent(commonEvent);
        ///////////////////
        (chelstonCityDocks = new UiItem()).addJenesisEvent(new Event() {
            public void onHover() {
                hoveredStage = Stage.CHELSTON_CITY_DOCKS;
            }
        });
        chelstonCityDocks.addJenesisEvent(commonEvent);
        ///////////////////
        (desertRuins = new UiItem()).addJenesisEvent(new Event() {
            public void onHover() {
                hoveredStage = Stage.DESERT_RUINS;
            }
        });
        desertRuins.addJenesisEvent(commonEvent);
        ///////////////////
        (chelstonCityStreets = new UiItem()).addJenesisEvent(new Event() {
            public void onHover() {
                hoveredStage = Stage.CHELSTON_CITY_STREETS;
            }
        });
        chelstonCityStreets.addJenesisEvent(commonEvent);
        ///////////////////
        (ibexHillNight = new UiItem()).addJenesisEvent(new Event() {
            public void onHover() {
                hoveredStage = Stage.IBEX_HILL_NIGHT;
            }
        });
        ibexHillNight.addJenesisEvent(commonEvent);
        ///////////////////
        (scorchedRuins = new UiItem()).addJenesisEvent(new Event() {
            public void onHover() {
                hoveredStage = Stage.SCORCHED_RUINS;
            }
        });
        scorchedRuins.addJenesisEvent(commonEvent);
        ///////////////////
        (frozenWilderness = new UiItem()).addJenesisEvent(new Event() {
            public void onHover() {
                hoveredStage = Stage.FROZEN_WILDERNESS;
            }
        });
        frozenWilderness.addJenesisEvent(commonEvent);
        ///////////////////
        (distantIsle = new UiItem()).addJenesisEvent(new Event() {
            public void onHover() {
                hoveredStage = Stage.DISTANT_ISLE;
            }
        });
        distantIsle.addJenesisEvent(commonEvent);
        ///////////////////
        (hiddenCave = new UiItem()).addJenesisEvent(new Event() {
            public void onHover() {
                hoveredStage = Stage.HIDDEN_CAVE;
            }
        });
        hiddenCave.addJenesisEvent(commonEvent);
        ///////////////////
        (africanVillage = new UiItem()).addJenesisEvent(new Event() {
            public void onHover() {
                hoveredStage = Stage.AFRICAN_VILLAGE;
            }
        });
        africanVillage.addJenesisEvent(commonEvent);
        ///////////////////
        (apocalypto = new UiItem()).addJenesisEvent(new Event() {
            public void onHover() {
                hoveredStage = Stage.APOCALYPTO;
            }
        });
        apocalypto.addJenesisEvent(commonEvent);
        ///////////////////
        (distantIsleNight = new UiItem()).addJenesisEvent(new Event() {
            public void onHover() {
                hoveredStage = Stage.DISTANT_ISLE_NIGHT;
            }
        });
        distantIsleNight.addJenesisEvent(commonEvent);
        ///////////////////
        (desertRuinsNight = new UiItem()).addJenesisEvent(new Event() {
            public void onHover() {
                hoveredStage = Stage.DESERT_RUINS_NIGHT;
            }
        });
        desertRuinsNight.addJenesisEvent(commonEvent);
        ///////////////////
        (scorchedRuinsNight = new UiItem()).addJenesisEvent(new Event() {
            public void onHover() {
                hoveredStage = Stage.SCORCHED_RUINS_NIGHT;
            }
        });
        scorchedRuinsNight.addJenesisEvent(commonEvent);
        ///////////////////
        (hiddenCaveNight = new UiItem()).addJenesisEvent(new Event() {
            public void onHover() {
                hoveredStage = Stage.HIDDEN_CAVE_NIGHT;
            }
        });
        hiddenCaveNight.addJenesisEvent(commonEvent);
        ///////////////////
        (random = new UiItem()).addJenesisEvent(new Event() {
            public void onHover() {
                hoveredStage = Stage.RANDOM;
            }
        });
        random.addJenesisEvent(commonEvent);

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

    public static synchronized RenderStageSelect getInstance() {
        if (instance == null)
            instance = new RenderStageSelect();
        return instance;
    }

    @Override
    public void loadAssetsIml() {
        loader = new Loader();
        normalFont = getMyFont(LoginScreen.normalTxtSize);
        loadCaps();
        setActiveItem(uiElements.get(0));
        loadAssets = false;
    }

    @Override
    public void cleanAssets() {
        loadAssets = true;
    }

    @Override
    public void render(GraphicsContext gc, double x, double y) {
        loadAssets();
        if (opacity < 0.98f) {
            opacity = opacity + 0.02f;
        }
        if (stageSelected) {
            gc.setFill(Color.BLACK);
            gc.drawImage(stagePrev[hoveredStage.index()], 0, 0);
            gc.setGlobalAlpha((0.7f));
            gc.fillRect(0, 0, 852, 480);
            gc.setGlobalAlpha((1.0f));
            gc.setGlobalAlpha((0.5f));
            gc.fillRect(200, 0, 452, 480);
            gc.setGlobalAlpha((1.0f));
            gc.drawImage(loading, 316, 183); //yCord = 286 - icoHeight
            gc.setFill(Color.WHITE);
            gc.fillText(Language.getInstance().get(165), (852 - getToolkit().getFontLoader().computeStringWidth(Language.getInstance().get(165), gc.getFont())) / 2, 200);
        } else if (ScndGenLegends.getInstance().getSubMode() == SubMode.LAN_CLIENT && !stageSelected) {
            gc.setFont(normalFont);
            gc.setFill(Color.BLACK);
            gc.fillRect(0, 0, 852, 480);
            gc.setFill(Color.WHITE);
            gc.fillText(">> " + Language.getInstance().get(166) + " <<", (852 - getToolkit().getFontLoader().computeStringWidth(">> " + Language.getInstance().get(166) + " <<", gc.getFont())) / 2, 300);
        } else if (ScndGenLegends.getInstance().getSubMode() == SubMode.LAN_CLIENT == false) {
            gc.setFont(normalFont);
            gc.setFill(Color.BLACK);
            gc.fillRect(0, 0, 852, 480);
            gc.setGlobalAlpha(opacity);
            gc.drawImage(stagePrev[hoveredStage.index()], 0, 0);
            gc.setGlobalAlpha((1.0f));
            gc.setGlobalAlpha((5 * 0.1F));
            gc.fillRoundRect(283, 0, 285, 480, 30, 30);
            gc.setGlobalAlpha((10 * 0.1F));
            for (int row = 0; row <= rows; row++) {
                for (int column = 0; column < columns; column++) {
                    int computedPosition = (row * columns) + column;
                    if (computedPosition >= stageCap.length) continue;
                    drawImage(gc, stageCap[computedPosition], hPos + (hSpacer * column), firstLine + (vSpacer * row), uiElements.get(computedPosition));
                    if (uiElements.get(computedPosition).isHovered()) {
                        showStageName(hoveredStage);
                        gc.drawImage(captionHighlight, hPos + (hSpacer * column), firstLine + (vSpacer * row));
                    }
                }
            }
        }
        Overlay.getInstance().overlay(gc, x, y);
    }

    private void loadCaps() {
        try {
            captionHighlight = loader.load("images/stageCaptionHighlight.png");
            loading = loader.load("images/loading.gif");
            for (int index = 0; index < stagePreviews.length; index++) {
                stageCap[index] = loader.load("images/t_" + stagePreviews[index] + ".png");
                stagePrev[index] = loader.load("images/prev/" + stagePreviews[index] + ".jpg");
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
            mode = StageSelectionMode.RANDOM;
        } else {
            mode = StageSelectionMode.NORMAL;
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
    }

}

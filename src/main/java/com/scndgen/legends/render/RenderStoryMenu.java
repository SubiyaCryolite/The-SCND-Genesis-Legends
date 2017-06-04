/**************************************************************************

 The SCND Genesis: Legends is a fighting game based on THE SCND GENESIS,
 a webcomic created by Ifunga Ndana ((([http://www.scndgen.com]))).

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
 along with The SCND Genesis: Legends. If not, see <http://www.gnu.org/licenses/>.

 **************************************************************************/
package com.scndgen.legends.render;

import com.scndgen.legends.Language;
import com.scndgen.legends.LoginScreen;
import com.scndgen.legends.ScndGenLegends;
import com.scndgen.legends.enums.AudioType;
import com.scndgen.legends.enums.ModeEnum;
import com.scndgen.legends.mode.StoryMenu;
import com.scndgen.legends.mode.StoryMode;
import com.scndgen.legends.ui.Event;
import com.scndgen.legends.ui.UiItem;
import io.github.subiyacryolite.enginev1.AudioPlayback;
import io.github.subiyacryolite.enginev1.Loader;
import io.github.subiyacryolite.enginev1.Overlay;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.HashMap;

import static com.sun.javafx.tk.Toolkit.getToolkit;


/**
 * @author: Ifunga Ndana
 * @class: drawPrevChar
 * This class creates a graphical preview of the characterEnum and opponent
 */
public class RenderStoryMenu extends StoryMenu {

    private static RenderStoryMenu instance;
    private Font header, normal;
    private Image stageHover, loading;
    private Image[] unlockedScene, unlockedCaptions, lockedScene;
    private Image storyPrev;
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
    private AudioPlayback menuMusic;

    public void onEnterMode() {
        menuMusic = new AudioPlayback("audio/scotty/scotty zepplin - We Are.ogg", AudioType.MUSIC, true);
        menuMusic.play();
    }

    public void onLeaveMode() {
        menuMusic.stop(2000);
    }


    public RenderStoryMenu() {
        lockedScene = new Image[numberOfScenes];
        unlockedScene = new Image[numberOfScenes];
        unlockedCaptions = new Image[numberOfScenes];
        unlockedStage = new boolean[numberOfScenes];
        for (int u = 0; u < unlockedStage.length; u++) {
            unlockedStage[u] = u <= currentScene;
        }
        //=======================================
        Event commonEvent = new Event() {
            public void onHover() {
                animateCaption();
            }

            public void onAccept() {
                selectScene();
            }

            public void onBackCancel() {
                ScndGenLegends.get().loadMode(ModeEnum.MAIN_MENU);
            }

            @Override
            public void onDown() {
                setActiveItem(source.getDown());
            }

            @Override
            public void onUp() {
                setActiveItem(source.getUp());
            }

            @Override
            public void onLeft() {
                setActiveItem(source.getLeft());
            }

            @Override
            public void onRight() {
                setActiveItem(source.getRight());
            }
        };
        (scene1 = new UiItem()).addJenesisEvent(commonEvent);
        scene1.addJenesisEvent(new Event() {
            public void onHover() {
                hoveredScene = 0;
            }
        });
        (scene2 = new UiItem()).addJenesisEvent(commonEvent);
        scene2.addJenesisEvent(new Event() {
            public void onHover() {
                hoveredScene = 1;
            }
        });
        (scene3 = new UiItem()).addJenesisEvent(commonEvent);
        scene3.addJenesisEvent(new Event() {
            public void onHover() {
                hoveredScene = 2;
            }
        });
        (scene4 = new UiItem()).addJenesisEvent(commonEvent);
        scene4.addJenesisEvent(new Event() {
            public void onHover() {
                hoveredScene = 3;
            }
        });
        (scene5 = new UiItem()).addJenesisEvent(commonEvent);
        scene5.addJenesisEvent(new Event() {
            public void onHover() {
                hoveredScene = 4;
            }
        });
        (scene6 = new UiItem()).addJenesisEvent(commonEvent);
        scene6.addJenesisEvent(new Event() {
            public void onHover() {
                hoveredScene = 5;
            }
        });
        (scene7 = new UiItem()).addJenesisEvent(commonEvent);
        scene7.addJenesisEvent(new Event() {
            public void onHover() {
                hoveredScene = 6;
            }
        });
        (scene8 = new UiItem()).addJenesisEvent(commonEvent);
        scene8.addJenesisEvent(new Event() {
            public void onHover() {
                hoveredScene = 7;
            }
        });
        (scene9 = new UiItem()).addJenesisEvent(commonEvent);
        scene9.addJenesisEvent(new Event() {
            public void onHover() {
                hoveredScene = 8;
            }
        });
        (scene10 = new UiItem()).addJenesisEvent(commonEvent);
        scene10.addJenesisEvent(new Event() {
            public void onHover() {
                hoveredScene = 9;
            }
        });
        (scene11 = new UiItem()).addJenesisEvent(commonEvent);
        scene11.addJenesisEvent(new Event() {
            public void onHover() {
                hoveredScene = 10;
            }
        });
        (scene12 = new UiItem()).addJenesisEvent(commonEvent);
        scene12.addJenesisEvent(new Event() {
            public void onHover() {
                hoveredScene = 11;
            }
        });
        (scene13 = new UiItem()).addJenesisEvent(commonEvent);
        scene13.addJenesisEvent(new Event() {
            public void onHover() {
                hoveredScene = 12;
            }
        });
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
    public void render(GraphicsContext gc, final double w, final double h) {
        loadAssets();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, 852, 480);
        gc.drawImage(storyPrev, charXcap + x, charYcap);
        gc.setGlobalAlpha((0.7f));
        gc.fillRect(0, 0, 852, 480);
        gc.setGlobalAlpha((0.5f));
        gc.fillRect(200, 0, 452, 480);
        gc.setGlobalAlpha((1.0f));
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                int computedPosition = (row * columns) + column;
                if (computedPosition >= unlockedStage.length) continue;
                UiItem currentEl = uiElements.get(computedPosition);
                drawImage(gc, unlockedStage[computedPosition] ? unlockedScene[computedPosition] : lockedScene[computedPosition], commonXCoord + (hSpacer * column), commonYCoord + (vSpacer * row), currentEl);
                if (!currentEl.isHovered()) continue;
                gc.drawImage(stageHover, commonXCoord + (hSpacer * column), commonYCoord + (vSpacer * row));
                if (!unlockedStage[hoveredScene]) continue;
                if (opacity < 0.95f)
                    opacity += 0.05f;
                gc.setGlobalAlpha((opacity));
                gc.drawImage(unlockedCaptions[hoveredScene], commonXCoord + (hSpacer * column), commonYCoord + (vSpacer * row));
                gc.setGlobalAlpha((1.0f));
            }
            gc.setFill(Color.WHITE);
            gc.setFont(header);
            gc.fillText(Language.get().get(307), (852 - getToolkit().getFontLoader().computeStringWidth(Language.get().get(307), gc.getFont()) / 2), 80);
            gc.setFont(normal);
            gc.fillText(Language.get().get(368), (852 - getToolkit().getFontLoader().computeStringWidth(Language.get().get(368), gc.getFont()) / 2), 380);
            showstoryName(hoveredScene);
        }
        Overlay.get().overlay(gc, x, y);
    }

    public void selectScene() {
        if (validIndex(hoveredScene)) {
            StoryMode.get().startStoryMode(hoveredScene);
            new AudioPlayback("audio/menu-select.oga", AudioType.SOUND, false).play();
        } else {
            new AudioPlayback("audio/menu-select.oga", AudioType.SOUND, false).play();
        }
    }

    public void loadAssetsIml() {
        header = loadFont(LoginScreen.EXTRA_LARGE_TXT_SIZE);
        normal = loadFont(LoginScreen.NORMAL_TXT_SIZE);
        Loader loader = new Loader();
        RenderStageSelect.get().setStageSelected(false);
        try {
            for (int i = 0; i < numberOfScenes; i++) {
                unlockedScene[i] = loader.load("images/story/locked/" + i + ".png");
                unlockedCaptions[i] = loader.load("images/story/captions/" + i + ".png");
                lockedScene[i] = loader.load("images/story/blur/" + i + ".png");
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        loading = loader.load("images/loading.gif");
        stageHover = loader.load("images/story/frame.png");
        int random = (int) (Math.random() * 4);
        switch (random) {
            case 0:
                storyPrev = loader.load("images/story/blur/s4.png");
                break;
            case 1:
                storyPrev = loader.load("images/story/blur/s5.png");
                break;
            case 2:
                storyPrev = loader.load("images/story/blur/s6.png");
                break;
            default:
                storyPrev = loader.load("images/story/blur/s6.png");
                break;
        }
        loadAssets = false;
    }

    public void cleanAssets() {
        header = null;
        normal = null;
        stageHover = null;
        loading = null;
        unlockedCaptions = null;
        lockedScene = null;
        storyPrev = null;
        loadAssets = true;
    }

    public void newInstance() {
        super.newInstance();
    }

    public void mouseClicked(MouseEvent mouseEvent) {
        switch (mouseEvent.getButton()) {
            case PRIMARY:
                onAccept();
                break;
            case SECONDARY:
                onBackCancel();
                break;
        }
    }
}

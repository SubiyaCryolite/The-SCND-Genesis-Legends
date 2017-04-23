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
import com.scndgen.legends.enums.SubMode;
import com.scndgen.legends.scene.StoryMenu;
import com.scndgen.legends.threads.AudioPlayback;
import io.github.subiyacryolite.enginev1.JenesisOverlay;
import io.github.subiyacryolite.enginev1.JenesisImageLoader;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import static com.sun.javafx.tk.Toolkit.getToolkit;


/**
 * @author: Ifunga Ndana
 * @class: drawPrevChar
 * This class creates a graphical preview of the characterEnum and opponent
 */
public class RenderStoryMenu extends StoryMenu {

    private static RenderStoryMenu instance;
    private Font header, normal;
    private Image charBack, loading;
    private Image[] unlockedScene, unlockedCaptions, lockedScene;
    private Image storyPrev;

    private RenderStoryMenu() {
        lockedScene = new Image[scenes];
        unlockedScene = new Image[scenes];
        unlockedCaptions = new Image[scenes];
        unlockedStage = new boolean[scenes];
        for (int u = 0; u < unlockedStage.length; u++) {
            unlockedStage[u] = u <= currentScene;
        }
    }

    public static synchronized RenderStoryMenu getInstance() {
        if (instance == null)
            instance = new RenderStoryMenu();
        return instance;
    }

    @Override
    public void render(GraphicsContext gc, final double w, final double h) {
        loadAssets();
        if (loadingNow) {
            gc.setFill(Color.BLACK);
            gc.drawImage(storyPrev, charXcap + x, charYcap);
            gc.setGlobalAlpha((0.7f));
            gc.fillRect(0, 0, 852, 480);
            gc.setGlobalAlpha((1.0f));
            gc.setGlobalAlpha((0.5f));
            gc.fillRect(200, 0, 452, 480);
            gc.setGlobalAlpha((1.0f));
            gc.drawImage(loading, 316, 183); //yCord = 286 - icoHeight
            gc.setFill(Color.WHITE);
        } else if (ScndGenLegends.getInstance().getSubMode() != SubMode.LAN_CLIENT) {
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
                    gc.drawImage(unlockedStage[computedPosition] ? unlockedScene[computedPosition] : lockedScene[computedPosition], commonXCoord + (hSpacer * column), commonYCoord + (vSpacer * row));
                }
            }
            gc.setFill(Color.WHITE);
            gc.setFont(header);
            gc.fillText(Language.getInstance().get(307), (852 - getToolkit().getFontLoader().computeStringWidth(Language.getInstance().get(307), gc.getFont()) / 2), 80);
            gc.setFont(normal);
            gc.fillText(Language.getInstance().get(368), (852 - getToolkit().getFontLoader().computeStringWidth(Language.getInstance().get(368), gc.getFont()) / 2), 380);
            showstoryName(hoveredStoryIndex);
            if (isUnlocked() && unlockedStage[hoveredStoryIndex]) {
                if (opacity < 0.98f)
                    opacity = opacity + 0.02f;
                gc.setGlobalAlpha((opacity));
                gc.drawImage(unlockedCaptions[hoveredStoryIndex], (commonXCoord - hSpacer) + (hSpacer * row), commonYCoord + (vSpacer * column));
                gc.setGlobalAlpha((1.0f));
                gc.drawImage(charBack, (commonXCoord - hSpacer) + (hSpacer * row), commonYCoord + (vSpacer * column));
            }
        }
        JenesisOverlay.getInstance().overlay(gc,x,y);
    }

    public void loadAssets() {
        if (!loadAssets) return;
        header = getMyFont(LoginScreen.extraTxtSize);
        normal = getMyFont(LoginScreen.normalTxtSize);
        victorySound = new AudioPlayback(AudioPlayback.soundGameOver(), true);
        menuSound = new AudioPlayback("audio/menu-select.mp3", true);
        JenesisImageLoader imageLoader = new JenesisImageLoader();
        RenderStageSelect.getInstance().setSelectedStage(false);
        try {
            for (int i = 0; i < scenes; i++) {
                unlockedScene[i] = imageLoader.loadImage("images/story/locked/" + i + ".png");
                unlockedCaptions[i] = imageLoader.loadImage("images/story/captions/" + i + ".png");
                lockedScene[i] = imageLoader.loadImage("images/story/blur/" + i + ".png");
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        //charBack = imageLoader.loadImage("images/selstory.png");
        loading = imageLoader.loadImage("images/loading.gif");
        charBack = imageLoader.loadImage("images/story/frame.png");
        int random = (int) (Math.random() * 4);
        switch (random) {
            case 0:
                storyPrev = imageLoader.loadImage("images/story/blur/s4.png");
                break;
            case 1:
                storyPrev = imageLoader.loadImage("images/story/blur/s5.png");
                break;
            case 2:
                storyPrev = imageLoader.loadImage("images/story/blur/s6.png");
                break;
            default:
                storyPrev = imageLoader.loadImage("images/story/blur/s6.png");
                break;
        }
        loadAssets = false;
    }

    public void cleanAssets() {
        header = null;
        normal = null;
        charBack = null;
        loading = null;
        for (Image image : unlockedScene) {
            image = null;
        }
        for (Image image : unlockedCaptions) {
            image = null;
        }
        for (Image image : lockedScene) {
            image = null;
        }
        storyPrev = null;
        loadAssets = true;
    }

    public void newInstance() {
        super.newInstance();
    }


}

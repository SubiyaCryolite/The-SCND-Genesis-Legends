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
import com.scndgen.legends.controller.StoryMode;
import com.scndgen.legends.enums.SubMode;
import com.scndgen.legends.scene.StoryMenu;
import com.scndgen.legends.threads.AudioPlayback;
import com.scndgen.legends.windows.JenesisPanel;
import io.github.subiyacryolite.enginev1.JenesisGlassPane;
import io.github.subiyacryolite.enginev1.JenesisImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;

/**
 * @author: Ifunga Ndana
 * @class: drawPrevChar
 * This class creates a graphical preview of the characterEnum and opponent
 */
public class RenderStoryMenu extends StoryMenu {

    private static RenderStoryMenu instance;
    private AudioPlayback victorySound;
    private AudioPlayback menuSound;
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
    public void paintComponent(Graphics2D g2d, ImageObserver io) {
        loadAssets();
        if (loadingNow) {
            g2d.setColor(Color.BLACK);
            g2d.drawImage(storyPrev, charXcap + x, charYcap, io);
            g2d.setComposite(makeComposite(0.7f));
            g2d.fillRect(0, 0, 852, 480);
            g2d.setComposite(makeComposite(1.0f));
            g2d.setComposite(makeComposite(0.5f));
            g2d.fillRect(200, 0, 452, 480);
            g2d.setComposite(makeComposite(1.0f));
            g2d.drawImage(loading, 316, 183, io); //yCord = 286 - icoHeight
            g2d.setColor(Color.WHITE);
        } else if (JenesisPanel.getInstance().getGameMode() != SubMode.LAN_CLIENT) {
            g2d.setColor(Color.BLACK);
            g2d.fillRect(0, 0, 852, 480);
            g2d.drawImage(storyPrev, charXcap + x, charYcap, io);
            g2d.setComposite(makeComposite(0.7f));
            g2d.fillRect(0, 0, 852, 480);
            g2d.setComposite(makeComposite(0.5f));
            g2d.fillRect(200, 0, 452, 480);
            g2d.setComposite(makeComposite(1.0f));
            for (int row = 0; row < rows; row++) {
                for (int column = 0; column < columns; column++) {
                    int computedPosition = (row * columns) + column;
                    if (computedPosition >= unlockedStage.length) continue;
                    g2d.drawImage(unlockedStage[computedPosition] ? unlockedScene[computedPosition] : lockedScene[computedPosition], commonXCoord + (hSpacer * column), commonYCoord + (vSpacer * row), io);
                }
            }
            g2d.setColor(Color.WHITE);
            g2d.setFont(header);
            g2d.drawString(Language.getInstance().getLine(307), (852 - g2d.getFontMetrics().stringWidth(Language.getInstance().getLine(307))) / 2, 80);
            g2d.setFont(normal);
            g2d.drawString(Language.getInstance().getLine(368), (852 - g2d.getFontMetrics().stringWidth(Language.getInstance().getLine(368))) / 2, 380);
            showstoryName(hoveredStoryIndex);
            if (isUnlocked() && unlockedStage[hoveredStoryIndex]) {
                if (opacity < 0.98f)
                    opacity = opacity + 0.02f;
                g2d.setComposite(makeComposite(opacity));
                g2d.drawImage(unlockedCaptions[hoveredStoryIndex], (commonXCoord - hSpacer) + (hSpacer * row), commonYCoord + (vSpacer * column), io);
                g2d.setComposite(makeComposite(1.0f));
                g2d.drawImage(charBack, (commonXCoord - hSpacer) + (hSpacer * row), commonYCoord + (vSpacer * column), io);
            }
        }
        JenesisGlassPane.getInstance().overlay(g2d, io);
    }

    public void loadAssets() {
        if (!loadAssets) return;
        header = LoginScreen.getInstance().getMyFont(LoginScreen.extraTxtSize);
        normal = LoginScreen.getInstance().getMyFont(LoginScreen.normalTxtSize);
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
                storyPrev = imageLoader.loadBufferedImage("images/story/blur/s4.png");
                break;
            case 1:
                storyPrev = imageLoader.loadBufferedImage("images/story/blur/s5.png");
                break;
            case 2:
                storyPrev = imageLoader.loadBufferedImage("images/story/blur/s6.png");
                break;
            default:
                storyPrev = imageLoader.loadBufferedImage("images/story/blur/s6.png");
                break;
        }
        loadAssets = false;
    }

    public void cleanAssets() {
        header = null;
        normal = null;
        charBack.flush();
        loading.flush();
        for (Image image : unlockedScene) {
            image.flush();
        }
        for (Image image : unlockedCaptions) {
            image.flush();
        }
        for (Image image : lockedScene) {
            image.flush();
        }
        storyPrev.flush();
        loadAssets = true;
    }

    public void newInstance() {
        super.newInstance();
    }

    /**
     * Are there more stages?????
     *
     * @return
     */
    public boolean moreStages() {
        boolean answer = false;
        resetCurrentStage();

        //check if more stages
        if (currentScene < StoryMode.getInstance().max) {
            answer = true;
        } //if won last 'final' match
        else if (RenderGameplay.getInstance().hasWon()) {
            //incrementMode();
            //go back to user difficulty
            LoginScreen.getInstance().difficultyDyn = LoginScreen.getInstance().difficultyStat;
            victorySound.play();
            JOptionPane.showMessageDialog(null, Language.getInstance().getLine(115), "Sweetness!!!", JOptionPane.INFORMATION_MESSAGE);
            answer = false;
        }

        return answer;
    }

    public void prepareStory() {
        for (int i = 0; i <= StoryMode.getInstance().max; i++) {
            if (hoveredStoryIndex == i) {
                startGame(i);
                menuSound.play();
                break;
            }
        }
    }

    public void selectStage() {
        prepareStory();
    }
}

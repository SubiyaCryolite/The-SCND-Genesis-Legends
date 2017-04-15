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

import com.scndgen.legends.LoginScreen;
import com.scndgen.legends.scene.StoryMenu;
import com.scndgen.legends.controller.StoryMode;
import com.scndgen.legends.Language;
import com.scndgen.legends.threads.AudioPlayback;
import com.scndgen.legends.windows.MainWindow;
import io.github.subiyacryolite.enginev1.JenesisGlassPane;
import io.github.subiyacryolite.enginev1.JenesisImageLoader;
import io.github.subiyacryolite.enginev1.JenesisRender;

import javax.swing.*;
import java.awt.*;

/**
 * @author: Ifunga Ndana
 * @class: drawPrevChar
 * This class creates a graphical preview of the character and opponent
 */
public class RenderStoryMenu extends StoryMenu implements JenesisRender {

    private AudioPlayback victorySound;
    private AudioPlayback menuSound;
    private Font headerFont, normalFont;
    private Image charBack, loading;
    private Image[] storyCap, storyCapUn, storyCapBlur;
    private Image storyPrev;
    private static RenderStoryMenu instance;

    public static synchronized RenderStoryMenu getInstance() {
        if (instance == null)
            instance = new RenderStoryMenu();
        return instance;
    }

    private RenderStoryMenu() {
        scenes = 12;
        headerFont = LoginScreen.getInstance().getMyFont(LoginScreen.extraTxtSize);
        normalFont = LoginScreen.getInstance().getMyFont(LoginScreen.normalTxtSize);
        rows = (scenes / 3);
        storyCapBlur = new Image[scenes];
        storyCap = new Image[scenes];
        storyCapUn = new Image[scenes];
        hiddenStage = new boolean[scenes];
        mode = LoginScreen.getInstance().stage;
        for (int u = 0; u < hiddenStage.length; u++) {
            if (u <= mode)//if you are higher stage enable
            {
                hiddenStage[u] = true;
            } else {
                hiddenStage[u] = false;
            }
        }
        setBorder(BorderFactory.createEmptyBorder());
    }

    @Override
    public void paintComponent(Graphics g) {
        createBackBuffer();
        loadAssets();
        if (loadingNow) {
            g2d.setColor(Color.BLACK);
            g2d.drawImage(storyPrev, charXcap + x, charYcap, this);
            g2d.setComposite(makeComposite(0.7f));
            g2d.fillRect(0, 0, 852, 480);
            g2d.setComposite(makeComposite(1.0f));
            g2d.setComposite(makeComposite(0.5f));
            g2d.fillRect(200, 0, 452, 480);
            g2d.setComposite(makeComposite(1.0f));
            g2d.drawImage(loading, 316, 183, this); //yCord = 286 - icoHeight
            g2d.setColor(Color.WHITE);
        } else if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanClient) == false) {
            g2d.setColor(Color.BLACK);
            g2d.fillRect(0, 0, 852, 480);
            g2d.drawImage(storyPrev, charXcap + x, charYcap, this);
            lastRow = 0;
            g2d.setComposite(makeComposite(0.7f));
            g2d.fillRect(0, 0, 852, 480);
            g2d.setComposite(makeComposite(1.0f));
            g2d.setComposite(makeComposite(0.5f));
            g2d.fillRect(200, 0, 452, 480);
            g2d.setComposite(makeComposite(1.0f));
            int col = 0;
            for (int i = 0; i < (storyCap.length / 3); i++) {
                g2d.drawImage(storyCap[col], hPos, firstLine + (vSpacer * i), this);
                if (!hiddenStage[col])
                    g2d.drawImage(storyCapBlur[col], hPos, firstLine + (vSpacer * i), this);
                col++;
                g2d.drawImage(storyCap[col], hPos + hSpacer, firstLine + (vSpacer * i), this);
                if (!hiddenStage[col])
                    g2d.drawImage(storyCapBlur[col], hPos + hSpacer, firstLine + (vSpacer * i), this);
                col++;
                g2d.drawImage(storyCap[col], hPos + (hSpacer * 2), firstLine + (vSpacer * i), this);
                if (!hiddenStage[col])
                    g2d.drawImage(storyCapBlur[col], hPos + (hSpacer * 2), firstLine + (vSpacer * i), this);
                col++;
                lastRow = i;
            }
            int rem = storyCap.length % 3;
            if (rem == 1) {
                g2d.drawImage(storyCap[col], hPos, firstLine + (vSpacer * (lastRow + 1)), this);
                if (!hiddenStage[col])
                    g2d.drawImage(storyCapBlur[col], hPos, firstLine + (vSpacer * (lastRow + 1)), this);

            } else if (rem == 2) {
                g2d.drawImage(storyCap[col], hPos, firstLine + (vSpacer * (lastRow + 1)), this);
                if (!hiddenStage[col])
                    g2d.drawImage(storyCapBlur[col], hPos, firstLine + (vSpacer * (lastRow + 1)), this);
                col++;
                g2d.drawImage(storyCap[col], hPos + hSpacer, firstLine + (vSpacer * (lastRow + 1)), this);
                if (!hiddenStage[col])
                    g2d.drawImage(storyCapBlur[col], hPos + hSpacer, firstLine + (vSpacer * (lastRow + 1)), this);
                col++;
            }
            g2d.setColor(Color.WHITE);
            g2d.setFont(headerFont);
            g2d.drawString(Language.getInstance().getLine(307), (852 - g2d.getFontMetrics().stringWidth(Language.getInstance().getLine(307))) / 2, 80);
            g2d.setFont(normalFont);
            g2d.drawString(Language.getInstance().getLine(368), (852 - g2d.getFontMetrics().stringWidth(Language.getInstance().getLine(368))) / 2, 380);
            showstoryName(storySelIndex);
            if (well() && hiddenStage[storySelIndex]) {
                if (opacity < 0.98f)
                    opacity = opacity + 0.02f;
                g2d.setComposite(makeComposite(opacity));
                g2d.drawImage(storyCapUn[storySelIndex], (hPos - hSpacer) + (hSpacer * hIndex), firstLine + (vSpacer * vIndex), this);
                g2d.setComposite(makeComposite(1.0f));
                g2d.drawImage(charBack, (hPos - hSpacer) + (hSpacer * hIndex), firstLine + (vSpacer * vIndex), this);
            }
        }
        JenesisGlassPane.getInstance().overlay(g2d, this);
        g.drawImage(volatileImg, 0, 0, this);
    }

    public void loadAssets() {
        if (!loadAssets) return;
        victorySound = new AudioPlayback(AudioPlayback.soundGameOver(), true);
        menuSound = new AudioPlayback("audio/menu-select.mp3", true);
        JenesisImageLoader pix = new JenesisImageLoader();
        RenderStageSelect.getInstance().setSelectedStage(false);
        try {
            for (int i = 0; i < scenes; i++) {
                storyCap[i] = pix.loadImage("images/Story/locked/x" + (i + 1) + ".png");
            }

            for (int i = 0; i < scenes; i++) {
                storyCapUn[i] = pix.loadImage("images/Story/x" + (i + 1) + ".png");
            }

            for (int i = 0; i < scenes; i++) {
                storyCapBlur[i] = pix.loadImage("images/Story/blur/t_" + i + ".png");
            }
        } catch (Exception e) {
            System.err.println(e);
        }

        //charBack = imageLoader.loadImage("images/selstory.png");
        loading = pix.loadImage("images/appletprogress.gif");
        charBack = pix.loadImage("images/Story/frame.png");
        int x = (int) (Math.random() * 4);
        switch (x) {
            case 0: {
                storyPrev = pix.loadBufferedImage("images/Story/blur/s4.png");
            }
            break;

            case 1: {
                storyPrev = pix.loadBufferedImage("images/Story/blur/s5.png");
            }
            break;

            case 2: {
                storyPrev = pix.loadBufferedImage("images/Story/blur/s6.png");

            }
            break;

            default: {
                storyPrev = pix.loadBufferedImage("images/Story/blur/s6.png");

            }
            break;
        }
        loadAssets = false;
    }

    public void cleanAssets() {
        headerFont = null;
        normalFont = null;
        charBack.flush();
        loading.flush();
        for (Image image : storyCap) {
            image.flush();
        }
        for (Image image : storyCapUn) {
            image.flush();
        }
        for (Image image : storyCapBlur) {
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
        if (currMode < StoryMode.getInstance().max) {
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
            if (storySelIndex == i) {
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

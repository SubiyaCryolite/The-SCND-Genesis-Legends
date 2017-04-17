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
import com.scndgen.legends.enums.Stage;
import com.scndgen.legends.enums.StageSelection;
import com.scndgen.legends.scene.StageSelect;
import com.scndgen.legends.windows.MainWindow;
import io.github.subiyacryolite.enginev1.JenesisGlassPane;
import io.github.subiyacryolite.enginev1.JenesisImageLoader;
import io.github.subiyacryolite.enginev1.JenesisRender;

import javax.swing.*;
import java.awt.*;

public class RenderStageSelect extends StageSelect implements JenesisRender {

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
    private JenesisImageLoader imageLoader;
    private Image[] stageCap = new Image[numberOfStages];
    private Image[] stagePrev = new Image[numberOfStages];
    private Font normalFont;
    private int hoveredStageIndex = -1;

    public RenderStageSelect() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.black, 1));
        stageCap = new Image[numberOfStages];
        stagePrev = new Image[numberOfStages];
    }

    public static synchronized RenderStageSelect getInstance() {
        if (instance == null)
            instance = new RenderStageSelect();
        return instance;
    }

    @Override
    public void loadAssets() {
        if (!loadAssets) return;
        imageLoader = new JenesisImageLoader();
        normalFont = LoginScreen.getInstance().getMyFont(LoginScreen.normalTxtSize);
        loadCaps();
        loadAssets = false;
    }

    @Override
    public void cleanAssets() {
        loadAssets = true;
    }

    @Override
    public void paintComponent(Graphics g) {
        createBackBuffer();
        loadAssets();
        if (opacity < 0.98f) {
            opacity = opacity + 0.02f;
        }
        if (selectedStage) {
            g2d.setColor(Color.BLACK);
            g2d.drawImage(stagePrev[hoveredStage.index()], charXcap + x, charYcap, this);
            g2d.setComposite(makeComposite(0.7f));
            g2d.fillRect(0, 0, 852, 480);
            g2d.setComposite(makeComposite(1.0f));
            g2d.setComposite(makeComposite(0.5f));
            g2d.fillRect(200, 0, 452, 480);
            g2d.setComposite(makeComposite(1.0f));
            g2d.drawImage(loading, 316, 183, this); //yCord = 286 - icoHeight
            g2d.setColor(Color.WHITE);
            g2d.drawString(Language.getInstance().getLine(165), (852 - g2d.getFontMetrics().stringWidth(Language.getInstance().getLine(165))) / 2, 200);
        } else if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanClient) && !selectedStage) {
            g2d.setFont(normalFont);
            g2d.setColor(Color.BLACK);
            g2d.fillRect(0, 0, 852, 480);
            g2d.setColor(Color.WHITE);
            g2d.drawString(">> " + Language.getInstance().getLine(166) + " <<", (852 - g2d.getFontMetrics(normalFont).stringWidth(">> " + Language.getInstance().getLine(166) + " <<")) / 2, 300);
        } else if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanClient) == false) {
            g2d.setFont(normalFont);
            g2d.setColor(Color.BLACK);
            g2d.fillRect(0, 0, 852, 480);
            g2d.setComposite(makeComposite(opacity));
            g2d.drawImage(stagePrev[hoveredStage.index()], charXcap + x, charYcap, this);
            g2d.setComposite(makeComposite(1.0f));
            g2d.setComposite(makeComposite(5 * 0.1F));
            g2d.fillRoundRect(283, 0, 285, 480, 30, 30);
            g2d.setComposite(makeComposite(10 * 0.1F));
            for (int row = 0; row < rows; row++) {
                for (int column = 0; column < columns; column++) {
                    int computedPosition = (row * columns) + column;
                    g2d.drawImage(stageCap[computedPosition], hPos + (hSpacer * column), firstLine + (vSpacer * row), this);
                }
            }
            if (isHoveredOverStage()) {
                showStageName(hoveredStage);
                g2d.drawImage(captionHighlight, hPos + (hSpacer * column), firstLine + (vSpacer * row), this);
            }
        }
        JenesisGlassPane.getInstance().overlay(g2d, this);
        g.drawImage(volatileImg, 0, 0, this);
    }

    private void loadCaps() {
        try {
            captionHighlight = imageLoader.loadImage("images/stageCaptionHighlight.png");
            loading = imageLoader.loadImage("images/loading.gif");
            for (int index = 0; index < stagePreviews.length; index++) {
                stageCap[index] = imageLoader.loadImage("images/t_" + stagePreviews[index] + ".png");
                stagePrev[index] = imageLoader.loadImage("images/prev/" + stagePreviews[index] + ".jpg");
            }
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
    }

    private void showStageName(Stage stage) {
        if (stage.index() == hoveredStageIndex) return;
        capAnim();
        systemNotice(lookupStageNames.get(stage));
        hoveredStageIndex = stage.index();
        if (hoveredStageIndex == Stage.RANDOM.index()) {
            mode = StageSelection.RANDOM;
        } else {
            mode = StageSelection.NORMAL;
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

    public boolean getSelectedStage() {
        return selectedStage;
    }

    public void setSelectedStage(boolean value) {
        selectedStage = value;
    }

    public String[] getAmnientMusicMetaData() {
        return amnientMusicMetaData;
    }

    public void newInstance() {
        super.newInstance();
    }

}

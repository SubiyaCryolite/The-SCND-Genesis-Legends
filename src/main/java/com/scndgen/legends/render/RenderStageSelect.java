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
import com.scndgen.legends.enums.Stage;
import com.scndgen.legends.enums.StageSelection;
import com.scndgen.legends.enums.SubMode;
import com.scndgen.legends.scene.StageSelect;
import io.github.subiyacryolite.enginev1.JenesisImageLoader;
import io.github.subiyacryolite.enginev1.JenesisOverlay;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

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
    private JenesisImageLoader imageLoader;
    private final Image[] stageCap = new Image[numberOfStages];
    private final Image[] stagePrev = new Image[numberOfStages];
    private Font normalFont;
    private int hoveredStageIndex = -1;

    public RenderStageSelect() {
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
        normalFont = getMyFont(LoginScreen.normalTxtSize);
        loadCaps();
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
        if (selectedStage) {
            gc.setFill(Color.BLACK);
            gc.drawImage(stagePrev[hoveredStage.index()], charXcap + x, charYcap);
            gc.setGlobalAlpha((0.7f));
            gc.fillRect(0, 0, 852, 480);
            gc.setGlobalAlpha((1.0f));
            gc.setGlobalAlpha((0.5f));
            gc.fillRect(200, 0, 452, 480);
            gc.setGlobalAlpha((1.0f));
            gc.drawImage(loading, 316, 183); //yCord = 286 - icoHeight
            gc.setFill(Color.WHITE);
            gc.fillText(Language.getInstance().get(165), (852 - getToolkit().getFontLoader().computeStringWidth(Language.getInstance().get(165), gc.getFont())) / 2, 200);
        } else if (ScndGenLegends.getInstance().getSubMode() == SubMode.LAN_CLIENT && !selectedStage) {
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
            if (hoveredStage.index() < stagePrev.length)
                gc.drawImage(stagePrev[hoveredStage.index()], charXcap + x, charYcap);
            gc.setGlobalAlpha((1.0f));
            gc.setGlobalAlpha((5 * 0.1F));
            gc.fillRoundRect(283, 0, 285, 480, 30, 30);
            gc.setGlobalAlpha((10 * 0.1F));
            for (int row = 0; row <= rows; row++) {
                for (int column = 0; column < columns; column++) {
                    int computedPosition = (row * columns) + column;
                    if (computedPosition >= stageCap.length) continue;
                    gc.drawImage(stageCap[computedPosition], hPos + (hSpacer * column), firstLine + (vSpacer * row));
                }
            }
            if (isHoveredOverStage()) {
                showStageName(hoveredStage);
                gc.drawImage(captionHighlight, hPos + (hSpacer * column), firstLine + (vSpacer * row));
            }
        }
        JenesisOverlay.getInstance().overlay(gc, x, y);
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
        primaryNotice(lookupStageNames.get(stage));
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

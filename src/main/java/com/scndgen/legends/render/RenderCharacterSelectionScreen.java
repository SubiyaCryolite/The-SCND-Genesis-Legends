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
import com.scndgen.legends.characters.Characters;
import com.scndgen.legends.characters.Raila;
import com.scndgen.legends.enums.CharacterEnum;
import com.scndgen.legends.enums.SubMode;
import com.scndgen.legends.scene.CharacterSelectionScreen;
import com.scndgen.legends.windows.JenesisPanel;
import io.github.subiyacryolite.enginev1.JenesisGlassPane;
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
public class RenderCharacterSelectionScreen extends CharacterSelectionScreen {

    private static RenderCharacterSelectionScreen instance;
    private final String[] charDesc = new String[numOfCharacters];
    private final Image[] thumbnailNormal = new Image[numOfCharacters];
    private final Image[] thumbnailBlurred = new Image[numOfCharacters];
    private final Image[] portrait = new Image[numOfCharacters];
    private final Image[] portraitFlipped = new Image[numOfCharacters];
    private final Image[] caption = new Image[numOfCharacters];
    private Font bigFont, normalFont;
    private Image fg1, fg2, fg3, bg3;
    private Image charBack, oppBack, charHold, p1, p2, fight, charDescPic, oppDescPic;

    private RenderCharacterSelectionScreen() {
        opacInc = 0.025f;
        loadAssets = true;
    }

    public static synchronized RenderCharacterSelectionScreen getInstance() {
        if (instance == null) {
            instance = new RenderCharacterSelectionScreen();
        }
        return instance;
    }

    @Override
    public void newInstance() {
        super.newInstance();
        Characters.getInstance().resetCharacters();
    }

    public void loadAssets() {
        if (!loadAssets) return;
        imageLoader = new JenesisImageLoader();
        loadCaps();
        loadDesc();
        loadAssets = false;
    }

    public void cleanAssets() {
        loadAssets = true;
    }

    @Override
    public void render(final GraphicsContext gc, final double w, final double h) {
        loadAssets();
        gc.setFont(normalFont);
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, 852, 480);
        gc.drawImage(bg3, 0, 0);
        gc.drawImage(fg1, xCordCloud, 0);
        gc.drawImage(fg2, xCordCloud2, 0);
        gc.drawImage(fg3, 0, 0);
        if (p1Opac < (1.0f - opacInc)) {
            p1Opac = p1Opac + opacInc;
        }
        if (opacChar < (1.0f - (opacInc * 2))) {
            opacChar = opacChar + (opacInc * 2);
        }
        gc.setFill(Color.BLACK);
        gc.setGlobalAlpha(0.70f);
        gc.fillRect(0, 0, 853, 480);
        gc.setGlobalAlpha(1.0f);
        //characterEnum preview DYNAMIC change
        if (characterSelected != true) {
            gc.setGlobalAlpha((p1Opac));
            gc.drawImage(portrait[charPrevLoicIndex], charXcap + x, charYcap);
            gc.setGlobalAlpha((1.0f));
            gc.drawImage(caption[charPrevLoicIndex], 40 - x, 400);
        }
        //opponent preview DYNAMIC change, only show if quick match, should change sprites
        if (characterSelected && opponentSelected != true && JenesisPanel.getInstance().getGameMode() == SubMode.SINGLE_PLAYER) {
            gc.setGlobalAlpha((p1Opac));
            gc.drawImage(portraitFlipped[charPrevLoicIndex], 512 - x, charYcap);
            gc.setGlobalAlpha((1.0f));
            gc.drawImage(caption[charPrevLoicIndex], 553 + x, 400);
        }
        //if characterEnum selected draw FIXED prev
        if (characterSelected) {
            gc.drawImage(portrait[charPrevLoc], charXcap, charYcap);
            gc.drawImage(caption[selectedCharIndex], 40, 380);
        }
        //if opp selected, draw FIXED prev
        if (opponentSelected) {
            gc.drawImage(portraitFlipped[oppPrevLoc], 512, charYcap);
            gc.drawImage(caption[selectedOppIndex], 553, 380);
        }
        gc.drawImage(charHold, 311, 0);
        for (int row = 0; row <= (thumbnailNormal.length / columns); row++) {
            for (int column = 0; column < columns; column++) {
                int computedPosition = (columns * row) + column;
                if (computedPosition >= numOfCharacters) continue;
                boolean characterOpenToSelection = (selectedCharIndex != computedPosition || selectedOppIndex != computedPosition);
                boolean validRow = rowIndex == row;
                boolean validColumn = columnIndex == column;
                boolean notAllCharactersSelect = bothArentSelected();
                if (notAllCharactersSelect && validColumn && validRow && characterOpenToSelection)//clear
                {
                    if (characterSelected != true) {
                        gc.drawImage(charBack, hPos + (hSpacer * column), firstLine + (vSpacer * row));
                    }
                    if (characterSelected && opponentSelected != true && JenesisPanel.getInstance().getGameMode() == SubMode.SINGLE_PLAYER) {
                        gc.drawImage(oppBack, hPos + (hSpacer * column), firstLine + (vSpacer * row));
                    }
                    gc.setGlobalAlpha((opacChar));
                    gc.drawImage(thumbnailNormal[computedPosition], hPos + (hSpacer * column), firstLine + (vSpacer * row));
                    charPrevLoicIndex = computedPosition;
                    gc.setGlobalAlpha((1.0f));
                } else {
                    gc.drawImage(thumbnailBlurred[computedPosition], hPos + (hSpacer * column), firstLine + (vSpacer * row));
                }
            }
        }
        if (characterSelected && opponentSelected) {
            //TODO show select lastStoryScene instead
            gc.drawImage(fight, 0, 0);
            gc.setFont(bigFont);
            gc.setFill(Color.WHITE);
            gc.fillText("<< " + Language.getInstance().get(146) + " >>", (852 - getToolkit().getFontLoader().computeStringWidth("<< " + Language.getInstance().get(146) + " >>", gc.getFont())) / 2, 360);
            gc.fillText("<< " + Language.getInstance().get(147) + " >>", (852 - getToolkit().getFontLoader().computeStringWidth("<< " + Language.getInstance().get(147) + " >>", gc.getFont())) / 2, 390);
        }
        gc.setFont(normalFont);
        gc.setFill(Color.WHITE);
        if (characterSelected == false) {
            //select character
            gc.drawImage(charDescPic, 0, 0);
            gc.fillText(statsChar[charPrevLoicIndex], 4 + x, 18);
        }
        if (characterSelected && opponentSelected == false) {
            //select opponent
            gc.drawImage(oppDescPic, 452, 450);
            gc.fillText(statsChar[charPrevLoicIndex], 852 - getToolkit().getFontLoader().computeStringWidth(statsChar[charPrevLoicIndex], normalFont) + x, 468);
        }
        gc.drawImage(p1, 0, 180);
        gc.drawImage(p2, 812, 180);
        if (x < 0) {
            x = x + 2;
        }
        JenesisGlassPane.getInstance().overlay(gc);
    }

    private void loadCaps() {
        bigFont = getMyFont(LoginScreen.extraTxtSize);
        normalFont = getMyFont(LoginScreen.normalTxtSize);
        oppDescPic = imageLoader.loadImage("images/charInfoO.png");
        charDescPic = imageLoader.loadImage("images/charInfoC.png");
        loadUiContent(CharacterEnum.RAILA);
        loadUiContent(CharacterEnum.SUBIYA);
        loadUiContent(CharacterEnum.LYNX);
        loadUiContent(CharacterEnum.AISHA);
        loadUiContent(CharacterEnum.RAVAGE);
        loadUiContent(CharacterEnum.ADE);
        loadUiContent(CharacterEnum.JONAH);
        loadUiContent(CharacterEnum.NOVA_ADAM);
        loadUiContent(CharacterEnum.ADAM);
        loadUiContent(CharacterEnum.AZARIA);
        loadUiContent(CharacterEnum.SORROWE);
        loadUiContent(CharacterEnum.THING);
        charBack = imageLoader.loadImage("images/selChar.png");
        oppBack = imageLoader.loadImage("images/selOpp.png");
        charHold = imageLoader.loadImage("images/charHold.png");
        Image[] tmp = RenderMainMenu.getInstance().getPics();
        bg3 = tmp[0];
        fg1 = tmp[1];
        fg2 = tmp[2];
        fg3 = tmp[3];
        p1 = imageLoader.loadImage("images/player1.png");
        p2 = imageLoader.loadImage("images/player2.png");
        fight = imageLoader.loadImage("images/fight.png");
        charDesc[0] = Raila.class.getName();
    }

    public void loadUiContent(CharacterEnum characterEnum) {
        thumbnailNormal[characterEnum.index()] = imageLoader.loadImage("images/" + characterEnum.data() + "/cap.png");
        thumbnailBlurred[characterEnum.index()] = imageLoader.loadImage("images/" + characterEnum.data() + "/capB.png");
        caption[characterEnum.index()] = imageLoader.loadImage("images/" + characterEnum.data() + "/name.png");
        portrait[characterEnum.index()] = imageLoader.loadImage("images/" + characterEnum.data() + "/Prev.png");
        portraitFlipped[characterEnum.index()] = imageLoader.loadImage("images/" + characterEnum.data() + "/PrevO.png");
    }


    private void loadDesc() {
        statsChar[0] = Language.getInstance().get(134);
        statsChar[1] = Language.getInstance().get(135);
        statsChar[2] = Language.getInstance().get(136);
        statsChar[3] = Language.getInstance().get(137);
        statsChar[4] = Language.getInstance().get(138);
        statsChar[5] = Language.getInstance().get(139);
        statsChar[6] = Language.getInstance().get(140);
        statsChar[7] = Language.getInstance().get(141);
        statsChar[8] = Language.getInstance().get(142);
        statsChar[9] = Language.getInstance().get(143);
        statsChar[10] = Language.getInstance().get(144);
        statsChar[11] = Language.getInstance().get(145);
    }
}

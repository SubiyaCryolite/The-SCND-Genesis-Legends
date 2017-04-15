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
import com.scndgen.legends.characters.Raila;
import com.scndgen.legends.drawing.SpecialDrawModeRender;
import com.scndgen.legends.enums.CharacterEnum;
import com.scndgen.legends.scene.CharacterSelectionScreen;
import com.scndgen.legends.windows.WindowMain;
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
public class RenderCharacterSelectionScreen extends CharacterSelectionScreen implements JenesisRender {

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
        setBorder(BorderFactory.createEmptyBorder());
    }

    public static synchronized RenderCharacterSelectionScreen getInstance() {
        if (instance == null) {
            instance = new RenderCharacterSelectionScreen();
        }
        return instance;
    }

    @Override
    public void newInstance() {

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
    public void paintComponent(Graphics g) {
        createBackBuffer();
        loadAssets();
        g2d.setFont(normalFont);

        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, 852, 480);


        g2d.drawImage(bg3, 0, 0, this);

        g2d.drawImage(fg1, xCordCloud, 0, this);
        g2d.drawImage(fg2, xCordCloud2, 0, this);
        g2d.drawImage(fg3, 0, 0, this);

        if (p1Opac < (1.0f - opacInc)) {
            p1Opac = p1Opac + opacInc;
        }

        if (opacChar < (1.0f - (opacInc * 2))) {
            opacChar = opacChar + (opacInc * 2);
        }

        g2d.setColor(Color.BLACK);
        g2d.setComposite(makeComposite(0.70f));
        g2d.fillRect(0, 0, 853, 480);
        g2d.setComposite(makeComposite(1.0f));

        //character preview DYNAMIC change
        if (characterSelected != true) {
            g2d.setComposite(makeComposite(p1Opac));
            g2d.drawImage(portrait[charPrevLoicIndex], charXcap + x, charYcap, this);
            g2d.setComposite(makeComposite(1.0f));
            g2d.drawImage(caption[charPrevLoicIndex], 40 - x, 400, this);
        }

        //opponent preview DYNAMIC change, only show if quick match, should change sprites
        if (characterSelected && opponentSelected != true && LoginScreen.getInstance().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.singlePlayer)) {
            g2d.setComposite(makeComposite(p1Opac));
            g2d.drawImage(portraitFlipped[charPrevLoicIndex], 512 - x, charYcap, this);
            g2d.setComposite(makeComposite(1.0f));
            g2d.drawImage(caption[charPrevLoicIndex], 553 + x, 400, this);
        }


        //if character selected draw FIXED prev
        if (characterSelected) {
            g2d.drawImage(portrait[charPrevLoc], charXcap, charYcap, this);
            g2d.drawImage(caption[selectedCharIndex], 40, 380, this);
        }

        //if opp selected, draw FIXED prev
        if (opponentSelected) {
            g2d.drawImage(portraitFlipped[oppPrevLoc], 512, charYcap, this);
            g2d.drawImage(caption[selectedOppIndex], 553, 380, this);
        }

        //all char caps in this segment
        {

            g2d.drawImage(charHold, 311, 0, this);

            if (well()) {
                if (characterSelected != true) {
                    g2d.drawImage(charBack, (hPos - hSpacer) + (hSpacer * hIndex), firstLine + (vSpacer * vIndex), this);
                }

                if (characterSelected && opponentSelected != true && LoginScreen.getInstance().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.singlePlayer)) {
                    g2d.drawImage(oppBack, (hPos - hSpacer) + (hSpacer * hIndex), firstLine + (vSpacer * vIndex), this);
                }
            }

            col = 0;
            for (int i = 0; i < (thumbnailNormal.length / 3); i++) {
                {
                    g2d.drawImage(thumbnailBlurred[col], hPos, firstLine + (vSpacer * i), this);
                    //normal
                    if (bothArentSelected() && hIndex == 1 && vIndex == i && allPlayers[((vIndex * 3) + hIndex) - 1] == 0)//clear
                    {
                        g2d.setComposite(makeComposite(opacChar));
                        g2d.drawImage(thumbnailNormal[col], hPos, firstLine + (vSpacer * i), this);
                        charPrevLoicIndex = col;
                        g2d.setComposite(makeComposite(1.0f));
                    }
                    col++;
                }

                {
                    g2d.drawImage(thumbnailBlurred[col], hPos + (hSpacer * 1), firstLine + (vSpacer * i), this);
                    //normal
                    if (bothArentSelected() && hIndex == 2 && vIndex == i && allPlayers[((vIndex * 3) + hIndex) - 1] == 0)//clear
                    {
                        g2d.setComposite(makeComposite(opacChar));
                        g2d.drawImage(thumbnailNormal[col], hPos + (hSpacer * 1), firstLine + (vSpacer * i), this);
                        charPrevLoicIndex = col;
                        g2d.setComposite(makeComposite(1.0f));
                    }
                    col++;
                }

                {
                    g2d.drawImage(thumbnailBlurred[col], hPos + (hSpacer * 2), firstLine + (vSpacer * i), this);
                    //normal
                    if (bothArentSelected() && hIndex == 3 && vIndex == i && allPlayers[((vIndex * 3) + hIndex) - 1] == 0)//clear
                    {
                        g2d.setComposite(makeComposite(opacChar));
                        g2d.drawImage(thumbnailNormal[col], hPos + (hSpacer * 2), firstLine + (vSpacer * i), this);
                        charPrevLoicIndex = col;
                        g2d.setComposite(makeComposite(1.0f));
                    }
                    col++;
                }
                lastRow = i;
            }

            int rem = thumbnailBlurred.length % 3;

            if (rem == 1) {
                g2d.drawImage(thumbnailBlurred[col], hPos, firstLine, this);
                //normal
                if (bothArentSelected() && hIndex == 1 && vIndex == lastRow && allPlayers[((vIndex * 3) + hIndex) - 1] == 0)//clear
                {
                    g2d.setComposite(makeComposite(opacChar));
                    g2d.drawImage(thumbnailNormal[col], hPos, firstLine, this);
                    charPrevLoicIndex = col;
                    g2d.setComposite(makeComposite(1.0f));
                }
            } else if (rem == 2) {
                {
                    g2d.drawImage(thumbnailBlurred[col], hPos, firstLine, this);
                    //normal
                    if (bothArentSelected() && hIndex == 1 && vIndex == lastRow && allPlayers[((vIndex * 3) + hIndex) - 1] == 0)//clear
                    {
                        g2d.setComposite(makeComposite(opacChar));
                        g2d.drawImage(thumbnailNormal[col], hPos, firstLine, this);
                        charPrevLoicIndex = col;
                        g2d.setComposite(makeComposite(1.0f));
                    }
                    col++;
                }

                {
                    g2d.drawImage(thumbnailBlurred[col + 1], hPos + hSpacer, firstLine + vSpacer, this);
                    //normal
                    if (bothArentSelected() && hIndex == 2 && vIndex == lastRow && allPlayers[((vIndex * 3) + hIndex) - 1] == 0)//clear
                    {
                        g2d.setComposite(makeComposite(opacChar));
                        g2d.drawImage(thumbnailNormal[col + 1], hPos + hSpacer, firstLine + vSpacer, this);
                        charPrevLoicIndex = col + 1;
                        g2d.setComposite(makeComposite(1.0f));
                    }
                    col++;
                }
            }

            if (characterSelected && opponentSelected) {
                g2d.drawImage(fight, 0, 0, this);
                g2d.setFont(bigFont);
                g2d.setColor(Color.WHITE);
                g2d.drawString("<< " + Language.getInstance().getLine(146) + " >>", (852 - g2d.getFontMetrics(bigFont).stringWidth("<< " + Language.getInstance().getLine(146) + " >>")) / 2, 360);
                g2d.drawString("<< " + Language.getInstance().getLine(147) + " >>", (852 - g2d.getFontMetrics(bigFont).stringWidth("<< " + Language.getInstance().getLine(147) + " >>")) / 2, 390);
            }
        }
        g2d.setFont(normalFont);
        g2d.setColor(Color.white);
        if (characterSelected == false) {
            g2d.drawImage(charDescPic, 0, 0, this);
            g2d.drawString(statsChar[charPrevLoicIndex], 4 + x, 18);
        }
        if (characterSelected && opponentSelected == false) {
            g2d.drawImage(oppDescPic, 452, 450, this);
            g2d.drawString(statsChar[charPrevLoicIndex], 852 - g2d.getFontMetrics(normalFont).stringWidth(statsChar[charPrevLoicIndex]) + x, 468);
        }
        g2d.drawImage(p1, 0, 180, this);
        g2d.drawImage(p2, 812, 180, this);
        if (x < 0) {
            x = x + 2;
        }
        JenesisGlassPane.getInstance().overlay(g2d, this);
        g.drawImage(volatileImg, 0, 0, this);
    }

    private void loadCaps() {
        bigFont = LoginScreen.getInstance().getMyFont(LoginScreen.extraTxtSize);
        normalFont = LoginScreen.getInstance().getMyFont(LoginScreen.normalTxtSize);
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
        Image[] tmp = SpecialDrawModeRender.getPics();
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
        statsChar[0] = Language.getInstance().getLine(134);
        statsChar[1] = Language.getInstance().getLine(135);
        statsChar[2] = Language.getInstance().getLine(136);
        statsChar[3] = Language.getInstance().getLine(137);
        statsChar[4] = Language.getInstance().getLine(138);
        statsChar[5] = Language.getInstance().getLine(139);
        statsChar[6] = Language.getInstance().getLine(140);
        statsChar[7] = Language.getInstance().getLine(141);
        statsChar[8] = Language.getInstance().getLine(142);
        statsChar[9] = Language.getInstance().getLine(143);
        statsChar[10] = Language.getInstance().getLine(144);
        statsChar[11] = Language.getInstance().getLine(145);
    }
}

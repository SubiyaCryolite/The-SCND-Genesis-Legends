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
import com.scndgen.legends.attacks.AttackOpponent;
import com.scndgen.legends.attacks.AttackPlayer;
import com.scndgen.legends.characters.Characters;
import com.scndgen.legends.enums.CharacterEnum;
import com.scndgen.legends.enums.CharacterState;
import com.scndgen.legends.enums.SubMode;
import com.scndgen.legends.scene.Gameplay;
import com.scndgen.legends.state.GameState;
import com.scndgen.legends.threads.*;
import com.scndgen.legends.windows.JenesisPanel;
import io.github.subiyacryolite.enginev1.JenesisImageLoader;
import io.github.subiyacryolite.enginev1.JenesisOverlay;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;

import static com.sun.javafx.tk.Toolkit.getToolkit;

/**
 * @author Ifunga Ndana
 */
public class RenderGameplay extends Gameplay {
    private static RenderGameplay instance;
    private Animations1 animations1;
    private Animations2 animations2;
    private Animations3 animations3;
    private Font largeFont, normalFont;
    private Font notSelected;
    private Image stat1, stat2, stat3, stat4;
    private Image particlesLayer1, particlesLayer2, foreGround;
    private LinearGradient gradient1 = new LinearGradient(xLocal, 10, 255, 10, true, CycleMethod.REFLECT, new Stop(0.0, Color.YELLOW), new Stop(1.0, Color.RED));
    private LinearGradient gradient3 = new LinearGradient(0, 0, 100, 100, true, CycleMethod.REFLECT, new Stop(0.0, Color.YELLOW), new Stop(1.0, Color.RED));
    private Image flashy;
    private Image[] moveCat, numberPix;
    private Image[] characterPortraits;
    private Image[] comboPicArray, comicBookText, times, statusEffectSprites = new Image[5];
    private Image oppBar, quePic1, furyBar, counterPane, quePic2, num0, num1, num2, num3, num4, num5, num6, num7, num8, num9, numNull, stageBackground, damageLayer, hpHolder, hud1, hud2, win, lose, status, menuHold, fury, fury1, fury2, phys, cel, itm, curr, numInfinite, figGuiSrc10, figGuiSrc20, figGuiSrc30, figGuiSrc40, figGuiSrc1, figGuiSrc2, figGuiSrc3, figGuiSrc4, time0, time1, time2, time3, time4, time5, time6, time7, time8, time9;
    private Image[] charSprites, oppSprites;
    private Image[] attackAnim2, attackAnim1;
    private Image[] storyPicArr, stats;
    private Image characterPortrait, storyPic;
    private Color currentColor = Color.RED;


    private RenderGameplay() {
    }

    public static synchronized RenderGameplay getInstance() {
        if (instance == null)
            instance = new RenderGameplay();
        return instance;
    }

    public void loadAssets() {
        if (!loadAssets) return;
        loadAssets = false;
        notSelected = getMyFont(12);
        largeFont = getMyFont(LoginScreen.bigTxtSize);
        normalFont = getMyFont(LoginScreen.normalTxtSize);
        setCharMoveset();
        cacheNumPix();
        loadSprites();
        if (ScndGenLegends.getInstance().getSubMode() == SubMode.LAN_HOST) {
            server = JenesisPanel.getInstance().getServer();
        }
        if (ScndGenLegends.getInstance().getSubMode() == SubMode.LAN_CLIENT) {
            //get ip from game
            client = JenesisPanel.getInstance().getClient();
        }
        charPointInc = Characters.getInstance().getPoints();
    }

    public void cleanAssets() {
        if (animations1 != null)
            if (animations1.isRunning())
                animations1.stop();
        if (animations2 != null)
            if (animations2.isRunning())
                animations2.stop();
        if (animations3 != null)
            if (animations3.isRunning())
                animations3.stop();
        loadAssets = true;
    }

    @Override
    public void render(GraphicsContext gc, double width, double height) {
        loadAssets();
        if (GameInstance.getInstance().storySequence) {
            gc.setFill(Color.BLACK);
            gc.fillRect(0, 0, width, height);
            if (opacityPic < 0.98f) {
                opacityPic = opacityPic + 0.02f;
            }
            gc.setGlobalAlpha((opacityPic));
            gc.drawImage(storyPic, 0, 0);
            gc.setGlobalAlpha((10 * 0.1f));

            gc.setGlobalAlpha((0.5f));
            gc.fillRoundRect(0, 424, width, 48, 48, 48); //mid minus half the font size (430-6)
            gc.setGlobalAlpha((10 * 0.1f));

            gc.setFill(Color.WHITE);
            gc.setFont(normalFont);
            gc.fillText(Language.getInstance().get(146) + " >>", (852 - getToolkit().getFontLoader().computeStringWidth(Language.getInstance().get(146) + " >>", gc.getFont())), 462);


            if (opacityTxt < 0.98f) {
                opacityTxt = opacityTxt + 0.02f;
            }
            gc.setGlobalAlpha((opacityTxt));
            gc.drawImage(characterPortrait, ((852 - getToolkit().getFontLoader().computeStringWidth(battleInformation.toString(), gc.getFont())) / 2) - 50, 424);
            gc.fillText(battleInformation.toString(), ((852 - getToolkit().getFontLoader().computeStringWidth(battleInformation.toString(), gc.getFont())) / 2), 450);
            gc.setGlobalAlpha((10 * 0.1f));

        } else if (GameInstance.getInstance().gameOver == false && GameInstance.getInstance().storySequence == false) {
            gc.drawImage(stageBackground, 0, 0);
            gc.setFont(notSelected);
            if (getCharacterHp() >= 0) {
                drawStageBackground(gc);
                drawStageCharacters(gc, width, height);
                drawStageForeground(gc);
                drawDamageLayer(gc);
                gc.setGlobalAlpha((1.0f));
                drawCharacterMenuAndQueSlots(gc);
                drawComicBookText(gc);
                drawBattleInformation(gc);
                if (statusEffectCharacterOpacity > 0.02f) {
                    statusEffectCharacterOpacity = statusEffectCharacterOpacity - 0.02f;
                }
                gc.setGlobalAlpha((statusEffectCharacterOpacity));
                gc.drawImage(statusEffectSprites[statIndexChar], 150 + uiShakeEffectOffsetCharacter, 100 + basicY - uiShakeEffectOffsetCharacter + statusEffectCharacterYCoord);
                gc.setGlobalAlpha((1.0f));
                statusEffectCharacterYCoord = statusEffectCharacterYCoord + 1;


                if (statusEffectOpponentOpacity > 0.02f) {
                    statusEffectOpponentOpacity = statusEffectOpponentOpacity - 0.02f;
                }
                gc.setGlobalAlpha((statusEffectOpponentOpacity));
                gc.drawImage(statusEffectSprites[statIndexOpp], 602 + uiShakeEffectOffsetOpponent, 100 + basicY - uiShakeEffectOffsetOpponent + statusEffectOpponentYCoord);
                gc.setGlobalAlpha((1.0f));
                statusEffectOpponentYCoord = statusEffectOpponentYCoord + 1;

                //---opponrnt activity bar + text

                gc.drawImage(hpHolder, (45 + 62 + x2) + uiShakeEffectOffsetOpponent, (height + 4 + y2 - oppBarYOffset) - uiShakeEffectOffsetOpponent);
                gc.setFill(Color.WHITE);
                gc.fillText("HP: " + Math.round(getOpponentHp()) + " : " + opponentHpAsPercent + "%", (55 + 64 + x2) + uiShakeEffectOffsetOpponent, (18 + y2 - oppBarYOffset) - uiShakeEffectOffsetOpponent);

                gc.setFill(Color.BLACK);
                gc.drawImage(oppBar, (x2 - 20) + uiShakeEffectOffsetOpponent, (y2 + 18 - oppBarYOffset) - uiShakeEffectOffsetOpponent);
                gc.setFill(gradient1);
                gc.fillRoundRect((x2 - 17) + uiShakeEffectOffsetOpponent, (y2 + 22 - oppBarYOffset) - uiShakeEffectOffsetOpponent, GameInstance.getInstance().getRecoveryUnitsOpp(), 6, 6, 6);

                //------------player 1 HUD---------------------//
                gc.drawImage(hpHolder, (lbx2 - 438) + uiShakeEffectOffsetCharacter, (lby2 - 410) - uiShakeEffectOffsetCharacter); // HOLDS hp
                //outline
                gc.drawImage(hud1, (lbx2 - 498) + uiShakeEffectOffsetCharacter, (lby2 - 417) - uiShakeEffectOffsetCharacter);
                //inner
                //gc.setFill(Color.RED);
                gc.setFill(gradient3);
                gc.arc(lbx2 - 493 + uiShakeEffectOffsetCharacter, lby2 - 412 - uiShakeEffectOffsetCharacter, 90, 90, 0, phyAngle());
                //inner loop
                gc.setFill(Color.BLACK);
                gc.drawImage(hud2, lbx2 - 488 + uiShakeEffectOffsetCharacter, lby2 - 407 - uiShakeEffectOffsetCharacter);
                gc.setFill(Color.WHITE);
                gc.fillText("HP: " + Math.round(getCharacterHp()) + " : " + characterHpAsPercent + "%", (lbx2 - 416) + uiShakeEffectOffsetCharacter, (lby2 - 398) - uiShakeEffectOffsetCharacter);
                gc.setGlobalAlpha((10 * 0.1f)); //op back to normal for other drawings
            }

            drawTimer(gc);
            drawItemMenu(gc);
            drawFuryBar(gc);
            drawFuryComboEffects(gc);
            drawClashes(gc);
            drawDamageDigits(gc);
            checkFuryStatus();
        }

        //-----------ENDS ATTACKS QUEING UP--------------

        //when paused
        if (GameInstance.getInstance().gamePaused == true) {
            gc.setFill(Color.BLACK);
            gc.setGlobalAlpha((5 * 0.1f));//initial val between 1 and 10
            gc.fillRect(0, 0, width, height);
            gc.setGlobalAlpha((10 * 0.1f));
            gc.setFill(Color.WHITE);
            gc.fillText(Language.getInstance().get(148), 400, 240);
            gc.fillText(Language.getInstance().get(149), 400, 260);
            gc.fillText(Language.getInstance().get(150), 400, 280);
        }

        //when gameover
        if (GameInstance.getInstance().gameOver == true) {
            gc.setFill(Color.WHITE);
            gc.fillRect(0, 0, width, height);
            gc.setFill(Color.BLACK);
            gc.setGlobalAlpha((8 * 0.1f));//initial val between 1 and 10
            gc.fillRect(0, 210, width, 121);
            gc.setGlobalAlpha((10 * 0.1f));
            gc.drawImage(status, 0, 210);
            gc.setFill(Color.WHITE);
            gc.setFont(notSelected);
            gc.fillText(achievementName, 400, 240); //+14
            gc.fillText(achievementDescription, 400, 254);
            gc.fillText(achievementClass, 400, 268);
            gc.fillText(achievementPoints, 400, 282);
            gc.fillText("<< " + Language.getInstance().get(146) + " >>", 400, 296);
        }
        JenesisOverlay.getInstance().overlay(gc, width, height);
    }

    private void drawStageBackground(GraphicsContext gc) {
        if (animLayer.equalsIgnoreCase("both")) {
            gc.drawImage(particlesLayer2, particlesLayer2PositionX, particlesLayer2PositionY);
        } else if (animLayer.equalsIgnoreCase("back")) {
            gc.drawImage(particlesLayer1, particlesLayer1PositionX, particlesLayer1PositionY);
            gc.drawImage(particlesLayer2, particlesLayer2PositionX, particlesLayer2PositionY);
        }
    }

    private void drawStageCharacters(GraphicsContext gc, double width, double height) {
        if (getAttacksChar().isOverlayDisabled()) {
            gc.drawImage(charSprites[charMeleeSpriteStatus], charXcord + uiShakeEffectOffsetCharacter, charYcord - uiShakeEffectOffsetCharacter);
        }
        gc.drawImage(oppSprites[oppMeleeSpriteStatus], oppXcord + uiShakeEffectOffsetCharacter, oppYcord + uiShakeEffectOffsetCharacter, width, height, width, 0, -width, height);
        if (!getAttacksChar().isOverlayDisabled()) {
            //if character is attacking
            gc.drawImage(charSprites[charMeleeSpriteStatus], charXcord + uiShakeEffectOffsetCharacter, charYcord - uiShakeEffectOffsetCharacter);
        }
    }

    private void drawStageForeground(GraphicsContext gc) {
        if (animLayer.equalsIgnoreCase("both")) {
            gc.drawImage(particlesLayer1, particlesLayer1PositionX, particlesLayer1PositionY);
        } else if (animLayer.equalsIgnoreCase("forg")) {
            gc.drawImage(particlesLayer1, particlesLayer1PositionX, particlesLayer1PositionY);
            gc.drawImage(particlesLayer2, particlesLayer2PositionX, particlesLayer2PositionY);
        }
        gc.drawImage(foreGround, foreGroundPositionX, foreGroundPositionY);
    }

    private void drawDamageLayer(GraphicsContext gc) {
        if ((getCharacterHp() / getCharacterMaximumHp()) < 0.66f) {
            damageLayerOpacity = 6.66f - ((getCharacterHp() / getCharacterMaximumHp()) * 10);
        }
        gc.setGlobalAlpha(damageLayerOpacity * 0.1f);
        gc.drawImage(damageLayer, 0, 0);
    }

    private void drawCharacterMenuAndQueSlots(GraphicsContext gc) {
        gc.drawImage(menuHold, leftHandXAxisOffset, menuBarY);
        for (int queItem = 0; queItem < 4; queItem++) {
            gc.drawImage(quePic1, (queItem * 70 + 5 + leftHandXAxisOffset), 440);
        }
        if (comboCounter >= 1) {
            for (int queItem = 0; queItem < comboCounter; queItem++) {
                gc.drawImage(quePic2, (queItem * 70 + 5 + leftHandXAxisOffset), 440);
            }
        }
    }

    private void drawComicBookText(GraphicsContext gc) {
        if (comicBookTextOpacity >= 0.0f) {
            comicBookTextOpacity = comicBookTextOpacity - 0.0125f;
        }
        gc.setGlobalAlpha((comicBookTextOpacity));
        gc.drawImage(comicBookText[comicBookTextIndex], 170, 112 + basicY + comicBookTextPositionY);
        gc.setGlobalAlpha((1.0f));
        comicBookTextPositionY = comicBookTextPositionY + 3;
    }

    private void drawBattleInformation(GraphicsContext gc) {
        if (opacityTxt < 0.98f) {
            opacityTxt = opacityTxt + 0.02f;
        }
        gc.setGlobalAlpha((opacityTxt));
        gc.setFill(Color.WHITE);
        gc.fillText(battleInformation.toString(), 32 + leftHandXAxisOffset, 470);
        gc.setGlobalAlpha((1.0f));
    }

    private void drawItemMenu(GraphicsContext gc) {
        if (opac < 0.95f) {
            opac = opac + 0.05f;
        }
        gc.setGlobalAlpha((opac));
        gc.setFill(currentColor);
        gc.drawImage(curr, itemX + leftHandXAxisOffset, itemY);

        if (fontSizes[0] == LoginScreen.bigTxtSize) {
            gc.setFont(largeFont);
        } else {
            gc.setFont(normalFont);
        }
        gc.fillText(currentColumn[0], (yTEST + leftHandXAxisOffset), ((366) + fontSizes[0]));

        if (fontSizes[1] == LoginScreen.bigTxtSize) {
            gc.setFont(largeFont);
        } else {
            gc.setFont(normalFont);
        }
        gc.fillText(currentColumn[1], (yTEST + leftHandXAxisOffset), ((366) + fontSizes[0] + 2 + fontSizes[1]));
        if (fontSizes[2] == LoginScreen.bigTxtSize) {
            gc.setFont(largeFont);
        } else {
            gc.setFont(normalFont);
        }
        gc.fillText(currentColumn[2], (yTEST + leftHandXAxisOffset), ((366) + fontSizes[0] + 2 + fontSizes[1] + 2 + fontSizes[2]));
        if (fontSizes[3] == LoginScreen.bigTxtSize) {
            gc.setFont(largeFont);
        } else {
            gc.setFont(normalFont);
        }
        gc.fillText(currentColumn[3], (yTEST + leftHandXAxisOffset), ((366) + fontSizes[0] + 2 + fontSizes[1] + 2 + fontSizes[2] + 2 + fontSizes[3]));
        gc.setGlobalAlpha((1.0f));
    }

    private void drawTimer(GraphicsContext gc) {
        gc.drawImage(counterPane, paneCord, 0);
        if (GameInstance.getInstance().timeLimit > 180) {
            gc.drawImage(numberPix[11], (int) (386), 0);
        } else {
            if (times.length > GameInstance.getInstance().time1)
                gc.drawImage(times[GameInstance.getInstance().time1], 356, 0);
            if (times.length > GameInstance.getInstance().time2)
                gc.drawImage(times[GameInstance.getInstance().time2], 356 + 40, 0);
            if (times.length > GameInstance.getInstance().time3)
                gc.drawImage(times[GameInstance.getInstance().time3], 356 + 80, 0);
        }
    }

    private void drawFuryBar(GraphicsContext gc) {
        gc.drawImage(fury, 20 + ((uiShakeEffectOffsetOpponent + uiShakeEffectOffsetCharacter) / 2), 190 - ((uiShakeEffectOffsetOpponent + uiShakeEffectOffsetCharacter) / 2));
        gc.drawImage(furyBar, 10 + ((uiShakeEffectOffsetOpponent + uiShakeEffectOffsetCharacter) / 2), furyBarY - ((uiShakeEffectOffsetOpponent + uiShakeEffectOffsetCharacter) / 2));
        gc.setFill(Color.RED);
        gc.fillRoundRect(12 + ((uiShakeEffectOffsetOpponent + uiShakeEffectOffsetCharacter) / 2), 132 - ((uiShakeEffectOffsetOpponent + uiShakeEffectOffsetCharacter) / 2), 12, getBreak() / 5, 12, 12);
    }

    private void drawFuryComboEffects(GraphicsContext gc) {
        if (furyComboOpacity > 0.01f) {
            furyComboOpacity = furyComboOpacity - 0.01f;
        }
        gc.setGlobalAlpha((furyComboOpacity));
        gc.drawImage(comboPicArray[comboPicArrayPosOpp], comX + ((uiShakeEffectOffsetOpponent + uiShakeEffectOffsetCharacter) / 2), comY - ((uiShakeEffectOffsetOpponent + uiShakeEffectOffsetCharacter) / 2));
        gc.setGlobalAlpha((1.0f));
        gc.setFont(notSelected);
    }

    private void drawClashes(GraphicsContext gc) {
        if (clasherRunning) {
            gc.setFill(Color.BLACK);
            gc.fillRoundRect(221, 395, 410, 20, 10, 10);
            gc.drawImage(flashy, (int) (ClashSystem.getInstance().plyClashPerc * 4) + 226, 385);
            gc.setFill(Color.RED);
            gc.fillRect((int) (626 - (ClashSystem.getInstance().oppClashPerc * 4)), 400, (int) ClashSystem.getInstance().oppClashPerc * 4, 10);
            gc.setFill(Color.YELLOW);
            gc.fillRect(226, 400, (int) (ClashSystem.getInstance().plyClashPerc * 4), 10);
        }
    }

    private void drawDamageDigits(GraphicsContext gc) {
        gc.setGlobalAlpha((opponentDamageOpacity));
        //opp damage imageLoader
        gc.drawImage(figGuiSrc1, playerDamageXLoc + uiShakeEffectOffsetCharacter, opponentDamageYLoc - uiShakeEffectOffsetCharacter);
        gc.drawImage(figGuiSrc2, playerDamageXLoc + (spacer * 1) + uiShakeEffectOffsetCharacter, opponentDamageYLoc - uiShakeEffectOffsetCharacter);
        gc.drawImage(figGuiSrc3, playerDamageXLoc + (spacer * 2) + uiShakeEffectOffsetCharacter, opponentDamageYLoc - uiShakeEffectOffsetCharacter);
        gc.drawImage(figGuiSrc4, playerDamageXLoc + (spacer * 3) + uiShakeEffectOffsetCharacter, opponentDamageYLoc - uiShakeEffectOffsetCharacter);
        gc.setGlobalAlpha((1.0f));
        if (opponentDamageOpacity >= 0.0f) {
            opponentDamageOpacity = opponentDamageOpacity - 0.0125f;
        }
        if (opponentDamageOpacity < 0.8f) {
            opponentDamageYLoc = opponentDamageYLoc - 3;
        }


        gc.setGlobalAlpha((playerDamageOpacity));
        //char damage imageLoader
        gc.drawImage(figGuiSrc10, opponentDamageXLoc + uiShakeEffectOffsetOpponent, playerDamageYCoord - uiShakeEffectOffsetOpponent);
        gc.drawImage(figGuiSrc20, opponentDamageXLoc + (spacer * 1) + uiShakeEffectOffsetOpponent, playerDamageYCoord - uiShakeEffectOffsetOpponent);
        gc.drawImage(figGuiSrc30, opponentDamageXLoc + (spacer * 2) + uiShakeEffectOffsetOpponent, playerDamageYCoord - uiShakeEffectOffsetOpponent);
        gc.drawImage(figGuiSrc40, opponentDamageXLoc + (spacer * 3) + uiShakeEffectOffsetOpponent, playerDamageYCoord - uiShakeEffectOffsetOpponent);
        gc.setGlobalAlpha((1.0f));
        if (playerDamageOpacity >= 0.0f) {
            playerDamageOpacity = playerDamageOpacity - 0.0125f;
        }
        if (playerDamageOpacity < 0.8f) {
            playerDamageYCoord = playerDamageYCoord - 3;
        }
    }

    /**
     * Show wins pic
     */
    public void showWinLabel() {
        status = win;
    }

    /**
     * Show loose pic
     */
    public void showLoseLabel() {
        status = lose;
    }

    /**
     * Change storyboard pic
     */
    public void changeStoryBoard(int here2) {
        try {
            storyPic = storyPicArr[here2 + 1];
            opacityPic = 0.0f;
        } catch (Exception e) {
            storyPic = storyPicArr[1];
        }
    }

    private void checkFuryStatus() {
        if (getBreak() == 1000) {
            fury = fury1;
        } else {
            fury = fury2;
        }
        if (GameInstance.getInstance().gameOver == true) {
            //slow mo!!!!
        }
    }

    public void setOpponentDamage(int oneA, int twoA, int threeA, int fourA) {
        comicText();
        nrmlDamageSound();
        attackSoundOpp();
        hurtSoundOpp();


        playerDamageYCoord = 160 + (int) (Math.random() * 100);
        playerDamageXLoc = 575 + (int) (Math.random() * 100);
        playerDamageOpacity = 1.0f;

        oneO = oneA;
        twoO = twoA;
        threeO = threeA;
        fourO = fourA;

        figGuiSrc10 = numberPix[oneO];
        figGuiSrc20 = numberPix[twoO];
        figGuiSrc30 = numberPix[threeO];
        figGuiSrc40 = numberPix[fourO];
    }


    public void setPlayerDamage(int oneA, int twoA, int threeA, int fourA) {
        comicText();

        nrmlDamageSound();
        attackSoundChar();
        hurtSoundChar();

        opponentDamageYLoc = 160 + (int) (Math.random() * 100);
        opponentDamageXLoc = 150 + (int) (Math.random() * 100);
        opponentDamageOpacity = 1.0f;

        one = oneA;
        two = twoA;
        three = threeA;
        four = fourA;

        figGuiSrc1 = numberPix[one];
        figGuiSrc2 = numberPix[two];
        figGuiSrc3 = numberPix[three];
        figGuiSrc4 = numberPix[four];
    }

    /**
     * Caches number
     */
    private void cacheNumPix() {
        leftHandXAxisOffset = GameState.getInstance().getLogin().isLeftHanded() ? 0 : 548;
        JenesisImageLoader pix = new JenesisImageLoader();
        counterPane = pix.loadImage("images/countPane.png");
        foreGround = pix.loadImage(RenderStageSelect.getInstance().getFgLocation());
        num0 = pix.loadImage("images/fig/0.png");
        num1 = pix.loadImage("images/fig/1.png");
        num2 = pix.loadImage("images/fig/2.png");
        num3 = pix.loadImage("images/fig/3.png");
        num4 = pix.loadImage("images/fig/4.png");
        num5 = pix.loadImage("images/fig/5.png");
        num6 = pix.loadImage("images/fig/6.png");
        num7 = pix.loadImage("images/fig/7.png");
        num8 = pix.loadImage("images/fig/8.png");
        num9 = pix.loadImage("images/fig/9.png");
        numInfinite = pix.loadImage("images/fig/infinite.png");
        numNull = pix.loadImage("images/trans.png");
        //flashy=imageLoader.loadImage("images/flash.gif",40,40);
        flashy = null;
        numberPix = new Image[]{num0, num1, num2, num3, num4, num5, num6, num7, num8, num9, numNull, numInfinite};

        statusEffectSprites[0] = pix.loadImage("images/trans.png");
        statusEffectSprites[1] = pix.loadImage("images/stats/stat1.png");
        statusEffectSprites[2] = pix.loadImage("images/stats/stat2.png");
        statusEffectSprites[3] = pix.loadImage("images/stats/stat3.png");
        statusEffectSprites[4] = pix.loadImage("images/stats/stat4.png");

        System.out.println("loaded all imageLoader");

    }

    /**
     * EPIC!!!! Loads har sprites
     */
    private void loadSprites() {
        try {
            JenesisImageLoader imageLoader = new JenesisImageLoader();
            Characters.getInstance().getCharacter().loadMeHigh();
            Characters.getInstance().getOpponent().loadMeHigh();

            charSprites = new Image[Characters.getInstance().getCharacter().getNumberOfSprites()];
            for (int i = 0; i < charSprites.length; i++)
                charSprites[i] = Characters.getInstance().getCharacter().getSprite(i);

            oppSprites = new Image[Characters.getInstance().getOpponent().getNumberOfSprites()];
            for (int i = 0; i < oppSprites.length; i++)
                oppSprites[i] = Characters.getInstance().getOpponent().getSprite(i);

            comboPicArray = new Image[9];
            for (int u = 0; u < 6; u++)
                comboPicArray[u] = imageLoader.loadImage("images/screenTxt/" + u + ".png");
            comboPicArray[7] = imageLoader.loadImage("images/screenTxt/7.png");
            comboPicArray[8] = Characters.getInstance().getCharacter().getSprite(11);

            comicBookText = new Image[10];
            comicBookText[0] = Characters.getInstance().getCharacter().getSprite(11);
            for (int bx = 1; bx < numOfComicPics + 1; bx++)
                comicBookText[bx] = imageLoader.loadImage("images/screenComic/" + (bx - 1) + ".png");
            menuHold = imageLoader.loadImage("images/" + Characters.getInstance().getCharacter().getEnum().data() + "/menu.png");
            damageLayer = imageLoader.loadImage("images/damage1.png");

            time0 = imageLoader.loadImage("images/fig/0.png");
            time1 = imageLoader.loadImage("images/fig/1.png");
            time2 = imageLoader.loadImage("images/fig/2.png");
            time3 = imageLoader.loadImage("images/fig/3.png");
            time4 = imageLoader.loadImage("images/fig/4.png");
            time5 = imageLoader.loadImage("images/fig/5.png");
            time6 = imageLoader.loadImage("images/fig/6.png");
            time7 = imageLoader.loadImage("images/fig/7.png");
            time8 = imageLoader.loadImage("images/fig/8.png");
            time9 = imageLoader.loadImage("images/fig/9.png");
            times = new Image[]{time0, time1, time2, time3, time4, time5, time6, time7, time8, time9};

            characterPortraits = new Image[charNames.length];
            if (ScndGenLegends.getInstance().getSubMode() == SubMode.STORY_MODE) {
                for (CharacterEnum characterEnum : CharacterEnum.values()) {
                    characterPortraits[characterEnum.index()] = imageLoader.loadImage("images/" + characterEnum.data() + "/cap.png");
                }
            } else {
                for (int p = 0; p < charNames.length; p++) {
                    characterPortraits[p] = null;
                }
            }
            Image transBuf = imageLoader.loadImage("images/trans.png");
            hpHolder = imageLoader.loadImage("images/hpHolder.png");
            stageBackground = imageLoader.loadImage(RenderStageSelect.getInstance().getBgLocation());
            phys = imageLoader.loadImage("images/t_physical.png");
            cel = imageLoader.loadImage("images/t_celestia.png");
            itm = imageLoader.loadImage("images/t_item.png");
            fury1 = imageLoader.loadImage("images/fury.gif");
            fury2 = imageLoader.loadImage("images/furyo.png");
            fury = fury2;
            particlesLayer1 = imageLoader.loadImage("images/bgBG" + RenderStageSelect.getInstance().getHoveredStage().filePrefix() + "a.png");
            particlesLayer2 = imageLoader.loadImage("images/bgBG" + RenderStageSelect.getInstance().getHoveredStage().filePrefix() + "b.png");
            if (ScndGenLegends.getInstance().getSubMode() == SubMode.STORY_MODE) {
                storyPicArr = new Image[13];
                for (int u = 0; u < 11; u++) {
                    storyPicArr[u] = imageLoader.loadImage("images/story/s" + u + ".png");
                }
                storyPic = storyPicArr[0];
            }
            furyBar = imageLoader.loadImage("images/furyBar.png");
            quePic1 = imageLoader.loadImage("images/queB.png");
            quePic2 = imageLoader.loadImage("images/que.gif");
            oppBar = imageLoader.loadImage("images/oppBar.png");
            moveCat = new Image[]{phys, cel, itm};
            curr = moveCat[0];
            //stat1 = imageLoader.loadImage("images/stats/stat1.png", 90, 24);
            //stat2 = imageLoader.loadImage("images/stats/stat2.png", 90, 24);
            //stat3 = imageLoader.loadImage("images/stats/stat3.png", 90, 24);
            //stat4 = imageLoader.loadImage("images/stats/stat4.png", 90, 24);
            stats = new Image[]{stat1, stat2, stat3, stat4};
            hud1 = imageLoader.loadImage("images/hud1.png");
            hud2 = imageLoader.loadImage("images/hud2.png");
            win = imageLoader.loadImage("images/win.png");
            lose = imageLoader.loadImage("images/lose.png");
            status = transBuf;
            System.out.println("loaded all char sprites imageLoader");
            //ensures method is only run once
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    /**
     * Go to next command menu column
     */
    public void onRight() {
        if (nextEnabled && backEnabled) {
            backEnabled = false;
            yTEST = yTESTinit;
            if (currentColumnIndex < moveCat.length - 1) {
                currentColumnIndex++;
            } else {
                currentColumnIndex = 0;
            }
            curr = moveCat[currentColumnIndex];
            resolveText();
            opac = 0.0f;
            backEnabled = true;
        }
    }

    /**
     * Go to previous command menu column
     */
    public void onLeft() {
        if (backEnabled && nextEnabled) {
            nextEnabled = false;
            yTEST = yTESTinit;
            opac = 0.0f;
            if (currentColumnIndex > 0) {
                currentColumnIndex = currentColumnIndex - 1;
            } else {
                currentColumnIndex = moveCat.length - 1;
            }
            curr = moveCat[currentColumnIndex];
            resolveText();
            opac = 0.0f;
            nextEnabled = true;
        }
    }

    public void newInstance() {
        super.newInstance();
        attackOpponent = new AttackOpponent();
        attackPlayer = new AttackPlayer();
        damageLayerOpacity = 0;
        one = 10;
        two = 10;
        three = 10;
        four = 10;
        oneO = 10;
        twoO = 10;
        threeO = 10;
        fourO = 10;
        opponentDamageYLoc = 400;
        playerDamageYCoord = 400;
        if (animations1 != null)
            animations1.stop();
        if (animations2 != null)
            animations2.stop();
        if (animations3 != null)
            animations3.stop();
        animations1 = new Animations1();
        animations2 = new Animations2();
        animations3 = new Animations3();
        loadedUpdaters = true;
        System.out.println("Char inc: " + charPointInc);
    }

    /**
     * displays damage graphically
     *
     * @param damageAmount - damage dealt
     * @param who          - who dealt the damage
     */
    public void guiScreenChaos(float damageAmount, CharacterState who) {
        manipulateThis = "" + Math.round(damageAmount);
        if (who == CharacterState.CHARACTER) {
            if (manipulateThis.length() == 1) {
                setPlayerDamage(Integer.parseInt("" + manipulateThis.charAt(0) + ""), 10, 10, 10);
            }
            if (manipulateThis.length() == 2) {
                setPlayerDamage(Integer.parseInt("" + manipulateThis.charAt(0) + ""), Integer.parseInt("" + manipulateThis.charAt(1) + ""), 10, 10);
            }
            if (manipulateThis.length() == 3) {
                setPlayerDamage(Integer.parseInt("" + manipulateThis.charAt(0) + ""), Integer.parseInt("" + manipulateThis.charAt(1) + ""), Integer.parseInt("" + manipulateThis.charAt(2) + ""), 10);
            }
            if (manipulateThis.length() == 4) {
                setPlayerDamage(Integer.parseInt("" + manipulateThis.charAt(0) + ""), Integer.parseInt("" + manipulateThis.charAt(1) + ""), Integer.parseInt("" + manipulateThis.charAt(2) + ""), Integer.parseInt("" + manipulateThis.charAt(3) + ""));
            }
        }

        if (who == CharacterState.OPPONENT) {
            if (manipulateThis.length() == 1) {
                setOpponentDamage(Integer.parseInt("" + manipulateThis.charAt(0) + ""), 10, 10, 10);
            }
            if (manipulateThis.length() == 2) {
                setOpponentDamage(Integer.parseInt("" + manipulateThis.charAt(0) + ""), Integer.parseInt("" + manipulateThis.charAt(1) + ""), 10, 10);
            }
            if (manipulateThis.length() == 3) {
                setOpponentDamage(Integer.parseInt("" + manipulateThis.charAt(0) + ""), Integer.parseInt("" + manipulateThis.charAt(1) + ""), Integer.parseInt("" + manipulateThis.charAt(2) + ""), 10);
            }
            if (manipulateThis.length() == 4) {
                setOpponentDamage(Integer.parseInt("" + manipulateThis.charAt(0) + ""), Integer.parseInt("" + manipulateThis.charAt(1) + ""), Integer.parseInt("" + manipulateThis.charAt(2) + ""), Integer.parseInt("" + manipulateThis.charAt(3) + ""));
            }
        }
    }

    /**
     * Attack sounds
     */
    private void attackSoundChar() {
        if (Characters.getInstance().getCharacter().isMale()) {
            randSoundIntChar = (int) (Math.random() * AudioPlayback.MALE_HURT.length * 2);
            if (randSoundIntChar < AudioPlayback.MALE_HURT.length) {
                attackChar = new AudioPlayback(AudioPlayback.maleAttack(randSoundIntChar), false);
                attackChar.play();
            }
        } else {
            randSoundIntChar = (int) (Math.random() * AudioPlayback.FEMALE_HURT.length * 2);
            if (randSoundIntChar < AudioPlayback.FEMALE_HURT.length) {
                attackChar = new AudioPlayback(AudioPlayback.femaleAttack(randSoundIntChar), false);
                attackChar.play();
            }
        }
    }

    protected void attackSoundOpp() {
        if (Characters.getInstance().getOpponent().isMale()) {
            randSoundIntOpp = (int) (Math.random() * AudioPlayback.MALE_HURT.length * 2);
            if (randSoundIntOpp < AudioPlayback.MALE_HURT.length) {
                attackOpp = new AudioPlayback(AudioPlayback.maleAttack(randSoundIntOpp), false);
                attackOpp.play();
            }
        } else {
            randSoundIntOpp = (int) (Math.random() * AudioPlayback.FEMALE_HURT.length * 2);
            if (randSoundIntOpp < AudioPlayback.FEMALE_HURT.length) {
                attackOpp = new AudioPlayback(AudioPlayback.femaleAttack(randSoundIntOpp), false);
                attackOpp.play();
            }
        }
    }

    protected void hurtSoundChar() {
        if (Characters.getInstance().getOpponent().isMale()) {
            randSoundIntCharHurt = (int) (Math.random() * AudioPlayback.MALE_ATTACKS.length * 2);
            if (randSoundIntCharHurt < AudioPlayback.MALE_ATTACKS.length) {
                hurtChar = new AudioPlayback(AudioPlayback.maleHurt(randSoundIntCharHurt), false);
                hurtChar.play();
            }
        } else {
            randSoundIntCharHurt = (int) (Math.random() * AudioPlayback.FEMALE_ATTACKS.length * 2);
            if (randSoundIntCharHurt < AudioPlayback.FEMALE_ATTACKS.length) {
                hurtChar = new AudioPlayback(AudioPlayback.femaleHurt(randSoundIntCharHurt), false);
                hurtChar.play();
            }
        }
    }

    public void hurtSoundOpp() {
        if (Characters.getInstance().getCharacter().isMale()) {
            randSoundIntOppHurt = (int) (Math.random() * AudioPlayback.MALE_ATTACKS.length * 2);
            if (randSoundIntOppHurt < AudioPlayback.MALE_ATTACKS.length) {
                hurtOpp = new AudioPlayback(AudioPlayback.maleHurt(randSoundIntOppHurt), false);
                hurtOpp.play();
            }
        } else {
            randSoundIntOppHurt = (int) (Math.random() * AudioPlayback.FEMALE_ATTACKS.length * 2);
            if (randSoundIntOppHurt < AudioPlayback.FEMALE_ATTACKS.length) {
                hurtOpp = new AudioPlayback(AudioPlayback.femaleHurt(randSoundIntOppHurt), false);
                hurtOpp.play();
            }
        }
    }

    public void furySound() {
        furySound = new AudioPlayback(AudioPlayback.furyAttck(), false);
        furySound.play();
    }

    private void nrmlDamageSound() {
        damageSound = new AudioPlayback(AudioPlayback.playerAttack(), false);
        damageSound.play();
    }


    private void setRandomPic() {
        comicBookTextIndex = Math.round((float) (numOfComicPics * Math.random()));
        comicBookTextOpacity = 1.0f;
        comicBookTextPositionY = 0;
    }

    public void resetComicTxt() {
        comicBookTextIndex = 0;
    }

    public void comicText() {
        if (GameState.getInstance().getLogin().getComicEffectOccurence() > 0) {
            int well = Math.round((float) (Math.random() * GameState.getInstance().getLogin().getComicEffectOccurence()));

            if (well == 1) {
                setRandomPic();
            }
        }
    }

    public synchronized void playBGSound() {
        if (ambientMusic != null) {
            ambientMusic.stop();
        }
        ambientMusic = new AudioPlayback("audio/" + RenderStageSelect.getInstance().getAmbientMusic()[RenderStageSelect.getInstance().getAmbientMusicIndex()] + ".ogg", true);
        ambientMusic.play();
    }

    /**
     * pause threads
     */
    public void pauseThreads() {
        try {
            ambientMusic.pause();
            animations1.pauseThread();
            animations2.pauseThread();
            animations3.pauseThread();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    public void closeAudio() {
        try {
            ambientMusic.stop();
            //ambientMusic.close();
        } catch (Exception e) {
        }
    }

    /**
     * resume threads
     */
    public void resumeThreads() {
        try {
            ambientMusic.resume();
            animations1.resumeThread();
            animations2.resumeThread();
            animations3.resumeThread();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    /**
     * Clear char port
     */
    public void charPortBlank() {
        characterPortrait = null;
    }

    /**
     * Change port pic
     *
     * @param here - index of pic
     */
    public void setCharacterPortrait(int here) {
        characterPortrait = characterPortraits[here];
    }


    /**
     * Animate attacks in graphics context
     *
     * @param thischar - active characterEnum
     */
    public void AnimatePhyAttax(CharacterState thischar) {
        if (thischar == CharacterState.CHARACTER || thischar == CharacterState.OPPONENT) {
            //sprites back to normal poses
            setSprites(CharacterState.CHARACTER, 9, 11);
            setSprites(CharacterState.OPPONENT, 9, 11);
        }
    }


    /**
     * set the break status
     */
    public void setBreak(int change) {
        limitBreak = limitBreak + change;
    }

    /**
     * Makes text white, meaning its OK to select a move
     */
    public void enableSelection() {
        currentColor = Color.WHITE;
        safeToSelect = true;
    }

    /**
     * Makes text red, meaning its NOT OK to select a move
     */
    public void disableSelection() {
        currentColor = Color.RED;
        safeToSelect = false;
    }


    /**
     * Calculates angle of circle
     *
     * @return circel angle
     */
    private int phyAngle() {
        float start = GameInstance.getInstance().getRecoveryUnitsChar() / 290.0f;
        angleRaw = start * 360;
        result = Integer.parseInt("" + Math.round(angleRaw));
        if (result >= 360) {
            enableSelection();
        } else {
            disableSelection();
        }
        return result;
    }
}

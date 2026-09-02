/**************************************************************************

 The SCND Genesis: Legends is a fighting game based on THE SCND GENESIS,
 a webcomic created by Ifunga Ndana (https://www.scndgen.com).

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
 along with The SCND Genesis: Legends. If not, see <https://www.gnu.org/licenses/>.

 **************************************************************************/
package com.scndgen.legends.render;

import com.scndgen.legends.Language;
import com.scndgen.legends.ScndGenLegends;
import com.scndgen.legends.UiConstants;
import com.scndgen.legends.Utils;
import com.scndgen.legends.characters.Characters;
import com.scndgen.legends.command.GameCommand;
import com.scndgen.legends.command.GameCommandBus;
import com.scndgen.legends.constants.AudioConstants;
import com.scndgen.legends.enums.*;
import com.scndgen.legends.mode.GamePlay;
import com.scndgen.legends.mode.StoryMode;
import com.scndgen.legends.state.State;
import com.scndgen.legends.ui.Event;
import com.scndgen.legends.ui.UiAction;
import com.scndgen.legends.ui.UiItem;
import io.github.subiyacryolite.enginev2.Audio;
import io.github.subiyacryolite.enginev2.DesignViewport;
import io.github.subiyacryolite.enginev2.DrawContext;
import io.github.subiyacryolite.enginev2.NvgImage;

import static org.lwjgl.glfw.GLFW.*;

/**
 * @author Ifunga Ndana
 */
public class RenderGamePlay extends GamePlay {
    private static RenderGamePlay instance;
    private NvgImage stageAmbientForeground, stageAmbientBackground, stageForeground;
    private NvgImage[] numberPix;
    private NvgImage[] comboPicArray, comicBookText, times, statusEffectSprites = new NvgImage[5];
    private NvgImage oppBar, furyBar, counterPane, num0, num1, num2, num3, num4, num5, num6, num7, num8, num9, numNull, stageBackground, damageLayer, hpHolder, hpHolderOpponent, hud1, characterHpBar, win, lose, status, furyState, furyActive, furyInactive, numInfinite, figGuiSrc10, figGuiSrc20, figGuiSrc30, figGuiSrc40, figGuiSrc1, figGuiSrc2, figGuiSrc3, figGuiSrc4, time0i, time1i, time2i, time3i, time4i, time5i, time6i, time7i, time8i, time9i;
    private NvgImage[] charSprites, oppSprites;
    private NvgImage[] characterPortraits;
    private int characterPortraitIndex;
    private NvgImage[] storyBoards;
    private int storyBoardIndex;
    private final UiItem attackOne;
    private final UiItem attackTwo;
    private final UiItem attackThree;
    private final UiItem attackFour;
    private final UiItem attackFive;
    private final UiItem attackSix;
    private final UiItem attackSeven;
    private final UiItem attackEight;
    private final UiItem attackNine;
    private final UiItem attackTen;
    private final UiItem attackEleven;
    private final UiItem attackTwelve;
    private final UiItem fury;
    private Audio ambientMusic;

    public void onLeaveMode() {
        if (ambientMusic != null)
            ambientMusic.stop(2000);
    }


    public RenderGamePlay() {
        fury = new UiItem();

        (attackOne = new UiItem()).addJenesisEvent(attackSlot(0, 0, physicalAttacks));
        (attackTwo = new UiItem()).addJenesisEvent(attackSlot(0, 1, physicalAttacks));
        (attackThree = new UiItem()).addJenesisEvent(attackSlot(0, 2, physicalAttacks));
        (attackFour = new UiItem()).addJenesisEvent(attackSlot(0, 3, physicalAttacks));
        (attackFive = new UiItem()).addJenesisEvent(attackSlot(1, 0, celestiaAttacks));
        (attackSix = new UiItem()).addJenesisEvent(attackSlot(1, 1, celestiaAttacks));
        (attackSeven = new UiItem()).addJenesisEvent(attackSlot(1, 2, celestiaAttacks));
        (attackEight = new UiItem()).addJenesisEvent(attackSlot(1, 3, celestiaAttacks));
        (attackNine = new UiItem()).addJenesisEvent(attackSlot(2, 0, itemAttacks));
        (attackTen = new UiItem()).addJenesisEvent(attackSlot(2, 1, itemAttacks));
        (attackEleven = new UiItem()).addJenesisEvent(attackSlot(2, 2, itemAttacks));
        (attackTwelve = new UiItem()).addJenesisEvent(attackSlot(2, 3, itemAttacks));
        fury.addJenesisEvent(Event.of(action -> {
            switch (action) {
                case ACCEPT -> requestCharacterFury();
                case HOVER -> setActiveItem(fury);
                case BACK_CANCEL -> paused = !paused;
                case LEFT, RIGHT, UP, DOWN -> setActiveItem(attackOne);
                default -> {
                }
            }
        }));
        fury.addJenesisEvent(new PauseAndNavigate());
        attackOne.addJenesisEvent(new PauseAndNavigate());
        attackTwo.addJenesisEvent(new PauseAndNavigate());
        attackThree.addJenesisEvent(new PauseAndNavigate());
        attackFour.addJenesisEvent(new PauseAndNavigate());
        attackFive.addJenesisEvent(new PauseAndNavigate());
        attackSix.addJenesisEvent(new PauseAndNavigate());
        attackSeven.addJenesisEvent(new PauseAndNavigate());
        attackEight.addJenesisEvent(new PauseAndNavigate());
        attackNine.addJenesisEvent(new PauseAndNavigate());
        attackTen.addJenesisEvent(new PauseAndNavigate());
        attackEleven.addJenesisEvent(new PauseAndNavigate());
        attackTwelve.addJenesisEvent(new PauseAndNavigate());

        fury.setLeft(attackOne);
        fury.setUp(attackOne);
        fury.setDown(attackOne);
        fury.setRight(attackOne);

        //column 1 - right
        attackOne.setRight(attackFive);
        attackTwo.setRight(attackSix);
        attackThree.setRight(attackSeven);
        attackFour.setRight(attackEight);
        //column 2 - right
        attackFive.setRight(attackNine);
        attackSix.setRight(attackTen);
        attackSeven.setRight(attackEleven);
        attackEight.setRight(attackTwelve);
        //column 3 - right
        attackNine.setRight(attackOne);
        attackTen.setRight(attackTwo);
        attackEleven.setRight(attackThree);
        attackTwelve.setRight(attackFour);
        //column 1 - down
        attackOne.setDown(attackTwo);
        attackTwo.setDown(attackThree);
        attackThree.setDown(attackFour);
        attackFour.setDown(attackOne);
        //column 2 - down
        attackFive.setDown(attackSix);
        attackSix.setDown(attackSeven);
        attackSeven.setDown(attackEight);
        attackEight.setDown(attackFive);
        //column 3 - down
        attackNine.setDown(attackTen);
        attackTen.setDown(attackEleven);
        attackEleven.setDown(attackTwelve);
        attackTwelve.setDown(attackNine);

    }

    public static synchronized RenderGamePlay get() {
        if (instance == null)
            instance = new RenderGamePlay();
        return instance;
    }

    public void loadAssetsIml() {
        loadAssets = false;
        setCharMoveset();
        cacheNumPix();
        loadSprites();
        charPointInc = Characters.get().getPoints();
    }

    public void cleanAssets() {
        stageAmbientForeground = null;
        stageAmbientBackground = null;
        stageForeground = null;
        stageBackground = null;
        numberPix = null;
        comboPicArray = null;
        comicBookText = null;
        times = null;
        java.util.Arrays.fill(statusEffectSprites, null);
        oppBar = null;
        furyBar = null;
        counterPane = null;
        num0 = null;
        num1 = null;
        num2 = null;
        num3 = null;
        num4 = null;
        num5 = null;
        num6 = null;
        num7 = null;
        num8 = null;
        num9 = null;
        numNull = null;
        numInfinite = null;
        damageLayer = null;
        hpHolder = null;
        hpHolderOpponent = null;
        hud1 = null;
        characterHpBar = null;
        win = null;
        lose = null;
        status = null;
        furyState = null;
        furyActive = null;
        furyInactive = null;
        figGuiSrc10 = null;
        figGuiSrc20 = null;
        figGuiSrc30 = null;
        figGuiSrc40 = null;
        figGuiSrc1 = null;
        figGuiSrc2 = null;
        figGuiSrc3 = null;
        figGuiSrc4 = null;
        time0i = null;
        time1i = null;
        time2i = null;
        time3i = null;
        time4i = null;
        time5i = null;
        time6i = null;
        time7i = null;
        time8i = null;
        time9i = null;
        charSprites = null;
        oppSprites = null;
        characterPortraits = null;
        storyBoards = null;
        var characters = Characters.get();
        var character = characters.getCharacter();
        if (character != null) {
            character.unloadSprites(assets());
        }
        var opponent = characters.getOpponent();
        if (opponent != null) {
            opponent.unloadSprites(assets());
        }
        super.cleanAssets();
    }

    @Override
    public void render(DrawContext draw) {
        loadAssets();
        float width = DesignViewport.DESIGN_WIDTH;
        float height = DesignViewport.DESIGN_HEIGHT;
        if (playingCutscene) {
            draw.setFill(0f, 0f, 0f);
            draw.fillRect(0, 0, width, height);
            draw.setGlobalAlpha(opacityPic);
            draw.drawImage(storyBoardIndex >= 0 ? storyBoards[storyBoardIndex] : null, 0, 0);
            draw.setGlobalAlpha(1.0f);

            draw.setGlobalAlpha(0.5f);
            draw.fillRoundRect(0, 424, width, 48, 48); //mid minus half the font size (430-6)
            draw.setGlobalAlpha(1.0f);

            draw.setFill(1f, 1f, 1f);
            setFont(draw, UiConstants.NORMAL_TXT_SIZE);
            draw.setGlobalAlpha(opacityTxt);
            float infoWidth = Utils.computeStringWidth(battleInformation.toString(), draw);
            draw.drawImage(characterPortraitIndex >= 0 ? characterPortraits[characterPortraitIndex] : null, ((852 - infoWidth) / 2) - 50, 424);
            draw.fillText(battleInformation.toString(), ((852 - infoWidth) / 2), 450);
            draw.setGlobalAlpha(1.0f);
        } else if (!gameOver && !playingCutscene) {
            draw.drawImage(stageBackground, 0, 0);
            setFont(draw, 12);
            if (getCharacterHp() >= 0) {
                drawStageBackground(draw);
                drawStageCharacters(draw);
                drawStageForeground(draw);
                drawDamageLayer(draw);
                draw.setGlobalAlpha(1.0f);
                drawComicBookText(draw);
                drawBattleInformation(draw);
                draw.setGlobalAlpha(statusEffectCharacterOpacity);
                draw.drawImage(statusEffectSprites[statIndexChar], 150 + uiShakeEffectOffsetCharacter, 100 + basicY - uiShakeEffectOffsetCharacter + statusEffectCharacterYCoord);
                draw.setGlobalAlpha(1.0f);

                draw.setGlobalAlpha(statusEffectOpponentOpacity);
                draw.drawImage(statusEffectSprites[statIndexOpp], 602 + uiShakeEffectOffsetOpponent, 100 + basicY - uiShakeEffectOffsetOpponent + statusEffectOpponentYCoord);
                draw.setGlobalAlpha(1.0f);

                //---opponrnt activity bar + text

                draw.drawImage(hpHolder, (45 + 62 + x2) + uiShakeEffectOffsetOpponent, (height + 4 + y2 - oppBarYOffset) - uiShakeEffectOffsetOpponent);
                draw.drawImage(hpHolderOpponent, (55 + 56 + x2) + uiShakeEffectOffsetOpponent, (4 + y2 - oppBarYOffset) - uiShakeEffectOffsetOpponent);
                draw.setFill(1f, 1f, 1f);
                draw.fillText("HP: " + Math.round(getOpponentHp()) + " : " + opponentHpAsPercent + "%", (55 + 64 + x2) + uiShakeEffectOffsetOpponent, (18 + y2 - oppBarYOffset) - uiShakeEffectOffsetOpponent);

                draw.drawImage(oppBar, (x2 - 20) + uiShakeEffectOffsetOpponent, (y2 + 18 - oppBarYOffset) - uiShakeEffectOffsetOpponent);
                draw.setFill(1f, 0.647f, 0f); // orange
                draw.fillRoundRect((x2 - 17) + uiShakeEffectOffsetOpponent, (y2 + 22 - oppBarYOffset) - uiShakeEffectOffsetOpponent, getOpponentAtbValue(), 6, 6);

                //------------player 1 HUD---------------------//
                draw.drawImage(hpHolder, (lbx2 - 438) + uiShakeEffectOffsetCharacter, (lby2 - 410) - uiShakeEffectOffsetCharacter); // HOLDS hp
                //outline
                draw.drawImage(hud1, (lbx2 - 498) + uiShakeEffectOffsetCharacter, (lby2 - 417) - uiShakeEffectOffsetCharacter);
                //inner
                draw.setFill(1f, 0.647f, 0f); // orange
                draw.fillArc(lbx2 - 493 + uiShakeEffectOffsetCharacter, lby2 - 412 - uiShakeEffectOffsetCharacter, 90, 90, 0, phyAngle());
                //inner loop
                draw.setFill(0f, 0f, 0f);
                draw.drawImage(characterHpBar, lbx2 - 488 + uiShakeEffectOffsetCharacter, lby2 - 407 - uiShakeEffectOffsetCharacter);
                draw.setFill(1f, 1f, 1f);
                draw.fillText("HP: " + Math.round(getCharacterHp()) + " : " + characterHpAsPercent + "%", (lbx2 - 416) + uiShakeEffectOffsetCharacter, (lby2 - 398) - uiShakeEffectOffsetCharacter);
                draw.setGlobalAlpha(1.0f); //op onBackCancel to normal for other drawings
            }

            drawTimer(draw);
            drawAttackMenu(draw);
            drawFuryBar(draw);
            drawFuryComboEffects(draw);
            drawDamageDigits(draw);
            checkFuryStatus();
        }

        //-----------ENDS ATTACKS QUEING UP--------------

        //when paused
        if (paused) {
            draw.setFill(0f, 0f, 0f);
            draw.setGlobalAlpha(5 * 0.1f);//initial val between 1 and 10
            draw.fillRect(0, 0, width, height);
            draw.setGlobalAlpha(1.0f);
            draw.setFill(1f, 1f, 1f);
            draw.fillText(Language.get().get(148), 400, 240);
            draw.fillText(Language.get().get(149), 400, 260);
            draw.fillText(Language.get().get(150), 400, 280);
        }

        //when gameover
        if (gameOver) {
            draw.setFill(1f, 1f, 1f);
            draw.fillRect(0, 0, width, height);
            draw.setFill(0f, 0f, 0f);
            draw.setGlobalAlpha(8 * 0.1f);//initial val between 1 and 10
            draw.fillRect(0, 210, width, 121);
            draw.setGlobalAlpha(1.0f);
            draw.drawImage(status, 0, 210);
            draw.setFill(1f, 1f, 1f);
            setFont(draw, 12);
            if (achievementName.length > unlockedAchievementInstance) {
                draw.fillText(achievementName[unlockedAchievementInstance], 400, 240); //+14
                draw.fillText(achievementDescription[unlockedAchievementInstance], 400, 254);
                draw.fillText(achievementClass[unlockedAchievementInstance], 400, 268);
                draw.fillText(achievementPoints[unlockedAchievementInstance], 400, 282);
            }
            draw.fillText("<< " + Language.get().get(146) + " >>", 400, 296);
        }
        // Overlay is drawn by ScndGenLegends after mode.render
    }

    private void drawStageBackground(DrawContext draw) {
        switch (ambientMode) {
            case INDEPENDENT -> draw.drawImage(stageAmbientBackground, ambientBackgroundX, ambientBackgroundY);
            case BOTH_IN_BACKGROUND -> {
                draw.drawImage(stageAmbientForeground, ambientForegroundX, ambientForegroundY);
                draw.drawImage(stageAmbientBackground, ambientBackgroundX, ambientBackgroundY);
            }
            default -> {
            }
        }
    }

    private void drawStageCharacters(DrawContext draw) {
        if (!isCharacterAttacking) {
            draw.drawImage(charSprites[charMeleeSpriteStatus], charXcord + uiShakeEffectOffsetCharacter, charYcord - uiShakeEffectOffsetCharacter);
        }
        draw.drawImageFlippedHorizontal(oppSprites[oppMeleeSpriteStatus], oppXcord + uiShakeEffectOffsetCharacter, oppYcord + uiShakeEffectOffsetCharacter);
        if (isCharacterAttacking) {
            draw.drawImage(charSprites[charMeleeSpriteStatus], charXcord + uiShakeEffectOffsetCharacter, charYcord - uiShakeEffectOffsetCharacter);
        }
    }

    private void drawStageForeground(DrawContext draw) {
        switch (ambientMode) {
            case INDEPENDENT -> draw.drawImage(stageAmbientForeground, ambientForegroundX, ambientForegroundY);
            case BOTH_IN_FOREGROUND -> {
                draw.drawImage(stageAmbientForeground, ambientForegroundX, ambientForegroundY);
                draw.drawImage(stageAmbientBackground, ambientBackgroundX, ambientBackgroundY);
            }
            default -> {
            }
        }
        draw.drawImage(stageForeground, foreGroundPositionX, foreGroundPositionY);
    }

    private void drawDamageLayer(DrawContext draw) {
        if ((getCharacterHp() / getCharacterMaximumHp()) < 0.66f) {
            damageLayerOpacity = 6.66f - ((getCharacterHp() / getCharacterMaximumHp()) * 10);
        }
        draw.setGlobalAlpha(damageLayerOpacity * 0.1f);
        draw.drawImage(damageLayer, 0, 0);
    }

    private void drawComicBookText(DrawContext draw) {
        draw.setGlobalAlpha(comicBookTextOpacity);
        draw.drawImage(comicBookText[comicBookTextIndex], 170, 112 + basicY + comicBookTextPositionY);
        draw.setGlobalAlpha(1.0f);
    }

    private void drawBattleInformation(DrawContext draw) {
        draw.setGlobalAlpha(opacityTxt);
        draw.setFill(1f, 1f, 1f);
        draw.fillText(battleInformation.toString(), 32 + attackMenuXPos, 470);
        draw.setGlobalAlpha(1.0f);
    }

    private void drawAttackMenu(DrawContext draw) {
        draw.setGlobalAlpha(opac);
        switch (columnIndex) {
            case 0 -> {
                drawAttackItem(draw, attackMenuTextXPos - 6, attackMenuTextYPos + (24), physicalAttacks[0], attackOne);
                drawAttackItem(draw, attackMenuTextXPos - 12, attackMenuTextYPos + (24 * 2), physicalAttacks[1], attackTwo);
                drawAttackItem(draw, attackMenuTextXPos - 18, attackMenuTextYPos + (24 * 3), physicalAttacks[2], attackThree);
                drawAttackItem(draw, attackMenuTextXPos - 24, attackMenuTextYPos + (24 * 4), physicalAttacks[3], attackFour);
            }
            case 1 -> {
                drawAttackItem(draw, attackMenuTextXPos - 6, attackMenuTextYPos + (24), celestiaAttacks[0], attackFive);
                drawAttackItem(draw, attackMenuTextXPos - 12, attackMenuTextYPos + (24 * 2), celestiaAttacks[1], attackSix);
                drawAttackItem(draw, attackMenuTextXPos - 18, attackMenuTextYPos + (24 * 3), celestiaAttacks[2], attackSeven);
                drawAttackItem(draw, attackMenuTextXPos - 24, attackMenuTextYPos + (24 * 4), celestiaAttacks[3], attackEight);
            }
            case 2 -> {
                drawAttackItem(draw, attackMenuTextXPos - 6, attackMenuTextYPos + (24), itemAttacks[0], attackNine);
                drawAttackItem(draw, attackMenuTextXPos - 12, attackMenuTextYPos + (24 * 2), itemAttacks[1], attackTen);
                drawAttackItem(draw, attackMenuTextXPos - 18, attackMenuTextYPos + (24 * 3), itemAttacks[2], attackEleven);
                drawAttackItem(draw, attackMenuTextXPos - 24, attackMenuTextYPos + (24 * 4), itemAttacks[3], attackTwelve);
            }
            default -> {
            }
        }

        draw.setGlobalAlpha(1.0f);
        draw.setFill(0f, 0f, 0f);
        float diameter = 40;
        draw.fillArc(426 - 100, 420, diameter, diameter, 0, 360);
        draw.fillArc(426 - 50, 420, diameter, diameter, 0, 360);
        draw.fillArc(426 + 5, 420, diameter, diameter, 0, 360);
        draw.fillArc(426 + 55, 420, diameter, diameter, 0, 360);
        draw.fillRect(426 - 70, 438, 140, 4);
        draw.setFill(1f, 1f, 1f);
        diameter = 35;
        if (characterAttacks.size() >= 1 || triggerCharacterAttack) {
            draw.fillArc(426 - 97.5f, 422.5f, diameter, diameter, 0, 360);
        }
        if (characterAttacks.size() >= 2 || triggerCharacterAttack) {
            draw.fillArc(426 - 47.5f, 422.5f, diameter, diameter, 0, 360);
        }
        if (characterAttacks.size() >= 3 || triggerCharacterAttack) {
            draw.fillArc(426 + 7.5f, 422.5f, diameter, diameter, 0, 360);
        }
        if (characterAttacks.size() >= 4 || triggerCharacterAttack) {
            draw.fillArc(426 + 57.5f, 422.5f, diameter, diameter, 0, 360);
        }
        draw.setGlobalAlpha(1.0f);
    }

    private void drawAttackItem(DrawContext draw, int x, int y, String attack, UiItem uiItem) {
        float fontSize = uiItem.isHovered() ? UiConstants.LARGE_TXT_SIZE : UiConstants.NORMAL_TXT_SIZE;
        setFont(draw, fontSize);

        int half = 2;
        int full = 6;
        boolean validHighlight = uiItem.isHovered() && safeToSelect;

        float computedSize = Utils.computeStringWidth(attack, draw) + full;
        float width = computedSize < 150 ? 150 : computedSize;
        float height = fontSize + full;

        draw.setFill(validHighlight ? 1f : 0f, validHighlight ? 1f : 0f, validHighlight ? 1f : 0f);
        draw.strokeRoundRect(x - half, y - fontSize - half, width, height, half, 1f);

        draw.setFill(validHighlight ? 0f : 1f, validHighlight ? 0f : 1f, validHighlight ? 0f : 1f);
        draw.setGlobalAlpha(validHighlight ? 1.0f : 0.5f);
        draw.fillRoundRect(x - half, y - fontSize - half, width, height, half);

        draw.setFill(validHighlight ? 1f : 0f, validHighlight ? 1f : 0f, validHighlight ? 1f : 0f);
        draw.setGlobalAlpha(validHighlight ? 1.0f : 0.5f);
        fillText(draw, attack, x, y, uiItem, width, height);
    }

    private void drawTimer(DrawContext draw) {
        draw.drawImage(counterPane, paneCord, 0);
        if (timeLimit > 180) {
            draw.drawImage(numberPix[11], 386, 0);
        } else {
            if (times.length > time1) {
                draw.drawImage(times[time1], 356, 0);
            }
            if (times.length > time2) {
                draw.drawImage(times[time2], 356 + 40, 0);
            }
            if (times.length > time3) {
                draw.drawImage(times[time3], 356 + 80, 0);
            }
        }
    }

    private void drawFuryBar(DrawContext draw) {
        drawImage(draw, furyState, 20 + ((uiShakeEffectOffsetOpponent + uiShakeEffectOffsetCharacter) / 2f), 190 - ((uiShakeEffectOffsetOpponent + uiShakeEffectOffsetCharacter) / 2f), fury);
        draw.drawImage(furyBar, 10 + ((uiShakeEffectOffsetOpponent + uiShakeEffectOffsetCharacter) / 2f), furyBarY - ((uiShakeEffectOffsetOpponent + uiShakeEffectOffsetCharacter) / 2f));
        draw.setFill(1f, 0f, 0f);
        draw.fillRoundRect(12 + ((uiShakeEffectOffsetOpponent + uiShakeEffectOffsetCharacter) / 2f), 132 - ((uiShakeEffectOffsetOpponent + uiShakeEffectOffsetCharacter) / 2f), 12, getFuryLevel() / 5f, 12);
    }

    private void drawFuryComboEffects(DrawContext draw) {
        draw.setGlobalAlpha(furyComboOpacity);
        if (comboPicArrayPosOpp < 9) {
            draw.drawImage(comboPicArray[comboPicArrayPosOpp], comX + ((uiShakeEffectOffsetOpponent + uiShakeEffectOffsetCharacter) / 2f), comY - ((uiShakeEffectOffsetOpponent + uiShakeEffectOffsetCharacter) / 2f));
        }
        draw.setGlobalAlpha(1.0f);
        setFont(draw, 12);
    }

    private void drawDamageDigits(DrawContext draw) {
        draw.setGlobalAlpha(opponentDamageOpacity);
        //opp damage loader
        draw.drawImage(figGuiSrc1, playerDamageXLoc + uiShakeEffectOffsetCharacter, opponentDamageYLoc - uiShakeEffectOffsetCharacter);
        draw.drawImage(figGuiSrc2, playerDamageXLoc + (spacer) + uiShakeEffectOffsetCharacter, opponentDamageYLoc - uiShakeEffectOffsetCharacter);
        draw.drawImage(figGuiSrc3, playerDamageXLoc + (spacer * 2) + uiShakeEffectOffsetCharacter, opponentDamageYLoc - uiShakeEffectOffsetCharacter);
        draw.drawImage(figGuiSrc4, playerDamageXLoc + (spacer * 3) + uiShakeEffectOffsetCharacter, opponentDamageYLoc - uiShakeEffectOffsetCharacter);
        draw.setGlobalAlpha(1.0f);

        draw.setGlobalAlpha(playerDamageOpacity);
        //char damage loader
        draw.drawImage(figGuiSrc10, opponentDamageXLoc + uiShakeEffectOffsetOpponent, playerDamageYCoord - uiShakeEffectOffsetOpponent);
        draw.drawImage(figGuiSrc20, opponentDamageXLoc + (spacer) + uiShakeEffectOffsetOpponent, playerDamageYCoord - uiShakeEffectOffsetOpponent);
        draw.drawImage(figGuiSrc30, opponentDamageXLoc + (spacer * 2) + uiShakeEffectOffsetOpponent, playerDamageYCoord - uiShakeEffectOffsetOpponent);
        draw.drawImage(figGuiSrc40, opponentDamageXLoc + (spacer * 3) + uiShakeEffectOffsetOpponent, playerDamageYCoord - uiShakeEffectOffsetOpponent);
        draw.setGlobalAlpha(1.0f);
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
    public void characterPortrait(CharacterEnum characterEnum) {
        characterPortraitIndex = characterEnum.index();
    }

    /**
     * Change storyboard pic
     */
    public void storyBoard(int index) {
        storyBoardIndex = index;
        opacityPic = 0.0f;
    }

    private void checkFuryStatus() {
        furyState = isFuryBarFull() ? furyActive : furyInactive;
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
        attackMenuXPos = 670;
        attackMenuTextXPos = attackMenuXPos + 25;
        attackMenuTextYPos = 366;
        counterPane = bag().loadImage("images/countPane.png");
        num0 = bag().loadImage("images/fig/0.png");
        num1 = bag().loadImage("images/fig/1.png");
        num2 = bag().loadImage("images/fig/2.png");
        num3 = bag().loadImage("images/fig/3.png");
        num4 = bag().loadImage("images/fig/4.png");
        num5 = bag().loadImage("images/fig/5.png");
        num6 = bag().loadImage("images/fig/6.png");
        num7 = bag().loadImage("images/fig/7.png");
        num8 = bag().loadImage("images/fig/8.png");
        num9 = bag().loadImage("images/fig/9.png");
        numInfinite = bag().loadImage("images/fig/infinite.png");
        numNull = bag().loadImage("images/trans.png");
        numberPix = new NvgImage[]{num0, num1, num2, num3, num4, num5, num6, num7, num8, num9, numNull, numInfinite};
        statusEffectSprites[0] = bag().loadImage("images/trans.png");
        statusEffectSprites[1] = bag().loadImage("images/stats/stat1.png");
        statusEffectSprites[2] = bag().loadImage("images/stats/stat2.png");
        statusEffectSprites[3] = bag().loadImage("images/stats/stat3.png");
        statusEffectSprites[4] = bag().loadImage("images/stats/stat4.png");
        System.out.println("loaded all loader");
    }

    /**
     * EPIC!!!! Loads har sprites
     */
    private void loadSprites() {
        try {
            var characters = Characters.get();
            characters.getCharacter().loadMeHigh(assets());
            characters.getOpponent().loadMeHigh(assets());

            charSprites = new NvgImage[characters.getCharacter().getNumberOfSprites()];
            for (int i = 0; i < charSprites.length; i++)
                charSprites[i] = characters.getCharacter().getSprite(i);

            oppSprites = new NvgImage[characters.getOpponent().getNumberOfSprites()];
            for (int i = 0; i < oppSprites.length; i++)
                oppSprites[i] = characters.getOpponent().getSprite(i);

            comboPicArray = new NvgImage[9];
            for (int u = 0; u < 6; u++)
                comboPicArray[u] = bag().loadImage("images/screenTxt/" + u + ".png");
            comboPicArray[7] = bag().loadImage("images/screenTxt/7.png");
            comboPicArray[8] = characters.getCharacter().getSprite(11);

            comicBookText = new NvgImage[10];
            comicBookText[0] = characters.getCharacter().getSprite(11);
            for (int bx = 1; bx < numOfComicPics + 1; bx++)
                comicBookText[bx] = bag().loadImage("images/screenComic/" + (bx - 1) + ".png");
            damageLayer = bag().loadImage("images/damage1.png");

            time0i = bag().loadImage("images/fig/0.png");
            time1i = bag().loadImage("images/fig/1.png");
            time2i = bag().loadImage("images/fig/2.png");
            time3i = bag().loadImage("images/fig/3.png");
            time4i = bag().loadImage("images/fig/4.png");
            time5i = bag().loadImage("images/fig/5.png");
            time6i = bag().loadImage("images/fig/6.png");
            time7i = bag().loadImage("images/fig/7.png");
            time8i = bag().loadImage("images/fig/8.png");
            time9i = bag().loadImage("images/fig/9.png");
            times = new NvgImage[]{time0i, time1i, time2i, time3i, time4i, time5i, time6i, time7i, time8i, time9i};

            if (ScndGenLegends.get().getSubMode() == SubMode.STORY_MODE) {
                characterPortraits = new NvgImage[charNames.length];
                for (CharacterEnum characterEnum : CharacterEnum.values()) {
                    characterPortraits[characterEnum.index()] = bag().loadImage("images/" + characterEnum.data() + "/cap.png");
                }
                storyBoards = new NvgImage[12];
                for (int u = 0; u < storyBoards.length; u++) {
                    storyBoards[u] = bag().loadImage("images/story/s" + u + ".png");
                }
            }
            NvgImage transBuf = bag().loadImage("images/trans.png");
            hpHolder = bag().loadImage("images/hpHolder.png");
            hpHolderOpponent = bag().loadImage("images/hpHolderOpponent.png");
            var renderStageSelect = RenderStageSelect.get();
            stageBackground = bag().loadImage(renderStageSelect.getStageBackground());
            stageForeground = bag().loadImage(renderStageSelect.getStageForeground());
            stageAmbientForeground = bag().loadImage(renderStageSelect.getFgLocation1());
            stageAmbientBackground = bag().loadImage(renderStageSelect.getFgLocation2());
            furyActive = bag().loadImage("images/fury.gif");
            furyInactive = bag().loadImage("images/furyo.png");
            furyState = furyInactive;

            furyBar = bag().loadImage("images/furyBar.png");
            oppBar = bag().loadImage("images/oppBar.png");
            hud1 = bag().loadImage("images/hud1.png");
            characterHpBar = bag().loadImage("images/hud2.png");
            win = bag().loadImage("images/win.png");
            lose = bag().loadImage("images/lose.png");
            status = transBuf;
            System.out.println("loaded all char sprites loader");
            //ensures method is only run once
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    public void animateCaption() {
        opac = 0.0f;
    }

    @Override
    public void reset() {
        super.reset();
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
        loadedUpdaters = true;
        setActiveItem(attackOne);
    }

    /**
     * displays damage graphically
     *
     * @param damageAmount - damage dealt
     * @param who          - who dealt the damage
     */
    public void guiScreenChaos(float damageAmount, PlayerType who) {
        manipulateThis = "" + Math.round(damageAmount);
        if (who == PlayerType.PLAYER1) {
            if (manipulateThis.length() == 1) {
                setPlayerDamage(Integer.parseInt("" + manipulateThis.charAt(0)), 10, 10, 10);
            }
            if (manipulateThis.length() == 2) {
                setPlayerDamage(Integer.parseInt("" + manipulateThis.charAt(0)), Integer.parseInt("" + manipulateThis.charAt(1)), 10, 10);
            }
            if (manipulateThis.length() == 3) {
                setPlayerDamage(Integer.parseInt("" + manipulateThis.charAt(0)), Integer.parseInt("" + manipulateThis.charAt(1)), Integer.parseInt("" + manipulateThis.charAt(2)), 10);
            }
            if (manipulateThis.length() == 4) {
                setPlayerDamage(Integer.parseInt("" + manipulateThis.charAt(0)), Integer.parseInt("" + manipulateThis.charAt(1)), Integer.parseInt("" + manipulateThis.charAt(2)), Integer.parseInt("" + manipulateThis.charAt(3)));
            }
        }

        if (who == PlayerType.PLAYER2) {
            if (manipulateThis.length() == 1) {
                setOpponentDamage(Integer.parseInt("" + manipulateThis.charAt(0)), 10, 10, 10);
            }
            if (manipulateThis.length() == 2) {
                setOpponentDamage(Integer.parseInt("" + manipulateThis.charAt(0)), Integer.parseInt("" + manipulateThis.charAt(1)), 10, 10);
            }
            if (manipulateThis.length() == 3) {
                setOpponentDamage(Integer.parseInt("" + manipulateThis.charAt(0)), Integer.parseInt("" + manipulateThis.charAt(1)), Integer.parseInt("" + manipulateThis.charAt(2)), 10);
            }
            if (manipulateThis.length() == 4) {
                setOpponentDamage(Integer.parseInt("" + manipulateThis.charAt(0)), Integer.parseInt("" + manipulateThis.charAt(1)), Integer.parseInt("" + manipulateThis.charAt(2)), Integer.parseInt("" + manipulateThis.charAt(3)));
            }
        }
    }

    /**
     * Attack sounds
     */
    private void attackSoundChar() {
        if (Characters.get().getCharacter().isMale()) {
            randSoundIntChar = (int) (Math.random() * AudioConstants.MALE_HURT.length * 2);
            if (randSoundIntChar < AudioConstants.MALE_HURT.length) {
                Audio attackChar = new Audio(AudioConstants.maleAttack(randSoundIntChar), AudioType.VOICE, false);
                attackChar.play();
            }
        } else {
            randSoundIntChar = (int) (Math.random() * AudioConstants.FEMALE_HURT.length * 2);
            if (randSoundIntChar < AudioConstants.FEMALE_HURT.length) {
                Audio attackChar = new Audio(AudioConstants.femaleAttack(randSoundIntChar), AudioType.VOICE, false);
                attackChar.play();
            }
        }
    }

    protected void attackSoundOpp() {
        if (Characters.get().getOpponent().isMale()) {
            randSoundIntOpp = (int) (Math.random() * AudioConstants.MALE_HURT.length * 2);
            if (randSoundIntOpp < AudioConstants.MALE_HURT.length) {
                Audio attackOpp = new Audio(AudioConstants.maleAttack(randSoundIntOpp), AudioType.VOICE, false);
                attackOpp.play();
            }
        } else {
            randSoundIntOpp = (int) (Math.random() * AudioConstants.FEMALE_HURT.length * 2);
            if (randSoundIntOpp < AudioConstants.FEMALE_HURT.length) {
                Audio attackOpp = new Audio(AudioConstants.femaleAttack(randSoundIntOpp), AudioType.VOICE, false);
                attackOpp.play();
            }
        }
    }

    protected void hurtSoundChar() {
        if (Characters.get().getOpponent().isMale()) {
            randSoundIntCharHurt = (int) (Math.random() * AudioConstants.MALE_ATTACKS.length * 2);
            if (randSoundIntCharHurt < AudioConstants.MALE_ATTACKS.length) {
                Audio hurtChar = new Audio(AudioConstants.maleHurt(randSoundIntCharHurt), AudioType.VOICE, false);
                hurtChar.play();
            }
        } else {
            randSoundIntCharHurt = (int) (Math.random() * AudioConstants.FEMALE_ATTACKS.length * 2);
            if (randSoundIntCharHurt < AudioConstants.FEMALE_ATTACKS.length) {
                Audio hurtChar = new Audio(AudioConstants.femaleHurt(randSoundIntCharHurt), AudioType.VOICE, false);
                hurtChar.play();
            }
        }
    }

    public void hurtSoundOpp() {
        if (Characters.get().getCharacter().isMale()) {
            randSoundIntOppHurt = (int) (Math.random() * AudioConstants.MALE_ATTACKS.length * 2);
            if (randSoundIntOppHurt < AudioConstants.MALE_ATTACKS.length) {
                Audio hurtOpp = new Audio(AudioConstants.maleHurt(randSoundIntOppHurt), AudioType.VOICE, false);
                hurtOpp.play();
            }
        } else {
            randSoundIntOppHurt = (int) (Math.random() * AudioConstants.FEMALE_ATTACKS.length * 2);
            if (randSoundIntOppHurt < AudioConstants.FEMALE_ATTACKS.length) {
                Audio hurtOpp = new Audio(AudioConstants.femaleHurt(randSoundIntOppHurt), AudioType.VOICE, false);
                hurtOpp.play();
            }
        }
    }

    public void furySound() {
        Audio furySound = new Audio(AudioConstants.furyAttck(), AudioType.SOUND, false);
        furySound.play();
    }

    private void nrmlDamageSound() {
        Audio damageSound = new Audio(AudioConstants.playerAttack(), AudioType.SOUND, false);
        damageSound.play();
    }

    private void setRandomPic() {
        comicBookTextIndex = Math.round((float) (numOfComicPics * Math.random()));
        comicBookTextOpacity = 1.0f;
        comicBookTextPositionY = 0;
    }

    public void comicText() {
        var login = State.get().getLogin();
        if (login.getComicEffectOccurence() > 0) {
            int randomInt = Math.round((float) (Math.random() * login.getComicEffectOccurence()));
            if (randomInt == 1) {
                setRandomPic();
            }
        }
    }

    public synchronized void playBGMusic() {
        var renderStageSelect = RenderStageSelect.get();
        ambientMusic = new Audio("audio/" + renderStageSelect.getAmbientMusic()[renderStageSelect.getAmbientMusicIndex()] + ".ogg", AudioType.MUSIC, true);
        ambientMusic.play();
    }

    public void closeAudio() {
        if (ambientMusic != null)
            ambientMusic.stop(2000);
    }

    /**
     * Clear char port
     */
    public void characterPortrait() {
        characterPortraitIndex = -1;
    }


    /**
     * Calculates angle of circle
     *
     * @return circel angle
     */
    private float phyAngle() {
        return getCharacterAtbPercent() * 360;
    }

    @Override
    public void keyPressed(int glfwKey) {
        switch (glfwKey) {
            case GLFW_KEY_UP, GLFW_KEY_W -> onUp();
            case GLFW_KEY_DOWN, GLFW_KEY_S -> onDown();
            case GLFW_KEY_LEFT, GLFW_KEY_D -> onLeft();
            case GLFW_KEY_RIGHT, GLFW_KEY_A -> onRight();
            case GLFW_KEY_ENTER -> onAccept();
            case GLFW_KEY_BACKSPACE -> {
                unQueMove();
                onBackCancel();
            }
            case GLFW_KEY_ESCAPE -> onBackCancel();
            case GLFW_KEY_L -> {
                setActiveItem(fury);
                fury.accept();
            }
            case GLFW_KEY_F5 -> cancelMatch();
            default -> {
            }
        }
    }

    public void reloadAssets() {
        loadAssets = true;
    }

    @Override
    public void mouseScrolled(double dy) {
        if (dy > 0) {
            onLeft();
        } else {
            onRight();
        }
    }

    private Event attackSlot(int column, int row, String[] labels) {
        return Event.of(action -> {
            switch (action) {
                case HOVER -> {
                    columnIndex = column;
                    rowIndex = row;
                    labels[row] = labels[row].toUpperCase();
                }
                case LEAVE -> labels[row] = labels[row].toLowerCase();
                default -> {
                }
            }
        });
    }

    private class PauseAndNavigate extends Event {
        @Override
        public void on(UiAction action) {
            switch (action) {
                case ACCEPT -> {
                    if (!gameOver && !playingCutscene) {
                        if (safeToSelect) {
                            Audio sound = new Audio(AudioConstants.selectSound(), AudioType.SOUND, false);
                            sound.play();
                            activeAttack = (columnIndex * 4) + (rowIndex + 1);
                            characterAttacks.add(activeAttack); // count initially negative 1, add one to get to index 0
                            checkStatus();
                        } else {
                            RenderCharacterSelection.get().errorSound();
                        }
                    } else if (playingCutscene) {
                        StoryMode.get().onAccept();
                    } else if (gameOver) {
                        updatePlayerProfile();
                        switch (ScndGenLegends.get().getSubMode()) {
                            case SINGLE_PLAYER -> closingThread(true);
                            case STORY_MODE -> StoryMode.get().onAccept();
                            case LAN_HOST -> GameCommandBus.get().dispatch(new GameCommand.GoToCharacterSelect(true));
                            default -> {
                            }
                        }
                    }
                }
                case BACK_CANCEL -> {
                    if (!gameOver && !playingCutscene) {
                        onTogglePause();
                    } else if (playingCutscene) {
                        StoryMode.get().onBackCancel();
                    }
                }
                case LEFT -> setActiveItem(source.getLeft());
                case RIGHT -> setActiveItem(source.getRight());
                case UP -> setActiveItem(source.getUp());
                case DOWN -> setActiveItem(source.getDown());
                default -> {
                }
            }
        }
    }
}

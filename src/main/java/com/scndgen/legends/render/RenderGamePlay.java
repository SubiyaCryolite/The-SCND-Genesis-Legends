/**************************************************************************

 The SCND Genesis: Legends is a fighting game based on THE SCND GENESIS,
 a webcomic created by Ifunga Ndana ((([<a href="https://www.scndgen.com">https://www.scndgen.com</a>]))).

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
 along with The SCND Genesis: Legends. If not, see <<a href="http://www.gnu.org/licenses/">http://www.gnu.org/licenses/</a>>.

 **************************************************************************/
package com.scndgen.legends.render;

import com.scndgen.legends.Language;
import com.scndgen.legends.ScndGenLegends;
import com.scndgen.legends.UiConstants;
import com.scndgen.legends.Utils;
import com.scndgen.legends.characters.Characters;
import com.scndgen.legends.constants.AudioConstants;
import com.scndgen.legends.constants.NetworkConstants;
import com.scndgen.legends.enums.*;
import com.scndgen.legends.mode.GamePlay;
import com.scndgen.legends.mode.StoryMode;
import com.scndgen.legends.network.NetworkManager;
import com.scndgen.legends.state.State;
import com.scndgen.legends.ui.Event;
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
        (fury = new UiItem()).addJenesisEvent(new Event() {
            @Override
            public void onAccept() {
                triggerFury(PlayerType.PLAYER1);
            }

            @Override
            public void onHover() {
                setActiveItem(fury);
            }

            @Override
            public void onBackCancel() {
                paused = !paused;
            }

            @Override
            public void onLeft() {
                setActiveItem(attackOne);
            }

            @Override
            public void onRight() {
                setActiveItem(attackOne);
            }

            @Override
            public void onUp() {
                setActiveItem(attackOne);
            }

            @Override
            public void onDown() {
                setActiveItem(attackOne);
            }
        });

        (attackOne = new UiItem()).addJenesisEvent(new Event() {
            @Override
            public void onHover() {
                columnIndex = 0;
                rowIndex = 0;
                physicalAttacks[rowIndex] = physicalAttacks[rowIndex].toUpperCase();
            }

            @Override
            public void onLeave() {
                physicalAttacks[rowIndex] = physicalAttacks[rowIndex].toLowerCase();
            }
        });
        (attackTwo = new UiItem()).addJenesisEvent(new Event() {
            @Override
            public void onHover() {
                columnIndex = 0;
                rowIndex = 1;
                physicalAttacks[rowIndex] = physicalAttacks[rowIndex].toUpperCase();
            }

            @Override
            public void onLeave() {
                physicalAttacks[rowIndex] = physicalAttacks[rowIndex].toLowerCase();
            }
        });
        (attackThree = new UiItem()).addJenesisEvent(new Event() {
            @Override
            public void onHover() {
                columnIndex = 0;
                rowIndex = 2;
                physicalAttacks[rowIndex] = physicalAttacks[rowIndex].toUpperCase();
            }

            @Override
            public void onLeave() {
                physicalAttacks[rowIndex] = physicalAttacks[rowIndex].toLowerCase();
            }
        });
        (attackFour = new UiItem()).addJenesisEvent(new Event() {
            @Override
            public void onHover() {
                columnIndex = 0;
                rowIndex = 3;
                physicalAttacks[rowIndex] = physicalAttacks[rowIndex].toUpperCase();
            }

            @Override
            public void onLeave() {
                physicalAttacks[rowIndex] = physicalAttacks[rowIndex].toLowerCase();
            }
        });
        (attackFive = new UiItem()).addJenesisEvent(new Event() {
            @Override
            public void onHover() {
                columnIndex = 1;
                rowIndex = 0;
                celestiaAttacks[rowIndex] = celestiaAttacks[rowIndex].toUpperCase();
            }

            @Override
            public void onLeave() {
                celestiaAttacks[rowIndex] = celestiaAttacks[rowIndex].toLowerCase();
            }
        });
        (attackSix = new UiItem()).addJenesisEvent(new Event() {
            @Override
            public void onHover() {
                columnIndex = 1;
                rowIndex = 1;
                celestiaAttacks[rowIndex] = celestiaAttacks[rowIndex].toUpperCase();
            }

            @Override
            public void onLeave() {
                celestiaAttacks[rowIndex] = celestiaAttacks[rowIndex].toLowerCase();
            }
        });
        (attackSeven = new UiItem()).addJenesisEvent(new Event() {
            @Override
            public void onHover() {
                columnIndex = 1;
                rowIndex = 2;
                celestiaAttacks[rowIndex] = celestiaAttacks[rowIndex].toUpperCase();
            }

            @Override
            public void onLeave() {
                celestiaAttacks[rowIndex] = celestiaAttacks[rowIndex].toLowerCase();
            }
        });
        (attackEight = new UiItem()).addJenesisEvent(new Event() {
            @Override
            public void onHover() {
                columnIndex = 1;
                rowIndex = 3;
                celestiaAttacks[rowIndex] = celestiaAttacks[rowIndex].toUpperCase();
            }

            @Override
            public void onLeave() {
                celestiaAttacks[rowIndex] = celestiaAttacks[rowIndex].toLowerCase();
            }
        });
        (attackNine = new UiItem()).addJenesisEvent(new Event() {
            @Override
            public void onHover() {
                columnIndex = 2;
                rowIndex = 0;
                itemAttacks[rowIndex] = itemAttacks[rowIndex].toUpperCase();
            }

            @Override
            public void onLeave() {
                itemAttacks[rowIndex] = itemAttacks[rowIndex].toLowerCase();
            }
        });
        (attackTen = new UiItem()).addJenesisEvent(new Event() {
            @Override
            public void onHover() {
                columnIndex = 2;
                rowIndex = 1;
                itemAttacks[rowIndex] = itemAttacks[rowIndex].toUpperCase();
            }

            @Override
            public void onLeave() {
                itemAttacks[rowIndex] = itemAttacks[rowIndex].toLowerCase();
            }
        });
        (attackEleven = new UiItem()).addJenesisEvent(new Event() {
            @Override
            public void onHover() {
                columnIndex = 2;
                rowIndex = 2;
                itemAttacks[rowIndex] = itemAttacks[rowIndex].toUpperCase();
            }

            @Override
            public void onLeave() {
                itemAttacks[rowIndex] = itemAttacks[rowIndex].toLowerCase();
            }
        });
        (attackTwelve = new UiItem()).addJenesisEvent(new Event() {
            @Override
            public void onHover() {
                columnIndex = 2;
                rowIndex = 3;
                itemAttacks[rowIndex] = itemAttacks[rowIndex].toUpperCase();
            }

            @Override
            public void onLeave() {
                itemAttacks[rowIndex] = itemAttacks[rowIndex].toLowerCase();
            }
        });
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
        loadAssets = true;
    }

    @Override
    public void render(DrawContext draw) {
        loadAssets();
        float width = DesignViewport.DESIGN_WIDTH;
        float height = DesignViewport.DESIGN_HEIGHT;
        if (playingCutscene) {
            draw.setFill(0f, 0f, 0f);
            draw.fillRect(0, 0, width, height);
            if (opacityPic < 0.98f) {
                opacityPic += 0.02f;
            }
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
            if (opacityTxt < 0.98f) {
                opacityTxt = opacityTxt + 0.02f;
            }
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
                if (statusEffectCharacterOpacity > 0.02f) {
                    statusEffectCharacterOpacity = statusEffectCharacterOpacity - 0.02f;
                }
                draw.setGlobalAlpha(statusEffectCharacterOpacity);
                draw.drawImage(statusEffectSprites[statIndexChar], 150 + uiShakeEffectOffsetCharacter, 100 + basicY - uiShakeEffectOffsetCharacter + statusEffectCharacterYCoord);
                draw.setGlobalAlpha(1.0f);
                statusEffectCharacterYCoord = statusEffectCharacterYCoord + 1;


                if (statusEffectOpponentOpacity > 0.02f) {
                    statusEffectOpponentOpacity = statusEffectOpponentOpacity - 0.02f;
                }
                draw.setGlobalAlpha(statusEffectOpponentOpacity);
                draw.drawImage(statusEffectSprites[statIndexOpp], 602 + uiShakeEffectOffsetOpponent, 100 + basicY - uiShakeEffectOffsetOpponent + statusEffectOpponentYCoord);
                draw.setGlobalAlpha(1.0f);
                statusEffectOpponentYCoord = statusEffectOpponentYCoord + 1;

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
        if (comicBookTextOpacity >= 0.0f) {
            comicBookTextOpacity = comicBookTextOpacity - 0.0125f;
        }
        draw.setGlobalAlpha(comicBookTextOpacity);
        draw.drawImage(comicBookText[comicBookTextIndex], 170, 112 + basicY + comicBookTextPositionY);
        draw.setGlobalAlpha(1.0f);
        comicBookTextPositionY = comicBookTextPositionY + 3;
    }

    private void drawBattleInformation(DrawContext draw) {
        if (opacityTxt < 0.98f) {
            opacityTxt = opacityTxt + 0.02f;
        }
        draw.setGlobalAlpha(opacityTxt);
        draw.setFill(1f, 1f, 1f);
        draw.fillText(battleInformation.toString(), 32 + attackMenuXPos, 470);
        draw.setGlobalAlpha(1.0f);
    }

    private void drawAttackMenu(DrawContext draw) {
        if (opac < 0.95f) {
            opac = opac + 0.05f;
        }
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
        if (furyComboOpacity > 0.01f) {
            furyComboOpacity -= 0.01f;
        }
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
        if (opponentDamageOpacity >= 0.0f) {
            opponentDamageOpacity = opponentDamageOpacity - 0.0125f;
        }
        if (opponentDamageOpacity < 0.8f) {
            opponentDamageYLoc = opponentDamageYLoc - 3;
        }


        draw.setGlobalAlpha(playerDamageOpacity);
        //char damage loader
        draw.drawImage(figGuiSrc10, opponentDamageXLoc + uiShakeEffectOffsetOpponent, playerDamageYCoord - uiShakeEffectOffsetOpponent);
        draw.drawImage(figGuiSrc20, opponentDamageXLoc + (spacer) + uiShakeEffectOffsetOpponent, playerDamageYCoord - uiShakeEffectOffsetOpponent);
        draw.drawImage(figGuiSrc30, opponentDamageXLoc + (spacer * 2) + uiShakeEffectOffsetOpponent, playerDamageYCoord - uiShakeEffectOffsetOpponent);
        draw.drawImage(figGuiSrc40, opponentDamageXLoc + (spacer * 3) + uiShakeEffectOffsetOpponent, playerDamageYCoord - uiShakeEffectOffsetOpponent);
        draw.setGlobalAlpha(1.0f);
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
        counterPane = assets().loadImage("images/countPane.png");
        num0 = assets().loadImage("images/fig/0.png");
        num1 = assets().loadImage("images/fig/1.png");
        num2 = assets().loadImage("images/fig/2.png");
        num3 = assets().loadImage("images/fig/3.png");
        num4 = assets().loadImage("images/fig/4.png");
        num5 = assets().loadImage("images/fig/5.png");
        num6 = assets().loadImage("images/fig/6.png");
        num7 = assets().loadImage("images/fig/7.png");
        num8 = assets().loadImage("images/fig/8.png");
        num9 = assets().loadImage("images/fig/9.png");
        numInfinite = assets().loadImage("images/fig/infinite.png");
        numNull = assets().loadImage("images/trans.png");
        numberPix = new NvgImage[]{num0, num1, num2, num3, num4, num5, num6, num7, num8, num9, numNull, numInfinite};
        statusEffectSprites[0] = assets().loadImage("images/trans.png");
        statusEffectSprites[1] = assets().loadImage("images/stats/stat1.png");
        statusEffectSprites[2] = assets().loadImage("images/stats/stat2.png");
        statusEffectSprites[3] = assets().loadImage("images/stats/stat3.png");
        statusEffectSprites[4] = assets().loadImage("images/stats/stat4.png");
        System.out.println("loaded all loader");
    }

    /**
     * EPIC!!!! Loads har sprites
     */
    private void loadSprites() {
        try {
            Characters.get().getCharacter().loadMeHigh();
            Characters.get().getOpponent().loadMeHigh();

            charSprites = new NvgImage[Characters.get().getCharacter().getNumberOfSprites()];
            for (int i = 0; i < charSprites.length; i++)
                charSprites[i] = Characters.get().getCharacter().getSprite(i);

            oppSprites = new NvgImage[Characters.get().getOpponent().getNumberOfSprites()];
            for (int i = 0; i < oppSprites.length; i++)
                oppSprites[i] = Characters.get().getOpponent().getSprite(i);

            comboPicArray = new NvgImage[9];
            for (int u = 0; u < 6; u++)
                comboPicArray[u] = assets().loadImage("images/screenTxt/" + u + ".png");
            comboPicArray[7] = assets().loadImage("images/screenTxt/7.png");
            comboPicArray[8] = Characters.get().getCharacter().getSprite(11);

            comicBookText = new NvgImage[10];
            comicBookText[0] = Characters.get().getCharacter().getSprite(11);
            for (int bx = 1; bx < numOfComicPics + 1; bx++)
                comicBookText[bx] = assets().loadImage("images/screenComic/" + (bx - 1) + ".png");
            damageLayer = assets().loadImage("images/damage1.png");

            time0i = assets().loadImage("images/fig/0.png");
            time1i = assets().loadImage("images/fig/1.png");
            time2i = assets().loadImage("images/fig/2.png");
            time3i = assets().loadImage("images/fig/3.png");
            time4i = assets().loadImage("images/fig/4.png");
            time5i = assets().loadImage("images/fig/5.png");
            time6i = assets().loadImage("images/fig/6.png");
            time7i = assets().loadImage("images/fig/7.png");
            time8i = assets().loadImage("images/fig/8.png");
            time9i = assets().loadImage("images/fig/9.png");
            times = new NvgImage[]{time0i, time1i, time2i, time3i, time4i, time5i, time6i, time7i, time8i, time9i};

            if (ScndGenLegends.get().getSubMode() == SubMode.STORY_MODE) {
                characterPortraits = new NvgImage[charNames.length];
                for (CharacterEnum characterEnum : CharacterEnum.values()) {
                    characterPortraits[characterEnum.index()] = assets().loadImage("images/" + characterEnum.data() + "/cap.png");
                }
                storyBoards = new NvgImage[12];
                for (int u = 0; u < storyBoards.length; u++) {
                    storyBoards[u] = assets().loadImage("images/story/s" + u + ".png");
                }
            }
            NvgImage transBuf = assets().loadImage("images/trans.png");
            hpHolder = assets().loadImage("images/hpHolder.png");
            hpHolderOpponent = assets().loadImage("images/hpHolderOpponent.png");
            stageBackground = assets().loadImage(RenderStageSelect.get().getStageBackground());
            stageForeground = assets().loadImage(RenderStageSelect.get().getStageForeground());
            stageAmbientForeground = assets().loadImage(RenderStageSelect.get().getFgLocation1());
            stageAmbientBackground = assets().loadImage(RenderStageSelect.get().getFgLocation2());
            furyActive = assets().loadImage("images/fury.gif");
            furyInactive = assets().loadImage("images/furyo.png");
            furyState = furyInactive;

            furyBar = assets().loadImage("images/furyBar.png");
            oppBar = assets().loadImage("images/oppBar.png");
            hud1 = assets().loadImage("images/hud1.png");
            characterHpBar = assets().loadImage("images/hud2.png");
            win = assets().loadImage("images/win.png");
            lose = assets().loadImage("images/lose.png");
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

    public void newInstance() {
        super.newInstance();
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
        //======================
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
        if (State.get().getLogin().getComicEffectOccurence() > 0) {
            int randomInt = Math.round((float) (Math.random() * State.get().getLogin().getComicEffectOccurence()));
            if (randomInt == 1) {
                setRandomPic();
            }
        }
    }

    public synchronized void playBGMusic() {
        ambientMusic = new Audio("audio/" + RenderStageSelect.get().getAmbientMusic()[RenderStageSelect.get().getAmbientMusicIndex()] + ".ogg", AudioType.MUSIC, true);
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

    private class PauseAndNavigate extends Event {
        @Override
        public void onAccept() {
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
                    case SINGLE_PLAYER:
                        closingThread(true);
                        break;
                    case STORY_MODE:
                        StoryMode.get().onAccept();
                        break;
                    case LAN_HOST:
                        NetworkManager.get().send(NetworkConstants.TO_CHARACTER_SELECT_NEW_MATCH);
                        ScndGenLegends.get().loadMode(ModeEnum.CHAR_SELECT_SCREEN, true);
                        break;
                }

            }
        }

        @Override
        public void onBackCancel() {
            //closeTheServer();
            if (!gameOver && !playingCutscene) {
                onTogglePause();
            } else if (playingCutscene) {
                StoryMode.get().onBackCancel();
            }
        }

        @Override
        public void onLeft() {
            setActiveItem(source.getLeft());
        }

        @Override
        public void onRight() {
            setActiveItem(source.getRight());
        }

        @Override
        public void onUp() {
            setActiveItem(source.getUp());
        }

        @Override
        public void onDown() {
            setActiveItem(source.getDown());
        }
    }
}

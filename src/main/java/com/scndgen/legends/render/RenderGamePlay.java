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
import com.scndgen.legends.LoginScreen;
import com.scndgen.legends.ScndGenLegends;
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
import io.github.subiyacryolite.enginev1.Audio;
import io.github.subiyacryolite.enginev1.Loader;
import io.github.subiyacryolite.enginev1.Overlay;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;

import static com.sun.javafx.tk.Toolkit.getToolkit;

/**
 * @author Ifunga Ndana
 */
public class RenderGamePlay extends GamePlay {
    private static RenderGamePlay instance;
    private Font largeFont, normalFont;
    private Font notSelected;
    private Image stageAmbientForeground, stageAmbientBackground, stageForeground;
    private Image[] numberPix;
    private Image[] comboPicArray, comicBookText, times, statusEffectSprites = new Image[5];
    private Image oppBar, furyBar, counterPane, num0, num1, num2, num3, num4, num5, num6, num7, num8, num9, numNull, stageBackground, damageLayer, hpHolder, hpHolderOpponent, hud1, characterHpBar, win, lose, status, furyState, furyActive, furyInactive, numInfinite, figGuiSrc10, figGuiSrc20, figGuiSrc30, figGuiSrc40, figGuiSrc1, figGuiSrc2, figGuiSrc3, figGuiSrc4, time0i, time1i, time2i, time3i, time4i, time5i, time6i, time7i, time8i, time9i;
    private Image[] charSprites, oppSprites;
    private Image[] characterPortraits;
    private int characterPortraitIndex;
    private Image[] storyBoards;
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
        notSelected = loadFont(12);
        largeFont = loadFont(LoginScreen.LARGE_TXT_SIZE);
        normalFont = loadFont(LoginScreen.NORMAL_TXT_SIZE);
        setCharMoveset();
        cacheNumPix();
        loadSprites();
        charPointInc = Characters.get().getPoints();
    }

    public void cleanAssets() {
        loadAssets = true;
    }

    @Override
    public void render(GraphicsContext gc, double width, double height) {
        loadAssets();
        if (playingCutscene) {
            gc.setFill(Color.BLACK);
            gc.fillRect(0, 0, width, height);
            if (opacityPic < 0.98f) {
                opacityPic += 0.02f;
            }
            gc.setGlobalAlpha(opacityPic);
            gc.drawImage(storyBoardIndex >= 0 ? storyBoards[storyBoardIndex] : null, 0, 0);
            gc.setGlobalAlpha(1.0f);

            gc.setGlobalAlpha(0.5f);
            gc.fillRoundRect(0, 424, width, 48, 48, 48); //mid minus half the font size (430-6)
            gc.setGlobalAlpha(1.0f);

            gc.setFill(Color.WHITE);
            gc.setFont(normalFont);
            gc.setGlobalAlpha((opacityTxt));
            gc.drawImage(characterPortraitIndex >= 0 ? characterPortraits[characterPortraitIndex] : null, ((852 -Utils.computeStringWidth(battleInformation.toString(), gc.getFont())) / 2) - 50, 424);
            gc.fillText(battleInformation.toString(), ((852 - Utils.computeStringWidth(battleInformation.toString(), gc.getFont())) / 2), 450);
            gc.setGlobalAlpha(1.0f);
            if (opacityTxt < 0.98f) {
                opacityTxt = opacityTxt + 0.02f;
            }
        } else if (!gameOver && !playingCutscene) {
            gc.drawImage(stageBackground, 0, 0);
            gc.setFont(notSelected);
            if (getCharacterHp() >= 0) {
                drawStageBackground(gc);
                drawStageCharacters(gc, width, height);
                drawStageForeground(gc);
                drawDamageLayer(gc);
                gc.setGlobalAlpha(1.0f);
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
                gc.drawImage(hpHolderOpponent, (55 + 56 + x2) + uiShakeEffectOffsetOpponent, (4 + y2 - oppBarYOffset) - uiShakeEffectOffsetOpponent);
                gc.setFill(Color.WHITE);
                gc.fillText("HP: " + Math.round(getOpponentHp()) + " : " + opponentHpAsPercent + "%", (55 + 64 + x2) + uiShakeEffectOffsetOpponent, (18 + y2 - oppBarYOffset) - uiShakeEffectOffsetOpponent);

                gc.drawImage(oppBar, (x2 - 20) + uiShakeEffectOffsetOpponent, (y2 + 18 - oppBarYOffset) - uiShakeEffectOffsetOpponent);
                gc.setFill(Color.ORANGE);
                gc.fillRoundRect((x2 - 17) + uiShakeEffectOffsetOpponent, (y2 + 22 - oppBarYOffset) - uiShakeEffectOffsetOpponent, getOpponentAtbValue(), 6, 6, 6);

                //------------player 1 HUD---------------------//
                gc.drawImage(hpHolder, (lbx2 - 438) + uiShakeEffectOffsetCharacter, (lby2 - 410) - uiShakeEffectOffsetCharacter); // HOLDS hp
                //outline
                gc.drawImage(hud1, (lbx2 - 498) + uiShakeEffectOffsetCharacter, (lby2 - 417) - uiShakeEffectOffsetCharacter);
                //inner
                //gc.setFill(Color.RED);
                gc.setFill(Color.ORANGE);
                gc.fillArc(lbx2 - 493 + uiShakeEffectOffsetCharacter, lby2 - 412 - uiShakeEffectOffsetCharacter, 90, 90, 0, phyAngle(), ArcType.ROUND);
                //inner loop
                gc.setFill(Color.BLACK);
                gc.drawImage(characterHpBar, lbx2 - 488 + uiShakeEffectOffsetCharacter, lby2 - 407 - uiShakeEffectOffsetCharacter);
                gc.setFill(Color.WHITE);
                gc.fillText("HP: " + Math.round(getCharacterHp()) + " : " + characterHpAsPercent + "%", (lbx2 - 416) + uiShakeEffectOffsetCharacter, (lby2 - 398) - uiShakeEffectOffsetCharacter);
                gc.setGlobalAlpha(1.0f); //op onBackCancel to normal for other drawings
            }

            drawTimer(gc);
            drawAttackMenu(gc);
            drawFuryBar(gc);
            drawFuryComboEffects(gc);
            drawDamageDigits(gc);
            checkFuryStatus();
        }

        //-----------ENDS ATTACKS QUEING UP--------------

        //when paused
        if (paused) {
            gc.setFill(Color.BLACK);
            gc.setGlobalAlpha((5 * 0.1f));//initial val between 1 and 10
            gc.fillRect(0, 0, width, height);
            gc.setGlobalAlpha(1.0f);
            gc.setFill(Color.WHITE);
            gc.fillText(Language.get().get(148), 400, 240);
            gc.fillText(Language.get().get(149), 400, 260);
            gc.fillText(Language.get().get(150), 400, 280);
        }

        //when gameover
        if (gameOver) {
            gc.setFill(Color.WHITE);
            gc.fillRect(0, 0, width, height);
            gc.setFill(Color.BLACK);
            gc.setGlobalAlpha((8 * 0.1f));//initial val between 1 and 10
            gc.fillRect(0, 210, width, 121);
            gc.setGlobalAlpha(1.0f);
            gc.drawImage(status, 0, 210);
            gc.setFill(Color.WHITE);
            gc.setFont(notSelected);
            if (achievementName.length > unlockedAchievementInstance) {
                gc.fillText(achievementName[unlockedAchievementInstance], 400, 240); //+14
                gc.fillText(achievementDescription[unlockedAchievementInstance], 400, 254);
                gc.fillText(achievementClass[unlockedAchievementInstance], 400, 268);
                gc.fillText(achievementPoints[unlockedAchievementInstance], 400, 282);
            }
            gc.fillText("<< " + Language.get().get(146) + " >>", 400, 296);
        }
        Overlay.get().overlay(gc, width, height);
    }

    private void drawStageBackground(GraphicsContext gc) {
        switch (ambientMode) {
            case INDEPENDENT:
                gc.drawImage(stageAmbientBackground, ambientBackgroundX, ambientBackgroundY);
                break;
            case BOTH_IN_BACKGROUND:
                gc.drawImage(stageAmbientForeground, ambientForegroundX, ambientForegroundY);
                gc.drawImage(stageAmbientBackground, ambientBackgroundX, ambientBackgroundY);
                break;
        }
    }

    private void drawStageCharacters(GraphicsContext gc, double width, double height) {
        if (!isCharacterAttacking)
            gc.drawImage(charSprites[charMeleeSpriteStatus], charXcord + uiShakeEffectOffsetCharacter, charYcord - uiShakeEffectOffsetCharacter);
        gc.drawImage(oppSprites[oppMeleeSpriteStatus], oppXcord + uiShakeEffectOffsetCharacter, oppYcord + uiShakeEffectOffsetCharacter, width, height, width, 0, -width, height);
        if (isCharacterAttacking)
            gc.drawImage(charSprites[charMeleeSpriteStatus], charXcord + uiShakeEffectOffsetCharacter, charYcord - uiShakeEffectOffsetCharacter);
    }

    private void drawStageForeground(GraphicsContext gc) {
        switch (ambientMode) {
            case INDEPENDENT:
                gc.drawImage(stageAmbientForeground, ambientForegroundX, ambientForegroundY);
                break;
            case BOTH_IN_FOREGROUND:
                gc.drawImage(stageAmbientForeground, ambientForegroundX, ambientForegroundY);
                gc.drawImage(stageAmbientBackground, ambientBackgroundX, ambientBackgroundY);
                break;
        }
        gc.drawImage(stageForeground, foreGroundPositionX, foreGroundPositionY);
    }

    private void drawDamageLayer(GraphicsContext gc) {
        if ((getCharacterHp() / getCharacterMaximumHp()) < 0.66f) {
            damageLayerOpacity = 6.66f - ((getCharacterHp() / getCharacterMaximumHp()) * 10);
        }
        gc.setGlobalAlpha(damageLayerOpacity * 0.1f);
        gc.drawImage(damageLayer, 0, 0);
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
        gc.fillText(battleInformation.toString(), 32 + attackMenuXPos, 470);
        gc.setGlobalAlpha((1.0f));
    }

    private void drawAttackMenu(GraphicsContext gc) {
        if (opac < 0.95f) {
            opac = opac + 0.05f;
        }
        gc.setGlobalAlpha(opac);
        /*
        drawAttackItem(gc, attackMenuTextXPos, attackMenuTextYPos - (24 * 7), physicalAttacks[0], attackOne);
        drawAttackItem(gc, attackMenuTextXPos, attackMenuTextYPos - (24 * 6), physicalAttacks[1], attackTwo);
        drawAttackItem(gc, attackMenuTextXPos, attackMenuTextYPos - (24 * 5), physicalAttacks[2], attackThree);
        drawAttackItem(gc, attackMenuTextXPos, attackMenuTextYPos - (24 * 4), physicalAttacks[3], attackFour);
        drawAttackItem(gc, attackMenuTextXPos, attackMenuTextYPos - (24 * 3), celestiaAttacks[0], attackFive);
        drawAttackItem(gc, attackMenuTextXPos, attackMenuTextYPos - (24 * 2), celestiaAttacks[1], attackSix);
        drawAttackItem(gc, attackMenuTextXPos, attackMenuTextYPos - (24 * 1), celestiaAttacks[2], attackSeven);
        drawAttackItem(gc, attackMenuTextXPos, attackMenuTextYPos - (24 * 0), celestiaAttacks[3], attackEight);
        drawAttackItem(gc, attackMenuTextXPos - 6, attackMenuTextYPos + (24 * 1), itemAttacks[0], attackNine);
        drawAttackItem(gc, attackMenuTextXPos - 12, attackMenuTextYPos + (24 * 2), itemAttacks[1], attackTen);
        drawAttackItem(gc, attackMenuTextXPos - 18, attackMenuTextYPos + (24 * 3), itemAttacks[2], attackEleven);
        drawAttackItem(gc, attackMenuTextXPos - 24, attackMenuTextYPos + (24 * 4), itemAttacks[3], attackTwelve);
*/
        switch (columnIndex) {
            case 0:
                drawAttackItem(gc, attackMenuTextXPos - 6, attackMenuTextYPos + (24), physicalAttacks[0], attackOne);
                drawAttackItem(gc, attackMenuTextXPos - 12, attackMenuTextYPos + (24 * 2), physicalAttacks[1], attackTwo);
                drawAttackItem(gc, attackMenuTextXPos - 18, attackMenuTextYPos + (24 * 3), physicalAttacks[2], attackThree);
                drawAttackItem(gc, attackMenuTextXPos - 24, attackMenuTextYPos + (24 * 4), physicalAttacks[3], attackFour);
                break;
            case 1:
                drawAttackItem(gc, attackMenuTextXPos - 6, attackMenuTextYPos + (24), celestiaAttacks[0], attackFive);
                drawAttackItem(gc, attackMenuTextXPos - 12, attackMenuTextYPos + (24 * 2), celestiaAttacks[1], attackSix);
                drawAttackItem(gc, attackMenuTextXPos - 18, attackMenuTextYPos + (24 * 3), celestiaAttacks[2], attackSeven);
                drawAttackItem(gc, attackMenuTextXPos - 24, attackMenuTextYPos + (24 * 4), celestiaAttacks[3], attackEight);
                break;
            case 2:
                drawAttackItem(gc, attackMenuTextXPos - 6, attackMenuTextYPos + (24), itemAttacks[0], attackNine);
                drawAttackItem(gc, attackMenuTextXPos - 12, attackMenuTextYPos + (24 * 2), itemAttacks[1], attackTen);
                drawAttackItem(gc, attackMenuTextXPos - 18, attackMenuTextYPos + (24 * 3), itemAttacks[2], attackEleven);
                drawAttackItem(gc, attackMenuTextXPos - 24, attackMenuTextYPos + (24 * 4), itemAttacks[3], attackTwelve);
                break;
        }

        gc.setGlobalAlpha(1.0f);
        gc.setFill(Color.BLACK);
        double diameter = 40;
        gc.fillArc(426 - 100, 420, diameter, diameter, 0, 360, ArcType.ROUND);
        gc.fillArc(426 - 50, 420, diameter, diameter, 0, 360, ArcType.ROUND);
        gc.fillArc(426 + 5, 420, diameter, diameter, 0, 360, ArcType.ROUND);
        gc.fillArc(426 + 55, 420, diameter, diameter, 0, 360, ArcType.ROUND);
        gc.fillRect(426 - 70, 438, 140, 4);
        gc.setFill(Color.WHITE);
        diameter = 35;
        if (characterAttacks.size() >= 1 || triggerCharacterAttack)
            gc.fillArc(426 - 97.5, 422.5, diameter, diameter, 0, 360, ArcType.ROUND);
        if (characterAttacks.size() >= 2 || triggerCharacterAttack)
            gc.fillArc(426 - 47.5, 422.5, diameter, diameter, 0, 360, ArcType.ROUND);
        if (characterAttacks.size() >= 3 || triggerCharacterAttack)
            gc.fillArc(426 + 7.5, 422.5, diameter, diameter, 0, 360, ArcType.ROUND);
        if (characterAttacks.size() >= 4 || triggerCharacterAttack)
            gc.fillArc(426 + 57.5, 422.5, diameter, diameter, 0, 360, ArcType.ROUND);
        gc.setGlobalAlpha((1.0f));
    }

    private void drawAttackItem(GraphicsContext gc, int x, int y, String attack, UiItem uiItem) {
        gc.setFont(uiItem.isHovered() ? largeFont : normalFont);

        int half = 2;
        int full = 6;
        boolean validHighlight = uiItem.isHovered() && safeToSelect;

        double computedSize =Utils.computeStringWidth(attack, gc.getFont()) + full;
        double width = computedSize < 150 ? 150 : computedSize;
        double height = gc.getFont().getSize() + full;

        gc.setFill(validHighlight ? Color.WHITE : Color.BLACK);
        gc.strokeRoundRect(x - half, y - gc.getFont().getSize() - half, width, height, half, half);

        gc.setFill(validHighlight ? Color.BLACK : Color.WHITE);
        gc.setGlobalAlpha(validHighlight ? 1.0f : 0.5f);
        gc.fillRoundRect(x - half, y - gc.getFont().getSize() - half, width, height, half, half);

        gc.setFill(validHighlight ? Color.WHITE : Color.BLACK);
        gc.setGlobalAlpha(validHighlight ? 1.0f : 0.5f);
        fillText(gc, attack, x, y, uiItem, width, height);


    }

    private void drawTimer(GraphicsContext gc) {
        gc.drawImage(counterPane, paneCord, 0);
        if (timeLimit > 180) {
            gc.drawImage(numberPix[11], (int) (386), 0);
        } else {
            if (times.length > time1)
                gc.drawImage(times[time1], 356, 0);
            if (times.length > time2)
                gc.drawImage(times[time2], 356 + 40, 0);
            if (times.length > time3)
                gc.drawImage(times[time3], 356 + 80, 0);
        }
    }

    private void drawFuryBar(GraphicsContext gc) {
        drawImage(gc, furyState, 20 + ((uiShakeEffectOffsetOpponent + uiShakeEffectOffsetCharacter) / 2), 190 - ((uiShakeEffectOffsetOpponent + uiShakeEffectOffsetCharacter) / 2), fury);
        gc.drawImage(furyBar, 10 + ((uiShakeEffectOffsetOpponent + uiShakeEffectOffsetCharacter) / 2), furyBarY - ((uiShakeEffectOffsetOpponent + uiShakeEffectOffsetCharacter) / 2));
        gc.setFill(Color.RED);
        gc.fillRoundRect(12 + ((uiShakeEffectOffsetOpponent + uiShakeEffectOffsetCharacter) / 2), 132 - ((uiShakeEffectOffsetOpponent + uiShakeEffectOffsetCharacter) / 2), 12, getFuryLevel() / 5, 12, 12);
    }

    private void drawFuryComboEffects(GraphicsContext gc) {
        if (furyComboOpacity > 0.01f) {
            furyComboOpacity -= 0.01f;
        }
        gc.setGlobalAlpha((furyComboOpacity));
        if (comboPicArrayPosOpp < 9)
            gc.drawImage(comboPicArray[comboPicArrayPosOpp], comX + ((uiShakeEffectOffsetOpponent + uiShakeEffectOffsetCharacter) / 2), comY - ((uiShakeEffectOffsetOpponent + uiShakeEffectOffsetCharacter) / 2));
        gc.setGlobalAlpha((1.0f));
        gc.setFont(notSelected);
    }

    private void drawDamageDigits(GraphicsContext gc) {
        gc.setGlobalAlpha((opponentDamageOpacity));
        //opp damage loader
        gc.drawImage(figGuiSrc1, playerDamageXLoc + uiShakeEffectOffsetCharacter, opponentDamageYLoc - uiShakeEffectOffsetCharacter);
        gc.drawImage(figGuiSrc2, playerDamageXLoc + (spacer) + uiShakeEffectOffsetCharacter, opponentDamageYLoc - uiShakeEffectOffsetCharacter);
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
        //char damage loader
        gc.drawImage(figGuiSrc10, opponentDamageXLoc + uiShakeEffectOffsetOpponent, playerDamageYCoord - uiShakeEffectOffsetOpponent);
        gc.drawImage(figGuiSrc20, opponentDamageXLoc + (spacer) + uiShakeEffectOffsetOpponent, playerDamageYCoord - uiShakeEffectOffsetOpponent);
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
        Loader pix = new Loader();
        counterPane = pix.load("images/countPane.png");
        num0 = pix.load("images/fig/0.png");
        num1 = pix.load("images/fig/1.png");
        num2 = pix.load("images/fig/2.png");
        num3 = pix.load("images/fig/3.png");
        num4 = pix.load("images/fig/4.png");
        num5 = pix.load("images/fig/5.png");
        num6 = pix.load("images/fig/6.png");
        num7 = pix.load("images/fig/7.png");
        num8 = pix.load("images/fig/8.png");
        num9 = pix.load("images/fig/9.png");
        numInfinite = pix.load("images/fig/infinite.png");
        numNull = pix.load("images/trans.png");
        numberPix = new Image[]{num0, num1, num2, num3, num4, num5, num6, num7, num8, num9, numNull, numInfinite};
        statusEffectSprites[0] = pix.load("images/trans.png");
        statusEffectSprites[1] = pix.load("images/stats/stat1.png");
        statusEffectSprites[2] = pix.load("images/stats/stat2.png");
        statusEffectSprites[3] = pix.load("images/stats/stat3.png");
        statusEffectSprites[4] = pix.load("images/stats/stat4.png");
        System.out.println("loaded all loader");
    }

    /**
     * EPIC!!!! Loads har sprites
     */
    private void loadSprites() {
        try {
            Loader loader = new Loader();
            Characters.get().getCharacter().loadMeHigh();
            Characters.get().getOpponent().loadMeHigh();

            charSprites = new Image[Characters.get().getCharacter().getNumberOfSprites()];
            for (int i = 0; i < charSprites.length; i++)
                charSprites[i] = Characters.get().getCharacter().getSprite(i);

            oppSprites = new Image[Characters.get().getOpponent().getNumberOfSprites()];
            for (int i = 0; i < oppSprites.length; i++)
                oppSprites[i] = Characters.get().getOpponent().getSprite(i);

            comboPicArray = new Image[9];
            for (int u = 0; u < 6; u++)
                comboPicArray[u] = loader.load("images/screenTxt/" + u + ".png");
            comboPicArray[7] = loader.load("images/screenTxt/7.png");
            comboPicArray[8] = Characters.get().getCharacter().getSprite(11);

            comicBookText = new Image[10];
            comicBookText[0] = Characters.get().getCharacter().getSprite(11);
            for (int bx = 1; bx < numOfComicPics + 1; bx++)
                comicBookText[bx] = loader.load("images/screenComic/" + (bx - 1) + ".png");
            damageLayer = loader.load("images/damage1.png");

            time0i = loader.load("images/fig/0.png");
            time1i = loader.load("images/fig/1.png");
            time2i = loader.load("images/fig/2.png");
            time3i = loader.load("images/fig/3.png");
            time4i = loader.load("images/fig/4.png");
            time5i = loader.load("images/fig/5.png");
            time6i = loader.load("images/fig/6.png");
            time7i = loader.load("images/fig/7.png");
            time8i = loader.load("images/fig/8.png");
            time9i = loader.load("images/fig/9.png");
            times = new Image[]{time0i, time1i, time2i, time3i, time4i, time5i, time6i, time7i, time8i, time9i};

            if (ScndGenLegends.get().getSubMode() == SubMode.STORY_MODE) {
                characterPortraits = new Image[charNames.length];
                for (CharacterEnum characterEnum : CharacterEnum.values()) {
                    characterPortraits[characterEnum.index()] = loader.load("images/" + characterEnum.data() + "/cap.png");
                }
                storyBoards = new Image[12];
                for (int u = 0; u < storyBoards.length; u++) {
                    storyBoards[u] = loader.load("images/story/s" + u + ".png");
                }
            }
            Image transBuf = loader.load("images/trans.png");
            hpHolder = loader.load("images/hpHolder.png");
            hpHolderOpponent = loader.load("images/hpHolderOpponent.png");
            stageBackground = loader.load(RenderStageSelect.get().getStageBackground());
            stageForeground = loader.load(RenderStageSelect.get().getStageForeground());
            stageAmbientForeground = loader.load(RenderStageSelect.get().getFgLocation1());
            stageAmbientBackground = loader.load(RenderStageSelect.get().getFgLocation2());
            furyActive = loader.load("images/fury.gif");
            furyInactive = loader.load("images/furyo.png");
            furyState = furyInactive;

            furyBar = loader.load("images/furyBar.png");
            oppBar = loader.load("images/oppBar.png");
            hud1 = loader.load("images/hud1.png");
            characterHpBar = loader.load("images/hud2.png");
            win = loader.load("images/win.png");
            lose = loader.load("images/lose.png");
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

    public void keyPressed(KeyEvent keyEvent) {
        KeyCode keyCode = keyEvent.getCode();
        switch (keyCode) {
            case UP:
            case W:
                onUp();
                break;
            case DOWN:
            case S:
                onDown();
                break;
            case LEFT:
            case D:
                onLeft();
                break;
            case RIGHT:
            case A:
                onRight();
                break;
            case ENTER:
                onAccept();
                break;
            case BACK_SPACE:
                unQueMove();
            case ESCAPE:
                onBackCancel();
                break;
            case L:
                setActiveItem(fury);
                fury.accept();
                break;
            case F5:
                cancelMatch();
                break;
        }
    }

    public void reloadAssets() {
        loadAssets = true;
    }

    public void mouseScrolled(ScrollEvent scrollEvent) {
        if (scrollEvent.getDeltaY() > 0)
            onLeft();
        else
            onRight();
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

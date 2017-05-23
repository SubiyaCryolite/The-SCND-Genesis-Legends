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
import com.scndgen.legends.characters.Characters;
import com.scndgen.legends.constants.AudioConstants;
import com.scndgen.legends.enums.AudioType;
import com.scndgen.legends.enums.CharacterEnum;
import com.scndgen.legends.enums.Player;
import com.scndgen.legends.enums.SubMode;
import com.scndgen.legends.mode.GamePlay;
import com.scndgen.legends.mode.StoryMode;
import com.scndgen.legends.network.NetworkManager;
import com.scndgen.legends.state.GameState;
import com.scndgen.legends.ui.Event;
import com.scndgen.legends.ui.UiItem;
import io.github.subiyacryolite.enginev1.AudioPlayback;
import io.github.subiyacryolite.enginev1.Loader;
import io.github.subiyacryolite.enginev1.Overlay;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;

import static com.sun.javafx.tk.Toolkit.getToolkit;

/**
 * @author Ifunga Ndana
 */
public class RenderGamePlay extends GamePlay {
    private static RenderGamePlay instance;
    private Font largeFont, normalFont;
    private Font notSelected;
    private Image particlesLayer1, particlesLayer2, foreGround;
    private LinearGradient gradient1 = new LinearGradient(xLocal, 10, 255, 10, true, CycleMethod.REFLECT, new Stop(0.0, Color.YELLOW), new Stop(1.0, Color.RED));
    private LinearGradient gradient3 = new LinearGradient(0, 0, 100, 100, true, CycleMethod.REFLECT, new Stop(0.0, Color.YELLOW), new Stop(1.0, Color.RED));
    private Image flashy;
    private Image[] attackCategory, numberPix;
    private Image[] characterPortraits;
    private Image[] comboPicArray, comicBookText, times, statusEffectSprites = new Image[5];
    private Image oppBar, quePic1, furyBar, counterPane, quePic2, num0, num1, num2, num3, num4, num5, num6, num7, num8, num9, numNull, stageBackground, damageLayer, hpHolder, hud1, hud2, win, lose, status, menuHold, furyPlaceholder, fury1, fury2, phys, cel, itm, numInfinite, figGuiSrc10, figGuiSrc20, figGuiSrc30, figGuiSrc40, figGuiSrc1, figGuiSrc2, figGuiSrc3, figGuiSrc4, time0i, time1i, time2i, time3i, time4i, time5i, time6i, time7i, time8i, time9i;
    private Image[] charSprites, oppSprites;
    private Image[] storyPicArr;
    private Image characterPortrait, storyPic;
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


    public RenderGamePlay() {
        (fury = new UiItem()).addJenesisEvent(new Event() {
            @Override
            public void onAccept() {
                triggerFury(Player.CHARACTER);
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

    public static synchronized RenderGamePlay getInstance() {
        if (instance == null)
            instance = new RenderGamePlay();
        return instance;
    }

    public void loadAssetsIml() {
        loadAssets = false;
        notSelected = getMyFont(12);
        largeFont = getMyFont(LoginScreen.bigTxtSize);
        normalFont = getMyFont(LoginScreen.normalTxtSize);
        setCharMoveset();
        cacheNumPix();
        loadSprites();
        if (ScndGenLegends.getInstance().getSubMode() == SubMode.LAN_HOST) {
            server = NetworkManager.getInstance().getServer();
        }
        if (ScndGenLegends.getInstance().getSubMode() == SubMode.LAN_CLIENT) {
            //get ip from game
            client = NetworkManager.getInstance().getClient();
        }
        charPointInc = Characters.getInstance().getPoints();
    }

    public void cleanAssets() {
        loadAssets = true;
    }

    @Override
    public void render(GraphicsContext gc, double width, double height) {
        loadAssets();
        if (storySequence) {
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

        } else if (!gameOver && !storySequence) {
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
                gc.fillRoundRect((x2 - 17) + uiShakeEffectOffsetOpponent, (y2 + 22 - oppBarYOffset) - uiShakeEffectOffsetOpponent, getOpponentAtbValue(), 6, 6, 6);

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
                gc.setGlobalAlpha((10 * 0.1f)); //op onBackCancel to normal for other drawings
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
            gc.setGlobalAlpha((10 * 0.1f));
            gc.setFill(Color.WHITE);
            gc.fillText(Language.getInstance().get(148), 400, 240);
            gc.fillText(Language.getInstance().get(149), 400, 260);
            gc.fillText(Language.getInstance().get(150), 400, 280);
        }

        //when gameover
        if (gameOver) {
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
        Overlay.getInstance().overlay(gc, width, height);
    }

    private void drawStageBackground(GraphicsContext gc) {
        switch (animLayer) {
            case BOTH:
                gc.drawImage(particlesLayer2, particlesLayer2PositionX, particlesLayer2PositionY);
                break;
            case BACKGROUND:
                gc.drawImage(particlesLayer1, particlesLayer1PositionX, particlesLayer1PositionY);
                gc.drawImage(particlesLayer2, particlesLayer2PositionX, particlesLayer2PositionY);
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
        switch (animLayer) {
            case BOTH:
                gc.drawImage(particlesLayer1, particlesLayer1PositionX, particlesLayer1PositionY);
                break;
            case FOREGROUND:
                gc.drawImage(particlesLayer1, particlesLayer1PositionX, particlesLayer1PositionY);
                gc.drawImage(particlesLayer2, particlesLayer2PositionX, particlesLayer2PositionY);
                break;
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
        gc.drawImage(menuHold, attackMenuXPos, menuBarY);
        for (int queItem = 0; queItem < 4; queItem++) {
            gc.drawImage(quePic1, (queItem * 70 + 5 + attackMenuXPos), 440);
        }
        if (characterAttacks.size() >= 1) {
            for (int queItem = 0; queItem < characterAttacks.size(); queItem++) {
                gc.drawImage(quePic2, (queItem * 70 + 5 + attackMenuXPos), 440);
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
        gc.fillText(battleInformation.toString(), 32 + attackMenuXPos, 470);
        gc.setGlobalAlpha((1.0f));
    }

    private void drawAttackMenu(GraphicsContext gc) {
        if (opac < 0.95f) {
            opac = opac + 0.05f;
        }
        gc.setGlobalAlpha((opac));
        gc.setFill(currentColor);
        gc.drawImage(attackCategory[columnIndex], itemX + attackMenuXPos, itemY);
        switch (columnIndex) {
            case 0:
                gc.setFont(attackOne.isHovered() ? largeFont : normalFont);
                fillText(gc, physicalAttacks[0], attackMenuTextXPos, attackMenuTextYPos + fontSizes[0], attackOne);
                gc.setFont(attackTwo.isHovered() ? largeFont : normalFont);
                fillText(gc, physicalAttacks[1], attackMenuTextXPos, attackMenuTextYPos + fontSizes[0] + 2 + fontSizes[1], attackTwo);
                gc.setFont(attackThree.isHovered() ? largeFont : normalFont);
                fillText(gc, physicalAttacks[2], attackMenuTextXPos, attackMenuTextYPos + fontSizes[0] + 2 + fontSizes[1] + 2 + fontSizes[2], attackThree);
                gc.setFont(attackFour.isHovered() ? largeFont : normalFont);
                fillText(gc, physicalAttacks[3], attackMenuTextXPos, attackMenuTextYPos + fontSizes[0] + 2 + fontSizes[1] + 2 + fontSizes[2] + 2 + fontSizes[3], attackFour);
                break;
            case 1:
                gc.setFont(attackFive.isHovered() ? largeFont : normalFont);
                fillText(gc, celestiaAttacks[0], attackMenuTextXPos, attackMenuTextYPos + fontSizes[0], attackFive);
                gc.setFont(attackSix.isHovered() ? largeFont : normalFont);
                fillText(gc, celestiaAttacks[1], attackMenuTextXPos, attackMenuTextYPos + fontSizes[0] + 2 + fontSizes[1], attackSix);
                gc.setFont(attackSeven.isHovered() ? largeFont : normalFont);
                fillText(gc, celestiaAttacks[2], attackMenuTextXPos, attackMenuTextYPos + fontSizes[0] + 2 + fontSizes[1] + 2 + fontSizes[2], attackSeven);
                gc.setFont(attackEight.isHovered() ? largeFont : normalFont);
                fillText(gc, celestiaAttacks[3], attackMenuTextXPos, attackMenuTextYPos + fontSizes[0] + 2 + fontSizes[1] + 2 + fontSizes[2] + 2 + fontSizes[3], attackEight);
                break;
            case 2:
                gc.setFont(attackNine.isHovered() ? largeFont : normalFont);
                fillText(gc, itemAttacks[0], attackMenuTextXPos, attackMenuTextYPos + fontSizes[0], attackNine);
                gc.setFont(attackTen.isHovered() ? largeFont : normalFont);
                fillText(gc, itemAttacks[1], attackMenuTextXPos, attackMenuTextYPos + fontSizes[0] + 2 + fontSizes[1], attackTen);
                gc.setFont(attackEleven.isHovered() ? largeFont : normalFont);
                fillText(gc, itemAttacks[2], attackMenuTextXPos, attackMenuTextYPos + fontSizes[0] + 2 + fontSizes[1] + 2 + fontSizes[2], attackEleven);
                gc.setFont(attackTwelve.isHovered() ? largeFont : normalFont);
                fillText(gc, itemAttacks[3], attackMenuTextXPos, attackMenuTextYPos + fontSizes[0] + 2 + fontSizes[1] + 2 + fontSizes[2] + 2 + fontSizes[3], attackTwelve);
                break;
        }
        gc.setGlobalAlpha((1.0f));
    }

    private void drawTimer(GraphicsContext gc) {
        gc.drawImage(counterPane, paneCord, 0);
        if (time > 180) {
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
        drawImage(gc, furyPlaceholder, 20 + ((uiShakeEffectOffsetOpponent + uiShakeEffectOffsetCharacter) / 2), 190 - ((uiShakeEffectOffsetOpponent + uiShakeEffectOffsetCharacter) / 2), fury);
        gc.drawImage(furyBar, 10 + ((uiShakeEffectOffsetOpponent + uiShakeEffectOffsetCharacter) / 2), furyBarY - ((uiShakeEffectOffsetOpponent + uiShakeEffectOffsetCharacter) / 2));
        gc.setFill(Color.RED);
        gc.fillRoundRect(12 + ((uiShakeEffectOffsetOpponent + uiShakeEffectOffsetCharacter) / 2), 132 - ((uiShakeEffectOffsetOpponent + uiShakeEffectOffsetCharacter) / 2), 12, getBreak() / 5, 12, 12);
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
        //char damage loader
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
            furyPlaceholder = fury1;
        } else {
            furyPlaceholder = fury2;
        }
        if (gameOver == true) {
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
        attackMenuXPos = GameState.getInstance().getLogin().isLeftHanded() ? 0 : 547;
        attackMenuTextXPos = attackMenuXPos + 25;
        attackMenuTextYPos = 366;
        Loader pix = new Loader();
        counterPane = pix.load("images/countPane.png");
        foreGround = pix.load(RenderStageSelect.getInstance().getFgLocation());
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
        //flashy=loader.load("images/flash.gif",40,40);
        flashy = null;
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
                comboPicArray[u] = loader.load("images/screenTxt/" + u + ".png");
            comboPicArray[7] = loader.load("images/screenTxt/7.png");
            comboPicArray[8] = Characters.getInstance().getCharacter().getSprite(11);

            comicBookText = new Image[10];
            comicBookText[0] = Characters.getInstance().getCharacter().getSprite(11);
            for (int bx = 1; bx < numOfComicPics + 1; bx++)
                comicBookText[bx] = loader.load("images/screenComic/" + (bx - 1) + ".png");
            menuHold = loader.load("images/" + Characters.getInstance().getCharacter().getEnum().data() + "/menu.png");
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

            characterPortraits = new Image[charNames.length];
            if (ScndGenLegends.getInstance().getSubMode() == SubMode.STORY_MODE) {
                for (CharacterEnum characterEnum : CharacterEnum.values()) {
                    characterPortraits[characterEnum.index()] = loader.load("images/" + characterEnum.data() + "/cap.png");
                }
            } else {
                for (int p = 0; p < charNames.length; p++) {
                    characterPortraits[p] = null;
                }
            }
            Image transBuf = loader.load("images/trans.png");
            hpHolder = loader.load("images/hpHolder.png");
            stageBackground = loader.load(RenderStageSelect.getInstance().getBgLocation());
            phys = loader.load("images/t_physical.png");
            cel = loader.load("images/t_celestia.png");
            itm = loader.load("images/t_item.png");
            fury1 = loader.load("images/fury.gif");
            fury2 = loader.load("images/furyo.png");
            furyPlaceholder = fury2;
            particlesLayer1 = loader.load("images/bgBG" + RenderStageSelect.getInstance().getHoveredStage().filePrefix() + "a.png");
            particlesLayer2 = loader.load("images/bgBG" + RenderStageSelect.getInstance().getHoveredStage().filePrefix() + "b.png");
            if (ScndGenLegends.getInstance().getSubMode() == SubMode.STORY_MODE) {
                storyPicArr = new Image[13];
                for (int u = 0; u < 11; u++) {
                    storyPicArr[u] = loader.load("images/story/s" + u + ".png");
                }
                storyPic = storyPicArr[0];
            }
            furyBar = loader.load("images/furyBar.png");
            quePic1 = loader.load("images/queB.png");
            quePic2 = loader.load("images/que.gif");
            oppBar = loader.load("images/oppBar.png");
            attackCategory = new Image[]{phys, cel, itm};
            //stat1 = loader.load("images/stats/stat1.png", 90, 24);
            //stat2 = loader.load("images/stats/stat2.png", 90, 24);
            //stat3 = loader.load("images/stats/stat3.png", 90, 24);
            //stat4 = loader.load("images/stats/stat4.png", 90, 24);
            hud1 = loader.load("images/hud1.png");
            hud2 = loader.load("images/hud2.png");
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
    }

    /**
     * displays damage graphically
     *
     * @param damageAmount - damage dealt
     * @param who          - who dealt the damage
     */
    public void guiScreenChaos(float damageAmount, Player who) {
        manipulateThis = "" + Math.round(damageAmount);
        if (who == Player.CHARACTER) {
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

        if (who == Player.OPPONENT) {
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
            randSoundIntChar = (int) (Math.random() * AudioConstants.MALE_HURT.length * 2);
            if (randSoundIntChar < AudioConstants.MALE_HURT.length) {
                attackChar = new AudioPlayback(AudioConstants.maleAttack(randSoundIntChar), AudioType.VOICE, false);
                attackChar.play();
            }
        } else {
            randSoundIntChar = (int) (Math.random() * AudioConstants.FEMALE_HURT.length * 2);
            if (randSoundIntChar < AudioConstants.FEMALE_HURT.length) {
                attackChar = new AudioPlayback(AudioConstants.femaleAttack(randSoundIntChar), AudioType.VOICE, false);
                attackChar.play();
            }
        }
    }

    protected void attackSoundOpp() {
        if (Characters.getInstance().getOpponent().isMale()) {
            randSoundIntOpp = (int) (Math.random() * AudioConstants.MALE_HURT.length * 2);
            if (randSoundIntOpp < AudioConstants.MALE_HURT.length) {
                attackOpp = new AudioPlayback(AudioConstants.maleAttack(randSoundIntOpp), AudioType.VOICE, false);
                attackOpp.play();
            }
        } else {
            randSoundIntOpp = (int) (Math.random() * AudioConstants.FEMALE_HURT.length * 2);
            if (randSoundIntOpp < AudioConstants.FEMALE_HURT.length) {
                attackOpp = new AudioPlayback(AudioConstants.femaleAttack(randSoundIntOpp), AudioType.VOICE, false);
                attackOpp.play();
            }
        }
    }

    protected void hurtSoundChar() {
        if (Characters.getInstance().getOpponent().isMale()) {
            randSoundIntCharHurt = (int) (Math.random() * AudioConstants.MALE_ATTACKS.length * 2);
            if (randSoundIntCharHurt < AudioConstants.MALE_ATTACKS.length) {
                hurtChar = new AudioPlayback(AudioConstants.maleHurt(randSoundIntCharHurt), AudioType.VOICE, false);
                hurtChar.play();
            }
        } else {
            randSoundIntCharHurt = (int) (Math.random() * AudioConstants.FEMALE_ATTACKS.length * 2);
            if (randSoundIntCharHurt < AudioConstants.FEMALE_ATTACKS.length) {
                hurtChar = new AudioPlayback(AudioConstants.femaleHurt(randSoundIntCharHurt), AudioType.VOICE, false);
                hurtChar.play();
            }
        }
    }

    public void hurtSoundOpp() {
        if (Characters.getInstance().getCharacter().isMale()) {
            randSoundIntOppHurt = (int) (Math.random() * AudioConstants.MALE_ATTACKS.length * 2);
            if (randSoundIntOppHurt < AudioConstants.MALE_ATTACKS.length) {
                hurtOpp = new AudioPlayback(AudioConstants.maleHurt(randSoundIntOppHurt), AudioType.VOICE, false);
                hurtOpp.play();
            }
        } else {
            randSoundIntOppHurt = (int) (Math.random() * AudioConstants.FEMALE_ATTACKS.length * 2);
            if (randSoundIntOppHurt < AudioConstants.FEMALE_ATTACKS.length) {
                hurtOpp = new AudioPlayback(AudioConstants.femaleHurt(randSoundIntOppHurt), AudioType.VOICE, false);
                hurtOpp.play();
            }
        }
    }

    public void furySound() {
        furySound.play();
    }

    private void nrmlDamageSound() {
        damageSound.play();
    }

    private void setRandomPic() {
        comicBookTextIndex = Math.round((float) (numOfComicPics * Math.random()));
        comicBookTextOpacity = 1.0f;
        comicBookTextPositionY = 0;
    }

    public void comicText() {
        if (GameState.getInstance().getLogin().getComicEffectOccurence() > 0) {
            int randomInt = Math.round((float) (Math.random() * GameState.getInstance().getLogin().getComicEffectOccurence()));
            if (randomInt == 1) {
                setRandomPic();
            }
        }
    }

    public synchronized void playBGMusic() {
        if (ambientMusic != null) {
            ambientMusic.stop();
        }
        ambientMusic = new AudioPlayback("audio/" + RenderStageSelect.getInstance().getAmbientMusic()[RenderStageSelect.getInstance().getAmbientMusicIndex()] + ".ogg", AudioType.MUSIC, true);
        ambientMusic.play();
    }

    /**
     * togglePause threads
     */
    public void pauseThreads() {
        try {
            ambientMusic.togglePause();
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
     * Calculates angle of circle
     *
     * @return circel angle
     */
    private int phyAngle() {
        float start = getCharacterAtbValue() / 290.0f;
        angleRaw = start * 360;
        result = Integer.parseInt("" + Math.round(angleRaw));
        if (result >= 360) {
            enableSelection();
        } else {
            disableSelection();
        }
        return result;
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

    private class PauseAndNavigate extends Event {
        @Override
        public void onAccept() {
            if (!gameOver && !storySequence) {
                if (safeToSelect) {
                    sound = new AudioPlayback(AudioConstants.selectSound(), AudioType.SOUND, false);
                    sound.play();
                    activeAttack = (columnIndex * 4) + (rowIndex + 1);
                    characterAttacks.push(activeAttack); // count initially negative 1, add one to get to index 0
                    checkStatus();
                    showBattleMessage("Queued up " + getAttack(activeAttack));
                } else {
                    RenderCharacterSelection.getInstance().errorSound();
                }
            } else if (storySequence) {
                StoryMode.getInstance().onAccept();
            } else if (gameOver) {
                updatePlayerProfile();
                switch (ScndGenLegends.getInstance().getSubMode()) {
                    case SINGLE_PLAYER:
                    case LAN_CLIENT:
                    case LAN_HOST:
                        closingThread(true);
                        break;
                    case STORY_MODE:
                        StoryMode.getInstance().onAccept();
                        break;
                    default:
                        closingThread(false);
                        break;
                }
            }
        }

        @Override
        public void onBackCancel() {
            //closeTheServer();
            if (!gameOver && storySequence == false) {
                onTogglePause();
            } else if (storySequence) {
                StoryMode.getInstance().onBackCancel();
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

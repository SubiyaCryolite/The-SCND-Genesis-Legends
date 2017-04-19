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
import com.scndgen.legends.attacks.AttackOpponent;
import com.scndgen.legends.attacks.AttackPlayer;
import com.scndgen.legends.characters.Characters;
import com.scndgen.legends.enums.CharacterEnum;
import com.scndgen.legends.enums.CharacterState;
import com.scndgen.legends.enums.Stage;
import com.scndgen.legends.enums.SubMode;
import com.scndgen.legends.scene.Gameplay;
import com.scndgen.legends.threads.*;
import com.scndgen.legends.windows.MainWindow;
import io.github.subiyacryolite.enginev1.JenesisGlassPane;
import io.github.subiyacryolite.enginev1.JenesisImageLoader;
import io.github.subiyacryolite.enginev1.JenesisRender;

import javax.swing.*;
import java.awt.*;
import java.awt.image.VolatileImage;

/**
 * @author Ifunga Ndana
 */
public class RenderGameplay extends Gameplay implements JenesisRender {
    private static RenderGameplay instance;
    private Animations1 animations1;
    private Animations2 animations2;
    private Animations3 animations3;
    private Font largeFont, normalFont;
    private Font notSelected;
    private VolatileImage stat1, stat2, stat3, stat4;
    private VolatileImage particlesLayer1, particlesLayer2, foreGround;
    private GradientPaint gradient1 = new GradientPaint(xLocal, 10, Color.YELLOW, 255, 10, Color.RED, true);
    private GradientPaint gradient3 = new GradientPaint(0, 0, Color.YELLOW, 100, 100, Color.RED, true);
    private VolatileImage flashy;
    private Image[] moveCat, numberPix;
    private VolatileImage[] characterPortraits;
    private AudioPlayback sound, ambientMusic, furySound, damageSound, hurtChar, hurtOpp, attackChar, attackOpp;
    private Image[] comboPicArray, comicBookText, times, statusEffectSprites = new Image[5];
    private Image oppBar, quePic1, furyBar, counterPane, quePic2, num0, num1, num2, num3, num4, num5, num6, num7, num8, num9, numNull, stageBackground, damageLayer, hpHolder, hud1, hud2, win, lose, status, menuHold, fury, fury1, fury2, phys, cel, itm, curr, numInfinite, figGuiSrc10, figGuiSrc20, figGuiSrc30, figGuiSrc40, figGuiSrc1, figGuiSrc2, figGuiSrc3, figGuiSrc4, time0, time1, time2, time3, time4, time5, time6, time7, time8, time9;
    private Image[] charSprites, oppSprites;
    private VolatileImage[] attackAnim2, attackAnim1;
    private VolatileImage[] storyPicArr, stats;
    private VolatileImage characterPortrait, storyPic;
    private Color currentColor = (Color.RED);


    private RenderGameplay() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.black, 1));
    }

    public static synchronized RenderGameplay getInstance() {
        if (instance == null)
            instance = new RenderGameplay();
        return instance;
    }

    public void loadAssets() {
        if (!loadAssets) return;
        notSelected = LoginScreen.getInstance().getMyFont(12);
        largeFont = LoginScreen.getInstance().getMyFont(LoginScreen.bigTxtSize);
        normalFont = LoginScreen.getInstance().getMyFont(LoginScreen.normalTxtSize);
        setCharMoveset();
        LoginScreen.getInstance().defHeight = LoginScreen.getInstance().getGameHeight();
        cacheNumPix();
        loadSprites();
        if (MainWindow.getInstance().getGameMode() == SubMode.LAN_HOST) {
            server = MainWindow.getInstance().getServer();
        }
        if (MainWindow.getInstance().getGameMode() == SubMode.LAN_CLIENT) {
            //get ip from game
            client = MainWindow.getInstance().getClient();
        }
        charPointInc = Characters.getInstance().getPoints();
        loadAssets = false;
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
    public void paintComponent(Graphics g) {
        /* Fixed performance issues, got rid off geometry and replaced with static VolatileImage s -----facepalm*/
        createBackBuffer();
        loadAssets();
        if (GameInstance.getInstance().storySequence) {
            g2d.setColor(Color.BLACK);
            g2d.fillRect(0, 0, LoginScreen.getInstance().getdefSpriteWidth(), LoginScreen.getInstance().getdefSpriteHeight());

            if (opacityPic < 0.98f) {
                opacityPic = opacityPic + 0.02f;
            }
            g2d.setComposite(makeComposite(opacityPic));
            g2d.drawImage(storyPic, 0, 0, this);
            g2d.setComposite(makeComposite(10 * 0.1f));

            g2d.setComposite(makeComposite(0.5f));
            g2d.fillRoundRect(0, 424, LoginScreen.getInstance().getdefSpriteWidth(), 48, 48, 48); //mid minus half the font size (430-6)
            g2d.setComposite(makeComposite(10 * 0.1f));

            g2d.setColor(Color.WHITE);
            g2d.setFont(normalFont);
            g2d.drawString(Language.getInstance().getLine(146) + " >>", (852 - g2d.getFontMetrics().stringWidth(Language.getInstance().getLine(146) + " >>")), 462);


            if (opacityTxt < 0.98f) {
                opacityTxt = opacityTxt + 0.02f;
            }
            g2d.setComposite(makeComposite(opacityTxt));
            g2d.drawImage(characterPortrait, ((852 - g2d.getFontMetrics().stringWidth(battleInformation.toString())) / 2) - 50, 424, this);
            g2d.drawString(battleInformation.toString(), ((852 - g2d.getFontMetrics().stringWidth(battleInformation.toString())) / 2), 450);
            g2d.setComposite(makeComposite(10 * 0.1f));

        } else if (GameInstance.getInstance().gameOver == false && GameInstance.getInstance().storySequence == false) {
            g2d.drawImage(stageBackground, 0, 0, this);
            g2d.setFont(notSelected);
            if (RenderGameplay.getInstance().getCharLife() >= 0) {
                if (animLayer.equalsIgnoreCase("both")) {
                    g2d.drawImage(particlesLayer2, particlesLayer2PositionX, particlesLayer2PositionY, this);
                } else if (animLayer.equalsIgnoreCase("back")) {
                    g2d.drawImage(particlesLayer1, particlesLayer1PositionX, particlesLayer1PositionY, this);
                    g2d.drawImage(particlesLayer2, particlesLayer2PositionX, particlesLayer2PositionY, this);
                }

                if (getAttacksChar().isOverlayDisabled()) {
                    g2d.drawImage(charSprites[charMeleeSpriteStatus], charXcord + uiShakeEffectOffsetCharacter, charYcord - uiShakeEffectOffsetCharacter, this);
                }

                g2d.drawImage(oppSprites[oppMeleeSpriteStatus], LoginScreen.getGameWidth(), (int) (oppYcord), (int) (oppXcord), LoginScreen.getInstance().getGameHeight(), (int) oppYcord, (int) (oppXcord), LoginScreen.getInstance().getGameWidth(), LoginScreen.getInstance().getGameHeight(), this);


                if (!getAttacksChar().isOverlayDisabled()) {
                    g2d.drawImage(charSprites[charMeleeSpriteStatus], charXcord + uiShakeEffectOffsetCharacter, charYcord - uiShakeEffectOffsetCharacter, this);
                }

                if (animLayer.equalsIgnoreCase("both")) {
                    g2d.drawImage(particlesLayer1, particlesLayer1PositionX, particlesLayer1PositionY, this);
                } else if (animLayer.equalsIgnoreCase("forg")) {
                    g2d.drawImage(particlesLayer1, particlesLayer1PositionX, particlesLayer1PositionY, this);
                    g2d.drawImage(particlesLayer2, particlesLayer2PositionX, particlesLayer2PositionY, this);
                }

                /** characterEnum sprite on below, opponent on top
                 * "Java Tip 32: You'll flip over Java images -- literally! - JavaWorld"
                 * http://www.javaworld.com/javaworld/javatips/jw-javatip32.html?page=2
                 */
                if ((RenderGameplay.getInstance().getCharLife() / RenderGameplay.getInstance().getCharMaxLife()) < 0.66f) {
                    damageLayerOpacity = 6.66f - ((RenderGameplay.getInstance().getCharLife() / RenderGameplay.getInstance().getCharMaxLife()) * 10);
                }
                g2d.drawImage(foreGround, foreGroundPositionX, foreGroundPositionY, this);
                g2d.setComposite(makeComposite((float) damageLayerOpacity * 0.1f));
                g2d.drawImage(damageLayer, 0, 0, this);
                g2d.setComposite(makeComposite(1.0f));

                g2d.drawImage(menuHold, leftHandXAxisOffset, menuBarY, this);

                for (int xB = 0; xB < 4; xB++) {
                    g2d.drawImage(quePic1, (xB * 70 + 5 + leftHandXAxisOffset), (int) (LoginScreen.getInstance().getGameYScale() * 440), this);
                }

                if (RenderGameplay.getInstance().comboCounter >= 1) {
                    for (int xV = 0; xV < RenderGameplay.getInstance().comboCounter; xV++) {
                        g2d.drawImage(quePic2, (xV * 70 + 5 + leftHandXAxisOffset), (int) (LoginScreen.getInstance().getGameYScale() * 440), this);
                    }
                }


                if (comicBookTextOpacity >= 0.0f) {
                    comicBookTextOpacity = comicBookTextOpacity - 0.0125f;
                }
                g2d.setComposite(makeComposite(comicBookTextOpacity));
                g2d.drawImage(comicBookText[comicBookTextIndex], 170, 112 + basicY + comicBookTextPositionY, this);
                g2d.setComposite(makeComposite(1.0f));
                comicBookTextPositionY = comicBookTextPositionY + 3;

                if (opacityTxt < 0.98f) {
                    opacityTxt = opacityTxt + 0.02f;
                }
                g2d.setComposite(makeComposite(opacityTxt));
                g2d.setColor(Color.WHITE);
                g2d.drawString(battleInformation.toString(), 32 + leftHandXAxisOffset, 470);
                g2d.setComposite(makeComposite(1.0f));

                //-----------draws battle info messages--------------


                //STATS

                if (statusEffectCharacterOpacity > 0.02f) {
                    statusEffectCharacterOpacity = statusEffectCharacterOpacity - 0.02f;
                }
                g2d.setComposite(makeComposite(statusEffectCharacterOpacity));
                g2d.drawImage(statusEffectSprites[statIndexChar], 150 + uiShakeEffectOffsetCharacter, 100 + basicY - uiShakeEffectOffsetCharacter + statusEffectCharacterYCoord, this);
                g2d.setComposite(makeComposite(1.0f));
                statusEffectCharacterYCoord = statusEffectCharacterYCoord + 1;


                if (statusEffectOpponentOpacity > 0.02f) {
                    statusEffectOpponentOpacity = statusEffectOpponentOpacity - 0.02f;
                }
                g2d.setComposite(makeComposite(statusEffectOpponentOpacity));
                g2d.drawImage(statusEffectSprites[statIndexOpp], 602 + uiShakeEffectOffsetOpponent, 100 + basicY - uiShakeEffectOffsetOpponent + statusEffectOpponentYCoord, this);
                g2d.setComposite(makeComposite(1.0f));
                statusEffectOpponentYCoord = statusEffectOpponentYCoord + 1;

                //---opponrnt activity bar + text

                g2d.drawImage(hpHolder, (45 + 62 + x2) + uiShakeEffectOffsetOpponent, (y + 4 + y2 - oppBarYOffset) - uiShakeEffectOffsetOpponent, this);
                g2d.setColor(Color.WHITE);
                g2d.drawString("HP: " + Math.round(RenderGameplay.getInstance().getOppLife()) + " : " + RenderGameplay.getInstance().perCent2 + "%", (55 + 64 + x2) + uiShakeEffectOffsetOpponent, (18 + y2 - oppBarYOffset) - uiShakeEffectOffsetOpponent);

                g2d.setColor(Color.BLACK);
                g2d.drawImage(oppBar, (x2 - 20) + uiShakeEffectOffsetOpponent, (y2 + 18 - oppBarYOffset) - uiShakeEffectOffsetOpponent, this);
                g2d.setPaint(gradient1);
                g2d.fillRoundRect((x2 - 17) + uiShakeEffectOffsetOpponent, (y2 + 22 - oppBarYOffset) - uiShakeEffectOffsetOpponent, GameInstance.getInstance().getRecoveryUnitsOpp(), 6, 6, 6);

                //------------player 1 HUD---------------------//
                g2d.drawImage(hpHolder, (lbx2 - 438) + uiShakeEffectOffsetCharacter, (lby2 - 410) - uiShakeEffectOffsetCharacter, this); // HOLDS hp
                //outline
                g2d.drawImage(hud1, (lbx2 - 498) + uiShakeEffectOffsetCharacter, (lby2 - 417) - uiShakeEffectOffsetCharacter, this);
                //inner
                //g2d.setColor(Color.RED);
                g2d.setPaint(gradient3);
                g2d.fillArc(lbx2 - 493 + uiShakeEffectOffsetCharacter, lby2 - 412 - uiShakeEffectOffsetCharacter, 90, 90, 0, phyAngle());
                //inner loop
                g2d.setColor(Color.BLACK);
                g2d.drawImage(hud2, lbx2 - 488 + uiShakeEffectOffsetCharacter, lby2 - 407 - uiShakeEffectOffsetCharacter, this);
                g2d.setColor(Color.WHITE);
                g2d.drawString("HP: " + Math.round(RenderGameplay.getInstance().getCharLife()) + " : " + RenderGameplay.getInstance().perCent + "%", (lbx2 - 416) + uiShakeEffectOffsetCharacter, (lby2 - 398) - uiShakeEffectOffsetCharacter);
                g2d.setComposite(makeComposite(10 * 0.1f)); //op back to normal for other drawings
            }

            g2d.drawImage(counterPane, paneCord, 0, this);

            if (GameInstance.getInstance().time > 180) {
                g2d.drawImage(numberPix[11], (int) (386), 0, this);
            } else {
                if (times.length > GameInstance.getInstance().time1)
                    g2d.drawImage(times[GameInstance.getInstance().time1], 356, 0, this);
                if (times.length > GameInstance.getInstance().time2)
                    g2d.drawImage(times[GameInstance.getInstance().time2], 356 + 40, 0, this);
                if (times.length > GameInstance.getInstance().time3)
                    g2d.drawImage(times[GameInstance.getInstance().time3], 356 + 80, 0, this);
            }


            {
                if (opac < 0.95f) {
                    opac = opac + 0.05f;
                }
                g2d.setComposite(makeComposite(opac));
                g2d.setColor(currentColor);
                g2d.drawImage(curr, itemX + leftHandXAxisOffset, itemY, this);

                if (fontSizes[0] == LoginScreen.bigTxtSize) {
                    g2d.setFont(largeFont);
                } else {
                    g2d.setFont(normalFont);
                }
                g2d.drawString(currentColumn[0], (yTEST + leftHandXAxisOffset), ((366) + fontSizes[0]));


                if (fontSizes[1] == LoginScreen.bigTxtSize) {
                    g2d.setFont(largeFont);
                } else {
                    g2d.setFont(normalFont);
                }
                g2d.drawString(currentColumn[1], (yTEST + leftHandXAxisOffset), ((366) + fontSizes[0] + 2 + fontSizes[1]));

                if (fontSizes[2] == LoginScreen.bigTxtSize) {
                    g2d.setFont(largeFont);
                } else {
                    g2d.setFont(normalFont);
                }
                g2d.drawString(currentColumn[2], (yTEST + leftHandXAxisOffset), ((366) + fontSizes[0] + 2 + fontSizes[1] + 2 + fontSizes[2]));

                if (fontSizes[3] == LoginScreen.bigTxtSize) {
                    g2d.setFont(largeFont);
                } else {
                    g2d.setFont(normalFont);
                }
                g2d.drawString(currentColumn[3], (yTEST + leftHandXAxisOffset), ((366) + fontSizes[0] + 2 + fontSizes[1] + 2 + fontSizes[2] + 2 + fontSizes[3]));

                g2d.setComposite(makeComposite(1.0f));
            }

            //limit break stuff
            g2d.drawImage(fury, 20 + ((uiShakeEffectOffsetOpponent + uiShakeEffectOffsetCharacter) / 2), 190 - ((uiShakeEffectOffsetOpponent + uiShakeEffectOffsetCharacter) / 2), this);
            g2d.drawImage(furyBar, 10 + ((uiShakeEffectOffsetOpponent + uiShakeEffectOffsetCharacter) / 2), furyBarY - ((uiShakeEffectOffsetOpponent + uiShakeEffectOffsetCharacter) / 2), this);
            g2d.setColor(Color.RED);
            g2d.fillRoundRect(12 + ((uiShakeEffectOffsetOpponent + uiShakeEffectOffsetCharacter) / 2), 132 - ((uiShakeEffectOffsetOpponent + uiShakeEffectOffsetCharacter) / 2), 12, getBreak() / 5, 12, 12);
            //COMBO

            if (furyComboOpacity > 0.01f) {
                furyComboOpacity = furyComboOpacity - 0.01f;
            }
            g2d.setComposite(makeComposite(furyComboOpacity));
            g2d.drawImage(comboPicArray[comboPicArrayPosOpp], comX + ((uiShakeEffectOffsetOpponent + uiShakeEffectOffsetCharacter) / 2), comY - ((uiShakeEffectOffsetOpponent + uiShakeEffectOffsetCharacter) / 2), this);
            g2d.setComposite(makeComposite(1.0f));
            g2d.setFont(notSelected);


            //clash area
            if (clasherRunnign) {
                g2d.setColor(Color.BLACK);
                g2d.fillRoundRect(221, 395, 410, 20, 10, 10);
                g2d.drawImage(flashy, (int) (ClashSystem.getInstance().plyClashPerc * 4) + 226, 385, this);
                g2d.setColor(Color.RED);
                g2d.fillRect((int) (626 - (ClashSystem.getInstance().oppClashPerc * 4)), 400, (int) ClashSystem.getInstance().oppClashPerc * 4, 10);
                g2d.setColor(Color.YELLOW);
                g2d.fillRect(226, 400, (int) (ClashSystem.getInstance().plyClashPerc * 4), 10);
            }

            //damage digits
            {
                g2d.setComposite(makeComposite(opponentDamageOpacity));
                //opp damage imageLoader
                g2d.drawImage(figGuiSrc1, playerDamageXLoc + uiShakeEffectOffsetCharacter, opponentDamageYLoc - uiShakeEffectOffsetCharacter, this);
                g2d.drawImage(figGuiSrc2, playerDamageXLoc + (spacer * 1) + uiShakeEffectOffsetCharacter, opponentDamageYLoc - uiShakeEffectOffsetCharacter, this);
                g2d.drawImage(figGuiSrc3, playerDamageXLoc + (spacer * 2) + uiShakeEffectOffsetCharacter, opponentDamageYLoc - uiShakeEffectOffsetCharacter, this);
                g2d.drawImage(figGuiSrc4, playerDamageXLoc + (spacer * 3) + uiShakeEffectOffsetCharacter, opponentDamageYLoc - uiShakeEffectOffsetCharacter, this);
                g2d.setComposite(makeComposite(1.0f));
                if (opponentDamageOpacity >= 0.0f) {
                    opponentDamageOpacity = opponentDamageOpacity - 0.0125f;
                }
                if (opponentDamageOpacity < 0.8f) {
                    opponentDamageYLoc = opponentDamageYLoc - 3;
                }


                g2d.setComposite(makeComposite(playerDamageOpacity));
                //char damage imageLoader
                g2d.drawImage(figGuiSrc10, opponentDamageXLoc + uiShakeEffectOffsetOpponent, playerDamageYCoord - uiShakeEffectOffsetOpponent, this);
                g2d.drawImage(figGuiSrc20, opponentDamageXLoc + (spacer * 1) + uiShakeEffectOffsetOpponent, playerDamageYCoord - uiShakeEffectOffsetOpponent, this);
                g2d.drawImage(figGuiSrc30, opponentDamageXLoc + (spacer * 2) + uiShakeEffectOffsetOpponent, playerDamageYCoord - uiShakeEffectOffsetOpponent, this);
                g2d.drawImage(figGuiSrc40, opponentDamageXLoc + (spacer * 3) + uiShakeEffectOffsetOpponent, playerDamageYCoord - uiShakeEffectOffsetOpponent, this);
                g2d.setComposite(makeComposite(1.0f));
                if (playerDamageOpacity >= 0.0f) {
                    playerDamageOpacity = playerDamageOpacity - 0.0125f;
                }
                if (playerDamageOpacity < 0.8f) {
                    playerDamageYCoord = playerDamageYCoord - 3;
                }
            }
            checkFuryStatus();
        }

        //-----------ENDS ATTACKS QUEING UP--------------

        //when paused
        if (GameInstance.getInstance().gamePaused == true) {
            g2d.setColor(Color.BLACK);
            g2d.setComposite(makeComposite(5 * 0.1f));//initial val between 1 and 10
            g2d.fillRect(0, 0, getGameWidth(), getGameHeight());
            g2d.setComposite(makeComposite(10 * 0.1f));
            g2d.setColor(Color.WHITE);
            g2d.drawString(Language.getInstance().getLine(148), 400, 240);
            g2d.drawString(Language.getInstance().getLine(149), 400, 260);
            g2d.drawString(Language.getInstance().getLine(150), 400, 280);
        }

        //when gameover
        if (GameInstance.getInstance().gameOver == true) {
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, getGameWidth(), getGameHeight());
            g2d.setColor(Color.BLACK);
            g2d.setComposite(makeComposite(8 * 0.1f));//initial val between 1 and 10
            g2d.fillRect(0, 210, getGameWidth(), 121);
            g2d.setComposite(makeComposite(10 * 0.1f));
            g2d.drawImage(status, 0, 210, this);
            g2d.setColor(Color.WHITE);
            g2d.setFont(notSelected);
            g2d.drawString(achievementName, 400, 240); //+14
            g2d.drawString(achievementDescription, 400, 254);
            g2d.drawString(achievementClass, 400, 268);
            g2d.drawString(achievementPoints, 400, 282);
            g2d.drawString("<< " + Language.getInstance().getLine(146) + " >>", 400, 296);
        }

        //global overlay
        JenesisGlassPane.getInstance().overlay(g2d, this);

        g.drawImage(volatileImage, 0, 0, this);
    }

    /**
     * Show win pic
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
        if (RenderGameplay.getInstance().getBreak() == 1000) {
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
        if (LoginScreen.getInstance().isLefty().equalsIgnoreCase("no")) {
            leftHandXAxisOffset = 548;
        } else {
            leftHandXAxisOffset = 0;
        }
        JenesisImageLoader pix = new JenesisImageLoader();
        counterPane = pix.loadImage("images/countPane.png");
        if (RenderStageSelect.getInstance().getHoveredStage() != Stage.DISTANT_ISLE) {
            foreGround = pix.loadVolatileImage(RenderStageSelect.getInstance().getFgLocation(), 852, 480, this);
        } else {
            foreGround = pix.loadVolatileImage(RenderStageSelect.getInstance().getFgLocation(), 960, 480, this);
        }
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
        //flashy=imageLoader.loadVolatileImage("images/flash.gif",40,40);
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
            Characters.getInstance().getCharacter().loadMeHigh(this);
            Characters.getInstance().getOpponent().loadMeHigh(this);

            charSprites = new VolatileImage[12];
            for (int i = 0; i < Characters.getInstance().getCharacter().getNumberOfSprites(); i++) {
                charSprites[i] = Characters.getInstance().getCharacter().getHighQualitySprite(i);
            }

            oppSprites = new VolatileImage[12];
            for (int i = 0; i < Characters.getInstance().getOpponent().getNumberOfSprites(); i++) {
                oppSprites[i] = Characters.getInstance().getOpponent().getHighQualitySprite(i);
            }

            comboPicArray = new Image[9];
            for (int u = 0; u < 6; u++) {
                comboPicArray[u] = imageLoader.loadImage("images/screenTxt/" + u + ".png");
            }
            comboPicArray[7] = imageLoader.loadImage("images/screenTxt/7.png");
            comboPicArray[8] = Characters.getInstance().getCharacter().getHighQualitySprite(11);

            comicBookText = new Image[10];
            comicBookText[0] = Characters.getInstance().getCharacter().getHighQualitySprite(11);
            for (int bx = 1; bx < numOfComicPics + 1; bx++) {
                comicBookText[bx] = imageLoader.loadImage("images/screenComic/" + (bx - 1) + ".png");
            }

            menuHold = imageLoader.loadImage("images/" + Characters.getInstance().getCharacter().getEnum().data() + "/menu.png");
            damageLayer = imageLoader.loadImage("images/damage1.png", LoginScreen.getInstance().getdefSpriteWidth(), LoginScreen.getInstance().getdefSpriteHeight());

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

            characterPortraits = new VolatileImage[charNames.length];

            if (MainWindow.getInstance().getGameMode() == SubMode.STORY_MODE) {
                for (CharacterEnum characterEnum : CharacterEnum.values()) {
                    characterPortraits[characterEnum.index()] = imageLoader.loadVolatileImage("images/" + characterEnum.data() + "/cap.png", 48, 48, this);
                }
            } else {
                for (int p = 0; p < charNames.length; p++) {
                    characterPortraits[p] = null;
                }
            }
            Image transBuf = imageLoader.loadImage("images/trans.png", 5, 5);
            hpHolder = imageLoader.loadImage("images/hpHolder.png");
            stageBackground = imageLoader.loadImage(RenderStageSelect.getInstance().getBgLocation(), 852, 480);
            phys = imageLoader.loadImage("images/t_physical.png");
            cel = imageLoader.loadImage("images/t_celestia.png");
            itm = imageLoader.loadImage("images/t_item.png");
            fury1 = imageLoader.loadImage("images/fury.gif");
            fury2 = imageLoader.loadImage("images/furyo.png");
            fury = fury2;
            particlesLayer1 = imageLoader.loadVolatileImage("images/bgBG" + RenderStageSelect.getInstance().getHoveredStage().filePrefix() + "a.png", 852, 480, this);
            particlesLayer2 = imageLoader.loadVolatileImage("images/bgBG" + RenderStageSelect.getInstance().getHoveredStage().filePrefix() + "b.png", 852, 480, this);
            if (MainWindow.getInstance().getGameMode() == SubMode.STORY_MODE) {
                storyPicArr = new VolatileImage[13];
                for (int u = 0; u < 11; u++) {
                    storyPicArr[u] = imageLoader.loadVolatileImage("images/story/s" + u + ".png", LoginScreen.getInstance().getdefSpriteWidth(), LoginScreen.getInstance().getdefSpriteHeight(), this);
                }
                storyPic = storyPicArr[0];
            }
            furyBar = imageLoader.loadImage("images/furyBar.png");
            quePic1 = imageLoader.loadImage("images/queB.png");
            quePic2 = imageLoader.loadImage("images/que.gif");
            oppBar = imageLoader.loadImage("images/oppBar.png");
            moveCat = new Image[]{phys, cel, itm};
            curr = moveCat[0];
            stat1 = imageLoader.loadVolatileImage("images/stats/stat1.png", 90, 24, this);
            stat2 = imageLoader.loadVolatileImage("images/stats/stat2.png", 90, 24, this);
            stat3 = imageLoader.loadVolatileImage("images/stats/stat3.png", 90, 24, this);
            stat4 = imageLoader.loadVolatileImage("images/stats/stat4.png", 90, 24, this);
            stats = new VolatileImage[]{stat1, stat2, stat3, stat4};
            hud1 = imageLoader.loadImage("images/hud1.png");
            hud2 = imageLoader.loadImage("images/hud2.png");
            win = imageLoader.loadImage("images/win.png");
            lose = imageLoader.loadImage("images/lose.png");
            status = transBuf;
            System.out.println("loaded all char sprites imageLoader");
            //ensures method is only run once
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    /**
     * Flashy text at bottom of screen
     *
     * @param thisMessage
     */
    public void flashyText(String thisMessage) {
        opacityTxt = 0.0f;
        battleInformation = new StringBuilder(thisMessage);
    }

    /**
     * Go to next command menu column
     */
    public void nextAnimation() {
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
    public void prevAnimation() {
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
     * Draws battle message at bottom of screen
     *
     * @param writeThis - what to display
     */
    public void showBattleMessage(String writeThis) {
        flashyText(writeThis);
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
        if (LoginScreen.getInstance().comicPicOcc > 0) {
            int well = Math.round((float) (Math.random() * LoginScreen.getInstance().comicPicOcc));

            if (well == 1) {
                setRandomPic();
            }
        }
    }

    /**
     * Selecting a move
     */
    public void moveSelected() {
        if (clasherRunnign) {
            ClashSystem.getInstance().plrClashing();
            System.out.println("Playr clashin");
        } else if (safeToSelect) {
            numOfAttacks = numOfAttacks + 1;
            sound = new AudioPlayback(AudioPlayback.selectSound(), false);
            sound.play();
            move = (currentColumnIndex * 4) + itemIndex + 1;
            attackArray[comboCounter] = genStr(move); // count initially negative 1, add one to get to index 0
            incrimentComboCounter();
            checkStatus();
            showBattleMessage("Qued up " + getSelMove(move));
        } else {
            RenderCharacterSelectionScreen.getInstance().errorSound();
        }
    }

    public synchronized void playBGSound() {
        if (ambientMusic != null) {
            ambientMusic.stop();
        }
        ambientMusic = new AudioPlayback("audio/" + RenderStageSelect.getInstance().getAmbientMusic()[RenderStageSelect.getInstance().getAmbientMusicIndex()] + ".mp3", true);
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

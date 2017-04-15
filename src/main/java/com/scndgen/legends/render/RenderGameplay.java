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
import com.scndgen.legends.enums.CharacterEnum;
import com.scndgen.legends.scene.Gameplay;
import com.scndgen.legends.threads.*;
import com.scndgen.legends.windows.MainWindow;
import com.scndgen.legends.windows.WindowOptions;
import io.github.subiyacryolite.enginev1.JenesisGlassPane;
import io.github.subiyacryolite.enginev1.JenesisImageLoader;
import io.github.subiyacryolite.enginev1.JenesisRender;

import javax.swing.*;
import java.awt.*;
import java.awt.image.VolatileImage;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Ifunga Ndana
 */
public class RenderGameplay extends Gameplay implements JenesisRender {
    private static RenderGameplay instance;
    private Animations1 upDown;
    private Animations2 upDown2;
    private Animations3 upDown3;
    private Font bigFont, normalFont;
    private Font notSelected;
    private Font statusFont;
    private VolatileImage charSpec, blankPortrait;
    private VolatileImage infoPic, stat1, stat2, stat3, stat4;
    private VolatileImage ambient1, ambient2, foreGround;
    private GradientPaint queBar = new GradientPaint(0, 0, Color.YELLOW, 255, 10, Color.RED, false);
    private GradientPaint gradient1 = new GradientPaint(xLocal, 10, Color.YELLOW, 255, 10, Color.RED, true);
    private GradientPaint gradient2 = new GradientPaint(xLocal, 10, Color.white, 255, 10, Color.blue, true);
    private GradientPaint gradient3 = new GradientPaint(0, 0, Color.YELLOW, 100, 100, Color.RED, true);
    private VolatileImage flashy;
    private Image[] moveCat, numberPix;
    private VolatileImage[] characterPortraits;
    private AudioPlayback sound, ambientMusic, furySound, damageSound, hurtChar, hurtOpp, attackChar, attackOpp;
    private Image[] comboPicArray, comicPicArray, times, statsPicChar = new Image[5], statsPicOpp = new Image[5];
    private Image oppBar, quePic1, furyBar, counterPane, quePic2, num0, num1, num2, num3, num4, num5, num6, num7, num8, num9, numNull, bgPic, damLayer, hpHolder, hud1, hud2, win, lose, status, menuHold, fury, fury1, fury2, phys, cel, itm, curr, numInfinite, figGuiSrc10, figGuiSrc20, figGuiSrc30, figGuiSrc40, figGuiSrc1, figGuiSrc2, figGuiSrc3, figGuiSrc4, time0, time1, time2, time3, time4, time5, time6, time7, time8, time9;
    private Image[] charSprites, oppSprites;
    private VolatileImage[] attackAnim2, attackAnim1;
    private VolatileImage[] storyPicArr, stats;
    private VolatileImage characterPortrait, storyPic;
    private Color CurrentColor = (Color.RED);

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
        statusFont = LoginScreen.getInstance().getMyFont(28);
        bigFont = LoginScreen.getInstance().getMyFont(LoginScreen.bigTxtSize);
        normalFont = LoginScreen.getInstance().getMyFont(LoginScreen.normalTxtSize);
        setCharMoveset();
        LoginScreen.getInstance().defHeight = LoginScreen.getInstance().getGameHeight();
        cacheNumPix();
        if (WindowOptions.graphics.equalsIgnoreCase("High")) {
            loadCharSpritesHigh();
        } else {
            loadCharSpritesLow();
        }
        if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
            server = MainWindow.getInstance().getServer();
        }
        if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanClient)) {
            //get ip from game
            client = MainWindow.getInstance().getClient();
        }

        if (MainWindow.getInstance().getGameMode().equals(MainWindow.singlePlayer2)) {
            charAssSpriteStatus = 9;
            oppAssSpriteStatus = 9;
        } else {
            charAssSpriteStatus = 11;
            oppAssSpriteStatus = 11;
        }
        charPointInc = RenderCharacterSelectionScreen.getInstance().getPlayers().getPoints();
        loadAssets = false;
    }

    public void cleanAssets() {
        loadAssets = true;
    }

    @Override
    public void paintComponent(Graphics g) {
        /* Fixed performance issues, got rid off geometry and replaced with static VolatileImage s -----facepalm*/
        createBackBuffer();
        loadAssets();
        if (imagesCharChached != true) {
            g2d.fillRect(0, 0, LoginScreen.getInstance().getdefSpriteWidth(), LoginScreen.getInstance().getdefSpriteHeight());
        } else if (ThreadGameInstance.storySequence) {
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
            g2d.drawImage(characterPortrait, ((852 - g2d.getFontMetrics().stringWidth(battleInf.toString())) / 2) - 50, 424, this);
            g2d.drawString(battleInf.toString(), ((852 - g2d.getFontMetrics().stringWidth(battleInf.toString())) / 2), 450);
            g2d.setComposite(makeComposite(10 * 0.1f));

        } else if (ThreadGameInstance.isGameOver == false && ThreadGameInstance.storySequence == false) {
            g2d.drawImage(bgPic, 0, 0, this);
            g2d.setFont(notSelected);
            if (RenderGameplay.getInstance().getCharLife() >= 0) {
                if (animLayer.equalsIgnoreCase("both")) {
                    g2d.drawImage(ambient2, amb2x, amb2y, this);
                } else if (animLayer.equalsIgnoreCase("back")) {
                    g2d.drawImage(ambient1, amb1x, amb1y, this);
                    g2d.drawImage(ambient2, amb2x, amb2y, this);
                }

                if (MainWindow.getInstance().getAttacksChar().isOverlayDisabled()) {
                    g2d.drawImage(charSprites[charMeleeSpriteStatus], charXcord + shakeyOffsetChar, charYcord - shakeyOffsetChar, this);
                }

                g2d.drawImage(oppSprites[oppMeleeSpriteStatus], LoginScreen.getGameWidth(), (int) (oppYcord), (int) (oppXcord), LoginScreen.getInstance().getGameHeight(), (int) oppYcord, (int) (oppXcord), LoginScreen.getInstance().getGameWidth(), LoginScreen.getInstance().getGameHeight(), this);


                if (!MainWindow.getInstance().getAttacksChar().isOverlayDisabled()) {
                    g2d.drawImage(charSprites[charMeleeSpriteStatus], charXcord + shakeyOffsetChar, charYcord - shakeyOffsetChar, this);
                }

                if (animLayer.equalsIgnoreCase("both")) {
                    g2d.drawImage(ambient1, amb1x, amb1y, this);
                } else if (animLayer.equalsIgnoreCase("forg")) {
                    g2d.drawImage(ambient1, amb1x, amb1y, this);
                    g2d.drawImage(ambient2, amb2x, amb2y, this);
                }

                /** character sprite on below, opponent on top
                 * "Java Tip 32: You'll flip over Java images -- literally! - JavaWorld"
                 * http://www.javaworld.com/javaworld/javatips/jw-javatip32.html?page=2
                 */
                if ((RenderGameplay.getInstance().getCharLife() / RenderGameplay.getInstance().getCharMaxLife()) < 0.66f) {
                    damOpInc = 6.66f - ((RenderGameplay.getInstance().getCharLife() / RenderGameplay.getInstance().getCharMaxLife()) * 10);
                    //BACK to transparency
                }

                if (specialEffect) {
                    g2d.drawImage(foreGround, fgx, fgy, this);
                }

                g2d.setComposite(makeComposite((float) damOpInc * 0.1f));
                g2d.drawImage(damLayer, 0, 0, this);
                g2d.setComposite(makeComposite(1.0f));

                g2d.drawImage(menuHold, leftyXOffset, menuBarY, this);


                for (int xB = 0; xB < 4; xB++) {
                    g2d.drawImage(quePic1, (xB * 70 + 5 + leftyXOffset), (int) (LoginScreen.getInstance().getGameYScale() * 440), this);
                }

                if (RenderGameplay.getInstance().comboCounter >= 1) {
                    for (int xV = 0; xV < RenderGameplay.getInstance().comboCounter; xV++) {
                        g2d.drawImage(quePic2, (xV * 70 + 5 + leftyXOffset), (int) (LoginScreen.getInstance().getGameYScale() * 440), this);
                    }
                }


                if (comicBookTextOpacity >= 0.0f) {
                    comicBookTextOpacity = comicBookTextOpacity - 0.0125f;
                }
                g2d.setComposite(makeComposite(comicBookTextOpacity));
                g2d.drawImage(comicPicArray[comicPicArrayPos], 170 + basicX, 112 + basicY + comicY, this);
                g2d.setComposite(makeComposite(1.0f));
                comicY = comicY + 3;

                if (opacityTxt < 0.98f) {
                    opacityTxt = opacityTxt + 0.02f;
                }
                g2d.setComposite(makeComposite(opacityTxt));
                g2d.setColor(Color.WHITE);
                g2d.drawString(battleInf.toString(), 32 + leftyXOffset, 470);
                g2d.setComposite(makeComposite(1.0f));

                //-----------draws battle info messages--------------


                //stats

                if (statusOpChar > 0.02f) {
                    statusOpChar = statusOpChar - 0.02f;
                }
                g2d.setComposite(makeComposite(statusOpChar));
                g2d.drawImage(statsPicChar[statIndexChar], 150 + basicX + shakeyOffsetChar, 100 + basicY - shakeyOffsetChar + statsPosYChar, this);
                g2d.setComposite(makeComposite(1.0f));
                statsPosYChar = statsPosYChar + 1;


                if (statusOpOpp > 0.02f) {
                    statusOpOpp = statusOpOpp - 0.02f;
                }
                g2d.setComposite(makeComposite(statusOpOpp));
                g2d.drawImage(statsPicOpp[statIndexOpp], 602 + basicX + shakeyOffsetOpp, 100 + basicY - shakeyOffsetOpp + statsPosYOpp, this);
                g2d.setComposite(makeComposite(1.0f));
                statsPosYOpp = statsPosYOpp + 1;

                //---opponrnt activity bar + text

                g2d.drawImage(hpHolder, (45 + 62 + x2) + shakeyOffsetOpp, (y + 4 + y2 - oppBarYOffset) - shakeyOffsetOpp, this);
                g2d.setColor(Color.WHITE);
                if (MainWindow.getInstance().getGameMode().equals(MainWindow.singlePlayer2)) {
                    g2d.drawString("HP: " + Math.round((RenderGameplay.getInstance().getOppLife2() + RenderGameplay.getInstance().getOppLife())) + " : " + ((RenderGameplay.getInstance().perCent2a + RenderGameplay.getInstance().perCent2) / 2) + "%", (int) ((55 + 64 + x2)), (int) ((18 + y2 - oppBarYOffset)));
                } else {
                    g2d.drawString("HP: " + Math.round(RenderGameplay.getInstance().getOppLife()) + " : " + RenderGameplay.getInstance().perCent2 + "%", (55 + 64 + x2) + shakeyOffsetOpp, (18 + y2 - oppBarYOffset) - shakeyOffsetOpp);
                }

                g2d.setColor(Color.BLACK);
                g2d.drawImage(oppBar, (x2 - 20) + shakeyOffsetOpp, (y2 + 18 - oppBarYOffset) - shakeyOffsetOpp, this);
                g2d.setPaint(gradient1);
                g2d.fillRoundRect((x2 - 17) + shakeyOffsetOpp, (y2 + 22 - oppBarYOffset) - shakeyOffsetOpp, RenderGameplay.getInstance().getGameInstance().getRecoveryUnitsOpp(), 6, 6, 6);
                if (MainWindow.getInstance().getGameMode().equals(MainWindow.singlePlayer2)) {
                    g2d.drawImage(hpHolder, 45 + 62 + x2, (y + 4 + y2 - 40), this);
                    g2d.setColor(Color.WHITE);
                    g2d.drawString("HP: " + Math.round((RenderGameplay.getInstance().getOppLife2() + RenderGameplay.getInstance().getOppLife())) + " : " + ((RenderGameplay.getInstance().perCent2a + RenderGameplay.getInstance().perCent2) / 2) + "%", 55 + 64 + x2, 18 + y2 - 40);
                    g2d.setColor(Color.BLACK);
                    g2d.drawImage(oppBar, x2 - 20, y2 + 18 - 40, this);
                    g2d.setPaint(gradient1);
                    g2d.fillRoundRect(x2 - 17, y2 + 22 - 40, RenderGameplay.getInstance().getGameInstance().getRecoveryUnitsOpp2(), 6, 6, 6);

                    g2d.drawImage(hpHolder, 45 + 62 + x2, (y + 4 + y2 - 80), this);
                    g2d.setColor(Color.WHITE);
                    g2d.drawString("HP: " + ((Math.round(RenderGameplay.getInstance().getCharLife3()) + Math.round(RenderGameplay.getInstance().getCharLife()))) + " : " + ((RenderGameplay.getInstance().perCent3a + RenderGameplay.getInstance().perCent) / 2) + "%", 55 + 64 + x2, 18 + y2 - 80);
                    g2d.setColor(Color.BLACK);
                    g2d.drawImage(oppBar, x2 - 20, y2 + 18 - 80, this);
                    g2d.setPaint(gradient2);
                    g2d.fillRoundRect(x2 - 17, y2 + 22 - 80, RenderGameplay.getInstance().getGameInstance().getRecoveryUnitsChar2(), 6, 6, 6);
                }

                //------------player 1 HUD---------------------//
                g2d.drawImage(hpHolder, (lbx2 - 438) + shakeyOffsetChar, (lby2 - 410) - shakeyOffsetChar, this); // HOLDS hp
                //outline
                g2d.drawImage(hud1, (lbx2 - 498) + shakeyOffsetChar, (lby2 - 417) - shakeyOffsetChar, this);
                //inner
                //g2d.setColor(Color.RED);
                g2d.setPaint(gradient3);
                g2d.fillArc(lbx2 - 493 + shakeyOffsetChar, lby2 - 412 - shakeyOffsetChar, 90, 90, 0, phyAngle());
                //inner loop
                g2d.setColor(Color.BLACK);
                g2d.drawImage(hud2, lbx2 - 488 + shakeyOffsetChar, lby2 - 407 - shakeyOffsetChar, this);

                {
                    g2d.setColor(Color.WHITE);

                    if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.singlePlayer2)) {
                        g2d.drawString("HP: " + ((Math.round(RenderGameplay.getInstance().getCharLife3()) + Math.round(RenderGameplay.getInstance().getCharLife()))) + " : " + ((RenderGameplay.getInstance().perCent3a + RenderGameplay.getInstance().perCent) / 2) + "%", (lbx2 - 416) + shakeyOffsetChar, (lby2 - 398) - shakeyOffsetChar);
                    } else {
                        g2d.drawString("HP: " + Math.round(RenderGameplay.getInstance().getCharLife()) + " : " + RenderGameplay.getInstance().perCent + "%", (lbx2 - 416) + shakeyOffsetChar, (lby2 - 398) - shakeyOffsetChar);
                    }
                    g2d.setComposite(makeComposite(10 * 0.1f)); //op back to normal for other drawings
                }
            }

            g2d.drawImage(counterPane, paneCord, 0, this);

            if (RenderGameplay.getInstance().getGameInstance().time > 180) {
                g2d.drawImage(numberPix[11], (int) (386), 0, this);
            } else {

                g2d.drawImage(times[RenderGameplay.getInstance().getGameInstance().time1], (int) (356), 0, this);
                g2d.drawImage(times[RenderGameplay.getInstance().getGameInstance().time2], (int) ((356) + 40), 0, this);
                g2d.drawImage(times[RenderGameplay.getInstance().getGameInstance().time3], (int) ((356) + 80), 0, this);
            }


            {
                if (opac < 0.95f) {
                    opac = opac + 0.05f;
                }
                g2d.setComposite(makeComposite(opac));
                g2d.setColor(CurrentColor);
                g2d.drawImage(curr, itemX + leftyXOffset, itemY, this);

                if (fontSizes[0] == LoginScreen.bigTxtSize) {
                    g2d.setFont(bigFont);
                } else {
                    g2d.setFont(normalFont);
                }
                g2d.drawString(currentColumn[0], (yTEST + leftyXOffset), ((366) + fontSizes[0]));


                if (fontSizes[1] == LoginScreen.bigTxtSize) {
                    g2d.setFont(bigFont);
                } else {
                    g2d.setFont(normalFont);
                }
                g2d.drawString(currentColumn[1], (yTEST + leftyXOffset), ((366) + fontSizes[0] + 2 + fontSizes[1]));

                if (fontSizes[2] == LoginScreen.bigTxtSize) {
                    g2d.setFont(bigFont);
                } else {
                    g2d.setFont(normalFont);
                }
                g2d.drawString(currentColumn[2], (yTEST + leftyXOffset), ((366) + fontSizes[0] + 2 + fontSizes[1] + 2 + fontSizes[2]));

                if (fontSizes[3] == LoginScreen.bigTxtSize) {
                    g2d.setFont(bigFont);
                } else {
                    g2d.setFont(normalFont);
                }
                g2d.drawString(currentColumn[3], (yTEST + leftyXOffset), ((366) + fontSizes[0] + 2 + fontSizes[1] + 2 + fontSizes[2] + 2 + fontSizes[3]));

                g2d.setComposite(makeComposite(1.0f));
            }

            //limit break stuff
            g2d.drawImage(fury, 20 + ((shakeyOffsetOpp + shakeyOffsetChar) / 2), 190 - ((shakeyOffsetOpp + shakeyOffsetChar) / 2), this);
            g2d.drawImage(furyBar, 10 + ((shakeyOffsetOpp + shakeyOffsetChar) / 2), furyBarY - ((shakeyOffsetOpp + shakeyOffsetChar) / 2), this);
            g2d.setColor(Color.RED);
            g2d.fillRoundRect(12 + ((shakeyOffsetOpp + shakeyOffsetChar) / 2), 132 - ((shakeyOffsetOpp + shakeyOffsetChar) / 2), 12, getBreak() / 5, 12, 12);
            //COMBO

            if (furyComboOpacity > 0.01f) {
                furyComboOpacity = furyComboOpacity - 0.01f;
            }
            g2d.setComposite(makeComposite(furyComboOpacity));
            g2d.drawImage(comboPicArray[comboPicArrayPosOpp], comX + ((shakeyOffsetOpp + shakeyOffsetChar) / 2), comY - ((shakeyOffsetOpp + shakeyOffsetChar) / 2), this);
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
                g2d.drawImage(figGuiSrc1, playerDamageXLoc + shakeyOffsetChar, opponentDamageYLoc - shakeyOffsetChar, this);
                g2d.drawImage(figGuiSrc2, playerDamageXLoc + (spacer * 1) + shakeyOffsetChar, opponentDamageYLoc - shakeyOffsetChar, this);
                g2d.drawImage(figGuiSrc3, playerDamageXLoc + (spacer * 2) + shakeyOffsetChar, opponentDamageYLoc - shakeyOffsetChar, this);
                g2d.drawImage(figGuiSrc4, playerDamageXLoc + (spacer * 3) + shakeyOffsetChar, opponentDamageYLoc - shakeyOffsetChar, this);
                g2d.setComposite(makeComposite(1.0f));
                if (opponentDamageOpacity >= 0.0f) {
                    opponentDamageOpacity = opponentDamageOpacity - 0.0125f;
                }
                if (opponentDamageOpacity < 0.8f) {
                    opponentDamageYLoc = opponentDamageYLoc - 3;
                }


                g2d.setComposite(makeComposite(playerDamageOpacity));
                //char damage imageLoader
                g2d.drawImage(figGuiSrc10, opponentDamageXLoc + shakeyOffsetOpp, playerDamageYLoc - shakeyOffsetOpp, this);
                g2d.drawImage(figGuiSrc20, opponentDamageXLoc + (spacer * 1) + shakeyOffsetOpp, playerDamageYLoc - shakeyOffsetOpp, this);
                g2d.drawImage(figGuiSrc30, opponentDamageXLoc + (spacer * 2) + shakeyOffsetOpp, playerDamageYLoc - shakeyOffsetOpp, this);
                g2d.drawImage(figGuiSrc40, opponentDamageXLoc + (spacer * 3) + shakeyOffsetOpp, playerDamageYLoc - shakeyOffsetOpp, this);
                g2d.setComposite(makeComposite(1.0f));
                if (playerDamageOpacity >= 0.0f) {
                    playerDamageOpacity = playerDamageOpacity - 0.0125f;
                }
                if (playerDamageOpacity < 0.8f) {
                    playerDamageYLoc = playerDamageYLoc - 3;
                }
            }
            checkFuryStatus();
        }

        //-----------ENDS ATTACKS QUEING UP--------------

        //when paused
        if (ThreadGameInstance.isPaused == true) {
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
        if (ThreadGameInstance.isGameOver == true) {
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, getGameWidth(), getGameHeight());
            g2d.setColor(Color.BLACK);
            g2d.setComposite(makeComposite(8 * 0.1f));//initial val between 1 and 10
            g2d.fillRect(0, 210, getGameWidth(), 121);
            g2d.setComposite(makeComposite(10 * 0.1f));
            g2d.drawImage(status, 0, 210, this);
            g2d.setColor(Color.WHITE);
            g2d.setFont(notSelected);
            g2d.drawString(ach1, 400, 240); //+14
            g2d.drawString(ach2, 400, 254);
            g2d.drawString(ach3, 400, 268);
            g2d.drawString(ach4, 400, 282);
            g2d.drawString("<< " + Language.getInstance().getLine(146) + " >>", 400, 296);
        }

        //global overlay
        JenesisGlassPane.getInstance().overlay(g2d, this);

        g.drawImage(volatileImg, 0, 0, this);
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

    /**
     * Playing around with animate sprites
     *
     * @param whichOne move
     * @param loop     loop animation or not
     */
    public void specialEffect(int whichOne, boolean loop) {
        final int thisOne = whichOne;
        final boolean isLoop = loop;

        new Thread() {

            @SuppressWarnings({"static-access", "static-access", "SleepWhileHoldingLock"})
            @Override
            public void run() {
                if (animCharFree) {
                    try {
                        animCharFree = false;


                        //anim1
                        if (thisOne == 1) {
                            for (int u = 0; u < attackAnim1.length; u++) {
                                charSpec = attackAnim1[u];
                                if (isLoop) {
                                    this.sleep(animTime / (attackAnim1.length * 2));
                                } else {
                                    this.sleep(animTime / attackAnim1.length);
                                }
                            }

                            if (isLoop) {
                                for (int u = attackAnim1.length - 1; u > -1; u--) {
                                    charSpec = attackAnim1[u];
                                    this.sleep(animTime / (attackAnim1.length * 2));
                                }
                            }
                        }

                        //anim1
                        if (thisOne == 2) {
                            for (int u = 0; u < attackAnim2.length; u++) {
                                charSpec = attackAnim2[u];
                                if (isLoop) {
                                    this.sleep(animTime / (attackAnim2.length * 2));
                                } else {
                                    this.sleep(animTime / attackAnim2.length);
                                }
                            }

                            if (isLoop) {
                                for (int u = attackAnim2.length - 1; u > -1; u--) {
                                    charSpec = attackAnim2[u];
                                    this.sleep(animTime / (attackAnim2.length * 2));
                                }
                            }
                        }
                        animCharFree = true;
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Gameplay.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }.start();
    }

    private void checkFuryStatus() {
        if (RenderGameplay.getInstance().getBreak() == 1000) {
            fury = fury1;
        } else {
            fury = fury2;
        }

        if (RenderGameplay.getInstance().getGameInstance().isGameOver == true) {
            //slow mo!!!!
        }
    }

    public void setOpponentDamage(int oneA, int twoA, int threeA, int fourA) {
        comicText();

        nrmlDamageSound();
        attackSoundOpp();
        hurtSoundOpp();


        playerDamageYLoc = 160 + (int) (Math.random() * 100);
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
            leftyXOffset = 548;
        } else {
            leftyXOffset = 0;
        }
        if (imagesNumChached == false) {
            JenesisImageLoader pix = new JenesisImageLoader();
            counterPane = pix.loadImage("images/countPane.png");
            if (activeStage != 100) {
                foreGround = pix.loadVolatileImage(fgLocation, 852, 480, this);
            } else {
                foreGround = pix.loadVolatileImage(fgLocation, 960, 480, this);
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

            statsPicChar[0] = pix.loadImage("images/trans.png");
            statsPicChar[1] = pix.loadImage("images/stats/stat1.png");
            statsPicChar[2] = pix.loadImage("images/stats/stat2.png");
            statsPicChar[3] = pix.loadImage("images/stats/stat3.png");
            statsPicChar[4] = pix.loadImage("images/stats/stat4.png");

            statsPicOpp[0] = pix.loadImage("images/trans.png");
            statsPicOpp[1] = pix.loadImage("images/stats/stat1.png");
            statsPicOpp[2] = pix.loadImage("images/stats/stat2.png");
            statsPicOpp[3] = pix.loadImage("images/stats/stat3.png");
            statsPicOpp[4] = pix.loadImage("images/stats/stat4.png");

            System.out.println("loaded all imageLoader");
            imagesNumChached = true;
            //ensures method is only run once
        }
    }

    /**
     * EPIC!!!! Loads har sprites
     */
    private void loadCharSpritesHigh() {
        if (imagesCharChached == false) {
            try {
                JenesisImageLoader pix = new JenesisImageLoader();
                RenderCharacterSelectionScreen.getInstance().getPlayers().getCharacter().loadMeHigh(this);
                RenderCharacterSelectionScreen.getInstance().getPlayers().getOpponent().loadMeHigh(this);

                charSprites = new VolatileImage[12];
                for (int i = 0; i < RenderCharacterSelectionScreen.getInstance().getPlayers().getCharacter().getNumberOfSprites(); i++) {
                    charSprites[i] = RenderCharacterSelectionScreen.getInstance().getPlayers().getCharacter().getHighQualitySprite(i);
                }

                oppSprites = new VolatileImage[12];
                for (int i = 0; i < RenderCharacterSelectionScreen.getInstance().getPlayers().getOpponent().getNumberOfSprites(); i++) {
                    oppSprites[i] = RenderCharacterSelectionScreen.getInstance().getPlayers().getOpponent().getHighQualitySprite(i);
                }

                comboPicArray = new Image[9];
                for (int u = 0; u < 6; u++) {
                    comboPicArray[u] = pix.loadImage("images/screenTxt/" + u + ".png");
                }
                comboPicArray[7] = pix.loadImage("images/screenTxt/7.png");
                comboPicArray[8] = RenderCharacterSelectionScreen.getInstance().getPlayers().getCharacter().getHighQualitySprite(11);

                comicPicArray = new Image[10];
                comicPicArray[0] = RenderCharacterSelectionScreen.getInstance().getPlayers().getCharacter().getHighQualitySprite(11);
                for (int bx = 1; bx < numOfComicPics + 1; bx++) {
                    comicPicArray[bx] = pix.loadImage("images/screenComic/" + (bx - 1) + ".png");
                }

                menuHold = pix.loadImage("images/" + RenderCharacterSelectionScreen.getInstance().getPlayers().getCharacter().getEnum().data() + "/menu.png");
                damLayer = pix.loadImage("images/damage1.png", LoginScreen.getInstance().getdefSpriteWidth(), LoginScreen.getInstance().getdefSpriteHeight());

                time0 = pix.loadImage("images/fig/0.png");
                time1 = pix.loadImage("images/fig/1.png");
                time2 = pix.loadImage("images/fig/2.png");
                time3 = pix.loadImage("images/fig/3.png");
                time4 = pix.loadImage("images/fig/4.png");
                time5 = pix.loadImage("images/fig/5.png");
                time6 = pix.loadImage("images/fig/6.png");
                time7 = pix.loadImage("images/fig/7.png");
                time8 = pix.loadImage("images/fig/8.png");
                time9 = pix.loadImage("images/fig/9.png");
                times = new Image[]{time0, time1, time2, time3, time4, time5, time6, time7, time8, time9};

                characterPortraits = new VolatileImage[charNames.length];

                if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.storyMode)) {
                    for (CharacterEnum characterEnum : CharacterEnum.values()) {
                        characterPortraits[characterEnum.index()] = pix.loadVolatileImage("images/" + characterEnum.data() + "/cap.png", 48, 48, this);
                    }
                } else {
                    for (int p = 0; p < charNames.length; p++) {
                        characterPortraits[p] = null;
                    }
                }
                Image transBuf = pix.loadImage("images/trans.png", 5, 5);
                hpHolder = pix.loadImage("images/hpHolder.png");
                bgPic = pix.loadImage(bgLocation, 852, 480);
                phys = pix.loadImage("images/t_physical.png");
                cel = pix.loadImage("images/t_celestia.png");
                itm = pix.loadImage("images/t_item.png");
                fury1 = pix.loadImage("images/fury.gif");
                fury2 = pix.loadImage("images/furyo.png");
                fury = fury2;
                ambient1 = pix.loadVolatileImage("images/bgBG" + activeStage + "a.png", 852, 480, this);
                ambient2 = pix.loadVolatileImage("images/bgBG" + activeStage + "b.png", 852, 480, this);
                if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.storyMode)) {
                    storyPicArr = new VolatileImage[13];
                    for (int u = 0; u < 11; u++) {
                        storyPicArr[u] = pix.loadVolatileImage("images/Story/s" + u + ".png", LoginScreen.getInstance().getdefSpriteWidth(), LoginScreen.getInstance().getdefSpriteHeight(), this);
                    }
                    storyPic = storyPicArr[0];
                }
                furyBar = pix.loadImage("images/furyBar.png");
                quePic1 = pix.loadImage("images/queB.png");
                quePic2 = pix.loadImage("images/que.gif");
                oppBar = pix.loadImage("images/oppBar.png");
                moveCat = new Image[]{phys, cel, itm};
                curr = moveCat[0];
                stat1 = pix.loadVolatileImage("images/stats/stat1.png", 90, 24, this);
                stat2 = pix.loadVolatileImage("images/stats/stat2.png", 90, 24, this);
                stat3 = pix.loadVolatileImage("images/stats/stat3.png", 90, 24, this);
                stat4 = pix.loadVolatileImage("images/stats/stat4.png", 90, 24, this);
                stats = new VolatileImage[]{stat1, stat2, stat3, stat4};
                hud1 = pix.loadImage("images/hud1.png");
                hud2 = pix.loadImage("images/hud2.png");
                win = pix.loadImage("images/win.png");
                lose = pix.loadImage("images/lose.png");
                status = transBuf;
                System.out.println("loaded all char sprites imageLoader");
                imagesCharChached = true;
                //ensures method is only run once
            } catch (Exception e) {
                imagesCharChached = false;
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    /**
     * EPIC!!!! Loads char sprite
     * Uses toolkit images and less RAM
     */
    private void loadCharSpritesLow() {
        if (imagesCharChached == false) {
            try {
                JenesisImageLoader pix = new JenesisImageLoader();
                RenderCharacterSelectionScreen.getInstance().getPlayers().getCharacter().loadMeLow();
                RenderCharacterSelectionScreen.getInstance().getPlayers().getOpponent().loadMeLow();
                charSprites = new Image[12];
                for (int i = 0; i < RenderCharacterSelectionScreen.getInstance().getPlayers().getCharacter().getNumberOfSprites(); i++) {
                    charSprites[i] = RenderCharacterSelectionScreen.getInstance().getPlayers().getCharacter().getLowQualitySprite(i);
                }
                oppSprites = new Image[12];
                for (int i = 0; i < RenderCharacterSelectionScreen.getInstance().getPlayers().getOpponent().getNumberOfSprites(); i++) {
                    oppSprites[i] = RenderCharacterSelectionScreen.getInstance().getPlayers().getOpponent().getLowQualitySprite(i);
                }
                comboPicArray = new Image[9];
                for (int u = 0; u < 6; u++) {
                    comboPicArray[u] = pix.loadImage("images/screenTxt/" + u + ".png");
                }
                comboPicArray[7] = pix.loadImage("images/screenTxt/7.png");
                comboPicArray[8] = RenderCharacterSelectionScreen.getInstance().getPlayers().getCharacter().getHighQualitySprite(11);
                comicPicArray = new Image[10];
                comicPicArray[0] = RenderCharacterSelectionScreen.getInstance().getPlayers().getCharacter().getHighQualitySprite(11);
                for (int bx = 1; bx < numOfComicPics + 1; bx++) {
                    comicPicArray[bx] = pix.loadImage("images/screenComic/" + (bx - 1) + ".png");
                }
                menuHold = pix.loadImage("images/" + RenderCharacterSelectionScreen.getInstance().getPlayers().getCharName() + "/menu.png");
                damLayer = pix.loadImage("images/damage1.png", LoginScreen.getInstance().getdefSpriteWidth(), LoginScreen.getInstance().getdefSpriteHeight());
                time0 = pix.loadImage("images/fig/0.png");
                time1 = pix.loadImage("images/fig/1.png");
                time2 = pix.loadImage("images/fig/2.png");
                time3 = pix.loadImage("images/fig/3.png");
                time4 = pix.loadImage("images/fig/4.png");
                time5 = pix.loadImage("images/fig/5.png");
                time6 = pix.loadImage("images/fig/6.png");
                time7 = pix.loadImage("images/fig/7.png");
                time8 = pix.loadImage("images/fig/8.png");
                time9 = pix.loadImage("images/fig/9.png");
                times = new Image[]{time0, time1, time2, time3, time4, time5, time6, time7, time8, time9};
                characterPortraits = new VolatileImage[charNames.length];
                if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.storyMode)) {
                    for (int p = 0; p < charNames.length; p++) {
                        characterPortraits[p] = pix.loadVolatileImage("images/" + charNames[p] + "/cap.png", 48, 48, this);
                    }
                } else {
                    for (int p = 0; p < charNames.length; p++) {
                        characterPortraits[p] = null;
                    }
                }
                Image transBuf = pix.loadImage("images/trans.png", 5, 5);
                hpHolder = pix.loadImage("images/hpHolder.png");
                bgPic = pix.loadImage(bgLocation, 852, 480);
                phys = pix.loadImage("images/t_physical.png");
                cel = pix.loadImage("images/t_celestia.png");
                itm = pix.loadImage("images/t_item.png");
                fury1 = pix.loadImage("images/fury.gif");
                fury2 = pix.loadImage("images/furyo.png");
                fury = fury2;
                ambient1 = pix.loadVolatileImage("images/bgBG" + activeStage + "a.png", 852, 480, this);
                ambient2 = pix.loadVolatileImage("images/bgBG" + activeStage + "b.png", 852, 480, this);
                if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.storyMode)) {
                    storyPicArr = new VolatileImage[11];
                    for (int u = 0; u < 11; u++) {
                        storyPicArr[u] = pix.loadVolatileImage("images/Story/s" + u + ".png", LoginScreen.getInstance().getdefSpriteWidth(), LoginScreen.getInstance().getdefSpriteHeight(), this);
                    }
                    storyPic = storyPicArr[0];
                }
                furyBar = pix.loadImage("images/furyBar.png");
                quePic1 = pix.loadImage("images/queB.png");
                quePic2 = pix.loadImage("images/que.gif");
                oppBar = pix.loadImage("images/oppBar.png");
                moveCat = new Image[]{phys, cel, itm};
                curr = moveCat[0];
                stat1 = pix.loadVolatileImage("images/stats/stat1.png", 90, 24, this);
                stat2 = pix.loadVolatileImage("images/stats/stat2.png", 90, 24, this);
                stat3 = pix.loadVolatileImage("images/stats/stat3.png", 90, 24, this);
                stat4 = pix.loadVolatileImage("images/stats/stat4.png", 90, 24, this);
                stats = new VolatileImage[]{stat1, stat2, stat3, stat4};
                hud1 = pix.loadImage("images/hud1.png");
                hud2 = pix.loadImage("images/hud2.png");
                win = pix.loadImage("images/win.png");
                lose = pix.loadImage("images/lose.png");
                status = transBuf;
                System.out.println("loaded all char sprites imageLoader");
                imagesCharChached = true;
                //ensures method is only run once
            } catch (Exception e) {
                imagesCharChached = true;
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    /**
     * Flashy text at bottom of screen
     *
     * @param thisMessage
     */
    public void flashyText(String thisMessage) {
        opacityTxt = 0.0f;
        battleInf = new StringBuilder(thisMessage);
    }

    /**
     * Go to next command menu column
     */
    public void nextAnim() {
        if (nextEnabled && backEnabled) {
            backEnabled = false;
            yTEST = yTESTinit;
            if (currentCols < moveCat.length - 1) {
                currentCols++;
            } else {
                currentCols = 0;
            }
            curr = moveCat[currentCols];
            resolveText();
            opac = 0.0f;
            backEnabled = true;
        }
    }

    /**
     * Go to previous command menu column
     */
    public void prevAnim() {
        if (backEnabled && nextEnabled) {
            nextEnabled = false;
            yTEST = yTESTinit;
            opac = 0.0f;
            if (currentCols > 0) {
                currentCols = currentCols - 1;
            } else {
                currentCols = moveCat.length - 1;
            }
            curr = moveCat[currentCols];
            resolveText();
            opac = 0.0f;
            nextEnabled = true;
        }
    }

    public void newInstance() {
        super.newInstance();
        damOpInc = 0;
        one = 10;
        two = 10;
        three = 10;
        four = 10;
        oneO = 10;
        twoO = 10;
        threeO = 10;
        fourO = 10;
        opponentDamageYLoc = 400;
        playerDamageYLoc = 400;
        upDown = new Animations1();
        if (WindowOptions.graphics.equalsIgnoreCase("High")) {
            upDown2 = new Animations2();
            upDown3 = new Animations3();
            loadedUpdaters = true;
        }
        System.out.println("Char inc: " + charPointInc);
    }

    /**
     * displays damage graphically
     *
     * @param damageAmount - damage dealt
     * @param who          - who dealt the damage
     */
    public void guiScreenChaos(float damageAmount, char who) {
        manipulateThis = "" + Math.round(damageAmount);
        if (who == 'c') {
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

        if (who == 'o') {
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
        if (RenderCharacterSelectionScreen.getInstance().getPlayers().getCharacter().isMale()) {
            randSoundIntChar = (int) (Math.random() * AudioPlayback.maleHurt.length * 2);
            if (randSoundIntChar < AudioPlayback.maleHurt.length) {
                attackChar = new AudioPlayback(AudioPlayback.maleAttack(randSoundIntChar), false);
                attackChar.play();
            }
        } else {
            randSoundIntChar = (int) (Math.random() * AudioPlayback.femaleHurt.length * 2);
            if (randSoundIntChar < AudioPlayback.femaleHurt.length) {
                attackChar = new AudioPlayback(AudioPlayback.femaleAttack(randSoundIntChar), false);
                attackChar.play();
            }
        }
    }

    protected void attackSoundOpp() {
        if (RenderCharacterSelectionScreen.getInstance().getPlayers().getOpponent().isMale()) {
            randSoundIntOpp = (int) (Math.random() * AudioPlayback.maleHurt.length * 2);
            if (randSoundIntOpp < AudioPlayback.maleHurt.length) {
                attackOpp = new AudioPlayback(AudioPlayback.maleAttack(randSoundIntOpp), false);
                attackOpp.play();
            }
        } else {
            randSoundIntOpp = (int) (Math.random() * AudioPlayback.femaleHurt.length * 2);
            if (randSoundIntOpp < AudioPlayback.femaleHurt.length) {
                attackOpp = new AudioPlayback(AudioPlayback.femaleAttack(randSoundIntOpp), false);
                attackOpp.play();
            }
        }
    }

    protected void hurtSoundChar() {
        if (RenderCharacterSelectionScreen.getInstance().getPlayers().getOpponent().isMale()) {
            randSoundIntCharHurt = (int) (Math.random() * AudioPlayback.maleAttacks.length * 2);
            if (randSoundIntCharHurt < AudioPlayback.maleAttacks.length) {
                hurtChar = new AudioPlayback(AudioPlayback.maleHurt(randSoundIntCharHurt), false);
                hurtChar.play();
            }
        } else {
            randSoundIntCharHurt = (int) (Math.random() * AudioPlayback.femaleAttacks.length * 2);
            if (randSoundIntCharHurt < AudioPlayback.femaleAttacks.length) {
                hurtChar = new AudioPlayback(AudioPlayback.femaleHurt(randSoundIntCharHurt), false);
                hurtChar.play();
            }
        }
    }

    public void hurtSoundOpp() {
        if (RenderCharacterSelectionScreen.getInstance().getPlayers().getCharacter().isMale()) {
            randSoundIntOppHurt = (int) (Math.random() * AudioPlayback.maleAttacks.length * 2);
            if (randSoundIntOppHurt < AudioPlayback.maleAttacks.length) {
                hurtOpp = new AudioPlayback(AudioPlayback.maleHurt(randSoundIntOppHurt), false);
                hurtOpp.play();
            }
        } else {
            randSoundIntOppHurt = (int) (Math.random() * AudioPlayback.femaleAttacks.length * 2);
            if (randSoundIntOppHurt < AudioPlayback.femaleAttacks.length) {
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
        comicPicArrayPos = Math.round((float) (numOfComicPics * Math.random()));
        comicBookTextOpacity = 1.0f;
        comicY = 0;
    }

    public void resetComicTxt() {
        comicPicArrayPos = 0;
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
            move = (currentCols * 4) + itemindex + 1;
            attackArray[comboCounter] = genStr(move); // count initially negative 1, add one to get to index 0
            incrimentComboCounter();
            checkStatus();
            showBattleMessage("Qued up " + getSelMove(move));
        } else {
            RenderCharacterSelectionScreen.getInstance().errorSound();
        }
    }

    public synchronized void playBGSound() {
        if (ambientMusic == null)
            ambientMusic = new AudioPlayback("audio/" + RenderStageSelect.getInstance().getAmbientMusic()[RenderStageSelect.getInstance().getAmbientMusicIndex()] + ".mp3", true);
        ambientMusic.play();
    }

    /**
     * pause threads
     */
    public void pauseThreads() {
        try {
            ambientMusic.pause();
            if (WindowOptions.graphics.equalsIgnoreCase("High")) {
                upDown.pauseThread();
                upDown2.pauseThread();
                upDown3.pauseThread();
            }
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
            if (WindowOptions.graphics.equalsIgnoreCase("High")) {
                upDown.resumeThread();
                upDown2.resumeThread();
                upDown3.resumeThread();
            }
        } catch (Exception e) {
        }
    }

    /**
     * Clear char port
     */
    public void charPortBlank() {
        characterPortrait = blankPortrait;
    }

    /**
     * Change port pic
     *
     * @param here - index of pic
     */
    public void charPortSet(int here) {
        characterPortrait = characterPortraits[here];
    }


    /**
     * Animate attacks in graphics context
     *
     * @param thischar - active character
     */
    public void AnimatePhyAttax(char thischar) {
        if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.singlePlayer2)) {
            setSprites('c', 9, 11);
            setSprites('o', 9, 11);
            setSprites('b', 9, 11);
            setSprites('a', 9, 11);
        } else if (thischar == 'c' || thischar == 'o') {
            //sprites back to normal poses
            setSprites('c', 9, 11);
            setSprites('o', 9, 11);
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
        CurrentColor = Color.WHITE;
        safeToSelect = true;
    }

    /**
     * Makes text red, meaning its NOT OK to select a move
     */
    public void disableSelection() {
        CurrentColor = Color.RED;
        safeToSelect = false;
    }


    /**
     * Calculates angle of circle
     *
     * @return circel angle
     */
    private int phyAngle() {
        float start = RenderGameplay.getInstance().getGameInstance().getRecoveryUnitsChar() / 290.0f;
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

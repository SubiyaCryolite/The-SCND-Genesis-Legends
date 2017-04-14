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
package com.scndgen.legends.engine;

import com.scndgen.legends.LoginScreen;
import com.scndgen.legends.threads.ThreadMP3;
import io.github.subiyacryolite.enginev1.JenesisImageLoader;

import java.awt.*;
import java.awt.image.ImageObserver;

/**
 * @author ndana
 */
public class JenesisTutorial implements Runnable {

    private Image[] pix, arrows;
    private Image forward, back;
    private Thread t;
    private JenesisImageLoader pixfac;
    private boolean globalBreak, isShowing, skipSec;
    private int cord, tutSpeed, sec, pixLoc, arrowLoc, slide;
    private String tutText, topText;
    private float opacityTxt, picOpac, arrowOpac;
    private Font normalFont;
    private ThreadMP3 bgSound, nextSound, backSound;

    public JenesisTutorial() {
        pixfac = new JenesisImageLoader();
        normalFont = LoginScreen.getInstance().getMyFont(LoginScreen.normalTxtSize);
        pixLoc = 0;
        sec = 0;
        slide = -1;
        opacityTxt = 1.0f;
        picOpac = 1.0f;
        arrowOpac = 1.0f;
        tutSpeed = 8;
        cord = 360;

        pix = new Image[6];
        for (int u = 0; u < pix.length; u++) {
            pix[u] = pixfac.loadImageFromToolkitNoScale("images/tutorial/" + u + ".png");
        }

        arrows = new Image[9];
        for (int u = 0; u < arrows.length; u++) {
            arrows[u] = pixfac.loadImageFromToolkitNoScale("images/tutorial/a" + u + ".png");
        }

        forward = pixfac.loadImageFromToolkitNoScale("images/tutorial/list_item_arrow_r.png");
        back = pixfac.loadImageFromToolkitNoScale("images/tutorial/list_item_arrow_l.png");

        tutText = "tutorial";
        isShowing = true;
        bgSound = new ThreadMP3(ThreadMP3.tutorialSound(), false);
        bgSound.play();
    }

    public void startTut() {
        t = null;
        t = new Thread(this);
        t.start();
        {
            globalBreak = true;
        }
    }

    public void stopTut() {
        globalBreak = false;
        isShowing = false;
        if (bgSound != null) {
            bgSound.stop();
        }
    }

    public void backTut() {
        if (sec == 1) {
            sec = sec - 1;
        } else if (sec > 1) {
            sec = sec - 2;
        }
        playBackSound();
        skipSec = true;
    }

    public void forwarTut() {
        skipSec = true;
        playForwardSound();
    }

    public void draw(Graphics2D screen, ImageObserver obs) {
        screen.setColor(Color.BLACK);
        screen.setFont(normalFont);
        screen.fillRect(0, 0, 1024, 1024);

        if (picOpac < 0.98f) {
            picOpac = picOpac + 0.02f;
        }
        screen.setComposite(makeComposite(picOpac));
        screen.drawImage(pix[pixLoc], 0, 0, obs);
        screen.setComposite(makeComposite(1.0f));

        if (arrowOpac < 0.98f) {
            arrowOpac = arrowOpac + 0.02f;
        }
        screen.setComposite(makeComposite(arrowOpac));
        screen.drawImage(arrows[arrowLoc], 0, 0, obs);
        screen.setComposite(makeComposite(1.0f));

        screen.setComposite(makeComposite(0.5f));
        screen.setColor(Color.BLACK);
        screen.fillRoundRect(0, 216, LoginScreen.getInstance().getdefSpriteWidth(), 48, 48, 48); //mid minus half the font size (430-6)

        screen.setComposite(makeComposite(10 * 0.1f));
        screen.setColor(Color.WHITE);

        screen.drawImage(back, 10, 224, obs);
        screen.drawImage(forward, 810, 224, obs);

        if (opacityTxt < 0.98f) {
            opacityTxt = opacityTxt + 0.02f;
        }
        screen.setComposite(makeComposite(opacityTxt));
        screen.drawString(tutText, ((852 - screen.getFontMetrics().stringWidth(tutText)) / 2), 233);
        screen.setComposite(makeComposite(1.0f));

        screen.drawString(":: " + topText + " - " + JenesisLanguage.getInstance().getLine(365) + " " + sec + " ::", ((852 - screen.getFontMetrics().stringWidth(":: " + topText + " - " + JenesisLanguage.getInstance().getLine(365) + " " + sec + " ::")) / 2), 253);

        screen.drawString(JenesisLanguage.getInstance().getLine(366) + ":", 10, cord);
        screen.drawString("1 - " + JenesisLanguage.getInstance().getLine(356), 20, (cord + (1 * 14)));
        screen.drawString("2 - " + JenesisLanguage.getInstance().getLine(360), 20, (cord + (2 * 14)));
        screen.drawString("3 - " + JenesisLanguage.getInstance().getLine(355), 20, (cord + (3 * 14)));
        screen.drawString("4 - " + JenesisLanguage.getInstance().getLine(358), 20, (cord + (4 * 14)));
        screen.drawString("5 - " + JenesisLanguage.getInstance().getLine(357), 20, (cord + (5 * 14)));
        screen.drawString("6 - " + JenesisLanguage.getInstance().getLine(359), 20, (cord + (6 * 14)));
        screen.drawString(JenesisLanguage.getInstance().getLine(343), 20, (cord + (7 * 14)));

    }

    public void skipToSection(int n) {
        sec = n;
        skipSec = true;
    }

    private void setPic(int p) {
        if (p != pixLoc) {
            pixLoc = p;
            picOpac = 0.0f;
        }
    }

    private void setArr(int p) {
        if (p != arrowLoc) {
            arrowLoc = p;
            arrowOpac = 0.0f;
        }

        if (p > arrowLoc) {
            playForwardSound();
        } else {
            playBackSound();
        }
    }

    private void playBackSound() {
        if (isShowing) {
            backSound = new ThreadMP3(ThreadMP3.soundBack(), false);
            backSound.play();
        }
    }

    private void playForwardSound() {
        if (isShowing) {
            nextSound = new ThreadMP3(ThreadMP3.soundNext(), false);
            nextSound.play();
        }
    }

    private void setTxt(String p) {
        tutText = p;
        opacityTxt = 0.0f;
    }

    private void setTop(String p) {
        topText = p;
    }

    /**
     * Transparency method
     *
     * @param alpha transparency level
     * @return, transparency
     */
    private AlphaComposite makeComposite(float alpha) {
        int type = AlphaComposite.SRC_OVER;
        return (AlphaComposite.getInstance(type, alpha));
    }

    @Override
    @SuppressWarnings({"static-access", "SleepWhileInLoop"})
    public void run() {
        try {
            while (globalBreak) {
                slide = -1;

                slide++;
                if (sec == slide) {
                    sec = slide + 1;
                    setPic(0);
                    setArr(0);
                    setTop(JenesisLanguage.getInstance().getLine(356)); //intro
                    setTxt(JenesisLanguage.getInstance().getLine(320));
                    sec3:
                    for (int i = 0; i < (tutSpeed * (tutText.length()) * 1); i++) {
                        if (skipSec) {
                            skipSec = false;
                            break sec3;
                        }
                        t.sleep(16);
                    }
                }

                slide++;
                if (sec == slide) {
                    sec = slide + 1;
                    setTop(JenesisLanguage.getInstance().getLine(356)); //intro
                    setTxt(JenesisLanguage.getInstance().getLine(321));
                    setPic(0);
                    setArr(0);
                    sec3:
                    for (int i = 0; i < (tutSpeed * (tutText.length()) * 1); i++) {
                        if (skipSec) {
                            skipSec = false;
                            break sec3;
                        }
                        t.sleep(16);
                    }
                }

                slide++;
                if (sec == slide) {
                    sec = slide + 1;
                    setTop(JenesisLanguage.getInstance().getLine(360)); //basics
                    setTxt(JenesisLanguage.getInstance().getLine(322));
                    setPic(0);
                    setArr(0);
                    sec3:
                    for (int i = 0; i < (tutSpeed * (tutText.length()) * 1); i++) {
                        if (skipSec) {
                            skipSec = false;
                            break sec3;
                        }
                        t.sleep(16);
                    }
                }

                slide++;
                if (sec == slide) {
                    sec = slide + 1;
                    setTop(JenesisLanguage.getInstance().getLine(360)); //basics
                    setTxt(JenesisLanguage.getInstance().getLine(344));
                    setPic(0);
                    setArr(6);
                    sec3:
                    for (int i = 0; i < (tutSpeed * (tutText.length()) * 1); i++) {
                        if (skipSec) {
                            skipSec = false;
                            break sec3;
                        }
                        t.sleep(16);
                    }
                }

                slide++;
                if (sec == slide) {
                    sec = slide + 1;
                    setTop(JenesisLanguage.getInstance().getLine(360)); //basics
                    setTxt(JenesisLanguage.getInstance().getLine(345));
                    setPic(0);
                    setArr(6);
                    sec3:
                    for (int i = 0; i < (tutSpeed * (tutText.length()) * 1); i++) {
                        if (skipSec) {
                            skipSec = false;
                            break sec3;
                        }
                        t.sleep(16);
                    }
                }

                slide++;
                if (sec == slide) {
                    sec = slide + 1;
                    setTop(JenesisLanguage.getInstance().getLine(360)); //basics
                    setTxt(JenesisLanguage.getInstance().getLine(323));
                    setPic(0);
                    setArr(1);
                    sec3:
                    for (int i = 0; i < (tutSpeed * (tutText.length()) * 1); i++) {
                        if (skipSec) {
                            skipSec = false;
                            break sec3;
                        }
                        t.sleep(16);
                    }
                }

                slide++;
                if (sec == slide) {
                    sec = slide + 1;
                    setTop(JenesisLanguage.getInstance().getLine(360)); //basics
                    setTxt(JenesisLanguage.getInstance().getLine(324));
                    setPic(0);
                    setArr(1);
                    sec3:
                    for (int i = 0; i < (tutSpeed * (tutText.length()) * 1); i++) {
                        if (skipSec) {
                            skipSec = false;
                            break sec3;
                        }
                        t.sleep(16);
                    }
                }
                slide++;
                if (sec == slide) {
                    sec = slide + 1;
                    setTop(JenesisLanguage.getInstance().getLine(360)); //basics
                    setTxt(JenesisLanguage.getInstance().getLine(325));
                    setPic(0);
                    setArr(1);
                    sec3:
                    for (int i = 0; i < (tutSpeed * (tutText.length()) * 1); i++) {
                        if (skipSec) {
                            skipSec = false;
                            break sec3;
                        }
                        t.sleep(16);
                    }
                }
                slide++;
                if (sec == slide) {
                    sec = slide + 1;
                    setTop(JenesisLanguage.getInstance().getLine(360)); //basics
                    setTxt(JenesisLanguage.getInstance().getLine(326));
                    setPic(0);
                    setArr(1);
                    sec3:
                    for (int i = 0; i < (tutSpeed * (tutText.length()) * 1); i++) {
                        if (skipSec) {
                            skipSec = false;
                            break sec3;
                        }
                        t.sleep(16);
                    }
                }

                slide++;
                if (sec == slide) {
                    sec = slide + 1;
                    setTop(JenesisLanguage.getInstance().getLine(360)); //basics
                    setTxt(JenesisLanguage.getInstance().getLine(327));
                    setPic(0);
                    setArr(2);
                    sec3:
                    for (int i = 0; i < (tutSpeed * (tutText.length()) * 1); i++) {
                        if (skipSec) {
                            skipSec = false;
                            break sec3;
                        }
                        t.sleep(16);
                    }
                }

                slide++;
                if (sec == slide) {
                    sec = slide + 1;
                    setTop(JenesisLanguage.getInstance().getLine(355)); //CM
                    setTxt(JenesisLanguage.getInstance().getLine(328));
                    setPic(0);
                    setArr(5);
                    sec3:
                    for (int i = 0; i < (tutSpeed * (tutText.length()) * 1); i++) {
                        if (skipSec) {
                            skipSec = false;
                            break sec3;
                        }
                        t.sleep(16);
                    }
                }

                slide++;
                if (sec == slide) {
                    sec = slide + 1;
                    setTop(JenesisLanguage.getInstance().getLine(355)); //CM
                    setTxt(JenesisLanguage.getInstance().getLine(329));
                    setPic(0);
                    setArr(5);
                    sec3:
                    for (int i = 0; i < (tutSpeed * (tutText.length()) * 1); i++) {
                        if (skipSec) {
                            skipSec = false;
                            break sec3;
                        }
                        t.sleep(16);
                    }
                }

                slide++;
                if (sec == slide) {
                    sec = slide + 1;
                    setTop(JenesisLanguage.getInstance().getLine(355)); //CM
                    setTxt(JenesisLanguage.getInstance().getLine(330));
                    setPic(0);
                    setArr(5);
                    sec3:
                    for (int i = 0; i < (tutSpeed * (tutText.length()) * 1); i++) {
                        if (skipSec) {
                            skipSec = false;
                            break sec3;
                        }
                        t.sleep(16);
                    }
                }

                slide++;
                if (sec == slide) {
                    sec = slide + 1;
                    setTop(JenesisLanguage.getInstance().getLine(355)); //CM
                    setTxt(JenesisLanguage.getInstance().getLine(331));
                    setPic(0);
                    setArr(5);
                    sec3:
                    for (int i = 0; i < (tutSpeed * (tutText.length()) * 1); i++) {
                        if (skipSec) {
                            skipSec = false;
                            break sec3;
                        }
                        t.sleep(16);
                    }
                }

                slide++;
                if (sec == slide) {
                    sec = slide + 1;
                    setTop(JenesisLanguage.getInstance().getLine(355)); //CM
                    setTxt(JenesisLanguage.getInstance().getLine(332));
                    setPic(0);
                    setArr(5);
                    sec3:
                    for (int i = 0; i < (tutSpeed * (tutText.length()) * 1); i++) {
                        if (skipSec) {
                            skipSec = false;
                            break sec3;
                        }
                        t.sleep(16);
                    }
                }

                slide++;
                if (sec == slide) {
                    sec = slide + 1;
                    setTop(JenesisLanguage.getInstance().getLine(355)); //CM
                    setTxt(JenesisLanguage.getInstance().getLine(333));
                    setPic(0);
                    setArr(5);
                    sec3:
                    for (int i = 0; i < (tutSpeed * (tutText.length()) * 1); i++) {
                        if (skipSec) {
                            skipSec = false;
                            break sec3;
                        }
                        t.sleep(16);
                    }
                }

                slide++;
                if (sec == slide) {
                    sec = slide + 1;
                    setTop(JenesisLanguage.getInstance().getLine(355)); //CM
                    setTxt(JenesisLanguage.getInstance().getLine(334));
                    setPic(0);
                    setArr(5);
                    sec3:
                    for (int i = 0; i < (tutSpeed * (tutText.length()) * 1); i++) {
                        if (skipSec) {
                            skipSec = false;
                            break sec3;
                        }
                        t.sleep(16);
                    }
                }

                slide++;
                if (sec == slide) {
                    sec = slide + 1;
                    setTop(JenesisLanguage.getInstance().getLine(355)); //CM
                    setTxt(JenesisLanguage.getInstance().getLine(335));
                    setPic(0);
                    setArr(5);
                    sec3:
                    for (int i = 0; i < (tutSpeed * (tutText.length()) * 1); i++) {
                        if (skipSec) {
                            skipSec = false;
                            break sec3;
                        }
                        t.sleep(16);
                    }
                }

                slide++;
                if (sec == slide) {
                    sec = slide + 1;
                    setTop(JenesisLanguage.getInstance().getLine(355)); //CM
                    setTxt(JenesisLanguage.getInstance().getLine(336));
                    setPic(0);
                    setArr(5);
                    sec3:
                    for (int i = 0; i < (tutSpeed * (tutText.length()) * 1); i++) {
                        if (skipSec) {
                            skipSec = false;
                            break sec3;
                        }
                        t.sleep(16);
                    }
                }

                slide++;
                if (sec == slide) {
                    sec = slide + 1;
                    setTop(JenesisLanguage.getInstance().getLine(358)); //FB
                    setTxt(JenesisLanguage.getInstance().getLine(352));
                    setArr(8);
                    setPic(4);
                    sec3:
                    for (int i = 0; i < (tutSpeed * (tutText.length()) * 1); i++) {
                        if (skipSec) {
                            skipSec = false;
                            break sec3;
                        }
                        t.sleep(16);
                    }
                }

                slide++;
                if (sec == slide) {
                    sec = slide + 1;
                    setTop(JenesisLanguage.getInstance().getLine(358)); //FB
                    setTxt(JenesisLanguage.getInstance().getLine(353));
                    setArr(8);
                    setPic(4);
                    sec3:
                    for (int i = 0; i < (tutSpeed * (tutText.length()) * 1); i++) {
                        if (skipSec) {
                            skipSec = false;
                            break sec3;
                        }
                        t.sleep(16);
                    }
                }

                slide++;
                if (sec == slide) {
                    sec = slide + 1;
                    setTop(JenesisLanguage.getInstance().getLine(358)); //FB
                    setTxt(JenesisLanguage.getInstance().getLine(354));
                    setArr(8);
                    setPic(5);
                    sec3:
                    for (int i = 0; i < (tutSpeed * (tutText.length()) * 1); i++) {
                        if (skipSec) {
                            skipSec = false;
                            break sec3;
                        }
                        t.sleep(16);
                    }
                }

                slide++;
                if (sec == slide) {
                    sec = slide + 1;
                    setTop(JenesisLanguage.getInstance().getLine(358)); //FB
                    setTxt(JenesisLanguage.getInstance().getLine(361));
                    setArr(8);
                    setPic(5);
                    sec3:
                    for (int i = 0; i < (tutSpeed * (tutText.length()) * 1); i++) {
                        if (skipSec) {
                            skipSec = false;
                            break sec3;
                        }
                        t.sleep(16);
                    }
                }

                slide++;
                if (sec == slide) {
                    sec = slide + 1;
                    setTop(JenesisLanguage.getInstance().getLine(358)); //FB
                    setTxt(JenesisLanguage.getInstance().getLine(362));
                    setArr(8);
                    setPic(5);
                    sec3:
                    for (int i = 0; i < (tutSpeed * (tutText.length()) * 1); i++) {
                        if (skipSec) {
                            skipSec = false;
                            break sec3;
                        }
                        t.sleep(16);
                    }
                }

                slide++;
                if (sec == slide) {
                    sec = slide + 1;
                    setTop(JenesisLanguage.getInstance().getLine(358)); //FB
                    setTxt(JenesisLanguage.getInstance().getLine(363));
                    setArr(8);
                    setPic(5);
                    sec3:
                    for (int i = 0; i < (tutSpeed * (tutText.length()) * 1); i++) {
                        if (skipSec) {
                            skipSec = false;
                            break sec3;
                        }
                        t.sleep(16);
                    }
                }

                slide++;
                if (sec == slide) {
                    sec = slide + 1;
                    setTop(JenesisLanguage.getInstance().getLine(358)); //FB
                    setTxt(JenesisLanguage.getInstance().getLine(336));
                    setArr(8);
                    setPic(4);
                    sec3:
                    for (int i = 0; i < (tutSpeed * (tutText.length()) * 1); i++) {
                        if (skipSec) {
                            skipSec = false;
                            break sec3;
                        }
                        t.sleep(16);
                    }
                }

                slide++;
                if (sec == slide) {
                    sec = slide + 1;
                    setTop(JenesisLanguage.getInstance().getLine(357)); //AB
                    setTxt(JenesisLanguage.getInstance().getLine(337));
                    setPic(4);
                    setArr(3);
                    sec3:
                    for (int i = 0; i < (tutSpeed * (tutText.length()) * 1); i++) {
                        if (skipSec) {
                            skipSec = false;
                            break sec3;
                        }
                        t.sleep(16);
                    }
                }

                slide++;
                if (sec == slide) {
                    sec = slide + 1;
                    setTop(JenesisLanguage.getInstance().getLine(357)); //AB
                    setTxt(JenesisLanguage.getInstance().getLine(338));
                    setPic(4);
                    setArr(3);
                    sec3:
                    for (int i = 0; i < (tutSpeed * (tutText.length()) * 1); i++) {
                        if (skipSec) {
                            skipSec = false;
                            break sec3;
                        }
                        t.sleep(16);
                    }
                }

                slide++;
                if (sec == slide) {
                    sec = slide + 1;
                    setTop(JenesisLanguage.getInstance().getLine(357)); //AB
                    setTxt(JenesisLanguage.getInstance().getLine(339));
                    setPic(4);
                    setArr(3);
                    sec3:
                    for (int i = 0; i < (tutSpeed * (tutText.length()) * 1); i++) {
                        if (skipSec) {
                            skipSec = false;
                            break sec3;
                        }
                        t.sleep(16);
                    }
                }

                slide++;
                if (sec == slide) {
                    sec = slide + 1;
                    setTop(JenesisLanguage.getInstance().getLine(357)); //AB
                    setTxt(JenesisLanguage.getInstance().getLine(340));
                    setPic(4);
                    setArr(4);
                    sec3:
                    for (int i = 0; i < (tutSpeed * (tutText.length()) * 1); i++) {
                        if (skipSec) {
                            skipSec = false;
                            break sec3;
                        }
                        t.sleep(16);
                    }
                }

                slide++;
                if (sec == slide) {
                    sec = slide + 1;
                    setTop(JenesisLanguage.getInstance().getLine(357)); //AB
                    setTxt(JenesisLanguage.getInstance().getLine(341));
                    setPic(0);
                    setArr(4);
                    sec3:
                    for (int i = 0; i < (tutSpeed * (tutText.length()) * 1); i++) {
                        if (skipSec) {
                            skipSec = false;
                            break sec3;
                        }
                        t.sleep(16);
                    }
                }

                slide++;
                if (sec == slide) {
                    sec = slide + 1;
                    setTop(JenesisLanguage.getInstance().getLine(359)); //MoveSel
                    setTxt(JenesisLanguage.getInstance().getLine(346));
                    setPic(0);
                    setArr(0);
                    sec3:
                    for (int i = 0; i < (tutSpeed * (tutText.length()) * 1); i++) {
                        if (skipSec) {
                            skipSec = false;
                            break sec3;
                        }
                        t.sleep(16);
                    }
                }

                slide++;
                if (sec == slide) {
                    sec = slide + 1;
                    setTop(JenesisLanguage.getInstance().getLine(359)); //MoveSel
                    setTxt(JenesisLanguage.getInstance().getLine(347));
                    setPic(4);
                    setArr(7);
                    sec3:
                    for (int i = 0; i < (tutSpeed * (tutText.length()) * 1); i++) {
                        if (skipSec) {
                            skipSec = false;
                            break sec3;
                        }
                        t.sleep(16);
                    }
                }

                slide++;
                if (sec == slide) {
                    sec = slide + 1;
                    setTop(JenesisLanguage.getInstance().getLine(359)); //MoveSel
                    setTxt(JenesisLanguage.getInstance().getLine(348));
                    setPic(1);
                    setArr(7);
                    sec3:
                    for (int i = 0; i < (tutSpeed * (tutText.length()) * 1); i++) {
                        if (skipSec) {
                            skipSec = false;
                            break sec3;
                        }
                        t.sleep(16);
                    }
                }

                slide++;
                if (sec == slide) {
                    sec = slide + 1;
                    setTop(JenesisLanguage.getInstance().getLine(359)); //MoveSel
                    setTxt(JenesisLanguage.getInstance().getLine(349));
                    setPic(2);
                    setArr(7);
                    sec3:
                    for (int i = 0; i < (tutSpeed * (tutText.length()) * 1); i++) {
                        if (skipSec) {
                            skipSec = false;
                            break sec3;
                        }
                        t.sleep(16);
                    }
                }

                slide++;
                if (sec == slide) {
                    sec = slide + 1;
                    setTop(JenesisLanguage.getInstance().getLine(359)); //MoveSel
                    setTxt(JenesisLanguage.getInstance().getLine(350));
                    setPic(3);
                    setArr(0);
                    sec3:
                    for (int i = 0; i < (tutSpeed * (tutText.length()) * 1); i++) {
                        if (skipSec) {
                            skipSec = false;
                            break sec3;
                        }
                        t.sleep(16);
                    }
                }

                slide++;
                if (sec == slide) {
                    sec = slide + 1;
                    setTop(JenesisLanguage.getInstance().getLine(359)); //MoveSel
                    setTxt(JenesisLanguage.getInstance().getLine(367));
                    setPic(3);
                    setArr(0);
                    sec3:
                    for (int i = 0; i < (tutSpeed * (tutText.length()) * 1); i++) {
                        if (skipSec) {
                            skipSec = false;
                            break sec3;
                        }
                        t.sleep(16);
                    }
                }

                slide++;
                if (sec == slide) {
                    sec = slide + 1;
                    setTop(JenesisLanguage.getInstance().getLine(359)); //MoveSel
                    setTxt(JenesisLanguage.getInstance().getLine(351));
                    setArr(0);
                    setPic(3);
                    sec3:
                    for (int i = 0; i < (tutSpeed * (tutText.length()) * 1); i++) {
                        if (skipSec) {
                            skipSec = false;
                            break sec3;
                        }
                        t.sleep(16);
                    }
                }


                slide++;
                if (sec == slide) {
                    setTxt(JenesisLanguage.getInstance().getLine(393));
                    setPic(0);
                    setArr(0);
                    sec3:
                    for (int i = 0; i < (16 * 30 * 1); i++) {
                        if (skipSec) {
                            skipSec = false;
                            break sec3;
                        }
                        t.sleep(16);
                    }
                    //last slide should not inc
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
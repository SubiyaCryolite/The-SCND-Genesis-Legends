/**************************************************************************

 The SCND Genesis: Legends is a fighting game based on THE SCND GENESIS,
 a webcomic created by Ifunga Ndana ((([http://www.scndgen.com]))).

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
 along with The SCND Genesis: Legends. If not, see <http://www.gnu.org/licenses/>.

 **************************************************************************/
package com.scndgen.legends.mode;

import com.scndgen.legends.Language;
import com.scndgen.legends.LoginScreen;
import com.scndgen.legends.constants.AudioConstants;
import com.scndgen.legends.enums.AudioType;
import com.scndgen.legends.enums.Overlay;
import com.scndgen.legends.render.RenderMainMenu;
import io.github.subiyacryolite.enginev1.AudioPlayback;
import io.github.subiyacryolite.enginev1.Loader;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import static com.sun.javafx.tk.Toolkit.getToolkit;


/**
 * @author ndana
 */
public class Tutorial implements Runnable {

    private Image[] slides, arrows;
    private Image forward, back;
    private Thread thread;
    private Loader loader;
    private boolean skipSec;
    private int cord, tutSpeed, sec, pixLoc, arrowLoc, slide;
    private String tutText, topText;
    private float opacityTxt, picOpac, arrowOpac;
    private Font normalFont;
    private final AudioPlayback bgSound, backSound;

    public Tutorial() {
        backSound = new AudioPlayback(AudioConstants.soundBack(), AudioType.SOUND, false);
        bgSound = new AudioPlayback(AudioConstants.tutorialSound(), AudioType.MUSIC, true);
        loader = new Loader();
        normalFont = getMyFont(LoginScreen.NORMAL_TXT_SIZE);
        pixLoc = 0;
        sec = 0;
        slide = -1;
        opacityTxt = 1.0f;
        picOpac = 1.0f;
        arrowOpac = 1.0f;
        tutSpeed = 8;
        cord = 360;

        slides = new Image[6];
        for (int u = 0; u < slides.length; u++) {
            slides[u] = loader.load("images/tutorial/" + u + ".png");
        }
        arrows = new Image[9];
        for (int u = 0; u < arrows.length; u++) {
            arrows[u] = loader.load("images/tutorial/a" + u + ".png");
        }
        forward = loader.load("images/tutorial/list_item_arrow_r.png");
        back = loader.load("images/tutorial/list_item_arrow_l.png");
        tutText = "TUTORIAL";
    }

    public void beginTutorial() {
        RenderMainMenu.get().onLeaveMode();
        thread = null;
        thread = new Thread(this);
        thread.start();
        bgSound.play();
    }

    public void onLeft() {
        if (sec == 1) {
            sec = sec - 1;
        } else if (sec > 1) {
            sec = sec - 2;
        }
        playBackSound();
        skipSec = true;
    }

    public void onRight() {
        skipSec = true;
        playForwardSound();
    }

    public void draw(GraphicsContext gc, double x, double y) {
        gc.setFill(Color.BLACK);
        gc.setFont(normalFont);
        gc.fillRect(0, 0, 1024, 1024);

        if (picOpac < 0.98f) {
            picOpac = picOpac + 0.02f;
        }
        gc.setGlobalAlpha((picOpac));
        gc.drawImage(slides[pixLoc], 0, 0);
        gc.setGlobalAlpha((1.0f));

        if (arrowOpac < 0.98f) {
            arrowOpac = arrowOpac + 0.02f;
        }
        gc.setGlobalAlpha((arrowOpac));
        gc.drawImage(arrows[arrowLoc], 0, 0);
        gc.setGlobalAlpha((1.0f));

        gc.setGlobalAlpha((0.5f));
        gc.setFill(Color.BLACK);
        gc.fillRoundRect(0, 216, x, 48, 48, 48); //mid minus half the font size (430-6)

        gc.setGlobalAlpha((10 * 0.1f));
        gc.setFill(Color.WHITE);

        gc.drawImage(back, 10, 224);
        gc.drawImage(forward, 810, 224);

        if (opacityTxt < 0.98f) {
            opacityTxt = opacityTxt + 0.02f;
        }
        gc.setGlobalAlpha((opacityTxt));
        gc.fillText(tutText, (852 - getToolkit().getFontLoader().computeStringWidth(tutText, gc.getFont())) / 2, 233);
        gc.setGlobalAlpha((1.0f));

        gc.fillText(":: " + topText + " - " + Language.get().get(365) + " " + sec + " ::", (852 - getToolkit().getFontLoader().computeStringWidth(":: " + topText + " - " + Language.get().get(365) + " " + sec + " ::", gc.getFont())) / 2, 253);

        gc.fillText(Language.get().get(366) + ":", 10, cord);
        gc.fillText("1 - " + Language.get().get(356), 20, (cord + (1 * 14)));
        gc.fillText("2 - " + Language.get().get(360), 20, (cord + (2 * 14)));
        gc.fillText("3 - " + Language.get().get(355), 20, (cord + (3 * 14)));
        gc.fillText("4 - " + Language.get().get(358), 20, (cord + (4 * 14)));
        gc.fillText("5 - " + Language.get().get(357), 20, (cord + (5 * 14)));
        gc.fillText("6 - " + Language.get().get(359), 20, (cord + (6 * 14)));
        gc.fillText(Language.get().get(343), 20, (cord + (7 * 14)));

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
        AudioPlayback backSound = new AudioPlayback(AudioConstants.soundBack(), AudioType.SOUND, false);
        backSound.play();
    }

    private void playForwardSound() {
        AudioPlayback nextSound = new AudioPlayback(AudioConstants.soundNext(), AudioType.SOUND, false);
        nextSound.play();
    }

    private void setTxt(String p) {
        tutText = p;
        opacityTxt = 0.0f;
    }

    private void setTop(String p) {
        topText = p;
    }

    @Override
    public void run() {
        try {
            while (true) {
                slide = -1;
                slide++;
                if (sec == slide) {
                    sec = slide + 1;
                    setPic(0);
                    setArr(0);
                    setTop(Language.get().get(356)); //intro
                    setTxt(Language.get().get(320));
                    sec3:
                    for (int i = 0; i < (tutSpeed * (tutText.length()) * 1); i++) {
                        if (skipSec) {
                            skipSec = false;
                            break sec3;
                        }
                        thread.sleep(16);
                    }
                }
                slide++;
                if (sec == slide) {
                    sec = slide + 1;
                    setTop(Language.get().get(356)); //intro
                    setTxt(Language.get().get(321));
                    setPic(0);
                    setArr(0);
                    sec3:
                    for (int i = 0; i < (tutSpeed * (tutText.length()) * 1); i++) {
                        if (skipSec) {
                            skipSec = false;
                            break sec3;
                        }
                        thread.sleep(16);
                    }
                }
                slide++;
                if (sec == slide) {
                    sec = slide + 1;
                    setTop(Language.get().get(360)); //basics
                    setTxt(Language.get().get(322));
                    setPic(0);
                    setArr(0);
                    sec3:
                    for (int i = 0; i < (tutSpeed * (tutText.length()) * 1); i++) {
                        if (skipSec) {
                            skipSec = false;
                            break sec3;
                        }
                        thread.sleep(16);
                    }
                }
                slide++;
                if (sec == slide) {
                    sec = slide + 1;
                    setTop(Language.get().get(360)); //basics
                    setTxt(Language.get().get(344));
                    setPic(0);
                    setArr(6);
                    sec3:
                    for (int i = 0; i < (tutSpeed * (tutText.length()) * 1); i++) {
                        if (skipSec) {
                            skipSec = false;
                            break sec3;
                        }
                        thread.sleep(16);
                    }
                }
                slide++;
                if (sec == slide) {
                    sec = slide + 1;
                    setTop(Language.get().get(360)); //basics
                    setTxt(Language.get().get(345));
                    setPic(0);
                    setArr(6);
                    sec3:
                    for (int i = 0; i < (tutSpeed * (tutText.length()) * 1); i++) {
                        if (skipSec) {
                            skipSec = false;
                            break sec3;
                        }
                        thread.sleep(16);
                    }
                }
                slide++;
                if (sec == slide) {
                    sec = slide + 1;
                    setTop(Language.get().get(360)); //basics
                    setTxt(Language.get().get(323));
                    setPic(0);
                    setArr(1);
                    sec3:
                    for (int i = 0; i < (tutSpeed * (tutText.length()) * 1); i++) {
                        if (skipSec) {
                            skipSec = false;
                            break sec3;
                        }
                        thread.sleep(16);
                    }
                }
                slide++;
                if (sec == slide) {
                    sec = slide + 1;
                    setTop(Language.get().get(360)); //basics
                    setTxt(Language.get().get(324));
                    setPic(0);
                    setArr(1);
                    sec3:
                    for (int i = 0; i < (tutSpeed * (tutText.length()) * 1); i++) {
                        if (skipSec) {
                            skipSec = false;
                            break sec3;
                        }
                        thread.sleep(16);
                    }
                }
                slide++;
                if (sec == slide) {
                    sec = slide + 1;
                    setTop(Language.get().get(360)); //basics
                    setTxt(Language.get().get(325));
                    setPic(0);
                    setArr(1);
                    sec3:
                    for (int i = 0; i < (tutSpeed * (tutText.length()) * 1); i++) {
                        if (skipSec) {
                            skipSec = false;
                            break sec3;
                        }
                        thread.sleep(16);
                    }
                }
                slide++;
                if (sec == slide) {
                    sec = slide + 1;
                    setTop(Language.get().get(360)); //basics
                    setTxt(Language.get().get(326));
                    setPic(0);
                    setArr(1);
                    sec3:
                    for (int i = 0; i < (tutSpeed * (tutText.length()) * 1); i++) {
                        if (skipSec) {
                            skipSec = false;
                            break sec3;
                        }
                        thread.sleep(16);
                    }
                }
                slide++;
                if (sec == slide) {
                    sec = slide + 1;
                    setTop(Language.get().get(360)); //basics
                    setTxt(Language.get().get(327));
                    setPic(0);
                    setArr(2);
                    sec3:
                    for (int i = 0; i < (tutSpeed * (tutText.length()) * 1); i++) {
                        if (skipSec) {
                            skipSec = false;
                            break sec3;
                        }
                        thread.sleep(16);
                    }
                }
                slide++;
                if (sec == slide) {
                    sec = slide + 1;
                    setTop(Language.get().get(355)); //CM
                    setTxt(Language.get().get(328));
                    setPic(0);
                    setArr(5);
                    sec3:
                    for (int i = 0; i < (tutSpeed * (tutText.length()) * 1); i++) {
                        if (skipSec) {
                            skipSec = false;
                            break sec3;
                        }
                        thread.sleep(16);
                    }
                }
                slide++;
                if (sec == slide) {
                    sec = slide + 1;
                    setTop(Language.get().get(355)); //CM
                    setTxt(Language.get().get(329));
                    setPic(0);
                    setArr(5);
                    sec3:
                    for (int i = 0; i < (tutSpeed * (tutText.length()) * 1); i++) {
                        if (skipSec) {
                            skipSec = false;
                            break sec3;
                        }
                        thread.sleep(16);
                    }
                }
                slide++;
                if (sec == slide) {
                    sec = slide + 1;
                    setTop(Language.get().get(355)); //CM
                    setTxt(Language.get().get(330));
                    setPic(0);
                    setArr(5);
                    sec3:
                    for (int i = 0; i < (tutSpeed * (tutText.length()) * 1); i++) {
                        if (skipSec) {
                            skipSec = false;
                            break sec3;
                        }
                        thread.sleep(16);
                    }
                }
                slide++;
                if (sec == slide) {
                    sec = slide + 1;
                    setTop(Language.get().get(355)); //CM
                    setTxt(Language.get().get(331));
                    setPic(0);
                    setArr(5);
                    sec3:
                    for (int i = 0; i < (tutSpeed * (tutText.length()) * 1); i++) {
                        if (skipSec) {
                            skipSec = false;
                            break sec3;
                        }
                        thread.sleep(16);
                    }
                }
                slide++;
                if (sec == slide) {
                    sec = slide + 1;
                    setTop(Language.get().get(355)); //CM
                    setTxt(Language.get().get(332));
                    setPic(0);
                    setArr(5);
                    sec3:
                    for (int i = 0; i < (tutSpeed * (tutText.length()) * 1); i++) {
                        if (skipSec) {
                            skipSec = false;
                            break sec3;
                        }
                        thread.sleep(16);
                    }
                }
                slide++;
                if (sec == slide) {
                    sec = slide + 1;
                    setTop(Language.get().get(355)); //CM
                    setTxt(Language.get().get(333));
                    setPic(0);
                    setArr(5);
                    sec3:
                    for (int i = 0; i < (tutSpeed * (tutText.length()) * 1); i++) {
                        if (skipSec) {
                            skipSec = false;
                            break sec3;
                        }
                        thread.sleep(16);
                    }
                }
                slide++;
                if (sec == slide) {
                    sec = slide + 1;
                    setTop(Language.get().get(355)); //CM
                    setTxt(Language.get().get(334));
                    setPic(0);
                    setArr(5);
                    sec3:
                    for (int i = 0; i < (tutSpeed * (tutText.length()) * 1); i++) {
                        if (skipSec) {
                            skipSec = false;
                            break sec3;
                        }
                        thread.sleep(16);
                    }
                }
                slide++;
                if (sec == slide) {
                    sec = slide + 1;
                    setTop(Language.get().get(355)); //CM
                    setTxt(Language.get().get(335));
                    setPic(0);
                    setArr(5);
                    sec3:
                    for (int i = 0; i < (tutSpeed * (tutText.length()) * 1); i++) {
                        if (skipSec) {
                            skipSec = false;
                            break sec3;
                        }
                        thread.sleep(16);
                    }
                }
                slide++;
                if (sec == slide) {
                    sec = slide + 1;
                    setTop(Language.get().get(355)); //CM
                    setTxt(Language.get().get(336));
                    setPic(0);
                    setArr(5);
                    sec3:
                    for (int i = 0; i < (tutSpeed * (tutText.length()) * 1); i++) {
                        if (skipSec) {
                            skipSec = false;
                            break sec3;
                        }
                        thread.sleep(16);
                    }
                }
                slide++;
                if (sec == slide) {
                    sec = slide + 1;
                    setTop(Language.get().get(358)); //FB
                    setTxt(Language.get().get(352));
                    setArr(8);
                    setPic(4);
                    sec3:
                    for (int i = 0; i < (tutSpeed * (tutText.length()) * 1); i++) {
                        if (skipSec) {
                            skipSec = false;
                            break sec3;
                        }
                        thread.sleep(16);
                    }
                }
                slide++;
                if (sec == slide) {
                    sec = slide + 1;
                    setTop(Language.get().get(358)); //FB
                    setTxt(Language.get().get(353));
                    setArr(8);
                    setPic(4);
                    sec3:
                    for (int i = 0; i < (tutSpeed * (tutText.length()) * 1); i++) {
                        if (skipSec) {
                            skipSec = false;
                            break sec3;
                        }
                        thread.sleep(16);
                    }
                }
                slide++;
                if (sec == slide) {
                    sec = slide + 1;
                    setTop(Language.get().get(358)); //FB
                    setTxt(Language.get().get(354));
                    setArr(8);
                    setPic(5);
                    sec3:
                    for (int i = 0; i < (tutSpeed * (tutText.length()) * 1); i++) {
                        if (skipSec) {
                            skipSec = false;
                            break sec3;
                        }
                        thread.sleep(16);
                    }
                }
                slide++;
                if (sec == slide) {
                    sec = slide + 1;
                    setTop(Language.get().get(358)); //FB
                    setTxt(Language.get().get(361));
                    setArr(8);
                    setPic(5);
                    sec3:
                    for (int i = 0; i < (tutSpeed * (tutText.length()) * 1); i++) {
                        if (skipSec) {
                            skipSec = false;
                            break sec3;
                        }
                        thread.sleep(16);
                    }
                }
                slide++;
                if (sec == slide) {
                    sec = slide + 1;
                    setTop(Language.get().get(358)); //FB
                    setTxt(Language.get().get(362));
                    setArr(8);
                    setPic(5);
                    sec3:
                    for (int i = 0; i < (tutSpeed * (tutText.length()) * 1); i++) {
                        if (skipSec) {
                            skipSec = false;
                            break sec3;
                        }
                        thread.sleep(16);
                    }
                }
                slide++;
                if (sec == slide) {
                    sec = slide + 1;
                    setTop(Language.get().get(358)); //FB
                    setTxt(Language.get().get(363));
                    setArr(8);
                    setPic(5);
                    sec3:
                    for (int i = 0; i < (tutSpeed * (tutText.length()) * 1); i++) {
                        if (skipSec) {
                            skipSec = false;
                            break sec3;
                        }
                        thread.sleep(16);
                    }
                }
                slide++;
                if (sec == slide) {
                    sec = slide + 1;
                    setTop(Language.get().get(358)); //FB
                    setTxt(Language.get().get(336));
                    setArr(8);
                    setPic(4);
                    sec3:
                    for (int i = 0; i < (tutSpeed * (tutText.length()) * 1); i++) {
                        if (skipSec) {
                            skipSec = false;
                            break sec3;
                        }
                        thread.sleep(16);
                    }
                }
                slide++;
                if (sec == slide) {
                    sec = slide + 1;
                    setTop(Language.get().get(357)); //AB
                    setTxt(Language.get().get(337));
                    setPic(4);
                    setArr(3);
                    sec3:
                    for (int i = 0; i < (tutSpeed * (tutText.length()) * 1); i++) {
                        if (skipSec) {
                            skipSec = false;
                            break sec3;
                        }
                        thread.sleep(16);
                    }
                }
                slide++;
                if (sec == slide) {
                    sec = slide + 1;
                    setTop(Language.get().get(357)); //AB
                    setTxt(Language.get().get(338));
                    setPic(4);
                    setArr(3);
                    sec3:
                    for (int i = 0; i < (tutSpeed * (tutText.length()) * 1); i++) {
                        if (skipSec) {
                            skipSec = false;
                            break sec3;
                        }
                        thread.sleep(16);
                    }
                }
                slide++;
                if (sec == slide) {
                    sec = slide + 1;
                    setTop(Language.get().get(357)); //AB
                    setTxt(Language.get().get(339));
                    setPic(4);
                    setArr(3);
                    sec3:
                    for (int i = 0; i < (tutSpeed * (tutText.length()) * 1); i++) {
                        if (skipSec) {
                            skipSec = false;
                            break sec3;
                        }
                        thread.sleep(16);
                    }
                }
                slide++;
                if (sec == slide) {
                    sec = slide + 1;
                    setTop(Language.get().get(357)); //AB
                    setTxt(Language.get().get(340));
                    setPic(4);
                    setArr(4);
                    sec3:
                    for (int i = 0; i < (tutSpeed * (tutText.length()) * 1); i++) {
                        if (skipSec) {
                            skipSec = false;
                            break sec3;
                        }
                        thread.sleep(16);
                    }
                }
                slide++;
                if (sec == slide) {
                    sec = slide + 1;
                    setTop(Language.get().get(357)); //AB
                    setTxt(Language.get().get(341));
                    setPic(0);
                    setArr(4);
                    sec3:
                    for (int i = 0; i < (tutSpeed * (tutText.length()) * 1); i++) {
                        if (skipSec) {
                            skipSec = false;
                            break sec3;
                        }
                        thread.sleep(16);
                    }
                }
                slide++;
                if (sec == slide) {
                    sec = slide + 1;
                    setTop(Language.get().get(359)); //MoveSel
                    setTxt(Language.get().get(346));
                    setPic(0);
                    setArr(0);
                    sec3:
                    for (int i = 0; i < (tutSpeed * (tutText.length()) * 1); i++) {
                        if (skipSec) {
                            skipSec = false;
                            break sec3;
                        }
                        thread.sleep(16);
                    }
                }
                slide++;
                if (sec == slide) {
                    sec = slide + 1;
                    setTop(Language.get().get(359)); //MoveSel
                    setTxt(Language.get().get(347));
                    setPic(4);
                    setArr(7);
                    sec3:
                    for (int i = 0; i < (tutSpeed * (tutText.length()) * 1); i++) {
                        if (skipSec) {
                            skipSec = false;
                            break sec3;
                        }
                        thread.sleep(16);
                    }
                }
                slide++;
                if (sec == slide) {
                    sec = slide + 1;
                    setTop(Language.get().get(359)); //MoveSel
                    setTxt(Language.get().get(348));
                    setPic(1);
                    setArr(7);
                    sec3:
                    for (int i = 0; i < (tutSpeed * (tutText.length()) * 1); i++) {
                        if (skipSec) {
                            skipSec = false;
                            break sec3;
                        }
                        thread.sleep(16);
                    }
                }
                slide++;
                if (sec == slide) {
                    sec = slide + 1;
                    setTop(Language.get().get(359)); //MoveSel
                    setTxt(Language.get().get(349));
                    setPic(2);
                    setArr(7);
                    sec3:
                    for (int i = 0; i < (tutSpeed * (tutText.length()) * 1); i++) {
                        if (skipSec) {
                            skipSec = false;
                            break sec3;
                        }
                        thread.sleep(16);
                    }
                }
                slide++;
                if (sec == slide) {
                    sec = slide + 1;
                    setTop(Language.get().get(359)); //MoveSel
                    setTxt(Language.get().get(350));
                    setPic(3);
                    setArr(0);
                    sec3:
                    for (int i = 0; i < (tutSpeed * (tutText.length()) * 1); i++) {
                        if (skipSec) {
                            skipSec = false;
                            break sec3;
                        }
                        thread.sleep(16);
                    }
                }
                slide++;
                if (sec == slide) {
                    sec = slide + 1;
                    setTop(Language.get().get(359)); //MoveSel
                    setTxt(Language.get().get(367));
                    setPic(3);
                    setArr(0);
                    sec3:
                    for (int i = 0; i < (tutSpeed * (tutText.length()) * 1); i++) {
                        if (skipSec) {
                            skipSec = false;
                            break sec3;
                        }
                        thread.sleep(16);
                    }
                }
                slide++;
                if (sec == slide) {
                    sec = slide + 1;
                    setTop(Language.get().get(359)); //MoveSel
                    setTxt(Language.get().get(351));
                    setArr(0);
                    setPic(3);
                    sec3:
                    for (int i = 0; i < (tutSpeed * (tutText.length()) * 1); i++) {
                        if (skipSec) {
                            skipSec = false;
                            break sec3;
                        }
                        thread.sleep(16);
                    }
                }
                slide++;
                if (sec == slide) {
                    setTxt(Language.get().get(393));
                    setPic(0);
                    setArr(0);
                    sec3:
                    for (int i = 0; i < (16 * 30 * 1); i++) {
                        if (skipSec) {
                            skipSec = false;
                            break sec3;
                        }
                        thread.sleep(16);
                    }
                    //last slide should not inc
                }

            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    public Font getMyFont(float size) {
        try {
            return Font.loadFont(getClass().getResourceAsStream("font/Sawasdee.ttf"), size);
        } catch (Exception re) {
            return new javafx.scene.text.Font("Sans", size);
        }
    }

    public void onBackCancel() {
        bgSound.stop();
        thread.stop();
        RenderMainMenu.get().onEnterMode();
        RenderMainMenu.get().setOverlay(Overlay.PRIMARY_MENU);
    }

    public void onAccept() {
        onBackCancel();
    }

    public void keyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case W:
            case UP:
                onUp();
                break;
            case S:
            case DOWN:
                onDown();
                break;
            case A:
            case LEFT:
                onLeft();
                break;
            case D:
            case RIGHT:
                onRight();
                break;
            case ENTER:
            case SPACE:
                onAccept();
                break;
            case DELETE:
            case BACK_SPACE:
                onBackCancel();
                break;
            case DIGIT1:
                sktpToTut(0);
                break;
            case DIGIT2:
                sktpToTut(3);
                break;
            case DIGIT3:
                sktpToTut(11);
                break;
            case DIGIT4:
                sktpToTut(20);
                break;
            case DIGIT5:
                sktpToTut(27);
                break;
            case DIGIT6:
                sktpToTut(32);
                break;
        }
    }

    private void sktpToTut(int n) {
        skipToSection(n - 1);
    }

    public void onUp() {
        this.onLeft();
    }

    public void onDown() {
        this.onRight();
    }
}
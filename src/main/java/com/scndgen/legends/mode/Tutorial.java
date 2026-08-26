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
package com.scndgen.legends.mode;

import com.scndgen.legends.Language;
import com.scndgen.legends.ScndGenLegends;
import com.scndgen.legends.UiConstants;
import com.scndgen.legends.Utils;
import com.scndgen.legends.constants.AudioConstants;
import com.scndgen.legends.enums.AudioType;
import com.scndgen.legends.enums.MainMenuOverlay;
import com.scndgen.legends.render.RenderMainMenu;
import io.github.subiyacryolite.enginev2.Accumulator;
import io.github.subiyacryolite.enginev2.AssetLoader;
import io.github.subiyacryolite.enginev2.Audio;
import io.github.subiyacryolite.enginev2.DrawContext;
import io.github.subiyacryolite.enginev2.NvgImage;
import io.github.subiyacryolite.enginev2.Rgba;

import static org.lwjgl.glfw.GLFW.*;


/**
 * Scripted tutorial overlay driven by {@link Accumulator} at 60 Hz (former Thread + sleep(16)).
 *
 * @author ndana
 */
public class Tutorial {

    private record Section(int topLangId, int textLangId, int pic, int arr, boolean fixedFrames, int frameCount) {
        Section(int topLangId, int textLangId, int pic, int arr) {
            this(topLangId, textLangId, pic, arr, false, 0);
        }
    }

    private static final Section[] SECTIONS = {
            new Section(356, 320, 0, 0),
            new Section(356, 321, 0, 0),
            new Section(360, 322, 0, 0),
            new Section(360, 344, 0, 6),
            new Section(360, 345, 0, 6),
            new Section(360, 323, 0, 1),
            new Section(360, 324, 0, 1),
            new Section(360, 325, 0, 1),
            new Section(360, 326, 0, 1),
            new Section(360, 327, 0, 2),
            new Section(355, 328, 0, 5),
            new Section(355, 329, 0, 5),
            new Section(355, 330, 0, 5),
            new Section(355, 331, 0, 5),
            new Section(355, 332, 0, 5),
            new Section(355, 333, 0, 5),
            new Section(355, 334, 0, 5),
            new Section(355, 335, 0, 5),
            new Section(355, 336, 0, 5),
            new Section(358, 352, 4, 8),
            new Section(358, 353, 4, 8),
            new Section(358, 354, 5, 8),
            new Section(358, 361, 5, 8),
            new Section(358, 362, 5, 8),
            new Section(358, 363, 5, 8),
            new Section(358, 336, 4, 8),
            new Section(357, 337, 4, 3),
            new Section(357, 338, 4, 3),
            new Section(357, 339, 4, 3),
            new Section(357, 340, 4, 4),
            new Section(357, 341, 0, 4),
            new Section(359, 346, 0, 0),
            new Section(359, 347, 4, 7),
            new Section(359, 348, 1, 7),
            new Section(359, 349, 2, 7),
            new Section(359, 350, 3, 0),
            new Section(359, 367, 3, 0),
            new Section(359, 351, 3, 0),
            new Section(-1, 393, 0, 0, true, 16 * 30), // last: no top change, no sec increment
    };

    private NvgImage[] slides, arrows;
    private NvgImage forward, back;
    private boolean skipSec;
    private boolean running;
    private boolean needSetup;
    private final long tutSpeed;
    private int cord, sec, pixLoc, arrowLoc;
    private int remainingFrames;
    private String tutText, topText;
    private float opacityTxt, picOpac, arrowOpac;
    private final Audio bgSound, backSound;
    private final Accumulator waitAccum = Accumulator.atFrequency(60);

    public Tutorial() {
        backSound = new Audio(AudioConstants.soundBack(), AudioType.SOUND, false);
        bgSound = new Audio(AudioConstants.tutorialSound(), AudioType.MUSIC, true);
        pixLoc = 0;
        sec = 0;
        opacityTxt = 1.0f;
        picOpac = 1.0f;
        arrowOpac = 1.0f;
        tutSpeed = 8;
        cord = 360;
        tutText = "TUTORIAL";
    }

    private void ensureImagesLoaded() {
        if (slides != null) {
            return;
        }
        AssetLoader assets = ScndGenLegends.get().loader();
        slides = new NvgImage[6];
        for (int u = 0; u < slides.length; u++) {
            slides[u] = assets.loadImage("images/tutorial/" + u + ".png");
        }
        arrows = new NvgImage[9];
        for (int u = 0; u < arrows.length; u++) {
            arrows[u] = assets.loadImage("images/tutorial/a" + u + ".png");
        }
        forward = assets.loadImage("images/tutorial/list_item_arrow_r.png");
        back = assets.loadImage("images/tutorial/list_item_arrow_l.png");
    }

    public void freeImages() {
        AssetLoader assets = ScndGenLegends.get().loader();
        if (assets == null) {
            slides = null;
            arrows = null;
            forward = null;
            back = null;
            return;
        }
        assets.free(slides);
        assets.free(arrows);
        assets.free(forward, back);
        slides = null;
        arrows = null;
        forward = null;
        back = null;
    }

    public void beginTutorial() {
        RenderMainMenu.get().onLeaveMode();
        ensureImagesLoaded();
        running = true;
        skipSec = false;
        sec = 0;
        remainingFrames = 0;
        needSetup = true;
        waitAccum.setFrequency(60);
        waitAccum.reset();
        bgSound.play();
    }

    public void tick(double deltaSeconds) {
        if (!running) {
            return;
        }
        waitAccum.advance(deltaSeconds);
        while (waitAccum.consume()) {
            stepFrame();
        }
    }

    private void stepFrame() {
        if (skipSec) {
            skipSec = false;
            remainingFrames = 0;
            needSetup = true;
        }
        if (remainingFrames > 0) {
            remainingFrames--;
            if (remainingFrames == 0) {
                needSetup = true;
            }
            return;
        }
        if (needSetup) {
            setupSection(sec);
            needSetup = false;
        }
    }

    private void setupSection(int index) {
        if (index < 0 || index >= SECTIONS.length) {
            return;
        }
        Section section = SECTIONS[index];
        if (section.topLangId >= 0) {
            setTop(Language.get().get(section.topLangId));
        }
        setTxt(Language.get().get(section.textLangId));
        setPic(section.pic);
        setArr(section.arr);
        if (section.fixedFrames) {
            remainingFrames = section.frameCount;
            // last slide should not increment sec — re-runs when frames expire
        } else {
            remainingFrames = (int) (tutSpeed * tutText.length());
            sec = index + 1;
        }
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

    public void draw(DrawContext draw) {
        draw.setFill(Rgba.BLACK);
        draw.setFont("menu", UiConstants.NORMAL_TXT_SIZE);
        draw.fillRect(0, 0, 1024, 1024);

        if (picOpac < 0.98f) {
            picOpac = picOpac + 0.02f;
        }
        draw.setGlobalAlpha(picOpac);
        draw.drawImage(slides[pixLoc], 0, 0);
        draw.setGlobalAlpha(1.0f);

        if (arrowOpac < 0.98f) {
            arrowOpac = arrowOpac + 0.02f;
        }
        draw.setGlobalAlpha(arrowOpac);
        draw.drawImage(arrows[arrowLoc], 0, 0);
        draw.setGlobalAlpha(1.0f);

        draw.setGlobalAlpha(0.5f);
        draw.setFill(Rgba.BLACK);
        draw.fillRoundRect(0, 216, 852, 48, 48);

        draw.setGlobalAlpha(1.0f);
        draw.setFill(Rgba.WHITE);

        draw.drawImage(back, 10, 224);
        draw.drawImage(forward, 810, 224);

        if (opacityTxt < 0.98f) {
            opacityTxt = opacityTxt + 0.02f;
        }
        draw.setGlobalAlpha(opacityTxt);
        draw.fillText(tutText, (852 - Utils.computeStringWidth(tutText, draw)) / 2, 233);
        draw.setGlobalAlpha(1.0f);

        var topLine = ":: " + topText + " - " + Language.get().get(365) + " " + sec + " ::";
        draw.fillText(topLine, (852 - Utils.computeStringWidth(topLine, draw)) / 2, 253);

        draw.fillText(Language.get().get(366) + ":", 10, cord);
        draw.fillText("1 - " + Language.get().get(356), 20, (cord + (1 * 14)));
        draw.fillText("2 - " + Language.get().get(360), 20, (cord + (2 * 14)));
        draw.fillText("3 - " + Language.get().get(355), 20, (cord + (3 * 14)));
        draw.fillText("4 - " + Language.get().get(358), 20, (cord + (4 * 14)));
        draw.fillText("5 - " + Language.get().get(357), 20, (cord + (5 * 14)));
        draw.fillText("6 - " + Language.get().get(359), 20, (cord + (6 * 14)));
        draw.fillText(Language.get().get(343), 20, (cord + (7 * 14)));

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
        Audio backSound = new Audio(AudioConstants.soundBack(), AudioType.SOUND, false);
        backSound.play();
    }

    private void playForwardSound() {
        Audio nextSound = new Audio(AudioConstants.soundNext(), AudioType.SOUND, false);
        nextSound.play();
    }

    private void setTxt(String p) {
        tutText = p;
        opacityTxt = 0.0f;
    }

    private void setTop(String p) {
        topText = p;
    }

    public void onBackCancel() {
        running = false;
        bgSound.stop();
        freeImages();
        RenderMainMenu.get().onEnterMode();
        RenderMainMenu.get().setMainMenuOverlay(MainMenuOverlay.PRIMARY_MENU);
    }

    public void onAccept() {
        onBackCancel();
    }

    public void keyPressed(int glfwKey) {
        switch (glfwKey) {
            case GLFW_KEY_W, GLFW_KEY_UP -> onUp();
            case GLFW_KEY_S, GLFW_KEY_DOWN -> onDown();
            case GLFW_KEY_A, GLFW_KEY_LEFT -> onLeft();
            case GLFW_KEY_D, GLFW_KEY_RIGHT -> onRight();
            case GLFW_KEY_ENTER, GLFW_KEY_SPACE -> onAccept();
            case GLFW_KEY_DELETE, GLFW_KEY_BACKSPACE -> onBackCancel();
            case GLFW_KEY_1 -> sktpToTut(0);
            case GLFW_KEY_2 -> sktpToTut(3);
            case GLFW_KEY_3 -> sktpToTut(11);
            case GLFW_KEY_4 -> sktpToTut(20);
            case GLFW_KEY_5 -> sktpToTut(27);
            case GLFW_KEY_6 -> sktpToTut(32);
            default -> {
            }
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

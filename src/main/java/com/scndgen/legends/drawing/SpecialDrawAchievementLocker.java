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
package com.scndgen.legends.drawing;

import com.scndgen.legends.Achievements;
import com.scndgen.legends.LoginScreen;
import com.scndgen.legends.controller.StoryMode;
import com.scndgen.legends.render.RenderGameplay;
import com.scndgen.legends.Language;
import io.github.subiyacryolite.enginev1.JenesisImageLoader;

import java.awt.*;
import java.awt.image.ImageObserver;

/**
 * Draws Achievements
 *
 * @author ndana
 */
public class SpecialDrawAchievementLocker {

    private int spacer = 14;
    private Font font2 = LoginScreen.getInstance().getMyFont(spacer - 1);
    private Font font1 = LoginScreen.getInstance().getMyFont(spacer + 2);
    private String[] style = {"Newbie", "Cool!", "Awesome!!", "EPIC!!!"};
    private int offset = 10, offset2 = 350, offset2x = 40, achPic = 40, achPicSpacer = 60, scroller = 0;
    private String stat1, stat2, stat3,
            stat4, stat5, stat6, stat7, stat13, stat15, stat16, text2 = "", stat17;
    private int perc = 0;
    private Image no;
    private Image[] achCap;
    private JenesisImageLoader pix;
    private float gWin, gLoss, denom, progression;
    private boolean[] activated;
    private float total = 0.0f, triggeredCount = 0.0f;
    private Achievements ach;

    public SpecialDrawAchievementLocker() {
        pix = new JenesisImageLoader();
        loadPix();
        refreshStats();
        ach = LoginScreen.getInstance().getAch();
    }

    public float getStoryProgression() {
        return LoginScreen.getInstance().stage / (float) StoryMode.getInstance().max;
    }

    public int getAchUnlockedPerc() {
        return perc;
    }

    public int getGameCompletion() {
        return ((int) Math.round(getStoryProgression() * 100) + getAchUnlockedPerc()) / 2;
    }

    /**
     * Draw user statistics
     *
     * @param screen - Graphics2D context
     * @param obs    - image observer
     */
    public void drawStats(Graphics2D screen, ImageObserver obs) {
        try {
            denom = LoginScreen.getInstance().win + LoginScreen.getInstance().loss;
            gWin = 200 * (LoginScreen.getInstance().win / denom);
            gLoss = 200 * (LoginScreen.getInstance().loss / denom);
            progression = 200 * (getStoryProgression());
            //System.out.println("Dude "+(LoginScreen.getInstance().stage/(float)StoryMenu.max));
        } catch (Exception e) {
            gWin = 0;
            gLoss = 0;
        }

        //50% opacity
        screen.setComposite(makeComposite(4 * 0.1F));
        screen.setColor(Color.black);
        screen.fillRect(0, 0, 852, 480); //54+lines x 14

        //back to full opacity
        screen.setComposite(makeComposite(10 * 0.1F));

        if (LoginScreen.getInstance().isConnected()) {
            //-----player account type
            stat1 = Language.getInstance().getLine(118) + ": " + shortVer(LoginScreen.getInstance().strUser);
            stat2 = Language.getInstance().getLine(119) + ": " + shortVer(LoginScreen.getInstance().strPoint);
            stat3 = Language.getInstance().getLine(120) + ": " + LoginScreen.getInstance().timeCal(LoginScreen.getInstance().strPlayTime);
            stat4 = Language.getInstance().getLine(121) + ": " + LoginScreen.getInstance().getUnlockedAch();
            stat5 = Language.getInstance().getLine(122) + ": " + LoginScreen.getInstance().getATriggeredAchiev() + " time(s)";
            stat6 = Language.getInstance().getLine(123) + ": " + LoginScreen.getInstance().matchCountStr;

            try {
                stat7 = Language.getInstance().getLine(124) + ": " + Integer.parseInt(LoginScreen.getInstance().strPoint) / Integer.parseInt(LoginScreen.getInstance().matchCountStr);
            } catch (ArithmeticException ae) {
                stat7 = Language.getInstance().getLine(124) + ": 0";
            }

            stat15 = Language.getInstance().getLine(125) + ": " + LoginScreen.getInstance().win;
            stat16 = Language.getInstance().getLine(126) + ": " + LoginScreen.getInstance().loss;

            stat13 = Language.getInstance().getLine(127) + ": " + style[LoginScreen.getInstance().userAwesomeness()];
            stat17 = Language.getInstance().getLine(128) + ": " + RenderGameplay.getInstance().charNames[LoginScreen.getInstance().mostPopularChar()] + " " + LoginScreen.getInstance().mostPopularCharPercentage() + " %";
        }

        screen.setColor(Color.WHITE);
        screen.setFont(font2);
        screen.drawString(stat1, offset, 48 - 3);
        screen.drawString(stat2, offset, (48 - 3) + (spacer * 1));
        screen.drawString(stat3, offset, (48 - 3) + (spacer * 2));
        screen.drawString(stat4, offset, (48 - 3) + (spacer * 3));
        screen.drawString(stat5, offset, (48 - 3) + (spacer * 4));
        screen.drawString(stat6, offset, (48 - 3) + (spacer * 5));
        screen.drawString(stat7, offset, (48 - 3) + (spacer * 6));

        screen.drawString(stat15, offset + 400, (48 - 3) + (spacer * 1));


        //win
        screen.fillRect(offset + 400, 35, Integer.parseInt("" + Math.round(gWin)), spacer);
        //loss
        screen.setColor(Color.RED);
        screen.drawString(stat16, offset + 500, (48 - 3) + (spacer * 1));
        screen.fillRect(610 - Integer.parseInt("" + Math.round(gLoss)), 35, Integer.parseInt("" + Math.round(gLoss)), spacer);

        //story progress
        screen.setColor(Color.WHITE);

        screen.drawString(Language.getInstance().getLine(129) + " :", offset + 400, (48 - 3) + (spacer * 3));
        screen.drawString(" " + Math.round(100 * (getStoryProgression())) + " %", offset + 500, (48 - 3) + (spacer * 3));
        screen.drawString(Language.getInstance().getLine(130) + ": " + getGameCompletion() + " %", offset + 400, (48 - 3) + (spacer * 6));

        screen.fillRect(offset + 400, (48 - 3) + (spacer * 3) + 2, Integer.parseInt("" + Math.round(progression)), spacer);

        screen.fillRect(offset + 400, (48 - 3) + (spacer * 6) + 2, getGameCompletion() * 2, spacer);

        screen.setColor(Color.white);

        //last ach spacer + 2
        screen.drawString(stat13, offset, (48 - 3) + (spacer * 8));
        screen.drawString(stat17, offset, (48 - 3) + (spacer * 9));

        screen.drawString(Language.getInstance().getLine(131) + " >>>", offset, 430);
    }

    /**
     * Draw user Achievements
     *
     * @param screen - Graphics2D context
     * @param obs    - image observer
     */
    public void drawAch(Graphics2D screen, ImageObserver obs) {
        //BG
        screen.setColor(Color.black);
        screen.setComposite(makeComposite(0.75f));
        screen.fillRect(0, 0, 852, 480);
        screen.setComposite(makeComposite(1.0f));
        screen.setColor(Color.white);

        screen.drawString(triggeredCount + " " + Language.getInstance().getLine(121), 530, 100);
        screen.drawString(getAchUnlockedPerc() + " % " + Language.getInstance().getLine(132), 530, 114);
        screen.drawString(Language.getInstance().getLine(130) + " " + getGameCompletion() + " %", 530, 128);
        screen.drawString(Language.getInstance().getLine(131) + " >>>", 530, 470);

        //even
        for (int u = 0; u < LoginScreen.getInstance().ach.length; u++) {
            if (activated[u]) {
                screen.drawImage(achCap[u], offset2x, scroller + achPic + (u * achPicSpacer), obs);
                screen.setFont(font1);
                screen.drawString((u + 1) + ":: " + ach.achDesc[u] + " >>", offset2x + achPicSpacer, scroller + achPic + (u * achPicSpacer) + 14);
                screen.setFont(font2);
                screen.drawString(ach.achFull[u], offset2x + achPicSpacer, scroller + achPic + (u * achPicSpacer) + 28);
                screen.drawString(Language.getInstance().getLine(133) + " " + LoginScreen.getInstance().ach[u] + " time(s)", offset2x + achPicSpacer, scroller + achPic + (u * achPicSpacer) + 42);
            } else {
                screen.drawImage(no, offset2x, scroller + achPic + (u * achPicSpacer), obs);
                screen.setFont(font1);
                screen.drawString((u + 1) + ":: " + ach.achDesc[u], offset2x + achPicSpacer, scroller + achPic + (u * achPicSpacer) + 14);
                screen.setFont(font2);
                screen.drawString("?????????????????????", offset2x + achPicSpacer, scroller + achPic + (u * achPicSpacer) + 28);
                screen.drawString(Language.getInstance().getLine(133) + " " + LoginScreen.getInstance().ach[u] + " time(s)", offset2x + achPicSpacer, scroller + achPic + (u * achPicSpacer) + 42);
            }
        }
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

    /**
     * Load my imageLoader
     */
    private void loadPix() {
        total = (float) LoginScreen.getInstance().ach.length;
        achCap = new Image[LoginScreen.getInstance().ach.length];
        for (int u = 0; u < achCap.length; u++) {
            achCap[u] = pix.loadImageFromIcon("images/ach/" + u + ".png");
        }
        no = pix.loadImageFromIcon("images/ach/no.png");
    }

    /**
     * Scroll up
     */
    public void scrollUp() {
        if (scroller < 0) {
            scroller = scroller + 10;
        }
    }

    /**
     * Scroll up
     */
    public void scrollDown() {
        if (scroller > -((achPicSpacer) * (achCap.length / 1.5))) {
            scroller = scroller - 10;
        }
    }

    /**
     * Shortens strings
     *
     * @param thisS
     * @return
     */
    private String shortVer(String thisS) {
        if (thisS.length() > 33) {
            thisS = thisS.substring(0, 33);
        }
        return thisS;
    }

    /**
     * Shortens strings
     *
     * @param thisS
     * @return
     */
    private String shortVer2(String thisS) {
        if (thisS.length() > 33) {
            thisS = thisS.substring(33);
        } else {
            thisS = "";
        }
        return thisS;
    }

    /**
     * Refresh stats
     */
    @SuppressWarnings("static-access")
    public void refreshStats() {
        triggeredCount = 0.0f;
        activated = new boolean[LoginScreen.getInstance().ach.length];
        for (int u = 0; u < LoginScreen.getInstance().ach.length; u++) {
            if (LoginScreen.getInstance().ach[u] > 0) {
                activated[u] = true;
                triggeredCount = triggeredCount + 1.0f;
            }
        }
        perc = (int) ((triggeredCount / total) * 100);
    }
}

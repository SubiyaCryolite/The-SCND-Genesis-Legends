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

import com.scndgen.legends.Achievements;
import com.scndgen.legends.GameState;
import com.scndgen.legends.Language;
import com.scndgen.legends.LoginScreen;
import com.scndgen.legends.controller.StoryMode;
import io.github.subiyacryolite.enginev1.JenesisImageLoader;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


/**
 * Draws Achievements
 *
 * @author ndana
 */
public class OverlayAchievementLocker {

    private int spacer = 14;
    private Font font2;
    private Font font1;
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

    public OverlayAchievementLocker() {
        pix = new JenesisImageLoader();
        loadFontAndPictures();
        refreshStats();
        //ach = LoginScreen.getInstance().getAch();
    }

    public float getStoryProgression() {
        return GameState.getInstance().getLogin().getLastStoryScene() / StoryMode.getInstance().max;
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
     */
    public void drawStats(GraphicsContext screen) {
        try {
            denom = GameState.getInstance().getLogin().getWins() + GameState.getInstance().getLogin().getLosses();
            gWin = 200 * (GameState.getInstance().getLogin().getWins() / denom);
            gLoss = 200 * (GameState.getInstance().getLogin().getLosses() / denom);
            progression = 200 * getStoryProgression();
            //System.out.println("Dude "+(LoginScreen.getInstance().lastStoryScene/(float)StoryMenu.max));
        } catch (Exception e) {
            gWin = 0;
            gLoss = 0;
        }

        //50% opacity
        screen.setGlobalAlpha((4 * 0.1F));
        screen.setFill(Color.BLACK);
        screen.fillRect(0, 0, 852, 480); //54+lines x 14

        //back to full opacity
        screen.setGlobalAlpha((10 * 0.1F));

        if (LoginScreen.getInstance().isConnected()) {
            stat1 = Language.getInstance().get(118) + ": " + shortVer(GameState.getInstance().getLogin().getUserName());
            stat2 = Language.getInstance().get(119) + ": " + shortVer(GameState.getInstance().getLogin().getPoints() + "");
            stat3 = Language.getInstance().get(120) + ": " + timeCal(GameState.getInstance().getLogin().getPlayTime());
            stat4 = Language.getInstance().get(121) + ": " + LoginScreen.getInstance().getUnlockedAch();
            stat5 = Language.getInstance().get(122) + ": " + LoginScreen.getInstance().getNumberOfTimesAchivementTriggered() + " timeLimit(s)";
            stat6 = Language.getInstance().get(123) + ": " + GameState.getInstance().getLogin().getNumberOfMatches();
            try {
                stat7 = Language.getInstance().get(124) + ": " + GameState.getInstance().getLogin().getPoints() / GameState.getInstance().getLogin().getNumberOfMatches();
            } catch (ArithmeticException ae) {
                stat7 = Language.getInstance().get(124) + ": 0";
            }
            stat15 = Language.getInstance().get(125) + ": " + GameState.getInstance().getLogin().getWins();
            stat16 = Language.getInstance().get(126) + ": " + GameState.getInstance().getLogin().getLosses();
            stat13 = Language.getInstance().get(127) + ": " + style[LoginScreen.getInstance().userAwesomeness()];
            stat17 = Language.getInstance().get(128) + ": " + RenderGameplay.getInstance().getCharNames()[LoginScreen.getInstance().mostPopularChar()] + " " + LoginScreen.getInstance().mostPopularCharPercentage() + " %";
        }

        screen.setFill(Color.WHITE);
        screen.setFont(font2);
        screen.fillText(stat1, offset, 48 - 3);
        screen.fillText(stat2, offset, (48 - 3) + (spacer * 1));
        screen.fillText(stat3, offset, (48 - 3) + (spacer * 2));
        screen.fillText(stat4, offset, (48 - 3) + (spacer * 3));
        screen.fillText(stat5, offset, (48 - 3) + (spacer * 4));
        screen.fillText(stat6, offset, (48 - 3) + (spacer * 5));
        screen.fillText(stat7, offset, (48 - 3) + (spacer * 6));

        screen.fillText(stat15, offset + 400, (48 - 3) + (spacer * 1));


        //wins
        screen.fillRect(offset + 400, 35, Integer.parseInt("" + Math.round(gWin)), spacer);
        //losses
        screen.setFill(Color.RED);
        screen.fillText(stat16, offset + 500, (48 - 3) + (spacer * 1));
        screen.fillRect(610 - Integer.parseInt("" + Math.round(gLoss)), 35, Integer.parseInt("" + Math.round(gLoss)), spacer);

        //story progress
        screen.setFill(Color.WHITE);

        screen.fillText(Language.getInstance().get(129) + " :", offset + 400, (48 - 3) + (spacer * 3));
        screen.fillText(" " + Math.round(100 * (getStoryProgression())) + " %", offset + 500, (48 - 3) + (spacer * 3));
        screen.fillText(Language.getInstance().get(130) + ": " + getGameCompletion() + " %", offset + 400, (48 - 3) + (spacer * 6));

        screen.fillRect(offset + 400, (48 - 3) + (spacer * 3) + 2, Integer.parseInt("" + Math.round(progression)), spacer);

        screen.fillRect(offset + 400, (48 - 3) + (spacer * 6) + 2, getGameCompletion() * 2, spacer);

        screen.setFill(Color.WHITE);

        //last ACH spacer + 2
        screen.fillText(stat13, offset, (48 - 3) + (spacer * 8));
        screen.fillText(stat17, offset, (48 - 3) + (spacer * 9));

        screen.fillText(Language.getInstance().get(131) + " >>>", offset, 430);
    }

    /**
     * Draw user Achievements
     *
     * @param gc - Graphics2D context
     */
    public void drawAch(GraphicsContext gc) {
        //BG
        gc.setFill(Color.BLACK);
        gc.setGlobalAlpha((0.75f));
        gc.fillRect(0, 0, 852, 480);
        gc.setGlobalAlpha((1.0f));
        gc.setFill(Color.WHITE);

        gc.fillText(triggeredCount + " " + Language.getInstance().get(121), 530, 100);
        gc.fillText(getAchUnlockedPerc() + " % " + Language.getInstance().get(132), 530, 114);
        gc.fillText(Language.getInstance().get(130) + " " + getGameCompletion() + " %", 530, 128);
        gc.fillText(Language.getInstance().get(131) + " >>>", 530, 470);

        //even
        for (int u = 0; u < LoginScreen.getInstance().ach.length; u++) {
            if (activated[u]) {
                gc.drawImage(achCap[u], offset2x, scroller + achPic + (u * achPicSpacer));
                gc.setFont(font1);
                gc.fillText((u + 1) + ":: " + ach.achievementName[u] + " >>", offset2x + achPicSpacer, scroller + achPic + (u * achPicSpacer) + 14);
                gc.setFont(font2);
                gc.fillText(ach.achievementDescription[u], offset2x + achPicSpacer, scroller + achPic + (u * achPicSpacer) + 28);
                gc.fillText(Language.getInstance().get(133) + " " + LoginScreen.getInstance().ach[u] + " timeLimit(s)", offset2x + achPicSpacer, scroller + achPic + (u * achPicSpacer) + 42);
            } else {
                gc.drawImage(no, offset2x, scroller + achPic + (u * achPicSpacer));
                gc.setFont(font1);
                gc.fillText((u + 1) + ":: " + ach.achievementName[u], offset2x + achPicSpacer, scroller + achPic + (u * achPicSpacer) + 14);
                gc.setFont(font2);
                gc.fillText("?????????????????????", offset2x + achPicSpacer, scroller + achPic + (u * achPicSpacer) + 28);
                gc.fillText(Language.getInstance().get(133) + " " + LoginScreen.getInstance().ach[u] + " timeLimit(s)", offset2x + achPicSpacer, scroller + achPic + (u * achPicSpacer) + 42);
            }
        }
    }


    /**
     * Load my imageLoader
     */
    private void loadFontAndPictures() {
        font2 = getMyFont(spacer - 1);
        font1 = getMyFont(spacer + 2);
        //total = (float) LoginScreen.getInstance().ach.length;
        achCap = new Image[0];//[LoginScreen.getInstance().ach.length];
        for (int u = 0; u < achCap.length; u++) {
            achCap[u] = pix.loadImage("images/ach/" + u + ".png");
        }
        no = pix.loadImage("images/ach/no.png");
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
     * Refresh STATS
     */
    @SuppressWarnings("static-access")
    public void refreshStats() {
//        triggeredCount = 0.0f;
//        activated = new boolean[LoginScreen.getInstance().ach.length];
//        for (int u = 0; u < LoginScreen.getInstance().ach.length; u++) {
//            if (LoginScreen.getInstance().ach[u] > 0) {
//                activated[u] = true;
//                triggeredCount = triggeredCount + 1.0f;
//            }
//        }
//        perc = (int) ((triggeredCount / total) * 100);
    }

    public Font getMyFont(float size) {
        try {
            return Font.loadFont(getClass().getResourceAsStream("font/Sawasdee.ttf"), size);
        } catch (Exception re) {
            return new javafx.scene.text.Font("Sans", size);
        }
    }

    public String timeCal(int timeInt) {
        String moi = "";
        //minutes
        if (timeInt > -1 && timeInt <= 3600) {
            int minutes = timeInt / 60;
            int seconds = timeInt - (minutes * 60);
            moi = minutes + " minutes and " + seconds + " seconds";
        }
        if (timeInt > 3600 && timeInt <= 86400) {
            int hours = timeInt / 3600;
            int minutes = (timeInt - (hours * 3600)) / 60;
            int seconds = timeInt - ((minutes * 60) + (hours * 3600));
            moi = hours + " hours, " + minutes + " mins and " + seconds + " secs";
        }
        if (timeInt > 86400 && timeInt <= 604800) {
            int days = timeInt / 86400;
            int hours = (days * 86400) / 3600;
            int minutes = (timeInt - (hours * 3600) - (days * 86400)) / 60;
            int seconds = timeInt - ((minutes * 60) + (hours * 3600) + (days * 86400));
            moi = days + " days " + hours + "hrs, " + minutes + " mins and " + seconds + " secs";
        }
        return moi;
    }
}

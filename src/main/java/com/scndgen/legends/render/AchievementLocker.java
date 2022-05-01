/**************************************************************************

 The SCND Genesis: Legends is a fighting game based on THE SCND GENESIS,
 a webcomic created by Ifunga Ndana ((([<a href="http://www.scndgen.com">http://www.scndgen.com</a>]))).

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

import com.scndgen.legends.Achievement;
import com.scndgen.legends.Language;
import com.scndgen.legends.enums.Achievements;
import com.scndgen.legends.enums.MainMenuOverlay;
import com.scndgen.legends.mode.StoryMode;
import com.scndgen.legends.state.State;
import io.github.subiyacryolite.enginev1.Loader;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


/**
 * Draws Achievements
 *
 * @author ndana
 */
public class AchievementLocker {

    private int spacer = 14;
    private Font font2;
    private Font font1;
    private String[] style = {"Newbie", "Cool!", "Awesome!!", "EPIC!!!"};
    private int offset = 10, offset2 = 350, offset2x = 40, achPic = 40, achPicSpacer = 60, scroller = 0;
    private String stat1, stat2, stat3,
            stat4, stat5, stat6, stat7, stat13, stat15, stat16, text2 = "", stat17;
    private int percentageOfUnlockedAchievements = 0;
    private Image no;
    private Image[] achCap;
    private boolean[] isAchievementActivated;
    private Loader pix;
    private float gWin, gLoss, denom, progression;
    private float numberOfTriggeredAchievements = 0.0f;

    public AchievementLocker() {
        pix = new Loader();
        loadFontAndPictures();
        refreshStats();
    }

    public float getStoryProgression() {
        int highest = State.get().getLogin().getHighestStoryScene();
        float progress = highest / (float) StoryMode.get().totalScenes;
        return progress;
    }

    public int getAchUnlockedPerc() {
        return percentageOfUnlockedAchievements;
    }

    public int getGameCompletion() {
        return (Math.round(getStoryProgression() * 100) + getAchUnlockedPerc()) / 2;
    }

    /**
     * Draw user statistics
     *
     * @param screen - Graphics2D context
     */
    public void drawStats(GraphicsContext screen, double w, double h) {
        try {
            denom = State.get().getLogin().getWins() + State.get().getLogin().getLosses();
            gWin = 200 * (State.get().getLogin().getWins() / denom);
            gLoss = 200 * (State.get().getLogin().getLosses() / denom);
            progression = 200 * getStoryProgression();
        } catch (Exception e) {
            gWin = 0;
            gLoss = 0;
        }

        //50% opacity
        screen.setGlobalAlpha((4 * 0.1F));
        screen.setFill(Color.BLACK);
        screen.fillRect(0, 0, w, h); //54+lines x 14

        //onBackCancel to full opacity
        screen.setGlobalAlpha((10 * 0.1F));
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

        //playStory progress
        screen.setFill(Color.WHITE);

        screen.fillText(Language.get().get(129) + " :", offset + 400, (48 - 3) + (spacer * 3));
        screen.fillText(" " + Math.round(100 * (getStoryProgression())) + " %", offset + 500, (48 - 3) + (spacer * 3));
        screen.fillText(Language.get().get(130) + ": " + getGameCompletion() + " %", offset + 400, (48 - 3) + (spacer * 6));
        screen.fillRect(offset + 400, (48 - 3) + (spacer * 3) + 2, Integer.parseInt("" + Math.round(progression)), spacer);
        screen.fillRect(offset + 400, (48 - 3) + (spacer * 6) + 2, getGameCompletion() * 2, spacer);

        screen.setFill(Color.WHITE);
        //last ACH spacer + 2
        screen.fillText(stat13, offset, (48 - 3) + (spacer * 8));
        screen.fillText(stat17, offset, (48 - 3) + (spacer * 9));

        screen.fillText(Language.get().get(131) + " >>>", offset, 430);
    }

    /**
     * Draw user Achievements
     *
     * @param gc - Graphics2D context
     */
    public void drawAch(GraphicsContext gc, double w, double h) {
        //BG
        gc.setFill(Color.BLACK);
        gc.setGlobalAlpha((0.75f));
        gc.fillRect(0, 0, w, h);
        gc.setGlobalAlpha((1.0f));
        gc.setFill(Color.WHITE);

        gc.fillText(numberOfTriggeredAchievements + " " + Language.get().get(121), 530, 100);
        gc.fillText(getAchUnlockedPerc() + " % " + Language.get().get(132), 530, 114);
        gc.fillText(Language.get().get(130) + " " + getGameCompletion() + " %", 530, 128);
        gc.fillText(Language.get().get(131) + " >>>", 530, 470);

        //even
        for (Achievements achievement : Achievements.values()) {
            if (isAchievementActivated[achievement.id()]) {
                gc.drawImage(achCap[achievement.id()], offset2x, scroller + achPic + (achievement.id() * achPicSpacer));
                gc.setFont(font1);
                gc.fillText((achievement.id() + 1) + ":: " + Achievement.get().achievementName(achievement) + " >>", offset2x + achPicSpacer, scroller + achPic + (achievement.id() * achPicSpacer) + 14);
                gc.setFont(font2);
                gc.fillText(Achievement.get().achievementDescription(achievement), offset2x + achPicSpacer, scroller + achPic + (achievement.id() * achPicSpacer) + 28);
                gc.fillText(Language.get().get(133) + " " + State.get().getLogin().getAchievementTriggers(achievement) + " time(s)", offset2x + achPicSpacer, scroller + achPic + (achievement.id() * achPicSpacer) + 42);
            } else {
                gc.drawImage(no, offset2x, scroller + achPic + (achievement.id() * achPicSpacer));
                gc.setFont(font1);
                gc.fillText((achievement.id() + 1) + ":: " + Achievement.get().achievementName(achievement), offset2x + achPicSpacer, scroller + achPic + (achievement.id() * achPicSpacer) + 14);
                gc.setFont(font2);
                gc.fillText("?????????????????????", offset2x + achPicSpacer, scroller + achPic + (achievement.id() * achPicSpacer) + 28);
                gc.fillText(Language.get().get(133) + " " + State.get().getLogin().getAchievementTriggers(achievement) + " time(s)", offset2x + achPicSpacer, scroller + achPic + (achievement.id() * achPicSpacer) + 42);
            }
        }
    }


    /**
     * Load my loader
     */
    private void loadFontAndPictures() {
        font2 = getMyFont(spacer - 1);
        font1 = getMyFont(spacer + 2);
        achCap = new Image[Achievements.values().length];
        for (int u = 0; u < achCap.length; u++) {
            achCap[u] = pix.load("images/ach/" + u + ".png");
        }
        no = pix.load("images/ach/no.png");
    }

    /**
     * Scroll up
     */
    public void onUp() {
        if (scroller < 0) {
            scroller = scroller + 10;
        }
    }

    /**
     * Scroll up
     */
    public void onDown() {
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
     * Refresh STATS
     */
    public void refreshStats() {
        numberOfTriggeredAchievements = 0.0f;
        isAchievementActivated = new boolean[Achievements.values().length];
        for (Achievements achievement : Achievements.values()) {
            if (State.get().getLogin().getAchievementTriggers(achievement) > 0) {
                isAchievementActivated[achievement.id()] = true;
                numberOfTriggeredAchievements = numberOfTriggeredAchievements + 1.0f;
            }
        }
        percentageOfUnlockedAchievements = (int) ((numberOfTriggeredAchievements / (float) isAchievementActivated.length) * 100);
        stat1 = Language.get().get(118) + ": " + shortVer(State.get().getLogin().getUserName());
        stat2 = Language.get().get(119) + ": " + shortVer(State.get().getLogin().getPoints() + "");
        stat3 = Language.get().get(120) + ": " + timeCal(State.get().getLogin().getPlayTime());
        stat4 = Language.get().get(121) + ": " + State.get().getLogin().getUnlockedAch();
        stat5 = Language.get().get(122) + ": " + State.get().getLogin().getNumberOfTimesAchivementTriggered() + " time(s)";
        stat6 = Language.get().get(123) + ": " + State.get().getLogin().getNumberOfMatches();
        try {
            stat7 = Language.get().get(124) + ": " + State.get().getLogin().getPoints() / State.get().getLogin().getNumberOfMatches();
        } catch (ArithmeticException ae) {
            stat7 = Language.get().get(124) + ": 0";
        }
        stat15 = Language.get().get(125) + ": " + State.get().getLogin().getWins();
        stat16 = Language.get().get(126) + ": " + State.get().getLogin().getLosses();
        stat13 = Language.get().get(127) + ": " + State.get().getLogin().userAwesomeness();
        stat17 = Language.get().get(128) + ": " + State.get().getLogin().mostPopularCharEnum() + " " + State.get().getLogin().mostPopularCharPercentage() + " %";
    }

    public Font getMyFont(float size) {
        try {
            return Font.loadFont(getClass().getResourceAsStream("font/Sawasdee.ttf"), size);
        } catch (Exception re) {
            return new javafx.scene.text.Font("Sans", size);
        }
    }

    public String timeCal(int timeInt) {
        if (timeInt > -1 && timeInt <= 3600) {
            int minutes = timeInt / 60;
            int seconds = timeInt - (minutes * 60);
            return minutes + " minutes and " + seconds + " seconds";
        } else if (timeInt > 3600 && timeInt <= 86400) {
            int hours = timeInt / 3600;
            int minutes = (timeInt - (hours * 3600)) / 60;
            int seconds = timeInt - ((minutes * 60) + (hours * 3600));
            return hours + " hours, " + minutes + " mins and " + seconds + " secs";
        } else {
            int days = timeInt / 86400;
            int hours = (days * 86400) / 3600;
            int minutes = (timeInt - (hours * 3600) - (days * 86400)) / 60;
            int seconds = timeInt - ((minutes * 60) + (hours * 3600) + (days * 86400));
            return days + " days " + hours + "hrs, " + minutes + " mins and " + seconds + " secs";
        }
    }

    public void onRight() {
        onDown();
    }

    public void onLeft() {
        onUp();
    }

    public void onAccept() {
        this.onBackCancel();
    }

    public void onBackCancel() {
        RenderMainMenu.get().setMainMenuOverlay(MainMenuOverlay.PRIMARY_MENU);
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
        }
    }
}

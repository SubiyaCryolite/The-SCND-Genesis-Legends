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

import com.scndgen.legends.Achievement;
import com.scndgen.legends.Language;
import com.scndgen.legends.ScndGenLegends;
import com.scndgen.legends.enums.Achievements;
import com.scndgen.legends.enums.MainMenuOverlay;
import com.scndgen.legends.mode.StoryMode;
import com.scndgen.legends.state.State;
import io.github.subiyacryolite.enginev2.AssetLoader;
import io.github.subiyacryolite.enginev2.DrawContext;
import io.github.subiyacryolite.enginev2.NvgImage;
import io.github.subiyacryolite.enginev2.Rgba;

import static org.lwjgl.glfw.GLFW.*;


/**
 * Draws Achievements
 *
 * @author ndana
 */
public class AchievementLocker {

    private int spacer = 14;
    private String[] style = {"Newbie", "Cool!", "Awesome!!", "EPIC!!!"};
    private int offset = 10, offset2 = 350, offset2x = 40, achPic = 40, achPicSpacer = 60, scroller = 0;
    private String stat1, stat2, stat3,
            stat4, stat5, stat6, stat7, stat13, stat15, stat16, text2 = "", stat17;
    private int percentageOfUnlockedAchievements = 0;
    private NvgImage no;
    private NvgImage[] achCap;
    private boolean[] isAchievementActivated;
    private float gWin, gLoss, denom, progression;
    private float numberOfTriggeredAchievements = 0.0f;

    public AchievementLocker() {
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
     */
    public void drawStats(DrawContext draw) {
        float w = 852;
        float h = 480;
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
        draw.setGlobalAlpha(0.4f);
        draw.setFill(Rgba.BLACK);
        draw.fillRect(0, 0, w, h);

        //onBackCancel to full opacity
        draw.setGlobalAlpha(1.0f);
        draw.setFill(Rgba.WHITE);
        draw.setFont("menu", spacer - 1);
        draw.fillText(stat1, offset, 48 - 3);
        draw.fillText(stat2, offset, (48 - 3) + (spacer * 1));
        draw.fillText(stat3, offset, (48 - 3) + (spacer * 2));
        draw.fillText(stat4, offset, (48 - 3) + (spacer * 3));
        draw.fillText(stat5, offset, (48 - 3) + (spacer * 4));
        draw.fillText(stat6, offset, (48 - 3) + (spacer * 5));
        draw.fillText(stat7, offset, (48 - 3) + (spacer * 6));
        draw.fillText(stat15, offset + 400, (48 - 3) + (spacer * 1));

        //wins
        draw.fillRect(offset + 400, 35, Math.round(gWin), spacer);
        //losses
        draw.setFill(1f, 0f, 0f);
        draw.fillText(stat16, offset + 500, (48 - 3) + (spacer * 1));
        draw.fillRect(610 - Math.round(gLoss), 35, Math.round(gLoss), spacer);

        //playStory progress
        draw.setFill(Rgba.WHITE);

        draw.fillText(Language.get().get(129) + " :", offset + 400, (48 - 3) + (spacer * 3));
        draw.fillText(" " + Math.round(100 * (getStoryProgression())) + " %", offset + 500, (48 - 3) + (spacer * 3));
        draw.fillText(Language.get().get(130) + ": " + getGameCompletion() + " %", offset + 400, (48 - 3) + (spacer * 6));
        draw.fillRect(offset + 400, (48 - 3) + (spacer * 3) + 2, Math.round(progression), spacer);
        draw.fillRect(offset + 400, (48 - 3) + (spacer * 6) + 2, getGameCompletion() * 2, spacer);

        draw.setFill(Rgba.WHITE);
        //last ACH spacer + 2
        draw.fillText(stat13, offset, (48 - 3) + (spacer * 8));
        draw.fillText(stat17, offset, (48 - 3) + (spacer * 9));

        draw.fillText(Language.get().get(131) + " >>>", offset, 430);
    }

    /**
     * Draw user Achievements
     */
    public void drawAch(DrawContext draw) {
        ensureImagesLoaded();
        float w = 852;
        float h = 480;
        //BG
        draw.setFill(Rgba.BLACK);
        draw.setGlobalAlpha(0.75f);
        draw.fillRect(0, 0, w, h);
        draw.setGlobalAlpha(1.0f);
        draw.setFill(Rgba.WHITE);

        draw.fillText(numberOfTriggeredAchievements + " " + Language.get().get(121), 530, 100);
        draw.fillText(getAchUnlockedPerc() + " % " + Language.get().get(132), 530, 114);
        draw.fillText(Language.get().get(130) + " " + getGameCompletion() + " %", 530, 128);
        draw.fillText(Language.get().get(131) + " >>>", 530, 470);

        //even
        for (Achievements achievement : Achievements.values()) {
            if (isAchievementActivated[achievement.id()]) {
                draw.drawImage(achCap[achievement.id()], offset2x, scroller + achPic + (achievement.id() * achPicSpacer));
                draw.setFont("menu", spacer + 2);
                draw.fillText((achievement.id() + 1) + ":: " + Achievement.get().achievementName(achievement) + " >>", offset2x + achPicSpacer, scroller + achPic + (achievement.id() * achPicSpacer) + 14);
                draw.setFont("menu", spacer - 1);
                draw.fillText(Achievement.get().achievementDescription(achievement), offset2x + achPicSpacer, scroller + achPic + (achievement.id() * achPicSpacer) + 28);
                draw.fillText(Language.get().get(133) + " " + State.get().getLogin().getAchievementTriggers(achievement) + " time(s)", offset2x + achPicSpacer, scroller + achPic + (achievement.id() * achPicSpacer) + 42);
            } else {
                draw.drawImage(no, offset2x, scroller + achPic + (achievement.id() * achPicSpacer));
                draw.setFont("menu", spacer + 2);
                draw.fillText((achievement.id() + 1) + ":: " + Achievement.get().achievementName(achievement), offset2x + achPicSpacer, scroller + achPic + (achievement.id() * achPicSpacer) + 14);
                draw.setFont("menu", spacer - 1);
                draw.fillText("?????????????????????", offset2x + achPicSpacer, scroller + achPic + (achievement.id() * achPicSpacer) + 28);
                draw.fillText(Language.get().get(133) + " " + State.get().getLogin().getAchievementTriggers(achievement) + " time(s)", offset2x + achPicSpacer, scroller + achPic + (achievement.id() * achPicSpacer) + 42);
            }
        }
    }


    /**
     * Load images
     */
    private void ensureImagesLoaded() {
        if (achCap != null) {
            return;
        }
        AssetLoader pix = ScndGenLegends.get().loader();
        achCap = new NvgImage[Achievements.values().length];
        for (int u = 0; u < achCap.length; u++) {
            achCap[u] = pix.loadImage("images/ach/" + u + ".png");
        }
        no = pix.loadImage("images/ach/no.png");
    }

    public void freeImages() {
        AssetLoader pix = ScndGenLegends.get().loader();
        if (pix != null) {
            pix.free(achCap);
            pix.free(no);
        }
        achCap = null;
        no = null;
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
        freeImages();
        RenderMainMenu.get().setMainMenuOverlay(MainMenuOverlay.PRIMARY_MENU);
    }

    public void keyPressed(int glfwKey) {
        switch (glfwKey) {
            case GLFW_KEY_W, GLFW_KEY_UP -> onUp();
            case GLFW_KEY_S, GLFW_KEY_DOWN -> onDown();
            case GLFW_KEY_A, GLFW_KEY_LEFT -> onLeft();
            case GLFW_KEY_D, GLFW_KEY_RIGHT -> onRight();
            case GLFW_KEY_ENTER, GLFW_KEY_SPACE -> onAccept();
            case GLFW_KEY_DELETE, GLFW_KEY_BACKSPACE -> onBackCancel();
            default -> {
            }
        }
    }
}

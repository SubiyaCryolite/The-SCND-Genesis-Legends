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
package com.scndgen.legends;

import com.scndgen.legends.characters.Characters;
import com.scndgen.legends.controller.StoryMode;
import com.scndgen.legends.enums.CharacterState;
import com.scndgen.legends.render.RenderGameplay;
import com.scndgen.legends.threads.GameInstance;
import io.github.subiyacryolite.enginev1.JenesisGlassPane;

import java.util.ArrayList;

/**
 * @author ndana
 */
public class Achievements {

    private static Achievements instance;
    private final ArrayList name = new ArrayList();
    private final ArrayList descriptions = new ArrayList();
    private final ArrayList categories = new ArrayList();
    private final ArrayList points = new ArrayList();
    private final LoginScreen loginScreen;
    public String[] achievementName;
    public String[] achievementDescription;
    private int[] pointsArr = {102, 198, 300};
    private String[] catType = {"COOL", "SWEET", "EPIC"};
    private boolean[] isLocked = new boolean[14];
    private int currentPoints;
    private int bonus;

    public Achievements(LoginScreen loginScreen) {
        instance = this;
        this.loginScreen = loginScreen;
        newInstance();
        achievementName = new String[]{Language.getInstance().get(61), //0
                Language.getInstance().get(62), //1
                Language.getInstance().get(63), //2
                Language.getInstance().get(64), //3
                Language.getInstance().get(65), //4
                Language.getInstance().get(66), //5
                Language.getInstance().get(67), //6
                Language.getInstance().get(68), //7
                Language.getInstance().get(69), //8
                Language.getInstance().get(70), //9
                Language.getInstance().get(71),//10
        };
        achievementDescription = new String[]{
                Language.getInstance().get(72), //0
                Language.getInstance().get(73), //1
                Language.getInstance().get(74), //2
                Language.getInstance().get(75), //3
                Language.getInstance().get(76), //4
                Language.getInstance().get(77), //5
                Language.getInstance().get(78), //6
                Language.getInstance().get(79), //7
                Language.getInstance().get(80), //8
                Language.getInstance().get(81), //9
                Language.getInstance().get(82), //10
        };
    }

    public static synchronized Achievements getInstance() {
        return instance;
    }

    /**
     * Get the amount of Achievements triggered
     *
     * @return
     */
    public int getAcievementsTriggered() {
        return name.size();
    }

    /**
     * Get achievement details
     *
     * @param index
     * @return details
     */
    public String[] getName(int index) {
        String[] results = new String[4];
        results[0] = "" + name.get(index);
        results[1] = "" + descriptions.get(index);
        results[2] = "" + categories.get(index);
        results[3] = "" + points.get(index);
        return results;
    }

    public void clearAll() {
        name.clear();
        name.trimToSize();

        descriptions.clear();
        descriptions.trimToSize();

        categories.clear();
        categories.trimToSize();

        points.clear();
        points.trimToSize();
    }

    /**
     * Scan for conditions
     */
    public void scan() {
        if (Characters.getInstance().getCharMinLife() <= 79 && RenderGameplay.getInstance().getPercent() >= 82 && isLocked[0]) {
            RenderGameplay.getInstance().setNotifiationPic(1); //categories + 1
            name.add(achievementName[0]);
            JenesisGlassPane.getInstance().primaryNotice(Language.getInstance().get(83) + ": " + achievementName[0]);
            descriptions.add(achievementDescription[0]);
            categories.add(catType[0]);
            points.add(pointsArr[0] + bonus);
            currentPoints = currentPoints + pointsArr[0] + bonus;
            GameState.getInstance().getLogin().incrementAchievement(0);
            isLocked[0] = false;
        }

        if (Characters.getInstance().getCharMinLife() <= 30 && RenderGameplay.getInstance().getPercent() >= 50 && isLocked[1]) {
            RenderGameplay.getInstance().setNotifiationPic(3); //categories + 1
            name.add(achievementName[1]);
            JenesisGlassPane.getInstance().primaryNotice(Language.getInstance().get(83) + ": " + achievementName[1]);
            descriptions.add(achievementDescription[1]);
            categories.add(catType[1]);
            points.add(pointsArr[1] + bonus);
            currentPoints = currentPoints + pointsArr[1] + bonus;
            GameState.getInstance().getLogin().incrementAchievement(1);
            isLocked[1] = false;
        }

        if (((RenderGameplay.getInstance().getPercent() - RenderGameplay.getInstance().getPercent2()) >= 50) && isLocked[4]) {
            RenderGameplay.getInstance().setNotifiationPic(3); //categories + 1
            name.add(achievementName[2]);
            JenesisGlassPane.getInstance().primaryNotice(Language.getInstance().get(83) + ": " + achievementName[2]);
            descriptions.add(achievementDescription[2]);
            categories.add(catType[2]);
            points.add(pointsArr[2] + bonus);
            currentPoints = currentPoints + pointsArr[2] + bonus;
            GameState.getInstance().getLogin().incrementAchievement(2);
            isLocked[4] = false;
            isLocked[3] = false;
            isLocked[2] = false;
        }

        if (((RenderGameplay.getInstance().getPercent() - RenderGameplay.getInstance().getPercent2()) >= 40) && isLocked[3]) {
            RenderGameplay.getInstance().setNotifiationPic(2); //categories + 1
            name.add(achievementName[3]);
            JenesisGlassPane.getInstance().primaryNotice(Language.getInstance().get(83) + ": " + achievementName[3]);
            descriptions.add(achievementDescription[3]);
            categories.add(catType[1]);
            points.add(pointsArr[1] + bonus);
            currentPoints = currentPoints + pointsArr[1] + bonus;
            GameState.getInstance().getLogin().incrementAchievement(3);
            isLocked[3] = false;
            isLocked[2] = false;
        }

        if (((RenderGameplay.getInstance().getPercent() - RenderGameplay.getInstance().getPercent2()) >= 30) && isLocked[2]) {
            RenderGameplay.getInstance().setNotifiationPic(1); //categories + 1
            name.add(achievementName[4]);
            JenesisGlassPane.getInstance().primaryNotice(Language.getInstance().get(83) + ": " + achievementName[4]);
            descriptions.add(achievementDescription[4]);
            categories.add(catType[0]);
            points.add(pointsArr[0] + bonus);
            currentPoints = currentPoints + pointsArr[0] + bonus;
            GameState.getInstance().getLogin().incrementAchievement(4);
            isLocked[2] = false;
        }

        if (RenderGameplay.getInstance().getAttackType(CharacterState.CHARACTER).equalsIgnoreCase("fury") && GameInstance.getInstance().gameOver && RenderGameplay.getInstance().hasWon() && isLocked[5]) {
            RenderGameplay.getInstance().setNotifiationPic(3); //categories + 1
            name.add(achievementName[5]);
            JenesisGlassPane.getInstance().primaryNotice(Language.getInstance().get(83) + ": " + achievementName[5]);
            descriptions.add(achievementDescription[5]);
            categories.add(catType[0]);
            points.add(pointsArr[0] + bonus);
            currentPoints = currentPoints + pointsArr[0] + bonus;
            GameState.getInstance().getLogin().incrementAchievement(5);
            isLocked[5] = false;
        }

        if (RenderGameplay.getInstance().hasWon() && GameInstance.getInstance().gameOver) {
            RenderGameplay.getInstance().setNotifiationPic(2); //categories + 1
            name.add(achievementName[6]);
            JenesisGlassPane.getInstance().primaryNotice(Language.getInstance().get(83) + ": " + achievementName[6]);
            descriptions.add(achievementDescription[6]);
            categories.add(catType[0]);
            points.add(pointsArr[0] + bonus);
            GameState.getInstance().getLogin().incrementAchievement(6);
            currentPoints = currentPoints + pointsArr[0] + bonus;
        }

        if (RenderGameplay.getInstance().getAttackType(CharacterState.OPPONENT).equalsIgnoreCase("fury") && RenderGameplay.getInstance().hasWon() && GameInstance.getInstance().gameOver && isLocked[6]) {
            RenderGameplay.getInstance().setNotifiationPic(3); //categories + 1
            name.add(achievementName[7]);
            JenesisGlassPane.getInstance().primaryNotice(Language.getInstance().get(83) + ": " + achievementName[7]);
            descriptions.add(achievementDescription[7]);
            categories.add(catType[1]);
            points.add(pointsArr[1] + bonus);
            currentPoints = currentPoints + pointsArr[1] + bonus;
            GameState.getInstance().getLogin().incrementAchievement(7);
            isLocked[6] = false;
        }

        if (RenderGameplay.getInstance().hasWon() && GameInstance.getInstance().gameOver && (RenderGameplay.getInstance().getPercent() - RenderGameplay.getInstance().getPercent2() <= 30) && isLocked[7]) {
            RenderGameplay.getInstance().setNotifiationPic(2); //categories + 1
            name.add(achievementName[8]);
            JenesisGlassPane.getInstance().primaryNotice(Language.getInstance().get(83) + ": " + achievementName[8]);
            descriptions.add(achievementDescription[8]);
            categories.add(catType[0]);
            points.add(pointsArr[0] + bonus);
            currentPoints = currentPoints + pointsArr[0] + bonus;
            GameState.getInstance().getLogin().incrementAchievement(8);
            isLocked[7] = false;
        }

        if (RenderGameplay.getInstance().hasWon() && GameInstance.getInstance().gameOver && GameState.getInstance().getLogin().consecWins >= 5 && isLocked[8]) {
            RenderGameplay.getInstance().setNotifiationPic(2); //categories + 1
            name.add(achievementName[9]);
            JenesisGlassPane.getInstance().primaryNotice(Language.getInstance().get(83) + ": " + achievementName[9]);
            descriptions.add(achievementDescription[9]);
            categories.add(catType[2]);
            points.add(pointsArr[2] + bonus);
            currentPoints = currentPoints + pointsArr[2] + bonus;
            GameState.getInstance().getLogin().incrementAchievement(9);
            GameState.getInstance().getLogin().setConsequtiveWins(0);
            isLocked[8] = false;
        }

        if (RenderGameplay.getInstance().hasWon() && StoryMode.getInstance().stat.equalsIgnoreCase("half way") && GameInstance.getInstance().gameOver && isLocked[9]) {
            RenderGameplay.getInstance().setNotifiationPic(2); //categories + 1
            name.add(achievementName[10]);
            JenesisGlassPane.getInstance().primaryNotice(Language.getInstance().get(83) + ": " + achievementName[10]);
            descriptions.add(achievementDescription[10]);
            categories.add(catType[2]);
            points.add(pointsArr[2] + bonus);
            currentPoints = currentPoints + pointsArr[2] + bonus;
            GameState.getInstance().getLogin().incrementAchievement(10);
            isLocked[9] = false;
        }
    }

    /**
     * Get the users new score
     *
     * @return score
     */
    public int getNewUserPoints() {
        return currentPoints;
    }

    /**
     * New instance
     */
    public void newInstance() {
        name.clear();
        descriptions.clear();
        categories.clear();
        points.clear();
        name.trimToSize();
        descriptions.trimToSize();
        categories.trimToSize();
        points.trimToSize();
        currentPoints = GameState.getInstance().getLogin().getPoints();
        for (int i = 0; i < 14; i++) {
            isLocked[i] = true;
        }
        bonus = ((GameState.getInstance().getLogin().difficultyBase - GameState.getInstance().getLogin().difficultyDyn) / GameState.getInstance().getLogin().difficultyScale) * 5;
        currentPoints = currentPoints + 20 + bonus;
    }
}

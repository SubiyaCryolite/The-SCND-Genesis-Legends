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
import com.scndgen.legends.windows.MainWindow;

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
    public String[] achievemenDescription;
    private int[] pointsArr = {102, 198, 300};//50,33,17
    //private String[] catType = {"★", "★★★", "★★★★★"};
    private String[] catType = {"COOL", "SWEET", "EPIC"};
    private boolean[] isLocked = new boolean[14];
    private int currentPoints;
    private int bonus;

    public Achievements(LoginScreen loginScreen) {
        instance = this;
        this.loginScreen = loginScreen;
        newInstance();
        achievementName = new String[]{Language.getInstance().getLine(61), //0
                Language.getInstance().getLine(62), //1
                Language.getInstance().getLine(63), //2
                Language.getInstance().getLine(64), //3
                Language.getInstance().getLine(65), //4
                Language.getInstance().getLine(66), //5
                Language.getInstance().getLine(67), //6
                Language.getInstance().getLine(68), //7
                Language.getInstance().getLine(69), //8
                Language.getInstance().getLine(70), //9
                Language.getInstance().getLine(71),//10
        };
        achievemenDescription = new String[]{
                Language.getInstance().getLine(72), //0
                Language.getInstance().getLine(73), //1
                Language.getInstance().getLine(74), //2
                Language.getInstance().getLine(75), //3
                Language.getInstance().getLine(76), //4
                Language.getInstance().getLine(77), //5
                Language.getInstance().getLine(78), //6
                Language.getInstance().getLine(79), //7
                Language.getInstance().getLine(80), //8
                Language.getInstance().getLine(81), //9
                Language.getInstance().getLine(82), //10
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
            MainWindow.getInstance().systemNotice(Language.getInstance().getLine(83) + ": " + achievementName[0]);
            descriptions.add(achievemenDescription[0]);
            categories.add(catType[0]);
            points.add(pointsArr[0] + bonus);
            currentPoints = currentPoints + pointsArr[0] + bonus;
            loginScreen.incrementAchievement(0);
            isLocked[0] = false;
        }

        if (Characters.getInstance().getCharMinLife() <= 30 && RenderGameplay.getInstance().getPercent() >= 50 && isLocked[1]) {
            RenderGameplay.getInstance().setNotifiationPic(3); //categories + 1
            name.add(achievementName[1]);
            MainWindow.getInstance().systemNotice(Language.getInstance().getLine(83) + ": " + achievementName[1]);
            descriptions.add(achievemenDescription[1]);
            categories.add(catType[1]);
            points.add(pointsArr[1] + bonus);
            currentPoints = currentPoints + pointsArr[1] + bonus;
            loginScreen.incrementAchievement(1);
            isLocked[1] = false;
        }

        if (((RenderGameplay.getInstance().getPercent() - RenderGameplay.getInstance().getPercent2()) >= 50) && isLocked[4]) {
            RenderGameplay.getInstance().setNotifiationPic(3); //categories + 1
            name.add(achievementName[2]);
            MainWindow.getInstance().systemNotice(Language.getInstance().getLine(83) + ": " + achievementName[2]);
            descriptions.add(achievemenDescription[2]);
            categories.add(catType[2]);
            points.add(pointsArr[2] + bonus);
            currentPoints = currentPoints + pointsArr[2] + bonus;
            loginScreen.incrementAchievement(2);
            isLocked[4] = false;
            isLocked[3] = false;
            isLocked[2] = false;
        }

        if (((RenderGameplay.getInstance().getPercent() - RenderGameplay.getInstance().getPercent2()) >= 40) && isLocked[3]) {
            RenderGameplay.getInstance().setNotifiationPic(2); //categories + 1
            name.add(achievementName[3]);
            MainWindow.getInstance().systemNotice(Language.getInstance().getLine(83) + ": " + achievementName[3]);
            descriptions.add(achievemenDescription[3]);
            categories.add(catType[1]);
            points.add(pointsArr[1] + bonus);
            currentPoints = currentPoints + pointsArr[1] + bonus;
            loginScreen.incrementAchievement(3);
            isLocked[3] = false;
            isLocked[2] = false;
        }

        if (((RenderGameplay.getInstance().getPercent() - RenderGameplay.getInstance().getPercent2()) >= 30) && isLocked[2]) {
            RenderGameplay.getInstance().setNotifiationPic(1); //categories + 1
            name.add(achievementName[4]);
            MainWindow.getInstance().systemNotice(Language.getInstance().getLine(83) + ": " + achievementName[4]);
            descriptions.add(achievemenDescription[4]);
            categories.add(catType[0]);
            points.add(pointsArr[0] + bonus);
            currentPoints = currentPoints + pointsArr[0] + bonus;
            loginScreen.incrementAchievement(4);
            isLocked[2] = false;
        }

        if (RenderGameplay.getInstance().getAttackType(CharacterState.CHARACTER).equalsIgnoreCase("fury") && RenderGameplay.getInstance().getGameInstance().isGameOver && RenderGameplay.getInstance().hasWon() && isLocked[5]) {
            RenderGameplay.getInstance().setNotifiationPic(3); //categories + 1
            name.add(achievementName[5]);
            MainWindow.getInstance().systemNotice(Language.getInstance().getLine(83) + ": " + achievementName[5]);
            descriptions.add(achievemenDescription[5]);
            categories.add(catType[0]);
            points.add(pointsArr[0] + bonus);
            currentPoints = currentPoints + pointsArr[0] + bonus;
            loginScreen.incrementAchievement(5);
            isLocked[5] = false;
        }

        if (RenderGameplay.getInstance().hasWon() && RenderGameplay.getInstance().getGameInstance().isGameOver) {
            RenderGameplay.getInstance().setNotifiationPic(2); //categories + 1
            name.add(achievementName[6]);
            MainWindow.getInstance().systemNotice(Language.getInstance().getLine(83) + ": " + achievementName[6]);
            descriptions.add(achievemenDescription[6]);
            categories.add(catType[0]);
            points.add(pointsArr[0] + bonus);
            loginScreen.incrementAchievement(6);
            currentPoints = currentPoints + pointsArr[0] + bonus;
        }

        if (RenderGameplay.getInstance().getAttackType(CharacterState.OPPONENT).equalsIgnoreCase("fury") && RenderGameplay.getInstance().hasWon() && RenderGameplay.getInstance().getGameInstance().isGameOver && isLocked[6]) {
            RenderGameplay.getInstance().setNotifiationPic(3); //categories + 1
            name.add(achievementName[7]);
            MainWindow.getInstance().systemNotice(Language.getInstance().getLine(83) + ": " + achievementName[7]);
            descriptions.add(achievemenDescription[7]);
            categories.add(catType[1]);
            points.add(pointsArr[1] + bonus);
            currentPoints = currentPoints + pointsArr[1] + bonus;
            loginScreen.incrementAchievement(7);
            isLocked[6] = false;
        }

        if (RenderGameplay.getInstance().hasWon() && RenderGameplay.getInstance().getGameInstance().isGameOver && (RenderGameplay.getInstance().getPercent() - RenderGameplay.getInstance().getPercent2() <= 30) && isLocked[7]) {
            RenderGameplay.getInstance().setNotifiationPic(2); //categories + 1
            name.add(achievementName[8]);
            MainWindow.getInstance().systemNotice(Language.getInstance().getLine(83) + ": " + achievementName[8]);
            descriptions.add(achievemenDescription[8]);
            categories.add(catType[0]);
            points.add(pointsArr[0] + bonus);
            currentPoints = currentPoints + pointsArr[0] + bonus;
            loginScreen.incrementAchievement(8);
            isLocked[7] = false;
        }

        if (RenderGameplay.getInstance().hasWon() && RenderGameplay.getInstance().getGameInstance().isGameOver && loginScreen.consecWins >= 5 && isLocked[8]) {
            RenderGameplay.getInstance().setNotifiationPic(2); //categories + 1
            name.add(achievementName[9]);
            MainWindow.getInstance().systemNotice(Language.getInstance().getLine(83) + ": " + achievementName[9]);
            descriptions.add(achievemenDescription[9]);
            categories.add(catType[2]);
            points.add(pointsArr[2] + bonus);
            currentPoints = currentPoints + pointsArr[2] + bonus;
            loginScreen.incrementAchievement(9);
            loginScreen.consecWins = 0;
            isLocked[8] = false;
        }

        if (RenderGameplay.getInstance().hasWon() && StoryMode.getInstance().stat.equalsIgnoreCase("half way") && RenderGameplay.getInstance().getGameInstance().isGameOver && isLocked[9]) {
            RenderGameplay.getInstance().setNotifiationPic(2); //categories + 1
            name.add(achievementName[10]);
            MainWindow.getInstance().systemNotice(Language.getInstance().getLine(83) + ": " + achievementName[10]);
            descriptions.add(achievemenDescription[10]);
            categories.add(catType[2]);
            points.add(pointsArr[2] + bonus);
            currentPoints = currentPoints + pointsArr[2] + bonus;
            loginScreen.incrementAchievement(10);
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
        currentPoints = loginScreen.getPoints();
        for (int i = 0; i < 14; i++) {
            isLocked[i] = true;
        }
        bonus = ((loginScreen.difficultyBase - loginScreen.difficultyDyn) / loginScreen.difficultyScale) * 5;
        currentPoints = currentPoints + 20 + bonus;
    }
}

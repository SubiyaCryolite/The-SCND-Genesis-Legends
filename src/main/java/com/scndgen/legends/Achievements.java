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
import com.scndgen.legends.render.RenderGameplay;
import com.scndgen.legends.windows.MainWindow;
import io.github.subiyacryolite.enginev1.JenesisImageLoader;

import java.util.ArrayList;

/**
 * @author ndana
 */
public class Achievements {

    private final ArrayList name = new ArrayList();
    private final ArrayList desc = new ArrayList();
    private final ArrayList cat = new ArrayList();
    private final ArrayList points = new ArrayList();
    public String[] achDesc;
    public String[] achFull;
    private JenesisImageLoader pix;
    private LoginScreen parent;
    private int[] pointsArr = {102, 198, 300};//50,33,17
    //private String[] catType = {"★", "★★★", "★★★★★"};
    private String[] catType = {"COOL", "SWEET", "EPIC"};
    private boolean[] isLocked = new boolean[14];
    private int currentPoints;
    private int bonus;
    private static Achievements instance;

    public Achievements(LoginScreen loginScreen) {
        instance = this;
        parent = loginScreen;
        newInstance();
        achDesc = new String[]{Language.getInstance().getLine(61), //0
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
            /*
            lang.getLine(394), //11
            lang.getLine(395), //12
            lang.getLine(396), //13
            lang.getLine(397), //14
            lang.getLine(398), //15
            lang.getLine(399), //16
            lang.getLine(400), //17
            lang.getLine(401), //18
            lang.getLine(402), //19
            lang.getLine(403), //20
            lang.getLine(404) //21 */
        };

        achFull = new String[]{
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
            /*
            lang.getLine(405), //11
            lang.getLine(406), //12
            lang.getLine(407), //13
            lang.getLine(408), //14
            lang.getLine(409), //15
            lang.getLine(410), //16
            lang.getLine(411), //17
            lang.getLine(412), //18
            lang.getLine(413), //19
            lang.getLine(414), //20
            lang.getLine(415) //21*/
        };
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
        results[1] = "" + desc.get(index);
        results[2] = "" + cat.get(index);
        results[3] = "" + points.get(index);
        return results;
    }

    public void clearAll() {
        name.clear();
        name.trimToSize();

        desc.clear();
        desc.trimToSize();

        cat.clear();
        cat.trimToSize();

        points.clear();
        points.trimToSize();
    }

    /**
     * Scan for conditions
     */
    public void scan() {
        if (Characters.getCharMinLife() <= 79 && RenderGameplay.getInstance().getPercent() >= 82 && isLocked[0]) {
            RenderGameplay.getInstance().setNotifiationPic(1); //cat + 1
            name.add(achDesc[0]);
            MainWindow.getInstance().systemNotice(Language.getInstance().getLine(83) + ": " + achDesc[0]);
            desc.add(achFull[0]);
            cat.add(catType[0]);
            points.add(pointsArr[0] + bonus);
            currentPoints = currentPoints + pointsArr[0] + bonus;
            parent.incrementAchievement(0);
            isLocked[0] = false;
        }

        if (Characters.getCharMinLife() <= 30 && RenderGameplay.getInstance().getPercent() >= 50 && isLocked[1]) {
            RenderGameplay.getInstance().setNotifiationPic(3); //cat + 1
            name.add(achDesc[1]);
            MainWindow.getInstance().systemNotice(Language.getInstance().getLine(83) + ": " + achDesc[1]);
            desc.add(achFull[1]);
            cat.add(catType[1]);
            points.add(pointsArr[1] + bonus);
            currentPoints = currentPoints + pointsArr[1] + bonus;
            parent.incrementAchievement(1);
            isLocked[1] = false;
        }

        if (((RenderGameplay.getInstance().getPercent() - RenderGameplay.getInstance().getPercent2()) >= 50) && isLocked[4]) {
            RenderGameplay.getInstance().setNotifiationPic(3); //cat + 1
            name.add(achDesc[2]);
            MainWindow.getInstance().systemNotice(Language.getInstance().getLine(83) + ": " + achDesc[2]);
            desc.add(achFull[2]);
            cat.add(catType[2]);
            points.add(pointsArr[2] + bonus);
            currentPoints = currentPoints + pointsArr[2] + bonus;
            parent.incrementAchievement(2);
            isLocked[4] = false;
            isLocked[3] = false;
            isLocked[2] = false;
        }

        if (((RenderGameplay.getInstance().getPercent() - RenderGameplay.getInstance().getPercent2()) >= 40) && isLocked[3]) {
            RenderGameplay.getInstance().setNotifiationPic(2); //cat + 1
            name.add(achDesc[3]);
            MainWindow.getInstance().systemNotice(Language.getInstance().getLine(83) + ": " + achDesc[3]);
            desc.add(achFull[3]);
            cat.add(catType[1]);
            points.add(pointsArr[1] + bonus);
            currentPoints = currentPoints + pointsArr[1] + bonus;
            parent.incrementAchievement(3);
            isLocked[3] = false;
            isLocked[2] = false;
        }

        if (((RenderGameplay.getInstance().getPercent() - RenderGameplay.getInstance().getPercent2()) >= 30) && isLocked[2]) {
            RenderGameplay.getInstance().setNotifiationPic(1); //cat + 1
            name.add(achDesc[4]);
            MainWindow.getInstance().systemNotice(Language.getInstance().getLine(83) + ": " + achDesc[4]);
            desc.add(achFull[4]);
            cat.add(catType[0]);
            points.add(pointsArr[0] + bonus);
            currentPoints = currentPoints + pointsArr[0] + bonus;
            parent.incrementAchievement(4);
            isLocked[2] = false;
        }

        if (RenderGameplay.getInstance().getAttackType('c').equalsIgnoreCase("fury") && RenderGameplay.getInstance().getGameInstance().isGameOver && RenderGameplay.getInstance().hasWon() && isLocked[5]) {
            RenderGameplay.getInstance().setNotifiationPic(3); //cat + 1
            name.add(achDesc[5]);
            MainWindow.getInstance().systemNotice(Language.getInstance().getLine(83) + ": " + achDesc[5]);
            desc.add(achFull[5]);
            cat.add(catType[0]);
            points.add(pointsArr[0] + bonus);
            currentPoints = currentPoints + pointsArr[0] + bonus;
            parent.incrementAchievement(5);
            isLocked[5] = false;
        }

        if (RenderGameplay.getInstance().hasWon() && RenderGameplay.getInstance().getGameInstance().isGameOver) {
            RenderGameplay.getInstance().setNotifiationPic(2); //cat + 1
            name.add(achDesc[6]);
            MainWindow.getInstance().systemNotice(Language.getInstance().getLine(83) + ": " + achDesc[6]);
            desc.add(achFull[6]);
            cat.add(catType[0]);
            points.add(pointsArr[0] + bonus);
            parent.incrementAchievement(6);
            currentPoints = currentPoints + pointsArr[0] + bonus;
        }

        if (RenderGameplay.getInstance().getAttackType('o').equalsIgnoreCase("fury") && RenderGameplay.getInstance().hasWon() && RenderGameplay.getInstance().getGameInstance().isGameOver && isLocked[6]) {
            RenderGameplay.getInstance().setNotifiationPic(3); //cat + 1
            name.add(achDesc[7]);
            MainWindow.getInstance().systemNotice(Language.getInstance().getLine(83) + ": " + achDesc[7]);
            desc.add(achFull[7]);
            cat.add(catType[1]);
            points.add(pointsArr[1] + bonus);
            currentPoints = currentPoints + pointsArr[1] + bonus;
            parent.incrementAchievement(7);
            isLocked[6] = false;
        }

        if (RenderGameplay.getInstance().hasWon() && RenderGameplay.getInstance().getGameInstance().isGameOver && (RenderGameplay.getInstance().getPercent() - RenderGameplay.getInstance().getPercent2() <= 30) && isLocked[7]) {
            RenderGameplay.getInstance().setNotifiationPic(2); //cat + 1
            name.add(achDesc[8]);
            MainWindow.getInstance().systemNotice(Language.getInstance().getLine(83) + ": " + achDesc[8]);
            desc.add(achFull[8]);
            cat.add(catType[0]);
            points.add(pointsArr[0] + bonus);
            currentPoints = currentPoints + pointsArr[0] + bonus;
            parent.incrementAchievement(8);
            isLocked[7] = false;
        }

        if (RenderGameplay.getInstance().hasWon() && RenderGameplay.getInstance().getGameInstance().isGameOver && parent.consecWins >= 5 && isLocked[8]) {
            RenderGameplay.getInstance().setNotifiationPic(2); //cat + 1
            name.add(achDesc[9]);
            MainWindow.getInstance().systemNotice(Language.getInstance().getLine(83) + ": " + achDesc[9]);
            desc.add(achFull[9]);
            cat.add(catType[2]);
            points.add(pointsArr[2] + bonus);
            currentPoints = currentPoints + pointsArr[2] + bonus;
            parent.incrementAchievement(9);
            parent.consecWins = 0;
            isLocked[8] = false;
        }

        if (RenderGameplay.getInstance().hasWon() && StoryMode.getInstance().stat.equalsIgnoreCase("half way") && RenderGameplay.getInstance().getGameInstance().isGameOver && isLocked[9]) {
            RenderGameplay.getInstance().setNotifiationPic(2); //cat + 1
            name.add(achDesc[10]);
            MainWindow.getInstance().systemNotice(Language.getInstance().getLine(83) + ": " + achDesc[10]);
            desc.add(achFull[10]);
            cat.add(catType[2]);
            points.add(pointsArr[2] + bonus);
            currentPoints = currentPoints + pointsArr[2] + bonus;
            parent.incrementAchievement(10);
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
        desc.clear();
        cat.clear();
        points.clear();

        name.trimToSize();
        desc.trimToSize();
        cat.trimToSize();
        points.trimToSize();

        currentPoints = parent.getPoints();
        for (int i = 0; i < 14; i++) {
            isLocked[i] = true;
        }
        bonus = ((parent.difficultyBase - parent.difficultyDyn) / parent.difficultyScale) * 5;

        //
        //Gameplay.setNotifiationPic(1); //cat + 1
        currentPoints = currentPoints + 20 + bonus;
    }

    public static synchronized Achievements getInstance() {
        return instance;
    }
}

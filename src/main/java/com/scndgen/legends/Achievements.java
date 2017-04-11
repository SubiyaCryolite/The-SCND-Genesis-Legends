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
import com.scndgen.legends.drawing.DrawGame;
import com.scndgen.legends.engine.JenesisImage;
import com.scndgen.legends.engine.JenesisLanguage;

import java.util.ArrayList;

/**
 * @author ndana
 */
public class Achievements {

    private static ArrayList name = new ArrayList();
    private static ArrayList desc = new ArrayList();
    private static ArrayList cat = new ArrayList();
    private static ArrayList points = new ArrayList();
    public String[] achDesc;
    public String[] achFull;
    private JenesisLanguage lang;
    private JenesisImage pix;
    private LoginScreen parent;
    private int[] pointsArr = {102, 198, 300};//50,33,17
    //private String[] catType = {"★", "★★★", "★★★★★"};
    private String[] catType = {"COOL", "SWEET", "EPIC"};
    private boolean[] isLocked = new boolean[14];
    private int currentPoints;
    private int bonus;


    public Achievements(LoginScreen p) {

        parent = p;
        lang = parent.getLangInst();

        newInstance();

        achDesc = new String[]{lang.getLine(61), //0
                lang.getLine(62), //1
                lang.getLine(63), //2
                lang.getLine(64), //3
                lang.getLine(65), //4
                lang.getLine(66), //5
                lang.getLine(67), //6
                lang.getLine(68), //7
                lang.getLine(69), //8
                lang.getLine(70), //9
                lang.getLine(71),//10
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
                lang.getLine(72), //0
                lang.getLine(73), //1
                lang.getLine(74), //2
                lang.getLine(75), //3
                lang.getLine(76), //4
                lang.getLine(77), //5
                lang.getLine(78), //6
                lang.getLine(79), //7
                lang.getLine(80), //8
                lang.getLine(81), //9
                lang.getLine(82), //10
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
    public static int getAcievementsTriggered() {
        return name.size();
    }

    /**
     * Get achievement details
     *
     * @param index
     * @return details
     */
    public static String[] getName(int index) {
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
        if (Characters.getCharMinLife() <= 79 && LoginScreen.getLoginScreen().getMenu().getMain().getGame().perCent >= 82 && isLocked[0]) {
            DrawGame.setNotifiationPic(1); //cat + 1
            name.add(achDesc[0]);
            LoginScreen.getLoginScreen().getMenu().getMain().systemNotice(lang.getLine(83) + ": " + achDesc[0]);
            desc.add(achFull[0]);
            cat.add(catType[0]);
            points.add(pointsArr[0] + bonus);
            currentPoints = currentPoints + pointsArr[0] + bonus;
            parent.incrementAchievement(0);
            isLocked[0] = false;
        }

        if (Characters.getCharMinLife() <= 30 && LoginScreen.getLoginScreen().getMenu().getMain().getGame().perCent >= 50 && isLocked[1]) {
            DrawGame.setNotifiationPic(3); //cat + 1
            name.add(achDesc[1]);
            LoginScreen.getLoginScreen().getMenu().getMain().systemNotice(lang.getLine(83) + ": " + achDesc[1]);
            desc.add(achFull[1]);
            cat.add(catType[1]);
            points.add(pointsArr[1] + bonus);
            currentPoints = currentPoints + pointsArr[1] + bonus;
            parent.incrementAchievement(1);
            isLocked[1] = false;
        }

        if (((LoginScreen.getLoginScreen().getMenu().getMain().getGame().perCent - LoginScreen.getLoginScreen().getMenu().getMain().getGame().perCent2) >= 50) && isLocked[4]) {
            DrawGame.setNotifiationPic(3); //cat + 1
            name.add(achDesc[2]);
            LoginScreen.getLoginScreen().getMenu().getMain().systemNotice(lang.getLine(83) + ": " + achDesc[2]);
            desc.add(achFull[2]);
            cat.add(catType[2]);
            points.add(pointsArr[2] + bonus);
            currentPoints = currentPoints + pointsArr[2] + bonus;
            parent.incrementAchievement(2);
            isLocked[4] = false;
            isLocked[3] = false;
            isLocked[2] = false;
        }

        if (((LoginScreen.getLoginScreen().getMenu().getMain().getGame().perCent - LoginScreen.getLoginScreen().getMenu().getMain().getGame().perCent2) >= 40) && isLocked[3]) {
            DrawGame.setNotifiationPic(2); //cat + 1
            name.add(achDesc[3]);
            LoginScreen.getLoginScreen().getMenu().getMain().systemNotice(lang.getLine(83) + ": " + achDesc[3]);
            desc.add(achFull[3]);
            cat.add(catType[1]);
            points.add(pointsArr[1] + bonus);
            currentPoints = currentPoints + pointsArr[1] + bonus;
            parent.incrementAchievement(3);
            isLocked[3] = false;
            isLocked[2] = false;
        }

        if (((LoginScreen.getLoginScreen().getMenu().getMain().getGame().perCent - LoginScreen.getLoginScreen().getMenu().getMain().getGame().perCent2) >= 30) && isLocked[2]) {
            DrawGame.setNotifiationPic(1); //cat + 1
            name.add(achDesc[4]);
            LoginScreen.getLoginScreen().getMenu().getMain().systemNotice(lang.getLine(83) + ": " + achDesc[4]);
            desc.add(achFull[4]);
            cat.add(catType[0]);
            points.add(pointsArr[0] + bonus);
            currentPoints = currentPoints + pointsArr[0] + bonus;
            parent.incrementAchievement(4);
            isLocked[2] = false;
        }

        if (DrawGame.getAttackType('c').equalsIgnoreCase("fury") && LoginScreen.getLoginScreen().getMenu().getMain().getGame().getGameInstance().isGameOver && LoginScreen.getLoginScreen().getMenu().getMain().getGame().hasWon() && isLocked[5]) {
            DrawGame.setNotifiationPic(3); //cat + 1
            name.add(achDesc[5]);
            LoginScreen.getLoginScreen().getMenu().getMain().systemNotice(lang.getLine(83) + ": " + achDesc[5]);
            desc.add(achFull[5]);
            cat.add(catType[0]);
            points.add(pointsArr[0] + bonus);
            currentPoints = currentPoints + pointsArr[0] + bonus;
            parent.incrementAchievement(5);
            isLocked[5] = false;
        }

        if (LoginScreen.getLoginScreen().getMenu().getMain().getGame().hasWon() && LoginScreen.getLoginScreen().getMenu().getMain().getGame().getGameInstance().isGameOver) {
            DrawGame.setNotifiationPic(2); //cat + 1
            name.add(achDesc[6]);
            LoginScreen.getLoginScreen().getMenu().getMain().systemNotice(lang.getLine(83) + ": " + achDesc[6]);
            desc.add(achFull[6]);
            cat.add(catType[0]);
            points.add(pointsArr[0] + bonus);
            parent.incrementAchievement(6);
            currentPoints = currentPoints + pointsArr[0] + bonus;
        }

        if (DrawGame.getAttackType('o').equalsIgnoreCase("fury") && LoginScreen.getLoginScreen().getMenu().getMain().getGame().hasWon() && LoginScreen.getLoginScreen().getMenu().getMain().getGame().getGameInstance().isGameOver && isLocked[6]) {
            DrawGame.setNotifiationPic(3); //cat + 1
            name.add(achDesc[7]);
            LoginScreen.getLoginScreen().getMenu().getMain().systemNotice(lang.getLine(83) + ": " + achDesc[7]);
            desc.add(achFull[7]);
            cat.add(catType[1]);
            points.add(pointsArr[1] + bonus);
            currentPoints = currentPoints + pointsArr[1] + bonus;
            parent.incrementAchievement(7);
            isLocked[6] = false;
        }

        if (LoginScreen.getLoginScreen().getMenu().getMain().getGame().hasWon() && LoginScreen.getLoginScreen().getMenu().getMain().getGame().getGameInstance().isGameOver && (LoginScreen.getLoginScreen().getMenu().getMain().getGame().perCent - LoginScreen.getLoginScreen().getMenu().getMain().getGame().perCent2 <= 30) && isLocked[7]) {
            DrawGame.setNotifiationPic(2); //cat + 1
            name.add(achDesc[8]);
            LoginScreen.getLoginScreen().getMenu().getMain().systemNotice(lang.getLine(83) + ": " + achDesc[8]);
            desc.add(achFull[8]);
            cat.add(catType[0]);
            points.add(pointsArr[0] + bonus);
            currentPoints = currentPoints + pointsArr[0] + bonus;
            parent.incrementAchievement(8);
            isLocked[7] = false;
        }

        if (LoginScreen.getLoginScreen().getMenu().getMain().getGame().hasWon() && LoginScreen.getLoginScreen().getMenu().getMain().getGame().getGameInstance().isGameOver && parent.consecWins >= 5 && isLocked[8]) {
            DrawGame.setNotifiationPic(2); //cat + 1
            name.add(achDesc[9]);
            LoginScreen.getLoginScreen().getMenu().getMain().systemNotice(lang.getLine(83) + ": " + achDesc[9]);
            desc.add(achFull[9]);
            cat.add(catType[2]);
            points.add(pointsArr[2] + bonus);
            currentPoints = currentPoints + pointsArr[2] + bonus;
            parent.incrementAchievement(9);
            parent.consecWins = 0;
            isLocked[8] = false;
        }

        if (LoginScreen.getLoginScreen().getMenu().getMain().getGame().hasWon() && StoryMode.stat.equalsIgnoreCase("half way") && LoginScreen.getLoginScreen().getMenu().getMain().getGame().getGameInstance().isGameOver && isLocked[9]) {
            DrawGame.setNotifiationPic(2); //cat + 1
            name.add(achDesc[10]);
            LoginScreen.getLoginScreen().getMenu().getMain().systemNotice(lang.getLine(83) + ": " + achDesc[10]);
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
        //DrawGame.setNotifiationPic(1); //cat + 1
        currentPoints = currentPoints + 20 + bonus;
    }
}

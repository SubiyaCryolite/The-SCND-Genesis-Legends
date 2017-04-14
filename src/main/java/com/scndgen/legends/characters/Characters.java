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
package com.scndgen.legends.characters;


import com.scndgen.legends.LoginScreen;
import com.scndgen.legends.drawing.DrawGame;
import com.scndgen.legends.engine.JenesisCharacter;
import com.scndgen.legends.menus.CanvasGameRender;

import java.lang.annotation.Documented;

/**
 * This class should be self explainatory -_-
 *
 * @author Ifunga Ndana
 */
@Documented
@interface ClassPreamble {

    String author();

    String date();

    int currentRevision() default 1;

    String lastModified() default "N/A";

    String lastModifiedBy() default "N/A";

    String[] reviewers();  // Note use of array
}

@ClassPreamble(author = "Ifunga Ndana",
        date = "7/April/2011",
        currentRevision = 1,
        lastModified = "7/April/2011",
        lastModifiedBy = "Ifunga Ndana",
        reviewers = {"Random", "Dude"})
public class Characters {

    public static String[] moveMusicOpp = new String[8];
    public static String[] moveMusicChar = new String[8];
    public static int[] pointsArr = new int[12];
    public static String[] typeArray = new String[4];
    private static String nameChar, nameOpp, assChar, assOpp;
    private static float activityRecoveryRateChar2, activityRecoverRateChar, activityRecoveryRateOpp, activityRecoveryRateOpp2;
    private static float healthRecoveryRateChar2, healthRecoveryRateChar, healthRecoveryRateOpp, healthRecoveryRateOpp2;
    //AIRCON 12 GLOWING HOT GIMP 2.6.8
    private static int damageMultiplierOpp, damageMultiplierChar, minCharlife, minOppLife2, currCharLife3, minOppLife, currCharLife, currOppLife2, currOppLife, points, maxPoints;
    private JenesisCharacter dudeP, dudeP2, dudeO, dudeO2;

    //--------public accessor methods-----------------
    public static String getCharName() {
        return nameChar;
    }

    /**
     * Get the Characters assist partner
     *
     * @return character assist partner
     */
    public static String getCharAssName() {
        return assChar;
    }

    /**
     * Get the opponents assist partner
     *
     * @return opponent assist partner
     */
    public static String getOppAssName() {
        return assOpp;
    }

    public static String getOppName() {
        return nameOpp;
    }

    public static void setOppName(String thisName) {
        minOppLife = 100;
        currOppLife = 100;
        nameOpp = thisName;
    }

    public static float getCharRecoverySpeed() {
        return activityRecoverRateChar;
    }

    public static float getOppRecoverySpeed() {
        return activityRecoveryRateOpp;
    }

    public static float getOppRecoverySpeed2() {
        return activityRecoveryRateOpp2;
    }

    public static float getCharRecoverySpeed2() {
        return activityRecoveryRateChar2;
    }

    public static float getCharRecoveryRate() {
        return healthRecoveryRateChar;
    }

    public static float getOppRecoveryRate() {
        return healthRecoveryRateOpp;
    }

    public static void incrementSpeedRate(char who, float thisMuch) {
        if (who == 'c') {
            activityRecoverRateChar = activityRecoverRateChar + thisMuch;
        }

        if (who == 'o') {
            activityRecoveryRateOpp = activityRecoveryRateOpp + thisMuch;
        }
    }

    //called when character damaged
    public static void setCurrLifeChar(int life) {
        currCharLife = life;
        //percentages
        if (life < minCharlife) {
            minCharlife = life;
            //System.out.println("min char life "+minCharlife);
        }
    }

    //called when opp damaged
    public static void setCurrLifeOpp(int life) {
        currOppLife = life;

        //percentages
        if (life < minOppLife) {
            minOppLife = life;
        }
    }

    //called when opp damaged
    public static void setCurrLifeOpp2(int life) {
        currOppLife2 = life;

        //percentages
        if (life < minOppLife2) {
            minOppLife2 = life;
        }
    }

    //called when opp damaged
    public static void setCurrLifeChar2(int life) {
        currCharLife3 = life;

        //percentages
        if (life < currCharLife3) {
            currCharLife3 = life;
        }
    }

    public static float getCharMinLife() {
        return (float) minCharlife;
    }

    public static float getCharCurrLife() {
        return (float) currCharLife;
    }

    public static float getOppMinLife() {
        return (float) minOppLife;
    }

    public static float getOppCurrLife() {
        return (float) currOppLife;
    }

    public static float getPoints() {
        return (float) points / maxPoints;
    }

    public static void setPoints(int amount) {
        points = amount;
        maxPoints = amount;
    }

    public static void alterPoints(int thisMuch) {
        points = points - thisMuch;
    }

    /*
     *
     */
    public static void alterPoints2(int index) {
        if (DrawGame.numOfAttacks > 1) {
            DrawGame.numOfAttacks = DrawGame.numOfAttacks = 1;
            points = points + pointsArr[index];
        }
    }

    /**
     * SET damage multipliers, used to strengthen/weaken attacks
     *
     * @param per      the person calling the method
     * @param thisMuch the number to alter by
     */
    public static void setDamageCounter(char per, int thisMuch) {
        if (per == 'c') {
            damageMultiplierOpp = thisMuch;
        }

        if (per == 'o') {
            damageMultiplierChar = thisMuch;
        }
    }

    public static int getDamageMultiplier(char per) {
        int myInt = 0;

        if (per == 'c') {
            myInt = damageMultiplierOpp;
        } else if (per == 'o') {
            myInt = damageMultiplierChar;
        }

        return myInt;
    }

    public JenesisCharacter getDudeChar() {
        return dudeP;
    }

    public JenesisCharacter getDudeChar2() {
        return dudeP2;
    }

    public JenesisCharacter getDudeOpp() {
        return dudeO;
    }

    public JenesisCharacter getDudeOpp2() {
        return dudeO2;
    }

    public void prepare(int c) {

        minOppLife = 100;
        currOppLife = 100;
        minCharlife = 100;
        currCharLife = 100;
        setDamageCounter('c', 12);

        if (c == 1) {
            nameChar = "Subiya";
            LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().selectedCharIndex = 1;
            dudeP2 = new Raila();
            assChar = "Raila";
            dudeP = new Subiya();
        }

        if (c == 2) {
            nameChar = "Raila";
            LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().selectedCharIndex = 0;
            dudeP2 = new Subiya();
            assChar = "Subiya";
            dudeP = new Raila();
        }

        if (c == 3) {
            nameChar = "Lynx";
            LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().selectedCharIndex = 2;
            dudeP2 = new Aisha();
            assChar = "Aisha";
            dudeP = new Lynx();
        }

        if (c == 4) {
            nameChar = "Aisha";
            LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().selectedCharIndex = 3;
            dudeP2 = new Lynx();
            assChar = "Lynx";
            dudeP = new Aisha();
        }

        if (c == 5) {
            nameChar = "Ravage";
            LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().selectedCharIndex = 4;
            dudeP2 = new Jon();
            assChar = "Jonah";
            dudeP = new Ravage();
        }

        if (c == 6) {
            nameChar = "Ade";
            LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().selectedCharIndex = 5;
            dudeP2 = new Adam();
            assChar = "NovaAdam";
            dudeP = new Ade();
        }

        if (c == 7) {
            nameChar = "Jonah";
            LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().selectedCharIndex = 6;
            dudeP2 = new Ravage();
            assChar = "Ravage";
            dudeP = new Jon();
        }

        if (c == 8) {
            nameChar = "NovaAdam";
            LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().selectedCharIndex = 7;
            dudeP2 = new Ade();
            assChar = "Ade";
            dudeP = new Adam();
        }

        if (c == 9) {
            nameChar = "NOVA NovaAdam";
            LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().selectedCharIndex = 8;
            dudeP2 = new Ade();
            assChar = "Ade";
            assChar = "Blank";
            dudeP = new NovaAdam();
        }

        if (c == 10) {
            nameChar = "Azaria";
            LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().selectedCharIndex = 9;
            dudeP2 = new Lynx();
            assChar = "Lynx";
            dudeP = new Azaria();
        }

        if (c == 11) {
            nameChar = "Sorrowe";
            LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().selectedCharIndex = 10;

            dudeP2 = new Ade();
            assChar = "Ade";
            dudeP = new Sorr();
        }

        if (c == 12) {
            nameChar = "The Thing";
            LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().selectedCharIndex = 11;
            dudeP2 = new NovaAdam();
            assChar = "NOVA NovaAdam";
            dudeP = new Thing(0);
        }

        activityRecoverRateChar = dudeP.getRecovSpeed();
        healthRecoveryRateChar = dudeP.getHPRecovRate();
        setPoints(dudeP.getPoints());
        CanvasGameRender.setLife(dudeP.getLife());
        CanvasGameRender.setMaxLife(dudeP.getLife());


        activityRecoveryRateChar2 = dudeP2.getRecovSpeed();
        healthRecoveryRateChar2 = dudeP2.getHPRecovRate();
        CanvasGameRender.setCharLife3(dudeP2.getLife());
        CanvasGameRender.setCharMaxLife3(dudeP2.getLife());
        dudeP2.setAiProf3();
    }

    public void prepareO(int c) {

        minOppLife = 100;
        currOppLife = 100;
        minCharlife = 100;
        currCharLife = 100;
        setDamageCounter('o', 12);

        if (c == 1) {
            nameOpp = "Subiya";
            LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().selectedOppIndex = 1;
            dudeO2 = new Raila();
            assOpp = "Raila";
            dudeO = new Subiya();
        }

        if (c == 2) {
            nameOpp = "Raila";
            LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().selectedOppIndex = 0;
            dudeO2 = new Subiya();
            assOpp = "Subiya";
            dudeO = new Raila();
        }

        if (c == 3) {
            nameOpp = "Lynx";
            LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().selectedOppIndex = 2;
            dudeO2 = new Aisha();
            assOpp = "Aisha";
            dudeO = new Lynx();
        }

        if (c == 4) {
            nameOpp = "Aisha";
            LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().selectedOppIndex = 3;
            dudeO2 = new Lynx();
            assOpp = "Lynx";
            dudeO = new Aisha();
        }

        if (c == 5) {
            nameOpp = "Ravage";
            LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().selectedOppIndex = 4;
            dudeO2 = new Jon();
            assOpp = "Jonah";
            dudeO = new Ravage();
        }

        if (c == 6) {
            nameOpp = "Ade";
            LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().selectedOppIndex = 5;
            dudeO2 = new Adam();
            assOpp = "NovaAdam";
            dudeO = new Ade();
        }

        if (c == 7) {
            nameOpp = "Jonah";
            LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().selectedOppIndex = 6;
            dudeO2 = new Ravage();
            assOpp = "Ravage";
            dudeO = new Jon();
        }

        if (c == 8) {
            nameOpp = "NovaAdam";
            LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().selectedOppIndex = 7;
            dudeO2 = new Ade();
            assOpp = "Ade";
            dudeO = new Adam();
        }

        if (c == 9) {
            nameOpp = "NOVA NovaAdam";
            LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().selectedOppIndex = 8;
            dudeO2 = new Ade();
            assOpp = "Ade";
            dudeO = new NovaAdam();
        }

        if (c == 10) {
            nameOpp = "Azaria";
            LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().selectedOppIndex = 9;
            dudeO2 = new Lynx();
            assOpp = "Lynx";
            dudeO = new Azaria();
        }

        if (c == 11) {
            nameOpp = "Sorrowe";
            LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().selectedOppIndex = 10;
            dudeO2 = new Ade();
            assOpp = "Ade";
            dudeO = new Sorr();
        }

        if (c == 12) {
            nameOpp = "The Thing";
            LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().selectedOppIndex = 11;
            dudeO2 = new NovaAdam();
            assOpp = "NOVA NovaAdam";
            dudeO = new Thing(0);
        }

        if (c == 13) {
            nameOpp = "The Thing";
            LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().selectedOppIndex = 11;
            dudeO2 = new NovaAdam();
            assOpp = "NOVA NovaAdam";
            dudeO = new Thing(1);
        }


        activityRecoveryRateOpp = dudeO.getRecovSpeed();
        healthRecoveryRateOpp = dudeO.getHPRecovRate();
        CanvasGameRender.setOppLife(dudeO.getLife());
        CanvasGameRender.setOppMaxLife(dudeO.getLife());
        dudeO.setAiProf();


        activityRecoveryRateOpp2 = dudeO2.getRecovSpeed();
        healthRecoveryRateOpp2 = dudeO2.getHPRecovRate();
        CanvasGameRender.setOppLife2(dudeO2.getLife());
        CanvasGameRender.setOppMaxLife2(dudeO2.getLife());
        dudeO2.setAiProf2();
    }

    /**
     * Added 19/jan/2011 by SubiyaCryolite -
     * resets every character
     */
    public void resetCharacters() {
        dudeO.resetLimits();
        dudeP.resetLimits();
        LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().proceed1 = false;
        LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().proceed2 = false;
    }
}

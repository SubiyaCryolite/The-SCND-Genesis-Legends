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
package com.scndgen.legends.menus;

import com.scndgen.legends.LoginScreen;
import com.scndgen.legends.characters.Characters;
import com.scndgen.legends.drawing.DrawGame;
import com.scndgen.legends.threads.ThreadGameInstance;
import com.scndgen.legends.windows.WindowMain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is a fighting game based on my webcomic THE SCND GENESIS
 * http://www.scndgenesis.50webs.com
 * The battle system is inspired by Final Fantasy XIII and conventional 2D Fighters
 * The game has a handdrawn comic book graphical style similar to the comic and has other anime style effects
 * Feel free to tell me what you think about the game and comic ifungandana(at)gmail.com
 *
 * @author Ifunga Ndana
 */
public class RenderGameRender extends DrawGame implements ActionListener {

    public static String attackPicSrc;
    public static String attackPicOppSrc;
    public static String ActivePerson; // person who performed an attack, name shall show in battle info status area
    public static int Win = 0;
    public static int waitAnim = 0;
    public static int damageMultiplierChar, damageMultiplierOpp, celestiaMultiplierChar, celestiaMultiplierOpp;
    private static float daNum, daNum2, daNum2a, daNum3a;
    private static long lifePlain, lifeTotalPlain, lifePlain2, lifePlain2a, lifePlain3a, lifeTotalPlain2, lifeTotalPlain2a, lifeTotalPlain3a;
    private static int fancyBWAnimeEffect = 0;     //toggle fancy effect when HP low
    private static boolean fancyBWAnimeEffectEnabled;
    private static String manipulateThis;
    private static ThreadGameInstance fpsGen;
    private static boolean isMoveQued, gameOver;
    private static int thisInt; //max damage that can be dealt by Celestia Physics
    private static int counter1, damageC, damageO;
    private static int life, maXlife, oppLife, oppMaxLife, oppLife2, oppMaxLife2, charLife3, charMaxLife3;
    ;
    private static int damageChar, damageOpp, damageChar2, damageOpp2;
    private static int limitBreak, limitTop = 1000;
    private static String versionString = " 0.8e";
    private static int versioInt = 20120630; // yyyy-mm-dd
    private static float finalOppLife, finalCharLife;
    public int perCent = 100, perCent2 = 100, perCent2a = 100, perCent3a = 100;
    public Characters selectedChar, selectedOpp;
    public int done = 0; // if gameover
    public String[] attackArray = new String[8];//up to 8 moves can be qued
    public int comboCounter = 0; //must be negative one to reach index 0, app wide counter, enable you to que attacks of different kinds
    private Object source;
    private JMenuItem Gnew, Gend;
    //Year - Month - date

    // for every enhancement add 0.0.0.1
    // for every milestone and 0.0.0.3
    // 18/06/10 - Added Shakey digits 0.0.2.8
    // 17/07/10 - Added Difficulty Settings 0.0.2.9
    // 17/07/10 - Embedded attacks into main screen 0.0.3.0
    // 30/07/10 - Grouped all attacks into single class 0.0.3.1
    // 30/07/10 - Drastic architectural changes 0.0.3.2
    // 31/07/10 - Cool motif look and feel 0.0.3.3
    // 02/08/10 - Added game menu, ATB Bar, Tabbed Pane for different move type 0.0.3.6
    // 05/08/10 - Added regenerating HP 0.0.3.7
    // 05/08/10 - Attacks now execute in sequence they were added 0.0.3.8
    // 05/08/10 - All menus and panels under one roof 0.0.3.9
    // 09/08/10 - fixed MP3 plug-in 0.0.4.0
    // 15/08/10 - Added match timer and options 0.0.4.1
    // 11/08/10 - Converted timertasks to threads, added arena select, fixed relative sound path 0.0.4.4
    // 19/09/10 - caches all sprites, Serious thread optimizations, AI rewrites, SIGNIFICANT performance improvements 0.0.5.5
    // 22/09/10 - implemented LAN play!!!! Game chat, lobby system needed 0.0.6.5
    // 22/09/10 - implemented login screen, ciphering 0.0.6.7
    // 18/10/10 - implemented in-game chat, utf8 encoding, stats screen, achievement structure, game/profile save 0.0.7.3
    // 21/10/10 - added overworld map, began navigation and screen control 0.0.7.7
    // 23/10/10 - added collision detection algorythm for overworld 0.0.8.0
    // 27/10/10 - added new menu, transition, character classess, remved command panel 0.0.8.4
    // 30/10/10 -added mode menu, server-client embeddedin menu, match making/lobby system ACTUALLY WORKS!!!!!! 0.0.8.7
    // 03/11/10 -24- added new Character, Aisha 0.0.8.8
    // 03/11/10 -25- added limit break system, fixed LAN bugs - 0.0.9.0
    // 17/11/10 -26- better about screen, new versioning system ( major version | minor revision | updates/fixes ) 0.0.9.1
    // 20/11/10 -29- STORY MODE STRUCURE!!! Storymode bug-fixes, options and stats integrated into menu
    // 23/11/10 -30- fixed story mode and added pause, skip, resume :D
    // 02/12/10 -31- MOUSE INPUT :D, Fixed sound structure,Music pauses, added framrate chooser, sound-on/off works
    // 04/12/10 -32- One story mode to rule them all, bwa ha ha, added scene select as well :)
    // TODO 7/12/10 -33- Added background animation thread
    // 10/12/10 -34- Added Ravage, implemented character balance scheme, fixed bug in story mode thread
    // Sidenote 14/12/10: Joined Twitter ^_^
    // 15/12/10 -35- Added character Ade, Added quit game, resume, exit to gameplay. New achievement pics, mod to systemNotice(), better figures, sexy transparent HUD
    // 17/12/10 -36- Added new stages "Scorched Ruins" and "Frozen Wilderness"
    // 23/12/10 - Started porting the game to c++, evident performance benefits, fixed threads.
    // 27/12/10 -37- Realised how much I love Java, cross pompiling on C++ sucks, smoothened animations and start menu screen
    // 01/1/11 -38- Thread optimisations. wee
    // 04/1/11 -39- Fixed story mode bugs, adding GPL headerz gon opensource
    // 13/1/11 -40- Changed main menu, ditched runtime flipping for pre rendered images (opponents), performance benefits
    // 14/1/11 -41- Integrated stats into main menu, pending for connections can be cancelled
    // 15/1/11 -42- Backwards compatibility for new save items, fixed time
    // 15/1/11 -43- Changelog moved to WindowAbout.java in text3
    // 13/2/11 -44- I'm baaaaack, changelog in WindowAbout is clientSide only, codies go here
    // 13/2/11 -44- Fixed bug, reset move to physical at new match

    /**
     * Create the panel
     */
    public RenderGameRender() {
        initializePanel();
    }

    /**
     * Legacy code
     *
     * @param message to display
     * @return integer
     */
    public static int showConfirmMessage(String message) {
        return JOptionPane.showConfirmDialog(null, message, "Hey There", JOptionPane.YES_NO_CANCEL_OPTION);
    }

    /**
     * Gets the damage multiplier
     *
     * @param who - which character
     * @return damage multiplier
     */
    public static int getDamageDealt(char who) {

        if (who == 'c') {
            thisInt = damageC;
        }

        if (who == 'o') {
            thisInt = damageO;
        }

        return thisInt;
    }

    /**
     * Set player 1 life
     *
     * @param Life - value
     */
    public static void setLife(int Life) {
        life = Life;
    }

    /**
     * Set player 1 maximum life
     *
     * @param Life - value
     */
    public static void setMaxLife(int Life) {
        maXlife = Life;
    }

    public static boolean isGameRunning() {
        return fpsGen != null;
    }
    //------------- end action listers -------------
    //------------- start methods ------------------

    /**
     * Gets the games current version
     *
     * @return version
     */
    public static String getVersionStr() {
        return versionString;
    }

    /**
     * Gets the games current version
     *
     * @return version
     */
    public static int getVersionInt() {
        return versioInt;
    }

    /**
     * Legacy awesomeness
     *
     * @return is effect on?
     */
    public static boolean isFancyEffect() {
        {
            if (fancyBWAnimeEffect == 1) {
                fancyBWAnimeEffectEnabled = true;
            }

            if (fancyBWAnimeEffect != 1) {
                fancyBWAnimeEffectEnabled = false;
            }
        }

        return fancyBWAnimeEffectEnabled;
    }

    /**
     * Get the character multiplier
     *
     * @return the damage multiplier
     */
    public static int getDamageMultiplierChar() {
        return damageMultiplierChar;
    }

    /**
     * Get the opponent multiplier
     *
     * @return the damage multiplier
     */
    public static int getDamageMultiplierOpp() {
        return damageMultiplierOpp;
    }

    /**
     * Get the break status
     *
     * @return break status
     */
    public static int getBreak() {
        return limitBreak;
    }

    /**
     * set the break status
     */
    public static void setBreak(int change) {
        limitBreak = limitBreak + change;
    }

    /**
     * Increment limit
     */
    private static void incLImit(int ThisMuch) {
        final int inc = ThisMuch;
        new Thread() {
            //add one, make sure we dont go over 2000

            @SuppressWarnings("static-access")
            @Override
            public void run() {
                int icrement = inc;
                if (LoginScreen.getInstance().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.singlePlayer2)) {
                    icrement = icrement / 2;
                }
                this.setName("Fury bar increment stage");
                for (int o = 0; o < icrement; o++) {
                    if (limitBreak < limitTop) {
                        try {
                            limitBreak = limitBreak + 1;
                            this.sleep(15);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(RenderGameRender.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        }.start();
    }

    /**
     * 13 / Sept /2010
     * 16:24
     * <p>
     * be strong
     * love what you do
     * passion
     * expertice
     * ============Ultra High Definition=====================
     * 1980x1080 standard HD
     * UHD developed by NHK
     * 8000x4360, 24GiB/s compressed to 1.GiBs, encoded 350MiB/s
     */
    public void killGameInstance() {
        //fpsGen=null;
    }

    public void triggerFury(char who) {
        limitBreak(who);
    }

    /**
     * Get scale Y
     *
     * @return Y's scale
     */
    public float getscaleY() {
        return LoginScreen.getInstance().getGameYScale();
    }

    /**
     * Get scale Y
     *
     * @return Y's scale
     */
    public float getscaleX() {
        return LoginScreen.getInstance().getGameXScale();
    }

    /**
     * Takes a screen shot
     */
    public void takeScreenShot() {
        captureScreenShot();
        systemNotice("Screenshot taken");
    }

    //------------- start action listers -------------
    @Override
    public void actionPerformed(ActionEvent ae) {
        source = ae.getSource();

        if (source == Gnew) {
            int opt = showConfirmMessage("Wanna start a new game?");
            if (opt == JOptionPane.YES_OPTION) {
                //showMessage("Ok");startDrawing=0;Win=0;repaint();resetGame();
            }

            if (opt == JOptionPane.NO_OPTION) {
                systemNotice("Why'd you bother asking?");
            }
            if (opt == JOptionPane.CANCEL_OPTION) {
                systemNotice("Learn to make up your mind");
                counter1 = counter1 + 1;
            }
        }
    }

    /**
     * Determines if match has reached game over state
     */
    public void matchStatus() {
        if (gameOver == false) {
            if (LoginScreen.getInstance().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.singlePlayer2)) {
                if ((oppLife + oppLife2) < 0 || (life + charLife3) < 0 || (getGameInstance().time <= 0 && getGameInstance().time <= 180)) {
                    if ((float) (oppLife + oppLife2) / (float) (oppMaxLife + oppMaxLife2) > (float) (life + charLife3) / (float) (maXlife + charMaxLife3) || (float) (oppLife + oppLife2) / (float) (oppMaxLife + oppMaxLife2) < (float) (life + charLife3) / (float) (charMaxLife3 + maXlife)) {
                        getGameInstance().gameOver();
                    }
                }
            } else if (oppLife < 0 || life < 0 || (ThreadGameInstance.time <= 0 && ThreadGameInstance.time <= 180)) {
                if ((float) oppLife / (float) oppMaxLife > (float) life / (float) maXlife || (float) oppLife / (float) oppMaxLife < (float) life / (float) maXlife) {
                    getGameInstance().gameOver();

                }
            }
            //save life at gameover
            finalOppLife = (float) oppLife / (float) oppMaxLife;
            finalCharLife = (float) life / (float) maXlife;
        }
    }

    /**
     * Updates the life of Characters
     *
     * @param forWho   - the person affected
     * @param ThisMuch - the life to add/subtract
     * @param attacker - who inflicted damage
     */
    public void lifePhysUpdateSimple(int forWho, int ThisMuch, String attacker) {

        if (forWho == 1) //Attack from player
        {
            damageChar = ThisMuch;
            ActivePerson = attacker;
            incLImit(damageChar);
            guiScreenChaos(ThisMuch * getDamageMultiplierOpp(), 'o');
            for (int m = 0; m < damageChar; m++) {
                if (life >= 0) {
                    life = life - (1 * getDamageMultiplierOpp());
                }
            }
            daNum = ((getCharLife() / getCharMaxLife()) * 100); //perc life x life bar length
            lifePlain = Math.round(daNum); // round off
            lifeTotalPlain = Math.round(getCharLife()); // for text
            perCent = Math.round(lifePlain);
        }

        if (forWho == 2) //Attack from CPU pponent 1
        {
            damageOpp = ThisMuch;
            ActivePerson = attacker;
            incLImit(damageOpp);
            guiScreenChaos(ThisMuch * getDamageMultiplierChar(), 'c');
            for (int m = 0; m < damageOpp; m++) {
                if (oppLife >= 0) {
                    oppLife = oppLife - (1 * getDamageMultiplierOpp());
                }
            }
            daNum2 = ((getOppLife() / getOppMaxLife()) * 100); //perc life x life bar length
            lifePlain2 = Math.round(daNum2); // round off
            lifeTotalPlain2 = Math.round(getOppLife()); // for text
            perCent2 = Math.round(lifePlain2);
        }

        if (forWho == 4) //Attack from CPU opponent 2
        {
            damageOpp2 = ThisMuch;
            ActivePerson = attacker;
            incLImit(damageOpp2);
            guiScreenChaos(ThisMuch * getDamageMultiplierChar(), 'a');
            for (int m = 0; m < damageOpp2; m++) {
                if (oppLife2 >= 0) {
                    oppLife2 = oppLife2 - (1 * getDamageMultiplierOpp());
                }
            }
            daNum2a = ((getOppLife2() / getOppMaxLife2()) * 100); //perc life x life bar length
            lifePlain2a = Math.round(daNum2a); // round off
            lifeTotalPlain2a = Math.round(getOppLife2()); // for text
            perCent2a = Math.round(lifePlain2a);
        }

        if (forWho == 3) //Attack from CPU player 2
        {
            damageChar2 = ThisMuch;
            ActivePerson = attacker;
            incLImit(damageChar2);
            guiScreenChaos(ThisMuch * getDamageMultiplierOpp(), 'b');
            for (int m = 0; m < damageChar2; m++) {
                if (charLife3 >= 0) {
                    charLife3 = charLife3 - (1 * getDamageMultiplierOpp());
                }
            }
            daNum3a = ((getCharLife3() / getCharMaxLife3()) * 100); //perc life x life bar length
            lifePlain3a = Math.round(daNum3a); // round off
            lifeTotalPlain3a = Math.round(getCharLife3()); // for text
            perCent3a = Math.round(lifePlain3a);
        }
    }

    /**
     * Draws battle message at bottom of screen
     *
     * @param writeThis - what to display
     */
    public void showBattleMessage(String writeThis) {
        flashyText(writeThis);
    }

    /**
     * Get opponent 1 life
     *
     * @return value
     */
    public float getOppLife() {
        return (float) oppLife;
    }

    /**
     * Set opponent 1 life
     *
     * @param Life - value
     */
    public static void setOppLife(int Life) {
        oppLife = Life;
    }

    /**
     * Get opponent 1's maximum life
     *
     * @return value
     */
    public float getOppMaxLife() {
        return (float) oppMaxLife;
    }

    /**
     * Set opponent 1's maximum life
     *
     * @param Life
     */
    public static void setOppMaxLife(int Life) {
        oppMaxLife = Life;
    }

    /**
     * Get opponent 2's current life
     *
     * @return value
     */
    public float getOppLife2() {
        return (float) oppLife2;
    }

    /**
     * Set opponent 2's current life
     *
     * @param Life
     */
    public static void setOppLife2(int Life) {
        oppLife2 = Life;
    }

    /**
     * Get opponent 2's maximum life
     *
     * @return value
     */
    public float getOppMaxLife2() {
        return (float) oppMaxLife2;
    }

    /**
     * Set opponent 2's maximum life
     *
     * @param Life
     */
    public static void setOppMaxLife2(int Life) {
        oppMaxLife2 = Life;
    }

    /**
     * Get player 2's current life
     *
     * @return value
     */
    public float getCharLife3() {
        return (float) charLife3;
    }

    /**
     * Set player 2's current life
     *
     * @param Life
     */
    public static void setCharLife3(int Life) {
        charLife3 = Life;
    }

    /**
     * Get player 2's maximum life
     *
     * @return value
     */
    public float getCharMaxLife3() {
        return (float) charMaxLife3;
    }

    /**
     * Set player 2's maximum life
     *
     * @param Life
     */
    public static void setCharMaxLife3(int Life) {
        charMaxLife3 = Life;
    }

    /**
     * Animate attacks in graphics context
     *
     * @param thischar - active character
     */
    public void AnimatePhyAttax(char thischar) {
        if (LoginScreen.getInstance().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.singlePlayer2)) {
            setSprites('c', 9, 11);
            setSprites('o', 9, 11);
            setSprites('b', 9, 11);
            setSprites('a', 9, 11);
        } else if (thischar == 'c' || thischar == 'o') {
            //sprites back to normal poses
            setSprites('c', 9, 11);
            setSprites('o', 9, 11);
        }
    }

    /**
     * Resets the game after a match is done or cancelled
     */
    public void resetGame() {
        life = maXlife;
        oppLife = oppMaxLife;
        damageMultiplierChar = Characters.getDamageMultiplier('c');
        damageMultiplierOpp = Characters.getDamageMultiplier('o');
        celestiaMultiplierChar = 10;
        celestiaMultiplierOpp = 10;
        limitBreak = 5;
        showBattleMessage("");
    }

    /**
     * Slow down game
     *
     * @param amount - time duration
     */
    public void slowDown(int amount) {
        getGameInstance().sleepy(amount);
    }

    /**
     * Starts an actual fight
     */
    public void startFight() {
        resetGame();
        fpsGen = new ThreadGameInstance(1, this);
        startDrawing = 1;
        comboCounter = 0;
        LoginScreen.getInstance().getMenu().getMain().setGameRunning();
        perCent = 100;
        perCent2 = 100;
        LoginScreen.getInstance().getMenu().getMain().reSize("game");
    }

    public ThreadGameInstance getGameInstance() {
        return fpsGen;
    }

    /**
     * displays damage graphically
     *
     * @param damageAmount - damage dealt
     * @param who          - who dealt the damage
     */
    public void guiScreenChaos(float damageAmount, char who) {
        manipulateThis = "" + Math.round(damageAmount);
        //System.out.println("Damage in percentage "+Math.round(damageAmount));
        if (who == 'c') {
            if (manipulateThis.length() == 1) {
                setP1Damage(Integer.parseInt("" + manipulateThis.charAt(0) + ""), 10, 10, 10);
            }

            if (manipulateThis.length() == 2) {
                setP1Damage(Integer.parseInt("" + manipulateThis.charAt(0) + ""), Integer.parseInt("" + manipulateThis.charAt(1) + ""), 10, 10);
            }

            if (manipulateThis.length() == 3) {
                setP1Damage(Integer.parseInt("" + manipulateThis.charAt(0) + ""), Integer.parseInt("" + manipulateThis.charAt(1) + ""), Integer.parseInt("" + manipulateThis.charAt(2) + ""), 10);
            }

            if (manipulateThis.length() == 4) {
                setP1Damage(Integer.parseInt("" + manipulateThis.charAt(0) + ""), Integer.parseInt("" + manipulateThis.charAt(1) + ""), Integer.parseInt("" + manipulateThis.charAt(2) + ""), Integer.parseInt("" + manipulateThis.charAt(3) + ""));
            }
        }

        if (who == 'o') {
            if (manipulateThis.length() == 1) {
                setP2Damage(Integer.parseInt("" + manipulateThis.charAt(0) + ""), 10, 10, 10);
            }

            if (manipulateThis.length() == 2) {
                setP2Damage(Integer.parseInt("" + manipulateThis.charAt(0) + ""), Integer.parseInt("" + manipulateThis.charAt(1) + ""), 10, 10);
            }

            if (manipulateThis.length() == 3) {
                setP2Damage(Integer.parseInt("" + manipulateThis.charAt(0) + ""), Integer.parseInt("" + manipulateThis.charAt(1) + ""), Integer.parseInt("" + manipulateThis.charAt(2) + ""), 10);
            }

            if (manipulateThis.length() == 4) {
                LoginScreen.getInstance().getMenu().getMain().getGame().setP2Damage(Integer.parseInt("" + manipulateThis.charAt(0) + ""), Integer.parseInt("" + manipulateThis.charAt(1) + ""), Integer.parseInt("" + manipulateThis.charAt(2) + ""), Integer.parseInt("" + manipulateThis.charAt(3) + ""));
            }
        }
    }

    /**
     * Increment combo count
     */
    public void incrimentComboCounter() {
        comboCounter = comboCounter + 1;
        //System.out.println("DUDE :: "+comboCounter);
    }

    /**
     * Remove move from que
     */
    public void unQueMove() {
        if (comboCounter >= 1 && safeToSelect) {
            //change curent index
            comboCounter = comboCounter - 1;

            //ADD points at index
            int moi = Integer.parseInt(attackArray[comboCounter]);
            Characters.alterPoints2(moi);
            System.out.println("UNQUED " + moi);

            //numOfAttacks=numOfAttacks-1;
        }
    }

    /**
     * Scan que for desired attacks
     *
     * @param desiredAttack - id
     * @return status
     */
    public boolean scanPhyQue(int desiredAttack) {
        isMoveQued = false;

        for (int x = 0; x <= 3; x++) //loops 4 times
        {
            if (Integer.parseInt(attackArray[x]) == desiredAttack) {
                isMoveQued = true;
            }
        }

        return isMoveQued;
    }

    /**
     * update Player 1 life
     *
     * @param thisMuch - value
     */
    public void updateLife(int thisMuch) {
        int thisMuch2 = celestiaMultiplierChar * thisMuch;
        life = life + thisMuch2;
        daNum = ((getCharLife() / getCharMaxLife()) * 100); //perc life x life bar length
        lifePlain = Math.round(daNum); // round off
        lifeTotalPlain = Math.round(getCharLife()); // for text
        perCent = Math.round(lifePlain);
        Characters.setCurrLifeOpp(perCent2);
        Characters.setCurrLifeChar(perCent);
    }

    /**
     * update opponent 1 life
     *
     * @param thisMuch - value
     */
    public void updateOppLife(int thisMuch) {
        int thisMuch2 = celestiaMultiplierOpp * thisMuch;
        oppLife = oppLife + thisMuch2;
        daNum2 = ((getOppLife() / getOppMaxLife()) * 100); //perc life x life bar length
        lifePlain2 = Math.round(daNum2); // round off
        lifeTotalPlain2 = Math.round(getOppLife()); // for text
        perCent2 = Math.round(lifePlain2);
        Characters.setCurrLifeOpp(perCent2);
        Characters.setCurrLifeChar(perCent);
    }

    /**
     * update opponent 2 life
     *
     * @param thisMuch - value
     */
    public void updateOppLife2(int thisMuch) {
        int thisMuch2 = celestiaMultiplierOpp * thisMuch;
        oppLife2 = oppLife2 + thisMuch2;
        daNum2a = ((getOppLife2() / getOppMaxLife2()) * 100); //perc life x life bar length
        lifePlain2a = Math.round(daNum2a); // round off
        lifeTotalPlain2a = Math.round(getOppLife2()); // for text
        Characters.setCurrLifeChar2(perCent3a);
        Characters.setCurrLifeChar(perCent);
        Characters.setCurrLifeOpp2(perCent2a);
        Characters.setCurrLifeOpp(perCent2);
    }

    /**
     * update Player 2 life
     *
     * @param thisMuch - value
     */
    public void updateOppLife3(int thisMuch) {
        int thisMuch2 = celestiaMultiplierOpp * thisMuch;
        charLife3 = charLife3 + thisMuch2;
        daNum3a = ((getCharLife3() / getCharMaxLife3()) * 100); //perc life x life bar length
        lifePlain2a = Math.round(daNum3a); // round off
        lifeTotalPlain2a = Math.round(getCharLife3()); // for text
        perCent3a = Math.round(lifePlain3a);
        Characters.setCurrLifeChar2(perCent3a);
        Characters.setCurrLifeChar(perCent);
        Characters.setCurrLifeOpp2(perCent2a);
        Characters.setCurrLifeOpp(perCent2);
    }

    /**
     * Get the Characters life, these methods should be float as they are used in divisions
     *
     * @return Characters life
     */
    public float getCharLife() {
        return (float) life;
    }

    /**
     * Get the Characters max life, these methods should be float as they are used in divisions
     *
     * @return Characters maximum life
     */
    public float getCharMaxLife() {
        return (float) maXlife;
    }

    /**
     * prepare panel
     */
    protected void initializePanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.black, 1));
    }

    /**
     * Resume paused game
     */
    public void start() {
        fpsGen.resumeGame();
    }

    /**
     * Alter damage multipliers, used to strengthen/weaken attacks
     *
     * @param per      the person calling the method
     * @param thisMuch the number to alter by
     */
    public void alterDamageCounter(char per, int thisMuch) {
        if (per == 'c' && damageMultiplierOpp > 0 && damageMultiplierOpp < 20) {
            damageMultiplierOpp = damageMultiplierOpp + thisMuch;
        }

        if (per == 'o' && damageMultiplierChar > 0 && damageMultiplierChar < 20) {
            damageMultiplierChar = damageMultiplierChar + thisMuch;
        }
    }

    /**
     * Alter celestia multipliers, used to strengthen/weaken celestia attacks
     *
     * @param per      the person calling the method
     * @param thisMuch the number to alter by
     */
    public void alterCelestiaCounter(char per, int thisMuch) {
        if (per == 'c' && celestiaMultiplierOpp > 0 && celestiaMultiplierOpp < 16) {
            celestiaMultiplierOpp = celestiaMultiplierOpp + thisMuch;
        }

        if (per == 'o' && celestiaMultiplierChar > 0 && celestiaMultiplierChar < 16) {
            celestiaMultiplierChar = celestiaMultiplierChar + thisMuch;
        }
    }

    /**
     * Sets limit back to initial value
     */
    public void resetBreak() {
        limitBreak = 5;
    }

    /**
     * Determines if the player has won
     *
     * @return match status
     */
    public boolean hasWon() {
        return finalCharLife > finalOppLife;
    }
}

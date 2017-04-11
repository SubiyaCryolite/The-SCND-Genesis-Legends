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
import com.scndgen.legends.drawing.DrawCharSel;
import com.scndgen.legends.drawing.DrawGame;
import com.scndgen.legends.engine.JenesisLanguage;
import com.scndgen.legends.threads.ThreadMP3;
import com.scndgen.legends.windows.WindowMain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuCharSelect extends DrawCharSel implements ActionListener {

    public static int characterSel, opponentSel;
    public static String charDesc = "";
    public static String oppName, charName;
    public static int[] allPlayers = new int[LoginScreen.getLoginScreen().charNames.length];
    public static int oppPrevLoc, charPrevLoc;
    private static JenesisLanguage lang;
    private static int[] arr1, arr2, arr3, arr4, arr5;
    private static int[] arr1a, arr2a, arr3a, arr4a, arr5a;
    private static int[] arr1b, arr2b, arr3b, arr4b, arr5b;
    private static int[] attacks;
    private static int storyBoards;
    public boolean proceed1 = false, proceed2 = false, isAnimatorNotRuning = true;
    public int selectedCharIndex = 0, selectedOppIndex = 0;
    private Object source;
    //private static DrawStageSel charVisual;
    private ThreadMP3 sound, sound2, error;
    private Characters players;

    /**
     * Initialises the character select panel
     */
    public MenuCharSelect() {
        initializePanel();
    }

    /**
     * Disables all buttons, used in online and story modes
     */
    public static void disableAll() {
        for (int u = 0; u < allPlayers.length; u++) {
            allPlayers[u] = 1;
        }
    }

    /**
     * Get the storyboard size for the character
     *
     * @return the number of levels
     */
    public static int getStorySize() {
        return storyBoards;
    }

    /**
     * Set the number of setpieces
     *
     * @param size
     */
    public static void setStoryBoards(int size) {
        storyBoards = size;
    }

    /**
     * Set 5 AI personalities
     *
     * @param array - the movws
     * @param num   - the number
     */
    public static void setAISlot(int[] array, int num) {
        if (num == 1) {
            arr1 = array;
        }

        if (num == 2) {
            arr2 = array;
        }

        if (num == 3) {
            arr3 = array;
        }

        if (num == 4) {
            arr4 = array;
        }

        if (num == 5) {
            arr5 = array;
        }
    }

    /**
     * Set 5 AI personalities
     *
     * @param array - the movws
     * @param num   - the number
     */
    public static void setAISlot2(int[] array, int num) {
        if (num == 1) {
            arr1a = array;
        }

        if (num == 2) {
            arr2a = array;
        }

        if (num == 3) {
            arr3a = array;
        }

        if (num == 4) {
            arr4a = array;
        }

        if (num == 5) {
            arr5a = array;
        }
    }

    /**
     * Set 5 AI personalities
     *
     * @param array - the movws
     * @param num   - the number
     */
    public static void setAISlot3(int[] array, int num) {
        if (num == 1) {
            arr1b = array;
        }

        if (num == 2) {
            arr2b = array;
        }

        if (num == 3) {
            arr3b = array;
        }

        if (num == 4) {
            arr4b = array;
        }

        if (num == 5) {
            arr5b = array;
        }
    }

    /**
     * Get char AI
     *
     * @return AI - Personality
     */
    public static int[] getAISlot() {
        int[] array = {};
        //when doing well, all attacks
        if (LoginScreen.getLoginScreen().getMenu().getMain().getGame().getOppLife() / LoginScreen.getLoginScreen().getMenu().getMain().getGame().getOppMaxLife() >= 1.00) {
            array = arr1;
        } //when doing well, all attacks + 2 buffs
        else if (LoginScreen.getLoginScreen().getMenu().getMain().getGame().getOppLife() / LoginScreen.getLoginScreen().getMenu().getMain().getGame().getOppMaxLife() >= 0.75 && LoginScreen.getLoginScreen().getMenu().getMain().getGame().getOppLife() / LoginScreen.getLoginScreen().getMenu().getMain().getGame().getOppMaxLife() < 1.00) {
            array = arr2;
        } //when doing well, 4 attacks + 2 buffs
        else if (LoginScreen.getLoginScreen().getMenu().getMain().getGame().getOppLife() / LoginScreen.getLoginScreen().getMenu().getMain().getGame().getOppMaxLife() >= 0.50 && LoginScreen.getLoginScreen().getMenu().getMain().getGame().getOppLife() / LoginScreen.getLoginScreen().getMenu().getMain().getGame().getOppMaxLife() < 0.75) {
            if (MenuGameRender.getBreak() == 1000 && DrawGame.limitRunning) {
                LoginScreen.getLoginScreen().getMenu().getMain().triggerFury('o');
                array = new int[]{0, 0, 0, 0};
            } else {
                array = arr3;
            }
        } //when doing well, 4 buffs + 2 moves
        else if (LoginScreen.getLoginScreen().getMenu().getMain().getGame().getOppLife() / LoginScreen.getLoginScreen().getMenu().getMain().getGame().getOppMaxLife() >= 0.25 && LoginScreen.getLoginScreen().getMenu().getMain().getGame().getOppLife() / LoginScreen.getLoginScreen().getMenu().getMain().getGame().getOppMaxLife() < 0.50) {
            if (MenuGameRender.getBreak() == 1000 && DrawGame.limitRunning) {
                LoginScreen.getLoginScreen().getMenu().getMain().triggerFury('o');
                array = new int[]{0, 0, 0, 0};
            } else {
                array = arr4;
            }
        } //first fury, when doing well, 4 buffs + 2 moves
        else {
            if (MenuGameRender.getBreak() == 1000 && DrawGame.limitRunning) {
                LoginScreen.getLoginScreen().getMenu().getMain().triggerFury('o');
                array = new int[]{0, 0, 0, 0};
            } else {
                array = arr5;
            }
        }

        return array;
    }

    /**
     * Get char AI
     *
     * @return AI - Personality
     */
    public static int[] getAISlot2() {
        int[] array = {};

        //when doing well, all attacks
        if (LoginScreen.getLoginScreen().getMenu().getMain().getGame().getOppLife2() / LoginScreen.getLoginScreen().getMenu().getMain().getGame().getOppMaxLife2() >= 1.00) {
            array = arr1a;
        } //when doing well, all attacks + 2 buffs
        else if (LoginScreen.getLoginScreen().getMenu().getMain().getGame().getOppLife2() / LoginScreen.getLoginScreen().getMenu().getMain().getGame().getOppMaxLife2() >= 0.75 && LoginScreen.getLoginScreen().getMenu().getMain().getGame().getOppLife2() / LoginScreen.getLoginScreen().getMenu().getMain().getGame().getOppMaxLife2() < 1.00) {
            array = arr2a;
        } //when doing well, 4 attacks + 2 buffs
        else if (LoginScreen.getLoginScreen().getMenu().getMain().getGame().getOppLife2() / LoginScreen.getLoginScreen().getMenu().getMain().getGame().getOppMaxLife2() >= 0.50 && LoginScreen.getLoginScreen().getMenu().getMain().getGame().getOppLife2() / LoginScreen.getLoginScreen().getMenu().getMain().getGame().getOppMaxLife2() < 0.75) {
            if (MenuGameRender.getBreak() == 1000 && DrawGame.limitRunning) {
                LoginScreen.getLoginScreen().getMenu().getMain().triggerFury('b');
                array = new int[]{0, 0, 0, 0};
            } else {
                array = arr3a;
            }
        } //when doing well, 4 buffs + 2 moves
        else if (LoginScreen.getLoginScreen().getMenu().getMain().getGame().getOppLife2() / LoginScreen.getLoginScreen().getMenu().getMain().getGame().getOppMaxLife2() >= 0.25 && LoginScreen.getLoginScreen().getMenu().getMain().getGame().getOppLife2() / LoginScreen.getLoginScreen().getMenu().getMain().getGame().getOppMaxLife2() < 0.50) {
            if (MenuGameRender.getBreak() == 1000 && DrawGame.limitRunning) {
                LoginScreen.getLoginScreen().getMenu().getMain().triggerFury('b');
                array = new int[]{0, 0, 0, 0};
            } else {
                array = arr4a;
            }
        } //first fury, when doing well, 4 buffs + 2 moves
        else {
            if (MenuGameRender.getBreak() == 1000 && DrawGame.limitRunning) {
                LoginScreen.getLoginScreen().getMenu().getMain().triggerFury('b');
                array = new int[]{0, 0, 0, 0};
            } else {
                array = arr5a;
            }
        }

        return array;
    }

    /**
     * Get char AI
     *
     * @return AI - Personality
     */
    public static int[] getAISlot3() {
        int[] array = {};

        //when doing well, all attacks
        if (LoginScreen.getLoginScreen().getMenu().getMain().getGame().getCharLife3() / LoginScreen.getLoginScreen().getMenu().getMain().getGame().getCharMaxLife3() >= 1.00) {
            array = arr1a;
        } //when doing well, all attacks + 2 buffs
        else if (LoginScreen.getLoginScreen().getMenu().getMain().getGame().getCharLife3() / LoginScreen.getLoginScreen().getMenu().getMain().getGame().getCharMaxLife3() >= 0.75 && LoginScreen.getLoginScreen().getMenu().getMain().getGame().getCharLife3() / LoginScreen.getLoginScreen().getMenu().getMain().getGame().getCharMaxLife3() < 1.00) {
            array = arr2a;
        } //when doing well, 4 attacks + 2 buffs
        else if (LoginScreen.getLoginScreen().getMenu().getMain().getGame().getCharLife3() / LoginScreen.getLoginScreen().getMenu().getMain().getGame().getCharMaxLife3() >= 0.50 && LoginScreen.getLoginScreen().getMenu().getMain().getGame().getCharLife3() / LoginScreen.getLoginScreen().getMenu().getMain().getGame().getCharMaxLife3() < 0.75) {
            if (MenuGameRender.getBreak() == 1000 && DrawGame.limitRunning) {
                LoginScreen.getLoginScreen().getMenu().getMain().triggerFury('a');
                array = new int[]{0, 0, 0, 0};
            } else {
                array = arr3a;
            }
        } //when doing well, 4 buffs + 2 moves
        else if (LoginScreen.getLoginScreen().getMenu().getMain().getGame().getCharLife3() / LoginScreen.getLoginScreen().getMenu().getMain().getGame().getCharMaxLife3() >= 0.25 && LoginScreen.getLoginScreen().getMenu().getMain().getGame().getCharLife3() / LoginScreen.getLoginScreen().getMenu().getMain().getGame().getCharMaxLife3() < 0.50) {
            if (MenuGameRender.getBreak() == 1000 && DrawGame.limitRunning) {
                LoginScreen.getLoginScreen().getMenu().getMain().triggerFury('a');
                array = new int[]{0, 0, 0, 0};
            } else {
                array = arr4a;
            }
        } //first fury, when doing well, 4 buffs + 2 moves
        else {
            if (MenuGameRender.getBreak() == 1000 && DrawGame.limitRunning) {
                LoginScreen.getLoginScreen().getMenu().getMain().triggerFury('a');
                array = new int[]{0, 0, 0, 0};
            } else {
                array = arr5a;
            }
        }

        return array;
    }

    public static void setAttacks(int attackNUm, int frames) {
        attacks[attackNUm] = frames;
    }

    /**
     * Load attacks
     *
     * @return
     */
    public static int getAttacks() {
        return attacks.length;
    }

    /**
     * Get number of frames
     *
     * @param index - the attack
     * @return - the number of frames
     */
    public static int getAttack(int index) {
        return attacks[index];
    }

    /**
     * Animates cloud
     */
    public static void animCloud() {
    }

    /**
     * Depending on mode, sets
     */
    private static void sortMode(char who) {
        /**
         * If you choose a character in lan, you can't choose your opponent
         */
        if (who == 'c') {
        }
    }

    public Characters getPayers() {
        return players;
    }

    /**
     * Build the panel
     */
    protected void initializePanel() {
        setLayout(new BorderLayout());
        //add(charSelect(), BorderLayout.CENTER);
        players = new Characters();
        lang = LoginScreen.getLoginScreen().getLangInst();
        attacks = new int[4];
        setBorder(BorderFactory.createLineBorder(Color.black, 1));
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        source = ae.getSource();
    }

    /**
     * Goes back to main menu
     */
    public void backToMenu() {
        refreshSelections();
        //cancel hosting
        if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equals(WindowMain.lanHost)) {
            LoginScreen.getLoginScreen().getMenu().getMain().closeTheServer();
        } else if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equals(WindowMain.lanClient)) {
            LoginScreen.getLoginScreen().getMenu().getMain().closeTheClient();
        } else if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equals(WindowMain.storyMode)) {
            LoginScreen.getLoginScreen().getMenu().getMain().getStory().getStoryInstance().skipDialogue();
        }
        LoginScreen.getLoginScreen().getMenu().getMain().backToMenuScreen();
        if (LoginScreen.getLoginScreen().getMenu().getMain().isGameInstanciated()) {
            LoginScreen.getLoginScreen().getMenu().getMain().getGame().closeAudio();
        }
    }

    /**
     * Starts a new game
     */
    public void beginGame() {
        if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.singlePlayer) || LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanHost) || LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase("offline2")) {
            LoginScreen.getLoginScreen().getMenu().getMain().selectStage();

            if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanHost)) {
                LoginScreen.getLoginScreen().getMenu().getMain().sendToClient("watchStageSel_xcbD");
            }
        }

    }

    /**
     * Refresh selections
     */
    public void refreshSelections() {
        charPrevLoc = 0;
        oppPrevLoc = 0;
        //LoginScreen.getLoginScreen().getMenu().getMain().repaintCharSel();
        for (int u = 0; u < allPlayers.length; u++) {
            allPlayers[u] = 0; // 0 means free, 1 means selected
        }
        proceed1 = false;
        proceed2 = false;
        //selectedCharIndex=0;
        //selectedOppIndex=0;
        LoginScreen.getLoginScreen().getMenu().getMain().storedX = 99;
        LoginScreen.getLoginScreen().getMenu().getMain().storedY = 99;
    }

    /**
     * Get the Characters name
     *
     * @return character name
     */
    public String getCharName() {
        return charName;
    }

    /**
     * Get the opponents name
     *
     * @return opponent name
     */
    public String getOppName() {
        return oppName;
    }

    /**
     * Gets a screen shot
     */
    public void getScreeny() {
        captureScreenShot();
    }

    /**
     * Selecting Raila
     *
     * @param type - opponent ('o') or character ('c')
     */
    public void selRaila(char type) {
        systemNotice(lang.getLine(84));
        if (type == 'c') //when selecting char
        {
            {
                sound = new ThreadMP3(ThreadMP3.charSelectSound(), false);
                sound.play();
                proceed1 = true;
                charPrevLoc = 0;
                charName = "Raila";
                getPayers().prepare(2);
                selectedCharIndex = 0;
                //LoginScreen.getLoginScreen().getMenu().getMain().repaintCharSel();
                allPlayers[0] = 1;
                if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanHost)) {
                    LoginScreen.getLoginScreen().getMenu().getMain().sendToClient("selRai_jkxc");
                    disableAll();
                }
                if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanClient)) {
                    LoginScreen.getLoginScreen().getMenu().getMain().sendToServer("selRai_jkxc");
                    disableAll();
                }
            }
        } else if (type == 'o' && proceed2 == false) {
            sound2 = new ThreadMP3(ThreadMP3.charSelectSound(), false);
            sound2.play();
            if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanClient)) {
            }
            proceed2 = true;
            oppName = "Raila";
            selectedOppIndex = 0;
            getPayers().prepareO(2);
            oppPrevLoc = 0;
            //LoginScreen.getLoginScreen().getMenu().getMain().repaintCharSel();
            allPlayers[0] = 1;
        }
    }

    /**
     * Selecting Subiya
     *
     * @param type - opponent ('o') or character ('c')
     */
    public void selSubiya(char type) {
        if (type == 'c') //when selecting char
        {
            systemNotice(lang.getLine(85));
            {
                sound = new ThreadMP3(ThreadMP3.charSelectSound(), false);
                sound.play();
                proceed1 = true;
                charPrevLoc = 1;
                charName = "Subiya";
                selectedCharIndex = 1;
                getPayers().prepare(1);
                charDesc = getPayers().getDudeChar().getDescSmall();
                //LoginScreen.getLoginScreen().getMenu().getMain().repaintCharSel();
                allPlayers[1] = 1;
                if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanHost)) {
                    LoginScreen.getLoginScreen().getMenu().getMain().sendToClient("selSub_jkxc");
                    disableAll();
                }
                if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanClient)) {
                    LoginScreen.getLoginScreen().getMenu().getMain().sendToServer("selSub_jkxc");
                    disableAll();
                }
            }
        }

        if (type == 'o' && proceed2 == false) // when selecting opponent
        {
            sound2 = new ThreadMP3(ThreadMP3.charSelectSound(), false);
            sound2.play();
            proceed2 = true;
            oppName = "Subiya";
            selectedOppIndex = 1;
            getPayers().prepareO(1);
            oppPrevLoc = 1;
            //LoginScreen.getLoginScreen().getMenu().getMain().repaintCharSel();
            allPlayers[1] = 1;
        }
    }

    /**
     * Selecting Lynx
     *
     * @param type - opponent ('o') or character ('c')
     */
    public void selLynx(char type) {
        systemNotice(lang.getLine(86));
        if (type == 'c') //when selecting char
        {
            sound = new ThreadMP3(ThreadMP3.charSelectSound(), false);
            sound.play();
            proceed1 = true;
            charPrevLoc = 2;
            charName = "Lynx";
            selectedCharIndex = 2;
            getPayers().prepare(3);
            charDesc = getPayers().getDudeChar().getDescSmall();
            //LoginScreen.getLoginScreen().getMenu().getMain().repaintCharSel();
            allPlayers[2] = 1;
            if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanHost)) {
                LoginScreen.getLoginScreen().getMenu().getMain().sendToClient("selLyn_jkxc");
                disableAll();
            }
            if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanClient)) {
                LoginScreen.getLoginScreen().getMenu().getMain().sendToServer("selLyn_jkxc");
                disableAll();
            }

            //if(LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.StoryMode))
            //{
            //   Lynx.story(1,false);
            //   disableAll();
            //   {/*confirm.setEnabled(true)*/;}
            //}

        }

        if (type == 'o' && proceed2 == false) // when selecting opponent
        {
            sound2 = new ThreadMP3(ThreadMP3.charSelectSound(), false);
            sound2.play();
            proceed2 = true;
            oppName = "Lynx";
            selectedOppIndex = 2;
            getPayers().prepareO(3);
            oppPrevLoc = 2;
            //LoginScreen.getLoginScreen().getMenu().getMain().repaintCharSel();
            allPlayers[2] = 1;
        }
    }

    /**
     * Selecting Aisha
     *
     * @param type - opponent ('o') or character ('c')
     */
    public void selAisha(char type) {
        systemNotice(lang.getLine(87));
        if (type == 'c') //when selecting char
        {
            sound = new ThreadMP3(ThreadMP3.charSelectSound(), false);
            sound.play();
            proceed1 = true;
            charPrevLoc = 3;
            charName = "Aisha";
            selectedCharIndex = 3;
            getPayers().prepare(4);
            charDesc = getPayers().getDudeChar().getDescSmall();
            //LoginScreen.getLoginScreen().getMenu().getMain().repaintCharSel();
            allPlayers[3] = 1;
            if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanHost)) {
                LoginScreen.getLoginScreen().getMenu().getMain().sendToClient("selAlx_jkxc");
                disableAll();
            }
            if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanClient)) {
                LoginScreen.getLoginScreen().getMenu().getMain().sendToServer("selAlx_jkxc");
                disableAll();
            }
        }

        if (type == 'o' && proceed2 == false) // when selecting opponent
        {
            sound2 = new ThreadMP3(ThreadMP3.charSelectSound(), false);
            sound2.play();
            proceed2 = true;
            oppName = "Aisha";
            selectedOppIndex = 3;
            getPayers().prepareO(4);
            oppPrevLoc = 3;
            //LoginScreen.getLoginScreen().getMenu().getMain().repaintCharSel();
            allPlayers[3] = 1;
        }
    }

    /**
     * Selecting Ade
     *
     * @param type - opponent ('o') or character ('c')
     */
    public void selAde(char type) {
        systemNotice(lang.getLine(88));
        if (type == 'c') //when selecting char
        {
            sound = new ThreadMP3(ThreadMP3.charSelectSound(), false);
            sound.play();
            proceed1 = true;
            charPrevLoc = 5;
            charName = "Ade";
            selectedCharIndex = 5;
            getPayers().prepare(6);
            charDesc = getPayers().getDudeChar().getDescSmall();
            //LoginScreen.getLoginScreen().getMenu().getMain().repaintCharSel();
            allPlayers[5] = 1;
            if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanHost)) {
                LoginScreen.getLoginScreen().getMenu().getMain().sendToClient("selAde_jkxc");
                disableAll();
            }
            if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanClient)) {
                LoginScreen.getLoginScreen().getMenu().getMain().sendToServer("selAde_jkxc");
                disableAll();
            }
        }

        if (type == 'o' && proceed2 == false) // when selecting opponent
        {
            sound2 = new ThreadMP3(ThreadMP3.charSelectSound(), false);
            sound2.play();
            proceed2 = true;
            oppName = "Ade";
            selectedOppIndex = 5;
            getPayers().prepareO(6);
            oppPrevLoc = 5;
            //LoginScreen.getLoginScreen().getMenu().getMain().repaintCharSel();
            allPlayers[5] = 1;
        }
    }

    /**
     * Selecting Aisha
     *
     * @param type - opponent ('o') or character ('c')
     */
    public void selRav(char type) {
        systemNotice(lang.getLine(89));
        if (type == 'c') //when selecting char
        {
            sound = new ThreadMP3(ThreadMP3.charSelectSound(), false);
            sound.play();
            proceed1 = true;
            charPrevLoc = 4;
            charName = "Ravage";
            selectedCharIndex = 4;
            getPayers().prepare(5);
            charDesc = getPayers().getDudeChar().getDescSmall();
            //LoginScreen.getLoginScreen().getMenu().getMain().repaintCharSel();
            allPlayers[4] = 0;
            if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanHost)) {
                LoginScreen.getLoginScreen().getMenu().getMain().sendToClient("selRav_jkxc");
                disableAll();
            }
            if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanClient)) {
                LoginScreen.getLoginScreen().getMenu().getMain().sendToServer("selRav_jkxc");
                disableAll();
            }
        }

        if (type == 'o' && proceed2 == false) // when selecting opponent
        {
            sound2 = new ThreadMP3(ThreadMP3.charSelectSound(), false);
            sound2.play();
            proceed2 = true;
            oppName = "Ravage";
            selectedOppIndex = 4;
            getPayers().prepareO(5);
            oppPrevLoc = 4;
            //LoginScreen.getLoginScreen().getMenu().getMain().repaintCharSel();
            allPlayers[4] = 0;
        }
    }

    /**
     * Selecting Jonah
     *
     * @param type - opponent ('o') or character ('c')
     */
    public void selJon(char type) {
        systemNotice(lang.getLine(90));
        if (type == 'c') //when selecting char
        {
            sound = new ThreadMP3(ThreadMP3.charSelectSound(), false);
            sound.play();
            proceed1 = true;
            charPrevLoc = 6;
            charName = "Jonah";
            selectedCharIndex = 6;
            getPayers().prepare(7);
            charDesc = getPayers().getDudeChar().getDescSmall();
            allPlayers[6] = 1;
            if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanHost)) {
                LoginScreen.getLoginScreen().getMenu().getMain().sendToClient("selJon_jkxc");
                disableAll();
            }
            if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanClient)) {
                LoginScreen.getLoginScreen().getMenu().getMain().sendToServer("selJon_jkxc");
                disableAll();
            }
        }

        if (type == 'o' && proceed2 == false) // when selecting opponent
        {
            sound2 = new ThreadMP3(ThreadMP3.charSelectSound(), false);
            sound2.play();
            proceed2 = true;
            oppName = "Jonah";
            selectedOppIndex = 6;
            getPayers().prepareO(7);
            oppPrevLoc = 6;
            //LoginScreen.getLoginScreen().getMenu().getMain().repaintCharSel();
            allPlayers[6] = 1;
        }
    }

    /**
     * Selecting NovaAdam
     *
     * @param type - opponent ('o') or character ('c')
     */
    public void selAdam(char type) {
        systemNotice(lang.getLine(91));
        if (type == 'c') //when selecting char
        {
            sound = new ThreadMP3(ThreadMP3.charSelectSound(), false);
            sound.play();
            proceed1 = true;
            charPrevLoc = 7;
            charName = "NovaAdam";
            selectedCharIndex = 7;
            getPayers().prepare(8);
            charDesc = getPayers().getDudeChar().getDescSmall();
            //LoginScreen.getLoginScreen().getMenu().getMain().repaintCharSel();
            allPlayers[7] = 1;
            if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanHost)) {
                LoginScreen.getLoginScreen().getMenu().getMain().sendToClient("selAdam_jkxc");
                disableAll();
            }
            if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanClient)) {
                LoginScreen.getLoginScreen().getMenu().getMain().sendToServer("selAdam_jkxc");
                disableAll();
            }
        }

        if (type == 'o' && proceed2 == false) // when selecting opponent
        {
            sound2 = new ThreadMP3(ThreadMP3.charSelectSound(), false);
            sound2.play();
            proceed2 = true;
            oppName = "NovaAdam";
            selectedOppIndex = 7;
            getPayers().prepareO(8);
            oppPrevLoc = 7;
            allPlayers[7] = 1;
        }
    }

    /**
     * Selecting NOVA NovaAdam
     *
     * @param type - opponent ('o') or character ('c')
     */
    public void selNOVAAdam(char type) {
        systemNotice(lang.getLine(92));
        if (type == 'c') //when selecting char
        {
            sound = new ThreadMP3(ThreadMP3.charSelectSound(), false);
            sound.play();
            proceed1 = true;
            charPrevLoc = 8;
            charName = "NOVA NovaAdam";
            selectedCharIndex = 8;
            getPayers().prepare(9);
            charDesc = getPayers().getDudeChar().getDescSmall();
            //LoginScreen.getLoginScreen().getMenu().getMain().repaintCharSel();
            allPlayers[8] = 1;
            if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanHost)) {
                LoginScreen.getLoginScreen().getMenu().getMain().sendToClient("selNOVAAdam_jkxc");
                disableAll();
            }
            if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanClient)) {
                LoginScreen.getLoginScreen().getMenu().getMain().sendToServer("selNOVAAdam_jkxc");
                disableAll();
            }
        }

        if (type == 'o' && proceed2 == false) // when selecting opponent
        {
            sound2 = new ThreadMP3(ThreadMP3.charSelectSound(), false);
            sound2.play();
            proceed2 = true;
            oppName = "NOVA NovaAdam";
            selectedOppIndex = 8;
            getPayers().prepareO(9);
            oppPrevLoc = 8;
            //LoginScreen.getLoginScreen().getMenu().getMain().repaintCharSel();
            allPlayers[8] = 1;
        }
    }

    /**
     * Selecting Azaria
     *
     * @param type - opponent ('o') or character ('c')
     */
    public void selAza(char type) {
        systemNotice(lang.getLine(93));
        if (type == 'c') //when selecting char
        {
            sound = new ThreadMP3(ThreadMP3.charSelectSound(), false);
            sound.play();
            proceed1 = true;
            charPrevLoc = 9;
            charName = "Azaria";
            selectedCharIndex = 9;
            getPayers().prepare(10);
            charDesc = getPayers().getDudeChar().getDescSmall();
            //LoginScreen.getLoginScreen().getMenu().getMain().repaintCharSel();
            allPlayers[9] = 1;
            if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanHost)) {
                LoginScreen.getLoginScreen().getMenu().getMain().sendToClient("selAzaria_jkxc");
                disableAll();
            }
            if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanClient)) {
                LoginScreen.getLoginScreen().getMenu().getMain().sendToServer("selAzaria_jkxc");
                disableAll();
            }
        }

        if (type == 'o' && proceed2 == false) // when selecting opponent
        {
            sound2 = new ThreadMP3(ThreadMP3.charSelectSound(), false);
            sound2.play();
            proceed2 = true;
            oppName = "Azaria";
            selectedOppIndex = 9;
            getPayers().prepareO(10);
            oppPrevLoc = 9;
            //LoginScreen.getLoginScreen().getMenu().getMain().repaintCharSel();
            allPlayers[9] = 1;
        }
    }

    /**
     * Selecting Sorrowe
     *
     * @param type - opponent ('o') or character ('c')
     */
    public void selSorr(char type) {
        systemNotice(lang.getLine(94));
        if (type == 'c') //when selecting char
        {
            sound = new ThreadMP3(ThreadMP3.charSelectSound(), false);
            sound.play();
            proceed1 = true;
            charPrevLoc = 10;
            charName = "Sorrowe";
            selectedCharIndex = 10;
            getPayers().prepare(11);
            charDesc = getPayers().getDudeChar().getDescSmall();
            //LoginScreen.getLoginScreen().getMenu().getMain().repaintCharSel();
            allPlayers[10] = 1;
            if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanHost)) {
                LoginScreen.getLoginScreen().getMenu().getMain().sendToClient("selSorr_jkxc");
                disableAll();
            }
            if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanClient)) {
                LoginScreen.getLoginScreen().getMenu().getMain().sendToServer("selSorr_jkxc");
                disableAll();
            }
        }

        if (type == 'o' && proceed2 == false) // when selecting opponent
        {
            sound2 = new ThreadMP3(ThreadMP3.charSelectSound(), false);
            sound2.play();
            proceed2 = true;
            oppName = "Sorrowe";
            selectedOppIndex = 10;
            getPayers().prepareO(11);
            oppPrevLoc = 10;
            //LoginScreen.getLoginScreen().getMenu().getMain().repaintCharSel();
            allPlayers[10] = 1;
        }
    }

    /**
     * Selecting Sorrowe
     *
     * @param type - opponent ('o') or character ('c')
     */
    public void selThing(char type) {
        systemNotice("..........");
        if (type == 'c') //when selecting char
        {
            sound = new ThreadMP3(ThreadMP3.charSelectSound(), false);
            sound.play();
            proceed1 = true;
            charPrevLoc = 11;
            charName = "The Thing";
            selectedCharIndex = 11;
            getPayers().prepare(12);
            charDesc = getPayers().getDudeChar().getDescSmall();
            //LoginScreen.getLoginScreen().getMenu().getMain().repaintCharSel();
            allPlayers[11] = 1;
            if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanHost)) {
                LoginScreen.getLoginScreen().getMenu().getMain().sendToClient("selThi_jkxc");
                disableAll();
            }
            if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanClient)) {
                LoginScreen.getLoginScreen().getMenu().getMain().sendToServer("selThi_jkxc");
                disableAll();
            }
        }

        if (type == 'o' && proceed2 == false) // when selecting opponent
        {
            sound2 = new ThreadMP3(ThreadMP3.charSelectSound(), false);
            sound2.play();
            proceed2 = true;
            oppName = "The Thing";
            selectedOppIndex = 11;
            getPayers().prepareO(12);
            oppPrevLoc = 11;
            //LoginScreen.getLoginScreen().getMenu().getMain().repaintCharSel();
            allPlayers[11] = 1;
        }

        if (type == 'x' && proceed2 == false) // when selecting opponent as boss
        {
            sound2 = new ThreadMP3(ThreadMP3.charSelectSound(), false);
            sound2.play();
            proceed2 = true;
            oppName = "The Thing";
            selectedOppIndex = 11;
            getPayers().prepareO(13);
            oppPrevLoc = 11;
            //LoginScreen.getLoginScreen().getMenu().getMain().repaintCharSel();
            allPlayers[11] = 1;
        }
    }

    public void proceed2False() {
        proceed2 = false;
    }

    public void animateCharSelect() {
        new Thread() {

            @Override
            @SuppressWarnings("static-access")
            public void run() {
                this.setName("Character select bg animation thread");
                if (isAnimatorNotRuning) {
                    do {
                        isAnimatorNotRuning = false;
                        try {
                            for (int x = 0; x > (-1440 + 852); x++) {
                                {
                                    this.sleep(0033);
                                    this.setName("What am I doing");
                                    xCordCloud = xCordCloud - 3;
                                    xCordCloud2 = xCordCloud2 - 5;
                                    if (xCordCloud < -960) {
                                        xCordCloud = 852;
                                    }
                                    if (xCordCloud2 < -960) {
                                        xCordCloud2 = 852;
                                    }
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    while (LoginScreen.getLoginScreen().getMenu().getMain().currentScreen.equalsIgnoreCase("charSelectScreen"));
                }

            }
        }.start();
    }

    public void setItem() {
        currentSlot = (verticalRows * vIndex) + hIndex;
        System.out.println("Current Slot: " + currentSlot);
    }

    /**
     * Character select screen, move up
     */
    public void moveUp() {
        upMove();
    }

    /**
     * Character select screen, move down
     */
    public void moveDown() {
        downMove();
    }

    /**
     * Character select screen, move right
     */
    public void moveRight() {
        rightMove();
    }

    /**
     * Character select screen, move left
     */
    public void moveLeft() {
        leftMove();
    }

    /**
     * Gets the number of columns in the character select screen
     *
     * @return number of columns
     */
    public int getNumberOfCharColumns() {
        return horizColumns;
    }

    /**
     * Gets the char caption spacer
     *
     * @return spacer
     */
    public int getCharHSpacer() {
        return vSpacer;
    }

    /**
     * Gets the char caption spacer
     *
     * @return spacer
     */
    public int getCharVSpacer() {
        return hSpacer;
    }

    /**
     * Get starting x coordinate
     *
     * @return starting x coordinate
     */
    public int getStartX() {
        return hPos;
    }

    /**
     * Returns the starting Y coordinate
     *
     * @return starting y
     */
    public int getStartY() {
        return firstLine;
    }

    /**
     * Get number of char rows
     *
     * @return number of rows
     */
    public int getCharRows() {
        return verticalRows;
    }

    /**
     * Animates caption
     */
    public void animateCaption() {
        capAnim();
    }

    /**
     * Selects character
     */
    public void selectChar() {
        int horz = getHindex();
        int vert = getVindex();

        if (horz == 1 && vert == 0) {
            if (allPlayers[0] == 0) {
                if (proceed1 == false) {
                    selRaila('c');
                } else {
                    selRaila('o');
                }
            } else {
                errorSound();
            }
        }

        if (horz == 2 && vert == 0) {
            if (allPlayers[1] == 0) {
                if (proceed1 == false) {
                    selSubiya('c');
                } else {
                    selSubiya('o');
                }
            } else {
                errorSound();
            }
        }

        if (horz == 3 && vert == 0) {
            if (allPlayers[2] == 0) {
                if (proceed1 == false) {
                    selLynx('c');
                } else {
                    selLynx('o');
                }
            } else {
                errorSound();
            }
        }

        if (horz == 1 && vert == 1) {
            if (allPlayers[3] == 0) {
                if (proceed1 == false) {
                    selAisha('c');
                } else {
                    selAisha('o');
                }
            } else {
                errorSound();
            }
        }

        if (horz == 2 && vert == 1) {
            if (allPlayers[4] == 0) {
                if (proceed1 == false) {
                    selRav('c');
                } else {
                    selRav('o');
                }
            } else {
                errorSound();
            }
        }

        if (horz == 3 && vert == 1) {
            if (allPlayers[5] == 0) {
                if (proceed1 == false) {
                    selAde('c');
                } else {
                    selAde('o');
                }
            } else {
                errorSound();
            }
        }

        if (horz == 1 && vert == 2) {
            if (allPlayers[6] == 0) {
                if (proceed1 == false) {
                    selJon('c');
                } else {
                    selJon('o');
                }
            } else {
                errorSound();
            }
        }

        if (horz == 2 && vert == 2) {
            if (allPlayers[7] == 0) {
                if (proceed1 == false) {
                    selAdam('c');
                } else {
                    selAdam('o');
                }
            } else {
                errorSound();
            }
        }

        if (horz == 3 && vert == 2) {
            if (allPlayers[8] == 0) {
                if (proceed1 == false) {
                    selNOVAAdam('c');
                } else {
                    selNOVAAdam('o');
                }
            } else {
                errorSound();
            }
        }

        if (horz == 1 && vert == 3) {
            if (allPlayers[9] == 0) {
                if (proceed1 == false) {
                    selAza('c');
                } else {
                    selAza('o');
                }
            } else {
                errorSound();
            }
        }

        if (horz == 2 && vert == 3) {
            if (allPlayers[10] == 0) {
                if (proceed1 == false) {
                    selSorr('c');
                } else {
                    selSorr('o');
                }
            } else {
                errorSound();
            }
        }

        if (horz == 3 && vert == 3) {
            if (allPlayers[11] == 0) {
                if (proceed1 == false) {
                    selThing('c');
                } else {
                    selThing('o');
                }
            } else {
                errorSound();
            }
        }
    }

    /**
     * Plays error sound
     */
    public void errorSound() {
        error = new ThreadMP3("audio/error.mp3", false);
        error.play();
        System.out.println("Error sound");
    }
}

package com.scndgen.legends.scene;

import com.scndgen.legends.Language;
import com.scndgen.legends.LoginScreen;
import com.scndgen.legends.enums.Characters;
import com.scndgen.legends.enums.CharacterState;
import com.scndgen.legends.enums.Mode;
import com.scndgen.legends.render.RenderGameplay;
import com.scndgen.legends.threads.AudioPlayback;
import com.scndgen.legends.windows.MainWindow;
import io.github.subiyacryolite.enginev1.JenesisImageLoader;
import io.github.subiyacryolite.enginev1.JenesisMode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by ifunga on 14/04/2017.
 */
public abstract class CharacterSelectionScreen extends JenesisMode implements ActionListener {
    protected static int characterSel, opponentSel;
    protected static String charDesc = "";
    protected static int[] arr1, arr2, arr3, arr4, arr5;
    protected static int[] arr1a, arr2a, arr3a, arr4a, arr5a;
    protected static int[] arr1b, arr2b, arr3b, arr4b, arr5b;
    protected static int[] attacks;
    protected static int storyBoards;
    protected final com.scndgen.legends.characters.Characters players = new com.scndgen.legends.characters.Characters();
    protected String[] statsChar = new String[LoginScreen.getInstance().charNames.length];
    protected int numOfCharacters = Characters.values().length;
    protected int col, currentSlot = 0, lastRow, xCordCloud = 0, xCordCloud2 = 0, charYcap = 0, charXcap = 0, charPrevLoicIndex = 0, hIndex = 1, x = 0, y = 0, vIndex = 0, hSpacer = 48, vSpacer = 48, hPos = 354, firstLine = 105, horizColumns = 3, verticalRows = 3;
    protected JenesisImageLoader imageLoader;
    protected int charDescIndex = 0;
    protected float opacInc, p1Opac, opacChar;
    protected Characters opponent, characters;
    protected int[] allPlayers = new int[LoginScreen.getInstance().charNames.length];
    protected int oppPrevLoc, charPrevLoc;
    protected boolean characterSelected = false, opponentSelected = false, isAnimatorNotRuning = true;
    protected int selectedCharIndex = 0, selectedOppIndex = 0;
    protected Object source;
    //private static StageSelect charVisual;
    protected AudioPlayback sound, sound2, error;


    /**
     * Initialises the characters select panel
     */
    public CharacterSelectionScreen() {
        setLayout(new BorderLayout());
        attacks = new int[4];
        setBorder(BorderFactory.createLineBorder(Color.black, 1));
    }

    /**
     * Get the storyboard size for the characters
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
        if (RenderGameplay.getInstance().getOppLife() / RenderGameplay.getInstance().getOppMaxLife() >= 1.00) {
            array = arr1;
        } //when doing well, all attacks + 2 buffs
        else if (RenderGameplay.getInstance().getOppLife() / RenderGameplay.getInstance().getOppMaxLife() >= 0.75 && RenderGameplay.getInstance().getOppLife() / RenderGameplay.getInstance().getOppMaxLife() < 1.00) {
            array = arr2;
        } //when doing well, 4 attacks + 2 buffs
        else if (RenderGameplay.getInstance().getOppLife() / RenderGameplay.getInstance().getOppMaxLife() >= 0.50 && RenderGameplay.getInstance().getOppLife() / RenderGameplay.getInstance().getOppMaxLife() < 0.75) {
            if (RenderGameplay.getInstance().getBreak() == 1000 && RenderGameplay.getInstance().limitRunning) {
                MainWindow.getInstance().triggerFury(CharacterState.OPPONENT);
                array = new int[]{0, 0, 0, 0};
            } else {
                array = arr3;
            }
        } //when doing well, 4 buffs + 2 moves
        else if (RenderGameplay.getInstance().getOppLife() / RenderGameplay.getInstance().getOppMaxLife() >= 0.25 && RenderGameplay.getInstance().getOppLife() / RenderGameplay.getInstance().getOppMaxLife() < 0.50) {
            if (RenderGameplay.getInstance().getBreak() == 1000 && RenderGameplay.getInstance().limitRunning) {
                MainWindow.getInstance().triggerFury(CharacterState.OPPONENT);
                array = new int[]{0, 0, 0, 0};
            } else {
                array = arr4;
            }
        } //first fury, when doing well, 4 buffs + 2 moves
        else {
            if (RenderGameplay.getInstance().getBreak() == 1000 && RenderGameplay.getInstance().limitRunning) {
                MainWindow.getInstance().triggerFury(CharacterState.OPPONENT);
                array = new int[]{0, 0, 0, 0};
            } else {
                array = arr5;
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
     * Depending on scene, sets
     */
    private static void sortMode(CharacterState who) {
        /**
         * If you choose a characters in lan, you can't choose your opponent
         */
        if (who == CharacterState.CHARACTER) {
        }
    }

    /**
     * Disables all buttons, used in online and story modes
     */
    public void disableAll() {
        for (int u = 0; u < allPlayers.length; u++) {
            allPlayers[u] = 1;
        }
    }

    public com.scndgen.legends.characters.Characters getPlayers() {
        return players;
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
        if (MainWindow.getInstance().getGameMode().equals(MainWindow.lanHost)) {
            MainWindow.getInstance().closeTheServer();
        } else if (MainWindow.getInstance().getGameMode().equals(MainWindow.lanClient)) {
            MainWindow.getInstance().closeTheClient();
        } else if (MainWindow.getInstance().getGameMode().equals(MainWindow.storyMode)) {
            MainWindow.getInstance().getStory().getStoryInstance().skipDialogue();
        }
        MainWindow.getInstance().backToMenuScreen();
        RenderGameplay.getInstance().closeAudio();
    }

    /**
     * Starts a new game
     */
    public void beginGame() {
        if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.singlePlayer) || MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost) || MainWindow.getInstance().getGameMode().equalsIgnoreCase("offline2")) {
            MainWindow.getInstance().selectStage();

            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                MainWindow.getInstance().sendToClient("watchStageSel_xcbD");
            }
        }

    }

    /**
     * Refresh selections
     */
    public void refreshSelections() {
        charPrevLoc = 0;
        oppPrevLoc = 0;
        //MainWindow.getInstance().repaintCharSel();
        for (int u = 0; u < allPlayers.length; u++) {
            allPlayers[u] = 0; // 0 means free, 1 means selected
        }
        characterSelected = false;
        opponentSelected = false;
        //selectedCharIndex=0;
        //selectedOppIndex=0;
        MainWindow.getInstance().storedX = 99;
        MainWindow.getInstance().storedY = 99;
    }

    /**
     * Get the Characters name
     *
     * @return characters name
     */
    public Characters getCharName() {
        return characters;
    }

    /**
     * Get the opponents name
     *
     * @return opponent name
     */
    public Characters getOppName() {
        return opponent;
    }

    /**
     * Selecting Raila
     *
     * @param type - opponent (CharacterState.OPPONENT) or characters (CharacterState.CHARACTER)
     */
    public void selRaila(CharacterState type) {
        systemNotice(Language.getInstance().getLine(84));
        if (type == CharacterState.CHARACTER) //when selecting char
        {
            {
                sound = new AudioPlayback(AudioPlayback.charSelectSound(), false);
                sound.play();
                characterSelected = true;
                characters = Characters.RAILA;
                getPlayers().prepare(characters);
                allPlayers[characters.index()] = charPrevLoc = selectedCharIndex = characters.index();
                if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                    MainWindow.getInstance().sendToClient("selRai_jkxc");
                    disableAll();
                }
                if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanClient)) {
                    MainWindow.getInstance().sendToServer("selRai_jkxc");
                    disableAll();
                }
            }
        } else if (type == CharacterState.OPPONENT && opponentSelected == false) {
            sound2 = new AudioPlayback(AudioPlayback.charSelectSound(), false);
            sound2.play();
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanClient)) {
            }
            opponentSelected = true;
            opponent = Characters.RAILA;
            getPlayers().prepareO(opponent);
            selectedOppIndex = oppPrevLoc = allPlayers[2] = opponent.index();
        }
    }

    /**
     * Selecting Subiya
     *
     * @param type - opponent (CharacterState.OPPONENT) or characters (CharacterState.CHARACTER)
     */
    public void selSubiya(CharacterState type) {
        if (type == CharacterState.CHARACTER) //when selecting char
        {
            systemNotice(Language.getInstance().getLine(85));
            {
                sound = new AudioPlayback(AudioPlayback.charSelectSound(), false);
                sound.play();
                characterSelected = true;
                characters = Characters.SUBIYA;
                getPlayers().prepare(characters);
                allPlayers[characters.index()] = charPrevLoc = selectedCharIndex = characters.index();
                charDesc = getPlayers().getCharacter().getDescSmall();
                if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                    MainWindow.getInstance().sendToClient("selSub_jkxc");
                    disableAll();
                }
                if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanClient)) {
                    MainWindow.getInstance().sendToServer("selSub_jkxc");
                    disableAll();
                }
            }
        }

        if (type == CharacterState.OPPONENT && opponentSelected == false) // when selecting opponent
        {
            sound2 = new AudioPlayback(AudioPlayback.charSelectSound(), false);
            sound2.play();
            opponentSelected = true;
            opponent = Characters.SUBIYA;
            getPlayers().prepareO(opponent);
            selectedOppIndex = oppPrevLoc = allPlayers[2] = opponent.index();
        }
    }

    /**
     * Selecting Lynx
     *
     * @param type - opponent (CharacterState.OPPONENT) or characters (CharacterState.CHARACTER)
     */
    public void selLynx(CharacterState type) {
        systemNotice(Language.getInstance().getLine(86));
        if (type == CharacterState.CHARACTER) //when selecting char
        {
            sound = new AudioPlayback(AudioPlayback.charSelectSound(), false);
            sound.play();
            characterSelected = true;
            characters = Characters.LYNX;
            getPlayers().prepare(characters);
            allPlayers[characters.index()] = charPrevLoc = selectedCharIndex = characters.index();
            charDesc = getPlayers().getCharacter().getDescSmall();
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                MainWindow.getInstance().sendToClient("selLyn_jkxc");
                disableAll();
            }
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanClient)) {
                MainWindow.getInstance().sendToServer("selLyn_jkxc");
                disableAll();
            }
        }
        if (type == CharacterState.OPPONENT && opponentSelected == false) // when selecting opponent
        {
            sound2 = new AudioPlayback(AudioPlayback.charSelectSound(), false);
            sound2.play();
            opponentSelected = true;
            opponent = Characters.LYNX;
            getPlayers().prepareO(opponent);
            selectedOppIndex = oppPrevLoc = allPlayers[2] = opponent.index();
        }
    }

    /**
     * Selecting Aisha
     *
     * @param type - opponent (CharacterState.OPPONENT) or characters (CharacterState.CHARACTER)
     */
    public void selAisha(CharacterState type) {
        systemNotice(Language.getInstance().getLine(87));
        if (type == CharacterState.CHARACTER) //when selecting char
        {
            sound = new AudioPlayback(AudioPlayback.charSelectSound(), false);
            sound.play();
            characterSelected = true;
            characters = Characters.AISHA;
            getPlayers().prepare(characters);
            allPlayers[characters.index()] = charPrevLoc = selectedCharIndex = characters.index();
            charDesc = getPlayers().getCharacter().getDescSmall();
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                MainWindow.getInstance().sendToClient("selAlx_jkxc");
                disableAll();
            }
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanClient)) {
                MainWindow.getInstance().sendToServer("selAlx_jkxc");
                disableAll();
            }
        }

        if (type == CharacterState.OPPONENT && opponentSelected == false) // when selecting opponent
        {
            sound2 = new AudioPlayback(AudioPlayback.charSelectSound(), false);
            sound2.play();
            opponentSelected = true;
            opponent = Characters.AISHA;
            getPlayers().prepareO(opponent);
            selectedOppIndex = oppPrevLoc = allPlayers[2] = opponent.index();
        }
    }

    /**
     * Selecting Ade
     *
     * @param type - opponent (CharacterState.OPPONENT) or characters (CharacterState.CHARACTER)
     */
    public void selAde(CharacterState type) {
        systemNotice(Language.getInstance().getLine(88));
        if (type == CharacterState.CHARACTER) //when selecting char
        {
            sound = new AudioPlayback(AudioPlayback.charSelectSound(), false);
            sound.play();
            characterSelected = true;
            characters = Characters.ADE;
            getPlayers().prepare(characters);
            allPlayers[characters.index()] = charPrevLoc = selectedCharIndex = characters.index();
            charDesc = getPlayers().getCharacter().getDescSmall();
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                MainWindow.getInstance().sendToClient("selAde_jkxc");
                disableAll();
            }
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanClient)) {
                MainWindow.getInstance().sendToServer("selAde_jkxc");
                disableAll();
            }
        }

        if (type == CharacterState.OPPONENT && opponentSelected == false) // when selecting opponent
        {
            sound2 = new AudioPlayback(AudioPlayback.charSelectSound(), false);
            sound2.play();
            opponentSelected = true;
            opponent = Characters.ADE;
            getPlayers().prepareO(opponent);
            selectedOppIndex = oppPrevLoc = allPlayers[2] = opponent.index();
        }
    }

    /**
     * Selecting Aisha
     *
     * @param type - opponent (CharacterState.OPPONENT) or characters (CharacterState.CHARACTER)
     */
    public void selRav(CharacterState type) {
        systemNotice(Language.getInstance().getLine(89));
        if (type == CharacterState.CHARACTER) //when selecting char
        {
            sound = new AudioPlayback(AudioPlayback.charSelectSound(), false);
            sound.play();
            characterSelected = true;
            characters = Characters.RAVAGE;
            getPlayers().prepare(characters);
            allPlayers[characters.index()] = charPrevLoc = selectedCharIndex = characters.index();
            charDesc = getPlayers().getCharacter().getDescSmall();
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                MainWindow.getInstance().sendToClient("selRav_jkxc");
                disableAll();
            }
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanClient)) {
                MainWindow.getInstance().sendToServer("selRav_jkxc");
                disableAll();
            }
        }

        if (type == CharacterState.OPPONENT && opponentSelected == false) // when selecting opponent
        {
            sound2 = new AudioPlayback(AudioPlayback.charSelectSound(), false);
            sound2.play();
            opponentSelected = true;
            opponent = Characters.RAVAGE;
            getPlayers().prepareO(opponent);
            selectedOppIndex = oppPrevLoc = allPlayers[2] = opponent.index();
        }
    }

    /**
     * Selecting Jonah
     *
     * @param type - opponent (CharacterState.OPPONENT) or characters (CharacterState.CHARACTER)
     */
    public void selJon(CharacterState type) {
        systemNotice(Language.getInstance().getLine(90));
        if (type == CharacterState.CHARACTER) //when selecting char
        {
            sound = new AudioPlayback(AudioPlayback.charSelectSound(), false);
            sound.play();
            characterSelected = true;
            characters = Characters.JONAH;
            getPlayers().prepare(characters);
            charPrevLoc = selectedCharIndex = characters.index();
            charDesc = getPlayers().getCharacter().getDescSmall();
            allPlayers[6] = 1;
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                MainWindow.getInstance().sendToClient("selJon_jkxc");
                disableAll();
            }
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanClient)) {
                MainWindow.getInstance().sendToServer("selJon_jkxc");
                disableAll();
            }
        }

        if (type == CharacterState.OPPONENT && opponentSelected == false) // when selecting opponent
        {
            sound2 = new AudioPlayback(AudioPlayback.charSelectSound(), false);
            sound2.play();
            opponentSelected = true;
            opponent = Characters.JONAH;
            getPlayers().prepareO(opponent);
            selectedOppIndex = oppPrevLoc = allPlayers[2] = opponent.index();
        }
    }

    /**
     * Selecting NovaAdam
     *
     * @param type - opponent (CharacterState.OPPONENT) or characters (CharacterState.CHARACTER)
     */
    public void selAdam(CharacterState type) {
        systemNotice(Language.getInstance().getLine(91));
        if (type == CharacterState.CHARACTER) //when selecting char
        {
            sound = new AudioPlayback(AudioPlayback.charSelectSound(), false);
            sound.play();
            characterSelected = true;
            characters = Characters.ADAM;
            getPlayers().prepare(characters);
            allPlayers[characters.index()] = charPrevLoc = selectedCharIndex = characters.index();
            charDesc = getPlayers().getCharacter().getDescSmall();
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                MainWindow.getInstance().sendToClient("selAdam_jkxc");
                disableAll();
            }
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanClient)) {
                MainWindow.getInstance().sendToServer("selAdam_jkxc");
                disableAll();
            }
        }

        if (type == CharacterState.OPPONENT && opponentSelected == false) // when selecting opponent
        {
            sound2 = new AudioPlayback(AudioPlayback.charSelectSound(), false);
            sound2.play();
            opponentSelected = true;
            opponent = Characters.NOVA_ADAM;
            getPlayers().prepareO(opponent);
            selectedOppIndex = oppPrevLoc = allPlayers[2] = opponent.index();
        }
    }

    /**
     * Selecting NOVA NovaAdam
     *
     * @param type - opponent (CharacterState.OPPONENT) or characters (CharacterState.CHARACTER)
     */
    public void selNOVAAdam(CharacterState type) {
        systemNotice(Language.getInstance().getLine(92));
        if (type == CharacterState.CHARACTER) //when selecting char
        {
            sound = new AudioPlayback(AudioPlayback.charSelectSound(), false);
            sound.play();
            characterSelected = true;
            characters = Characters.NOVA_ADAM;
            getPlayers().prepare(characters);
            charPrevLoc = selectedCharIndex = characters.index();
            charDesc = getPlayers().getCharacter().getDescSmall();
            //MainWindow.getInstance().repaintCharSel();
            allPlayers[8] = 1;
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                MainWindow.getInstance().sendToClient("selNOVAAdam_jkxc");
                disableAll();
            }
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanClient)) {
                MainWindow.getInstance().sendToServer("selNOVAAdam_jkxc");
                disableAll();
            }
        }

        if (type == CharacterState.OPPONENT && opponentSelected == false) // when selecting opponent
        {
            sound2 = new AudioPlayback(AudioPlayback.charSelectSound(), false);
            sound2.play();
            opponentSelected = true;
            opponent = Characters.NOVA_ADAM;
            getPlayers().prepareO(opponent);
            selectedOppIndex = oppPrevLoc = allPlayers[2] = opponent.index();
        }
    }

    /**
     * Selecting Azaria
     *
     * @param type - opponent (CharacterState.OPPONENT) or characters (CharacterState.CHARACTER)
     */
    public void selAza(CharacterState type) {
        systemNotice(Language.getInstance().getLine(93));
        if (type == CharacterState.CHARACTER) //when selecting char
        {
            sound = new AudioPlayback(AudioPlayback.charSelectSound(), false);
            sound.play();
            characterSelected = true;
            characters = Characters.AZARIA;
            getPlayers().prepare(characters);
            charPrevLoc = selectedCharIndex = characters.index();
            charDesc = getPlayers().getCharacter().getDescSmall();
            //MainWindow.getInstance().repaintCharSel();
            allPlayers[9] = 1;
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                MainWindow.getInstance().sendToClient("selAzaria_jkxc");
                disableAll();
            }
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanClient)) {
                MainWindow.getInstance().sendToServer("selAzaria_jkxc");
                disableAll();
            }
        }

        if (type == CharacterState.OPPONENT && opponentSelected == false) // when selecting opponent
        {
            sound2 = new AudioPlayback(AudioPlayback.charSelectSound(), false);
            sound2.play();
            opponentSelected = true;
            opponent = Characters.AZARIA;
            getPlayers().prepareO(opponent);
            selectedOppIndex = oppPrevLoc = allPlayers[2] = opponent.index();
        }
    }

    /**
     * Selecting Sorrowe
     *
     * @param type - opponent (CharacterState.OPPONENT) or characters (CharacterState.CHARACTER)
     */
    public void selSorr(CharacterState type) {
        systemNotice(Language.getInstance().getLine(94));
        if (type == CharacterState.CHARACTER) //when selecting char
        {
            sound = new AudioPlayback(AudioPlayback.charSelectSound(), false);
            sound.play();
            characterSelected = true;
            characters = Characters.SORROWE;
            getPlayers().prepare(characters);
            charPrevLoc = selectedCharIndex = characters.index();
            charDesc = getPlayers().getCharacter().getDescSmall();
            //MainWindow.getInstance().repaintCharSel();
            allPlayers[10] = 1;
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                MainWindow.getInstance().sendToClient("selSorr_jkxc");
                disableAll();
            }
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanClient)) {
                MainWindow.getInstance().sendToServer("selSorr_jkxc");
                disableAll();
            }
        }

        if (type == CharacterState.OPPONENT && opponentSelected == false) // when selecting opponent
        {
            sound2 = new AudioPlayback(AudioPlayback.charSelectSound(), false);
            sound2.play();
            opponentSelected = true;
            getPlayers().prepareO(opponent);
            selectedOppIndex = oppPrevLoc = allPlayers[2] = opponent.index();
        }
    }

    /**
     * Selecting Sorrowe
     *
     * @param type - opponent (CharacterState.OPPONENT) or characters (CharacterState.CHARACTER)
     */
    public void selThing(CharacterState type) {
        systemNotice("..........");
        if (type == CharacterState.CHARACTER) //when selecting char
        {
            sound = new AudioPlayback(AudioPlayback.charSelectSound(), false);
            sound.play();
            characterSelected = true;
            characters = Characters.THING;
            getPlayers().prepare(characters);
            charPrevLoc = selectedCharIndex = characters.index();
            charDesc = getPlayers().getCharacter().getDescSmall();
            //MainWindow.getInstance().repaintCharSel();
            allPlayers[11] = 1;
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                MainWindow.getInstance().sendToClient("selThi_jkxc");
                disableAll();
            }
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanClient)) {
                MainWindow.getInstance().sendToServer("selThi_jkxc");
                disableAll();
            }
        }

        if (type == CharacterState.OPPONENT && opponentSelected == false) // when selecting opponent
        {
            sound2 = new AudioPlayback(AudioPlayback.charSelectSound(), false);
            sound2.play();
            opponentSelected = true;
            opponent = Characters.THING;
            getPlayers().prepareO(opponent);
            selectedOppIndex = oppPrevLoc = allPlayers[2] = opponent.index();
        }

        if (type == CharacterState.BOSS && opponentSelected == false) // when selecting opponent as boss
        {
            sound2 = new AudioPlayback(AudioPlayback.charSelectSound(), false);
            sound2.play();
            opponentSelected = true;
            opponent = Characters.THING;
            getPlayers().prepareO(opponent);
            selectedOppIndex = oppPrevLoc = allPlayers[2] = opponent.index();
        }
    }

    public void proceed2False() {
        opponentSelected = false;
    }

    public void animateCharSelect() {
        new Thread() {

            @Override
            @SuppressWarnings("static-access")
            public void run() {
                this.setName("Characters select bg animation thread");
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
                    while (MainWindow.getInstance().mode == Mode.CHAR_SELECT_SCREEN);
                }

            }
        }.start();
    }

    public void setItem() {
        currentSlot = (verticalRows * vIndex) + hIndex;
        System.out.println("Current Slot: " + currentSlot);
    }

    /**
     * Characters select screen, move up
     */
    public void moveUp() {
        upMove();
    }

    /**
     * Characters select screen, move down
     */
    public void moveDown() {
        downMove();
    }

    /**
     * Characters select screen, move right
     */
    public void moveRight() {
        rightMove();
    }

    /**
     * Characters select screen, move left
     */
    public void moveLeft() {
        leftMove();
    }

    /**
     * Gets the number of columns in the characters select screen
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
     * Selects characters
     */
    public void selectChar() {
        int horz = getHindex();
        int vert = getVindex();

        if (horz == 1 && vert == 0) {
            if (allPlayers[0] == 0) {
                if (characterSelected == false) {
                    selRaila(CharacterState.CHARACTER);
                } else {
                    selRaila(CharacterState.OPPONENT);
                }
            } else {
                errorSound();
            }
        }

        if (horz == 2 && vert == 0) {
            if (allPlayers[1] == 0) {
                if (characterSelected == false) {
                    selSubiya(CharacterState.CHARACTER);
                } else {
                    selSubiya(CharacterState.OPPONENT);
                }
            } else {
                errorSound();
            }
        }

        if (horz == 3 && vert == 0) {
            if (allPlayers[2] == 0) {
                if (characterSelected == false) {
                    selLynx(CharacterState.CHARACTER);
                } else {
                    selLynx(CharacterState.OPPONENT);
                }
            } else {
                errorSound();
            }
        }

        if (horz == 1 && vert == 1) {
            if (allPlayers[3] == 0) {
                if (characterSelected == false) {
                    selAisha(CharacterState.CHARACTER);
                } else {
                    selAisha(CharacterState.OPPONENT);
                }
            } else {
                errorSound();
            }
        }

        if (horz == 2 && vert == 1) {
            if (allPlayers[4] == 0) {
                if (characterSelected == false) {
                    selRav(CharacterState.CHARACTER);
                } else {
                    selRav(CharacterState.OPPONENT);
                }
            } else {
                errorSound();
            }
        }

        if (horz == 3 && vert == 1) {
            if (allPlayers[5] == 0) {
                if (characterSelected == false) {
                    selAde(CharacterState.CHARACTER);
                } else {
                    selAde(CharacterState.OPPONENT);
                }
            } else {
                errorSound();
            }
        }

        if (horz == 1 && vert == 2) {
            if (allPlayers[6] == 0) {
                if (characterSelected == false) {
                    selJon(CharacterState.CHARACTER);
                } else {
                    selJon(CharacterState.OPPONENT);
                }
            } else {
                errorSound();
            }
        }

        if (horz == 2 && vert == 2) {
            if (allPlayers[7] == 0) {
                if (characterSelected == false) {
                    selAdam(CharacterState.CHARACTER);
                } else {
                    selAdam(CharacterState.OPPONENT);
                }
            } else {
                errorSound();
            }
        }

        if (horz == 3 && vert == 2) {
            if (allPlayers[8] == 0) {
                if (characterSelected == false) {
                    selNOVAAdam(CharacterState.CHARACTER);
                } else {
                    selNOVAAdam(CharacterState.OPPONENT);
                }
            } else {
                errorSound();
            }
        }

        if (horz == 1 && vert == 3) {
            if (allPlayers[9] == 0) {
                if (characterSelected == false) {
                    selAza(CharacterState.CHARACTER);
                } else {
                    selAza(CharacterState.OPPONENT);
                }
            } else {
                errorSound();
            }
        }

        if (horz == 2 && vert == 3) {
            if (allPlayers[10] == 0) {
                if (characterSelected == false) {
                    selSorr(CharacterState.CHARACTER);
                } else {
                    selSorr(CharacterState.OPPONENT);
                }
            } else {
                errorSound();
            }
        }

        if (horz == 3 && vert == 3) {
            if (allPlayers[11] == 0) {
                if (characterSelected == false) {
                    selThing(CharacterState.CHARACTER);
                } else {
                    selThing(CharacterState.OPPONENT);
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
        error = new AudioPlayback("audio/error.mp3", false);
        error.play();
        System.out.println("Error sound");
    }


    /**
     * Move up
     */
    public void upMove() {
        if (vIndex > 0) {
            vIndex = vIndex - 1;
        } else {
            vIndex = verticalRows;
        }

        capAnim();
    }

    /**
     * Move down
     */
    public void downMove() {
        if (vIndex < verticalRows) {
            vIndex = vIndex + 1;
        } else {
            vIndex = 0;
        }

        capAnim();
    }

    /**
     * Move right
     */
    public void rightMove() {
        if (hIndex < horizColumns) {
            hIndex = hIndex + 1;
        } else {
            hIndex = 1;
        }

        capAnim();
    }

    /**
     * Move left
     */
    public void leftMove() {
        if (hIndex > 1) {
            hIndex = hIndex - 1;
        } else {
            hIndex = horizColumns;
        }

        capAnim();
    }

    /**
     * Horizontal index
     *
     * @return hIndex
     */
    public int getHindex() {
        return hIndex;
    }

    /**
     * Set horizontal index
     */
    public void setHindex(int value) {
        hIndex = value;
    }

    /**
     * Vertical index
     *
     * @return vIndex
     */
    public int getVindex() {
        return vIndex;
    }

    /**
     * Set vertical index
     */
    public void setVindex(int value) {
        vIndex = value;
    }

    /**
     * Animates captions
     */
    public void capAnim() {
        x = -100;
        p1Opac = 0.0f;
        opacChar = 0.0f;
    }

    /**
     * Checks if within number of Characters
     */
    public boolean well() {
        boolean ans = false;
        int whichChar = ((vIndex * 3) + hIndex) - 1;
        if (whichChar <= numOfCharacters) {
            ans = true;
            //System.out.println("Dude: "+whichChar);
        }

        return ans;
    }

    /**
     * When both playes are selected, this prevents movement.
     *
     * @return false if both Characters have been selected, true if only one is selected
     */
    public boolean bothArentSelected() {
        boolean answer = true;
        if (characterSelected && opponentSelected) {
            answer = false;
        }
        return answer;
    }

    public int getSelectedCharIndex() {
        return selectedCharIndex;
    }

    public void setSelectedCharIndex(int selectedCharIndex) {
        this.selectedCharIndex = selectedCharIndex;
    }

    public boolean getCharacterSelected() {
        return characterSelected;
    }

    public void setCharacterSelected(boolean characterSelected) {
        this.characterSelected = characterSelected;
    }

    public boolean getOpponentSelected() {
        return opponentSelected;
    }

    public void setOpponentSelected(boolean opponentSelected) {
        this.opponentSelected = opponentSelected;
    }

    public void setSelectedOppIndex(int selectedOppIndex) {
        this.selectedOppIndex = selectedOppIndex;
    }
}

package com.scndgen.legends.scene;

import com.scndgen.legends.Language;
import com.scndgen.legends.LoginScreen;
import com.scndgen.legends.enums.CharacterState;
import com.scndgen.legends.enums.Characters;
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
import java.util.Hashtable;

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
    protected final int numOfCharacters = Characters.values().length;
    protected int currentSlot = 0, xCordCloud = 0, xCordCloud2 = 0, charYcap = 0, charXcap = 0, charPrevLoicIndex = 0, columnIndex = 1, x = 0, y = 0, rowIndex = 0, hSpacer = 48, vSpacer = 48, hPos = 354, firstLine = 105;
    protected JenesisImageLoader imageLoader;
    protected int charDescIndex = 0;
    protected float opacInc, p1Opac, opacChar;
    protected Characters opponent, characters;
    protected int oppPrevLoc, charPrevLoc;
    protected boolean characterSelected, opponentSelected, animatorThreadRunning;
    protected int selectedCharIndex = 0, selectedOppIndex = 0;
    //private static StageSelect charVisual;
    protected AudioPlayback sound, sound2, error;
    protected final int columns = 3;
    protected final int rows = numOfCharacters / columns;
    protected boolean canSelectCharacter;
    protected final Hashtable<Integer, Characters> characterLookup = new Hashtable<>();

    public void newInstance() {
        canSelectCharacter = true;
        characterSelected = false;
        opponentSelected = false;
        refreshSelections();
        characterLookup.clear();
        for (Characters c : Characters.values()) {
            characterLookup.put(c.index(), c);
        }
    }

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
        //when doing isWithinRange, all attacks
        if (RenderGameplay.getInstance().getOppLife() / RenderGameplay.getInstance().getOppMaxLife() >= 1.00) {
            array = arr1;
        } //when doing isWithinRange, all attacks + 2 buffs
        else if (RenderGameplay.getInstance().getOppLife() / RenderGameplay.getInstance().getOppMaxLife() >= 0.75 && RenderGameplay.getInstance().getOppLife() / RenderGameplay.getInstance().getOppMaxLife() < 1.00) {
            array = arr2;
        } //when doing isWithinRange, 4 attacks + 2 buffs
        else if (RenderGameplay.getInstance().getOppLife() / RenderGameplay.getInstance().getOppMaxLife() >= 0.50 && RenderGameplay.getInstance().getOppLife() / RenderGameplay.getInstance().getOppMaxLife() < 0.75) {
            if (RenderGameplay.getInstance().getBreak() == 1000 && RenderGameplay.getInstance().limitRunning) {
                MainWindow.getInstance().triggerFury(CharacterState.OPPONENT);
                array = new int[]{0, 0, 0, 0};
            } else {
                array = arr3;
            }
        } //when doing isWithinRange, 4 buffs + 2 moves
        else if (RenderGameplay.getInstance().getOppLife() / RenderGameplay.getInstance().getOppMaxLife() >= 0.25 && RenderGameplay.getInstance().getOppLife() / RenderGameplay.getInstance().getOppMaxLife() < 0.50) {
            if (RenderGameplay.getInstance().getBreak() == 1000 && RenderGameplay.getInstance().limitRunning) {
                MainWindow.getInstance().triggerFury(CharacterState.OPPONENT);
                array = new int[]{0, 0, 0, 0};
            } else {
                array = arr4;
            }
        } //first fury, when doing isWithinRange, 4 buffs + 2 moves
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
    public void preventCharacterSelection() {
        canSelectCharacter = false;
    }

    public com.scndgen.legends.characters.Characters getPlayers() {
        return players;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {

    }

    /**
     * Goes back to main menu
     */
    public void backToMenu() {
        newInstance();
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
    private void refreshSelections() {
        charPrevLoc = 0;
        oppPrevLoc = 0;
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
                charPrevLoc = selectedCharIndex = characters.index();
                if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                    MainWindow.getInstance().sendToClient("selRai_jkxc");
                    preventCharacterSelection();
                }
                if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanClient)) {
                    MainWindow.getInstance().sendToServer("selRai_jkxc");
                    preventCharacterSelection();
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
            selectedOppIndex = oppPrevLoc = opponent.index();
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
                charPrevLoc = selectedCharIndex = characters.index();
                charDesc = getPlayers().getCharacter().getDescSmall();
                if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                    MainWindow.getInstance().sendToClient("selSub_jkxc");
                    preventCharacterSelection();
                }
                if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanClient)) {
                    MainWindow.getInstance().sendToServer("selSub_jkxc");
                    preventCharacterSelection();
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
            selectedOppIndex = oppPrevLoc = opponent.index();
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
            charPrevLoc = selectedCharIndex = characters.index();
            charDesc = getPlayers().getCharacter().getDescSmall();
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                MainWindow.getInstance().sendToClient("selLyn_jkxc");
                preventCharacterSelection();
            }
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanClient)) {
                MainWindow.getInstance().sendToServer("selLyn_jkxc");
                preventCharacterSelection();
            }
        }
        if (type == CharacterState.OPPONENT && opponentSelected == false) // when selecting opponent
        {
            sound2 = new AudioPlayback(AudioPlayback.charSelectSound(), false);
            sound2.play();
            opponentSelected = true;
            opponent = Characters.LYNX;
            getPlayers().prepareO(opponent);
            selectedOppIndex = oppPrevLoc = opponent.index();
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
            charPrevLoc = selectedCharIndex = characters.index();
            charDesc = getPlayers().getCharacter().getDescSmall();
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                MainWindow.getInstance().sendToClient("selAlx_jkxc");
                preventCharacterSelection();
            }
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanClient)) {
                MainWindow.getInstance().sendToServer("selAlx_jkxc");
                preventCharacterSelection();
            }
        }

        if (type == CharacterState.OPPONENT && opponentSelected == false) // when selecting opponent
        {
            sound2 = new AudioPlayback(AudioPlayback.charSelectSound(), false);
            sound2.play();
            opponentSelected = true;
            opponent = Characters.AISHA;
            getPlayers().prepareO(opponent);
            selectedOppIndex = oppPrevLoc = opponent.index();
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
            charPrevLoc = selectedCharIndex = characters.index();
            charDesc = getPlayers().getCharacter().getDescSmall();
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                MainWindow.getInstance().sendToClient("selAde_jkxc");
                preventCharacterSelection();
            }
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanClient)) {
                MainWindow.getInstance().sendToServer("selAde_jkxc");
                preventCharacterSelection();
            }
        }

        if (type == CharacterState.OPPONENT && opponentSelected == false) // when selecting opponent
        {
            sound2 = new AudioPlayback(AudioPlayback.charSelectSound(), false);
            sound2.play();
            opponentSelected = true;
            opponent = Characters.ADE;
            getPlayers().prepareO(opponent);
            selectedOppIndex = oppPrevLoc = opponent.index();
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
            charPrevLoc = selectedCharIndex = characters.index();
            charDesc = getPlayers().getCharacter().getDescSmall();
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                MainWindow.getInstance().sendToClient("selRav_jkxc");
                preventCharacterSelection();
            }
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanClient)) {
                MainWindow.getInstance().sendToServer("selRav_jkxc");
                preventCharacterSelection();
            }
        }

        if (type == CharacterState.OPPONENT && opponentSelected == false) // when selecting opponent
        {
            sound2 = new AudioPlayback(AudioPlayback.charSelectSound(), false);
            sound2.play();
            opponentSelected = true;
            opponent = Characters.RAVAGE;
            getPlayers().prepareO(opponent);
            selectedOppIndex = oppPrevLoc = opponent.index();
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
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                MainWindow.getInstance().sendToClient("selJon_jkxc");
                preventCharacterSelection();
            }
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanClient)) {
                MainWindow.getInstance().sendToServer("selJon_jkxc");
                preventCharacterSelection();
            }
        }

        if (type == CharacterState.OPPONENT && opponentSelected == false) // when selecting opponent
        {
            sound2 = new AudioPlayback(AudioPlayback.charSelectSound(), false);
            sound2.play();
            opponentSelected = true;
            opponent = Characters.JONAH;
            getPlayers().prepareO(opponent);
            selectedOppIndex = oppPrevLoc = opponent.index();
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
            charPrevLoc = selectedCharIndex = characters.index();
            charDesc = getPlayers().getCharacter().getDescSmall();
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                MainWindow.getInstance().sendToClient("selAdam_jkxc");
                preventCharacterSelection();
            }
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanClient)) {
                MainWindow.getInstance().sendToServer("selAdam_jkxc");
                preventCharacterSelection();
            }
        }

        if (type == CharacterState.OPPONENT && opponentSelected == false) // when selecting opponent
        {
            sound2 = new AudioPlayback(AudioPlayback.charSelectSound(), false);
            sound2.play();
            opponentSelected = true;
            opponent = Characters.NOVA_ADAM;
            getPlayers().prepareO(opponent);
            selectedOppIndex = oppPrevLoc = opponent.index();
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
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                MainWindow.getInstance().sendToClient("selNOVAAdam_jkxc");
                preventCharacterSelection();
            }
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanClient)) {
                MainWindow.getInstance().sendToServer("selNOVAAdam_jkxc");
                preventCharacterSelection();
            }
        }

        if (type == CharacterState.OPPONENT && opponentSelected == false) // when selecting opponent
        {
            sound2 = new AudioPlayback(AudioPlayback.charSelectSound(), false);
            sound2.play();
            opponentSelected = true;
            opponent = Characters.NOVA_ADAM;
            getPlayers().prepareO(opponent);
            selectedOppIndex = oppPrevLoc = opponent.index();
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
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                MainWindow.getInstance().sendToClient("selAzaria_jkxc");
                preventCharacterSelection();
            }
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanClient)) {
                MainWindow.getInstance().sendToServer("selAzaria_jkxc");
                preventCharacterSelection();
            }
        }

        if (type == CharacterState.OPPONENT && opponentSelected == false) // when selecting opponent
        {
            sound2 = new AudioPlayback(AudioPlayback.charSelectSound(), false);
            sound2.play();
            opponentSelected = true;
            opponent = Characters.AZARIA;
            getPlayers().prepareO(opponent);
            selectedOppIndex = oppPrevLoc = opponent.index();
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
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                MainWindow.getInstance().sendToClient("selSorr_jkxc");
                preventCharacterSelection();
            }
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanClient)) {
                MainWindow.getInstance().sendToServer("selSorr_jkxc");
                preventCharacterSelection();
            }
        }

        if (type == CharacterState.OPPONENT && opponentSelected == false) // when selecting opponent
        {
            sound2 = new AudioPlayback(AudioPlayback.charSelectSound(), false);
            sound2.play();
            opponentSelected = true;
            getPlayers().prepareO(opponent);
            selectedOppIndex = oppPrevLoc = opponent.index();
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
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                MainWindow.getInstance().sendToClient("selThi_jkxc");
                preventCharacterSelection();
            }
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanClient)) {
                MainWindow.getInstance().sendToServer("selThi_jkxc");
                preventCharacterSelection();
            }
        }

        if (type == CharacterState.OPPONENT && opponentSelected == false) // when selecting opponent
        {
            sound2 = new AudioPlayback(AudioPlayback.charSelectSound(), false);
            sound2.play();
            opponentSelected = true;
            opponent = Characters.THING;
            getPlayers().prepareO(opponent);
            selectedOppIndex = oppPrevLoc = opponent.index();
        }

        if (type == CharacterState.BOSS && opponentSelected == false) // when selecting opponent as boss
        {
            sound2 = new AudioPlayback(AudioPlayback.charSelectSound(), false);
            sound2.play();
            opponentSelected = true;
            opponent = Characters.THING;
            getPlayers().prepareO(opponent);
            selectedOppIndex = oppPrevLoc = opponent.index();
        }
    }

    public void proceed2False() {
        opponentSelected = false;
    }

    public void animateCharSelect() {
        if (animatorThreadRunning) return;
        new Thread() {
            @Override
            public void run() {
                this.setName("Characters select bg animation thread");
                do {
                    animatorThreadRunning = true;
                    try {
                        for (int x = 0; x > (-1440 + 852); x++) {
                            this.sleep(0033);
                            xCordCloud = xCordCloud - 3;
                            xCordCloud2 = xCordCloud2 - 5;
                            if (xCordCloud < -960) {
                                xCordCloud = 852;
                            }
                            if (xCordCloud2 < -960) {
                                xCordCloud2 = 852;
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                while (MainWindow.getInstance().mode == Mode.CHAR_SELECT_SCREEN);
                animatorThreadRunning = false;
            }
        }.start();
    }

    public void setItem() {
        currentSlot = (rows * rowIndex) + columnIndex;
        System.out.println("Current Slot: " + currentSlot);
    }

    /**
     * Characters select screen, move up
     */
    public void moveUp() {
        if (rowIndex > 0)
            rowIndex = rowIndex - 1;
        else
            rowIndex = rows - 1;
        capAnim();
    }

    /**
     * Characters select screen, move down
     */
    public void moveDown() {
        if (rowIndex < rows)
            rowIndex = rowIndex + 1;
        else
            rowIndex = 0;
        capAnim();
    }

    /**
     * Characters select screen, move right
     */
    public void moveRight() {
        if (columnIndex < columns)
            columnIndex = columnIndex + 1;
        else
            columnIndex = 0;
        capAnim();
    }

    /**
     * Characters select screen, move left
     */
    public void moveLeft() {
        if (columnIndex > 0)
            columnIndex = columnIndex - 1;
        else
            columnIndex = columns - 1;
        capAnim();
    }

    /**
     * Gets the number of columns in the characters select screen
     *
     * @return number of columns
     */
    public int getColumns() {
        return columns;
    }

    /**
     * Gets the char caption spacer
     *
     * @return spacer
     */
    public int getCaptionHeight() {
        return vSpacer;
    }

    /**
     * Gets the char caption spacer
     *
     * @return spacer
     */
    public int getCaptionWidth() {
        return hSpacer;
    }

    /**
     * Get starting x coordinate
     *
     * @return starting x coordinate
     */
    public int getTopX() {
        return hPos;
    }

    /**
     * Returns the starting Y coordinate
     *
     * @return starting y
     */
    public int getTopY() {
        return firstLine;
    }

    /**
     * Get number of char rows
     *
     * @return number of rows
     */
    public int getRows() {
        return rows;
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
    public void selectCharacter() {
        if (!canSelectCharacter) {
            errorSound();
            return;
        }
        int row = getHindex();
        int column = getVindex();
        int computedPosition = (columns * column) + row;
        Characters character = characterLookup.get(computedPosition);
        switch (character) {
            case SUBIYA:
                selSubiya(characterSelected ? CharacterState.OPPONENT : CharacterState.CHARACTER);
                break;
            case RAILA:
                selRaila(characterSelected ? CharacterState.OPPONENT : CharacterState.CHARACTER);
                break;
            case LYNX:
                selLynx(characterSelected ? CharacterState.OPPONENT : CharacterState.CHARACTER);
                break;
            case AISHA:
                selAisha(characterSelected ? CharacterState.OPPONENT : CharacterState.CHARACTER);
                break;
            case ADE:
                selAde(characterSelected ? CharacterState.OPPONENT : CharacterState.CHARACTER);
                break;
            case RAVAGE:
                selRav(characterSelected ? CharacterState.OPPONENT : CharacterState.CHARACTER);
                break;
            case JONAH:
                selJon(characterSelected ? CharacterState.OPPONENT : CharacterState.CHARACTER);
                break;
            case ADAM:
                selAdam(characterSelected ? CharacterState.OPPONENT : CharacterState.CHARACTER);
                break;
            case NOVA_ADAM:
                selNOVAAdam(characterSelected ? CharacterState.OPPONENT : CharacterState.CHARACTER);
                break;
            case AZARIA:
                selAza(characterSelected ? CharacterState.OPPONENT : CharacterState.CHARACTER);
                break;
            case SORROWE:
                selSorr(characterSelected ? CharacterState.OPPONENT : CharacterState.CHARACTER);
                break;
            case THING:
                selThing(characterSelected ? CharacterState.OPPONENT : CharacterState.CHARACTER);
                break;
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
     * Horizontal index
     *
     * @return columnIndex
     */
    public int getHindex() {
        return columnIndex;
    }

    /**
     * Set horizontal index
     */
    public void setHindex(int value) {
        columnIndex = value;
    }

    /**
     * Vertical index
     *
     * @return rowIndex
     */
    public int getVindex() {
        return rowIndex;
    }

    /**
     * Set vertical index
     */
    public void setVindex(int value) {
        rowIndex = value;
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
    public boolean isWithinRange() {
        boolean ans = false;
        int whichChar = ((rowIndex * columns) + columnIndex) - 1;
        if (whichChar <= numOfCharacters) {
            ans = true;
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

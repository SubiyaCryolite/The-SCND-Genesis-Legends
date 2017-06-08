package com.scndgen.legends.mode;

import com.scndgen.legends.Language;
import com.scndgen.legends.ScndGenLegends;
import com.scndgen.legends.characters.Characters;
import com.scndgen.legends.constants.AudioConstants;
import com.scndgen.legends.constants.NetworkConstants;
import com.scndgen.legends.enums.AudioType;
import com.scndgen.legends.enums.CharacterEnum;
import com.scndgen.legends.enums.PlayerType;
import com.scndgen.legends.enums.SubMode;
import com.scndgen.legends.network.NetworkManager;
import io.github.subiyacryolite.enginev1.Audio;
import io.github.subiyacryolite.enginev1.Loader;
import io.github.subiyacryolite.enginev1.Mode;
import javafx.scene.input.MouseEvent;

import java.util.Hashtable;

/**
 * Created by ifunga on 14/04/2017.
 */
public abstract class CharacterSelection extends Mode {
    protected static String charDesc = "";
    protected int[] attacks;
    protected String[] characterDescription;
    protected final int numOfCharacters = CharacterEnum.values().length;
    protected int xCordCloud = 0, xCordCloud2 = 0, charYcap = 0, charXcap = 0, column = 1, x = 0, y = 0, row = 0, hSpacer = 48, vSpacer = 48, hPos = 354, firstLine = 105;
    protected Loader loader;
    protected float opacInc, p1Opac, opacChar;
    protected CharacterEnum opponentEnum, characterEnum;
    protected int oppPrevLoc, charPrevLoc;
    protected boolean selectedCharacter, selectedOpponent, animatorThreadRunning;
    protected int selectedCharIndex = 0, selectedOppIndex = 0;
    protected final int columns = 3;
    protected boolean canSelectCharacter;
    protected final Hashtable<Integer, CharacterEnum> characterLookup = new Hashtable<>();

    public void newInstance() {
        loadAssets = true;
        canSelectCharacter = true;
        selectedCharacter = false;
        selectedOpponent = false;
        refreshSelections();
        characterLookup.clear();
        for (CharacterEnum character : CharacterEnum.values()) {
            characterLookup.put(character.index(), character);
        }
    }

    /**
     * Initialises the characterEnum select panel
     */
    public CharacterSelection() {

        attacks = new int[4];
    }

    protected void playSelectSound() {
        Audio sound = new Audio(AudioConstants.charSelectSound(), AudioType.SOUND, false);
        sound.play();
    }

    public void setAttacks(int attackNUm, int frames) {
        attacks[attackNUm] = frames;
    }

    /**
     * Load attacks
     *
     * @return
     */
    public int getAttacks() {
        return attacks.length;
    }

    /**
     * Get number of frames
     *
     * @param index - the attack
     * @return - the number of frames
     */
    public int getAttack(int index) {
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
    private static void sortMode(PlayerType who) {
        /**
         * If you choose a characterEnum in lan, you can't choose your opponent
         */
        if (who == PlayerType.PLAYER1) {
        }
    }

    /**
     * Disables all buttons, used in online and playStory modes
     */
    public void preventCharacterSelection() {
        canSelectCharacter = false;
    }


    /**
     * Refresh selections
     */
    private void refreshSelections() {
        charPrevLoc = 0;
        oppPrevLoc = 0;
        selectedCharacter = false;
        selectedOpponent = false;
        //selectedCharIndex=0;
        //selectedOppIndex=0;
    }

    /**
     * Get the CharacterEnum getInfo
     *
     * @return characterEnum getInfo
     */
    public CharacterEnum getCharName() {
        return characterEnum;
    }

    /**
     * Get the opponents getInfo
     *
     * @return opponent getInfo
     */
    public CharacterEnum getOppName() {
        return opponentEnum;
    }

    /**
     * Selecting Raila
     *
     * @param type - opponent (PlayerType.PLAYER2) or characterEnum (PlayerType.PLAYER1)
     */
    public void selRaila(PlayerType type) {
        primaryNotice(Language.get().get(84));
        if (type == PlayerType.PLAYER1) //when selecting char
        {
            playSelectSound();
            selectedCharacter = true;
            Characters.get().prepare(characterEnum = CharacterEnum.RAILA);
            charPrevLoc = selectedCharIndex = characterEnum.index();
            if (NetworkManager.get().isOnline()) {
                NetworkManager.get().send(NetworkConstants.SEL_RAILA);
                preventCharacterSelection();
            }
        } else if (type == PlayerType.PLAYER2 && !selectedOpponent) {
            playSelectSound();
            selectedOpponent = true;
            Characters.get().prepareO(opponentEnum = CharacterEnum.RAILA);
            selectedOppIndex = oppPrevLoc = opponentEnum.index();
        }
    }

    /**
     * Selecting Subiya
     *
     * @param type - opponent (PlayerType.PLAYER2) or characterEnum (PlayerType.PLAYER1)
     */
    public void selSubiya(PlayerType type) {
        if (type == PlayerType.PLAYER1) //when selecting char
        {
            primaryNotice(Language.get().get(85));
            playSelectSound();
            selectedCharacter = true;
            Characters.get().prepare(characterEnum = CharacterEnum.SUBIYA);
            charPrevLoc = selectedCharIndex = characterEnum.index();
            charDesc = Characters.get().getCharacter().getDescSmall();
            if (NetworkManager.get().isOnline()) {
                NetworkManager.get().send(NetworkConstants.SEL_SUBIYA);
                preventCharacterSelection();
            }
        }
        if (type == PlayerType.PLAYER2 && !selectedOpponent) // when selecting opponent
        {
            playSelectSound();
            selectedOpponent = true;
            Characters.get().prepareO(opponentEnum = CharacterEnum.SUBIYA);
            selectedOppIndex = oppPrevLoc = opponentEnum.index();
        }
    }

    /**
     * Selecting Lynx
     *
     * @param type - opponent (PlayerType.PLAYER2) or characterEnum (PlayerType.PLAYER1)
     */
    public void selLynx(PlayerType type) {
        primaryNotice(Language.get().get(86));
        if (type == PlayerType.PLAYER1) //when selecting char
        {
            playSelectSound();
            selectedCharacter = true;
            Characters.get().prepare(characterEnum = CharacterEnum.LYNX);
            charPrevLoc = selectedCharIndex = characterEnum.index();
            charDesc = Characters.get().getCharacter().getDescSmall();
            if (NetworkManager.get().isOnline()) {
                NetworkManager.get().send(NetworkConstants.SEL_LYNX);
                preventCharacterSelection();
            }
        }
        if (type == PlayerType.PLAYER2 && !selectedOpponent) // when selecting opponent
        {
            playSelectSound();
            selectedOpponent = true;
            Characters.get().prepareO(opponentEnum = CharacterEnum.LYNX);
            selectedOppIndex = oppPrevLoc = opponentEnum.index();
        }
    }

    /**
     * Selecting Aisha
     *
     * @param type - opponent (PlayerType.PLAYER2) or characterEnum (PlayerType.PLAYER1)
     */
    public void selAisha(PlayerType type) {
        primaryNotice(Language.get().get(87));
        if (type == PlayerType.PLAYER1) //when selecting char
        {
            playSelectSound();
            selectedCharacter = true;
            Characters.get().prepare( characterEnum = CharacterEnum.AISHA);
            charPrevLoc = selectedCharIndex = characterEnum.index();
            charDesc = Characters.get().getCharacter().getDescSmall();
            if (NetworkManager.get().isOnline()) {
                NetworkManager.get().send(NetworkConstants.SEL_ALEX);
                preventCharacterSelection();
            }
        }
        if (type == PlayerType.PLAYER2 && !selectedOpponent) // when selecting opponent
        {
            playSelectSound();
            selectedOpponent = true;
            Characters.get().prepareO(opponentEnum = CharacterEnum.AISHA);
            selectedOppIndex = oppPrevLoc = opponentEnum.index();
        }
    }

    /**
     * Selecting Ade
     *
     * @param type - opponent (PlayerType.PLAYER2) or characterEnum (PlayerType.PLAYER1)
     */
    public void selAde(PlayerType type) {
        primaryNotice(Language.get().get(88));
        if (type == PlayerType.PLAYER1) //when selecting char
        {
            playSelectSound();
            selectedCharacter = true;
            Characters.get().prepare(characterEnum = CharacterEnum.ADE);
            charPrevLoc = selectedCharIndex = characterEnum.index();
            charDesc = Characters.get().getCharacter().getDescSmall();
            if (NetworkManager.get().isOnline()) {
                NetworkManager.get().send(NetworkConstants.SEL_ADE);
                preventCharacterSelection();
            }
        }
        if (type == PlayerType.PLAYER2 && !selectedOpponent) // when selecting opponent
        {
            playSelectSound();
            selectedOpponent = true;
            Characters.get().prepareO(opponentEnum = CharacterEnum.ADE);
            selectedOppIndex = oppPrevLoc = opponentEnum.index();
        }
    }

    /**
     * Selecting Aisha
     *
     * @param type - opponent (PlayerType.PLAYER2) or characterEnum (PlayerType.PLAYER1)
     */
    public void selRav(PlayerType type) {
        primaryNotice(Language.get().get(89));
        if (type == PlayerType.PLAYER1) //when selecting char
        {
            playSelectSound();
            selectedCharacter = true;
            Characters.get().prepare(characterEnum = CharacterEnum.RAVAGE);
            charPrevLoc = selectedCharIndex = characterEnum.index();
            charDesc = Characters.get().getCharacter().getDescSmall();
            if (NetworkManager.get().isOnline()) {
                NetworkManager.get().send(NetworkConstants.SEL_RAVAGE);
                preventCharacterSelection();
            }
        }

        if (type == PlayerType.PLAYER2 && !selectedOpponent) // when selecting opponent
        {
            playSelectSound();
            selectedOpponent = true;
            Characters.get().prepareO(opponentEnum = CharacterEnum.RAVAGE);
            selectedOppIndex = oppPrevLoc = opponentEnum.index();
        }
    }

    /**
     * Selecting Jonah
     *
     * @param type - opponent (PlayerType.PLAYER2) or characterEnum (PlayerType.PLAYER1)
     */
    public void selJon(PlayerType type) {
        primaryNotice(Language.get().get(90));
        if (type == PlayerType.PLAYER1) //when selecting char
        {
            playSelectSound();
            selectedCharacter = true;
            Characters.get().prepare(characterEnum = CharacterEnum.JONAH);
            charPrevLoc = selectedCharIndex = characterEnum.index();
            charDesc = Characters.get().getCharacter().getDescSmall();
            if (NetworkManager.get().isOnline()) {
                NetworkManager.get().send(NetworkConstants.SEL_JOHN);
                preventCharacterSelection();
            }
        }

        if (type == PlayerType.PLAYER2 && !selectedOpponent) // when selecting opponent
        {
            playSelectSound();
            selectedOpponent = true;
            Characters.get().prepareO(opponentEnum = CharacterEnum.JONAH);
            selectedOppIndex = oppPrevLoc = opponentEnum.index();
        }
    }

    /**
     * Selecting NovaAdam
     *
     * @param type - opponent (PlayerType.PLAYER2) or characterEnum (PlayerType.PLAYER1)
     */
    public void selAdam(PlayerType type) {
        primaryNotice(Language.get().get(91));
        if (type == PlayerType.PLAYER1) //when selecting char
        {
            playSelectSound();
            selectedCharacter = true;
            Characters.get().prepare(characterEnum = CharacterEnum.ADAM);
            charPrevLoc = selectedCharIndex = characterEnum.index();
            charDesc = Characters.get().getCharacter().getDescSmall();
            if (NetworkManager.get().isOnline()) {
                NetworkManager.get().send(NetworkConstants.SEL_ADAM);
                preventCharacterSelection();
            }
        }
        if (type == PlayerType.PLAYER2 && !selectedOpponent) // when selecting opponent
        {
            playSelectSound();
            selectedOpponent = true;
            Characters.get().prepareO(opponentEnum = CharacterEnum.ADAM);
            selectedOppIndex = oppPrevLoc = opponentEnum.index();
        }
    }

    /**
     * Selecting NOVA NovaAdam
     *
     * @param type - opponent (PlayerType.PLAYER2) or characterEnum (PlayerType.PLAYER1)
     */
    public void selNOVAAdam(PlayerType type) {
        primaryNotice(Language.get().get(92));
        if (type == PlayerType.PLAYER1) //when selecting char
        {
            playSelectSound();
            selectedCharacter = true;
            Characters.get().prepare(characterEnum = CharacterEnum.NOVA_ADAM);
            charPrevLoc = selectedCharIndex = characterEnum.index();
            charDesc = Characters.get().getCharacter().getDescSmall();
            if (NetworkManager.get().isOnline()) {
                NetworkManager.get().send(NetworkConstants.SEL_NOVA_ADAM);
                preventCharacterSelection();
            }
        }

        if (type == PlayerType.PLAYER2 && !selectedOpponent) // when selecting opponent
        {
            playSelectSound();
            selectedOpponent = true;
            Characters.get().prepareO(opponentEnum = CharacterEnum.NOVA_ADAM);
            selectedOppIndex = oppPrevLoc = opponentEnum.index();
        }
    }

    /**
     * Selecting Azaria
     *
     * @param type - opponent (PlayerType.PLAYER2) or characterEnum (PlayerType.PLAYER1)
     */
    public void selAza(PlayerType type) {
        primaryNotice(Language.get().get(93));
        if (type == PlayerType.PLAYER1) //when selecting char
        {
            playSelectSound();
            selectedCharacter = true;
            Characters.get().prepare(characterEnum = CharacterEnum.AZARIA);
            charPrevLoc = selectedCharIndex = characterEnum.index();
            charDesc = Characters.get().getCharacter().getDescSmall();
            if (NetworkManager.get().isOnline()) {
                NetworkManager.get().send(NetworkConstants.SEL_AZARIA);
                preventCharacterSelection();
            }
        }
        if (type == PlayerType.PLAYER2 && !selectedOpponent) // when selecting opponent
        {
            playSelectSound();
            selectedOpponent = true;
            Characters.get().prepareO(opponentEnum = CharacterEnum.AZARIA);
            selectedOppIndex = oppPrevLoc = opponentEnum.index();
        }
    }

    /**
     * Selecting Sorrowe
     *
     * @param type - opponent (PlayerType.PLAYER2) or characterEnum (PlayerType.PLAYER1)
     */
    public void selSorr(PlayerType type) {
        primaryNotice(Language.get().get(94));
        if (type == PlayerType.PLAYER1) //when selecting char
        {
            playSelectSound();
            selectedCharacter = true;
            Characters.get().prepare(characterEnum = CharacterEnum.SORROWE);
            charPrevLoc = selectedCharIndex = characterEnum.index();
            charDesc = Characters.get().getCharacter().getDescSmall();
            if (NetworkManager.get().isOnline()) {
                NetworkManager.get().send(NetworkConstants.SEL_SORROWE);
                preventCharacterSelection();
            }
        }
        if (type == PlayerType.PLAYER2 && !selectedOpponent) // when selecting opponent
        {
            playSelectSound();
            selectedOpponent = true;
            Characters.get().prepareO(opponentEnum = CharacterEnum.SORROWE);
            selectedOppIndex = oppPrevLoc = opponentEnum.index();
        }
    }

    /**
     * Selecting Sorrowe
     *
     * @param type - opponent (PlayerType.PLAYER2) or characterEnum (PlayerType.PLAYER1)
     */
    public void selThing(PlayerType type) {
        primaryNotice("..........");
        if (type == PlayerType.PLAYER1) //when selecting char
        {
            playSelectSound();
            selectedCharacter = true;
            characterEnum = CharacterEnum.THING;
            Characters.get().prepare(characterEnum);
            charPrevLoc = selectedCharIndex = characterEnum.index();
            charDesc = Characters.get().getCharacter().getDescSmall();
            if (NetworkManager.get().isOnline()) {
                NetworkManager.get().send(NetworkConstants.SEL_THING);
                preventCharacterSelection();
            }
        }

        if (type == PlayerType.PLAYER2 && !selectedOpponent) // when selecting opponent
        {
            playSelectSound();
            selectedOpponent = true;
            opponentEnum = CharacterEnum.THING;
            Characters.get().prepareO(opponentEnum);
            selectedOppIndex = oppPrevLoc = opponentEnum.index();
        } else if (type == PlayerType.BOSS && !selectedOpponent) // when selecting opponent as boss
        {
            playSelectSound();
            selectedOpponent = true;
            Characters.get().prepareO(opponentEnum = CharacterEnum.THING);
            selectedOppIndex = oppPrevLoc = opponentEnum.index();
        }
    }

    public void proceed2False() {
        selectedOpponent = false;
    }

    public void animateCharSelect() {
        if (animatorThreadRunning) return;
        new Thread() {
            @Override
            public void run() {
                this.setName("CharacterEnum select bg animation thread");
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
                        e.printStackTrace(System.err);
                    }
                }
                while (ScndGenLegends.get().getSubMode() == SubMode.MAIN_MENU);
                animatorThreadRunning = false;
            }
        }.start();
    }

    /**
     * Selects characterEnum
     */
    public void selectCharacter() {
        if (!canSelectCharacter) {
            errorSound();
            return;
        }
        int row = getHindex();
        int column = getVindex();
        int computedPosition = (columns * column) + row;
        CharacterEnum character = characterLookup.get(computedPosition);
        switch (character) {
            case SUBIYA:
                selSubiya(selectedCharacter ? PlayerType.PLAYER2 : PlayerType.PLAYER1);
                break;
            case RAILA:
                selRaila(selectedCharacter ? PlayerType.PLAYER2 : PlayerType.PLAYER1);
                break;
            case LYNX:
                selLynx(selectedCharacter ? PlayerType.PLAYER2 : PlayerType.PLAYER1);
                break;
            case AISHA:
                selAisha(selectedCharacter ? PlayerType.PLAYER2 : PlayerType.PLAYER1);
                break;
            case ADE:
                selAde(selectedCharacter ? PlayerType.PLAYER2 : PlayerType.PLAYER1);
                break;
            case RAVAGE:
                selRav(selectedCharacter ? PlayerType.PLAYER2 : PlayerType.PLAYER1);
                break;
            case JONAH:
                selJon(selectedCharacter ? PlayerType.PLAYER2 : PlayerType.PLAYER1);
                break;
            case ADAM:
                selAdam(selectedCharacter ? PlayerType.PLAYER2 : PlayerType.PLAYER1);
                break;
            case NOVA_ADAM:
                selNOVAAdam(selectedCharacter ? PlayerType.PLAYER2 : PlayerType.PLAYER1);
                break;
            case AZARIA:
                selAza(selectedCharacter ? PlayerType.PLAYER2 : PlayerType.PLAYER1);
                break;
            case SORROWE:
                selSorr(selectedCharacter ? PlayerType.PLAYER2 : PlayerType.PLAYER1);
                break;
            case THING:
                selThing(selectedCharacter ? PlayerType.PLAYER2 : PlayerType.PLAYER1);
                break;
        }
    }

    /**
     * Plays error sound
     */
    public void errorSound() {
        Audio error = new Audio("audio/error.ogg", AudioType.SOUND, false);
        error.play();
    }

    /**
     * Horizontal index
     *
     * @return column
     */
    public int getHindex() {
        return column;
    }

    /**
     * Vertical index
     *
     * @return row
     */
    public int getVindex() {
        return row;
    }

    /**
     * Animates captions
     */
    protected void animatePortratit() {
        x = -100;
        p1Opac = 0.0f;
        opacChar = 0.0f;
    }

    /**
     * Checks if within number of CharacterEnum
     */
    public boolean isWithinRange() {
        boolean ans = false;
        int whichChar = ((row * columns) + column) - 1;
        if (whichChar <= numOfCharacters) {
            ans = true;
        }
        return ans;
    }

    /**
     * When both playes are selected, this prevents movement.
     *
     * @return false if both CharacterEnum have been selected, true if only one is selected
     */
    public boolean bothArentSelected() {
        boolean answer = true;
        if (selectedCharacter && selectedOpponent) {
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

    public void setSelectedCharacter(boolean selectedCharacter) {
        this.selectedCharacter = selectedCharacter;
    }

    public void setSelectedOpponent(boolean selectedOpponent) {
        this.selectedOpponent = selectedOpponent;
    }

    public void setSelectedOppIndex(int selectedOppIndex) {
        this.selectedOppIndex = selectedOppIndex;
    }

    public void mouseMoved(MouseEvent m) {
    }

    public void mouseClicked(MouseEvent mouseEvent) {
        switch (mouseEvent.getButton()) {
            case PRIMARY:
                onAccept();
                break;
            case SECONDARY:
                onBackCancel();
                break;
            case MIDDLE:
                break;
        }
    }
}

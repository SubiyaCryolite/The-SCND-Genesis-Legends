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


import com.scndgen.legends.enums.CharacterEnum;
import com.scndgen.legends.enums.CharacterState;
import com.scndgen.legends.render.RenderCharacterSelectionScreen;
import com.scndgen.legends.render.RenderGameplay;

/**
 * This class should be self explainatory -_-
 *
 * @author Ifunga Ndana
 */
public class Characters {

    public String[] moveMusicOpp = new String[8];
    public String[] moveMusicChar = new String[8];
    public int[] pointsArr = new int[12];
    public String[] typeArray = new String[4];
    //AIRCON 12 GLOWING HOT GIMP 2.6.8
    private int damageMultiplierOpp, damageMultiplierChar, minCharlife, minOppLife2, currCharLife3, minOppLife, currCharLife, currOppLife2, currOppLife, points, maxPoints;
    private float activityRecoverRateChar, activityRecoveryRateOpp;
    private String characterName, opponentName;
    private com.scndgen.legends.characters.Character character, opponent;
    private static Characters instance;

    public static synchronized Characters getInstance() {
        if (instance == null)
            instance = new Characters();
        return instance;
    }

    private Characters() {

    }

    //called when characterEnum damaged
    public void setCurrLifeChar(int life) {
        currCharLife = life;
        //percentages
        if (life < minCharlife) {
            minCharlife = life;
            //System.out.println("min char life "+minCharlife);
        }
    }

    //called when opp damaged
    public void setCurrLifeOpp(int life) {
        currOppLife = life;

        //percentages
        if (life < minOppLife) {
            minOppLife = life;
        }
    }

    //called when opp damaged
    public void setCurrLifeOpp2(int life) {
        currOppLife2 = life;

        //percentages
        if (life < minOppLife2) {
            minOppLife2 = life;
        }
    }

    //called when opp damaged
    public void setCurrLifeChar2(int life) {
        currCharLife3 = life;

        //percentages
        if (life < currCharLife3) {
            currCharLife3 = life;
        }
    }

    public float getCharMinLife() {
        return (float) minCharlife;
    }

    public float getCharCurrLife() {
        return (float) currCharLife;
    }

    public float getOppMinLife() {
        return (float) minOppLife;
    }

    public float getOppCurrLife() {
        return (float) currOppLife;
    }

    public float getPoints() {
        return (float) points / maxPoints;
    }

    public void setPoints(int amount) {
        points = amount;
        maxPoints = amount;
    }

    public void alterPoints(int thisMuch) {
        points = points - thisMuch;
    }

    /*
     *
     */
    public void alterPoints2(int index) {
        if (RenderGameplay.getInstance().getNumOfAttacks() > 1) {
            RenderGameplay.getInstance().setNumOfAttacks(1);
            points = points + pointsArr[index];
        }
    }

    /**
     * SET damage multipliers, used to strengthen/weaken attacks
     *
     * @param characterState the person calling the method
     * @param thisMuch       the number to alter by
     */
    public void setDamageCounter(CharacterState characterState, int thisMuch) {
        if (characterState == CharacterState.CHARACTER) {
            damageMultiplierOpp = thisMuch;
        }

        if (characterState == CharacterState.OPPONENT) {
            damageMultiplierChar = thisMuch;
        }
    }

    public int getDamageMultiplier(CharacterState per) {
        int myInt = 0;

        if (per == CharacterState.CHARACTER) {
            myInt = damageMultiplierOpp;
        } else if (per == CharacterState.OPPONENT) {
            myInt = damageMultiplierChar;
        }

        return myInt;
    }

    public float getCharRecoverySpeed() {
        return activityRecoverRateChar;
    }

    public float getOppRecoverySpeed() {
        return activityRecoveryRateOpp;
    }

    public void incrementSpeedRate(CharacterState who, float thisMuch) {
        if (who == CharacterState.CHARACTER) {
            activityRecoverRateChar = activityRecoverRateChar + thisMuch;
        }
        if (who == CharacterState.OPPONENT) {
            activityRecoveryRateOpp = activityRecoveryRateOpp + thisMuch;
        }
    }

    //--------public accessor methods-----------------
    public String getCharName() {
        return characterName;
    }

    public String getOppName() {
        return opponentName;
    }

    public void setOppName(String thisName) {
        minOppLife = 100;
        currOppLife = 100;
        opponentName = thisName;
    }

    public com.scndgen.legends.characters.Character getCharacter() {
        return character;
    }

    public com.scndgen.legends.characters.Character getOpponent() {
        return opponent;
    }

    public void prepare(CharacterEnum characterEnum) {

        minOppLife = 100;
        currOppLife = 100;
        minCharlife = 100;
        currCharLife = 100;
        setDamageCounter(CharacterState.CHARACTER, 12);

        switch (characterEnum) {
            case SUBIYA:
                this.character = new Subiya();
                break;
            case RAILA:
                this.character = new Raila();
                break;
            case LYNX:
                this.character = new Lynx();
                break;
            case AISHA:
                this.character = new Aisha();
                break;
            case RAVAGE:
                this.character = new Ravage();
                break;
            case ADE:
                this.character = new Ade();
                break;
            case JONAH:
                this.character = new Jonah();
                break;
            case ADAM:
                this.character = new Adam();
                break;
            case NOVA_ADAM:
                this.character = new NovaAdam();
                break;
            case AZARIA:
                this.character = new Azaria();
                break;
            case SORROWE:
                this.character = new Sorrowe();
                break;
            case THING:
                this.character = new Thing(0);
                break;
        }

        characterName = characterEnum.name();
        RenderCharacterSelectionScreen.getInstance().setSelectedCharIndex(characterEnum.index());
        activityRecoverRateChar = this.character.getRecovSpeed();
        setPoints(this.character.getPoints());
        RenderGameplay.getInstance().setLife(this.character.getLife());
        RenderGameplay.getInstance().setMaxLife(this.character.getLife());
    }

    public void prepareO(CharacterEnum characterEnum) {
        minOppLife = 100;
        currOppLife = 100;
        minCharlife = 100;
        currCharLife = 100;
        setDamageCounter(CharacterState.OPPONENT, 12);
        switch (characterEnum) {
            case SUBIYA:
                opponent = new Subiya();
                break;
            case RAILA:
                opponent = new Raila();
                break;
            case LYNX:
                opponent = new Lynx();
                break;
            case AISHA:
                opponent = new Aisha();
                break;
            case RAVAGE:
                opponent = new Ravage();
                break;
            case ADE:
                opponent = new Ade();
                break;
            case JONAH:
                opponent = new Jonah();
                break;
            case ADAM:
                opponent = new Adam();
                break;
            case NOVA_ADAM:
                opponent = new NovaAdam();
                break;
            case AZARIA:
                opponent = new Azaria();
                break;
            case SORROWE:
                opponent = new Sorrowe();
                break;
            case THING:
                opponent = new Thing(0);
                break;
        }
        opponentName = characterEnum.name();
        RenderCharacterSelectionScreen.getInstance().setSelectedOppIndex(characterEnum.index());
        activityRecoveryRateOpp = opponent.getRecovSpeed();
        RenderGameplay.getInstance().setOppLife(opponent.getLife());
        RenderGameplay.getInstance().setOppMaxLife(opponent.getLife());
        opponent.setAiProf();
    }

    /**
     * Added 19/jan/2011 by SubiyaCryolite -
     * resets every characterEnum
     */
    public void resetCharacters() {
        if (opponent != null)
            opponent.resetLimits();
        if (character != null)
            character.resetLimits();
        RenderCharacterSelectionScreen.getInstance().setCharacterSelected(false);
        RenderCharacterSelectionScreen.getInstance().setOpponentSelected(false);
    }
}

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
import com.scndgen.legends.enums.Player;
import com.scndgen.legends.render.RenderCharacterSelection;
import com.scndgen.legends.render.RenderGamePlay;

/**
 * This class should be self explainatory -_-
 *
 * @author Ifunga Ndana
 */
public class Characters {

    public int[] pointsArr = new int[12];
    public String[] typeArray = new String[4];
    //AIRCON 12 GLOWING HOT GIMP 2.6.8
    private float damageMultiplierOpp, damageMultiplierChar, minCharlife,  minOppLife, currCharLife,  currOppLife, points, maxPoints;
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
    public void setCurrLifeChar(float life) {
        currCharLife = life;
        if (life < minCharlife) {
            minCharlife = life;
        }
    }

    //called when opp damaged
    public void setCurrLifeOpp(float life) {
        currOppLife = life;
        if (life < minOppLife) {
            minOppLife = life;
        }
    }

    public float getCharMinLife() {
        return minCharlife;
    }

    public float getCharCurrLife() {
        return currCharLife;
    }

    public float getOppMinLife() {
        return minOppLife;
    }

    public float getOppCurrLife() {
        return currOppLife;
    }

    public float getPoints() {
        return points / maxPoints;
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
        if (RenderGamePlay.getInstance().getCharacterQueuedAttacks() > 1) {
            points += pointsArr[index];
        }
    }

    /**
     * SET damage multipliers, used to strengthen/weaken attacks
     *
     * @param player the person calling the method
     * @param thisMuch       the number to alter by
     */
    public void setDamageCounter(Player player, int thisMuch) {
        if (player == Player.CHARACTER) {
            damageMultiplierOpp = thisMuch;
        }

        if (player == Player.OPPONENT) {
            damageMultiplierChar = thisMuch;
        }
    }

    public float getDamageMultiplier(Player per) {
        float myInt = 0;
        if (per == Player.CHARACTER) {
            myInt = damageMultiplierOpp;
        } else if (per == Player.OPPONENT) {
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

    public void incrementSpeedRate(Player who, float thisMuch) {
        if (who == Player.CHARACTER) {
            activityRecoverRateChar = activityRecoverRateChar + thisMuch;
        }
        if (who == Player.OPPONENT) {
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
        setDamageCounter(Player.CHARACTER, 12);

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
        RenderCharacterSelection.getInstance().setSelectedCharIndex(characterEnum.index());
        activityRecoverRateChar = this.character.getAtbRecoveryRate();
        setPoints(this.character.getPoints());
        RenderGamePlay.getInstance().setCharacterHp(this.character.getLife());
        RenderGamePlay.getInstance().setMaxLife(this.character.getLife());
    }

    public void prepareO(CharacterEnum characterEnum) {
        minOppLife = 100;
        currOppLife = 100;
        minCharlife = 100;
        currCharLife = 100;
        setDamageCounter(Player.OPPONENT, 12);
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
        RenderCharacterSelection.getInstance().setSelectedOppIndex(characterEnum.index());
        activityRecoveryRateOpp = opponent.getAtbRecoveryRate();
        RenderGamePlay.getInstance().setOpponentHp(opponent.getLife());
        RenderGamePlay.getInstance().setOpponentMaximumHp(opponent.getLife());
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
        RenderCharacterSelection.getInstance().setSelectedCharacter(false);
        RenderCharacterSelection.getInstance().setSelectedOpponent(false);
    }
}

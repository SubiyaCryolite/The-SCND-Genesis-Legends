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

import com.scndgen.legends.constants.AudioConstants;
import com.scndgen.legends.enums.AudioType;
import com.scndgen.legends.enums.CharacterEnum;
import com.scndgen.legends.enums.Player;
import com.scndgen.legends.mode.GamePlay;
import com.scndgen.legends.render.RenderGamePlay;
import io.github.subiyacryolite.enginev1.AudioPlayback;
import io.github.subiyacryolite.enginev1.Loader;
import javafx.scene.image.Image;


/**
 * Basic characterEnum template
 *
 * @author ndana
 */
public abstract class Character {

    public String descSmall, name, attackStr;
    public String[] physical, celestia, status, bragRights;
    public int points, life, damage;
    public float damageMultiplier;
    public int[] behaviours1, behaviours2, behaviours3, behaviours4, behaviours5, limit;
    public float[] weakness;
    public float atbRecoveryRate;
    protected CharacterEnum characterEnum = CharacterEnum.SUBIYA;
    private Image[] sprites;
    private Loader pix;
    private String[] location;
    private boolean isMale;
    private int numberOfSprites = 12;

    public Character() {
        bragRights = new String[]{"", "", "", "", "", "", "", "", "", ""};
        isMale = true;
    }

    public void isNotMale() {
        isMale = false;
    }

    public boolean isMale() {
        return isMale;
    }

    public CharacterEnum getEnum() {
        return characterEnum;
    }

    protected final int celestiaMultiplier = 10;

    private void sortQue() {
        pix = new Loader();
        location = new String[numberOfSprites];
        location[0] = "images/" + characterEnum.data() + "/D.png";  //1
        location[1] = "images/" + characterEnum.data() + "/M1.png"; //2
        location[2] = "images/" + characterEnum.data() + "/M2.png"; //3
        location[3] = "images/" + characterEnum.data() + "/M3.png"; //4
        location[4] = "images/" + characterEnum.data() + "/M4.png"; //5
        location[5] = "images/" + characterEnum.data() + "/M5.png"; //6
        location[6] = "images/" + characterEnum.data() + "/M6.png"; //7
        location[7] = "images/" + characterEnum.data() + "/M7.png"; //8
        location[8] = "images/" + characterEnum.data() + "/M8.png"; //9
        location[9] = "images/" + characterEnum.data() + "/N.png"; //10
        location[10] = "images/" + characterEnum.data() + "/P.png"; //11
        location[11] = "images/trans.png"; //12
        System.out.println("Loaded " + characterEnum.data());
    }

    public int getNumberOfSprites() {
        return numberOfSprites;
    }

    public void loadMeHigh() {
        sortQue();
        sprites = new Image[numberOfSprites];
        sprites[0] = pix.load(location[0]);
        sprites[1] = pix.load(location[1]);
        sprites[2] = pix.load(location[2]);
        sprites[3] = pix.load(location[3]);
        sprites[4] = pix.load(location[4]);
        sprites[5] = pix.load(location[5]);
        sprites[6] = pix.load(location[6]);
        sprites[7] = pix.load(location[7]);
        sprites[8] = pix.load(location[8]);
        sprites[9] = pix.load(location[9]);
        sprites[10] = pix.load(location[10]);
        sprites[11] = pix.load(location[11]);
    }

    public Image getSprite(int i) {
        return sprites[i];
    }

    public abstract void attack(String attack, Player forWho, GamePlay gamePlay);

    /**
     * Gets the move set of the characterEnum
     *
     * @return array of physicalAttacks attacks
     */
    public void setCharacterAttackArrays() {
        RenderGamePlay.getInstance().setCharacterAttackArrays(physical, celestia, status);
    }

    /**
     * Gets the getInfo of a qued move
     *
     * @return The getInfo of the qued move
     */
    public String getMoveQued(int move) {
        int yus = move - 1;
        String txt = "";
        if (yus < 4) {
            txt = physical[yus];
        }
        if (yus >= 4 && yus <= 7) {
            txt = celestia[yus - 4];
        }
        if (yus >= 8 && yus <= 11) {
            txt = status[yus - 8];
        }
        return txt;
    }

    /**
     * Returns the CharacterEnum description. Used in menus
     *
     * @return The CharacterEnum description
     */
    public String getDescSmall() {
        return descSmall;
    }

    /**
     * Gets characterEnum to characterEnum battle taunts
     *
     * @param indx, the characterEnum
     * @return bragging text
     */
    public String getBraggingRights(int indx) {
        return name + ": " + bragRights[indx];
    }

    /**
     * Shall return the CharacterEnum characterHp
     *
     * @return characterEnum characterHp
     */
    public int getLife() {
        return life;
    }

    /**
     * Get the CharacterEnum recovery rate
     *
     * @return activity recovery rate
     */
    public float getAtbRecoveryRate() {
        return atbRecoveryRate;
    }

    /**
     * Gets the characterEnum getInfo
     *
     * @return characterEnum getInfo
     */
    public String getName() {
        return name;
    }

    /**
     * Set CharacterEnum AI, opponent 1
     */
    public void setAiProf() {
    }

    public int[] getAiProfile1()
    {
        return behaviours1;
    }

    public int[] getAiProfile2()
    {
        return behaviours2;
    }

    public int[] getAiProfile3()
    {
        return behaviours3;
    }

    public int[] getAiProfile4()
    {
        return behaviours4;
    }

    public int[] getAiProfile5()
    {
        return behaviours5;
    }

    public int getPoints() {
        return points;
    }

    /**
     * Added 19/January/2011 by SubiyaCryolite
     * resets the CharacterEnum limits after each fight
     */
    public void resetLimits() {
        for (int index = 0; index < limit.length; index++) {
            limit[index] = 0;
        }
    }

    public final int getCelestiaMultiplier() {
        return celestiaMultiplier;
    }

    public final float getDamageMultiplier() {
        return damageMultiplier;
    }

    public final void setDamageMultiplier(float value) {
        damageMultiplier = value;
    }

    public void play()
    {
        AudioPlayback sound3 = new AudioPlayback(AudioConstants.itemSound1(), AudioType.SOUND, false);
        sound3.play();
    }
}

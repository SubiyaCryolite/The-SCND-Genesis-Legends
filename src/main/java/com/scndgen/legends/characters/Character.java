/**************************************************************************

 The SCND Genesis: Legends is a fighting game based on THE SCND GENESIS,
 a webcomic created by Ifunga Ndana ((([<a href="https://www.scndgen.com">https://www.scndgen.com</a>]))).

 The SCND Genesis: Legends RMX  © 2017 Ifunga Ndana.

 The SCND Genesis: Legends is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 The SCND Genesis: Legends is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with The SCND Genesis: Legends. If not, see <<a href="http://www.gnu.org/licenses/">http://www.gnu.org/licenses/</a>>.

 **************************************************************************/
package com.scndgen.legends.characters;

import com.scndgen.legends.constants.AudioConstants;
import com.scndgen.legends.enums.AudioType;
import com.scndgen.legends.enums.CharacterEnum;
import com.scndgen.legends.enums.PlayerType;
import com.scndgen.legends.mode.GamePlay;
import com.scndgen.legends.render.RenderGamePlay;
import io.github.subiyacryolite.enginev2.AssetLoader;
import io.github.subiyacryolite.enginev2.Audio;
import io.github.subiyacryolite.enginev2.NvgImage;

import java.util.HashMap;


/**
 * Basic characterEnum template
 *
 * @author ndana
 */
public abstract class Character {

    public String descSmall, name, attackStr;
    public String[] physical, celestia, status;
    public final HashMap<Integer,String> bragRights = new HashMap<>();
    public int points, life, damage;
    public float strengthMultiplier;
    public int[] behaviours1, behaviours2, behaviours3, behaviours4, behaviours5, limit;
    public float[] weakness;
    public float atbRecoveryRate;
    protected CharacterEnum characterEnum = CharacterEnum.SUBIYA;
    private NvgImage[] sprites;
    private String[] location;
    private boolean isMale;
    private int numberOfSprites = 12;

    public Character() {
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
        location = new String[numberOfSprites];
        location[0] = "images/" + characterEnum.data() + "/D.png";
        location[1] = "images/" + characterEnum.data() + "/M1.png";
        location[2] = "images/" + characterEnum.data() + "/M2.png";
        location[3] = "images/" + characterEnum.data() + "/M3.png";
        location[4] = "images/" + characterEnum.data() + "/M4.png";
        location[5] = "images/" + characterEnum.data() + "/M5.png";
        location[6] = "images/" + characterEnum.data() + "/M6.png";
        location[7] = "images/" + characterEnum.data() + "/M7.png";
        location[8] = "images/" + characterEnum.data() + "/M8.png";
        location[9] = "images/" + characterEnum.data() + "/N.png";
        location[10] = "images/" + characterEnum.data() + "/P.png";
        location[11] = "images/trans.png";
        System.out.println("Loaded " + characterEnum.data());
    }

    public int getNumberOfSprites() {
        return numberOfSprites;
    }

    public void loadMeHigh(AssetLoader assets) {
        sortQue();
        sprites = new NvgImage[numberOfSprites];
        for (int i = 0; i < numberOfSprites; i++) {
            sprites[i] = assets.loadImage(location[i]);
        }
    }

    /**
     * @deprecated Prefer {@link #loadMeHigh(AssetLoader)}
     */
    @Deprecated
    public void loadMeHigh() {
        var legends = com.scndgen.legends.ScndGenLegends.get();
        if (legends == null || legends.loader() == null) {
            throw new IllegalStateException("Cannot load character sprites before the GLFW engine is ready");
        }
        loadMeHigh(legends.loader());
    }

    public NvgImage getSprite(int i) {
        return sprites[i];
    }

    public abstract void attack(String attack, PlayerType forWho, GamePlay gamePlay);

    /**
     * Gets the move set of the characterEnum
     *
     * @return array of physicalAttacks attacks
     */
    public void setCharacterAttackArrays() {
        RenderGamePlay.get().setCharacterAttackArrays(physical, celestia, status);
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
     * @param index, the characterEnum
     * @return bragging text
     */
    public String getBraggingRights(int index) {
        return name + ": " + bragRights.get(index);
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

    public int[] getAiProfile1() {
        return behaviours1;
    }

    public int[] getAiProfile2() {
        return behaviours2;
    }

    public int[] getAiProfile3() {
        return behaviours3;
    }

    public int[] getAiProfile4() {
        return behaviours4;
    }

    public int[] getAiProfile5() {
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

    public final float getStrengthMultiplier() {
        return strengthMultiplier;
    }

    public final void setStrengthMultiplier(float value) {
        strengthMultiplier = value;
    }

    public void play() {
        Audio sound3 = new Audio(AudioConstants.itemSound1(), AudioType.SOUND, false);
        sound3.play();
    }
}

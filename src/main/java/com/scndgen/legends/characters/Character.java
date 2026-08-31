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
import java.util.Map;


/**
 * Basic characterEnum template
 *
 * @author ndana
 */
public abstract class Character {

    public String descSmall, name, attackStr;
    public String[] physical, celestia, status;
    public final Map<Integer, String> bragRights = new HashMap<>();
    public int points, life, damage;
    public float strengthMultiplier;
    public int[] behaviours1, behaviours2, behaviours3, behaviours4, behaviours5, limit;
    public float atbRecoveryRate;
    protected CharacterEnum characterEnum = CharacterEnum.SUBIYA;
    private NvgImage[] sprites;
    private boolean isMale;
    private static final int NUMBER_OF_SPRITES = 12;

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

    private String[] spritePaths() {
        var folder = characterEnum.data();
        return new String[]{
                "images/" + folder + "/D.png",
                "images/" + folder + "/M1.png",
                "images/" + folder + "/M2.png",
                "images/" + folder + "/M3.png",
                "images/" + folder + "/M4.png",
                "images/" + folder + "/M5.png",
                "images/" + folder + "/M6.png",
                "images/" + folder + "/M7.png",
                "images/" + folder + "/M8.png",
                "images/" + folder + "/N.png",
                "images/" + folder + "/P.png",
                "images/trans.png"
        };
    }

    public int getNumberOfSprites() {
        return NUMBER_OF_SPRITES;
    }

    public void loadMeHigh(AssetLoader assets) {
        unloadSprites(assets);
        var paths = spritePaths();
        sprites = new NvgImage[NUMBER_OF_SPRITES];
        for (int i = 0; i < NUMBER_OF_SPRITES; i++) {
            sprites[i] = assets.loadImage(paths[i]);
        }
        System.out.println("Loaded " + characterEnum.data());
    }

    public void unloadSprites(AssetLoader assets) {
        if (sprites == null || assets == null) {
            sprites = null;
            return;
        }
        assets.free(sprites);
        sprites = null;
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
        var renderGamePlay = RenderGamePlay.get();
        renderGamePlay.setCharacterAttackArrays(physical, celestia, status);
    }

    /**
     * Gets the getInfo of a qued move
     *
     * @return The getInfo of the qued move
     */
    public String getMoveQued(int move) {
        int index = move - 1;
        if (index < 4) {
            return physical[index];
        }
        if (index <= 7) {
            return celestia[index - 4];
        }
        if (index <= 11) {
            return status[index - 8];
        }
        return "";
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
        new Audio(AudioConstants.itemSound1(), AudioType.SOUND, false).play();
    }

    protected void strike(GamePlay gamePlay, PlayerType forWho, String label, int dmg) {
        attackStr = label;
        damage = dmg;
        gamePlay.lifePhysUpdateSimple(forWho, damage);
    }

    protected void restore(GamePlay gamePlay, PlayerType forWho, String label, int dmg) {
        play();
        attackStr = label;
        damage = dmg;
        gamePlay.setStatIndex(1);
        if (forWho == PlayerType.PLAYER2) {
            gamePlay.updatePlayerLife(damage);
            gamePlay.setStatusPic(PlayerType.PLAYER1);
        } else {
            gamePlay.updateOpponentLife(damage);
            gamePlay.setStatusPic(PlayerType.PLAYER2);
        }
    }

    protected void boost(GamePlay gamePlay, PlayerType forWho) {
        limit[1]++;
        if (limit[1] > 4) {
            return;
        }
        play();
        attackStr = status[2];
        gamePlay.setStatIndex(3);
        if (forWho == PlayerType.PLAYER2) {
            gamePlay.setStatusPic(PlayerType.PLAYER1);
            gamePlay.alterStrength(PlayerType.PLAYER2, +1);
        } else {
            gamePlay.setStatusPic(PlayerType.PLAYER2);
            gamePlay.alterStrength(PlayerType.PLAYER1, +1);
        }
    }

    protected void weaken(GamePlay gamePlay, PlayerType forWho) {
        limit[0]++;
        if (limit[0] > 4) {
            return;
        }
        play();
        attackStr = status[3];
        gamePlay.setStatIndex(4);
        if (forWho == PlayerType.PLAYER2) {
            gamePlay.setStatusPic(PlayerType.PLAYER2);
            gamePlay.alterStrength(PlayerType.PLAYER1, -1);
        } else {
            gamePlay.setStatusPic(PlayerType.PLAYER1);
            gamePlay.alterStrength(PlayerType.PLAYER2, -1);
        }
    }
}

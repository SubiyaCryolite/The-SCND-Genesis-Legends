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
import com.scndgen.legends.threads.AudioPlayback;
import io.github.subiyacryolite.enginev1.JenesisImageLoader;
import javafx.scene.image.Image;


/**
 * Basic characterEnum template
 *
 * @author ndana
 */
public abstract class Character {

    public String descSmall, name, attackStr;
    public String[] physical, celestia, status, bragRights;
    public int points, life, damage, damageMultiplier;
    public int[] behaviours1, behaviours2, behaviours3, behaviours4, behaviours5, limit;
    public float[] weakness;
    public float atbRecoveryRate;
    protected AudioPlayback sound3;
    protected CharacterEnum characterEnum = CharacterEnum.SUBIYA;
    private Image[] highQualitySprites;
    private JenesisImageLoader pix;
    private String[] spriteLocation;
    private boolean isMale;
    private int numberOfSprites = 12;

    public Character() {
        bragRights = new String[]{"", "", "", "", "", "", "", "", "", ""};
        sound3 = new AudioPlayback(AudioPlayback.itemSound1(), false);
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
        pix = new JenesisImageLoader();
        spriteLocation = new String[numberOfSprites];
        spriteLocation[0] = "images/" + characterEnum.data() + "/D.png";  //1
        spriteLocation[1] = "images/" + characterEnum.data() + "/M1.png"; //2
        spriteLocation[2] = "images/" + characterEnum.data() + "/M2.png"; //3
        spriteLocation[3] = "images/" + characterEnum.data() + "/M3.png"; //4
        spriteLocation[4] = "images/" + characterEnum.data() + "/M4.png"; //5
        spriteLocation[5] = "images/" + characterEnum.data() + "/M5.png"; //6
        spriteLocation[6] = "images/" + characterEnum.data() + "/M6.png"; //7
        spriteLocation[7] = "images/" + characterEnum.data() + "/M7.png"; //8
        spriteLocation[8] = "images/" + characterEnum.data() + "/M8.png"; //9
        spriteLocation[9] = "images/" + characterEnum.data() + "/N.png"; //10
        spriteLocation[10] = "images/" + characterEnum.data() + "/P.png"; //11
        spriteLocation[11] = "images/trans.png"; //12
        System.out.println("FROM CHARACTER CLASS " + characterEnum.data());
    }

    public int getNumberOfSprites() {
        return numberOfSprites;
    }

    public void loadMeHigh() {
        sortQue();
        highQualitySprites = new Image[numberOfSprites];
        highQualitySprites[0] = pix.loadImage(spriteLocation[0]);  //1
        highQualitySprites[1] = pix.loadImage(spriteLocation[1]); //2
        highQualitySprites[2] = pix.loadImage(spriteLocation[2]); //3
        highQualitySprites[3] = pix.loadImage(spriteLocation[3]); //4
        highQualitySprites[4] = pix.loadImage(spriteLocation[4]); //5
        highQualitySprites[5] = pix.loadImage(spriteLocation[5]); //6
        highQualitySprites[6] = pix.loadImage(spriteLocation[6]); //7
        highQualitySprites[7] = pix.loadImage(spriteLocation[7]); //8
        highQualitySprites[8] = pix.loadImage(spriteLocation[8]); //9
        highQualitySprites[9] = pix.loadImage(spriteLocation[9]); //10
        highQualitySprites[10] = pix.loadImage(spriteLocation[10]); //11
        highQualitySprites[11] = pix.loadImage(spriteLocation[11]); //12
    }

    public Image getSprite(int i) {
        return highQualitySprites[i];
    }

    public abstract void attack(String attack, CharacterState forWho);

    /**
     * Gets the move set of the characterEnum
     *
     * @return array of physical attacks
     */
    public void setCharMoveset() {
        RenderGameplay.getInstance().setStats(physical, celestia, status);
    }

    /**
     * Gets the name of a qued move
     *
     * @return The name of the qued move
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
     * Gets the characterEnum name
     *
     * @return characterEnum name
     */
    public String getName() {
        return name;
    }

    /**
     * Set CharacterEnum AI, opponent 1
     */
    public void setAiProf() {
        RenderCharacterSelectionScreen.getInstance().setAISlot(behaviours1, 1);
        RenderCharacterSelectionScreen.getInstance().setAISlot(behaviours2, 2);
        RenderCharacterSelectionScreen.getInstance().setAISlot(behaviours3, 3);
        RenderCharacterSelectionScreen.getInstance().setAISlot(behaviours4, 4);
        RenderCharacterSelectionScreen.getInstance().setAISlot(behaviours5, 5);
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

    public final int getDamageMultiplier() {
        return damageMultiplier;
    }

    public final void setDamageMultiplier(int value) {
        damageMultiplier = value;
    }
}

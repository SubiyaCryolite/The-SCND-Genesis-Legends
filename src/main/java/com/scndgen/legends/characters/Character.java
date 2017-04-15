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

import com.scndgen.legends.LoginScreen;
import com.scndgen.legends.render.RenderCharacterSelectionScreen;
import com.scndgen.legends.render.RenderGameplay;
import com.scndgen.legends.enums.CharacterEnum;
import com.scndgen.legends.threads.AudioPlayback;
import io.github.subiyacryolite.enginev1.JenesisImageLoader;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.image.VolatileImage;

/**
 * Basic character template
 *
 * @author ndana
 */
public abstract class Character {

    public String descSmall, name, attackStr;
    public String[] physical, celestia, status, bragRights;
    //ints
    public int points, life, hitPoints, damage, celestiaMultiplier, damageMultiplier;
    public int[] behaviours1, behaviours2, behaviours3, behaviours4, behaviours5, limit;
    //floats
    public float[] weakness;
    public float actionRecoverRate, hpRecovRate;
    protected AudioPlayback sound3;
    protected CharacterEnum characterEnum = CharacterEnum.SUBIYA;
    //imgs
    private VolatileImage[] highQualitySprites;
    private Image[] lowQualitySprites;
    private JenesisImageLoader pix;
    //string
    private String[] spriteLocation;
    private boolean isMale;

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

    private void sortQue() {
        pix = new JenesisImageLoader();
        spriteLocation = new String[12];
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
        return spriteLocation.length;
    }

    public void loadMeHigh(ImageObserver obs) {
        sortQue();
        highQualitySprites = new VolatileImage[12];
        highQualitySprites[0] = pix.loadVolatileImage(spriteLocation[0], LoginScreen.getInstance().getdefSpriteWidth(), LoginScreen.getInstance().getdefSpriteHeight(), obs);  //1
        highQualitySprites[1] = pix.loadVolatileImage(spriteLocation[1], LoginScreen.getInstance().getdefSpriteWidth(), LoginScreen.getInstance().getdefSpriteHeight(), obs); //2
        highQualitySprites[2] = pix.loadVolatileImage(spriteLocation[2], LoginScreen.getInstance().getdefSpriteWidth(), LoginScreen.getInstance().getdefSpriteHeight(), obs); //3
        highQualitySprites[3] = pix.loadVolatileImage(spriteLocation[3], LoginScreen.getInstance().getdefSpriteWidth(), LoginScreen.getInstance().getdefSpriteHeight(), obs); //4
        highQualitySprites[4] = pix.loadVolatileImage(spriteLocation[4], LoginScreen.getInstance().getdefSpriteWidth(), LoginScreen.getInstance().getdefSpriteHeight(), obs); //5
        highQualitySprites[5] = pix.loadVolatileImage(spriteLocation[5], LoginScreen.getInstance().getdefSpriteWidth(), LoginScreen.getInstance().getdefSpriteHeight(), obs); //6
        highQualitySprites[6] = pix.loadVolatileImage(spriteLocation[6], LoginScreen.getInstance().getdefSpriteWidth(), LoginScreen.getInstance().getdefSpriteHeight(), obs); //7
        highQualitySprites[7] = pix.loadVolatileImage(spriteLocation[7], LoginScreen.getInstance().getdefSpriteWidth(), LoginScreen.getInstance().getdefSpriteHeight(), obs); //8
        highQualitySprites[8] = pix.loadVolatileImage(spriteLocation[8], LoginScreen.getInstance().getdefSpriteWidth(), LoginScreen.getInstance().getdefSpriteHeight(), obs); //9
        highQualitySprites[9] = pix.loadVolatileImage(spriteLocation[9], LoginScreen.getInstance().getdefSpriteWidth(), LoginScreen.getInstance().getdefSpriteHeight(), obs); //10
        highQualitySprites[10] = pix.loadVolatileImage(spriteLocation[10], LoginScreen.getInstance().getdefSpriteWidth(), LoginScreen.getInstance().getdefSpriteHeight(), obs); //11
        highQualitySprites[11] = pix.loadVolatileImage(spriteLocation[11], 32, 32, obs); //12
    }

    public void loadMeLow() {
        sortQue();
        lowQualitySprites = new Image[12];
        lowQualitySprites[0] = pix.loadImage(spriteLocation[0]);  //1
        lowQualitySprites[1] = pix.loadImage(spriteLocation[1]); //2
        lowQualitySprites[2] = pix.loadImage(spriteLocation[2]); //3
        lowQualitySprites[3] = pix.loadImage(spriteLocation[3]); //4
        lowQualitySprites[4] = pix.loadImage(spriteLocation[4]); //5
        lowQualitySprites[5] = pix.loadImage(spriteLocation[5]); //6
        lowQualitySprites[6] = pix.loadImage(spriteLocation[6]); //7
        lowQualitySprites[7] = pix.loadImage(spriteLocation[7]); //8
        lowQualitySprites[8] = pix.loadImage(spriteLocation[8]); //9
        lowQualitySprites[9] = pix.loadImage(spriteLocation[9]); //10
        lowQualitySprites[10] = pix.loadImage(spriteLocation[10]); //11
        lowQualitySprites[11] = pix.loadImage(spriteLocation[11]); //12
    }

    public Image getLowQualitySprite(int i) {
        return lowQualitySprites[i];
    }

    public VolatileImage getHighQualitySprite(int i) {
        return highQualitySprites[i];
    }

    public abstract void attack(String attack, int forWho);

    /**
     * Gets the move set of the character
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
     * Returns the Character description. Used in menus
     *
     * @return The Character description
     */
    public String getDescSmall() {
        return descSmall;
    }

    /**
     * Gets character to character battle taunts
     *
     * @param indx, the character
     * @return bragging text
     */
    public String getBraggingRights(int indx) {
        return name + ": " + bragRights[indx];
    }

    /**
     * Shall return the Character life
     *
     * @return character life
     */
    public int getLife() {
        return life;
    }

    /**
     * Get the Character recovery rate
     *
     * @return activity recovery rate
     */
    public float getRecovSpeed() {
        return actionRecoverRate;
    }

    /**
     * Get the Character hp recover rate
     *
     * @return hp recovery rate
     */
    public float getHPRecovRate() {
        return hpRecovRate;
    }

    /**
     * Gets the character name
     *
     * @return character name
     */
    public String getName() {
        return name;
    }

    /**
     * Get the Character hit points
     *
     * @return Hit Points
     */
    public int getHitPoints() {
        return hitPoints;
    }

    /**
     * Set Character AI, opponent 1
     */
    public void setAiProf() {
        RenderCharacterSelectionScreen.getInstance().setAISlot(behaviours1, 1);
        RenderCharacterSelectionScreen.getInstance().setAISlot(behaviours2, 2);
        RenderCharacterSelectionScreen.getInstance().setAISlot(behaviours3, 3);
        RenderCharacterSelectionScreen.getInstance().setAISlot(behaviours4, 4);
        RenderCharacterSelectionScreen.getInstance().setAISlot(behaviours5, 5);
    }

    /**
     * Set Character AI, opponent 2
     */
    public void setAiProf2() {
        RenderCharacterSelectionScreen.getInstance().setAISlot2(behaviours1, 1);
        RenderCharacterSelectionScreen.getInstance().setAISlot2(behaviours2, 2);
        RenderCharacterSelectionScreen.getInstance().setAISlot2(behaviours3, 3);
        RenderCharacterSelectionScreen.getInstance().setAISlot2(behaviours4, 4);
        RenderCharacterSelectionScreen.getInstance().setAISlot2(behaviours5, 5);
    }

    /**
     * Set Character AI, player 2
     */
    public void setAiProf3() {
        RenderCharacterSelectionScreen.getInstance().setAISlot3(behaviours1, 1);
        RenderCharacterSelectionScreen.getInstance().setAISlot3(behaviours2, 2);
        RenderCharacterSelectionScreen.getInstance().setAISlot3(behaviours3, 3);
        RenderCharacterSelectionScreen.getInstance().setAISlot3(behaviours4, 4);
        RenderCharacterSelectionScreen.getInstance().setAISlot3(behaviours5, 5);
    }

    public int getPoints() {
        return points;
    }

    /**
     * Added 19/January/2011 by SubiyaCryolite
     * resets the Character limits after each fight
     */
    public void resetLimits() {
        for (int index = 0; index < limit.length; index++) {
            limit[index] = 0;
        }
    }

    public final int getCelestiaMultiplier() {
        return celestiaMultiplier;
    }

    public final void setCelestiaMultiplier(int value) {
        celestiaMultiplier = value;
    }

    public final int getDamageMultiplier() {
        return damageMultiplier;
    }

    public final void setDamageMultiplier(int value) {
        damageMultiplier = value;
    }
}

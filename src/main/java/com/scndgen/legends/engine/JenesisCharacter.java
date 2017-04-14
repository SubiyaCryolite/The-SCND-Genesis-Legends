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
package com.scndgen.legends.engine;

import com.scndgen.legends.LoginScreen;
import com.scndgen.legends.arefactored.render.RenderStandardGameplay;
import com.scndgen.legends.arefactored.render.RenderCharacterSelectionScreen;
import com.scndgen.legends.enums.CharacterEnum;
import com.scndgen.legends.threads.ThreadMP3;
import io.github.subiyacryolite.enginev1.JenesisImageLoader;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.image.VolatileImage;

/**
 * Basic character template
 *
 * @author ndana
 */
public abstract class JenesisCharacter {

    public String descSmall, name, attackStr;
    public String[] physical, celestia, status, bragRights;
    //ints
    public int points, life, hitPoints, damage;
    public int[] behaviours1, behaviours2, behaviours3, behaviours4, behaviours5, limit;
    //floats
    public float[] weakness;
    public float actionRecoverRate, hpRecovRate;
    protected ThreadMP3 sound3;
    //imgs
    private VolatileImage[] spr;
    private Image[] spr2;
    private JenesisImageLoader pix;
    //string
    private String[] charSpritesStr;
    private boolean isMale;
    protected CharacterEnum characterEnum = CharacterEnum.SUBIYA;

    public JenesisCharacter() {
        bragRights = new String[]{"", "", "", "", "", "", "", "", "", ""};
        sound3 = new ThreadMP3(ThreadMP3.itemSound1(), false);
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
        charSpritesStr = new String[12];
        charSpritesStr[0] = "images/" + characterEnum.data() + "/D.png";  //1
        charSpritesStr[1] = "images/" + characterEnum.data() + "/M1.png"; //2
        charSpritesStr[2] = "images/" + characterEnum.data() + "/M2.png"; //3
        charSpritesStr[3] = "images/" + characterEnum.data() + "/M3.png"; //4
        charSpritesStr[4] = "images/" + characterEnum.data() + "/M4.png"; //5
        charSpritesStr[5] = "images/" + characterEnum.data() + "/M5.png"; //6
        charSpritesStr[6] = "images/" + characterEnum.data() + "/M6.png"; //7
        charSpritesStr[7] = "images/" + characterEnum.data() + "/M7.png"; //8
        charSpritesStr[8] = "images/" + characterEnum.data() + "/M8.png"; //9
        charSpritesStr[9] = "images/" + characterEnum.data() + "/N.png"; //10
        charSpritesStr[10] = "images/" + characterEnum.data() + "/P.png"; //11
        charSpritesStr[11] = "images/trans.png"; //12
        System.out.println("FROM CHARACTER CLASS " + characterEnum.data());
    }

    public int getNumberOfSprites() {
        return charSpritesStr.length;
    }

    public void loadMeHigh(ImageObserver obs) {
        sortQue();
        spr = new VolatileImage[12];
        spr[0] = pix.loadBImage2(charSpritesStr[0], LoginScreen.getInstance().getdefSpriteWidth(), LoginScreen.getInstance().getdefSpriteHeight(), obs);  //1
        spr[1] = pix.loadBImage2(charSpritesStr[1], LoginScreen.getInstance().getdefSpriteWidth(), LoginScreen.getInstance().getdefSpriteHeight(), obs); //2
        spr[2] = pix.loadBImage2(charSpritesStr[2], LoginScreen.getInstance().getdefSpriteWidth(), LoginScreen.getInstance().getdefSpriteHeight(), obs); //3
        spr[3] = pix.loadBImage2(charSpritesStr[3], LoginScreen.getInstance().getdefSpriteWidth(), LoginScreen.getInstance().getdefSpriteHeight(), obs); //4
        spr[4] = pix.loadBImage2(charSpritesStr[4], LoginScreen.getInstance().getdefSpriteWidth(), LoginScreen.getInstance().getdefSpriteHeight(), obs); //5
        spr[5] = pix.loadBImage2(charSpritesStr[5], LoginScreen.getInstance().getdefSpriteWidth(), LoginScreen.getInstance().getdefSpriteHeight(), obs); //6
        spr[6] = pix.loadBImage2(charSpritesStr[6], LoginScreen.getInstance().getdefSpriteWidth(), LoginScreen.getInstance().getdefSpriteHeight(), obs); //7
        spr[7] = pix.loadBImage2(charSpritesStr[7], LoginScreen.getInstance().getdefSpriteWidth(), LoginScreen.getInstance().getdefSpriteHeight(), obs); //8
        spr[8] = pix.loadBImage2(charSpritesStr[8], LoginScreen.getInstance().getdefSpriteWidth(), LoginScreen.getInstance().getdefSpriteHeight(), obs); //9
        spr[9] = pix.loadBImage2(charSpritesStr[9], LoginScreen.getInstance().getdefSpriteWidth(), LoginScreen.getInstance().getdefSpriteHeight(), obs); //10
        spr[10] = pix.loadBImage2(charSpritesStr[10], LoginScreen.getInstance().getdefSpriteWidth(), LoginScreen.getInstance().getdefSpriteHeight(), obs); //11
        spr[11] = pix.loadBImage2(charSpritesStr[11], 32, 32, obs); //12
    }

    public void loadMeLow() {
        sortQue();
        spr2 = new Image[12];
        spr2[0] = pix.loadImageFromToolkitNoScale(charSpritesStr[0]);  //1
        spr2[1] = pix.loadImageFromToolkitNoScale(charSpritesStr[1]); //2
        spr2[2] = pix.loadImageFromToolkitNoScale(charSpritesStr[2]); //3
        spr2[3] = pix.loadImageFromToolkitNoScale(charSpritesStr[3]); //4
        spr2[4] = pix.loadImageFromToolkitNoScale(charSpritesStr[4]); //5
        spr2[5] = pix.loadImageFromToolkitNoScale(charSpritesStr[5]); //6
        spr2[6] = pix.loadImageFromToolkitNoScale(charSpritesStr[6]); //7
        spr2[7] = pix.loadImageFromToolkitNoScale(charSpritesStr[7]); //8
        spr2[8] = pix.loadImageFromToolkitNoScale(charSpritesStr[8]); //9
        spr2[9] = pix.loadImageFromToolkitNoScale(charSpritesStr[9]); //10
        spr2[10] = pix.loadImageFromToolkitNoScale(charSpritesStr[10]); //11
        spr2[11] = pix.loadImageFromToolkitNoScale(charSpritesStr[11]); //12
    }

    public Image getMeLow(int i) {
        return spr2[i];
    }

    public VolatileImage getMeHigh(int i) {
        return spr[i];
    }

    public abstract void attack(String attack, int forWho);

    /**
     * Gets the move set of the character
     *
     * @return array of physical attacks
     */
    public void setCharMoveset() {
        RenderStandardGameplay.getInstance().setStats(physical, celestia, status);
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
}

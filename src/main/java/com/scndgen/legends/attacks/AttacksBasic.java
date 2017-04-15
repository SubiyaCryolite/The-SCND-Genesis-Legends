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
package com.scndgen.legends.attacks;

import com.scndgen.legends.render.RenderCharacterSelectionScreen;
import com.scndgen.legends.render.RenderGameplay;
import com.scndgen.legends.characters.Character;

public class AttacksBasic {

    public static boolean overlayEnabled;
    public String attackStr;
    public String[] attaX = new String[8]; //each car has 8 unique phtsical attacks
    public String statsStr = "phy";
    public String attackNum = "00";
    public int overlay = 0;
    public int oppPicAttack;
    public int victim = 0;
    public int move;
    public Character dude;

    /**
     * Creates an opponent object
     */
    public AttacksBasic() {
        dude = RenderCharacterSelectionScreen.getInstance().getPlayers().getOpponent();
    }

    /**
     * Attack sorter
     *
     * @param thisMove - the move to execute
     * @param forWho   - who's attacking
     */
    public void attack(int thisMove, int forWho, char attacker, char attackee) {
        move = thisMove;
        victim = forWho;

        if (thisMove > 8) {
            forWho = 999; //override for pose
        }
        if (thisMove == 0) {
            RenderGameplay.getInstance().setSprites(attacker, 9, 11);
            RenderGameplay.getInstance().setSprites(attackee, 9, 11);
            RenderGameplay.getInstance().showBattleMessage("");
        }

        if (thisMove == 1) {
            attackNum = "01";
        }

        if (thisMove == 2) {
            attackNum = "02";
        }

        if (thisMove == 3) {
            attackNum = "03";
        }

        if (thisMove == 4) {
            attackNum = "04";
        }

        if (thisMove == 5) {
            attackNum = "05";
        }

        if (thisMove == 6) {
            attackNum = "06";
        }

        if (thisMove == 7) {
            attackNum = "07";
        }

        if (thisMove == 8) {
            //char move thing
            attackNum = "08";
        }

        if (thisMove == 9) {
            attackNum = "09";
        }

        if (thisMove == 10) {
            attackNum = "10";
        }

        if (thisMove == 11) {
            attackNum = "11";
        }

        if (thisMove == 12) {
            attackNum = "12";
        }
        doThis(forWho, attacker, attackee);
        action();
        //regenerative moves update char, so overide forWho?
    }

    /**
     * call specific attack
     *
     * @param whoDoneIt who is active
     */
    public void doThis(int whoDoneIt, char attack, char target) {
        if (attack == 'c' || attack == 'a') {
            CharacterOverlayDisabled();
        } else {
            CharacterOverlayEnabled();
        }

        if (whoDoneIt == 999) {
            RenderGameplay.getInstance().setSprites(attack, 10, 11); //USE ITEM
        } else {
            //status moves use 10 (pose sprite)
            if (move > 9) {
                move = 10;
            }

            RenderGameplay.getInstance().setSprites(attack, move, 11); //attack
            RenderGameplay.getInstance().setSprites(target, 0, 11); //defend
        }
    }

    //------------------------------NEW CLEANY STUFF-----------------------

    /**
     * Checks if overlay is disabled
     *
     * @return overlay status
     */
    public boolean isOverlayDisabled() {
        return overlayEnabled;
    }

    /**
     * Checks if overlay is enabled
     *
     * @return overlay status
     */
    public boolean isOverlayEnabled() {
        return overlayEnabled;
    }

    /**
     * Disable the character overlay
     */
    public void CharacterOverlayDisabled() {
        overlayEnabled = false;
    }

    /**
     * Enable character overlay
     */
    public void CharacterOverlayEnabled() {
        overlayEnabled = true;
    }

    /**
     * ATTAAAAAAACK!!!!!!!
     */
    public void action() {
        dude.attack(attackNum, victim);
    }

    public Character getDude() {
        return dude;
    }
}

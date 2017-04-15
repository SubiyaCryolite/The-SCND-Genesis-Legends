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

import com.scndgen.legends.characters.Character;
import com.scndgen.legends.render.RenderGameplay;

public abstract class Attack {

    public boolean overlayEnabled;
    public String attackStr;
    public String attackIdentifier = "00";
    public int overlay = 0;
    public int victim = 0;
    public int attack;
    public Character opponent;

    /**
     * Attack sorter
     *
     * @param attack - the move to execute
     * @param target   - who's attacking
     */
    public void attack(int attack, int target, char source, char destination) {
        this.attack = attack;
        victim = target;
        if (attack > 8) {
            target = 999; //override for pose
        }
        if (attack == 0) {
            RenderGameplay.getInstance().setSprites(source, 9, 11);
            RenderGameplay.getInstance().setSprites(destination, 9, 11);
            RenderGameplay.getInstance().showBattleMessage("");
        }
        if (attack == 1) {
            attackIdentifier = "01";
        }

        if (attack == 2) {
            attackIdentifier = "02";
        }

        if (attack == 3) {
            attackIdentifier = "03";
        }

        if (attack == 4) {
            attackIdentifier = "04";
        }

        if (attack == 5) {
            attackIdentifier = "05";
        }

        if (attack == 6) {
            attackIdentifier = "06";
        }

        if (attack == 7) {
            attackIdentifier = "07";
        }

        if (attack == 8) {
            //char move thing
            attackIdentifier = "08";
        }

        if (attack == 9) {
            attackIdentifier = "09";
        }

        if (attack == 10) {
            attackIdentifier = "10";
        }

        if (attack == 11) {
            attackIdentifier = "11";
        }

        if (attack == 12) {
            attackIdentifier = "12";
        }
        doThis(target, source, destination);
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
            if (this.attack > 9) {
                this.attack = 10;
            }

            RenderGameplay.getInstance().setSprites(attack, this.attack, 11); //attack
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
        opponent.attack(attackIdentifier, victim);
    }

    public Character getOpponent() {
        return opponent;
    }
}

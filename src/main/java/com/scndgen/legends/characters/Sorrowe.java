/**************************************************************************

 The SCND Genesis: Legends is a fighting game based on THE SCND GENESIS,
 a webcomic created by Ifunga Ndana (https://www.scndgen.com).

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
 along with The SCND Genesis: Legends. If not, see <https://www.gnu.org/licenses/>.

 **************************************************************************/
package com.scndgen.legends.characters;

import com.scndgen.legends.enums.CharacterEnum;
import com.scndgen.legends.enums.PlayerType;
import com.scndgen.legends.mode.GamePlay;

import static com.scndgen.legends.enums.CharacterEnum.SORROWE;

/**
 * Sorrowe's class
 *
 * @author ndana
 */
public class Sorrowe extends Character {

    /**
     * Constructor
     */
    public Sorrowe() {
        descSmall = "Sorrowe - Specialised in celestiaAttacks combat and the flame element";
        name = "Sorrowe";
        characterEnum = SORROWE;
        isNotMale();
        physical = new String[]{"Lashing", "Whip-nado", "Lash assault", "Snared"};
        celestia = new String[]{"Hell Falme", "Hell Judgement", "Hell Blast", "Hell Blade"};
        status = new String[]{"Heal", "Cura EX", "Health ++", "E-Juice"};
        behaviours1 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8};
        behaviours2 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 10, 11};
        behaviours3 = new int[]{0, 1, 7, 8, 10, 11};
        behaviours4 = new int[]{0, 1, 9, 12, 10, 11};
        behaviours5 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        points = 1800;
        life = 31360;
        limit = new int[]{0, 0, 0, 0, 0};
        atbRecoveryRate = 2.02f;//2.10;
        bragRights.put(CharacterEnum.RAILA.index(), "Look what the cat dragged in");
        bragRights.put(CharacterEnum.SUBIYA.index(), "Sorry, you're not my type");
        bragRights.put(CharacterEnum.LYNX.index(), "A rea challenge, 'bout time");
        bragRights.put(CharacterEnum.AISHA.index(), "Weakling !!");
        bragRights.put(CharacterEnum.RAVAGE.index(), "You're incredibly annoying");
        bragRights.put(CharacterEnum.ADE.index(), "Don't go easy on me");
        bragRights.put(CharacterEnum.JONAH.index(), "Lets have some fun!");
        bragRights.put(CharacterEnum.ADAM.index(), "I shall surpass even you");
        bragRights.put(CharacterEnum.NOVA_ADAM.index(), "That power will soon be mine");
        bragRights.put(CharacterEnum.AZARIA.index(), "You don't scare me");
        bragRights.put(CharacterEnum.SORROWE.index(), "Let's do this");
        bragRights.put(CharacterEnum.THING.index(), "Ugh, disgusting");
    }

    @Override
    public void attack(String attack, PlayerType playerType, GamePlay gamePlay) {
        switch (attack) {
            case "01" -> strike(gamePlay, playerType, physical[0], 102);
            case "02" -> strike(gamePlay, playerType, physical[1], 105);
            case "03" -> strike(gamePlay, playerType, physical[2], 102);
            case "04" -> strike(gamePlay, playerType, physical[3], 103);
            case "05" -> strike(gamePlay, playerType, celestia[0], 102);
            case "06" -> strike(gamePlay, playerType, celestia[1], 101);
            case "07" -> strike(gamePlay, playerType, celestia[2], 108);
            case "08" -> strike(gamePlay, playerType, celestia[3], 105);
            case "09" -> restore(gamePlay, playerType, status[0], 78);
            case "10" -> restore(gamePlay, playerType, status[1], 80);
            case "11" -> restore(gamePlay, playerType, status[2], 84);
            case "12" -> restore(gamePlay, playerType, status[3], 76);
            default -> {
            }
        }
    }
}

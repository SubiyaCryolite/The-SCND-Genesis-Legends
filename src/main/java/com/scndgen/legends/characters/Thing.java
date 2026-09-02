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

/**
 * @author ndana
 */
public class Thing extends Character {

    private int bonus;

    public Thing(int y) {
        attackStr = "";
        descSmall = "The Thing - Origins unknown";
        name = "The Thing";
        characterEnum = CharacterEnum.THING;
        physical = new String[]{"Dash Strike", "Violent Thrust", "Epic Piercing", "Solar Flare"};
        celestia = new String[]{"Frost Bite", "Rock Rush", "Land Slide", "Solar Storm"};
        status = new String[]{"Heal Plus", "Heal EX", "Pain Killer", "Wound Spray"};
        behaviours1 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8};
        behaviours2 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 10, 11};
        behaviours3 = new int[]{0, 1, 7, 8, 10, 11};
        behaviours4 = new int[]{0, 1, 9, 12, 10, 11};
        behaviours5 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        points = 1800;
        damage = 0;
        if (y == 0) {
            life = 40000;
            bonus = 0;
        } else {
            life = 60000;
            bonus = 5;
        }
        limit = new int[]{0, 0, 0, 0, 0};
        atbRecoveryRate = 0.85f;//2.10;
        bragRights.put(CharacterEnum.RAILA.index(), "....");
        bragRights.put(CharacterEnum.SUBIYA.index(), "....");
        bragRights.put(CharacterEnum.LYNX.index(), "....");
        bragRights.put(CharacterEnum.AISHA.index(), "....");
        bragRights.put(CharacterEnum.RAVAGE.index(), "....");
        bragRights.put(CharacterEnum.ADE.index(), "....");
        bragRights.put(CharacterEnum.JONAH.index(), "....");
        bragRights.put(CharacterEnum.ADAM.index(), "....");
        bragRights.put(CharacterEnum.NOVA_ADAM.index(), "....");
        bragRights.put(CharacterEnum.AZARIA.index(), "....");
        bragRights.put(CharacterEnum.SORROWE.index(), "....");
        bragRights.put(CharacterEnum.THING.index(), "....");
    }

    @Override
    public void attack(String attack, PlayerType playerType, GamePlay gamePlay) {
        switch (attack) {
            case "01" -> strike(gamePlay, playerType, physical[0], bonus + 130);
            case "02" -> strike(gamePlay, playerType, physical[1], bonus + 129);
            case "03" -> strike(gamePlay, playerType, physical[2], bonus + 128);
            case "04" -> strike(gamePlay, playerType, physical[3], bonus + 127);
            case "05" -> strike(gamePlay, playerType, celestia[0], bonus + 130);
            case "06" -> strike(gamePlay, playerType, celestia[1], bonus + 129);
            case "07" -> strike(gamePlay, playerType, celestia[2], bonus + 128);
            case "08" -> strike(gamePlay, playerType, celestia[3], bonus + 127);
            case "09" -> restore(gamePlay, playerType, status[0], bonus + 106);
            case "10" -> restore(gamePlay, playerType, status[1], bonus + 100);
            case "11" -> restore(gamePlay, playerType, status[2], bonus + 108);
            case "12" -> restore(gamePlay, playerType, status[3], bonus + 102);
            default -> {
            }
        }
    }
}

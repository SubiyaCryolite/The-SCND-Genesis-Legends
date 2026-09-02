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

import static com.scndgen.legends.enums.CharacterEnum.RAVAGE;

/**
 * @author ndana
 */
public class Ravage extends Character {

    public Ravage() {
        descSmall = "Ravage - a fighter specialised in brute force via the Earth element";
        name = "Ravage";
        characterEnum = RAVAGE;
        life = 32000;
        limit = new int[]{0, 0, 0, 0, 0};
        physical = new String[]{"Strike", "Impale", "Stone Summon", "Deadly Snare"};
        celestia = new String[]{"Siezmic Slam", "Fist-Full", "Quake", "Boulder Rush"};
        status = new String[]{"Heal Plus", "Heal EX", "Energy Juice", "Weaken Opponent"};
        behaviours1 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8};
        behaviours2 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 10, 11};
        behaviours3 = new int[]{0, 1, 7, 8, 10, 11};
        behaviours4 = new int[]{0, 1, 9, 12, 10, 11};
        behaviours5 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        atbRecoveryRate = 1.70f;
        bragRights.put(CharacterEnum.RAILA.index(), "Pathetic weakling");
        bragRights.put(CharacterEnum.SUBIYA.index(), "Prepare to be owned");
        bragRights.put(CharacterEnum.LYNX.index(), "I owe you one....a beating that is");
        bragRights.put(CharacterEnum.AISHA.index(), "Lowly Saint, be gone!!!");
        bragRights.put(CharacterEnum.RAVAGE.index(), "Let's do this");
        bragRights.put(CharacterEnum.ADE.index(), "Lets see if you're strong enough");
        bragRights.put(CharacterEnum.JONAH.index(), "Lets do this bro");
        bragRights.put(CharacterEnum.ADAM.index(), "I won't hold back");
        bragRights.put(CharacterEnum.NOVA_ADAM.index(), "Goodie, no holding back, HA HA HA!!!");
        bragRights.put(CharacterEnum.AZARIA.index(), "I'll destroy you!!");
        bragRights.put(CharacterEnum.SORROWE.index(), "Don't get all high and mighty brat!!!");
        bragRights.put(CharacterEnum.THING.index(), "?????");
    }

    @Override
    public void attack(String attack, PlayerType target, GamePlay gamePlay) {
        switch (attack) {
            case "01" -> strike(gamePlay, target, physical[0], 108);
            case "02" -> strike(gamePlay, target, physical[1], 102);
            case "03" -> strike(gamePlay, target, physical[2], 103);
            case "04" -> strike(gamePlay, target, physical[3], 103);
            case "05" -> strike(gamePlay, target, celestia[0], 101);
            case "06" -> strike(gamePlay, target, celestia[1], 107);
            case "07" -> strike(gamePlay, target, celestia[2], 103);
            case "08" -> strike(gamePlay, target, celestia[3], 102);
            case "09" -> restore(gamePlay, target, status[0], 82);
            case "10" -> restore(gamePlay, target, status[1], 99);
            case "11" -> boost(gamePlay, target);
            case "12" -> weaken(gamePlay, target);
            default -> {
            }
        }
    }
}

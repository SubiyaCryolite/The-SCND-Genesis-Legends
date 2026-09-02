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

import static com.scndgen.legends.enums.CharacterEnum.NOVA_ADAM;

/**
 * @author ndana
 */
public class NovaAdam extends Character {

    public NovaAdam() {
        descSmall = "Nova Adam - an awakened Celestia Being specialised in celestiaAttacks combat";
        name = "NovaAdam";
        characterEnum = NOVA_ADAM;
        physical = new String[]{"Dark Flame", "Dark Rush", "Dark Slice", "Dark Ascent"};
        celestia = new String[]{"Nova Blitz", "Nova Torrent", "Nova Blaze", "Nova Frost"};
        status = new String[]{"Heal Plus", "Heal EX", "Pain Killer", "Wound Spray"};
        behaviours1 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8};
        behaviours2 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 10, 11};
        behaviours3 = new int[]{0, 1, 7, 8, 10, 11};
        behaviours4 = new int[]{0, 1, 9, 12, 10, 11};
        behaviours5 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        life = 38400;
        limit = new int[]{0, 0, 0, 0, 0};
        atbRecoveryRate = 1.10f;//2.10;
        bragRights.put(CharacterEnum.RAILA.index(), "Resistance is futile !!!");
        bragRights.put(CharacterEnum.SUBIYA.index(), "Resistance is futile !!!" );
        bragRights.put(CharacterEnum.LYNX.index(), "Resistance is futile !!!" );
        bragRights.put(CharacterEnum.AISHA.index(), "Resistance is futile !!!" );
        bragRights.put(CharacterEnum.RAVAGE.index(), "Resistance is futile !!!" );
        bragRights.put(CharacterEnum.ADE.index(),  "Resistance is futile !!!");
        bragRights.put(CharacterEnum.JONAH.index(), "Resistance is futile !!!" );
        bragRights.put(CharacterEnum.ADAM.index(), "Resistance is futile !!!" );
        bragRights.put(CharacterEnum.NOVA_ADAM.index(), "Resistance is... Hold on?" );
        bragRights.put(CharacterEnum.AZARIA.index(), "Resistance is futile !!!" );
        bragRights.put(CharacterEnum.SORROWE.index(), "Resistance is futile !!!" );
        bragRights.put(CharacterEnum.THING.index(),  "Resistance is futile !!!");
    }

    @Override
    public void attack(String attack, PlayerType playerType, GamePlay gamePlay) {
        switch (attack) {
            case "01" -> strike(gamePlay, playerType, physical[0], 128);
            case "02" -> strike(gamePlay, playerType, physical[1], 123);
            case "03" -> strike(gamePlay, playerType, physical[2], 122);
            case "04" -> strike(gamePlay, playerType, physical[3], 123);
            case "05" -> strike(gamePlay, playerType, celestia[0], 122);
            case "06" -> strike(gamePlay, playerType, celestia[1], 121);
            case "07" -> strike(gamePlay, playerType, celestia[2], 125);
            case "08" -> strike(gamePlay, playerType, celestia[3], 125);
            case "09" -> restore(gamePlay, playerType, status[0], 123);
            case "10" -> restore(gamePlay, playerType, status[1], 125);
            case "11" -> restore(gamePlay, playerType, status[2], 129);
            case "12" -> restore(gamePlay, playerType, status[3], 111);
            default -> {
            }
        }
    }
}

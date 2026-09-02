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
public class Azaria extends Character {

    public Azaria() {
        descSmall = "Azaria - Specialised in general combat and the water element";
        name = "Azaria";
        characterEnum = CharacterEnum.AZARIA;
        isNotMale();
        physical = new String[]{"Right Slash", "Left Slash", "Jaw Breaker", "Skull Smasher"};
        celestia = new String[]{"Hydro Blast", "Torrent Storm", "Violent Surge", "Torrent Slash"};
        status = new String[]{"Cure Plus", "Cure EX", "Holy Water", "Wound Spray"};
        behaviours1 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8};
        behaviours2 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 10, 11};
        behaviours3 = new int[]{0, 1, 7, 8, 10, 11};
        behaviours4 = new int[]{0, 1, 9, 12, 10, 11};
        behaviours5 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        points = 1800;
        life = 32000;
        limit = new int[]{0, 0, 0, 0, 0};
        atbRecoveryRate = 2.30f;//2.10;
        bragRights.put(CharacterEnum.RAILA.index(), "They grow up so fast, ready for a spanking little boy");
        bragRights.put(CharacterEnum.SUBIYA.index(), "You won't be so happy after this fight" );
        bragRights.put(CharacterEnum.LYNX.index(),  "You have potential to be great, but you gotta beat me to get there");
        bragRights.put(CharacterEnum.AISHA.index(),  "Lets show these guys what we can do, no holding back!!!");
        bragRights.put(CharacterEnum.RAVAGE.index(), "Filth! be gone" );
        bragRights.put(CharacterEnum.ADE.index(),  "Your attacks are cute. Cute becomes dumb in an instant");
        bragRights.put(CharacterEnum.JONAH.index(),  "You're the weakest of the group, just run away");
        bragRights.put(CharacterEnum.ADAM.index(),  "I won't let you pass" );
        bragRights.put(CharacterEnum.NOVA_ADAM.index(),  "Your power isn't absolute");
        bragRights.put(CharacterEnum.AZARIA.index(),  "Let's do this");
        bragRights.put(CharacterEnum.SORROWE.index(),  "You chose the wrong side little girl");
        bragRights.put(CharacterEnum.THING.index(),  "Looks like I'll have to put you down");
    }

    @Override
    public void attack(String attack, PlayerType forWho, GamePlay gamePlay) {
        switch (attack) {
            case "01" -> strike(gamePlay, forWho, physical[0], 102);
            case "02" -> strike(gamePlay, forWho, physical[1], 105);
            case "03" -> strike(gamePlay, forWho, physical[2], 102);
            case "04" -> strike(gamePlay, forWho, physical[3], 103);
            case "05" -> strike(gamePlay, forWho, celestia[0], 102);
            case "06" -> strike(gamePlay, forWho, celestia[1], 101);
            case "07" -> strike(gamePlay, forWho, celestia[2], 108);
            case "08" -> strike(gamePlay, forWho, celestia[3], 105);
            case "09" -> restore(gamePlay, forWho, status[0], 78);
            case "10" -> restore(gamePlay, forWho, status[1], 80);
            case "11" -> restore(gamePlay, forWho, status[2], 84);
            case "12" -> restore(gamePlay, forWho, status[3], 76);
            default -> {
            }
        }
    }
}

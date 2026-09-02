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

import static com.scndgen.legends.enums.CharacterEnum.ADE;

/**
 * @author ndana
 */
public class Ade extends Character {

    public Ade() {
        descSmall = "Ade - a fighter utilising the air element";
        name = "Ade";
        characterEnum = ADE;
        physical = new String[]{"Tornado Blast", "Hurricane Sphere", "Hurricane Barrage", "Violent Burst"};
        celestia = new String[]{"Crush Down", "Vortex Blades", "Cursed Seal", "Dark Swirl"};
        status = new String[]{"Heal Plus", "Heal EX", "Bandage", "Wound Spray"};
        behaviours1 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8};
        behaviours2 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 10, 11};
        behaviours3 = new int[]{0, 1, 7, 8, 10, 11};
        behaviours4 = new int[]{0, 1, 9, 12, 10, 11};
        behaviours5 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        points = 2200;
        life = 33600;
        limit = new int[]{0, 0, 0, 0, 0};
        atbRecoveryRate = 1.60f;
        bragRights.put(CharacterEnum.RAILA.index(), "Be gone");
        bragRights.put(CharacterEnum.SUBIYA.index(), "Weakling");
        bragRights.put(CharacterEnum.LYNX.index(), "Pathetic");
        bragRights.put(CharacterEnum.AISHA.index(), "Is this a joke?");
        bragRights.put(CharacterEnum.RAVAGE.index(), "Not you again");
        bragRights.put(CharacterEnum.ADE.index(), "Lets do this");
        bragRights.put(CharacterEnum.JONAH.index(), "You have more sense than you're brother, give up now");
        bragRights.put(CharacterEnum.ADAM.index(), "I won't hold back");
        bragRights.put(CharacterEnum.NOVA_ADAM.index(), "It's an honour to face you in this form");
        bragRights.put(CharacterEnum.AZARIA.index(), "Your title doesn't scare me, I'll still destroy you");
        bragRights.put(CharacterEnum.SORROWE.index(), "Sorrowe, don't get big headed");
        bragRights.put(CharacterEnum.THING.index(), "I've dealt with worse?");
    }

    @Override
    public void attack(String attack, PlayerType forWho, GamePlay gamePlay) {
        switch (attack) {
            case "00" -> strike(gamePlay, forWho, physical[0], 50);
            case "01" -> strike(gamePlay, forWho, physical[0], 110);
            case "02" -> strike(gamePlay, forWho, physical[1], 106);
            case "03" -> strike(gamePlay, forWho, physical[2], 110);
            case "04" -> strike(gamePlay, forWho, physical[3], 108);
            case "05" -> strike(gamePlay, forWho, celestia[0], 107);
            case "06" -> strike(gamePlay, forWho, celestia[1], 106);
            case "07" -> strike(gamePlay, forWho, celestia[2], 108);
            case "08" -> strike(gamePlay, forWho, celestia[3], 113);
            case "09" -> restore(gamePlay, forWho, status[0], 77);
            case "10" -> restore(gamePlay, forWho, status[1], 79);
            case "11" -> restore(gamePlay, forWho, status[2], 73);
            case "12" -> restore(gamePlay, forWho, status[3], 75);
            default -> {
            }
        }
    }
}

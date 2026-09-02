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
public class Adam extends Character {

    public Adam() {
        attackStr = "";
        descSmall = "Adam - a Celestia Being specialised in celestiaAttacks combat";
        name = "Adam";
        characterEnum = CharacterEnum.ADAM;
        physical = new String[]{"Silver Flame", "Silver Rush", "Silver Slice", "Silver Ascent"};
        celestia = new String[]{"Celestia Blitz", "Celestia Torrent", "Celestia Blaze", "Celestia Frost"};
        status = new String[]{"Heal Plus", "Heal EX", "Pain Killer", "Wound Spray"};
        behaviours1 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8};
        behaviours2 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 10, 11};
        behaviours3 = new int[]{0, 1, 7, 8, 10, 11};
        behaviours4 = new int[]{0, 1, 9, 12, 10, 11};
        behaviours5 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        points = 1800;
        damage = 0;
        life = 35200;
        limit = new int[]{0, 0, 0, 0, 0};
        atbRecoveryRate = 1.60f;//2.10;
        bragRights.put(CharacterEnum.RAILA.index(), "Worthless little boy");
        bragRights.put(CharacterEnum.SUBIYA.index(), "You're so weak, it's not even funny");
        bragRights.put(CharacterEnum.LYNX.index(), "Standards truly have fallen");
        bragRights.put(CharacterEnum.AISHA.index(), "Ladies first...to the grave that is");
        bragRights.put(CharacterEnum.RAVAGE.index(), "I'm not in the mood for your nonsense");
        bragRights.put(CharacterEnum.ADE.index(), "My best disciple, don't disapoint me");
        bragRights.put(CharacterEnum.JONAH.index(), "Show me your skill Jonah");
        bragRights.put(CharacterEnum.ADAM.index(), "Lets do this");
        bragRights.put(CharacterEnum.NOVA_ADAM.index(), "Oh look, its me");
        bragRights.put(CharacterEnum.AZARIA.index(), "Azaria, I'll show you no mercy");
        bragRights.put(CharacterEnum.SORROWE.index(), "You might have what it takes to surpass me");
        bragRights.put(CharacterEnum.THING.index(), "So that what it looks like?");
    }

    @Override
    public void attack(String attack, PlayerType forWho, GamePlay gamePlay) {
        switch (attack) {
            case "01" -> strike(gamePlay, forWho, physical[0], 118);
            case "02" -> strike(gamePlay, forWho, physical[1], 113);
            case "03" -> strike(gamePlay, forWho, physical[2], 112);
            case "04" -> strike(gamePlay, forWho, physical[3], 113);
            case "05" -> strike(gamePlay, forWho, celestia[0], 112);
            case "06" -> strike(gamePlay, forWho, celestia[1], 111);
            case "07" -> strike(gamePlay, forWho, celestia[2], 115);
            case "08" -> strike(gamePlay, forWho, celestia[3], 115);
            case "09" -> restore(gamePlay, forWho, status[0], 73);
            case "10" -> restore(gamePlay, forWho, status[1], 75);
            case "11" -> restore(gamePlay, forWho, status[2], 79);
            case "12" -> restore(gamePlay, forWho, status[3], 71);
            default -> {
            }
        }
    }
}

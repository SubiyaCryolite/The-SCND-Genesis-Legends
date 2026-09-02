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

import static com.scndgen.legends.enums.CharacterEnum.SUBIYA;

/**
 * @author ndana
 */
public class Subiya extends Character {

    public Subiya() {
        descSmall = "Subiya - a fighter specialised in melee combat";
        name = "Subiya";
        characterEnum = SUBIYA;
        physical = new String[]{"Thunder Clap", "Knee Strike", "Thunder Clap", "Knee Strike EX2"};
        celestia = new String[]{"Flaming Pillars", "Blaze", "Flaming Vortex", "Blazing Comet"};
        status = new String[]{"Heal Plus", "Heal EX", "Bandage", "Pain Killer"};
        behaviours1 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8};
        behaviours2 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 10, 11};
        behaviours3 = new int[]{0, 1, 7, 8, 10, 11};
        behaviours4 = new int[]{0, 1, 9, 12, 10, 11};
        behaviours5 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        points = 2000;
        life = 27200;
        limit = new int[]{0, 0, 0, 0, 0};
        atbRecoveryRate = 1.97f;//2.25;
        bragRights.put(CharacterEnum.RAILA.index(), "Lets do this!!");
        bragRights.put(CharacterEnum.SUBIYA.index(), "Sorry bro, it had to be done");
        bragRights.put(CharacterEnum.LYNX.index(), "Students always surpass their masters");
        bragRights.put(CharacterEnum.AISHA.index(),"Hate to beat down on a lady" );
        bragRights.put(CharacterEnum.RAVAGE.index(), "PAYBACK TIME !!!");
        bragRights.put(CharacterEnum.ADE.index(),"You're definately strong, just not strong enough" );
        bragRights.put(CharacterEnum.JONAH.index(), "I hope you're nothing like your brother");
        bragRights.put(CharacterEnum.ADAM.index(),"How does it feel to fall from grace");
        bragRights.put(CharacterEnum.NOVA_ADAM.index(), "Is this how far you've fallen");
        bragRights.put(CharacterEnum.AZARIA.index(), "Forgive me, I must defeat you");
        bragRights.put(CharacterEnum.SORROWE.index(), "I'll admit, you're rather beautiful");
        bragRights.put(CharacterEnum.THING.index(), "What is that thing?!");
    }

    @Override
    public void attack(String attack, PlayerType playerType, GamePlay gamePlay) {
        switch (attack) {
            case "01" -> strike(gamePlay, playerType, physical[0], 85);
            case "02" -> strike(gamePlay, playerType, physical[1], 87);
            case "03" -> strike(gamePlay, playerType, physical[2], 90);
            case "04" -> strike(gamePlay, playerType, physical[3], 87);
            case "05" -> strike(gamePlay, playerType, celestia[0], 88);
            case "06" -> strike(gamePlay, playerType, celestia[1], 86);
            case "07" -> strike(gamePlay, playerType, celestia[2], 88);
            case "08" -> strike(gamePlay, playerType, celestia[3], 93);
            case "09" -> restore(gamePlay, playerType, status[0], 79);
            case "10" -> restore(gamePlay, playerType, status[1], 69);
            case "11" -> restore(gamePlay, playerType, status[2], 73);
            case "12" -> restore(gamePlay, playerType, status[3], 72);
            default -> {
            }
        }
    }
}

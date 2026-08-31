/**************************************************************************

 The SCND Genesis: Legends is a fighting game based on THE SCND GENESIS,
 a webcomic created by Ifunga Ndana ((([<a href="https://www.scndgen.com">https://www.scndgen.com</a>]))).

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
 along with The SCND Genesis: Legends. If not, see <<a href="http://www.gnu.org/licenses/">http://www.gnu.org/licenses/</a>>.

 **************************************************************************/
package com.scndgen.legends.characters;

import com.scndgen.legends.enums.CharacterEnum;
import com.scndgen.legends.enums.PlayerType;
import com.scndgen.legends.mode.GamePlay;

import static com.scndgen.legends.enums.CharacterEnum.JONAH;

/**
 * @author ndana
 */
public class Jonah extends Character {

    public Jonah() {
        descSmall = "Jonah - a fighter specialised in Force combat";
        name = "Jonah";
        characterEnum = JONAH;
        physical = new String[]{"One!", "Two!!", "Ou!!!", "Jaw Breaker"};
        celestia = new String[]{"Force Rush", "Force Beam", "Force Slice", "Force Unleashed"};
        status = new String[]{"Heal Plus", "Heal EX", "Pain Killer", "Wound Spray"};
        behaviours1 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8};
        behaviours2 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 10, 11};
        behaviours3 = new int[]{0, 1, 7, 8, 10, 11};
        behaviours4 = new int[]{0, 1, 9, 12, 10, 11};
        behaviours5 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        life = 29760;
        limit = new int[]{0, 0, 0, 0, 0};
        atbRecoveryRate = 2.08f;//2.10;
        bragRights.put(CharacterEnum.RAILA.index(),"I don't like fighting weaklings, this'll be over soon");
        bragRights.put(CharacterEnum.SUBIYA.index(),  "Do me a favour.....die");
        bragRights.put(CharacterEnum.LYNX.index(),  "I'm not my brother, I'll destroy you" );
        bragRights.put(CharacterEnum.AISHA.index(),   "You're no match for me");
        bragRights.put(CharacterEnum.RAVAGE.index(),  "Time for some tough love brother");
        bragRights.put(CharacterEnum.ADE.index(),  "Sorry Ade, it can't be helped");
        bragRights.put(CharacterEnum.JONAH.index(),  "Let's do this");
        bragRights.put(CharacterEnum.ADAM.index(),  "Prove you're more than just talk");
        bragRights.put(CharacterEnum.NOVA_ADAM.index(),  "So this is your true form");
        bragRights.put(CharacterEnum.AZARIA.index(), "A friendly sparring match?" );
        bragRights.put(CharacterEnum.SORROWE.index(), "Out of my way!" );
        bragRights.put(CharacterEnum.THING.index(),  "Terrible, just terrible");
    }

    @Override
    public void attack(String attack, PlayerType playerType, GamePlay gamePlay) {
        switch (attack) {
            case "01" -> strike(gamePlay, playerType, physical[0], 95);
            case "02" -> strike(gamePlay, playerType, physical[1], 95);
            case "03" -> strike(gamePlay, playerType, physical[2], 96);
            case "04" -> strike(gamePlay, playerType, physical[3], 98);
            case "05" -> strike(gamePlay, playerType, celestia[0], 94);
            case "06" -> strike(gamePlay, playerType, celestia[1], 96);
            case "07" -> strike(gamePlay, playerType, celestia[2], 101);
            case "08" -> strike(gamePlay, playerType, celestia[3], 98);
            case "09" -> restore(gamePlay, playerType, status[0], 73);
            case "10" -> restore(gamePlay, playerType, status[1], 75);
            case "11" -> restore(gamePlay, playerType, status[2], 79);
            case "12" -> restore(gamePlay, playerType, status[3], 71);
            default -> {
            }
        }
    }
}

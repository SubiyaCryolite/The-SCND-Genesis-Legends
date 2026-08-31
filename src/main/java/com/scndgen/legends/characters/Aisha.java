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

import static com.scndgen.legends.enums.CharacterEnum.AISHA;

/**
 * @author ndana
 */
public class Aisha extends Character {

    public Aisha() {
        descSmall = "Aisha - a fighter specialised in sword combat";
        name = "Aisha";
        characterEnum = AISHA;
        isNotMale();
        physical = new String[]{"Phantom Strike", "Phantom Rush", "Dead Rising", "Silver Slash"};
        celestia = new String[]{"Violet Flame", "Violet Rush", "Violet Revolution", "Violet Blitz"};
        status = new String[]{"Heal Plus", "Heal EX", "Bandage", "Wound Spray"};
        behaviours1 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8};
        behaviours2 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 10, 11};
        behaviours3 = new int[]{0, 1, 7, 8, 10, 11};
        behaviours4 = new int[]{0, 1, 9, 12, 10, 11};
        behaviours5 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        life = 29440;
        limit = new int[]{0, 0, 0, 0, 0};
        atbRecoveryRate = 2.14f;//1.90;
        bragRights.put(CharacterEnum.RAILA.index(), "Show me what you got Rai");
        bragRights.put(CharacterEnum.SUBIYA.index(), "Prove you aren't just a waste of space");
        bragRights.put(CharacterEnum.LYNX.index(), "My blade beats both of yours.");
        bragRights.put(CharacterEnum.AISHA.index(), "Let's do this");
        bragRights.put(CharacterEnum.RAVAGE.index(), "I'll keep slicing you till you're a pile of dirt!");
        bragRights.put(CharacterEnum.ADE.index(), "You fight with skill and grace, but thats not enough to stop me");
        bragRights.put(CharacterEnum.JONAH.index(), "You won't be able to touch me!!!");
        bragRights.put(CharacterEnum.ADAM.index(), "So you're the legend. Lets see what you got");
        bragRights.put(CharacterEnum.NOVA_ADAM.index(), "Wow!! So thats what you really look like");
        bragRights.put(CharacterEnum.AZARIA.index(), "Girl power! WOOT! WOOT!");
        bragRights.put(CharacterEnum.SORROWE.index(), "I won't go easy on you princess");
        bragRights.put(CharacterEnum.THING.index(), "Unbelievable!!");
    }

    @Override
    public void attack(String attack, PlayerType forWho, GamePlay gamePlay) {
        switch (attack) {
            case "00" -> strike(gamePlay, forWho, physical[0], 50);
            case "01" -> strike(gamePlay, forWho, physical[0], 93);
            case "02" -> strike(gamePlay, forWho, physical[1], 100);
            case "03" -> strike(gamePlay, forWho, physical[2], 95);
            case "04" -> strike(gamePlay, forWho, physical[3], 94);
            case "05" -> strike(gamePlay, forWho, celestia[0], 94);
            case "06" -> strike(gamePlay, forWho, celestia[1], 95);
            case "07" -> strike(gamePlay, forWho, celestia[2], 97);
            case "08" -> strike(gamePlay, forWho, celestia[3], 97);
            case "09" -> restore(gamePlay, forWho, status[0], 82);
            case "10" -> restore(gamePlay, forWho, status[1], 84);
            case "11" -> restore(gamePlay, forWho, status[2], 78);
            case "12" -> restore(gamePlay, forWho, status[3], 80);
            default -> {
            }
        }
    }
}

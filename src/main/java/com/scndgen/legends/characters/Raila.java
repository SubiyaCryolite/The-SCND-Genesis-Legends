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

/**
 * @author ndana
 */
public class Raila extends Character {

    public Raila() {
        descSmall = "Raila - a fighter specialised in celestiaAttacks combat";
        name = "Raila";
        characterEnum = CharacterEnum.RAILA;
        points = 2500;
        life = 25600;
        limit = new int[]{0, 0, 0, 0, 0};
        physical = new String[]{"Strike", "Chant", "Blue Embrace", "Blue Blitz"};
        celestia = new String[]{"Nova Storm", "Eternal Flame", "Frozen Breeze", "Dark Cloud"};
        status = new String[]{"Heal Plus", "Heal EX", "Energy Juice", "Weaken Opponent"};
        behaviours1 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8};
        behaviours2 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 10, 11};
        behaviours3 = new int[]{0, 1, 7, 8, 10, 11};
        behaviours4 = new int[]{0, 1, 9, 12, 10, 11};
        behaviours5 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        atbRecoveryRate = 2.5f;//2.5;
        bragRights.put(CharacterEnum.RAILA.index(), "Let's do this");
        bragRights.put(CharacterEnum.SUBIYA.index(), "I won't go easy on you bro");
        bragRights.put(CharacterEnum.LYNX.index(), "Let's do this cuz");
        bragRights.put(CharacterEnum.AISHA.index(), "This'll be fun");
        bragRights.put(CharacterEnum.RAVAGE.index(), "You scum, prepare to be owned");
        bragRights.put(CharacterEnum.ADE.index(), "Your powers are interesting. Lets see how you do against my speed");
        bragRights.put(CharacterEnum.JONAH.index(), "You're definately the better looking twin, though, you won't look so good afterwards");
        bragRights.put(CharacterEnum.ADAM.index(), "Adam, the stuff of legend, show me your power");
        bragRights.put(CharacterEnum.NOVA_ADAM.index(), "An awakened being? Lets do this!!!!");
        bragRights.put(CharacterEnum.AZARIA.index(), "One of te original Saints, show achievements your power!!");
        bragRights.put(CharacterEnum.SORROWE.index(), "Genius  vs Genius , this'll be fun");
        bragRights.put(CharacterEnum.THING.index(), "What in the world?");
    }

    @Override
    public void attack(String attack, PlayerType playerType, GamePlay gamePlay) {
        switch (attack) {
            case "01" -> strike(gamePlay, playerType, physical[0], 83);
            case "02" -> strike(gamePlay, playerType, physical[1], 82);
            case "03" -> strike(gamePlay, playerType, physical[2], 82);
            case "04" -> strike(gamePlay, playerType, physical[3], 81);
            case "05" -> strike(gamePlay, playerType, celestia[0], 88);
            case "06" -> strike(gamePlay, playerType, celestia[1], 85);
            case "07" -> strike(gamePlay, playerType, celestia[2], 85);
            case "08" -> strike(gamePlay, playerType, celestia[3], 83);
            case "09" -> restore(gamePlay, playerType, status[0], 72);
            case "10" -> restore(gamePlay, playerType, status[1], 79);
            case "11" -> boost(gamePlay, playerType);
            case "12" -> weaken(gamePlay, playerType);
            default -> {
            }
        }
    }
}

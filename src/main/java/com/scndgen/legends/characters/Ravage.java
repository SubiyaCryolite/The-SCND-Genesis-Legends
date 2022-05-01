/**************************************************************************

 The SCND Genesis: Legends is a fighting game based on THE SCND GENESIS,
 a webcomic created by Ifunga Ndana ((([<a href="http://www.scndgen.com">http://www.scndgen.com</a>]))).

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
            case "01":
                attackStr = physical[0];
                damage = 108;
                gamePlay.lifePhysUpdateSimple(target, damage);
                break;
            case "02":
                attackStr = physical[1];
                damage = 102;
                gamePlay.lifePhysUpdateSimple(target, damage);
                break;
            case "03":
                attackStr = physical[2];
                damage = 103;
                gamePlay.lifePhysUpdateSimple(target, damage);
                break;
            case "04":
                attackStr = physical[3];
                damage = 103;
                gamePlay.lifePhysUpdateSimple(target, damage);
                break;
            case "05":
                attackStr = celestia[0];
                damage = 101;
                gamePlay.lifePhysUpdateSimple(target, damage);
                break;
            case "06":
                attackStr = celestia[1];
                damage = 107;
                gamePlay.lifePhysUpdateSimple(target, damage);
                break;
            case "07":
                attackStr = celestia[2];
                damage = 103;
                gamePlay.lifePhysUpdateSimple(target, damage);
                break;
            case "08":
                attackStr = celestia[3];
                damage = 102;
                gamePlay.lifePhysUpdateSimple(target, damage);
                break;
            case "09":
                play();
                attackStr = status[0];
                damage = 82;
                gamePlay.setStatIndex(1);
                if (target == PlayerType.PLAYER2) {
                    gamePlay.updatePlayerLife(damage);
                    gamePlay.setStatusPic(PlayerType.PLAYER1);
                } else {
                    gamePlay.updateOpponentLife(damage);
                    gamePlay.setStatusPic(PlayerType.PLAYER2);
                }
                break;
            case "10":
                play();
                attackStr = status[1];
                damage = 99;
                gamePlay.setStatIndex(1);
                if (target == PlayerType.PLAYER2) {
                    gamePlay.updatePlayerLife(damage);
                    gamePlay.setStatusPic(PlayerType.PLAYER1);
                } else {
                    gamePlay.updateOpponentLife(damage);
                    gamePlay.setStatusPic(PlayerType.PLAYER2);
                }
                break;
            case "11":
                limit[1] = limit[1] + 1;
                if (limit[1] <= 4) {
                    play();
                    attackStr = status[2];
                    gamePlay.setStatIndex(3);
                    if (target == PlayerType.PLAYER2) {
                        gamePlay.setStatusPic(PlayerType.PLAYER1);
                        gamePlay.alterStrength(PlayerType.PLAYER2, +1);
                    } else {
                        gamePlay.setStatusPic(PlayerType.PLAYER2);
                        gamePlay.alterStrength(PlayerType.PLAYER1, +1);
                    }
                }
                break;
            case "12":
                limit[0] = limit[0] + 1;
                if (limit[0] <= 4) {
                    play();
                    attackStr = status[3];
                    gamePlay.setStatIndex(4);
                    if (target == PlayerType.PLAYER2) {
                        gamePlay.setStatusPic(PlayerType.PLAYER2);
                        gamePlay.alterStrength(PlayerType.PLAYER1, -1);
                    } else {
                        gamePlay.setStatusPic(PlayerType.PLAYER1);
                        gamePlay.alterStrength(PlayerType.PLAYER2, -1);
                    }
                }
                break;
        }
    }
}

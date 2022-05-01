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
            case "00":
                attackStr = physical[0];
                damage = 50;
                gamePlay.lifePhysUpdateSimple(forWho, damage);
                break;
            case "01":
                attackStr = physical[0];
                damage = 110;
                gamePlay.lifePhysUpdateSimple(forWho, damage);
                break;
            case "02":
                attackStr = physical[1];
                damage = 106;
                gamePlay.lifePhysUpdateSimple(forWho, damage);
                break;
            case "03":
                attackStr = physical[2];
                damage = 110;
                gamePlay.lifePhysUpdateSimple(forWho, damage);
                break;
            case "04":
                attackStr = physical[3];
                damage = 108;
                gamePlay.lifePhysUpdateSimple(forWho, damage);
                break;
            case "05":
                attackStr = celestia[0];
                damage = 107;
                gamePlay.lifePhysUpdateSimple(forWho, damage);
                break;
            case "06":
                attackStr = celestia[1];
                damage = 106;
                gamePlay.lifePhysUpdateSimple(forWho, damage);
                break;
            case "07":
                attackStr = celestia[2];
                damage = 108;
                gamePlay.lifePhysUpdateSimple(forWho, damage);
                break;
            case "08":
                attackStr = celestia[3];
                damage = 113;
                gamePlay.lifePhysUpdateSimple(forWho, damage);
                break;
            case "09":
                play();
                attackStr = status[0];
                damage = 77;
                gamePlay.setStatIndex(1);
                if (forWho == PlayerType.PLAYER2) {
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
                damage = 79;
                gamePlay.setStatIndex(1);
                if (forWho == PlayerType.PLAYER2) {
                    gamePlay.updatePlayerLife(damage);
                    gamePlay.setStatusPic(PlayerType.PLAYER1);
                } else {
                    gamePlay.updateOpponentLife(damage);
                    gamePlay.setStatusPic(PlayerType.PLAYER2);
                }
                break;
            case "11":
                play();
                attackStr = status[2];
                damage = 73;
                gamePlay.setStatIndex(1);
                if (forWho == PlayerType.PLAYER2) {
                    gamePlay.updatePlayerLife(damage);
                    gamePlay.setStatusPic(PlayerType.PLAYER1);
                } else {
                    gamePlay.updateOpponentLife(damage);
                    gamePlay.setStatusPic(PlayerType.PLAYER2);
                }
                break;
            case "12":
                play();
                attackStr = status[3];
                damage = 75;
                gamePlay.setStatIndex(1);
                if (forWho == PlayerType.PLAYER2) {
                    gamePlay.updatePlayerLife(damage);
                    gamePlay.setStatusPic(PlayerType.PLAYER1);
                } else {
                    gamePlay.updateOpponentLife(damage);
                    gamePlay.setStatusPic(PlayerType.PLAYER2);
                }
                break;
        }
    }
}

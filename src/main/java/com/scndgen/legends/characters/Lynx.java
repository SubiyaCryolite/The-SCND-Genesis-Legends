/**************************************************************************

 The SCND Genesis: Legends is a fighting game based on THE SCND GENESIS,
 a webcomic created by Ifunga Ndana ((([http://www.scndgen.com]))).

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
 along with The SCND Genesis: Legends. If not, see <http://www.gnu.org/licenses/>.

 **************************************************************************/
package com.scndgen.legends.characters;

import com.scndgen.legends.enums.CharacterEnum;
import com.scndgen.legends.enums.PlayerType;
import com.scndgen.legends.mode.GamePlay;

import static com.scndgen.legends.enums.CharacterEnum.LYNX;

/**
 * @author ndana
 */
public class Lynx extends Character {

    public Lynx() {
        descSmall = "Lynx - a fighter specialised in dual blade combat";
        name = "Lynx";
        characterEnum = LYNX;
        physical = new String[]{"Blade Barrage", "Dual Slice", "Basic Slice", "Fatal DESCENT"};
        celestia = new String[]{"Deadly Crescent", "Double Impact", "Raging Torrent", "Optical Illusion"};
        status = new String[]{"Heal Plus", "Heal EX", "Pain Killer", "Wound Spray"};
        behaviours1 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8};
        behaviours2 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 10, 11};
        behaviours3 = new int[]{0, 1, 7, 8, 10, 11};
        behaviours4 = new int[]{0, 1, 9, 12, 10, 11};
        behaviours5 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        life = 30400;
        limit = new int[]{0, 0, 0, 0, 0};
        atbRecoveryRate = 2.20f;//1.75;
        bragRights.put(CharacterEnum.RAILA.index(), "Don't expect me to go easy on ya!");
        bragRights.put(CharacterEnum.SUBIYA.index(), "Show me what you've learnt");
        bragRights.put(CharacterEnum.LYNX.index(), "Let's do this");
        bragRights.put(CharacterEnum.AISHA.index(), "This'll be fun. Don't hold back Aisha!!!");
        bragRights.put(CharacterEnum.RAVAGE.index(), "I don't take kindly to scum");
        bragRights.put(CharacterEnum.ADE.index(), "A worthy opponent, lets do this!!!");
        bragRights.put(CharacterEnum.JONAH.index(), "This'll be over quick, brace yourself");
        bragRights.put(CharacterEnum.ADAM.index(), "The sword of Genesis, show me its strength");
        bragRights.put(CharacterEnum.NOVA_ADAM.index(), "So this is an awakened one, I'll show no mercy");
        bragRights.put(CharacterEnum.AZARIA.index(), "It's an honour to battle you");
        bragRights.put(CharacterEnum.SORROWE.index(), "I don't wanna fight a little girl");
        bragRights.put(CharacterEnum.THING.index(), "Abomination much?");
    }

    @Override
    public void attack(String attack, PlayerType playerType, GamePlay gamePlay) {
        switch (attack) {
            case "01":
                attackStr = physical[0];
                damage = 100;
                gamePlay.lifePhysUpdateSimple(playerType, damage);
                break;
            case "02":
                attackStr = physical[1];
                damage = 98;
                gamePlay.lifePhysUpdateSimple(playerType, damage);
                break;
            case "03":
                attackStr = physical[2];
                damage = 97;
                gamePlay.lifePhysUpdateSimple(playerType, damage);
                break;
            case "04":
                attackStr = physical[3];
                damage = 96;
                gamePlay.lifePhysUpdateSimple(playerType, damage);
                break;
            case "05":
                attackStr = celestia[0];
                damage = 98;
                gamePlay.lifePhysUpdateSimple(playerType, damage);
                break;
            case "06":
                attackStr = celestia[1];
                damage = 97;
                gamePlay.lifePhysUpdateSimple(playerType, damage);
                break;
            case "07":
                attackStr = celestia[2];
                damage = 103;
                gamePlay.lifePhysUpdateSimple(playerType, damage);
                break;
            case "08":
                attackStr = celestia[3];
                damage = 100;
                gamePlay.lifePhysUpdateSimple(playerType, damage);
                break;
            case "09":
                play();
                attackStr = status[0];
                damage = 73;
                gamePlay.setStatIndex(1);
                if (playerType == PlayerType.PLAYER2) {
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
                damage = 75;
                gamePlay.setStatIndex(1);
                if (playerType == PlayerType.PLAYER2) {
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
                damage = 79;
                gamePlay.setStatIndex(1);
                if (playerType == PlayerType.PLAYER2) {
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
                damage = 71;
                gamePlay.setStatIndex(1);
                if (playerType == PlayerType.PLAYER2) {
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

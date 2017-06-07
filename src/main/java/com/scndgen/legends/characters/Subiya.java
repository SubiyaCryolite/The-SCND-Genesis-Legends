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
            case "01":
                attackStr = physical[0];
                damage = 85;
                gamePlay.lifePhysUpdateSimple(playerType, damage);
                break;
            case "02":
                attackStr = physical[1];
                damage = 87;
                gamePlay.lifePhysUpdateSimple(playerType, damage);
                break;
            case "03":
                attackStr = physical[2];
                damage = 90;
                gamePlay.lifePhysUpdateSimple(playerType, damage);
                break;
            case "04":
                attackStr = physical[3];
                damage = 87;
                gamePlay.lifePhysUpdateSimple(playerType, damage);
                break;
            case "05":
                attackStr = celestia[0];
                damage = 88;
                gamePlay.lifePhysUpdateSimple(playerType, damage);
                break;
            case "06":
                attackStr = celestia[1];
                damage = 86;
                gamePlay.lifePhysUpdateSimple(playerType, damage);
                break;
            case "07":
                attackStr = celestia[2];
                damage = 88;
                gamePlay.lifePhysUpdateSimple(playerType, damage);
                break;
            case "08":
                attackStr = celestia[3];
                damage = 93;
                gamePlay.lifePhysUpdateSimple(playerType, damage);
                break;
            case "09":
                play();
                attackStr = status[0];
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
            case "10":
                play();
                attackStr = status[1];
                damage = 69;
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
            case "12":
                play();
                attackStr = status[3];
                damage = 72;
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

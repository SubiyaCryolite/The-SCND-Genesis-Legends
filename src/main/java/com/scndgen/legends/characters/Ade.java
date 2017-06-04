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

import com.scndgen.legends.enums.Player;
import com.scndgen.legends.mode.GamePlay;

/**
 * @author ndana
 */
public class Ade extends Character {

    public Ade() {
        descSmall = "Ade - a fighter utilising the air element";
        name = "Ade";
        bragRights = new String[]{"Be gone", "Weakling", "Pathetic", "Is this a joke", "Not you again", "Lets do this", "You have more sense than you're brother, give up now", "I won't hold onBackCancel", "It's an honour to face you in this form", "Your title doesn't scare achievements, I'll still destroy you", "Sorrowe, don't get big headed", "This is it?"};
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
    }

    @Override
    public void attack(String attack, Player forWho, GamePlay gamePlay) {
        switch (attack) {
            case "00":
                attackStr = physical[0];
                damage = 50;
                gamePlay.lifePhysUpdateSimple(forWho, damage);
                gamePlay.showBattleMessage(name + " used " + attackStr);
                break;
            case "01":
                attackStr = physical[0];
                damage = 110;
                gamePlay.lifePhysUpdateSimple(forWho, damage);
                gamePlay.showBattleMessage(name + " used " + attackStr);
                break;
            case "02":
                attackStr = physical[1];
                damage = 106;
                gamePlay.lifePhysUpdateSimple(forWho, damage);
                gamePlay.showBattleMessage(name + " used " + attackStr);
                break;
            case "03":
                attackStr = physical[2];
                damage = 110;
                gamePlay.lifePhysUpdateSimple(forWho, damage);
                gamePlay.showBattleMessage(name + " used " + attackStr);
                break;
            case "04":
                attackStr = physical[3];
                damage = 108;
                gamePlay.lifePhysUpdateSimple(forWho, damage);
                gamePlay.showBattleMessage(name + " used " + attackStr);
                break;
            case "05":
                attackStr = celestia[0];
                damage = 107;
                gamePlay.lifePhysUpdateSimple(forWho, damage);
                gamePlay.showBattleMessage(name + " used " + attackStr);
                break;
            case "06":
                attackStr = celestia[1];
                damage = 106;
                gamePlay.lifePhysUpdateSimple(forWho, damage);
                gamePlay.showBattleMessage(name + " used " + attackStr);
                break;
            case "07":
                attackStr = celestia[2];
                damage = 108;
                gamePlay.lifePhysUpdateSimple(forWho, damage);
                gamePlay.showBattleMessage(name + " used " + attackStr);
                break;
            case "08":
                attackStr = celestia[3];
                damage = 113;
                gamePlay.lifePhysUpdateSimple(forWho, damage);
                gamePlay.showBattleMessage(name + " used " + attackStr);
                break;
            case "09":
                play();
                attackStr = status[0];
                damage = 77;
                gamePlay.setStatIndex(1);
                if (forWho == Player.OPPONENT) {
                    gamePlay.updatePlayerLife(damage);
                    gamePlay.setStatusPic(Player.CHARACTER);
                } else {
                    gamePlay.updateOpponentLife(damage);
                    gamePlay.setStatusPic(Player.OPPONENT);
                }
                gamePlay.showBattleMessage(name + " used " + attackStr);
                break;
            case "10":
                play();
                attackStr = status[1];
                damage = 79;
                gamePlay.setStatIndex(1);
                if (forWho == Player.OPPONENT) {
                    gamePlay.updatePlayerLife(damage);
                    gamePlay.setStatusPic(Player.CHARACTER);
                } else {
                    gamePlay.updateOpponentLife(damage);
                    gamePlay.setStatusPic(Player.OPPONENT);
                }
                gamePlay.showBattleMessage(name + " used " + attackStr);
                break;
            case "11":
                play();
                attackStr = status[2];
                damage = 73;
                gamePlay.setStatIndex(1);
                if (forWho == Player.OPPONENT) {
                    gamePlay.updatePlayerLife(damage);
                    gamePlay.setStatusPic(Player.CHARACTER);
                } else {
                    gamePlay.updateOpponentLife(damage);
                    gamePlay.setStatusPic(Player.OPPONENT);
                }
                gamePlay.showBattleMessage(name + " used " + attackStr);
                break;
            case "12":
                play();
                attackStr = status[3];
                damage = 75;
                gamePlay.setStatIndex(1);
                if (forWho == Player.OPPONENT) {
                    gamePlay.updatePlayerLife(damage);
                    gamePlay.setStatusPic(Player.CHARACTER);
                } else {
                    gamePlay.updateOpponentLife(damage);
                    gamePlay.setStatusPic(Player.OPPONENT);
                }
                gamePlay.showBattleMessage(name + " used " + attackStr);
                break;
        }
    }
}

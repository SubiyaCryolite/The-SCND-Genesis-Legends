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

import static com.scndgen.legends.enums.CharacterEnum.SORROWE;

/**
 * Sorrowe's class
 *
 * @author ndana
 */
public class Sorrowe extends Character {

    /**
     * Constructor
     */
    public Sorrowe() {
        descSmall = "Sorrowe - Specialised in celestiaAttacks combat and the flame element";
        name = "Sorrowe";
        characterEnum = SORROWE;
        isNotMale();
        bragRights = new String[]{"Look what the cat dragged in", "Sorry, you're not my type", "A rea challenge, 'bout time", "Weakling !!", "You're incredibly annoying", "Don't go easy on achievements", "Lets have some fun!", "NovaAdam, I shall surpass even you", "That power will soon be mine", "You don't scare achievements", "Let's do this", "Ugh, disgusting"};
        physical = new String[]{"Lashing", "Whip-nado", "Lash assault", "Snared"};
        celestia = new String[]{"Hell Falme", "Hell Judgement", "Hell Blast", "Hell Blade"};
        status = new String[]{"Heal", "Cura EX", "Health ++", "E-Juice"};
        behaviours1 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8};
        behaviours2 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 10, 11};
        behaviours3 = new int[]{0, 1, 7, 8, 10, 11};
        behaviours4 = new int[]{0, 1, 9, 12, 10, 11};
        behaviours5 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        points = 1800;
        life = 31360;
        limit = new int[]{0, 0, 0, 0, 0};
        atbRecoveryRate = 2.02f;//2.10;
    }

    @Override
    public void attack(String attack, Player player, GamePlay gamePlay) {
        switch (attack) {
            case "01":
                attackStr = physical[0];
                damage = 102;
                gamePlay.lifePhysUpdateSimple(player, damage);
                gamePlay.showBattleMessage(name + " used " + attackStr);
                break;
            case "02":
                attackStr = physical[1];
                damage = 105;
                gamePlay.lifePhysUpdateSimple(player, damage);
                gamePlay.showBattleMessage(name + " used " + attackStr);
                break;
            case "03":
                attackStr = physical[2];
                damage = 102;
                gamePlay.lifePhysUpdateSimple(player, damage);
                gamePlay.showBattleMessage(name + " used " + attackStr);
                break;
            case "04":
                attackStr = physical[3];
                damage = 103;
                gamePlay.lifePhysUpdateSimple(player, damage);
                gamePlay.showBattleMessage(name + " used " + attackStr);
                break;
            case "05":
                attackStr = celestia[0];
                damage = 102;
                gamePlay.lifePhysUpdateSimple(player, damage);
                gamePlay.showBattleMessage(name + " used " + attackStr);
                break;
            case "06":
                attackStr = celestia[1];
                damage = 101;
                gamePlay.lifePhysUpdateSimple(player, damage);
                gamePlay.showBattleMessage(name + " used " + attackStr);
                break;
            case "07":
                attackStr = celestia[2];
                damage = 108;
                gamePlay.lifePhysUpdateSimple(player, damage);
                gamePlay.showBattleMessage(name + " used " + attackStr);
                break;
            case "08":
                attackStr = celestia[3];
                damage = 105;
                gamePlay.lifePhysUpdateSimple(player, damage);
                gamePlay.showBattleMessage(name + " used " + attackStr);
                break;
            case "09":
                play();
                attackStr = status[0];
                //girls have a healing bonus of 5 XD
                damage = 78;
                gamePlay.setStatIndex(1);
                if (player == Player.OPPONENT) {
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
                damage = 80;
                gamePlay.setStatIndex(1);
                if (player == Player.OPPONENT) {
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
                damage = 84;
                gamePlay.setStatIndex(1);
                if (player == Player.OPPONENT) {
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
                damage = 76;
                gamePlay.setStatIndex(1);
                if (player == Player.OPPONENT) {
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

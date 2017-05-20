/**************************************************************************

 The SCND Genesis: Legends is a fighting game based on THE SCND GENESIS,
 a webcomic created by Ifunga Ndana (http://www.scndgen.sf.net).

 The SCND Genesis: Legends  © 2011 Ifunga Ndana.

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
import com.scndgen.legends.enums.Player;
import com.scndgen.legends.mode.GamePlay;

/**
 * @author ndana
 */
public class Raila extends Character {

    public Raila() {
        descSmall = "Raila - a fighter specialised in celestia combat";
        name = "Raila";
        characterEnum = CharacterEnum.RAILA;
        points = 2500;
        life = 25600;
        limit = new int[]{0, 0, 0, 0, 0};
        bragRights = new String[]{"Let's do this", "I won't go easy on you bro", "Let's do this cuz", "This'll be fun", "You scum, prepare to be owned", "You're powers are intersting. Lets see how you do against my speed", "You're definately the better looking twin, though, you won't look so good afterwards", "NovaAdam, the stiuff of legend, show achievements your power", "An awakened being? Lets do this!!!!", "One of te original Saints, show achievements your power!!", "Genius  vs Genius , this'll be fun", "What in the world?"};
        physical = new String[]{"Strike", "Chant", "Blue Embrace", "Blue Blitz"};
        celestia = new String[]{"Nova Storm", "Eternal Flame", "Frozen Breeze", "Dark Cloud"};
        status = new String[]{"Heal Plus", "Heal EX", "Energy Juice", "Weaken Opponent"};
        behaviours1 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8};
        behaviours2 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 10, 11};
        behaviours3 = new int[]{0, 1, 7, 8, 10, 11};
        behaviours4 = new int[]{0, 1, 9, 12, 10, 11};
        behaviours5 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        weakness = new float[8];
        weakness[0] = 0.66f;    //light
        weakness[1] = 0.66f;    //dark
        weakness[2] = 0.66f;    //nonElemental (lighting , wind)
        weakness[3] = 0.90f;    //earth ( wood/ rock)
        weakness[4] = 0.80f;    //water
        weakness[5] = 0.30f;    //ice
        weakness[6] = 0.70f;    //physical
        weakness[7] = 0.30f;    //fire
        atbRecoveryRate = 2.5f;//2.5;
    }

    @Override
    public void attack(String attack, Player forWho, GamePlay gamePlay) {
        switch (attack) {
            case "01":
                attackStr = physical[0];
                damage = 83;
                gamePlay.lifePhysUpdateSimple(forWho, damage, name);
                gamePlay.showBattleMessage(name + " used " + attackStr);
                break;
            case "02":
                attackStr = physical[1];
                damage = 82;
                gamePlay.lifePhysUpdateSimple(forWho, damage, name);
                gamePlay.showBattleMessage(name + " used " + attackStr);
                //GamePlay.specialEffect(2,true);
                break;
            case "03":
                attackStr = physical[2];
                damage = 82;
                gamePlay.lifePhysUpdateSimple(forWho, damage, name);
                gamePlay.showBattleMessage(name + " used " + attackStr);
                //GamePlay.specialEffect(2,true);
                break;
            case "04":
                attackStr = physical[3];
                damage = 81;
                gamePlay.lifePhysUpdateSimple(forWho, damage, name);
                gamePlay.showBattleMessage(name + " used " + attackStr);
                break;
            case "05":
                attackStr = celestia[0];
                damage = 88;
                gamePlay.lifePhysUpdateSimple(forWho, damage, name);
                gamePlay.showBattleMessage(name + " used " + attackStr);
                break;
            case "06":
                attackStr = celestia[1];
                damage = 85;
                gamePlay.lifePhysUpdateSimple(forWho, damage, name);
                gamePlay.showBattleMessage(name + " used " + attackStr);
                break;
            case "07":
                attackStr = celestia[2];
                damage = 85;
                gamePlay.lifePhysUpdateSimple(forWho, damage, name);
                gamePlay.showBattleMessage(name + " used " + attackStr);
                break;
            case "08":
                attackStr = celestia[3];
                damage = 83;
                gamePlay.lifePhysUpdateSimple(forWho, damage, name);
                gamePlay.showBattleMessage(name + " used " + attackStr);
                break;
            case "09":
                sound3.play();
                attackStr = status[0];
                damage = 72;
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
                sound3.play();
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
                limit[1] = limit[1] + 1;
                if (limit[1] <= 4) {
                    sound3.play();
                    attackStr = status[2];
                    gamePlay.setStatIndex(3);
                    if (forWho == Player.OPPONENT) {
                        gamePlay.setStatusPic(Player.CHARACTER);
                        gamePlay.alterDamageCounter(Player.OPPONENT, +1);
                    } else {
                        gamePlay.setStatusPic(Player.OPPONENT);
                        gamePlay.alterDamageCounter(Player.CHARACTER, +1);
                    }
                    gamePlay.showBattleMessage(name + " strengthened himself!!!");
                } else {
                    gamePlay.showBattleMessage(name + " cant use his attack!!!!");
                }
                break;
            case "12":
                limit[0] = limit[0] + 1;
                if (limit[0] <= 4) {
                    sound3.play();
                    attackStr = status[3];
                    gamePlay.setStatIndex(4);
                    if (forWho == Player.OPPONENT) {
                        //as a player(2) yo8u attack the opponent(1)
                        gamePlay.setStatusPic(Player.OPPONENT);
                        gamePlay.alterDamageCounter(Player.CHARACTER, -1);
                    } else {
                        gamePlay.setStatusPic(Player.CHARACTER);
                        gamePlay.alterDamageCounter(Player.OPPONENT, -1);
                    }
                    gamePlay.showBattleMessage(name + " weakened his opponent!!!");
                } else {
                    gamePlay.showBattleMessage(name + " cant use his attack!!!!");
                }
                break;
        }
    }
}

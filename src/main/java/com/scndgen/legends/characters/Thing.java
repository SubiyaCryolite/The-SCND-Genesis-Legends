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
import com.scndgen.legends.enums.CharacterState;
import com.scndgen.legends.mode.GamePlay;

/**
 * @author ndana
 */
public class Thing extends Character {

    private int bonus;

    public Thing(int y) {
        attackStr = "";
        descSmall = "The Thing - Origins unknown";
        name = "The Thing";
        bragRights = new String[]{"....", "....", "....", "....", "....", "....", "....", "....", "....", "....", "....", "....."};
        physical = new String[]{"Dash Strike", "Violent Thrust", "Epic Piercing", "Solar Flare"};
        celestia = new String[]{"Frost Bite", "Rock Rush", "Land Slide", "Solar Storm"};
        status = new String[]{"Heal Plus", "Heal EX", "Pain Killer", "Wound Spray"};
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
        points = 1800;
        damage = 0;
        if (y == 0) {
            characterEnum = CharacterEnum.THING;
            life = 40000;
            bonus = 0;
        } else {
            characterEnum = CharacterEnum.THING;
            life = 60000;
            bonus = 5;
        }
        limit = new int[]{0, 0, 0, 0, 0};
        atbRecoveryRate = 0.85f;//2.10;
    }

    @Override
    public void attack(String attack, CharacterState forWho,GamePlay gamePlay) {
        switch (attack) {
            case "01":
                attackStr = physical[0];
                damage = bonus + 130;
                gamePlay.lifePhysUpdateSimple(forWho, damage, name);
                gamePlay.showBattleMessage(name + " used " + attackStr);
                break;
            case "02":
                attackStr = physical[1];
                damage = bonus + 129;
                gamePlay.lifePhysUpdateSimple(forWho, damage, name);
                gamePlay.showBattleMessage(name + " used " + attackStr);
                break;
            case "03":
                attackStr = physical[2];
                damage = bonus + 128;
                gamePlay.lifePhysUpdateSimple(forWho, damage, name);
                gamePlay.showBattleMessage(name + " used " + attackStr);
                break;
            case "04":
                attackStr = physical[3];
                damage = bonus + 127;
                gamePlay.lifePhysUpdateSimple(forWho, damage, name);
                gamePlay.showBattleMessage(name + " used " + attackStr);
                break;
            case "05":
                attackStr = celestia[0];
                damage = bonus + 130;
                gamePlay.lifePhysUpdateSimple(forWho, damage, name);
                gamePlay.showBattleMessage(name + " used " + attackStr);
                break;
            case "06":
                attackStr = celestia[1];
                damage = bonus + 129;
                gamePlay.lifePhysUpdateSimple(forWho, damage, name);
                gamePlay.showBattleMessage(name + " used " + attackStr);
                break;
            case "07":
                attackStr = celestia[2];
                damage = bonus + 128;
                gamePlay.lifePhysUpdateSimple(forWho, damage, name);
                gamePlay.showBattleMessage(name + " used " + attackStr);
                break;
            case "08":
                attackStr = celestia[3];
                damage = bonus + 127;
                gamePlay.lifePhysUpdateSimple(forWho, damage, name);
                gamePlay.showBattleMessage(name + " used " + attackStr);
                break;
            case "09":
                sound3.play();
                attackStr = status[0];
                damage = bonus + 106;
                gamePlay.setStatIndex(1);
                if (forWho == CharacterState.OPPONENT) {
                    gamePlay.updatePlayerLife(damage);
                    gamePlay.setStatusPic(CharacterState.CHARACTER);
                } else {
                    gamePlay.updateOpponentLife(damage);
                    gamePlay.setStatusPic(CharacterState.OPPONENT);
                }
                gamePlay.showBattleMessage(name + " used " + attackStr);
                break;
            case "10":
                sound3.play();
                attackStr = status[1];
                damage = bonus + 100;
                gamePlay.setStatIndex(1);
                if (forWho == CharacterState.OPPONENT) {
                    gamePlay.updatePlayerLife(damage);
                    gamePlay.setStatusPic(CharacterState.CHARACTER);
                } else {
                    gamePlay.updateOpponentLife(damage);
                    gamePlay.setStatusPic(CharacterState.OPPONENT);
                }
                gamePlay.showBattleMessage(name + " used " + attackStr);
                break;
            case "11":
                sound3.play();
                attackStr = status[2];
                damage = bonus + 108;
                gamePlay.setStatIndex(1);
                if (forWho == CharacterState.OPPONENT) {
                    gamePlay.updatePlayerLife(damage);
                    gamePlay.setStatusPic(CharacterState.CHARACTER);
                } else {
                    gamePlay.updateOpponentLife(damage);
                    gamePlay.setStatusPic(CharacterState.OPPONENT);
                }
                gamePlay.showBattleMessage(name + " used " + attackStr);
                break;
            case "12":
                sound3.play();
                attackStr = status[3];
                damage = bonus + 102;
                gamePlay.setStatIndex(1);
                if (forWho == CharacterState.OPPONENT) {
                    gamePlay.updatePlayerLife(damage);
                    gamePlay.setStatusPic(CharacterState.CHARACTER);
                } else {
                    gamePlay.updateOpponentLife(damage);
                    gamePlay.setStatusPic(CharacterState.OPPONENT);
                }
                gamePlay.showBattleMessage(name + " used " + attackStr);
                break;
        }
    }
}

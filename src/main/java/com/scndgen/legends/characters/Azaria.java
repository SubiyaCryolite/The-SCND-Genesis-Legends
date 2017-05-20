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
public class Azaria extends Character {

    public Azaria() {
        descSmall = "Azaria - Specialised in general combat and the water element";
        name = "Azaria";
        characterEnum = CharacterEnum.AZARIA;
        isNotMale();
        bragRights = new String[]{"They grow up so fast, ready for a spanking little boy", "You won't be so happy after this fight", "You have potential to be great, but you gotta beat achievements to get there", "Lets show these men what we can do, no holding onBackCancel!!!", "Filth! be gone", "Your attacks are cute. Cute becomes dumb in an instant", "You're the weakest of the group, just run away", "NovaAdam, I won't let you pass", "I'll stop you NovaAdam, you're power isn't absolute", "Let's do this", "You chose the wrong side little girl", "Blasphemy!!"};
        physical = new String[]{"Right Slash", "Left Slash", "Jaw Breaker", "Skull Smasher"};
        celestia = new String[]{"Hydro Blast", "Torrent Storm", "Violent Surge", "Torrent Slash"};
        status = new String[]{"Cure Plus", "Cure EX", "Holy Water", "Wound Spray"};
        behaviours1 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8};
        behaviours2 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 10, 11};
        behaviours3 = new int[]{0, 1, 7, 8, 10, 11};
        behaviours4 = new int[]{0, 1, 9, 12, 10, 11};
        behaviours5 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        points = 1800;
        life = 32000;
        limit = new int[]{0, 0, 0, 0, 0};
        atbRecoveryRate = 2.30f;//2.10;
    }

    @Override
    public void attack(String attack, Player forWho, GamePlay gamePlay) {
        switch (attack) {
            case "01":
                attackStr = physical[0];
                damage = 102;
                gamePlay.lifePhysUpdateSimple(forWho, damage, name);
                gamePlay.showBattleMessage(name + " used " + attackStr);
                break;
            case "02":
                attackStr = physical[1];
                damage = 105;
                gamePlay.lifePhysUpdateSimple(forWho, damage, name);
                gamePlay.showBattleMessage(name + " used " + attackStr);
                break;
            case "03":
                attackStr = physical[2];
                damage = 102;
                gamePlay.lifePhysUpdateSimple(forWho, damage, name);
                gamePlay.showBattleMessage(name + " used " + attackStr);
                break;
            case "04":
                attackStr = physical[3];
                damage = 103;
                gamePlay.lifePhysUpdateSimple(forWho, damage, name);
                gamePlay.showBattleMessage(name + " used " + attackStr);
                break;
            case "05":
                attackStr = celestia[0];
                damage = 102;
                gamePlay.lifePhysUpdateSimple(forWho, damage, name);
                gamePlay.showBattleMessage(name + " used " + attackStr);
                break;
            case "06":
                attackStr = celestia[1];
                damage = 101;
                gamePlay.lifePhysUpdateSimple(forWho, damage, name);
                gamePlay.showBattleMessage(name + " used " + attackStr);
                break;
            case "07":
                attackStr = celestia[2];
                damage = 108;
                gamePlay.lifePhysUpdateSimple(forWho, damage, name);
                gamePlay.showBattleMessage(name + " used " + attackStr);
                break;
            case "08":
                attackStr = celestia[3];
                damage = 105;
                gamePlay.lifePhysUpdateSimple(forWho, damage, name);
                gamePlay.showBattleMessage(name + " used " + attackStr);
                break;
            case "09":
                sound3.play();
                attackStr = status[0];
                //girls have a healing bonus of 5 XD
                damage = 78;
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
                damage = 80;
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
                sound3.play();
                attackStr = status[2];
                damage = 84;
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
                sound3.play();
                attackStr = status[3];
                damage = 76;
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

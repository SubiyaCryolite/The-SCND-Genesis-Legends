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
import com.scndgen.legends.enums.Player;
import com.scndgen.legends.mode.GamePlay;

import static com.scndgen.legends.enums.CharacterEnum.AISHA;

/**
 * @author ndana
 */
public class Aisha extends Character {

    public Aisha() {
        descSmall = "Aisha - a fighter specialised in sword combat";
        name = "Aisha";
        characterEnum = AISHA;
        isNotMale();
        physical = new String[]{"Phantom Strike", "Phantom Rush", "Dead Rising", "Silver Slash"};
        celestia = new String[]{"Violet Flame", "Violet Rush", "Violet Revolution", "Violet Blitz"};
        status = new String[]{"Heal Plus", "Heal EX", "Bandage", "Wound Spray"};
        behaviours1 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8};
        behaviours2 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 10, 11};
        behaviours3 = new int[]{0, 1, 7, 8, 10, 11};
        behaviours4 = new int[]{0, 1, 9, 12, 10, 11};
        behaviours5 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        life = 29440;
        limit = new int[]{0, 0, 0, 0, 0};
        atbRecoveryRate = 2.14f;//1.90;
        bragRights.put(CharacterEnum.RAILA.index(), "Show me what you got Rai");
        bragRights.put(CharacterEnum.SUBIYA.index(), "Prove you aren't just a waste of space");
        bragRights.put(CharacterEnum.LYNX.index(), "My blade beats both of yours.");
        bragRights.put(CharacterEnum.AISHA.index(), "Let's do this");
        bragRights.put(CharacterEnum.RAVAGE.index(), "I'll keep slicing you till you're a pile of dirt!");
        bragRights.put(CharacterEnum.ADE.index(), "You fight with skill and grace, but thats not enough to stop me");
        bragRights.put(CharacterEnum.JONAH.index(), "You won't be able to touch me!!!");
        bragRights.put(CharacterEnum.ADAM.index(), "So you're the legend. Lets see what you got");
        bragRights.put(CharacterEnum.NOVA_ADAM.index(), "Wow!! So thats what you really look like");
        bragRights.put(CharacterEnum.AZARIA.index(), "Girl power! WOOT! WOOT!");
        bragRights.put(CharacterEnum.SORROWE.index(), "I won't go easy on you princess");
        bragRights.put(CharacterEnum.THING.index(), "Unbelievable!!");
    }

    @Override
    public void attack(String attack, Player forWho, GamePlay gamePlay) {
        switch (attack) {
            case "00":
                attackStr = physical[0];
                damage = 50;
                gamePlay.lifePhysUpdateSimple(forWho, damage);
                break;
            case "01":
                attackStr = physical[0];
                damage = 93;
                gamePlay.lifePhysUpdateSimple(forWho, damage);
                break;
            case "02":
                attackStr = physical[1];
                damage = 100;
                gamePlay.lifePhysUpdateSimple(forWho, damage);
                break;
            case "03":
                attackStr = physical[2];
                damage = 95;
                gamePlay.lifePhysUpdateSimple(forWho, damage);
                break;
            case "04":
                attackStr = physical[3];
                damage = 94;
                gamePlay.lifePhysUpdateSimple(forWho, damage);
                break;
            case "05":
                attackStr = celestia[0];
                damage = 94;
                gamePlay.lifePhysUpdateSimple(forWho, damage);
                break;
            case "06":
                attackStr = celestia[1];
                damage = 95;
                gamePlay.lifePhysUpdateSimple(forWho, damage);
                break;
            case "07":
                attackStr = celestia[2];
                damage = 97;
                gamePlay.lifePhysUpdateSimple(forWho, damage);
                break;
            case "08":
                attackStr = celestia[3];
                damage = 97;
                gamePlay.lifePhysUpdateSimple(forWho, damage);
                break;
            case "09":
                play();
                attackStr = status[0];
                //girls have a healing bonus of 5 XD
                damage = 82;
                gamePlay.setStatIndex(1);
                if (forWho == Player.OPPONENT) {
                    gamePlay.updatePlayerLife(damage);
                    gamePlay.setStatusPic(Player.CHARACTER);
                } else {
                    gamePlay.updateOpponentLife(damage);
                    gamePlay.setStatusPic(Player.OPPONENT);
                }
                break;
            case "10":
                play();
                attackStr = status[1];
                damage = 84;
                gamePlay.setStatIndex(1);
                if (forWho == Player.OPPONENT) {
                    gamePlay.updatePlayerLife(damage);
                    gamePlay.setStatusPic(Player.CHARACTER);
                } else {
                    gamePlay.updateOpponentLife(damage);
                    gamePlay.setStatusPic(Player.OPPONENT);
                }
                break;
            case "11":
                play();
                attackStr = status[2];
                damage = 78;
                gamePlay.setStatIndex(1);
                if (forWho == Player.OPPONENT) {
                    gamePlay.updatePlayerLife(damage);
                    gamePlay.setStatusPic(Player.CHARACTER);
                } else {
                    gamePlay.updateOpponentLife(damage);
                    gamePlay.setStatusPic(Player.OPPONENT);
                }
                break;
            case "12":
                play();
                attackStr = status[3];
                damage = 80;
                gamePlay.setStatIndex(1);
                if (forWho == Player.OPPONENT) {
                    gamePlay.updatePlayerLife(damage);
                    gamePlay.setStatusPic(Player.CHARACTER);
                } else {
                    gamePlay.updateOpponentLife(damage);
                    gamePlay.setStatusPic(Player.OPPONENT);
                }
                break;
        }
    }
}

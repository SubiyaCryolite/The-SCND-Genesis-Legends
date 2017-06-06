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

import static com.scndgen.legends.enums.CharacterEnum.NOVA_ADAM;

/**
 * @author ndana
 */
public class NovaAdam extends Character {

    public NovaAdam() {
        descSmall = "Nova Adam - an awakened Celestia Being specialised in celestiaAttacks combat";
        name = "NovaAdam";
        characterEnum = NOVA_ADAM;
        physical = new String[]{"Dark Flame", "Dark Rush", "Dark Slice", "Dark Ascent"};
        celestia = new String[]{"Nova Blitz", "Nova Torrent", "Nova Blaze", "Nova Frost"};
        status = new String[]{"Heal Plus", "Heal EX", "Pain Killer", "Wound Spray"};
        behaviours1 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8};
        behaviours2 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 10, 11};
        behaviours3 = new int[]{0, 1, 7, 8, 10, 11};
        behaviours4 = new int[]{0, 1, 9, 12, 10, 11};
        behaviours5 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        life = 38400;
        limit = new int[]{0, 0, 0, 0, 0};
        atbRecoveryRate = 1.10f;//2.10;
        bragRights.put(CharacterEnum.RAILA.index(), "Resistance is futile !!!");
        bragRights.put(CharacterEnum.SUBIYA.index(), "Resistance is futile !!!" );
        bragRights.put(CharacterEnum.LYNX.index(), "Resistance is futile !!!" );
        bragRights.put(CharacterEnum.AISHA.index(), "Resistance is futile !!!" );
        bragRights.put(CharacterEnum.RAVAGE.index(), "Resistance is futile !!!" );
        bragRights.put(CharacterEnum.ADE.index(),  "Resistance is futile !!!");
        bragRights.put(CharacterEnum.JONAH.index(), "Resistance is futile !!!" );
        bragRights.put(CharacterEnum.ADAM.index(), "Resistance is futile !!!" );
        bragRights.put(CharacterEnum.NOVA_ADAM.index(), "Resistance is... Hold on?" );
        bragRights.put(CharacterEnum.AZARIA.index(), "Resistance is futile !!!" );
        bragRights.put(CharacterEnum.SORROWE.index(), "Resistance is futile !!!" );
        bragRights.put(CharacterEnum.THING.index(),  "Resistance is futile !!!");
    }

    @Override
    public void attack(String attack, Player player, GamePlay gamePlay) {
        switch (attack) {
            case "01":
                attackStr = physical[0];
                damage = 128;
                gamePlay.lifePhysUpdateSimple(player, damage);
                break;
            case "02":
                attackStr = physical[1];
                damage = 123;
                gamePlay.lifePhysUpdateSimple(player, damage);
                break;
            case "03":
                attackStr = physical[2];
                damage = 122;
                gamePlay.lifePhysUpdateSimple(player, damage);
                break;
            case "04":
                attackStr = physical[3];
                damage = 123;
                gamePlay.lifePhysUpdateSimple(player, damage);
                break;
            case "05":
                attackStr = celestia[0];
                damage = 122;
                gamePlay.lifePhysUpdateSimple(player, damage);
                break;
            case "06":
                attackStr = celestia[1];
                damage = 121;
                gamePlay.lifePhysUpdateSimple(player, damage);
                break;
            case "07":
                attackStr = celestia[2];
                damage = 125;
                gamePlay.lifePhysUpdateSimple(player, damage);
                break;
            case "08":
                attackStr = celestia[3];
                damage = 125;
                gamePlay.lifePhysUpdateSimple(player, damage);
                break;
            case "09":
                play();
                attackStr = status[0];
                damage = 123;
                gamePlay.setStatIndex(1);
                if (player == Player.OPPONENT) {
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
                damage = 125;
                gamePlay.setStatIndex(1);
                if (player == Player.OPPONENT) {
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
                damage = 129;
                gamePlay.setStatIndex(1);
                if (player == Player.OPPONENT) {
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
                damage = 111;
                gamePlay.setStatIndex(1);
                if (player == Player.OPPONENT) {
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

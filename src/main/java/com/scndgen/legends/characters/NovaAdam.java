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

import com.scndgen.legends.Colors;
import com.scndgen.legends.enums.*;
import com.scndgen.legends.render.RenderGameplay;

import static com.scndgen.legends.enums.Characters.NOVA_ADAM;

/**
 * @author ndana
 */
public class NovaAdam extends Character {

    public NovaAdam() {
        //strings
        descSmall = "Nova Adam - an awakened Celestia Being specialised in celestia combat";
        name = "NovaAdam";
        characters = NOVA_ADAM;
        //string arrays
        bragRights = new String[]{"Resistance is futile !!!", "Resistance is futile !!!", "Resistance is futile !!!", "Resistance is futile !!!", "Resistance is futile !!!", "Resistance is futile !!!", "Resistance is futile !!!", "Resistance is futile !!!", "Resistance is futile !!!", "Resistance is futile !!!", "Resistance is futile !!!", "*blank stare* Dude"};
        physical = new String[]{"Dark Flame", "Dark Rush", "Dark Slice", "Dark Ascent"};
        celestia = new String[]{"Nova Blitz", "Nova Torrent", "Nova Blaze", "Nova Frost"};
        status = new String[]{"Heal Plus", "Heal EX", "Pain Killer", "Wound Spray"};
        behaviours1 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8};
        behaviours2 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 10, 11};
        behaviours3 = new int[]{0, 1, 7, 8, 10, 11};
        behaviours4 = new int[]{0, 1, 9, 12, 10, 11};
        behaviours5 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        //ints
        life = 38400;
        limit = new int[]{0, 0, 0, 0, 0};
        //doubles
        actionRecoverRate = 1.10f;//2.10;
        hpRecovRate = 0.0002f;
    }

    @Override
    public void attack(String attack, CharacterState forWho) {
        switch (attack) {
            case "01":
                attackStr = physical[0];
                damage = 128;
                RenderGameplay.getInstance().lifePhysUpdateSimple(forWho, damage, name);
                RenderGameplay.getInstance().showBattleMessage(name + " used " + attackStr);
                break;
            case "02":
                attackStr = physical[1];
                damage = 123;
                RenderGameplay.getInstance().lifePhysUpdateSimple(forWho, damage, name);
                RenderGameplay.getInstance().showBattleMessage(name + " used " + attackStr);
                break;
            case "03":
                attackStr = physical[2];
                damage = 122;
                RenderGameplay.getInstance().lifePhysUpdateSimple(forWho, damage, name);
                RenderGameplay.getInstance().showBattleMessage(name + " used " + attackStr);
                break;
            case "04":
                attackStr = physical[3];
                damage = 123;
                RenderGameplay.getInstance().lifePhysUpdateSimple(forWho, damage, name);
                RenderGameplay.getInstance().showBattleMessage(name + " used " + attackStr);
                break;
            case "05":
                attackStr = celestia[0];
                damage = 122;
                RenderGameplay.getInstance().lifePhysUpdateSimple(forWho, damage, name);
                RenderGameplay.getInstance().showBattleMessage(name + " used " + attackStr);
                break;
            case "06":
                attackStr = celestia[1];
                damage = 121;
                RenderGameplay.getInstance().lifePhysUpdateSimple(forWho, damage, name);
                RenderGameplay.getInstance().showBattleMessage(name + " used " + attackStr);
                break;
            case "07":
                attackStr = celestia[2];
                damage = 125;
                RenderGameplay.getInstance().lifePhysUpdateSimple(forWho, damage, name);
                RenderGameplay.getInstance().showBattleMessage(name + " used " + attackStr);
                break;
            case "08":
                attackStr = celestia[3];
                damage = 125;
                RenderGameplay.getInstance().lifePhysUpdateSimple(forWho, damage, name);
                RenderGameplay.getInstance().showBattleMessage(name + " used " + attackStr);
                break;
            case "09":
                sound3.play();
                attackStr = status[0];
                damage = 123;
                RenderGameplay.getInstance().setStatIndex(1);
                if (forWho == CharacterState.OPPONENT) {
                    RenderGameplay.getInstance().updatePlayerLife(damage);
                    RenderGameplay.getInstance().setStatusPic(CharacterState.CHARACTER, "+" + damage + "0 HP", Colors.getColor("green"));
                } else {
                    RenderGameplay.getInstance().updateOpponentLife(damage);
                    RenderGameplay.getInstance().setStatusPic(CharacterState.OPPONENT, "+" + damage + "0 HP", Colors.getColor("green"));
                }
                RenderGameplay.getInstance().showBattleMessage(name + " used " + attackStr);
                break;
            case "10":
                sound3.play();
                attackStr = status[1];
                damage = 125;
                RenderGameplay.getInstance().setStatIndex(1);
                if (forWho == CharacterState.OPPONENT) {
                    RenderGameplay.getInstance().updatePlayerLife(damage);
                    RenderGameplay.getInstance().setStatusPic(CharacterState.CHARACTER, "+" + damage + "0 HP", Colors.getColor("green"));
                } else {
                    RenderGameplay.getInstance().updateOpponentLife(damage);
                    RenderGameplay.getInstance().setStatusPic(CharacterState.OPPONENT, "+" + damage + "0 HP", Colors.getColor("green"));
                }
                RenderGameplay.getInstance().showBattleMessage(name + " used " + attackStr);
                break;
            case "11":
                sound3.play();
                attackStr = status[2];
                damage = 129;
                RenderGameplay.getInstance().setStatIndex(1);
                if (forWho == CharacterState.OPPONENT) {
                    RenderGameplay.getInstance().updatePlayerLife(damage);
                    RenderGameplay.getInstance().setStatusPic(CharacterState.CHARACTER, "+" + damage + "0 HP", Colors.getColor("green"));
                } else {
                    RenderGameplay.getInstance().updateOpponentLife(damage);
                    RenderGameplay.getInstance().setStatusPic(CharacterState.OPPONENT, "+" + damage + "0 HP", Colors.getColor("green"));
                }
                RenderGameplay.getInstance().showBattleMessage(name + " used " + attackStr);
                break;
            case "12":
                sound3.play();
                attackStr = status[3];
                damage = 111;
                RenderGameplay.getInstance().setStatIndex(1);
                if (forWho == CharacterState.OPPONENT) {
                    RenderGameplay.getInstance().updatePlayerLife(damage);
                    RenderGameplay.getInstance().setStatusPic(CharacterState.CHARACTER, "+" + damage + "0 HP", Colors.getColor("green"));
                } else {
                    RenderGameplay.getInstance().updateOpponentLife(damage);
                    RenderGameplay.getInstance().setStatusPic(CharacterState.OPPONENT, "+" + damage + "0 HP", Colors.getColor("green"));
                }
                RenderGameplay.getInstance().showBattleMessage(name + " used " + attackStr);
                break;
        }
    }
}

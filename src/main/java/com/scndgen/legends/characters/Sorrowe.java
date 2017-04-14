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
import com.scndgen.legends.arefactored.mode.StandardGameplay;
import com.scndgen.legends.arefactored.render.RenderStandardGameplay;
import com.scndgen.legends.engine.JenesisCharacter;
import com.scndgen.legends.enums.CharacterEnum;

/**
 * Sorrowe's class
 *
 * @author ndana
 */
public class Sorrowe extends JenesisCharacter {

    /**
     * Constructor
     */
    public Sorrowe() {
        descSmall = "Sorrowe - Specialised in celestia combat and the flame element";
        name = "Sorrowe";
        characterEnum = CharacterEnum.SORROWE;
        isNotMale();
        bragRights = new String[]{"Look what the cat dragged in", "Sorry, you're not my type", "A rea challenge, 'bout time", "Weakling !!", "You're incredibly annoying", "Don't go easy on me", "Lets have some fun!", "NovaAdam, I shall surpass even you", "That power will soon be mine", "You don't scare me", "Let's do this", "Ugh, disgusting"};
        physical = new String[]{"Lashing", "Whip-nado", "Lash assault", "Snared"};
        celestia = new String[]{"Hell Falme", "Hell Judgement", "Hell Blast", "Hell Blade"};
        status = new String[]{"Heal", "Cura EX", "Health ++", "E-Juice"};
        behaviours1 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8};
        behaviours2 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 10, 11};
        behaviours3 = new int[]{0, 1, 7, 8, 10, 11};
        behaviours4 = new int[]{0, 1, 9, 12, 10, 11};
        behaviours5 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        //ints
        points = 1800;
        life = 31360;
        hitPoints = 100;
        limit = new int[]{0, 0, 0, 0, 0};
        //doubles
        actionRecoverRate = 2.02f;//2.10;
        hpRecovRate = 0.0002f;
    }

    @Override
    public void attack(String attack, int forWho) {
        if (attack.equalsIgnoreCase("01")) {
            attackStr = physical[0];
            damage = 102;
            RenderStandardGameplay.getInstance().lifePhysUpdateSimple(forWho, damage, name);
            RenderStandardGameplay.getInstance().showBattleMessage(name + " used " + attackStr);
        }

        if (attack.equalsIgnoreCase("02")) {
            attackStr = physical[1];
            damage = 105;
            RenderStandardGameplay.getInstance().lifePhysUpdateSimple(forWho, damage, name);
            RenderStandardGameplay.getInstance().showBattleMessage(name + " used " + attackStr);
        }

        if (attack.equalsIgnoreCase("03")) {
            attackStr = physical[2];
            damage = 102;
            RenderStandardGameplay.getInstance().lifePhysUpdateSimple(forWho, damage, name);
            RenderStandardGameplay.getInstance().showBattleMessage(name + " used " + attackStr);
        }

        if (attack.equalsIgnoreCase("04")) {
            attackStr = physical[3];
            damage = 103;
            RenderStandardGameplay.getInstance().lifePhysUpdateSimple(forWho, damage, name);
            RenderStandardGameplay.getInstance().showBattleMessage(name + " used " + attackStr);
        }

        if (attack.equalsIgnoreCase("05")) {
            attackStr = celestia[0];
            damage = 102;
            RenderStandardGameplay.getInstance().lifePhysUpdateSimple(forWho, damage, name);
            RenderStandardGameplay.getInstance().showBattleMessage(name + " used " + attackStr);
        }

        if (attack.equalsIgnoreCase("06")) {
            attackStr = celestia[1];
            damage = 101;
            RenderStandardGameplay.getInstance().lifePhysUpdateSimple(forWho, damage, name);
            RenderStandardGameplay.getInstance().showBattleMessage(name + " used " + attackStr);
        }

        if (attack.equalsIgnoreCase("07")) {
            attackStr = celestia[2];
            damage = 108;
            RenderStandardGameplay.getInstance().lifePhysUpdateSimple(forWho, damage, name);
            RenderStandardGameplay.getInstance().showBattleMessage(name + " used " + attackStr);
        }

        if (attack.equalsIgnoreCase("08")) {
            attackStr = celestia[3];
            damage = 105;
            RenderStandardGameplay.getInstance().lifePhysUpdateSimple(forWho, damage, name);
            RenderStandardGameplay.getInstance().showBattleMessage(name + " used " + attackStr);
        }

        if (attack.equalsIgnoreCase("09")) {
            sound3.play();
            attackStr = status[0];
            //girls have a healing bonus of 5 XD
            damage = 78;
            StandardGameplay.setStatIndex(1);
            if (forWho == 2) {
                RenderStandardGameplay.getInstance().updateLife(damage);
                StandardGameplay.setStatusPic('c', "+" + damage + "0 HP", Colors.getColor("green"));
            } else {
                RenderStandardGameplay.getInstance().updateOppLife(damage);
                StandardGameplay.setStatusPic('o', "+" + damage + "0 HP", Colors.getColor("green"));
            }
            RenderStandardGameplay.getInstance().showBattleMessage(name + " used " + attackStr);
        }

        if (attack.equalsIgnoreCase("10")) {
            sound3.play();
            attackStr = status[1];
            damage = 80;
            StandardGameplay.setStatIndex(1);
            if (forWho == 2) {
                RenderStandardGameplay.getInstance().updateLife(damage);
                StandardGameplay.setStatusPic('c', "+" + damage + "0 HP", Colors.getColor("green"));
            } else {
                RenderStandardGameplay.getInstance().updateOppLife(damage);
                StandardGameplay.setStatusPic('o', "+" + damage + "0 HP", Colors.getColor("green"));
            }
            RenderStandardGameplay.getInstance().showBattleMessage(name + " used " + attackStr);
        }

        if (attack.equalsIgnoreCase("11")) {
            sound3.play();
            attackStr = status[2];
            damage = 84;
            StandardGameplay.setStatIndex(1);
            if (forWho == 2) {
                RenderStandardGameplay.getInstance().updateLife(damage);
                StandardGameplay.setStatusPic('c', "+" + damage + "0 HP", Colors.getColor("green"));
            } else {
                RenderStandardGameplay.getInstance().updateOppLife(damage);
                StandardGameplay.setStatusPic('o', "+" + damage + "0 HP", Colors.getColor("green"));
            }
            RenderStandardGameplay.getInstance().showBattleMessage(name + " used " + attackStr);
        }

        if (attack.equalsIgnoreCase("12")) {
            sound3.play();
            attackStr = status[3];
            damage = 76;
            StandardGameplay.setStatIndex(1);
            if (forWho == 2) {
                RenderStandardGameplay.getInstance().updateLife(damage);
                StandardGameplay.setStatusPic('c', "+" + damage + "0 HP", Colors.getColor("green"));
            } else {
                RenderStandardGameplay.getInstance().updateOppLife(damage);
                StandardGameplay.setStatusPic('o', "+" + damage + "0 HP", Colors.getColor("green"));
            }
            RenderStandardGameplay.getInstance().showBattleMessage(name + " used " + attackStr);
        }

        //dummy, do nothing
        if (attack.equalsIgnoreCase("99")) {
        }
    }
}

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
import com.scndgen.legends.arefactored.render.RenderStandardGameplay;
import com.scndgen.legends.engine.JenesisCharacter;
import com.scndgen.legends.enums.CharacterEnum;

/**
 * @author ndana
 */
public class Subiya extends JenesisCharacter {

    public Subiya() {
        //strings
        descSmall = "Subiya - a fighter specialised in melee combat";
        name = "Subiya";
        characterEnum = CharacterEnum.SUBIYA;
        //string arrays
        bragRights = new String[]{"Lets do this!!", "Sorry bro, it had to be done", "Students always surpass their masters", "Hate to beat down on a lady", "PAYBACK TIME !!!", "You're definately strong, just not strong enough", "I hope you're nothing like your brother", "How does it feel to fall from grace", "Is this how far you've fallen", "Forgive me, I must defeat you", "I'll admit, you're rather beutiful", "What is that thing?!"};
        physical = new String[]{"Thunder Clap", "Knee Strike", "Thunder Clap", "Knee Strike EX2"};
        celestia = new String[]{"Flaming Pillars", "Blaze", "Flaming Vortex", "Blazing Comet"};
        status = new String[]{"Heal Plus", "Heal EX", "Bandage", "Pain Killer"};
        behaviours1 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8};
        behaviours2 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 10, 11};
        behaviours3 = new int[]{0, 1, 7, 8, 10, 11};
        behaviours4 = new int[]{0, 1, 9, 12, 10, 11};
        behaviours5 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};

        //ints
        points = 2000;
        life = 27200;
        hitPoints = 70;
        limit = new int[]{0, 0, 0, 0, 0};


        //doubles
        actionRecoverRate = 1.97f;//2.25;
        hpRecovRate = 0.0002f;
    }

    @Override
    public void attack(String attack, int forWho) {
        if (attack.equalsIgnoreCase("01")) {
            attackStr = physical[0];
            damage = 85;
            RenderStandardGameplay.getInstance().lifePhysUpdateSimple(forWho, damage, name);
            RenderStandardGameplay.getInstance().showBattleMessage(name + " used " + attackStr);
        } else if (attack.equalsIgnoreCase("02")) {
            attackStr = physical[1];
            damage = 87;
            RenderStandardGameplay.getInstance().lifePhysUpdateSimple(forWho, damage, name);
            RenderStandardGameplay.getInstance().showBattleMessage(name + " used " + attackStr);
        } else if (attack.equalsIgnoreCase("03")) {
            attackStr = physical[2];
            damage = 90;
            RenderStandardGameplay.getInstance().lifePhysUpdateSimple(forWho, damage, name);
            RenderStandardGameplay.getInstance().showBattleMessage(name + " used " + attackStr);
        } else if (attack.equalsIgnoreCase("04")) {
            attackStr = physical[3];
            damage = 87;
            RenderStandardGameplay.getInstance().lifePhysUpdateSimple(forWho, damage, name);
            RenderStandardGameplay.getInstance().showBattleMessage(name + " used " + attackStr);
        } else if (attack.equalsIgnoreCase("05")) {
            attackStr = celestia[0];
            damage = 88;
            RenderStandardGameplay.getInstance().lifePhysUpdateSimple(forWho, damage, name);
            RenderStandardGameplay.getInstance().showBattleMessage(name + " used " + attackStr);
        } else if (attack.equalsIgnoreCase("06")) {
            attackStr = celestia[1];
            damage = 86;
            RenderStandardGameplay.getInstance().lifePhysUpdateSimple(forWho, damage, name);
            RenderStandardGameplay.getInstance().showBattleMessage(name + " used " + attackStr);
        } else if (attack.equalsIgnoreCase("07")) {
            attackStr = celestia[2];
            damage = 88;
            RenderStandardGameplay.getInstance().lifePhysUpdateSimple(forWho, damage, name);
            RenderStandardGameplay.getInstance().showBattleMessage(name + " used " + attackStr);
        } else if (attack.equalsIgnoreCase("08")) {
            attackStr = celestia[3];
            damage = 93;
            RenderStandardGameplay.getInstance().lifePhysUpdateSimple(forWho, damage, name);
            RenderStandardGameplay.getInstance().showBattleMessage(name + " used " + attackStr);
        } else if (attack.equalsIgnoreCase("09")) {
            sound3.play();
            attackStr = status[0];
            damage = 79;
            RenderStandardGameplay.getInstance().setStatIndex(1);
            if (forWho == 2) {
                RenderStandardGameplay.getInstance().updatePlayerLife(damage);
                RenderStandardGameplay.getInstance().setStatusPic('c', "+" + damage + "0 HP", Colors.getColor("blue"));
            } else {
                RenderStandardGameplay.getInstance().updateOpponentLife(damage);
                RenderStandardGameplay.getInstance().setStatusPic('o', "+" + damage + "0 HP", Colors.getColor("blue"));
            }
            RenderStandardGameplay.getInstance().showBattleMessage(name + " used " + attackStr);
        } else if (attack.equalsIgnoreCase("10")) {
            sound3.play();
            attackStr = status[1];
            damage = 69;
            RenderStandardGameplay.getInstance().setStatIndex(1);
            if (forWho == 2) {
                RenderStandardGameplay.getInstance().updatePlayerLife(damage);
                RenderStandardGameplay.getInstance().setStatusPic('c', "+" + damage + "0 HP", Colors.getColor("green"));
            } else {
                RenderStandardGameplay.getInstance().updateOpponentLife(damage);
                RenderStandardGameplay.getInstance().setStatusPic('o', "+" + damage + "0 HP", Colors.getColor("green"));
            }
            RenderStandardGameplay.getInstance().showBattleMessage(name + " used " + attackStr);
        } else if (attack.equalsIgnoreCase("11")) {
            sound3.play();
            attackStr = status[2];
            damage = 73;
            RenderStandardGameplay.getInstance().setStatIndex(1);
            if (forWho == 2) {
                RenderStandardGameplay.getInstance().updatePlayerLife(damage);
                RenderStandardGameplay.getInstance().setStatusPic('c', "+" + damage + "0 HP", Colors.getColor("green"));
            } else {
                RenderStandardGameplay.getInstance().updateOpponentLife(damage);
                RenderStandardGameplay.getInstance().setStatusPic('o', "+" + damage + "0 HP", Colors.getColor("green"));
            }
            RenderStandardGameplay.getInstance().showBattleMessage(name + " used " + attackStr);
        } else if (attack.equalsIgnoreCase("12")) {
            sound3.play();
            attackStr = status[3];
            damage = 72;
            RenderStandardGameplay.getInstance().setStatIndex(1);
            if (forWho == 2) {
                RenderStandardGameplay.getInstance().updatePlayerLife(damage);
                RenderStandardGameplay.getInstance().setStatusPic('c', "+" + damage + "0 HP", Colors.getColor("green"));
            } else {
                RenderStandardGameplay.getInstance().updateOpponentLife(damage);
                RenderStandardGameplay.getInstance().setStatusPic('o', "+" + damage + "0 HP", Colors.getColor("green"));
            }
            RenderStandardGameplay.getInstance().showBattleMessage(name + " used " + attackStr);
        } else if (attack.equalsIgnoreCase("99")) {
        }
    }
}

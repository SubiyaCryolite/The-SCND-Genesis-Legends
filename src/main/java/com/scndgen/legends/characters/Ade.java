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
import com.scndgen.legends.drawing.DrawGame;
import com.scndgen.legends.engine.JenesisCharacter;
import com.scndgen.legends.LoginScreen;

/**
 * @author ndana
 */
public class Ade extends JenesisCharacter {

    public Ade() {
        //strings
        descSmall = "Ade - a fighter utilising the air element";
        name = "Ade";

        //string arrays
        bragRights = new String[]{"Be gone", "Weakling", "Pathetic", "Is this a joke", "Not you again", "Lets do this", "You have more sense than you're brother, give up now", "I won't hold back", "It's an honour to face you in this form", "Your title doesn't scare me, I'll still destroy you", "Sorrowe, don't get big headed", "This is it?"};
        physical = new String[]{"Tornado Blast", "Hurricane Sphere", "Hurricane Barrage", "Violent Burst"};
        celestia = new String[]{"Crush Down", "Vortex Blades", "Cursed Seal", "Dark Swirl"};
        status = new String[]{"Heal Plus", "Heal EX", "Bandage", "Wound Spray"};
        arr1 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8};
        arr2 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 10, 11};
        arr3 = new int[]{0, 1, 7, 8, 10, 11};
        arr4 = new int[]{0, 1, 9, 12, 10, 11};
        arr5 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};

        //ints
        points = 2200;
        life = 33600;
        hitPoints = 70;
        limit = new int[]{0, 0, 0, 0, 0};

        //doubles
        actionRecoverRate = 1.60f;//1.75;
        hpRecovRate = 0.00018f;

    }

    @Override
    public void attack(String attack, int forWho) {
        if (attack.equalsIgnoreCase("00")) {
            attackStr = physical[0];
            damage = 50;
            LoginScreen.getInstance().getMenu().getMain().getGame().lifePhysUpdateSimple(forWho, damage, name);
            LoginScreen.getInstance().getMenu().getMain().getGame().showBattleMessage(name + " used " + attackStr);
        }

        if (attack.equalsIgnoreCase("01")) {
            attackStr = physical[0];
            damage = 110;
            LoginScreen.getInstance().getMenu().getMain().getGame().lifePhysUpdateSimple(forWho, damage, name);
            LoginScreen.getInstance().getMenu().getMain().getGame().showBattleMessage(name + " used " + attackStr);
        }

        if (attack.equalsIgnoreCase("02")) {
            attackStr = physical[1];
            damage = 106;
            LoginScreen.getInstance().getMenu().getMain().getGame().lifePhysUpdateSimple(forWho, damage, name);
            LoginScreen.getInstance().getMenu().getMain().getGame().showBattleMessage(name + " used " + attackStr);
        }

        if (attack.equalsIgnoreCase("03")) {
            attackStr = physical[2];
            damage = 110;
            LoginScreen.getInstance().getMenu().getMain().getGame().lifePhysUpdateSimple(forWho, damage, name);
            LoginScreen.getInstance().getMenu().getMain().getGame().showBattleMessage(name + " used " + attackStr);
        }

        if (attack.equalsIgnoreCase("04")) {
            attackStr = physical[3];
            damage = 108;
            LoginScreen.getInstance().getMenu().getMain().getGame().lifePhysUpdateSimple(forWho, damage, name);
            LoginScreen.getInstance().getMenu().getMain().getGame().showBattleMessage(name + " used " + attackStr);
        }

        if (attack.equalsIgnoreCase("05")) {
            attackStr = celestia[0];
            damage = 107;
            LoginScreen.getInstance().getMenu().getMain().getGame().lifePhysUpdateSimple(forWho, damage, name);
            LoginScreen.getInstance().getMenu().getMain().getGame().showBattleMessage(name + " used " + attackStr);
        }

        if (attack.equalsIgnoreCase("06")) {
            attackStr = celestia[1];
            damage = 106;
            LoginScreen.getInstance().getMenu().getMain().getGame().lifePhysUpdateSimple(forWho, damage, name);
            LoginScreen.getInstance().getMenu().getMain().getGame().showBattleMessage(name + " used " + attackStr);
        }

        if (attack.equalsIgnoreCase("07")) {
            attackStr = celestia[2];
            damage = 108;
            LoginScreen.getInstance().getMenu().getMain().getGame().lifePhysUpdateSimple(forWho, damage, name);
            LoginScreen.getInstance().getMenu().getMain().getGame().showBattleMessage(name + " used " + attackStr);
        }

        if (attack.equalsIgnoreCase("08")) {
            attackStr = celestia[3];
            damage = 113;
            LoginScreen.getInstance().getMenu().getMain().getGame().lifePhysUpdateSimple(forWho, damage, name);
            LoginScreen.getInstance().getMenu().getMain().getGame().showBattleMessage(name + " used " + attackStr);
        }

        if (attack.equalsIgnoreCase("09")) {
            sound3.play();
            attackStr = status[0];
            damage = 77;
            DrawGame.setStatIndex(1);
            if (forWho == 2) {
                LoginScreen.getInstance().getMenu().getMain().getGame().updateLife(damage);
                DrawGame.setStatusPic('c', "+" + damage + "0 HP", Colors.getColor("green"));
            } else {
                LoginScreen.getInstance().getMenu().getMain().getGame().updateOppLife(damage);
                DrawGame.setStatusPic('o', "+" + damage + "0 HP", Colors.getColor("green"));
            }
            LoginScreen.getInstance().getMenu().getMain().getGame().showBattleMessage(name + " used " + attackStr);
        }

        if (attack.equalsIgnoreCase("10")) {
            sound3.play();
            attackStr = status[1];
            damage = 79;
            DrawGame.setStatIndex(1);
            if (forWho == 2) {
                LoginScreen.getInstance().getMenu().getMain().getGame().updateLife(damage);
                DrawGame.setStatusPic('c', "+" + damage + "0 HP", Colors.getColor("green"));
            } else {
                LoginScreen.getInstance().getMenu().getMain().getGame().updateOppLife(damage);
                DrawGame.setStatusPic('o', "+" + damage + "0 HP", Colors.getColor("green"));
            }
            LoginScreen.getInstance().getMenu().getMain().getGame().showBattleMessage(name + " used " + attackStr);
        }

        if (attack.equalsIgnoreCase("11")) {
            sound3.play();
            attackStr = status[2];
            damage = 73;
            DrawGame.setStatIndex(1);
            if (forWho == 2) {
                LoginScreen.getInstance().getMenu().getMain().getGame().updateLife(damage);
                DrawGame.setStatusPic('c', "+" + damage + "0 HP", Colors.getColor("green"));
            } else {
                LoginScreen.getInstance().getMenu().getMain().getGame().updateOppLife(damage);
                DrawGame.setStatusPic('o', "+" + damage + "0 HP", Colors.getColor("green"));
            }
            LoginScreen.getInstance().getMenu().getMain().getGame().showBattleMessage(name + " used " + attackStr);
        }

        if (attack.equalsIgnoreCase("12")) {
            sound3.play();
            attackStr = status[3];
            damage = 75;
            DrawGame.setStatIndex(1);
            if (forWho == 2) {
                LoginScreen.getInstance().getMenu().getMain().getGame().updateLife(damage);
                DrawGame.setStatusPic('c', "+" + damage + "0 HP", Colors.getColor("green"));
            } else {
                LoginScreen.getInstance().getMenu().getMain().getGame().updateOppLife(damage);
                DrawGame.setStatusPic('o', "+" + damage + "0 HP", Colors.getColor("green"));
            }
            LoginScreen.getInstance().getMenu().getMain().getGame().showBattleMessage(name + " used " + attackStr);
        }

        //dummy, do nothing
        if (attack.equalsIgnoreCase("99")) {
        }
    }
}

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
package com.scndgen.legends.threads;

import com.scndgen.legends.Colors;
import com.scndgen.legends.enums.CharacterState;
import com.scndgen.legends.enums.SubMode;
import com.scndgen.legends.render.RenderGameplay;
import com.scndgen.legends.windows.MainWindow;

/**
 * @author ndana
 */
public class ClashSystem implements Runnable {

    private static ClashSystem instance;
    public int sleepTime = 0, person;
    public float plyrClash, oppClash, plyClashPerc, oppClashPerc;
    private Thread thread;
    private boolean isClashOn = false;
    private CharacterState personChar;

    public ClashSystem(int who, CharacterState homie) {
        person = who;
        personChar = homie;
        if (thread != null) {
            thread.resume();
        } else {
            thread = new Thread(this);
            thread.start();
        }
        instance = this;
    }

    public static ClashSystem getInstance() {
        return instance;
    }

    public void oppClashing() {
        System.out.println("player : " + plyrClash + " opponent: " + oppClash);
        oppClash = oppClash + 1;
        plyClashPerc = (plyrClash / (plyrClash + oppClash)) * 100;
        oppClashPerc = (oppClash / (plyrClash + oppClash)) * 100;
    }

    public void setOpp(int amt) {
        oppClashPerc = (float) amt;
        plyClashPerc = 100 - oppClashPerc;
    }

    @Override
    public void run() {
        plyClashPerc = 50;
        oppClashPerc = 50;
        plyrClash = 1;
        oppClash = 1;
        isClashOn = true;
        for (int u = 0; u < 5; u++) {
            try {
                thread.sleep(1000);
                sleepTime = sleepTime - 1;
            } catch (InterruptedException ex) {
                ex.printStackTrace(System.err);
            }
        }
        isClashOn = false;
        RenderGameplay.getInstance().setClasherRunnign(false);
        clashWinner(person);
    }

    public void stop() {
        thread = null;
        //thread.destroy();
    }

    private void clashWinner(int caller) {
        if (plyClashPerc > oppClashPerc)//player wins
        {
            if (caller == 1) {
                //if player triggered clash and won, they attack
                RenderGameplay.getInstance().setStatusPic(CharacterState.CHARACTER, "GOT EM !!!", Colors.getColor("blue"));
                MainWindow.getInstance().triggerFury(CharacterState.CHARACTER);
            } else {
                RenderGameplay.getInstance().setStatusPic(CharacterState.OPPONENT, "EVADED YA!!!", Colors.getColor("red"));
                RenderGameplay.getInstance().resetBreak();
                RenderGameplay.getInstance().updatePlayerLife(200);
                //player didn'thread trigger clash but won, they arent attacked
            }

        } else //opponent wins
        {
            if (caller == 2) {
                //if opponent triggered clash and won, they attack
                RenderGameplay.getInstance().setStatusPic(CharacterState.OPPONENT, "GOT YA !!!", Colors.getColor("blue"));
                MainWindow.getInstance().triggerFury(CharacterState.OPPONENT);
            } else {
                RenderGameplay.getInstance().setStatusPic(CharacterState.CHARACTER, "EVADED !!!", Colors.getColor("red"));
                RenderGameplay.getInstance().resetBreak();
                RenderGameplay.getInstance().updateOpponentLife(200);
                //opponent didn'thread trigger clash but won, they arent attacked
            }
        }
    }

    public void plrClashing() {
        if (MainWindow.getInstance().getGameMode()== SubMode.LAN_CLIENT) {
            MainWindow.getInstance().sendToServer("oppClsh" + plyClashPerc);
        } else if (MainWindow.getInstance().getGameMode()== SubMode.LAN_HOST) {
            MainWindow.getInstance().sendToClient("oppClsh" + plyClashPerc);
        }
        plyrClash = plyrClash + 1;
        plyClashPerc = (plyrClash / (plyrClash + oppClash)) * 100;
        oppClashPerc = (oppClash / (plyrClash + oppClash)) * 100;
    }
}

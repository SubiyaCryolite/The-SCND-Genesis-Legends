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
import com.scndgen.legends.LoginScreen;
import com.scndgen.legends.arefactored.mode.StandardGameplay;
import com.scndgen.legends.arefactored.render.RenderStandardGameplay;
import com.scndgen.legends.windows.WindowMain;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author ndana
 */
public class ThreadClashSystem implements Runnable {

    public static int sleepTime = 0, person;
    public static float plyrClash, oppClash, plyClashPerc, oppClashPerc;
    private static Thread t;
    private static boolean isClashOn = false;
    private static char personChar;

    public ThreadClashSystem(int who, char homie) {
        person = who;
        personChar = homie;
        t = new Thread(this);
        t.setPriority(1);
        t.start();
    }

    public static void oppClashing() {
        System.out.println("player : " + plyrClash + " opponent: " + oppClash);
        oppClash = oppClash + 1;
        plyClashPerc = (plyrClash / (plyrClash + oppClash)) * 100;
        oppClashPerc = (oppClash / (plyrClash + oppClash)) * 100;
    }

    public static void setOpp(int amt) {
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
                t.sleep(1000);
                sleepTime = sleepTime - 1;
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadClashSystem.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        isClashOn = false;
        StandardGameplay.clasherOn = false;
        clashWinner(person);
    }

    public void stop() {
        t = null;
        //t.destroy();
    }

    public void pauseThread() {
        t.suspend();
    }

    public void resumeThread() {
        t.resume();
    }

    private void clashWinner(int caller) {
        if (plyClashPerc > oppClashPerc)//player wins
        {
            if (caller == 1) {
                //if player triggered clash and won, they attack
                StandardGameplay.setStatusPic('c', "GOT EM !!!", Colors.getColor("blue"));
                LoginScreen.getInstance().getMenu().getMain().triggerFury('c');
            } else {
                StandardGameplay.setStatusPic('o', "EVADED YA!!!", Colors.getColor("red"));
                RenderStandardGameplay.getInstance().resetBreak();
                RenderStandardGameplay.getInstance().updateLife(200);
                //player didn't trigger clash but won, they arent attacked
            }

        } else //opponent wins
        {
            if (caller == 2) {
                //if opponent triggered clash and won, they attack
                StandardGameplay.setStatusPic('o', "GOT YA !!!", Colors.getColor("blue"));
                LoginScreen.getInstance().getMenu().getMain().triggerFury('o');

            } else {
                StandardGameplay.setStatusPic('c', "EVADED !!!", Colors.getColor("red"));
                RenderStandardGameplay.getInstance().resetBreak();
                RenderStandardGameplay.getInstance().updateOppLife(200);
                //opponent didn't trigger clash but won, they arent attacked
            }
        }
    }

    public void plrClashing() {
        if (LoginScreen.getInstance().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanClient)) {
            LoginScreen.getInstance().getMenu().getMain().sendToServer("oppClsh" + plyClashPerc);
        } else if (LoginScreen.getInstance().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanHost)) {
            LoginScreen.getInstance().getMenu().getMain().sendToClient("oppClsh" + plyClashPerc);
        }

        plyrClash = plyrClash + 1;
        plyClashPerc = (plyrClash / (plyrClash + oppClash)) * 100;
        oppClashPerc = (oppClash / (plyrClash + oppClash)) * 100;
    }
}

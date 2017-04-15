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

import com.scndgen.legends.render.RenderGameplay;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author ndana
 */
public class ClashingOpponent implements Runnable {

    public static int sleepTime = 0, plyrClash, person, oppClash, plyClashPerc, oppClashPerc;
    private Thread thread;
    private static boolean isClashOn = false;
    private static char personChar;

    public ClashingOpponent() {
        if (thread != null) {
            thread.resume();
        } else {
            thread = new Thread(this);
            thread.setPriority(1);
            thread.start();
        }
    }

    @Override
    public void run() {
        while (RenderGameplay.getInstance().getClasherRunnign()) {
            try {
                int delay = 360;
                RenderGameplay.getInstance().opponetClashing();
                thread.sleep((int) (delay * Math.random()));
            } catch (InterruptedException ex) {
                Logger.getLogger(ClashingOpponent.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void stop() {
        thread.destroy();
        thread = null;
    }

    public void pauseThread() {
        thread.suspend();
    }

    public void resumeThread() {
        thread.resume();
    }
}

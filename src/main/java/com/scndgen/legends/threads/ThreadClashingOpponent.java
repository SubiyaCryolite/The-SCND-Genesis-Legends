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

import com.scndgen.legends.arefactored.render.RenderStandardGameplay;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author ndana
 */
public class ThreadClashingOpponent implements Runnable {

    public static int sleepTime = 0, plyrClash, person, oppClash, plyClashPerc, oppClashPerc;
    private static Thread t;
    private static boolean isClashOn = false;
    private static char personChar;

    public ThreadClashingOpponent() {
        t = new Thread(this);
        t.setPriority(1);
        t.start();
    }

    @Override
    public void run() {
        while (RenderStandardGameplay.getInstance().clasherOn) {
            try {
                int delay = 360;
                //int delay=WindowOptions.Arr[WindowOptions.whichOne()];
                RenderStandardGameplay.getInstance().opponetClashing();
                //JOptionPane.showMessageDialog(null,"Opponent clashing");
                t.sleep((int) (delay * Math.random()));
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadClashingOpponent.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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
}

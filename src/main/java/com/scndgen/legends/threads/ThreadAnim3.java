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

import com.scndgen.legends.arefactored.mode.StandardGameplay;
import com.scndgen.legends.drawing.DrawOverworld;
import com.scndgen.legends.windows.WindowOptions;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author ndana
 */
public class ThreadAnim3 implements Runnable {

    static Thread t;

    public ThreadAnim3() {
        if (t != null) {
            t.resume();
        } else {
            t = new Thread(this);
            t.setPriority(1);
            t.setName("Animator thread 3 - Background and Foreground");
            t.start();
        }
    }

    @Override
    public void run() {
        if (WindowOptions.graphics.equalsIgnoreCase("High")) {
            do {
                try {
                    if (StandardGameplay.verticalMove.equalsIgnoreCase("no")) {
                        t.sleep(0016);
                        StandardGameplay.amb1x = StandardGameplay.amb1x - StandardGameplay.ambSpeed1;
                        StandardGameplay.amb2x = StandardGameplay.amb2x - StandardGameplay.ambSpeed2;

                        if (StandardGameplay.amb1x < (-960)) {
                            StandardGameplay.amb1x = 852;
                        }
                        if (StandardGameplay.amb2x < (-960)) {
                            StandardGameplay.amb2x = 852;
                        }
                    } else {
                        t.sleep(0016);
                        StandardGameplay.amb1y = StandardGameplay.amb1y + StandardGameplay.ambSpeed1;
                        StandardGameplay.amb2y = StandardGameplay.amb2y + StandardGameplay.ambSpeed2;

                        if (StandardGameplay.amb1y > 480) {
                            StandardGameplay.amb1y = -480;
                        }
                        if (StandardGameplay.amb2y > 480) {
                            StandardGameplay.amb2y = -480;
                        }
                    }

                } catch (InterruptedException ex) {
                    Logger.getLogger(DrawOverworld.class.getName()).log(Level.SEVERE, null, ex);
                }
            } while (1 != 0);
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

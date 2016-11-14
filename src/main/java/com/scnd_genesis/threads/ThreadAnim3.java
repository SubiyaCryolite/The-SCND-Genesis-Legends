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
package com.scnd_genesis.threads;

import com.scnd_genesis.drawing.DrawGame;
import com.scnd_genesis.drawing.DrawOverworld;
import com.scnd_genesis.windows.WindowOptions;

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
                    if (DrawGame.verticalMove.equalsIgnoreCase("no")) {
                        t.sleep(0016);
                        DrawGame.amb1x = DrawGame.amb1x - DrawGame.ambSpeed1;
                        DrawGame.amb2x = DrawGame.amb2x - DrawGame.ambSpeed2;

                        if (DrawGame.amb1x < (-960)) {
                            DrawGame.amb1x = 852;
                        }
                        if (DrawGame.amb2x < (-960)) {
                            DrawGame.amb2x = 852;
                        }
                    } else {
                        t.sleep(0016);
                        DrawGame.amb1y = DrawGame.amb1y + DrawGame.ambSpeed1;
                        DrawGame.amb2y = DrawGame.amb2y + DrawGame.ambSpeed2;

                        if (DrawGame.amb1y > 480) {
                            DrawGame.amb1y = -480;
                        }
                        if (DrawGame.amb2y > 480) {
                            DrawGame.amb2y = -480;
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

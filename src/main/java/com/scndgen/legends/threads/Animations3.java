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

import com.scndgen.legends.drawing.DrawOverworld;
import com.scndgen.legends.render.RenderGameplay;
import com.scndgen.legends.windows.WindowOptions;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author ndana
 */
public class Animations3 implements Runnable {
    private Thread thread;

    public Animations3() {
        thread = new Thread(this);
        thread.setName("Animator thread 3 - Background and Foreground");
        thread.start();
    }

    @Override
    public void run() {
        if (WindowOptions.graphics.equalsIgnoreCase("High")) {
            do {
                try {
                    if (RenderGameplay.getInstance().getVerticalMove().equalsIgnoreCase("no")) {
                        thread.sleep(0016);
                        RenderGameplay.getInstance().setAmb1x(RenderGameplay.getInstance().getAmb1x() - RenderGameplay.getInstance().getAmbSpeed1());
                        RenderGameplay.getInstance().setAmb2x(RenderGameplay.getInstance().getAmb2x() - RenderGameplay.getInstance().getAmbSpeed2());
                        if (RenderGameplay.getInstance().getAmb1x() < -960) {
                            RenderGameplay.getInstance().setAmb1x(852);
                        }
                        if (RenderGameplay.getInstance().getAmb2x() < (-960)) {
                            RenderGameplay.getInstance().setAmb2x(852);
                        }
                    } else {
                        thread.sleep(0016);
                        RenderGameplay.getInstance().setAmb1y(RenderGameplay.getInstance().getAmb1y() + RenderGameplay.getInstance().getAmbSpeed1());
                        RenderGameplay.getInstance().setAmb2y(RenderGameplay.getInstance().getAmb2y() + RenderGameplay.getInstance().getAmbSpeed2());

                        if (RenderGameplay.getInstance().getAmb1y() > 480) {
                            RenderGameplay.getInstance().setAmb1y(-480);
                        }
                        if (RenderGameplay.getInstance().getAmb2y() > 480) {
                            RenderGameplay.getInstance().setAmb2y(-480);
                        }
                    }

                } catch (InterruptedException ex) {
                    Logger.getLogger(DrawOverworld.class.getName()).log(Level.SEVERE, null, ex);
                }
            } while (1 != 0);
        }
    }

    public boolean isRunning() {
        return thread.isAlive();
    }

    public void stop() {
        thread.stop();
    }

    public void pauseThread() {
        thread.suspend();
    }

    public void resumeThread() {
        thread.resume();
    }
}

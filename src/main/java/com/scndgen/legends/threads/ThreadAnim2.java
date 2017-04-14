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
import com.scndgen.legends.windows.WindowOptions;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author ndana
 */
public class ThreadAnim2 implements Runnable {

    private static Thread t;

    public ThreadAnim2() {
        if (t != null) {
            t.resume();
        } else {
            t = new Thread(this);
            t.setPriority(1);
            t.setName("Animator thread 2 - Foreground");
            t.start();
        }
    }

    @Override
    public void run() {
        if (WindowOptions.graphics.equalsIgnoreCase("High")) {
            do {
                //if rotating
                if (StandardGameplay.animDirection.equalsIgnoreCase("rot")) {
                    try {
                        //loop1
                        for (int o = 0; o <= StandardGameplay.animLoops; o++) {
                            StandardGameplay.fgx = StandardGameplay.fgx + StandardGameplay.fgxInc;
                            StandardGameplay.fgy = StandardGameplay.fgy + StandardGameplay.fgyInc;
                            t.sleep(StandardGameplay.delay);
                        }

                        //loop2
                        for (int o = 0; o <= StandardGameplay.animLoops; o++) {
                            StandardGameplay.fgx = StandardGameplay.fgx - StandardGameplay.fgxInc;
                            StandardGameplay.fgy = StandardGameplay.fgy + StandardGameplay.fgyInc;
                            t.sleep(StandardGameplay.delay);
                        }

                        //loop3
                        for (int o = 0; o <= StandardGameplay.animLoops; o++) {
                            StandardGameplay.fgx = StandardGameplay.fgx - StandardGameplay.fgxInc;
                            StandardGameplay.fgy = StandardGameplay.fgy - StandardGameplay.fgyInc;
                            t.sleep(StandardGameplay.delay);
                        }

                        //loop4
                        for (int o = 0; o <= StandardGameplay.animLoops; o++) {
                            StandardGameplay.fgx = StandardGameplay.fgx + StandardGameplay.fgxInc;
                            StandardGameplay.fgy = StandardGameplay.fgy - StandardGameplay.fgyInc;
                            t.sleep(StandardGameplay.delay);
                        }
                    } catch (InterruptedException ex) {
                        Logger.getLogger(StandardGameplay.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } //if NOT rotating
                else {
                    StandardGameplay.fgx = 0;
                    StandardGameplay.fgy = 0;

                    try {
                        for (int o = 0; o <= StandardGameplay.animLoops; o++) {
                            if (StandardGameplay.animDirection.equalsIgnoreCase("both")) {
                                StandardGameplay.fgx = StandardGameplay.fgx + StandardGameplay.fgxInc;
                                StandardGameplay.fgy = StandardGameplay.fgy + StandardGameplay.fgyInc;
                            } else if (StandardGameplay.animDirection.equalsIgnoreCase("horiz")) {
                                StandardGameplay.fgx = StandardGameplay.fgx + StandardGameplay.fgxInc;
                            } else if (StandardGameplay.animDirection.equalsIgnoreCase("vert")) {
                                StandardGameplay.fgy = StandardGameplay.fgy + StandardGameplay.fgyInc;
                            }

                            t.sleep(StandardGameplay.delay);
                        }

                        for (int o = 0; o <= StandardGameplay.animLoops; o++) {
                            if (StandardGameplay.animDirection.equalsIgnoreCase("both")) {
                                StandardGameplay.fgx = StandardGameplay.fgx - StandardGameplay.fgxInc;
                                StandardGameplay.fgy = StandardGameplay.fgy - StandardGameplay.fgyInc;
                            } else if (StandardGameplay.animDirection.equalsIgnoreCase("horiz")) {
                                StandardGameplay.fgx = StandardGameplay.fgx - StandardGameplay.fgxInc;
                            } else if (StandardGameplay.animDirection.equalsIgnoreCase("vert")) {
                                StandardGameplay.fgy = StandardGameplay.fgy - StandardGameplay.fgyInc;
                            }

                            t.sleep(StandardGameplay.delay);
                        }
                    } catch (InterruptedException ex) {
                        Logger.getLogger(StandardGameplay.class.getName()).log(Level.SEVERE, null, ex);
                    }
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

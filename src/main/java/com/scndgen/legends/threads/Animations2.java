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
import com.scndgen.legends.windows.WindowOptions;

/**
 * @author ndana
 */
public class Animations2 implements Runnable {

    private Thread thread;

    public Animations2() {
        if (thread != null) {
            thread.resume();
        } else {
            thread = new Thread(this);
            thread.setName("Animator thread 2 - Foreground");
            thread.start();
        }
    }

    @Override
    public void run() {
        if (WindowOptions.graphics.equalsIgnoreCase("High")) {
            do {
                //if rotating
                if (RenderGameplay.getInstance().animDirection.equalsIgnoreCase("rot")) {
                    try {
                        //loop1
                        for (int o = 0; o <= RenderGameplay.getInstance().animLoops; o++) {
                            RenderGameplay.getInstance().fgx = RenderGameplay.getInstance().fgx + RenderGameplay.getInstance().fgxInc;
                            RenderGameplay.getInstance().fgy = RenderGameplay.getInstance().fgy + RenderGameplay.getInstance().fgyInc;
                            thread.sleep(RenderGameplay.getInstance().delay);
                        }

                        //loop2
                        for (int o = 0; o <= RenderGameplay.getInstance().animLoops; o++) {
                            RenderGameplay.getInstance().fgx = RenderGameplay.getInstance().fgx - RenderGameplay.getInstance().fgxInc;
                            RenderGameplay.getInstance().fgy = RenderGameplay.getInstance().fgy + RenderGameplay.getInstance().fgyInc;
                            thread.sleep(RenderGameplay.getInstance().delay);
                        }

                        //loop3
                        for (int o = 0; o <= RenderGameplay.getInstance().animLoops; o++) {
                            RenderGameplay.getInstance().fgx = RenderGameplay.getInstance().fgx - RenderGameplay.getInstance().fgxInc;
                            RenderGameplay.getInstance().fgy = RenderGameplay.getInstance().fgy - RenderGameplay.getInstance().fgyInc;
                            thread.sleep(RenderGameplay.getInstance().delay);
                        }

                        //loop4
                        for (int o = 0; o <= RenderGameplay.getInstance().animLoops; o++) {
                            RenderGameplay.getInstance().fgx = RenderGameplay.getInstance().fgx + RenderGameplay.getInstance().fgxInc;
                            RenderGameplay.getInstance().fgy = RenderGameplay.getInstance().fgy - RenderGameplay.getInstance().fgyInc;
                            thread.sleep(RenderGameplay.getInstance().delay);
                        }
                    } catch (InterruptedException ex) {
                        ex.printStackTrace(System.err);
                    }
                } //if NOT rotating
                else {
                    RenderGameplay.getInstance().fgx = 0;
                    RenderGameplay.getInstance().fgy = 0;

                    try {
                        for (int o = 0; o <= RenderGameplay.getInstance().animLoops; o++) {
                            if (RenderGameplay.getInstance().animDirection.equalsIgnoreCase("both")) {
                                RenderGameplay.getInstance().fgx = RenderGameplay.getInstance().fgx + RenderGameplay.getInstance().fgxInc;
                                RenderGameplay.getInstance().fgy = RenderGameplay.getInstance().fgy + RenderGameplay.getInstance().fgyInc;
                            } else if (RenderGameplay.getInstance().animDirection.equalsIgnoreCase("horiz")) {
                                RenderGameplay.getInstance().fgx = RenderGameplay.getInstance().fgx + RenderGameplay.getInstance().fgxInc;
                            } else if (RenderGameplay.getInstance().animDirection.equalsIgnoreCase("vert")) {
                                RenderGameplay.getInstance().fgy = RenderGameplay.getInstance().fgy + RenderGameplay.getInstance().fgyInc;
                            }

                            thread.sleep(RenderGameplay.getInstance().delay);
                        }

                        for (int o = 0; o <= RenderGameplay.getInstance().animLoops; o++) {
                            if (RenderGameplay.getInstance().animDirection.equalsIgnoreCase("both")) {
                                RenderGameplay.getInstance().fgx = RenderGameplay.getInstance().fgx - RenderGameplay.getInstance().fgxInc;
                                RenderGameplay.getInstance().fgy = RenderGameplay.getInstance().fgy - RenderGameplay.getInstance().fgyInc;
                            } else if (RenderGameplay.getInstance().animDirection.equalsIgnoreCase("horiz")) {
                                RenderGameplay.getInstance().fgx = RenderGameplay.getInstance().fgx - RenderGameplay.getInstance().fgxInc;
                            } else if (RenderGameplay.getInstance().animDirection.equalsIgnoreCase("vert")) {
                                RenderGameplay.getInstance().fgy = RenderGameplay.getInstance().fgy - RenderGameplay.getInstance().fgyInc;
                            }

                            thread.sleep(RenderGameplay.getInstance().delay);
                        }
                    } catch (InterruptedException ex) {
                        ex.printStackTrace(System.err);
                    }
                }
            } while (1 != 0);
        }
    }

    public void stop() {
        thread = null;
        //thread.destroy();
    }

    public void pauseThread() {
        thread.suspend();
    }

    public void resumeThread() {
        thread.resume();
    }
}
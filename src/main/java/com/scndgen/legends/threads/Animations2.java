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

/**
 * @author ndana
 */
public class Animations2 implements Runnable {

    private Thread thread;

    public Animations2() {
        thread = new Thread(this);
        thread.setName("Animator thread 2 - Foreground");
        thread.start();
    }

    @Override
    public void run() {
        do {
            //if rotating
            switch (RenderGameplay.getInstance().getAnimationDirection()) {
                case ROTATION:
                    try {
                        //loop1
                        for (int o = 0; o <= RenderGameplay.getInstance().getAnimationLoops(); o++) {
                            RenderGameplay.getInstance().setForeGroundPositionX(RenderGameplay.getInstance().getForeGroundPositionX() + RenderGameplay.getInstance().getForeGroundXIncrement());
                            RenderGameplay.getInstance().setForeGroundPositionY(RenderGameplay.getInstance().getForeGroundPositionY() + RenderGameplay.getInstance().getForeGroundYIncrement());
                            thread.sleep(RenderGameplay.getInstance().getDelay());
                        }
                        //loop2
                        for (int o = 0; o <= RenderGameplay.getInstance().getAnimationLoops(); o++) {
                            RenderGameplay.getInstance().setForeGroundPositionX(RenderGameplay.getInstance().getForeGroundPositionX() - RenderGameplay.getInstance().getForeGroundXIncrement());
                            RenderGameplay.getInstance().setForeGroundPositionY(RenderGameplay.getInstance().getForeGroundPositionY() + RenderGameplay.getInstance().getForeGroundYIncrement());
                            thread.sleep(RenderGameplay.getInstance().getDelay());
                        }
                        //loop3
                        for (int o = 0; o <= RenderGameplay.getInstance().getAnimationLoops(); o++) {
                            RenderGameplay.getInstance().setForeGroundPositionX(RenderGameplay.getInstance().getForeGroundPositionX() - RenderGameplay.getInstance().getForeGroundXIncrement());
                            RenderGameplay.getInstance().setForeGroundPositionY(RenderGameplay.getInstance().getForeGroundPositionY() - RenderGameplay.getInstance().getForeGroundYIncrement());
                            thread.sleep(RenderGameplay.getInstance().getDelay());
                        }
                        //loop4
                        for (int o = 0; o <= RenderGameplay.getInstance().getAnimationLoops(); o++) {
                            RenderGameplay.getInstance().setForeGroundPositionX(RenderGameplay.getInstance().getForeGroundPositionX() + RenderGameplay.getInstance().getForeGroundXIncrement());
                            RenderGameplay.getInstance().setForeGroundPositionY(RenderGameplay.getInstance().getForeGroundPositionY() - RenderGameplay.getInstance().getForeGroundYIncrement());
                            thread.sleep(RenderGameplay.getInstance().getDelay());
                        }
                    } catch (InterruptedException ex) {
                        ex.printStackTrace(System.err);
                    }
                    break;
                default:
                    RenderGameplay.getInstance().setForeGroundPositionX(0);
                    RenderGameplay.getInstance().setForeGroundPositionY(0);
                    try {
                        for (int loop = 0; loop <= RenderGameplay.getInstance().getAnimationLoops(); loop++) {
                            switch (RenderGameplay.getInstance().getAnimationDirection()) {
                                case VERTICAL_HORIZONTAL:
                                    RenderGameplay.getInstance().setForeGroundPositionX(RenderGameplay.getInstance().getForeGroundPositionX() + RenderGameplay.getInstance().getForeGroundXIncrement());
                                    RenderGameplay.getInstance().setForeGroundPositionY(RenderGameplay.getInstance().getForeGroundPositionY() + RenderGameplay.getInstance().getForeGroundYIncrement());
                                    break;
                                case HORIZONTAL:
                                    RenderGameplay.getInstance().setForeGroundPositionX(RenderGameplay.getInstance().getForeGroundPositionX() + RenderGameplay.getInstance().getForeGroundXIncrement());
                                    break;
                                case VERTICAL:
                                    RenderGameplay.getInstance().setForeGroundPositionY(RenderGameplay.getInstance().getForeGroundPositionY() + RenderGameplay.getInstance().getForeGroundYIncrement());
                                    break;
                            }
                            thread.sleep(RenderGameplay.getInstance().getDelay());
                        }
                        for (int loop = 0; loop <= RenderGameplay.getInstance().getAnimationLoops(); loop++) {
                            switch (RenderGameplay.getInstance().getAnimationDirection()) {
                                case VERTICAL_HORIZONTAL:
                                    RenderGameplay.getInstance().setForeGroundPositionX(RenderGameplay.getInstance().getForeGroundPositionX() - RenderGameplay.getInstance().getForeGroundXIncrement());
                                    RenderGameplay.getInstance().setForeGroundPositionY(RenderGameplay.getInstance().getForeGroundPositionY() - RenderGameplay.getInstance().getForeGroundYIncrement());
                                    break;
                                case HORIZONTAL:
                                    RenderGameplay.getInstance().setForeGroundPositionX(RenderGameplay.getInstance().getForeGroundPositionX() - RenderGameplay.getInstance().getForeGroundXIncrement());
                                    break;
                                case VERTICAL:
                                    RenderGameplay.getInstance().setForeGroundPositionY(RenderGameplay.getInstance().getForeGroundPositionY() - RenderGameplay.getInstance().getForeGroundYIncrement());
                                    break;
                            }
                            thread.sleep(RenderGameplay.getInstance().getDelay());
                        }
                    } catch (InterruptedException ex) {
                        ex.printStackTrace(System.err);
                    }
                    break;
            }
        } while (1 != 0);
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

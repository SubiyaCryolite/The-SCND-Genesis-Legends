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
import com.scndgen.legends.arefactored.render.RenderStandardGameplay;
import com.scndgen.legends.windows.WindowOptions;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author ndana
 */
public class ThreadAnim1 implements Runnable {

    private Thread thread;
    private int factor;

    public ThreadAnim1() {
        if (thread != null) {
            thread.resume();
        } else  {
            thread = new Thread(this);
            thread.setName("Animator thread 1 - Character");
            thread.start();
        }
        factor = 30 - (8 + (WindowOptions.whichOne() * 2));
        System.out.println("Fury factor: " + factor);
    }

    @Override
    @SuppressWarnings("static-access")
    public void run() {
        do {
            try {
                if (RenderStandardGameplay.getInstance().getBreak() > 5 && RenderStandardGameplay.getInstance().getBreak() < 999) {
                    RenderStandardGameplay.getInstance().setBreak(-factor);
                }

                for (int o = 0; o <= 10; o++) {
                    RenderStandardGameplay.getInstance().charYcord = RenderStandardGameplay.getInstance().charYcord + 1;
                    RenderStandardGameplay.getInstance().oppYcord = RenderStandardGameplay.getInstance().oppYcord + 1;
                    thread.sleep(66);
                }

                for (int o = 0; o <= 10; o++) {
                    RenderStandardGameplay.getInstance().charYcord = RenderStandardGameplay.getInstance().charYcord - 1;
                    RenderStandardGameplay.getInstance().oppYcord = RenderStandardGameplay.getInstance().oppYcord - 1;
                    thread.sleep(66);
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(StandardGameplay.class.getName()).log(Level.SEVERE, null, ex);
            }
        } while (1 != 0);
    }

    public void stop() {
        thread = null;
    }

    public void pauseThread() {
        thread.suspend();
    }

    public void resumeThread() {
        thread.resume();
    }
}

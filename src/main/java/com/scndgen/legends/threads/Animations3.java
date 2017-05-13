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

import com.scndgen.legends.enums.AnimationDirection;
import com.scndgen.legends.render.RenderGamePlay;

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
        do {
            try {
                if (RenderGamePlay.getInstance().getAnimationDirection()!= AnimationDirection.VERTICAL) {
                    thread.sleep(0016);
                    RenderGamePlay.getInstance().setParticlesLayer1PositionX(RenderGamePlay.getInstance().getParticlesLayer1PositionX() - RenderGamePlay.getInstance().getAmbSpeed1());
                    RenderGamePlay.getInstance().setParticlesLayer2PositionX(RenderGamePlay.getInstance().getParticlesLayer2PositionX() - RenderGamePlay.getInstance().getAmbSpeed2());
                    if (RenderGamePlay.getInstance().getParticlesLayer1PositionX() < -960) {
                        RenderGamePlay.getInstance().setParticlesLayer1PositionX(852);
                    }
                    if (RenderGamePlay.getInstance().getParticlesLayer2PositionX() < (-960)) {
                        RenderGamePlay.getInstance().setParticlesLayer2PositionX(852);
                    }
                } else {
                    thread.sleep(0016);
                    RenderGamePlay.getInstance().setParticlesLayer1PositionY(RenderGamePlay.getInstance().getParticlesLayer1PositionY() + RenderGamePlay.getInstance().getAmbSpeed1());
                    RenderGamePlay.getInstance().setParticlesLayer2PositionY(RenderGamePlay.getInstance().getParticlesLayer2PositionY() + RenderGamePlay.getInstance().getAmbSpeed2());
                    if (RenderGamePlay.getInstance().getParticlesLayer1PositionY() > 480) {
                        RenderGamePlay.getInstance().setParticlesLayer1PositionY(-480);
                    }
                    if (RenderGamePlay.getInstance().getParticlesLayer2PositionY() > 480) {
                        RenderGamePlay.getInstance().setParticlesLayer2PositionY(-480);
                    }
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace(System.err);
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

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

import com.scndgen.legends.render.RenderGamePlay;
import com.scndgen.legends.mode.GamePlay;
import com.scndgen.legends.state.GameState;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author ndana
 */
public class Animations1 implements Runnable {

    private Thread thread;
    private int factor;

    public Animations1() {
        thread = new Thread(this);
        thread.setName("Animator thread 1 - CharacterEnum");
        thread.start();
        factor = 30 - (8 + (GameState.getInstance().getLogin().resolveDifficulty() * 2));
        System.out.println("Fury factor: " + factor);
    }

    @Override
    @SuppressWarnings("static-access")
    public void run() {
        do {
            try {
                if (RenderGamePlay.getInstance().getBreak() > 5 && RenderGamePlay.getInstance().getBreak() < 999) {
                    RenderGamePlay.getInstance().setBreak(-factor);
                }
                for (int o = 0; o <= 10; o++) {
                    RenderGamePlay.getInstance().setCharYcord(RenderGamePlay.getInstance().getCharYcord() + 1);
                    RenderGamePlay.getInstance().setOppYcord(RenderGamePlay.getInstance().getOppYcord() + 1);
                    thread.sleep(66);
                }
                for (int o = 0; o <= 10; o++) {
                    RenderGamePlay.getInstance().setCharYcord(RenderGamePlay.getInstance().getCharYcord() - 1);
                    RenderGamePlay.getInstance().setOppYcord(RenderGamePlay.getInstance().getOppYcord() - 1);
                    thread.sleep(66);
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(GamePlay.class.getName()).log(Level.SEVERE, null, ex);
            }
        } while (true);
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

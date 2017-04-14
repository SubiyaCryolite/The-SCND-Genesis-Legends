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
package com.scndgen.legends.executers;

import com.scndgen.legends.LoginScreen;
import com.scndgen.legends.menus.CharacterSelectionScreen;
import com.scndgen.legends.threads.ThreadGameInstance;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ExecuterMovesOpp implements Runnable {

    public int taskRun = 0, range;
    public char feeCol;
    public boolean isRunning = false;
    private Thread timer;
    private int[] aiMoves;

    public ExecuterMovesOpp() {
        timer = new Thread(this);
        timer.setName("Opponent attacking thread");
    }

    public void attack() {
        if (timer.isAlive()) {
            timer.resume();
        } else {
            timer.start();
        }
    }

    @Override
    public void run() {
        do {
            try {
                Thread.sleep(LoginScreen.getInstance().difficultyDyn);
            } catch (InterruptedException ex) {
                Logger.getLogger(ExecuterMovesOpp.class.getName()).log(Level.SEVERE, null, ex);
            }

            executingTheCommandsAI();

            LoginScreen.getInstance().getMenu().getMain().getGame().getGameInstance().setRecoveryUnitsOpp(0);
            LoginScreen.getInstance().getMenu().getMain().getGame().getGameInstance().aiRunning = false;

            timer.suspend();
        } while (1 != 0);
    }

    private void executingTheCommandsAI() {
        aiMoves = CharacterSelectionScreen.getAISlot();
        range = aiMoves.length - 1;

        if (ThreadGameInstance.isGameOver == false) {
            for (int o = 0; o < ((LoginScreen.difficultyBase - LoginScreen.getInstance().difficultyDyn) / LoginScreen.difficultyScale); o++) {
                //fix story mode bug
                if (ThreadGameInstance.story == false && ThreadGameInstance.isGameOver == false) {
                    LoginScreen.getInstance().getMenu().getMain().getAttacksChar().CharacterOverlayDisabled();
                    LoginScreen.getInstance().getMenu().getMain().getAttacksOpp().attack(aiMoves[Integer.parseInt("" + Math.round(Math.random() * range))], 1, 'o', 'c');
                    LoginScreen.getInstance().getMenu().getMain().getGame().shakeCharLB();
                    LoginScreen.getInstance().getMenu().getMain().getGame().AnimatePhyAttax('o');
                    LoginScreen.getInstance().getMenu().getMain().getAttacksChar().CharacterOverlayEnabled();
                }
            }
        }
    }

    public void pause() {
        timer.suspend();
    }

    public void resume() {
        timer.resume();
    }
}

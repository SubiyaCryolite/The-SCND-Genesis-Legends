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
package com.scnd_genesis.executers;

import com.scnd_genesis.LoginScreen;
import com.scnd_genesis.menus.MenuCharSelect;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ExecuterMovesChar2 implements Runnable {

    public static int taskComplete;
    public static int taskRun = 0, range;
    public static char feeCol;
    public static boolean isRunning = false;
    private static Thread timer;
    private int[] aiMoves;
    private int whoToAttack = 4;

    public ExecuterMovesChar2() {
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
                int time = LoginScreen.getLoginScreen().difficultyDyn;
                Thread.sleep((int) (time + (Math.random() * time)));
            } catch (InterruptedException ex) {
                Logger.getLogger(ExecuterMovesChar2.class.getName()).log(Level.SEVERE, null, ex);
            }

            executingTheCommandsAI();

            LoginScreen.getLoginScreen().getMenu().getMain().getGame().getGameInstance().setRecoveryUnitsChar2(0);
            LoginScreen.getLoginScreen().getMenu().getMain().getGame().getGameInstance().aiRunning3 = false;

            timer.suspend();
        } while (1 != 0);
    }

    private void executingTheCommandsAI() {
        aiMoves = MenuCharSelect.getAISlot3();
        range = aiMoves.length - 1;

        if (LoginScreen.getLoginScreen().getMenu().getMain().getGame().getGameInstance().isGameOver == false) {

            int randomNumber = (int) (Math.random() * 12);
            if (randomNumber <= 6) {
                if (LoginScreen.getLoginScreen().getMenu().getMain().getGame().perCent2a >= 0) {
                    whoToAttack = 4;
                } // normally CPU player 1 attacks CPU opponent 2
                else {
                    whoToAttack = 2;
                }
            } else if (randomNumber >= 7) {
                if (LoginScreen.getLoginScreen().getMenu().getMain().getGame().perCent2 >= 0) {
                    whoToAttack = 2;
                } //attack CPU opponent 1
                else {
                    whoToAttack = 4;
                }
            }


            for (int o = 0; o < 4; o++) {
                LoginScreen.getLoginScreen().getMenu().getMain().getAttacksChar().CharacterOverlayEnabled();
                //fix story mode bug
                if (LoginScreen.getLoginScreen().getMenu().getMain().getGame().getGameInstance().story == false) {
                    LoginScreen.getLoginScreen().getMenu().getMain().getAttacksOpp2().attack(aiMoves[Integer.parseInt("" + Math.round(Math.random() * range))], whoToAttack, 'a', 'b');
                    LoginScreen.getLoginScreen().getMenu().getMain().getGame().shakeCharLB();
                    LoginScreen.getLoginScreen().getMenu().getMain().getGame().AnimatePhyAttax('a');
                }
                LoginScreen.getLoginScreen().getMenu().getMain().getAttacksChar().CharacterOverlayDisabled();
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

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
import com.scndgen.legends.windows.WindowMain;

public class ExecuterMovesChar implements Runnable {

    public static int taskComplete;
    public static int taskRun = 0;
    public static boolean isRunning = false;
    private static Thread timer;

    public ExecuterMovesChar() {
        timer = new Thread(this);
        timer.setName("Player attacking thread");
        timer.start();
    }

    @Override
    public void run() {
        if (LoginScreen.getInstance().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.singlePlayer2)) {
            //LoginScreen.getInstance().getMenu().getMain().getGame().setSprites('a',9,11);
            //LoginScreen.getInstance().getMenu().getMain().getGame().setSprites('b',9,11);
        }
        //LoginScreen.getInstance().getMenu().getMain().getGame().DisableMenus(); disable issueing of more attacksCombatMage during execution
        // each Mattack will check if they are in the battle que.... if they are they execute
        LoginScreen.getInstance().getMenu().getMain().getGame().getGameInstance().pauseActivityRegen();
        LoginScreen.getInstance().getMenu().getMain().getGame().getGameInstance().setRecoveryUnitsChar(0);

        if (LoginScreen.getInstance().getMenu().getMain().getGame().getGameInstance().isGameOver == false) {
            for (int o = 0; o < 4; o++) {
                LoginScreen.getInstance().getMenu().getMain().getAttacksChar().CharacterOverlayEnabled();
                LoginScreen.getInstance().getMenu().getMain().getAttacksChar().attack(Integer.parseInt(LoginScreen.getInstance().getMenu().getMain().getGame().attackArray[o]), 2, 'c', 'o');
                LoginScreen.getInstance().getMenu().getMain().getGame().shakeOppCharLB();
                LoginScreen.getInstance().getMenu().getMain().getGame().AnimatePhyAttax('c');
                if ((o + 1) == LoginScreen.getInstance().getMenu().getMain().getGame().numOfAttacks) {
                    break;
                }
                LoginScreen.getInstance().getMenu().getMain().getAttacksChar().CharacterOverlayDisabled();
            }
        }

        if (LoginScreen.getInstance().getMenu().getMain().getGame().done != 1)// if game still running enable menus
        {
            LoginScreen.getInstance().getMenu().getMain().getGame().getGameInstance().resumeActivityRegen();
        }

        LoginScreen.getInstance().getMenu().getMain().getGame().comboCounter = 0;
        LoginScreen.getInstance().getMenu().getMain().getGame().numOfAttacks = 0;
    }

    public void pause() {
        timer.suspend();
    }

    public void resume() {
        timer.resume();
    }
}

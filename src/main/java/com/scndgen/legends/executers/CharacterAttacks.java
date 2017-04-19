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

import com.scndgen.legends.enums.CharacterState;
import com.scndgen.legends.render.RenderGameplay;
import com.scndgen.legends.threads.GameInstance;
import com.scndgen.legends.windows.MainWindow;

public class CharacterAttacks implements Runnable {

    private Thread timer;

    public CharacterAttacks() {
        timer = new Thread(this);
        timer.setName("Player attacking thread");
        timer.start();
    }

    @Override
    public void run() {
        //RenderGameplay.getInstance().DisableMenus(); disable issueing of more attacksCombatMage during execution
        //each attack will check if they are in the battle que.... if they are they execute
        GameInstance.getInstance().pauseActivityRegen();
        GameInstance.getInstance().setRecoveryUnitsChar(0);
        if (GameInstance.getInstance().isGameOver == false) {
            for (int o = 0; o < 4; o++) {
                MainWindow.getInstance().getAttacksChar().CharacterOverlayEnabled();
                MainWindow.getInstance().getAttacksChar().attack(Integer.parseInt(RenderGameplay.getInstance().getAttackArray()[o]), CharacterState.CHARACTER, CharacterState.OPPONENT);
                RenderGameplay.getInstance().shakeOppCharLB();
                RenderGameplay.getInstance().AnimatePhyAttax(CharacterState.CHARACTER);
                if ((o + 1) == RenderGameplay.getInstance().getNumOfAttacks()) {
                    break;
                }
                MainWindow.getInstance().getAttacksChar().CharacterOverlayDisabled();
            }
        }
        if (RenderGameplay.getInstance().getDone() != 1)// if game still running enable menus
        {
            GameInstance.getInstance().resumeActivityRegen();
        }
        RenderGameplay.getInstance().setComboCounter(0);
        RenderGameplay.getInstance().setNumOfAttacks(0);
    }

    public void pause() {
        timer.suspend();
    }

    public void resume() {
        timer.resume();
    }
}

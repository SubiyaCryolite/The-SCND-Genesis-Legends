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

public class CharacterAttacksOnline implements Runnable {

    private Thread timer;
    private int command1, command2, command3, command4;
    private char executionType;

    public CharacterAttacksOnline(int command1, int command2, int command3, int command4, char executionType) {
        this.executionType = executionType;
        this.command1 = command1;
        this.command2 = command2;
        this.command3 = command3;
        this.command4 = command4;
        timer = new Thread(this);
        timer.start();
    }

    public void pause() {
        timer.suspend();
    }

    public void resume() {
        timer.resume();
    }

    public void run() {
        //normal attack
        if (executionType == 'n') {
            RenderGameplay.getInstance().setComboCounter(0);
            //clear active combos
            RenderGameplay.getInstance().setSprites(CharacterState.CHARACTER, 9, 11);
            RenderGameplay.getInstance().setSprites(CharacterState.OPPONENT, 9, 11);
            //RenderGameplay.getInstance().DisableMenus(); disable issueing of more attacksCombatMage during execution
            // each Mattack will check if they are in the battle que.... if they are they execute
            executingTheCommands();
            GameInstance.getInstance().setRecoveryUnitsChar(0);
        } else if (executionType == 'l') {//limit break
            RenderGameplay.getInstance().clash(1, CharacterState.CHARACTER);
        }
    }

    private void executingTheCommands() {
        int[] action = {command1, command2, command3, command4};
        for (int index = 0; index < action.length; index++) {
            MainWindow.getInstance().getAttacksChar().CharacterOverlayEnabled();
            MainWindow.getInstance().getAttacksChar().attack(action[index], CharacterState.CHARACTER, CharacterState.OPPONENT);
            RenderGameplay.getInstance().shakeOppCharLB();
            RenderGameplay.getInstance().AnimatePhyAttax(CharacterState.CHARACTER);
            MainWindow.getInstance().getAttacksChar().CharacterOverlayDisabled();
        }
    }
}

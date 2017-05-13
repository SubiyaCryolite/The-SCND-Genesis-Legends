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
import com.scndgen.legends.render.RenderCharacterSelectionScreen;
import com.scndgen.legends.render.RenderGameplay;
import com.scndgen.legends.state.GameState;
import com.scndgen.legends.threads.GameInstance;

public class OpponentAttacks implements Runnable {

    public int attackRange;
    private Thread timer;
    private int[] aiQueuedAttacks;

    public OpponentAttacks() {

    }

    public void attack() {
        timer = new Thread(this);
        timer.setName("Opponent attacking thread");
        timer.start();
    }

    @Override
    public void run() {
        do {
            try {
                Thread.sleep(GameState.getInstance().getLogin().getDifficultyDynamic());
            } catch (InterruptedException ex) {
                ex.printStackTrace(System.err);
            }
            executingTheCommandsAI();
            GameInstance.getInstance().setRecoveryUnitsOpp(0);
            GameInstance.getInstance().enemyAiRunning = false;
            timer.suspend();
        } while (1 != 0);
    }

    private void executingTheCommandsAI() {
        aiQueuedAttacks = RenderCharacterSelectionScreen.getInstance().getAISlot();
        attackRange = aiQueuedAttacks.length - 1;
        if (GameInstance.getInstance().gameOver == false) {
            for (int o = 0; o < ((GameState.DIFFICULTY_BASE - GameState.getInstance().getLogin().getDifficultyDynamic()) / GameState.DIFFICULTY_SCALE); o++) {
                //fix story scene bug
                if (GameInstance.getInstance().storySequence == false && GameInstance.getInstance().gameOver == false) {
                    RenderGameplay.getInstance().getAttackOpponent().setAttackSpritesAndTrigger(aiQueuedAttacks[Math.round(Math.round(Math.random() * attackRange))], CharacterState.OPPONENT, CharacterState.CHARACTER, RenderGameplay.getInstance());
                    RenderGameplay.getInstance().shakeCharacterLifeBar();
                    RenderGameplay.getInstance().revertToDefaultSprites(CharacterState.OPPONENT);
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

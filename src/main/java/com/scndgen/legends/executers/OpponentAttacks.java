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

import com.scndgen.legends.characters.Characters;
import com.scndgen.legends.enums.CharacterState;
import com.scndgen.legends.render.RenderGameplay;
import com.scndgen.legends.state.GameState;
import com.scndgen.legends.threads.GameInstance;

import java.util.LinkedList;

public class OpponentAttacks implements Runnable {

    public int attackRange;
    private Thread timer;
    private final LinkedList<Integer> aiQueuedAttacks = new LinkedList<>();

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
            GameInstance.getInstance().setOpponentAtbValue(0);
            GameInstance.getInstance().enemyAiRunning = false;
            timer.suspend();
        } while (1 != 0);
    }

    private void executingTheCommandsAI() {
        aiQueuedAttacks.clear();
        for (int i : RenderGameplay.getInstance().updateAndGetOpponentAttackQue())
            aiQueuedAttacks.add(i);
        attackRange = aiQueuedAttacks.size();
        /////////////////////////////////////////////////////////////
        if (GameInstance.getInstance().gameOver == false) {
            int computedDifficulty = (GameState.DIFFICULTY_BASE - GameState.getInstance().getLogin().getDifficultyDynamic()) / GameState.DIFFICULTY_SCALE;
            for (int aiTimeout = 0; aiTimeout < computedDifficulty; aiTimeout++) {
                if (GameInstance.getInstance().storySequence == false && GameInstance.getInstance().gameOver == false) {
                    int randomAttack = aiQueuedAttacks.get(Math.round(Math.round(Math.random() * attackRange)));
                    RenderGameplay.getInstance().setAttackSpritesAndTrigger(randomAttack, CharacterState.OPPONENT, CharacterState.CHARACTER, RenderGameplay.getInstance(), Characters.getInstance().getCharacter());
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

/**************************************************************************

 The SCND Genesis: Legends is a fighting game based on THE SCND GENESIS,
 a webcomic created by Ifunga Ndana ((([<a href="https://www.scndgen.com">https://www.scndgen.com</a>]))).

 The SCND Genesis: Legends RMX  © 2017 Ifunga Ndana.

 The SCND Genesis: Legends is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 The SCND Genesis: Legends is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with The SCND Genesis: Legends. If not, see <<a href="http://www.gnu.org/licenses/">http://www.gnu.org/licenses/</a>>.

 **************************************************************************/
package com.scndgen.legends.mode;

import com.scndgen.legends.Language;
import com.scndgen.legends.constants.AudioConstants;
import com.scndgen.legends.enums.AudioType;
import com.scndgen.legends.render.RenderGamePlay;
import com.scndgen.legends.state.State;
import io.github.subiyacryolite.enginev1.Audio;
import io.github.subiyacryolite.enginev1.Mode;

import javax.swing.*;

public abstract class StoryMenu extends Mode {

    protected int charYcap = 0, charXcap = 0, row = 1, x = 0, y = 0, column = 0, vSpacer = 52, hSpacer = 92, commonXCoord = 299, commonYCoord = 105;
    protected final int columns = 3;
    protected final int numberOfScenes = StoryMode.get().totalScenes;
    protected final int rows = numberOfScenes / columns;
    protected int oldId = -1;
    protected boolean[] unlockedStage;
    protected int currentScene = State.get().getLogin().getHighestStoryScene();

    public void newInstance() {
        loadAssets = true;
        StoryMode.get().newInstance();
        opacity = 1.0f;
    }

    /**
     * Animates captions
     */
    public void animateCaption() {
        opacity = 0.0f;
    }

    /**
     * Find out the lastStoryScene the player is on
     */
    public void resetCurrentStage() {
        currentScene = State.get().getLogin().getHighestStoryScene();
    }

    /**
     * When in playStory currentScene, we go to the current level
     *
     * @return current level
     */
    public int getStage() {
        return currentScene;
    }

    protected void showstoryName(int id) {
        if (id != oldId) {
            primaryNotice("Scene " + (id + 1));
            oldId = id;
        }
    }

    /**
     * Are there more stages?????
     *
     * @return
     */
    public boolean moreStages() {
        boolean answer = false;
        resetCurrentStage();

        //check if more stages
        if (currentScene < StoryMode.get().totalScenes) {
            answer = true;
        } //if won last 'final' match
        else if (RenderGamePlay.get().hasWon()) {
            //incrementMode();
            //go onBackCancel to user difficulty
            State.get().getLogin().setDifficultyDynamic(State.get().getLogin().getDifficulty());
            Audio victorySound = new Audio(AudioConstants.soundGameOver(), AudioType.MUSIC, false);
            victorySound.play();
            JOptionPane.showMessageDialog(null, Language.get().get(115), "Sweetness!!!", JOptionPane.INFORMATION_MESSAGE);
            answer = false;
        }
        return answer;
    }

    protected boolean validIndex(int hoveredStoryIndex) {
        return unlockedStage[hoveredStoryIndex];
    }
}

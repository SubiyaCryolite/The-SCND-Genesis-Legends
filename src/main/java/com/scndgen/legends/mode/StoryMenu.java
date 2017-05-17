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
package com.scndgen.legends.mode;

import com.scndgen.legends.Language;
import com.scndgen.legends.ScndGenLegends;
import com.scndgen.legends.controller.StoryMode;
import com.scndgen.legends.enums.ModeEnum;
import com.scndgen.legends.render.RenderCharacterSelection;
import com.scndgen.legends.render.RenderGamePlay;
import com.scndgen.legends.state.GameState;
import io.github.subiyacryolite.enginev1.AudioPlayback;
import io.github.subiyacryolite.enginev1.Mode;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import javax.swing.*;

public abstract class StoryMenu extends Mode {

    protected int charYcap = 0, charXcap = 0, hoveredStoryIndex = 99, row = 1, x = 0, y = 0, column = 0, vSpacer = 52, hSpacer = 92, commonXCoord = 299, commonYCoord = 105;
    protected final int columns = 3;
    protected final int numberOfScenes = StoryMode.getInstance().max;
    protected final int rows = numberOfScenes / columns;
    protected final int rowsCiel = Math.round(Math.round(Math.ceil(numberOfScenes / (double) columns)));
    protected final int columnsCiel = numberOfScenes % columns;
    protected int oldId = -1;
    protected boolean[] unlockedStage;
    protected boolean loadingNow;
    protected int currentScene = GameState.getInstance().getLogin().getLastStoryScene();
    protected int storedX = 99, storedY = 99;
    protected AudioPlayback victorySound;
    protected AudioPlayback menuSound;

    /**
     * When both playes are selected, this prevents movement.
     *
     * @return false if both CharacterEnum have been selected, true if only one is selected
     */
    public static boolean bothArentSelected() {
        boolean answer = true;

        if (RenderCharacterSelection.getInstance().selectedCharacter && RenderCharacterSelection.getInstance().selectedOpponent) {
            answer = false;
        }

        return answer;
    }

    public void newInstance() {
        loadAssets = true;
        StoryMode.getInstance().newInstance();
        opacity = 1.0f;
    }


    /**
     * Animates captions
     */
    public void capAnim() {
        opacity = 0.0f;
    }

    /**
     * Move up
     */
    public void onUp() {
        if (row > 0)
            row -= 1;
        else {
            int upperLimit = column < columnsCiel ? rowsCiel - 1 : rows - 1;
            row = upperLimit;
        }
        capAnim();
    }

    /**
     * Move down
     */
    public void onDown() {
        int limit = column < columnsCiel ? rowsCiel - 1 : rows - 1;
        if (row < limit)
            row++;
        else
            row = 0;
        capAnim();
    }

    /**
     * Move right
     */
    public void onRight() {
        int limit = (row < rowsCiel) ? columns - 1 : columnsCiel - 1;
        if (column < limit)
            column ++;
        else
            column = 0;
        capAnim();
    }

    /**
     * Move left
     */
    public void onLeft() {
        int limit = (row < rowsCiel-1) ? columns - 1 : columnsCiel - 1;
        if (column > 0)
            column -= 1;
        else
            column = limit;
        capAnim();
    }
    /**
     * Gets the number of columns in the characterEnum select screen
     *
     * @return number of columns
     */
    public int getNumberOfCharColumns() {
        return columns;
    }

    /**
     * Gets the char caption spacer
     *
     * @return spacer
     */
    public int getCharHSpacer() {
        return vSpacer;
    }

    /**
     * Gets the char caption spacer
     *
     * @return spacer
     */
    public int getCharVSpacer() {
        return hSpacer;
    }

    /**
     * Get starting x coordinate
     *
     * @return starting x coordinate
     */
    public int getStartX() {
        return commonXCoord;
    }

    /**
     * Returns the starting Y coordinate
     *
     * @return starting y
     */
    public int getStartY() {
        return commonYCoord;
    }

    /**
     * Get number of char rows
     *
     * @return number of rows
     */
    public int getCharRows() {
        return rows;
    }


    /**
     * Animates caption
     */
    public void animateCaption() {
        capAnim();
    }

    public void onBackCancel() {
        ScndGenLegends.getInstance().loadMode(ModeEnum.MAIN_MENU);
    }

    /**
     * Find out the lastStoryScene the player is on
     */
    public void resetCurrentStage() {
        currentScene = GameState.getInstance().getLogin().getLastStoryScene();
    }

    /**
     * When in story currentScene, we go to the current level
     *
     * @return current level
     */
    public int getStage() {
        return currentScene;
    }


    /**
     * Get the storyboard size for the characterEnum
     *
     * @return the number of levels
     */
    public int getStorySize() {
        return StoryMode.getInstance().max;
    }


    /**
     * Sets the story
     *
     * @param where
     */
    public void setstory(int where) {
        hoveredStoryIndex = where;
    }

    /**
     * Checks if within number of CharacterEnum
     */
    public boolean isUnlocked() {
        boolean ans = false;
        int computedPosition = (row * columns) + column;
        if (computedPosition < numberOfScenes) {
            ans = true;
            hoveredStoryIndex = computedPosition;
        }
        return ans;
    }

    /**
     * Horizontal index
     *
     * @return column
     */
    public final int getHindex() {
        return row;
    }

    /**
     * Set horizontal index
     */
    public final void setHindex(int value) {
        row = value;
    }

    /**
     * Vertical index
     *
     * @return row
     */
    public final int getVindex() {
        return column;
    }

    /**
     * Set vertical index
     */
    public final void setVindex(int value) {
        column = value;
    }

    protected void showstoryName(int id) {
        if (id != oldId) {
            primaryNotice("Scene " + (id + 1));
            oldId = id;
        }
    }

    public void mouseMoved(MouseEvent me) {
        if (ScndGenLegends.getInstance().getModeEnum() == ModeEnum.STORY_SELECT_SCREEN) {
            int topY = getStartY();
            int topX = getStartX();
            int columns = getNumberOfCharColumns();
            int vspacer = getCharHSpacer();
            int hspacer = getCharVSpacer();
            int rows = getCharRows();
            if (me.getX() > topX && me.getX() < (topX + (hspacer * columns)) && (me.getY() > topY) && (me.getY() < topY + (vspacer * rows))) {
                int vIndex = Math.round(Math.round((me.getY() - topY) / vspacer));
                int hIndex = Math.round(Math.round((me.getX() - topX) / hspacer));
                setHindex(hIndex);
                setVindex(vIndex);
                animateCap2x(hIndex, vIndex);
            } else {
                setHindex(99);
                setVindex(99);
            }
        }
    }


    /**
     * To make sure the caption is animated once,
     * this method checks if the selected caption has changed
     *
     * @param x
     * @param y
     */
    public void animateCap2x(int x, int y) {
        int tmpx = x;
        int tmpy = y;

        if (tmpx == storedX && tmpy == storedY) //same vals, do nothing
        {
        } else {
            storedX = tmpx;
            storedY = tmpy;
            animateCaption();
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
        if (currentScene < StoryMode.getInstance().max) {
            answer = true;
        } //if won last 'final' match
        else if (RenderGamePlay.getInstance().hasWon()) {
            //incrementMode();
            //go onBackCancel to user difficulty
            GameState.getInstance().getLogin().setDifficultyDynamic(GameState.getInstance().getLogin().getDifficulty());
            victorySound.play();
            JOptionPane.showMessageDialog(null, Language.getInstance().get(115), "Sweetness!!!", JOptionPane.INFORMATION_MESSAGE);
            answer = false;
        }

        return answer;
    }

    public void selectScene() {
        StoryMode.getInstance().setCurrentScene(validIndex(hoveredStoryIndex));
        StoryMode.getInstance().startGame();
        menuSound.play();
    }

    private int validIndex(int hoveredStoryIndex) {
        for (int index = hoveredStoryIndex; index > 0; index--) {
            if (unlockedStage[index])
                return hoveredStoryIndex;
        }
        return 0;
    }


    public void mouseClicked(MouseEvent me) {
        switch (me.getButton()) {
            case PRIMARY:
                    onAccept();
                break;
        }
    }

    public void keyPressed(KeyEvent keyEvent) {
        KeyCode keyCode = keyEvent.getCode();
        switch (keyCode) {
            case ENTER:
                onAccept();
                break;
            case ESCAPE:
            case BACK_SPACE:
                onBackCancel();
                break;
            case UP:
            case W:
                onUp();
                break;
            case DOWN:
            case S:
                onDown();
                break;
            case LEFT:
            case A:
                onLeft();
                break;
            case RIGHT:
            case D:
                onRight();
                break;
        }
    }

    public void onAccept() {
        selectScene();
    }
}

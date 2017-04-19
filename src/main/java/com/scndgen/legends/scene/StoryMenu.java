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
package com.scndgen.legends.scene;

import com.scndgen.legends.LoginScreen;
import com.scndgen.legends.controller.StoryMode;
import com.scndgen.legends.render.RenderCharacterSelectionScreen;
import com.scndgen.legends.windows.JenesisPanel;
import io.github.subiyacryolite.enginev1.JenesisMode;

public abstract class StoryMenu extends JenesisMode {

    protected int charYcap = 0, charXcap = 0, hoveredStoryIndex = 99, row = 1, x = 0, y = 0, column = 0, vSpacer = 52, hSpacer = 92, commonXCoord = 299, commonYCoord = 105;
    protected final int columns = 3;
    protected final int scenes = StoryMode.getInstance().max;
    protected final int rows = scenes / columns;
    protected int oldId = -1;
    protected boolean[] unlockedStage;
    protected boolean loadingNow;
    protected int currentScene = LoginScreen.getInstance().stage;
    protected int storedX = 99, storedY = 99;
    protected boolean withinMenuPanel;

    /**
     * When both playes are selected, this prevents movement.
     *
     * @return false if both CharacterEnum have been selected, true if only one is selected
     */
    public static boolean bothArentSelected() {
        boolean answer = true;

        if (RenderCharacterSelectionScreen.getInstance().characterSelected && RenderCharacterSelectionScreen.getInstance().opponentSelected) {
            answer = false;
        }

        return answer;
    }

    public void newInstance() {
        loadAssets = true;
        StoryMode.getInstance().newInstance();
        opacity = 1.0f;
    }

    public StoryMode getStoryInstance() {
        return StoryMode.getInstance();
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
    public void moveUp() {
        if (column > 0) {
            column = column - 1;
        } else {
            column = rows;
        }
        capAnim();
    }

    public void moveDown() {
        if (column < rows - 1) {
            column = column + 1;
        } else {
            column = 0;
        }
        capAnim();
    }

    public void moveRight() {
        if (row < columns) {
            row = row + 1;
        } else {
            row = 1;
        }
        capAnim();
    }

    public void moveLeft() {
        if (row > 1) {
            row = row - 1;
        } else {
            row = columns;
        }
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

    public void storyProcceed() {
        getStoryInstance().story(hoveredStoryIndex, true);
    }

    public void back() {
        getStoryInstance().firstRun = true;
        JenesisPanel.getInstance().backToMenuScreen();
    }

    /**
     * Find out the stage the player is on
     */
    public void resetCurrentStage() {
        currentScene = LoginScreen.getInstance().stage;
    }

    public void backToMainMenu() {
        back();
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
     * When you win a match, move to the next level
     */
    public void incrementMode() {
        if (currentScene < StoryMode.getInstance().max) {
            currentScene = currentScene + 1;

            //dont mess up progress
            //if the player has advanced
            //and theres still more stages
            if (currentScene > currentScene) {
                LoginScreen.getInstance().stage = currentScene;
            }
        }
    }

    /**
     * Get the storyboard size for the characterEnum
     *
     * @return the number of levels
     */
    public int getStorySize() {
        return StoryMode.getInstance().max;
    }


    public void startGame(int mode) {
        if (unlockedStage[mode]) {
            if (hoveredStoryIndex < StoryMode.getInstance().max + 1) {
                hoveredStoryIndex = mode;
            } else {
                hoveredStoryIndex = mode - 1;
            }

            getStoryInstance().story(hoveredStoryIndex, false);
            {
                loadingNow = true;
                storyProcceed();
            }
        }
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
        if (computedPosition < scenes) {
            ans = true;
            hoveredStoryIndex = computedPosition;
        }
        return ans;
    }

    /**
     * Horizontal index
     *
     * @return columnIndex
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
     * @return rowIndex
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

    public void mouseMoved(int mouseX, int mouseY) {
        int topY = getStartY();
        int topX = getStartX();
        int columns = getNumberOfCharColumns();
        int vspacer = getCharHSpacer();
        int hspacer = getCharVSpacer();
        int rows = getCharRows();
        if (mouseX > topX && mouseX < (topX + (hspacer * columns)) && (mouseY > topY) && (mouseY < topY + (vspacer * rows))) {
            int vIndex = (mouseY - topY) / vspacer;
            int hIndex = (mouseX - topX) / hspacer ;
            setHindex(hIndex);
            setVindex(vIndex);
            animateCap2x(hIndex, vIndex);
            withinMenuPanel = true;
        } else {
            setHindex(99);
            setVindex(99);
            withinMenuPanel = false;
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

    public boolean getWithinMenuPanel() {
        return withinMenuPanel;
    }
}

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
package com.scndgen.legends.arefactored.mode;

import com.scndgen.legends.LoginScreen;
import com.scndgen.legends.arefactored.controller.StoryMode;
import com.scndgen.legends.arefactored.render.RenderCharacterSelectionScreen;
import io.github.subiyacryolite.enginev1.JenesisMode;

import javax.swing.*;
import java.awt.*;

public abstract class StoryMenu extends JenesisMode {

    public int lastRow, charYcap = 0, charXcap = 0, storySelIndex = 99, hIndex = 1, x = 0, y = 0, vIndex = 0, vSpacer = 52, hSpacer = 92, hPos = 299, firstLine = 105;
    public int mode, scenes, columns = 3, rows;
    protected int oldId = -1;
    protected boolean[] hiddenStage;
    protected boolean loadingNow;
    protected int currMode = LoginScreen.getInstance().stage;

    public StoryMenu() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.black, 1));

    }

    public void newInstance() {
        StoryMode.getInstance().newInstance();
        opacity = 1.0f;
    }

    /**
     * When both playes are selected, this prevents movement.
     *
     * @return false if both Character have been selected, true if only one is selected
     */
    public static boolean bothArentSelected() {
        boolean answer = true;

        if (RenderCharacterSelectionScreen.getInstance().characterSelected && RenderCharacterSelectionScreen.getInstance().opponentSelected) {
            answer = false;
        }

        return answer;
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
        if (vIndex > 0) {
            vIndex = vIndex - 1;
        } else {
            vIndex = rows;
        }
        capAnim();
    }

    public void moveDown() {
        if (vIndex < rows - 1) {
            vIndex = vIndex + 1;
        } else {
            vIndex = 0;
        }
        capAnim();
    }

    public void moveRight() {
        if (hIndex < columns) {
            hIndex = hIndex + 1;
        } else {
            hIndex = 1;
        }
        capAnim();
    }

    public void moveLeft() {
        if (hIndex > 1) {
            hIndex = hIndex - 1;
        } else {
            hIndex = columns;
        }
        capAnim();
    }

    /**
     * Gets the number of columns in the character select screen
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
        return hPos;
    }

    /**
     * Returns the starting Y coordinate
     *
     * @return starting y
     */
    public int getStartY() {
        return firstLine;
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
     * Get starting x coordinate
     *
     * @return starting x coordinate
     */
    public void takeScreenshotX() {
        captureScreenShot();
    }

    /**
     * Animates caption
     */
    public void animateCaption() {
        capAnim();
    }

    public void storyProcceed() {
        getStoryInstance().story(storySelIndex, true);
    }

    public void back() {
        getStoryInstance().firstRun = true;
        LoginScreen.getInstance().getMenu().getMain().backToMenuScreen();
    }

    /**
     * Find out the stage the player is on
     */
    public void resetCurrentStage() {
        mode = LoginScreen.getInstance().stage;
    }

    public void backToMainMenu() {
        back();
    }

    /**
     * When in story currMode, we go to the current level
     *
     * @return current level
     */
    public int getStage() {
        return currMode;
    }

    /**
     * When you win a match, move to the next level
     */
    public void incrementMode() {
        if (currMode < StoryMode.getInstance().max) {
            currMode = currMode + 1;

            //dont mess up progress
            //if the player has advanced
            //and theres still more stages
            if (currMode > mode) {
                LoginScreen.getInstance().stage = currMode;
            }
        }
    }

    /**
     * Set the stage the player is on
     *
     * @param here - stage the player is on
     */
    public void setCurrMode(int here) {
        currMode = here;
    }

    /**
     * Get the storyboard size for the character
     *
     * @return the number of levels
     */
    public int getStorySize() {
        return StoryMode.getInstance().max;
    }


    public void startGame(int mode) {
        if (hiddenStage[mode]) {
            if (storySelIndex < StoryMode.getInstance().max + 1) {
                storySelIndex = mode;
            } else {
                storySelIndex = mode - 1;
            }

            getStoryInstance().story(storySelIndex, false);
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
        storySelIndex = where;
    }

    /**
     * Checks if within number of Character
     */
    public boolean well() {
        boolean ans = false;
        int xV = (vIndex * 3) + hIndex;
        if (xV <= scenes) {
            ans = true;
            storySelIndex = xV - 1;
        }

        return ans;
    }

    /**
     * Horizontal index
     *
     * @return hIndex
     */
    public final int getHindex() {
        return hIndex;
    }

    /**
     * Set horizontal index
     */
    public final void setHindex(int value) {
        hIndex = value;
    }

    /**
     * Vertical index
     *
     * @return vIndex
     */
    public final int getVindex() {
        return vIndex;
    }

    /**
     * Set vertical index
     */
    public final void setVindex(int value) {
        vIndex = value;
    }

    protected void showstoryName(int id) {
        if (id != oldId) {
            systemNotice("Scene " + (id + 1));
            oldId = id;
        }
    }
}

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
package com.scndgen.legends.menus;

import com.scndgen.legends.LoginScreen;
import com.scndgen.legends.arefactored.render.RenderStandardGameplay;
import com.scndgen.legends.arefactored.render.RenderCharacterSelectionScreen;
import com.scndgen.legends.engine.JenesisLanguage;
import com.scndgen.legends.threads.ThreadMP3;
import io.github.subiyacryolite.enginev1.JenesisMode;

import javax.swing.*;
import java.awt.*;

public abstract class StoryMode extends JenesisMode {

    public int lastRow, currentSlot = 0, xCordCloud = 0, xCordCloud2 = 0, charYcap = 0, charXcap = 0, storySelIndex = 99, hIndex = 1, x = 0, y = 0, vIndex = 0, vSpacer = 52, hSpacer = 92, hPos = 299, firstLine = 105;
    public int characterSel, opponentSel;
    public String charDesc = "";
    public String oppName, charName;
    public String loadTxt = "";
    public int mode, scenes, columns = 3, rows;
    protected int oldId = -1;
    protected boolean[] hiddenStage;
    protected boolean loadingNow;
    private com.scndgen.legends.arefactored.mode.StoryMode storyInstance;
    private int currMode = LoginScreen.getInstance().stage;
    private ThreadMP3 yay;
    private ThreadMP3 menuSound;

    public StoryMode() {
        menuSound = new ThreadMP3("audio/menu-select.mp3", true);
        storyInstance = new com.scndgen.legends.arefactored.mode.StoryMode();
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.black, 1));
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

    public com.scndgen.legends.arefactored.mode.StoryMode getStoryInstance() {
        return storyInstance;
    }

    public void setStoryInstance(com.scndgen.legends.arefactored.mode.StoryMode p) {
        storyInstance = p;
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

    public void selectStage() {
        prepareStory();
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
        if (currMode < com.scndgen.legends.arefactored.mode.StoryMode.max) {
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
        return com.scndgen.legends.arefactored.mode.StoryMode.max;
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
        if (currMode < com.scndgen.legends.arefactored.mode.StoryMode.max) {
            answer = true;
        } //if won last 'final' match
        else if (RenderStandardGameplay.getInstance().hasWon()) {
            //incrementMode();
            //go back to user difficulty
            LoginScreen.getInstance().difficultyDyn = LoginScreen.getInstance().difficultyStat;
            yay = new ThreadMP3(ThreadMP3.soundGameOver(), true);
            yay.play();
            JOptionPane.showMessageDialog(null, JenesisLanguage.getInstance().getLine(115), "Sweetness!!!", JOptionPane.INFORMATION_MESSAGE);
            answer = false;
        }

        return answer;
    }

    public void prepareStory() {
        for (int i = 0; i <= com.scndgen.legends.arefactored.mode.StoryMode.max; i++) {
            if (storySelIndex == i) {
                startGame(i);
                menuSound.play();
                break;
            }
        }
    }

    public void startGame(int mode) {
        if (hiddenStage[mode]) {
            if (storySelIndex < com.scndgen.legends.arefactored.mode.StoryMode.max + 1) {
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

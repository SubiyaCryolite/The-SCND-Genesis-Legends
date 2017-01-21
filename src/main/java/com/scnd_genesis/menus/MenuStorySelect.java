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
package com.scnd_genesis.menus;

import com.scnd_genesis.LoginScreen;
import com.scnd_genesis.StoryMode;
import com.scnd_genesis.drawing.DrawStorySel;
import com.scnd_genesis.engine.JenesisLanguage;
import com.scnd_genesis.threads.ThreadMP3;

import javax.swing.*;
import java.awt.*;

public class MenuStorySelect extends DrawStorySel {

    public int characterSel, opponentSel;
    public String charDesc = "";
    public String oppName, charName;
    private JenesisLanguage lang;
    private StoryMode storyInstance;
    private int mode, currMode = LoginScreen.getLoginScreen().stage;
    private ThreadMP3 yay;

    public MenuStorySelect() {
        initializePanel();
    }

    public StoryMode getStoryInstance() {
        return storyInstance;
    }

    public void setStoryInstance(StoryMode p) {
        storyInstance = p;
    }

    /**
     * Character select screen, move up
     */
    public void moveUp() {
        upMove();
    }

    /**
     * Character select screen, move down
     */
    public void moveDown() {
        downMove();
    }

    /**
     * Character select screen, move right
     */
    public void moveRight() {
        rightMove();
    }

    /**
     * Character select screen, move left
     */
    public void moveLeft() {
        leftMove();
    }

    /**
     * Gets the number of columns in the character select screen
     *
     * @return number of columns
     */
    public int getNumberOfCharColumns() {
        return horizColumns;
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
        return verticalRows;
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


    protected void initializePanel() {
        storyInstance = new StoryMode();
        lang = LoginScreen.getLoginScreen().getLangInst();
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.black, 1));
    }

    public void storyProcceed() {
        getStoryInstance().story(storySelIndex, true);
    }

    @Override
    public void startGame(int mode) {
        if (hiddenStage[mode]) {
            if (storySelIndex < StoryMode.max + 1) {
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

    public void back() {
        getStoryInstance().firstRun = true;
        LoginScreen.getLoginScreen().getMenu().getMain().backToMenuScreen();
    }

    /**
     * Find out the stage the player is on
     */
    public void resetCurrentStage() {
        mode = LoginScreen.getLoginScreen().stage;
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
        if (currMode < StoryMode.max) {
            currMode = currMode + 1;

            //dont mess up progress
            //if the player has advanced
            //and theres still more stages
            if (currMode > mode) {
                LoginScreen.getLoginScreen().stage = currMode;
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
        return StoryMode.max;
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
        if (currMode < StoryMode.max) {
            answer = true;
        } //if won last 'final' match
        else if (LoginScreen.getLoginScreen().getMenu().getMain().getGame().hasWon()) {
            //incrementMode();
            //go back to user difficulty
            LoginScreen.getLoginScreen().difficultyDyn = LoginScreen.getLoginScreen().difficultyStat;
            yay = new ThreadMP3(ThreadMP3.soundGameOver(), true);
            yay.play();
            JOptionPane.showMessageDialog(null, lang.getLine(115), "Sweetness!!!", JOptionPane.INFORMATION_MESSAGE);
            answer = false;
        }

        return answer;
    }
}

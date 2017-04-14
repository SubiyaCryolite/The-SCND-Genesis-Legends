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
package com.scndgen.legends.threads;

import com.scndgen.legends.Achievements;
import com.scndgen.legends.LoginScreen;
import com.scndgen.legends.arefactored.mode.StandardGameplay;
import com.scndgen.legends.arefactored.mode.StoryMode;
import com.scndgen.legends.arefactored.render.RenderCharacterSelectionScreen;
import com.scndgen.legends.arefactored.render.RenderStandardGameplay;
import com.scndgen.legends.arefactored.render.RenderStoryMenu;
import com.scndgen.legends.executers.ExecuterMovesChar;
import com.scndgen.legends.executers.ExecuterMovesChar2;
import com.scndgen.legends.executers.ExecuterMovesOpp;
import com.scndgen.legends.executers.ExecuterMovesOpp2;
import com.scndgen.legends.menus.RenderStageSelect;
import com.scndgen.legends.windows.WindowMain;
import com.scndgen.legends.windows.WindowOptions;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class ensures the game runs at a particular fps and initialises several
 * other threads to execute during runtime
 *
 * @author Ifunga Ndana
 */
public class ThreadGameInstance implements Runnable, ActionListener {

    public static boolean isGameOver, isPaused, gameRunning;
    public static int time, count2;
    public static boolean instance, storySequence;
    private static boolean incrementActivityBar = true, incrementActivityBarOpp = true, incrementActivityBarOpp2 = true, incrementActivityBarChar2 = true;
    private final StandardGameplay standardGameplay;
    public int taskComplete;
    public int taskRun = 0;
    public int feeCol;
    public boolean isRunning = false;
    public String musicStr;
    public String timeStr;//, mode;
    public int time1 = 10, time2 = 10, time3 = 10;
    public boolean aiAttack = false, aiRunning = false, aiRunning2 = false, aiRunning3 = false;
    public Achievements ach;
    public ThreadMP3 loseMus, winMus;
    private Thread t;
    private Timer timer;
    //recov char HP
    private float hpChar, hpChar2;
    //recove char activity bar
    private int sampleChar;
    private float sampleCharDB;
    private int limitChar;
    private int sampleChar2;
    private float sampleCharDB2;
    private int limitChar2;
    //recov opp hp
    private float hpOpp;
    //recov opp activity bar
    private int sampleOpp;
    private float sampleOppDB;
    private int limitOpp;
    private int sampleOpp2;
    private float sampleOppDB2;
    private int limitOpp2;
    private int timeToChill;
    private float count = 0.0f;
    private ExecuterMovesOpp executorAI;
    private ExecuterMovesOpp2 executorAI2;
    private ExecuterMovesChar2 executorAI3;
    private ExecuterMovesChar executorPlyr;
    private int speedFactor = 30; //equal to the fps division
    private int matchDuration, playTimeCounter;

    //indicates if game is running, controls game over screen and Achievements which require wins
    public ThreadGameInstance(int forWho, StandardGameplay standardGameplay) {
        this.standardGameplay = standardGameplay;
        musicStr = RenderStageSelect.getTrack();
        newInstance();
    }

    /**
     * The main game instance thread
     */
    @SuppressWarnings("static-access")
    @Override
    public void run() {

        do {
            instance = true;
            try {
                t.sleep(33); // fps
                standardGameplay.matchStatus();
                ach.scan();
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadGameInstance.class.getName()).log(Level.SEVERE, null, ex);
            }

            //---------recover Character activity bar
            if (sampleChar <= limitChar && incrementActivityBar) {
                sampleCharDB = sampleCharDB + (RenderCharacterSelectionScreen.getInstance().getPlayers().getCharRecoverySpeed());
                sampleChar = Integer.parseInt("" + Math.round(sampleCharDB) + "");
            }

            //---------recover opponents activity bar

            if (sampleOpp <= limitOpp && incrementActivityBarOpp && storySequence == false) {
                sampleOppDB = sampleOppDB + (RenderCharacterSelectionScreen.getInstance().getPlayers().getOppRecoverySpeed());
                sampleOpp = Integer.parseInt("" + Math.round(sampleOppDB) + "");
            } else if (aiRunning == false && incrementActivityBarOpp && storySequence == false) {
                if (LoginScreen.getInstance().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.singlePlayer) || LoginScreen.getInstance().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.storyMode) || LoginScreen.getInstance().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.singlePlayer2)) {
                    aiRunning = true;
                    executorAI.attack();
                }
            }

            if (LoginScreen.getInstance().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.singlePlayer2)) {
                if (sampleOpp2 <= limitOpp2 && incrementActivityBarOpp2 && storySequence == false) {
                    sampleOppDB2 = sampleOppDB2 + (RenderCharacterSelectionScreen.getInstance().getPlayers().getOppRecoverySpeed2());
                    sampleOpp2 = Integer.parseInt("" + Math.round(sampleOppDB2) + "");
                } else if (aiRunning2 == false && incrementActivityBarOpp2 && storySequence == false) {

                    {
                        aiRunning2 = true;
                        executorAI2.attack();
                    }
                }

                if (sampleChar2 <= limitChar2 && incrementActivityBarChar2 && storySequence == false) {
                    sampleCharDB2 = sampleCharDB2 + (RenderCharacterSelectionScreen.getInstance().getPlayers().getCharRecoverySpeed2());
                    sampleChar2 = Integer.parseInt("" + Math.round(sampleCharDB2) + "");
                } else if (aiRunning3 == false && incrementActivityBarChar2 && storySequence == false) {
                    {
                        aiRunning3 = true;
                        executorAI3.attack();
                    }
                }
            }


            if ((time <= 180 && storySequence == false)) {

                if (count < 1000) //continue till we make a second
                {
                    count = count + speedFactor;
                } else {
                    try {
                        time = time - 1;
                        timeStr = "" + time + "";
                        count = 0.0f;


                        if (timeStr.length() == 1) {
                            time1 = 10;
                            time2 = 10;
                            time3 = Integer.parseInt("" + timeStr.charAt(0));
                        }

                        if (timeStr.length() == 2) {
                            time1 = 10;
                            time2 = Integer.parseInt("" + timeStr.charAt(0));
                            time3 = Integer.parseInt("" + timeStr.charAt(1));
                        }

                        if (timeStr.length() == 3) {
                            time1 = Integer.parseInt("" + timeStr.charAt(0));
                            time2 = Integer.parseInt("" + timeStr.charAt(1));
                            time3 = Integer.parseInt("" + timeStr.charAt(2));
                        }
                    } catch (NumberFormatException nfe) {
                    }
                }
            }
        } while (gameRunning);
    }

    /**
     * Get the Character recovery units
     *
     * @return Character recovery units
     */
    public int getRecoveryUnitsChar() {
        return sampleChar;
    }

    /**
     * Set the Character recovery units
     *
     * @param thisNum - Character recovery units
     */
    public void setRecoveryUnitsChar(int thisNum) {
        sampleCharDB = (int) Float.parseFloat("" + thisNum + "");
        sampleChar = thisNum;
    }

    /**
     * Gets the Character recovery units
     *
     * @return Character recovery units
     */
    public int getLimitChar() {
        return limitChar;
    }

    /**
     * Get the opponents recovery units
     *
     * @return opponents recovery units
     */
    public int getRecoveryUnitsOpp() {
        return sampleOpp;
    }

    /**
     * Set the opponents recovery units
     *
     * @param thisNum2 - the value to set
     */
    public void setRecoveryUnitsOpp(int thisNum2) {
        sampleOppDB = (int) Float.parseFloat("" + thisNum2 + "");
        sampleOpp = thisNum2;
    }

    /**
     * Get the opponents recovery limits
     *
     * @return recovery limit for opponent
     */
    public int getLimitOpp() {
        return limitOpp;
    }

    /**
     * Get the opponents recovery limits
     *
     * @return recovery limit for opponent
     */
    public int getLimitChar2() {
        return limitChar2;
    }

    /**
     * Get the opponents recovery units
     *
     * @return opponents recovery units
     */
    public int getRecoveryUnitsOpp2() {
        return sampleOpp2;
    }

    /**
     * Set the opponents recovery units
     *
     * @param thisNum2 - the value to set
     */
    public void setRecoveryUnitsOpp2(int thisNum2) {
        sampleOppDB2 = (int) Float.parseFloat("" + thisNum2 + "");
        sampleOpp2 = thisNum2;
    }

    /**
     * Get the opponents recovery units
     *
     * @return opponents recovery units
     */
    public int getRecoveryUnitsChar2() {
        return sampleChar2;
    }

    /**
     * Set the opponents recovery units
     *
     * @param thisNum2 - the value to set
     */
    public void setRecoveryUnitsChar2(int thisNum2) {
        sampleCharDB2 = (int) Float.parseFloat("" + thisNum2 + "");
        sampleChar2 = thisNum2;
    }

    /**
     * Get the opponents recovery limits
     *
     * @return recovery limit for opponent
     */
    public int getLimitOpp2() {
        return limitOpp2;
    }

    /**
     * Pauses the current match
     *
     * @throws InterruptedException
     */
    public void pauseGame() {
        try {

            isPaused = true;
            RenderStandardGameplay.getInstance().pauseThreads();
            t.suspend();
            executorAI.pause();
            executorPlyr.pause();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Resumes a paused match
     */
    public void resumeGame() {
        try {
            isPaused = false;
            RenderStandardGameplay.getInstance().resumeThreads();
            t.resume();
            executorAI.resume();
            executorPlyr.resume();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Performs game over operations, updates player profile and displays Achievements
     */
    public void gameOver() {
        gameRunning = false;
        isGameOver = true;
        LoginScreen.getInstance().getMenu().getMain().freeToSave = true;
        RenderStandardGameplay.getInstance().closeAudio();
        LoginScreen.getInstance().setCurrentPlayTime(playTimeCounter);
        ach.scan();
        //if not story mode, increment char usage
        if (LoginScreen.getInstance().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.storyMode) == false) {
            LoginScreen.getInstance().incrementCharUsage(RenderCharacterSelectionScreen.getInstance().selectedCharIndex);
        }
        if (standardGameplay.hasWon()) {
            RenderStandardGameplay.getInstance().showWinLabel();
            winMus.play();
        } else {
            RenderStandardGameplay.getInstance().showLoseLabel();
            loseMus.play();
        }
        RenderStageSelect.selectedStage = false;
        RenderCharacterSelectionScreen.getInstance().getPlayers().resetCharacters();
        RenderStandardGameplay.getInstance().drawAchievements();
    }

    /**
     * Thread sleep method
     *
     * @param thisTime - the time to sleep
     */
    @SuppressWarnings("static-access")
    public void sleepy(int thisTime) {
        try {
            t.sleep(thisTime);
        } catch (InterruptedException ex) {
            Logger.getLogger(ThreadGameInstance.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Get the status of AI activity
     *
     * @return AI activity status
     */
    public boolean getAiAttack() {
        return aiAttack;
    }

    /**
     * Inicate the execution of an AI attack
     *
     * @param thisWhy
     */
    public void setAiAttack(boolean thisWhy) {
        aiAttack = thisWhy;
    }

    /**
     * When the player selects all his moves, a new attack sequence is triggered
     */
    public void triggerCharAttack() {
        executorPlyr = new ExecuterMovesChar();
    }

    /**
     * This method records play time
     */
    private void recordPlayTime() {
        matchDuration = 0;
        playTimeCounter = LoginScreen.getInstance().getCurrentPlayTime();
        new Thread() {

            @Override
            @SuppressWarnings("static-access")
            public void run() {
                do {
                    try {
                        this.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ThreadGameInstance.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    playTimeCounter = playTimeCounter + 1;
                    matchDuration = matchDuration + 1;
                } while (gameRunning);
            }
        }.start();
    }

    public int getMatchTime() {
        return matchDuration;
    }

    /**
     * Get this context
     *
     * @return
     */
    public Achievements me() {
        return ach;
    }

    /**
     * This thread executes when a game is over, designed to free unused memory
     */
    public void closingThread(int mo) {
        instance = false;
        //update profile operations
        LoginScreen.getInstance().incrementMatchCount();
        LoginScreen.getInstance().setPoints(ach.getNewUserPoints());
        //save profile
        LoginScreen.getInstance().saveConfigFile();
        LoginScreen.getInstance().getMenu().getMain().systemNotice("Saved File");
        t.stop(); //stop this thread
        if (LoginScreen.getInstance().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.storyMode) && RenderStoryMenu.getInstance().moreStages()) {
            //nextStage if you've won
            if (standardGameplay.hasWon()) {
                LoginScreen.getInstance().getMenu().getMain().getStory().incrementMode();
                winMus.play();
            } else {
                loseMus.play();
            }
            LoginScreen.getInstance().getMenu().getMain().getStory().storyProcceed();
            LoginScreen.getInstance().getMenu().getMain().nextStage();
        } else {
            //javax.swing.JOptionPane.showMessageDialog(null, "Done");
            if (mo == 0) {
                LoginScreen.getInstance().getMenu().getMain().backToMenuScreen();
            } else if (mo == 1) {
                LoginScreen.getInstance().getMenu().getMain().backToCharSelect();
            }
        }
    }

    /**
     * Cancel the game mid fight
     */
    public void terminateThread() {
        isPaused = false;
        gameRunning = false;
        isGameOver = true;
        instance = false;
        RenderCharacterSelectionScreen.getInstance().getPlayers().resetCharacters();

        //kill threads
        RenderStandardGameplay.getInstance().closeAudio();
    }

    public void timeOut(int time) {
        timeToChill = time;
    }

    /**
     * This method prevents the regeneration of Activity while movesare executing
     */
    public void pauseActivityRegen() {
        incrementActivityBar = false;
    }

    /**
     * Resumes the activity bar
     */
    public void resumeActivityRegen() {
        incrementActivityBar = true;
    }

    /**
     * This method prevents the regeneration of Activity while movesare executing
     */
    public void pauseActivityRegenOpp() {
        incrementActivityBarOpp = false;
    }

    /**
     * This method prevents the regeneration of Activity while movesare executing
     */
    public void pauseActivityRegenOpp2() {
        incrementActivityBarOpp2 = false;
    }

    /**
     * Resumes the activity bar
     */
    public void resumeActivityRegenOpp() {
        incrementActivityBarOpp = true;
    }

    /**
     * Resumes the activity bar
     */
    public void resumeActivityRegenOpp2() {
        incrementActivityBarOpp2 = true;
    }

    /**
     * This method prevents the regeneration of Activity while movesare executing
     */
    public void pauseActivityRegenChar2() {
        incrementActivityBarChar2 = false;
    }

    /**
     * Resumes the activity bar
     */
    public void resumeActivityRegenChar2() {
        incrementActivityBarChar2 = true;
    }

    private void newInstance() {
        executorAI = new ExecuterMovesOpp();
        executorAI2 = new ExecuterMovesOpp2();
        executorAI3 = new ExecuterMovesChar2();

        ach = LoginScreen.getInstance().getAch();
        if (LoginScreen.getInstance().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.storyMode)) {
            storySequence = true;
            time = StoryMode.getInstance().time;
        } //if LAN, client uses hosts time preset
        else if (LoginScreen.getInstance().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanClient)) {
            time = LoginScreen.getInstance().getMenu().getMain().hostTime;
        } else {
            time = WindowOptions.time;
        }
        gameRunning = true;
        recordPlayTime();
        hpChar = 0.0f;
        sampleChar = 00;
        sampleCharDB = 00;
        limitChar = 290;
        hpChar2 = 0.0f;
        sampleChar2 = 00;
        sampleCharDB2 = 00;
        limitChar2 = 290;
        hpOpp = 0.0f;
        sampleOpp = 00;
        sampleOppDB = 00;
        limitOpp = 290;
        sampleOpp2 = 00;
        sampleOppDB2 = 00;
        limitOpp2 = 290;
        isPaused = false;
        isGameOver = false;
        ach.newInstance();
        LoginScreen.getInstance().newGame = true;
        winMus = new ThreadMP3(ThreadMP3.winSound(), false);
        loseMus = new ThreadMP3(ThreadMP3.loseSound(), false);
        if (LoginScreen.getInstance().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.storyMode) == false) {
            RenderStandardGameplay.getInstance().playBGSound();
            musNotice();
            LoginScreen.getInstance().getMenu().getMain().systemNotice(LoginScreen.getInstance().getMenu().getMain().getAttacksOpp().getDude().getBraggingRights(RenderCharacterSelectionScreen.getInstance().selectedCharIndex));
        }

        t = new Thread(this);
        t.setName("MAIN GAME LOGIC THREAD");
        t.setPriority(5);
        t.start();
        LoginScreen.getInstance().getMenu().getMain().reSize("menu");
        count2 = 0;
    }

    public void musNotice() {
        LoginScreen.getInstance().getMenu().getMain().systemNotice2(RenderStageSelect.trax[RenderStageSelect.musicInt]);
    }

    /**
     * Play music once story text is over
     */
    public void playMusicNow() {
        try {
            RenderStandardGameplay.getInstance().playBGSound();
            LoginScreen.getInstance().getMenu().getMain().systemNotice(LoginScreen.getInstance().getMenu().getMain().getAttacksOpp().getDude().getBraggingRights(RenderCharacterSelectionScreen.getInstance().selectedCharIndex));
        } catch (Exception e) {
            System.out.println("Dude, somin went wrong" + e.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //
    }
}

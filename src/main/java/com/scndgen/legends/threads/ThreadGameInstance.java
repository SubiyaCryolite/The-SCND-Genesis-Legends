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
import com.scndgen.legends.drawing.DrawGame;
import com.scndgen.legends.executers.ExecuterMovesChar;
import com.scndgen.legends.executers.ExecuterMovesChar2;
import com.scndgen.legends.executers.ExecuterMovesOpp;
import com.scndgen.legends.executers.ExecuterMovesOpp2;
import com.scndgen.legends.menus.MenuGameRender;
import com.scndgen.legends.menus.MenuStageSelect;
import com.scndgen.legends.StoryMode;
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
    public static boolean instance, story;
    private static boolean incrementActivityBar = true, incrementActivityBarOpp = true, incrementActivityBarOpp2 = true, incrementActivityBarChar2 = true;
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
    private MenuGameRender parentx;

    //indicates if game is running, controls game over screen and Achievements which require wins
    public ThreadGameInstance(int forWho, MenuGameRender parentx) {
        this.parentx = parentx;
        musicStr = MenuStageSelect.getTrack();
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
                parentx.matchStatus();
                ach.scan();
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadGameInstance.class.getName()).log(Level.SEVERE, null, ex);
            }

            //---------recover Characters activity bar
            if (sampleChar <= limitChar && incrementActivityBar) {
                sampleCharDB = sampleCharDB + (LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().getPayers().getCharRecoverySpeed());
                sampleChar = Integer.parseInt("" + Math.round(sampleCharDB) + "");
            }

            //---------recover opponents activity bar

            if (sampleOpp <= limitOpp && incrementActivityBarOpp && story == false) {
                sampleOppDB = sampleOppDB + (LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().getPayers().getOppRecoverySpeed());
                sampleOpp = Integer.parseInt("" + Math.round(sampleOppDB) + "");
            } else if (aiRunning == false && incrementActivityBarOpp && story == false) {
                if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.singlePlayer) || LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.storyMode) || LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.singlePlayer2)) {
                    aiRunning = true;
                    executorAI.attack();
                }
            }

            if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.singlePlayer2)) {
                if (sampleOpp2 <= limitOpp2 && incrementActivityBarOpp2 && story == false) {
                    sampleOppDB2 = sampleOppDB2 + (LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().getPayers().getOppRecoverySpeed2());
                    sampleOpp2 = Integer.parseInt("" + Math.round(sampleOppDB2) + "");
                } else if (aiRunning2 == false && incrementActivityBarOpp2 && story == false) {

                    {
                        aiRunning2 = true;
                        executorAI2.attack();
                    }
                }

                if (sampleChar2 <= limitChar2 && incrementActivityBarChar2 && story == false) {
                    sampleCharDB2 = sampleCharDB2 + (LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().getPayers().getCharRecoverySpeed2());
                    sampleChar2 = Integer.parseInt("" + Math.round(sampleCharDB2) + "");
                } else if (aiRunning3 == false && incrementActivityBarChar2 && story == false) {
                    {
                        aiRunning3 = true;
                        executorAI3.attack();
                    }
                }
            }


            if ((time <= 180 && story == false)) {

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
     * Get the Characters recovery units
     *
     * @return Characters recovery units
     */
    public int getRecoveryUnitsChar() {
        return sampleChar;
    }

    /**
     * Set the Characters recovery units
     *
     * @param thisNum - Characters recovery units
     */
    public void setRecoveryUnitsChar(int thisNum) {
        sampleCharDB = (int) Float.parseFloat("" + thisNum + "");
        sampleChar = thisNum;
    }

    /**
     * Gets the Characters recovery units
     *
     * @return Characters recovery units
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
            LoginScreen.getLoginScreen().getMenu().getMain().getGame().pauseThreads();
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
            LoginScreen.getLoginScreen().getMenu().getMain().getGame().resumeThreads();
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
        LoginScreen.getLoginScreen().getMenu().getMain().freeToSave = true;
        LoginScreen.getLoginScreen().getMenu().getMain().getGame().closeAudio();
        LoginScreen.getLoginScreen().setCurrentPlayTime(playTimeCounter);
        ach.scan();
        //if not story mode, increment char usage
        if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.storyMode) == false) {
            LoginScreen.getLoginScreen().incrementCharUsage(LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().selectedCharIndex);
        }
        if (parentx.hasWon()) {
            DrawGame.winPic();
            winMus.play();
        } else {
            DrawGame.losePic();
            loseMus.play();
        }
        MenuStageSelect.selectedStage = false;
        LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().getPayers().resetCharacters();
        DrawGame.drawAchievements();
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
        playTimeCounter = LoginScreen.getLoginScreen().getCurrentPlayTime();
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
        LoginScreen.getLoginScreen().incrementMatchCount();
        LoginScreen.getLoginScreen().setPoints(ach.getNewUserPoints());
        //save profile
        LoginScreen.getLoginScreen().saveConfigFile();
        LoginScreen.getLoginScreen().getMenu().getMain().systemNotice("Saved File");
        t.stop(); //stop this thread
        if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.storyMode) && LoginScreen.getLoginScreen().getMenu().getMain().getStory().moreStages()) {
            //nextStage if you've won
            if (parentx.hasWon()) {
                LoginScreen.getLoginScreen().getMenu().getMain().getStory().incrementMode();
                winMus.play();
            } else {
                loseMus.play();
            }
            LoginScreen.getLoginScreen().getMenu().getMain().getStory().storyProcceed();
            LoginScreen.getLoginScreen().getMenu().getMain().nextStage();
        } else {
            //javax.swing.JOptionPane.showMessageDialog(null, "Done");
            if (mo == 0) {
                LoginScreen.getLoginScreen().getMenu().getMain().backToMenuScreen();
            } else if (mo == 1) {
                LoginScreen.getLoginScreen().getMenu().getMain().backToCharSelect();
            }
        }
        //go back to mode menu
        DrawGame.currentCols = 0;
        DrawGame.currentColumn = DrawGame.physical;
    }

    /**
     * Cancel the game mid fight
     */
    public void terminateThread() {
        isPaused = false;
        gameRunning = false;
        isGameOver = true;
        instance = false;
        LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().getPayers().resetCharacters();

        //kill threads
        LoginScreen.getLoginScreen().getMenu().getMain().getGame().closeAudio();
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

        ach = LoginScreen.getLoginScreen().getAch();
        if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.storyMode)) {
            story = true;
            time = StoryMode.time;
        } //if LAN, client uses hosts time preset
        else if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanClient)) {
            time = LoginScreen.getLoginScreen().getMenu().getMain().hostTime;
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
        LoginScreen.getLoginScreen().newGame = true;
        winMus = new ThreadMP3(ThreadMP3.winSound(), false);
        loseMus = new ThreadMP3(ThreadMP3.loseSound(), false);
        if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.storyMode) == false) {
            LoginScreen.getLoginScreen().getMenu().getMain().getGame().playBGSound();
            musNotice();
            LoginScreen.getLoginScreen().getMenu().getMain().systemNotice(LoginScreen.getLoginScreen().getMenu().getMain().getAttacksOpp().getDude().getBraggingRights(LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().selectedCharIndex));
        }

        t = new Thread(this);
        t.setName("MAIN GAME LOGIC THREAD");
        t.setPriority(5);
        t.start();
        LoginScreen.getLoginScreen().getMenu().getMain().reSize("menu");
        count2 = 0;
    }

    public void musNotice() {
        LoginScreen.getLoginScreen().getMenu().getMain().systemNotice2(MenuStageSelect.trax[MenuStageSelect.musicInt]);
    }

    /**
     * Play music once story text is over
     */
    public void playMusicNow() {
        try {
            LoginScreen.getLoginScreen().getMenu().getMain().getGame().playBGSound();
            LoginScreen.getLoginScreen().getMenu().getMain().systemNotice(LoginScreen.getLoginScreen().getMenu().getMain().getAttacksOpp().getDude().getBraggingRights(LoginScreen.getLoginScreen().getMenu().getMain().getCharSelect().selectedCharIndex));
        } catch (Exception e) {
            System.out.println("Dude, somin went wrong" + e.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //
    }
}

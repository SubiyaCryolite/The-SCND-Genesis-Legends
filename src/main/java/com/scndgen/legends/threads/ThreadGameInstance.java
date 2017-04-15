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
import com.scndgen.legends.controller.StoryMode;
import com.scndgen.legends.executers.CharacterAttacks;
import com.scndgen.legends.executers.OpponentAttacks;
import com.scndgen.legends.render.RenderCharacterSelectionScreen;
import com.scndgen.legends.render.RenderGameplay;
import com.scndgen.legends.render.RenderStageSelect;
import com.scndgen.legends.render.RenderStoryMenu;
import com.scndgen.legends.scene.Gameplay;
import com.scndgen.legends.windows.MainWindow;
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
    private static boolean incrementActivityBar = true, incrementActivityBarOpp = true;
    private final Gameplay gameplay;
    public int taskComplete;
    public int taskRun = 0;
    public int feeCol;
    public boolean isRunning = false;
    public String musicStr;
    public String timeStr;//, scene;
    public int time1 = 10, time2 = 10, time3 = 10;
    public boolean aiAttack = false, aiRunning = false;
    public Achievements ach;
    public AudioPlayback loseMus, winMus;
    private Thread thread;
    private Timer timer;
    //recov char HP
    private float hpChar, hpChar2;
    //recove char activity bar
    private int sampleChar;
    private float sampleCharDB;
    private int limitChar;
    //recov opp hp
    private float hpOpp;
    //recov opp activity bar
    private int sampleOpp;
    private float sampleOppDB;
    private int limitOpp;
    private float count = 0.0f;
    private OpponentAttacks executorAI;
    private CharacterAttacks executorPlyr;
    private int speedFactor = 30; //equal to the fps division
    private int matchDuration, playTimeCounter;
    private int timeOut;

    //indicates if game is running, controls game over screen and Achievements which require wins
    public ThreadGameInstance(int forWho, Gameplay gameplay) {
        this.gameplay = gameplay;
        musicStr = RenderStageSelect.getInstance().getTrack();
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
                thread.sleep(33); // fps
                gameplay.matchStatus();
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
                if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.singlePlayer) || MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.storyMode)) {
                    aiRunning = true;
                    executorAI.attack();
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
     * Pauses the current match
     *
     * @throws InterruptedException
     */
    public void pauseGame() {
        try {

            isPaused = true;
            RenderGameplay.getInstance().pauseThreads();
            thread.suspend();
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
            RenderGameplay.getInstance().resumeThreads();
            thread.resume();
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
        MainWindow.getInstance().freeToSave = true;
        RenderGameplay.getInstance().closeAudio();
        LoginScreen.getInstance().setCurrentPlayTime(playTimeCounter);
        ach.scan();
        //if not story scene, increment char usage
        if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.storyMode) == false) {
            LoginScreen.getInstance().incrementCharUsage(RenderCharacterSelectionScreen.getInstance().getSelectedCharIndex());
        }
        if (gameplay.hasWon()) {
            RenderGameplay.getInstance().showWinLabel();
            winMus.play();
        } else {
            RenderGameplay.getInstance().showLoseLabel();
            loseMus.play();
        }
        RenderStageSelect.getInstance().setSelectedStage(false);
        RenderCharacterSelectionScreen.getInstance().getPlayers().resetCharacters();
        RenderGameplay.getInstance().drawAchievements();
    }

    /**
     * Thread sleep method
     *
     * @param thisTime - the time to sleep
     */
    @SuppressWarnings("static-access")
    public void sleepy(int thisTime) {
        try {
            thread.sleep(thisTime);
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
        executorPlyr = new CharacterAttacks();
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
        MainWindow.getInstance().systemNotice("Saved File");
        thread.stop(); //stop this thread
        if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.storyMode) && RenderStoryMenu.getInstance().moreStages()) {
            //nextStage if you've won
            if (gameplay.hasWon()) {
                MainWindow.getInstance().getStory().incrementMode();
                winMus.play();
            } else {
                loseMus.play();
            }
            MainWindow.getInstance().getStory().storyProcceed();
            MainWindow.getInstance().nextStage();
        } else {
            //javax.swing.JOptionPane.showMessageDialog(null, "Done");
            if (mo == 0) {
                MainWindow.getInstance().backToMenuScreen();
            } else if (mo == 1) {
                MainWindow.getInstance().backToCharSelect();
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
        RenderGameplay.getInstance().closeAudio();
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
     * Resumes the activity bar
     */
    public void resumeActivityRegenOpp() {
        incrementActivityBarOpp = true;
    }


    private void newInstance() {
        executorAI = new OpponentAttacks();
        ach = LoginScreen.getInstance().getAch();
        if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.storyMode)) {
            storySequence = true;
            time = StoryMode.getInstance().time;
        } //if LAN, client uses hosts time preset
        else if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanClient)) {
            time = MainWindow.getInstance().hostTime;
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
        hpOpp = 0.0f;
        sampleOpp = 00;
        sampleOppDB = 00;
        limitOpp = 290;
        isPaused = false;
        isGameOver = false;
        ach.newInstance();
        LoginScreen.getInstance().newGame = true;
        winMus = new AudioPlayback(AudioPlayback.winSound(), false);
        loseMus = new AudioPlayback(AudioPlayback.loseSound(), false);
        if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.storyMode) == false) {
            RenderGameplay.getInstance().playBGSound();
            musNotice();
            MainWindow.getInstance().systemNotice(MainWindow.getInstance().getAttackOpponent().getOpponent().getBraggingRights(RenderCharacterSelectionScreen.getInstance().getSelectedCharIndex()));
        }
        if (thread != null) {
            thread.resume();
        } else {
            thread = new Thread(this);
            thread.setName("MAIN GAME LOGIC THREAD");
            thread.start();
        }
        MainWindow.getInstance().reSize("menu");
        count2 = 0;
    }

    public void musNotice() {
        MainWindow.getInstance().systemNotice2(RenderStageSelect.getInstance().getAmnientMusicMetaData()[RenderStageSelect.getInstance().getAmbientMusicIndex()]);
    }

    /**
     * Play music once story text is over
     */
    public void playMusicNow() {
        try {
            RenderGameplay.getInstance().playBGSound();
            MainWindow.getInstance().systemNotice(MainWindow.getInstance().getAttackOpponent().getOpponent().getBraggingRights(RenderCharacterSelectionScreen.getInstance().getSelectedCharIndex()));
        } catch (Exception e) {
            System.out.println("Dude, somin went wrong" + e.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }
}

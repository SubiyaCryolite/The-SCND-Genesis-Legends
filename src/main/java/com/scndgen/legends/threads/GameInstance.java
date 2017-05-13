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

import com.scndgen.legends.Achievement;
import com.scndgen.legends.ScndGenLegends;
import com.scndgen.legends.characters.Characters;
import com.scndgen.legends.constants.AudioConstants;
import com.scndgen.legends.controller.StoryMode;
import com.scndgen.legends.enums.AudioType;
import com.scndgen.legends.enums.ModeEnum;
import com.scndgen.legends.enums.SubMode;
import com.scndgen.legends.executers.OpponentAttacks;
import com.scndgen.legends.render.RenderCharacterSelectionScreen;
import com.scndgen.legends.render.RenderGameplay;
import com.scndgen.legends.render.RenderStageSelect;
import com.scndgen.legends.state.GameState;
import com.scndgen.legends.windows.JenesisPanel;
import io.github.subiyacryolite.enginev1.AudioPlayback;
import io.github.subiyacryolite.enginev1.Overlay;

/**
 * This class ensures the game runs at a particular fps and initialises several
 * other threads to execute during runtime
 *
 * @author Ifunga Ndana
 */
public class GameInstance implements Runnable {

    public boolean gameOver, gamePaused, gameRunning;
    public int timeLimit, count2;
    public boolean storySequence;
    private boolean runCharacterAtb = true, runOpponentAtb = true;
    public boolean isRunning = false;
    public String timeStr;//, scene;
    public int time1 = 10, time2 = 10, time3 = 10;
    public boolean aiAttack = false, enemyAiRunning = false;
    public AudioPlayback loseMusic, winMusic;
    private boolean newMatch;
    private Thread thread;
    private int sampleChar;
    private float sampleCharDB;
    private int limitChar;
    private int sampleOpp;
    private float sampleOppDB;
    private int limitOpp;
    private float count = 0.0f;
    private OpponentAttacks executorAI;
    private int speedFactor = 30; //equal to the fps division
    private int matchDuration, playTimeCounter;
    private static GameInstance instance;

    public static synchronized GameInstance getInstance() {
        if (instance == null)
            instance = new GameInstance();
        return instance;
    }

    /**
     * The main game instance thread
     */
    @Override
    public void run() {
        do {
            try {
                thread.sleep(33); // fps
                RenderGameplay.getInstance().matchStatus();
                Achievement.getInstance().scan();
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }
            if (sampleChar <= limitChar && runCharacterAtb) {
                sampleCharDB = sampleCharDB + (Characters.getInstance().getCharRecoverySpeed());
                sampleChar = Integer.parseInt("" + Math.round(sampleCharDB) + "");
            }
            if (sampleOpp <= limitOpp && runOpponentAtb && storySequence == false) {
                sampleOppDB = sampleOppDB + (Characters.getInstance().getOppRecoverySpeed());
                sampleOpp = Integer.parseInt("" + Math.round(sampleOppDB) + "");
            } else if (enemyAiRunning == false && runOpponentAtb && storySequence == false) {
                if (ScndGenLegends.getInstance().getSubMode() == SubMode.SINGLE_PLAYER || ScndGenLegends.getInstance().getSubMode() == SubMode.STORY_MODE) {
                    enemyAiRunning = true;
                    //execute enemy AI
                    //executorAI.attack();
                    {}
                }
            }
            if ((timeLimit <= 180 && storySequence == false)) {
                if (count < 1000) //continue till we make a second
                {
                    count = count + speedFactor;
                } else {
                    try {
                        if (timeLimit < 999 && timeLimit > 0) {
                            timeLimit = timeLimit - 1;
                            timeStr = "" + timeLimit + "";
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
                        }
                    } catch (Exception nfe) {
                        nfe.printStackTrace(System.err);
                    }
                }
            }
        } while (gameRunning);
    }

    /**
     * Get the CharacterEnum recovery units
     *
     * @return CharacterEnum recovery units
     */
    public int getRecoveryUnitsChar() {
        return sampleChar;
    }

    /**
     * Set the CharacterEnum recovery units
     *
     * @param thisNum - CharacterEnum recovery units
     */
    public void setRecoveryUnitsChar(int thisNum) {
        sampleCharDB = (int) Float.parseFloat("" + thisNum + "");
        sampleChar = thisNum;
    }

    /**
     * Gets the CharacterEnum recovery units
     *
     * @return CharacterEnum recovery units
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
            gamePaused = true;
            RenderGameplay.getInstance().pauseThreads();
            thread.suspend();
            executorAI.pause();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    /**
     * Resumes a paused match
     */
    public void resumeGame() {
        try {
            gamePaused = false;
            RenderGameplay.getInstance().resumeThreads();
            thread.resume();
            executorAI.resume();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    /**
     * Performs game over operations, updates player profile and displays Achievements
     */
    public void gameOver() {
        gameRunning = false;
        gameOver = true;
        RenderGameplay.getInstance().closeAudio();
        GameState.getInstance().getLogin().setPlayTime(playTimeCounter);
        Achievement.getInstance().scan();
        //if not story scene, increment char usage
        if (ScndGenLegends.getInstance().getSubMode() == SubMode.STORY_MODE == false) {
            GameState.getInstance().getLogin().setCharacterUsage(RenderCharacterSelectionScreen.getInstance().getCharName());
        }
        if (RenderGameplay.getInstance().hasWon()) {
            RenderGameplay.getInstance().showWinLabel();
            winMusic.play();
        } else {
            RenderGameplay.getInstance().showLoseLabel();
            loseMusic.play();
        }
        RenderStageSelect.getInstance().newInstance();
        RenderCharacterSelectionScreen.getInstance().newInstance();
        RenderGameplay.getInstance().drawAchievements();
    }

    /**
     * Thread sleep method
     *
     * @param thisTime - the timeLimit to sleep
     */
    @SuppressWarnings("static-access")
    public void sleepy(int thisTime) {
        try {
            thread.sleep(thisTime);
        } catch (InterruptedException ex) {
            ex.printStackTrace(System.err);
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
     * This method records play timeLimit
     */
    private void recordPlayTime() {
        matchDuration = 0;
        playTimeCounter = GameState.getInstance().getLogin().getPlayTime();
        new Thread() {
            @Override
            @SuppressWarnings("static-access")
            public void run() {
                do {
                    try {
                        this.sleep(1000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace(System.err);
                    }
                    playTimeCounter++;
                    matchDuration++;
                } while (gameRunning);
            }
        }.start();
    }

    public int getMatchTime() {
        return matchDuration;
    }

    /**
     * This thread executes when a game is over, designed to free unused memory
     */
    public void closingThread(boolean toCharacterSelect) {
        //logic profile operations
        incrementWinsOrLosses();
        GameState.getInstance().getLogin().setPoints(Achievement.getInstance().getNewUserPoints());
        //save profile
        GameState.getInstance().saveConfigFile();
        Overlay.getInstance().primaryNotice("Saved File");
        thread.stop(); //stop this thread
        if (toCharacterSelect) {
            ScndGenLegends.getInstance().loadMode(ModeEnum.MAIN_MENU);
        } else {
            ScndGenLegends.getInstance().loadMode(ModeEnum.CHAR_SELECT_SCREEN);
        }
    }

    private void incrementWinsOrLosses() {
        if (newMatch) {
            newMatch = false;
            GameState.getInstance().getLogin().setNumberOfMatches(GameState.getInstance().getLogin().getNumberOfMatches() + 1);
            if (RenderGameplay.getInstance().getCharacterHp() < RenderGameplay.getInstance().getOpponentHp()) {
                GameState.getInstance().getLogin().setLosses(GameState.getInstance().getLogin().getLosses() + 1);
                GameState.getInstance().getLogin().setConsecutiveWins(0);
            } else {
                GameState.getInstance().getLogin().setWins(GameState.getInstance().getLogin().getWins() + 1);
                GameState.getInstance().getLogin().setConsecutiveWins(GameState.getInstance().getLogin().getConsecutiveWins() + 1);
            }
        }
    }

    /**
     * Cancel the game mid fight
     */
    public void terminateGameplay() {
        gamePaused = false;
        gameRunning = false;
        gameOver = true;
        RenderCharacterSelectionScreen.getInstance().newInstance();
        RenderStageSelect.getInstance().newInstance();
        RenderGameplay.getInstance().closeAudio();
    }


    /**
     * This method prevents the regeneration of Activity while movesare executing
     */
    public void pauseActivityRegen() {
        runCharacterAtb = false;
    }

    /**
     * Resumes the activity bar
     */
    public void resumeActivityRegen() {
        runCharacterAtb = true;
    }

    /**
     * This method prevents the regeneration of Activity while movesare executing
     */
    public void pauseActivityRegenOpp() {
        runOpponentAtb = false;
    }

    /**
     * Resumes the activity bar
     */
    public void resumeActivityRegenOpp() {
        runOpponentAtb = true;
    }


    public void newInstance() {
        executorAI = new OpponentAttacks();
        Achievement.getInstance().newInstance();
        if (ScndGenLegends.getInstance().getSubMode() == SubMode.STORY_MODE) {
            storySequence = true;
            timeLimit = StoryMode.getInstance().time;
        } //if LAN, client uses hosts timeLimit preset
        else if (ScndGenLegends.getInstance().getSubMode() == SubMode.LAN_CLIENT) {
            timeLimit = JenesisPanel.getInstance().hostTime;
        } else {
            timeLimit = GameState.getInstance().getLogin().getTimeLimit();
        }
        gameRunning = true;
        recordPlayTime();
        sampleChar = 00;
        sampleCharDB = 00;
        limitChar = 290;
        sampleOpp = 00;
        sampleOppDB = 00;
        limitOpp = 290;
        gamePaused = false;
        gameOver = false;
        newMatch = true;
        winMusic = new AudioPlayback(AudioConstants.winSound(), AudioType.MUSIC, false);
        loseMusic = new AudioPlayback(AudioConstants.loseSound(), AudioType.MUSIC, false);
        if (ScndGenLegends.getInstance().getSubMode() == SubMode.STORY_MODE == false) {
            RenderGameplay.getInstance().playBGSound();
            musNotice();
            Overlay.getInstance().primaryNotice(RenderGameplay.getInstance().getAttackOpponent().getOpponent().getBraggingRights(RenderCharacterSelectionScreen.getInstance().getSelectedCharIndex()));
        }
        thread = new Thread(this);
        thread.setName("MAIN GAME LOGIC THREAD");
        thread.start();
        count2 = 0;
    }

    public void musNotice() {
        Overlay.getInstance().secondaryNotice(RenderStageSelect.getInstance().getAmbientMusicMetaData()[RenderStageSelect.getInstance().getAmbientMusicIndex()]);
    }

    /**
     * Play music once story text is over
     */
    public void playMusicNow() {
        try {
            RenderGameplay.getInstance().playBGSound();
            Overlay.getInstance().primaryNotice(RenderGameplay.getInstance().getAttackOpponent().getOpponent().getBraggingRights(RenderCharacterSelectionScreen.getInstance().getSelectedCharIndex()));
        } catch (Exception e) {
            System.out.println("Dude, somin went wrong" + e.getMessage());
        }
    }
}

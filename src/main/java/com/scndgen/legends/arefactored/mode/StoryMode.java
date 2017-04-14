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
import com.scndgen.legends.arefactored.render.RenderCharacterSelectionScreen;
import com.scndgen.legends.arefactored.render.RenderStandardGameplay;
import com.scndgen.legends.characters.Character;
import com.scndgen.legends.engine.JenesisLanguage;
import com.scndgen.legends.menus.RenderStageSelect;
import com.scndgen.legends.threads.ThreadGameInstance;
import com.scndgen.legends.threads.ThreadMP3;
import com.scndgen.legends.windows.WindowOptions;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author ndana
 */
public class StoryMode implements Runnable {
    //mp3

    public boolean notAsked, firstRun = true, doneShowingText = false;
    public String stat = "";
    public int max = 11;
    ; // starts from 0, max = last stage or scene-1, scene=max+1
    public int time;
    private ThreadMP3 storyMus;
    private String storyText;
    private int opt, tlkSpeed, modeN;
    //thread
    private Thread thread;
    private static StoryMode instance;

    public static synchronized StoryMode getInstance() {
        if (instance == null)
            instance = new StoryMode();
        return instance;
    }

    public synchronized void newInstance() {
        instance = new StoryMode();
    }

    private StoryMode() {
        stat = "";
        time = 181;
        storyText = "";
        modeN = 0;
    }

    public void story(int stage, boolean start) {
        thread = new Thread(this);
        thread.setName("Story mode thread");
        thread.setPriority(5);
        LoginScreen.getInstance().getMenu().getMain().getStory().setCurrMode(stage);
        storyMus = new ThreadMP3(ThreadMP3.storySound(), false);
        tlkSpeed = WindowOptions.txtSpeed;
        notAsked = true;
        opt = -1;

        if (stage == 0) {
            time = 181;
            stat = "nrml";
            RenderCharacterSelectionScreen.getInstance().selRaila('c');
            RenderCharacterSelectionScreen.getInstance().opponentSelected = false;
            RenderCharacterSelectionScreen.getInstance().selRav('o');
            RenderStageSelect.stage1();
        }

        if (stage == 1) {
            time = 181;
            stat = "nrml";
            RenderCharacterSelectionScreen.getInstance().selLynx('c');
            RenderCharacterSelectionScreen.getInstance().opponentSelected = false;
            RenderCharacterSelectionScreen.getInstance().selRaila('o');
            RenderStageSelect.stage100();
        }

        if (stage == 2) {
            time = 30;
            stat = "nrml";
            RenderCharacterSelectionScreen.getInstance().selAisha('c');
            RenderCharacterSelectionScreen.getInstance().opponentSelected = false;
            RenderCharacterSelectionScreen.getInstance().selLynx('o');
            RenderStageSelect.stage5();
        }


        if (stage == 3) {
            time = 181;
            stat = "nrml";
            RenderCharacterSelectionScreen.getInstance().selRaila('c');
            RenderCharacterSelectionScreen.getInstance().opponentSelected = false;
            RenderCharacterSelectionScreen.getInstance().selSubiya('o');
            RenderStageSelect.stage4();
        }

        if (stage == 4) {
            time = 45;
            stat = "half way";
            RenderCharacterSelectionScreen.getInstance().selRav('c');
            RenderCharacterSelectionScreen.getInstance().opponentSelected = false;
            RenderCharacterSelectionScreen.getInstance().selAde('o');
            RenderStageSelect.stage7();
        }

        if (stage == 5) {
            time = 45;
            stat = "nrml";
            RenderStandardGameplay.getInstance().setNumOfBoards(2);
            RenderCharacterSelectionScreen.getInstance().selAdam('c');
            RenderCharacterSelectionScreen.getInstance().opponentSelected = false;
            RenderCharacterSelectionScreen.getInstance().selJon('o');
            RenderStageSelect.stage7();
        }

        if (stage == 6) {
            time = 181;
            stat = "nrml";
            RenderCharacterSelectionScreen.getInstance().selAza('c');
            RenderCharacterSelectionScreen.getInstance().opponentSelected = false;
            RenderCharacterSelectionScreen.getInstance().selNOVAAdam('o');
            RenderStageSelect.stage10();
        }

        if (stage == 7) {
            time = 181;
            stat = "nrml";
            RenderCharacterSelectionScreen.getInstance().selSubiya('c');
            RenderCharacterSelectionScreen.getInstance().opponentSelected = false;
            RenderCharacterSelectionScreen.getInstance().selRav('o');
            RenderStageSelect.stage2();
        }

        if (stage == 8) {
            time = 181;
            stat = "nrml";
            RenderCharacterSelectionScreen.getInstance().selLynx('c');
            RenderCharacterSelectionScreen.getInstance().opponentSelected = false;
            RenderCharacterSelectionScreen.getInstance().selAdam('o');
            RenderStageSelect.stage10();
        }

        if (stage == 9) {
            time = 60;
            stat = "nrml";
            RenderCharacterSelectionScreen.getInstance().selRaila('c');
            RenderCharacterSelectionScreen.getInstance().opponentSelected = false;
            RenderCharacterSelectionScreen.getInstance().selSorr('o');
            RenderStageSelect.stage10();
        }

        if (stage == 10) {
            time = 90;
            stat = "nrml";
            RenderCharacterSelectionScreen.getInstance().selSubiya('c');
            RenderCharacterSelectionScreen.getInstance().opponentSelected = false;
            RenderCharacterSelectionScreen.getInstance().selNOVAAdam('o');
            RenderStageSelect.stage11();
        }

        if (stage == 11) {
            time = 181;
            stat = "nrml";
            RenderCharacterSelectionScreen.getInstance().selAdam('c');
            RenderCharacterSelectionScreen.getInstance().opponentSelected = false;
            RenderCharacterSelectionScreen.getInstance().selThing('x');
            RenderStageSelect.stage13();
        }

        if (start) {
            startStoryMode(stage);
        }
    }

    public void startStoryMode(int x) {
        modeN = x;
        thread.start();
    }

    /**
     * In story mode chars and opp should generate nothin
     */
    private void storyIn() {
        storyMus.play();
        ThreadGameInstance.storySequence = true;
        doneShowingText = false;
        RenderStandardGameplay.getInstance().getGameInstance().pauseActivityRegen();
        RenderStandardGameplay.getInstance().getGameInstance().pauseActivityRegenOpp();
    }

    private void storyOut(boolean tx) {
        if (tx) {
            storyMus.close();
            RenderStandardGameplay.getInstance().getGameInstance().playMusicNow();
            RenderStandardGameplay.getInstance().getGameInstance().musNotice();
        }
        RenderStandardGameplay.getInstance().charPortBlank();
        RenderStandardGameplay.getInstance().flashyText("");
        thread.stop();
        ThreadGameInstance.storySequence = false;
        doneShowingText = true;
        RenderStandardGameplay.getInstance().getGameInstance().resumeActivityRegen();
        RenderStandardGameplay.getInstance().getGameInstance().resumeActivityRegenOpp();
    }

    @SuppressWarnings("static-access")
    @Override
    public void run() {
        try {
            System.out.println("Stage " + LoginScreen.getInstance().getMenu().getMain().getStory().getStage());

            if (modeN == 0) //scene 1
            {
                LoginScreen.getInstance().getMenu().getMain().storyGame();
                storyIn();
                firstRun = false;
                //set difficulty
                //LoginScreen.difficultyDyn=500;
                //Character.setDamageCounter('o',14);

                thread.sleep(5000);
                RenderStandardGameplay.getInstance().changeStoryBoard(0);

                storyText = JenesisLanguage.getInstance().getLine(174);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);


                storyText = JenesisLanguage.getInstance().getLine(175);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                storyText = JenesisLanguage.getInstance().getLine(176);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);


                RenderStandardGameplay.getInstance().changeStoryBoard(1);
                storyText = JenesisLanguage.getInstance().getLine(177);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(0);
                storyText = JenesisLanguage.getInstance().getLine(178);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(4);
                storyText = JenesisLanguage.getInstance().getLine(179);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(4);
                storyText = JenesisLanguage.getInstance().getLine(372);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(1); //sub
                storyText = JenesisLanguage.getInstance().getLine(180);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(4); //rav
                storyText = JenesisLanguage.getInstance().getLine(181);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(1); //sub
                storyText = JenesisLanguage.getInstance().getLine(182);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);
                RenderStandardGameplay.getInstance().charPortBlank();


                storyOut(false);
            }

            if (modeN == 1) //scene 2
            {
                LoginScreen.getInstance().getMenu().getMain().storyGame();
                storyIn();
                firstRun = false;

                thread.sleep(5000);
                RenderStandardGameplay.getInstance().changeStoryBoard(1);

                storyText = JenesisLanguage.getInstance().getLine(183);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                storyText = JenesisLanguage.getInstance().getLine(184);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                storyText = JenesisLanguage.getInstance().getLine(185);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().changeStoryBoard(2);
                storyText = JenesisLanguage.getInstance().getLine(186);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                storyText = JenesisLanguage.getInstance().getLine(187);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                storyText = JenesisLanguage.getInstance().getLine(146);
                RenderStandardGameplay.getInstance().flashyText(storyText);

                storyOut(false);
            }

            if (modeN == 2) //scene 3
            {
                LoginScreen.getInstance().getMenu().getMain().storyGame();
                storyIn();
                firstRun = false;

                thread.sleep(5000);
                RenderStandardGameplay.getInstance().changeStoryBoard(2);

                RenderStandardGameplay.getInstance().charPortSet(2); //lynx
                storyText = JenesisLanguage.getInstance().getLine(188);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(0); //raila
                storyText = JenesisLanguage.getInstance().getLine(189);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(2); //lynx
                storyText = JenesisLanguage.getInstance().getLine(190) + " .......";
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(3); //aisha
                storyText = JenesisLanguage.getInstance().getLine(191);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(2);
                storyText = JenesisLanguage.getInstance().getLine(192);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(3);
                storyText = JenesisLanguage.getInstance().getLine(193);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(2);
                storyText = JenesisLanguage.getInstance().getLine(194);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(3);
                storyText = JenesisLanguage.getInstance().getLine(195);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(2);
                storyText = JenesisLanguage.getInstance().getLine(196);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(0);
                storyText = JenesisLanguage.getInstance().getLine(197);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(1);
                storyText = JenesisLanguage.getInstance().getLine(198);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().changeStoryBoard(3);
                RenderStandardGameplay.getInstance().charPortBlank();
                storyText = JenesisLanguage.getInstance().getLine(199);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(3);
                storyText = JenesisLanguage.getInstance().getLine(200);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(2);
                storyText = JenesisLanguage.getInstance().getLine(201);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(3);
                storyText = JenesisLanguage.getInstance().getLine(202);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(2);
                storyText = JenesisLanguage.getInstance().getLine(203);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(3);
                storyText = JenesisLanguage.getInstance().getLine(204);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(2);
                storyText = JenesisLanguage.getInstance().getLine(205);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                storyOut(false);
            }

            if (modeN == 3) //scene 4
            {
                LoginScreen.getInstance().getMenu().getMain().storyGame();
                storyIn();
                firstRun = false;

                thread.sleep(5000);
                RenderStandardGameplay.getInstance().changeStoryBoard(3);

                RenderStandardGameplay.getInstance().charPortSet(1);
                storyText = JenesisLanguage.getInstance().getLine(206);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(1);
                storyText = JenesisLanguage.getInstance().getLine(207);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(1);
                storyText = JenesisLanguage.getInstance().getLine(208);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().changeStoryBoard(5);
                RenderStandardGameplay.getInstance().charPortSet(0);
                storyText = JenesisLanguage.getInstance().getLine(209);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(1);
                storyText = JenesisLanguage.getInstance().getLine(210);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(0);
                storyText = JenesisLanguage.getInstance().getLine(211);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(0);
                storyText = JenesisLanguage.getInstance().getLine(212);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(0);
                storyText = JenesisLanguage.getInstance().getLine(213);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(0);
                storyText = JenesisLanguage.getInstance().getLine(214);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(0);
                storyText = JenesisLanguage.getInstance().getLine(215);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(1);
                storyText = JenesisLanguage.getInstance().getLine(216);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(0);
                storyText = JenesisLanguage.getInstance().getLine(203);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(1);
                storyText = JenesisLanguage.getInstance().getLine(217);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                storyOut(false);
            }

            if (modeN == 4) //scene 5
            {
                LoginScreen.getInstance().getMenu().getMain().storyGame();
                storyIn();
                firstRun = false;

                thread.sleep(5000);
                RenderStandardGameplay.getInstance().changeStoryBoard(5);

                storyText = JenesisLanguage.getInstance().getLine(218) + " .......";
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                storyText = JenesisLanguage.getInstance().getLine(219);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(5); //ade
                storyText = JenesisLanguage.getInstance().getLine(220);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(10); //sorrowe
                storyText = JenesisLanguage.getInstance().getLine(221);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(5); //ade
                storyText = JenesisLanguage.getInstance().getLine(222);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(4); //ravage
                storyText = JenesisLanguage.getInstance().getLine(223);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(5); //ade
                storyText = JenesisLanguage.getInstance().getLine(224);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(10);
                storyText = JenesisLanguage.getInstance().getLine(225);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(5); //ade
                storyText = JenesisLanguage.getInstance().getLine(226);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(6); //jonah
                storyText = JenesisLanguage.getInstance().getLine(227);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(4);
                storyText = JenesisLanguage.getInstance().getLine(228);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(4);
                storyText = JenesisLanguage.getInstance().getLine(229);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(5);//ade
                storyText = JenesisLanguage.getInstance().getLine(230);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                storyOut(false);
            }

            if (modeN == 5) //scene 6
            {
                LoginScreen.getInstance().getMenu().getMain().storyGame();
                storyIn();
                firstRun = false;

                thread.sleep(5000);
                RenderStandardGameplay.getInstance().changeStoryBoard(5);

                RenderStandardGameplay.getInstance().charPortSet(4);
                storyText = JenesisLanguage.getInstance().getLine(231);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(5);//ade
                storyText = JenesisLanguage.getInstance().getLine(232);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(4);
                storyText = JenesisLanguage.getInstance().getLine(233);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(6);
                storyText = JenesisLanguage.getInstance().getLine(234);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(7);
                storyText = JenesisLanguage.getInstance().getLine(235);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(6);
                storyText = JenesisLanguage.getInstance().getLine(236);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(7);
                storyText = JenesisLanguage.getInstance().getLine(237);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(6);
                storyText = JenesisLanguage.getInstance().getLine(238);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(7);
                storyText = JenesisLanguage.getInstance().getLine(239);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(6);
                storyText = JenesisLanguage.getInstance().getLine(240);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(7);
                storyText = JenesisLanguage.getInstance().getLine(241);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(6);
                storyText = JenesisLanguage.getInstance().getLine(242);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(7);
                storyText = JenesisLanguage.getInstance().getLine(243);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(6);
                storyText = JenesisLanguage.getInstance().getLine(244);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(6);
                storyText = JenesisLanguage.getInstance().getLine(245);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(5);
                storyText = JenesisLanguage.getInstance().getLine(246);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(7);
                storyText = JenesisLanguage.getInstance().getLine(247);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(7);
                storyText = JenesisLanguage.getInstance().getLine(248);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(6);
                storyText = JenesisLanguage.getInstance().getLine(249);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(7);
                storyText = JenesisLanguage.getInstance().getLine(250);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                storyOut(false);
            }

            if (modeN == 6) //scene 7
            {
                LoginScreen.getInstance().getMenu().getMain().storyGame();
                storyIn();
                firstRun = false;

                thread.sleep(5000);
                RenderStandardGameplay.getInstance().changeStoryBoard(4);

                storyText = JenesisLanguage.getInstance().getLine(251);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                storyText = JenesisLanguage.getInstance().getLine(252);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                storyText = JenesisLanguage.getInstance().getLine(253);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().changeStoryBoard(6);
                storyText = JenesisLanguage.getInstance().getLine(254);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(9);
                storyText = JenesisLanguage.getInstance().getLine(255);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(7);
                storyText = JenesisLanguage.getInstance().getLine(256);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(9);
                storyText = JenesisLanguage.getInstance().getLine(257);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(7);
                storyText = JenesisLanguage.getInstance().getLine(258);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(7);
                storyText = JenesisLanguage.getInstance().getLine(259);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(9);
                storyText = JenesisLanguage.getInstance().getLine(260);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(7);
                storyText = JenesisLanguage.getInstance().getLine(261);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(7);
                storyText = JenesisLanguage.getInstance().getLine(262);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                storyOut(false);
            }


            if (modeN == 7) //scene 8
            {
                LoginScreen.getInstance().getMenu().getMain().storyGame();
                storyIn();
                firstRun = false;

                thread.sleep(5000);
                RenderStandardGameplay.getInstance().changeStoryBoard(6);

                RenderStandardGameplay.getInstance().charPortSet(1);
                storyText = JenesisLanguage.getInstance().getLine(263);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(0);
                storyText = JenesisLanguage.getInstance().getLine(264);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(4);
                storyText = JenesisLanguage.getInstance().getLine(265);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(0);
                storyText = JenesisLanguage.getInstance().getLine(266);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(4);
                storyText = JenesisLanguage.getInstance().getLine(267);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(1);
                storyText = JenesisLanguage.getInstance().getLine(268);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(0);
                storyText = ".......... " + JenesisLanguage.getInstance().getLine(269);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                storyOut(false);
            }

            if (modeN == 8) //scene 9
            {
                LoginScreen.getInstance().getMenu().getMain().storyGame();
                storyIn();
                firstRun = false;

                thread.sleep(5000);
                RenderStandardGameplay.getInstance().changeStoryBoard(8);


                storyText = JenesisLanguage.getInstance().getLine(270);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(7);
                storyText = JenesisLanguage.getInstance().getLine(271);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(7);
                storyText = JenesisLanguage.getInstance().getLine(272);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(9);
                storyText = JenesisLanguage.getInstance().getLine(273);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(7);
                storyText = JenesisLanguage.getInstance().getLine(274);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(2);
                storyText = JenesisLanguage.getInstance().getLine(275);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(9);
                storyText = JenesisLanguage.getInstance().getLine(276);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(7);
                storyText = JenesisLanguage.getInstance().getLine(277);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(7);
                storyText = JenesisLanguage.getInstance().getLine(231) + " !!!";
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(2);
                storyText = JenesisLanguage.getInstance().getLine(278);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(7);
                storyText = JenesisLanguage.getInstance().getLine(279) + " !!!";
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                storyOut(false);
            }

            //new stage they arrive

            if (modeN == 9) //scene 10
            {
                LoginScreen.getInstance().getMenu().getMain().storyGame();
                storyIn();
                firstRun = false;

                thread.sleep(5000);
                RenderStandardGameplay.getInstance().changeStoryBoard(9);

                RenderStandardGameplay.getInstance().charPortSet(0);
                storyText = JenesisLanguage.getInstance().getLine(280) + " !!!";
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(9);
                storyText = JenesisLanguage.getInstance().getLine(281);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(0);
                storyText = JenesisLanguage.getInstance().getLine(282);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(9);
                storyText = JenesisLanguage.getInstance().getLine(283);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(0);
                storyText = JenesisLanguage.getInstance().getLine(284);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(0);
                storyText = JenesisLanguage.getInstance().getLine(285);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(9);
                storyText = JenesisLanguage.getInstance().getLine(286);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortBlank();
                storyText = JenesisLanguage.getInstance().getLine(287) + " ...";
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(10);
                storyText = JenesisLanguage.getInstance().getLine(288);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(0);
                storyText = JenesisLanguage.getInstance().getLine(289) + " !!";
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(10);
                storyText = JenesisLanguage.getInstance().getLine(290);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(0);
                storyText = JenesisLanguage.getInstance().getLine(291);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(0);
                storyText = JenesisLanguage.getInstance().getLine(292);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(10);
                storyText = JenesisLanguage.getInstance().getLine(293);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                storyOut(false);
            }

            if (modeN == 10) //scene 11
            {
                LoginScreen.getInstance().getMenu().getMain().storyGame();
                storyIn();
                firstRun = false;

                thread.sleep(5000);
                RenderStandardGameplay.getInstance().changeStoryBoard(11);

                //set difficulty - hard
                Character.setDamageCounter('o', 18);

                RenderStandardGameplay.getInstance().charPortSet(10);
                storyText = JenesisLanguage.getInstance().getLine(294) + " !!!";
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(0);
                storyText = JenesisLanguage.getInstance().getLine(231) + " !!!";
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(1);
                storyText = JenesisLanguage.getInstance().getLine(295);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(0);
                storyText = JenesisLanguage.getInstance().getLine(296);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(10);
                storyText = JenesisLanguage.getInstance().getLine(297);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(0);
                storyText = JenesisLanguage.getInstance().getLine(298);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(10);
                storyText = JenesisLanguage.getInstance().getLine(299) + "!!";
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(7);
                storyText = JenesisLanguage.getInstance().getLine(300) + " !!!";
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(1);
                storyText = JenesisLanguage.getInstance().getLine(301) + " ?";
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(7);
                storyText = JenesisLanguage.getInstance().getLine(302) + " !!!!!!!!!!!!!!";
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(1);
                storyText = JenesisLanguage.getInstance().getLine(303) + " !!!";
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(2);
                storyText = JenesisLanguage.getInstance().getLine(304);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(1);
                storyText = JenesisLanguage.getInstance().getLine(305);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(7);
                storyText = JenesisLanguage.getInstance().getLine(306);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                storyOut(false);
            }

            if (modeN == 11) //scene 12
            {
                LoginScreen.getInstance().getMenu().getMain().storyGame();
                storyIn();
                firstRun = false;

                thread.sleep(5000);
                RenderStandardGameplay.getInstance().changeStoryBoard(10);

                RenderStandardGameplay.getInstance().charPortSet(10);
                storyText = JenesisLanguage.getInstance().getLine(373);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortBlank();
                storyText = JenesisLanguage.getInstance().getLine(374);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(1);
                storyText = JenesisLanguage.getInstance().getLine(375);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(9);
                storyText = JenesisLanguage.getInstance().getLine(376);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(1);
                storyText = JenesisLanguage.getInstance().getLine(377);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortBlank();
                storyText = JenesisLanguage.getInstance().getLine(378);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(4);
                storyText = JenesisLanguage.getInstance().getLine(379);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(5);
                storyText = JenesisLanguage.getInstance().getLine(380);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                storyText = JenesisLanguage.getInstance().getLine(381);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(8);
                storyText = JenesisLanguage.getInstance().getLine(383);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(11);
                storyText = JenesisLanguage.getInstance().getLine(384);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(10);
                storyText = JenesisLanguage.getInstance().getLine(385);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(9);
                storyText = JenesisLanguage.getInstance().getLine(386);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(8);
                storyText = JenesisLanguage.getInstance().getLine(387);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(6);
                storyText = JenesisLanguage.getInstance().getLine(388);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(10);
                storyText = JenesisLanguage.getInstance().getLine(389);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(8);
                storyText = JenesisLanguage.getInstance().getLine(390);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(8);
                storyText = JenesisLanguage.getInstance().getLine(391);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderStandardGameplay.getInstance().charPortSet(6);
                storyText = JenesisLanguage.getInstance().getLine(392);
                RenderStandardGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                storyOut(false);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(StoryMode.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void pauseDialogue() {
        thread.suspend();
    }

    public void resumeDialogue() {
        thread.resume();
    }

    public void skipDialogue() {
        thread.stop();
        storyOut(true);
    }
}

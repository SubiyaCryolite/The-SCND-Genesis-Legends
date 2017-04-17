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
package com.scndgen.legends.controller;

import com.scndgen.legends.Language;
import com.scndgen.legends.characters.Characters;
import com.scndgen.legends.enums.Stage;
import com.scndgen.legends.render.RenderCharacterSelectionScreen;
import com.scndgen.legends.render.RenderGameplay;
import com.scndgen.legends.render.RenderStageSelect;
import com.scndgen.legends.threads.AudioPlayback;
import com.scndgen.legends.threads.ThreadGameInstance;
import com.scndgen.legends.windows.MainWindow;
import com.scndgen.legends.windows.WindowOptions;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author ndana
 */
public class StoryMode implements Runnable {
    //mp3

    private static StoryMode instance;
    public boolean notAsked, firstRun = true, doneShowingText = false;
    public String stat = "";
    public int max = 11;
    public int time;
    private AudioPlayback storyMus;
    private String storyText;
    private int opt, tlkSpeed, modeN;
    //thread
    private Thread thread;

    private StoryMode() {
        stat = "";
        time = 181;
        storyText = "";
        modeN = 0;
    }

    public static synchronized StoryMode getInstance() {
        if (instance == null)
            instance = new StoryMode();
        return instance;
    }

    public synchronized void newInstance() {
        instance = new StoryMode();
    }

    public void story(int scene, boolean start) {
        thread = new Thread(this);
        thread.setName("Story scene thread");
        thread.setPriority(5);
        MainWindow.getInstance().getStory().setCurrMode(Stage.RANDOM); //TODO see effects of this
        storyMus = new AudioPlayback(AudioPlayback.storySound(), false);
        tlkSpeed = WindowOptions.txtSpeed;
        notAsked = true;
        opt = -1;
        switch (scene) {
            case 0:
                time = 181;
                stat = "nrml";
                RenderCharacterSelectionScreen.getInstance().selRaila('c');
                RenderCharacterSelectionScreen.getInstance().setOpponentSelected(false);
                RenderCharacterSelectionScreen.getInstance().selRav('o');
                RenderStageSelect.getInstance().selectStage(Stage.IBEX_HILL);
                break;
            case 1:
                time = 181;
                stat = "nrml";
                RenderCharacterSelectionScreen.getInstance().selLynx('c');
                RenderCharacterSelectionScreen.getInstance().setOpponentSelected(false);
                RenderCharacterSelectionScreen.getInstance().selRaila('o');
                RenderStageSelect.getInstance().selectStage(Stage.DISTANT_ISLE);
                break;
            case 2:
                time = 30;
                stat = "nrml";
                RenderCharacterSelectionScreen.getInstance().selAisha('c');
                RenderCharacterSelectionScreen.getInstance().setOpponentSelected(false);
                RenderCharacterSelectionScreen.getInstance().selLynx('o');
                RenderStageSelect.getInstance().selectStage(Stage.IBEX_HILL_NIGHT);
                break;
            case 3:
                time = 181;
                stat = "nrml";
                RenderCharacterSelectionScreen.getInstance().selRaila('c');
                RenderCharacterSelectionScreen.getInstance().setOpponentSelected(false);
                RenderCharacterSelectionScreen.getInstance().selSubiya('o');
                RenderStageSelect.getInstance().selectStage(Stage.CHELSTON_CITY_STREETS);
                break;
            case 4:
                time = 45;
                stat = "half way";
                RenderCharacterSelectionScreen.getInstance().selRav('c');
                RenderCharacterSelectionScreen.getInstance().setOpponentSelected(false);
                RenderCharacterSelectionScreen.getInstance().selAde('o');
                RenderStageSelect.getInstance().selectStage(Stage.FROZEN_WILDERNESS);
                break;
            case 5:
                time = 45;
                stat = "nrml";
                RenderGameplay.getInstance().setNumOfBoards(2);
                RenderCharacterSelectionScreen.getInstance().selAdam('c');
                RenderCharacterSelectionScreen.getInstance().setOpponentSelected(false);
                RenderCharacterSelectionScreen.getInstance().selJon('o');
                RenderStageSelect.getInstance().selectStage(Stage.FROZEN_WILDERNESS);
                break;
            case 6:
                time = 181;
                stat = "nrml";
                RenderCharacterSelectionScreen.getInstance().selAza('c');
                RenderCharacterSelectionScreen.getInstance().setOpponentSelected(false);
                RenderCharacterSelectionScreen.getInstance().selNOVAAdam('o');
                RenderStageSelect.getInstance().selectStage(Stage.APOCALYPTO);
                break;
            case 7:
                time = 181;
                stat = "nrml";
                RenderCharacterSelectionScreen.getInstance().selSubiya('c');
                RenderCharacterSelectionScreen.getInstance().setOpponentSelected(false);
                RenderCharacterSelectionScreen.getInstance().selRav('o');
                RenderStageSelect.getInstance().selectStage(Stage.CHELSTON_CITY_DOCKS);
                break;
            case 8:
                time = 181;
                stat = "nrml";
                RenderCharacterSelectionScreen.getInstance().selLynx('c');
                RenderCharacterSelectionScreen.getInstance().setOpponentSelected(false);
                RenderCharacterSelectionScreen.getInstance().selAdam('o');
                RenderStageSelect.getInstance().selectStage(Stage.APOCALYPTO);
                break;
            case 9:
                time = 60;
                stat = "nrml";
                RenderCharacterSelectionScreen.getInstance().selRaila('c');
                RenderCharacterSelectionScreen.getInstance().setOpponentSelected(false);
                RenderCharacterSelectionScreen.getInstance().selSorr('o');
                RenderStageSelect.getInstance().selectStage(Stage.APOCALYPTO);
                break;
            case 10:
                time = 90;
                stat = "nrml";
                RenderCharacterSelectionScreen.getInstance().selSubiya('c');
                RenderCharacterSelectionScreen.getInstance().setOpponentSelected(false);
                RenderCharacterSelectionScreen.getInstance().selNOVAAdam('o');
                RenderStageSelect.getInstance().selectStage(Stage.DISTANT_ISLE_NIGHT);
                break;
            case 11:
                time = 181;
                stat = "nrml";
                RenderCharacterSelectionScreen.getInstance().selAdam('c');
                RenderCharacterSelectionScreen.getInstance().setOpponentSelected(false);
                RenderCharacterSelectionScreen.getInstance().selThing('x');
                RenderStageSelect.getInstance().selectStage(Stage.DESERT_RUINS_NIGHT);
                break;
        }
        if (start) {
            startStoryMode(scene);
        }
    }

    public void startStoryMode(int x) {
        RenderGameplay.getInstance().newInstance();
        modeN = x;
        thread.start();
    }

    /**
     * In story scene chars and opp should generate nothin
     */
    private void storyIn() {
        storyMus.play();
        ThreadGameInstance.storySequence = true;
        doneShowingText = false;
        RenderGameplay.getInstance().getGameInstance().pauseActivityRegen();
        RenderGameplay.getInstance().getGameInstance().pauseActivityRegenOpp();
    }

    private void storyOut(boolean tx) {
        if (tx) {
            storyMus.close();
            RenderGameplay.getInstance().getGameInstance().playMusicNow();
            RenderGameplay.getInstance().getGameInstance().musNotice();
        }
        RenderGameplay.getInstance().charPortBlank();
        RenderGameplay.getInstance().flashyText("");
        thread.stop();
        ThreadGameInstance.storySequence = false;
        doneShowingText = true;
        RenderGameplay.getInstance().getGameInstance().resumeActivityRegen();
        RenderGameplay.getInstance().getGameInstance().resumeActivityRegenOpp();
    }

    @SuppressWarnings("static-access")
    @Override
    public void run() {
        try {
            System.out.println("Stage " + MainWindow.getInstance().getStory().getStage());

            if (modeN == 0) //scene 1
            {
                MainWindow.getInstance().storyGame();
                storyIn();
                firstRun = false;
                //set difficulty
                //LoginScreen.difficultyDyn=500;
                //Character.setDamageCounter('o',14);

                thread.sleep(5000);
                RenderGameplay.getInstance().changeStoryBoard(0);

                storyText = Language.getInstance().getLine(174);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);


                storyText = Language.getInstance().getLine(175);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                storyText = Language.getInstance().getLine(176);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);


                RenderGameplay.getInstance().changeStoryBoard(1);
                storyText = Language.getInstance().getLine(177);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(0);
                storyText = Language.getInstance().getLine(178);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(4);
                storyText = Language.getInstance().getLine(179);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(4);
                storyText = Language.getInstance().getLine(372);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(1); //sub
                storyText = Language.getInstance().getLine(180);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(4); //rav
                storyText = Language.getInstance().getLine(181);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(1); //sub
                storyText = Language.getInstance().getLine(182);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);
                RenderGameplay.getInstance().charPortBlank();


                storyOut(false);
            }

            if (modeN == 1) //scene 2
            {
                MainWindow.getInstance().storyGame();
                storyIn();
                firstRun = false;

                thread.sleep(5000);
                RenderGameplay.getInstance().changeStoryBoard(1);

                storyText = Language.getInstance().getLine(183);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                storyText = Language.getInstance().getLine(184);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                storyText = Language.getInstance().getLine(185);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().changeStoryBoard(2);
                storyText = Language.getInstance().getLine(186);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                storyText = Language.getInstance().getLine(187);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                storyText = Language.getInstance().getLine(146);
                RenderGameplay.getInstance().flashyText(storyText);

                storyOut(false);
            }

            if (modeN == 2) //scene 3
            {
                MainWindow.getInstance().storyGame();
                storyIn();
                firstRun = false;

                thread.sleep(5000);
                RenderGameplay.getInstance().changeStoryBoard(2);

                RenderGameplay.getInstance().charPortSet(2); //lynx
                storyText = Language.getInstance().getLine(188);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(0); //raila
                storyText = Language.getInstance().getLine(189);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(2); //lynx
                storyText = Language.getInstance().getLine(190) + " .......";
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(3); //aisha
                storyText = Language.getInstance().getLine(191);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(2);
                storyText = Language.getInstance().getLine(192);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(3);
                storyText = Language.getInstance().getLine(193);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(2);
                storyText = Language.getInstance().getLine(194);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(3);
                storyText = Language.getInstance().getLine(195);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(2);
                storyText = Language.getInstance().getLine(196);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(0);
                storyText = Language.getInstance().getLine(197);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(1);
                storyText = Language.getInstance().getLine(198);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().changeStoryBoard(3);
                RenderGameplay.getInstance().charPortBlank();
                storyText = Language.getInstance().getLine(199);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(3);
                storyText = Language.getInstance().getLine(200);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(2);
                storyText = Language.getInstance().getLine(201);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(3);
                storyText = Language.getInstance().getLine(202);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(2);
                storyText = Language.getInstance().getLine(203);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(3);
                storyText = Language.getInstance().getLine(204);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(2);
                storyText = Language.getInstance().getLine(205);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                storyOut(false);
            }

            if (modeN == 3) //scene 4
            {
                MainWindow.getInstance().storyGame();
                storyIn();
                firstRun = false;

                thread.sleep(5000);
                RenderGameplay.getInstance().changeStoryBoard(3);

                RenderGameplay.getInstance().charPortSet(1);
                storyText = Language.getInstance().getLine(206);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(1);
                storyText = Language.getInstance().getLine(207);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(1);
                storyText = Language.getInstance().getLine(208);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().changeStoryBoard(5);
                RenderGameplay.getInstance().charPortSet(0);
                storyText = Language.getInstance().getLine(209);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(1);
                storyText = Language.getInstance().getLine(210);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(0);
                storyText = Language.getInstance().getLine(211);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(0);
                storyText = Language.getInstance().getLine(212);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(0);
                storyText = Language.getInstance().getLine(213);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(0);
                storyText = Language.getInstance().getLine(214);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(0);
                storyText = Language.getInstance().getLine(215);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(1);
                storyText = Language.getInstance().getLine(216);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(0);
                storyText = Language.getInstance().getLine(203);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(1);
                storyText = Language.getInstance().getLine(217);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                storyOut(false);
            }

            if (modeN == 4) //scene 5
            {
                MainWindow.getInstance().storyGame();
                storyIn();
                firstRun = false;

                thread.sleep(5000);
                RenderGameplay.getInstance().changeStoryBoard(5);

                storyText = Language.getInstance().getLine(218) + " .......";
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                storyText = Language.getInstance().getLine(219);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(5); //ade
                storyText = Language.getInstance().getLine(220);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(10); //sorrowe
                storyText = Language.getInstance().getLine(221);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(5); //ade
                storyText = Language.getInstance().getLine(222);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(4); //ravage
                storyText = Language.getInstance().getLine(223);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(5); //ade
                storyText = Language.getInstance().getLine(224);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(10);
                storyText = Language.getInstance().getLine(225);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(5); //ade
                storyText = Language.getInstance().getLine(226);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(6); //jonah
                storyText = Language.getInstance().getLine(227);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(4);
                storyText = Language.getInstance().getLine(228);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(4);
                storyText = Language.getInstance().getLine(229);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(5);//ade
                storyText = Language.getInstance().getLine(230);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                storyOut(false);
            }

            if (modeN == 5) //scene 6
            {
                MainWindow.getInstance().storyGame();
                storyIn();
                firstRun = false;

                thread.sleep(5000);
                RenderGameplay.getInstance().changeStoryBoard(5);

                RenderGameplay.getInstance().charPortSet(4);
                storyText = Language.getInstance().getLine(231);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(5);//ade
                storyText = Language.getInstance().getLine(232);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(4);
                storyText = Language.getInstance().getLine(233);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(6);
                storyText = Language.getInstance().getLine(234);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(7);
                storyText = Language.getInstance().getLine(235);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(6);
                storyText = Language.getInstance().getLine(236);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(7);
                storyText = Language.getInstance().getLine(237);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(6);
                storyText = Language.getInstance().getLine(238);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(7);
                storyText = Language.getInstance().getLine(239);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(6);
                storyText = Language.getInstance().getLine(240);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(7);
                storyText = Language.getInstance().getLine(241);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(6);
                storyText = Language.getInstance().getLine(242);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(7);
                storyText = Language.getInstance().getLine(243);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(6);
                storyText = Language.getInstance().getLine(244);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(6);
                storyText = Language.getInstance().getLine(245);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(5);
                storyText = Language.getInstance().getLine(246);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(7);
                storyText = Language.getInstance().getLine(247);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(7);
                storyText = Language.getInstance().getLine(248);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(6);
                storyText = Language.getInstance().getLine(249);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(7);
                storyText = Language.getInstance().getLine(250);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                storyOut(false);
            }

            if (modeN == 6) //scene 7
            {
                MainWindow.getInstance().storyGame();
                storyIn();
                firstRun = false;

                thread.sleep(5000);
                RenderGameplay.getInstance().changeStoryBoard(4);

                storyText = Language.getInstance().getLine(251);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                storyText = Language.getInstance().getLine(252);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                storyText = Language.getInstance().getLine(253);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().changeStoryBoard(6);
                storyText = Language.getInstance().getLine(254);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(9);
                storyText = Language.getInstance().getLine(255);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(7);
                storyText = Language.getInstance().getLine(256);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(9);
                storyText = Language.getInstance().getLine(257);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(7);
                storyText = Language.getInstance().getLine(258);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(7);
                storyText = Language.getInstance().getLine(259);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(9);
                storyText = Language.getInstance().getLine(260);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(7);
                storyText = Language.getInstance().getLine(261);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(7);
                storyText = Language.getInstance().getLine(262);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                storyOut(false);
            }


            if (modeN == 7) //scene 8
            {
                MainWindow.getInstance().storyGame();
                storyIn();
                firstRun = false;

                thread.sleep(5000);
                RenderGameplay.getInstance().changeStoryBoard(6);

                RenderGameplay.getInstance().charPortSet(1);
                storyText = Language.getInstance().getLine(263);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(0);
                storyText = Language.getInstance().getLine(264);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(4);
                storyText = Language.getInstance().getLine(265);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(0);
                storyText = Language.getInstance().getLine(266);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(4);
                storyText = Language.getInstance().getLine(267);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(1);
                storyText = Language.getInstance().getLine(268);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(0);
                storyText = ".......... " + Language.getInstance().getLine(269);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                storyOut(false);
            }

            if (modeN == 8) //scene 9
            {
                MainWindow.getInstance().storyGame();
                storyIn();
                firstRun = false;

                thread.sleep(5000);
                RenderGameplay.getInstance().changeStoryBoard(8);


                storyText = Language.getInstance().getLine(270);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(7);
                storyText = Language.getInstance().getLine(271);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(7);
                storyText = Language.getInstance().getLine(272);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(9);
                storyText = Language.getInstance().getLine(273);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(7);
                storyText = Language.getInstance().getLine(274);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(2);
                storyText = Language.getInstance().getLine(275);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(9);
                storyText = Language.getInstance().getLine(276);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(7);
                storyText = Language.getInstance().getLine(277);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(7);
                storyText = Language.getInstance().getLine(231) + " !!!";
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(2);
                storyText = Language.getInstance().getLine(278);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(7);
                storyText = Language.getInstance().getLine(279) + " !!!";
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                storyOut(false);
            }

            //new stage they arrive

            if (modeN == 9) //scene 10
            {
                MainWindow.getInstance().storyGame();
                storyIn();
                firstRun = false;

                thread.sleep(5000);
                RenderGameplay.getInstance().changeStoryBoard(9);

                RenderGameplay.getInstance().charPortSet(0);
                storyText = Language.getInstance().getLine(280) + " !!!";
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(9);
                storyText = Language.getInstance().getLine(281);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(0);
                storyText = Language.getInstance().getLine(282);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(9);
                storyText = Language.getInstance().getLine(283);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(0);
                storyText = Language.getInstance().getLine(284);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(0);
                storyText = Language.getInstance().getLine(285);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(9);
                storyText = Language.getInstance().getLine(286);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortBlank();
                storyText = Language.getInstance().getLine(287) + " ...";
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(10);
                storyText = Language.getInstance().getLine(288);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(0);
                storyText = Language.getInstance().getLine(289) + " !!";
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(10);
                storyText = Language.getInstance().getLine(290);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(0);
                storyText = Language.getInstance().getLine(291);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(0);
                storyText = Language.getInstance().getLine(292);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(10);
                storyText = Language.getInstance().getLine(293);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                storyOut(false);
            }

            if (modeN == 10) //scene 11
            {
                MainWindow.getInstance().storyGame();
                storyIn();
                firstRun = false;

                thread.sleep(5000);
                RenderGameplay.getInstance().changeStoryBoard(11);

                //set difficulty - hard
                Characters.setDamageCounter('o', 18);

                RenderGameplay.getInstance().charPortSet(10);
                storyText = Language.getInstance().getLine(294) + " !!!";
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(0);
                storyText = Language.getInstance().getLine(231) + " !!!";
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(1);
                storyText = Language.getInstance().getLine(295);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(0);
                storyText = Language.getInstance().getLine(296);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(10);
                storyText = Language.getInstance().getLine(297);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(0);
                storyText = Language.getInstance().getLine(298);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(10);
                storyText = Language.getInstance().getLine(299) + "!!";
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(7);
                storyText = Language.getInstance().getLine(300) + " !!!";
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(1);
                storyText = Language.getInstance().getLine(301) + " ?";
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(7);
                storyText = Language.getInstance().getLine(302) + " !!!!!!!!!!!!!!";
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(1);
                storyText = Language.getInstance().getLine(303) + " !!!";
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(2);
                storyText = Language.getInstance().getLine(304);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(1);
                storyText = Language.getInstance().getLine(305);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(7);
                storyText = Language.getInstance().getLine(306);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                storyOut(false);
            }

            if (modeN == 11) //scene 12
            {
                MainWindow.getInstance().storyGame();
                storyIn();
                firstRun = false;

                thread.sleep(5000);
                RenderGameplay.getInstance().changeStoryBoard(10);

                RenderGameplay.getInstance().charPortSet(10);
                storyText = Language.getInstance().getLine(373);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortBlank();
                storyText = Language.getInstance().getLine(374);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(1);
                storyText = Language.getInstance().getLine(375);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(9);
                storyText = Language.getInstance().getLine(376);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(1);
                storyText = Language.getInstance().getLine(377);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortBlank();
                storyText = Language.getInstance().getLine(378);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(4);
                storyText = Language.getInstance().getLine(379);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(5);
                storyText = Language.getInstance().getLine(380);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                storyText = Language.getInstance().getLine(381);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(8);
                storyText = Language.getInstance().getLine(383);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(11);
                storyText = Language.getInstance().getLine(384);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(10);
                storyText = Language.getInstance().getLine(385);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(9);
                storyText = Language.getInstance().getLine(386);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(8);
                storyText = Language.getInstance().getLine(387);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(6);
                storyText = Language.getInstance().getLine(388);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(10);
                storyText = Language.getInstance().getLine(389);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(8);
                storyText = Language.getInstance().getLine(390);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(8);
                storyText = Language.getInstance().getLine(391);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortSet(6);
                storyText = Language.getInstance().getLine(392);
                RenderGameplay.getInstance().flashyText(storyText);
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

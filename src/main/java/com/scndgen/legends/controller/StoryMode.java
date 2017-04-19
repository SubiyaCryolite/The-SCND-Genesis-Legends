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
import com.scndgen.legends.enums.CharacterState;
import com.scndgen.legends.enums.Stage;
import com.scndgen.legends.render.RenderCharacterSelectionScreen;
import com.scndgen.legends.render.RenderGameplay;
import com.scndgen.legends.render.RenderStageSelect;
import com.scndgen.legends.render.RenderStoryMenu;
import com.scndgen.legends.threads.AudioPlayback;
import com.scndgen.legends.threads.GameInstance;
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
    public final int max = 11;
    public int time;
    private AudioPlayback storyMus;
    private String storyText;
    private int opt, tlkSpeed, sceneId;
    //thread
    private Thread thread;

    private StoryMode() {
        stat = "";
        time = 181;
        storyText = "";
        sceneId = 0;
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
        thread.setName("story scene thread");
        thread.setPriority(5);
        storyMus = new AudioPlayback(AudioPlayback.storySound(), false);
        tlkSpeed = WindowOptions.txtSpeed;
        notAsked = true;
        opt = -1;
        RenderCharacterSelectionScreen.getInstance().newInstance();
        RenderStageSelect.getInstance().newInstance();
        switch (scene) {
            case 0:
                time = 181;
                stat = "nrml";
                RenderCharacterSelectionScreen.getInstance().selRaila(CharacterState.CHARACTER);
                RenderCharacterSelectionScreen.getInstance().selRav(CharacterState.OPPONENT);
                RenderStageSelect.getInstance().selectStage(Stage.IBEX_HILL);
                break;
            case 1:
                time = 181;
                stat = "nrml";
                RenderCharacterSelectionScreen.getInstance().selLynx(CharacterState.CHARACTER);
                RenderCharacterSelectionScreen.getInstance().selRaila(CharacterState.OPPONENT);
                RenderStageSelect.getInstance().selectStage(Stage.DISTANT_ISLE);
                break;
            case 2:
                time = 30;
                stat = "nrml";
                RenderCharacterSelectionScreen.getInstance().selAisha(CharacterState.CHARACTER);
                RenderCharacterSelectionScreen.getInstance().selLynx(CharacterState.OPPONENT);
                RenderStageSelect.getInstance().selectStage(Stage.IBEX_HILL_NIGHT);
                break;
            case 3:
                time = 181;
                stat = "nrml";
                RenderCharacterSelectionScreen.getInstance().selRaila(CharacterState.CHARACTER);
                RenderCharacterSelectionScreen.getInstance().selSubiya(CharacterState.OPPONENT);
                RenderStageSelect.getInstance().selectStage(Stage.CHELSTON_CITY_STREETS);
                break;
            case 4:
                time = 45;
                stat = "half way";
                RenderCharacterSelectionScreen.getInstance().selRav(CharacterState.CHARACTER);
                RenderCharacterSelectionScreen.getInstance().selAde(CharacterState.OPPONENT);
                RenderStageSelect.getInstance().selectStage(Stage.FROZEN_WILDERNESS);
                break;
            case 5:
                time = 45;
                stat = "nrml";
                RenderGameplay.getInstance().setNumOfBoards(2);
                RenderCharacterSelectionScreen.getInstance().selAdam(CharacterState.CHARACTER);
                RenderCharacterSelectionScreen.getInstance().selJon(CharacterState.OPPONENT);
                RenderStageSelect.getInstance().selectStage(Stage.FROZEN_WILDERNESS);
                break;
            case 6:
                time = 181;
                stat = "nrml";
                RenderCharacterSelectionScreen.getInstance().selAza(CharacterState.CHARACTER);
                RenderCharacterSelectionScreen.getInstance().selNOVAAdam(CharacterState.OPPONENT);
                RenderStageSelect.getInstance().selectStage(Stage.APOCALYPTO);
                break;
            case 7:
                time = 181;
                stat = "nrml";
                RenderCharacterSelectionScreen.getInstance().selSubiya(CharacterState.CHARACTER);
                RenderCharacterSelectionScreen.getInstance().selRav(CharacterState.OPPONENT);
                RenderStageSelect.getInstance().selectStage(Stage.CHELSTON_CITY_DOCKS);
                break;
            case 8:
                time = 181;
                stat = "nrml";
                RenderCharacterSelectionScreen.getInstance().selLynx(CharacterState.CHARACTER);
                RenderCharacterSelectionScreen.getInstance().selAdam(CharacterState.OPPONENT);
                RenderStageSelect.getInstance().selectStage(Stage.APOCALYPTO);
                break;
            case 9:
                time = 60;
                stat = "nrml";
                RenderCharacterSelectionScreen.getInstance().selRaila(CharacterState.CHARACTER);
                RenderCharacterSelectionScreen.getInstance().selSorr(CharacterState.OPPONENT);
                RenderStageSelect.getInstance().selectStage(Stage.APOCALYPTO);
                break;
            case 10:
                time = 90;
                stat = "nrml";
                RenderCharacterSelectionScreen.getInstance().selSubiya(CharacterState.CHARACTER);
                RenderCharacterSelectionScreen.getInstance().selNOVAAdam(CharacterState.OPPONENT);
                RenderStageSelect.getInstance().selectStage(Stage.DISTANT_ISLE_NIGHT);
                break;
            case 11:
                time = 181;
                stat = "nrml";
                RenderCharacterSelectionScreen.getInstance().selAdam(CharacterState.CHARACTER);
                RenderCharacterSelectionScreen.getInstance().selThing(CharacterState.BOSS);
                RenderStageSelect.getInstance().selectStage(Stage.DESERT_RUINS_NIGHT);
                break;
        }
        if (start) {
            startStoryMode(scene);
        }
    }

    public void startStoryMode(int x) {
        RenderGameplay.getInstance().newInstance();
        sceneId = x;
        thread.start();
    }

    /**
     * In story scene chars and opp should generate nothin
     */
    private void storyIn() {
        storyMus.play();
        GameInstance.getInstance().storySequence = true;
        doneShowingText = false;
        GameInstance.getInstance().pauseActivityRegen();
        GameInstance.getInstance().pauseActivityRegenOpp();
    }

    private void storyOut(boolean terminateMode) {
        if (terminateMode) {
            storyMus.close();
            GameInstance.getInstance().playMusicNow();
            GameInstance.getInstance().musNotice();
        }
        RenderGameplay.getInstance().charPortBlank();
        RenderGameplay.getInstance().flashyText("");
        thread.stop();
        GameInstance.getInstance().storySequence = false;
        doneShowingText = true;
        GameInstance.getInstance().resumeActivityRegen();
        GameInstance.getInstance().resumeActivityRegenOpp();
    }

    @Override
    public void run() {
        try {
            System.out.println("Stage " + RenderStoryMenu.getInstance().getStage());
            MainWindow.getInstance().startStoryMatch();
            storyIn();
            firstRun = false;

            if (sceneId == 0) //scene 1
            {
                thread.sleep(5000);
                RenderGameplay.getInstance().changeStoryBoard(0);

                RenderGameplay.getInstance().flashyText(storyText = Language.getInstance().getLine(174));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().flashyText(storyText = Language.getInstance().getLine(175));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().flashyText(storyText = Language.getInstance().getLine(176));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().changeStoryBoard(1);
                RenderGameplay.getInstance().flashyText(storyText = Language.getInstance().getLine(177));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(0);
                RenderGameplay.getInstance().flashyText(storyText = Language.getInstance().getLine(178));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(4);
                RenderGameplay.getInstance().flashyText(storyText = Language.getInstance().getLine(179));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(4);
                RenderGameplay.getInstance().flashyText(storyText = Language.getInstance().getLine(372));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(1); //sub               
                RenderGameplay.getInstance().flashyText(storyText = Language.getInstance().getLine(180));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(4); //rav                
                RenderGameplay.getInstance().flashyText(storyText = Language.getInstance().getLine(181));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(1); //sub
                RenderGameplay.getInstance().flashyText(storyText = Language.getInstance().getLine(182));
                thread.sleep(storyText.length() * tlkSpeed);
                RenderGameplay.getInstance().charPortBlank();


                storyOut(false);
            }

            if (sceneId == 1) //scene 2
            {
                thread.sleep(5000);
                RenderGameplay.getInstance().changeStoryBoard(1);

                RenderGameplay.getInstance().flashyText(storyText = Language.getInstance().getLine(183));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().flashyText(storyText = Language.getInstance().getLine(184));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().flashyText(storyText = Language.getInstance().getLine(185));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().changeStoryBoard(2);
                RenderGameplay.getInstance().flashyText(storyText = Language.getInstance().getLine(186));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().flashyText(storyText = Language.getInstance().getLine(187));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().flashyText(storyText = Language.getInstance().getLine(146));

                storyOut(false);
            }

            if (sceneId == 2) //scene 3
            {
                thread.sleep(5000);
                RenderGameplay.getInstance().changeStoryBoard(2);

                RenderGameplay.getInstance().setCharacterPortrait(2); //lynx
                RenderGameplay.getInstance().flashyText(storyText = Language.getInstance().getLine(188));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(0); //raila                
                RenderGameplay.getInstance().flashyText(storyText = Language.getInstance().getLine(189));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(2); //lynx
                RenderGameplay.getInstance().flashyText(storyText = Language.getInstance().getLine(190) + " .......");
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(3); //aisha
                RenderGameplay.getInstance().flashyText(storyText = Language.getInstance().getLine(191));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(2);
                RenderGameplay.getInstance().flashyText(storyText = Language.getInstance().getLine(192));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(3);
                RenderGameplay.getInstance().flashyText(storyText = Language.getInstance().getLine(193));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(2);
                RenderGameplay.getInstance().flashyText(storyText = Language.getInstance().getLine(194));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(3);
                RenderGameplay.getInstance().flashyText(storyText = Language.getInstance().getLine(195));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(2);
                RenderGameplay.getInstance().flashyText(storyText = Language.getInstance().getLine(196));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(0);
                RenderGameplay.getInstance().flashyText(storyText = Language.getInstance().getLine(197));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(1);
                RenderGameplay.getInstance().flashyText(storyText = Language.getInstance().getLine(198));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().changeStoryBoard(3);
                RenderGameplay.getInstance().charPortBlank();
                RenderGameplay.getInstance().flashyText(storyText = Language.getInstance().getLine(199));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(3);
                RenderGameplay.getInstance().flashyText(storyText = Language.getInstance().getLine(200));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(2);
                ;
                RenderGameplay.getInstance().flashyText(storyText = Language.getInstance().getLine(201));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(3);
                storyText = Language.getInstance().getLine(202);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(2);
                storyText = Language.getInstance().getLine(203);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(3);
                storyText = Language.getInstance().getLine(204);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(2);
                storyText = Language.getInstance().getLine(205);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                storyOut(false);
            }

            if (sceneId == 3) //scene 4
            {
                thread.sleep(5000);
                RenderGameplay.getInstance().changeStoryBoard(3);

                RenderGameplay.getInstance().setCharacterPortrait(1);
                RenderGameplay.getInstance().flashyText(storyText = Language.getInstance().getLine(206));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(1);
                RenderGameplay.getInstance().flashyText(storyText = Language.getInstance().getLine(207));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(1);
                storyText = Language.getInstance().getLine(208);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().changeStoryBoard(5);
                RenderGameplay.getInstance().setCharacterPortrait(0);
                RenderGameplay.getInstance().flashyText(storyText = Language.getInstance().getLine(209));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(1);
                RenderGameplay.getInstance().flashyText(storyText = Language.getInstance().getLine(210));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(0);
                RenderGameplay.getInstance().flashyText(storyText = Language.getInstance().getLine(211));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(0);
                RenderGameplay.getInstance().flashyText(storyText = Language.getInstance().getLine(212));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(0);
                RenderGameplay.getInstance().flashyText(storyText = Language.getInstance().getLine(213));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(0);
                RenderGameplay.getInstance().flashyText(storyText = Language.getInstance().getLine(214));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(0);
                RenderGameplay.getInstance().flashyText(storyText = Language.getInstance().getLine(215));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(1);
                RenderGameplay.getInstance().flashyText(storyText = Language.getInstance().getLine(216));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(0);
                RenderGameplay.getInstance().flashyText(storyText = Language.getInstance().getLine(203));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(1);
                RenderGameplay.getInstance().flashyText(storyText = Language.getInstance().getLine(217));
                thread.sleep(storyText.length() * tlkSpeed);

                storyOut(false);
            }

            if (sceneId == 4) //scene 5
            {
                thread.sleep(5000);
                RenderGameplay.getInstance().changeStoryBoard(5);

                storyText = Language.getInstance().getLine(218) + " .......";
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                storyText = Language.getInstance().getLine(219);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(5); //ade
                storyText = Language.getInstance().getLine(220);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(10); //sorrowe
                storyText = Language.getInstance().getLine(221);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(5); //ade
                storyText = Language.getInstance().getLine(222);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(4); //ravage
                storyText = Language.getInstance().getLine(223);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(5); //ade
                storyText = Language.getInstance().getLine(224);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(10);
                storyText = Language.getInstance().getLine(225);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(5); //ade
                storyText = Language.getInstance().getLine(226);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(6); //jonah
                storyText = Language.getInstance().getLine(227);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(4);
                storyText = Language.getInstance().getLine(228);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(4);
                storyText = Language.getInstance().getLine(229);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(5);//ade
                storyText = Language.getInstance().getLine(230);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                storyOut(false);
            }

            if (sceneId == 5) //scene 6
            {
                thread.sleep(5000);
                RenderGameplay.getInstance().changeStoryBoard(5);

                RenderGameplay.getInstance().setCharacterPortrait(4);
                storyText = Language.getInstance().getLine(231);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(5);//ade
                storyText = Language.getInstance().getLine(232);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(4);
                storyText = Language.getInstance().getLine(233);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(6);
                storyText = Language.getInstance().getLine(234);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(7);
                storyText = Language.getInstance().getLine(235);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(6);
                storyText = Language.getInstance().getLine(236);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(7);
                storyText = Language.getInstance().getLine(237);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(6);
                storyText = Language.getInstance().getLine(238);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(7);
                storyText = Language.getInstance().getLine(239);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(6);
                storyText = Language.getInstance().getLine(240);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(7);
                storyText = Language.getInstance().getLine(241);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(6);
                storyText = Language.getInstance().getLine(242);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(7);
                storyText = Language.getInstance().getLine(243);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(6);
                storyText = Language.getInstance().getLine(244);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(6);
                storyText = Language.getInstance().getLine(245);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(5);
                storyText = Language.getInstance().getLine(246);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(7);
                storyText = Language.getInstance().getLine(247);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(7);
                storyText = Language.getInstance().getLine(248);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(6);
                storyText = Language.getInstance().getLine(249);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(7);
                storyText = Language.getInstance().getLine(250);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                storyOut(false);
            }

            if (sceneId == 6) //scene 7
            {
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

                RenderGameplay.getInstance().setCharacterPortrait(9);
                storyText = Language.getInstance().getLine(255);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(7);
                storyText = Language.getInstance().getLine(256);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(9);
                storyText = Language.getInstance().getLine(257);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(7);
                storyText = Language.getInstance().getLine(258);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(7);
                storyText = Language.getInstance().getLine(259);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(9);
                storyText = Language.getInstance().getLine(260);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(7);
                storyText = Language.getInstance().getLine(261);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(7);
                storyText = Language.getInstance().getLine(262);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                storyOut(false);
            }


            if (sceneId == 7) //scene 8
            {
                thread.sleep(5000);
                RenderGameplay.getInstance().changeStoryBoard(6);

                RenderGameplay.getInstance().setCharacterPortrait(1);
                storyText = Language.getInstance().getLine(263);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(0);
                storyText = Language.getInstance().getLine(264);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(4);
                storyText = Language.getInstance().getLine(265);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(0);
                storyText = Language.getInstance().getLine(266);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(4);
                storyText = Language.getInstance().getLine(267);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(1);
                storyText = Language.getInstance().getLine(268);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(0);
                storyText = ".......... " + Language.getInstance().getLine(269);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                storyOut(false);
            }

            if (sceneId == 8) //scene 9
            {
                thread.sleep(5000);
                RenderGameplay.getInstance().changeStoryBoard(8);

                storyText = Language.getInstance().getLine(270);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(7);
                storyText = Language.getInstance().getLine(271);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(7);
                storyText = Language.getInstance().getLine(272);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(9);
                storyText = Language.getInstance().getLine(273);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(7);
                storyText = Language.getInstance().getLine(274);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(2);
                storyText = Language.getInstance().getLine(275);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(9);
                storyText = Language.getInstance().getLine(276);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(7);
                storyText = Language.getInstance().getLine(277);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(7);
                storyText = Language.getInstance().getLine(231) + " !!!";
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(2);
                storyText = Language.getInstance().getLine(278);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(7);
                storyText = Language.getInstance().getLine(279) + " !!!";
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                storyOut(false);
            }

            //new stage they arrive

            if (sceneId == 9) //scene 10
            {
                thread.sleep(5000);
                RenderGameplay.getInstance().changeStoryBoard(9);

                RenderGameplay.getInstance().setCharacterPortrait(0);
                storyText = Language.getInstance().getLine(280) + " !!!";
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(9);
                storyText = Language.getInstance().getLine(281);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(0);
                storyText = Language.getInstance().getLine(282);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(9);
                storyText = Language.getInstance().getLine(283);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(0);
                storyText = Language.getInstance().getLine(284);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(0);
                storyText = Language.getInstance().getLine(285);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(9);
                storyText = Language.getInstance().getLine(286);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortBlank();
                storyText = Language.getInstance().getLine(287) + " ...";
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(10);
                storyText = Language.getInstance().getLine(288);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(0);
                storyText = Language.getInstance().getLine(289) + " !!";
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(10);
                storyText = Language.getInstance().getLine(290);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(0);
                storyText = Language.getInstance().getLine(291);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(0);
                storyText = Language.getInstance().getLine(292);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(10);
                storyText = Language.getInstance().getLine(293);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                storyOut(false);
            }

            if (sceneId == 10) //scene 11
            {
                thread.sleep(5000);
                RenderGameplay.getInstance().changeStoryBoard(11);

                //set difficulty - hard
                Characters.getInstance().setDamageCounter(CharacterState.OPPONENT, 18);

                RenderGameplay.getInstance().setCharacterPortrait(10);
                storyText = Language.getInstance().getLine(294) + " !!!";
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(0);
                storyText = Language.getInstance().getLine(231) + " !!!";
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(1);
                storyText = Language.getInstance().getLine(295);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(0);
                storyText = Language.getInstance().getLine(296);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(10);
                storyText = Language.getInstance().getLine(297);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(0);
                storyText = Language.getInstance().getLine(298);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(10);
                storyText = Language.getInstance().getLine(299) + "!!";
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(7);
                storyText = Language.getInstance().getLine(300) + " !!!";
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(1);
                storyText = Language.getInstance().getLine(301) + " ?";
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(7);
                storyText = Language.getInstance().getLine(302) + " !!!!!!!!!!!!!!";
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(1);
                storyText = Language.getInstance().getLine(303) + " !!!";
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(2);
                storyText = Language.getInstance().getLine(304);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(1);
                storyText = Language.getInstance().getLine(305);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(7);
                storyText = Language.getInstance().getLine(306);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                storyOut(false);
            }

            if (sceneId == 11) //scene 12
            {
                thread.sleep(5000);
                RenderGameplay.getInstance().changeStoryBoard(10);

                RenderGameplay.getInstance().setCharacterPortrait(10);
                storyText = Language.getInstance().getLine(373);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortBlank();
                storyText = Language.getInstance().getLine(374);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(1);
                storyText = Language.getInstance().getLine(375);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(9);
                storyText = Language.getInstance().getLine(376);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(1);
                storyText = Language.getInstance().getLine(377);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortBlank();
                storyText = Language.getInstance().getLine(378);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(4);
                storyText = Language.getInstance().getLine(379);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(5);
                storyText = Language.getInstance().getLine(380);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                storyText = Language.getInstance().getLine(381);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(8);
                storyText = Language.getInstance().getLine(383);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(11);
                storyText = Language.getInstance().getLine(384);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(10);
                storyText = Language.getInstance().getLine(385);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(9);
                storyText = Language.getInstance().getLine(386);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(8);
                storyText = Language.getInstance().getLine(387);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(6);
                storyText = Language.getInstance().getLine(388);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(10);
                storyText = Language.getInstance().getLine(389);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(8);
                storyText = Language.getInstance().getLine(390);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(8);
                storyText = Language.getInstance().getLine(391);
                RenderGameplay.getInstance().flashyText(storyText);
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(6);
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

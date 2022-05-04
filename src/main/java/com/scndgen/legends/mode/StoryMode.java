/**************************************************************************

 The SCND Genesis: Legends is a fighting game based on THE SCND GENESIS,
 a webcomic created by Ifunga Ndana ((([<a href="http://www.scndgen.com">http://www.scndgen.com</a>]))).

 The SCND Genesis: Legends RMX  © 2017 Ifunga Ndana.

 The SCND Genesis: Legends is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 The SCND Genesis: Legends is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with The SCND Genesis: Legends. If not, see <<a href="http://www.gnu.org/licenses/">http://www.gnu.org/licenses/</a>>.

 **************************************************************************/
package com.scndgen.legends.mode;

import com.scndgen.legends.Language;
import com.scndgen.legends.ScndGenLegends;
import com.scndgen.legends.characters.Characters;
import com.scndgen.legends.constants.AudioConstants;
import com.scndgen.legends.enums.*;
import com.scndgen.legends.render.RenderCharacterSelection;
import com.scndgen.legends.render.RenderGamePlay;
import com.scndgen.legends.render.RenderStageSelect;
import com.scndgen.legends.render.RenderStoryMenu;
import com.scndgen.legends.state.State;
import io.github.subiyacryolite.enginev1.Audio;

import static com.scndgen.legends.constants.GeneralConstants.INFINITE_TIME;

/**
 * @author ndana
 */
public class StoryMode implements Runnable {
    private static StoryMode instance;
    public StoryProgress storyProgress = StoryProgress.NORMAL;
    public final int totalScenes = 12;
    public int timeLimit;
    private Audio storyMusic;
    private String text;
    private long textSpeed;
    private int currentScene;
    private static Thread thread;

    private StoryMode() {
        storyProgress = StoryProgress.NORMAL;
        timeLimit = INFINITE_TIME;
        text = "";
        currentScene = 0;
    }

    public static synchronized StoryMode get() {
        if (instance == null)
            instance = new StoryMode();
        return instance;
    }

    public synchronized void newInstance() {
        instance = new StoryMode();
    }

    private void setScene(int scene) {
        storyMusic = new Audio(AudioConstants.storySound(), AudioType.MUSIC, false);
        textSpeed = State.get().getLogin().getTextSpeedInt();
        RenderCharacterSelection.get().newInstance();
        RenderStageSelect.get().newInstance();
        switch (scene) {
            case 0:
                timeLimit = INFINITE_TIME;
                storyProgress = StoryProgress.START;
                RenderCharacterSelection.get().selRaila(PlayerType.PLAYER1);
                RenderCharacterSelection.get().selRav(PlayerType.PLAYER2);
                RenderStageSelect.get().selectStage(Stage.IBEX_HILL);
                break;
            case 1:
                timeLimit = INFINITE_TIME;
                storyProgress = StoryProgress.NORMAL;
                RenderCharacterSelection.get().selLynx(PlayerType.PLAYER1);
                RenderCharacterSelection.get().selRaila(PlayerType.PLAYER2);
                RenderStageSelect.get().selectStage(Stage.DISTANT_ISLE);
                break;
            case 2:
                timeLimit = 30;
                storyProgress = StoryProgress.NORMAL;
                RenderCharacterSelection.get().selAisha(PlayerType.PLAYER1);
                RenderCharacterSelection.get().selLynx(PlayerType.PLAYER2);
                RenderStageSelect.get().selectStage(Stage.IBEX_HILL_NIGHT);
                break;
            case 3:
                timeLimit = INFINITE_TIME;
                storyProgress = StoryProgress.NORMAL;
                RenderCharacterSelection.get().selRaila(PlayerType.PLAYER1);
                RenderCharacterSelection.get().selSubiya(PlayerType.PLAYER2);
                RenderStageSelect.get().selectStage(Stage.CHELSTON_CITY_STREETS);
                break;
            case 4:
                timeLimit = 45;
                storyProgress = StoryProgress.HALFWAY;
                RenderCharacterSelection.get().selRav(PlayerType.PLAYER1);
                RenderCharacterSelection.get().selAde(PlayerType.PLAYER2);
                RenderStageSelect.get().selectStage(Stage.FROZEN_WILDERNESS);
                break;
            case 5:
                timeLimit = 45;
                storyProgress = StoryProgress.NORMAL;
                RenderGamePlay.get().setNumOfBoards(2);
                RenderCharacterSelection.get().selAdam(PlayerType.PLAYER1);
                RenderCharacterSelection.get().selJon(PlayerType.PLAYER2);
                RenderStageSelect.get().selectStage(Stage.FROZEN_WILDERNESS);
                break;
            case 6:
                timeLimit = INFINITE_TIME;
                storyProgress = StoryProgress.NORMAL;
                RenderCharacterSelection.get().selAza(PlayerType.PLAYER1);
                RenderCharacterSelection.get().selNOVAAdam(PlayerType.PLAYER2);
                RenderStageSelect.get().selectStage(Stage.APOCALYPTO);
                break;
            case 7:
                timeLimit = INFINITE_TIME;
                storyProgress = StoryProgress.NORMAL;
                RenderCharacterSelection.get().selSubiya(PlayerType.PLAYER1);
                RenderCharacterSelection.get().selRav(PlayerType.PLAYER2);
                RenderStageSelect.get().selectStage(Stage.CHELSTON_CITY_DOCKS);
                break;
            case 8:
                timeLimit = INFINITE_TIME;
                storyProgress = StoryProgress.NORMAL;
                RenderCharacterSelection.get().selLynx(PlayerType.PLAYER1);
                RenderCharacterSelection.get().selAdam(PlayerType.PLAYER2);
                RenderStageSelect.get().selectStage(Stage.APOCALYPTO);
                break;
            case 9:
                timeLimit = 60;
                storyProgress = StoryProgress.NORMAL;
                RenderCharacterSelection.get().selRaila(PlayerType.PLAYER1);
                RenderCharacterSelection.get().selSorr(PlayerType.PLAYER2);
                RenderStageSelect.get().selectStage(Stage.APOCALYPTO);
                break;
            case 10:
                timeLimit = 90;
                storyProgress = StoryProgress.NORMAL;
                RenderCharacterSelection.get().selSubiya(PlayerType.PLAYER1);
                RenderCharacterSelection.get().selNOVAAdam(PlayerType.PLAYER2);
                RenderStageSelect.get().selectStage(Stage.DISTANT_ISLE_NIGHT);
                break;
            case 11:
                timeLimit = INFINITE_TIME;
                storyProgress = StoryProgress.END;
                RenderCharacterSelection.get().selAdam(PlayerType.PLAYER1);
                RenderCharacterSelection.get().selThing(PlayerType.BOSS);
                RenderStageSelect.get().selectStage(Stage.DESERT_RUINS_NIGHT);
                break;
        }
    }

    public void startStoryMode(int x) {
        RenderGamePlay.get().newInstance();
        currentScene = x;
        if (thread != null)
            thread.stop();
        thread = new Thread(this); //single static thread, always fire up new
        thread.setName("playStory scene thread");
        thread.start();
    }

    /**
     * In playStory scene chars and opp should generate nothin
     */
    private void beginCinematic() {
        storyMusic.play();
        RenderGamePlay.get().reloadAssets();//set new properties, load relevant sprites
        RenderGamePlay.get().characterPortrait();
        RenderGamePlay.get().storyText("");
        RenderGamePlay.get().playingCutscene = true;
        RenderGamePlay.get().pauseCharacterAtb();
        RenderGamePlay.get().pauseOpponentAtb();
    }

    public void exitCinematic(boolean terminateMode) {
        if (terminateMode) {
            stopMusic();
            RenderGamePlay.get().musNotice();
        }
        RenderGamePlay.get().playBGMusic();
        RenderGamePlay.get().characterPortrait();
        RenderGamePlay.get().storyText("");
        thread.stop();
        RenderGamePlay.get().playingCutscene = false;
        RenderGamePlay.get().resumeCharacterAtb();
        RenderGamePlay.get().resumeOpponentAtb();
    }

    @Override
    public void run() {
        try {
            setScene(currentScene);
            ScndGenLegends.get().loadMode(ModeEnum.STANDARD_GAMEPLAY_START);
            ScndGenLegends.get().setSubMode(SubMode.STORY_MODE);
            beginCinematic();
            RenderGamePlay.get().storyBoard(currentScene);
            switch (currentScene) {
                case 0:
                    //UPDATED
                    Thread.sleep(2000);
                    RenderGamePlay.get().characterPortrait();
                    RenderGamePlay.get().storyText(text = Language.get().get(174));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().storyText(text = Language.get().get(175));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().storyText(text = Language.get().get(176));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().storyText(text = Language.get().get(431));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().storyText(text = Language.get().get(432));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().storyText(text = Language.get().get(433));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().storyText(text = Language.get().get(434));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().storyText(text = Language.get().get(435));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().storyText(text = Language.get().get(436));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().storyText(text = Language.get().get(437));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.RAILA);
                    RenderGamePlay.get().storyText(text = Language.get().get(438));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().storyText(text = Language.get().get(439));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().storyText(text = Language.get().get(440));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().storyText(text = Language.get().get(178));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.RAVAGE);
                    RenderGamePlay.get().storyText(text = Language.get().get(179));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().storyText(text = Language.get().get(180));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.RAILA);
                    RenderGamePlay.get().storyText(text = Language.get().get(181));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.RAVAGE);
                    RenderGamePlay.get().storyText(text = Language.get().get(182));
                    Thread.sleep(text.length() * textSpeed);

                    exitCinematic(false);
                    break;
                case 1:
                    //UPDATED
                    Thread.sleep(2000);

                    RenderGamePlay.get().characterPortrait();
                    RenderGamePlay.get().storyText(text = Language.get().get(441));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().storyText(text = Language.get().get(183));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().storyText(text = Language.get().get(184));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().storyText(text = Language.get().get(185));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().storyText(text = Language.get().get(186));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.LYNX);
                    RenderGamePlay.get().storyText(text = Language.get().get(443));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().storyText(text = Language.get().get(444));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait();
                    RenderGamePlay.get().storyText(text = Language.get().get(187));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().storyText(text = Language.get().get(146));

                    exitCinematic(false);
                    break;
                case 2:
                    //UPDATED
                    Thread.sleep(2000);
                    RenderGamePlay.get().characterPortrait();

                    RenderGamePlay.get().characterPortrait(CharacterEnum.LYNX); //lynx
                    RenderGamePlay.get().storyText(text = Language.get().get(188));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.RAILA); //raila
                    RenderGamePlay.get().storyText(text = Language.get().get(189));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait();
                    RenderGamePlay.get().storyText(text = Language.get().get(190) + " .......");
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.AISHA); //aisha
                    RenderGamePlay.get().storyText(text = Language.get().get(191));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.LYNX);
                    RenderGamePlay.get().storyText(text = Language.get().get(192));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.AISHA);
                    RenderGamePlay.get().storyText(text = Language.get().get(193));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.LYNX);
                    RenderGamePlay.get().storyText(text = Language.get().get(194));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.AISHA);
                    RenderGamePlay.get().storyText(text = Language.get().get(195));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.LYNX);
                    RenderGamePlay.get().storyText(text = Language.get().get(196));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.RAILA);
                    RenderGamePlay.get().storyText(text = Language.get().get(197));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.SUBIYA);
                    RenderGamePlay.get().storyText(text = Language.get().get(198));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait();
                    RenderGamePlay.get().storyText(text = Language.get().get(199));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.AISHA);
                    RenderGamePlay.get().storyText(text = Language.get().get(200));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.LYNX);
                    RenderGamePlay.get().storyText(text = Language.get().get(201));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.AISHA);
                    RenderGamePlay.get().storyText(text = Language.get().get(202));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.LYNX);
                    RenderGamePlay.get().storyText(text = Language.get().get(203));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.AISHA);
                    RenderGamePlay.get().storyText(text = Language.get().get(204));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.AISHA);
                    RenderGamePlay.get().storyText(text = Language.get().get(205));
                    Thread.sleep(text.length() * textSpeed);

                    exitCinematic(false);
                    break;
                case 3: //scene 4
                    //UPDATED and reviewed
                    Thread.sleep(2000);
                    RenderGamePlay.get().characterPortrait();

                    RenderGamePlay.get().characterPortrait(CharacterEnum.SUBIYA);
                    RenderGamePlay.get().storyText(text = Language.get().get(206));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.RAILA);
                    RenderGamePlay.get().storyText(text = Language.get().get(207));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.SUBIYA);
                    RenderGamePlay.get().storyText(text = Language.get().get(208));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.RAILA);
                    RenderGamePlay.get().storyText(text = Language.get().get(209));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.SUBIYA);
                    RenderGamePlay.get().storyText(text = Language.get().get(210));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.RAILA);
                    RenderGamePlay.get().storyText(text = Language.get().get(211));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().storyText(text = Language.get().get(212));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().storyText(text = Language.get().get(213));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().storyText(text = Language.get().get(214));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().storyText(text = Language.get().get(215));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.SUBIYA);
                    RenderGamePlay.get().storyText(text = Language.get().get(216));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().storyText(text = Language.get().get(425));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().storyText(text = Language.get().get(426));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().storyText(text = Language.get().get(427));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().storyText(text = Language.get().get(428));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().storyText(text = Language.get().get(429));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.RAILA);
                    RenderGamePlay.get().storyText(text = Language.get().get(430));
                    Thread.sleep(text.length() * textSpeed);

                    exitCinematic(false);
                    break;
                case 4:
                    //DONE
                    Thread.sleep(2000);

                    RenderGamePlay.get().characterPortrait();
                    RenderGamePlay.get().storyText(text = Language.get().get(218));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().storyText(text = Language.get().get(219));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.ADE); //ade
                    RenderGamePlay.get().storyText(text = Language.get().get(220));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.SORROWE); //sorrowe
                    RenderGamePlay.get().storyText(text = Language.get().get(221));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.ADE); //ade
                    RenderGamePlay.get().storyText(text = Language.get().get(222));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.RAVAGE); //ravage
                    RenderGamePlay.get().storyText(text = Language.get().get(223));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.ADE); //ade
                    RenderGamePlay.get().storyText(text = Language.get().get(224));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.SORROWE);
                    RenderGamePlay.get().storyText(text = Language.get().get(225));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.ADE); //ade
                    RenderGamePlay.get().storyText(text = Language.get().get(226));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.JONAH); //jonah
                    RenderGamePlay.get().storyText(text = Language.get().get(227));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.RAVAGE);
                    RenderGamePlay.get().storyText(text = Language.get().get(228));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.RAVAGE);
                    RenderGamePlay.get().storyText(text = Language.get().get(229));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.ADE);//ade
                    RenderGamePlay.get().storyText(text = Language.get().get(230));
                    Thread.sleep(text.length() * textSpeed);

                    exitCinematic(false);
                    break;
                case 5:
                    //DONE
                    Thread.sleep(2000);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.RAVAGE);
                    RenderGamePlay.get().storyText(text = Language.get().get(231));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.ADE);//ade
                    RenderGamePlay.get().storyText(text = Language.get().get(232));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.RAVAGE);
                    RenderGamePlay.get().storyText(text = Language.get().get(233));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.JONAH);
                    RenderGamePlay.get().storyText(text = Language.get().get(234));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.ADAM);
                    RenderGamePlay.get().storyText(text = Language.get().get(235));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.JONAH);
                    RenderGamePlay.get().storyText(text = Language.get().get(236));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.ADAM);
                    RenderGamePlay.get().storyText(text = Language.get().get(237));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.JONAH);
                    RenderGamePlay.get().storyText(text = Language.get().get(238));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.ADAM);
                    RenderGamePlay.get().storyText(text = Language.get().get(239));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.JONAH);
                    RenderGamePlay.get().storyText(text = Language.get().get(240));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.ADAM);
                    RenderGamePlay.get().storyText(text = Language.get().get(241));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.ADE);
                    RenderGamePlay.get().storyText(text = Language.get().get(242));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.ADAM);
                    RenderGamePlay.get().storyText(text = Language.get().get(243));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.JONAH);
                    RenderGamePlay.get().storyText(text = Language.get().get(244));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.JONAH);
                    RenderGamePlay.get().storyText(text = Language.get().get(245));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.ADE);
                    RenderGamePlay.get().storyText(text = Language.get().get(246));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.ADAM);
                    RenderGamePlay.get().storyText(text = Language.get().get(247));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.ADAM);
                    RenderGamePlay.get().storyText(text = Language.get().get(248));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.JONAH);
                    RenderGamePlay.get().storyText(text = Language.get().get(249));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.ADAM);
                    RenderGamePlay.get().storyText(text = Language.get().get(250));
                    Thread.sleep(text.length() * textSpeed);

                    exitCinematic(false);
                    break;
                case 6:
                    //DONE
                    Thread.sleep(2000);
                    RenderGamePlay.get().characterPortrait();

                    RenderGamePlay.get().storyText(text = Language.get().get(251));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().storyText(text = Language.get().get(252));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().storyText(text = Language.get().get(253));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().storyText(text = Language.get().get(254));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.AZARIA);
                    RenderGamePlay.get().storyText(text = Language.get().get(255));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.ADAM);
                    RenderGamePlay.get().storyText(text = Language.get().get(256));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.AZARIA);
                    RenderGamePlay.get().storyText(text = Language.get().get(257));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.ADAM);
                    RenderGamePlay.get().storyText(text = Language.get().get(258));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.ADAM);
                    RenderGamePlay.get().storyText(text = Language.get().get(259));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.AZARIA);
                    RenderGamePlay.get().storyText(text = Language.get().get(260));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.ADAM);
                    RenderGamePlay.get().storyText(text = Language.get().get(261));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.ADAM);
                    RenderGamePlay.get().storyText(text = Language.get().get(262));
                    Thread.sleep(text.length() * textSpeed);

                    exitCinematic(false);
                    break;
                case 7:
                    //DONE
                    Thread.sleep(2000);
                    RenderGamePlay.get().characterPortrait();

                    RenderGamePlay.get().characterPortrait(CharacterEnum.SUBIYA);
                    RenderGamePlay.get().storyText(text = Language.get().get(263));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.RAILA);
                    RenderGamePlay.get().storyText(text = Language.get().get(264));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.RAVAGE);
                    RenderGamePlay.get().storyText(text = Language.get().get(265));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.RAILA);
                    RenderGamePlay.get().storyText(text = Language.get().get(266));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.RAVAGE);
                    RenderGamePlay.get().storyText(text = Language.get().get(267));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.SUBIYA);
                    RenderGamePlay.get().storyText(text = Language.get().get(268));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.RAILA);
                    RenderGamePlay.get().storyText(text = Language.get().get(269));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.SUBIYA);
                    RenderGamePlay.get().storyText(text = Language.get().get(445));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.RAVAGE);
                    RenderGamePlay.get().storyText(text = Language.get().get(446));
                    Thread.sleep(text.length() * textSpeed);

                    exitCinematic(false);
                    break;
                case 8:
                    //DONE
                    Thread.sleep(2000);
                    RenderGamePlay.get().characterPortrait();

                    RenderGamePlay.get().storyText(text = Language.get().get(270));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.ADAM);
                    RenderGamePlay.get().storyText(text = Language.get().get(271));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.ADAM);
                    RenderGamePlay.get().storyText(text = Language.get().get(272));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.AZARIA);
                    RenderGamePlay.get().storyText(text = Language.get().get(273));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.ADAM);
                    RenderGamePlay.get().storyText(text = Language.get().get(274));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.LYNX);
                    RenderGamePlay.get().storyText(text = Language.get().get(275));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.AZARIA);
                    RenderGamePlay.get().storyText(text = Language.get().get(276));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.ADAM);
                    RenderGamePlay.get().storyText(text = Language.get().get(277));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.LYNX);
                    RenderGamePlay.get().storyText(text = Language.get().get(278));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.ADAM);
                    RenderGamePlay.get().storyText(text = Language.get().get(279));
                    Thread.sleep(text.length() * textSpeed);

                    exitCinematic(false);
                    break;
                case 9:
                    //DONE
                    Thread.sleep(2000);
                    RenderGamePlay.get().characterPortrait();

                    RenderGamePlay.get().characterPortrait(CharacterEnum.RAILA);
                    RenderGamePlay.get().storyText(text = Language.get().get(280));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.AZARIA);
                    RenderGamePlay.get().storyText(text = Language.get().get(281));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.RAILA);
                    RenderGamePlay.get().storyText(text = Language.get().get(282));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.AZARIA);
                    RenderGamePlay.get().storyText(text = Language.get().get(283));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.RAILA);
                    RenderGamePlay.get().storyText(text = Language.get().get(284));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.AZARIA);
                    RenderGamePlay.get().storyText(text = Language.get().get(447));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.RAILA);
                    RenderGamePlay.get().storyText(text = Language.get().get(285));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.AZARIA);
                    RenderGamePlay.get().storyText(text = Language.get().get(286));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait();
                    RenderGamePlay.get().storyText(text = Language.get().get(287));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.SORROWE);
                    RenderGamePlay.get().storyText(text = Language.get().get(288));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().storyText(text = Language.get().get(448));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().storyText(text = Language.get().get(449));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.RAILA);
                    RenderGamePlay.get().storyText(text = Language.get().get(289));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.SORROWE);
                    RenderGamePlay.get().storyText(text = Language.get().get(290));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.RAILA);
                    RenderGamePlay.get().storyText(text = Language.get().get(291));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().storyText(text = Language.get().get(292));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.SORROWE);
                    RenderGamePlay.get().storyText(text = Language.get().get(293));
                    Thread.sleep(text.length() * textSpeed);

                    exitCinematic(false);
                    break;
                case 10:
                    //DONE
                    Thread.sleep(2000);
                    RenderGamePlay.get().characterPortrait(CharacterEnum.THING);

                    //set difficulty - hard
                    Characters.get().setDamageCounter(PlayerType.PLAYER2, 18);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.SORROWE);
                    RenderGamePlay.get().storyText(text = Language.get().get(294));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.SUBIYA);
                    RenderGamePlay.get().storyText(text = Language.get().get(231));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.RAILA);
                    RenderGamePlay.get().storyText(text = Language.get().get(295));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.RAILA);
                    RenderGamePlay.get().storyText(text = Language.get().get(296));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.SORROWE);
                    RenderGamePlay.get().storyText(text = Language.get().get(297));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.RAILA);
                    RenderGamePlay.get().storyText(text = Language.get().get(298));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.SORROWE);
                    RenderGamePlay.get().storyText(text = Language.get().get(299));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.ADAM);
                    RenderGamePlay.get().storyText(text = Language.get().get(300));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.RAILA);
                    RenderGamePlay.get().storyText(text = Language.get().get(301));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.NOVA_ADAM);
                    RenderGamePlay.get().storyText(text = Language.get().get(302) + " !!!!!!!!!!!!!!");
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.RAILA);
                    RenderGamePlay.get().storyText(text = Language.get().get(303));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.LYNX);
                    RenderGamePlay.get().storyText(text = Language.get().get(304));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.RAILA);
                    RenderGamePlay.get().storyText(text = Language.get().get(305));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.ADAM);
                    RenderGamePlay.get().storyText(text = Language.get().get(306));
                    Thread.sleep(text.length() * textSpeed);

                    exitCinematic(false);
                    break;
                case 11: //scene 12

                    Thread.sleep(2000);
                    RenderGamePlay.get().characterPortrait();

                    RenderGamePlay.get().characterPortrait(CharacterEnum.NOVA_ADAM);
                    RenderGamePlay.get().storyText(text = Language.get().get(373));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait();
                    RenderGamePlay.get().storyText(text = Language.get().get(374));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.RAILA);
                    RenderGamePlay.get().storyText(text = Language.get().get(375));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.AZARIA);
                    RenderGamePlay.get().storyText(text = Language.get().get(376));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.RAILA);
                    RenderGamePlay.get().storyText(text = Language.get().get(377));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait();
                    RenderGamePlay.get().storyText(text = Language.get().get(378));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.RAVAGE);
                    RenderGamePlay.get().storyText(text = Language.get().get(379));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.ADE);
                    RenderGamePlay.get().storyText(text = Language.get().get(380));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().storyText(text = Language.get().get(381));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.NOVA_ADAM);
                    RenderGamePlay.get().storyText(text = Language.get().get(383));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.THING);
                    RenderGamePlay.get().storyText(text = Language.get().get(384));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.SORROWE);
                    RenderGamePlay.get().storyText(text = Language.get().get(385));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.AZARIA);
                    RenderGamePlay.get().storyText(text = Language.get().get(386));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.NOVA_ADAM);
                    RenderGamePlay.get().storyText(text = Language.get().get(387));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.JONAH);
                    RenderGamePlay.get().storyText(text = Language.get().get(388));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.SORROWE);
                    RenderGamePlay.get().storyText(text = Language.get().get(389));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.NOVA_ADAM);
                    RenderGamePlay.get().storyText(text = Language.get().get(390));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.NOVA_ADAM);
                    RenderGamePlay.get().storyText(text = Language.get().get(391));
                    Thread.sleep(text.length() * textSpeed);

                    RenderGamePlay.get().characterPortrait(CharacterEnum.JONAH);
                    RenderGamePlay.get().storyText(text = Language.get().get(392));
                    Thread.sleep(text.length() * textSpeed);

                    exitCinematic(false);
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
    }

    public void startFight() {
        exitCinematic(false);
    }

    public void onBackCancel() {
        onAccept();
    }

    public void onAccept() {
        if (RenderGamePlay.get().isGameOver()) {
            if (RenderGamePlay.get().hasWon()) {
                incrementMode();
                if (RenderStoryMenu.get().moreStages()) {
                    startStoryMode(currentScene);//play next scene
                } else {
                    ScndGenLegends.get().loadMode(ModeEnum.MAIN_MENU);
                }
            } else {
                startStoryMode(currentScene);//try again
                RenderGamePlay.get().onLeaveMode();//stop music!!
            }
        } else {
            stopMusic();
            startFight();
        }
    }

    /**
     * Move to the next level when you win a match
     */

    public void incrementMode() {
        if (currentScene < totalScenes)
            currentScene += 1;
        State.get().getLogin().setHighestStoryScene(currentScene + 1);
    }

    private void stopMusic() {
        storyMusic.stop(2000);
    }
}

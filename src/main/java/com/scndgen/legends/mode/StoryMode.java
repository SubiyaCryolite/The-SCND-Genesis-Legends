/**************************************************************************

 The SCND Genesis: Legends is a fighting game based on THE SCND GENESIS,
 a webcomic created by Ifunga Ndana (https://www.scndgen.com).

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
 along with The SCND Genesis: Legends. If not, see <https://www.gnu.org/licenses/>.

 **************************************************************************/
package com.scndgen.legends.mode;

import com.scndgen.legends.LangKey;
import com.scndgen.legends.Language;
import com.scndgen.legends.StoryKey;
import com.scndgen.legends.TextKey;
import com.scndgen.legends.ScndGenLegends;
import com.scndgen.legends.characters.Characters;
import com.scndgen.legends.constants.AudioConstants;
import com.scndgen.legends.enums.*;
import com.scndgen.legends.render.RenderCharacterSelection;
import com.scndgen.legends.render.RenderGamePlay;
import com.scndgen.legends.render.RenderStageSelect;
import com.scndgen.legends.render.RenderStoryMenu;
import com.scndgen.legends.state.State;
import io.github.subiyacryolite.enginev2.Accumulator;
import io.github.subiyacryolite.enginev2.Audio;

import java.util.ArrayList;
import java.util.List;

import static com.scndgen.legends.constants.GeneralConstants.INFINITE_TIME;

/**
 * Story cutscenes driven by {@link Accumulator} waits (former Thread + sleep).
 *
 * @author ndana
 */
public class StoryMode {
    private static StoryMode instance;
    public StoryProgress storyProgress = StoryProgress.NORMAL;
    public final int totalScenes = 12;
    public int timeLimit;
    private Audio storyMusic;
    private String text;
    private long textSpeed;
    private int currentScene;
    private boolean active;
    private final Accumulator waitAccum = Accumulator.atInterval(1.0);
    private final List<Step> steps = new ArrayList<>();
    private int stepIndex;

    private enum StepKind {
        WAIT,
        LINE,
        LINE_NO_WAIT,
        ACTION,
        EXIT
    }

    private record Step(
            StepKind kind,
            CharacterEnum portrait, // null = leave as-is; CLEAR sentinel via clearPortrait
            boolean setPortrait,
            boolean clearPortrait,
            String line,
            double waitSeconds,
            Runnable action
    ) {
        static Step wait(double seconds) {
            return new Step(StepKind.WAIT, null, false, false, null, seconds, null);
        }

        static Step line(String text, double waitSeconds) {
            return new Step(StepKind.LINE, null, false, false, text, waitSeconds, null);
        }

        static Step line(CharacterEnum portrait, String text, double waitSeconds) {
            return new Step(StepKind.LINE, portrait, true, false, text, waitSeconds, null);
        }

        static Step lineClearPortrait(String text, double waitSeconds) {
            return new Step(StepKind.LINE, null, true, true, text, waitSeconds, null);
        }

        static Step lineNoWait(String text) {
            return new Step(StepKind.LINE_NO_WAIT, null, false, false, text, 0, null);
        }

        static Step action(Runnable action) {
            return new Step(StepKind.ACTION, null, false, false, null, 0, action);
        }

        static Step exit() {
            return new Step(StepKind.EXIT, null, false, false, null, 0, null);
        }
    }

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
        var renderCharacterSelection = RenderCharacterSelection.get();
        var renderStageSelect = RenderStageSelect.get();
        renderCharacterSelection.newInstance();
        renderStageSelect.newInstance();
        switch (scene) {
            case 0:
                timeLimit = INFINITE_TIME;
                storyProgress = StoryProgress.START;
                renderCharacterSelection.selRaila(PlayerType.PLAYER1);
                renderCharacterSelection.selRav(PlayerType.PLAYER2);
                renderStageSelect.selectStage(Stage.IBEX_HILL);
                break;
            case 1:
                timeLimit = INFINITE_TIME;
                storyProgress = StoryProgress.NORMAL;
                renderCharacterSelection.selLynx(PlayerType.PLAYER1);
                renderCharacterSelection.selRaila(PlayerType.PLAYER2);
                renderStageSelect.selectStage(Stage.DISTANT_ISLE);
                break;
            case 2:
                timeLimit = 30;
                storyProgress = StoryProgress.NORMAL;
                renderCharacterSelection.selAisha(PlayerType.PLAYER1);
                renderCharacterSelection.selLynx(PlayerType.PLAYER2);
                renderStageSelect.selectStage(Stage.IBEX_HILL_NIGHT);
                break;
            case 3:
                timeLimit = INFINITE_TIME;
                storyProgress = StoryProgress.NORMAL;
                renderCharacterSelection.selRaila(PlayerType.PLAYER1);
                renderCharacterSelection.selSubiya(PlayerType.PLAYER2);
                renderStageSelect.selectStage(Stage.CHELSTON_CITY_STREETS);
                break;
            case 4:
                timeLimit = 45;
                storyProgress = StoryProgress.HALFWAY;
                renderCharacterSelection.selRav(PlayerType.PLAYER1);
                renderCharacterSelection.selAde(PlayerType.PLAYER2);
                renderStageSelect.selectStage(Stage.FROZEN_WILDERNESS);
                break;
            case 5:
                timeLimit = 45;
                storyProgress = StoryProgress.NORMAL;
                RenderGamePlay.get().setNumOfBoards(2);
                renderCharacterSelection.selAdam(PlayerType.PLAYER1);
                renderCharacterSelection.selJon(PlayerType.PLAYER2);
                renderStageSelect.selectStage(Stage.FROZEN_WILDERNESS);
                break;
            case 6:
                timeLimit = INFINITE_TIME;
                storyProgress = StoryProgress.NORMAL;
                renderCharacterSelection.selAza(PlayerType.PLAYER1);
                renderCharacterSelection.selNOVAAdam(PlayerType.PLAYER2);
                renderStageSelect.selectStage(Stage.APOCALYPTO);
                break;
            case 7:
                timeLimit = INFINITE_TIME;
                storyProgress = StoryProgress.NORMAL;
                renderCharacterSelection.selSubiya(PlayerType.PLAYER1);
                renderCharacterSelection.selRav(PlayerType.PLAYER2);
                renderStageSelect.selectStage(Stage.CHELSTON_CITY_DOCKS);
                break;
            case 8:
                timeLimit = INFINITE_TIME;
                storyProgress = StoryProgress.NORMAL;
                renderCharacterSelection.selLynx(PlayerType.PLAYER1);
                renderCharacterSelection.selAdam(PlayerType.PLAYER2);
                renderStageSelect.selectStage(Stage.APOCALYPTO);
                break;
            case 9:
                timeLimit = 60;
                storyProgress = StoryProgress.NORMAL;
                renderCharacterSelection.selRaila(PlayerType.PLAYER1);
                renderCharacterSelection.selSorr(PlayerType.PLAYER2);
                renderStageSelect.selectStage(Stage.APOCALYPTO);
                break;
            case 10:
                timeLimit = 90;
                storyProgress = StoryProgress.NORMAL;
                renderCharacterSelection.selSubiya(PlayerType.PLAYER1);
                renderCharacterSelection.selNOVAAdam(PlayerType.PLAYER2);
                renderStageSelect.selectStage(Stage.DISTANT_ISLE_NIGHT);
                break;
            case 11:
                timeLimit = INFINITE_TIME;
                storyProgress = StoryProgress.END;
                renderCharacterSelection.selAdam(PlayerType.PLAYER1);
                renderCharacterSelection.selThing(PlayerType.BOSS);
                renderStageSelect.selectStage(Stage.DESERT_RUINS_NIGHT);
                break;
        }
    }

    public void startStoryMode(int x) {
        var renderGamePlay = RenderGamePlay.get();
        renderGamePlay.newInstance();
        currentScene = x;
        active = false;
        steps.clear();
        stepIndex = 0;
        waitAccum.reset();

        setScene(currentScene);
        var scndGenLegends = ScndGenLegends.get();
        scndGenLegends.loadMode(ModeEnum.STANDARD_GAMEPLAY_START);
        scndGenLegends.setSubMode(SubMode.STORY_MODE);
        beginCinematic();
        renderGamePlay.storyBoard(currentScene);
        buildSceneSteps(currentScene);
        active = true;
        stepIndex = 0;
        scheduleFromCurrent();
    }

    public boolean isActive() {
        return active;
    }

    public void tick(double deltaSeconds) {
        if (!active) {
            return;
        }
        waitAccum.advance(deltaSeconds);
        while (active && waitAccum.consume()) {
            scheduleFromCurrent();
        }
    }

    private void scheduleFromCurrent() {
        while (active && stepIndex < steps.size()) {
            Step step = steps.get(stepIndex++);
            executeStep(step);
            if (step.waitSeconds > 0) {
                waitAccum.setInterval(step.waitSeconds);
                waitAccum.reset();
                return;
            }
            if (step.kind == StepKind.EXIT) {
                return;
            }
        }
        active = false;
    }

    private void executeStep(Step step) {
        switch (step.kind) {
            case WAIT -> {
                // interval already set by scheduleFromCurrent
            }
            case LINE, LINE_NO_WAIT -> {
                if (step.setPortrait) {
                    if (step.clearPortrait) {
                        RenderGamePlay.get().characterPortrait();
                    } else {
                        RenderGamePlay.get().characterPortrait(step.portrait);
                    }
                }
                text = step.line;
                RenderGamePlay.get().storyText(text);
            }
            case ACTION -> {
                if (step.action != null) {
                    step.action.run();
                }
            }
            case EXIT -> exitCinematic(false);
        }
    }

    private double textWait(String line) {
        return (line.length() * textSpeed) / 1000.0;
    }

    private void addWait(double seconds) {
        steps.add(Step.wait(seconds));
    }

    private void addLine(TextKey key) {
        String line = Language.get().get(key);
        steps.add(Step.line(line, textWait(line)));
    }

    private void addLine(CharacterEnum portrait, TextKey key) {
        String line = Language.get().get(key);
        steps.add(Step.line(portrait, line, textWait(line)));
    }

    private void addLineClear(TextKey key) {
        String line = Language.get().get(key);
        steps.add(Step.lineClearPortrait(line, textWait(line)));
    }

    private void addLine(TextKey key, String suffix) {
        String line = Language.get().get(key) + suffix;
        steps.add(Step.line(line, textWait(line)));
    }

    private void addLine(CharacterEnum portrait, TextKey key, String suffix) {
        String line = Language.get().get(key) + suffix;
        steps.add(Step.line(portrait, line, textWait(line)));
    }

    private void addLineClear(TextKey key, String suffix) {
        String line = Language.get().get(key) + suffix;
        steps.add(Step.lineClearPortrait(line, textWait(line)));
    }

    private void addLineNoWait(TextKey key) {
        steps.add(Step.lineNoWait(Language.get().get(key)));
    }

    private void addPortraitThen(CharacterEnum portrait) {
        steps.add(Step.action(() -> RenderGamePlay.get().characterPortrait(portrait)));
    }

    private void addClearPortrait() {
        steps.add(Step.action(() -> RenderGamePlay.get().characterPortrait()));
    }

    private void addExit() {
        steps.add(Step.exit());
    }

    private void buildSceneSteps(int scene) {
        steps.clear();
        switch (scene) {
            case 0 -> buildScene0();
            case 1 -> buildScene1();
            case 2 -> buildScene2();
            case 3 -> buildScene3();
            case 4 -> buildScene4();
            case 5 -> buildScene5();
            case 6 -> buildScene6();
            case 7 -> buildScene7();
            case 8 -> buildScene8();
            case 9 -> buildScene9();
            case 10 -> buildScene10();
            case 11 -> buildScene11();
            default -> {
            }
        }
    }

    private void buildScene0() {
        addWait(2.0);
        addClearPortrait();
        addLine(StoryKey.S1_01);
        addLine(StoryKey.S1_02);
        addLine(StoryKey.S1_03);
        addLine(StoryKey.S1_04);
        addLine(StoryKey.S1_05);
        addLine(StoryKey.S1_06);
        addLine(StoryKey.S1_07);
        addLine(StoryKey.S1_08);
        addLine(StoryKey.S1_09);
        addLine(StoryKey.S1_10);
        addLine(CharacterEnum.RAILA, StoryKey.S1_11);
        addLine(StoryKey.S1_12);
        addLine(StoryKey.S1_13);
        addLine(StoryKey.S1_14);
        addLine(CharacterEnum.RAVAGE, StoryKey.S1_15);
        addLine(StoryKey.S1_16);
        addLine(CharacterEnum.RAILA, StoryKey.S1_17);
        addLine(CharacterEnum.RAVAGE, StoryKey.S1_18);
        addExit();
    }

    private void buildScene1() {
        addWait(2.0);
        addClearPortrait();
        addLine(StoryKey.S2_01);
        addLine(StoryKey.S2_02);
        addLine(StoryKey.S2_03);
        addLine(StoryKey.S2_04);
        addLine(StoryKey.S2_05);
        addLine(CharacterEnum.LYNX, StoryKey.S2_06);
        addLine(StoryKey.S2_07);
        addLineClear(StoryKey.S2_08);
        addLineNoWait(LangKey.PRESS_ENTER_PROCEED);
        addExit();
    }

    private void buildScene2() {
        addWait(2.0);
        addClearPortrait();
        addLine(CharacterEnum.LYNX, StoryKey.S3_01);
        addLine(CharacterEnum.RAILA, StoryKey.S3_02);
        addLineClear(StoryKey.S3_03, " .......");
        addLine(CharacterEnum.AISHA, StoryKey.S3_04);
        addLine(CharacterEnum.LYNX, StoryKey.S3_05);
        addLine(CharacterEnum.AISHA, StoryKey.S3_06);
        addLine(CharacterEnum.LYNX, StoryKey.S3_07);
        addLine(CharacterEnum.AISHA, StoryKey.S3_08);
        addLine(CharacterEnum.LYNX, StoryKey.S3_09);
        addLine(CharacterEnum.RAILA, StoryKey.S3_10);
        addLine(CharacterEnum.SUBIYA, StoryKey.S3_11);
        addLineClear(StoryKey.S3_12);
        addLine(CharacterEnum.AISHA, StoryKey.S3_13);
        addLine(CharacterEnum.LYNX, StoryKey.S3_14);
        addLine(CharacterEnum.AISHA, StoryKey.S3_15);
        addLine(CharacterEnum.LYNX, StoryKey.S3_16);
        addLine(CharacterEnum.AISHA, StoryKey.S3_17);
        addLine(CharacterEnum.AISHA, StoryKey.S3_18);
        addExit();
    }

    private void buildScene3() {
        addWait(2.0);
        addClearPortrait();
        addLine(CharacterEnum.SUBIYA, StoryKey.S4_01);
        addLine(CharacterEnum.RAILA, StoryKey.S4_02);
        addLine(CharacterEnum.SUBIYA, StoryKey.S4_03);
        addLine(CharacterEnum.RAILA, StoryKey.S4_04);
        addLine(CharacterEnum.SUBIYA, StoryKey.S4_05);
        addLine(CharacterEnum.RAILA, StoryKey.S4_06);
        addLine(StoryKey.S4_07);
        addLine(StoryKey.S4_08);
        addLine(StoryKey.S4_09);
        addLine(StoryKey.S4_10);
        addLine(CharacterEnum.SUBIYA, StoryKey.S4_11);
        addLine(StoryKey.S4_12);
        addLine(StoryKey.S4_13);
        addLine(StoryKey.S4_14);
        addLine(StoryKey.S4_15);
        addLine(StoryKey.S4_16);
        addLine(CharacterEnum.RAILA, StoryKey.S4_17);
        addExit();
    }

    private void buildScene4() {
        addWait(2.0);
        addClearPortrait();
        addLine(StoryKey.S5_01);
        addLine(StoryKey.S5_02);
        addLine(CharacterEnum.ADE, StoryKey.S5_03);
        addLine(CharacterEnum.SORROWE, StoryKey.S5_04);
        addLine(CharacterEnum.ADE, StoryKey.S5_05);
        addLine(CharacterEnum.RAVAGE, StoryKey.S5_06);
        addLine(CharacterEnum.ADE, StoryKey.S5_07);
        addLine(CharacterEnum.SORROWE, StoryKey.S5_08);
        addLine(CharacterEnum.ADE, StoryKey.S5_09);
        addLine(CharacterEnum.JONAH, StoryKey.S5_10);
        addLine(CharacterEnum.RAVAGE, StoryKey.S5_11);
        addLine(CharacterEnum.RAVAGE, StoryKey.S5_12);
        addLine(CharacterEnum.ADE, StoryKey.S5_13);
        addExit();
    }

    private void buildScene5() {
        addWait(2.0);
        addLine(CharacterEnum.RAVAGE, StoryKey.S6_01);
        addLine(CharacterEnum.ADE, StoryKey.S6_02);
        addLine(CharacterEnum.RAVAGE, StoryKey.S6_03);
        addLine(CharacterEnum.JONAH, StoryKey.S6_04);
        addLine(CharacterEnum.ADAM, StoryKey.S6_05);
        addLine(CharacterEnum.JONAH, StoryKey.S6_06);
        addLine(CharacterEnum.ADAM, StoryKey.S6_07);
        addLine(CharacterEnum.JONAH, StoryKey.S6_08);
        addLine(CharacterEnum.ADAM, StoryKey.S6_09);
        addLine(CharacterEnum.JONAH, StoryKey.S6_10);
        addLine(CharacterEnum.ADAM, StoryKey.S6_11);
        addLine(CharacterEnum.ADE, StoryKey.S6_12);
        addLine(CharacterEnum.ADAM, StoryKey.S6_13);
        addLine(CharacterEnum.JONAH, StoryKey.S6_14);
        addLine(CharacterEnum.JONAH, StoryKey.S6_15);
        addLine(CharacterEnum.ADE, StoryKey.S6_16);
        addLine(CharacterEnum.ADAM, StoryKey.S6_17);
        addLine(CharacterEnum.ADAM, StoryKey.S6_18);
        addLine(CharacterEnum.JONAH, StoryKey.S6_19);
        addLine(CharacterEnum.ADAM, StoryKey.S6_20);
        addExit();
    }

    private void buildScene6() {
        addWait(2.0);
        addClearPortrait();
        addLine(StoryKey.S7_01);
        addLine(StoryKey.S7_02);
        addLine(StoryKey.S7_03);
        addLine(StoryKey.S7_04);
        addLine(CharacterEnum.AZARIA, StoryKey.S7_05);
        addLine(CharacterEnum.ADAM, StoryKey.S7_06);
        addLine(CharacterEnum.AZARIA, StoryKey.S7_07);
        addLine(CharacterEnum.ADAM, StoryKey.S7_08);
        addLine(CharacterEnum.ADAM, StoryKey.S7_09);
        addLine(CharacterEnum.AZARIA, StoryKey.S7_10);
        addLine(CharacterEnum.ADAM, StoryKey.S7_11);
        addLine(CharacterEnum.ADAM, StoryKey.S7_12);
        addExit();
    }

    private void buildScene7() {
        addWait(2.0);
        addClearPortrait();
        addLine(CharacterEnum.SUBIYA, StoryKey.S8_01);
        addLine(CharacterEnum.RAILA, StoryKey.S8_02);
        addLine(CharacterEnum.RAVAGE, StoryKey.S8_03);
        addLine(CharacterEnum.RAILA, StoryKey.S8_04);
        addLine(CharacterEnum.RAVAGE, StoryKey.S8_05);
        addLine(CharacterEnum.SUBIYA, StoryKey.S8_06);
        addLine(CharacterEnum.RAILA, StoryKey.S8_07);
        addLine(CharacterEnum.SUBIYA, StoryKey.S8_08);
        addLine(CharacterEnum.RAVAGE, StoryKey.S8_09);
        addExit();
    }

    private void buildScene8() {
        addWait(2.0);
        addClearPortrait();
        addLine(StoryKey.S9_01);
        addLine(CharacterEnum.ADAM, StoryKey.S9_02);
        addLine(CharacterEnum.ADAM, StoryKey.S9_03);
        addLine(CharacterEnum.AZARIA, StoryKey.S9_04);
        addLine(CharacterEnum.ADAM, StoryKey.S9_05);
        addLine(CharacterEnum.LYNX, StoryKey.S9_06);
        addLine(CharacterEnum.AZARIA, StoryKey.S9_07);
        addLine(CharacterEnum.ADAM, StoryKey.S9_08);
        addLine(CharacterEnum.LYNX, StoryKey.S9_09);
        addLine(CharacterEnum.ADAM, StoryKey.S9_10);
        addExit();
    }

    private void buildScene9() {
        addWait(2.0);
        addClearPortrait();
        addLine(CharacterEnum.RAILA, StoryKey.S10_01);
        addLine(CharacterEnum.AZARIA, StoryKey.S10_02);
        addLine(CharacterEnum.RAILA, StoryKey.S10_03);
        addLine(CharacterEnum.AZARIA, StoryKey.S10_04);
        addLine(CharacterEnum.RAILA, StoryKey.S10_05);
        addLine(CharacterEnum.AZARIA, StoryKey.S10_06);
        addLine(CharacterEnum.RAILA, StoryKey.S10_07);
        addLine(CharacterEnum.AZARIA, StoryKey.S10_08);
        addLineClear(StoryKey.S10_09);
        addLine(CharacterEnum.SORROWE, StoryKey.S10_10);
        addLine(StoryKey.S10_11);
        addLine(StoryKey.S10_12);
        addLine(CharacterEnum.RAILA, StoryKey.S10_13);
        addLine(CharacterEnum.SORROWE, StoryKey.S10_14);
        addLine(CharacterEnum.RAILA, StoryKey.S10_15);
        addLine(StoryKey.S10_16);
        addLine(CharacterEnum.SORROWE, StoryKey.S10_17);
        addExit();
    }

    private void buildScene10() {
        addWait(2.0);
        addPortraitThen(CharacterEnum.THING);
        steps.add(Step.action(() -> Characters.get().setDamageCounter(PlayerType.PLAYER2, 18)));
        addLine(CharacterEnum.SORROWE, StoryKey.S11_01);
        addLine(CharacterEnum.SUBIYA, StoryKey.S11_02);
        addLine(CharacterEnum.RAILA, StoryKey.S11_03);
        addLine(CharacterEnum.RAILA, StoryKey.S11_04);
        addLine(CharacterEnum.SORROWE, StoryKey.S11_05);
        addLine(CharacterEnum.RAILA, StoryKey.S11_06);
        addLine(CharacterEnum.SORROWE, StoryKey.S11_07);
        addLine(CharacterEnum.ADAM, StoryKey.S11_08);
        addLine(CharacterEnum.RAILA, StoryKey.S11_09);
        addLine(CharacterEnum.NOVA_ADAM, StoryKey.S11_10, " !!!!!!!!!!!!!!");
        addLine(CharacterEnum.RAILA, StoryKey.S11_11);
        addLine(CharacterEnum.LYNX, StoryKey.S11_12);
        addLine(CharacterEnum.RAILA, StoryKey.S11_13);
        addLine(CharacterEnum.ADAM, StoryKey.S11_14);
        addExit();
    }

    private void buildScene11() {
        addWait(2.0);
        addClearPortrait();
        addLine(CharacterEnum.NOVA_ADAM, StoryKey.S12_01);
        addLineClear(StoryKey.S12_02);
        addLine(CharacterEnum.RAILA, StoryKey.S12_03);
        addLine(CharacterEnum.AZARIA, StoryKey.S12_04);
        addLine(CharacterEnum.RAILA, StoryKey.S12_05);
        addLineClear(StoryKey.S12_06);
        addLine(CharacterEnum.RAVAGE, StoryKey.S12_07);
        addLine(CharacterEnum.ADE, StoryKey.S12_08);
        addLine(StoryKey.S12_09);
        addLine(CharacterEnum.NOVA_ADAM, StoryKey.S12_10);
        addLine(CharacterEnum.THING, StoryKey.S12_11);
        addLine(CharacterEnum.SORROWE, StoryKey.S12_12);
        addLine(CharacterEnum.AZARIA, StoryKey.S12_13);
        addLine(CharacterEnum.NOVA_ADAM, StoryKey.S12_14);
        addLine(CharacterEnum.JONAH, StoryKey.S12_15);
        addLine(CharacterEnum.SORROWE, StoryKey.S12_16);
        addLine(CharacterEnum.NOVA_ADAM, StoryKey.S12_17);
        addLine(CharacterEnum.NOVA_ADAM, StoryKey.S12_18);
        addLine(CharacterEnum.JONAH, StoryKey.S12_19);
        addExit();
    }

    /**
     * In playStory scene chars and opp should generate nothin
     */
    private void beginCinematic() {
        storyMusic.play();
        var renderGamePlay = RenderGamePlay.get();
        renderGamePlay.reloadAssets();//set new properties, load relevant sprites
        renderGamePlay.characterPortrait();
        renderGamePlay.storyText("");
        renderGamePlay.playingCutscene = true;
        renderGamePlay.pauseCharacterAtb();
        renderGamePlay.pauseOpponentAtb();
    }

    public void exitCinematic(boolean terminateMode) {
        var renderGamePlay = RenderGamePlay.get();
        if (terminateMode) {
            stopMusic();
            renderGamePlay.musNotice();
        }
        renderGamePlay.playBGMusic();
        renderGamePlay.characterPortrait();
        renderGamePlay.storyText("");
        active = false;
        steps.clear();
        stepIndex = 0;
        waitAccum.reset();
        renderGamePlay.playingCutscene = false;
        renderGamePlay.resumeCharacterAtb();
        renderGamePlay.resumeOpponentAtb();
    }

    public void startFight() {
        exitCinematic(false);
    }

    public void onBackCancel() {
        onAccept();
    }

    public void onAccept() {
        var renderGamePlay = RenderGamePlay.get();
        if (renderGamePlay.isGameOver()) {
            if (renderGamePlay.hasWon()) {
                incrementMode();
                if (RenderStoryMenu.get().moreStages()) {
                    startStoryMode(currentScene);//play next scene
                } else {
                    ScndGenLegends.get().loadMode(ModeEnum.MAIN_MENU);
                }
            } else {
                startStoryMode(currentScene);//try again
                renderGamePlay.onLeaveMode();//stop music!!
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

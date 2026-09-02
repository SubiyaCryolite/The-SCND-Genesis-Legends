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

    private void addLine(int langId) {
        String line = Language.get().get(langId);
        steps.add(Step.line(line, textWait(line)));
    }

    private void addLine(CharacterEnum portrait, int langId) {
        String line = Language.get().get(langId);
        steps.add(Step.line(portrait, line, textWait(line)));
    }

    private void addLineClear(int langId) {
        String line = Language.get().get(langId);
        steps.add(Step.lineClearPortrait(line, textWait(line)));
    }

    private void addLine(int langId, String suffix) {
        String line = Language.get().get(langId) + suffix;
        steps.add(Step.line(line, textWait(line)));
    }

    private void addLine(CharacterEnum portrait, int langId, String suffix) {
        String line = Language.get().get(langId) + suffix;
        steps.add(Step.line(portrait, line, textWait(line)));
    }

    private void addLineClear(int langId, String suffix) {
        String line = Language.get().get(langId) + suffix;
        steps.add(Step.lineClearPortrait(line, textWait(line)));
    }

    private void addLineNoWait(int langId) {
        steps.add(Step.lineNoWait(Language.get().get(langId)));
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
        addLine(174);
        addLine(175);
        addLine(176);
        addLine(431);
        addLine(432);
        addLine(433);
        addLine(434);
        addLine(435);
        addLine(436);
        addLine(437);
        addLine(CharacterEnum.RAILA, 438);
        addLine(439);
        addLine(440);
        addLine(178);
        addLine(CharacterEnum.RAVAGE, 179);
        addLine(180);
        addLine(CharacterEnum.RAILA, 181);
        addLine(CharacterEnum.RAVAGE, 182);
        addExit();
    }

    private void buildScene1() {
        addWait(2.0);
        addClearPortrait();
        addLine(441);
        addLine(183);
        addLine(184);
        addLine(185);
        addLine(186);
        addLine(CharacterEnum.LYNX, 443);
        addLine(444);
        addLineClear(187);
        addLineNoWait(146);
        addExit();
    }

    private void buildScene2() {
        addWait(2.0);
        addClearPortrait();
        addLine(CharacterEnum.LYNX, 188);
        addLine(CharacterEnum.RAILA, 189);
        addLineClear(190, " .......");
        addLine(CharacterEnum.AISHA, 191);
        addLine(CharacterEnum.LYNX, 192);
        addLine(CharacterEnum.AISHA, 193);
        addLine(CharacterEnum.LYNX, 194);
        addLine(CharacterEnum.AISHA, 195);
        addLine(CharacterEnum.LYNX, 196);
        addLine(CharacterEnum.RAILA, 197);
        addLine(CharacterEnum.SUBIYA, 198);
        addLineClear(199);
        addLine(CharacterEnum.AISHA, 200);
        addLine(CharacterEnum.LYNX, 201);
        addLine(CharacterEnum.AISHA, 202);
        addLine(CharacterEnum.LYNX, 203);
        addLine(CharacterEnum.AISHA, 204);
        addLine(CharacterEnum.AISHA, 205);
        addExit();
    }

    private void buildScene3() {
        addWait(2.0);
        addClearPortrait();
        addLine(CharacterEnum.SUBIYA, 206);
        addLine(CharacterEnum.RAILA, 207);
        addLine(CharacterEnum.SUBIYA, 208);
        addLine(CharacterEnum.RAILA, 209);
        addLine(CharacterEnum.SUBIYA, 210);
        addLine(CharacterEnum.RAILA, 211);
        addLine(212);
        addLine(213);
        addLine(214);
        addLine(215);
        addLine(CharacterEnum.SUBIYA, 216);
        addLine(425);
        addLine(426);
        addLine(427);
        addLine(428);
        addLine(429);
        addLine(CharacterEnum.RAILA, 430);
        addExit();
    }

    private void buildScene4() {
        addWait(2.0);
        addClearPortrait();
        addLine(218);
        addLine(219);
        addLine(CharacterEnum.ADE, 220);
        addLine(CharacterEnum.SORROWE, 221);
        addLine(CharacterEnum.ADE, 222);
        addLine(CharacterEnum.RAVAGE, 223);
        addLine(CharacterEnum.ADE, 224);
        addLine(CharacterEnum.SORROWE, 225);
        addLine(CharacterEnum.ADE, 226);
        addLine(CharacterEnum.JONAH, 227);
        addLine(CharacterEnum.RAVAGE, 228);
        addLine(CharacterEnum.RAVAGE, 229);
        addLine(CharacterEnum.ADE, 230);
        addExit();
    }

    private void buildScene5() {
        addWait(2.0);
        addLine(CharacterEnum.RAVAGE, 231);
        addLine(CharacterEnum.ADE, 232);
        addLine(CharacterEnum.RAVAGE, 233);
        addLine(CharacterEnum.JONAH, 234);
        addLine(CharacterEnum.ADAM, 235);
        addLine(CharacterEnum.JONAH, 236);
        addLine(CharacterEnum.ADAM, 237);
        addLine(CharacterEnum.JONAH, 238);
        addLine(CharacterEnum.ADAM, 239);
        addLine(CharacterEnum.JONAH, 240);
        addLine(CharacterEnum.ADAM, 241);
        addLine(CharacterEnum.ADE, 242);
        addLine(CharacterEnum.ADAM, 243);
        addLine(CharacterEnum.JONAH, 244);
        addLine(CharacterEnum.JONAH, 245);
        addLine(CharacterEnum.ADE, 246);
        addLine(CharacterEnum.ADAM, 247);
        addLine(CharacterEnum.ADAM, 248);
        addLine(CharacterEnum.JONAH, 249);
        addLine(CharacterEnum.ADAM, 250);
        addExit();
    }

    private void buildScene6() {
        addWait(2.0);
        addClearPortrait();
        addLine(251);
        addLine(252);
        addLine(253);
        addLine(254);
        addLine(CharacterEnum.AZARIA, 255);
        addLine(CharacterEnum.ADAM, 256);
        addLine(CharacterEnum.AZARIA, 257);
        addLine(CharacterEnum.ADAM, 258);
        addLine(CharacterEnum.ADAM, 259);
        addLine(CharacterEnum.AZARIA, 260);
        addLine(CharacterEnum.ADAM, 261);
        addLine(CharacterEnum.ADAM, 262);
        addExit();
    }

    private void buildScene7() {
        addWait(2.0);
        addClearPortrait();
        addLine(CharacterEnum.SUBIYA, 263);
        addLine(CharacterEnum.RAILA, 264);
        addLine(CharacterEnum.RAVAGE, 265);
        addLine(CharacterEnum.RAILA, 266);
        addLine(CharacterEnum.RAVAGE, 267);
        addLine(CharacterEnum.SUBIYA, 268);
        addLine(CharacterEnum.RAILA, 269);
        addLine(CharacterEnum.SUBIYA, 445);
        addLine(CharacterEnum.RAVAGE, 446);
        addExit();
    }

    private void buildScene8() {
        addWait(2.0);
        addClearPortrait();
        addLine(270);
        addLine(CharacterEnum.ADAM, 271);
        addLine(CharacterEnum.ADAM, 272);
        addLine(CharacterEnum.AZARIA, 273);
        addLine(CharacterEnum.ADAM, 274);
        addLine(CharacterEnum.LYNX, 275);
        addLine(CharacterEnum.AZARIA, 276);
        addLine(CharacterEnum.ADAM, 277);
        addLine(CharacterEnum.LYNX, 278);
        addLine(CharacterEnum.ADAM, 279);
        addExit();
    }

    private void buildScene9() {
        addWait(2.0);
        addClearPortrait();
        addLine(CharacterEnum.RAILA, 280);
        addLine(CharacterEnum.AZARIA, 281);
        addLine(CharacterEnum.RAILA, 282);
        addLine(CharacterEnum.AZARIA, 283);
        addLine(CharacterEnum.RAILA, 284);
        addLine(CharacterEnum.AZARIA, 447);
        addLine(CharacterEnum.RAILA, 285);
        addLine(CharacterEnum.AZARIA, 286);
        addLineClear(287);
        addLine(CharacterEnum.SORROWE, 288);
        addLine(448);
        addLine(449);
        addLine(CharacterEnum.RAILA, 289);
        addLine(CharacterEnum.SORROWE, 290);
        addLine(CharacterEnum.RAILA, 291);
        addLine(292);
        addLine(CharacterEnum.SORROWE, 293);
        addExit();
    }

    private void buildScene10() {
        addWait(2.0);
        addPortraitThen(CharacterEnum.THING);
        steps.add(Step.action(() -> Characters.get().setDamageCounter(PlayerType.PLAYER2, 18)));
        addLine(CharacterEnum.SORROWE, 294);
        addLine(CharacterEnum.SUBIYA, 231);
        addLine(CharacterEnum.RAILA, 295);
        addLine(CharacterEnum.RAILA, 296);
        addLine(CharacterEnum.SORROWE, 297);
        addLine(CharacterEnum.RAILA, 298);
        addLine(CharacterEnum.SORROWE, 299);
        addLine(CharacterEnum.ADAM, 300);
        addLine(CharacterEnum.RAILA, 301);
        addLine(CharacterEnum.NOVA_ADAM, 302, " !!!!!!!!!!!!!!");
        addLine(CharacterEnum.RAILA, 303);
        addLine(CharacterEnum.LYNX, 304);
        addLine(CharacterEnum.RAILA, 305);
        addLine(CharacterEnum.ADAM, 306);
        addExit();
    }

    private void buildScene11() {
        addWait(2.0);
        addClearPortrait();
        addLine(CharacterEnum.NOVA_ADAM, 373);
        addLineClear(374);
        addLine(CharacterEnum.RAILA, 375);
        addLine(CharacterEnum.AZARIA, 376);
        addLine(CharacterEnum.RAILA, 377);
        addLineClear(378);
        addLine(CharacterEnum.RAVAGE, 379);
        addLine(CharacterEnum.ADE, 380);
        addLine(381);
        addLine(CharacterEnum.NOVA_ADAM, 383);
        addLine(CharacterEnum.THING, 384);
        addLine(CharacterEnum.SORROWE, 385);
        addLine(CharacterEnum.AZARIA, 386);
        addLine(CharacterEnum.NOVA_ADAM, 387);
        addLine(CharacterEnum.JONAH, 388);
        addLine(CharacterEnum.SORROWE, 389);
        addLine(CharacterEnum.NOVA_ADAM, 390);
        addLine(CharacterEnum.NOVA_ADAM, 391);
        addLine(CharacterEnum.JONAH, 392);
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

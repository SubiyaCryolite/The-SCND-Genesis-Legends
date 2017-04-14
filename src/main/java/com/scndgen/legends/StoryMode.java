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
package com.scndgen.legends;

import com.scndgen.legends.characters.Characters;
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

    public static boolean notAsked, firstRun = true, doneShowingText = false;
    public static String stat = "";
    public static int max = 11; // starts from 0, max = last stage or scene-1, scene=max+1
    public static int time = 181;
    private static ThreadMP3 storyMus;
    private static String storyText = "";
    private static int opt, tlkSpeed, modeN;
    //story
    private JenesisLanguage langz;
    //thread
    private Thread t;

    public StoryMode() {
        langz = LoginScreen.getInstance().getLangInst();
        modeN = 0;
    }

    public void story(int stage, boolean start) {
        t = new Thread(this);
        t.setName("Story mode thread");
        t.setPriority(5);
        LoginScreen.getInstance().getMenu().getMain().getStory().setCurrMode(stage);
        storyMus = new ThreadMP3(ThreadMP3.storySound(), false);
        tlkSpeed = WindowOptions.txtSpeed;
        notAsked = true;
        opt = -1;

        if (stage == 0) {
            time = 181;
            stat = "nrml";
            LoginScreen.getInstance().getMenu().getMain().getCharSelect().selRaila('c');
            LoginScreen.getInstance().getMenu().getMain().getCharSelect().proceed2 = false;
            LoginScreen.getInstance().getMenu().getMain().getCharSelect().selRav('o');
            RenderStageSelect.stage1();
        }

        if (stage == 1) {
            time = 181;
            stat = "nrml";
            LoginScreen.getInstance().getMenu().getMain().getCharSelect().selLynx('c');
            LoginScreen.getInstance().getMenu().getMain().getCharSelect().proceed2 = false;
            LoginScreen.getInstance().getMenu().getMain().getCharSelect().selRaila('o');
            RenderStageSelect.stage100();
        }

        if (stage == 2) {
            time = 30;
            stat = "nrml";
            LoginScreen.getInstance().getMenu().getMain().getCharSelect().selAisha('c');
            LoginScreen.getInstance().getMenu().getMain().getCharSelect().proceed2 = false;
            LoginScreen.getInstance().getMenu().getMain().getCharSelect().selLynx('o');
            RenderStageSelect.stage5();
        }


        if (stage == 3) {
            time = 181;
            stat = "nrml";
            LoginScreen.getInstance().getMenu().getMain().getCharSelect().selRaila('c');
            LoginScreen.getInstance().getMenu().getMain().getCharSelect().proceed2 = false;
            LoginScreen.getInstance().getMenu().getMain().getCharSelect().selSubiya('o');
            RenderStageSelect.stage4();
        }

        if (stage == 4) {
            time = 45;
            stat = "half way";
            LoginScreen.getInstance().getMenu().getMain().getCharSelect().selRav('c');
            LoginScreen.getInstance().getMenu().getMain().getCharSelect().proceed2 = false;
            LoginScreen.getInstance().getMenu().getMain().getCharSelect().selAde('o');
            RenderStageSelect.stage7();
        }

        if (stage == 5) {
            time = 45;
            stat = "nrml";
            LoginScreen.getInstance().getMenu().getMain().getGame().setNumOfBoards(2);
            LoginScreen.getInstance().getMenu().getMain().getCharSelect().selAdam('c');
            LoginScreen.getInstance().getMenu().getMain().getCharSelect().proceed2 = false;
            LoginScreen.getInstance().getMenu().getMain().getCharSelect().selJon('o');
            RenderStageSelect.stage7();
        }

        if (stage == 6) {
            time = 181;
            stat = "nrml";
            LoginScreen.getInstance().getMenu().getMain().getCharSelect().selAza('c');
            LoginScreen.getInstance().getMenu().getMain().getCharSelect().proceed2 = false;
            LoginScreen.getInstance().getMenu().getMain().getCharSelect().selNOVAAdam('o');
            RenderStageSelect.stage10();
        }

        if (stage == 7) {
            time = 181;
            stat = "nrml";
            LoginScreen.getInstance().getMenu().getMain().getCharSelect().selSubiya('c');
            LoginScreen.getInstance().getMenu().getMain().getCharSelect().proceed2 = false;
            LoginScreen.getInstance().getMenu().getMain().getCharSelect().selRav('o');
            RenderStageSelect.stage2();
        }

        if (stage == 8) {
            time = 181;
            stat = "nrml";
            LoginScreen.getInstance().getMenu().getMain().getCharSelect().selLynx('c');
            LoginScreen.getInstance().getMenu().getMain().getCharSelect().proceed2 = false;
            LoginScreen.getInstance().getMenu().getMain().getCharSelect().selAdam('o');
            RenderStageSelect.stage10();
        }

        if (stage == 9) {
            time = 60;
            stat = "nrml";
            LoginScreen.getInstance().getMenu().getMain().getCharSelect().selRaila('c');
            LoginScreen.getInstance().getMenu().getMain().getCharSelect().proceed2 = false;
            LoginScreen.getInstance().getMenu().getMain().getCharSelect().selSorr('o');
            RenderStageSelect.stage10();
        }

        if (stage == 10) {
            time = 90;
            stat = "nrml";
            LoginScreen.getInstance().getMenu().getMain().getCharSelect().selSubiya('c');
            LoginScreen.getInstance().getMenu().getMain().getCharSelect().proceed2 = false;
            LoginScreen.getInstance().getMenu().getMain().getCharSelect().selNOVAAdam('o');
            RenderStageSelect.stage11();
        }

        if (stage == 11) {
            time = 181;
            stat = "nrml";
            LoginScreen.getInstance().getMenu().getMain().getCharSelect().selAdam('c');
            LoginScreen.getInstance().getMenu().getMain().getCharSelect().proceed2 = false;
            LoginScreen.getInstance().getMenu().getMain().getCharSelect().selThing('x');
            RenderStageSelect.stage13();
        }

        if (start) {
            startStoryMode(stage);
        }
    }

    public void startStoryMode(int x) {
        modeN = x;
        t.start();
    }

    /**
     * In story mode chars and opp should generate nothin
     */
    private void storyIn() {
        storyMus.play();
        ThreadGameInstance.story = true;
        doneShowingText = false;
        LoginScreen.getInstance().getMenu().getMain().getGame().getGameInstance().pauseActivityRegen();
        LoginScreen.getInstance().getMenu().getMain().getGame().getGameInstance().pauseActivityRegenOpp();
    }

    private void storyOut(boolean tx) {
        if (tx) {
            storyMus.close();
            LoginScreen.getInstance().getMenu().getMain().getGame().getGameInstance().playMusicNow();
            LoginScreen.getInstance().getMenu().getMain().getGame().getGameInstance().musNotice();
        }
        LoginScreen.getInstance().getMenu().getMain().getGame().charPortBlank();
        LoginScreen.getInstance().getMenu().getMain().getGame().flashyText("");
        t.stop();
        ThreadGameInstance.story = false;
        doneShowingText = true;
        LoginScreen.getInstance().getMenu().getMain().getGame().getGameInstance().resumeActivityRegen();
        LoginScreen.getInstance().getMenu().getMain().getGame().getGameInstance().resumeActivityRegenOpp();
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
                //Characters.setDamageCounter('o',14);

                t.sleep(5000);
                LoginScreen.getInstance().getMenu().getMain().getGame().changeStoryBoard(0);

                storyText = langz.getLine(174);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);


                storyText = langz.getLine(175);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                storyText = langz.getLine(176);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);


                LoginScreen.getInstance().getMenu().getMain().getGame().changeStoryBoard(1);
                storyText = langz.getLine(177);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(0);
                storyText = langz.getLine(178);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(4);
                storyText = langz.getLine(179);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(4);
                storyText = langz.getLine(372);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(1); //sub
                storyText = langz.getLine(180);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(4); //rav
                storyText = langz.getLine(181);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(1); //sub
                storyText = langz.getLine(182);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);
                LoginScreen.getInstance().getMenu().getMain().getGame().charPortBlank();


                storyOut(false);
            }

            if (modeN == 1) //scene 2
            {
                LoginScreen.getInstance().getMenu().getMain().storyGame();
                storyIn();
                firstRun = false;

                t.sleep(5000);
                LoginScreen.getInstance().getMenu().getMain().getGame().changeStoryBoard(1);

                storyText = langz.getLine(183);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                storyText = langz.getLine(184);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                storyText = langz.getLine(185);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().changeStoryBoard(2);
                storyText = langz.getLine(186);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                storyText = langz.getLine(187);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                storyText = langz.getLine(146);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);

                storyOut(false);
            }

            if (modeN == 2) //scene 3
            {
                LoginScreen.getInstance().getMenu().getMain().storyGame();
                storyIn();
                firstRun = false;

                t.sleep(5000);
                LoginScreen.getInstance().getMenu().getMain().getGame().changeStoryBoard(2);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(2); //lynx
                storyText = langz.getLine(188);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(0); //raila
                storyText = langz.getLine(189);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(2); //lynx
                storyText = langz.getLine(190) + " .......";
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(3); //aisha
                storyText = langz.getLine(191);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(2);
                storyText = langz.getLine(192);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(3);
                storyText = langz.getLine(193);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(2);
                storyText = langz.getLine(194);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(3);
                storyText = langz.getLine(195);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(2);
                storyText = langz.getLine(196);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(0);
                storyText = langz.getLine(197);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(1);
                storyText = langz.getLine(198);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().changeStoryBoard(3);
                LoginScreen.getInstance().getMenu().getMain().getGame().charPortBlank();
                storyText = langz.getLine(199);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(3);
                storyText = langz.getLine(200);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(2);
                storyText = langz.getLine(201);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(3);
                storyText = langz.getLine(202);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(2);
                storyText = langz.getLine(203);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(3);
                storyText = langz.getLine(204);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(2);
                storyText = langz.getLine(205);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                storyOut(false);
            }

            if (modeN == 3) //scene 4
            {
                LoginScreen.getInstance().getMenu().getMain().storyGame();
                storyIn();
                firstRun = false;

                t.sleep(5000);
                LoginScreen.getInstance().getMenu().getMain().getGame().changeStoryBoard(3);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(1);
                storyText = langz.getLine(206);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(1);
                storyText = langz.getLine(207);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(1);
                storyText = langz.getLine(208);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().changeStoryBoard(5);
                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(0);
                storyText = langz.getLine(209);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(1);
                storyText = langz.getLine(210);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(0);
                storyText = langz.getLine(211);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(0);
                storyText = langz.getLine(212);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(0);
                storyText = langz.getLine(213);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(0);
                storyText = langz.getLine(214);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(0);
                storyText = langz.getLine(215);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(1);
                storyText = langz.getLine(216);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(0);
                storyText = langz.getLine(203);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(1);
                storyText = langz.getLine(217);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                storyOut(false);
            }

            if (modeN == 4) //scene 5
            {
                LoginScreen.getInstance().getMenu().getMain().storyGame();
                storyIn();
                firstRun = false;

                t.sleep(5000);
                LoginScreen.getInstance().getMenu().getMain().getGame().changeStoryBoard(5);

                storyText = langz.getLine(218) + " .......";
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                storyText = langz.getLine(219);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(5); //ade
                storyText = langz.getLine(220);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(10); //sorrowe
                storyText = langz.getLine(221);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(5); //ade
                storyText = langz.getLine(222);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(4); //ravage
                storyText = langz.getLine(223);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(5); //ade
                storyText = langz.getLine(224);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(10);
                storyText = langz.getLine(225);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(5); //ade
                storyText = langz.getLine(226);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(6); //jonah
                storyText = langz.getLine(227);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(4);
                storyText = langz.getLine(228);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(4);
                storyText = langz.getLine(229);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(5);//ade
                storyText = langz.getLine(230);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                storyOut(false);
            }

            if (modeN == 5) //scene 6
            {
                LoginScreen.getInstance().getMenu().getMain().storyGame();
                storyIn();
                firstRun = false;

                t.sleep(5000);
                LoginScreen.getInstance().getMenu().getMain().getGame().changeStoryBoard(5);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(4);
                storyText = langz.getLine(231);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(5);//ade
                storyText = langz.getLine(232);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(4);
                storyText = langz.getLine(233);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(6);
                storyText = langz.getLine(234);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(7);
                storyText = langz.getLine(235);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(6);
                storyText = langz.getLine(236);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(7);
                storyText = langz.getLine(237);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(6);
                storyText = langz.getLine(238);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(7);
                storyText = langz.getLine(239);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(6);
                storyText = langz.getLine(240);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(7);
                storyText = langz.getLine(241);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(6);
                storyText = langz.getLine(242);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(7);
                storyText = langz.getLine(243);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(6);
                storyText = langz.getLine(244);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(6);
                storyText = langz.getLine(245);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(5);
                storyText = langz.getLine(246);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(7);
                storyText = langz.getLine(247);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(7);
                storyText = langz.getLine(248);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(6);
                storyText = langz.getLine(249);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(7);
                storyText = langz.getLine(250);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                storyOut(false);
            }

            if (modeN == 6) //scene 7
            {
                LoginScreen.getInstance().getMenu().getMain().storyGame();
                storyIn();
                firstRun = false;

                t.sleep(5000);
                LoginScreen.getInstance().getMenu().getMain().getGame().changeStoryBoard(4);

                storyText = langz.getLine(251);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                storyText = langz.getLine(252);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                storyText = langz.getLine(253);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().changeStoryBoard(6);
                storyText = langz.getLine(254);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(9);
                storyText = langz.getLine(255);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(7);
                storyText = langz.getLine(256);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(9);
                storyText = langz.getLine(257);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(7);
                storyText = langz.getLine(258);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(7);
                storyText = langz.getLine(259);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(9);
                storyText = langz.getLine(260);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(7);
                storyText = langz.getLine(261);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(7);
                storyText = langz.getLine(262);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                storyOut(false);
            }


            if (modeN == 7) //scene 8
            {
                LoginScreen.getInstance().getMenu().getMain().storyGame();
                storyIn();
                firstRun = false;

                t.sleep(5000);
                LoginScreen.getInstance().getMenu().getMain().getGame().changeStoryBoard(6);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(1);
                storyText = langz.getLine(263);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(0);
                storyText = langz.getLine(264);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(4);
                storyText = langz.getLine(265);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(0);
                storyText = langz.getLine(266);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(4);
                storyText = langz.getLine(267);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(1);
                storyText = langz.getLine(268);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(0);
                storyText = ".......... " + langz.getLine(269);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                storyOut(false);
            }

            if (modeN == 8) //scene 9
            {
                LoginScreen.getInstance().getMenu().getMain().storyGame();
                storyIn();
                firstRun = false;

                t.sleep(5000);
                LoginScreen.getInstance().getMenu().getMain().getGame().changeStoryBoard(8);


                storyText = langz.getLine(270);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(7);
                storyText = langz.getLine(271);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(7);
                storyText = langz.getLine(272);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(9);
                storyText = langz.getLine(273);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(7);
                storyText = langz.getLine(274);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(2);
                storyText = langz.getLine(275);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(9);
                storyText = langz.getLine(276);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(7);
                storyText = langz.getLine(277);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(7);
                storyText = langz.getLine(231) + " !!!";
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(2);
                storyText = langz.getLine(278);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(7);
                storyText = langz.getLine(279) + " !!!";
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                storyOut(false);
            }

            //new stage they arrive

            if (modeN == 9) //scene 10
            {
                LoginScreen.getInstance().getMenu().getMain().storyGame();
                storyIn();
                firstRun = false;

                t.sleep(5000);
                LoginScreen.getInstance().getMenu().getMain().getGame().changeStoryBoard(9);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(0);
                storyText = langz.getLine(280) + " !!!";
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(9);
                storyText = langz.getLine(281);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(0);
                storyText = langz.getLine(282);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(9);
                storyText = langz.getLine(283);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(0);
                storyText = langz.getLine(284);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(0);
                storyText = langz.getLine(285);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(9);
                storyText = langz.getLine(286);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortBlank();
                storyText = langz.getLine(287) + " ...";
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(10);
                storyText = langz.getLine(288);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(0);
                storyText = langz.getLine(289) + " !!";
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(10);
                storyText = langz.getLine(290);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(0);
                storyText = langz.getLine(291);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(0);
                storyText = langz.getLine(292);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(10);
                storyText = langz.getLine(293);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                storyOut(false);
            }

            if (modeN == 10) //scene 11
            {
                LoginScreen.getInstance().getMenu().getMain().storyGame();
                storyIn();
                firstRun = false;

                t.sleep(5000);
                LoginScreen.getInstance().getMenu().getMain().getGame().changeStoryBoard(11);

                //set difficulty - hard
                Characters.setDamageCounter('o', 18);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(10);
                storyText = langz.getLine(294) + " !!!";
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(0);
                storyText = langz.getLine(231) + " !!!";
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(1);
                storyText = langz.getLine(295);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(0);
                storyText = langz.getLine(296);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(10);
                storyText = langz.getLine(297);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(0);
                storyText = langz.getLine(298);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(10);
                storyText = langz.getLine(299) + "!!";
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(7);
                storyText = langz.getLine(300) + " !!!";
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(1);
                storyText = langz.getLine(301) + " ?";
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(7);
                storyText = langz.getLine(302) + " !!!!!!!!!!!!!!";
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(1);
                storyText = langz.getLine(303) + " !!!";
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(2);
                storyText = langz.getLine(304);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(1);
                storyText = langz.getLine(305);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(7);
                storyText = langz.getLine(306);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                storyOut(false);
            }

            if (modeN == 11) //scene 12
            {
                LoginScreen.getInstance().getMenu().getMain().storyGame();
                storyIn();
                firstRun = false;

                t.sleep(5000);
                LoginScreen.getInstance().getMenu().getMain().getGame().changeStoryBoard(10);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(10);
                storyText = langz.getLine(373);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortBlank();
                storyText = langz.getLine(374);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(1);
                storyText = langz.getLine(375);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(9);
                storyText = langz.getLine(376);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(1);
                storyText = langz.getLine(377);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortBlank();
                storyText = langz.getLine(378);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(4);
                storyText = langz.getLine(379);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(5);
                storyText = langz.getLine(380);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                storyText = langz.getLine(381);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(8);
                storyText = langz.getLine(383);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(11);
                storyText = langz.getLine(384);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(10);
                storyText = langz.getLine(385);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(9);
                storyText = langz.getLine(386);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(8);
                storyText = langz.getLine(387);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(6);
                storyText = langz.getLine(388);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(10);
                storyText = langz.getLine(389);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(8);
                storyText = langz.getLine(390);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(8);
                storyText = langz.getLine(391);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                LoginScreen.getInstance().getMenu().getMain().getGame().charPortSet(6);
                storyText = langz.getLine(392);
                LoginScreen.getInstance().getMenu().getMain().getGame().flashyText(storyText);
                t.sleep(storyText.length() * tlkSpeed);

                storyOut(false);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(StoryMode.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void pauseDialogue() {
        t.suspend();
    }

    public void resumeDialogue() {
        t.resume();
    }

    public void skipDialogue() {
        t.stop();
        storyOut(true);
    }
}

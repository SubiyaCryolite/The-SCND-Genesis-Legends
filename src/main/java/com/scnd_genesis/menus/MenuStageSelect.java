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
package com.scnd_genesis.menus;

import com.scnd_genesis.LoginScreen;
import com.scnd_genesis.drawing.DrawGame;
import com.scnd_genesis.drawing.DrawStageSel;
import com.scnd_genesis.windows.WindowMain;

import javax.swing.*;
import java.awt.*;

public class MenuStageSelect extends DrawStageSel {

    //♩♪♬♫
    public static String[] trax = {"\"The King is Dead\" by \"Mattias Westlund\" from \"The Battle for Wesnoth OST\"", //0
            "\"vengeful\" by \"Jeremy Nicoll\" from \"The Battle for Wesnoth OST\"", //1
            "\"The City Falls\" by \"Doug Kaufman\" from \"The Battle for Wesnoth OST\"", //2
            "\"Suspense\" by \"Ryan Reilly\" from \"The Battle for Wesnoth OST\"", //3
            "\"Elvish theme\" by \"Doug Kaufman\" from \"The Battle for Wesnoth OST\"", //4
            "\"Breaking the Chains\" by \"Mattias Westlund\" from \"The Battle for Wesnoth OST\"", //5
            "\"Battle Music\" by \"Aleksi Aubry-Carlson\" from \"The Battle for Wesnoth OST\""}; //6
    public static String[] musFiles = {"Mattias Westlund - The King is Dead",
            "Jeremy Nicoll - Vengeful Pursuit",
            "Doug Kaufman - The City Falls",
            "Ryan Reilly - Suspense",
            "Doug Kaufman - Elvish theme",
            "Mattias Westlund - Breaking the Chains",
            "Aleksi Aubry-Carlson - Battle Music"};
    public static int musicInt = 0;
    public static String charPrevLoc = "images/trans.png", oppPrevLoc = "images/trans.png";
    public static boolean selectedStage = false;
    private static int source;

    public MenuStageSelect() {
        initializePanel();
    }

    /**
     * SHows loading screen
     */
    public static void nowLoading() {
        selectedStage = true;
    }

    public static void SelectStageNow() {
        if (mode == 1) {
            if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.singlePlayer) || LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.singlePlayer2) || LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanHost)) {
                source = stageSelIndex;
                nowLoading();
                if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanHost)) {
                    LoginScreen.getLoginScreen().getMenu().getMain().sendToClient("loadingGVSHA");
                }
            }//repaint();
        } //random stage
        else {
            source = (int) (Math.random() * (numberOfStages - 1));
            if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.singlePlayer) || LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.singlePlayer2) || LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanHost)) {
                nowLoading();
                if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanHost)) {
                    LoginScreen.getLoginScreen().getMenu().getMain().sendToClient("loadingGVSHA");
                }
            }
        }

        if (source == 0) {
            if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.singlePlayer) || LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.singlePlayer2) || LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanHost)) {
                stage1();
                if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanHost)) {
                    LoginScreen.getLoginScreen().getMenu().getMain().sendToClient("stage1_vgdt");
                }
            }
        }

        if (source == 1) {
            if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.singlePlayer) || LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.singlePlayer2) || LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanHost)) {
                stage2();
                if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanHost)) {
                    LoginScreen.getLoginScreen().getMenu().getMain().sendToClient("stage2_vgdt");
                }
            }
        }

        if (source == 2) {
            if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.singlePlayer) || LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.singlePlayer2) || LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanHost)) {
                stage3();
                if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanHost)) {
                    LoginScreen.getLoginScreen().getMenu().getMain().sendToClient("stage3_vgdt");
                }
            }
        }

        if (source == 3) {
            if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.singlePlayer) || LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.singlePlayer2) || LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanHost)) {
                stage4();
                if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanHost)) {
                    LoginScreen.getLoginScreen().getMenu().getMain().sendToClient("stage4_vgdt");
                }
            }
        }

        if (source == 4) {
            if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.singlePlayer) || LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.singlePlayer2) || LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanHost)) {
                stage5();
                if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanHost)) {
                    LoginScreen.getLoginScreen().getMenu().getMain().sendToClient("stage5_vgdt");
                }
            }
        }

        if (source == 5) {
            if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.singlePlayer) || LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.singlePlayer2) || LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanHost)) {
                stage6();
                if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanHost)) {
                    LoginScreen.getLoginScreen().getMenu().getMain().sendToClient("stage6_vgdt");
                }
            }
        }

        if (source == 6) {
            if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.singlePlayer) || LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.singlePlayer2) || LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanHost)) {
                stage7();
                if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanHost)) {
                    LoginScreen.getLoginScreen().getMenu().getMain().sendToClient("stage7_vgdt");
                }
            }
        }

        if (source == 10) {
            if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.singlePlayer) || LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.singlePlayer2) || LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanHost)) {
                stage100();
                if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanHost)) {
                    LoginScreen.getLoginScreen().getMenu().getMain().sendToClient("stage100_vgdt");
                }
            }
        }

        if (source == 7) {
            if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.singlePlayer) || LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.singlePlayer2) || LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanHost)) {
                stage8();
                if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanHost)) {
                    LoginScreen.getLoginScreen().getMenu().getMain().sendToClient("stage8_vgdt");
                }
            }
        }

        if (source == 8) {
            if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.singlePlayer) || LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.singlePlayer2) || LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanHost)) {
                stage9();
                if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanHost)) {
                    LoginScreen.getLoginScreen().getMenu().getMain().sendToClient("stage9_vgdt");
                }
            }
        }

        if (source == 9) {
            if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.singlePlayer) || LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.singlePlayer2) || LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanHost)) {
                stage10();
                if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanHost)) {
                    LoginScreen.getLoginScreen().getMenu().getMain().sendToClient("stage10_vgdt");
                }
            }
        }

        if (source == 11) {
            if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.singlePlayer) || LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.singlePlayer2) || LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanHost)) {
                stage11();
                if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanHost)) {
                    LoginScreen.getLoginScreen().getMenu().getMain().sendToClient("stage11_vgdt");
                }
            }
        }

        if (source == 12) {
            if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.singlePlayer) || LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.singlePlayer2) || LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanHost)) {
                stage12();
                if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanHost)) {
                    LoginScreen.getLoginScreen().getMenu().getMain().sendToClient("stage12_vgdt");
                }
            }
        }

        if (source == 13) {
            if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.singlePlayer) || LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.singlePlayer2) || LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanHost)) {
                stage13();
                if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanHost)) {
                    LoginScreen.getLoginScreen().getMenu().getMain().sendToClient("stage13_vgdt");
                }
            }
        }

        if (source == 14) {
            if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.singlePlayer) || LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.singlePlayer2) || LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanHost)) {
                stage14();
                if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanHost)) {
                    LoginScreen.getLoginScreen().getMenu().getMain().sendToClient("stage14_vgdt");
                }
            }
        }

        if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.singlePlayer) || LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.singlePlayer2) || LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanHost) || LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase("watch")) {
            if (LoginScreen.getLoginScreen().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanHost)) {
                LoginScreen.getLoginScreen().getMenu().getMain().sendToClient("gameStart7%^&");
            }
            start();
        }
    }

    public static void defValue() {

        setStage(0);
        DrawGame.fgx = 0;
        DrawGame.fgy = 10;
        DrawGame.fgxInc = 1;
        DrawGame.fgyInc = 1;
        DrawGame.animLoops = 10;
        DrawGame.animDirection = "vert";
        DrawGame.verticalMove = "no";
        DrawGame.animLayer = "both";
        DrawGame.delay = 33;
        DrawGame.ambSpeed1 = 4;
        DrawGame.ambSpeed2 = 3;
        musicInt = 0;
        getReady();
    }

    /**
     * Ibex Hill- day
     */
    public static void stage1() //
    {
        defValue();
    }

    /**
     * Chelston City docks
     */
    public static void stage2() {
        setStage(1);
        DrawGame.fgx = 0;
        DrawGame.fgy = 0;
        DrawGame.fgxInc = 1;
        DrawGame.fgyInc = 1;
        DrawGame.animLoops = 20;
        DrawGame.animDirection = "none";
        DrawGame.verticalMove = "no";
        DrawGame.animLayer = "none";
        DrawGame.delay = 66;
        DrawGame.ambSpeed1 = 0;
        DrawGame.ambSpeed2 = 0;
        musicInt = 1;
        getReady();
    }

    /**
     * The Ruined Hall
     */
    public static void stage3() {
        setStage(2);
        DrawGame.fgx = 0;
        DrawGame.fgy = 0;
        DrawGame.fgxInc = 5;
        DrawGame.fgyInc = 1;
        DrawGame.animLoops = 4;
        DrawGame.animDirection = "none";
        DrawGame.verticalMove = "no";
        DrawGame.animLayer = "forg";
        DrawGame.delay = 33;
        DrawGame.ambSpeed1 = 2;
        DrawGame.ambSpeed2 = 1;
        musicInt = 2;
        getReady();
    }

    /**
     * Chelston City - Streets
     */
    public static void stage4() {
        setStage(3);
        DrawGame.fgx = 0;
        DrawGame.fgy = 0;
        DrawGame.fgxInc = 1;
        DrawGame.fgyInc = 1;
        DrawGame.animLoops = 20;
        DrawGame.animDirection = "none";
        DrawGame.verticalMove = "no";
        DrawGame.animLayer = "none";
        DrawGame.delay = 66;
        DrawGame.ambSpeed1 = 0;
        DrawGame.ambSpeed2 = 0;
        musicInt = 3;
        getReady();
    }

    /**
     * Ibex Hill - Night
     */
    public static void stage5() {
        setStage(4);
        DrawGame.fgx = 0;
        DrawGame.fgy = 10;
        DrawGame.fgxInc = 1;
        DrawGame.fgyInc = 1;
        DrawGame.animLoops = 10;
        DrawGame.animDirection = "vert";
        DrawGame.verticalMove = "no";
        DrawGame.animLayer = "both";
        DrawGame.delay = 33;
        DrawGame.ambSpeed1 = 4;
        DrawGame.ambSpeed2 = 3;
        musicInt = 4;
        getReady();
    }

    /**
     * Scorched Ruins
     */
    public static void stage6() {
        setStage(5);
        DrawGame.fgx = 0;
        DrawGame.fgy = 0;
        DrawGame.fgxInc = 5;
        DrawGame.fgyInc = 1;
        DrawGame.animLoops = 4;
        DrawGame.animDirection = "none";
        DrawGame.verticalMove = "no";
        DrawGame.animLayer = "forg";
        DrawGame.delay = 33;
        DrawGame.ambSpeed1 = 2;
        DrawGame.ambSpeed2 = 1;
        musicInt = 5;
        getReady();
    }

    /**
     * Frozen Wilderness
     */
    public static void stage7() {
        setStage(6);
        DrawGame.fgx = 0;
        DrawGame.fgy = 10;
        DrawGame.fgxInc = 5;
        DrawGame.fgyInc = 1;
        DrawGame.animLoops = 4;
        DrawGame.animDirection = "none";
        DrawGame.verticalMove = "yes";
        DrawGame.animLayer = "both";
        DrawGame.delay = 33;
        DrawGame.ambSpeed1 = 2;
        DrawGame.ambSpeed2 = 1;
        musicInt = 6;
        getReady();
    }

    /**
     * Distant Isle
     */
    public static void stage100() {
        setStage(10);
        DrawGame.fgx = -40;
        DrawGame.fgy = 20;
        DrawGame.fgxInc = 2;
        DrawGame.fgyInc = 1;
        DrawGame.animLoops = 20;
        DrawGame.animDirection = "rot";
        DrawGame.verticalMove = "no";
        DrawGame.animLayer = "back";
        DrawGame.delay = 25;
        DrawGame.ambSpeed1 = 1;
        DrawGame.ambSpeed2 = 2;
        musicInt = 0;
        getReady();
    }

    public static void stage12() {
        setStage(12);
        DrawGame.fgx = 0;
        DrawGame.fgy = 0;
        DrawGame.fgxInc = 1;
        DrawGame.fgyInc = 1;
        DrawGame.animLoops = 20;
        DrawGame.animDirection = "none";
        DrawGame.verticalMove = "no";
        DrawGame.animLayer = "none";
        DrawGame.delay = 66;
        DrawGame.ambSpeed1 = 0;
        DrawGame.ambSpeed2 = 0;
        musicInt = 3;
        getReady();
    }

    /**
     * Distant Isle night
     */
    public static void stage11() {
        setStage(11);
        DrawGame.fgx = -40;
        DrawGame.fgy = 20;
        DrawGame.fgxInc = 2;
        DrawGame.fgyInc = 1;
        DrawGame.animLoops = 20;
        DrawGame.animDirection = "rot";
        DrawGame.verticalMove = "no";
        DrawGame.animLayer = "back";
        DrawGame.delay = 25;
        DrawGame.ambSpeed1 = 1;
        DrawGame.ambSpeed2 = 2;
        musicInt = 1;
        getReady();
    }

    /**
     * Hidden Cave
     */
    public static void stage8() {
        setStage(7);
        DrawGame.fgx = 0;
        DrawGame.fgy = 0;
        DrawGame.fgxInc = 1;
        DrawGame.fgyInc = 1;
        DrawGame.animLoops = 20;
        DrawGame.animDirection = "none";
        DrawGame.verticalMove = "no";
        DrawGame.animLayer = "none";
        DrawGame.delay = 66;
        DrawGame.ambSpeed1 = 0;
        DrawGame.ambSpeed2 = 0;
        musicInt = 2;
        getReady();
    }

    /**
     * African Village
     */
    public static void stage9() {
        setStage(8);
        DrawGame.fgx = 0;
        DrawGame.fgy = 0;
        DrawGame.fgxInc = 1;
        DrawGame.fgyInc = 1;
        DrawGame.animLoops = 20;
        DrawGame.animDirection = "none";
        DrawGame.verticalMove = "no";
        DrawGame.animLayer = "back";
        DrawGame.delay = 122;
        DrawGame.ambSpeed1 = 2;
        DrawGame.ambSpeed2 = 1;
        musicInt = 3;
        getReady();
    }

    /**
     * The Apocalypse
     */
    public static void stage10() {
        setStage(9);
        DrawGame.fgx = 0;
        DrawGame.fgy = 0;
        DrawGame.fgxInc = 5;
        DrawGame.fgyInc = 1;
        DrawGame.animLoops = 4;
        DrawGame.animDirection = "none";
        DrawGame.verticalMove = "no";
        DrawGame.animLayer = "both";
        DrawGame.delay = 33;
        DrawGame.ambSpeed1 = 2;
        DrawGame.ambSpeed2 = 1;
        musicInt = 4;
        getReady();
    }

    public static void stage13() {
        setStage(13);
        DrawGame.fgx = 0;
        DrawGame.fgy = 0;
        DrawGame.fgxInc = 5;
        DrawGame.fgyInc = 1;
        DrawGame.animLoops = 4;
        DrawGame.animDirection = "none";
        DrawGame.verticalMove = "no";
        DrawGame.animLayer = "forg";
        DrawGame.delay = 33;
        DrawGame.ambSpeed1 = 2;
        DrawGame.ambSpeed2 = 1;
        musicInt = 1;
        getReady();
    }

    public static void stage14() {
        setStage(14);
        DrawGame.fgx = 0;
        DrawGame.fgy = 0;
        DrawGame.fgxInc = 1;
        DrawGame.fgyInc = 1;
        DrawGame.animLoops = 20;
        DrawGame.animDirection = "none";
        DrawGame.verticalMove = "no";
        DrawGame.animLayer = "none";
        DrawGame.delay = 66;
        DrawGame.ambSpeed1 = 0;
        DrawGame.ambSpeed2 = 0;
        musicInt = 3;
        getReady();
    }

    public static String getTrack() {
        return musFiles[musicInt];
    }

    private static void getReady() {
        DrawGame.activeStage = Integer.parseInt(getStage().substring(4));
        DrawGame.bgLocation = "images/" + getStage() + ".png";
        DrawGame.fgLocation = "images/" + getStage() + "fg.png";
    }

    public static void loadTxt(String args) {
        loadTxt = args;
    }

    public static void start() {
        getReady();

        new Thread() {

            @Override
            @SuppressWarnings("static-access")
            public void run() {
                try {
                    this.sleep(1000);
                    LoginScreen.getLoginScreen().getMenu().getMain().newGame();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }.start();
    }

    protected void initializePanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.black, 1));
    }

    /**
     * Character select screen, move up
     */
    public void moveUp() {
        upMove();
    }

    /**
     * Character select screen, move down
     */
    public void moveDown() {
        downMove();
    }

    /**
     * Character select screen, move right
     */
    public void moveRight() {
        rightMove();
    }

    /**
     * Character select screen, move left
     */
    public void moveLeft() {
        leftMove();
    }

    public void screenShot() {
        captureScreenShot();
    }


    /**
     * Gets the number of columns in the character select screen
     *
     * @return number of columns
     */
    public int getNumberOfCharColumns() {
        return horizColumns;
    }

    /**
     * Gets the char caption spacer
     *
     * @return spacer
     */
    public int getCharHSpacer() {
        return vSpacer;
    }

    /**
     * Gets the char caption spacer
     *
     * @return spacer
     */
    public int getCharVSpacer() {
        return hSpacer;
    }

    /**
     * Get starting x coordinate
     *
     * @return starting x coordinate
     */
    public int getStartX() {
        return hPos;
    }

    /**
     * Returns the starting Y coordinate
     *
     * @return starting y
     */
    public int getStartY() {
        return firstLine;
    }

    /**
     * Get number of char rows
     *
     * @return number of rows
     */
    public int getCharRows() {
        return verticalRows;
    }

    /**
     * Animates caption
     */
    public void animateCaption() {
        capAnim();
    }
}

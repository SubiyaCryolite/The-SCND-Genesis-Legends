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
package com.scndgen.legends.scene;

import com.scndgen.legends.enums.StageSelection;
import com.scndgen.legends.render.RenderCharacterSelectionScreen;
import com.scndgen.legends.render.RenderGameplay;
import com.scndgen.legends.windows.MainWindow;
import io.github.subiyacryolite.enginev1.JenesisMode;

/**
 * @author: Ifunga Ndana
 * @class: drawPrevChar
 * This class creates a graphical preview of the character and opponent
 */
public abstract class StageSelect extends JenesisMode {

    protected int lastRow, currentSlot = 0, xCordCloud = 0, xCordCloud2 = 0, charYcap = 0, charXcap = 0, stageSelIndex = 0, hIndex = 1, x = 0, y = 0, vIndex = 0, vSpacer = 52, hSpacer = 92, hPos = 288, firstLine = 105;
    protected StageSelection mode = StageSelection.NORMAL;
    protected int ambientMusicIndex = 0;
    protected int source;
    protected int numberOfStages;
    protected boolean selectedStage = false;
    protected String[] stagePrevLox = new String[]{"bgBG1", "bgBG2", "bgBG3", "bgBG4", "bgBG5", "bgBG6", "bgBG7", "bgBG8", "bgBG9", "bgBG10", "bgBG100", "bgBG11", "bgBG13", "bgBG14", "bgBG15", "bgBG12"};

    /**
     * SHows loading screen
     */
    public void nowLoading() {
        selectedStage = true;
    }

    public void selectStage() {
        if (mode == StageSelection.NORMAL) {
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.singlePlayer) || MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                source = stageSelIndex;
                nowLoading();
                if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                    MainWindow.getInstance().sendToClient("loadingGVSHA");
                }
            }
        } else {
            source = (int) (Math.random() * (numberOfStages - 1));
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.singlePlayer) || MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                nowLoading();
                if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                    MainWindow.getInstance().sendToClient("loadingGVSHA");
                }
            }
        }

        if (source == 0) {
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.singlePlayer) || MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                stage1();
                if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                    MainWindow.getInstance().sendToClient("stage1_vgdt");
                }
            }
        }

        if (source == 1) {
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.singlePlayer) || MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                stage2();
                if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                    MainWindow.getInstance().sendToClient("stage2_vgdt");
                }
            }
        }

        if (source == 2) {
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.singlePlayer) || MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                stage3();
                if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                    MainWindow.getInstance().sendToClient("stage3_vgdt");
                }
            }
        }

        if (source == 3) {
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.singlePlayer) || MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                stage4();
                if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                    MainWindow.getInstance().sendToClient("stage4_vgdt");
                }
            }
        }

        if (source == 4) {
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.singlePlayer) || MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                stage5();
                if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                    MainWindow.getInstance().sendToClient("stage5_vgdt");
                }
            }
        }

        if (source == 5) {
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.singlePlayer) || MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                stage6();
                if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                    MainWindow.getInstance().sendToClient("stage6_vgdt");
                }
            }
        }

        if (source == 6) {
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.singlePlayer) || MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                stage7();
                if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                    MainWindow.getInstance().sendToClient("stage7_vgdt");
                }
            }
        }

        if (source == 10) {
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.singlePlayer) || MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                stage100();
                if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                    MainWindow.getInstance().sendToClient("stage100_vgdt");
                }
            }
        }

        if (source == 7) {
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.singlePlayer) || MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                stage8();
                if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                    MainWindow.getInstance().sendToClient("stage8_vgdt");
                }
            }
        }

        if (source == 8) {
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.singlePlayer) || MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                stage9();
                if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                    MainWindow.getInstance().sendToClient("stage9_vgdt");
                }
            }
        }

        if (source == 9) {
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.singlePlayer) || MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                stage10();
                if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                    MainWindow.getInstance().sendToClient("stage10_vgdt");
                }
            }
        }

        if (source == 11) {
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.singlePlayer) || MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                stage11();
                if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                    MainWindow.getInstance().sendToClient("stage11_vgdt");
                }
            }
        }

        if (source == 12) {
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.singlePlayer) || MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                stage12();
                if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                    MainWindow.getInstance().sendToClient("stage12_vgdt");
                }
            }
        }

        if (source == 13) {
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.singlePlayer) || MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                stage13();
                if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                    MainWindow.getInstance().sendToClient("stage13_vgdt");
                }
            }
        }

        if (source == 14) {
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.singlePlayer) || MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                stage14();
                if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                    MainWindow.getInstance().sendToClient("stage14_vgdt");
                }
            }
        }

        if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.singlePlayer) || MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost) || MainWindow.getInstance().getGameMode().equalsIgnoreCase("watch")) {
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                MainWindow.getInstance().sendToClient("gameStart7%^&");
            }
            start();
        }
    }

    public void defValue() {
        setStage(0);
        RenderGameplay.getInstance().fgx = 0;
        RenderGameplay.getInstance().fgy = 10;
        RenderGameplay.getInstance().fgxInc = 1;
        RenderGameplay.getInstance().fgyInc = 1;
        RenderGameplay.getInstance().animLoops = 10;
        RenderGameplay.getInstance().animDirection = "vert";
        RenderGameplay.getInstance().verticalMove = "no";
        RenderGameplay.getInstance().animLayer = "both";
        RenderGameplay.getInstance().delay = 33;
        RenderGameplay.getInstance().ambSpeed1 = 4;
        RenderGameplay.getInstance().ambSpeed2 = 3;
        ambientMusicIndex = 0;
        getReady();
    }

    /**
     * Ibex Hill- day
     */
    public void stage1() //
    {
        defValue();
    }

    /**
     * Chelston City docks
     */
    public void stage2() {
        setStage(1);
        RenderGameplay.getInstance().fgx = 0;
        RenderGameplay.getInstance().fgy = 0;
        RenderGameplay.getInstance().fgxInc = 1;
        RenderGameplay.getInstance().fgyInc = 1;
        RenderGameplay.getInstance().animLoops = 20;
        RenderGameplay.getInstance().animDirection = "none";
        RenderGameplay.getInstance().verticalMove = "no";
        RenderGameplay.getInstance().animLayer = "none";
        RenderGameplay.getInstance().delay = 66;
        RenderGameplay.getInstance().ambSpeed1 = 0;
        RenderGameplay.getInstance().ambSpeed2 = 0;
        ambientMusicIndex = 1;
        getReady();
    }

    /**
     * The Ruined Hall
     */
    public void stage3() {
        setStage(2);
        RenderGameplay.getInstance().fgx = 0;
        RenderGameplay.getInstance().fgy = 0;
        RenderGameplay.getInstance().fgxInc = 5;
        RenderGameplay.getInstance().fgyInc = 1;
        RenderGameplay.getInstance().animLoops = 4;
        RenderGameplay.getInstance().animDirection = "none";
        RenderGameplay.getInstance().verticalMove = "no";
        RenderGameplay.getInstance().animLayer = "forg";
        RenderGameplay.getInstance().delay = 33;
        RenderGameplay.getInstance().ambSpeed1 = 2;
        RenderGameplay.getInstance().ambSpeed2 = 1;
        ambientMusicIndex = 2;
        getReady();
    }

    /**
     * Chelston City - Streets
     */
    public void stage4() {
        setStage(3);
        RenderGameplay.getInstance().fgx = 0;
        RenderGameplay.getInstance().fgy = 0;
        RenderGameplay.getInstance().fgxInc = 1;
        RenderGameplay.getInstance().fgyInc = 1;
        RenderGameplay.getInstance().animLoops = 20;
        RenderGameplay.getInstance().animDirection = "none";
        RenderGameplay.getInstance().verticalMove = "no";
        RenderGameplay.getInstance().animLayer = "none";
        RenderGameplay.getInstance().delay = 66;
        RenderGameplay.getInstance().ambSpeed1 = 0;
        RenderGameplay.getInstance().ambSpeed2 = 0;
        ambientMusicIndex = 3;
        getReady();
    }

    /**
     * Ibex Hill - Night
     */
    public void stage5() {
        setStage(4);
        RenderGameplay.getInstance().fgx = 0;
        RenderGameplay.getInstance().fgy = 10;
        RenderGameplay.getInstance().fgxInc = 1;
        RenderGameplay.getInstance().fgyInc = 1;
        RenderGameplay.getInstance().animLoops = 10;
        RenderGameplay.getInstance().animDirection = "vert";
        RenderGameplay.getInstance().verticalMove = "no";
        RenderGameplay.getInstance().animLayer = "both";
        RenderGameplay.getInstance().delay = 33;
        RenderGameplay.getInstance().ambSpeed1 = 4;
        RenderGameplay.getInstance().ambSpeed2 = 3;
        ambientMusicIndex = 4;
        getReady();
    }

    /**
     * Scorched Ruins
     */
    public void stage6() {
        setStage(5);
        RenderGameplay.getInstance().fgx = 0;
        RenderGameplay.getInstance().fgy = 0;
        RenderGameplay.getInstance().fgxInc = 5;
        RenderGameplay.getInstance().fgyInc = 1;
        RenderGameplay.getInstance().animLoops = 4;
        RenderGameplay.getInstance().animDirection = "none";
        RenderGameplay.getInstance().verticalMove = "no";
        RenderGameplay.getInstance().animLayer = "forg";
        RenderGameplay.getInstance().delay = 33;
        RenderGameplay.getInstance().ambSpeed1 = 2;
        RenderGameplay.getInstance().ambSpeed2 = 1;
        ambientMusicIndex = 5;
        getReady();
    }

    /**
     * Frozen Wilderness
     */
    public void stage7() {
        setStage(6);
        RenderGameplay.getInstance().fgx = 0;
        RenderGameplay.getInstance().fgy = 10;
        RenderGameplay.getInstance().fgxInc = 5;
        RenderGameplay.getInstance().fgyInc = 1;
        RenderGameplay.getInstance().animLoops = 4;
        RenderGameplay.getInstance().animDirection = "none";
        RenderGameplay.getInstance().verticalMove = "yes";
        RenderGameplay.getInstance().animLayer = "both";
        RenderGameplay.getInstance().delay = 33;
        RenderGameplay.getInstance().ambSpeed1 = 2;
        RenderGameplay.getInstance().ambSpeed2 = 1;
        ambientMusicIndex = 6;
        getReady();
    }

    /**
     * Distant Isle
     */
    public void stage100() {
        setStage(10);
        RenderGameplay.getInstance().fgx = -40;
        RenderGameplay.getInstance().fgy = 20;
        RenderGameplay.getInstance().fgxInc = 2;
        RenderGameplay.getInstance().fgyInc = 1;
        RenderGameplay.getInstance().animLoops = 20;
        RenderGameplay.getInstance().animDirection = "rot";
        RenderGameplay.getInstance().verticalMove = "no";
        RenderGameplay.getInstance().animLayer = "back";
        RenderGameplay.getInstance().delay = 25;
        RenderGameplay.getInstance().ambSpeed1 = 1;
        RenderGameplay.getInstance().ambSpeed2 = 2;
        ambientMusicIndex = 0;
        getReady();
    }

    public void stage12() {
        setStage(12);
        RenderGameplay.getInstance().fgx = 0;
        RenderGameplay.getInstance().fgy = 0;
        RenderGameplay.getInstance().fgxInc = 1;
        RenderGameplay.getInstance().fgyInc = 1;
        RenderGameplay.getInstance().animLoops = 20;
        RenderGameplay.getInstance().animDirection = "none";
        RenderGameplay.getInstance().verticalMove = "no";
        RenderGameplay.getInstance().animLayer = "none";
        RenderGameplay.getInstance().delay = 66;
        RenderGameplay.getInstance().ambSpeed1 = 0;
        RenderGameplay.getInstance().ambSpeed2 = 0;
        ambientMusicIndex = 3;
        getReady();
    }

    /**
     * Distant Isle night
     */
    public void stage11() {
        setStage(11);
        RenderGameplay.getInstance().fgx = -40;
        RenderGameplay.getInstance().fgy = 20;
        RenderGameplay.getInstance().fgxInc = 2;
        RenderGameplay.getInstance().fgyInc = 1;
        RenderGameplay.getInstance().animLoops = 20;
        RenderGameplay.getInstance().animDirection = "rot";
        RenderGameplay.getInstance().verticalMove = "no";
        RenderGameplay.getInstance().animLayer = "back";
        RenderGameplay.getInstance().delay = 25;
        RenderGameplay.getInstance().ambSpeed1 = 1;
        RenderGameplay.getInstance().ambSpeed2 = 2;
        ambientMusicIndex = 1;
        getReady();
    }

    /**
     * Hidden Cave
     */
    public void stage8() {
        setStage(7);
        RenderGameplay.getInstance().fgx = 0;
        RenderGameplay.getInstance().fgy = 0;
        RenderGameplay.getInstance().fgxInc = 1;
        RenderGameplay.getInstance().fgyInc = 1;
        RenderGameplay.getInstance().animLoops = 20;
        RenderGameplay.getInstance().animDirection = "none";
        RenderGameplay.getInstance().verticalMove = "no";
        RenderGameplay.getInstance().animLayer = "none";
        RenderGameplay.getInstance().delay = 66;
        RenderGameplay.getInstance().ambSpeed1 = 0;
        RenderGameplay.getInstance().ambSpeed2 = 0;
        ambientMusicIndex = 2;
        getReady();
    }

    /**
     * African Village
     */
    public void stage9() {
        setStage(8);
        RenderGameplay.getInstance().fgx = 0;
        RenderGameplay.getInstance().fgy = 0;
        RenderGameplay.getInstance().fgxInc = 1;
        RenderGameplay.getInstance().fgyInc = 1;
        RenderGameplay.getInstance().animLoops = 20;
        RenderGameplay.getInstance().animDirection = "none";
        RenderGameplay.getInstance().verticalMove = "no";
        RenderGameplay.getInstance().animLayer = "back";
        RenderGameplay.getInstance().delay = 122;
        RenderGameplay.getInstance().ambSpeed1 = 2;
        RenderGameplay.getInstance().ambSpeed2 = 1;
        ambientMusicIndex = 3;
        getReady();
    }

    /**
     * The Apocalypse
     */
    public void stage10() {
        setStage(9);
        RenderGameplay.getInstance().fgx = 0;
        RenderGameplay.getInstance().fgy = 0;
        RenderGameplay.getInstance().fgxInc = 5;
        RenderGameplay.getInstance().fgyInc = 1;
        RenderGameplay.getInstance().animLoops = 4;
        RenderGameplay.getInstance().animDirection = "none";
        RenderGameplay.getInstance().verticalMove = "no";
        RenderGameplay.getInstance().animLayer = "both";
        RenderGameplay.getInstance().delay = 33;
        RenderGameplay.getInstance().ambSpeed1 = 2;
        RenderGameplay.getInstance().ambSpeed2 = 1;
        ambientMusicIndex = 4;
        getReady();
    }

    public void stage13() {
        setStage(13);
        RenderGameplay.getInstance().fgx = 0;
        RenderGameplay.getInstance().fgy = 0;
        RenderGameplay.getInstance().fgxInc = 5;
        RenderGameplay.getInstance().fgyInc = 1;
        RenderGameplay.getInstance().animLoops = 4;
        RenderGameplay.getInstance().animDirection = "none";
        RenderGameplay.getInstance().verticalMove = "no";
        RenderGameplay.getInstance().animLayer = "forg";
        RenderGameplay.getInstance().delay = 33;
        RenderGameplay.getInstance().ambSpeed1 = 2;
        RenderGameplay.getInstance().ambSpeed2 = 1;
        ambientMusicIndex = 1;
        getReady();
    }

    public void stage14() {
        setStage(14);
        RenderGameplay.getInstance().fgx = 0;
        RenderGameplay.getInstance().fgy = 0;
        RenderGameplay.getInstance().fgxInc = 1;
        RenderGameplay.getInstance().fgyInc = 1;
        RenderGameplay.getInstance().animLoops = 20;
        RenderGameplay.getInstance().animDirection = "none";
        RenderGameplay.getInstance().verticalMove = "no";
        RenderGameplay.getInstance().animLayer = "none";
        RenderGameplay.getInstance().delay = 66;
        RenderGameplay.getInstance().ambSpeed1 = 0;
        RenderGameplay.getInstance().ambSpeed2 = 0;
        ambientMusicIndex = 3;
        getReady();
    }


    /**
     * @return stage
     */
    public String getStage() {
        return stagePrevLox[stageSelIndex];
    }

    /**
     * Sets the stage
     *
     * @param where
     */
    public void setStage(int where) {
        stageSelIndex = where;
    }

    private void getReady() {
        RenderGameplay.getInstance().activeStage = Integer.parseInt(getStage().substring(4));
        RenderGameplay.getInstance().bgLocation = "images/" + getStage() + ".png";
        RenderGameplay.getInstance().fgLocation = "images/" + getStage() + "fg.png";
    }

    public void start() {
        getReady();

        new Thread() {

            @Override
            @SuppressWarnings("static-access")
            public void run() {
                try {
                    this.sleep(1000);
                    MainWindow.getInstance().newGame();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * When both playes are selected, this prevents movement.
     *
     * @return false if both Character have been selected, true if only one is selected
     */
    public boolean bothArentSelected() {
        boolean answer = true;

        if (RenderCharacterSelectionScreen.getInstance().characterSelected && RenderCharacterSelectionScreen.getInstance().opponentSelected) {
            answer = false;
        }

        return answer;
    }

    /**
     * Animates captions
     */
    public void capAnim() {
        opacity = 0.0f;
    }

    public void animCloud() {
    }

    /**
     * Checks if within number of Character
     */
    public boolean well() {
        boolean ans = false;
        int xV = (vIndex * 3) + hIndex;
        if (xV <= numberOfStages) {
            ans = true;
            stageSelIndex = xV - 1;
        }
        return ans;
    }
}

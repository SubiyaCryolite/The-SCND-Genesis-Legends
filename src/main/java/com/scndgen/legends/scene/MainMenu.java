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

import com.scndgen.legends.Language;
import com.scndgen.legends.ScndGenLegends;
import com.scndgen.legends.controller.Tutorial;
import com.scndgen.legends.enums.Mode;
import com.scndgen.legends.enums.Overlay;
import com.scndgen.legends.enums.SubMode;
import com.scndgen.legends.render.OverlayAchievementLocker;
import io.github.subiyacryolite.enginev1.JenesisMode;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;

import java.awt.*;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.net.URI;
import java.util.Calendar;

/**
 * @Author: Ifunga Ndana
 * @Class: screenDrawer
 * This class draws nd manipulates all sprites, images and effects used in the game
 */
public abstract class MainMenu extends JenesisMode {

    protected final int fontSize = 16;
    protected boolean animThread = true;
    protected Font font;
    protected int hoveredMenuIndex = 0;
    protected int xMenu = 500;
    protected Overlay overlay = Overlay.PRIMARY_MENU;
    protected int menuItemIndex, menuEntries = 11;
    protected int yMenu = ((576 - fontSize) - (fontSize * (menuEntries + 1))) / 2; //centered, multiply fontSize with number of menu items+1
    protected int cloudOnePositionX = 0, yCordCloud = 0, cloudTwoPositionX = 0, yCordCloud2 = 20, cloudThreePositionX = 0, yCordCloud3 = 40;
    protected String stat1, stat2, stat3, stat4, stat5, stat6, stat7, ach1, ach2, ach3, ach4, ach5, stat13, ach6, stat15, stat16, ach7, ach8, text2 = "", stat17;
    protected SubMode menuItmStr;
    protected int timeInt = 0;
    protected int spacer = 12, time;
    protected OverlayAchievementLocker achachievementLocker;
    protected String mess;
    protected boolean fadeOutFeedback;
    protected float logoFadeOpacity = 1.0f;
    protected String[] menuItem;
    protected int offset = 10;
    protected Calendar cal;
    protected Font menuFont;
    protected float gWin, gLoss, denom, progression;
    protected Tutorial tutorial;
    //---blur op
    protected int size;
    protected float[] data;
    protected float sigma, opactity;
    protected float twoSigmaSquare;
    protected float sigmaRoot;
    protected float total;
    protected Kernel kernel;
    //---blur op

    @SuppressWarnings("CallToThreadStartDuringObjectConstruction")
    public MainMenu() {
        opactity = 3.0f;
        logoFadeOpacity = 1.0f;
        fadeOutFeedback = false;
        menuItem = new String[(menuEntries + 2) * 2];
        achachievementLocker = new OverlayAchievementLocker();
        cal = Calendar.getInstance();
        time = (cal.get(Calendar.HOUR_OF_DAY));
        System.out.println("Hour: " + time);
        loadPix();
        font = getMyFont(fontSize - 2);
        menuItem[0] = Language.getInstance().get(307);
        menuItem[1] = "STORY MODE";
        menuItem[2] = Language.getInstance().get(308);
        menuItem[3] = "QUICK MATCH";
        menuItem[4] = "Quick Match (2 vs 2)";
        menuItem[5] = "QUICK MATCH (2 vs 2)";
        menuItem[6] = Language.getInstance().get(309);
        menuItem[7] = "HOST A LAN MATCH";
        menuItem[8] = Language.getInstance().get(310);
        menuItem[9] = "JOIN A LAN MATCH";
        menuItem[10] = Language.getInstance().get(311);
        menuItem[11] = "YOUR STATS";
        menuItem[12] = Language.getInstance().get(312);
        menuItem[13] = "OPTIONS";
        menuItem[14] = Language.getInstance().get(313);
        menuItem[15] = "VIEW CONTROLS";
        menuItem[16] = Language.getInstance().get(314);
        menuItem[17] = "ABOUT";
        menuItem[18] = Language.getInstance().get(315);
        menuItem[19] = "EXIT";
        menuItem[20] = Language.getInstance().get(316);
        menuItem[21] = "ACHIEVEMENT LOCKER";
        menuItem[22] = Language.getInstance().get(317);
        menuItem[23] = "ONLINE LEADER BOARDS";
        menuItem[22] = Language.getInstance().get(318);
        menuItem[23] = "LOG OUT";
        menuItem[24] = Language.getInstance().get(319);
        menuItem[25] = "TUTORIAL";

        new Thread() {

            @Override
            @SuppressWarnings("static-access")
            public void run() {
                try {
                    fadeOutFeedback = false;
                    logoFadeOpacity = 1.0f;
                    this.sleep(15000);
                    fadeOutFeedback = true;
                } catch (Exception e) {
                }
            }
        }.start();
    }

    /**
     * Get timeLimit, dynamic menu
     *
     * @return timeLimit
     */
    public int getTime() {
        return time;
    }

    protected String shortStr(String message) {
        String returnThis;

        if (message.length() < 20) {
            returnThis = message;
        } else {
            returnThis = message.substring(0, 20) + "...";
        }

        return returnThis;
    }


    /**
     * Refresh achievement STATS
     */
    public void refreshStats() {
        achachievementLocker.refreshStats();
    }


    public void stopTutorial() {
        tutorial.stopTut();
    }

    public void reverseTutorial() {
        tutorial.backTut();
    }

    public void advanceTutorial() {
        tutorial.forwarTut();
    }

    public void startTut() {
        tutorial.startTut();
    }

    public void sktpToTut(int n) {
        tutorial.skipToSection(n - 1);
    }

    /**
     * Get the string representation of a scene
     *
     * @return
     */
    public SubMode getMenuModeStr() {
        return menuItmStr;
    }

    public void StopRepaint() {
        animThread = false;
    }

    public int getXMenu() {
        return xMenu;
    }

    public int getYMenu() {
        return yMenu;
    }

    public int getSpacer() {
        return fontSize;
    }

    public void setMenuPos(int where) {
        hoveredMenuIndex = where;
    }

    public void goDown() {
        if (hoveredMenuIndex < menuEntries && overlay == Overlay.PRIMARY_MENU) {
            hoveredMenuIndex = hoveredMenuIndex + 1;
        } else if (overlay == Overlay.ACHIEVEMENTS) {
            achachievementLocker.scrollDown();
        } else {
            hoveredMenuIndex = 0;
        }
    }

    public void goUp() {
        if (hoveredMenuIndex > 0 && overlay == Overlay.PRIMARY_MENU) {
            hoveredMenuIndex = hoveredMenuIndex - 1;
        } else if (overlay == Overlay.ACHIEVEMENTS) {
            achachievementLocker.scrollUp();
        } else {
            hoveredMenuIndex = menuEntries;
        }
    }

    protected void loadPix() {
    }

    public Overlay getOverlay() {
        return overlay;
    }

    public void setOverlay(Overlay overlay) {
        if (overlay == Overlay.TUTORIAL) {
            tutorial = new Tutorial();
        }
        this.overlay = overlay;
    }

    protected ConvolveOp getGaussianBlurFilter(int radius, boolean horizontal) {
        if (radius < 1) {
            throw new IllegalArgumentException("Radius must be >= 1");
        }

        size = radius * 2 + 1;
        data = new float[size];
        sigma = radius / 3.0f;
        twoSigmaSquare = 2.0f * sigma * sigma;
        sigmaRoot = (float) Math.sqrt(twoSigmaSquare * Math.PI);
        total = 0.0f;
        for (int i = -radius; i <= radius; i++) {
            float distance = i * i;
            int index = i + radius;
            data[index] = (float) Math.exp(-distance / twoSigmaSquare) / sigmaRoot;
            total += data[index];
        }

        for (int i = 0; i < data.length; i++) {
            data[i] /= total;
        }
        kernel = null;
        if (horizontal) {
            kernel = new Kernel(size, 1, data);
        } else {
            kernel = new Kernel(1, size, data);
        }
        return new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
    }

    public void newInstance() {
        loadAssets = true;
    }

    public void keyPressed(KeyCode keyCode) {
        if (keyCode == KeyCode.UP || keyCode == KeyCode.W) {
            goUp();
        }
        if (keyCode == KeyCode.DOWN || keyCode == KeyCode.S) {
            goDown();
        }
        if (keyCode == KeyCode.F) {
            provideFeedback('f');
        }
        if (keyCode == KeyCode.B) {
            provideFeedback('b');
        }
        if (keyCode == KeyCode.L) {
            provideFeedback('l');
        }
    }

    public void mouseClicked(MouseEvent mouseEvent) {
        int x = getXMenu();
        int y = getYMenu() - 14;
        int space = getSpacer();
        if (getOverlay() == Overlay.TUTORIAL) {
            if (mouseEvent.getX() >= 425) {
                advanceTutorial();
            } else {
                reverseTutorial();
            }
        } else if ((mouseEvent.getY() > y) && (mouseEvent.getY() < (y + (space * 13))) && mouseEvent.getX() > x) {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {

                SubMode destination = getMenuModeStr();
                if (destination == SubMode.LAN_HOST) {
                    primaryNotice(Language.getInstance().get(107));
                    //JenesisWindow.getInstance().setContentPane(newInstance(JenesisWindow.strUser, destination));
                } else if (destination == SubMode.SINGLE_PLAYER) {
                    primaryNotice(Language.getInstance().get(108));
                    //JenesisWindow.getInstance().setContentPane(newInstance(JenesisWindow.strUser, destination));
                } else if (destination == SubMode.STORY_MODE) {
                    ScndGenLegends.getInstance().switchMode(Mode.STORY_SELECT_SCREEN);
                } else if (destination == SubMode.STATS) {
                    setOverlay(Overlay.STATISTICS);
                } else if (destination == SubMode.ACH) {
                    refreshStats();
                    setOverlay(Overlay.ACHIEVEMENTS);
                } else if (destination == SubMode.TUTORIAL) {
                    setOverlay(Overlay.TUTORIAL);
                    startTut();
                }
            }
        }
    }

    public void mouseMoved(final MouseEvent mouseEvent) {
        int x = getXMenu();
        int y = getYMenu() - 14;
        int space = getSpacer() - 2;
        if ((mouseEvent.getX() > x) && (mouseEvent.getX() < x + 200)) {
            if ((mouseEvent.getY() > space) && (mouseEvent.getY() < (y + space)))
                setMenuPos(0);
            if ((mouseEvent.getY() > (y + (space * 1))) && (mouseEvent.getY() < (y + (space * 2))))
                setMenuPos(1);
            if ((mouseEvent.getY() > (y + (space * 2))) && (mouseEvent.getY() < (y + (space * 3))))
                setMenuPos(2);
            if ((mouseEvent.getY() > (y + (space * 3))) && (mouseEvent.getY() < (y + (space * 4))))
                setMenuPos(3);
            if ((mouseEvent.getY() > (y + (space * 4))) && (mouseEvent.getY() < (y + (space * 5))))
                setMenuPos(4);
            if ((mouseEvent.getY() > (y + (space * 5))) && (mouseEvent.getY() < (y + (space * 6))))
                setMenuPos(5);
            if ((mouseEvent.getY() > (y + (space * 7))) && (mouseEvent.getY() < (y + (space * 8))))
                setMenuPos(6);
            if ((mouseEvent.getY() > (y + (space * 8))) && (mouseEvent.getY() < (y + (space * 9))))
                setMenuPos(7);
            if ((mouseEvent.getY() > (y + (space * 9))) && (mouseEvent.getY() < (y + (space * 10))))
                setMenuPos(8);
            if ((mouseEvent.getY() > (y + (space * 10))) && (mouseEvent.getY() < (y + (space * 11))))
                setMenuPos(9);
            if ((mouseEvent.getY() > (y + (space * 11))) && (mouseEvent.getY() < (y + (space * 12))))
                setMenuPos(10);
            if ((mouseEvent.getY() > (y + (space * 12))) && (mouseEvent.getY() < (y + (space * 13))))
                setMenuPos(11);
            if ((mouseEvent.getY() > (y + (space * 13))) && (mouseEvent.getY() < (y + (space * 13))))
                setMenuPos(12);
        }
    }

    private void provideFeedback(char code) {
        if (!Desktop.isDesktopSupported()) return;
        Desktop desktop = Desktop.getDesktop();
        if (!desktop.isSupported(Desktop.Action.BROWSE)) return;
        URI uri;
        try {
            switch (code) {
                case 'f':
                    uri = new URI("https://docs.google.com/spreadsheet/viewform?formkey=dGppbVViZHE5QWxZYkRBazZNcUtTRHc6MQ");
                    break;
                case 'b':
                    uri = new URI("https://subiyacryolite.github.io/");
                    break;
                case 'l':
                    uri = new URI("http://www.facebook.com/pages/THE-SCND-GENESIS/111839318834780");
                    break;
                default:
                    uri = new URI("http://www.scndgen.com");
                    break;
            }
            desktop.browse(uri);
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }
}

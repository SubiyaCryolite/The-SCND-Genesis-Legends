/**************************************************************************

 The SCND Genesis: Legends is a fighting game based on THE SCND GENESIS,
 a webcomic created by Ifunga Ndana ((([<a href="https://www.scndgen.com">https://www.scndgen.com</a>]))).

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

import com.scndgen.legends.ScndGenLegends;
import com.scndgen.legends.enums.MainMenuOverlay;
import com.scndgen.legends.enums.SubMode;
import com.scndgen.legends.render.AchievementLocker;
import io.github.subiyacryolite.enginev2.Mode;

import java.awt.*;
import java.net.URI;
import java.util.Calendar;

import static org.lwjgl.glfw.GLFW.*;

/**
 * @Author: Ifunga Ndana
 * @Class: screenDrawer
 * This class draws nd manipulates all sprites, images and effects used in the game
 */
public abstract class MainMenu extends Mode {

    protected final int fontSize = 16;
    protected int xMenu = 500;
    protected MainMenuOverlay mainMenuOverlay = MainMenuOverlay.PRIMARY_MENU;
    protected int menuItemIndex;
    protected int menuEntries = 11;
    protected int yMenu = ((576 - fontSize) - (fontSize * (menuEntries + 1))) / 2; //centered, multiply fontSize with number of menu items+1
    protected int cloudOnePositionX = 0, yCordCloud = 0, cloudTwoPositionX = 0, yCordCloud2 = 20, cloudThreePositionX = 0, yCordCloud3 = 40;
    protected int time;
    protected AchievementLocker achievementLocker;
    protected String mess;
    protected boolean fadeOutFeedback;
    protected float logoFadeOpacity = 1.0f;
    protected Calendar cal;
    protected Tutorial tutorial;

    public MainMenu() {
        ScndGenLegends.get().setSubMode(SubMode.MAIN_MENU);
        setMainMenuOverlay(MainMenuOverlay.PRIMARY_MENU);
        opacity = 3.0f;
        logoFadeOpacity = 1.0f;
        fadeOutFeedback = false;
        achievementLocker = new AchievementLocker();
        cal = Calendar.getInstance();
        time = (cal.get(Calendar.HOUR_OF_DAY));
        System.out.println("Hour: " + time);
        new Thread() {
            @Override
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
     * Refresh achievement STATS
     */
    public void refreshStats() {
        achievementLocker.refreshStats();
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

    public MainMenuOverlay getMainMenuOverlay() {
        return mainMenuOverlay;
    }

    public void setMainMenuOverlay(MainMenuOverlay mainMenuOverlay) {
        if (mainMenuOverlay == MainMenuOverlay.TUTORIAL) {
            tutorial = new Tutorial();
        }
        this.mainMenuOverlay = mainMenuOverlay;
    }

    public void newInstance() {
        loadAssets = true;
    }

    @Override
    public void keyPressed(int glfwKey) {
        switch (getMainMenuOverlay()) {
            case TUTORIAL -> tutorial.keyPressed(glfwKey);
            case STATISTICS, ACHIEVEMENT_LOCKER -> achievementLocker.keyPressed(glfwKey);
            case PRIMARY_MENU -> {
                switch (glfwKey) {
                    case GLFW_KEY_ENTER -> onAccept();
                    case GLFW_KEY_ESCAPE, GLFW_KEY_BACKSPACE -> onBackCancel();
                    case GLFW_KEY_UP, GLFW_KEY_W -> onUp();
                    case GLFW_KEY_DOWN, GLFW_KEY_S -> onDown();
                    case GLFW_KEY_LEFT, GLFW_KEY_A -> onLeft();
                    case GLFW_KEY_RIGHT, GLFW_KEY_D -> onRight();
                    case GLFW_KEY_F -> provideFeedback('f');
                    case GLFW_KEY_B -> provideFeedback('b');
                    case GLFW_KEY_L -> provideFeedback('l');
                    default -> {
                    }
                }
            }
            default -> {
            }
        }
    }

    @Override
    public void mouseClicked(float x, float y) {
        onAccept();
    }

    @Override
    public void mouseMoved(float x, float y) {
    }

    private void provideFeedback(char code) {
        if (!Desktop.isDesktopSupported()) return;
        Desktop desktop = Desktop.getDesktop();
        if (!desktop.isSupported(Desktop.Action.BROWSE)) return;
        URI uri;
        try {
            switch (code) {
                case 'f' -> uri = new URI("https://docs.google.com/spreadsheet/viewform?formkey=dGppbVViZHE5QWxZYkRBazZNcUtTRHc6MQ");
                case 'b' -> uri = new URI("https://subiyacryolite.github.io/");
                case 'l' -> uri = new URI("http://www.facebook.com/pages/THE-SCND-GENESIS/111839318834780");
                default -> uri = new URI("([https://www.scndgen.com])");
            }
            desktop.browse(uri);
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }
}

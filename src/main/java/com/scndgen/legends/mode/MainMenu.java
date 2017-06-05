/**************************************************************************

 The SCND Genesis: Legends is a fighting game based on THE SCND GENESIS,
 a webcomic created by Ifunga Ndana ((([http://www.scndgen.com]))).

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
 along with The SCND Genesis: Legends. If not, see <http://www.gnu.org/licenses/>.

 **************************************************************************/
package com.scndgen.legends.mode;

import com.scndgen.legends.ScndGenLegends;
import com.scndgen.legends.enums.MainMenuOverlay;
import com.scndgen.legends.enums.SubMode;
import com.scndgen.legends.render.AchievementLocker;
import io.github.subiyacryolite.enginev1.Mode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;

import java.awt.*;
import java.net.URI;
import java.util.Calendar;

/**
 * @Author: Ifunga Ndana
 * @Class: screenDrawer
 * This class draws nd manipulates all sprites, images and effects used in the game
 */
public abstract class MainMenu extends Mode {

    protected final int fontSize = 16;
    protected Font font;
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
    protected Font menuFont;
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
        font = loadFont(fontSize - 2);
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

    public void keyPressed(KeyEvent keyEvent) {
        switch (getMainMenuOverlay()) {
            case TUTORIAL:
                tutorial.keyPressed(keyEvent);
                break;
            case STATISTICS:
            case ACHIEVEMENT_LOCKER:
                achievementLocker.keyPressed(keyEvent);
                break;
            case PRIMARY_MENU:
                switch (keyEvent.getCode()) {
                    case ENTER:
                        onAccept();
                        break;
                    case ESCAPE:
                    case BACK_SPACE:
                        onBackCancel();
                        break;
                    case UP:
                    case W:
                        onUp();
                        break;
                    case DOWN:
                    case S:
                        onDown();
                        break;
                    case LEFT:
                    case A:
                        onLeft();
                        break;
                    case RIGHT:
                    case D:
                        onRight();
                        break;
                    case F:
                        provideFeedback('f');
                        break;
                    case B:
                        provideFeedback('b');
                        break;
                    case L:
                        provideFeedback('l');
                        break;
                }
                break;
        }
    }

    public void mouseClicked(MouseEvent mouseEvent) {
            switch (mouseEvent.getButton()) {
                case PRIMARY:
                    onAccept();
                    break;
                case SECONDARY:
                    onBackCancel();
                    break;
            }
    }

    public void mouseMoved(final MouseEvent mouseEvent) {
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
                    uri = new URI("([http://www.scndgen.com])");
                    break;
            }
            desktop.browse(uri);
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }
}

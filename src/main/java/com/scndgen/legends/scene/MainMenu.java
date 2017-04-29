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
import com.scndgen.legends.render.AchievementLocker;
import io.github.subiyacryolite.enginev1.JenesisMode;
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
public abstract class MainMenu extends JenesisMode {

    protected final int fontSize = 16;
    protected Font font;
    protected int hoveredMenuIndex = 0;
    protected int xMenu = 500;
    protected Overlay overlay = Overlay.PRIMARY_MENU;
    protected int menuItemIndex, menuEntries = 11;
    protected int yMenu = ((576 - fontSize) - (fontSize * (menuEntries + 1))) / 2; //centered, multiply fontSize with number of menu items+1
    protected int cloudOnePositionX = 0, yCordCloud = 0, cloudTwoPositionX = 0, yCordCloud2 = 20, cloudThreePositionX = 0, yCordCloud3 = 40;
    protected int time;
    protected AchievementLocker achievementLocker;
    protected String mess;
    protected boolean fadeOutFeedback;
    protected float logoFadeOpacity = 1.0f;
    protected String[] menuItem;
    protected Calendar cal;
    protected Font menuFont;
    protected Tutorial tutorial;

    public MainMenu() {
        ScndGenLegends.getInstance().setSubMode(SubMode.MAIN_MENU);
        setOverlay(Overlay.PRIMARY_MENU);
        opacity = 3.0f;
        logoFadeOpacity = 1.0f;
        fadeOutFeedback = false;
        menuItem = new String[(menuEntries + 2) * 2];
        achievementLocker = new AchievementLocker();
        cal = Calendar.getInstance();
        time = (cal.get(Calendar.HOUR_OF_DAY));
        System.out.println("Hour: " + time);
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

    public void setMenuPosition(int where) {
        hoveredMenuIndex = where;
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

    public void newInstance() {
        loadAssets = true;
    }

    public void keyPressed(KeyEvent keyEvent) {
        switch (getOverlay()) {
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

    public void onBackCancel() {
        switch (getOverlay()) {
            case TUTORIAL:
                tutorial.onBackCancel();
                break;
            case ACHIEVEMENT_LOCKER:
            case STATISTICS:
                achievementLocker.onBackCancel();
                break;
            case PRIMARY_MENU:
                break;
        }
    }

    public void onDown() {
        switch (getOverlay()) {
            case TUTORIAL:
                tutorial.onDown();
                break;
            case STATISTICS:
            case ACHIEVEMENT_LOCKER:
                achievementLocker.onDown();
                break;
            case PRIMARY_MENU:
                if (hoveredMenuIndex < menuEntries && overlay == Overlay.PRIMARY_MENU) {
                    hoveredMenuIndex = hoveredMenuIndex + 1;
                } else {
                    hoveredMenuIndex = 0;
                }
                break;
        }
    }

    public void onUp() {
        switch (getOverlay()) {
            case TUTORIAL:
                tutorial.onUp();
                break;
            case STATISTICS:
            case ACHIEVEMENT_LOCKER:
                achievementLocker.onUp();
                break;
            case PRIMARY_MENU:
                if (hoveredMenuIndex > 0 && overlay == Overlay.PRIMARY_MENU) {
                    hoveredMenuIndex = hoveredMenuIndex - 1;
                } else {
                    hoveredMenuIndex = menuEntries;
                }
                break;
        }
    }

    public void onRight() {
        switch (getOverlay()) {
            case TUTORIAL:
                tutorial.onRight();
                break;
            case STATISTICS:
            case ACHIEVEMENT_LOCKER:
                achievementLocker.onRight();
                break;
            case PRIMARY_MENU:
                break;
        }
    }

    public void onLeft() {
        switch (getOverlay()) {
            case TUTORIAL:
                tutorial.onLeft();
                break;
            case STATISTICS:
            case ACHIEVEMENT_LOCKER:
                achievementLocker.onLeft();
                break;
            case PRIMARY_MENU:
                break;
        }
    }

    public void onAccept() {
        switch (getOverlay()) {
            case TUTORIAL:
                tutorial.onAccept();
                break;
            case STATISTICS:
            case ACHIEVEMENT_LOCKER:
                achievementLocker.onAccept();
                break;
            case PRIMARY_MENU:
                switch (ScndGenLegends.getInstance().getSubMode()) {
                    case LAN_HOST:
                        primaryNotice(Language.getInstance().get(107));
                        ScndGenLegends.getInstance().loadMode(Mode.CHAR_SELECT_SCREEN);
                        break;
                    case SINGLE_PLAYER:
                        primaryNotice(Language.getInstance().get(108));
                        ScndGenLegends.getInstance().loadMode(Mode.CHAR_SELECT_SCREEN);
                        break;
                    case STORY_MODE:
                        ScndGenLegends.getInstance().loadMode(Mode.STORY_SELECT_SCREEN);
                        break;
                    case STATS:
                        setOverlay(Overlay.STATISTICS);
                        break;
                    case ACH:
                        refreshStats();
                        setOverlay(Overlay.ACHIEVEMENT_LOCKER);
                        break;
                    case TUTORIAL:
                        setOverlay(Overlay.TUTORIAL);
                        tutorial.beginTutorial();
                        break;
                }
                break;
        }
    }

    public void mouseClicked(MouseEvent mouseEvent) {
        if (validHover) {
            switch (mouseEvent.getButton()) {
                case PRIMARY:
                    onAccept();
                    break;
                case SECONDARY:
                    onBackCancel();
                    break;
            }
        }
    }

    public void mouseMoved(final MouseEvent mouseEvent) {
        int x = getXMenu();
        int y = getYMenu();
        int space = getSpacer() - 2;
        switch (getOverlay()) {
            case TUTORIAL:
                validHover = true;
                break;
            case STATISTICS:
            case ACHIEVEMENT_LOCKER:
                validHover = true;
                break;
            case PRIMARY_MENU:
                if ((mouseEvent.getX() > x) && (mouseEvent.getX() < x + 200)) {
                    validHover = true;
                    if ((mouseEvent.getY() > space) && (mouseEvent.getY() < (y + space)))
                        setMenuPosition(0);
                    if ((mouseEvent.getY() > (y + (space * 1))) && (mouseEvent.getY() < (y + (space * 2))))
                        setMenuPosition(1);
                    if ((mouseEvent.getY() > (y + (space * 2))) && (mouseEvent.getY() < (y + (space * 3))))
                        setMenuPosition(2);
                    if ((mouseEvent.getY() > (y + (space * 3))) && (mouseEvent.getY() < (y + (space * 4))))
                        setMenuPosition(3);
                    if ((mouseEvent.getY() > (y + (space * 4))) && (mouseEvent.getY() < (y + (space * 5))))
                        setMenuPosition(4);
                    if ((mouseEvent.getY() > (y + (space * 5))) && (mouseEvent.getY() < (y + (space * 6))))
                        setMenuPosition(5);
                    if ((mouseEvent.getY() > (y + (space * 7))) && (mouseEvent.getY() < (y + (space * 8))))
                        setMenuPosition(6);
                    if ((mouseEvent.getY() > (y + (space * 8))) && (mouseEvent.getY() < (y + (space * 9))))
                        setMenuPosition(7);
                    if ((mouseEvent.getY() > (y + (space * 9))) && (mouseEvent.getY() < (y + (space * 10))))
                        setMenuPosition(8);
                    if ((mouseEvent.getY() > (y + (space * 10))) && (mouseEvent.getY() < (y + (space * 11))))
                        setMenuPosition(9);
                    if ((mouseEvent.getY() > (y + (space * 11))) && (mouseEvent.getY() < (y + (space * 12))))
                        setMenuPosition(10);
                    if ((mouseEvent.getY() > (y + (space * 12))) && (mouseEvent.getY() < (y + (space * 13))))
                        setMenuPosition(11);
                    if ((mouseEvent.getY() > (y + (space * 13))) && (mouseEvent.getY() < (y + (space * 13))))
                        setMenuPosition(12);
                } else {
                    validHover = false;
                }
                break;
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

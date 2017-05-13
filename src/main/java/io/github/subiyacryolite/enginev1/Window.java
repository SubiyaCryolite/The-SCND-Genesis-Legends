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
package io.github.subiyacryolite.enginev1;

import com.scndgen.legends.Language;
import com.scndgen.legends.LoginScreen;
import com.scndgen.legends.ScndGenLegends;
import com.scndgen.legends.constants.AudioConstants;
import com.scndgen.legends.enums.AudioType;
import com.scndgen.legends.enums.Overlay;
import com.scndgen.legends.enums.SubMode;
import com.scndgen.legends.network.NetworkScanLan;
import com.scndgen.legends.render.RenderMainMenu;
import com.scndgen.legends.windows.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import javax.swing.*;

/**
 * @author Ndana
 */
public class Window {

    public static String strUser = "no user", strPoint = "0", strPlayTime = "0", matchCountStr = "0";
    public static boolean boardNotUp = true, controller = false, isActive = true, doneChilling;
    private static LoginScreen p;
    private WindowControls controls;
    private WindowOptions options;
    private WindowAbout about;
    private NetworkScanLan scan;
    private MenuLeaderBoard board;
    private AudioPlayback startup;
    private boolean[] buttonz;
    private int compassDir, compassDir2, last = 13;

    @SuppressWarnings("LeakingThisInConstructor")
    public Window(String dude, LoginScreen px) {
        p = px;
        startup = new AudioPlayback(AudioConstants.startUpSound(), AudioType.MUSIC, false);
        startup.play();
        strUser = dude;
        //setContentPane(JenesisPanel.newInstance(strUser, SubMode.MAIN_MENU));
    }

    public static Window getInstance() {
        return p.getMenu();
    }

    /**
     * Returns the username
     *
     * @return username
     */
    public static String getUserName() {
        return strUser;
    }

    public void logOut() {
        p.showWindow();
    }

    public final void sytemNotice(String moi) {
        RenderMainMenu.getInstance().primaryNotice(moi);
    }


    /**
     * Select option in menu
     */
    private void select() {
        //if viewing STATS, go onBackCancel to menu
        if (RenderMainMenu.getInstance().getOverlay() == Overlay.STATISTICS || RenderMainMenu.getInstance().getOverlay() == Overlay.ACHIEVEMENT_LOCKER || RenderMainMenu.getInstance().getOverlay() == Overlay.TUTORIAL) {
        } else {
            SubMode destination = ScndGenLegends.getInstance().getSubMode();
            if (destination == SubMode.LAN_CLIENT) {
                scan = new NetworkScanLan();
            }
            if (destination == SubMode.LOGOUT) {
                logOut();
            } else if (destination == SubMode.LEADERS) {
                if (boardNotUp) {
                    board = new MenuLeaderBoard();
                    boardNotUp = false;
                } else {
                    board.reappear();
                }
            } else if (destination == SubMode.ABOUT) {
                about = new WindowAbout();
            } else if (destination == SubMode.CONTROLS) {
                controls = new WindowControls();
            } else if (destination == SubMode.OPTIONS) {
                options = new WindowOptions();
            }
            if (destination == SubMode.EXIT) {
                exit();
            }
        }
    }

    public void mouseClicked(MouseEvent m) {
        JenesisPanel.getInstance().mouseClicked(m);
    }

    public void mouseMoved(MouseEvent m) {
        JenesisPanel.getInstance().mouseMoved(m);
    }


    public void keyPressed(KeyEvent e) {
        JenesisPanel.getInstance().keyPressed(e);
        KeyCode keyCode = e.getCode();
        if (keyCode == KeyCode.ENTER) {
            select();
        }
    }

    /**
     * Exit game
     */
    public void exit() {
        int exit = JOptionPane.showConfirmDialog(null, Language.getInstance().get(110), "Exit", JOptionPane.YES_NO_OPTION);
        if (exit == JOptionPane.YES_OPTION) {
            int seriously = JOptionPane.showConfirmDialog(null, Language.getInstance().get(111), "Seriously", JOptionPane.YES_NO_OPTION);
            if (seriously == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(null, Language.getInstance().get(112), "Later", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }
        }
    }

    /**
     * Show menu (maximise)
     */
    public void showModes() {
        isActive = true;
    }

    /**
     * Create a client game
     */
    public void joinGame() {
        JenesisPanel.newInstance(Window.getUserName(), SubMode.LAN_CLIENT);
    }
}

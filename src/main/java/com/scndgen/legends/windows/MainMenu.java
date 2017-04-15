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
package com.scndgen.legends.windows;

import com.scndgen.legends.render.RenderGameplay;
import com.scndgen.legends.render.RenderMainMenu;
import io.github.subiyacryolite.enginev1.JenesisGamePad;
import com.scndgen.legends.LoginScreen;
import com.scndgen.legends.Language;
import com.scndgen.legends.menus.MenuLeaderBoard;
import com.scndgen.legends.network.NetworkScanLan;
import com.scndgen.legends.threads.AudioPlayback;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author Ndana
 */
public class MainMenu extends JFrame implements ActionListener, KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {

    public static String strUser = "no user", strPoint = "0", strPlayTime = "0", matchCountStr = "0";
    public static boolean boardNotUp = true, controller = false, isActive = true, doneChilling;
    private static LoginScreen p;
    public int[] ach = new int[5];
    public int[] classArr = new int[5];
    private String[] mode = {MainWindow.singlePlayer, MainWindow.lanHost, MainWindow.lanClient, MainWindow.storyMode, MainWindow.singlePlayer2};
    private WindowControls controls;
    private WindowOptions options;
    private WindowAbout about;
    private NetworkScanLan scan;
    private MenuLeaderBoard board;
    private AudioPlayback startup;
    private boolean[] buttonz;
    private Desktop desktop;
    private int compassDir, compassDir2, last = 13;

    @SuppressWarnings("LeakingThisInConstructor")
    public MainMenu(String dude, LoginScreen px) {
        p = px;
        startup = new AudioPlayback(AudioPlayback.startUpSound(), false);
        startup.play();
        strUser = dude;
        setUndecorated(true);
        setLayout(new BorderLayout());
        RenderMainMenu.getInstance().newInstance();
        setContentPane(RenderMainMenu.getInstance());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("The SCND Genesis: Legends" + RenderGameplay.getInstance().getVersionStr());
        addMouseMotionListener(this);
        addMouseListener(this);
        addMouseWheelListener(this);
        requestFocusInWindow();
        setFocusable(true);
        addKeyListener(this);
        pack();
        setLocationRelativeTo(null); // Centers JFrame on screen //
        setResizable(false);
        setVisible(true);
        try {
            if (JenesisGamePad.getInstance().controllerFound) {
                controller = true;
                buttonz = new boolean[JenesisGamePad.getInstance().NUM_BUTTONS];
                pollController();
            }
            if (this.p.controller) {

                if (JenesisGamePad.getInstance().statusInt == 1) {
                    sytemNotice(JenesisGamePad.getInstance().controllerName + " " + Language.getInstance().getLine(103));
                } else if (JenesisGamePad.getInstance().statusInt == 0) {
                    sytemNotice(Language.getInstance().getLine(104));
                } else if (JenesisGamePad.getInstance().statusInt == 2) {
                    sytemNotice(Language.getInstance().getLine(105));
                }
            }
        } catch (Error ex) {
            sytemNotice(Language.getInstance().getLine(106));
        }
        refreshWindow();
    }

    public static MainMenu getMenu() {
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
        dispose();
        p.showWindow();
    }

    public final void sytemNotice(String moi) {
        RenderMainMenu.getInstance().systemNotice(moi);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
    }

    /**
     * Select option in menu
     */
    private void select() {
        //if viewing stats, go back to menu
        if (RenderMainMenu.getInstance().getPlace() == 1 || RenderMainMenu.getInstance().getPlace() == 2 || RenderMainMenu.getInstance().getPlace() == 3) {
            if (RenderMainMenu.getInstance().getPlace() == 3) {
                RenderMainMenu.getInstance().stopTut();
            }
            RenderMainMenu.getInstance().setPlace(0);
        } else {
            String destination = RenderMainMenu.getInstance().getMenuModeStr();

            if (destination.equalsIgnoreCase(MainWindow.lanClient)) {
                scan = new NetworkScanLan();
            } else if (destination.equalsIgnoreCase(MainWindow.lanHost)) {
                terminateThis();
                RenderMainMenu.getInstance().systemNotice(Language.getInstance().getLine(107));
                MainWindow.newInstance(strUser, mode[1]);
            } else if (destination.equalsIgnoreCase("vs1")) {
                terminateThis();
                RenderMainMenu.getInstance().systemNotice(Language.getInstance().getLine(108));
                MainWindow.newInstance(strUser, mode[0]);
            } else if (destination.equalsIgnoreCase("vs2")) {
                terminateThis();
                RenderMainMenu.getInstance().systemNotice(Language.getInstance().getLine(109));
                MainWindow.newInstance(strUser, mode[4]);
            } else if (destination.equalsIgnoreCase(MainWindow.storyMode)) {

                terminateThis();
                MainWindow.newInstance(strUser, MainWindow.storyMode);
            } else if (destination.equalsIgnoreCase("options")) {
                options = new WindowOptions();
            } else if (destination.equalsIgnoreCase("stats")) {
                RenderMainMenu.getInstance().setPlace(1);
            } else if (destination.equalsIgnoreCase("ach")) {
                RenderMainMenu.getInstance().refreshStats();
                RenderMainMenu.getInstance().setPlace(2);
            } else if (destination.equalsIgnoreCase("about")) {
                about = new WindowAbout();
            } else if (destination.equalsIgnoreCase("controls")) {
                controls = new WindowControls();
            } else if (destination.equalsIgnoreCase("tutorial")) {
                RenderMainMenu.getInstance().setPlace(3);
                RenderMainMenu.getInstance().startTut();
            } else if (destination.equalsIgnoreCase("logout")) {
                logOut();
            } else if (destination.equalsIgnoreCase("leaders")) {
                if (boardNotUp) {
                    board = new MenuLeaderBoard();
                    boardNotUp = false;
                } else {
                    board.reappear();
                }
            }

            if (destination.equalsIgnoreCase("exit")) {
                exit();
            }
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent mwe) {
        {
            int count = mwe.getWheelRotation();

            //down - positive values
            if (count >= 0) {
                RenderMainMenu.getInstance().goDown();
            }
            //up -negative values
            if (count < 0) {
                RenderMainMenu.getInstance().goUp();
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent m) {
        int x = RenderMainMenu.getInstance().getXMenu();
        int y = RenderMainMenu.getInstance().getYMenu() - 14;
        int space = RenderMainMenu.getInstance().getSpacer();
        if (RenderMainMenu.getInstance().getPlace() == 3) {
            if (m.getX() >= 425) {
                RenderMainMenu.getInstance().forwarTut();
            } else {
                RenderMainMenu.getInstance().backTut();
            }
        } else if ((m.getY() > y) && (m.getY() < (y + (space * last))) && m.getX() > x) {
            if (m.getButton() == MouseEvent.BUTTON1) {
                select();
            }

            //middle mouse
            if (m.getButton() == MouseEvent.BUTTON2) {
            }

            //middle mouse
            if (m.getButton() == MouseEvent.BUTTON3) {
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent m) {
    }

    @Override
    public void mouseDragged(MouseEvent m) {
    }

    @Override
    public void mouseMoved(MouseEvent m) {
        int x = RenderMainMenu.getInstance().getXMenu();
        int y = RenderMainMenu.getInstance().getYMenu() - 14;
        int space = RenderMainMenu.getInstance().getSpacer() - 2;
        //menu space
        if ((m.getX() > x) && (m.getX() < x + 200)) {
            if ((m.getY() > space) && (m.getY() < (y + space))) {
                RenderMainMenu.getInstance().setMenuPos(0);
            }

            if ((m.getY() > (y + space)) && (m.getY() < (y + (space * 2)))) {
                RenderMainMenu.getInstance().setMenuPos(1);
            }

            if ((m.getY() > (y + (space * 2))) && (m.getY() < (y + (space * 3)))) {
                RenderMainMenu.getInstance().setMenuPos(2);
            }

            if ((m.getY() > (y + (space * 3))) && (m.getY() < (y + (space * 4)))) {
                RenderMainMenu.getInstance().setMenuPos(3);
            }

            if ((m.getY() > (y + (space * 4))) && (m.getY() < (y + (space * 5)))) {
                RenderMainMenu.getInstance().setMenuPos(4);
            }

            if ((m.getY() > (y + (space * 5))) && (m.getY() < (y + (space * 6)))) {
                RenderMainMenu.getInstance().setMenuPos(5);
            }

            if ((m.getY() > (y + (space * 7))) && (m.getY() < (y + (space * 8)))) {
                RenderMainMenu.getInstance().setMenuPos(6);
            }

            if ((m.getY() > (y + (space * 8))) && (m.getY() < (y + (space * 9)))) {
                RenderMainMenu.getInstance().setMenuPos(7);
            }

            if ((m.getY() > (y + (space * 9))) && (m.getY() < (y + (space * 10)))) {
                RenderMainMenu.getInstance().setMenuPos(8);
            }

            if ((m.getY() > (y + (space * 10))) && (m.getY() < (y + (space * 11)))) {
                RenderMainMenu.getInstance().setMenuPos(9);
            }

            if ((m.getY() > (y + (space * 11))) && (m.getY() < (y + (space * 12)))) {
                RenderMainMenu.getInstance().setMenuPos(10);
            }

            if ((m.getY() > (y + (space * 12))) && (m.getY() < (y + (space * last)))) {
                RenderMainMenu.getInstance().setMenuPos(11);
            }

            if ((m.getY() > (y + (space * 13))) && (m.getY() < (y + (space * last)))) {
                RenderMainMenu.getInstance().setMenuPos(12);
            }
        }
        //System.out.print("Mouse X:"+m.getX());
        //System.out.println("Mouse y:"+m.getY());
    }

    @Override
    public void mouseExited(MouseEvent m) {
    }

    @Override
    public void mousePressed(MouseEvent m) {
    }

    @Override
    public void mouseReleased(MouseEvent m) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) {
            RenderMainMenu.getInstance().goDown();
        }

        if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {
            RenderMainMenu.getInstance().goUp();
        }

        if (keyCode == KeyEvent.VK_F) {
            provideFeedback('f');
        }

        if (keyCode == KeyEvent.VK_B) {
            provideFeedback('b');
        }

        if (keyCode == KeyEvent.VK_L) {
            provideFeedback('l');
        }

        if (keyCode == KeyEvent.VK_RIGHT) {
            if (RenderMainMenu.getInstance().getPlace() == 3) {
                RenderMainMenu.getInstance().forwarTut();
            }
        }

        if (keyCode == KeyEvent.VK_LEFT) {
            if (RenderMainMenu.getInstance().getPlace() == 3) {
                RenderMainMenu.getInstance().backTut();
            }
        }

        if (keyCode == KeyEvent.VK_1) {
            if (RenderMainMenu.getInstance().getPlace() == 3) {
                RenderMainMenu.getInstance().sktpToTut(0);
            }
        }

        if (keyCode == KeyEvent.VK_2) {
            if (RenderMainMenu.getInstance().getPlace() == 3) {
                RenderMainMenu.getInstance().sktpToTut(3);
            }
        }

        if (keyCode == KeyEvent.VK_3) {
            if (RenderMainMenu.getInstance().getPlace() == 3) {
                RenderMainMenu.getInstance().sktpToTut(11);
            }
        }

        if (keyCode == KeyEvent.VK_4) {
            if (RenderMainMenu.getInstance().getPlace() == 3) {
                RenderMainMenu.getInstance().sktpToTut(20);
            }
        }

        if (keyCode == KeyEvent.VK_5) {
            if (RenderMainMenu.getInstance().getPlace() == 3) {
                RenderMainMenu.getInstance().sktpToTut(27);
            }
        }

        if (keyCode == KeyEvent.VK_6) {
            if (RenderMainMenu.getInstance().getPlace() == 3) {
                RenderMainMenu.getInstance().sktpToTut(32);
            }
        }

        if (keyCode == KeyEvent.VK_F12) {
            RenderMainMenu.getInstance().captureScreenShot();
        }

        if (keyCode == KeyEvent.VK_ENTER) {
            select();
        }
    }

    private void provideFeedback(char code) {
        if (Desktop.isDesktopSupported()) {
            desktop = Desktop.getDesktop();
            if (desktop.isSupported(Desktop.Action.BROWSE)) {
                URI uri = null;
                try {
                    switch (code) {
                        case 'f': {
                            uri = new URI("https://docs.google.com/spreadsheet/viewform?formkey=dGppbVViZHE5QWxZYkRBazZNcUtTRHc6MQ");
                        }
                        break;
                        case 'b': {
                            uri = new URI("http://scndgen.sf.net/blog.php");
                        }
                        break;
                        case 'l': {
                            uri = new URI("http://www.facebook.com/pages/THE-SCND-GENESIS/111839318834780");
                        }
                        break;

                        default: {
                            uri = new URI("http://scndgen.sf.net");
                        }
                    }

                    desktop.browse(uri);
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                } catch (URISyntaxException use) {
                    use.printStackTrace();

                }
            }
        }
        p.trayMessage("Thanks", "Thank you for your support!!");
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Exit game
     */
    public void exit() {
        int x = JOptionPane.showConfirmDialog(null, Language.getInstance().getLine(110), "Exit", JOptionPane.YES_NO_OPTION);
        if (x == JOptionPane.YES_OPTION) {
            int b = JOptionPane.showConfirmDialog(null, Language.getInstance().getLine(111), "Seriously", JOptionPane.YES_NO_OPTION);
            if (b == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(null, Language.getInstance().getLine(112), "Later", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }
        }
    }

    /**
     * Repaints GUI
     */
    public void refresh() {
        RenderMainMenu.getInstance().repaint();
    }

    /**
     * Stops repainting GUI
     */
    public void stopPaint() {
        RenderMainMenu.getInstance().StopRepaint();
    }

    /**
     * Gets rid of main menu (minimise)
     */
    public void terminateThis() {
        RenderMainMenu.getInstance().StopRepaint();
        isActive = false;
        dispose();
    }

    /**
     * Show menu (maximise)
     */
    public void showModes() {
        isActive = true;
        try {
            if (JenesisGamePad.getInstance().controllerFound) {
                pollController();
            }
        } catch (Exception e) {
        }
        setVisible(true);
    }

    /**
     * Create a client game
     */
    public void hostGame() {
        MainWindow.newInstance(com.scndgen.legends.windows.MainMenu.getUserName(), MainWindow.lanClient);
    }

    private void refreshWindow() {
        new Thread() {

            @Override
            @SuppressWarnings({"static-access", "SleepWhileHoldingLock"})
            public void run() {
                while (true) {
                    try {
                        this.sleep(16);
                        repaint();
                    } catch (Exception e) {
                    }
                }
            }
        }.start();
    }

    private void pollController() {
        new Thread() {

            @Override
            @SuppressWarnings({"static-access", "SleepWhileHoldingLock"})
            public void run() {
                try {
                    do {
                        JenesisGamePad.getInstance().poll();

                        compassDir2 = JenesisGamePad.getInstance().getXYStickDir();
                        if (compassDir2 == JenesisGamePad.getInstance().NORTH) {
                            RenderMainMenu.getInstance().goUp();
                        } else if (compassDir2 == JenesisGamePad.getInstance().SOUTH) {
                            RenderMainMenu.getInstance().goDown();
                        }

                        //update bottons
                        buttonz = JenesisGamePad.getInstance().getButtons();


                        // get POV hat compass direction
                        compassDir = JenesisGamePad.getInstance().getHatDir();
                        {
                            if (compassDir == JenesisGamePad.getInstance().SOUTH) {
                                if (doneChilling) {
                                    RenderMainMenu.getInstance().goDown();
                                }
                                menuLatency();
                            }

                            if (compassDir == JenesisGamePad.getInstance().NORTH) {
                                if (doneChilling) {
                                    RenderMainMenu.getInstance().goUp();
                                }
                                menuLatency();
                            }
                        }

                        if (buttonz[2]) {
                            if (doneChilling) {
                                quickVibrate(0.4f, 1000);
                                select();
                            }
                            menuLatency();
                        }

                        if (buttonz[3]) {
                            if (doneChilling) {
                                quickVibrate(0.8f, 1000);
                                exit();
                            }
                            menuLatency();
                        }

                        // get compass direction for the two analog sticks
                        compassDir = JenesisGamePad.getInstance().getXYStickDir();

                        compassDir = JenesisGamePad.getInstance().getZRZStickDir();


                        this.sleep(66);
                    } while (isActive);
                } catch (Exception e) {
                }
            }
        }.start();
    }

    /**
     * Responsible for latency in game menus(controller)
     */
    private void menuLatency() {
        new Thread() {

            @Override
            @SuppressWarnings("static-access")
            public void run() {
                try {
                    doneChilling = false;
                    this.sleep(166);
                    doneChilling = true;
                } catch (Exception e) {
                }
            }
        }.start();
    }

    /**
     * Vibrate
     */
    private void quickVibrate(float strength, int length) {
        final float power = strength;
        final int time = length;
        new Thread() {

            @SuppressWarnings("static-access")
            @Override
            public void run() {
                try {
                    JenesisGamePad.getInstance().setRumbler(true, power);
                    this.sleep(time);
                    JenesisGamePad.getInstance().setRumbler(false, 0.0f);
                } catch (Exception e) {
                }
            }
        }.start();
    }
}

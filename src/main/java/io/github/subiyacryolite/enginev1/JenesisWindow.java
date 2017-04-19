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
import com.scndgen.legends.enums.Overlay;
import com.scndgen.legends.enums.SubMode;
import com.scndgen.legends.network.NetworkScanLan;
import com.scndgen.legends.render.RenderGameplay;
import com.scndgen.legends.render.RenderMainMenu;
import com.scndgen.legends.threads.AudioPlayback;
import com.scndgen.legends.windows.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URI;

/**
 * @author Ndana
 */
public class JenesisWindow extends JFrame implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {

    public static String strUser = "no user", strPoint = "0", strPlayTime = "0", matchCountStr = "0";
    public static boolean boardNotUp = true, controller = false, isActive = true, doneChilling;
    private static LoginScreen p;
    public int[] ach = new int[5];
    public int[] classArr = new int[5];
    private WindowControls controls;
    private WindowOptions options;
    private WindowAbout about;
    private NetworkScanLan scan;
    private MenuLeaderBoard board;
    private AudioPlayback startup;
    private boolean[] buttonz;
    private int compassDir, compassDir2, last = 13;

    @SuppressWarnings("LeakingThisInConstructor")
    public JenesisWindow(String dude, LoginScreen px) {
        p = px;
        startup = new AudioPlayback(AudioPlayback.startUpSound(), false);
        startup.play();
        strUser = dude;
        setUndecorated(true);
        setLayout(new BorderLayout());
        setContentPane(JenesisPanel.newInstance(strUser, SubMode.MAIN_MENU));
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

    public static JenesisWindow getInstance() {
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
        RenderMainMenu.getInstance().primaryNotice(moi);
    }


    /**
     * Select option in menu
     */
    private void select() {
        //if viewing STATS, go back to menu
        if (RenderMainMenu.getInstance().getOverlay() == Overlay.STATISTICS || RenderMainMenu.getInstance().getOverlay() == Overlay.ACHIEVEMENTS || RenderMainMenu.getInstance().getOverlay() == Overlay.TUTORIAL) {
            if (RenderMainMenu.getInstance().getOverlay() == Overlay.TUTORIAL) {
                RenderMainMenu.getInstance().stopTutorial();
            }
            RenderMainMenu.getInstance().setOverlay(Overlay.PRIMARY);
        } else {
            SubMode destination = RenderMainMenu.getInstance().getMenuModeStr();
            if (destination == SubMode.LAN_CLIENT) {
                scan = new NetworkScanLan();
            } if (destination == SubMode.LOGOUT) {
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

    @Override
    public void mouseWheelMoved(MouseWheelEvent mwe) {
        JenesisPanel.getInstance().mouseWheelMoved(mwe);
    }

    @Override
    public void mouseClicked(MouseEvent m) {
        JenesisPanel.getInstance().mouseClicked(m);
    }

    @Override
    public void mouseEntered(MouseEvent m) {
        JenesisPanel.getInstance().mouseEntered(m);
    }

    @Override
    public void mouseDragged(MouseEvent m) {
        JenesisPanel.getInstance().mouseDragged(m);
    }

    @Override
    public void mouseMoved(MouseEvent m) {
        JenesisPanel.getInstance().mouseMoved(m);
    }

    @Override
    public void mouseExited(MouseEvent m) {
        JenesisPanel.getInstance().mouseExited(m);
    }

    @Override
    public void mousePressed(MouseEvent m) {
        JenesisPanel.getInstance().mousePressed(m);
    }

    @Override
    public void mouseReleased(MouseEvent m) {
        JenesisPanel.getInstance().mouseReleased(m);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        JenesisPanel.getInstance().keyPressed(e);
        int keyCode = e.getKeyCode();
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
        if (keyCode == KeyEvent.VK_ENTER) {
            select();
        }
    }

    private void provideFeedback(char code) {
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            if (desktop.isSupported(Desktop.Action.BROWSE)) {
                URI uri = null;
                try {
                    switch (code) {
                        case 'f': {
                            uri = new URI("https://docs.google.com/spreadsheet/viewform?formkey=dGppbVViZHE5QWxZYkRBazZNcUtTRHc6MQ");
                        }
                        break;
                        case 'b': {
                            uri = new URI("https://subiyacryolite.github.io/");
                        }
                        break;
                        case 'l': {
                            uri = new URI("http://www.facebook.com/pages/THE-SCND-GENESIS/111839318834780");
                        }
                        break;

                        default: {
                            uri = new URI("http://www.scndgen.com");
                        }
                    }
                    desktop.browse(uri);
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
            }
        }
        p.trayMessage("Thanks", "Thank you for your support!!");
    }

    @Override
    public void keyReleased(KeyEvent e) {
        JenesisPanel.getInstance().keyReleased(e);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        JenesisPanel.getInstance().keyTyped(e);
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
    public void joinGame() {
        JenesisPanel.newInstance(JenesisWindow.getUserName(), SubMode.LAN_CLIENT);
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

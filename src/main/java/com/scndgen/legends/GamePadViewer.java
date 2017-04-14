package com.scndgen.legends;
// GamePadViewer.java
// Andrew Davison, October 2006, ad@fivedots.coe.psu.ac.th

/* The game pad is represented by a canvas (HatPanel) which shows:
 * 10 textfields (ButtonsPanel),
which represent the game pad's buttons;

 * two JPanels for the (x,y) and (z,rz) analog sticks
(CompassPanel)

 * a JPanel for the POV hat (another CompassPanel)

 * a Rumble checkbox for switching rumbling on/off

Every DELAY ms the game pad is polled (using GamePadController),
and the CompassPanels and ButtonsPanel are updated.

The updates are carried out by an action handler which is executed 
in the event-dispatching thread. That means that the Swing updates
will be carried out correctly.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePadViewer extends JFrame {

    private static final int DELAY = 40;   // ms (polling interval)
    // needs to be fast to catch fast button pressing!
    private CompassPanel xyPanel, zrzPanel, hatPanel;
    // shows the two analog sticks and POV hat
    private ButtonsPanel buttonsPanel;   // shows which buttons are pressed
    private JCheckBox rumblerCheck;
    private Timer pollTimer;   // timer which triggers the polling

    public GamePadViewer() {
        super("GamePad Viewer");
        makeGUI();
        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                pollTimer.stop();   // stop the timer
                System.exit(0);
            }
        });
        pack();
        setResizable(false);
        setVisible(true);
        startPolling();
    } // end of GamePadViewer()

    // -----------------------------------------------------
    public static void main(String args[]) {
        new GamePadViewer();
    }

    private void makeGUI() /* GUI for button textfields, three compass panels, and
    the rumbler checkbox */ {
        Container c = getContentPane();
        c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS)); // vertical box layout

        buttonsPanel = new ButtonsPanel();
        c.add(buttonsPanel);

        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS)); // horizontal box layout

        // three CompassPanels in a row
        hatPanel = new CompassPanel("POV");
        p.add(hatPanel);

        xyPanel = new CompassPanel("xy");
        p.add(xyPanel);

        zrzPanel = new CompassPanel("zRz");
        p.add(zrzPanel);

        c.add(p);

        // rumbler checkbox
        rumblerCheck = new JCheckBox("Rumbler");
        rumblerCheck.addItemListener(new ItemListener() {

            public void itemStateChanged(ItemEvent ie) {
                if (ie.getStateChange() == ItemEvent.SELECTED) {
                    GamePadController.getInstance().setRumbler(true, 0.8f);  // switch on
                } else // deselected
                {
                    GamePadController.getInstance().setRumbler(false, 0.0f);  // switch off
                }
            }
        });
        c.add(rumblerCheck);
    }  // end of makeGUI()

    private void startPolling() /* Set up a timer which is activated every DELAY ms
    and polls the game pad and updates the GUI.
    Safe since the action handler is executed in the
    event-dispatching thread. */ {
        ActionListener pollPerformer = new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                // System.out.println("polling...");
                GamePadController.getInstance().poll();

                // update the GUI:
                // get POV hat compass direction
                int compassDir = GamePadController.getInstance().getHatDir();
                hatPanel.setCompass(compassDir);

                // get compass direction for the two analog sticks
                compassDir = GamePadController.getInstance().getXYStickDir();
                xyPanel.setCompass(compassDir);

                compassDir = GamePadController.getInstance().getZRZStickDir();
                zrzPanel.setCompass(compassDir);

                // get button settings
                boolean[] buttons = GamePadController.getInstance().getButtons();
                buttonsPanel.setButtons(buttons);
            }
        };

        pollTimer = new Timer(DELAY, pollPerformer);
        pollTimer.start();
    }  // end of startPolling()
}  // end of GamePadViewer class


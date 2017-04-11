package com.scndgen.legends;
// CompassPanel.java
// Andrew Davison, October 2006, ad@fivedots.coe.psu.ac.th

/* A canvas which draws a circle in the current compass position for
the analog stick / hat (and a label as background).
 */

import javax.swing.*;
import java.awt.*;

public class CompassPanel extends JPanel {

    private static final int PANEL_SIZE = 80;
    private static final int CIRCLE_RADIUS = 5;
    private static final int OFFSET = 8;   // of the circle from the panel's edge
    // (x,y) coordinates for the compass positions
    private int xPos[], yPos[];
    private int compassDir;
    /* holds the current compass value, which is used to index into
    the xPos[] and yPos[] arrays */
    // for displaying the axes label
    private Font labelFont;
    private String axesLabel;
    private int xLabel, yLabel;

    public CompassPanel(String label) {
        axesLabel = label;

        setBackground(Color.white);
        setPreferredSize(new Dimension(PANEL_SIZE, PANEL_SIZE));

        initCompass();

        // set up label font and (x,y) position for label
        labelFont = new Font("SansSerif", Font.BOLD, 12);
        FontMetrics metrics = this.getFontMetrics(labelFont);
        xLabel = (PANEL_SIZE - metrics.stringWidth(axesLabel)) / 2;
        yLabel = PANEL_SIZE / 5;

    }  // end of CompassPanel()

    private void initCompass() /* Fill the xPos[] and yPos[] arrays with the (x,y) coordinates
    of the compass positions.
    The array will be accessed using the compass constants
    defined in the GamePadController class.
     */ {
        xPos = new int[GamePadController.NUM_COMPASS_DIRS];
        yPos = new int[GamePadController.NUM_COMPASS_DIRS];

        // top row
        xPos[GamePadController.NW] = OFFSET;
        yPos[GamePadController.NW] = OFFSET;
        xPos[GamePadController.NORTH] = PANEL_SIZE / 2;
        yPos[GamePadController.NORTH] = OFFSET;
        xPos[GamePadController.NE] = PANEL_SIZE - OFFSET;
        yPos[GamePadController.NE] = OFFSET;

        // middle row
        xPos[GamePadController.WEST] = OFFSET;
        yPos[GamePadController.WEST] = PANEL_SIZE / 2;
        xPos[GamePadController.NONE] = PANEL_SIZE / 2;
        yPos[GamePadController.NONE] = PANEL_SIZE / 2;
        xPos[GamePadController.EAST] = PANEL_SIZE - OFFSET;
        yPos[GamePadController.EAST] = PANEL_SIZE / 2;

        // bottom row
        xPos[GamePadController.SW] = OFFSET;
        yPos[GamePadController.SW] = PANEL_SIZE - OFFSET;
        xPos[GamePadController.SOUTH] = PANEL_SIZE / 2;
        yPos[GamePadController.SOUTH] = PANEL_SIZE - OFFSET;
        xPos[GamePadController.SE] = PANEL_SIZE - OFFSET;
        yPos[GamePadController.SE] = PANEL_SIZE - OFFSET;

        // initial compass position -- the center
        compassDir = GamePadController.NONE;
    }  // end of initCompass()

    public void setCompass(int pos) // update the current compass position
    {
        // System.out.println("Compass pos: " + pos);

        if ((pos < 0) || (pos >= GamePadController.NUM_COMPASS_DIRS)) {
            System.out.println("Compass value out of range");
            compassDir = GamePadController.NONE;    // middle of compass
        } else {
            compassDir = pos;
        }
        repaint();
    } // end of setCompass()

    public void paintComponent(Graphics g) // draw the current compass position as a black circle
    {
        super.paintComponent(g);

        g.drawRect(1, 1, PANEL_SIZE - 2, PANEL_SIZE - 2);   // a black border

        g.setFont(labelFont);
        g.drawString(axesLabel, xLabel, yLabel);   // axes label

        /* use the compass direction (compassDir) to index into xPos[] and
        yPos[] to get the (x,y) position where the circle should be
        drawn. */
        g.fillOval(xPos[compassDir] - CIRCLE_RADIUS, // x position
                yPos[compassDir] - CIRCLE_RADIUS, // y position
                CIRCLE_RADIUS * 2, CIRCLE_RADIUS * 2);

    } // end of paintComponent()
} // end of CompassPanel class


package com.scndgen.legends.tests.jinput;
// CompassPanel.java
// Andrew Davison, October 2006, ad@fivedots.coe.psu.ac.th

/* A canvas which draws a circle in the current compass position for
the analog stick / hat (and a label as background).
 */

import io.github.subiyacryolite.enginev1.JenesisGamePad;

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
    defined in the JenesisGamePad class.
     */ {
        xPos = new int[JenesisGamePad.NUM_COMPASS_DIRS];
        yPos = new int[JenesisGamePad.NUM_COMPASS_DIRS];

        // top row
        xPos[JenesisGamePad.NW] = OFFSET;
        yPos[JenesisGamePad.NW] = OFFSET;
        xPos[JenesisGamePad.NORTH] = PANEL_SIZE / 2;
        yPos[JenesisGamePad.NORTH] = OFFSET;
        xPos[JenesisGamePad.NE] = PANEL_SIZE - OFFSET;
        yPos[JenesisGamePad.NE] = OFFSET;

        // middle row
        xPos[JenesisGamePad.WEST] = OFFSET;
        yPos[JenesisGamePad.WEST] = PANEL_SIZE / 2;
        xPos[JenesisGamePad.NONE] = PANEL_SIZE / 2;
        yPos[JenesisGamePad.NONE] = PANEL_SIZE / 2;
        xPos[JenesisGamePad.EAST] = PANEL_SIZE - OFFSET;
        yPos[JenesisGamePad.EAST] = PANEL_SIZE / 2;

        // bottom row
        xPos[JenesisGamePad.SW] = OFFSET;
        yPos[JenesisGamePad.SW] = PANEL_SIZE - OFFSET;
        xPos[JenesisGamePad.SOUTH] = PANEL_SIZE / 2;
        yPos[JenesisGamePad.SOUTH] = PANEL_SIZE - OFFSET;
        xPos[JenesisGamePad.SE] = PANEL_SIZE - OFFSET;
        yPos[JenesisGamePad.SE] = PANEL_SIZE - OFFSET;

        // initial compass position -- the center
        compassDir = JenesisGamePad.NONE;
    }  // end of initCompass()

    public void setCompass(int pos) // update the current compass position
    {
        // System.out.println("Compass pos: " + pos);

        if ((pos < 0) || (pos >= JenesisGamePad.NUM_COMPASS_DIRS)) {
            System.out.println("Compass value out of range");
            compassDir = JenesisGamePad.NONE;    // middle of compass
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


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
package com.scnd_genesis.drawing;

import com.scnd_genesis.LoginScreen;
import com.scnd_genesis.engine.JenesisLanguage;
import com.scnd_genesis.engine.JenesisGlassPane;
import com.scnd_genesis.engine.JenesisMenu;
import com.scnd_genesis.OverWorld;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.VolatileImage;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @Author: Ifunga Ndana
 * @Class: screenDrawer
 * This class draws nd manipulates all sprites, images and effects used in the game
 */
public class DrawOverworld extends JenesisMenu {

    public Graphics2D g2d;
    RenderingHints renderHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //anti aliasing, kill jaggies
    private ClassLoader classloader;
    private GraphicsEnvironment ge;
    private URL url;
    private JenesisLanguage lang;
    private Color bg = new Color(214, 217, 223);
    private Image userPic = loadIcon("images/clouds.png");
    private boolean status = true;
    private boolean geo = false;
    private Image[] charSpr;
    private Image charSpr2, charSpr1, drawSpr;
    private Image netWork = loadIcon("images/worldMap.jpg");
    private Image hills = loadIcon("images/trees.png");
    private int offset = 10;
    private int xCord = 0, yCord = 0, xCordCloud = 0, yCordCloud = 0, xCordChar = 245, yCordChar = 240;
    private String stat1, stat2, stat3, text2 = "";
    private int timeInt = 0;
    private int screenWidth = 852, screenHeight = 480;
    private int moveSpec = 10;
    private Font font = new Font("SansSerif", Font.PLAIN, 14);
    private int spriteSto = 0;
    private int valCode, picWidth, picHeight;
    private int yTEST = 400;
    private float opac = 10;
    private AffineTransform tfm = new AffineTransform(1.0, 0.0,
            0.0, 0.5,
            0.0, 1.0);
    private float[] coord = {(float) 0.37793, (float) -0.47500, (float) 228.0,
            (float) 0.0, (float) 0.19599, (float) 118.0,
            (float) 0.0, (float) -0.00130, (float) 1.0
    };
    private AffineTransform tfm2 = new AffineTransform(1.0, 0.0,
            0.0, -1.0,
            (float) 50, (float) screenHeight);
    private Line2D lineT, lineB, lineL, lineR;
    private VolatileImage volatileImg;
    private GraphicsConfiguration gc;
    private int[] obX = new int[]{888, 2618};
    private int[] obY = new int[]{1575, 693};
    private Rectangle charBoxT, charBoxR, charBoxL, charBoxB;
    private int[] xpoints = {42, 52, 72, 52, 60, 40, 15, 28, 9, 32, 42};
    private int[] ypoints = {38, 62, 68, 80, 105, 85, 102, 75, 58, 60, 38};
    private int[] tree1x = {302, 262, 319, 325, 342, 373, 385, 302};
    private int[] tree1y = {1302, 1344, 1359, 1375, 1376, 1367, 1344, 1302};
    private int[] tree2x = {794, 766, 827, 829, 852, 878, 900, 870, 794};
    private int[] tree2y = {1550, 1578, 1593, 1609, 1613, 1604, 1582, 1555, 1550};
    private int[] tree3x = {1232, 1195, 1255, 1261, 1281, 1309, 1326, 1299, 1232};
    private int[] tree3y = {1131, 1162, 1182, 1201, 1202, 1194, 1163, 1144, 1131};
    private int[] tree4x = {1086, 1104, 1144, 1146, 1173, 1198, 1224, 1188, 1086};
    private int[] tree4y = {1994, 2004, 2009, 2022, 2025, 2014, 1994, 1970, 1994};
    private int[] river1x = {1900, 1833, 1846, 1868, 1746, 1740, 1919, 1914, 2091, 2098, 2328, 2676, 2848, 1900};
    private int[] river1y = {1, 188, 383, 660, 803, 1139, 1137, 918, 717, 602, 552, 220, 7, 1};
    private int[] river2x = {1749, 1753, 1722, 1442, 1440, 1951, 1996, 1997, 1846, 1853, 1900, 1946, 1950, 1749};
    private int[] river2y = {1258, 1336, 1401, 1679, 2043, 2043, 1942, 1823, 1541, 1465, 1434, 1346, 1269, 1258};
    private Polygon tree1 = new Polygon(tree1x, tree1y, tree1x.length);
    private Polygon tree2 = new Polygon(tree2x, tree2y, tree2x.length);
    private Polygon tree3 = new Polygon(tree3x, tree3y, tree3x.length);
    private Polygon tree4 = new Polygon(tree4x, tree4y, tree4x.length);
    private Polygon river1 = new Polygon(river1x, river1y, river1x.length);
    private Polygon river2 = new Polygon(river2x, river2y, river2x.length);
    private Polygon star = new Polygon(xpoints, ypoints, xpoints.length);
    private Polygon[] allGeo;
    private boolean runNew, showCollision = false;
    private OverWorld parentx;

    @SuppressWarnings("LeakingThisInConstructor")
    public DrawOverworld(OverWorld p) {
        runNew = true;
        over1 = new JenesisGlassPane();
        parentx = p;
        lang = LoginScreen.getLoginScreen().getLangInst();
        charSpr2 = loadIcon("images/RailaSmall.png");
        drawSpr = charSpr2;
        picWidth = 33;
        picHeight = 97;
        charBoxT = new Rectangle(xCordChar, yCordChar, picWidth, 2);
        charBoxB = new Rectangle(xCordChar, yCordChar + picHeight, picWidth, 2);
        charBoxR = new Rectangle(xCordChar + picWidth, yCordChar, 2, picHeight);
        charBoxL = new Rectangle(xCordChar, yCordChar, 2, picHeight);
        animCloud();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(screenWidth, screenHeight); //480p, 16:9 widescreen enhanced definition, max resolution of Nintendo Wii too :D
    }

    @Override
    public void paintComponent(Graphics g) {
        createBackBuffer();
        //Check if Image is valid
        valCode = volatileImg.validate(gc);
        //if not create new vi, only affects windows apparently
        if (valCode == VolatileImage.IMAGE_INCOMPATIBLE) {
            createBackBuffer();
        }
        g2d.drawImage(netWork, xCord, yCord, this);
        g2d.drawImage(drawSpr, xCordChar, yCordChar, this); //245,240
        g2d.drawImage(hills, xCord, yCord, this);
        g2d.drawImage(userPic, xCordCloud, yCordCloud, this);
        g2d.setColor(Color.BLACK);
        g2d.drawString("X: " + (xCord), 700, 24);
        g2d.drawString("Y: " + (yCord), 700, 38);
        g2d.drawString(lang.getLine(151), 700, 48);
        g2d.drawString("CharX: " + (xCordChar), 750, 24);
        g2d.drawString("CharY: " + (yCordChar), 750, 38);

        if (showCollision) {
            //this code was used in my collision detection tests
            g2d.setComposite(makeComposite(5 * 0.1F));
            g2d.setColor(Color.WHITE);
            g2d.fill(star);
            g2d.fill(tree1);
            g2d.fill(tree2);
            g2d.fill(tree3);
            g2d.fill(tree4);
            g2d.fill(charBoxT);
            g2d.fill(charBoxB);
            g2d.fill(charBoxR);
            g2d.fill(charBoxL);
            g2d.fill(river1);
            g2d.fill(river2);
            g2d.setComposite(makeComposite(10 * 0.1F));
            //this code was used in my collision detection tests
        }
        over1.overlay(g2d, this);
    }

    private Image loadIcon(String imageName) {
        try {
            classloader = getClass().getClassLoader();
            url = classloader.getResource(imageName);
            if (url != null) {
                //return new ImageIcon( url ).getImage();
                return Toolkit.getDefaultToolkit().getImage(url);
            }
        } catch (Exception e) {
            e.printStackTrace(); // keep for dev to see missing files
        }

        throw new IllegalArgumentException("Unable to load image: " + imageName);
    }

    public void setStr(String thisText) {
        text2 = thisText;
    }

    private String shortStr(String message) {
        String returnThis;

        if (message.length() < 20) {
            returnThis = message;
        } else {
            returnThis = message.substring(0, 20) + "...";
        }

        return returnThis;
    }

    private String timeCal(String time) {
        int sec = 0;
        int min = 0;
        int day = 0;
        int hour = 0;
        timeInt = Integer.parseInt(time);
        String moi;

        if (timeInt > 60 && timeInt < 3600) {
            {
                min = timeInt / 60;
            }

            sec = timeInt - (min * 60);
            moi = min + " min " + sec + " seconds";
        } else if (timeInt >= 3600 && timeInt < 86400) {
            {
                hour = timeInt / (60 * 60); // 3600
            }

            min = (timeInt - (hour * 60 * 60)) / 60;

            sec = timeInt - ((hour * 60 * 60) + (min * 60));

            moi = hour + " : " + min + " : " + sec;
        } else if (timeInt >= 86400) {
            day = timeInt / 86400;
            moi = day + " day(s)";
        } else {
            moi = time + " seconds";
        }

        return moi;
    }

    public void moveLeft() {
        if (safeToWalk('l')) {
            //fully functional, took several hours
            if (xCord < -moveSpec && xCordChar < screenWidth / 2) {
                xCord = xCord + moveSpec;
                xCordCloud = xCordCloud + moveSpec;
                moveObsX(moveSpec);
            } else if (xCordChar > 10) {
                xCordChar = xCordChar - moveSpec;
            }
        }
    }

    public void moveRight() {
        if (safeToWalk('r')) {
            //fully functional, took several hours
            if (xCord > (-4086 + screenWidth) && xCordChar > screenWidth / 2) {
                xCord = xCord - moveSpec;
                xCordCloud = xCordCloud - moveSpec;
                moveObsX(-moveSpec);
            } else if (xCordChar < (screenWidth - picWidth)) {
                xCordChar = xCordChar + moveSpec;
            }
        }
    }

    public void moveDown() {
        if (safeToWalk('d')) {
            if (yCord > (-2048 + screenHeight) && yCordChar > screenHeight / 2) {
                //switchUser();
                yCordCloud = yCordCloud - moveSpec;
                yCord = yCord - moveSpec;
                moveObsY(-moveSpec);


            } else if (yCordChar < (screenHeight - picHeight)) {
                yCordChar = yCordChar + moveSpec;
            }
        }
    }

    public void moveUp() {
        if (safeToWalk('u')) {
            if (yCord < (-moveSpec) && yCordChar < screenHeight / 2) {
                //switchUser();
                yCordCloud = yCordCloud + moveSpec;
                yCord = yCord + moveSpec;
                moveObsY(moveSpec);
            } else if (yCordChar > 10) {
                yCordChar = yCordChar - moveSpec;
            }
        }
    }

    public void toggleDebug() {
        showCollision = !showCollision;
    }

    public void animCloud() {
        new Thread() {

            public void run() {
                do {

                    for (int x = 0; x > (-4096 + screenHeight); x++) {
                        try {
                            this.sleep(0033);
                            xCordCloud = xCordCloud - 4;
                            parentx.refresh();
                            if (xCordCloud < (-4096 + screenHeight)) {
                                System.out.println("Back");
                                xCordCloud = screenWidth;
                            }
                        } catch (InterruptedException ex) {
                            Logger.getLogger(DrawOverworld.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                } while (1 != 0);

            }
        }.start();
    }

    /**
     * Hardware acceleration
     */
    private void createBackBuffer() {
        if (runNew) {
            ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            System.out.println("Accelerateable memory!!!!!!!!!!! " + ge.getDefaultScreenDevice().getAvailableAcceleratedMemory());
            gc = ge.getDefaultScreenDevice().getDefaultConfiguration();
            //optimise, clear unused memory
            if (volatileImg != null) {
                volatileImg.flush();
                volatileImg = null;
            }
            volatileImg = gc.createCompatibleVolatileImage(LoginScreen.getLoginScreen().getGameWidth(), LoginScreen.getLoginScreen().getGameHeight());
            volatileImg.setAccelerationPriority(1.0f);
            g2d = volatileImg.createGraphics();
            g2d.setRenderingHints(renderHints); //activate aliasing
            runNew = false;
        }
    }

    private void switchUser() {
        int where = spriteSto;
        int count = 0;
        if (where == 0 && count <= 1500) {
            drawSpr = charSpr[1];
            spriteSto = 1;
            count++;
            System.out.println("Left " + spriteSto);
        }

        if (where == 1 && count >= 0) {
            drawSpr = charSpr[0];
            spriteSto = 0;
            count--;
            System.out.println("Right " + spriteSto);
        }
    }

    private boolean safeToWalk(char direction) {
        //TOP LEFT (798,1116) | (-3298, 1116)
        //BOTTOM RIGHT (1599,1919) | (-2497, 1919)
        //map version

        charBoxT = new Rectangle(xCordChar, yCordChar, picWidth, 2);
        charBoxB = new Rectangle(xCordChar, yCordChar + picHeight, picWidth, 2);
        charBoxR = new Rectangle(xCordChar + picWidth, yCordChar, 2, picHeight);
        charBoxL = new Rectangle(xCordChar, yCordChar, 2, picHeight);

        tree1 = new Polygon(tree1x, tree1y, tree1x.length);
        tree2 = new Polygon(tree2x, tree2y, tree2x.length);
        tree3 = new Polygon(tree3x, tree3y, tree3x.length);
        tree4 = new Polygon(tree4x, tree4y, tree4x.length);
        river1 = new Polygon(river1x, river1y, river1x.length);
        river2 = new Polygon(river2x, river2y, river2x.length);
        allGeo = new Polygon[]{tree1, tree2, tree3, tree4, star, river1, river2};

        status = true;


        if (direction == 'u') {
            if (geoScan(charBoxT)) {
                status = false;
                systemNotice("Not safe to walk");
            }
        }

        if (direction == 'l') {
            if (geoScan(charBoxL)) {
                status = false;
                systemNotice("Not safe to walk");
            }
        }

        if (direction == 'r') {
            if (geoScan(charBoxR)) //if intersects is true flag
            {
                status = false;
                systemNotice("Not safe to walk");
            }
        }

        if (direction == 'd') {
            if (geoScan(charBoxB)) {
                status = false;
                systemNotice("Not safe to walk");
            }
        }


        return status;
    }

    private void moveObsX(int value) {
        for (int u = 0; u < obX.length; u++) {
            obX[u] = obX[u] + value;
        }
        for (int r = 0; r < tree1x.length; r++) {
            tree1x[r] = tree1x[r] + value;
        }
        for (int r = 0; r < tree2x.length; r++) {
            tree2x[r] = tree2x[r] + value;
        }
        for (int r = 0; r < tree3x.length; r++) {
            tree3x[r] = tree3x[r] + value;
        }
        for (int r = 0; r < tree4x.length; r++) {
            tree4x[r] = tree4x[r] + value;
        }
        for (int r = 0; r < river1x.length; r++) {
            river1x[r] = river1x[r] + value;
        }
        for (int r = 0; r < river2x.length; r++) {
            river2x[r] = river2x[r] + value;
        }
    }

    private void moveObsY(int value) {
        for (int u = 0; u < obY.length; u++) {
            obY[u] = obY[u] + value;
        }
        for (int r = 0; r < tree1y.length; r++) {
            tree1y[r] = tree1y[r] + value;
        }
        for (int r = 0; r < tree2y.length; r++) {
            tree2y[r] = tree2y[r] + value;
        }
        for (int r = 0; r < tree3y.length; r++) {
            tree3y[r] = tree3y[r] + value;
        }
        for (int r = 0; r < tree4y.length; r++) {
            tree4y[r] = tree4y[r] + value;
        }
        for (int r = 0; r < river1y.length; r++) {
            river1y[r] = river1y[r] + value;
        }
        for (int r = 0; r < river2y.length; r++) {
            river2y[r] = river2y[r] + value;
        }
    }

    public boolean geoScan(Rectangle2D me) {

        geo = false;

        thisLoop:
        for (int y = 0; y < allGeo.length; y++) {
            //if true, geo will be false
            if (allGeo[y].intersects(me)) {
                geo = true;
                break thisLoop;
            }
        }

        return geo;
    }
}

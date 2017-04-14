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

import com.scndgen.legends.LoginScreen;
import com.scndgen.legends.drawing.DrawGame;
import com.scndgen.legends.engine.JenesisLanguage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.io.File;

/**
 * Genesis engine - basic menu operations
 *
 * @author ndana
 */
public abstract class JenesisRender extends JPanel {

    public JenesisGlassPane over1;
    protected VolatileImage volatileImg;
    protected JenesisLanguage langz;
    protected GraphicsEnvironment ge;
    protected GraphicsConfiguration gc;
    protected Graphics2D g2d;
    protected int screenWidth;
    protected int screenHeight;
    protected int valCode;
    protected final RenderingHints renderHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //anti aliasing, kill jaggies

    public abstract void paintComponent(Graphics g);

    /**
     * System notice in overlay
     *
     * @param message - the message to display
     */
    public final void systemNotice(String message) {
        over1.systemNotice(message);
    }

    /**
     * System notice in overlay
     *
     * @param message - the message to display
     */
    public final void systemNotice2(String message) {
        over1.systemNotice2(message);
    }

    /**
     * Transparency
     * e.g AlphaComposite(10*0.1f)
     *
     * @param alpha, value from 10 to 0
     * @return
     */
    public final AlphaComposite makeComposite(float alpha) {
        int type = AlphaComposite.SRC_OVER;
        if (alpha >= 0.0f && alpha <= 1.0f) {
            //nothing
        } else {
            alpha = 0.0f;
        }
        return (AlphaComposite.getInstance(type, alpha));
    }

    /**
     * Hardware acceleration
     */
    protected final void createBackBuffer() {
        if (volatileImg == null) {
            ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            System.out.println("Accelerateable memory!!!!!!!!!!! " + ge.getDefaultScreenDevice().getAvailableAcceleratedMemory());
            gc = ge.getDefaultScreenDevice().getDefaultConfiguration();
            volatileImg = gc.createCompatibleVolatileImage(LoginScreen.getInstance().getGameWidth(), LoginScreen.getInstance().getGameHeight());
            volatileImg.setAccelerationPriority(1.0f);
            g2d = volatileImg.createGraphics();
            g2d.setRenderingHints(renderHints); //activate aliasing
        }
    }

    /**
     * Gets screenshot
     */
    public final void captureScreenShot() {
        try {
            BufferedImage dudeC = volatileImg.getSnapshot();

            File file;

            if (!new File(System.getProperty("user.home") + File.separator + ".config" + File.separator + "scndgen" + File.separator + "screenshots").exists()) {
                new File(System.getProperty("user.home") + File.separator + ".config" + File.separator + "scndgen" + File.separator + "screenshots").mkdirs();
            }
            file = new File(System.getProperty("user.home") + File.separator + ".config" + File.separator + "scndgen" + File.separator + "screenshots" + File.separator + DrawGame.generateUID() + ".png");
            ImageIO.write(dudeC, "png", file);
            systemNotice(langz.getLine(170));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}

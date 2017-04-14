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
package com.scndgen.legends.drawing;

import com.scndgen.legends.LoginScreen;
import io.github.subiyacryolite.enginev1.JenesisImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.VolatileImage;

/**
 * @Author: Ifunga Ndana
 * @Class: screenDrawer
 * This class draws nd manipulates all sprites, images and effects used in the game
 */
public class SpecialDrawMenuBGs extends JPanel {

    public static Graphics2D g2d;
    private JenesisImageLoader pix;
    private BufferedImage pic;
    private GraphicsEnvironment ge;
    private GraphicsConfiguration gc;
    private VolatileImage volatileImg;
    private boolean runNew;

    public SpecialDrawMenuBGs() {
        runNew = true;
        pix = new JenesisImageLoader();
        int x = (int) (Math.random() * 5);
        switch (x) {
            case 0: {
                pic = pix.loadBufferedImage("images/Story/blur/s4.png");
            }
            break;

            case 1: {
                pic = pix.loadBufferedImage("images/Story/blur/s5.png");
            }
            break;

            case 2: {
                pic = pix.loadBufferedImage("images/Story/blur/s6.png");

            }
            break;

            case 3: {
                pic = pix.loadBufferedImage("images/Story/blur/s11.png");

            }
            break;

            default: {
                pic = pix.loadBufferedImage("images/Story/blur/s6.png");
            }
            break;
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(852, 480); //480p, 16:9 widescreen enhanced definition, max resolution of Nintendo Wii too :D
    }

    @Override
    public void paintComponent(Graphics g) {
        g2d = (Graphics2D) g;
        g2d.drawImage(pic, 0, 0, this);
        g2d.setColor(Color.BLACK);
        g2d.setComposite(makeComposite(0.7f));
        g2d.fillRect(0, 0, 853, 480);
        g2d.setComposite(makeComposite(1.0f));
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
            volatileImg = gc.createCompatibleVolatileImage(LoginScreen.getInstance().getGameWidth(), LoginScreen.getInstance().getGameHeight());
            volatileImg.setAccelerationPriority(1.0f);
            g2d = volatileImg.createGraphics();
            runNew = false;
        }
    }

    private ConvolveOp getGaussianBlurFilter(int radius, boolean horizontal) {
        if (radius < 1) {
            throw new IllegalArgumentException("Radius must be >= 1");
        }

        int size = radius * 2 + 1;
        float[] data = new float[size];
        float sigma = radius / 3.0f;
        float twoSigmaSquare = 2.0f * sigma * sigma;
        float sigmaRoot = (float) Math.sqrt(twoSigmaSquare * Math.PI);
        float total = 0.0f;
        for (int i = -radius; i <= radius; i++) {
            float distance = i * i;
            int index = i + radius;
            data[index] = (float) Math.exp(-distance / twoSigmaSquare) / sigmaRoot;
            total += data[index];
        }

        for (int i = 0; i < data.length; i++) {
            data[i] /= total;
        }
        Kernel kernel = null;
        if (horizontal) {
            kernel = new Kernel(size, 1, data);
        } else {
            kernel = new Kernel(1, size, data);
        }
        return new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
    }

    /**
     * Transparency
     * e.g AlphaComposite(10*0.1f)
     *
     * @param alpha, value from 10 to 0
     * @return
     */
    public AlphaComposite makeComposite(float alpha) {
        int type = AlphaComposite.SRC_OVER;
        if (alpha >= 0.0f && alpha <= 1.0f) {
            //nothing
        } else {
            alpha = 0.0f;
        }
        return (AlphaComposite.getInstance(type, alpha));
    }
}

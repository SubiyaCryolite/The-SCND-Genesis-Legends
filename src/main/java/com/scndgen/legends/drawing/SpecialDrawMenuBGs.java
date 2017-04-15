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

import io.github.subiyacryolite.enginev1.JenesisImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @Author: Ifunga Ndana
 * @Class: screenDrawer
 * This class draws nd manipulates all sprites, images and effects used in the game
 */
public class SpecialDrawMenuBGs extends JPanel {

    private final JenesisImageLoader imageLoader;
    private final BufferedImage bufferedImage;
    public Graphics2D g2d;

    public SpecialDrawMenuBGs() {
        imageLoader = new JenesisImageLoader();
        int x = (int) (Math.random() * 5);
        switch (x) {
            case 0:
                bufferedImage = imageLoader.loadBufferedImage("images/Story/blur/s4.png");
                break;
            case 1:
                bufferedImage = imageLoader.loadBufferedImage("images/Story/blur/s5.png");
                break;
            case 2:
                bufferedImage = imageLoader.loadBufferedImage("images/Story/blur/s6.png");
                break;
            case 3:
                bufferedImage = imageLoader.loadBufferedImage("images/Story/blur/s11.png");
                break;
            default:
                bufferedImage = imageLoader.loadBufferedImage("images/Story/blur/s6.png");
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
        g2d.drawImage(bufferedImage, 0, 0, this);
        g2d.setColor(Color.BLACK);
        g2d.setComposite(makeComposite(0.7f));
        g2d.fillRect(0, 0, 853, 480);
        g2d.setComposite(makeComposite(1.0f));
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

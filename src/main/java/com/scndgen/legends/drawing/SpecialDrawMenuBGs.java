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
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import javax.swing.*;

/**
 * @Author: Ifunga Ndana
 * @Class: screenDrawer
 * This class draws nd manipulates all sprites, images and effects used in the game
 */
public class SpecialDrawMenuBGs extends JPanel {

    private final Image bufferedImage;

    public SpecialDrawMenuBGs() {
        JenesisImageLoader imageLoader = new JenesisImageLoader();
        int x = (int) (Math.random() * 5);
        switch (x) {
            case 0:
                bufferedImage = imageLoader.loadImage("images/story/blur/s4.png");
                break;
            case 1:
                bufferedImage = imageLoader.loadImage("images/story/blur/s5.png");
                break;
            case 2:
                bufferedImage = imageLoader.loadImage("images/story/blur/s6.png");
                break;
            case 3:
                bufferedImage = imageLoader.loadImage("images/story/blur/s11.png");
                break;
            default:
                bufferedImage = imageLoader.loadImage("images/story/blur/s6.png");
                break;
        }
    }

    public void paintComponent(GraphicsContext gc, double x, double y) {
        gc.drawImage(bufferedImage, 0, 0);
        gc.setFill(Color.BLACK);
        gc.setGlobalAlpha((0.7f));
        gc.fillRect(0, 0, 853, 480);
        gc.setGlobalAlpha((1.0f));
    }
}

/**************************************************************************

 The SCND Genesis: Legends is a fighting game based on THE SCND GENESIS,
 a webcomic created by Ifunga Ndana ((([<a href="https://www.scndgen.com">https://www.scndgen.com</a>]))).

 The SCND Genesis: Legends RMX  © 2017 Ifunga Ndana.

 The SCND Genesis: Legends is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 The SCND Genesis: Legends is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with The SCND Genesis: Legends. If not, see <<a href="http://www.gnu.org/licenses/">http://www.gnu.org/licenses/</a>>.

 **************************************************************************/
package com.scndgen.legends.drawing;

import io.github.subiyacryolite.enginev1.Loader;
import javafx.scene.image.Image;

import javax.swing.*;

/**
 * @Author: Ifunga Ndana
 * @Class: screenDrawer
 * This class draws nd manipulates all sprites, images and effects used in the game
 */
public class BackgroundImages extends JPanel {

    private Image image;

    public BackgroundImages() {
        Loader loader = new Loader();
        int x = (int) (Math.random() * 5);
        switch (x) {
            case 0:
                image = loader.load("images/story/blur/s4.png");
                break;
            case 1:
                image = loader.load("images/story/blur/s5.png");
                break;
            case 2:
                image = loader.load("images/story/blur/s6.png");
                break;
            case 3:
                image = loader.load("images/story/blur/s11.png");
                break;
            default:
                image = loader.load("images/story/blur/s6.png");
                break;
        }
    }

    public Image getImage() {
        return image;
    }
}

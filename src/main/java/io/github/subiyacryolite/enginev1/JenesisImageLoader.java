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

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.VolatileImage;
import java.net.URL;

/**
 * Handles images application wide
 *
 * @author ndana 16-Apr-2011
 */
public class JenesisImageLoader {

    private int imageQual = Image.SCALE_SMOOTH;

    /**
     * Loads a an image from an image icon
     *
     * @param imageName - location of image
     * @return picture from image icon
     */
    public Image loadImageFromIcon(String imageName) {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            URL url = classLoader.getResource(imageName);
            return new ImageIcon(url).getImage();
        } catch (Exception e) {
            System.err.println(e); // keep for dev to see missing files
        }
        throw new IllegalArgumentException("Unable to load image: " + imageName);
    }

    /**
     * Loads image from toolkit
     *
     * @param imageName - location of image
     * @param h         - height of image
     * @param w         - width of image
     * @return toolkit image
     */
    public Image loadImage(String imageName, int w, int h) {
        try {
            ClassLoader cl = getClass().getClassLoader();
            URL url = cl.getResource(imageName);
            System.out.println("Processing imageLoader........" + imageName);
            int width = (int) (LoginScreen.getInstance().getGameXScale() * w);
            int height = (int) (LoginScreen.getInstance().getGameYScale() * h);
            return Toolkit.getDefaultToolkit().createImage(url).getScaledInstance(width, height, imageQual);
        } catch (Exception e) {
            System.err.println(e); // keep for dev to see missing files
        }
        throw new IllegalArgumentException("Unable to load image: " + imageName);
    }

    /**
     * Loads image from toolkit
     *
     * @param imageName - location of image
     * @return toolkit image
     */
    public BufferedImage loadBufferedImage(String imageName) {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            URL resource = classLoader.getResource(imageName);
            System.out.println("Processing imageLoader........" + imageName);
            return ImageIO.read(resource);
        } catch (Exception e) {
            System.err.println(e); // keep for dev to see missing files
        }
        throw new IllegalArgumentException("Unable to load image: " + imageName);
    }

    /**
     * Loads image from toolkit
     *
     * @param imageName - location of image
     * @return toolkit image
     */
    public Image loadImage(String imageName) {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            URL url = classLoader.getResource(imageName);
            System.out.println("Processing imageLoader........" + imageName);
            return Toolkit.getDefaultToolkit().createImage(url);
        } catch (Exception e) {
            System.err.println(e); // keep for dev to see missing files
        }
        throw new IllegalArgumentException("Unable to load image: " + imageName);
    }

    /**
     * Loads volatile images
     *
     * @param imageName - string location
     * @param heightB   - height of an image
     * @param widthB    - width of the image
     * @param obs       - an image observer
     * @return the image
     */
    public VolatileImage loadVolatileImage(String imageName, int widthB, int heightB, ImageObserver obs) {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            URL resource = classLoader.getResource(imageName);
            System.out.println("Processing imageLoader........" + imageName);
            GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsConfiguration graphicsConfiguration = graphicsEnvironment.getDefaultScreenDevice().getDefaultConfiguration();
            BufferedImage bufferedImage = ImageIO.read(resource);
            int width = bufferedImage.getWidth();
            int height = bufferedImage.getHeight();
            VolatileImage volatileImage = graphicsConfiguration.createCompatibleVolatileImage(width, height, bufferedImage.getTransparency());
            Graphics2D graphics2D = volatileImage.createGraphics();
            graphics2D.setComposite(AlphaComposite.Src);
            graphics2D.setColor(new Color(0, 0, 0, 0));
            graphics2D.fillRect(0, 0, width, height);
            graphics2D.drawImage(bufferedImage, 0, 0, obs);
            graphics2D.dispose();
            bufferedImage.flush();
            return volatileImage;
        } catch (Exception e) {
            System.err.println(e); // keep for dev to see missing files
        }
        throw new IllegalArgumentException("Unable to load image: " + imageName);
    }
}

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
package com.scndgen.legends.engine;

import com.scndgen.legends.LoginScreen;
import com.scndgen.legends.menus.CanvasStageSelect;

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
public class JenesisImage {

    private int imageQual = Image.SCALE_SMOOTH;

    public JenesisImage() {
    }

    /**
     * Loads a an image from an image icon
     *
     * @param imageName - location of image
     * @return picture from image icon
     */
    public Image loadImageFromIcon(String imageName) {
        try {
            ClassLoader cl = getClass().getClassLoader();
            URL url = cl.getResource(imageName);
            if (url != null) {
                return new ImageIcon(url).getImage();
                //return Toolkit.getDefaultToolkit().getImage(url);
            }
        } catch (Exception e) {
            System.err.println(e); // keep for dev to see missing files
        }

        throw new IllegalArgumentException("Unable to load image: " + imageName);
    }

    /**
     * Loads image from toolkit
     *
     * @param imageName - location of image
     * @param heightB   - height of image
     * @param widthB    - width of image
     * @return toolkit image
     */
    public Image loadImageFromToolkit(String imageName, int widthB, int heightB) {
        try {
            ClassLoader cl = getClass().getClassLoader();
            URL url = cl.getResource(imageName);
            if (url != null) {
                System.out.println("Processing pix........" + imageName);
                int picWidth = (int) (LoginScreen.getLoginScreen().getGameXScale() * widthB);
                int picHeight = (int) (LoginScreen.getLoginScreen().getGameYScale() * heightB);
                return Toolkit.getDefaultToolkit().createImage(url).getScaledInstance(picWidth, picHeight, imageQual);
            }
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
    public BufferedImage loadBufferedImageFromToolkit(String imageName) {
        try {
            ClassLoader cl = getClass().getClassLoader();
            URL url = cl.getResource(imageName);
            if (url != null) {
                System.out.println("Processing pix........" + imageName);
                return ImageIO.read(url);
            }
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
    public Image loadImageFromToolkitNoScale(String imageName) {
        try {
            ClassLoader cl = getClass().getClassLoader();
            URL url = cl.getResource(imageName);
            if (url != null) {
                System.out.println("Processing pix........" + imageName);
                return Toolkit.getDefaultToolkit().createImage(url);
            }
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
    public VolatileImage loadBImage2(String imageName, int widthB, int heightB, ImageObserver obs) {
        try {
            ClassLoader cl = getClass().getClassLoader();
            URL url = cl.getResource(imageName);
            if (url != null) {
                System.out.println("Processing pix........" + imageName);
                CanvasStageSelect.loadTxt("caching :: " + imageName);
                //return new ImageIcon( url ).getImage();

                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                GraphicsConfiguration gc = ge.getDefaultScreenDevice().getDefaultConfiguration();

                BufferedImage bi = ImageIO.read(url);
                int picWidth = bi.getWidth();
                int picHeight = bi.getHeight();
                //Image bi=Toolkit.getDefaultToolkit().createImage(url);
                //,Transparency.OPAQU
                //Optimized buffered image
                VolatileImage vimage = gc.createCompatibleVolatileImage(picWidth, picHeight, bi.getTransparency());
                Graphics2D gt = null;

                //try
                {
                    gt = vimage.createGraphics();
                    gt.setComposite(AlphaComposite.Src);
                    gt.setColor(new Color(0, 0, 0, 0));
                    gt.fillRect(0, 0, picWidth, picHeight);

                    gt.drawImage(bi, 0, 0, obs);
                }

                //finally
                {
                    bi = null;
                    gt.dispose();
                }

                return vimage;
            }

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
    public VolatileImage loadBImage(String imageName, int widthB, int heightB, ImageObserver obs) {
        try {
            ClassLoader cl = getClass().getClassLoader();
            URL url = cl.getResource(imageName);
            if (url != null) {
                System.out.println("Processing pix........" + imageName);
                CanvasStageSelect.loadTxt("caching :: " + imageName);
                //return new ImageIcon( url ).getImage();

                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                GraphicsConfiguration gc = ge.getDefaultScreenDevice().getDefaultConfiguration();

                BufferedImage bi = ImageIO.read(url);
                int picWidth = (int) (LoginScreen.getLoginScreen().getGameXScale() * widthB);
                int picHeight = (int) (LoginScreen.getLoginScreen().getGameYScale() * heightB);
                Image pic = bi.getScaledInstance(picWidth, picHeight, imageQual);
                //Image bi=Toolkit.getDefaultToolkit().createImage(url);
                //,Transparency.OPAQU
                //Optimized buffered image
                VolatileImage vimage = gc.createCompatibleVolatileImage(picWidth, picHeight, bi.getTransparency());
                Graphics2D graphics2D = null;

                //try
                {
                    graphics2D = vimage.createGraphics();
                    graphics2D.setComposite(AlphaComposite.Src);
                    graphics2D.setColor(new Color(0, 0, 0, 0));
                    graphics2D.fillRect(0, 0, picWidth, picHeight);

                    graphics2D.drawImage(pic, 0, 0, obs);
                }

                //finally
                {
                    bi = null;
                    graphics2D.dispose();
                }

                return vimage;
            }

        } catch (Exception e) {
            System.err.println(e); // keep for dev to see missing files
        }

        throw new IllegalArgumentException("Unable to load image: " + imageName);
    }

    /**
     * Loads an image icon
     *
     * @param imageName - the name of the image
     * @return the image icon
     */
    public ImageIcon loadIconImage(String imageName) {
        try {
            ClassLoader cl = getClass().getClassLoader();
            URL url = cl.getResource(imageName);
            if (url != null) {
                ImageIcon pic = new ImageIcon(url);
                return pic;
            }
        } catch (Exception e) {
            System.err.println(e); // keep for dev to see missing files
        }
        throw new IllegalArgumentException("Unable to load image: " + imageName);
    }
}

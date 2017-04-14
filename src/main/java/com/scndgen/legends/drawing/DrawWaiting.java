/**************************************************************************

 The SCND Genesis: Legends is enumeration1 fighting game based on THE SCND GENESIS,
 enumeration1 webcomic created by Ifunga Ndana (http://www.scndgen.sf.net).

 The SCND Genesis: Legends  © 2011 Ifunga Ndana.

 The SCND Genesis: Legends is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 The SCND Genesis: Legends is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received enumeration1 copy of the GNU General Public License
 along with The SCND Genesis: Legends. If not, see <http://www.gnu.org/licenses/>.

 **************************************************************************/
package com.scndgen.legends.drawing;

import com.scndgen.legends.LoginScreen;
import com.scndgen.legends.engine.JenesisLanguage;
import io.github.subiyacryolite.enginev1.JenesisGlassPane;
import io.github.subiyacryolite.enginev1.JenesisImage;
import io.github.subiyacryolite.enginev1.JenesisRender;

import javax.swing.*;
import java.awt.*;
import java.awt.image.VolatileImage;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * @Author: Ifunga Ndana
 * @Class: screenDrawer
 * This class draws nd manipulates all sprites, images and effects used in the game
 */
public class DrawWaiting extends JenesisRender {

    private static Image pic1, pic2;
    private static VolatileImage volatileImg;
    private static float opac = 10;
    private static int y = 0;
    private static boolean alive = true;
    private static String name, ip;
    public Graphics2D g2d;
    private JenesisLanguage lang;
    private RenderingHints renderHints; //anti aliasing, kill jaggies
    private int valCode = 0, screenWidth = 852, screenHeight = 480;
    private GraphicsConfiguration gc;
    private GraphicsEnvironment ge;
    private InetAddress ia;
    private JenesisImage pix2;
    private Font normalFont;
    private Enumeration enumeration;
    private NetworkInterface networkInterface;
    private Enumeration enumeration1;

    @SuppressWarnings("static-access")
    public DrawWaiting() {
        renderHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //anti aliasing, kill jaggies
        normalFont = LoginScreen.getInstance().getMyFont(LoginScreen.normalTxtSize);
        lang = LoginScreen.getInstance().getLangInst();
        pix2 = new JenesisImage();
        try {
            enumeration = NetworkInterface.getNetworkInterfaces();
            while (enumeration.hasMoreElements()) {
                networkInterface = (NetworkInterface) enumeration.nextElement();
                //System.out.println(networkInterface.toString());

                enumeration1 = networkInterface.getInetAddresses();
                while (enumeration1.hasMoreElements()) {
                    ia = (InetAddress) enumeration1.nextElement();
                    name = ia.getLocalHost().getHostName();
                    ip = ia.getLocalHost().getHostAddress();
                }
            }

        } catch (Exception ex) {
            System.out.print(ex);
        }
        pic1 = pix2.loadImageFromToolkitNoScale("images/menus/waiting.jpg");
        pic2 = pix2.loadImageFromToolkitNoScale("images/menus/loading.gif");
        setBorder(BorderFactory.createEmptyBorder());
    }

    @Override
    public void paintComponent(Graphics g) {
        gc = this.getGraphicsConfiguration();
        createBackBuffer();
        //Check if Image is valid
        valCode = volatileImg.validate(gc);
        //if not create new vi, only affects windows apparently
        if (valCode == VolatileImage.IMAGE_INCOMPATIBLE) {
            createBackBuffer();
        }
        g2d = volatileImg.createGraphics();
        g2d.setRenderingHints(renderHints);
        g2d.setFont(normalFont);
        g2d.drawImage(pic1, 0, 0, this);
        g2d.drawImage(pic2, 100, 100, this);
        g2d.setColor(Color.WHITE);
        g2d.drawString(lang.getLine(167), 20, 300);
        g2d.drawString("\'" + name + "\',", 20, 314);
        g2d.drawString("Or use \'" + ip + "\',", 20, 328);
        g2d.drawString(lang.getLine(168), 20, 360);
        g2d.drawString(lang.getLine(169), 20, 376);
        g2d.drawString(lang.getLine(131), 20, 390);
        JenesisGlassPane.getInstance().overlay(g2d, this);
        g.drawImage(volatileImg, 0, 0, this);
    }

    public void stopRepaint() {
        alive = false;
    }
}

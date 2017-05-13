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

import com.scndgen.legends.Language;
import com.scndgen.legends.LoginScreen;
import io.github.subiyacryolite.enginev1.Overlay;
import io.github.subiyacryolite.enginev1.ImageLoader;
import io.github.subiyacryolite.enginev1.Mode;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * @Author: Ifunga Ndana
 * @Class: screenDrawer
 * This class draws nd manipulates all sprites, images and effects used in the game
 */
public class LanHostWaitLobby extends Mode {

    private static float opac = 10;
    private static int y = 0;
    private static boolean alive = true;
    private static String name, ip;
    private Image pic1, pic2;
    private InetAddress ia;
    private ImageLoader imageLoader;
    private Font normalFont;
    private Enumeration enumeration;
    private NetworkInterface networkInterface;
    private Enumeration enumeration1;

    public LanHostWaitLobby() {
        normalFont = getMyFont(LoginScreen.normalTxtSize);
        imageLoader = new ImageLoader();
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
        pic1 = imageLoader.loadImage("images/menus/waiting.jpg");
        pic2 = imageLoader.loadImage("images/menus/loading.gif");
    }

    @Override
    public void newInstance() {

    }

    @Override
    public void loadAssetsIml() {

    }

    @Override
    public void cleanAssets() {

    }

    @Override
    public void render(GraphicsContext gc, double x, double y) {
        gc.setFont(normalFont);
        gc.drawImage(pic1, 0, 0);
        gc.drawImage(pic2, 100, 100);
        gc.setFill(Color.WHITE);
        gc.fillText(Language.getInstance().get(167), 20, 300);
        gc.fillText("\'" + name + "\',", 20, 314);
        gc.fillText("Or use \'" + ip + "\',", 20, 328);
        gc.fillText(Language.getInstance().get(168), 20, 360);
        gc.fillText(Language.getInstance().get(169), 20, 376);
        gc.fillText(Language.getInstance().get(131), 20, 390);
        Overlay.getInstance().overlay(gc,x,y);
    }

    public void stopRepaint() {
        alive = false;
    }
}

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

import java.awt.*;
import java.awt.image.ImageObserver;

/**
 * From page 226 Filthy Rich Clients
 *
 * @author ndana
 */
// Second technique: JPanel
public class JenesisGlassPane {

    private static JenesisGlassPane instance;
    private String sysNot = "", sysNot2 = "";
    private float sysNotOpac = 0.0f, sysNotOpac2 = 0.0f;
    private int gameWidth = 0;
    private float opacShow, opacShow2;
    private boolean opacUpb, opacShowb, opacUpb2, opacShowb2;

    private JenesisGlassPane() {
        gameWidth = LoginScreen.getGameWidth();
    }

    public static synchronized JenesisGlassPane getInstance() {
        if (instance == null)
            instance = new JenesisGlassPane();
        return instance;
    }

    /**
     * Adds overlay to drawing commands, place at bottom
     *
     * @param here, the Graphics2D object
     * @param obs,  the image observer object
     */
    public void overlay(Graphics2D here, ImageObserver obs) {
        here.setColor(Color.black);
        if (opacUpb && sysNotOpac < 0.99f) {//fade up
            sysNotOpac = sysNotOpac + 0.01f;
        } else {
            opacUpb = false;
            opacShowb = true;
        }
        if (opacUpb == false && opacShowb) {
            if (opacShow < (180.0f)) {//show for 3 seconds
                opacShow = opacShow + 1.0f;
            } else if (sysNotOpac > 0.01f) {
                sysNotOpac = sysNotOpac - 0.01f;
            }
        }
        here.setComposite(makeComposite(sysNotOpac / 2.5f));
        here.fillRoundRect((gameWidth - 5 - 5 - here.getFontMetrics().stringWidth(sysNot)), 55, 14 + (sysNot.length() * 8), 20, 10, 10);
        here.setComposite(makeComposite(sysNotOpac));
        here.setColor(Color.white);
        here.drawString(sysNot, (gameWidth - 5 - here.getFontMetrics().stringWidth(sysNot)), 70);
        here.setComposite(makeComposite(1.0f));

        if (opacUpb2 && sysNotOpac2 < 0.99f) {//fade up
            sysNotOpac2 = sysNotOpac2 + 0.01f;
        } else {
            opacUpb2 = false;
            opacShowb2 = true;
        }
        if (opacUpb2 == false && opacShowb2) {
            if (opacShow2 < (360.0f)) {//show for 6 seconds
                opacShow2 = opacShow2 + 1.0f;
            } else if (sysNotOpac2 > 0.01f) {
                sysNotOpac2 = sysNotOpac2 - 0.01f;
            }
        }
        here.setColor(Color.black);
        here.setComposite(makeComposite(sysNotOpac2 / 2.5f));
        here.fillRoundRect((gameWidth - 5 - 5 - here.getFontMetrics().stringWidth(sysNot2)), 35, 14 + (sysNot2.length() * 8), 20, 10, 10);
        here.setComposite(makeComposite(sysNotOpac2));
        here.setColor(Color.white);
        here.drawString(sysNot2, (gameWidth - 5 - here.getFontMetrics().stringWidth(sysNot2)), 50);
        here.setComposite(makeComposite(1.0F));
    }

    /**
     * Transparency
     * e.g AlphaComposite(10*0.1f)
     *
     * @param alpha, value from 10 to 0
     * @return alpha composite
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

    /**
     * Shows notice in overlay
     *
     * @param message to display
     */
    public void systemNotice(String message) {
        //new message
        sysNot = message;
        opacUpb = true;
        opacShowb = false;

        sysNotOpac = 0.0f;
        opacShow = 0.0f;
    }

    /**
     * Shows notice in overlay
     *
     * @param message to display
     */
    public void systemNotice2(String message) {
        //new message
        sysNot2 = message;
        opacUpb2 = true;
        opacShowb2 = false;
        sysNotOpac2 = 0.0f;
        opacShow2 = 0.0f;
    }
}

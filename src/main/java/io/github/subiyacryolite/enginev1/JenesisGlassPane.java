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
public class JenesisGlassPane {

    private static JenesisGlassPane instance;
    private String primaryNotification = "", secondaryNotification = "";
    private float primaryOpacity = 0.0f, secondaryOpacity = 0.0f;
    private int gameWidth = 0;
    private float primaryTimeout, secondaryTimeout;
    private boolean increasePrimaryOpacity, fadeOutPrimaryNotification, increaseSecondaryOpacity, fadeOutSecondaryNotification;

    private JenesisGlassPane() {
        gameWidth = LoginScreen.getGameWidth();
    }

    public static synchronized JenesisGlassPane getInstance() {
        if (instance == null)
            instance = new JenesisGlassPane();
        return instance;
    }

    /**
     * Adds overlay to drawing commands, overlay at bottom
     *
     * @param graphics2D,    the Graphics2D object
     * @param imageObserver, the image observer object
     */
    public void overlay(Graphics2D graphics2D, ImageObserver imageObserver) {
        graphics2D.setColor(Color.black);
        if (increasePrimaryOpacity && primaryOpacity < 0.99f) {//fade up
            primaryOpacity = primaryOpacity + 0.01f;
        } else {
            increasePrimaryOpacity = false;
            fadeOutPrimaryNotification = true;
        }
        if (increasePrimaryOpacity == false && fadeOutPrimaryNotification) {
            if (primaryTimeout < (180.0f)) {//show for 3 seconds
                primaryTimeout = primaryTimeout + 1.0f;
            } else if (primaryOpacity > 0.01f) {
                primaryOpacity = primaryOpacity - 0.01f;
            }
        }
        graphics2D.setComposite(makeComposite(primaryOpacity / 2.5f));
        graphics2D.fillRoundRect((gameWidth - 5 - 5 - graphics2D.getFontMetrics().stringWidth(primaryNotification)), 55, 14 + (primaryNotification.length() * 8), 20, 10, 10);
        graphics2D.setComposite(makeComposite(primaryOpacity));
        graphics2D.setColor(Color.white);
        graphics2D.drawString(primaryNotification, (gameWidth - 5 - graphics2D.getFontMetrics().stringWidth(primaryNotification)), 70);
        graphics2D.setComposite(makeComposite(1.0f));

        if (increaseSecondaryOpacity && secondaryOpacity < 0.99f) {//fade up
            secondaryOpacity = secondaryOpacity + 0.01f;
        } else {
            increaseSecondaryOpacity = false;
            fadeOutSecondaryNotification = true;
        }
        if (increaseSecondaryOpacity == false && fadeOutSecondaryNotification) {
            if (secondaryTimeout < (360.0f)) {//show for 6 seconds
                secondaryTimeout = secondaryTimeout + 1.0f;
            } else if (secondaryOpacity > 0.01f) {
                secondaryOpacity = secondaryOpacity - 0.01f;
            }
        }
        graphics2D.setColor(Color.black);
        graphics2D.setComposite(makeComposite(secondaryOpacity / 2.5f));
        graphics2D.fillRoundRect((gameWidth - 5 - 5 - graphics2D.getFontMetrics().stringWidth(secondaryNotification)), 35, 14 + (secondaryNotification.length() * 8), 20, 10, 10);
        graphics2D.setComposite(makeComposite(secondaryOpacity));
        graphics2D.setColor(Color.white);
        graphics2D.drawString(secondaryNotification, (gameWidth - 5 - graphics2D.getFontMetrics().stringWidth(secondaryNotification)), 50);
        graphics2D.setComposite(makeComposite(1.0F));
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
    public void primaryNotice(String message) {
        primaryNotification = message;
        increasePrimaryOpacity = true;
        fadeOutPrimaryNotification = false;
        primaryOpacity = 0.0f;
        primaryTimeout = 0.0f;
    }

    /**
     * Shows notice in overlay
     *
     * @param message to display
     */
    public void secondaryNotice(String message) {
        secondaryNotification = message;
        increaseSecondaryOpacity = true;
        fadeOutSecondaryNotification = false;
        secondaryOpacity = 0.0f;
        secondaryTimeout = 0.0f;
    }
}

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

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import static com.sun.javafx.tk.Toolkit.getToolkit;


/**
 * From page 226 Filthy Rich Clients
 *
 * @author ndana
 */
public class JenesisGlassPane {

    private static JenesisGlassPane instance;
    private String primaryNotification = "", secondaryNotification = "";
    private float primaryOpacity = 0.0f, secondaryOpacity = 0.0f;
    private float primaryTimeout, secondaryTimeout;
    private boolean increasePrimaryOpacity, fadeOutPrimaryNotification, increaseSecondaryOpacity, fadeOutSecondaryNotification;

    private JenesisGlassPane() {
    }

    public static synchronized JenesisGlassPane getInstance() {
        if (instance == null)
            instance = new JenesisGlassPane();
        return instance;
    }

    /**
     * Adds overlay to drawing commands, overlay at bottom
     *
     * @param gc, the Graphics2D object
     */
    public void overlay(GraphicsContext gc, double w, double h) {
        gc.setFill(Color.BLACK);
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
        gc.setGlobalAlpha((primaryOpacity / 2.5f));
        gc.fillRoundRect((w - 5 - 5 - getToolkit().getFontLoader().computeStringWidth(primaryNotification, gc.getFont())), 55, 14 + (primaryNotification.length() * 8), 20, 10, 10);
        gc.setGlobalAlpha((primaryOpacity));
        gc.setFill(Color.WHITE);
        gc.fillText(primaryNotification, (w - 5 - getToolkit().getFontLoader().computeStringWidth(primaryNotification, gc.getFont())), 70);
        gc.setGlobalAlpha((1.0f));

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
        gc.setFill(Color.BLACK);
        gc.setGlobalAlpha((secondaryOpacity / 2.5f));
        gc.fillRoundRect((w - 5 - 5 - getToolkit().getFontLoader().computeStringWidth(secondaryNotification, gc.getFont())), 35, 14 + (secondaryNotification.length() * 8), 20, 10, 10);
        gc.setGlobalAlpha((secondaryOpacity));
        gc.setFill(Color.WHITE);
        gc.fillText(secondaryNotification, (w - 5 - getToolkit().getFontLoader().computeStringWidth(secondaryNotification, gc.getFont())), 50);
        gc.setGlobalAlpha((1.0F));
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

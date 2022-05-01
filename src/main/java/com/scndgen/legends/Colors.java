/**************************************************************************

 The SCND Genesis: Legends is a fighting game based on THE SCND GENESIS,
 a webcomic created by Ifunga Ndana ((([<a href="http://www.scndgen.com">http://www.scndgen.com</a>]))).

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
package com.scndgen.legends;

import java.awt.*;

/**
 * @author ndana
 */
public class Colors {

    private static Color thisColor = new Color(0, 0, 0);

    public static Color getColor(String color) {

        if (color.equalsIgnoreCase("blue")) {
            thisColor = new Color(117, 227, 226);
        }

        if (color.equalsIgnoreCase("green")) {
            thisColor = new Color(99, 232, 63);
        }

        if (color.equalsIgnoreCase("yellow")) {
            thisColor = new Color(255, 255, 0);
        }

        if (color.equalsIgnoreCase("white")) {
            thisColor = new Color(255, 255, 255);
        }

        if (color.equalsIgnoreCase("red")) {
            thisColor = new Color(253, 20, 9);
        }

        return thisColor;
    }
}

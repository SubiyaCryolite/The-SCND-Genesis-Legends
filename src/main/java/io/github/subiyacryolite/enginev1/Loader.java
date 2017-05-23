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

import javafx.scene.image.Image;

/**
 * Handles images application wide
 *
 * @author ndana 16-Apr-2011
 */
public class Loader {

    /**
     * Loads image from toolkit
     *
     * @param url location of image
     * @return image
     */
    public Image load(String url) {
        try {
            return new Image(url);
        } catch (Exception e) {
            throw new IllegalArgumentException("Unable to load image: " + url);
        }
    }
}

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
package com.scndgen.legends.ui;

/**
 * @author ndana
 */
public class Event {

    public UiItem source;

    public Event() {
    }

    /**
     * What happens when this itemAttacks is clicked/selected
     */
    public void onAccept() {
    }

    /**
     * What happens when this itemAttacks is clicked/selected (ALTERNATE FIRE)
     */
    public void onAcceptAction2() {
    }

    /**
     * What happens when this itemAttacks is unclicked/canceled
     */
    public void onBackCancel() {
    }

    /**
     * What happens when this itemAttacks is isHovered/active
     */
    public void onHover() {
    }

    /**
     * What happens when this itemAttacks is uiLeft/made non active
     */
    public void onLeave() {
    }

    public void onUp() {
    }

    public void onDown() {
    }

    public void onLeft() {
    }

    public void onRight() {
    }
}
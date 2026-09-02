/**************************************************************************

 The SCND Genesis: Legends is a fighting game based on THE SCND GENESIS,
 a webcomic created by Ifunga Ndana (https://www.scndgen.com).

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
 along with The SCND Genesis: Legends. If not, see <https://www.gnu.org/licenses/>.

 **************************************************************************/
package com.scndgen.legends.ui;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * @author ndana
 */
public abstract class Event {

    public UiItem source;

    /**
     * Handle a control or focus action for {@link #source}.
     */
    public abstract void on(UiAction action);

    /**
     * Listener that runs only for a single action (typical hover-only tiles).
     */
    public static Event on(UiAction type, Runnable handler) {
        Objects.requireNonNull(type, "type");
        Objects.requireNonNull(handler, "handler");
        return new Event() {
            @Override
            public void on(UiAction action) {
                if (action == type) {
                    handler.run();
                }
            }
        };
    }

    /**
     * Listener that receives every action; use a switch in the consumer.
     */
    public static Event of(Consumer<UiAction> handler) {
        Objects.requireNonNull(handler, "handler");
        return new Event() {
            @Override
            public void on(UiAction action) {
                handler.accept(action);
            }
        };
    }
}

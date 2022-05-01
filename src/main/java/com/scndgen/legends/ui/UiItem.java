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
package com.scndgen.legends.ui;

import java.util.ArrayList;
import java.util.List;

/**
 * @author indana
 */
public class UiItem {
    protected UiItem up, down, left, right;
    private int tagInt;
    private Object tagObject;
    private String tagString, name;
    private boolean selectable, hovered;
    protected final List<Event> events = new ArrayList<>();

    /**
     * Set getInfo of the object
     *
     * @param name getInfo
     */
    public void getName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isHovered() {
        return hovered;
    }

    public void addJenesisEvent(Event event) {
        event.source = this;
        event.source.isSelectable(true);
        events.add(event);
    }

    public void removeJenesisEvent(Event event) {
        event.source = null;
        events.remove(event);
    }

    public final void hover() {
        hovered = true;
        events.stream().forEach((event) -> {
            event.onHover();
        });
    }

    public final void leave() {
        hovered = false;
        events.stream().forEach((event) -> {
            event.onLeave();
        });
    }

    public final void accept() {
        events.stream().forEach((event) -> {
            event.onAccept();
        });
    }

    public final void backCancel() {
        events.stream().forEach((event) -> {
            event.onBackCancel();
        });
    }

    public final void left() {
        events.stream().forEach((event) -> {
            event.onLeft();
        });
    }

    public final void right() {
        events.stream().forEach((event) -> {
            event.onRight();
        });
    }

    public final void up() {
        events.stream().forEach((event) -> {
            event.onUp();
        });
    }

    public final void down() {
        events.stream().forEach((event) -> {
            event.onDown();
        });
    }

    public boolean isSelectable() {
        return selectable;
    }

    public void isSelectable(boolean val) {
        selectable = val;
    }

    public final UiItem getUp() {
        if (up != null) {
            return up;
        }
        return this;
    }

    public final UiItem getDown() {
        if (down != null) {
            return down;
        }
        return this;
    }

    public final UiItem getLeft() {
        if (left != null) {
            return left;
        }
        return this;
    }

    public final UiItem getRight() {
        if (right != null) {
            return right;
        }
        return this;
    }

    public final void setRelatives(final UiItem up, UiItem down, UiItem left, UiItem right) {
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
    }

    public final void setUp(final UiItem up) {
        this.up = up;
        up.isSelectable(true);
        if (up.getDown() == up) {
            up.setDown(this);
        }
    }

    public final void setDown(final UiItem down) {
        this.down = down;
        down.isSelectable(true);
        if (down.getUp() == down) {
            down.setUp(this);
        }
    }

    public final void setLeft(final UiItem left) {
        this.left = left;
        left.isSelectable(true);
        if (left.getRight() == left) {
            left.setRight(this);
        }
    }

    public final void setRight(final UiItem right) {
        this.right = right;
        right.isSelectable(true);
        if (right.getLeft() == right) {
            right.setLeft(this);
        }
    }

    public void tagInt(int tag) {
        tagInt = tag;
    }

    public int tagInt() {
        return tagInt;
    }

    public final void tagString(String tag) {
        tagString = tag;
    }

    public final String tagString() {
        return tagString;
    }

    public final void tagObject(Object tag) {
        tagObject = tag;
    }

    public final Object tagObject() {
        return tagObject;
    }
}

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
        isSelectable(true);
        events.add(event);
    }

    public void removeJenesisEvent(Event event) {
        event.source = null;
        events.remove(event);
    }

    public final void dispatch(UiAction action) {
        switch (action) {
            case HOVER -> hovered = true;
            case LEAVE -> hovered = false;
            default -> {
            }
        }
        events.forEach(event -> event.on(action));
    }

    public final void hover() {
        dispatch(UiAction.HOVER);
    }

    public final void leave() {
        dispatch(UiAction.LEAVE);
    }

    public final void accept() {
        dispatch(UiAction.ACCEPT);
    }

    public final void backCancel() {
        dispatch(UiAction.BACK_CANCEL);
    }

    public final void left() {
        dispatch(UiAction.LEFT);
    }

    public final void right() {
        dispatch(UiAction.RIGHT);
    }

    public final void up() {
        dispatch(UiAction.UP);
    }

    public final void down() {
        dispatch(UiAction.DOWN);
    }

    public boolean isSelectable() {
        return selectable;
    }

    public void isSelectable(boolean val) {
        selectable = val;
    }

    public final UiItem getUp() {
        return up != null ? up : this;
    }

    public final UiItem getDown() {
        return down != null ? down : this;
    }

    public final UiItem getLeft() {
        return left != null ? left : this;
    }

    public final UiItem getRight() {
        return right != null ? right : this;
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

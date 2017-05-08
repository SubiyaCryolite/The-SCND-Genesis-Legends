package com.scndgen.legends.ui;

/**
 *
 * @author indana
 */
public class UiElement {

    private boolean hovered;
    private UiElement left;
    private UiElement right;
    private UiElement up;
    private UiElement down;
    private UiElement back;

    public final void setBack(UiElement value) {
        back = value;
    }

    public final void setUp(UiElement value) {
        up = value;
    }

    public final void setDown(UiElement value) {
        down = value;
    }

    public final void setLeft(UiElement value) {
        left = value;
    }

    public final void setRight(UiElement value) {
        right = value;
    }

    public final boolean isHovered() {
        return hovered;
    }

    public final void setHovered(boolean value) {
        hovered = value;
        if (hovered) {
            onHover();
        }
    }

    ///////////////events
    public void onHover() {
    }

    public void onLeave() {
    }

    public void onActionPrimary() {
    }

    public void onActionSecondary() {
    }

    public void onCancelPrimary() {
    }

    public void onCancelSecondary() {
    }

    public final void onLeft() {
        if (left == null) {
            return;
        }
        this.onLeave();
        left.setHovered(true);
    }

    public final void onRight() {
        if (right == null) {
            return;
        }
        this.onLeave();
        right.setHovered(true);
    }

    public final void onUp() {
        if (up == null) {
            return;
        }
        this.onLeave();
        up.setHovered(true);
    }

    public final void onDown() {
        if (down == null) {
            return;
        }
        this.onLeave();
        down.setHovered(true);
    }
    
    public final void onBack() {
        if (back == null) {
            return;
        }
        this.onLeave();
        back.setHovered(true);
    }
}

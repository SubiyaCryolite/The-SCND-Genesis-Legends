package io.github.subiyacryolite.enginev1;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;


/**
 * Created by ifunga on 14/04/2017.
 */
public abstract class JenesisMode {

    protected int screenWidth = 852;
    protected int screenHeight = 480;
    protected float opacity;
    protected boolean loadAssets = true;
    protected long lastDelta;
    protected long diff;
    protected boolean isPaused;
    private long accumulator16ms;
    private long accumulator33ms;

    /**
     * System notice in overlay
     *
     * @param message - the message to display
     */
    public final void primaryNotice(String message) {
        JenesisOverlay.getInstance().primaryNotice(message);
    }

    /**
     * System notice in overlay
     *
     * @param message - the message to display
     */
    public final void secondaryNotice(String message) {
        JenesisOverlay.getInstance().secondaryNotice(message);
    }

    public void onLeft() {
    }

    public void onRight() {
    }

    public void onUp() {
    }

    public void onDown() {
    }

    public void onAccept() {
    }

    public void onBackCancel() {
    }

    public void onTogglePause()
    {}

    public abstract void render(final GraphicsContext gc, final double w, final double h);

    public final void update(final long delta) {
        lastDelta = delta;
        diff = lastDelta == 0 ? 0 : delta - lastDelta;
        accumulator16ms += delta;
        accumulator33ms += delta;
        logic(delta);
    }

    protected void logic(final long delta) {
    }

    public void keyReleased(final KeyEvent keyEvent) {

    }

    public void keyPressed(final KeyEvent keyEvent) {

    }

    public void mouseMoved(final MouseEvent mouseEvent) {

    }

    public void mouseClicked(final MouseEvent mouseEvent) {

    }

    protected boolean isDelta60fps() {
        if (accumulator16ms >= 1.6e+7) {
            accumulator16ms = 0;
            return true;
        }
        return false;
    }

    protected boolean isDelta30fps() {
        if (accumulator33ms >= 1.6e+7) {
            accumulator33ms = 0;
            return true;
        }
        return false;
    }

    public abstract void newInstance();

    public abstract void loadAssets();

    public abstract void cleanAssets();

    public Font getMyFont(float size) {
        try {
            return Font.loadFont(getClass().getResourceAsStream("font/Sawasdee.ttf"), size);
        } catch (Exception re) {
            return new javafx.scene.text.Font("Sans", size);
        }
    }
}

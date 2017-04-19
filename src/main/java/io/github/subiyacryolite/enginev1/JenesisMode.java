package io.github.subiyacryolite.enginev1;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.awt.image.ImageObserver;

/**
 * Created by ifunga on 14/04/2017.
 */
public abstract class JenesisMode  {

    protected int screenWidth = 852;
    protected int screenHeight = 480;
    protected float opacity;
    protected boolean loadAssets = true;

    /**
     * System notice in overlay
     *
     * @param message - the message to display
     */
    public final void primaryNotice(String message) {
        JenesisGlassPane.getInstance().primaryNotice(message);
    }

    /**
     * System notice in overlay
     *
     * @param message - the message to display
     */
    public final void secondaryNotice(String message) {
        JenesisGlassPane.getInstance().secondaryNotice(message);
    }

    /**
     * Transparency
     * e.g AlphaComposite(10*0.1f)
     *
     * @param alpha, value from 10 to 0
     * @return
     */
    public final AlphaComposite makeComposite(float alpha) {
        int type = AlphaComposite.SRC_OVER;
        if (alpha >= 0.0f && alpha <= 1.0f) {
            //nothing
        } else {
            alpha = 0.0f;
        }
        return (AlphaComposite.getInstance(type, alpha));
    }


    /**
     * Gets screenshot
     */
    public final void captureScreenShot() {

    }

    public void moveLeft() {
    }

    public void moveRight() {
    }

    public void moveUp() {
    }

    public void moveDown() {
    }

    public void render(final GraphicsContext gc, final double w, final double h) {
    }

    public final void update(final long delta) {
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

    protected void isDelta60fps(long value)
    {}

    protected void isDelta30fps(long value)
    {}

    public abstract void newInstance();

    public abstract void loadAssets();

    public abstract void cleanAssets();

    public abstract void paintComponent(Graphics2D g2d, ImageObserver io);
}

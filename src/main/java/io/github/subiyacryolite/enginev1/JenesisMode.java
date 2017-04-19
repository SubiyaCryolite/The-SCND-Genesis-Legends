package io.github.subiyacryolite.enginev1;

import com.scndgen.legends.Language;
import com.scndgen.legends.LoginScreen;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.io.File;

/**
 * Created by ifunga on 14/04/2017.
 */
public abstract class JenesisMode extends JPanel {

    protected final RenderingHints renderHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //anti aliasing, kill jaggies
    protected VolatileImage volatileImage;
    protected GraphicsEnvironment ge;
    protected GraphicsConfiguration gc;
    protected Graphics2D g2d;
    protected int screenWidth = 852;
    protected int screenHeight = 480;
    protected float opacity;
    protected boolean loadAssets = true;

    /**
     * System notice in overlay
     *
     * @param message - the message to display
     */
    public final void systemNotice(String message) {
        JenesisGlassPane.getInstance().systemNotice(message);
    }

    /**
     * System notice in overlay
     *
     * @param message - the message to display
     */
    public final void systemNotice2(String message) {
        JenesisGlassPane.getInstance().systemNotice2(message);
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
     * Hardware acceleration
     */
    protected final void createBackBuffer() {
        if (volatileImage == null) {
            ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            System.out.println("Accelerateable memory!!!!!!!!!!! " + ge.getDefaultScreenDevice().getAvailableAcceleratedMemory());
            gc = ge.getDefaultScreenDevice().getDefaultConfiguration();
            volatileImage = gc.createCompatibleVolatileImage(LoginScreen.getInstance().getGameWidth(), LoginScreen.getInstance().getGameHeight());
            volatileImage.setAccelerationPriority(1.0f);
            g2d = volatileImage.createGraphics();
            g2d.setRenderingHints(renderHints); //activate aliasing
        }
    }

    /**
     * Gets screenshot
     */
    public final void captureScreenShot() {
        try {
            BufferedImage bufferedImage = volatileImage.getSnapshot();
            File file;
            if (!new File(System.getProperty("user.home") + File.separator + ".config" + File.separator + "scndgen" + File.separator + "screenshots").exists())
                new File(System.getProperty("user.home") + File.separator + ".config" + File.separator + "scndgen" + File.separator + "screenshots").mkdirs();
            file = new File(System.getProperty("user.home") + File.separator + ".config" + File.separator + "scndgen" + File.separator + "screenshots" + File.separator + generateUID() + ".png");
            if (ImageIO.write(bufferedImage, "png", file))
                systemNotice(Language.getInstance().getLine(170));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Generates unique ID for screens
     *
     * @return unique ID
     */
    public final String generateUID() {
        String random_name = "scndgen-legends_";
        StringBuilder userIDBuff = new StringBuilder(random_name);
        String[] letters = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
        userIDBuff.append("").append(Math.round(Math.random() * 100)).append("_");
        int v1 = Integer.parseInt("" + Math.round((Math.random() * 24) + 1));
        int v2 = Integer.parseInt("" + Math.round((Math.random() * 24) + 1));
        int v3 = Integer.parseInt("" + Math.round((Math.random() * 24) + 1));
        userIDBuff.append(letters[v1]);
        userIDBuff.append(letters[v2]);
        userIDBuff.append(letters[v3]);
        userIDBuff.append("_").append(Math.round(Math.random() * 10000)).append("");
        random_name = userIDBuff.toString();

        return random_name;
    }

    @Override
    public final Dimension getPreferredSize() {
        return new Dimension(screenWidth, screenHeight); //480p, 16:9 widescreen enhanced definition, max resolution of Nintendo Wii too :D
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

    public void update(final long delta) {
    }

    public void keyReleased(final KeyEvent keyEvent) {

    }

    public void keyPressed(final KeyEvent keyEvent) {

    }

    public void mouseMoved(final MouseEvent mouseEvent) {

    }

    public void mouseClicked(final MouseEvent mouseEvent) {

    }
}

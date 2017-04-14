package io.github.subiyacryolite.enginev1;

import com.scndgen.legends.LoginScreen;
import com.scndgen.legends.arefactored.mode.StandardGameplay;
import com.scndgen.legends.engine.JenesisLanguage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.io.File;

/**
 * Created by ifung on 14/04/2017.
 */
public abstract class JenesisMode extends JPanel {

    protected final RenderingHints renderHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //anti aliasing, kill jaggies
    protected VolatileImage volatileImg;
    protected GraphicsEnvironment ge;
    protected GraphicsConfiguration gc;
    protected Graphics2D g2d;
    protected int screenWidth = 852;
    protected int screenHeight = 480;
    protected float opacity;

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
        if (volatileImg == null) {
            ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            System.out.println("Accelerateable memory!!!!!!!!!!! " + ge.getDefaultScreenDevice().getAvailableAcceleratedMemory());
            gc = ge.getDefaultScreenDevice().getDefaultConfiguration();
            volatileImg = gc.createCompatibleVolatileImage(LoginScreen.getInstance().getGameWidth(), LoginScreen.getInstance().getGameHeight());
            volatileImg.setAccelerationPriority(1.0f);
            g2d = volatileImg.createGraphics();
            g2d.setRenderingHints(renderHints); //activate aliasing
        }
    }

    /**
     * Gets screenshot
     */
    public final void captureScreenShot() {
        try {
            BufferedImage bufferedImage = volatileImg.getSnapshot();
            File file;
            if (!new File(System.getProperty("user.home") + File.separator + ".config" + File.separator + "scndgen" + File.separator + "screenshots").exists()) {
                new File(System.getProperty("user.home") + File.separator + ".config" + File.separator + "scndgen" + File.separator + "screenshots").mkdirs();
            }
            file = new File(System.getProperty("user.home") + File.separator + ".config" + File.separator + "scndgen" + File.separator + "screenshots" + File.separator + StandardGameplay.generateUID() + ".png");
            if (ImageIO.write(bufferedImage, "png", file))
                systemNotice(JenesisLanguage.getInstance().getLine(170));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
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
}

package com.scndgen.legends.windows;

import com.scndgen.legends.GamePadController;
import com.scndgen.legends.LoginScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.VolatileImage;
import java.util.ArrayList;

/**
 * Created by ifunga on 12/04/2017.
 */
public class Base extends JFrame implements KeyListener, WindowListener, MouseListener, MouseMotionListener, MouseWheelListener {

    private final RenderingHints renderHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //anti aliasing, kill jaggies
    protected Graphics2D g2d;
    private boolean runNew;
    private GraphicsEnvironment ge;
    private GraphicsConfiguration gc;
    private VolatileImage volatileImg;
    private boolean doneChilling;
    private ArrayList imageList;

    /**
     * Hardware acceleration
     */
    private void createBackBuffer() {
        if (runNew) {
            ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            System.out.println("Accelerateable memory!!!!!!!!!!! " + ge.getDefaultScreenDevice().getAvailableAcceleratedMemory());
            gc = ge.getDefaultScreenDevice().getDefaultConfiguration();
            volatileImg = gc.createCompatibleVolatileImage(LoginScreen.getInstance().getGameWidth(), LoginScreen.getInstance().getGameHeight());
            volatileImg.setAccelerationPriority(1.0f);
            g2d = volatileImg.createGraphics();
            g2d.setRenderingHints(renderHints); //activate aliasing
            runNew = false;
        }
    }

    public void paintComponent(Graphics g) {
        createBackBuffer();
        paint();
    }

    public void paint() {
    }

    public void sytemNotice(String message) {
    }

    /**
     * Responsible for latency in game menus(controller)
     */
    protected void menuLatency() {
        new Thread() {

            @Override
            @SuppressWarnings("static-access")
            public void run() {
                try {
                    doneChilling = false;
                    this.sleep(166);
                    doneChilling = true;
                } catch (Exception e) {
                }
            }
        }.start();
    }

    /**
     * Vibrate
     */
    protected void quickVibrate(float strength, int length) {
        final float power = strength;
        final int time = length;
        new Thread() {

            @SuppressWarnings("static-access")
            @Override
            public void run() {
                try {
                    GamePadController.getInstance().setRumbler(true, power);
                    this.sleep(time);
                    GamePadController.getInstance().setRumbler(false, 0.0f);
                } catch (Exception e) {
                }
            }
        }.start();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}

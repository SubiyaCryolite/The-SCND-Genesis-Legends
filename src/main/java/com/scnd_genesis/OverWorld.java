/**************************************************************************

 The SCND Genesis: Legends is a fighting game based on THE SCND GENESIS,
 a webcomic created by Ifunga Ndana (http://www.scndgen.sf.net).

 The SCND Genesis: Legends  © 2011 Ifunga Ndana.

 The SCND Genesis: Legends is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 The SCND Genesis: Legends is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with The SCND Genesis: Legends. If not, see <http://www.gnu.org/licenses/>.

 **************************************************************************/
package com.scnd_genesis;

import com.scnd_genesis.drawing.DrawOverworld;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class OverWorld extends JFrame implements ActionListener, KeyListener {

    private static DrawOverworld map;
    private static OverWorld world;
    private JFrame daWindow;

    @SuppressWarnings("LeakingThisInConstructor")
    public OverWorld() {
        map = new DrawOverworld(this);
        daWindow = new JFrame();
        daWindow.setTitle("SCND GENESIS: LEGENDS - Overworld");
        daWindow.add(map);
        daWindow.setUndecorated(true);
        daWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        daWindow.pack();
        daWindow.requestFocusInWindow();
        daWindow.setFocusable(true);
        daWindow.addKeyListener(this);
        daWindow.setLocationRelativeTo(null); // Centers JFrame on screen //
        daWindow.setResizable(false);
        daWindow.setVisible(true);
    }

    public static void main(String[] args) {
        world = new OverWorld();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_RIGHT) {
            map.moveRight();
        }

        if (keyCode == KeyEvent.VK_LEFT) {
            map.moveLeft();
        }

        if (keyCode == KeyEvent.VK_DOWN) {
            map.moveDown();
        }

        if (keyCode == KeyEvent.VK_UP) {
            map.moveUp();
        }

        if (keyCode == KeyEvent.VK_D) {
            map.toggleDebug();
        }

        if (keyCode == KeyEvent.VK_LEFT && keyCode == KeyEvent.VK_UP) {
            map.moveLeft();
            map.moveUp();
        }

        if (keyCode == KeyEvent.VK_ESCAPE || keyCode == KeyEvent.VK_SPACE || keyCode == KeyEvent.VK_ENTER) {
            daWindow.dispose();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    public void refresh() {
        if (daWindow.isVisible()) {
            map.repaint();
        }
    }
}

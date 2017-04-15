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
package com.scndgen.legends.windows;

import com.scndgen.legends.LoginScreen;
import com.scndgen.legends.drawing.SpecialDrawMenuBGs;
import com.scndgen.legends.Language;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * This class holds about information
 *
 * @author ndana
 */
public class WindowControls extends JFrame implements ActionListener, KeyListener {

    private Object source;
    private JPanel bottom;
    private JButton ok;
    private SpecialDrawMenuBGs logoPic;
    private Box box;
    private Font normalFont;
    private JPanel p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17;
    private JLabel lLabel, l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11, l12, l13, l14, l15, l16, l17, l18, l19, l20;
    private JLabel mLabel, m1, m2, m3, m4, m5, m6, m8, m7, m9, m10;

    @SuppressWarnings("LeakingThisInConstructor")
    public WindowControls() {
        normalFont = LoginScreen.getInstance().getMyFont(LoginScreen.normalTxtSize - 2);
        logoPic = new SpecialDrawMenuBGs();

        box = new Box(BoxLayout.Y_AXIS);
        box.setOpaque(false);

        ok = new JButton(Language.getInstance().getLine(36));
        ok.addActionListener(this);
        bottom = new JPanel();
        bottom.add(ok);

        lLabel = new JLabel(Language.getInstance().getLine(37));
        l1 = new JLabel(" " + Language.getInstance().getLine(38));
        l2 = new JLabel(Language.getInstance().getLine(40));
        l17 = new JLabel(" " + Language.getInstance().getLine(39));
        l18 = new JLabel(Language.getInstance().getLine(41));
        l3 = new JLabel(" " + Language.getInstance().getLine(42));
        l4 = new JLabel("F12");
        l5 = new JLabel(" " + Language.getInstance().getLine(43));
        l6 = new JLabel("ESC");
        l7 = new JLabel(" " + Language.getInstance().getLine(44));
        l8 = new JLabel("L");
        l9 = new JLabel(" " + Language.getInstance().getLine(45));
        l10 = new JLabel("Left");
        l11 = new JLabel(" " + Language.getInstance().getLine(46));
        l12 = new JLabel("Right");
        l13 = new JLabel(" " + Language.getInstance().getLine(47));
        l14 = new JLabel("Up");
        l15 = new JLabel(" " + Language.getInstance().getLine(48));
        l16 = new JLabel(Language.getInstance().getLine(49));
        l19 = new JLabel(" " + Language.getInstance().getLine(50));
        l20 = new JLabel("F4");

        p9 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        p9.add(lLabel);

        p1 = new JPanel(new GridLayout(1, 2));
        p1.add(l1);
        p1.add(l2);

        p2 = new JPanel(new GridLayout(1, 2));
        p2.add(l3);
        p2.add(l4);


        p3 = new JPanel(new GridLayout(1, 2));
        p3.add(l5);
        p3.add(l6);

        p4 = new JPanel(new GridLayout(1, 2));
        p4.add(l7);
        p4.add(l8);

        p5 = new JPanel(new GridLayout(1, 2));
        p5.add(l9);
        p5.add(l10);

        p6 = new JPanel(new GridLayout(1, 2));
        p6.add(l11);
        p6.add(l12);

        p7 = new JPanel(new GridLayout(1, 2));
        p7.add(l13);
        p7.add(l14);

        p8 = new JPanel(new GridLayout(1, 2));
        p8.add(l15);
        p8.add(l16);

        p15 = new JPanel(new GridLayout(1, 2));
        p15.add(l17);
        p15.add(l18);

        p17 = new JPanel(new GridLayout(1, 2));
        p17.add(l19);
        p17.add(l20);

        //Mouse
        mLabel = new JLabel(Language.getInstance().getLine(51));
        p13 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        p13.add(mLabel);

        m1 = new JLabel(Language.getInstance().getLine(52));
        m2 = new JLabel(" " + Language.getInstance().getLine(45));
        p10 = new JPanel(new GridLayout(1, 2));
        p10.add(m2);
        p10.add(m1);

        m3 = new JLabel(Language.getInstance().getLine(53));
        m4 = new JLabel(" " + Language.getInstance().getLine(46));
        p11 = new JPanel(new GridLayout(1, 2));
        p11.add(m4);
        p11.add(m3);

        m5 = new JLabel(Language.getInstance().getLine(54));
        m6 = new JLabel(" " + Language.getInstance().getLine(38));
        p12 = new JPanel(new GridLayout(1, 2));
        p12.add(m6);
        p12.add(m5);

        m7 = new JLabel(Language.getInstance().getLine(55));
        m8 = new JLabel(" " + Language.getInstance().getLine(44));
        p14 = new JPanel(new GridLayout(1, 2));
        p14.add(m8);
        p14.add(m7);

        m9 = new JLabel(Language.getInstance().getLine(56));
        m10 = new JLabel(" " + Language.getInstance().getLine(39));
        p16 = new JPanel(new GridLayout(1, 2));
        p16.add(m10);
        p16.add(m9);

        alterJLabel(lLabel);
        alterJLabel(l1);
        alterJLabel(l2);
        alterJLabel(l3);
        alterJLabel(l4);
        alterJLabel(l5);
        alterJLabel(l6);
        alterJLabel(l7);
        alterJLabel(l8);
        alterJLabel(l9);
        alterJLabel(l10);
        alterJLabel(l11);
        alterJLabel(l12);
        alterJLabel(l13);
        alterJLabel(l14);
        alterJLabel(l15);
        alterJLabel(l16);
        alterJLabel(l17);
        alterJLabel(l18);
        alterJLabel(l19);
        alterJLabel(l20);

        //keyboard
        alterJPanel(p9);
        alterJPanel(p1);
        alterJPanel(p15);
        alterJPanel(p2);
        alterJPanel(p3);
        alterJPanel(p4);
        alterJPanel(p5);
        alterJPanel(p6);
        alterJPanel(p7);
        alterJPanel(p8);
        alterJPanel(p17);

        alterJLabel(mLabel);
        alterJLabel(m1);
        alterJLabel(m2);
        alterJLabel(m3);
        alterJLabel(m4);
        alterJLabel(m5);
        alterJLabel(m6);
        alterJLabel(m8);
        alterJLabel(m7);
        alterJLabel(m9);
        alterJLabel(m10);

        alterJPanel(bottom);

        //mouse
        alterJPanel(p13);
        alterJPanel(p10);
        alterJPanel(p11);
        alterJPanel(p12);
        alterJPanel(p14);
        alterJPanel(p16);


        //keyboard
        box.add(p9);
        box.add(p1);
        box.add(p15);
        box.add(p2);
        box.add(p3);
        box.add(p4);
        box.add(p5);
        box.add(p6);
        box.add(p7);
        box.add(p8);
        box.add(p17);

        //mouse
        box.add(p13);
        box.add(p10);
        box.add(p11);
        box.add(p12);
        box.add(p14);
        box.add(p16);

        box.add(bottom);

        setTitle(Language.getInstance().getLine(57));
        setContentPane(logoPic);
        add(box, BorderLayout.CENTER);
        setUndecorated(true);
        pack();
        addKeyListener(this);
        requestFocusInWindow();
        setFocusable(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private void alterJPanel(JPanel p) {
        p.setOpaque(false);
    }

    public void alterJLabel(JLabel l) {
        l.setFont(normalFont);
        l.setForeground(Color.WHITE);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        source = ae.getSource();
        if (source == ok) {
            dispose();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            dispose();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}

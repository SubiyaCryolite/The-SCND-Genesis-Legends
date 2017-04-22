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

import com.scndgen.legends.Language;
import com.scndgen.legends.LoginScreen;
import com.scndgen.legends.drawing.SpecialDrawMenuBGs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;

/**
 * This class holds about information
 *
 * @author ndana
 */
public class WindowUpdate extends JFrame implements ActionListener {

    private Object source;
    private JTextArea txt;
    private JPanel logo, textHolder, bottom;
    private JButton ok, get;
    private JScrollPane scroller;
    private SpecialDrawMenuBGs logoPic;
    private Box box = new Box(BoxLayout.Y_AXIS);
    private String updateVer, text;
    private String[] updateArr;
    //private Font normalFont;
    private String fname;

    @SuppressWarnings("LeakingThisInConstructor")
    public WindowUpdate(String updateVerStr, String fname, String[] updates, LoginScreen p) {
        //normalFont = getMyFont(LoginScreen.normalTxtSize);
        updateVer = updateVerStr;
        updateArr = updates;
        logo = new JPanel();
        this.fname = fname;
        logoPic = new SpecialDrawMenuBGs();
        logo.add(logoPic);

        text = "The SCND Genesis: Legends " + updateVer + " " + Language.getInstance().get(58) + " "
                + "\nhttp://www.sourceforge.com/projects/scndgen/files/executable/" + fname + "/download"
                + "\n\n" + Language.getInstance().get(59) + ": \n"
                + stringBuilder(updates)
                + "\n\nThe SCND Genesis: Legends copyright © " + WindowAbout.year() + " Ifunga Ndana.";

        txt = new JTextArea("", 2, 2);
        //txt.setFont(normalFont);
        txt.append(text);
        txt.setEditable(false);
        txt.setLineWrap(true);
        txt.setWrapStyleWord(true);
        scroller = new JScrollPane(txt);
        scroller.setPreferredSize(new Dimension(640, 500));
        scroller.setSize(new Dimension(640, 500));
        textHolder = new JPanel();
        textHolder.add(scroller);

        get = new JButton(Language.getInstance().get(416));
        //get.setFont(normalFont);
        get.addActionListener(this);
        ok = new JButton(Language.getInstance().get(417));
        //ok.setFont(normalFont);
        ok.addActionListener(this);
        bottom = new JPanel();
        bottom.add(get);
        bottom.add(ok);

        box.add(scroller);
        box.add(bottom);

        setTitle(updateVer + " " + Language.getInstance().get(60));
        add(box);
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        source = ae.getSource();
        if (source == ok) {
            dispose();
        } else if (source == get) {
            try {
                Desktop me = Desktop.getDesktop();
                me.browse(new URI("http://www.sourceforge.com/projects/scndgen/files/executable/" + fname + "/download"));
                JOptionPane.showMessageDialog(null, "You must install the patch and \n"
                        + "then reboot the game in order to complete the update.\n "
                        + "Have a great day!");
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }
            dispose();
            System.exit(0);
        }
    }

    private String stringBuilder(String[] args) {
        StringBuilder epic = new StringBuilder("");

        for (int i = 0; i < args.length; i++) {
            epic.append(args[i]);
        }

        return epic.toString();
    }
}

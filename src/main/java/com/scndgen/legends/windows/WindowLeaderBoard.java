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
import com.scndgen.legends.network.SqlQuery;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author ifunga
 */
public class WindowLeaderBoard extends JFrame implements ActionListener {

    private JPanel line1, line2, line3;
    private JButton btnUpload, btnView, btnClose;
    private boolean notLoaded = true;
    private Box box;
    private SqlQuery viewer;
    private boolean viewerNotLoaded = true, notInitislied = true;
    private String database;
    private Connection con;
    private Statement stmt;
    private ResultSet rs;

    public WindowLeaderBoard() {
        super(Language.getInstance().get(98));
        if (notLoaded) {
            btnUpload = new JButton(Language.getInstance().get(97));
            btnUpload.addActionListener(this);
            btnView = new JButton(Language.getInstance().get(96));
            btnView.addActionListener(this);
            btnClose = new JButton(Language.getInstance().get(95));
            btnClose.addActionListener(this);
            line1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
            line1.add(btnView);
            line2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
            line2.add(btnUpload);
            line3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
            line3.add(btnClose);
            box = new Box(BoxLayout.Y_AXIS);
            box.add(line1);
            box.add(line2);
            box.add(line3);
            add(box);
            pack();
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLocationRelativeTo(null);
            setResizable(false);
            setVisible(true);
            notLoaded = false;
        }
    }

    public static void main(String[] args) {
        new WindowLeaderBoard();
    }

    public void reappear() {
        show();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == btnView) {
            if (viewerNotLoaded) {
                viewer = new SqlQuery();
                viewerNotLoaded = false;
            } else {
                viewer.reappear();
            }
        }
        if (actionEvent.getSource() == btnUpload) {
            uploadToServer();
        }
        if (actionEvent.getSource() == btnClose) {
            dispose();
        }
    }

    private void uploadToServer() {
    }
}

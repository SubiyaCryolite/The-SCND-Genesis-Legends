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
package com.scndgen.legends.menus;

import com.scndgen.legends.LoginScreen;
import com.scndgen.legends.engine.JenesisLanguage;
import com.scndgen.legends.network.SqlQuery;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author ifunga
 */
public class MenuLeaderBoard implements ActionListener {

    private JFrame frame;
    private JenesisLanguage lang;
    private JPanel line1, line2, line3;
    private JButton upload, view, close;
    private String dbName = "sql09.freemysql.net/scndrating";
    private String passWd = "user=subiyacryolite&password=dbHomie";
    private boolean notLoaded = true;
    private Object source;
    private Box box;
    private SqlQuery viewer;
    private boolean viewerNotLoaded = true, notInitislied = true;
    private String database;
    private Connection con;
    private Statement stmt;
    private ResultSet rs;

    public MenuLeaderBoard() {
        if (notLoaded) {
            lang = LoginScreen.getLoginScreen().getLoginScreen().getLangInst();

            upload = new JButton(lang.getLine(97));
            upload.addActionListener(this);
            view = new JButton(lang.getLine(96));
            view.addActionListener(this);
            close = new JButton(lang.getLine(95));
            close.addActionListener(this);

            line1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
            line1.add(view);
            line2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
            line2.add(upload);
            line3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
            line3.add(close);

            box = new Box(BoxLayout.Y_AXIS);
            box.add(line1);
            box.add(line2);
            box.add(line3);

            frame = new JFrame(lang.getLine(98));
            frame.add(box);
            frame.pack();
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setResizable(false);
            frame.setVisible(true);
            notLoaded = false;
        }
    }

    public static void main(String[] args) {
        new MenuLeaderBoard();
    }

    public void reappear() {
        frame.show();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        source = ae.getSource();

        if (source == view) {
            if (viewerNotLoaded) {
                viewer = new SqlQuery();
                viewerNotLoaded = false;
            } else {
                viewer.reappear();
            }
        }

        if (source == upload) {
            upload();
        }

        if (source == close) {
            frame.dispose();
        }
    }

    private void upload() {
        try {
            try {
                if (notInitislied) {
                    Class.forName("com.mysql.jdbc.Driver").newInstance();
                    database = "jdbc:mysql://" + dbName + "?" + passWd;
                    con = DriverManager.getConnection(database);
                    System.out.println("Connected to " + database);
                    notInitislied = false;
                }
                stmt = con.createStatement();

                try {
                    //if exists error shall be thrown
                    System.out.println("Brand new");
                    stmt.executeUpdate("INSERT INTO user(id, rating, userName, userCountry, gameVersion, versionInt, userTotalMatches, userWin, userLoss, favCharacter, userPoints) VALUES ('" + LoginScreen.getLoginScreen().usrCode + "', " + LoginScreen.getLoginScreen().getLoginScreen().getGameRating() + ", '" + LoginScreen.getLoginScreen().strUser + "', '" + LoginScreen.getLoginScreen().getLoginScreen().getCCode() + "', '" + CanvasGameRender.getVersionStr() + "', " + CanvasGameRender.getVersionInt() + ", " + (LoginScreen.getLoginScreen().getLoginScreen().win + LoginScreen.getLoginScreen().getLoginScreen().loss) + ", " + LoginScreen.getLoginScreen().getLoginScreen().win + ", " + LoginScreen.getLoginScreen().getLoginScreen().loss + ", " + LoginScreen.getLoginScreen().getLoginScreen().mostPopularChar() + ", " + LoginScreen.getLoginScreen().strPoint + ")");
                } catch (Exception e) {
                    System.out.println("Override old record");
                    stmt.executeUpdate("UPDATE user SET id='" + LoginScreen.getLoginScreen().usrCode + "', rating=" + LoginScreen.getLoginScreen().getLoginScreen().getGameRating() + ", userName='" + LoginScreen.getLoginScreen().strUser + "', userCountry='" + LoginScreen.getLoginScreen().getLoginScreen().getCCode() + "', gameVersion='" + CanvasGameRender.getVersionStr() + "', versionInt=" + CanvasGameRender.getVersionInt() + ", userTotalMatches=" + (LoginScreen.getLoginScreen().getLoginScreen().win + LoginScreen.getLoginScreen().getLoginScreen().loss) + ", userWin=" + LoginScreen.getLoginScreen().getLoginScreen().win + ", userLoss=" + LoginScreen.getLoginScreen().getLoginScreen().loss + ", favCharacter=" + LoginScreen.getLoginScreen().getLoginScreen().mostPopularChar() + ", userPoints=" + LoginScreen.getLoginScreen().strPoint + " WHERE id='" + LoginScreen.getLoginScreen().usrCode + "'");

                }


                LoginScreen.getLoginScreen().getLoginScreen().saveConfigFile();
                JOptionPane.showMessageDialog(null, lang.getLine(99), lang.getLine(102), JOptionPane.PLAIN_MESSAGE);
            } catch (Exception e) {
                System.err.println(e);
                notInitislied = true;
                JOptionPane.showMessageDialog(null, lang.getLine(100), lang.getLine(101), JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            System.err.println(e);
            JOptionPane.showMessageDialog(null, lang.getLine(100), lang.getLine(101), JOptionPane.ERROR_MESSAGE);
            notInitislied = true;
        }
    }
}

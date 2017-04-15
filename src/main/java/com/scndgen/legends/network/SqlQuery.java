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
package com.scndgen.legends.network;

import com.scndgen.legends.render.RenderGameplay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SqlQuery extends JFrame implements ActionListener {

    private static SqlQuery me;
    private JComboBox levelC, comboSort;
    private JLabel levelL, averageMatch, averagePoint, labelTop, sortBy;
    private JTable table;
    private JScrollPane scroller;
    private JPanel lineTop, line1, line2, line3, line4;
    private JButton close, sort;
    private Connection con;
    private String database;
    private Statement stmt;
    private ResultSet rs;
    private String dbName = "sql09.freemysql.net/scndrating";
    private String passWd = "user=subiyacryolite&password=dbHomie";
    private Object[][] results;
    private int currentIndex, arrayLength;
    private Box box;
    private int[] levels;
    private String[] sorters;
    private String[] tblColumns;
    private String[] levelsStr;
    private boolean initSuccess, notInitislied;
    private float avrg;

    @SuppressWarnings("LeakingThisInConstructor")
    public SqlQuery() {
        super("SCND GENESIS -- Leaderboards");
        levels = new int[]{1, 5, 10, 50, 100, 200};
        levelsStr = new String[]{"King of the Hill", "Top 5", "Top 10", "Top 50", "Top 100", "Top 200"};
        notInitislied = true;
        tblColumns = new String[]{"No", "Name", "Rating", "Country", "Version", "Code", "Matches", "Wins", "Loss", "Characters", "Points"};

        sortBy = new JLabel("Sort by: ");
        sorters = new String[]{"userPoints", "rating", "userWin", "userLoss"};
        comboSort = new JComboBox(sorters);

        labelTop = new JLabel("<< Not connected >>");
        averageMatch = new JLabel("Average Match count: ");
        averagePoint = new JLabel("Average Points scored: ");

        avrg = 0;
        reset(50);

        levelL = new JLabel("Show : ");
        levelC = new JComboBox(levelsStr);
        levelC.setSelectedIndex(3);
        sort = new JButton("Sort");
        sort.addActionListener(this);

        lineTop = new JPanel(new FlowLayout(FlowLayout.CENTER));
        lineTop.add(labelTop);

        line1 = new JPanel(new GridLayout(1, 3));
        line1.add(levelL);
        line1.add(levelC);
        line1.add(sortBy);
        line1.add(comboSort);
        line1.add(sort);


        line2 = new JPanel(new GridLayout(1, 2));
        line2.add(averageMatch);
        line2.add(averagePoint);

        line3 = new JPanel();
        line3.add(scroller);

        close = new JButton("Close");
        close.addActionListener(this);
        line4 = new JPanel();
        line4.add(close);

        box = new Box(BoxLayout.Y_AXIS);
        box.add(lineTop);
        box.add(line1);
        box.add(line2);
        box.add(line4);
        box.add(line3);
        add(box);
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) {
        me = new SqlQuery();
    }

    public void reappear() {
        show();
    }

    private void reset(int number) {
        try {
            currentIndex = 0;
            arrayLength = 12;
            results = new Object[number][arrayLength];
            {
                for (int i = 0; i < number; i++) {
                    for (int u = 0; u < arrayLength; u++) {
                        results[i][u] = " ";
                    }
                }
            }
            initSuccess = false;
            try {
                try {
                    {
                        labelTop.setText("<< Attemting to Connect... >>");
                        Class.forName("com.mysql.jdbc.Driver").newInstance();
                        database = "jdbc:mysql://" + dbName + "?" + passWd;
                        con = DriverManager.getConnection(database);
                        System.out.println("Connected to " + database);
                        notInitislied = false;
                    }
                    initSuccess = true;

                    stmt = con.createStatement();

                    rs = stmt.executeQuery("SELECT * FROM user ORDER BY " + sorters[comboSort.getSelectedIndex()] + " DESC  LIMIT 0," + number + "");
                    if (rs != null) {
                        while (rs.next()) {
                            results[currentIndex][0] = currentIndex + 1;
                            results[currentIndex][1] = (rs.getString("userName"));
                            results[currentIndex][2] = (rs.getString("rating"));
                            results[currentIndex][3] = (rs.getString("userCountry"));
                            results[currentIndex][4] = (rs.getString("gameVersion"));
                            results[currentIndex][5] = (rs.getInt("versionInt"));
                            results[currentIndex][7] = (rs.getInt("userWin"));
                            results[currentIndex][8] = (rs.getInt("userLoss"));
                            results[currentIndex][6] = (Integer.parseInt("" + results[currentIndex][8]) + Integer.parseInt("" + results[currentIndex][7]));
                            results[currentIndex][9] = RenderGameplay.getInstance().getFavChar(rs.getInt("favCharacter"));
                            results[currentIndex][10] = (rs.getInt("userPoints"));
                            currentIndex++;
                        }
                    }
                    table = new JTable(results, tblColumns);
                    scroller = new JScrollPane(table);
                    scroller.setPreferredSize(new Dimension(768, 512));

                    //average match
                    rs = stmt.executeQuery("SELECT AVG(userTotalMatches) FROM user LIMIT 0," + number + "");
                    if (rs != null) {
                        while (rs.next()) {
                            avrg = rs.getFloat(1);
                        }
                    }
                    averageMatch.setText("Average Match count is: " + avrg);


                    //average points
                    rs = stmt.executeQuery("SELECT AVG(userPoints) FROM user LIMIT 0," + number + "");
                    if (rs != null) {
                        while (rs.next()) {
                            avrg = rs.getFloat(1);
                        }
                    }
                    averagePoint.setText("Average Point count is: " + avrg);
                    //clean up
                    {
                        rs.close();
                        stmt.close();
                        labelTop.setText("<< Connected >>");
                        System.out.println("Done");
                        initSuccess = true;
                    }

                } catch (Exception e) {
                    System.err.println(e.toString());
                    initSuccess = false;
                    notInitislied = true;
                    labelTop.setText("<< Not Connected >>");
                    table = new JTable(results, tblColumns);
                    scroller = new JScrollPane(table);
                    scroller.setPreferredSize(new Dimension(768, 512));
                }
            } catch (Exception ex) {
                // handle the error
                table = new JTable(results, tblColumns);
                scroller = new JScrollPane(table);
                scroller.setPreferredSize(new Dimension(768, 512));
                labelTop.setText("<< Not Connected >>");
            }
        } catch (Exception e) {
            table = new JTable(results, tblColumns);
            scroller = new JScrollPane(table);
            scroller.setPreferredSize(new Dimension(768, 512));
            labelTop.setText("<< Not Connected >>");
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == sort) {
            line3.remove(scroller);
            box.remove(line3);
            reset(levels[levelC.getSelectedIndex()]);
            line3.add(scroller);
            box.add(line3);
            box.revalidate();
        }
        if (ae.getSource() == close) {
            dispose();
        }
    }
}

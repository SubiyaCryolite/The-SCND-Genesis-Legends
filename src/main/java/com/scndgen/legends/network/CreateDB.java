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

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class CreateDB {

    private static SqlQuery me;
    private JFrame frame;
    private JComboBox levelC, comboSort;
    private JLabel levelL, averageMatch, averagePoint, labelTop, sortBy;
    private JTable table;
    private JScrollPane scroller;
    private JPanel lineTop, line1, line2, line3, line4;
    private JButton close, sort;
    private Object source;
    private Connection con;
    private String database;
    private Statement stmt;
    //private String dbName = "localhost/scndgen";
    //private String passWd = "user=root&password=password";
    private ResultSet rs;
    /**
     * Guys the two lines below are VERY important, please don't pillage :D
     */
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

    public CreateDB() {
        try {
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                database = "jdbc:mysql://" + dbName + "?" + passWd;
                con = DriverManager.getConnection(database);
                System.out.println("Connected to " + database);
                notInitislied = false;
            } catch (Exception exl) {
                System.err.println(exl);
            }
            initSuccess = true;

            stmt = con.createStatement();

            stmt.executeUpdate("CREATE TABLE  user(id VARCHAR(30) NOT NULL, rating INTEGER, userName VARCHAR(50), userCountry VARCHAR(50), gameVersion VARCHAR(20), versionInt INTEGER, userTotalMatches INTEGER, userWin INTEGER, userLoss INTEGER, favCharacter INTEGER, userPoints INT, PRIMARY KEY (id) );");
            System.out.println("Created DB");
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public static void main(String[] args) {
        new CreateDB();
    }
}

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
package com.scndgen.legends;

import com.scndgen.legends.render.RenderGameplay;
import com.scndgen.legends.drawing.DrawUserLogin;
import com.scndgen.legends.enums.CharacterEnum;
import com.scndgen.legends.windows.MainMenu;
import com.scndgen.legends.windows.WindowMain;
import com.scndgen.legends.windows.WindowOptions;
import com.scndgen.legends.windows.WindowUpdate;
import io.github.subiyacryolite.enginev1.JenesisImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is a fighting game based on my webcomic THE SCND GENESIS
 * http://www.scndgenesis.50webs.com
 * The battle system is inspired by Final Fantasy XIII and conventional 2D Fighters
 * The game has a handdrawn comic book graphical style similar to the comic and has other anime style effects
 * Feel free to tell me what you think about the game and comic ifungandana(at)gmail.com
 * Year - Month - date
 * for every enhancement add 0.0.0.1
 * for every milestone and 0.0.0.3
 * 18/06/10 - Added Shakey digits 0.0.2.8
 * 17/07/10 - Added Difficulty Settings 0.0.2.9
 * 17/07/10 - Embedded attacks into main screen 0.0.3.0
 * 30/07/10 - Grouped all attacks into single class 0.0.3.1
 * 30/07/10 - Drastic architectural changes 0.0.3.2
 * 31/07/10 - Cool motif look and feel 0.0.3.3
 * 02/08/10 - Added game menu, ATB Bar, Tabbed Pane for different move type 0.0.3.6
 * 05/08/10 - Added regenerating HP 0.0.3.7
 * 05/08/10 - Attacks now execute in sequence they were added 0.0.3.8
 * 05/08/10 - All menus and panels under one roof 0.0.3.9
 * 09/08/10 - fixed MP3 plug-in 0.0.4.0
 * 15/08/10 - Added match timer and options 0.0.4.1
 * 11/08/10 - Converted timertasks to threads, added arena select, fixed relative sound path 0.0.4.4
 * 19/09/10 - caches all sprites, Serious thread optimizations, AI rewrites, SIGNIFICANT performance improvements 0.0.5.5
 * 22/09/10 - implemented LAN play!!!! Game chat, lobby system needed 0.0.6.5
 * 22/09/10 - implemented login screen, ciphering 0.0.6.7
 * 18/10/10 - implemented in-game chat, utf8 encoding, stats screen, achievement structure, game/profile save 0.0.7.3
 * 21/10/10 - added overworld map, began navigation and screen control 0.0.7.7
 * 23/10/10 - added collision detection algorythm for overworld 0.0.8.0
 * 27/10/10 - added new menu, transition, character classess, remved command panel 0.0.8.4
 * 30/10/10 -added mode menu, server-client embeddedin menu, match making/lobby system ACTUALLY WORKS!!!!!! 0.0.8.7
 * 03/11/10 -24- added new Character, Aisha 0.0.8.8
 * 03/11/10 -25- added limit break system, fixed LAN bugs - 0.0.9.0
 * 17/11/10 -26- better about screen, new versioning system ( major version | minor revision | updates/fixes ) 0.0.9.1
 * 20/11/10 -29- STORY MODE STRUCURE!!! Storymode bug-fixes, options and stats integrated into menu
 * 23/11/10 -30- fixed story mode and added pause, skip, resume :D
 * 02/12/10 -31- MOUSE INPUT :D, Fixed sound structure,Music pauses, added framrate chooser, sound-on/off works
 * 04/12/10 -32- One story mode to rule them all, bwa ha ha, added scene select as well :)
 * 07/12/10 -33- Added background animation thread
 * 10/12/10 -34- Added Ravage, implemented character balance scheme, fixed bug in story mode thread
 * Sidenote 14/12/10: Joined Twitter ^_^
 * 15/12/10 -35- Added character Ade, Added quit game, resume, exit to gameplay. New achievement pics, mod to systemNotice(), better figures, sexy transparent HUD
 * 17/12/10 -36- Added new stages "Scorched Ruins" and "Frozen Wilderness"
 * 23/12/10 - Started porting the game to c++, evident performance benefits, fixed threads.
 * 27/12/10 -37- Realised how much I love Java, cross pompiling on C++ sucks, smoothened animations and start menu screen
 * 01/1/11 -38- Thread optimisations. wee
 * 04/1/11 -39- Fixed story mode bugs, adding GPL headerz gon opensource
 * 13/1/11 -40- Changed main menu, ditched runtime flipping for pre rendered images (opponents), performance benefits
 * 14/1/11 -41- Integrated stats into main menu, pending for connections can be cancelled
 * 15/1/11 -42- Backwards compatibility for new save items, fixed time
 * 15/1/11 -43- Changelog moved to WindowAbout.java in text3
 * 13/2/11 -44- I'm baaaaack, changelog in WindowAbout is clientSide only, codies go here
 * 13/2/11 -44- Fixed bug, reset move to physical at new match
 *
 * @author Ndana
 */

public class LoginScreen extends JFrame implements ActionListener, KeyListener {

    public static final CharacterEnum[] charNames = CharacterEnum.values();
    public static int difficultyBase = 8000, difficultyScale = 1333;
    public static String configLoc = System.getProperty("user.home") + File.separator + ".config" + File.separator + "scndgen" + File.separator;
    public static int normalTxtSize = 14, bigTxtSize = 20, extraTxtSize = 26;
    private static LoginScreen instance;
    public int frames, activLang;
    public boolean ans, newGame = true;
    public int comicPicOcc, difficultyStat, difficultyDyn, stage;
    public int timePref;
    public String whoCalled, networkStatus, soundStatus = "on", currentFile;
    public int isLeftyInt, win, loss;
    public boolean connected = true, isDownloadingMusic = false;
    public boolean controller = true;
    public String graffix, currentTrack, fileNameMus, upToDate, autoUpdate, downloadedAudio, fileName = configLoc + "scndupd.xml";
    public String isLefty, usrCode, controllerStr, strUser, strPoint, strPlayTime, matchCountStr;
    public int[] ach;
    public int[] classArr;
    public float musicPerc;
    public long currentTrackSize, trackSize;
    public int consecWinsTmp, consecWins;
    public int scalePos;
    public float scaleY;
    public float scaleX;
    public int defSpriteWidth, defSpriteHeight;
    public int updates, defWidth, defHeight;
    private String random_name, newAcc;
    private int v1x, v2x, v3x;
    private StringBuilder userIDBuff;
    private String[] letters;
    private ResultSet rs;
    private Statement stat;
    private Connection conn;
    private PreparedStatement prep;
    private Object source;
    private JPanel pan1, pan2, pan4;
    private DrawUserLogin thisPic;
    private JButton enter, quit, newAccount, userAccount;
    private JComboBox users;
    private JLabel userName, countryCode;
    private JTextField login;
    private JenesisImageLoader pix;
    private Box box = new Box(BoxLayout.Y_AXIS);
    private WindowUpdate newy = null;
    private RandomAccessFile rand;
    private MainMenu startApp;
    private String IPAdd;
    private Image image;
    private SystemTray tray;
    private PopupMenu popup;
    private MenuItem defaultItem;
    private TrayIcon trayIcon;
    private URL url;
    private BufferedInputStream in;
    private FileOutputStream fos;
    private BufferedOutputStream bout;
    private File out;
    private byte[] data;
    private int numberOfAccounts, x, gameRating;
    private BufferedReader br;
    private String[] updatesArr, countries;
    private int[] charUsage = new int[charNames.length];
    private int[] widthz;
    private int[] heightz;
    private float[] resScaleY;
    private Achievements achObj;
    private float[] resScaleX;
    private boolean noUsers, oldFile, newFile;
    private File db;
    private boolean[] foundAch;
    private int searchIndex;
    private String ansc, webVersion, countryStr, sx, tx = "", updateFileURL = "http://scndgen.sourceforge.net/game/scndupd.xml";
    //private String ansc, webVersion, countryStr, sx, tx = "", updateFileURL = "http://localhost/scnd/game2/scndupd.xml";
    private String fname;

    @SuppressWarnings("LeakingThisInConstructor")
    private LoginScreen() throws FileNotFoundException {
        changeTheLookAndFeel(2);

        createDB();
        createDBStructure();

        activLang = 0;
        consecWinsTmp = 0;
        consecWins = 0;
        scalePos = 2;
        widthz = new int[]{720, 852, 1024, 1280, 1680, 1920};
        heightz = new int[]{405, 480, 576, 720, 1050, 1080};
        resScaleY = new float[]{0.8437f, 1.0f, 1.2f, 1.5f, 2.1875f, 2.25f};
        scaleY = 1;
        resScaleX = new float[]{0.8450f, 1.0f, 1.1976f, 1.5023f, 1.9718f, 2.2535f};
        scaleX = 1;
        defSpriteWidth = 852;
        defSpriteHeight = 480;
        controllerStr = "true";
        strUser = "no user";
        strPoint = "0";
        strPlayTime = "0";
        matchCountStr = "0";
        countries = Locale.getISOCountries();

        loadTray();
        //trayMessage("Welcome", "Welcome to The SCND Genesis: Legends" + RenderGameplay.getVersionStr());
        pan1 = new JPanel(new FlowLayout(FlowLayout.CENTER));

        pan2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        userName = new JLabel("User Name: ");
        login = new JTextField("", 10);
        pan2.add(userName);
        pan2.add(login);

        pan4 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        enter = new JButton("Load Selected Account");
        enter.addActionListener(this);
        quit = new JButton("Quit");
        quit.addActionListener(this);
        newAccount = new JButton("Create a New Account");
        newAccount.addActionListener(this);
        pan4.add(newAccount);
        box.add(pan1);
        box.add(pan2);
        box.add(pan4);
        add(box);
        createUserCombo();
        thisPic = new DrawUserLogin(this);
        {
            achObj = new Achievements(this);
            ach = new int[achObj.achDesc.length];
            classArr = new int[ach.length];

            //make all achievents zero first
            for (int y = 0; y < ach.length; y++) {
                ach[y] = 0;
            }
            //achievement classes
            classArr[0] = 1;
            classArr[1] = 2;
            classArr[2] = 1;
            classArr[3] = 2;
            classArr[4] = 3;
            classArr[5] = 3;
            classArr[6] = 2;
            classArr[7] = 1;
            classArr[8] = 1;
            classArr[9] = 1;
            classArr[10] = 1;
            /*
            classArr[11] = 2;
            classArr[12] = 0;
            classArr[13] = 1;
            classArr[14] = 2;
            classArr[15] = 1;
            classArr[16] = 1;
            classArr[17] = 0;
            classArr[18] = 1;
            classArr[19] = 2;
            classArr[20] = 2;
            classArr[21] = 2;*/
        }
        readDB(users.getSelectedItem() + "");
        pan1.add(thisPic);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("The SCND Genesis: Legends" + RenderGameplay.getInstance().getVersionStr());
        pack();
        addKeyListener(this);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);

        newVersionOutThere("");
    }

    /**
     * Get screen width
     *
     * @return width
     */
    public static int getGameWidth() {
        //return widthz[scalePos];
        return 852;
    }

    public static void main(String[] args) {
        try {
            instance = new LoginScreen();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(LoginScreen.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    /**
     * Get this instance
     *
     * @return this instance
     */
    public static LoginScreen getInstance() {
        return instance;
    }

    /**
     * Get screen height
     *
     * @return height
     */
    public int getGameHeight() {
        return heightz[scalePos];
    }

    /**
     * Get native resolution of game sprites
     *
     * @return
     */
    public int getdefSpriteWidth() {
        return defSpriteWidth;
    }

    /**
     * Get the games rating
     *
     * @return rating
     */
    public int getGameRating() {
        System.out.println("Current game rating: " + gameRating);
        return gameRating;
    }

    /**
     * Set the game rating
     */
    public void setGameRating(int num) {
        gameRating = num * 20;
    }

    /**
     * Get native resolution of game sprites
     *
     * @return
     */
    public float getGameYScale() {
        return resScaleY[scalePos];
    }

    /**
     * Is this dude left handed?
     *
     * @return
     */
    public String isLefty() {
        return isLefty;
    }

    /**
     * Get native resolution of game sprites
     *
     * @return
     */
    public float getGameXScale() {
        return resScaleX[scalePos];
    }

    /**
     * Get native resolution of game sprites
     *
     * @return
     */
    public int getdefSpriteHeight() {
        return defSpriteHeight;
    }

    public String timeCal(String x) {
        return thisPic.timeCal(x);
    }

    private void createDB() {
        db = new File(System.getProperty("user.home") + File.separator + ".config" + File.separator + "scndgen");
        if (!new File(db.getAbsolutePath() + File.separator + "scndsave.db").exists()) {
            try {
                db.mkdirs();
                new File(db.getAbsolutePath() + File.separator + "scndsave.db").createNewFile();
                System.err.println("Creating database");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Erase all user accounts
     */
    private void clearDB() {
        try {
            stat = conn.createStatement();
            stat.executeUpdate("drop table if exists scndsave;");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the DB Structure
     */
    private void createDBStructure() {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:" + System.getProperty("user.home") + File.separator + ".config" + File.separator + "scndgen" + File.separator + "scndsave.db");
            stat = conn.createStatement();
            stat.executeUpdate("CREATE TABLE IF NOT EXISTS scndsave(name VARCHAR(24),usrCode VARCHAR(42),points VARCHAR(24),"
                    + "time VARCHAR(24),matches VARCHAR(24),ach1 VARCHAR(24),ach2 VARCHAR(24),ach3 VARCHAR(24),"
                    + "ach4 VARCHAR(24),ach5 VARCHAR(24),ach6 VARCHAR(24),ach7 VARCHAR(24),ach8 VARCHAR(24),ach9 VARCHAR(24),"
                    + "ach10 VARCHAR(24),ach11 VARCHAR(24),win VARCHAR(24),loss VARCHAR(24),"
                    + "frames INT,sound VARCHAR(3),difficulty VARCHAR(24),modeN VARCHAR(24),"
                    + "prefTime INT,graffix VARCHAR(5),upToDate VARCHAR(3),autoUpdate VARCHAR(3),"
                    + "musicFiles VARCHAR(3),char0 VARCHAR(24),char1 VARCHAR(24),char2 VARCHAR(24),"
                    + "char3 VARCHAR(24),char4 VARCHAR(24),char5 VARCHAR(24),char6 VARCHAR(24),"
                    + "char7 VARCHAR(24),char8 VARCHAR(24),char9 VARCHAR(24),char10 VARCHAR(24),"
                    + "comicT VARCHAR(24),rez VARCHAR(24),rating VARCHAR(24),countryStr VARCHAR(24),"
                    + "controller VARCHAR(24),lang VARCHAR(24),isLefty VARCHAR(3),PRIMARY KEY (usrCode))");
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            foundAch = new boolean[12];
            stat = conn.createStatement();
            rs = stat.executeQuery("PRAGMA table_info('scndsave')");
            while (rs.next()) {
                searchIndex = 0;
                ansc = rs.getString(2);
                if (ansc.contains("ach")) {
                    for (int ix = 12; ix <= 22; ix++) {
                        //System.err.println("Checking ach" + ix + " in " + ansc);
                        if (ansc.equalsIgnoreCase("ach" + ix)) {
                            System.out.println(ansc + " equals ach" + ix + " thus exisits");
                            if (foundAch[ix - 12] != true) {
                                System.out.println("Found ach" + ix);
                                foundAch[ix - 12] = true;
                            }
                        }
                    }
                }
            }

            searchIndex = 0;
            for (int ix = 12; ix < 23; ix++) {
                //if not found alter the table
                if (foundAch[searchIndex] != true) {
                    ansc = "ALTER TABLE scndsave ADD ach" + ix + " VARCHAR(24) default 'g7h%'";
                    stat = conn.createStatement();
                    stat.executeUpdate(ansc);
                    stat.close();
                }
                searchIndex++;
            }
        } catch (Exception e) {
            System.out.println(ansc);
            e.printStackTrace();
        }
    }

    private void jenesisLog(Object c) {
        System.err.println("log :: " + c.toString());
    }

    /**
     * Called whenever accessing user accounts
     *
     * @param u user to scan
     */
    private void readDB(String u) {
        try {
            stat = conn.createStatement();
            rs = stat.executeQuery("SELECT * FROM scndsave WHERE name='" + u + "';");
            while (rs.next()) {
                strUser = u;
                jenesisLog(strUser);

                usrCode = rs.getString("usrCode");
                jenesisLog(usrCode);

                strPoint = scndDecipher(rs.getString("points"));
                jenesisLog(strPoint);

                strPlayTime = "" + rs.getInt("time");
                jenesisLog(strPlayTime);

                matchCountStr = "" + Integer.parseInt(scndDecipher(rs.getString("matches")));
                jenesisLog(matchCountStr);

                win = Integer.parseInt(scndDecipher(rs.getString("win")));
                jenesisLog(win);

                loss = Integer.parseInt(scndDecipher(rs.getString("loss")));
                jenesisLog(loss);

                soundStatus = rs.getString("sound");
                jenesisLog(soundStatus);

                difficultyStat = Integer.parseInt(scndDecipher(rs.getString("difficulty")));
                difficultyDyn = difficultyStat;
                jenesisLog(difficultyStat);

                stage = Integer.parseInt(scndDecipher(rs.getString("modeN")));
                jenesisLog(stage);

                ach[0] = Integer.parseInt(scndDecipher(rs.getString("ach1")));
                ach[1] = Integer.parseInt(scndDecipher(rs.getString("ach2")));
                ach[2] = Integer.parseInt(scndDecipher(rs.getString("ach3")));
                ach[3] = Integer.parseInt(scndDecipher(rs.getString("ach4")));
                ach[4] = Integer.parseInt(scndDecipher(rs.getString("ach5")));
                ach[5] = Integer.parseInt(scndDecipher(rs.getString("ach6")));
                ach[6] = Integer.parseInt(scndDecipher(rs.getString("ach7")));
                ach[7] = Integer.parseInt(scndDecipher(rs.getString("ach8")));


                timePref = rs.getInt("prefTime");
                WindowOptions.time = timePref;
                jenesisLog(timePref);

                graffix = rs.getString("graffix");
                WindowOptions.graphics = graffix;
                jenesisLog(graffix);

                frames = rs.getInt("frames");
                WindowMain.tTime = frames;
                jenesisLog(frames);

                upToDate = rs.getString("upToDate");
                jenesisLog(upToDate);
                //autoUpdate = rs.getString("autoUpdate");
                autoUpdate = "autoUpdate";

                downloadedAudio = rs.getString("musicFiles");
                jenesisLog(downloadedAudio);

                for (int uV = 0; uV < 10; uV++) {
                    try {
                        charUsage[uV] = Integer.parseInt(rs.getString("char" + uV));
                        jenesisLog(charUsage[uV]);
                    } catch (Exception e) {
                        charUsage[uV] = 0;
                    }
                }

                //>>>>>>>>>>>>>>>>>>> for indexes above 10
                for (int uB = 10; uB < charNames.length; uB++) {
                    try {
                        charUsage[uB] = Integer.parseInt(rs.getString("char" + uB));
                    } catch (Exception e) {
                        charUsage[uB] = 0;
                    }
                }
                ach[8] = Integer.parseInt(scndDecipher(rs.getString("ach9")));
                jenesisLog(ach[8]);

                ach[9] = Integer.parseInt(scndDecipher(rs.getString("ach10")));
                jenesisLog(ach[9]);

                ach[10] = Integer.parseInt(scndDecipher(rs.getString("ach11")));
                jenesisLog(ach[10]);

                comicPicOcc = Integer.parseInt(scndDecipher(rs.getString("comicT")));
                jenesisLog(comicPicOcc);

                activLang = Integer.parseInt(scndDecipher(rs.getString("lang")));
                scalePos = Integer.parseInt(scndDecipher(rs.getString("rez")));

                countryStr = rs.getString("countryStr");

                isLefty = rs.getString("isLefty");
                if (isLefty.equalsIgnoreCase("no")) {
                    isLeftyInt = 0; //no
                } else {
                    isLeftyInt = 1; //yes
                }

                try {
                    gameRating = Integer.parseInt(rs.getString("rating"));
                } catch (Exception e) {
                    gameRating = 300;
                }

                try {
                    controllerStr = rs.getString("controller");
                    if (controllerStr.equalsIgnoreCase("true")) {
                        controller = true;
                    } else {
                        controllerStr = "false";
                        controller = false;
                    }
                } catch (Exception e) {
                    controller = false;
                }
            }
            rs.close();
            stat.close();
            thisPic.repaint();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create a combo box with all users listed
     */
    private void createUserCombo() {
        users = new JComboBox();
        numberOfAccounts = 0;
        try {
            stat = conn.createStatement();
            rs = stat.executeQuery("SELECT name FROM scndsave ORDER BY time DESC;");
            while (rs.next()) {
                users.addItem("" + rs.getString("name"));
                numberOfAccounts++;
            }
            rs.close();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (numberOfAccounts == 0) {
            noUsers = true;
            login.setText("");

            enter.setEnabled(false);
            pan4.remove(enter);
            //no users!!!!!
        } else {
            noUsers = false;
            System.out.println("file exists!!!!");

            box.remove(pan2);
            pan4.removeAll();
            pan4.setLayout(new GridLayout(3, 1));
            enter.setEnabled(true);
            users.addActionListener(this);
            pan4.add(newAccount);
            pan4.add(users);
            pan4.add(enter);
            box.add(pan4);
            box.revalidate();
        }
    }

    /**
     * Get instance of our mode select menu
     *
     * @return
     */
    public MainMenu getMenu() {
        return startApp;
    }

    public int getSelLang() {
        return activLang;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        source = ae.getSource();

        if (source == quit) {
            System.exit(0);
        }

        if (source == users) {
            readDB(users.getSelectedItem() + "");
        }

        if (source == enter) {
            closeWindow();
            startApp = new MainMenu(strUser, this);
        }

        if (source == newAccount) {
            if (numberOfAccounts == 0) {
                //if password
                if (login.getText().length() >= 1 && login.getText().length() <= 24) {
                    createConfigFile(login.getText());
                } else {
                    JOptionPane.showMessageDialog(null, "Username should be between\n1 and 24 Characters long");
                }
            } else if (numberOfAccounts > 0) {
                //extra account
                newAcc = JOptionPane.showInputDialog(null, "Enter new account name", "Enter account name", JOptionPane.INFORMATION_MESSAGE);
                if (newAcc.length() >= 1 && newAcc.length() <= 24) {
                    createConfigFile(newAcc);
                } else {
                    JOptionPane.showMessageDialog(null, "Username should be between\n1 and 24 Characters long");
                }
            }
        }

        if (source == userAccount) {
            closeWindow();
            startApp = new MainMenu(strUser, this);
        }
    }

    /**
     * @author Java2s
     * @see {http://www.java2s.com/Code/Java/Swing-JFC/HowtochangethelookandfeelofSwingapplications.htm}
     * Changes the look and feel. It requires the
     * Java Runtime Environment version 6 update 18
     * or higher to work. If the JRE is below update 18
     * it resorts to the default Java metal look and feel
     */
    private void changeTheLookAndFeel(int num) {
        switch (num) {
            case 0: {
                try {
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                    SwingUtilities.updateComponentTreeUI(this);
                } catch (Exception fail) {
                    changeTheLookAndFeel(1);
                }
            }
            break;

            case 1: {
                try {
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
                    SwingUtilities.updateComponentTreeUI(this);
                } catch (Exception ex) {
                }
            }
            break;

            case 2: {
                try {
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
                    SwingUtilities.updateComponentTreeUI(this);
                } catch (Exception el) {
                    changeTheLookAndFeel(0);
                }
            }
            break;
        }
    }

    public String getNetworkStatus() {
        return networkStatus;
    }

    public boolean isConnected() {
        return connected;
    }

    /**
     * Checks for new versions of the game
     *
     * @param caller - default on startup of player in menu
     * @return true of false
     */
    private void newVersionOutThere(String caller) {
        ans = true;
        whoCalled = caller;

        new Thread() {

            @Override
            public void run() {
                try {
                    //download the file
                    if (downloadUpdateFile(updateFileURL, fileName)) {
                        //read the file
                        try {
                            br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF8"));
                            do {
                                sx = br.readLine();
                                if (sx != null) {
                                    tx = tx + sx + "";
                                }
                            } while (sx != null);
                            br.close();
                        } catch (IOException e) {
                            System.out.println("Couldn't read update file");
                        }
                        webVersion = "";
                        updatesArr = null;
                        try {
                            String start = "<latestVer>";
                            System.out.println(tx);
                            webVersion = tx.substring(tx.indexOf(start) + start.length(), tx.indexOf("</latestVer>"));
                            System.out.println("Latest version: " + webVersion);

                            start = "<fileName>";
                            fname = tx.substring(tx.indexOf(start) + start.length(), tx.indexOf("</fileName>"));
                            System.out.println("Latest version: " + fname);

                            updates = Integer.parseInt(tx.substring(tx.indexOf("<updates>") + 9, tx.indexOf("</updates>")));
                            updatesArr = new String[updates];

                            for (int i = 0; i < updates; i++) {
                                updatesArr[i] = "\n- " + tx.substring(tx.indexOf("<update" + (i + 1) + ">") + 9, tx.indexOf("</update" + (i + 1) + ">"));
                            }
                        } catch (Exception e) {
                            System.err.println(e.toString());
                        } //if latest version, just try and download music
                        if (webVersion.equalsIgnoreCase(RenderGameplay.getInstance().getVersionStr())) {
                            ans = false;
                            if (whoCalled.equalsIgnoreCase("default")) {
                                System.out.println("You are up to date");
                            }
                        } //show new version is available
                        else {
                            new Thread() {

                                @Override
                                public void run() {
                                    newy = new WindowUpdate(webVersion, fname, updatesArr, instance);
                                    ans = true;
                                }
                            }.start();
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Couldn't access the file" + e);
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void setIsLefty(String val) {
        isLefty = val;
        if (isLefty.equalsIgnoreCase("no")) {
            isLeftyInt = 0; //no
        } else {
            isLeftyInt = 1; //yes
        }
    }

    public int getIsLeftyInt() {
        return isLeftyInt;
    }

    /**
     * the number Achievements unlocked
     */
    public int getUnlockedAch() {
        int counter = 0;


        for (int y = 0; y
                < ach.length; y++) {
            if (ach[y] > 0) {
                counter = counter + 1;
            }
        }
        return counter;


    }

    /**
     * Set the country code
     *
     * @param me
     */
    public void setCountryCode(String me) {
        countryStr = me;


    }

    public void setActivLang(int x) {
        activLang = x;
        Language.getInstance().setLanguage(activLang);
    }

    /**
     * get the country code
     *
     * @return
     */
    public String getCCode() {
        return countryStr;


    }

    /**
     * The number Achievements unlocked
     */
    public int getATriggeredAchiev() {
        int counter = 0;


        for (int y = 0; y
                < ach.length; y++) {
            counter = counter + ach[y];


        }

        return counter;


    }

    private void newCode() {
        random_name = "scnd_";
        userIDBuff = new StringBuilder(random_name);

        letters = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};

        userIDBuff.append("").append(Math.round(Math.random() * 100)).append("_");
        v1x = Integer.parseInt("" + Math.round((Math.random() * (letters.length - 1))));
        v2x = Integer.parseInt("" + Math.round((Math.random() * (letters.length - 1))));
        v3x = Integer.parseInt("" + Math.round((Math.random() * (letters.length - 1))));
        userIDBuff.append(letters[v1x]);
        userIDBuff.append(letters[v2x]);
        userIDBuff.append(letters[v3x]);
        userIDBuff.append("_").append(Math.round(Math.random() * 10000)).append("");
        random_name = userIDBuff.toString();

        usrCode = random_name;
    }

    private void createConfigFile(String m) {
        try {

            newCode();
            stat = conn.createStatement();
            stat.executeUpdate("INSERT INTO scndsave (name, usrCode, points, time, matches, ach1, ach2, ach3, ach4, ach5, ach6, ach7, ach8, ach9, ach10, ach11, win, loss, frames, sound, difficulty, modeN, prefTime, graffix, upToDate, autoUpdate, musicFiles, char0, char1, char2, char3, char4, char5, char6, char7, char8, char9, char10, comicT, rez, rating, countryStr, controller, lang, isLefty) VALUES ('" + m + "', '" + usrCode + "', '" + scndCipher("" + 100) + "', '" + 0 + "', '" + scndCipher("" + 0) + "', '" + scndCipher("" + 0) + "', '" + scndCipher("" + 0) + "', '" + scndCipher("" + 0) + "', '" + scndCipher("" + 0) + "', '" + scndCipher("" + 0) + "', '" + scndCipher("" + 0) + "', '" + scndCipher("" + 0) + "', '" + scndCipher("" + 0) + "', '" + scndCipher("" + 0) + "', '" + scndCipher("" + 0) + "', '" + scndCipher("" + 0) + "', '" + scndCipher("" + 0) + "', '" + scndCipher("" + 0) + "', 60, 'on', '" + scndCipher("" + 3500) + "', '" + scndCipher("" + 0) + "', " + 181 + ", 'High', 'no', 'yes', 'yes', '" + scndCipher("" + 0) + "', '" + scndCipher("" + 0) + "', '" + scndCipher("" + 0) + "', '" + scndCipher("" + 0) + "', '" + scndCipher("" + 0) + "', '" + scndCipher("" + 0) + "', '" + scndCipher("" + 0) + "', '" + scndCipher("" + 0) + "', '" + scndCipher("" + 0) + "', '" + scndCipher("" + 0) + "', '" + scndCipher("" + 0) + "', '" + scndCipher("" + 2) + "', '" + scndCipher("" + 1) + "', '" + scndCipher("" + 60) + "', 'countryStr', 'false', '" + scndCipher("" + 0) + "', 'no');");
            JOptionPane.showMessageDialog(null, "New account created \nYou can now login");

        } catch (Exception e) {
            System.out.println(">>> loadsave: Could not write ");
        }


        createUserCombo();
        readDB(m);
        thisPic.repaint();
        enter.setEnabled(true);
    }

    public void saveConfigFile() {
        try {
            stat = conn.createStatement();
            stat.executeUpdate("UPDATE scndsave SET name='" + strUser + "',"
                    + " points='" + scndCipher(strPoint) + "',"
                    + " time='" + strPlayTime + "', matches='" + scndCipher(matchCountStr) + "',"
                    + " ach1='" + scndCipher("" + ach[0]) + "', ach2='" + scndCipher("" + ach[1]) + "',"
                    + " ach3='" + scndCipher("" + ach[2]) + "', ach4='" + scndCipher("" + ach[3]) + "',"
                    + " ach5='" + scndCipher("" + ach[4]) + "', ach6='" + scndCipher("" + ach[5]) + "', "
                    + " ach7='" + scndCipher("" + ach[6]) + "', ach8='" + scndCipher("" + ach[7]) + "',"
                    + " ach9='" + scndCipher("" + ach[8]) + "', ach10='" + scndCipher("" + ach[9]) + "',"
                    + " ach11='" + scndCipher("" + ach[10]) + "', win='" + scndCipher("" + win) + "',"
                    + " loss='" + scndCipher("" + loss) + "', frames=" + frames + ","
                    + " sound='" + soundStatus + "', difficulty='" + scndCipher("" + difficultyStat) + "',"
                    + " modeN='" + scndCipher("" + stage) + "', prefTime=" + timePref + ", graffix='" + graffix + "',"
                    + " upToDate='" + upToDate + "', autoUpdate='" + autoUpdate + "', musicFiles='" + downloadedAudio + "',"
                    + " char0='" + scndCipher("" + charUsage[0]) + "', char1='" + scndCipher("" + charUsage[1]) + "', char2='" + scndCipher("" + charUsage[2]) + "',"
                    + " char3='" + scndCipher("" + charUsage[3]) + "', char4='" + scndCipher("" + charUsage[4]) + "', char5='" + scndCipher("" + charUsage[5]) + "',"
                    + " char6='" + scndCipher("" + charUsage[6]) + "', char7='" + scndCipher("" + charUsage[7]) + "', char8='" + scndCipher("" + charUsage[8]) + "',"
                    + " char9='" + scndCipher("" + charUsage[9]) + "', char10='" + scndCipher("" + charUsage[10]) + "', "
                    + " comicT='" + scndCipher("" + comicPicOcc) + "', rez='" + scndCipher("" + scalePos) + "',"
                    + " rating='" + scndCipher("" + gameRating) + "', countryStr='" + countryStr + "', controller='" + controllerStr + "', lang='" + scndCipher("" + activLang) + "', "
                    + " isLefty='" + isLefty + "' WHERE usrCode='" + usrCode + "';");
            jenesisLog("Saved file");
            /**
             * Phew !!!!
             */
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private String generateString(char[] sourceChar) {
        StringBuilder sb = new StringBuilder("");


        int limit = sourceChar.length;

        //reassemble


        for (int u = 0; u
                < limit; u++) {
            sb.append("").append(sourceChar[u]);


        }

        return sb.toString();


    }

    //returns ciphered string
    private String scndCipher(String toCipher) {
        int limit = toCipher.length();
        StringBuilder epix = new StringBuilder();


        for (int y = 0; y
                < limit; y++) {
            epix.append(cipherChar(toCipher.charAt(y)));
        }
        return epix.toString();
    }

    //deciphers ciphered string
    private String scndDecipher(String textToDecipher) {
        int limit = textToDecipher.length();
        StringBuilder epix2 = new StringBuilder();
        for (int i = 0; i
                < limit; i += 4) {
            epix2.append(deCipherChar(textToDecipher.substring(i, i + 4)));
        }
        return epix2.toString();
    } //conver char to scnd cipher

    private String cipherChar(char cv) {
        String done = "";
        if (cv == '1') {
            done = "~er5";
        } else if (cv == '2') {
            done = "9!tw";
        } else if (cv == '3') {
            done = "dz@4";
        } else if (cv == '4') {
            done = "yt3h";
        } else if (cv == '5') {
            done = "1a:d";
        } else if (cv == '6') {
            done = "m;9s";
        } else if (cv == '7') {
            done = "-5cy";
        } else if (cv == '8') {
            done = "9-n0";
        } else if (cv == '9') {
            done = "2s?v";
        } else if (cv == '0') {
            done = "g7h%";
        }
        return done;
    }

    private char deCipherChar(String done) {
        char cv = '0';
        if (done.equalsIgnoreCase("~er5")) {
            cv = '1';
        } else if (done.equalsIgnoreCase("9!tw")) {
            cv = '2';
        } else if (done.equalsIgnoreCase("dz@4")) {
            cv = '3';
        } else if (done.equalsIgnoreCase("yt3h")) {
            cv = '4';
        } else if (done.equalsIgnoreCase("1a:d")) {
            cv = '5';
        } else if (done.equalsIgnoreCase("m;9s")) {
            cv = '6';
        } else if (done.equalsIgnoreCase("-5cy")) {
            cv = '7';
        } else if (done.equalsIgnoreCase("9-n0")) {
            cv = '8';
        } else if (done.equalsIgnoreCase("2s?v")) {
            cv = '9';
        } else if (done.equalsIgnoreCase("g7h%")) {
            cv = '0';
        }
        return cv;
    }

    private void closeWindow() {
        dispose();
    }

    public void showWindow() {
        setVisible(true);
    }

    public Achievements getAch() {
        return achObj;
    }

    public int getCurrentPlayTime() {
        return Integer.parseInt(strPlayTime);
    }

    public void setCurrentPlayTime(int time) {
        strPlayTime = "" + time;
    }

    public void incrementAchievement(int index) {
        ach[index] = ach[index] + 1;
        //increment move trigger
    }

    public int getPoints() {
        return Integer.parseInt(strPoint);
    }

    public void setPoints(int value) {
        strPoint = "" + value;
    }

    public void incrementMatchCount() {
        if (newGame) {
            int y = Integer.parseInt(matchCountStr) + 1;
            matchCountStr = "" + y;
            newGame = false;
            if (RenderGameplay.getInstance().getCharLife() < RenderGameplay.getInstance().getOppLife()) {
                loss = loss + 1;
                consecWins = 0;
                consecWinsTmp = 0;
            } else {
                win = win + 1;
                consecWins = consecWins + 1;
                consecWinsTmp = consecWinsTmp + 1;
            }
        }
    }

    public int userAwesomeness() {
        int total = 0;
        int returnThis = 0;
        try {
            for (int s = 0; s
                    < ach.length; s++) {
                total = total + (ach[s] * classArr[s]);


            }
            System.out.println("Style points: " + total);
            returnThis = total / getATriggeredAchiev();
        } catch (Exception e) {
            System.out.println("new user, awesomenss is newbie");
            returnThis = 0;
        }
        return returnThis;
    }

    public String getIP() {
        System.out.println("getting " + IPAdd);
        return IPAdd;
    }

    public void setIP(String thisTxt) {
        IPAdd = thisTxt;
    }

    /**
     * Downloads the update file
     */
    private boolean downloadUpdateFile(String urlStr, String file) {
        boolean managedToDownload = false;


        try {
            url = new URL(urlStr);
            in = new BufferedInputStream(url.openStream());
            out = new File(file);
            fos = new FileOutputStream(out);
            bout = new BufferedOutputStream(fos, 1024);
            data = new byte[1024];
            x = 0;


            while ((x = in.read(data, 0, 1024)) >= 0) {
                bout.write(data, 0, x);


            }
            bout.close();
            in.close();
            managedToDownload = true;


        } catch (Exception e) {
            System.err.println(e);
        }

        return managedToDownload;


    }

    private void downloadMusicFile(String urlStr, String directory, String file, long size) {

        final String urlF = urlStr, directoryF = directory, fileF = file;
        final long sizeF = size;


        try {
            musicPerc = 0;
            url = new URL(urlF);
            in = new BufferedInputStream(url.openStream());
            out = new File(directoryF + "/" + fileF);
            currentFile = fileF;
            trackSize = sizeF;
            fos = new FileOutputStream(out);
            bout = new BufferedOutputStream(fos, 1024);
            data = new byte[1024];
            x = 0;
            System.out.println("Size of file " + trackSize);


            while ((x = in.read(data, 0, 1024)) != -1) {
                thisPic.repaint();
                bout.write(data, 0, x);


                long currSize = out.length();
                //System.outR.println("Size in kb "+currSize);
                musicPerc = Math.round(((float) currSize / (float) trackSize) * 100);
                System.out.println("Download Percent: " + musicPerc + "%");
                if (startApp != null) {
                    startApp.sytemNotice("Downloading track " + musicPerc + "%");
                }

            }
            bout.close();
            in.close();
            musicPerc = 100;


        } catch (Exception e) {
        }
    }

    public void downloadMusic() {
        new Thread() {

            public void run() {
                if (startApp != null) {
                    startApp.sytemNotice("Trying to download music bro");
                }
                //download the file
                if (downloadUpdateFile(updateFileURL, fileName)) {
                    //make audio directory
                    String directory = "audio";


                    new File(directory).mkdir();
                    isDownloadingMusic = true;
                    thisPic.repaint();
                    //find outR the number of trax needed
                    //read the file
                    try {
                        br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF8"));


                        do {
                            sx = br.readLine();


                            if (sx != null) {
                                tx = tx + sx + "";


                            }
                        } while (sx != null);
                        br.close();


                    } catch (IOException e) {
                        System.out.println("Couldn't read update file");


                    }

                    try {
                        int numOfTraxToDownload = Integer.parseInt(tx.substring(tx.indexOf("<totalTrax>") + 11, tx.indexOf("</totalTrax>")));
                        System.out.println("We have " + numOfTraxToDownload + " Files to download");


                        for (int u = 1; u
                                <= numOfTraxToDownload; u++) {
                            if (u < 10) {
                                currentTrack = tx.substring(tx.indexOf("<trax" + u + ">") + 7, tx.indexOf("</trax" + u + ">"));
                                fileNameMus = tx.substring(tx.indexOf("<name" + u + ">") + 7, tx.indexOf("</name" + u + ">"));
                                currentTrackSize = Long.parseLong(tx.substring(tx.indexOf("<size" + u + ">") + 7, tx.indexOf("</size" + u + ">")));
                                System.out.println("Parsed " + currentTrackSize);


                            } else//float digit songs
                            {
                                currentTrack = tx.substring(tx.indexOf("<trax" + u + ">") + 8, tx.indexOf("</trax" + u + ">"));
                                currentTrackSize = Long.parseLong(tx.substring(tx.indexOf("<size" + u + ">") + 8, tx.indexOf("</size" + u + ">")));
                                fileNameMus = tx.substring(tx.indexOf("<name" + u + ">") + 8, tx.indexOf("</name" + u + ">"));


                            }

                            downloadMusicFile(currentTrack, directory, fileNameMus, currentTrackSize);


                        }
                        isDownloadingMusic = false;
                        thisPic.repaint();
                        downloadedAudio = "yes";
                        saveConfigFile();


                    } catch (Exception e) {
                        System.err.println("What " + e);
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Download Percent: " + musicPerc + "%");
                    if (startApp != null) {
                        startApp.sytemNotice("Can't download audio files");
                    }
                }
            }
        }.start();
    }

    /**
     * Increments character usage, to determine usage
     *
     * @param dude
     */
    public void incrementCharUsage(int dude) {
        charUsage[dude] = charUsage[dude] + 1;
    }

    /**
     * Returns an int, corresponding to the index of the name of the most popular character
     *
     * @return index of most popular dude
     */
    public int mostPopularChar() {
        int max = charUsage[0];
        int dex = 0;
        for (int u = 0; u
                < charUsage.length; u++) {
            if (charUsage[u] > max) {
                max = charUsage[u];
                dex = u;
            }
        }
        return dex;
    }

    public int mostPopularCharPercentage() {
        float ans = 0;
        float count = 0;
        for (int u = 0; u
                < charUsage.length; u++) {
            count = count + charUsage[u];
            //getting the total
        }
        ans = ((float) charUsage[mostPopularChar()] / (float) count) * 100;
        return Integer.parseInt("" + Math.round(ans));
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_ENTER) {
            closeWindow();
            startApp = new MainMenu(strUser, this);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    public Font getMyFont(float s) {
        Font fnt = null;
        try {
            fnt = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("font/Sawasdee.ttf"));
            fnt = fnt.deriveFont(s + 2);
        } catch (Exception re) {
            fnt = new Font("Sans", Font.PLAIN, (int) s + 2);
        }
        return fnt;
    }

    public void trayMessage(String s, String m) {
        //trayIcon.displayMessage(s, m, TrayIcon.MessageType.INFO);
    }

    private void loadTray() {
        /*
        if (SystemTray.isSupported()) {
        imageLoader = new JenesisImageLoader();
        tray = SystemTray.getSystemTray();
        image = imageLoader.loadBufferedImage("images/GameIco16.png");
        
        popup = new PopupMenu();
        trayIcon = new TrayIcon(image, "The SCND Genesis: Legends", null);
        trayIcon.setToolTip("The SCND Genesis: Legends");
        try {
        tray.add(trayIcon);
        } catch (Exception e) {
        System.err.println("Whoopsie " + e.getMessage());
        }
        }*/
    }
}

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

import com.scndgen.legends.drawing.DrawUserLogin;
import com.scndgen.legends.enums.CharacterEnum;
import com.scndgen.legends.render.RenderGameplay;
import com.scndgen.legends.windows.WindowUpdate;
import io.github.subiyacryolite.enginev1.JenesisImageLoader;
import io.github.subiyacryolite.enginev1.JenesisWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.net.URL;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is a fighting game based on my webcomic THE SCND GENESIS
 * http://www.scndgenesis.50webs.com
 * The battle system is inspired by Final Fantasy XIII and conventional 2D Fighters
 * The game has a handdrawn comic book graphical style similar to the comic and has other anime style effects
 * Feel free to tell achievements what you think ABOUT the game and comic ifungandana(at)gmail.com
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
 * 15/08/10 - Added match timer and OPTIONS 0.0.4.1
 * 11/08/10 - Converted timertasks to threads, added arena select, fixed relative sound path 0.0.4.4
 * 19/09/10 - caches all sprites, Serious thread optimizations, AI rewrites, SIGNIFICANT performance improvements 0.0.5.5
 * 22/09/10 - implemented LAN play!!!! Game chat, lobby system needed 0.0.6.5
 * 22/09/10 - implemented login screen, ciphering 0.0.6.7
 * 18/10/10 - implemented in-game chat, utf8 encoding, STATS screen, achievement structure, game/profile save 0.0.7.3
 * 21/10/10 - added overworld map, began navigation and screen control 0.0.7.7
 * 23/10/10 - added collision detection algorythm for overworld 0.0.8.0
 * 27/10/10 - added new menu, transition, characterEnum classess, remved command panel 0.0.8.4
 * 30/10/10 -added scene menu, server-client embeddedin menu, match making/lobby system ACTUALLY WORKS!!!!!! 0.0.8.7
 * 03/11/10 -24- added new CharacterEnum, Aisha 0.0.8.8
 * 03/11/10 -25- added limit break system, fixed LAN bugs - 0.0.9.0
 * 17/11/10 -26- better ABOUT screen, new versioning system ( major version | minor revision | updates/fixes ) 0.0.9.1
 * 20/11/10 -29- STORY MODE STRUCURE!!! Storymode bug-fixes, OPTIONS and STATS integrated into menu
 * 23/11/10 -30- fixed story scene and added pause, skip, resume :D
 * 02/12/10 -31- MOUSE INPUT :D, Fixed sound structure,Music pauses, added framrate chooser, sound-on/off works
 * 04/12/10 -32- One story scene to rule them all, bwa ha ha, added scene select as isWithinRange :)
 * 07/12/10 -33- Added background animation thread
 * 10/12/10 -34- Added Ravage, implemented characterEnum balance scheme, fixed bug in story scene thread
 * Sidenote 14/12/10: Joined Twitter ^_^
 * 15/12/10 -35- Added characterEnum Ade, Added quit game, resume, EXIT to gameplay. New achievement pics, mod to primaryNotice(), better figures, sexy transparent HUD
 * 17/12/10 -36- Added new stages "Scorched Ruins" and "Frozen Wilderness"
 * 23/12/10 - Started porting the game to c++, evident performance benefits, fixed threads.
 * 27/12/10 -37- Realised how much I love Java, cross pompiling on C++ sucks, smoothened animations and initiate menu screen
 * 01/1/11 -38- Thread optimisations. wee
 * 04/1/11 -39- Fixed story scene bugs, adding GPL headerz gon opensource
 * 13/1/11 -40- Changed main menu, ditched runtime flipping for pre rendered images (opponents), performance benefits
 * 14/1/11 -41- Integrated STATS into main menu, pending for connections can be cancelled
 * 15/1/11 -42- Backwards compatibility for new save items, fixed timeLimit
 * 15/1/11 -43- Changelog moved to WindowAbout.java in text3
 * 13/2/11 -44- I'm baaaaack, changelog in WindowAbout is clientSide only, codies go here
 * 13/2/11 -44- Fixed bug, reset move to physical at new match
 *
 * @author Ndana
 */

public class LoginScreen extends JFrame implements ActionListener, KeyListener {

    public static final CharacterEnum[] charNames = CharacterEnum.values();
    public int difficultyBase = 8000, difficultyScale = 1333;
    public static final String configLoc = System.getProperty("user.home") + File.separator + ".config" + File.separator + "scndgen" + File.separator;
    public static int normalTxtSize = 14, bigTxtSize = 20, extraTxtSize = 26;
    private static LoginScreen instance;
    public boolean ans, newMatch = true;
    public int difficultyDyn;
    public String whoCalled, networkStatus;
    public boolean connected = true;
    public String fileName = configLoc + "scndupd.xml";
    public int[] classArr;
    public int updates;
    private String  newAcc;
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
    private JenesisWindow startApp;
    private String IPAdd;
    private Image image;
    private SystemTray tray;
    private PopupMenu popup;
    private MenuItem defaultItem;
    private TrayIcon trayIcon;
    private String[] updatesArr, countries;
    private Achievements achObj;
    private boolean noUsers, oldFile, newFile;
    private boolean[] foundAch;
    private int searchIndex;
    private String ansc, webVersion, sx, tx = "", updateFileURL = "http://scndgen.sourceforge.net/game/scndupd.xml";
    //private String ansc, webVersion, country, sx, tx = "", updateFileURL = "http://localhost/scnd/game2/scndupd.xml";
    private String fname;

    @SuppressWarnings("LeakingThisInConstructor")
    private LoginScreen() throws FileNotFoundException {
        countries = Locale.getISOCountries();
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
        achObj = new Achievements(this);
        classArr = new int[11];
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
     * Create a combo box with all users listed
     */
    private void createUserCombo() {
        users = new JComboBox();
        for (GameSave gs : GameState.getInstance().getGameSaves()) {
            users.addItem(gs.toString());
        }
        if (GameState.getInstance().getGameSaves().size() == 0) {
            noUsers = true;
            login.setText("");
            enter.setEnabled(false);
            pan4.remove(enter);
        } else {
            noUsers = false;
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
     * Get instance of our scene select menu
     *
     * @return
     */
    public JenesisWindow getMenu() {
        return startApp;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        source = ae.getSource();

        if (source == quit) {
            System.exit(0);
        }

        if (source == enter) {
            closeWindow();
            startApp = new JenesisWindow(strUser, this);
        }

        if (source == newAccount) {
            if (GameState.getInstance().getGameSaves().size() == 0) {
                //if password
                if (login.getText().length() >= 1 && login.getText().length() <= 24) {
                    createUserCombo();
                    enter.setEnabled(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Username should be between\n1 and 24 CharacterEnum long");
                }
            } else {
                //extra account
                newAcc = JOptionPane.showInputDialog(null, "Enter new account name", "Enter account name", JOptionPane.INFORMATION_MESSAGE);
                if (newAcc.length() >= 1 && newAcc.length() <= 24) {
                    createUserCombo();
                    enter.setEnabled(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Username should be between\n1 and 24 CharacterEnum long");
                }
            }
        }

        if (source == userAccount) {
            closeWindow();
            startApp = new JenesisWindow(strUser, this);
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
                        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF8"))) {
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
                    e.printStackTrace(System.err);
                }
            }
        }.start();
    }

    /**
     * the number Achievements unlocked
     */
    public int getUnlockedAch() {
        int counter = 0;
        for (int y = 0; y < ach.length; y++) {
            if (ach[y] > 0) {
                counter = counter + 1;
            }
        }
        return counter;
    }

    /**
     * The number Achievements unlocked
     */
    public int getNumberOfTimesAchivementTriggered() {
        int counter = 0;
        for (int y = 0; y < ach.length; y++) {
            counter = counter + ach[y];
        }
        return counter;
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

    public void incrementMatchCount() {
        if (newMatch) {
            int y = Integer.parseInt(numberOfMatches) + 1;
            numberOfMatches = "" + y;
            newMatch = false;
            if (RenderGameplay.getInstance().getCharLife() < RenderGameplay.getInstance().getOppLife()) {
                losses = losses + 1;
                consecWins = 0;
                consecWinsTmp = 0;
            } else {
                wins = wins + 1;
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
            returnThis = total / getUnlockedAch();
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
        int bufferSize = 1024;
        File out = new File(file);
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new URL(urlStr).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(out);
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream, bufferSize)) {
            byte[] data = new byte[bufferSize];
            int x;
            while ((x = bufferedInputStream.read(data, 0, bufferSize)) >= 0) {
                bufferedOutputStream.write(data, 0, x);
            }
            bufferedOutputStream.close();
            bufferedInputStream.close();
            managedToDownload = true;
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return managedToDownload;
    }


    /**
     * Returns an int, corresponding to the index of the name of the most popular characterEnum
     *
     * @return index of most popular opponent
     */
    public int mostPopularChar() {
        int max = characterUsage[0];
        int dex = 0;
        for (int u = 0; u
                < characterUsage.length; u++) {
            if (characterUsage[u] > max) {
                max = characterUsage[u];
                dex = u;
            }
        }
        return dex;
    }

    public int mostPopularCharPercentage() {
        float ans = 0;
        float count = 0;
        for (int u = 0; u
                < characterUsage.length; u++) {
            count = count + characterUsage[u];
            //getting the total
        }
        ans = ((float) characterUsage[mostPopularChar()] / (float) count) * 100;
        return Integer.parseInt("" + Math.round(ans));
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_ENTER) {
            closeWindow();
            startApp = new JenesisWindow(strUser, this);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}

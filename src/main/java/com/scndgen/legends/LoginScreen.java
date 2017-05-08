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
import com.scndgen.legends.state.GameState;
import com.scndgen.legends.state.LoginState;
import com.scndgen.legends.windows.WindowUpdate;
import io.github.subiyacryolite.enginev1.JenesisImageLoader;
import io.github.subiyacryolite.enginev1.JenesisWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

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
 * 11/08/10 - Converted timer tasks to threads, added arena select, fixed relative sound path 0.0.4.4
 * 19/09/10 - caches all sprites, Serious thread optimizations, AI rewrites, SIGNIFICANT performance improvements 0.0.5.5
 * 22/09/10 - implemented LAN play!!!! Game chat, lobby system needed 0.0.6.5
 * 22/09/10 - implemented login screen, ciphering 0.0.6.7
 * 18/10/10 - implemented in-game chat, utf8 encoding, STATS screen, achievement structure, game/profile save 0.0.7.3
 * 21/10/10 - added over world map, began navigation and screen control 0.0.7.7
 * 23/10/10 - added collision detection algorythm for overworld 0.0.8.0
 * 27/10/10 - added new menu, transition, characterEnum classess, remved command panel 0.0.8.4
 * 30/10/10 -added scene menu, server-client embedded in menu, match making/lobby system ACTUALLY WORKS!!!!!! 0.0.8.7
 * 03/11/10 -24- added new CharacterEnum, Aisha 0.0.8.8
 * 03/11/10 -25- added limit break system, fixed LAN bugs - 0.0.9.0
 * 17/11/10 -26- better ABOUT screen, new versioning system ( major version | minor revision | updates/fixes ) 0.0.9.1
 * 20/11/10 -29- STORY MODE STRUCURE!!! Storymode bug-fixes, OPTIONS and STATS integrated into menu
 * 23/11/10 -30- fixed story scene and added togglePause, skip, resume :D
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

public class LoginScreen extends JFrame {

    public static final CharacterEnum[] charNames = CharacterEnum.values();
    public static final String configLoc = System.getProperty("user.home") + File.separator + ".config" + File.separator + "scndgen" + File.separator;
    public static int normalTxtSize = 14, bigTxtSize = 20, extraTxtSize = 26;
    private static LoginScreen instance;
    private String newAcc;
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
    private boolean noUsers, oldFile, newFile;
    private boolean[] foundAch;
    private int searchIndex;
    //private String ansc, webVersion, country, sx, tx = "", updateFileURL = "http://localhost/scnd/game2/scndupd.xml";


    @SuppressWarnings("LeakingThisInConstructor")
    private LoginScreen() throws FileNotFoundException {
        pan1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pan2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        userName = new JLabel("User Name: ");
        login = new JTextField("", 10);
        pan2.add(userName);
        pan2.add(login);
        pan4 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        enter = new JButton("Load Selected Account");
        quit = new JButton("Quit");
        newAccount = new JButton("Create a New Account");
        pan4.add(newAccount);
        box.add(pan1);
        box.add(pan2);
        box.add(pan4);
        add(box);
        createUserCombo();
        thisPic = new DrawUserLogin(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("The SCND Genesis: Legends" + RenderGameplay.getInstance().getVersionStr());
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    public static LoginScreen getInstance() {
        return instance;
    }

    /**
     * Create a combo box with all users listed
     */
    private void createUserCombo() {
        users = new JComboBox();
        for (LoginState gs : GameState.getInstance().getLoginStates()) {
            users.addItem(gs.toString());
        }
        if (GameState.getInstance().getLoginStates().size() == 0) {
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

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == newAccount) {
            if (GameState.getInstance().getLoginStates().size() == 0) {
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
    }

    private void closeWindow() {
        dispose();
    }

    public void showWindow() {
        setVisible(true);
    }

    public String getIP() {
        System.out.println("getting " + IPAdd);
        return IPAdd;
    }

    public void setIP(String thisTxt) {
        IPAdd = thisTxt;
    }
}

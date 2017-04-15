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
import java.awt.event.*;

/**
 * @author: Ifunga Ndana
 * @class: WindowOptions
 * This class contains the games options
 */
public class WindowOptions extends JFrame implements ActionListener, ItemListener, KeyListener {

    public static int txtSpeed = 200, one;
    public static int diff0 = 0,
            diff1 = 1000,
            diff2 = 2500,
            diff3 = 3500,
            diff4 = 4500,
            diff5 = 6000;
    public static String graphics;
    public static int time;
    public int[] txtSpeedArr = new int[]{50, 100, 200, 250};
    public boolean downloadingMusic, isSoundOn = true;
    public int[] Arr = {diff0, diff1, diff2, diff3, diff4, diff5};
    public String charPrevLoc = "images/trans.png", oppPrevLoc = "images/trans.png";
    private Object source;
    private JToggleButton[] ratings;
    private JToggleButton soundOn, soundOff, updateOn, updateOff, star1, star2, star3, star4, star5;
    private ButtonGroup soundBG, updateOptions;
    private JLabel ratingL, opLabel1, opLabel2, opLabel3, opLabel4, opLabel5, opLabel6, opLabel7, opLabel8, opLabel9, opLabel10, opLabel11;
    private JComboBox diffSett, resSett, langSett;
    private String[] comicTxtFreqStr;
    private String[] mode;
    private String[] diffSettOpt;
    private JCheckBox cont;
    private JPanel panGameRating, pan2l, pan3, panDifficulty, panSound, panTimeDur, panTextSpeed, panGraffix, panFrameRate, opPanelSave, panUpdates, panComicText, panController, panLefty, panLanguage, panMusic;
    private Box box;
    private JComboBox lefty, timeDur, txtSpeedCombo, graphicsCombo, frameR, comicT;
    private String[] times = {"infinite", "180", "150", "120", "90", "60", "30"};
    private String[] frames = {"25", "30", "40", "60", "75", "120"};
    private String[] preset;
    private JButton reset, save, updateButton, audio;
    private int gameRating;
    private SpecialDrawMenuBGs logoPic;
    private Font normalFont;

    /**
     * Constructor
     */
    @SuppressWarnings("LeakingThisInConstructor")
    public WindowOptions() {
        normalFont = LoginScreen.getInstance().getMyFont(LoginScreen.normalTxtSize - 2);
        box = new Box(BoxLayout.Y_AXIS);
        box.setOpaque(false);

        logoPic = new SpecialDrawMenuBGs();
        comicTxtFreqStr = new String[]{Language.getInstance().getLine(1), Language.getInstance().getLine(2), Language.getInstance().getLine(3), Language.getInstance().getLine(4)};
        mode = new String[]{Language.getInstance().getLine(22), Language.getInstance().getLine(23), Language.getInstance().getLine(24), Language.getInstance().getLine(25)};
        diffSettOpt = new String[]{Language.getInstance().getLine(26), Language.getInstance().getLine(27), Language.getInstance().getLine(28), Language.getInstance().getLine(29), Language.getInstance().getLine(30)};
        preset = new String[]{Language.getInstance().getLine(32), Language.getInstance().getLine(33)};

        gameRating = 0;

        reset = new JButton(Language.getInstance().getLine(31));
        reset.setPreferredSize(new Dimension(64, 32));
        reset.addActionListener(this);

        audio = new JButton("Download Audio Files Now");
        audio.addActionListener(this);

        star1 = new JToggleButton("★");
        star1.setToolTipText("1 out of 5");
        star1.addActionListener(this);
        star2 = new JToggleButton("★★");
        star2.setToolTipText("2 out of 5");
        star2.addActionListener(this);
        star3 = new JToggleButton("★★★");
        star3.setToolTipText("3 out of 5");
        star3.addActionListener(this);
        star4 = new JToggleButton("★★★★");
        star4.setToolTipText("4 out of 5");
        star4.addActionListener(this);
        star5 = new JToggleButton("★★★★★");
        star5.setToolTipText("5 out of 5");
        star5.addActionListener(this);

        ratings = new JToggleButton[]{star1, star2, star3, star4, star5};
        pan2l = new JPanel(new FlowLayout());
        pan2l.add(reset);
        pan2l.add(ratings[0]);
        pan2l.add(ratings[1]);
        pan2l.add(ratings[2]);
        pan2l.add(ratings[3]);
        pan2l.add(ratings[4]);

        panMusic = new JPanel(new FlowLayout());
        panMusic.add(audio);

        ratingL = new JLabel(Language.getInstance().getLine(5) + gameRating + "/10");
        alterJLabel(ratingL);
        panGameRating = new JPanel(new GridLayout(1, 2));
        panGameRating.add(ratingL);
        pan2l.setOpaque(false);
        panGameRating.add(pan2l);

        /*
        countries = Locale.getISOCountries();
        nation = new JComboBox(countries);
        nation.setSelectedItem(LoginScreen.getInstance().getCCode());
         */

        diffSett = new JComboBox(diffSettOpt);
        diffSett.setSelectedIndex(whichOne());
        diffSett.addActionListener(this);
        opLabel1 = new JLabel(Language.getInstance().getLine(6));
        panDifficulty = new JPanel(new GridLayout(1, 2));
        panDifficulty.add(opLabel1);
        panDifficulty.add(diffSett);

        /*
        resSett = new JComboBox(reso);
        resSett.setSelectedIndex(LoginScreen.scalePos);
        resSett.addActionListener(this);
        opLabel10 = new JLabel("Screen resolution: ");
        panLefty = new JPanel(new GridLayout(1, 2));
        panLefty.add(opLabel10);
        panLefty.add(resSett);*/

        txtSpeedCombo = new JComboBox(mode);
        txtSpeedCombo.setSelectedIndex(2);
        txtSpeedCombo.addActionListener(this);
        opLabel4 = new JLabel(Language.getInstance().getLine(7));
        panTextSpeed = new JPanel(new GridLayout(1, 2));
        panTextSpeed.add(opLabel4);
        panTextSpeed.add(txtSpeedCombo);

        opLabel5 = new JLabel(Language.getInstance().getLine(8));
        graphicsCombo = new JComboBox(preset);
        graphics = LoginScreen.getInstance().graffix;
        graphicsCombo.setSelectedItem(LoginScreen.getInstance().graffix);
        graphicsCombo.addActionListener(this);
        graphicsCombo.setToolTipText(Language.getInstance().getLine(9));
        panGraffix = new JPanel(new GridLayout(1, 2));
        panGraffix.add(opLabel5);
        panGraffix.add(graphicsCombo);

        frameR = new JComboBox(frames);
        frameR.addActionListener(this);
        frameR.setSelectedItem(LoginScreen.getInstance().frames + "");
        opLabel6 = new JLabel(Language.getInstance().getLine(10));
        panFrameRate = new JPanel(new GridLayout(1, 2));
        panFrameRate.add(opLabel6);
        panFrameRate.add(frameR);

        opLabel2 = new JLabel(Language.getInstance().getLine(11));
        soundOn = new JToggleButton(Language.getInstance().getLine(12));
        soundOff = new JToggleButton(Language.getInstance().getLine(13));
        soundOn.addActionListener(this);
        soundOff.addActionListener(this);

        if (LoginScreen.getInstance().soundStatus.equalsIgnoreCase("on")) {
            soundOn.setSelected(true); //on by default
        } else {
            soundOff.setSelected(true);
        }
        soundBG = new ButtonGroup();
        soundBG.add(soundOn);
        soundBG.add(soundOff);

        panSound = new JPanel(new GridLayout(1, 3));
        panSound.add(opLabel2);
        panSound.add(soundOn);
        panSound.add(soundOff);
        panSound.setOpaque(false);

        opLabel3 = new JLabel(Language.getInstance().getLine(14));
        timeDur = new JComboBox(times);
        timeDur.setSelectedItem("" + LoginScreen.getInstance().timePref);
        timeDur.addActionListener(this);
        panTimeDur = new JPanel(new GridLayout(1, 2));
        panTimeDur.add(opLabel3);
        panTimeDur.add(timeDur);

        opLabel7 = new JLabel(Language.getInstance().getLine(15));
        updateOn = new JToggleButton(Language.getInstance().getLine(12));
        updateOff = new JToggleButton(Language.getInstance().getLine(13));
        updateOn.addActionListener(this);
        updateOff.addActionListener(this);


        pan3 = new JPanel();
        updateButton = new JButton(Language.getInstance().getLine(16));
        updateButton.addActionListener(this);
        pan3.add(updateButton);
        /*
        countryCode = new JLabel("Country Code :");
        pan3.add(countryCode);
        pan3.add(nation);
         */
        if (LoginScreen.getInstance().autoUpdate.equalsIgnoreCase("yes")) {
            updateOn.setSelected(true); //on by default
        } else {
            updateOff.setSelected(true);
        }
        updateOptions = new ButtonGroup();
        updateOptions.add(updateOn);
        updateOptions.add(updateOff);
        panUpdates = new JPanel(new GridLayout(1, 2));
        panUpdates.add(opLabel7);
        panUpdates.add(updateOn);
        panUpdates.add(updateOff);

        panLefty = new JPanel(new GridLayout(1, 2));
        lefty = new JComboBox(new String[]{Language.getInstance().getLine(172), Language.getInstance().getLine(171)});
        lefty.setSelectedIndex(LoginScreen.getInstance().getIsLeftyInt());
        lefty.addActionListener(this);
        opLabel10 = new JLabel(Language.getInstance().getLine(173));
        panLefty.add(opLabel10);
        panLefty.add(lefty);

        comicT = new JComboBox(comicTxtFreqStr);
        comicT.setSelectedIndex(LoginScreen.getInstance().comicPicOcc);
        comicT.addActionListener(this);
        opLabel8 = new JLabel(Language.getInstance().getLine(17));
        panComicText = new JPanel(new GridLayout(1, 2));
        panComicText.add(opLabel8);
        panComicText.add(comicT);

        cont = new JCheckBox();
        cont.addItemListener(this);
        if (LoginScreen.getInstance().controller) {
            cont.setSelected(true);
        } else {
            cont.setSelected(false);
        }
        opLabel9 = new JLabel(Language.getInstance().getLine(18));
        panController = new JPanel(new GridLayout(1, 2));
        panController.add(opLabel9);
        panController.add(cont);

        langSett = new JComboBox(Language.getInstance().getSupportedLanguages());
        langSett.setSelectedIndex(LoginScreen.getInstance().getSelLang());
        langSett.addActionListener(this);
        opLabel11 = new JLabel(Language.getInstance().getLine(19));
        panLanguage = new JPanel(new GridLayout(1, 2));
        panLanguage.add(opLabel11);
        panLanguage.add(langSett);


        save = new JButton(Language.getInstance().getLine(20));
        save.addActionListener(this);

        opPanelSave = new JPanel(new FlowLayout());
        opPanelSave.add(save);


        panLanguage.setOpaque(false);
        //panGameRating.setOpaque(false);
        panLefty.setOpaque(false);
        panFrameRate.setOpaque(false);
        panDifficulty.setOpaque(false);
        panSound.setOpaque(false);
        panTimeDur.setOpaque(false);
        panTextSpeed.setOpaque(false);
        panUpdates.setOpaque(false);
        panComicText.setOpaque(false);
        //panController.setOpaque(false)
        opPanelSave.setOpaque(false);


        alterJLabel(opLabel9);
        alterJLabel(opLabel1);
        alterJLabel(opLabel2);
        alterJLabel(opLabel3);
        alterJLabel(opLabel4);
        alterJLabel(opLabel5);
        alterJLabel(opLabel6);
        alterJLabel(opLabel7);
        alterJLabel(opLabel8);
        alterJLabel(opLabel9);
        alterJLabel(opLabel10);
        alterJLabel(opLabel11);

        box.add(panLanguage);
        //box.add(panGameRating);
        box.add(panLefty);
        //box.add(panFrameRate);
        box.add(panDifficulty);
        box.add(panSound);
        box.add(panTimeDur);
        box.add(panTextSpeed);
        //box.add(panUpdates);
        box.add(panComicText);
        //box.add(panController);
        box.add(opPanelSave);

        setTitle(Language.getInstance().getLine(34));
        setUndecorated(true);
        setContentPane(logoPic);
        add(box);
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addKeyListener(this);
        requestFocusInWindow();
        setFocusable(true);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        setRating(LoginScreen.getInstance().getGameRating() / 20);
    }

    /**
     * Sorts difficulty
     *
     * @return difficulty array index
     */
    public static int whichOne() {
        one = 0;
        //500,2500, 3500, 4500, 6500

        if (LoginScreen.getInstance().difficultyStat == diff0) {
            one = 0;
        }

        if (LoginScreen.getInstance().difficultyStat == diff1) {
            one = 1;
        }

        if (LoginScreen.getInstance().difficultyStat == diff2) {
            one = 2;
        }

        if (LoginScreen.getInstance().difficultyStat == diff3) {
            one = 3;
        }

        if (LoginScreen.getInstance().difficultyStat == diff4) {
            one = 4;
        }

        if (LoginScreen.getInstance().difficultyStat == diff5) {
            one = 5;
        }

        return one;
    }

    public void alterJLabel(JLabel l) {
        l.setForeground(Color.WHITE);
        l.setFont(normalFont);
    }

    @Override
    public void itemStateChanged(ItemEvent ie) {
        if (ie.getStateChange() == ItemEvent.SELECTED) {
            LoginScreen.getInstance().controllerStr = "true";
        } else {
            LoginScreen.getInstance().controllerStr = "false";
        }
    }

    private void resetRating() {
        for (int u = 0; u < 5; u++) {
            ratings[u].setEnabled(true);
        }
    }

    private void setRating(int num) {
        if (num > -1 && num < 6) {
            for (int u = 0; u < ratings.length; u++) {
                ratings[u].setEnabled(true);
            }

            //set new val
            for (int u = 0; u < num; u++) {
                ratings[u].setEnabled(false);
            }
            gameRating = num;
            LoginScreen.getInstance().setGameRating(gameRating);
            ratingL.setText(Language.getInstance().getLine(35) + gameRating + "/5");
            LoginScreen.getInstance().saveConfigFile();
        }
    }

    /**
     * Action performed method
     *
     * @param ae - action event
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        source = ae.getSource();

        for (int um = 0; um < 5; um++) {
            if (source == ratings[um]) {
                setRating(um + 1);
            }
        }

        if (source == audio) {
            if (!downloadingMusic) {
                LoginScreen.getInstance().downloadMusic();
                downloadingMusic = true;
            }
        }

        if (source == lefty) {
            String val = "";

            if (lefty.getSelectedIndex() == 0) {
                val = "no";
            } else {
                val = "yes";
            }

            LoginScreen.getInstance().setIsLefty(val);
        }
        if (source == reset) {
            resetRating();
        } else if (source == frameR) {
            LoginScreen.getInstance().frames = Integer.parseInt(frameR.getSelectedItem() + "");
        } else if (source == comicT) {
            LoginScreen.getInstance().comicPicOcc = comicT.getSelectedIndex();
        } else if (source == resSett) {
            LoginScreen.getInstance().scalePos = resSett.getSelectedIndex();
        } else if (source == langSett) {
            Language.getInstance().setLanguage(langSett.getSelectedIndex());
            LoginScreen.getInstance().setActivLang(langSett.getSelectedIndex());
        } else if (source == diffSett) {
            LoginScreen.getInstance().difficultyDyn = Arr[diffSett.getSelectedIndex()];
            LoginScreen.getInstance().difficultyStat = Arr[diffSett.getSelectedIndex()];
            System.out.println("Diff = " + Arr[diffSett.getSelectedIndex()]);
            System.out.println("Diff Range = " + (LoginScreen.difficultyBase - Arr[diffSett.getSelectedIndex()]));
            System.out.println("Diff Range Rate = " + ((LoginScreen.difficultyBase - Arr[diffSett.getSelectedIndex()])) / LoginScreen.difficultyScale);
        } else if (source == txtSpeedCombo) {
            txtSpeed = txtSpeedArr[txtSpeedCombo.getSelectedIndex()];
        } else if (source == soundOn) {
            LoginScreen.getInstance().soundStatus = "on";
        } else if (source == soundOff) {
            LoginScreen.getInstance().soundStatus = "off";
        } else if (source == updateOn) {
            LoginScreen.getInstance().autoUpdate = "yes";
        } else if (source == updateOff) {
            LoginScreen.getInstance().autoUpdate = "no";
        } else if (source == graphicsCombo) {
            graphics = "" + graphicsCombo.getSelectedItem();
            LoginScreen.getInstance().graffix = "" + graphicsCombo.getSelectedItem();
        } else if (source == timeDur) {
            if (timeDur.getSelectedItem() == "infinite") {
                time = 182; //above limit
            } else if (timeDur.getSelectedItem() == "180") {
                time = 180;
            } else if (timeDur.getSelectedItem() == "150") {
                time = 150;
            } else if (timeDur.getSelectedItem() == "120") {
                time = 120;
            } else if (timeDur.getSelectedItem() == "90") {
                time = 90;
            } else if (timeDur.getSelectedItem() == "60") {
                time = 60;
            } else if (timeDur.getSelectedItem() == "30") {
                time = 30;
            }

            LoginScreen.getInstance().timePref = time;
        } else if (source == save) {
            //LoginScreen.getInstance().setCountryCode(nation.getSelectedItem() + "");
            LoginScreen.getInstance().saveConfigFile();
            setVisible(false);
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

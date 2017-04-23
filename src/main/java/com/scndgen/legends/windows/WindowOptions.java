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

import com.scndgen.legends.state.GameState;
import com.scndgen.legends.Language;
import com.scndgen.legends.drawing.SpecialDrawMenuBGs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * @author: Ifunga Ndana
 * @class: WindowOptions
 * This class contains the games OPTIONS
 */
public class WindowOptions extends JFrame implements ActionListener, ItemListener, KeyListener {

    public String charPrevLoc = "images/trans.png", oppPrevLoc = "images/trans.png";
    private Object source;
    private JToggleButton[] btnRating;
    private JToggleButton btnSoundOn, btnSoundOff, star1, star2, star3, star4, star5;
    private ButtonGroup soundBG;
    private JLabel ratingL, lblOption1, oplblOption2, opLabel3, lblOption4, opLabel8, opLabel9, opLabel10, opLabel11;
    private JComboBox diffSett, cmbLanguage;
    private String[] comicTxtFreqStr;
    private String[] mode;
    private String[] diffSettOpt;
    private JCheckBox cont;
    private JPanel pnlGameRating, pnl2l, pan3, panDifficulty, panSound, panTimeDur, panTextSpeed, opPanelSave, panComicText, panController, panLefty, panLanguage;
    private Box box;
    private JComboBox lefty, cmbTimeDuration, cmbTextSpeed, cmbComicTextOccurence;
    private String[] times = {"infinite", "180", "150", "120", "90", "60", "30"};
    private String[] frames = {"25", "30", "40", "60", "75", "120"};
    private String[] preset;
    private JButton btnReset, save, updateButton;
    private int gameRating;
    private SpecialDrawMenuBGs logoPic;
    //private Font normalFont;

    /**
     * Constructor
     */
    @SuppressWarnings("LeakingThisInConstructor")
    public WindowOptions() {
        box = new Box(BoxLayout.Y_AXIS);
        box.setOpaque(false);

        logoPic = new SpecialDrawMenuBGs();
        comicTxtFreqStr = new String[]{Language.getInstance().get(1), Language.getInstance().get(2), Language.getInstance().get(3), Language.getInstance().get(4)};
        mode = new String[]{Language.getInstance().get(22), Language.getInstance().get(23), Language.getInstance().get(24), Language.getInstance().get(25)};
        diffSettOpt = new String[]{Language.getInstance().get(26), Language.getInstance().get(27), Language.getInstance().get(28), Language.getInstance().get(29), Language.getInstance().get(30)};
        preset = new String[]{Language.getInstance().get(32), Language.getInstance().get(33)};

        gameRating = 0;

        btnReset = new JButton(Language.getInstance().get(31));
        btnReset.setPreferredSize(new Dimension(64, 32));
        btnReset.addActionListener(this);

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

        btnRating = new JToggleButton[]{star1, star2, star3, star4, star5};
        pnl2l = new JPanel(new FlowLayout());
        pnl2l.add(btnReset);
        pnl2l.add(btnRating[0]);
        pnl2l.add(btnRating[1]);
        pnl2l.add(btnRating[2]);
        pnl2l.add(btnRating[3]);
        pnl2l.add(btnRating[4]);

        ratingL = new JLabel(Language.getInstance().get(5) + gameRating + "/10");
        alterJLabel(ratingL);
        pnlGameRating = new JPanel(new GridLayout(1, 2));
        pnlGameRating.add(ratingL);
        pnl2l.setOpaque(false);
        pnlGameRating.add(pnl2l);

        /*
        countries = Locale.getISOCountries();
        nation = new JComboBox(countries);
        nation.setSelectedItem(LoginScreen.getInstance().getCountry());
         */

        diffSett = new JComboBox(diffSettOpt);
        diffSett.setSelectedIndex(GameState.getInstance().getLogin().resolveDifficulty());
        diffSett.addActionListener(this);
        lblOption1 = new JLabel(Language.getInstance().get(6));
        panDifficulty = new JPanel(new GridLayout(1, 2));
        panDifficulty.add(lblOption1);
        panDifficulty.add(diffSett);

        /*
        cmbResolution = new JComboBox(reso);
        cmbResolution.setSelectedIndex(LoginScreen.scalePos);
        cmbResolution.addActionListener(this);
        opLabel10 = new JLabel("Screen resolution: ");
        panLefty = new JPanel(new GridLayout(1, 2));
        panLefty.add(opLabel10);
        panLefty.add(cmbResolution);*/

        cmbTextSpeed = new JComboBox(mode);
        cmbTextSpeed.setSelectedIndex(2);
        cmbTextSpeed.addActionListener(this);
        lblOption4 = new JLabel(Language.getInstance().get(7));
        panTextSpeed = new JPanel(new GridLayout(1, 2));
        panTextSpeed.add(lblOption4);
        panTextSpeed.add(cmbTextSpeed);

        oplblOption2 = new JLabel(Language.getInstance().get(11));
        btnSoundOn = new JToggleButton(Language.getInstance().get(12));
        btnSoundOff = new JToggleButton(Language.getInstance().get(13));
        btnSoundOn.addActionListener(this);
        btnSoundOff.addActionListener(this);
        btnSoundOn.setSelected(GameState.getInstance().getLogin().isAudioOn()); //on by default
        btnSoundOff.setSelected(!GameState.getInstance().getLogin().isAudioOn());

        soundBG = new ButtonGroup();
        soundBG.add(btnSoundOn);
        soundBG.add(btnSoundOff);

        panSound = new JPanel(new GridLayout(1, 3));
        panSound.add(oplblOption2);
        panSound.add(btnSoundOn);
        panSound.add(btnSoundOff);
        panSound.setOpaque(false);

        opLabel3 = new JLabel(Language.getInstance().get(14));
        cmbTimeDuration = new JComboBox(times);
        cmbTimeDuration.setSelectedItem("" + GameState.getInstance().getLogin().getTimeLimit());
        cmbTimeDuration.addActionListener(this);
        panTimeDur = new JPanel(new GridLayout(1, 2));
        panTimeDur.add(opLabel3);
        panTimeDur.add(cmbTimeDuration);

        pan3 = new JPanel();
        updateButton = new JButton(Language.getInstance().get(16));
        updateButton.addActionListener(this);
        pan3.add(updateButton);
        /*
        countryCode = new JLabel("Country Code :");
        pan3.add(countryCode);
        pan3.add(nation);
         */

        panLefty = new JPanel(new GridLayout(1, 2));
        lefty = new JComboBox(new String[]{Language.getInstance().get(172), Language.getInstance().get(171)});
        lefty.setSelectedIndex(GameState.getInstance().getLogin().isLeftHanded() ? 0 : 1);
        lefty.addActionListener(this);
        opLabel10 = new JLabel(Language.getInstance().get(173));
        panLefty.add(opLabel10);
        panLefty.add(lefty);

        cmbComicTextOccurence = new JComboBox(comicTxtFreqStr);
        cmbComicTextOccurence.setSelectedIndex(GameState.getInstance().getLogin().getComicEffectOccurence());
        cmbComicTextOccurence.addActionListener(this);
        opLabel8 = new JLabel(Language.getInstance().get(17));
        panComicText = new JPanel(new GridLayout(1, 2));
        panComicText.add(opLabel8);
        panComicText.add(cmbComicTextOccurence);

        cont = new JCheckBox();
        cont.addItemListener(this);
        cont.setSelected(GameState.getInstance().getLogin().getUsingController());
        cont.setSelected(false);
        opLabel9 = new JLabel(Language.getInstance().get(18));
        panController = new JPanel(new GridLayout(1, 2));
        panController.add(opLabel9);
        panController.add(cont);

        cmbLanguage = new JComboBox(Language.getInstance().getSupportedLanguages());
        cmbLanguage.setSelectedIndex(GameState.getInstance().getLogin().getCurrentLanguage());
        cmbLanguage.addActionListener(this);
        opLabel11 = new JLabel(Language.getInstance().get(19));
        panLanguage = new JPanel(new GridLayout(1, 2));
        panLanguage.add(opLabel11);
        panLanguage.add(cmbLanguage);


        save = new JButton(Language.getInstance().get(20));
        save.addActionListener(this);

        opPanelSave = new JPanel(new FlowLayout());
        opPanelSave.add(save);


        panLanguage.setOpaque(false);
        //pnlGameRating.setOpaque(false);
        panLefty.setOpaque(false);
        panDifficulty.setOpaque(false);
        panSound.setOpaque(false);
        panTimeDur.setOpaque(false);
        panTextSpeed.setOpaque(false);
        panComicText.setOpaque(false);
        //panController.setOpaque(false)
        opPanelSave.setOpaque(false);


        alterJLabel(opLabel9);
        alterJLabel(lblOption1);
        alterJLabel(oplblOption2);
        alterJLabel(opLabel3);
        alterJLabel(lblOption4);
        alterJLabel(opLabel8);
        alterJLabel(opLabel9);
        alterJLabel(opLabel10);
        alterJLabel(opLabel11);

        box.add(panLanguage);
        //box.add(pnlGameRating);
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

        setTitle(Language.getInstance().get(34));
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
        setRating(GameState.getInstance().getLogin().getGameRating() / 20);
    }


    public void alterJLabel(JLabel jLabel) {
        jLabel.setForeground(Color.WHITE);
        //jLabel.setFont(normalFont);
    }

    @Override
    public void itemStateChanged(ItemEvent ie) {
        GameState.getInstance().getLogin().setUsingController(ie.getStateChange() == ItemEvent.SELECTED);
    }

    private void resetRating() {
        for (int u = 0; u < 5; u++) {
            btnRating[u].setEnabled(true);
        }
    }

    private void setRating(int num) {
        if (num > -1 && num < 6) {
            for (int u = 0; u < btnRating.length; u++) {
                btnRating[u].setEnabled(true);
            }
            //set new val
            for (int u = 0; u < num; u++) {
                btnRating[u].setEnabled(false);
            }
            gameRating = num;
            GameState.getInstance().getLogin().setGameRating(gameRating);
            ratingL.setText(Language.getInstance().get(35) + gameRating + "/5");
            GameState.getInstance().saveConfigFile();
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
            if (source == btnRating[um]) {
                setRating(um + 1);
            }
        }
        if (source == lefty) {
            GameState.getInstance().getLogin().setLeftHanded(lefty.getSelectedIndex() != 0);
        }
        if (source == btnReset) {
            resetRating();
        } else if (source == cmbComicTextOccurence) {
            GameState.getInstance().getLogin().setComicEffectOccurence(cmbComicTextOccurence.getSelectedIndex());
        } else if (source == cmbLanguage) {
            Language.getInstance().setLanguage(cmbLanguage.getSelectedIndex());
            GameState.getInstance().getLogin().setCurrentLanguage(cmbLanguage.getSelectedIndex());
        } else if (source == diffSett) {
            GameState.getInstance().getLogin().setDifficultyDynamic(GameState.getInstance().getLogin().getDifficultyConstant(diffSett.getSelectedIndex()));
            GameState.getInstance().getLogin().setDifficulty(GameState.getInstance().getLogin().getDifficultyConstant(diffSett.getSelectedIndex()));
            System.out.println("Diff = " + GameState.getInstance().getLogin().getDifficultyConstant(diffSett.getSelectedIndex()));
            System.out.println("Diff Range = " + (GameState.DIFFICULTY_BASE - GameState.getInstance().getLogin().getDifficultyConstant(diffSett.getSelectedIndex())));
            System.out.println("Diff Range Rate = " + (GameState.DIFFICULTY_BASE - GameState.getInstance().getLogin().getDifficultyConstant(diffSett.getSelectedIndex())) / GameState.DIFFICULTY_BASE);
        } else if (source == cmbTextSpeed) {
            GameState.getInstance().getLogin().setTxtSpeed(GameState.getInstance().getLogin().getTxtSpeedConstant(cmbTextSpeed.getSelectedIndex()));
        } else if (source == btnSoundOn) {
            GameState.getInstance().getLogin().setIsAudioOn(true);
        } else if (source == btnSoundOff) {
            GameState.getInstance().getLogin().setIsAudioOn(false);
        } else if (source == cmbTimeDuration) {
            if (cmbTimeDuration.getSelectedItem() == "infinite") {
                GameState.getInstance().getLogin().setTimeLimit(GameState.MAX_TIME);
            } else if (cmbTimeDuration.getSelectedItem() == "180") {
                GameState.getInstance().getLogin().setTimeLimit(180);
            } else if (cmbTimeDuration.getSelectedItem() == "150") {
                GameState.getInstance().getLogin().setTimeLimit(150);
            } else if (cmbTimeDuration.getSelectedItem() == "120") {
                GameState.getInstance().getLogin().setTimeLimit(120);
            } else if (cmbTimeDuration.getSelectedItem() == "90") {
                GameState.getInstance().getLogin().setTimeLimit(90);
            } else if (cmbTimeDuration.getSelectedItem() == "60") {
                GameState.getInstance().getLogin().setTimeLimit(60);
            } else if (cmbTimeDuration.getSelectedItem() == "30") {
                GameState.getInstance().getLogin().setTimeLimit(30);
            }
        } else if (source == save) {
            //GameState.getInstance().getLogin().setCountry(nation.getSelectedItem() + "");
            GameState.getInstance().saveConfigFile();
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

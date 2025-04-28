/**************************************************************************

 The SCND Genesis: Legends is a fighting game based on THE SCND GENESIS,
 a webcomic created by Ifunga Ndana ((([<a href="https://www.scndgen.com">https://www.scndgen.com</a>]))).

 The SCND Genesis: Legends RMX  © 2017 Ifunga Ndana.

 The SCND Genesis: Legends is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 The SCND Genesis: Legends is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with The SCND Genesis: Legends. If not, see <<a href="http://www.gnu.org/licenses/">http://www.gnu.org/licenses/</a>>.

 **************************************************************************/
package com.scndgen.legends.windows;

import com.scndgen.legends.Language;
import com.scndgen.legends.enums.AudioType;
import com.scndgen.legends.state.State;
import io.github.subiyacryolite.enginev1.Audio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import static com.scndgen.legends.constants.GeneralConstants.INFINITE_TIME;

/**
 * @author: Ifunga Ndana
 * @class: WindowOptions
 * This class contains the games OPTIONS
 */
public class WindowOptions extends Stage {

    private Label lblDifficultySetting, lblTimeDuration, lblTextSpeed, lblComicTextOccurence;
    private GridPane gridPane;
    private ObservableList<String> comicTextOccurence;
    private ObservableList<String> textSpeed;
    private ObservableList<String> difficultySetting;
    private ObservableList<String> timeLimits;
    private ComboBox<String> cmbDifficultySetting;
    private ComboBox<String> cmbTimeDuration;
    private ComboBox<String> cmbTextSpeed;
    private ComboBox<String> cmbComicTextOccurence;
    private Spinner<Integer> soundVolume;
    private Spinner<Integer> voiceVolume;
    private Spinner<Integer> musicVolume;
    private Label lblMusicVolume;
    private Label lblSoundVolume;
    private Label lblVoiceVolume;
    private Button btnSave;
    private Button btnCancel;

    /**
     * Constructor
     */
    public WindowOptions() {
        super(StageStyle.UNDECORATED);
        gridPane = new GridPane();
        gridPane.setHgap(4);
        gridPane.setVgap(4);

        timeLimits = FXCollections.observableArrayList(new String[]{Language.get().get(424), "180", "150", "120", "90", "60", "45","30"});
        comicTextOccurence = FXCollections.observableArrayList(new String[]{Language.get().get(1), Language.get().get(2), Language.get().get(3), Language.get().get(4)});
        textSpeed = FXCollections.observableArrayList(new String[]{Language.get().get(22), Language.get().get(23), Language.get().get(24), Language.get().get(25)});
        difficultySetting = FXCollections.observableArrayList(new String[]{Language.get().get(26), Language.get().get(27), Language.get().get(28), Language.get().get(29), Language.get().get(30)});

        int row = 1;

        cmbDifficultySetting = new ComboBox(difficultySetting);
        cmbDifficultySetting.setValue(State.get().getLogin().resolveDifficulty());
        cmbDifficultySetting.getSelectionModel().selectedItemProperty().addListener((src, oldValue, newValue) -> {
            State.get().getLogin().setDifficultyDynamic(State.get().getLogin().getDifficultyConstant(cmbDifficultySetting.getSelectionModel().getSelectedIndex()));
            State.get().getLogin().setDifficulty(State.get().getLogin().getDifficultyConstant(cmbDifficultySetting.getSelectionModel().getSelectedIndex()));
        });
        lblDifficultySetting = new Label(Language.get().get(6));
        gridPane.add(lblDifficultySetting, 1, row);
        gridPane.add(cmbDifficultySetting, 2, row);
        row++;

        voiceVolume = new Spinner<>();
        voiceVolume.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL);
        voiceVolume.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, State.get().getLogin().getVoiceVolume()));
        voiceVolume.valueProperty().addListener((observable, oldValue, newValue) -> {
            State.get().getLogin().setVoiceVolume(newValue);
            Audio.volume(AudioType.VOICE, newValue);
        });
        lblVoiceVolume = new Label(Language.get().get(420));
        gridPane.add(lblVoiceVolume, 1, row);
        gridPane.add(voiceVolume, 2, row);
        row++;

        soundVolume = new Spinner<>();
        soundVolume.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL);
        soundVolume.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, State.get().getLogin().getSoundVolume()));
        soundVolume.valueProperty().addListener((observable, oldValue, newValue) -> {
            State.get().getLogin().setSoundVolume(newValue);
            Audio.volume(AudioType.SOUND, newValue);
        });
        lblSoundVolume = new Label(Language.get().get(418));
        gridPane.add(lblSoundVolume, 1, row);
        gridPane.add(soundVolume, 2, row);
        row++;

        musicVolume = new Spinner<>();
        musicVolume.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL);
        musicVolume.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, State.get().getLogin().getMusicVolume()));
        musicVolume.valueProperty().addListener((observable, oldValue, newValue) -> {
            State.get().getLogin().setMusicVolume(newValue);
            Audio.volume(AudioType.MUSIC, newValue);
        });
        lblMusicVolume = new Label(Language.get().get(419));
        gridPane.add(lblMusicVolume, 1, row);
        gridPane.add(musicVolume, 2, row);
        row++;

        cmbTextSpeed = new ComboBox(textSpeed);
        cmbTextSpeed.setValue(State.get().getLogin().getTextSpeed());
        cmbTextSpeed.getSelectionModel().selectedItemProperty().addListener((src, oldValue, newValue) -> {
            State.get().getLogin().setTextSpeed(newValue);
        });
        lblTextSpeed = new Label(Language.get().get(7));
        gridPane.add(lblTextSpeed, 1, row);
        gridPane.add(cmbTextSpeed, 2, row);
        row++;

        lblTimeDuration = new Label(Language.get().get(14));
        cmbTimeDuration = new ComboBox(timeLimits);
        cmbTimeDuration.setValue(State.get().getLogin().getTimeLimitString());
        cmbTimeDuration.getSelectionModel().selectedItemProperty().addListener((list, oldValue, newValue) -> {
            if (newValue.equalsIgnoreCase("infinite"))
                State.get().getLogin().setTimeLimit(INFINITE_TIME);
            else
                State.get().getLogin().setTimeLimit(Integer.parseInt(newValue));
        });
        gridPane.add(lblTimeDuration, 1, row);
        gridPane.add(cmbTimeDuration, 2, row);
        row++;

        cmbComicTextOccurence = new ComboBox(comicTextOccurence);
        cmbComicTextOccurence.getSelectionModel().select(State.get().getLogin().getComicEffectOccurence());
        cmbComicTextOccurence.getSelectionModel().selectedItemProperty().addListener((list, newv, oldv) -> {

        });
        lblComicTextOccurence = new Label(Language.get().get(17));
        gridPane.add(lblComicTextOccurence, 1, row);
        gridPane.add(cmbComicTextOccurence, 2, row);
        row++;

        btnSave = new Button(Language.get().get(20));
        btnSave.setOnAction(event -> {
            try {
                State.get().saveConfigFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
            close();
        });
        btnCancel = new Button(Language.get().get(421));
        btnCancel.setOnAction(event -> {
            close();
        });
        gridPane.add(btnSave, 1, row);
        gridPane.add(btnCancel, 2, row);
        GridPane.setHalignment(btnSave, HPos.CENTER);
        GridPane.setHalignment(btnCancel, HPos.CENTER);
        row++;

        setTitle(Language.get().get(34));
        setScene(new Scene(gridPane));
        setResizable(false);
        initModality(Modality.APPLICATION_MODAL);
        show();
    }
}

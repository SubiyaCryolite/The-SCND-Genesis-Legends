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
import com.scndgen.legends.state.GameState;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import static com.scndgen.legends.constants.GeneralConstants.INFINITE_TIME;

/**
 * @author: Ifunga Ndana
 * @class: WindowOptions
 * This class contains the games OPTIONS
 */
public class WindowOptions extends Stage {

    private Label lblDifficultySetting, lblTimeDuration, lblTextSpeed, lblComicTextOccurence, lblIsLeftHanded;
    private GridPane gridPane;
    private ObservableList<String> comicTextOccurence;
    private ObservableList<String> textSpeed;
    private ObservableList<String> difficultySetting;
    private ObservableList<String> timeLimits;
    private ObservableList<String> isLeftHanded;
    private ComboBox<String> cmbDifficultySetting;
    private ComboBox<String> cmbIsLeftHanded;
    private ComboBox<String> cmbTimeDuration;
    private ComboBox<String> cmbTextSpeed;
    private ComboBox<String> cmbComicTextOccurence;
    private Button btnSave;

    /**
     * Constructor
     */
    public WindowOptions() {
        super(StageStyle.UNDECORATED);
        gridPane = new GridPane();
        isLeftHanded = FXCollections.observableArrayList(new String[]{Language.getInstance().get(172), Language.getInstance().get(171)});
        timeLimits = FXCollections.observableArrayList(new String[]{"Infinite", "180", "150", "120", "90", "60", "30"});
        comicTextOccurence = FXCollections.observableArrayList(new String[]{Language.getInstance().get(1), Language.getInstance().get(2), Language.getInstance().get(3), Language.getInstance().get(4)});
        textSpeed = FXCollections.observableArrayList(new String[]{Language.getInstance().get(22), Language.getInstance().get(23), Language.getInstance().get(24), Language.getInstance().get(25)});
        difficultySetting = FXCollections.observableArrayList(new String[]{Language.getInstance().get(26), Language.getInstance().get(27), Language.getInstance().get(28), Language.getInstance().get(29), Language.getInstance().get(30)});

        int row = 1;

        cmbDifficultySetting = new ComboBox(difficultySetting);
        cmbDifficultySetting.setValue(GameState.getInstance().getLogin().resolveDifficulty());
        cmbDifficultySetting.getSelectionModel().selectedItemProperty().addListener((src, oldValue, newValue) -> {
            GameState.getInstance().getLogin().setDifficultyDynamic(GameState.getInstance().getLogin().getDifficultyConstant(cmbDifficultySetting.getSelectionModel().getSelectedIndex()));
            GameState.getInstance().getLogin().setDifficulty(GameState.getInstance().getLogin().getDifficultyConstant(cmbDifficultySetting.getSelectionModel().getSelectedIndex()));
        });
        lblDifficultySetting = new Label(Language.getInstance().get(6));
        gridPane.add(lblDifficultySetting, 1, row);
        gridPane.add(cmbDifficultySetting, 2, row);
        row++;

        cmbTextSpeed = new ComboBox(textSpeed);
        cmbTextSpeed.setValue(GameState.getInstance().getLogin().getTextSpeed());
        cmbTextSpeed.getSelectionModel().selectedItemProperty().addListener((src, oldValue, newValue) -> {
            GameState.getInstance().getLogin().setTextSpeed(newValue);
        });
        lblTextSpeed = new Label(Language.getInstance().get(7));
        gridPane.add(lblTextSpeed, 1, row);
        gridPane.add(cmbTextSpeed, 2, row);
        row++;

        lblTimeDuration = new Label(Language.getInstance().get(14));
        cmbTimeDuration = new ComboBox(timeLimits);
        cmbTimeDuration.setValue(GameState.getInstance().getLogin().getTimeLimitString());
        cmbTimeDuration.getSelectionModel().selectedItemProperty().addListener((list, oldValue, newValue) -> {
            if (newValue.equalsIgnoreCase("infinite"))
                GameState.getInstance().getLogin().setTimeLimit(INFINITE_TIME);
            else
                GameState.getInstance().getLogin().setTimeLimit(Integer.parseInt(newValue));
        });
        gridPane.add(lblTimeDuration, 1, row);
        gridPane.add(cmbTimeDuration, 2, row);
        row++;

        cmbIsLeftHanded = new ComboBox(isLeftHanded);
        cmbIsLeftHanded.getSelectionModel().select(GameState.getInstance().getLogin().isLeftHanded() ? 0 : 1);
        cmbIsLeftHanded.getSelectionModel().selectedItemProperty().addListener((list, oldValue, newValue) -> {
            GameState.getInstance().getLogin().setLeftHanded(list.getValue().indexOf(newValue) == 0 ? true : false);
        });
        lblIsLeftHanded = new Label(Language.getInstance().get(173));
        gridPane.add(lblIsLeftHanded, 1, row);
        gridPane.add(cmbIsLeftHanded, 2, row);
        row++;

        cmbComicTextOccurence = new ComboBox(comicTextOccurence);
        cmbComicTextOccurence.getSelectionModel().select(GameState.getInstance().getLogin().getComicEffectOccurence());
        cmbComicTextOccurence.getSelectionModel().selectedItemProperty().addListener((list, newv, oldv) -> {

        });
        lblComicTextOccurence = new Label(Language.getInstance().get(17));
        gridPane.add(lblComicTextOccurence, 1, row);
        gridPane.add(cmbComicTextOccurence, 2, row);
        row++;

        btnSave = new Button(Language.getInstance().get(20));
        btnSave.setOnAction(event -> {
            GameState.getInstance().saveConfigFile();
            close();
        });
        gridPane.add(btnSave, 1, row, 2, 1);
        GridPane.setHalignment(btnSave, HPos.CENTER);
        row++;

        setTitle(Language.getInstance().get(34));
        setScene(new Scene(gridPane));
        setResizable(false);
        show();
    }
}

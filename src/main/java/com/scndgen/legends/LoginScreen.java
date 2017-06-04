/**************************************************************************

 The SCND Genesis: Legends is a fighting game based on THE SCND GENESIS,
 a webcomic created by Ifunga Ndana (([http://www.scndgen.com])).

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
 along with The SCND Genesis: Legends. If not, see <http://www.gnu.org/licenses/>.

 **************************************************************************/
package com.scndgen.legends;


import com.scndgen.legends.drawing.DrawUserLogin;
import com.scndgen.legends.enums.CharacterEnum;
import com.scndgen.legends.state.State;
import com.scndgen.legends.state.Login;
import io.github.subiyacryolite.enginev1.FxDialogs;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class LoginScreen extends Stage {

    public static final CharacterEnum[] charNames = CharacterEnum.values();
    public static final int NORMAL_TXT_SIZE = 14, LARGE_TXT_SIZE = 20, EXTRA_LARGE_TXT_SIZE = 26;
    private GridPane gridPane;
    private DrawUserLogin drawUserLogin;
    private Button btnEnter, btnQuit, btnCreateAccount;
    private ComboBox<Login> cmbUsers;
    private Label lblUserName;
    private TextField txtActiveAccount;

    private LoginScreen() {

        lblUserName = new Label("User Name: ");
        txtActiveAccount = new TextField("");
                btnCreateAccount = new Button("Create a New Account");
        btnCreateAccount.setOnAction(event -> {
            String accountName = FxDialogs.input("Create ccount", "Enter new account getInfo", "Enter account info");
            if (accountName.length() >= 1 && accountName.length() <= 32) {
                State.get().createLogin(accountName);
                updateComboboxAndUi();
            } else {
                btnEnter.setDisable(true);
                FxDialogs.error("Error", "Username should be between 1 and 32 characters long", "");
            }

        });

        cmbUsers = new ComboBox(State.get().getLogins());
        cmbUsers.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        });

        btnEnter = new Button("Load Selected Account");
        btnEnter.setOnAction(event -> {
            State.get().setCurrentLogin(cmbUsers.getValue());
        });
        btnQuit = new Button("Quit");
        btnQuit.setOnAction(event -> {
            if (ScndGenLegends.get() != null) {
                ScndGenLegends.get().exit();
            }
            close();
        });


        drawUserLogin = new DrawUserLogin(this);

        gridPane = new GridPane();
        gridPane.add(lblUserName, 1, 1);
        gridPane.add(txtActiveAccount, 2, 1);
        gridPane.add(cmbUsers, 1, 2, 2, 1);
        gridPane.add(btnCreateAccount, 3, 1, 2, 1);
        gridPane.add(btnEnter, 1, 4);
        gridPane.add(btnQuit, 2, 4);
        updateComboboxAndUi();

        Group group = new Group(gridPane);
        setScene(new Scene(group));
        setTitle("The SCND Genesis: Legends RMX");
        setResizable(false);
        show();
    }

    private void updateComboboxAndUi() {
        if (State.get().getLogins().size() == 0) {
            btnEnter.setDisable(true);
        } else {
            txtActiveAccount.setText(State.get().getLogin().toString());
            btnEnter.setDisable(false);
        }
    }

}

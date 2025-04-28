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
import com.scndgen.legends.drawing.BackgroundImages;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * This class holds ABOUT information
 *
 * @author ndana
 */
public class WindowControls extends Stage {

    private Button btnOk;
    private BackgroundImages backgroundImages;
    private GridPane gridPane;
    //private Font normalFont;
    private HBox line1, line2, line3, line4, line5, line6, line7, line8, line9, line10, line11, line12, line13, line14, line15, line16, line17;
    private Label lLabel, l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11, l12, l13, l14, l15, l16, l17, l18, l19, l20;
    private Label mLabel, m1, m2, m3, m4, m5, m6, m8, m7, m9, m10;

    public WindowControls() {
        super(StageStyle.UNDECORATED);
        backgroundImages = new BackgroundImages();
        BackgroundImage backgroundImage = new BackgroundImage(backgroundImages.getImage(), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        gridPane = new GridPane();
        gridPane.setHgap(4);
        gridPane.setVgap(4);

        btnOk = new Button(Language.get().get(36));
        btnOk.setOnAction(event -> close());

        lLabel = new Label(Language.get().get(37));
        l1 = new Label(Language.get().get(38));
        l2 = new Label(Language.get().get(40));
        l17 = new Label(Language.get().get(39));
        l18 = new Label(Language.get().get(41));
        l3 = new Label(Language.get().get(42));
        l4 = new Label("F12");
        l5 = new Label(Language.get().get(43));
        l6 = new Label("ESC");
        l7 = new Label(Language.get().get(44));
        l8 = new Label("L");
        l9 = new Label(Language.get().get(45));
        l10 = new Label("Left");
        l11 = new Label(Language.get().get(46));
        l12 = new Label("Right");
        l13 = new Label(Language.get().get(47));
        l14 = new Label("Up");
        l15 = new Label(Language.get().get(48));
        l16 = new Label(Language.get().get(49));
        l19 = new Label(Language.get().get(50));
        l20 = new Label("F4");

        //keyboard
        gridPane.add(lLabel, 1, 1, 2, 1);
        GridPane.setHalignment(lLabel, HPos.CENTER);
        gridPane.add(l1, 1, 2);
        gridPane.add(l2, 2, 2);
        gridPane.add(l3, 1, 3);
        gridPane.add(l4, 2, 3);
        gridPane.add(l5, 1, 4);
        gridPane.add(l6, 2, 4);
        gridPane.add(l7, 1, 5);
        gridPane.add(l8, 2, 5);
        gridPane.add(l9, 1, 6);
        gridPane.add(l10, 2, 6);
        gridPane.add(l11, 1, 7);
        gridPane.add(l12, 2, 7);
        gridPane.add(l13, 1, 8);
        gridPane.add(l14, 2, 8);
        gridPane.add(l15, 1, 9);
        gridPane.add(l16, 2, 9);
        gridPane.add(l17, 1, 10);
        gridPane.add(l18, 2, 10);
        gridPane.add(l19, 1, 11);
        gridPane.add(l20, 2, 11);

        //Mouse
        mLabel = new Label(Language.get().get(51));
        gridPane.add(mLabel, 1, 12, 2, 1);
        GridPane.setHalignment(mLabel, HPos.CENTER);

        gridPane.add(m2 = new Label(Language.get().get(45)), 1, 13);
        gridPane.add(m1 = new Label(Language.get().get(52)), 2, 13);

        gridPane.add(m4 = new Label(Language.get().get(46)), 1, 14);
        gridPane.add(m3 = new Label(Language.get().get(53)), 2, 14);

        gridPane.add(m6 = new Label(Language.get().get(38)), 1, 15);
        gridPane.add(m5 = new Label(Language.get().get(54)), 2, 15);

        gridPane.add(m8 = new Label(Language.get().get(44)), 1, 16);
        gridPane.add(new Label(Language.get().get(55)), 2, 16);

        gridPane.add(m10 = new Label(Language.get().get(39)), 1, 17);
        gridPane.add(m9 = new Label(Language.get().get(56)), 2, 17);

        gridPane.add(btnOk, 1, 18, 2, 1);
        GridPane.setHalignment(btnOk, HPos.CENTER);

        setTitle(Language.get().get(57));
        setScene(new Scene(gridPane));
        setResizable(false);
        initModality(Modality.APPLICATION_MODAL);
        show();
    }

    private void alterPanel(HBox hBox) {
        //hBox.setBackground(Background.EMPTY);
    }

    public void alterLabel(Label label) {
        //label.setTextFill(Color.WHITE);
    }
}

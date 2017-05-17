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
import com.scndgen.legends.drawing.SpecialDrawMenuBGs;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * This class holds ABOUT information
 *
 * @author ndana
 */
public class WindowControls extends Stage {

    private Object source;
    private HBox bottom;
    private Button ok;
    private SpecialDrawMenuBGs logoPic;
    private VBox box;
    //private Font normalFont;
    private HBox p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17;
    private Label lLabel, l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11, l12, l13, l14, l15, l16, l17, l18, l19, l20;
    private Label mLabel, m1, m2, m3, m4, m5, m6, m8, m7, m9, m10;

    public WindowControls() {
        //normalFont = getMyFont(LoginScreen.normalTxtSize - 2);
        logoPic = new SpecialDrawMenuBGs();

        box = new VBox();

        ok = new Button(Language.getInstance().get(36));
        ok.setOnAction(event -> close());
        bottom = new HBox();
        bottom.getChildren().add(ok);

        lLabel = new Label(Language.getInstance().get(37));
        l1 = new Label(" " + Language.getInstance().get(38));
        l2 = new Label(Language.getInstance().get(40));
        l17 = new Label(" " + Language.getInstance().get(39));
        l18 = new Label(Language.getInstance().get(41));
        l3 = new Label(" " + Language.getInstance().get(42));
        l4 = new Label("F12");
        l5 = new Label(" " + Language.getInstance().get(43));
        l6 = new Label("ESC");
        l7 = new Label(" " + Language.getInstance().get(44));
        l8 = new Label("L");
        l9 = new Label(" " + Language.getInstance().get(45));
        l10 = new Label("Left");
        l11 = new Label(" " + Language.getInstance().get(46));
        l12 = new Label("Right");
        l13 = new Label(" " + Language.getInstance().get(47));
        l14 = new Label("Up");
        l15 = new Label(" " + Language.getInstance().get(48));
        l16 = new Label(Language.getInstance().get(49));
        l19 = new Label(" " + Language.getInstance().get(50));
        l20 = new Label("F4");

        (p9 = new HBox()).getChildren().add(lLabel);
        (p1 = new HBox()).getChildren().addAll(l1, l2);
        (p2 = new HBox()).getChildren().addAll(l3, l4);
        (p3 = new HBox()).getChildren().addAll(l5, l6);
        (p4 = new HBox()).getChildren().addAll(l7, l8);
        (p5 = new HBox()).getChildren().addAll(l9, l10);
        (p6 = new HBox()).getChildren().addAll(l11, l12);
        (p7 = new HBox()).getChildren().addAll(l13, l14);
        (p8 = new HBox()).getChildren().addAll(l15, l16);
        (p15 = new HBox()).getChildren().addAll(l17, l18);
        (p17 = new HBox()).getChildren().addAll(l19, l20);

        //Mouse
        mLabel = new Label(Language.getInstance().get(51));
        (p13 = new HBox()).getChildren().add(mLabel);

        m2 = new Label(" " + Language.getInstance().get(45));
        m1 = new Label(Language.getInstance().get(52));
        (p10 = new HBox()).getChildren().addAll(m2, m1);

        m3 = new Label(Language.getInstance().get(53));
        m4 = new Label(" " + Language.getInstance().get(46));
        (p11 = new HBox()).getChildren().addAll(m4, m3);

        m5 = new Label(Language.getInstance().get(54));
        m6 = new Label(" " + Language.getInstance().get(38));
        (p12 = new HBox()).getChildren().addAll(m6, m5);

        m7 = new Label(Language.getInstance().get(55));
        m8 = new Label(" " + Language.getInstance().get(44));
        (p14 = new HBox()).getChildren().addAll(m8, m7);

        m9 = new Label(Language.getInstance().get(56));
        m10 = new Label(" " + Language.getInstance().get(39));
        (p16 = new HBox()).getChildren().addAll(m10, m9);

        alterLabel(lLabel);
        alterLabel(l1);
        alterLabel(l2);
        alterLabel(l3);
        alterLabel(l4);
        alterLabel(l5);
        alterLabel(l6);
        alterLabel(l7);
        alterLabel(l8);
        alterLabel(l9);
        alterLabel(l10);
        alterLabel(l11);
        alterLabel(l12);
        alterLabel(l13);
        alterLabel(l14);
        alterLabel(l15);
        alterLabel(l16);
        alterLabel(l17);
        alterLabel(l18);
        alterLabel(l19);
        alterLabel(l20);

        //keyboard
        alterPanel(p9);
        alterPanel(p1);
        alterPanel(p15);
        alterPanel(p2);
        alterPanel(p3);
        alterPanel(p4);
        alterPanel(p5);
        alterPanel(p6);
        alterPanel(p7);
        alterPanel(p8);
        alterPanel(p17);

        alterLabel(mLabel);
        alterLabel(m1);
        alterLabel(m2);
        alterLabel(m3);
        alterLabel(m4);
        alterLabel(m5);
        alterLabel(m6);
        alterLabel(m8);
        alterLabel(m7);
        alterLabel(m9);
        alterLabel(m10);

        alterPanel(bottom);

        //mouse
        alterPanel(p13);
        alterPanel(p10);
        alterPanel(p11);
        alterPanel(p12);
        alterPanel(p14);
        alterPanel(p16);

        //keyboard
        box.getChildren().add(p9);
        box.getChildren().add(p1);
        box.getChildren().add(p15);
        box.getChildren().add(p2);
        box.getChildren().add(p3);
        box.getChildren().add(p4);
        box.getChildren().add(p5);
        box.getChildren().add(p6);
        box.getChildren().add(p7);
        box.getChildren().add(p8);
        box.getChildren().add(p17);

        //mouse
        box.getChildren().add(p13);
        box.getChildren().add(p10);
        box.getChildren().add(p11);
        box.getChildren().add(p12);
        box.getChildren().add(p14);
        box.getChildren().add(p16);

        box.getChildren().add(bottom);

        setTitle(Language.getInstance().get(57));
        setScene(new Scene(box));
        setResizable(false);
        show();
    }

    private void alterPanel(HBox hBox) {
        //hBox.setBackground(Background.EMPTY);
    }

    public void alterLabel(Label label) {
        //label.setTextFill(Color.WHITE);
    }
}

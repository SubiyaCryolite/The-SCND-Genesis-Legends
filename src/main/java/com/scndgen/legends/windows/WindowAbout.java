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
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.commons.io.IOUtils;

import java.nio.charset.Charset;

/**
 * This class holds ABOUT information
 *
 * @author ndana
 */
public class WindowAbout extends Stage {

    private TextArea txtAbout, txtLicense, txtChangeLog, txtSourceCode;
    private Button btnOk;
    private ScrollPane scrlAbout, scrlLicense, scrlChangeLog, scrlSourceCode;
    private TabPane tabPane;
    private String about;
    private String licenseText;
    private String changeLog;
    private String sourceCode;

    public WindowAbout() {
        super(StageStyle.UNDECORATED);
        try {
            about = IOUtils.toString(Thread.currentThread().getContextClassLoader().getResourceAsStream("text/txtAbout.txt"), Charset.defaultCharset());
            licenseText = IOUtils.toString(Thread.currentThread().getContextClassLoader().getResourceAsStream("text/txtLicense.txt"), Charset.defaultCharset());
            changeLog = IOUtils.toString(Thread.currentThread().getContextClassLoader().getResourceAsStream("text/txtChangelog.txt"), Charset.defaultCharset());
            sourceCode = IOUtils.toString(Thread.currentThread().getContextClassLoader().getResourceAsStream("text/txtSourceCode.txt"), Charset.defaultCharset());
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
        txtAbout = new TextArea("");
        txtAbout.setText(about);
        txtAbout.setEditable(false);
        txtAbout.setWrapText(true);
        scrlAbout = new ScrollPane(txtAbout);

        txtLicense = new TextArea("");
        txtLicense.setText(licenseText);
        txtLicense.setEditable(false);
        txtLicense.setWrapText(true);
        scrlLicense = new ScrollPane(txtLicense);

        txtChangeLog = new TextArea("");
        txtChangeLog.setText(changeLog);
        txtChangeLog.setEditable(false);
        txtChangeLog.setWrapText(true);
        scrlChangeLog = new ScrollPane(txtChangeLog);

        txtSourceCode = new TextArea("");
        txtSourceCode.setText(sourceCode);
        txtSourceCode.setEditable(false);
        txtSourceCode.setWrapText(true);
        scrlSourceCode = new ScrollPane(txtSourceCode);

        Tab tab1 = new Tab("About");
        tab1.setClosable(false);
        Tab tab2 = new Tab("License");
        tab2.setClosable(false);
        Tab tab3 = new Tab("Develop");
        tab3.setClosable(false);

        tab1.setContent(scrlAbout);
        tab2.setContent(scrlLicense);
        tab3.setContent(scrlSourceCode);

        tabPane = new TabPane();
        tabPane.getTabs().add(tab1);
        tabPane.getTabs().add(tab2);
        tabPane.getTabs().add(tab3);

        btnOk = new Button("OK");
        btnOk.setOnAction(event -> {
            close();
        });

        VBox vBox = new VBox();
        vBox.getChildren().add(tabPane);
        vBox.getChildren().add(btnOk);

        setTitle(Language.getInstance().get(57));
        setScene(new Scene(vBox));
        setResizable(false);
        show();
    }
}

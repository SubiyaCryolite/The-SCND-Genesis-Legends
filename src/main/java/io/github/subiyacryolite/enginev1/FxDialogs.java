package io.github.subiyacryolite.enginev1;

import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Modality;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Optional;

/**
 * http://code.makery.ch/blog/javafx-dialogs-official/
 * Created by ifunga on 03/06/2017.
 */
public class FxDialogs {
    public static void error(String title, String header, String content) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText(header);
            alert.setContentText(content);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.showAndWait();
        });
    }

    public static void error(String title, String header, String content, Exception exception) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText(header);
            alert.setContentText(content);
            alert.initModality(Modality.APPLICATION_MODAL);

            String exceptionText;

            // Create expandable Exception.
            try (StringWriter stringWriter = new StringWriter(); PrintWriter printWriter = new PrintWriter(stringWriter)) {
                exception.printStackTrace(printWriter);
                exceptionText = stringWriter.toString();
            } catch (Exception ez) {
                exception.printStackTrace(System.err);
                exceptionText = "";
            }
            Label label = new Label("The exception stacktrace was:");

            TextArea textArea = new TextArea(exceptionText);
            textArea.setEditable(false);
            textArea.setWrapText(true);

            textArea.setMaxWidth(Double.MAX_VALUE);
            textArea.setMaxHeight(Double.MAX_VALUE);
            GridPane.setVgrow(textArea, Priority.ALWAYS);
            GridPane.setHgrow(textArea, Priority.ALWAYS);

            GridPane expContent = new GridPane();
            expContent.setMaxWidth(Double.MAX_VALUE);
            expContent.add(label, 0, 0);
            expContent.add(textArea, 0, 1);

            // Set expandable Exception into the dialog pane.
            alert.getDialogPane().setExpandableContent(expContent);
            alert.showAndWait();
        });
    }

    public static String input(String title, String header, String content, String defaultValue) {
        TextInputDialog textInputDialog = new TextInputDialog(defaultValue);
        textInputDialog.setTitle(title);
        textInputDialog.setHeaderText(header);
        textInputDialog.setContentText(content);
        textInputDialog.initModality(Modality.APPLICATION_MODAL);

        Optional<String> result = textInputDialog.showAndWait();

        result.ifPresent(name -> System.out.println("Your name: " + name));
        return result.isPresent() ? result.get() : null;
    }

    public static String input(String title, String header, String content) {
        return input(title, header, content, "");
    }

    public static <T extends Object> Optional<T> choice(String title, String header, String content, T defaultValue, List<T> choices) {
        ChoiceDialog<T> choiceDialog = new ChoiceDialog<T>(defaultValue, choices);
        choiceDialog.setTitle(title);
        choiceDialog.setHeaderText(header);
        choiceDialog.setContentText(content);
        choiceDialog.initModality(Modality.APPLICATION_MODAL);

        Optional<T> result = choiceDialog.showAndWait();
        return result;
    }

    public static ButtonBar.ButtonData yesNo(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.initModality(Modality.APPLICATION_MODAL);

        ButtonType btnYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType btnNo = new ButtonType("No", ButtonBar.ButtonData.NO);

        alert.getButtonTypes().setAll(btnYes, btnNo);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
            return result.get().getButtonData();
        } else return ButtonBar.ButtonData.NO;
    }

    public static ButtonBar.ButtonData yesNoCancel(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.initModality(Modality.APPLICATION_MODAL);

        ButtonType btnYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType btnNo = new ButtonType("No", ButtonBar.ButtonData.NO);
        ButtonType btnCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(btnYes, btnNo, btnCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
            return result.get().getButtonData();
        } else return ButtonBar.ButtonData.CANCEL_CLOSE;
    }

    public static void message(String title, String header, String content) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(header);
            alert.setContentText(content);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.show();
        });
    }
}

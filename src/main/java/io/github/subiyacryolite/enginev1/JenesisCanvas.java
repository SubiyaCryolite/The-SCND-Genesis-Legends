package io.github.subiyacryolite.enginev1;

import com.scndgen.legends.ScndGenLegends;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class JenesisCanvas extends Application {

    private JenesisGame game;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        game = new ScndGenLegends();
        Canvas gameCanvas = new Canvas(game.getWidth(), game.getHeight());
        gameCanvas.setOnKeyReleased(
                new EventHandler<KeyEvent>() {
                    public void handle(KeyEvent keyEvent) {
                        game.keyReleased(keyEvent);
                    }
                });
        gameCanvas.setOnKeyPressed(
                new EventHandler<KeyEvent>() {
                    public void handle(KeyEvent keyEvent) {
                        game.keyPressed(keyEvent);
                    }
                });
        gameCanvas.setOnMouseMoved(
                new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent mouseEvent) {
                        game.mouseMoved(mouseEvent);
                    }
                });
        gameCanvas.setOnMouseClicked(
                new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent mouseEvent) {
                        game.mouseClicked(mouseEvent);
                    }
                });
        gameCanvas.setFocusTraversable(true);
        Group group = new Group();
        group.getChildren().add(gameCanvas);
        stage.setScene(new Scene(group));
        stage.show();
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (game.getMode() != null && !game.isSwitchingModes())
                    game.getMode().render(gameCanvas.getGraphicsContext2D(), gameCanvas.getHeight(), gameCanvas.getWidth());
                else
                    game.drawLoadingAnimation(gameCanvas.getGraphicsContext2D(), gameCanvas.getHeight(), gameCanvas.getWidth());
            }
        };
        animationTimer.start();
    }

    private void drawScene(GraphicsContext gc) {
    }
}
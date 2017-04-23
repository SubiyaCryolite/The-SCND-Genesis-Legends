package io.github.subiyacryolite.enginev1;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;

public class JenesisEngine extends Application {
    public static Class<? extends JenesisGame> applicationStage;
    private JenesisGame jenesisGame;
    private Canvas canvas;


    @Override
    public void start(Stage stage) {
        try {
            jenesisGame = applicationStage.newInstance();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        canvas = new Canvas(jenesisGame.getWidth(), jenesisGame.getHeight());
        canvas.setOnKeyReleased(keyEvent -> jenesisGame.keyReleased(keyEvent));
        canvas.setOnKeyPressed(keyEvent -> jenesisGame.keyPressed(keyEvent));
        canvas.setOnMouseMoved(mouseEvent -> jenesisGame.mouseMoved(mouseEvent));
        canvas.setOnMouseClicked(mouseEvent -> jenesisGame.mouseClicked(mouseEvent));
        canvas.setFocusTraversable(true);
        Group group = new Group();
        group.getChildren().add(canvas);
        stage.setScene(new Scene(group));
        stage.show();
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                //canvas.getGraphicsContext2D().setGlobalBlendMode(BlendMode.ADD);
                if (jenesisGame == null) return;
                if (jenesisGame.getJenesisMode() != null && !jenesisGame.isSwitchingModes()) {
                    jenesisGame.getJenesisMode().update(now);
                    jenesisGame.getJenesisMode().render(canvas.getGraphicsContext2D(), canvas.getWidth(), canvas.getHeight());
                } else
                    jenesisGame.drawLoadingAnimation(canvas.getGraphicsContext2D(), canvas.getWidth(), canvas.getHeight());
            }
        };
        animationTimer.start();
    }
}
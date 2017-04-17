package com.scndgen.legends.tests;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CanvasPerformance extends Application {

    public static final int CYCLE_TIME_MS = 10000;
    public static final double CYCLE_TIME_NORM_FACTOR = 1d / CYCLE_TIME_MS;
    private AnimationTimer timer;

    @Override
    public void start(Stage stage) throws Exception {
        DemoCanvas canvas = new DemoCanvas(1280,720);
        Group root = new Group();
        root.getChildren().add(canvas);
        stage.setScene(new Scene(root));
        stage.setTitle(getClass().getSimpleName());
        stage.show();
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                long nowMS = now / 1000000;
                double cycleOffset = (nowMS % CYCLE_TIME_MS) * CYCLE_TIME_NORM_FACTOR;
                canvas.fill = cycleOffset;
                canvas.draw(canvas.getGraphicsContext2D());
            }
        };
        timer.start();
    }

    @Override
    public void stop() throws Exception {
        timer.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
package io.github.subiyacryolite.enginev1;

import com.scndgen.legends.enums.ModeEnum;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Created by ifunga on 15/04/2017.
 */
public abstract class Game {
    private final Color CIRCLE_BASE_FILL_COLOR = Color.LIGHTSKYBLUE;
    private final Color OUTLINE_COLOR = Color.GRAY;
    private final Color BACKGROUND_COLOR = Color.WHITE;
    private Mode mode;
    private ModeEnum modeEnum;
    private boolean switchingModes;
    private boolean running;
    private double width;
    private double height;
    private Stage stage;

    public Game() {
        running = true;
    }

    public final Mode getMode() {
        return mode;
    }

    public final boolean isSwitchingModes() {
        return switchingModes;
    }

    public void drawLoadingAnimation(GraphicsContext context, double width, double height) {
        final double cx = width / 2;
        final double cy = height / 2;
        final double outerRadius = Math.min(width / 2, height / 2);
        final double innerRadius = outerRadius * 0.2;
        final double range = outerRadius - innerRadius;
        final double currentRadius = innerRadius + range * 0;
        context.setFill(BACKGROUND_COLOR);
        context.fillRect(0, 0, width, height);
        context.setFill(CIRCLE_BASE_FILL_COLOR);
        context.fillOval(cx - currentRadius, cy - currentRadius, currentRadius * 2, currentRadius * 2);
        context.setStroke(OUTLINE_COLOR);
        context.strokeOval(cx - innerRadius, cy - innerRadius, innerRadius * 2, innerRadius * 2);
        context.strokeOval(cx - currentRadius, cy - currentRadius, currentRadius * 2, currentRadius * 2);
    }

    public final ModeEnum getModeEnum() {
        return modeEnum;
    }

    public final void setModeEnum(ModeEnum value) {
        modeEnum = value;
    }

    public final double getWidth() {
        return width;
    }

    public final double getHeight() {
        return height;
    }

    public final void setWidth(double value) {
        width = value;
    }

    public final void setHeight(double value) {
        height = value;
    }

    public final void setSize(double width, double height) {
        setWidth(width);
        setHeight(height);
    }

    public final void setMode(Mode mode) {
        if (mode == null) return;
        if (this.mode == mode) return;
        if (this.mode != null)
            this.mode.onLeaveMode();
        this.mode = mode;
        this.mode.onEnterMode();
    }

    public void setSwitchingModes(boolean switchingModes) {
        this.switchingModes = switchingModes;
    }

    public abstract void onKeyReleased(KeyEvent keyEvent);

    public abstract void onKeyPressed(KeyEvent keyEvent);

    public abstract void onMouseMoved(MouseEvent mouseEvent);

    public abstract void onMouseClicked(MouseEvent mouseEvent);

    public abstract void onScroll(ScrollEvent scrollEvent);

    public void run() {
        do {
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace(System.err);
            }
        }
        while (running);
    }


    public abstract void onCloseRequest(WindowEvent closeRequest);

    public abstract void shutDown();

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void exit()
    {
        shutDown();
        stage.close();
    }
}

package com.scndgen.legends.tests;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DemoCanvas extends Canvas {
    private static final Color CIRCLE_BASE_FILL_COLOR = Color.LIGHTSKYBLUE;
    private static final Color OUTLINE_COLOR = Color.GRAY;
    private static final Color BACKGROUND_COLOR = Color.WHITE;

    public DemoCanvas(int width, int height) {
        super(width, height);
    }

    public double fill = 0;

    public void draw(final GraphicsContext context) {
        final double h = getHeight();
        final double w = getWidth();
        final double cx = w / 2;
        final double cy = h / 2;
        final double outerRadius = Math.min(w / 2, h / 2);
        final double innerRadius = outerRadius * 0.2;
        final double range = outerRadius - innerRadius;
        final double currentRadius = innerRadius + range * fill;
        context.setFill(BACKGROUND_COLOR);
        context.fillRect(0, 0, w, h);
        context.setFill(CIRCLE_BASE_FILL_COLOR);
        context.fillOval(cx - currentRadius, cy - currentRadius, currentRadius * 2, currentRadius * 2);
        context.setStroke(OUTLINE_COLOR);
        context.strokeOval(cx - innerRadius, cy - innerRadius, innerRadius * 2, innerRadius * 2);
        context.strokeOval(cx - currentRadius, cy - currentRadius, currentRadius * 2, currentRadius * 2);
    }

    @Override
    public boolean isResizable() {
        return true;
    }

    @Override
    public double prefWidth(double height) {
        return getWidth();
    }

    @Override
    public double prefHeight(double width) {
        return getHeight();
    }
}
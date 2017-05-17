package io.github.subiyacryolite.enginev1;

import com.scndgen.legends.ScndGenLegends;
import com.scndgen.legends.ui.UiItem;
import com.scndgen.legends.ui.UiScreen;
import com.sun.javafx.tk.Toolkit;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;


/**
 * Created by ifunga on 14/04/2017.
 */
public abstract class Mode implements UiScreen {

    protected static final double MS33 = 3.3e+7;
    protected static final double MS16 = 1.6e+7;
    protected static final double MS1320 = 132.0e+7;
    protected int screenWidth = 852;
    protected int screenHeight = 480;
    protected float opacity;
    protected boolean loadAssets = true;
    protected long lastDelta;
    protected long diff;
    protected boolean paused;
    protected UiItem activeItem;
    private long accumulator16ms;
    private long accumulator33ms;

    /**
     * System notice in overlay
     *
     * @param message - the message to display
     */
    public final void primaryNotice(String message) {
        Overlay.getInstance().primaryNotice(message);
    }

    /**
     * System notice in overlay
     *
     * @param message - the message to display
     */
    public final void secondaryNotice(String message) {
        Overlay.getInstance().secondaryNotice(message);
    }

    public void onLeft() {
        activeItem.left();
    }

    public void onRight() {
        activeItem.right();
    }

    public void onUp() {
        activeItem.up();
    }

    public void onDown() {
        activeItem.down();
    }

    public void onAccept() {
        activeItem.accept();
    }

    public void onBackCancel() {
        activeItem.backCancel();
    }

    public void onTogglePause() {
        paused = !paused;
    }

    public boolean isPaused() {
        return paused;
    }

    public abstract void render(final GraphicsContext gc, final double w, final double h);

    public final void logic(final long delta) {
        lastDelta = delta;
        diff = lastDelta == 0 ? 0 : delta - lastDelta;
        accumulator16ms += delta;
        accumulator33ms += delta;
        update(delta);
    }

    protected void update(final long delta) {
        if (paused) return;
    }

    public void keyReleased(final KeyEvent keyEvent) {

    }

    public void keyPressed(KeyEvent ke) {
        KeyCode keyCode = ke.getCode();
        switch (keyCode) {
            case ENTER:
                onAccept();
                break;
            case ESCAPE:
            case BACK_SPACE:
                onBackCancel();
                break;
            case UP:
            case W:
                onUp();
                break;
            case DOWN:
            case S:
                onDown();
                break;
            case LEFT:
            case A:
                onLeft();
                break;
            case RIGHT:
            case D:
                onRight();
                break;
        }
    }

    public void mouseMoved(final MouseEvent mouseEvent) {

    }

    public void mouseClicked(final MouseEvent mouseEvent) {

    }

    protected boolean isDelta60fps() {
        if (accumulator16ms >= MS16) {
            accumulator16ms = 0;
            return true;
        }
        return false;
    }

    protected boolean isDelta30fps() {
        if (accumulator33ms >= MS33) {
            accumulator33ms = 0;
            return true;
        }
        return false;
    }

    public abstract void newInstance();

    public final void loadAssets() {
        if (!loadAssets) return;
        loadAssetsIml();
        ensureActiveUiItemSet();
    }

    public abstract void loadAssetsIml();

    public abstract void cleanAssets();

    public Font getMyFont(float size) {
        try {
            return Font.loadFont(getClass().getResourceAsStream("font/Sawasdee.ttf"), size);
        } catch (Exception re) {
            return new javafx.scene.text.Font("Sans", size);
        }
    }

    public void drawImage(GraphicsContext gc, Image img, double upperLeftX, double upperLeftY, UiItem uiTile) {
        gc.drawImage(img, upperLeftX, upperLeftY);
        /////////////////////////////////////
        double topLeftX = upperLeftX;
        double topLeftY = upperLeftY;
        double bottomRightX = upperLeftX + img.getWidth();
        double bottomRightY = upperLeftY + img.getHeight();
        /////////////////////////////////////
        double mouseActualX = ScndGenLegends.getInstance().getMouseX();
        double mouseActualY = ScndGenLegends.getInstance().getMouseY();
        boolean check1 = bottomRightX >= mouseActualX && mouseActualX >= topLeftX;
        boolean check2 = topLeftY <= mouseActualY && mouseActualY <= bottomRightY;
        if (check1 && check2) {
            setActiveItem(uiTile);
        }
    }

    public void fillText(GraphicsContext gc, String text, double upperLeftX, double upperLeftY, UiItem uiTile) {
        gc.fillText(text, upperLeftX, upperLeftY);
        /////////////////////////////////////
        double topLeftX = upperLeftX;
        double topLeftY = upperLeftY;
        double bottomRightX = upperLeftX + Toolkit.getToolkit().getFontLoader().computeStringWidth(text, gc.getFont());
        double bottomRightY = upperLeftY - Toolkit.getToolkit().getFontLoader().getFontMetrics(gc.getFont()).getLineHeight();
        /////////////////////////////////////
        double mouseActualX = ScndGenLegends.getInstance().getMouseX();
        double mouseActualY = ScndGenLegends.getInstance().getMouseY();
        boolean check1 = bottomRightX >= mouseActualX && mouseActualX >= topLeftX;
        boolean check2 = topLeftY >= mouseActualY && mouseActualY >= bottomRightY;
        if (check1 && check2) {
            setActiveItem(uiTile);
        }
    }

    public final void setActiveItem(UiItem uiItem) {
        if (activeItem != uiItem) {
            if (activeItem != null) {
                activeItem.leave();
            }
            activeItem = uiItem;
            activeItem.hover();
        }
    }

    @Override
    public final UiItem getActiveItem() {
        return activeItem;
    }

    protected final void ensureActiveUiItemSet() {
        if (activeItem == null)
            throw new RuntimeException("Each mode must have a default active UI item");
    }
}

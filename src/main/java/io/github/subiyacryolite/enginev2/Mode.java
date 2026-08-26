package io.github.subiyacryolite.enginev2;

import com.scndgen.legends.ScndGenLegends;
import com.scndgen.legends.network.NetworkManager;
import com.scndgen.legends.ui.UiItem;
import com.scndgen.legends.ui.UiScreen;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_BACKSPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ENTER;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;

/**
 * Base class for NanoVG game modes (menus, gameplay, etc.).
 * Frame timing comes from {@link #tick(double)} on the GLFW thread.
 */
public abstract class Mode implements UiScreen {

    /** ~60 Hz interval in seconds. */
    protected static final double DT_60 = 1.0 / 60.0;
    /** ~30 Hz interval in seconds. */
    protected static final double DT_30 = 1.0 / 30.0;
    /** Legacy “1.32s” hold used by gameplay achievement/fury cool-down. */
    protected static final double DT_1320 = 1.32;

    protected int screenWidth = 852;
    protected int screenHeight = 480;
    protected float opacity;
    protected boolean loadAssets = true;
    protected boolean paused;
    protected UiItem activeItem;
    protected GlfwEngine engine;
    protected AssetLoader assets;

    /** Shared ~60 Hz tick; advanced in {@link #tick(double)}. */
    protected final Accumulator tick60 = Accumulator.atFrequency(60);
    /** Shared ~30 Hz tick; advanced in {@link #tick(double)}. */
    protected final Accumulator tick30 = Accumulator.atFrequency(30);
    private double elapsedSeconds;
    private double lastFrameDeltaSeconds;

    public final void bind(GlfwEngine engine, AssetLoader assets) {
        this.engine = engine;
        this.assets = assets;
        loadAssets = true;
    }

    public final AssetLoader assets() {
        return assets;
    }

    public final GlfwEngine engine() {
        return engine;
    }

    /**
     * Called once per GLFW frame with {@code glfwGetTime()} delta in seconds.
     * Advances {@link #tick60} / {@link #tick30}; modes call {@code while (tick60.consume())} in {@link #update(double)}.
     */
    public final void tick(double deltaSeconds) {
        lastFrameDeltaSeconds = deltaSeconds;
        double clamped = Math.max(0.0, Math.min(deltaSeconds, 0.25));
        elapsedSeconds += clamped;
        tick60.advance(deltaSeconds);
        tick30.advance(deltaSeconds);
        update(deltaSeconds);
    }

    /** Monotonic time since this mode started receiving ticks (seconds). */
    public final double elapsedSeconds() {
        return elapsedSeconds;
    }

    public final double lastFrameDeltaSeconds() {
        return lastFrameDeltaSeconds;
    }

    public final void primaryNotice(String message) {
        Overlay.get().primaryNotice(message);
    }

    public final void secondaryNotice(String message) {
        Overlay.get().secondaryNotice(message);
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
        if (NetworkManager.get().isOffline()) {
            paused = !paused;
        }
    }

    public boolean isPaused() {
        return paused;
    }

    public abstract void render(DrawContext draw);

    protected void update(double deltaSeconds) {
    }

    public void keyReleased(int glfwKey) {
    }

    public void keyPressed(int glfwKey) {
        switch (glfwKey) {
            case GLFW_KEY_ENTER -> onAccept();
            case GLFW_KEY_ESCAPE, GLFW_KEY_BACKSPACE -> onBackCancel();
            case GLFW_KEY_UP, GLFW_KEY_W -> onUp();
            case GLFW_KEY_DOWN, GLFW_KEY_S -> onDown();
            case GLFW_KEY_LEFT, GLFW_KEY_A -> onLeft();
            case GLFW_KEY_RIGHT, GLFW_KEY_D -> onRight();
            default -> {
            }
        }
    }

    public void mouseMoved(float x, float y) {
    }

    public void mouseClicked(float x, float y) {
    }

    public void mouseClicked(float x, float y, int button) {
        mouseClicked(x, y);
    }

    public abstract void newInstance();

    public final void loadAssets() {
        if (!loadAssets) {
            return;
        }
        loadAssetsIml();
        ensureActiveUiItemSet();
    }

    public abstract void loadAssetsIml();

    public abstract void cleanAssets();

    public void setFont(DrawContext draw, float size) {
        draw.setFont("menu", size);
    }

    public void drawImage(DrawContext draw, NvgImage img, float upperLeftX, float upperLeftY, UiItem uiTile) {
        draw.drawImage(img, upperLeftX, upperLeftY);
        float bottomRightX = upperLeftX + (img == null ? 0 : img.width());
        float bottomRightY = upperLeftY + (img == null ? 0 : img.height());
        float mouseActualX = ScndGenLegends.get().getMouseX();
        float mouseActualY = ScndGenLegends.get().getMouseY();
        if (bottomRightX >= mouseActualX && mouseActualX >= upperLeftX
                && upperLeftY <= mouseActualY && mouseActualY <= bottomRightY) {
            setActiveItem(uiTile);
        }
    }

    public void fillText(DrawContext draw, String text, float x, float y, UiItem uiTile) {
        draw.fillText(text, x, y);
        float bottomRightX = x + draw.measureText(text);
        float bottomRightY = y - draw.getFontSize();
        float mouseActualX = ScndGenLegends.get().getMouseX();
        float mouseActualY = ScndGenLegends.get().getMouseY();
        if (bottomRightX >= mouseActualX && mouseActualX >= x
                && y >= mouseActualY && mouseActualY >= bottomRightY) {
            setActiveItem(uiTile);
        }
    }

    public void fillText(DrawContext draw, String text, float x, float y, UiItem uiTile, float width, float height) {
        draw.fillText(text, x, y);
        float mouseActualX = ScndGenLegends.get().getMouseX();
        float mouseActualY = ScndGenLegends.get().getMouseY();
        if (x + width >= mouseActualX && mouseActualX >= x
                && y >= mouseActualY && mouseActualY >= y - height) {
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
        if (activeItem == null) {
            throw new RuntimeException("Each mode must have a default active UI itemAttacks");
        }
    }

    public void mouseScrolled(double dy) {
        if (dy > 0) {
            onUp();
        } else {
            onDown();
        }
    }

    public void onLeaveMode() {
    }

    public void onEnterMode() {
        elapsedSeconds = 0.0;
        tick60.reset();
        tick30.reset();
    }
}

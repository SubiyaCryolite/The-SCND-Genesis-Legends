package io.github.subiyacryolite.enginev2;

import com.scndgen.legends.ScndGenLegends;
import com.scndgen.legends.network.NetworkManager;
import com.scndgen.legends.ui.UiItem;
import com.scndgen.legends.ui.UiScreen;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Base class for NanoVG game modes (menus, gameplay, etc.).
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
    protected GlfwEngine engine;
    protected AssetLoader assets;

    /** Called by the router when this mode becomes active. */
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

    public final void logic(final long delta) {
        lastDelta = delta;
        diff = lastDelta == 0 ? 0 : delta - lastDelta;
        accumulator16ms += delta;
        accumulator33ms += delta;
        update(delta);
    }

    protected void update(final long delta) {
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
        if (!loadAssets) {
            return;
        }
        loadAssetsIml();
        ensureActiveUiItemSet();
    }

    public abstract void loadAssetsIml();

    public abstract void cleanAssets();

    /**
     * Select the shared "menu" face at the given size.
     */
    public void setFont(DrawContext draw, float size) {
        draw.setFont("menu", size);
    }

    public void drawImage(DrawContext draw, NvgImage img, float upperLeftX, float upperLeftY, UiItem uiTile) {
        draw.drawImage(img, upperLeftX, upperLeftY);
        float topLeftX = upperLeftX;
        float topLeftY = upperLeftY;
        float bottomRightX = upperLeftX + (img == null ? 0 : img.width());
        float bottomRightY = upperLeftY + (img == null ? 0 : img.height());
        float mouseActualX = (float) ScndGenLegends.get().getMouseX();
        float mouseActualY = (float) ScndGenLegends.get().getMouseY();
        boolean check1 = bottomRightX >= mouseActualX && mouseActualX >= topLeftX;
        boolean check2 = topLeftY <= mouseActualY && mouseActualY <= bottomRightY;
        if (check1 && check2) {
            setActiveItem(uiTile);
        }
    }

    public void fillText(DrawContext draw, String text, float x, float y, UiItem uiTile) {
        draw.fillText(text, x, y);
        float bottomRightX = x + draw.measureText(text);
        float bottomRightY = y - draw.getFontSize();
        float mouseActualX = (float) ScndGenLegends.get().getMouseX();
        float mouseActualY = (float) ScndGenLegends.get().getMouseY();
        boolean check1 = bottomRightX >= mouseActualX && mouseActualX >= x;
        boolean check2 = y >= mouseActualY && mouseActualY >= bottomRightY;
        if (check1 && check2) {
            setActiveItem(uiTile);
        }
    }

    public void fillText(DrawContext draw, String text, float x, float y, UiItem uiTile, float width, float height) {
        draw.fillText(text, x, y);
        float bottomRightX = x + width;
        float bottomRightY = y - height;
        float mouseActualX = (float) ScndGenLegends.get().getMouseX();
        float mouseActualY = (float) ScndGenLegends.get().getMouseY();
        boolean check1 = bottomRightX >= mouseActualX && mouseActualX >= x;
        boolean check2 = y >= mouseActualY && mouseActualY >= bottomRightY;
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
    }
}

/**************************************************************************

 The SCND Genesis: Legends is a fighting game based on THE SCND GENESIS,
 a webcomic created by Ifunga Ndana ((([<a href="https://www.scndgen.com">https://www.scndgen.com</a>]))).

 The SCND Genesis: Legends RMX  © 2017 Ifunga Ndana.

 The SCND Genesis: Legends is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 The SCND Genesis: Legends is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with The SCND Genesis: Legends. If not, see <<a href="http://www.gnu.org/licenses/">http://www.gnu.org/licenses/</a>>.

 **************************************************************************/
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

    protected float opacity;
    protected boolean loadAssets = true;
    protected boolean paused;
    protected UiItem activeItem;
    protected GlfwEngine engine;
    protected AssetLoader assets;
    protected AssetLoader.AssetBag bag;

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

    /** Mode-owned image bag; images loaded here are freed in {@link #cleanAssets()}. */
    public final AssetLoader.AssetBag bag() {
        if (bag == null) {
            if (assets == null) {
                throw new IllegalStateException("Mode is not bound to an AssetLoader");
            }
            bag = assets.openBag();
        }
        return bag;
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
        double clamped = Math.clamp(deltaSeconds, 0.0, 0.25);
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
        if (bag == null && assets != null) {
            bag = assets.openBag();
        }
        loadAssetsIml();
        ensureActiveUiItemSet();
    }

    public abstract void loadAssetsIml();

    /**
     * Frees NanoVG images owned by this mode's {@link #bag()}. Subclasses should null fields then call {@code super.cleanAssets()}.
     */
    public void cleanAssets() {
        if (bag != null) {
            bag.close();
            bag = null;
        }
        loadAssets = true;
    }

    public void setFont(DrawContext draw, float size) {
        draw.setFont("menu", size);
    }

    public void drawImage(DrawContext draw, NvgImage img, float upperLeftX, float upperLeftY, UiItem uiTile) {
        draw.drawImage(img, upperLeftX, upperLeftY);
        float bottomRightX = upperLeftX + (img == null ? 0 : img.width());
        float bottomRightY = upperLeftY + (img == null ? 0 : img.height());
        var scndGenLegends = ScndGenLegends.get();
        float mouseActualX = scndGenLegends.getMouseX();
        float mouseActualY = scndGenLegends.getMouseY();
        if (bottomRightX >= mouseActualX && mouseActualX >= upperLeftX
                && upperLeftY <= mouseActualY && mouseActualY <= bottomRightY) {
            setActiveItem(uiTile);
        }
    }

    public void fillText(DrawContext draw, String text, float x, float y, UiItem uiTile) {
        draw.fillText(text, x, y);
        float bottomRightX = x + draw.measureText(text);
        float bottomRightY = y - draw.getFontSize();
        var scndGenLegends = ScndGenLegends.get();
        float mouseActualX = scndGenLegends.getMouseX();
        float mouseActualY = scndGenLegends.getMouseY();
        if (bottomRightX >= mouseActualX && mouseActualX >= x
                && y >= mouseActualY && mouseActualY >= bottomRightY) {
            setActiveItem(uiTile);
        }
    }

    public void fillText(DrawContext draw, String text, float x, float y, UiItem uiTile, float width, float height) {
        draw.fillText(text, x, y);
        var scndGenLegends = ScndGenLegends.get();
        float mouseActualX = scndGenLegends.getMouseX();
        float mouseActualY = scndGenLegends.getMouseY();
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
        cleanAssets();
    }

    public void onEnterMode() {
        elapsedSeconds = 0.0;
        tick60.reset();
        tick30.reset();
    }
}

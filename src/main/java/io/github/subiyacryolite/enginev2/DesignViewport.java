package io.github.subiyacryolite.enginev2;

/**
 * Fixed game design resolution mapped onto the real window.
 * Scale is driven only by horizontal size; vertical mismatch is letterboxed.
 * Scenes draw in design space; {@link #begin(long)} / {@link #end(long)} apply NanoVG scale.
 */
public final class DesignViewport {
    public static final int DESIGN_WIDTH = 852;
    public static final int DESIGN_HEIGHT = 480;

    private float scale = 1f;
    private float offsetX;
    private float offsetY;
    private int windowWidth = DESIGN_WIDTH;
    private int windowHeight = DESIGN_HEIGHT;

    public void update(int windowWidth, int windowHeight) {
        this.windowWidth = Math.max(1, windowWidth);
        this.windowHeight = Math.max(1, windowHeight);
        scale = this.windowWidth / (float) DESIGN_WIDTH;
        offsetX = 0f;
        offsetY = (this.windowHeight - DESIGN_HEIGHT * scale) * 0.5f;
    }

    public float scale() {
        return scale;
    }

    public float offsetX() {
        return offsetX;
    }

    public float offsetY() {
        return offsetY;
    }

    public int windowWidth() {
        return windowWidth;
    }

    public int windowHeight() {
        return windowHeight;
    }

    public float toDesignX(double windowX) {
        return (float) ((windowX - offsetX) / scale);
    }

    public float toDesignY(double windowY) {
        return (float) ((windowY - offsetY) / scale);
    }

    public void begin(long vg) {
        org.lwjgl.nanovg.NanoVG.nvgSave(vg);
        org.lwjgl.nanovg.NanoVG.nvgTranslate(vg, offsetX, offsetY);
        org.lwjgl.nanovg.NanoVG.nvgScale(vg, scale, scale);
    }

    public void end(long vg) {
        org.lwjgl.nanovg.NanoVG.nvgRestore(vg);
    }
}

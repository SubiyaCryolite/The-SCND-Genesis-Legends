package io.github.subiyacryolite.enginev2;

/**
 * NanoVG-backed image handle with pixel dimensions.
 */
public final class NvgImage {
    private final int handle;
    private final int width;
    private final int height;

    public NvgImage(int handle, int width, int height) {
        this.handle = handle;
        this.width = width;
        this.height = height;
    }

    public int handle() {
        return handle;
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }
}

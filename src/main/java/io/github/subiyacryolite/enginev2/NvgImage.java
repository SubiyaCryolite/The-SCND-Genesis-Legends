package io.github.subiyacryolite.enginev2;

/**
 * NanoVG-backed image handle with pixel dimensions.
 */
public record NvgImage(int handle, int width, int height) {
    public static final NvgImage NONE = new NvgImage(0, 0, 0);

    public boolean isValid() {
        return handle != 0;
    }
}

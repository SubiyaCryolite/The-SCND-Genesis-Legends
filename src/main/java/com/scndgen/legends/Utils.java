package com.scndgen.legends;

import io.github.subiyacryolite.enginev2.DrawContext;

public final class Utils {
    private Utils() {
    }

    public static float computeStringWidth(String string, DrawContext draw) {
        return draw.measureText(string);
    }
}

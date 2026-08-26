package io.github.subiyacryolite.enginev2;

public record Rgba(float r, float g, float b, float a) {
    public static Rgba of(float r, float g, float b) {
        return new Rgba(r, g, b, 1f);
    }

    public static final Rgba BLACK = of(0, 0, 0);
    public static final Rgba WHITE = of(1, 1, 1);
}

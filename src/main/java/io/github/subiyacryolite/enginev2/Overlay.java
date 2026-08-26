package io.github.subiyacryolite.enginev2;

/**
 * Fade-in / hold / fade-out notification overlays drawn in design space.
 */
public final class Overlay {

    private static Overlay instance;
    private String primaryNotification = "";
    private String secondaryNotification = "";
    private float primaryOpacity = 0.0f;
    private float secondaryOpacity = 0.0f;
    private float primaryTimeout;
    private float secondaryTimeout;
    private boolean increasePrimaryOpacity;
    private boolean fadeOutPrimaryNotification;
    private boolean increaseSecondaryOpacity;
    private boolean fadeOutSecondaryNotification;

    private Overlay() {
    }

    public static synchronized Overlay get() {
        if (instance == null) {
            instance = new Overlay();
        }
        return instance;
    }

    /**
     * Draw primary/secondary notices (right-aligned near the top).
     */
    public void overlay(DrawContext draw, float w, float h) {
        if (increasePrimaryOpacity && primaryOpacity < 0.99f) {
            primaryOpacity = primaryOpacity + 0.01f;
        } else {
            increasePrimaryOpacity = false;
            fadeOutPrimaryNotification = true;
        }
        if (!increasePrimaryOpacity) {
            if (primaryTimeout < 180.0f) {
                primaryTimeout = primaryTimeout + 1.0f;
            } else if (primaryOpacity > 0.01f) {
                primaryOpacity = primaryOpacity - 0.01f;
            }
        }
        float primaryWidth = draw.measureText(primaryNotification);
        draw.setFill(Rgba.BLACK.r(), Rgba.BLACK.g(), Rgba.BLACK.b());
        draw.setGlobalAlpha(primaryOpacity / 2.5f);
        draw.fillRoundRect(w - 5 - 5 - primaryWidth, 55, 14 + (primaryNotification.length() * 8), 20, 10);
        draw.setGlobalAlpha(primaryOpacity);
        draw.setFill(Rgba.WHITE.r(), Rgba.WHITE.g(), Rgba.WHITE.b());
        draw.fillText(primaryNotification, w - 5 - primaryWidth, 70);
        draw.setGlobalAlpha(1.0f);

        if (increaseSecondaryOpacity && secondaryOpacity < 0.99f) {
            secondaryOpacity = secondaryOpacity + 0.01f;
        } else {
            increaseSecondaryOpacity = false;
            fadeOutSecondaryNotification = true;
        }
        if (!increaseSecondaryOpacity) {
            if (secondaryTimeout < 360.0f) {
                secondaryTimeout = secondaryTimeout + 1.0f;
            } else if (secondaryOpacity > 0.01f) {
                secondaryOpacity = secondaryOpacity - 0.01f;
            }
        }
        float secondaryWidth = draw.measureText(secondaryNotification);
        draw.setFill(Rgba.BLACK.r(), Rgba.BLACK.g(), Rgba.BLACK.b());
        draw.setGlobalAlpha(secondaryOpacity / 2.5f);
        draw.fillRoundRect(w - 5 - 5 - secondaryWidth, 35, 14 + (secondaryNotification.length() * 8), 20, 10);
        draw.setGlobalAlpha(secondaryOpacity);
        draw.setFill(Rgba.WHITE.r(), Rgba.WHITE.g(), Rgba.WHITE.b());
        draw.fillText(secondaryNotification, w - 5 - secondaryWidth, 50);
        draw.setGlobalAlpha(1.0f);
    }

    public void primaryNotice(String message) {
        primaryNotification = message;
        increasePrimaryOpacity = true;
        fadeOutPrimaryNotification = false;
        primaryOpacity = 0.0f;
        primaryTimeout = 0.0f;
    }

    public void secondaryNotice(String message) {
        secondaryNotification = message;
        increaseSecondaryOpacity = true;
        fadeOutSecondaryNotification = false;
        secondaryOpacity = 0.0f;
        secondaryTimeout = 0.0f;
    }
}

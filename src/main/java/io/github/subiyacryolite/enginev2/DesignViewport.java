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

/**
 * Fixed 852×480 (16:9) design space mapped onto the real window.
 * Scale uses the limiting axis so the design aspect is preserved:
 * wider windows (e.g. 21:9) get side bars; taller windows (e.g. 4:3) get top/bottom bars.
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
        float scaleX = this.windowWidth / (float) DESIGN_WIDTH;
        float scaleY = this.windowHeight / (float) DESIGN_HEIGHT;
        scale = Math.min(scaleX, scaleY);
        offsetX = (this.windowWidth - DESIGN_WIDTH * scale) * 0.5f;
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

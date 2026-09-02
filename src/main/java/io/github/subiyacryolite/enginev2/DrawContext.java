/**************************************************************************

 The SCND Genesis: Legends is a fighting game based on THE SCND GENESIS,
 a webcomic created by Ifunga Ndana (https://www.scndgen.com).

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
 along with The SCND Genesis: Legends. If not, see <https://www.gnu.org/licenses/>.

 **************************************************************************/
package io.github.subiyacryolite.enginev2;

import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGPaint;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;

import static org.lwjgl.nanovg.NanoVG.*;
import static org.lwjgl.system.MemoryStack.stackPush;

/**
 * Thin immediate-mode drawing API for NanoVG scenes (design-space coordinates).
 * All NanoVG structs are allocated via {@link MemoryStack} (nested push per call).
 */
public final class DrawContext {
    private final long vg;

    private float fillR = 1f;
    private float fillG = 1f;
    private float fillB = 1f;
    private float fillA = 1f;
    private float globalAlpha = 1f;
    private float fontSize = 16f;
    private String fontFace = "menu";

    /**
     * Optional frame-scoped stack pushed by the engine loop. Nested draw calls still
     * {@link MemoryStack#stackPush() push} their own frames so allocations are reclaimed per call.
     */
    private MemoryStack frameStack;

    public DrawContext(long vg) {
        this.vg = vg;
    }

    public long vg() {
        return vg;
    }

    public MemoryStack frameStack() {
        return frameStack;
    }

    public void beginFrame(MemoryStack frameStack, float width, float height, float pixelRatio) {
        this.frameStack = frameStack;
        nvgBeginFrame(vg, width, height, pixelRatio);
        nvgFontFace(vg, fontFace);
        nvgFontSize(vg, fontSize);
        nvgTextAlign(vg, NVG_ALIGN_LEFT | NVG_ALIGN_BASELINE);
        nvgGlobalAlpha(vg, globalAlpha);
    }

    public void endFrame() {
        nvgEndFrame(vg);
        frameStack = null;
    }

    public void setFill(float r, float g, float b) {
        setFill(r, g, b, 1f);
    }

    public void setFill(float r, float g, float b, float a) {
        fillR = r;
        fillG = g;
        fillB = b;
        fillA = a;
    }

    public void setFill(Rgba rgba) {
        setFill(rgba.r(), rgba.g(), rgba.b(), rgba.a());
    }

    public void setGlobalAlpha(float alpha) {
        globalAlpha = Math.max(0f, Math.min(1f, alpha));
        nvgGlobalAlpha(vg, globalAlpha);
    }

    public float getGlobalAlpha() {
        return globalAlpha;
    }

    public void setFont(String face, float size) {
        fontFace = face;
        fontSize = size;
        nvgFontFace(vg, fontFace);
        nvgFontSize(vg, fontSize);
    }

    public void setFontSize(float size) {
        fontSize = size;
        nvgFontSize(vg, fontSize);
    }

    public float getFontSize() {
        return fontSize;
    }

    public void fillRect(float x, float y, float w, float h) {
        try (MemoryStack stack = stackPush()) {
            nvgBeginPath(vg);
            nvgRect(vg, x, y, w, h);
            nvgFillColor(vg, fillColor(stack));
            nvgFill(vg);
        }
    }

    public void fillRoundRect(float x, float y, float w, float h, float radius) {
        try (MemoryStack stack = stackPush()) {
            nvgBeginPath(vg);
            nvgRoundedRect(vg, x, y, w, h, radius);
            nvgFillColor(vg, fillColor(stack));
            nvgFill(vg);
        }
    }

    public void strokeRoundRect(float x, float y, float w, float h, float radius, float strokeWidth) {
        try (MemoryStack stack = stackPush()) {
            nvgBeginPath(vg);
            nvgRoundedRect(vg, x, y, w, h, radius);
            nvgStrokeWidth(vg, strokeWidth);
            nvgStrokeColor(vg, fillColor(stack));
            nvgStroke(vg);
        }
    }

    public void fillCircle(float centerX, float centerY, float radius) {
        try (MemoryStack stack = stackPush()) {
            nvgBeginPath(vg);
            nvgCircle(vg, centerX, centerY, radius);
            nvgFillColor(vg, fillColor(stack));
            nvgFill(vg);
        }
    }

    /**
     * Pie/arc fill for HUD meters.
     * Angles are degrees; 0 is east, positive is counter-clockwise.
     */
    public void fillArc(float x, float y, float w, float h, float startAngleDeg, float extentDeg) {
        float cx = x + w * 0.5f;
        float cy = y + h * 0.5f;
        float rx = w * 0.5f;
        float ry = h * 0.5f;
        float a0 = (float) Math.toRadians(-startAngleDeg);
        float a1 = (float) Math.toRadians(-(startAngleDeg + extentDeg));
        try (MemoryStack stack = stackPush()) {
            nvgBeginPath(vg);
            nvgMoveTo(vg, cx, cy);
            nvgArc(vg, cx, cy, Math.min(rx, ry), a0, a1, extentDeg >= 0 ? NVG_CCW : NVG_CW);
            nvgClosePath(vg);
            nvgFillColor(vg, fillColor(stack));
            nvgFill(vg);
        }
    }

    public void drawImage(NvgImage image, float x, float y) {
        if (image == null || !image.isValid()) {
            return;
        }
        drawImage(image, x, y, image.width(), image.height());
    }

    public void drawImage(NvgImage image, float x, float y, float w, float h) {
        if (image == null || !image.isValid()) {
            return;
        }
        try (MemoryStack stack = stackPush()) {
            NVGPaint paint = NVGPaint.malloc(stack);
            nvgImagePattern(vg, x, y, w, h, 0, image.handle(), 1f, paint);
            nvgBeginPath(vg);
            nvgRect(vg, x, y, w, h);
            nvgFillPaint(vg, paint);
            nvgFill(vg);
        }
    }

    /**
     * Horizontal flip (negative destination width).
     */
    public void drawImageFlippedHorizontal(NvgImage image, float x, float y) {
        if (image == null || !image.isValid()) {
            return;
        }
        float w = image.width();
        float h = image.height();
        nvgSave(vg);
        nvgTranslate(vg, x + w, y);
        nvgScale(vg, -1f, 1f);
        drawImage(image, 0, 0, w, h);
        nvgRestore(vg);
    }

    public void fillText(String text, float x, float y) {
        if (text == null || text.isEmpty()) {
            return;
        }
        try (MemoryStack stack = stackPush()) {
            nvgFontFace(vg, fontFace);
            nvgFontSize(vg, fontSize);
            nvgTextAlign(vg, NVG_ALIGN_LEFT | NVG_ALIGN_BASELINE);
            nvgFillColor(vg, fillColor(stack));
            nvgText(vg, x, y, text);
        }
    }

    public float measureText(String text) {
        if (text == null || text.isEmpty()) {
            return 0f;
        }
        try (MemoryStack stack = stackPush()) {
            FloatBuffer bounds = stack.mallocFloat(4);
            nvgFontFace(vg, fontFace);
            nvgFontSize(vg, fontSize);
            nvgTextBounds(vg, 0, 0, text, bounds);
            return bounds.get(2) - bounds.get(0);
        }
    }

    private NVGColor fillColor(MemoryStack stack) {
        return nvgRGBAf(fillR, fillG, fillB, fillA, NVGColor.malloc(stack));
    }
}

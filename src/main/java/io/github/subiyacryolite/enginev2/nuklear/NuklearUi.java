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
package io.github.subiyacryolite.enginev2.nuklear;

import org.lwjgl.system.MemoryStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Manages a Nuklear backend and a stack of {@link UiOverlay} panels.
 * Adapted from the LWJGL Nuklear GLFWDemo integration pattern.
 */
public final class NuklearUi implements AutoCloseable {

    private static final String DEFAULT_FONT = "font/Sawasdee.ttf";

    private final List<UiOverlay> overlays = new ArrayList<>();
    private NuklearBackend backend;

    public void init(long window) {
        if (backend != null) {
            throw new IllegalStateException("NuklearUi already initialized");
        }
        backend = new NuklearBackend(window, DEFAULT_FONT);
        backend.init();
    }

    public NuklearBackend backend() {
        return Objects.requireNonNull(backend, "NuklearUi not initialized");
    }

    public void push(UiOverlay overlay) {
        overlays.add(Objects.requireNonNull(overlay));
        syncUiActive();
    }

    public void pop() {
        if (!overlays.isEmpty()) {
            overlays.remove(overlays.size() - 1);
            syncUiActive();
        }
    }

    public boolean hasOverlay() {
        return !overlays.isEmpty();
    }

    public boolean isBlocking() {
        return hasOverlay();
    }

    public void beginInput() {
        backend().beginInput();
    }

    public void endInput() {
        backend().endInput();
    }

    public void onScroll(double x, double y) {
        backend().onScroll(x, y);
    }

    public void onChar(int codepoint) {
        backend().onChar(codepoint);
    }

    public void onKey(int key, int action) {
        backend().onKey(key, action);
    }

    public void onCursorPos(double x, double y) {
        backend().onCursorPos(x, y);
    }

    public void onMouseButton(int button, int action, double x, double y) {
        backend().onMouseButton(button, action, x, y);
    }

    public boolean wantsKeyboard() {
        return isBlocking() || backend().wantsKeyboard();
    }

    public boolean wantsMouse() {
        return isBlocking() || backend().wantsMouse();
    }

    /**
     * Updates frame sizes, lays out open overlays, and renders Nuklear.
     */
    public void layoutAndRender(MemoryStack stack, int winW, int winH, int fbW, int fbH) {
        NuklearBackend nk = backend();
        nk.newFrame(winW, winH, fbW, fbH);
        layoutOverlays(stack, winW, winH);
        nk.render();
    }

    public void layoutOverlays(MemoryStack stack, int winW, int winH) {
        NuklearBackend nk = backend();
        // Snapshot so overlays may push/pop during layout (e.g. chained confirm dialogs).
        List<UiOverlay> snapshot = new ArrayList<>(overlays);
        List<UiOverlay> closed = new ArrayList<>();
        for (UiOverlay overlay : snapshot) {
            if (!overlay.layout(nk.ctx(), stack, winW, winH)) {
                closed.add(overlay);
            }
        }
        if (!closed.isEmpty()) {
            overlays.removeAll(closed);
        }
        syncUiActive();
    }

    @Override
    public void close() {
        overlays.clear();
        if (backend != null) {
            backend.close();
            backend = null;
        }
    }

    private void syncUiActive() {
        if (backend != null) {
            backend.setUiActive(!overlays.isEmpty());
        }
    }
}

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

import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.MemoryStack;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.lwjgl.glfw.GLFW.glfwGetMonitors;
import static org.lwjgl.glfw.GLFW.glfwGetVideoModes;
import static org.lwjgl.system.MemoryStack.stackPush;

/**
 * Queries GLFW video modes across all monitors. Options list unique
 * {@code width × height} sizes; refresh follows the window’s current monitor.
 * {@link DesignViewport} letterboxes the fixed 852×480 design space.
 */
public final class DisplayModes {

    /** Default window size when no resolution is saved. Design space remains 852×480. */
    public static final int DEFAULT_WIDTH = 1280;
    public static final int DEFAULT_HEIGHT = 720;

    public record Mode(int width, int height) {
        public String label() {
            return width + " x " + height;
        }

        /** Persisted as {@code widthxheight}. Legacy {@code widthxheight@hz} still loads. */
        public String storageKey() {
            return width + "x" + height;
        }

        public int pixelCount() {
            return width * height;
        }
    }

    private DisplayModes() {
    }

    /**
     * Unique width×height sizes from every connected monitor, sorted by pixel
     * count descending. Always includes the default window size and design space.
     * Must be called after {@code glfwInit()}.
     */
    public static List<Mode> queryAll() {
        Map<String, Mode> unique = new LinkedHashMap<>();
        try (MemoryStack stack = stackPush()) {
            PointerBuffer monitors = glfwGetMonitors();
            if (monitors != null) {
                for (int m = 0; m < monitors.limit(); m++) {
                    long monitor = monitors.get(m);
                    GLFWVidMode.Buffer modes = glfwGetVideoModes(monitor);
                    if (modes == null) {
                        continue;
                    }
                    for (int i = 0; i < modes.limit(); i++) {
                        GLFWVidMode mode = modes.get(i);
                        int w = mode.width();
                        int h = mode.height();
                        if (w < 1 || h < 1) {
                            continue;
                        }
                        unique.putIfAbsent(w + "x" + h, new Mode(w, h));
                    }
                }
            }
        }

        unique.putIfAbsent(DEFAULT_WIDTH + "x" + DEFAULT_HEIGHT, defaultWindow());
        unique.putIfAbsent(
                DesignViewport.DESIGN_WIDTH + "x" + DesignViewport.DESIGN_HEIGHT,
                designFallback()
        );

        List<Mode> result = new ArrayList<>(unique.values());
        result.sort(Comparator
                .comparingInt(Mode::pixelCount).reversed()
                .thenComparing(Comparator.comparingInt(Mode::width).reversed())
                .thenComparing(Comparator.comparingInt(Mode::height).reversed()));
        return result;
    }

    public static Mode findByStorageKey(List<Mode> modes, String key) {
        if (key == null || key.isBlank()) {
            return modes.isEmpty() ? defaultWindow() : preferredDefault(modes);
        }
        String normalized = key.replace(" ", "").toLowerCase();
        int at = normalized.indexOf('@');
        if (at >= 0) {
            normalized = normalized.substring(0, at);
        }
        for (Mode mode : modes) {
            if (mode.storageKey().equalsIgnoreCase(normalized)) {
                return mode;
            }
        }
        int x = normalized.indexOf('x');
        if (x > 0) {
            try {
                int w = Integer.parseInt(normalized.substring(0, x));
                int h = Integer.parseInt(normalized.substring(x + 1));
                for (Mode mode : modes) {
                    if (mode.width() == w && mode.height() == h) {
                        return mode;
                    }
                }
                return new Mode(w, h);
            } catch (NumberFormatException ignored) {
            }
        }
        return modes.isEmpty() ? defaultWindow() : preferredDefault(modes);
    }

    public static int indexOf(List<Mode> modes, Mode mode) {
        if (mode == null || modes.isEmpty()) {
            return 0;
        }
        for (int i = 0; i < modes.size(); i++) {
            Mode candidate = modes.get(i);
            if (candidate.width() == mode.width() && candidate.height() == mode.height()) {
                return i;
            }
        }
        return 0;
    }

    public static Mode defaultWindow() {
        return new Mode(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public static Mode designFallback() {
        return new Mode(DesignViewport.DESIGN_WIDTH, DesignViewport.DESIGN_HEIGHT);
    }

    private static Mode preferredDefault(List<Mode> modes) {
        for (Mode mode : modes) {
            if (mode.width() == DEFAULT_WIDTH && mode.height() == DEFAULT_HEIGHT) {
                return mode;
            }
        }
        return modes.isEmpty() ? defaultWindow() : modes.getLast();
    }
}

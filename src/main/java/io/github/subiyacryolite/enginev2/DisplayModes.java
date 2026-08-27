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
import java.util.Objects;

import static org.lwjgl.glfw.GLFW.glfwGetMonitorName;
import static org.lwjgl.glfw.GLFW.glfwGetMonitors;
import static org.lwjgl.glfw.GLFW.glfwGetVideoModes;
import static org.lwjgl.system.MemoryStack.stackPush;

/**
 * Queries GLFW video modes across all monitors. Any aspect ratio is fine:
 * {@link DesignViewport} letterboxes the fixed 852×480 design space.
 */
public final class DisplayModes {

    /** Default window size when no resolution is saved. Design space remains 852×480. */
    public static final int DEFAULT_WIDTH = 1280;
    public static final int DEFAULT_HEIGHT = 720;

    public record Mode(int width, int height, int refreshRateHz, String monitorName) {
        public String label() {
            String monitor = monitorName == null || monitorName.isBlank()
                    ? ""
                    : " · " + asciiSafe(monitorName);
            return width + " x " + height + " @" + refreshRateHz + "Hz" + monitor;
        }

        /** Persisted as {@code widthxheight@hz}. Windowed apply uses width×height. */
        public String storageKey() {
            return width + "x" + height + "@" + refreshRateHz;
        }

        public int pixelCount() {
            return width * height;
        }

        private static String asciiSafe(String value) {
            StringBuilder sb = new StringBuilder(value.length());
            for (int i = 0; i < value.length(); i++) {
                char c = value.charAt(i);
                sb.append(c >= 32 && c < 256 ? c : '?');
            }
            return sb.toString();
        }
    }

    private DisplayModes() {
    }

    /**
     * Unique width×height×refresh modes from every connected monitor,
     * sorted by size (pixels) descending, then refresh rate descending.
     * Always includes the default window size and the design resolution.
     * Must be called after {@code glfwInit()}.
     */
    public static List<Mode> queryAll() {
        Map<String, Mode> unique = new LinkedHashMap<>();
        try (MemoryStack stack = stackPush()) {
            PointerBuffer monitors = glfwGetMonitors();
            if (monitors != null) {
                for (int m = 0; m < monitors.limit(); m++) {
                    long monitor = monitors.get(m);
                    String monitorName = Objects.toString(glfwGetMonitorName(monitor), "Monitor " + (m + 1));
                    GLFWVidMode.Buffer modes = glfwGetVideoModes(monitor);
                    if (modes == null) {
                        continue;
                    }
                    for (int i = 0; i < modes.limit(); i++) {
                        GLFWVidMode mode = modes.get(i);
                        int w = mode.width();
                        int h = mode.height();
                        int hz = mode.refreshRate();
                        if (w < 1 || h < 1) {
                            continue;
                        }
                        String key = w + "x" + h + "@" + hz;
                        unique.putIfAbsent(key, new Mode(w, h, hz, monitorName));
                    }
                }
            }
        }

        unique.putIfAbsent(
                DEFAULT_WIDTH + "x" + DEFAULT_HEIGHT + "@60",
                new Mode(DEFAULT_WIDTH, DEFAULT_HEIGHT, 60, "Default")
        );
        unique.putIfAbsent(
                DesignViewport.DESIGN_WIDTH + "x" + DesignViewport.DESIGN_HEIGHT + "@60",
                new Mode(DesignViewport.DESIGN_WIDTH, DesignViewport.DESIGN_HEIGHT, 60, "Design")
        );

        List<Mode> result = new ArrayList<>(unique.values());
        result.sort(Comparator
                .comparingInt(Mode::pixelCount).reversed()
                .thenComparing(Comparator.comparingInt(Mode::width).reversed())
                .thenComparing(Comparator.comparingInt(Mode::height).reversed())
                .thenComparing(Comparator.comparingInt(Mode::refreshRateHz).reversed()));
        return result;
    }

    public static Mode findByStorageKey(List<Mode> modes, String key) {
        if (key == null || key.isBlank()) {
            return modes.isEmpty() ? defaultWindow() : preferredDefault(modes);
        }
        String normalized = key.replace(" ", "").toLowerCase();
        for (Mode mode : modes) {
            if (mode.storageKey().equalsIgnoreCase(normalized)) {
                return mode;
            }
        }
        // Legacy "WxH" or "WxH@hz"
        int x = normalized.indexOf('x');
        if (x > 0) {
            try {
                int w = Integer.parseInt(normalized.substring(0, x));
                String rest = normalized.substring(x + 1);
                int at = rest.indexOf('@');
                int h;
                int hz = 60;
                if (at >= 0) {
                    h = Integer.parseInt(rest.substring(0, at));
                    hz = Integer.parseInt(rest.substring(at + 1));
                } else {
                    h = Integer.parseInt(rest);
                }
                for (Mode mode : modes) {
                    if (mode.width() == w && mode.height() == h && mode.refreshRateHz() == hz) {
                        return mode;
                    }
                }
                for (Mode mode : modes) {
                    if (mode.width() == w && mode.height() == h) {
                        return mode;
                    }
                }
                return new Mode(w, h, hz, "Saved");
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
            if (candidate.width() == mode.width()
                    && candidate.height() == mode.height()
                    && candidate.refreshRateHz() == mode.refreshRateHz()) {
                return i;
            }
        }
        for (int i = 0; i < modes.size(); i++) {
            if (modes.get(i).storageKey().startsWith(mode.width() + "x" + mode.height())) {
                return i;
            }
        }
        return 0;
    }

    public static Mode defaultWindow() {
        return new Mode(DEFAULT_WIDTH, DEFAULT_HEIGHT, 60, "Default");
    }

    public static Mode designFallback() {
        return new Mode(DesignViewport.DESIGN_WIDTH, DesignViewport.DESIGN_HEIGHT, 60, "Design");
    }

    private static Mode preferredDefault(List<Mode> modes) {
        for (Mode mode : modes) {
            if (mode.width() == DEFAULT_WIDTH && mode.height() == DEFAULT_HEIGHT) {
                return mode;
            }
        }
        return modes.isEmpty() ? defaultWindow() : modes.get(modes.size() - 1);
    }
}

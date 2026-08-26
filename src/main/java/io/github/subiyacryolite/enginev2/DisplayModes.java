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

    public record Mode(int width, int height, int refreshRateHz, String monitorName) {
        public String label() {
            String monitor = monitorName == null || monitorName.isBlank() ? "" : " · " + monitorName;
            return width + " x " + height + " @" + refreshRateHz + "Hz" + monitor;
        }

        public String storageKey() {
            return width + "x" + height;
        }
    }

    private DisplayModes() {
    }

    /**
     * Unique width×height modes from every connected monitor (highest refresh kept),
     * sorted largest-first. Always includes the design resolution.
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
                        if (w < 1 || h < 1) {
                            continue;
                        }
                        String key = w + "x" + h;
                        Mode candidate = new Mode(w, h, mode.refreshRate(), monitorName);
                        Mode existing = unique.get(key);
                        if (existing == null || candidate.refreshRateHz() > existing.refreshRateHz()) {
                            unique.put(key, candidate);
                        }
                    }
                }
            }
        }

        unique.putIfAbsent(
                DesignViewport.DESIGN_WIDTH + "x" + DesignViewport.DESIGN_HEIGHT,
                new Mode(DesignViewport.DESIGN_WIDTH, DesignViewport.DESIGN_HEIGHT, 60, "Design")
        );

        List<Mode> result = new ArrayList<>(unique.values());
        result.sort(Comparator
                .comparingInt(Mode::width).reversed()
                .thenComparing(Comparator.comparingInt(Mode::height).reversed())
                .thenComparing(Comparator.comparingInt(Mode::refreshRateHz).reversed()));
        return result;
    }

    public static Mode findByStorageKey(List<Mode> modes, String key) {
        if (key == null || key.isBlank()) {
            return modes.isEmpty() ? designFallback() : preferredDefault(modes);
        }
        String normalized = key.replace(" ", "").toLowerCase();
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
                return new Mode(w, h, 60, "Saved");
            } catch (NumberFormatException ignored) {
            }
        }
        return modes.isEmpty() ? designFallback() : preferredDefault(modes);
    }

    public static int indexOf(List<Mode> modes, Mode mode) {
        if (mode == null || modes.isEmpty()) {
            return 0;
        }
        for (int i = 0; i < modes.size(); i++) {
            if (modes.get(i).storageKey().equals(mode.storageKey())) {
                return i;
            }
        }
        return 0;
    }

    public static Mode designFallback() {
        return new Mode(DesignViewport.DESIGN_WIDTH, DesignViewport.DESIGN_HEIGHT, 60, "Design");
    }

    private static Mode preferredDefault(List<Mode> modes) {
        for (Mode mode : modes) {
            if (mode.width() == DesignViewport.DESIGN_WIDTH && mode.height() == DesignViewport.DESIGN_HEIGHT) {
                return mode;
            }
        }
        return modes.get(modes.size() - 1);
    }
}

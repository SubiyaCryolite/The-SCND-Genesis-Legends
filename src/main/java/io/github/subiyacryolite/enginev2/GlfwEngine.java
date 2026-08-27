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

import io.github.subiyacryolite.enginev2.nuklear.NuklearUi;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.IntConsumer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.nanovg.NanoVGGL3.NVG_ANTIALIAS;
import static org.lwjgl.nanovg.NanoVGGL3.NVG_STENCIL_STROKES;
import static org.lwjgl.nanovg.NanoVGGL3.nvgCreate;
import static org.lwjgl.nanovg.NanoVGGL3.nvgDelete;
import static org.lwjgl.opengl.GL11C.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11C.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11C.GL_STENCIL_BUFFER_BIT;
import static org.lwjgl.opengl.GL11C.glClear;
import static org.lwjgl.opengl.GL11C.glClearColor;
import static org.lwjgl.opengl.GL11C.glViewport;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.system.MemoryUtil.memFree;

/**
 * GLFW window + OpenGL 3.3 core + NanoVG scene rendering + Nuklear UI overlays.
 * Scenes always draw in {@link DesignViewport} 16:9 design space; the window is letterboxed.
 */
public final class GlfwEngine implements AutoCloseable {
    public interface Scene {
        void init(GlfwEngine engine, AssetLoader loader, DrawContext draw);

        void update(double deltaSeconds);

        /**
         * Draw in design coordinates ({@link DesignViewport#DESIGN_WIDTH} x {@link DesignViewport#DESIGN_HEIGHT}).
         * Scale/letterbox is applied by the engine.
         */
        void render(MemoryStack stack, DrawContext draw);

        default void onKey(int key, int action, int mods) {
        }

        /** Mouse position is already converted to design space. */
        default void onMouseMove(float designX, float designY) {
        }

        /** Mouse position is already converted to design space. */
        default void onMouseButton(int button, int action, float designX, float designY) {
        }

        /** Vertical scroll (positive = up / away from user, GLFW convention). */
        default void onScroll(double yOffset) {
        }

        default void onWindowResize(int windowWidth, int windowHeight) {
        }
    }

    private final String title;
    private final Scene scene;
    private final List<ByteBuffer> retainedFontData = new ArrayList<>();
    private final NuklearUi nuklearUi = new NuklearUi();
    private final DesignViewport viewport = new DesignViewport();
    private final List<IntConsumer> resizeListeners = new ArrayList<>();

    private long window;
    private long vg;
    private DrawContext draw;
    private AssetLoader loader;
    private boolean running;
    private volatile boolean resizePending;
    private float letterboxRf;
    private float letterboxGf;
    private float letterboxBf;

    public GlfwEngine(String title, Scene scene) {
        this.title = Objects.requireNonNull(title);
        this.scene = Objects.requireNonNull(scene);
    }

    public NuklearUi ui() {
        return nuklearUi;
    }

    public DesignViewport viewport() {
        return viewport;
    }

    public void run() {
        initWindow();
        initGlAndNanoVg();
        AudioEngine.init();
        nuklearUi.init(window);
        scene.init(this, loader, draw);
        loop();
    }

    private void initWindow() {
        GLFWErrorCallback.createPrint(System.err).set();
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
        glfwWindowHint(GLFW_STENCIL_BITS, 8);
        glfwWindowHint(GLFW_DEPTH_BITS, 24);

        DisplayModes.Mode initial = resolveSavedResolution();
        window = glfwCreateWindow(initial.width(), initial.height(), title, NULL, NULL);
        if (window == NULL) {
            throw new IllegalStateException("Failed to create GLFW window (OpenGL 3.3 core)");
        }
        refreshLetterboxColor();

        glfwSetKeyCallback(window, (win, key, scancode, action, mods) -> {
            nuklearUi.onKey(key, action);
            if (nuklearUi.isBlocking()) {
                return;
            }
            scene.onKey(key, action, mods);
        });
        glfwSetCharCallback(window, (win, codepoint) -> nuklearUi.onChar(codepoint));
        glfwSetScrollCallback(window, (win, x, y) -> {
            nuklearUi.onScroll(x, y);
            if (!nuklearUi.isBlocking()) {
                scene.onScroll(y);
            }
        });
        glfwSetCursorPosCallback(window, (win, x, y) -> {
            nuklearUi.onCursorPos(x, y);
            if (!nuklearUi.isBlocking()) {
                scene.onMouseMove(viewport.toDesignX(x), viewport.toDesignY(y));
            }
        });
        glfwSetMouseButtonCallback(window, (win, button, action, mods) -> {
            try (MemoryStack stack = stackPush()) {
                DoubleBuffer xpos = stack.mallocDouble(1);
                DoubleBuffer ypos = stack.mallocDouble(1);
                glfwGetCursorPos(win, xpos, ypos);
                nuklearUi.onMouseButton(button, action, xpos.get(0), ypos.get(0));
                if (!nuklearUi.isBlocking()) {
                    scene.onMouseButton(
                            button,
                            action,
                            viewport.toDesignX(xpos.get(0)),
                            viewport.toDesignY(ypos.get(0))
                    );
                }
            }
        });
        glfwSetWindowSizeCallback(window, (win, width, height) -> {
            viewport.update(width, height);
            resizePending = true;
            for (IntConsumer listener : resizeListeners) {
                listener.accept(width);
            }
            scene.onWindowResize(width, height);
        });

        try (MemoryStack stack = stackPush()) {
            IntBuffer width = stack.mallocInt(1);
            IntBuffer height = stack.mallocInt(1);
            glfwGetWindowSize(window, width, height);
            viewport.update(width.get(0), height.get(0));
            GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
            if (vidMode != null) {
                glfwSetWindowPos(
                        window,
                        (vidMode.width() - width.get(0)) / 2,
                        (vidMode.height() - height.get(0)) / 2
                );
            }
        }

        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);
        glfwShowWindow(window);
    }

    private static DisplayModes.Mode resolveSavedResolution() {
        try {
            var modes = DisplayModes.queryAll();
            return DisplayModes.findByStorageKey(
                    modes,
                    com.scndgen.legends.state.State.get().getLogin().getGraphicsSetting()
            );
        } catch (Exception ex) {
            return DisplayModes.defaultWindow();
        }
    }

    private void initGlAndNanoVg() {
        GL.createCapabilities();
        vg = nvgCreate(NVG_ANTIALIAS | NVG_STENCIL_STROKES);
        if (vg == NULL) {
            throw new IllegalStateException("Could not create NanoVG GL3 context");
        }
        draw = new DrawContext(vg);
        loader = new AssetLoader(vg);
    }

    public void retainFont(String name, String classpathResource) {
        retainedFontData.add(loader.loadFont(name, classpathResource));
    }

    /**
     * Reload letterbox RGB from saved login state (0–255 → 0–1 clear color).
     */
    public void refreshLetterboxColor() {
        try {
            var login = com.scndgen.legends.state.State.get().getLogin();
            letterboxRf = login.getLetterboxR() / 255f;
            letterboxGf = login.getLetterboxG() / 255f;
            letterboxBf = login.getLetterboxB() / 255f;
        } catch (Exception ex) {
            letterboxRf = 0f;
            letterboxGf = 0f;
            letterboxBf = 0f;
        }
    }

    /**
     * Resize the GLFW window. Design 16:9 is letterboxed/pillarboxed into the window.
     */
    public void applyResolution(int width, int height) {
        if (window == NULL || width < 1 || height < 1) {
            return;
        }
        glfwSetWindowSize(window, width, height);
        try (MemoryStack stack = stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            glfwGetWindowSize(window, w, h);
            viewport.update(w.get(0), h.get(0));
            GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
            if (vidMode != null) {
                glfwSetWindowPos(
                        window,
                        Math.max(0, (vidMode.width() - w.get(0)) / 2),
                        Math.max(0, (vidMode.height() - h.get(0)) / 2)
                );
            }
        }
    }

    private void loop() {
        running = true;
        double lastTime = glfwGetTime();
        while (running && !glfwWindowShouldClose(window)) {
            double now = glfwGetTime();
            double delta = now - lastTime;
            lastTime = now;

            nuklearUi.beginInput();
            glfwPollEvents();
            try (MemoryStack stack = stackPush()) {
                IntBuffer windowWidth = stack.mallocInt(1);
                IntBuffer windowHeight = stack.mallocInt(1);
                IntBuffer framebufferWidth = stack.mallocInt(1);
                IntBuffer framebufferHeight = stack.mallocInt(1);
                glfwGetWindowSize(window, windowWidth, windowHeight);
                glfwGetFramebufferSize(window, framebufferWidth, framebufferHeight);
                viewport.update(windowWidth.get(0), windowHeight.get(0));
                nuklearUi.backend().newFrame(
                        Math.max(1, windowWidth.get(0)),
                        Math.max(1, windowHeight.get(0)),
                        Math.max(1, framebufferWidth.get(0)),
                        Math.max(1, framebufferHeight.get(0))
                );
            }
            nuklearUi.endInput();

            try (MemoryStack stack = stackPush()) {
                IntBuffer framebufferWidth = stack.mallocInt(1);
                IntBuffer framebufferHeight = stack.mallocInt(1);
                IntBuffer windowWidth = stack.mallocInt(1);
                IntBuffer windowHeight = stack.mallocInt(1);
                glfwGetFramebufferSize(window, framebufferWidth, framebufferHeight);
                glfwGetWindowSize(window, windowWidth, windowHeight);

                int fbW = framebufferWidth.get(0);
                int fbH = framebufferHeight.get(0);
                int winW = Math.max(1, windowWidth.get(0));
                int winH = Math.max(1, windowHeight.get(0));
                viewport.update(winW, winH);
                float pixelRatio = (float) fbW / (float) winW;

                if (resizePending) {
                    resizePending = false;
                }

                glViewport(0, 0, fbW, fbH);
                glClearColor(letterboxRf, letterboxGf, letterboxBf, 1f);
                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);

                if (!nuklearUi.isBlocking()) {
                    scene.update(delta);
                }
                AudioEngine audio = AudioEngine.get();
                if (audio != null) {
                    audio.update(delta);
                }

                draw.beginFrame(stack, winW, winH, pixelRatio);
                viewport.begin(draw.vg());
                scene.render(stack, draw);
                viewport.end(draw.vg());
                draw.endFrame();

                nuklearUi.layoutOverlays(stack, winW, winH);
                nuklearUi.backend().render();
            }

            glfwSwapBuffers(window);
        }
    }

    public void requestClose() {
        if (window != NULL) {
            glfwSetWindowShouldClose(window, true);
        }
        running = false;
    }

    @Override
    public void close() {
        running = false;
        AudioEngine.shutdown();
        nuklearUi.close();
        if (vg != NULL) {
            nvgDelete(vg);
            vg = NULL;
        }
        for (ByteBuffer fontData : retainedFontData) {
            memFree(fontData);
        }
        retainedFontData.clear();
        if (window != NULL) {
            glfwFreeCallbacks(window);
            glfwDestroyWindow(window);
            window = NULL;
        }
        glfwTerminate();
        GLFWErrorCallback callback = glfwSetErrorCallback(null);
        if (callback != null) {
            callback.free();
        }
    }
}

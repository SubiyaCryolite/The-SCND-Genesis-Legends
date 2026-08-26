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
 * Frame loop uses {@link MemoryStack} for short-lived native allocations.
 */
public final class GlfwEngine implements AutoCloseable {
    public interface Scene {
        void init(GlfwEngine engine, AssetLoader loader, DrawContext draw);

        void update(double deltaSeconds);

        void render(MemoryStack stack, DrawContext draw, int width, int height);

        default void onKey(int key, int action, int mods) {
        }

        default void onMouseMove(double x, double y) {
        }

        default void onMouseButton(int button, int action, double x, double y) {
        }
    }

    private final int logicalWidth;
    private final int logicalHeight;
    private final String title;
    private final Scene scene;
    private final List<ByteBuffer> retainedFontData = new ArrayList<>();
    private final NuklearUi nuklearUi = new NuklearUi();

    private long window;
    private long vg;
    private DrawContext draw;
    private AssetLoader loader;
    private boolean running;

    public GlfwEngine(int logicalWidth, int logicalHeight, String title, Scene scene) {
        this.logicalWidth = logicalWidth;
        this.logicalHeight = logicalHeight;
        this.title = Objects.requireNonNull(title);
        this.scene = Objects.requireNonNull(scene);
    }

    public NuklearUi ui() {
        return nuklearUi;
    }

    public void run() {
        initWindow();
        initGlAndNanoVg();
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

        window = glfwCreateWindow(logicalWidth, logicalHeight, title, NULL, NULL);
        if (window == NULL) {
            throw new IllegalStateException("Failed to create GLFW window (OpenGL 3.3 core)");
        }

        glfwSetKeyCallback(window, (win, key, scancode, action, mods) -> {
            nuklearUi.onKey(key, action);
            if (nuklearUi.isBlocking()) {
                return;
            }
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
                glfwSetWindowShouldClose(win, true);
                return;
            }
            scene.onKey(key, action, mods);
        });
        glfwSetCharCallback(window, (win, codepoint) -> nuklearUi.onChar(codepoint));
        glfwSetScrollCallback(window, (win, x, y) -> nuklearUi.onScroll(x, y));
        glfwSetCursorPosCallback(window, (win, x, y) -> {
            nuklearUi.onCursorPos(x, y);
            if (!nuklearUi.isBlocking()) {
                scene.onMouseMove(x, y);
            }
        });
        glfwSetMouseButtonCallback(window, (win, button, action, mods) -> {
            try (MemoryStack stack = stackPush()) {
                DoubleBuffer xpos = stack.mallocDouble(1);
                DoubleBuffer ypos = stack.mallocDouble(1);
                glfwGetCursorPos(win, xpos, ypos);
                nuklearUi.onMouseButton(button, action, xpos.get(0), ypos.get(0));
                if (!nuklearUi.isBlocking()) {
                    scene.onMouseButton(button, action, xpos.get(0), ypos.get(0));
                }
            }
        });

        try (MemoryStack stack = stackPush()) {
            IntBuffer width = stack.mallocInt(1);
            IntBuffer height = stack.mallocInt(1);
            glfwGetWindowSize(window, width, height);
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
                // Mouse-grab handling must happen before nk_input_end.
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
                float pixelRatio = (float) fbW / (float) winW;

                glViewport(0, 0, fbW, fbH);
                glClearColor(0f, 0f, 0f, 1f);
                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);

                if (!nuklearUi.isBlocking()) {
                    scene.update(delta);
                }
                draw.beginFrame(stack, winW, winH, pixelRatio);
                scene.render(stack, draw, winW, winH);
                draw.endFrame();

                // Sizes already refreshed in newFrame; layout overlays then draw Nuklear.
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

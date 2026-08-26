package io.github.subiyacryolite.enginev2.nuklear;

import org.lwjgl.nuklear.NkAllocator;
import org.lwjgl.nuklear.NkBuffer;
import org.lwjgl.nuklear.NkContext;
import org.lwjgl.nuklear.NkConvertConfig;
import org.lwjgl.nuklear.NkDrawCommand;
import org.lwjgl.nuklear.NkDrawNullTexture;
import org.lwjgl.nuklear.NkDrawVertexLayoutElement;
import org.lwjgl.nuklear.NkMouse;
import org.lwjgl.nuklear.NkUserFont;
import org.lwjgl.nuklear.NkUserFontGlyph;
import org.lwjgl.nuklear.NkVec2;
import org.lwjgl.stb.STBTTAlignedQuad;
import org.lwjgl.stb.STBTTFontinfo;
import org.lwjgl.stb.STBTTPackContext;
import org.lwjgl.stb.STBTTPackedchar;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.Platform;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Objects;

import static org.lwjgl.glfw.GLFW.GLFW_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_HIDDEN;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_NORMAL;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_BACKSPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_B;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_C;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_DELETE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_E;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_END;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ENTER;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_HOME;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_CONTROL;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_SHIFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_P;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_PAGE_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_PAGE_UP;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_R;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT_CONTROL;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT_SHIFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_TAB;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_X;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_Z;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_MIDDLE;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.glfwGetKey;
import static org.lwjgl.glfw.GLFW.glfwSetClipboardString;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPos;
import static org.lwjgl.glfw.GLFW.glfwSetInputMode;
import static org.lwjgl.glfw.GLFW.nglfwGetClipboardString;
import static org.lwjgl.nuklear.Nuklear.NK_ANTI_ALIASING_ON;
import static org.lwjgl.nuklear.Nuklear.NK_BUTTON_LEFT;
import static org.lwjgl.nuklear.Nuklear.NK_BUTTON_MIDDLE;
import static org.lwjgl.nuklear.Nuklear.NK_BUTTON_RIGHT;
import static org.lwjgl.nuklear.Nuklear.NK_FORMAT_COUNT;
import static org.lwjgl.nuklear.Nuklear.NK_FORMAT_FLOAT;
import static org.lwjgl.nuklear.Nuklear.NK_FORMAT_R8G8B8A8;
import static org.lwjgl.nuklear.Nuklear.NK_KEY_BACKSPACE;
import static org.lwjgl.nuklear.Nuklear.NK_KEY_COPY;
import static org.lwjgl.nuklear.Nuklear.NK_KEY_CUT;
import static org.lwjgl.nuklear.Nuklear.NK_KEY_DEL;
import static org.lwjgl.nuklear.Nuklear.NK_KEY_DOWN;
import static org.lwjgl.nuklear.Nuklear.NK_KEY_ENTER;
import static org.lwjgl.nuklear.Nuklear.NK_KEY_LEFT;
import static org.lwjgl.nuklear.Nuklear.NK_KEY_PASTE;
import static org.lwjgl.nuklear.Nuklear.NK_KEY_RIGHT;
import static org.lwjgl.nuklear.Nuklear.NK_KEY_SCROLL_DOWN;
import static org.lwjgl.nuklear.Nuklear.NK_KEY_SCROLL_END;
import static org.lwjgl.nuklear.Nuklear.NK_KEY_SCROLL_START;
import static org.lwjgl.nuklear.Nuklear.NK_KEY_SCROLL_UP;
import static org.lwjgl.nuklear.Nuklear.NK_KEY_SHIFT;
import static org.lwjgl.nuklear.Nuklear.NK_KEY_TAB;
import static org.lwjgl.nuklear.Nuklear.NK_KEY_TEXT_END;
import static org.lwjgl.nuklear.Nuklear.NK_KEY_TEXT_LINE_END;
import static org.lwjgl.nuklear.Nuklear.NK_KEY_TEXT_LINE_START;
import static org.lwjgl.nuklear.Nuklear.NK_KEY_TEXT_REDO;
import static org.lwjgl.nuklear.Nuklear.NK_KEY_TEXT_START;
import static org.lwjgl.nuklear.Nuklear.NK_KEY_TEXT_UNDO;
import static org.lwjgl.nuklear.Nuklear.NK_KEY_TEXT_WORD_LEFT;
import static org.lwjgl.nuklear.Nuklear.NK_KEY_TEXT_WORD_RIGHT;
import static org.lwjgl.nuklear.Nuklear.NK_KEY_UP;
import static org.lwjgl.nuklear.Nuklear.NK_UTF_INVALID;
import static org.lwjgl.nuklear.Nuklear.NK_VERTEX_ATTRIBUTE_COUNT;
import static org.lwjgl.nuklear.Nuklear.NK_VERTEX_COLOR;
import static org.lwjgl.nuklear.Nuklear.NK_VERTEX_POSITION;
import static org.lwjgl.nuklear.Nuklear.NK_VERTEX_TEXCOORD;
import static org.lwjgl.nuklear.Nuklear.nk__draw_begin;
import static org.lwjgl.nuklear.Nuklear.nk__draw_next;
import static org.lwjgl.nuklear.Nuklear.nk_buffer_clear;
import static org.lwjgl.nuklear.Nuklear.nk_buffer_free;
import static org.lwjgl.nuklear.Nuklear.nk_buffer_init;
import static org.lwjgl.nuklear.Nuklear.nk_buffer_init_fixed;
import static org.lwjgl.nuklear.Nuklear.nk_clear;
import static org.lwjgl.nuklear.Nuklear.nk_convert;
import static org.lwjgl.nuklear.Nuklear.nk_free;
import static org.lwjgl.nuklear.Nuklear.nk_init;
import static org.lwjgl.nuklear.Nuklear.nk_input_begin;
import static org.lwjgl.nuklear.Nuklear.nk_input_button;
import static org.lwjgl.nuklear.Nuklear.nk_input_end;
import static org.lwjgl.nuklear.Nuklear.nk_input_key;
import static org.lwjgl.nuklear.Nuklear.nk_input_motion;
import static org.lwjgl.nuklear.Nuklear.nk_input_scroll;
import static org.lwjgl.nuklear.Nuklear.nk_input_unicode;
import static org.lwjgl.nuklear.Nuklear.nk_item_is_any_active;
import static org.lwjgl.nuklear.Nuklear.nk_style_set_font;
import static org.lwjgl.nuklear.Nuklear.nnk_strlen;
import static org.lwjgl.nuklear.Nuklear.nnk_textedit_paste;
import static org.lwjgl.nuklear.Nuklear.nnk_utf_decode;
import static org.lwjgl.opengl.GL30C.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL30C.GL_BLEND;
import static org.lwjgl.opengl.GL30C.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL30C.GL_CULL_FACE;
import static org.lwjgl.opengl.GL30C.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL30C.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL30C.GL_FLOAT;
import static org.lwjgl.opengl.GL30C.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL30C.GL_FUNC_ADD;
import static org.lwjgl.opengl.GL30C.GL_LINEAR;
import static org.lwjgl.opengl.GL30C.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL30C.GL_NEAREST;
import static org.lwjgl.opengl.GL30C.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL30C.GL_RGBA;
import static org.lwjgl.opengl.GL30C.GL_RGBA8;
import static org.lwjgl.opengl.GL30C.GL_SCISSOR_TEST;
import static org.lwjgl.opengl.GL30C.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL30C.GL_STREAM_DRAW;
import static org.lwjgl.opengl.GL30C.GL_TEXTURE0;
import static org.lwjgl.opengl.GL30C.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL30C.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL30C.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL30C.GL_TRIANGLES;
import static org.lwjgl.opengl.GL30C.GL_TRUE;
import static org.lwjgl.opengl.GL30C.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL30C.GL_UNSIGNED_INT_8_8_8_8_REV;
import static org.lwjgl.opengl.GL30C.GL_UNSIGNED_SHORT;
import static org.lwjgl.opengl.GL30C.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL30C.GL_WRITE_ONLY;
import static org.lwjgl.opengl.GL30C.glActiveTexture;
import static org.lwjgl.opengl.GL30C.glAttachShader;
import static org.lwjgl.opengl.GL30C.glBindBuffer;
import static org.lwjgl.opengl.GL30C.glBindTexture;
import static org.lwjgl.opengl.GL30C.glBindVertexArray;
import static org.lwjgl.opengl.GL30C.glBlendEquation;
import static org.lwjgl.opengl.GL30C.glBlendFunc;
import static org.lwjgl.opengl.GL30C.glBufferData;
import static org.lwjgl.opengl.GL30C.glCompileShader;
import static org.lwjgl.opengl.GL30C.glCreateProgram;
import static org.lwjgl.opengl.GL30C.glCreateShader;
import static org.lwjgl.opengl.GL30C.glDeleteBuffers;
import static org.lwjgl.opengl.GL30C.glDeleteProgram;
import static org.lwjgl.opengl.GL30C.glDeleteShader;
import static org.lwjgl.opengl.GL30C.glDeleteTextures;
import static org.lwjgl.opengl.GL30C.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30C.glDetachShader;
import static org.lwjgl.opengl.GL30C.glDisable;
import static org.lwjgl.opengl.GL30C.glDrawElements;
import static org.lwjgl.opengl.GL30C.glEnable;
import static org.lwjgl.opengl.GL30C.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30C.glGenBuffers;
import static org.lwjgl.opengl.GL30C.glGenTextures;
import static org.lwjgl.opengl.GL30C.glGenVertexArrays;
import static org.lwjgl.opengl.GL30C.glGetAttribLocation;
import static org.lwjgl.opengl.GL30C.glGetProgrami;
import static org.lwjgl.opengl.GL30C.glGetShaderi;
import static org.lwjgl.opengl.GL30C.glGetUniformLocation;
import static org.lwjgl.opengl.GL30C.glLinkProgram;
import static org.lwjgl.opengl.GL30C.glMapBuffer;
import static org.lwjgl.opengl.GL30C.glScissor;
import static org.lwjgl.opengl.GL30C.glShaderSource;
import static org.lwjgl.opengl.GL30C.glTexImage2D;
import static org.lwjgl.opengl.GL30C.glTexParameteri;
import static org.lwjgl.opengl.GL30C.glUniform1i;
import static org.lwjgl.opengl.GL30C.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL30C.glUnmapBuffer;
import static org.lwjgl.opengl.GL30C.glUseProgram;
import static org.lwjgl.opengl.GL30C.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30C.glViewport;
import static org.lwjgl.stb.STBTruetype.stbtt_GetCodepointHMetrics;
import static org.lwjgl.stb.STBTruetype.stbtt_GetFontVMetrics;
import static org.lwjgl.stb.STBTruetype.stbtt_GetPackedQuad;
import static org.lwjgl.stb.STBTruetype.stbtt_InitFont;
import static org.lwjgl.stb.STBTruetype.stbtt_PackBegin;
import static org.lwjgl.stb.STBTruetype.stbtt_PackEnd;
import static org.lwjgl.stb.STBTruetype.stbtt_PackFontRange;
import static org.lwjgl.stb.STBTruetype.stbtt_PackSetOversampling;
import static org.lwjgl.stb.STBTruetype.stbtt_ScaleForPixelHeight;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.system.MemoryUtil.memAddress;
import static org.lwjgl.system.MemoryUtil.memAlloc;
import static org.lwjgl.system.MemoryUtil.memCopy;
import static org.lwjgl.system.MemoryUtil.memFree;
import static org.lwjgl.system.MemoryUtil.nmemAllocChecked;
import static org.lwjgl.system.MemoryUtil.nmemFree;

/**
 * Reusable LWJGL Nuklear OpenGL 3.3 renderer.
 * Adapted from the LWJGL Nuklear GLFWDemo (OpenGL 3.3).
 */
public final class NuklearBackend implements AutoCloseable {

    private static final int BUFFER_INITIAL_SIZE = 4 * 1024;
    private static final int MAX_VERTEX_BUFFER = 512 * 1024;
    private static final int MAX_ELEMENT_BUFFER = 128 * 1024;
    private static final int FONT_HEIGHT = 18;
    private static final int BITMAP_W = 1024;
    private static final int BITMAP_H = 1024;
    /** Packed range: Latin-1 printable (32..255). Out-of-range glyphs fall back to '?'. */
    private static final int FONT_FIRST_CODEPOINT = 32;
    private static final int FONT_CHAR_COUNT = 224;

    private static final NkAllocator ALLOCATOR = NkAllocator.create()
            .alloc((handle, old, size) -> nmemAllocChecked(size))
            .mfree((handle, ptr) -> nmemFree(ptr));

    private static final NkDrawVertexLayoutElement.Buffer VERTEX_LAYOUT = NkDrawVertexLayoutElement.create(4)
            .position(0).attribute(NK_VERTEX_POSITION).format(NK_FORMAT_FLOAT).offset(0)
            .position(1).attribute(NK_VERTEX_TEXCOORD).format(NK_FORMAT_FLOAT).offset(8)
            .position(2).attribute(NK_VERTEX_COLOR).format(NK_FORMAT_R8G8B8A8).offset(16)
            .position(3).attribute(NK_VERTEX_ATTRIBUTE_COUNT).format(NK_FORMAT_COUNT).offset(0)
            .flip();

    private final long glfwWindow;
    private ByteBuffer ttf;

    private final NkContext ctx = NkContext.create();
    private final NkUserFont defaultFont = NkUserFont.create();
    private final NkBuffer cmds = NkBuffer.create();
    private final NkDrawNullTexture nullTexture = NkDrawNullTexture.create();

    private final STBTTFontinfo fontInfo = STBTTFontinfo.create();
    private final STBTTPackedchar.Buffer cdata = STBTTPackedchar.create(FONT_CHAR_COUNT);

    private int width;
    private int height;
    private int displayWidth;
    private int displayHeight;

    private int vbo;
    private int vao;
    private int ebo;
    private int prog;
    private int vertShdr;
    private int fragShdr;
    private int uniformTex;
    private int uniformProj;
    private int fontTexId;

    private float fontScale;
    private float fontDescent;
    private boolean uiActive;
    private boolean initialized;

    public NuklearBackend(long glfwWindow, String fontClasspathPath) {
        this.glfwWindow = glfwWindow;
        this.ttf = loadClasspathResource(Objects.requireNonNull(fontClasspathPath));
    }

    public void init() {
        if (initialized) {
            return;
        }

        nk_init(ctx, ALLOCATOR, null);
        ctx.clip()
                .copy((handle, text, len) -> {
                    if (len == 0) {
                        return;
                    }
                    try (MemoryStack stack = stackPush()) {
                        ByteBuffer str = stack.malloc(len + 1);
                        memCopy(text, memAddress(str), len);
                        str.put(len, (byte) 0);
                        glfwSetClipboardString(glfwWindow, str);
                    }
                })
                .paste((handle, edit) -> {
                    long text = nglfwGetClipboardString(glfwWindow);
                    if (text != NULL) {
                        nnk_textedit_paste(edit, text, nnk_strlen(text));
                    }
                });

        setupDevice();
        setupFont();
        initialized = true;
    }

    public void beginInput() {
        nk_input_begin(ctx);
    }

    public void endInput() {
        nk_input_end(ctx);
    }

    public void onScroll(double x, double y) {
        try (MemoryStack stack = stackPush()) {
            NkVec2 scroll = NkVec2.malloc(stack)
                    .x((float) x)
                    .y((float) y);
            nk_input_scroll(ctx, scroll);
        }
    }

    public void onChar(int codepoint) {
        nk_input_unicode(ctx, codepoint);
    }

    public void onKey(int key, int action) {
        boolean press = action != GLFW_RELEASE;
        switch (key) {
            case GLFW_KEY_DELETE -> nk_input_key(ctx, NK_KEY_DEL, press);
            case GLFW_KEY_ENTER -> nk_input_key(ctx, NK_KEY_ENTER, press);
            case GLFW_KEY_TAB -> nk_input_key(ctx, NK_KEY_TAB, press);
            case GLFW_KEY_BACKSPACE -> nk_input_key(ctx, NK_KEY_BACKSPACE, press);
            case GLFW_KEY_UP -> nk_input_key(ctx, NK_KEY_UP, press);
            case GLFW_KEY_DOWN -> nk_input_key(ctx, NK_KEY_DOWN, press);
            case GLFW_KEY_LEFT -> nk_input_key(ctx, NK_KEY_LEFT, press);
            case GLFW_KEY_RIGHT -> nk_input_key(ctx, NK_KEY_RIGHT, press);
            case GLFW_KEY_HOME -> {
                nk_input_key(ctx, NK_KEY_TEXT_START, press);
                nk_input_key(ctx, NK_KEY_SCROLL_START, press);
            }
            case GLFW_KEY_END -> {
                nk_input_key(ctx, NK_KEY_TEXT_END, press);
                nk_input_key(ctx, NK_KEY_SCROLL_END, press);
            }
            case GLFW_KEY_PAGE_DOWN -> nk_input_key(ctx, NK_KEY_SCROLL_DOWN, press);
            case GLFW_KEY_PAGE_UP -> nk_input_key(ctx, NK_KEY_SCROLL_UP, press);
            case GLFW_KEY_LEFT_SHIFT, GLFW_KEY_RIGHT_SHIFT -> nk_input_key(ctx, NK_KEY_SHIFT, press);
            case GLFW_KEY_LEFT_CONTROL, GLFW_KEY_RIGHT_CONTROL -> {
                if (press) {
                    nk_input_key(ctx, NK_KEY_COPY, glfwGetKey(glfwWindow, GLFW_KEY_C) == GLFW_PRESS);
                    nk_input_key(ctx, NK_KEY_PASTE, glfwGetKey(glfwWindow, GLFW_KEY_P) == GLFW_PRESS);
                    nk_input_key(ctx, NK_KEY_CUT, glfwGetKey(glfwWindow, GLFW_KEY_X) == GLFW_PRESS);
                    nk_input_key(ctx, NK_KEY_TEXT_UNDO, glfwGetKey(glfwWindow, GLFW_KEY_Z) == GLFW_PRESS);
                    nk_input_key(ctx, NK_KEY_TEXT_REDO, glfwGetKey(glfwWindow, GLFW_KEY_R) == GLFW_PRESS);
                    nk_input_key(ctx, NK_KEY_TEXT_WORD_LEFT, glfwGetKey(glfwWindow, GLFW_KEY_LEFT) == GLFW_PRESS);
                    nk_input_key(ctx, NK_KEY_TEXT_WORD_RIGHT, glfwGetKey(glfwWindow, GLFW_KEY_RIGHT) == GLFW_PRESS);
                    nk_input_key(ctx, NK_KEY_TEXT_LINE_START, glfwGetKey(glfwWindow, GLFW_KEY_B) == GLFW_PRESS);
                    nk_input_key(ctx, NK_KEY_TEXT_LINE_END, glfwGetKey(glfwWindow, GLFW_KEY_E) == GLFW_PRESS);
                } else {
                    nk_input_key(ctx, NK_KEY_LEFT, glfwGetKey(glfwWindow, GLFW_KEY_LEFT) == GLFW_PRESS);
                    nk_input_key(ctx, NK_KEY_RIGHT, glfwGetKey(glfwWindow, GLFW_KEY_RIGHT) == GLFW_PRESS);
                    nk_input_key(ctx, NK_KEY_COPY, false);
                    nk_input_key(ctx, NK_KEY_PASTE, false);
                    nk_input_key(ctx, NK_KEY_CUT, false);
                    nk_input_key(ctx, NK_KEY_SHIFT, false);
                }
            }
            default -> {
            }
        }
    }

    public void onCursorPos(double x, double y) {
        nk_input_motion(ctx, (int) x, (int) y);
    }

    public void onMouseButton(int button, int action, double x, double y) {
        int nkButton = switch (button) {
            case GLFW_MOUSE_BUTTON_RIGHT -> NK_BUTTON_RIGHT;
            case GLFW_MOUSE_BUTTON_MIDDLE -> NK_BUTTON_MIDDLE;
            default -> NK_BUTTON_LEFT;
        };
        nk_input_button(ctx, nkButton, (int) x, (int) y, action == GLFW_PRESS);
    }

    public void newFrame(int windowWidth, int windowHeight, int fbWidth, int fbHeight) {
        width = Math.max(1, windowWidth);
        height = Math.max(1, windowHeight);
        displayWidth = Math.max(1, fbWidth);
        displayHeight = Math.max(1, fbHeight);

        NkMouse mouse = ctx.input().mouse();
        if (mouse.grab()) {
            glfwSetInputMode(glfwWindow, GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
        } else if (mouse.grabbed()) {
            float prevX = mouse.prev().x();
            float prevY = mouse.prev().y();
            glfwSetCursorPos(glfwWindow, prevX, prevY);
            mouse.pos().x(prevX);
            mouse.pos().y(prevY);
        } else if (mouse.ungrab()) {
            glfwSetInputMode(glfwWindow, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
        }
    }

    public NkContext ctx() {
        return ctx;
    }

    public void setUiActive(boolean uiActive) {
        this.uiActive = uiActive;
    }

    public boolean wantsKeyboard() {
        return uiActive || nk_item_is_any_active(ctx);
    }

    public boolean wantsMouse() {
        return uiActive || nk_item_is_any_active(ctx);
    }

    public void render() {
        try (MemoryStack stack = stackPush()) {
            glEnable(GL_BLEND);
            glBlendEquation(GL_FUNC_ADD);
            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
            glDisable(GL_CULL_FACE);
            glDisable(GL_DEPTH_TEST);
            glEnable(GL_SCISSOR_TEST);
            glActiveTexture(GL_TEXTURE0);

            glUseProgram(prog);
            glUniform1i(uniformTex, 0);
            glUniformMatrix4fv(uniformProj, false, stack.floats(
                    2.0f / width, 0.0f, 0.0f, 0.0f,
                    0.0f, -2.0f / height, 0.0f, 0.0f,
                    0.0f, 0.0f, -1.0f, 0.0f,
                    -1.0f, 1.0f, 0.0f, 1.0f
            ));
            glViewport(0, 0, displayWidth, displayHeight);
        }

        glBindVertexArray(vao);
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);

        glBufferData(GL_ARRAY_BUFFER, MAX_VERTEX_BUFFER, GL_STREAM_DRAW);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, MAX_ELEMENT_BUFFER, GL_STREAM_DRAW);

        ByteBuffer vertices = Objects.requireNonNull(glMapBuffer(GL_ARRAY_BUFFER, GL_WRITE_ONLY, MAX_VERTEX_BUFFER, null));
        ByteBuffer elements = Objects.requireNonNull(glMapBuffer(GL_ELEMENT_ARRAY_BUFFER, GL_WRITE_ONLY, MAX_ELEMENT_BUFFER, null));
        try (MemoryStack stack = stackPush()) {
            NkConvertConfig config = NkConvertConfig.calloc(stack)
                    .vertex_layout(VERTEX_LAYOUT)
                    .vertex_size(20)
                    .vertex_alignment(4)
                    .tex_null(nullTexture)
                    .circle_segment_count(22)
                    .curve_segment_count(22)
                    .arc_segment_count(22)
                    .global_alpha(1.0f)
                    .shape_AA(NK_ANTI_ALIASING_ON)
                    .line_AA(NK_ANTI_ALIASING_ON);

            NkBuffer vbuf = NkBuffer.malloc(stack);
            NkBuffer ebuf = NkBuffer.malloc(stack);

            nk_buffer_init_fixed(vbuf, vertices);
            nk_buffer_init_fixed(ebuf, elements);
            nk_convert(ctx, cmds, vbuf, ebuf, config);
        }
        glUnmapBuffer(GL_ELEMENT_ARRAY_BUFFER);
        glUnmapBuffer(GL_ARRAY_BUFFER);

        float fbScaleX = (float) displayWidth / (float) width;
        float fbScaleY = (float) displayHeight / (float) height;

        long offset = NULL;
        for (NkDrawCommand cmd = nk__draw_begin(ctx, cmds); cmd != null; cmd = nk__draw_next(cmd, cmds, ctx)) {
            if (cmd.elem_count() == 0) {
                continue;
            }
            glBindTexture(GL_TEXTURE_2D, cmd.texture().id());
            glScissor(
                    (int) (cmd.clip_rect().x() * fbScaleX),
                    (int) ((height - (int) (cmd.clip_rect().y() + cmd.clip_rect().h())) * fbScaleY),
                    (int) (cmd.clip_rect().w() * fbScaleX),
                    (int) (cmd.clip_rect().h() * fbScaleY)
            );
            glDrawElements(GL_TRIANGLES, cmd.elem_count(), GL_UNSIGNED_SHORT, offset);
            offset += cmd.elem_count() * 2L;
        }
        nk_clear(ctx);
        nk_buffer_clear(cmds);

        glUseProgram(0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
        glBindTexture(GL_TEXTURE_2D, 0);
        glDisable(GL_BLEND);
        glDisable(GL_SCISSOR_TEST);
    }

    public void shutdown() {
        if (initialized) {
            Objects.requireNonNull(ctx.clip().copy()).free();
            Objects.requireNonNull(ctx.clip().paste()).free();
            nk_free(ctx);

            glDetachShader(prog, vertShdr);
            glDetachShader(prog, fragShdr);
            glDeleteShader(vertShdr);
            glDeleteShader(fragShdr);
            glDeleteProgram(prog);
            glDeleteTextures(fontTexId);
            glDeleteTextures(nullTexture.texture().id());
            glDeleteBuffers(vbo);
            glDeleteBuffers(ebo);
            glDeleteVertexArrays(vao);
            nk_buffer_free(cmds);

            Objects.requireNonNull(defaultFont.query()).free();
            Objects.requireNonNull(defaultFont.width()).free();
            initialized = false;
        }
        if (ttf != null) {
            memFree(ttf);
            ttf = null;
        }
    }

    @Override
    public void close() {
        shutdown();
    }

    private void setupDevice() {
        String nkShaderVersion = Platform.get() == Platform.MACOSX ? "#version 150\n" : "#version 300 es\n";
        String vertexShader =
                nkShaderVersion +
                        "uniform mat4 ProjMtx;\n" +
                        "in vec2 Position;\n" +
                        "in vec2 TexCoord;\n" +
                        "in vec4 Color;\n" +
                        "out vec2 Frag_UV;\n" +
                        "out vec4 Frag_Color;\n" +
                        "void main() {\n" +
                        "   Frag_UV = TexCoord;\n" +
                        "   Frag_Color = Color;\n" +
                        "   gl_Position = ProjMtx * vec4(Position.xy, 0, 1);\n" +
                        "}\n";
        String fragmentShader =
                nkShaderVersion +
                        "precision mediump float;\n" +
                        "uniform sampler2D Texture;\n" +
                        "in vec2 Frag_UV;\n" +
                        "in vec4 Frag_Color;\n" +
                        "out vec4 Out_Color;\n" +
                        "void main(){\n" +
                        "   Out_Color = Frag_Color * texture(Texture, Frag_UV.st);\n" +
                        "}\n";

        nk_buffer_init(cmds, ALLOCATOR, BUFFER_INITIAL_SIZE);
        prog = glCreateProgram();
        vertShdr = glCreateShader(GL_VERTEX_SHADER);
        fragShdr = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(vertShdr, vertexShader);
        glShaderSource(fragShdr, fragmentShader);
        glCompileShader(vertShdr);
        glCompileShader(fragShdr);
        if (glGetShaderi(vertShdr, GL_COMPILE_STATUS) != GL_TRUE) {
            throw new IllegalStateException("Nuklear vertex shader compile failed");
        }
        if (glGetShaderi(fragShdr, GL_COMPILE_STATUS) != GL_TRUE) {
            throw new IllegalStateException("Nuklear fragment shader compile failed");
        }
        glAttachShader(prog, vertShdr);
        glAttachShader(prog, fragShdr);
        glLinkProgram(prog);
        if (glGetProgrami(prog, GL_LINK_STATUS) != GL_TRUE) {
            throw new IllegalStateException("Nuklear shader program link failed");
        }

        uniformTex = glGetUniformLocation(prog, "Texture");
        uniformProj = glGetUniformLocation(prog, "ProjMtx");
        int attribPos = glGetAttribLocation(prog, "Position");
        int attribUv = glGetAttribLocation(prog, "TexCoord");
        int attribCol = glGetAttribLocation(prog, "Color");

        vbo = glGenBuffers();
        ebo = glGenBuffers();
        vao = glGenVertexArrays();

        glBindVertexArray(vao);
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);

        glEnableVertexAttribArray(attribPos);
        glEnableVertexAttribArray(attribUv);
        glEnableVertexAttribArray(attribCol);

        glVertexAttribPointer(attribPos, 2, GL_FLOAT, false, 20, 0);
        glVertexAttribPointer(attribUv, 2, GL_FLOAT, false, 20, 8);
        glVertexAttribPointer(attribCol, 4, GL_UNSIGNED_BYTE, true, 20, 16);

        int nullTexId = glGenTextures();
        nullTexture.texture().id(nullTexId);
        nullTexture.uv().set(0.5f, 0.5f);

        glBindTexture(GL_TEXTURE_2D, nullTexId);
        try (MemoryStack stack = stackPush()) {
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, 1, 1, 0, GL_RGBA, GL_UNSIGNED_INT_8_8_8_8_REV, stack.ints(0xFFFFFFFF));
        }
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);

        glBindTexture(GL_TEXTURE_2D, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }

    private void setupFont() {
        fontTexId = glGenTextures();

        try (MemoryStack stack = stackPush()) {
            stbtt_InitFont(fontInfo, ttf);
            fontScale = stbtt_ScaleForPixelHeight(fontInfo, FONT_HEIGHT);

            IntBuffer d = stack.mallocInt(1);
            stbtt_GetFontVMetrics(fontInfo, null, d, null);
            fontDescent = d.get(0) * fontScale;

            ByteBuffer bitmap = memAlloc(BITMAP_W * BITMAP_H);
            STBTTPackContext pc = STBTTPackContext.malloc(stack);
            stbtt_PackBegin(pc, bitmap, BITMAP_W, BITMAP_H, 0, 1, NULL);
            stbtt_PackSetOversampling(pc, 4, 4);
            stbtt_PackFontRange(pc, ttf, 0, FONT_HEIGHT, FONT_FIRST_CODEPOINT, cdata);
            stbtt_PackEnd(pc);

            ByteBuffer texture = memAlloc(BITMAP_W * BITMAP_H * 4);
            for (int i = 0; i < bitmap.capacity(); i++) {
                texture.putInt((bitmap.get(i) << 24) | 0x00FFFFFF);
            }
            texture.flip();

            glBindTexture(GL_TEXTURE_2D, fontTexId);
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, BITMAP_W, BITMAP_H, 0, GL_RGBA, GL_UNSIGNED_INT_8_8_8_8_REV, texture);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
            glBindTexture(GL_TEXTURE_2D, 0);

            memFree(texture);
            memFree(bitmap);
        }

        defaultFont
                .width((handle, h, text, len) -> {
                    float textWidth = 0;
                    try (MemoryStack stack = stackPush()) {
                        IntBuffer unicode = stack.mallocInt(1);
                        int glyphLen = nnk_utf_decode(text, memAddress(unicode), len);
                        int textLen = glyphLen;
                        if (glyphLen == 0) {
                            return 0;
                        }
                        IntBuffer advance = stack.mallocInt(1);
                        while (textLen <= len && glyphLen != 0) {
                            if (unicode.get(0) == NK_UTF_INVALID) {
                                break;
                            }
                            stbtt_GetCodepointHMetrics(fontInfo, unicode.get(0), advance, null);
                            textWidth += advance.get(0) * fontScale;
                            glyphLen = nnk_utf_decode(text + textLen, memAddress(unicode), len - textLen);
                            textLen += glyphLen;
                        }
                    }
                    return textWidth;
                })
                .height(FONT_HEIGHT)
                .query((handle, fontHeight, glyph, codepoint, nextCodepoint) -> {
                    try (MemoryStack stack = stackPush()) {
                        FloatBuffer x = stack.floats(0.0f);
                        FloatBuffer y = stack.floats(0.0f);
                        STBTTAlignedQuad q = STBTTAlignedQuad.malloc(stack);
                        IntBuffer advance = stack.mallocInt(1);

                        int glyphCodepoint = codepoint;
                        if (glyphCodepoint < FONT_FIRST_CODEPOINT
                                || glyphCodepoint >= FONT_FIRST_CODEPOINT + FONT_CHAR_COUNT) {
                            glyphCodepoint = '?';
                        }
                        stbtt_GetPackedQuad(
                                cdata,
                                BITMAP_W,
                                BITMAP_H,
                                glyphCodepoint - FONT_FIRST_CODEPOINT,
                                x,
                                y,
                                q,
                                false
                        );
                        stbtt_GetCodepointHMetrics(fontInfo, codepoint, advance, null);

                        NkUserFontGlyph ufg = NkUserFontGlyph.create(glyph);
                        ufg.width(q.x1() - q.x0());
                        ufg.height(q.y1() - q.y0());
                        ufg.offset().set(q.x0(), q.y0() + (FONT_HEIGHT + fontDescent));
                        ufg.xadvance(advance.get(0) * fontScale);
                        ufg.uv(0).set(q.s0(), q.t0());
                        ufg.uv(1).set(q.s1(), q.t1());
                    }
                })
                .texture(it -> it.id(fontTexId));

        nk_style_set_font(ctx, defaultFont);
    }

    private static ByteBuffer loadClasspathResource(String classpathResource) {
        String normalized = classpathResource.startsWith("/")
                ? classpathResource.substring(1)
                : classpathResource;
        try (InputStream inputStream = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(normalized)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("Missing classpath resource: " + classpathResource);
            }
            byte[] bytes = inputStream.readAllBytes();
            ByteBuffer buffer = memAlloc(bytes.length);
            buffer.put(bytes).flip();
            return buffer;
        } catch (IOException ex) {
            throw new IllegalArgumentException("Unable to read resource: " + classpathResource, ex);
        }
    }
}

package io.github.subiyacryolite.enginev2;

import org.apache.commons.io.IOUtils;
import org.lwjgl.system.MemoryStack;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.nanovg.NanoVG.nvgCreateFontMem;
import static org.lwjgl.nanovg.NanoVG.nvgCreateImageMem;
import static org.lwjgl.nanovg.NanoVG.nvgImageSize;
import static org.lwjgl.system.MemoryUtil.memAlloc;
import static org.lwjgl.system.MemoryUtil.memFree;

/**
 * Loads classpath PNGs and fonts into a NanoVG context.
 */
public final class AssetLoader {
    private final long vg;

    public AssetLoader(long vg) {
        this.vg = vg;
    }

    public NvgImage loadImage(String classpathResource) {
        ByteBuffer data = readResource(classpathResource);
        try {
            int handle = nvgCreateImageMem(vg, 0, data);
            if (handle == 0) {
                throw new IllegalArgumentException("Unable to decode image: " + classpathResource);
            }
            try (MemoryStack stack = MemoryStack.stackPush()) {
                IntBuffer w = stack.mallocInt(1);
                IntBuffer h = stack.mallocInt(1);
                nvgImageSize(vg, handle, w, h);
                return new NvgImage(handle, w.get(0), h.get(0));
            }
        } finally {
            // NanoVG copies image pixels; free the source buffer.
            memFree(data);
        }
    }

    /**
     * Loads a TrueType font. The returned ByteBuffer must stay alive for the lifetime of the NanoVG context.
     */
    public ByteBuffer loadFont(String name, String classpathResource) {
        ByteBuffer data = readResource(classpathResource);
        int font = nvgCreateFontMem(vg, name, data, false);
        if (font == -1) {
            memFree(data);
            throw new IllegalArgumentException("Unable to load font: " + classpathResource);
        }
        return data;
    }

    private static ByteBuffer readResource(String classpathResource) {
        String normalized = classpathResource.startsWith("/")
                ? classpathResource.substring(1)
                : classpathResource;
        try (InputStream inputStream = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(normalized)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("Missing classpath resource: " + classpathResource);
            }
            byte[] bytes = IOUtils.toByteArray(inputStream);
            ByteBuffer buffer = memAlloc(bytes.length);
            buffer.put(bytes).flip();
            return buffer;
        } catch (IOException ex) {
            throw new IllegalArgumentException("Unable to read resource: " + classpathResource, ex);
        }
    }
}

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

import org.apache.commons.io.IOUtils;
import org.lwjgl.system.MemoryStack;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.lwjgl.nanovg.NanoVG.NVG_IMAGE_NEAREST;
import static org.lwjgl.nanovg.NanoVG.nvgCreateFontMem;
import static org.lwjgl.nanovg.NanoVG.nvgCreateImageMem;
import static org.lwjgl.nanovg.NanoVG.nvgDeleteImage;
import static org.lwjgl.nanovg.NanoVG.nvgImageSize;
import static org.lwjgl.system.MemoryUtil.memAlloc;
import static org.lwjgl.system.MemoryUtil.memFree;

/**
 * Loads classpath PNGs and fonts into a NanoVG context.
 * Prefer {@link #openBag()} so mode-owned images are freed together.
 */
public final class AssetLoader {
    private final long vg;

    public AssetLoader(long vg) {
        this.vg = vg;
    }

    public long vg() {
        return vg;
    }

    /**
     * Load an image that the caller owns and must {@link #free(NvgImage)} later.
     * Uses nearest-neighbor sampling so low-res sprites stay sharp when scaled.
     */
    public NvgImage loadImage(String classpathResource) {
        return loadImage(classpathResource, NVG_IMAGE_NEAREST);
    }

    /**
     * @param imageFlags NanoVG flags such as {@link org.lwjgl.nanovg.NanoVG#NVG_IMAGE_NEAREST}
     *                   or {@code 0} for default linear filtering.
     */
    public NvgImage loadImage(String classpathResource, int imageFlags) {
        ByteBuffer data = readResource(classpathResource);
        try {
            int handle = nvgCreateImageMem(vg, imageFlags, data);
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
            memFree(data);
        }
    }

    public void free(NvgImage image) {
        if (image == null || !image.isValid()) {
            return;
        }
        nvgDeleteImage(vg, image.handle());
    }

    public void free(NvgImage... images) {
        if (images == null) {
            return;
        }
        for (NvgImage image : images) {
            free(image);
        }
    }

    public void freeAll(Iterable<NvgImage> images) {
        if (images == null) {
            return;
        }
        for (NvgImage image : images) {
            free(image);
        }
    }

    /**
     * Scoped bag: images loaded through the bag are freed on {@link AssetBag#close()}.
     */
    public AssetBag openBag() {
        return new AssetBag(this);
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

    /**
     * Owns images loaded for one mode/session and frees them together.
     */
    public static final class AssetBag implements AutoCloseable {
        private final AssetLoader loader;
        private final List<NvgImage> owned = new ArrayList<>();
        private boolean closed;

        private AssetBag(AssetLoader loader) {
            this.loader = Objects.requireNonNull(loader);
        }

        public NvgImage loadImage(String classpathResource) {
            return loadImage(classpathResource, NVG_IMAGE_NEAREST);
        }

        public NvgImage loadImage(String classpathResource, int imageFlags) {
            ensureOpen();
            NvgImage image = loader.loadImage(classpathResource, imageFlags);
            owned.add(image);
            return image;
        }

        /** Track an already-created image so {@link #close()} frees it. */
        public NvgImage adopt(NvgImage image) {
            ensureOpen();
            if (image != null && image.isValid()) {
                owned.add(image);
            }
            return image;
        }

        public void close() {
            if (closed) {
                return;
            }
            closed = true;
            for (int i = owned.size() - 1; i >= 0; i--) {
                loader.free(owned.get(i));
            }
            owned.clear();
        }

        private void ensureOpen() {
            if (closed) {
                throw new IllegalStateException("AssetBag is closed");
            }
        }
    }
}

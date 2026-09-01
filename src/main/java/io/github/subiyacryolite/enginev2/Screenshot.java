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

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.imageio.ImageIO;

import static org.lwjgl.opengl.GL11C.GL_PACK_ALIGNMENT;
import static org.lwjgl.opengl.GL11C.GL_RGBA;
import static org.lwjgl.opengl.GL11C.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11C.glPixelStorei;
import static org.lwjgl.opengl.GL11C.glReadPixels;
import org.lwjgl.system.MemoryUtil;

/**
 * One-shot framebuffer PNG capture. Set {@link #captureScreenshot} from input;
 * consume it on the GLFW thread after the frame has been drawn and before swap.
 */
public final class Screenshot {

    private static final DateTimeFormatter FILE_STAMP = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
    private static final DateTimeFormatter FILE_STAMP_MS = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss-SSS");

    /**
     * Armed by F12 (or any caller). Cleared when the render loop captures pixels.
     */
    public static volatile boolean captureScreenshot = false;

    private Screenshot() {
    }

    /**
     * If {@link #captureScreenshot} is set, read the current framebuffer and write a PNG.
     * Must run on the thread that owns the GL context, after NanoVG and Nuklear have drawn.
     */
    public static void captureFramebufferIfRequested(int framebufferWidth, int framebufferHeight) {
        if (!captureScreenshot) {
            return;
        }
        captureScreenshot = false;
        int width = Math.max(1, framebufferWidth);
        int height = Math.max(1, framebufferHeight);
        int stride = width * 4;
        ByteBuffer pixels = MemoryUtil.memAlloc(stride * height);
        try {
            glPixelStorei(GL_PACK_ALIGNMENT, 1);
            glReadPixels(0, 0, width, height, GL_RGBA, GL_UNSIGNED_BYTE, pixels);
            flipVertically(pixels, height, stride);
            byte[] copy = new byte[stride * height];
            pixels.get(0, copy);
            var file = nextFile();
            Overlay.get().primaryNotice("Screenshot " + file.getFileName());
            Thread.ofVirtual().name("screenshot-png").start(() -> writePng(file, width, height, copy));
        } finally {
            MemoryUtil.memFree(pixels);
        }
    }

    static Path directory() {
        return Path.of(System.getProperty("user.home"), ".config", "scndgen", "legends", "screenshots");
    }

    static Path nextFile() {
        var dir = directory();
        var now = LocalDateTime.now();
        var file = dir.resolve("scndgen-legends-" + FILE_STAMP.format(now) + ".png");
        if (Files.exists(file)) {
            file = dir.resolve("scndgen-legends-" + FILE_STAMP_MS.format(now) + ".png");
        }
        return file;
    }

    private static void writePng(Path file, int width, int height, byte[] rgba) {
        try {
            Files.createDirectories(file.getParent());
            var image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            var argb = new int[width * height];
            for (int i = 0; i < argb.length; i++) {
                int o = i * 4;
                int r = rgba[o] & 0xff;
                int g = rgba[o + 1] & 0xff;
                int b = rgba[o + 2] & 0xff;
                int a = rgba[o + 3] & 0xff;
                argb[i] = (a << 24) | (r << 16) | (g << 8) | b;
            }
            image.setRGB(0, 0, width, height, argb, 0, width);
            if (!ImageIO.write(image, "png", file.toFile())) {
                System.err.println("Failed to write screenshot: " + file);
                return;
            }
            System.out.println("Saved screenshot " + file.toAbsolutePath());
        } catch (Exception ex) {
            System.err.println("Failed to write screenshot: " + file);
            ex.printStackTrace(System.err);
        }
    }

    private static void flipVertically(ByteBuffer pixels, int height, int stride) {
        byte[] row = new byte[stride];
        byte[] other = new byte[stride];
        for (int y = 0; y < height / 2; y++) {
            int top = y * stride;
            int bottom = (height - 1 - y) * stride;
            pixels.get(top, row);
            pixels.get(bottom, other);
            pixels.put(top, other);
            pixels.put(bottom, row);
        }
        pixels.rewind();
    }
}

package io.github.subiyacryolite.enginev2.nuklear;

import com.scndgen.legends.Language;
import org.apache.commons.io.IOUtils;
import org.lwjgl.nuklear.NkContext;
import org.lwjgl.nuklear.NkRect;
import org.lwjgl.system.MemoryStack;

import java.nio.charset.StandardCharsets;

import static org.lwjgl.nuklear.Nuklear.NK_TEXT_LEFT;
import static org.lwjgl.nuklear.Nuklear.NK_WINDOW_BORDER;
import static org.lwjgl.nuklear.Nuklear.NK_WINDOW_MOVABLE;
import static org.lwjgl.nuklear.Nuklear.NK_WINDOW_TITLE;
import static org.lwjgl.nuklear.Nuklear.nk_begin;
import static org.lwjgl.nuklear.Nuklear.nk_button_label;
import static org.lwjgl.nuklear.Nuklear.nk_end;
import static org.lwjgl.nuklear.Nuklear.nk_label;
import static org.lwjgl.nuklear.Nuklear.nk_layout_row_dynamic;
import static org.lwjgl.nuklear.Nuklear.nk_rect;
import static org.lwjgl.nuklear.Nuklear.nk_spacing;

/**
 * About / License / Changelog / Source overlay.
 */
public final class AboutOverlay implements UiOverlay {
    private final String[] tabs = {"About", "License", "Changelog", "Source"};
    private final String[] bodies = new String[4];
    private int tab;
    private boolean open = true;

    public AboutOverlay() {
        bodies[0] = read("text/txtAbout.txt");
        bodies[1] = read("text/txtLicense.txt");
        bodies[2] = read("text/txtChangelog.txt");
        bodies[3] = read("text/txtSourceCode.txt");
    }

    @Override
    public boolean layout(NkContext ctx, MemoryStack stack, int windowWidth, int windowHeight) {
        if (!open) {
            return false;
        }
        float w = 560;
        float h = 420;
        NkRect bounds = nk_rect((windowWidth - w) * 0.5f, (windowHeight - h) * 0.5f, w, h, NkRect.malloc(stack));
        if (nk_begin(ctx, Language.get().get(314), bounds, NK_WINDOW_BORDER | NK_WINDOW_TITLE | NK_WINDOW_MOVABLE)) {
            nk_layout_row_dynamic(ctx, 28, tabs.length);
            for (int i = 0; i < tabs.length; i++) {
                if (nk_button_label(ctx, tabs[i])) {
                    tab = i;
                }
            }
            nk_layout_row_dynamic(ctx, 8, 1);
            nk_spacing(ctx, 1);
            String body = bodies[tab] == null ? "" : bodies[tab];
            String[] lines = body.split("\\R", 24);
            for (String line : lines) {
                nk_layout_row_dynamic(ctx, 16, 1);
                nk_label(ctx, line.length() > 90 ? line.substring(0, 90) + "…" : line, NK_TEXT_LEFT);
            }
            nk_layout_row_dynamic(ctx, 32, 1);
            if (nk_button_label(ctx, Language.get().get(36))) {
                open = false;
            }
        }
        nk_end(ctx);
        return open;
    }

    private static String read(String resource) {
        try {
            var stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(resource);
            if (stream == null) {
                return "(missing " + resource + ")";
            }
            return IOUtils.toString(stream, StandardCharsets.UTF_8);
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }
}

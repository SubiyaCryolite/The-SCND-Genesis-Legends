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
package io.github.subiyacryolite.enginev2.nuklear;

import com.scndgen.legends.Language;
import org.lwjgl.nuklear.NkContext;
import org.lwjgl.nuklear.NkRect;
import org.lwjgl.system.MemoryStack;

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

/**
 * Controls help overlay.
 */
public final class ControlsOverlay implements UiOverlay {
    private boolean open = true;

    @Override
    public boolean layout(NkContext ctx, MemoryStack stack, int windowWidth, int windowHeight) {
        if (!open) {
            return false;
        }
        Language lang = Language.get();
        float w = 480;
        float h = 420;
        NkRect bounds = nk_rect((windowWidth - w) * 0.5f, (windowHeight - h) * 0.5f, w, h, NkRect.malloc(stack));
        if (nk_begin(ctx, lang.get(313), bounds, NK_WINDOW_BORDER | NK_WINDOW_TITLE | NK_WINDOW_MOVABLE)) {
            row(ctx, lang.get(37), "");
            row(ctx, lang.get(38), lang.get(40));
            row(ctx, lang.get(39), lang.get(41));
            row(ctx, lang.get(42), "F12");
            row(ctx, lang.get(43), "ESC");
            row(ctx, lang.get(44), "L");
            row(ctx, lang.get(45), "Left");
            row(ctx, lang.get(46), "Right");
            row(ctx, lang.get(47), "Up");
            row(ctx, lang.get(48), lang.get(49));
            row(ctx, lang.get(50), "Down / Enter");
            nk_layout_row_dynamic(ctx, 32, 1);
            if (nk_button_label(ctx, lang.get(36))) {
                open = false;
            }
        }
        nk_end(ctx);
        return open;
    }

    private static void row(NkContext ctx, String left, String right) {
        nk_layout_row_dynamic(ctx, 20, 2);
        nk_label(ctx, left == null ? "" : left, NK_TEXT_LEFT);
        nk_label(ctx, right == null ? "" : right, NK_TEXT_LEFT);
    }
}

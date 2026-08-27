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
package io.github.subiyacryolite.enginev2.nuklear;

import org.lwjgl.nuklear.NkContext;
import org.lwjgl.nuklear.NkRect;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.util.function.Consumer;

import static org.lwjgl.nuklear.Nuklear.NK_EDIT_FIELD;
import static org.lwjgl.nuklear.Nuklear.NK_TEXT_LEFT;
import static org.lwjgl.nuklear.Nuklear.NK_WINDOW_BORDER;
import static org.lwjgl.nuklear.Nuklear.NK_WINDOW_MOVABLE;
import static org.lwjgl.nuklear.Nuklear.NK_WINDOW_NO_SCROLLBAR;
import static org.lwjgl.nuklear.Nuklear.NK_WINDOW_TITLE;
import static org.lwjgl.nuklear.Nuklear.nk_begin;
import static org.lwjgl.nuklear.Nuklear.nk_button_label;
import static org.lwjgl.nuklear.Nuklear.nk_edit_string;
import static org.lwjgl.nuklear.Nuklear.nk_end;
import static org.lwjgl.nuklear.Nuklear.nk_layout_row_dynamic;
import static org.lwjgl.nuklear.Nuklear.nk_label;
import static org.lwjgl.nuklear.Nuklear.nk_rect;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.memASCII;

/**
 * Immediate-mode message / confirm / text-input dialogs.
 */
public final class NkDialogs {

    public enum Answer { YES, NO, CANCEL, NONE }

    private NkDialogs() {
    }

    public static UiOverlay message(String title, String header, String body) {
        return new MessageDialog(title, header, body);
    }

    public static UiOverlay yesNo(String title, String header, String body, Consumer<Answer> onAnswer) {
        return new ConfirmDialog(title, header, body, true, onAnswer);
    }

    public static UiOverlay input(String title, String header, String defaultValue, Consumer<String> onSubmit) {
        return new InputDialog(title, header, defaultValue, onSubmit);
    }

    private static final class MessageDialog implements UiOverlay {
        private final String title;
        private final String header;
        private final String body;
        private boolean open = true;

        MessageDialog(String title, String header, String body) {
            this.title = title;
            this.header = header;
            this.body = body;
        }

        @Override
        public boolean layout(NkContext ctx, MemoryStack stack, int windowWidth, int windowHeight) {
            if (!open) {
                return false;
            }
            float w = 360;
            float h = 180;
            NkRect bounds = nk_rect((windowWidth - w) * 0.5f, (windowHeight - h) * 0.5f, w, h, NkRect.malloc(stack));
            if (nk_begin(ctx, title, bounds, NK_WINDOW_BORDER | NK_WINDOW_TITLE | NK_WINDOW_NO_SCROLLBAR | NK_WINDOW_MOVABLE)) {
                nk_layout_row_dynamic(ctx, 24, 1);
                nk_label(ctx, header == null ? "" : header, NK_TEXT_LEFT);
                nk_layout_row_dynamic(ctx, 48, 1);
                nk_label(ctx, body == null ? "" : body, NK_TEXT_LEFT);
                nk_layout_row_dynamic(ctx, 30, 1);
                if (nk_button_label(ctx, "OK")) {
                    open = false;
                }
            }
            nk_end(ctx);
            return open;
        }
    }

    private static final class ConfirmDialog implements UiOverlay {
        private final String title;
        private final String header;
        private final String body;
        private final boolean showCancel;
        private final Consumer<Answer> onAnswer;
        private boolean open = true;
        private Answer answer = Answer.NONE;

        ConfirmDialog(String title, String header, String body, boolean showCancel, Consumer<Answer> onAnswer) {
            this.title = title;
            this.header = header;
            this.body = body;
            this.showCancel = showCancel;
            this.onAnswer = onAnswer;
        }

        @Override
        public boolean layout(NkContext ctx, MemoryStack stack, int windowWidth, int windowHeight) {
            if (!open) {
                return false;
            }
            float w = 380;
            float h = 170;
            NkRect bounds = nk_rect((windowWidth - w) * 0.5f, (windowHeight - h) * 0.5f, w, h, NkRect.malloc(stack));
            if (nk_begin(ctx, title, bounds, NK_WINDOW_BORDER | NK_WINDOW_TITLE | NK_WINDOW_NO_SCROLLBAR | NK_WINDOW_MOVABLE)) {
                nk_layout_row_dynamic(ctx, 24, 1);
                nk_label(ctx, header == null ? "" : header, NK_TEXT_LEFT);
                nk_layout_row_dynamic(ctx, 40, 1);
                nk_label(ctx, body == null ? "" : body, NK_TEXT_LEFT);
                nk_layout_row_dynamic(ctx, 30, showCancel ? 3 : 2);
                if (nk_button_label(ctx, "Yes")) {
                    answer = Answer.YES;
                    open = false;
                }
                if (nk_button_label(ctx, "No")) {
                    answer = Answer.NO;
                    open = false;
                }
                if (showCancel && nk_button_label(ctx, "Cancel")) {
                    answer = Answer.CANCEL;
                    open = false;
                }
            }
            nk_end(ctx);
            if (!open && onAnswer != null && answer != Answer.NONE) {
                onAnswer.accept(answer);
            }
            return open;
        }
    }

    private static final class InputDialog implements UiOverlay {
        private final String title;
        private final String header;
        private final Consumer<String> onSubmit;
        private final ByteBuffer textBuffer;
        private final int[] length = new int[]{0};
        private boolean open = true;

        InputDialog(String title, String header, String defaultValue, Consumer<String> onSubmit) {
            this.title = title;
            this.header = header;
            this.onSubmit = onSubmit;
            this.textBuffer = org.lwjgl.system.MemoryUtil.memAlloc(256);
            String seed = defaultValue == null ? "" : defaultValue;
            byte[] bytes = seed.getBytes(java.nio.charset.StandardCharsets.UTF_8);
            int n = Math.min(bytes.length, 255);
            textBuffer.clear();
            textBuffer.put(bytes, 0, n);
            for (int i = n; i < 256; i++) {
                textBuffer.put((byte) 0);
            }
            textBuffer.flip();
            length[0] = n;
        }

        @Override
        public boolean layout(NkContext ctx, MemoryStack stack, int windowWidth, int windowHeight) {
            if (!open) {
                return false;
            }
            float w = 400;
            float h = 170;
            NkRect bounds = nk_rect((windowWidth - w) * 0.5f, (windowHeight - h) * 0.5f, w, h, NkRect.malloc(stack));
            if (nk_begin(ctx, title, bounds, NK_WINDOW_BORDER | NK_WINDOW_TITLE | NK_WINDOW_NO_SCROLLBAR | NK_WINDOW_MOVABLE)) {
                nk_layout_row_dynamic(ctx, 24, 1);
                nk_label(ctx, header == null ? "" : header, NK_TEXT_LEFT);
                nk_layout_row_dynamic(ctx, 30, 1);
                nk_edit_string(ctx, NK_EDIT_FIELD, textBuffer, length, 255, null);
                nk_layout_row_dynamic(ctx, 30, 2);
                if (nk_button_label(ctx, "OK")) {
                    open = false;
                    if (onSubmit != null) {
                        onSubmit.accept(memASCII(textBuffer, length[0]));
                    }
                }
                if (nk_button_label(ctx, "Cancel")) {
                    open = false;
                }
            }
            nk_end(ctx);
            if (!open) {
                org.lwjgl.system.MemoryUtil.memFree(textBuffer);
            }
            return open;
        }
    }
}

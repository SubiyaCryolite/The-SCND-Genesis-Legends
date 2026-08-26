package io.github.subiyacryolite.enginev2.nuklear;

import org.lwjgl.nuklear.NkContext;
import org.lwjgl.system.MemoryStack;

public interface UiOverlay {
    /** @return true if still open */
    boolean layout(NkContext ctx, MemoryStack stack, int windowWidth, int windowHeight);
}

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

import com.scndgen.legends.Language;
import com.scndgen.legends.enums.AudioType;
import com.scndgen.legends.state.State;
import io.github.subiyacryolite.enginev2.Audio;
import io.github.subiyacryolite.enginev2.DisplayModes;
import io.github.subiyacryolite.enginev2.GlfwEngine;
import org.lwjgl.BufferUtils;
import org.lwjgl.nuklear.NkContext;
import org.lwjgl.nuklear.NkRect;
import org.lwjgl.nuklear.NkVec2;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;
import java.util.List;

import static com.scndgen.legends.constants.GeneralConstants.INFINITE_TIME;
import static org.lwjgl.nuklear.Nuklear.NK_TEXT_LEFT;
import static org.lwjgl.nuklear.Nuklear.NK_WINDOW_BORDER;
import static org.lwjgl.nuklear.Nuklear.NK_WINDOW_MOVABLE;
import static org.lwjgl.nuklear.Nuklear.NK_WINDOW_TITLE;
import static org.lwjgl.nuklear.Nuklear.nk_begin;
import static org.lwjgl.nuklear.Nuklear.nk_button_label;
import static org.lwjgl.nuklear.Nuklear.nk_combo_begin_label;
import static org.lwjgl.nuklear.Nuklear.nk_combo_end;
import static org.lwjgl.nuklear.Nuklear.nk_combo_item_label;
import static org.lwjgl.nuklear.Nuklear.nk_end;
import static org.lwjgl.nuklear.Nuklear.nk_label;
import static org.lwjgl.nuklear.Nuklear.nk_layout_row_dynamic;
import static org.lwjgl.nuklear.Nuklear.nk_property_int;
import static org.lwjgl.nuklear.Nuklear.nk_rect;
import static org.lwjgl.nuklear.Nuklear.nk_widget_width;
import static org.lwjgl.nuklear.Nuklear.nk_vec2;

/**
 * Nuklear options: GLFW-queried resolutions, letterbox color, and gameplay settings.
 */
public final class OptionsOverlay implements UiOverlay {
    private final GlfwEngine engine;
    private final List<DisplayModes.Mode> modes;
    private final String[] resolutionLabels;

    private final IntBuffer voice = BufferUtils.createIntBuffer(1).put(0, State.get().getLogin().getVoiceVolume());
    private final IntBuffer sound = BufferUtils.createIntBuffer(1).put(0, State.get().getLogin().getSoundVolume());
    private final IntBuffer music = BufferUtils.createIntBuffer(1).put(0, State.get().getLogin().getMusicVolume());
    private final IntBuffer letterboxR = BufferUtils.createIntBuffer(1).put(0, State.get().getLogin().getLetterboxR());
    private final IntBuffer letterboxG = BufferUtils.createIntBuffer(1).put(0, State.get().getLogin().getLetterboxG());
    private final IntBuffer letterboxB = BufferUtils.createIntBuffer(1).put(0, State.get().getLogin().getLetterboxB());

    private int difficultyIndex = Math.max(0, State.get().getLogin().resolveDifficultyInt());
    private int textSpeedIndex;
    private int timeLimitIndex;
    private int comicIndex = State.get().getLogin().getComicEffectOccurence();
    private int resolutionIndex;
    private boolean open = true;

    private final String[] difficulties;
    private final String[] textSpeeds;
    private final String[] timeLimits;
    private final String[] comicRates;

    public OptionsOverlay(GlfwEngine engine) {
        this.engine = engine;
        this.modes = DisplayModes.queryAll();
        this.resolutionLabels = modes.stream().map(DisplayModes.Mode::label).toArray(String[]::new);

        Language lang = Language.get();
        difficulties = new String[]{lang.get(26), lang.get(27), lang.get(28), lang.get(29), lang.get(30)};
        textSpeeds = new String[]{lang.get(22), lang.get(23), lang.get(24), lang.get(25)};
        timeLimits = new String[]{lang.get(424), "180", "150", "120", "90", "60", "45", "30"};
        comicRates = new String[]{lang.get(1), lang.get(2), lang.get(3), lang.get(4)};

        textSpeedIndex = indexOf(textSpeeds, State.get().getLogin().getTextSpeed(), 2);
        timeLimitIndex = indexOf(timeLimits, State.get().getLogin().getTimeLimitString(), 4);
        DisplayModes.Mode selected = DisplayModes.findByStorageKey(modes, State.get().getLogin().getGraphicsSetting());
        resolutionIndex = DisplayModes.indexOf(modes, selected);
        if (difficultyIndex >= difficulties.length || difficultyIndex < 0) {
            difficultyIndex = 0;
        }
    }

    @Override
    public boolean layout(NkContext ctx, MemoryStack stack, int windowWidth, int windowHeight) {
        if (!open) {
            return false;
        }
        float w = 460;
        float h = 520;
        NkRect bounds = nk_rect((windowWidth - w) * 0.5f, (windowHeight - h) * 0.5f, w, h, NkRect.malloc(stack));
        Language lang = Language.get();
        if (nk_begin(ctx, lang.get(34), bounds, NK_WINDOW_BORDER | NK_WINDOW_TITLE | NK_WINDOW_MOVABLE)) {
            resolutionIndex = comboRow(ctx, stack, "Resolution", resolutionLabels, resolutionIndex);
            nk_layout_row_dynamic(ctx, 18, 1);
            nk_label(ctx, "Refresh follows the monitor the window is on (vsync).", NK_TEXT_LEFT);
            nk_layout_row_dynamic(ctx, 18, 1);
            nk_label(ctx, "852×480 (16:9) is preserved; bars fill unused edges.", NK_TEXT_LEFT);

            nk_layout_row_dynamic(ctx, 24, 1);
            nk_label(ctx, "Letterbox color", NK_TEXT_LEFT);
            nk_layout_row_dynamic(ctx, 28, 1);
            nk_property_int(ctx, "R", 0, letterboxR, 255, 1, 1);
            nk_layout_row_dynamic(ctx, 28, 1);
            nk_property_int(ctx, "G", 0, letterboxG, 255, 1, 1);
            nk_layout_row_dynamic(ctx, 28, 1);
            nk_property_int(ctx, "B", 0, letterboxB, 255, 1, 1);

            difficultyIndex = comboRow(ctx, stack, lang.get(6), difficulties, difficultyIndex);
            nk_layout_row_dynamic(ctx, 28, 1);
            nk_property_int(ctx, lang.get(420), 1, voice, 100, 1, 1);
            nk_layout_row_dynamic(ctx, 28, 1);
            nk_property_int(ctx, lang.get(418), 1, sound, 100, 1, 1);
            nk_layout_row_dynamic(ctx, 28, 1);
            nk_property_int(ctx, lang.get(419), 1, music, 100, 1, 1);
            textSpeedIndex = comboRow(ctx, stack, lang.get(7), textSpeeds, textSpeedIndex);
            timeLimitIndex = comboRow(ctx, stack, lang.get(14), timeLimits, timeLimitIndex);
            comicIndex = comboRow(ctx, stack, lang.get(17), comicRates, comicIndex);

            nk_layout_row_dynamic(ctx, 32, 2);
            if (nk_button_label(ctx, lang.get(20))) {
                applyAndSave();
                open = false;
            }
            if (nk_button_label(ctx, lang.get(421))) {
                open = false;
            }
        }
        nk_end(ctx);
        return open;
    }

    private void applyAndSave() {
        var login = State.get().getLogin();
        login.setDifficulty(login.getDifficultyConstant(Math.min(difficultyIndex, 5)));
        login.setDifficultyDynamic(login.getDifficulty());
        login.setVoiceVolume(voice.get(0));
        login.setSoundVolume(sound.get(0));
        login.setMusicVolume(music.get(0));
        Audio.volume(AudioType.VOICE, voice.get(0));
        Audio.volume(AudioType.SOUND, sound.get(0));
        Audio.volume(AudioType.MUSIC, music.get(0));
        login.setTextSpeed(textSpeeds[textSpeedIndex]);
        String time = timeLimits[timeLimitIndex];
        if (time.equalsIgnoreCase(Language.get().get(424)) || time.equalsIgnoreCase("infinite")) {
            login.setTimeLimit(INFINITE_TIME);
        } else {
            login.setTimeLimit(Integer.parseInt(time));
        }
        login.setComicEffectOccurence(comicIndex);
        login.setLetterboxR(letterboxR.get(0));
        login.setLetterboxG(letterboxG.get(0));
        login.setLetterboxB(letterboxB.get(0));

        DisplayModes.Mode mode = modes.get(Math.max(0, Math.min(resolutionIndex, modes.size() - 1)));
        login.setGraphicsSetting(mode.storageKey());
        engine.applyResolution(mode.width(), mode.height());
        engine.refreshLetterboxColor();

        try {
            State.get().saveConfigFile();
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
    }

    private static int comboRow(NkContext ctx, MemoryStack stack, String label, String[] items, int selected) {
        if (items.length == 0) {
            return 0;
        }
        nk_layout_row_dynamic(ctx, 24, 1);
        nk_label(ctx, label, NK_TEXT_LEFT);
        nk_layout_row_dynamic(ctx, 28, 1);
        int clamped = Math.max(0, Math.min(selected, items.length - 1));
        float popupHeight = Math.min(items.length, 12) * 28f;
        NkVec2 size = nk_vec2(nk_widget_width(ctx), popupHeight, NkVec2.malloc(stack));
        if (nk_combo_begin_label(ctx, items[clamped], size)) {
            nk_layout_row_dynamic(ctx, 25, 1);
            for (int i = 0; i < items.length; i++) {
                if (nk_combo_item_label(ctx, items[i], NK_TEXT_LEFT)) {
                    clamped = i;
                }
            }
            nk_combo_end(ctx);
        }
        return clamped;
    }

    private static int indexOf(String[] items, String value, int fallback) {
        if (value == null) {
            return fallback;
        }
        for (int i = 0; i < items.length; i++) {
            if (value.equalsIgnoreCase(items[i])) {
                return i;
            }
        }
        return fallback;
    }
}

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
package com.scndgen.legends.mode;

import java.util.EnumMap;
import java.util.Map;

import com.scndgen.legends.LangKey;
import com.scndgen.legends.enums.AmbientMode;
import com.scndgen.legends.enums.AnimationDirection;
import com.scndgen.legends.enums.Stage;
import com.scndgen.legends.render.RenderGamePlay;

/**
 * Stage preview prefixes, display names, and motion setup in one table.
 */
public final class StageCatalog {

    public record Layout(
            LangKey name,
            String previewPrefix,
            float fgX,
            float fgY,
            float fgXInc,
            float fgYInc,
            float loops,
            AnimationDirection ambientDir,
            AnimationDirection fgDir,
            AmbientMode ambientMode,
            int ambSpeed1,
            int ambSpeed2,
            int musicIndex
    ) {
        public void apply(RenderGamePlay renderGamePlay) {
            renderGamePlay.applyStagePresentation(
                    fgX, fgY, fgXInc, fgYInc, loops,
                    ambientDir, fgDir, ambientMode, ambSpeed1, ambSpeed2);
        }
    }

    private static final Map<Stage, Layout> LAYOUTS = new EnumMap<>(Stage.class);

    static {
        LAYOUTS.put(Stage.IBEX_HILL, layout(LangKey.STAGE_IBEX_HILL, "bgBG1",
                0, 10, 1, 1, 10,
                AnimationDirection.HORIZONTAL, AnimationDirection.NONE,
                AmbientMode.BOTH_IN_FOREGROUND, 4, 3, 0));
        LAYOUTS.put(Stage.CHELSTON_CITY_DOCKS, layout(LangKey.STAGE_CHESTON_DOCKS, "bgBG2",
                0, 0, 1, 1, 20,
                AnimationDirection.NONE, AnimationDirection.NONE,
                AmbientMode.NONE, 0, 0, 1));
        LAYOUTS.put(Stage.DESERT_RUINS, layout(LangKey.STAGE_RUINED_HALL, "bgBG3",
                0, 0, 5, 1, 4,
                AnimationDirection.HORIZONTAL, AnimationDirection.NONE,
                AmbientMode.BOTH_IN_FOREGROUND, 2, 1, 2));
        LAYOUTS.put(Stage.CHELSTON_CITY_STREETS, layout(LangKey.STAGE_CHESTON_STREETS, "bgBG4",
                0, 0, 1, 1, 20,
                AnimationDirection.NONE, AnimationDirection.NONE,
                AmbientMode.NONE, 0, 0, 3));
        LAYOUTS.put(Stage.IBEX_HILL_NIGHT, layout(LangKey.STAGE_IBEX_NIGHT, "bgBG5",
                0, 10, 1, 1, 10,
                AnimationDirection.HORIZONTAL, AnimationDirection.NONE,
                AmbientMode.BOTH_IN_FOREGROUND, 4, 3, 4));
        LAYOUTS.put(Stage.SCORCHED_RUINS, layout(LangKey.STAGE_SCORCHED, "bgBG6",
                0, 0, 5, 1, 4,
                AnimationDirection.HORIZONTAL, AnimationDirection.NONE,
                AmbientMode.INDEPENDENT, 2, 1, 5));
        LAYOUTS.put(Stage.FROZEN_WILDERNESS, layout(LangKey.STAGE_FROZEN, "bgBG7",
                0, 10, 5, 1, 4,
                AnimationDirection.VERTICAL, AnimationDirection.NONE,
                AmbientMode.BOTH_IN_BACKGROUND, 2, 1, 6));
        LAYOUTS.put(Stage.DISTANT_ISLE, layout(LangKey.STAGE_DISTANT_ISLE, "bgBG100",
                -40, 20, 2, 0.5f, 20,
                AnimationDirection.HORIZONTAL, AnimationDirection.VERTICAL,
                AmbientMode.BOTH_IN_BACKGROUND, 1, 2, 0));
        LAYOUTS.put(Stage.HIDDEN_CAVE, layout(LangKey.STAGE_HIDDEN_CAVE, "bgBG8",
                0, 0, 1, 1, 20,
                AnimationDirection.NONE, AnimationDirection.NONE,
                AmbientMode.NONE, 0, 0, 2));
        LAYOUTS.put(Stage.AFRICAN_VILLAGE, layout(LangKey.STAGE_AFRICAN_VILLAGE, "bgBG9",
                0, 0, 1, 1, 20,
                AnimationDirection.HORIZONTAL, AnimationDirection.NONE,
                AmbientMode.BOTH_IN_BACKGROUND, 2, 1, 3));
        LAYOUTS.put(Stage.APOCALYPTO, layout(LangKey.STAGE_APOCALYPSE, "bgBG10",
                0, 0, 5, 1, 4,
                AnimationDirection.HORIZONTAL, AnimationDirection.NONE,
                AmbientMode.BOTH_IN_FOREGROUND, 2, 1, 4));
        LAYOUTS.put(Stage.DISTANT_ISLE_NIGHT, layout(LangKey.STAGE_MOONLIT_SHORE, "bgBG11",
                -40, 20, 2, 0.5f, 20,
                AnimationDirection.HORIZONTAL, AnimationDirection.VERTICAL,
                AmbientMode.BOTH_IN_BACKGROUND, 1, 2, 1));
        LAYOUTS.put(Stage.DESERT_RUINS_NIGHT, layout(LangKey.STAGE_RUINED_HALL_NIGHT, "bgBG13",
                0, 0, 5, 1, 4,
                AnimationDirection.HORIZONTAL, AnimationDirection.NONE,
                AmbientMode.BOTH_IN_FOREGROUND, 2, 1, 1));
        LAYOUTS.put(Stage.SCORCHED_RUINS_NIGHT, layout(LangKey.STAGE_SCORCHED_NIGHT, "bgBG14",
                0, 0, 5, 1, 4,
                AnimationDirection.HORIZONTAL, AnimationDirection.NONE,
                AmbientMode.INDEPENDENT, 2, 1, 3));
        LAYOUTS.put(Stage.HIDDEN_CAVE_NIGHT, layout(LangKey.STAGE_HIDDEN_CAVE_NIGHT, "bgBG15",
                0, 0, 1, 1, 20,
                AnimationDirection.NONE, AnimationDirection.NONE,
                AmbientMode.NONE, 0, 0, 2));
        LAYOUTS.put(Stage.RANDOM, layout(LangKey.STAGE_RANDOM, "bgBG12",
                0, 10, 1, 1, 10,
                AnimationDirection.VERTICAL, AnimationDirection.NONE,
                AmbientMode.INDEPENDENT, 4, 3, 0));
    }

    private StageCatalog() {
    }

    public static Layout layout(Stage stage) {
        return LAYOUTS.get(stage);
    }

    private static Layout layout(LangKey name,
                                 String previewPrefix,
                                 float fgX,
                                 float fgY,
                                 float fgXInc,
                                 float fgYInc,
                                 float loops,
                                 AnimationDirection ambientDir,
                                 AnimationDirection fgDir,
                                 AmbientMode ambientMode,
                                 int ambSpeed1,
                                 int ambSpeed2,
                                 int musicIndex) {
        return new Layout(name, previewPrefix, fgX, fgY, fgXInc, fgYInc, loops,
                ambientDir, fgDir, ambientMode, ambSpeed1, ambSpeed2, musicIndex);
    }
}

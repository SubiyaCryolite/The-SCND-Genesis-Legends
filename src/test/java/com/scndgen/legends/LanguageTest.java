/**************************************************************************

 The SCND Genesis: Legends is a fighting game based on THE SCND GENESIS,
 a webcomic created by Ifunga Ndana (https://www.scndgen.com).

 The SCND Genesis: Legends RMX  © 2017 Ifunga Ndana.

 The SCND Genesis: Legends is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
    10| the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 The SCND Genesis: Legends is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with The SCND Genesis: Legends. If not, see <https://www.gnu.org/licenses/>.

 **************************************************************************/
package com.scndgen.legends;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LanguageTest {

    @BeforeEach
    void reset() {
        Language.resetForTests();
    }

    @Test
    void namedKeysResolveInEnglish() {
        var language = Language.get();
        for (var key : LangKey.values()) {
            var value = language.get(key);
            assertFalse(value.startsWith("No translation ::"), () -> key + " missing");
        }
        for (var key : StoryKey.values()) {
            var value = language.get(key);
            assertFalse(value.startsWith("No translation ::"), () -> key + " missing");
        }
        for (var key : TutorialKey.values()) {
            var value = language.get(key);
            assertFalse(value.startsWith("No translation ::"), () -> key + " missing");
        }
        for (var key : BragKey.values()) {
            var value = language.get(key);
            assertFalse(value.startsWith("No translation ::"), () -> key + " missing");
        }
        for (var key : MoveKey.values()) {
            var value = language.get(key);
            assertFalse(value.startsWith("No translation ::"), () -> key + " missing");
        }
    }

    @Test
    void storyLinesKeepFoldedSuffixes() {
        var language = Language.get();
        assertEquals("*ring* *ring* .......", language.get(StoryKey.S3_03));
        assertEquals("DIE!!!!! !!!!!!!!!!!!!!", language.get(StoryKey.S11_10));
    }

    @Test
    void templatesSubstitutePlaceholders() {
        var language = Language.get();
        assertEquals("HP: 80 : 40%", language.get(LangKey.HP, 80, 40));
        assertEquals("Scene 3", language.get(LangKey.SCENE, 3));
        assertEquals("2 time(s)", language.get(LangKey.TIMES, 2));
        assertEquals("3 minutes and 5 seconds", language.get(LangKey.PLAY_TIME_MINUTES, 3, 5));
        assertEquals("The SCND Genesis: Legends RMX | copyright © 2011-2026 Ifunga Ndana.",
                language.get(LangKey.COPYRIGHT_RMX, "2011-2026"));
    }

    @Test
    void missingKeyFallsBackThenPlaceholder() {
        var language = Language.get();
        assertTrue(language.get(LangKey.OK).length() > 0);
        assertEquals("No translation :: 99999", language.get(99999));
    }

    @Test
    void unknownPackFallsBackToEnglish() {
        var language = Language.get();
        language.setLanguage(99);
        assertEquals(Language.ENGLISH, language.currentIndex());
        assertEquals("OK", language.get(LangKey.OK));
    }

    @Test
    void localeListenersFire() {
        var language = Language.get();
        var hits = new AtomicInteger();
        language.addLocaleListener(hits::incrementAndGet);
        language.setLanguage(Language.ENGLISH);
        assertEquals(1, hits.get());
    }

    @Test
    void englishPackContainsEveryNamedKey() {
        var pack = Language.get().englishPack();
        var missing = new ArrayList<String>();
        for (var key : LangKey.values()) {
            if (!pack.containsKey(key.id())) {
                missing.add(key.name());
            }
        }
        for (var key : StoryKey.values()) {
            if (!pack.containsKey(key.id())) {
                missing.add(key.name());
            }
        }
        for (var key : TutorialKey.values()) {
            if (!pack.containsKey(key.id())) {
                missing.add(key.name());
            }
        }
        for (var key : BragKey.values()) {
            if (!pack.containsKey(key.id())) {
                missing.add(key.name());
            }
        }
        for (var key : MoveKey.values()) {
            if (!pack.containsKey(key.id())) {
                missing.add(key.name());
            }
        }
        assertTrue(missing.isEmpty(), () -> "English pack missing " + missing);
    }
}

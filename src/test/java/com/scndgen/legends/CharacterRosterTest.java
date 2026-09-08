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
package com.scndgen.legends;

import com.scndgen.legends.characters.CharacterRoster;
import com.scndgen.legends.enums.CharacterEnum;
import com.scndgen.legends.enums.Stage;
import com.scndgen.legends.mode.StageCatalog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CharacterRosterTest {

    @BeforeEach
    void resetLanguage() {
        Language.resetForTests();
    }

    @Test
    void everyFighterHasARosterEntry() {
        for (var id : CharacterEnum.values()) {
            var def = CharacterRoster.def(id);
            assertNotNull(def, () -> id + " missing from roster");
            assertEquals(id, def.id());
            assertEquals(12, def.brags().length, () -> id + " brag count");
            assertTrue(def.attacks().containsKey("01"), () -> id + " missing 01");
            assertTrue(def.attacks().containsKey("12"), () -> id + " missing 12");
        }
        assertTrue(CharacterRoster.def(CharacterEnum.AISHA).attacks().containsKey("00"));
        assertTrue(CharacterRoster.def(CharacterEnum.ADE).attacks().containsKey("00"));
        assertEquals(CharacterRoster.MoveKind.BOOST,
                CharacterRoster.def(CharacterEnum.RAILA).attacks().get("11").kind());
        assertEquals(40000, CharacterRoster.def(CharacterEnum.THING).life());
    }

    @Test
    void bragsAndMovesResolveByOpponentAndSlot() {
        var language = Language.get();
        assertEquals("Lets do this!!", language.get(BragKey.SUBIYA_VS_RAILA));
        assertEquals("I won't go easy on you bro", language.get(BragKey.RAILA_VS_SUBIYA));
        assertEquals("Thunder Clap", language.get(MoveKey.SUBIYA_P0));
        assertEquals("Weaken Opponent", language.get(MoveKey.RAILA_S3));
        var subiya = CharacterRoster.create(CharacterEnum.SUBIYA);
        assertEquals("Subiya: Lets do this!!", subiya.getBraggingRights(CharacterEnum.RAILA.index()));
        assertEquals("Thunder Clap", subiya.getMoveQued(1));
    }

    @Test
    void everyStageHasALayout() {
        for (var stage : Stage.values()) {
            var layout = StageCatalog.layout(stage);
            assertNotNull(layout, () -> stage + " missing layout");
            assertTrue(layout.previewPrefix().startsWith("bgBG"));
        }
    }
}

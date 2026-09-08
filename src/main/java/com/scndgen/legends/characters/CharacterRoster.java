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
package com.scndgen.legends.characters;

import com.scndgen.legends.BragKey;
import com.scndgen.legends.MoveKey;
import com.scndgen.legends.enums.CharacterEnum;

import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * One fighter type plus a data table. Move names and vs-brags are {@link MoveKey}/{@link BragKey}.
 */
public final class CharacterRoster {

    public enum MoveKind {
        STRIKE,
        RESTORE,
        BOOST,
        WEAKEN
    }

    public record AttackSpec(MoveKind kind, int nameIndex, int damage) {
    }

    public record CharacterDef(
            CharacterEnum id,
            String name,
            String descSmall,
            boolean male,
            int points,
            int life,
            float atbRecoveryRate,
            int damageBonus,
            MoveKey[] physical,
            MoveKey[] celestia,
            MoveKey[] status,
            BragKey[] brags,
            int[] behaviours1,
            int[] behaviours2,
            int[] behaviours3,
            int[] behaviours4,
            int[] behaviours5,
            Map<String, AttackSpec> attacks
    ) {
    }

    private static final int[] B1 = {0, 1, 2, 3, 4, 5, 6, 7, 8};
    private static final int[] B2 = {0, 1, 2, 3, 4, 5, 6, 7, 8, 10, 11};
    private static final int[] B3 = {0, 1, 7, 8, 10, 11};
    private static final int[] B4 = {0, 1, 9, 12, 10, 11};
    private static final int[] B5 = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};

    private static final Map<CharacterEnum, CharacterDef> DEFS = new EnumMap<>(CharacterEnum.class);

    static {
        put(fighter(CharacterEnum.SUBIYA, "Subiya", "Subiya - a fighter specialised in melee combat", true,
                2000, 27200, 1.97f, 0, heals(85, 87, 90, 87, 88, 86, 88, 93, 79, 69, 73, 72)));
        put(fighter(CharacterEnum.RAILA, "Raila", "Raila - a fighter specialised in celestiaAttacks combat", true,
                2500, 25600, 2.5f, 0, mix(83, 82, 82, 81, 88, 85, 85, 83, 72, 79)));
        put(fighter(CharacterEnum.LYNX, "Lynx", "Lynx - a fighter specialised in dual blade combat", true,
                0, 30400, 2.20f, 0, heals(100, 98, 97, 96, 98, 97, 103, 100, 73, 75, 79, 71)));
        put(fighter(CharacterEnum.AISHA, "Aisha", "Aisha - a fighter specialised in sword combat", false,
                0, 29440, 2.14f, 0, healsExtra(50, 93, 100, 95, 94, 94, 95, 97, 97, 82, 84, 78, 80)));
        put(fighter(CharacterEnum.ADE, "Ade", "Ade - a fighter utilising the air element", true,
                2200, 33600, 1.60f, 0, healsExtra(50, 110, 106, 110, 108, 107, 106, 108, 113, 77, 79, 73, 75)));
        put(fighter(CharacterEnum.RAVAGE, "Ravage", "Ravage - a fighter specialised in brute force via the Earth element", true,
                0, 32000, 1.70f, 0, mix(108, 102, 103, 103, 101, 107, 103, 102, 82, 99)));
        put(fighter(CharacterEnum.JONAH, "Jonah", "Jonah - a fighter specialised in Force combat", true,
                0, 29760, 2.08f, 0, heals(95, 95, 96, 98, 94, 96, 101, 98, 73, 75, 79, 71)));
        put(fighter(CharacterEnum.ADAM, "Adam", "Adam - a Celestia Being specialised in celestiaAttacks combat", true,
                1800, 35200, 1.60f, 0, heals(118, 113, 112, 113, 112, 111, 115, 115, 73, 75, 79, 71)));
        put(fighter(CharacterEnum.NOVA_ADAM, "NovaAdam",
                "Nova Adam - an awakened Celestia Being specialised in celestiaAttacks combat", true,
                0, 38400, 1.10f, 0, heals(128, 123, 122, 123, 122, 121, 125, 125, 123, 125, 129, 111)));
        put(fighter(CharacterEnum.AZARIA, "Azaria", "Azaria - Specialised in general combat and the water element", false,
                1800, 32000, 2.30f, 0, heals(102, 105, 102, 103, 102, 101, 108, 105, 78, 80, 84, 76)));
        put(fighter(CharacterEnum.SORROWE, "Sorrowe",
                "Sorrowe - Specialised in celestiaAttacks combat and the flame element", false,
                1800, 31360, 2.02f, 0, heals(102, 105, 102, 103, 102, 101, 108, 105, 78, 80, 84, 76)));
        put(fighter(CharacterEnum.THING, "The Thing", "The Thing - Origins unknown", true,
                1800, 40000, 0.85f, 0, heals(130, 129, 128, 127, 130, 129, 128, 127, 106, 100, 108, 102)));
    }

    private CharacterRoster() {
    }

    public static Character create(CharacterEnum id) {
        var def = DEFS.get(id);
        if (def == null) {
            throw new IllegalArgumentException("No roster entry for " + id);
        }
        return new Character(def);
    }

    public static CharacterDef def(CharacterEnum id) {
        return DEFS.get(id);
    }

    private static void put(CharacterDef def) {
        DEFS.put(def.id(), def);
    }

    private static CharacterDef fighter(CharacterEnum id,
                                        String name,
                                        String descSmall,
                                        boolean male,
                                        int points,
                                        int life,
                                        float atb,
                                        int bonus,
                                        Map<String, AttackSpec> attacks) {
        return new CharacterDef(id, name, descSmall, male, points, life, atb, bonus,
                slotKeys(id, "P"), slotKeys(id, "C"), slotKeys(id, "S"), brags(id),
                B1, B2, B3, B4, B5, attacks);
    }

    private static MoveKey[] slotKeys(CharacterEnum id, String group) {
        return new MoveKey[]{
                MoveKey.valueOf(id.name() + "_" + group + "0"),
                MoveKey.valueOf(id.name() + "_" + group + "1"),
                MoveKey.valueOf(id.name() + "_" + group + "2"),
                MoveKey.valueOf(id.name() + "_" + group + "3")
        };
    }

    private static BragKey[] brags(CharacterEnum speaker) {
        var opponents = CharacterEnum.values();
        var keys = new BragKey[opponents.length];
        for (int i = 0; i < opponents.length; i++) {
            keys[i] = BragKey.valueOf(speaker.name() + "_VS_" + opponents[i].name());
        }
        return keys;
    }

    private static AttackSpec strike(int nameIndex, int damage) {
        return new AttackSpec(MoveKind.STRIKE, nameIndex, damage);
    }

    private static AttackSpec restore(int statusIndex, int damage) {
        return new AttackSpec(MoveKind.RESTORE, 8 + statusIndex, damage);
    }

    private static Map<String, AttackSpec> heals(int p0, int p1, int p2, int p3,
                                                 int c0, int c1, int c2, int c3,
                                                 int s0, int s1, int s2, int s3) {
        return kit(null, p0, p1, p2, p3, c0, c1, c2, c3, s0, s1,
                restore(2, s2), restore(3, s3));
    }

    private static Map<String, AttackSpec> healsExtra(int extra, int p0, int p1, int p2, int p3,
                                                      int c0, int c1, int c2, int c3,
                                                      int s0, int s1, int s2, int s3) {
        return kit(extra, p0, p1, p2, p3, c0, c1, c2, c3, s0, s1,
                restore(2, s2), restore(3, s3));
    }

    private static Map<String, AttackSpec> mix(int p0, int p1, int p2, int p3,
                                               int c0, int c1, int c2, int c3,
                                               int s0, int s1) {
        return kit(null, p0, p1, p2, p3, c0, c1, c2, c3, s0, s1,
                new AttackSpec(MoveKind.BOOST, 10, 0),
                new AttackSpec(MoveKind.WEAKEN, 11, 0));
    }

    private static Map<String, AttackSpec> kit(Integer extra,
                                               int p0, int p1, int p2, int p3,
                                               int c0, int c1, int c2, int c3,
                                               int s0, int s1,
                                               AttackSpec eleven,
                                               AttackSpec twelve) {
        var attacks = new LinkedHashMap<String, AttackSpec>();
        if (extra != null) {
            attacks.put("00", strike(0, extra));
        }
        attacks.put("01", strike(0, p0));
        attacks.put("02", strike(1, p1));
        attacks.put("03", strike(2, p2));
        attacks.put("04", strike(3, p3));
        attacks.put("05", strike(4, c0));
        attacks.put("06", strike(5, c1));
        attacks.put("07", strike(6, c2));
        attacks.put("08", strike(7, c3));
        attacks.put("09", restore(0, s0));
        attacks.put("10", restore(1, s1));
        attacks.put("11", eleven);
        attacks.put("12", twelve);
        return Map.copyOf(attacks);
    }
}

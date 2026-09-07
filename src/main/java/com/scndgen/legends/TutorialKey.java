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

/**
 * Tutorial overlay copy, grouped by the six shortcut chapters plus the closing slide.
 */
public enum TutorialKey implements TextKey {
    INTRO_01(320),
    INTRO_02(321),

    HUD_01(322),
    HUD_02(344),
    HUD_03(345),
    HUD_04(323),
    HUD_05(324),
    HUD_06(325),
    HUD_07(326),
    HUD_08(327),

    CM_01(328),
    CM_02(329),
    CM_03(330),
    CM_04(331),
    CM_05(332),
    CM_06(333),
    CM_07(334),
    CM_08(335),
    CM_09(336),

    FURY_01(352),
    FURY_02(353),
    FURY_03(354),
    FURY_04(361),
    FURY_05(362),
    FURY_06(363),
    FURY_07(336),

    AB_01(337),
    AB_02(338),
    AB_03(339),
    AB_04(340),
    AB_05(341),

    ATTACKS_01(346),
    ATTACKS_02(347),
    ATTACKS_03(348),
    ATTACKS_04(349),
    ATTACKS_05(350),
    ATTACKS_06(367),
    ATTACKS_07(351),

    DONE(393);

    private final int id;

    TutorialKey(int id) {
        this.id = id;
    }

    @Override
    public int id() {
        return id;
    }
}

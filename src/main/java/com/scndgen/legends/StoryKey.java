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
 * Story cutscene lines, numbered as the player sees them (Scene 1–12).
 * {@code Sn_mm} is line mm in that scene, in playback order.
 */
public enum StoryKey implements TextKey {
    S1_01(174),
    S1_02(175),
    S1_03(176),
    S1_04(431),
    S1_05(432),
    S1_06(433),
    S1_07(434),
    S1_08(435),
    S1_09(436),
    S1_10(437),
    S1_11(438),
    S1_12(439),
    S1_13(440),
    S1_14(178),
    S1_15(179),
    S1_16(180),
    S1_17(181),
    S1_18(182),

    S2_01(441),
    S2_02(183),
    S2_03(184),
    S2_04(185),
    S2_05(186),
    S2_06(443),
    S2_07(444),
    S2_08(187),

    S3_01(188),
    S3_02(189),
    S3_03(190),
    S3_04(191),
    S3_05(192),
    S3_06(193),
    S3_07(194),
    S3_08(195),
    S3_09(196),
    S3_10(197),
    S3_11(198),
    S3_12(199),
    S3_13(200),
    S3_14(201),
    S3_15(202),
    S3_16(203),
    S3_17(204),
    S3_18(205),

    S4_01(206),
    S4_02(207),
    S4_03(208),
    S4_04(209),
    S4_05(210),
    S4_06(211),
    S4_07(212),
    S4_08(213),
    S4_09(214),
    S4_10(215),
    S4_11(216),
    S4_12(425),
    S4_13(426),
    S4_14(427),
    S4_15(428),
    S4_16(429),
    S4_17(430),

    S5_01(218),
    S5_02(219),
    S5_03(220),
    S5_04(221),
    S5_05(222),
    S5_06(223),
    S5_07(224),
    S5_08(225),
    S5_09(226),
    S5_10(227),
    S5_11(228),
    S5_12(229),
    S5_13(230),

    S6_01(231),
    S6_02(232),
    S6_03(233),
    S6_04(234),
    S6_05(235),
    S6_06(236),
    S6_07(237),
    S6_08(238),
    S6_09(239),
    S6_10(240),
    S6_11(241),
    S6_12(242),
    S6_13(243),
    S6_14(244),
    S6_15(245),
    S6_16(246),
    S6_17(247),
    S6_18(248),
    S6_19(249),
    S6_20(250),

    S7_01(251),
    S7_02(252),
    S7_03(253),
    S7_04(254),
    S7_05(255),
    S7_06(256),
    S7_07(257),
    S7_08(258),
    S7_09(259),
    S7_10(260),
    S7_11(261),
    S7_12(262),

    S8_01(263),
    S8_02(264),
    S8_03(265),
    S8_04(266),
    S8_05(267),
    S8_06(268),
    S8_07(269),
    S8_08(445),
    S8_09(446),

    S9_01(270),
    S9_02(271),
    S9_03(272),
    S9_04(273),
    S9_05(274),
    S9_06(275),
    S9_07(276),
    S9_08(277),
    S9_09(278),
    S9_10(279),

    S10_01(280),
    S10_02(281),
    S10_03(282),
    S10_04(283),
    S10_05(284),
    S10_06(447),
    S10_07(285),
    S10_08(286),
    S10_09(287),
    S10_10(288),
    S10_11(448),
    S10_12(449),
    S10_13(289),
    S10_14(290),
    S10_15(291),
    S10_16(292),
    S10_17(293),

    S11_01(294),
    S11_02(231),
    S11_03(295),
    S11_04(296),
    S11_05(297),
    S11_06(298),
    S11_07(299),
    S11_08(300),
    S11_09(301),
    S11_10(302),
    S11_11(303),
    S11_12(304),
    S11_13(305),
    S11_14(306),

    S12_01(373),
    S12_02(374),
    S12_03(375),
    S12_04(376),
    S12_05(377),
    S12_06(378),
    S12_07(379),
    S12_08(380),
    S12_09(381),
    S12_10(383),
    S12_11(384),
    S12_12(385),
    S12_13(386),
    S12_14(387),
    S12_15(388),
    S12_16(389),
    S12_17(390),
    S12_18(391),
    S12_19(392);

    private final int id;

    StoryKey(int id) {
        this.id = id;
    }

    @Override
    public int id() {
        return id;
    }
}

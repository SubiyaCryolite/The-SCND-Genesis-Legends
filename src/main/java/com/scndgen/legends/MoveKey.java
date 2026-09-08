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

/**
 * In-match move names. {@code CHAR_P*} physical, {@code CHAR_C*} celestia, {@code CHAR_S*} status.
 */
public enum MoveKey implements TextKey {
    // SUBIYA physical, celestia, status
    SUBIYA_P0(655),
    SUBIYA_P1(656),
    SUBIYA_P2(657),
    SUBIYA_P3(658),
    SUBIYA_C0(659),
    SUBIYA_C1(660),
    SUBIYA_C2(661),
    SUBIYA_C3(662),
    SUBIYA_S0(663),
    SUBIYA_S1(664),
    SUBIYA_S2(665),
    SUBIYA_S3(666),
    // RAILA physical, celestia, status
    RAILA_P0(667),
    RAILA_P1(668),
    RAILA_P2(669),
    RAILA_P3(670),
    RAILA_C0(671),
    RAILA_C1(672),
    RAILA_C2(673),
    RAILA_C3(674),
    RAILA_S0(675),
    RAILA_S1(676),
    RAILA_S2(677),
    RAILA_S3(678),
    // LYNX physical, celestia, status
    LYNX_P0(679),
    LYNX_P1(680),
    LYNX_P2(681),
    LYNX_P3(682),
    LYNX_C0(683),
    LYNX_C1(684),
    LYNX_C2(685),
    LYNX_C3(686),
    LYNX_S0(687),
    LYNX_S1(688),
    LYNX_S2(689),
    LYNX_S3(690),
    // AISHA physical, celestia, status
    AISHA_P0(691),
    AISHA_P1(692),
    AISHA_P2(693),
    AISHA_P3(694),
    AISHA_C0(695),
    AISHA_C1(696),
    AISHA_C2(697),
    AISHA_C3(698),
    AISHA_S0(699),
    AISHA_S1(700),
    AISHA_S2(701),
    AISHA_S3(702),
    // ADE physical, celestia, status
    ADE_P0(703),
    ADE_P1(704),
    ADE_P2(705),
    ADE_P3(706),
    ADE_C0(707),
    ADE_C1(708),
    ADE_C2(709),
    ADE_C3(710),
    ADE_S0(711),
    ADE_S1(712),
    ADE_S2(713),
    ADE_S3(714),
    // RAVAGE physical, celestia, status
    RAVAGE_P0(715),
    RAVAGE_P1(716),
    RAVAGE_P2(717),
    RAVAGE_P3(718),
    RAVAGE_C0(719),
    RAVAGE_C1(720),
    RAVAGE_C2(721),
    RAVAGE_C3(722),
    RAVAGE_S0(723),
    RAVAGE_S1(724),
    RAVAGE_S2(725),
    RAVAGE_S3(726),
    // JONAH physical, celestia, status
    JONAH_P0(727),
    JONAH_P1(728),
    JONAH_P2(729),
    JONAH_P3(730),
    JONAH_C0(731),
    JONAH_C1(732),
    JONAH_C2(733),
    JONAH_C3(734),
    JONAH_S0(735),
    JONAH_S1(736),
    JONAH_S2(737),
    JONAH_S3(738),
    // ADAM physical, celestia, status
    ADAM_P0(739),
    ADAM_P1(740),
    ADAM_P2(741),
    ADAM_P3(742),
    ADAM_C0(743),
    ADAM_C1(744),
    ADAM_C2(745),
    ADAM_C3(746),
    ADAM_S0(747),
    ADAM_S1(748),
    ADAM_S2(749),
    ADAM_S3(750),
    // NOVA_ADAM physical, celestia, status
    NOVA_ADAM_P0(751),
    NOVA_ADAM_P1(752),
    NOVA_ADAM_P2(753),
    NOVA_ADAM_P3(754),
    NOVA_ADAM_C0(755),
    NOVA_ADAM_C1(756),
    NOVA_ADAM_C2(757),
    NOVA_ADAM_C3(758),
    NOVA_ADAM_S0(759),
    NOVA_ADAM_S1(760),
    NOVA_ADAM_S2(761),
    NOVA_ADAM_S3(762),
    // AZARIA physical, celestia, status
    AZARIA_P0(763),
    AZARIA_P1(764),
    AZARIA_P2(765),
    AZARIA_P3(766),
    AZARIA_C0(767),
    AZARIA_C1(768),
    AZARIA_C2(769),
    AZARIA_C3(770),
    AZARIA_S0(771),
    AZARIA_S1(772),
    AZARIA_S2(773),
    AZARIA_S3(774),
    // SORROWE physical, celestia, status
    SORROWE_P0(775),
    SORROWE_P1(776),
    SORROWE_P2(777),
    SORROWE_P3(778),
    SORROWE_C0(779),
    SORROWE_C1(780),
    SORROWE_C2(781),
    SORROWE_C3(782),
    SORROWE_S0(783),
    SORROWE_S1(784),
    SORROWE_S2(785),
    SORROWE_S3(786),
    // THING physical, celestia, status
    THING_P0(787),
    THING_P1(788),
    THING_P2(789),
    THING_P3(790),
    THING_C0(791),
    THING_C1(792),
    THING_C2(793),
    THING_C3(794),
    THING_S0(795),
    THING_S1(796),
    THING_S2(797),
    THING_S3(798)
    ;

    private final int id;

    MoveKey(int id) {
        this.id = id;
    }

    @Override
    public int id() {
        return id;
    }
}

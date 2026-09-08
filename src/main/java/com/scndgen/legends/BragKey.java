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
 * Pre-match taunt from the speaker toward a specific opponent.
 * {@code SPEAKER_VS_OPPONENT} follows {@link com.scndgen.legends.enums.CharacterEnum} index order.
 */
public enum BragKey implements TextKey {
    // SUBIYA vs opponent (CharacterEnum index order)
    SUBIYA_VS_SUBIYA(511),
    SUBIYA_VS_RAILA(512),
    SUBIYA_VS_LYNX(513),
    SUBIYA_VS_AISHA(514),
    SUBIYA_VS_ADE(515),
    SUBIYA_VS_RAVAGE(516),
    SUBIYA_VS_JONAH(517),
    SUBIYA_VS_ADAM(518),
    SUBIYA_VS_NOVA_ADAM(519),
    SUBIYA_VS_AZARIA(520),
    SUBIYA_VS_SORROWE(521),
    SUBIYA_VS_THING(522),
    // RAILA vs opponent (CharacterEnum index order)
    RAILA_VS_SUBIYA(523),
    RAILA_VS_RAILA(524),
    RAILA_VS_LYNX(525),
    RAILA_VS_AISHA(526),
    RAILA_VS_ADE(527),
    RAILA_VS_RAVAGE(528),
    RAILA_VS_JONAH(529),
    RAILA_VS_ADAM(530),
    RAILA_VS_NOVA_ADAM(531),
    RAILA_VS_AZARIA(532),
    RAILA_VS_SORROWE(533),
    RAILA_VS_THING(534),
    // LYNX vs opponent (CharacterEnum index order)
    LYNX_VS_SUBIYA(535),
    LYNX_VS_RAILA(536),
    LYNX_VS_LYNX(537),
    LYNX_VS_AISHA(538),
    LYNX_VS_ADE(539),
    LYNX_VS_RAVAGE(540),
    LYNX_VS_JONAH(541),
    LYNX_VS_ADAM(542),
    LYNX_VS_NOVA_ADAM(543),
    LYNX_VS_AZARIA(544),
    LYNX_VS_SORROWE(545),
    LYNX_VS_THING(546),
    // AISHA vs opponent (CharacterEnum index order)
    AISHA_VS_SUBIYA(547),
    AISHA_VS_RAILA(548),
    AISHA_VS_LYNX(549),
    AISHA_VS_AISHA(550),
    AISHA_VS_ADE(551),
    AISHA_VS_RAVAGE(552),
    AISHA_VS_JONAH(553),
    AISHA_VS_ADAM(554),
    AISHA_VS_NOVA_ADAM(555),
    AISHA_VS_AZARIA(556),
    AISHA_VS_SORROWE(557),
    AISHA_VS_THING(558),
    // ADE vs opponent (CharacterEnum index order)
    ADE_VS_SUBIYA(559),
    ADE_VS_RAILA(560),
    ADE_VS_LYNX(561),
    ADE_VS_AISHA(562),
    ADE_VS_ADE(563),
    ADE_VS_RAVAGE(564),
    ADE_VS_JONAH(565),
    ADE_VS_ADAM(566),
    ADE_VS_NOVA_ADAM(567),
    ADE_VS_AZARIA(568),
    ADE_VS_SORROWE(569),
    ADE_VS_THING(570),
    // RAVAGE vs opponent (CharacterEnum index order)
    RAVAGE_VS_SUBIYA(571),
    RAVAGE_VS_RAILA(572),
    RAVAGE_VS_LYNX(573),
    RAVAGE_VS_AISHA(574),
    RAVAGE_VS_ADE(575),
    RAVAGE_VS_RAVAGE(576),
    RAVAGE_VS_JONAH(577),
    RAVAGE_VS_ADAM(578),
    RAVAGE_VS_NOVA_ADAM(579),
    RAVAGE_VS_AZARIA(580),
    RAVAGE_VS_SORROWE(581),
    RAVAGE_VS_THING(582),
    // JONAH vs opponent (CharacterEnum index order)
    JONAH_VS_SUBIYA(583),
    JONAH_VS_RAILA(584),
    JONAH_VS_LYNX(585),
    JONAH_VS_AISHA(586),
    JONAH_VS_ADE(587),
    JONAH_VS_RAVAGE(588),
    JONAH_VS_JONAH(589),
    JONAH_VS_ADAM(590),
    JONAH_VS_NOVA_ADAM(591),
    JONAH_VS_AZARIA(592),
    JONAH_VS_SORROWE(593),
    JONAH_VS_THING(594),
    // ADAM vs opponent (CharacterEnum index order)
    ADAM_VS_SUBIYA(595),
    ADAM_VS_RAILA(596),
    ADAM_VS_LYNX(597),
    ADAM_VS_AISHA(598),
    ADAM_VS_ADE(599),
    ADAM_VS_RAVAGE(600),
    ADAM_VS_JONAH(601),
    ADAM_VS_ADAM(602),
    ADAM_VS_NOVA_ADAM(603),
    ADAM_VS_AZARIA(604),
    ADAM_VS_SORROWE(605),
    ADAM_VS_THING(606),
    // NOVA_ADAM vs opponent (CharacterEnum index order)
    NOVA_ADAM_VS_SUBIYA(607),
    NOVA_ADAM_VS_RAILA(608),
    NOVA_ADAM_VS_LYNX(609),
    NOVA_ADAM_VS_AISHA(610),
    NOVA_ADAM_VS_ADE(611),
    NOVA_ADAM_VS_RAVAGE(612),
    NOVA_ADAM_VS_JONAH(613),
    NOVA_ADAM_VS_ADAM(614),
    NOVA_ADAM_VS_NOVA_ADAM(615),
    NOVA_ADAM_VS_AZARIA(616),
    NOVA_ADAM_VS_SORROWE(617),
    NOVA_ADAM_VS_THING(618),
    // AZARIA vs opponent (CharacterEnum index order)
    AZARIA_VS_SUBIYA(619),
    AZARIA_VS_RAILA(620),
    AZARIA_VS_LYNX(621),
    AZARIA_VS_AISHA(622),
    AZARIA_VS_ADE(623),
    AZARIA_VS_RAVAGE(624),
    AZARIA_VS_JONAH(625),
    AZARIA_VS_ADAM(626),
    AZARIA_VS_NOVA_ADAM(627),
    AZARIA_VS_AZARIA(628),
    AZARIA_VS_SORROWE(629),
    AZARIA_VS_THING(630),
    // SORROWE vs opponent (CharacterEnum index order)
    SORROWE_VS_SUBIYA(631),
    SORROWE_VS_RAILA(632),
    SORROWE_VS_LYNX(633),
    SORROWE_VS_AISHA(634),
    SORROWE_VS_ADE(635),
    SORROWE_VS_RAVAGE(636),
    SORROWE_VS_JONAH(637),
    SORROWE_VS_ADAM(638),
    SORROWE_VS_NOVA_ADAM(639),
    SORROWE_VS_AZARIA(640),
    SORROWE_VS_SORROWE(641),
    SORROWE_VS_THING(642),
    // THING vs opponent (CharacterEnum index order)
    THING_VS_SUBIYA(643),
    THING_VS_RAILA(644),
    THING_VS_LYNX(645),
    THING_VS_AISHA(646),
    THING_VS_ADE(647),
    THING_VS_RAVAGE(648),
    THING_VS_JONAH(649),
    THING_VS_ADAM(650),
    THING_VS_NOVA_ADAM(651),
    THING_VS_AZARIA(652),
    THING_VS_SORROWE(653),
    THING_VS_THING(654)
    ;

    private final int id;

    BragKey(int id) {
        this.id = id;
    }

    @Override
    public int id() {
        return id;
    }
}

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
package com.scndgen.legends.command;

import com.scndgen.legends.ScndGenLegends;
import com.scndgen.legends.enums.CharacterEnum;
import com.scndgen.legends.enums.ModeEnum;
import com.scndgen.legends.enums.PlayerType;
import com.scndgen.legends.network.NetworkManager;
import com.scndgen.legends.render.RenderCharacterSelection;
import com.scndgen.legends.render.RenderGamePlay;
import com.scndgen.legends.render.RenderStageSelect;
import io.github.subiyacryolite.enginev2.Mode;
import io.github.subiyacryolite.enginev2.nuklear.NkDialogs;

/**
 * Applies {@link GameCommand}s on the game thread by calling existing mode APIs.
 */
public final class GameCommandApplier {

    private GameCommandApplier() {
    }

    public static void apply(GameCommand command) {
        switch (command) {
            case GameCommand.SelectCharacter(var character, var slot) -> {
                selectCharacter(character, slot);
                if (slot == PlayerType.PLAYER1 && NetworkManager.get().isOnline()) {
                    RenderCharacterSelection.get().preventCharacterSelection();
                }
            }
            case GameCommand.DeselectOpponentSlot() ->
                    RenderCharacterSelection.get().setSelectedOpponent(false);
            case GameCommand.SelectStage(var stage) -> RenderStageSelect.get().selectStage(stage);
            case GameCommand.StartMatch() -> RenderStageSelect.get().start();
            case GameCommand.GoToStageSelect() ->
                    ScndGenLegends.get().loadMode(ModeEnum.STAGE_SELECT_SCREEN);
            case GameCommand.GoToCharacterSelect(var newInstance) ->
                    ScndGenLegends.get().loadMode(ModeEnum.CHAR_SELECT_SCREEN, newInstance);
            case GameCommand.AttackQueue(var attacker, var moves) -> {
                var renderGamePlay = RenderGamePlay.get();
                if (attacker == PlayerType.PLAYER2) {
                    renderGamePlay.opponentAttack(moves.stream().map(String::valueOf).toList());
                } else {
                    renderGamePlay.characterAttack(moves);
                }
            }
            case GameCommand.TriggerFury(var player) -> RenderGamePlay.get().triggerFury(player);
            case GameCommand.HostTimeLimit(var seconds) -> {
                NetworkManager.get().hostTimeLimit = seconds;
                System.out.println("aquired time is " + seconds);
            }
            case GameCommand.ConnectToHost() -> {
                var networkManager = NetworkManager.get();
                if (networkManager.isServer()) {
                    networkManager.promptServer();
                } else {
                    networkManager.setConnectedToPartner(true);
                }
            }
            case GameCommand.DisconnectFromHost() -> {
                ScndGenLegends.get().engine().ui().push(NkDialogs.message(
                        "Ouchies",
                        "HARSH!",
                        "The opponent doesnt want to fight you -_-"
                ));
                NetworkManager.get().close();
            }
            case GameCommand.CancelConnectivity() -> {
                ScndGenLegends.get().engine().ui().push(NkDialogs.message(
                        "Yikes",
                        "Your opponent has terminated this network session",
                        "Well, that sucks"
                ));
                NetworkManager.get().close();
            }
            case GameCommand.SessionError(var detail) -> {
                ScndGenLegends.get().engine().ui().push(NkDialogs.message(
                        "Network Error",
                        "Something went wrong during the online session",
                        detail == null ? "" : detail
                ));
                NetworkManager.get().close();
            }
            case GameCommand.LoadMode(var mode, var newInstance) ->
                    ScndGenLegends.get().loadMode(mode, newInstance);
            case GameCommand.TogglePause() -> {
                Mode active = ScndGenLegends.get().getMode();
                if (active != null) {
                    active.onTogglePause();
                }
            }
        }
    }

    private static void selectCharacter(CharacterEnum character, PlayerType slot) {
        var selection = RenderCharacterSelection.get();
        switch (character) {
            case RAILA -> selection.selRaila(slot);
            case SUBIYA -> selection.selSubiya(slot);
            case LYNX -> selection.selLynx(slot);
            case AISHA -> selection.selAisha(slot);
            case ADE -> selection.selAde(slot);
            case RAVAGE -> selection.selRav(slot);
            case JONAH -> selection.selJon(slot);
            case ADAM -> selection.selAdam(slot);
            case NOVA_ADAM -> selection.selNOVAAdam(slot);
            case AZARIA -> selection.selAza(slot);
            case SORROWE -> selection.selSorr(slot);
            case THING -> selection.selThing(slot);
        }
    }
}

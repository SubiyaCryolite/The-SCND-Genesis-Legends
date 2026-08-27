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

import com.scndgen.legends.constants.NetworkConstants;
import com.scndgen.legends.enums.CharacterEnum;
import com.scndgen.legends.enums.PlayerType;
import com.scndgen.legends.enums.Stage;

import java.util.ArrayList;
import java.util.Optional;

import static com.scndgen.legends.constants.NetworkConstants.*;

/**
 * Encodes/decodes the legacy UTF string protocol at the network edge.
 */
public final class GameCommandCodec {

    private GameCommandCodec() {
    }

    public static Optional<String> encode(GameCommand command) {
        return switch (command) {
            case GameCommand.SelectCharacter(var character, var slot) when slot == PlayerType.PLAYER1 ->
                    Optional.of(encodeCharacter(character));
            case GameCommand.DeselectOpponentSlot() -> Optional.of(DESELECT_OPPONENT);
            case GameCommand.SelectStage(var stage) -> Optional.of(stage.shortCode());
            case GameCommand.StartMatch() -> Optional.of(GAME_START);
            case GameCommand.GoToStageSelect() -> Optional.of(TO_STAGE_SELECT);
            case GameCommand.GoToCharacterSelect(var newInstance) -> Optional.of(
                    newInstance ? TO_CHARACTER_SELECT_NEW_MATCH : TO_CHARACTER_SELECT_CHANGE_SELECTION);
            case GameCommand.AttackQueue(var attacker, var moves) when attacker == PlayerType.PLAYER1 ->
                    Optional.of(encodeAttack(moves));
            case GameCommand.TriggerFury(var player) when player == PlayerType.PLAYER1 ->
                    Optional.of(FURY_ATTACK);
            case GameCommand.HostTimeLimit(var seconds) ->
                    Optional.of(NetworkConstants.connectToHost(seconds));
            case GameCommand.ConnectToHost() -> Optional.of(CONNECT_TO_HOST);
            case GameCommand.DisconnectFromHost() -> Optional.of(DISCONNECT_FROM_HOST);
            case GameCommand.CancelConnectivity() -> Optional.of(CANCEL_CONNECTIVITY);
            default -> Optional.empty();
        };
    }

    public static Optional<GameCommand> decode(String line) {
        if (line == null || line.isEmpty()) {
            return Optional.empty();
        }
        if (line.endsWith(ATTACK_POSTFIX)) {
            var attackArray = line.split(":");
            var moves = new ArrayList<Integer>(attackArray.length);
            for (var i = 0; i < attackArray.length - 1; i++) {
                if (!attackArray[i].isEmpty()) {
                    moves.add(Integer.parseInt(attackArray[i]));
                }
            }
            return Optional.of(new GameCommand.AttackQueue(PlayerType.PLAYER2, moves));
        }
        if (line.startsWith(HOST_TIME_CONSTANT)) {
            var seconds = Integer.parseInt(line.replace(HOST_TIME_CONSTANT, ""));
            return Optional.of(new GameCommand.HostTimeLimit(seconds));
        }
        return switch (line) {
            case TO_CHARACTER_SELECT_CHANGE_SELECTION ->
                    Optional.of(new GameCommand.GoToCharacterSelect(false));
            case TO_CHARACTER_SELECT_NEW_MATCH ->
                    Optional.of(new GameCommand.GoToCharacterSelect(true));
            case CANCEL_CONNECTIVITY -> Optional.of(new GameCommand.CancelConnectivity());
            case DESELECT_OPPONENT -> Optional.of(new GameCommand.DeselectOpponentSlot());
            case SEL_RAILA -> Optional.of(new GameCommand.SelectCharacter(CharacterEnum.RAILA, PlayerType.PLAYER2));
            case SEL_SUBIYA -> Optional.of(new GameCommand.SelectCharacter(CharacterEnum.SUBIYA, PlayerType.PLAYER2));
            case SEL_LYNX -> Optional.of(new GameCommand.SelectCharacter(CharacterEnum.LYNX, PlayerType.PLAYER2));
            case SEL_ALEX -> Optional.of(new GameCommand.SelectCharacter(CharacterEnum.AISHA, PlayerType.PLAYER2));
            case SEL_ADE -> Optional.of(new GameCommand.SelectCharacter(CharacterEnum.ADE, PlayerType.PLAYER2));
            case SEL_RAVAGE -> Optional.of(new GameCommand.SelectCharacter(CharacterEnum.RAVAGE, PlayerType.PLAYER2));
            case SEL_JOHN -> Optional.of(new GameCommand.SelectCharacter(CharacterEnum.JONAH, PlayerType.PLAYER2));
            case SEL_ADAM -> Optional.of(new GameCommand.SelectCharacter(CharacterEnum.ADAM, PlayerType.PLAYER2));
            case SEL_NOVA_ADAM ->
                    Optional.of(new GameCommand.SelectCharacter(CharacterEnum.NOVA_ADAM, PlayerType.PLAYER2));
            case SEL_AZARIA -> Optional.of(new GameCommand.SelectCharacter(CharacterEnum.AZARIA, PlayerType.PLAYER2));
            case SEL_SORROWE ->
                    Optional.of(new GameCommand.SelectCharacter(CharacterEnum.SORROWE, PlayerType.PLAYER2));
            case SEL_THING -> Optional.of(new GameCommand.SelectCharacter(CharacterEnum.THING, PlayerType.PLAYER2));
            case TO_STAGE_SELECT -> Optional.of(new GameCommand.GoToStageSelect());
            case GAME_START -> Optional.of(new GameCommand.StartMatch());
            case FURY_ATTACK -> Optional.of(new GameCommand.TriggerFury(PlayerType.PLAYER2));
            case CONNECT_TO_HOST -> Optional.of(new GameCommand.ConnectToHost());
            case DISCONNECT_FROM_HOST -> Optional.of(new GameCommand.DisconnectFromHost());
            default -> decodeStage(line);
        };
    }

    private static Optional<GameCommand> decodeStage(String line) {
        for (var stage : Stage.values()) {
            if (stage.shortCode().equals(line)) {
                return Optional.of(new GameCommand.SelectStage(stage));
            }
        }
        return Optional.empty();
    }

    private static String encodeCharacter(CharacterEnum character) {
        return switch (character) {
            case RAILA -> SEL_RAILA;
            case SUBIYA -> SEL_SUBIYA;
            case LYNX -> SEL_LYNX;
            case AISHA -> SEL_ALEX;
            case ADE -> SEL_ADE;
            case RAVAGE -> SEL_RAVAGE;
            case JONAH -> SEL_JOHN;
            case ADAM -> SEL_ADAM;
            case NOVA_ADAM -> SEL_NOVA_ADAM;
            case AZARIA -> SEL_AZARIA;
            case SORROWE -> SEL_SORROWE;
            case THING -> SEL_THING;
        };
    }

    private static String encodeAttack(java.util.List<Integer> moves) {
        var builder = new StringBuilder();
        for (var move : moves) {
            builder.append(move).append(':');
        }
        builder.append(ATTACK_POSTFIX);
        return builder.toString();
    }
}

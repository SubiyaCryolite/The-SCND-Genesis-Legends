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

import com.scndgen.legends.enums.CharacterEnum;
import com.scndgen.legends.enums.ModeEnum;
import com.scndgen.legends.enums.PlayerType;
import com.scndgen.legends.enums.Stage;

import java.util.List;

/**
 * Discriminated game intents applied on the GLFW/game thread.
 * Wire protocol strings are a codec concern; modes speak {@link GameCommand} only.
 */
public sealed interface GameCommand {

    record SelectCharacter(CharacterEnum character, PlayerType slot) implements GameCommand {
    }

    record DeselectOpponentSlot() implements GameCommand {
    }

    record SelectStage(Stage stage) implements GameCommand {
    }

    record StartMatch() implements GameCommand {
    }

    record GoToStageSelect() implements GameCommand {
    }

    record GoToCharacterSelect(boolean newInstance) implements GameCommand {
    }

    record AttackQueue(PlayerType attacker, List<Integer> moves) implements GameCommand {
    }

    record TriggerFury(PlayerType player) implements GameCommand {
    }

    record HostTimeLimit(int seconds) implements GameCommand {
    }

    record ConnectToHost() implements GameCommand {
    }

    record DisconnectFromHost() implements GameCommand {
    }

    record CancelConnectivity() implements GameCommand {
    }

    record SessionError(String detail) implements GameCommand {
    }

    record LoadMode(ModeEnum mode, boolean newInstance) implements GameCommand {
    }

    record TogglePause() implements GameCommand {
    }
}

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
package com.scndgen.legends.command;

import com.scndgen.legends.network.NetworkManager;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Single game-thread command queue for offline and online play.
 * <p>
 * Local input and network decode both {@link #offer} here; {@link #drainAndApply()}
 * runs on the GLFW thread before {@code mode.tick}.
 */
public final class GameCommandBus {

    private static final GameCommandBus INSTANCE = new GameCommandBus();

    private final ConcurrentLinkedQueue<GameCommand> queue = new ConcurrentLinkedQueue<>();

    private GameCommandBus() {
    }

    public static GameCommandBus get() {
        return INSTANCE;
    }

    /**
     * Enqueue a command produced locally. Optionally mirrors it on the wire.
     */
    public void dispatch(GameCommand command, boolean broadcast) {
        if (broadcast) {
            publish(command);
        }
        offer(command);
    }

    public void dispatch(GameCommand command) {
        dispatch(command, shouldBroadcast(command));
    }

    /**
     * Send a command on the wire without applying it locally (already applied).
     */
    public void publish(GameCommand command) {
        GameCommandCodec.encode(command).ifPresent(NetworkManager.get()::send);
    }

    public void offer(GameCommand command) {
        if (command != null) {
            queue.offer(command);
        }
    }

    public void offerEncoded(String line) {
        GameCommandCodec.decode(line).ifPresent(this::offer);
    }

    public void drainAndApply() {
        GameCommand command;
        while ((command = queue.poll()) != null) {
            GameCommandApplier.apply(command);
        }
    }

    private static boolean shouldBroadcast(GameCommand command) {
        if (NetworkManager.get().isOffline()) {
            return false;
        }
        return GameCommandCodec.encode(command).isPresent();
    }
}

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
package com.scndgen.legends.mode.gameplay;

import io.github.subiyacryolite.enginev2.Accumulator;

/**
 * Match / career play-time counters advanced at 1 Hz on the GLFW thread.
 */
public final class MatchPlayClock {
    private final Accumulator tick = Accumulator.atFrequency(1);
    private boolean tracking;
    private int playTimeCounter;
    private int matchDuration;

    public void start(int existingPlayTimeSeconds) {
        playTimeCounter = existingPlayTimeSeconds;
        matchDuration = 0;
        tick.reset();
        tracking = true;
    }

    public void stop() {
        tracking = false;
    }

    public void reset() {
        tracking = false;
        matchDuration = 0;
        tick.reset();
    }

    /**
     * @return {@code true} if tracking stopped because {@code gameOver} became true this tick cycle
     */
    public void tick(double deltaSeconds, boolean gameOver) {
        if (!tracking) {
            return;
        }
        tick.advance(deltaSeconds);
        while (tick.consume()) {
            playTimeCounter++;
            matchDuration++;
            if (gameOver) {
                tracking = false;
                break;
            }
        }
    }

    public boolean isTracking() {
        return tracking;
    }

    public int playTimeCounter() {
        return playTimeCounter;
    }

    public int matchDuration() {
        return matchDuration;
    }
}

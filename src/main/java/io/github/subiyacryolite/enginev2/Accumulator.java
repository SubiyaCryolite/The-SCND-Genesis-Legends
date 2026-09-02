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
package io.github.subiyacryolite.enginev2;

/**
 * Fixed-timestep scheduler for the GLFW main loop.
 * Feed each frame's {@code glfwGetTime()} delta into {@link #advance(double)},
 * then {@code while (consume())} run one discrete tick.
 */
public final class Accumulator {

    private static final double MAX_DELTA_SECONDS = 0.25;

    private double intervalSeconds;
    private double accumulatedSeconds;
    private double lastDeltaSeconds;
    private long ticksConsumed;
    private int maxCatchUpTicks = 8;

    public Accumulator() {
    }

    public static Accumulator atFrequency(double ticksPerSecond) {
        var accumulator = new Accumulator();
        accumulator.setFrequency(ticksPerSecond);
        return accumulator;
    }

    public static Accumulator atInterval(double seconds) {
        var accumulator = new Accumulator();
        accumulator.setInterval(seconds);
        return accumulator;
    }

    public void advance(double deltaSeconds) {
        double clampedDelta = deltaSeconds;
        if (clampedDelta < 0.0) {
            clampedDelta = 0.0;
        } else if (clampedDelta > MAX_DELTA_SECONDS) {
            clampedDelta = MAX_DELTA_SECONDS;
        }

        lastDeltaSeconds = clampedDelta;
        if (intervalSeconds <= 0.0) {
            return;
        }

        accumulatedSeconds += clampedDelta;
        double maxAccumulated = intervalSeconds * maxCatchUpTicks;
        if (accumulatedSeconds > maxAccumulated) {
            accumulatedSeconds = maxAccumulated;
        }
    }

    public boolean consume() {
        if (intervalSeconds <= 0.0 || accumulatedSeconds < intervalSeconds) {
            return false;
        }
        accumulatedSeconds -= intervalSeconds;
        ticksConsumed++;
        return true;
    }

    public void setFrequency(double ticksPerSecond) {
        if (ticksPerSecond <= 0.0) {
            intervalSeconds = 0.0;
            return;
        }
        intervalSeconds = 1.0 / ticksPerSecond;
    }

    public void setInterval(double seconds) {
        if (seconds <= 0.0) {
            intervalSeconds = 0.0;
            return;
        }
        intervalSeconds = seconds;
    }

    public void setMaxCatchUpTicks(int ticks) {
        maxCatchUpTicks = Math.max(1, ticks);
    }

    public void reset() {
        accumulatedSeconds = 0.0;
        lastDeltaSeconds = 0.0;
    }

    public double lastDeltaSeconds() {
        return lastDeltaSeconds;
    }

    public double intervalSeconds() {
        return intervalSeconds;
    }

    public long ticksConsumed() {
        return ticksConsumed;
    }
}

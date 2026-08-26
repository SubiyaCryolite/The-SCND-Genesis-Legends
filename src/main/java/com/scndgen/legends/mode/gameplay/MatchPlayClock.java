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

package com.scndgen.legends.mode.gameplay;

import io.github.subiyacryolite.enginev2.Accumulator;

/**
 * Fury meter filled/emptied on fixed 60 Hz ticks (replaces per-increment Threads).
 */
public final class FuryBar {
    public static final int MAX = 1000;

    private final Accumulator tick = Accumulator.atFrequency(60);
    private int level;
    private int pending;

    public void reset() {
        level = 0;
        pending = 0;
        tick.reset();
    }

    public void queue(int delta) {
        pending += delta;
    }

    public void tick(double deltaSeconds) {
        tick.advance(deltaSeconds);
        while (tick.consume()) {
            if (pending > 0 && level < MAX) {
                level++;
                pending--;
            } else if (pending < 0) {
                if (level > 0) {
                    level--;
                }
                pending++;
            } else {
                break;
            }
        }
    }

    public int level() {
        return level;
    }

    public void setLevel(int level) {
        this.level = Math.max(0, Math.min(MAX, level));
    }

    public boolean isFull() {
        return level >= MAX;
    }

    public float percent() {
        return level / (float) MAX;
    }
}

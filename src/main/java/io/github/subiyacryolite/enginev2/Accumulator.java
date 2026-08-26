package io.github.subiyacryolite.enginev2;

/**
 * Fixed-timestep scheduler for the GLFW main loop.
 * Feed each frame's {@code glfwGetTime()} delta into {@link #advance(double)},
 * then {@code while (consume())} run one discrete tick.
 */
public final class Accumulator {

    private static final double MAX_DELTA_SECONDS = 0.25;
    private static final int MAX_CATCH_UP_TICKS = 8;

    private double intervalSeconds;
    private double accumulatedSeconds;
    private double lastDeltaSeconds;
    private long ticksConsumed;

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
        double maxAccumulated = intervalSeconds * MAX_CATCH_UP_TICKS;
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

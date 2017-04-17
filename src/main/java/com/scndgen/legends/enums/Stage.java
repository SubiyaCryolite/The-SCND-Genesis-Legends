package com.scndgen.legends.enums;

/**
 * Created by ifunga on 15/04/2017.
 */
public enum Stage {
    IBEX_HILL("IBEX_HILL", "stage1_vgdt", 0),
    CHELSTON_CITY_DOCKS("CHELSTON_CITY_DOCKS", "stage2_vgdt", 1),
    DESERT_RUINS("DESERT_RUINS", "stage3_vgdt", 2),
    CHELSTON_CITY_STREETS("CHELSTON_CITY_STREETS", "stage4_vgdt", 3),
    IBEX_HILL_NIGHT("IBEX_HILL_NIGHT", "stage5_vgdt", 4),
    SCORCHED_RUINS("SCORCHED_RUINS", "stage6_vgdt", 5),
    FROZEN_WILDERNESS("FROZEN_WILDERNESS", "stage7_vgdt", 6),
    DISTANT_ISLE("DISTANT_ISLE", "stage100_vgdt", 7),
    HIDDEN_CAVE("HIDDEN_CAVE", "stage8_vgdt", 8),
    AFRICAN_VILLAGE("AFRICAN_VILLAGE", "stage9_vgdt", 9),
    APOCALYPTO("APOCALYPTO", "stage10_vgdt", 10),
    DISTANT_ISLE_NIGHT("DISTANT_ISLE_NIGHT", "stage11_vgdt", 11),
    DESERT_RUINS_NIGHT("DESERT_RUINS_NIGHT", "stage13_vgdt", 12),
    SCORCHED_RUINS_NIGHT("SCORCHED_RUINS_NIGHT", "stage14_vgdt", 13),
    HIDDEN_CAVE_NIGHT("HIDDEN_CAVE_NIGHT", "stage15_vgdt", 14),
    RANDOM("Ibex RANDOM Academy", "stage12_vgdt", 15);
    private final String name;
    private final String shortCode;
    private final int index;

    Stage(final String name, final String shortCode, final int index) {
        this.name = name;
        this.shortCode = shortCode;
        this.index = index;
    }

    public final String getName() {
        return name;
    }

    public final String getShortCode() {
        return shortCode;
    }

    public int getIndex() {
        return index;
    }

    public String toString() {
        return name;
    }
}

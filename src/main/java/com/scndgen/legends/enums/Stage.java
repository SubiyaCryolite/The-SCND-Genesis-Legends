package com.scndgen.legends.enums;

import com.scndgen.legends.constants.NetworkConstants;

/**
 * Created by ifunga on 15/04/2017.
 */
public enum Stage {
    IBEX_HILL("IBEX_HILL", NetworkConstants.STAGE_IBEX_HILL, "1", 0),
    CHELSTON_CITY_DOCKS("CHELSTON_CITY_DOCKS", NetworkConstants.STAGE_CHELSTON_CITY_DOCKS, "2", 1),
    DESERT_RUINS("DESERT_RUINS", NetworkConstants.STAGE_DESERT_RUINS, "3", 2),
    CHELSTON_CITY_STREETS("CHELSTON_CITY_STREETS", NetworkConstants.STAGE_CHELSTON_CITY_STREETS, "4", 3),
    IBEX_HILL_NIGHT("IBEX_HILL_NIGHT", NetworkConstants.STAGE_IBEX_HILL_NIGHT, "5", 4),
    SCORCHED_RUINS("SCORCHED_RUINS", NetworkConstants.STAGE_SCORCHED_RUINS, "6", 5),
    FROZEN_WILDERNESS("FROZEN_WILDERNESS", NetworkConstants.STAGE_FROZEN_WILDERNESS, "7", 6),
    DISTANT_ISLE("DISTANT_ISLE", NetworkConstants.STAGE_DISTANT_ISLE, "100", 7),
    HIDDEN_CAVE("HIDDEN_CAVE", NetworkConstants.STAGE_HIDDEN_CAVE, "8", 8),
    AFRICAN_VILLAGE("AFRICAN_VILLAGE", NetworkConstants.STAGE_AFRICAN_VILLAGE, "9", 9),
    APOCALYPTO("APOCALYPTO", NetworkConstants.STAGE_APOCALYPTO, "10", 10),
    DISTANT_ISLE_NIGHT("DISTANT_ISLE_NIGHT", NetworkConstants.STAGE_DISTANT_ISLE_NIGHT, "11", 11),
    DESERT_RUINS_NIGHT("DESERT_RUINS_NIGHT", NetworkConstants.STAGE_DESERT_RUINS_NIGHT, "13", 12),
    SCORCHED_RUINS_NIGHT("SCORCHED_RUINS_NIGHT", NetworkConstants.STAGE_SCORCHED_RUINS_NIGHT, "14", 13),
    HIDDEN_CAVE_NIGHT("HIDDEN_CAVE_NIGHT", NetworkConstants.STAGE_HIDDEN_CAVE_NIGHT, "15", 14),
    RANDOM("RANDOM", NetworkConstants.STAGE_RANDOM, "12", 15);// RANDOM SHOULD ALWAYS BE LAST!!!!!!!!!!!
    private final String name;
    private final String shortCode;
    private final String filePrefix;
    private final int index;

    Stage(final String name, final String shortCode, final String filePrefix, final int index) {
        this.name = name;
        this.shortCode = shortCode;
        this.index = index;
        this.filePrefix = filePrefix;
    }

    public final String getName() {
        return name;
    }

    public final String shortCode() {
        return shortCode;
    }

    public final String filePrefix() {
        return filePrefix;
    }

    public int index() {
        return index;
    }

    public String toString() {
        return name;
    }
}

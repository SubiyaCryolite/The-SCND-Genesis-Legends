package com.scndgen.legends.enums;

/**
 * Created by ifunga on 15/04/2017.
 */
public enum Stage {
    IBEX_HILL("Ibex Hill Academy", "stage4_vgdt", 1);
    private final String name;
    private final String shortCode;
    private final int index;

    Stage(final String name, final String shortCode, final int index) {
        this.name = name;
        this.shortCode = shortCode;
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public String getShortCode() {
        return shortCode;
    }

    public int getIndex() {
        return index;
    }

    public String toString() {
        return name;
    }
}

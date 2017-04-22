package com.scndgen.legends.enums;

/**
 * Created by ifung on 23/04/2017.
 */
public enum AchievementCategories {
    COOL(102), SWEET(198), EPIC(300);

    private final int points;

    AchievementCategories(final int points) {
        this.points = points;
    }

    public int points() {
        return points;
    }
}

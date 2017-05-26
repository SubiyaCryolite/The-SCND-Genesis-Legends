package com.scndgen.legends.enums;

/**
 * Created by ifunga on 22/04/2017.
 */
public enum Achievements {
    UPPER_HAND(0, AchievementCategories.COOL),
    BEAT_THE_ODDS(1, AchievementCategories.SWEET),
    OWNAGE(2, AchievementCategories.EPIC),
    HEARTLESS(3, AchievementCategories.SWEET),
    MEANIE(4, AchievementCategories.COOL),
    RAGE(5, AchievementCategories.COOL),
    WINNER(6, AchievementCategories.COOL),
    BUZZ_KILL(7, AchievementCategories.SWEET),
    CLOSE_CALL(8, AchievementCategories.COOL),
    ON_A_ROLL(9, AchievementCategories.EPIC),
    HALF_WAY_THROUGH(10, AchievementCategories.EPIC),
    Ach12(11, AchievementCategories.COOL);
    private final AchievementCategories achievementCategory;
    private final int id;

    Achievements(int id, AchievementCategories achievementCategory) {
        this.id = id;
        this.achievementCategory = achievementCategory;
    }

    public int id() {
        return id;
    }

    public AchievementCategories achievementCategory() {
        return achievementCategory;
    }
}

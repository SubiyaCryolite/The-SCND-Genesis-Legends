package com.scndgen.legends.constants;

/**
 * Created by ifunga on 30/04/2017.
 */
public class AudioConstants {
    public static final String[] MALE_HURT = {"audio/from_Ardentryst/male/attack/Pain4.ogg",
            "audio/from_Ardentryst/male/attack/death_16.ogg",
            "audio/from_Ardentryst/male/attack/Pain5.ogg",
            "audio/from_Ardentryst/male/attack/Pain7.ogg"};
    public static final String[] MALE_ATTACKS = {"audio/from_Ardentryst/male/pain/death_11.ogg",
            "audio/from_Ardentryst/male/pain/death_14.ogg",
            "audio/from_Ardentryst/male/pain/death_17.ogg",
            "audio/from_Ardentryst/male/pain/Pain2.ogg",
            "audio/from_Ardentryst/male/pain/Pain6.ogg",
            "audio/from_Ardentryst/male/pain/death_13.ogg",
            "audio/from_Ardentryst/male/pain/death_15.ogg",
            "audio/from_Ardentryst/male/pain/death_18.ogg",
            "audio/from_Ardentryst/male/pain/Pain3.ogg",
            "audio/from_Ardentryst/male/pain/Pain8.ogg"};
    public static final String[] FEMALE_ATTACKS = {"audio/from_Ardentryst/female/attack/NYX_JUMP1.ogg",
            "audio/from_Ardentryst/female/attack/NYX_JUMP2.ogg",
            "audio/from_Ardentryst/female/attack/NYX_JUMP3.ogg",
            "audio/from_Ardentryst/female/attack/NYX_JUMP4.ogg",
            "audio/from_Ardentryst/female/attack/NYX_JUMP5.ogg",
            "audio/from_Ardentryst/female/attack/NYX_JUMP6.ogg"};
    public static final String[] FEMALE_HURT = {"audio/from_Ardentryst/female/pain/NYX_PAIN4.ogg",
            "audio/from_Ardentryst/female/pain/NYX_PAIN5.ogg",
            "audio/from_Ardentryst/female/pain/NYX_PAIN6.ogg"};

    public static String maleAttack(int x) {
        return MALE_HURT[x];
    }

    public static String maleHurt(int x) {
        return MALE_ATTACKS[x];
    }

    public static String femaleAttack(int x) {
        return FEMALE_HURT[x];
    }

    public static String femaleHurt(int x) {
        return FEMALE_ATTACKS[x];
    }

    public static String tutorialSound() {
        return "audio/from_0AD/germanic_peace_1.ogg";
    }

    public static String soundBack() {
        return "audio/from_0AD/WeaponSwing.ogg";
    }

    public static String soundGameOver() {
        return "audio/from_0AD/gen_loss_track.ogg";
    }

    public static String soundNext() {
        return "audio/from_0AD/WeaponSwingHigh.ogg";
    }

    public static String furyAttck() {
        return "audio/hithard.ogg";
    }

    public static String playerAttack() {
        return "audio/hitlow.ogg";
    }

    public static String selectSound() {
        return "audio/menu-small-select.oga";
    }

    public static String charSelectSound() {
        return "audio/menu-back.oga";
    }

    public static String itemSound1() {
        return "audio/itembox_get.ogg";
    }

    public static String menuMus() {
        return "audio/scotty/Scotty Zepplin - Zulu Warrior.ogg";
    }

    public static String storySound() {
        return "audio/Ryan Reilly - Suspense.ogg";
    }
}

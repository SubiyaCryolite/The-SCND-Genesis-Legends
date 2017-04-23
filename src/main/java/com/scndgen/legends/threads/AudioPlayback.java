/*************************************************************************
 *  Compilation:  javac -classpath .:jl1.0.jar MP3.java         (OS X)
 *                javac -classpath .;jl1.0.jar MP3.java         (Windows)
 *  Execution:    java -classpath .:jl1.0.jar MP3 filename.ogg  (OS X / Linux)
 *                java -classpath .;jl1.0.jar MP3 filename.ogg  (Windows)
 *
 *  Plays an MP3 file using the JLayer MP3 library.
 *
 *  Reference:  http://www.javazoom.net/javalayer/sources.html
 *
 *
 *  To execute, get the file jl1.0.jar from the website above or from
 *
 *      http://www.cs.princeton.edu/introcs/24inout/jl1.0.jar
 *
 *  and put it in your working directory with this file MP3.java.
 *  small edits by Ifunga Ndana
 *
 *************************************************************************/
package com.scndgen.legends.threads;

import javazoom.jl.player.Player;

public class AudioPlayback implements Runnable {

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
    private String fileName;
    private Player player;
    private final Thread thread;

    public AudioPlayback() {
        //dummy, grant access to methods
        // run in new thread to play in background
        thread = new Thread(this);
        thread.setPriority(1);
    }

    public AudioPlayback(String filename, boolean loop) {
        this();
        try {
            fileName = filename;
        } catch (Exception e) {
            //e.printStackTrace(System.err);
        }
    }

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

    public static String enemyAttck() {
        return "audio/hitlow.ogg";
    }

    public static String furyAttck() {
        return "audio/hithard.ogg";
    }

    public static String playerAttack() {
        return "audio/hitlow.ogg";
    }

    public static String selectSound() {
        return "audio/menu-small-select.ogg";
    }

    public static String charSelectSound() {
        return "audio/menu-back.ogg";
    }

    public static String itemSound1() {
        return "audio/itembox_get.ogg";
    }

    public static String itemSound2() {
        return "audio/itembox_get.ogg";
    }

    public static String startUpSound() {
        return "audio/Ryan Reilly - Victory.ogg";
    }

    public static String flameSwoosh() {
        return "audio/flame_whoosh.ogg";
    }

    public static String menuMus() {
        return "audio/Doug Kaufman - The City Falls.ogg";
    }

    public static String winSound() {
        return "audio/Ryan Reilly - Victory.ogg";
    }

    public static String loseSound() {
        return "audio/Timothy Pinkham - Defeat.ogg";
    }

    public static String storySound() {
        return "audio/Ryan Reilly - Suspense.ogg";
    }

    public void close() {
        if (thread != null) {
            thread.stop();
        }
    }

    public boolean isPlaying() {
        boolean dude = false;
        if (player != null) {
            dude = true;
        }
        return dude;
    }

    // play the MP3 file to the sound card
    public void play() {
//        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName)) {
//            if (GameState.getInstance().getLogin().isAudioOn()) {
//                player = new Player(inputStream);
//                thread.setName("Music Thread");
//                thread.start();
//            }
//        } catch (Exception e) {
//            System.out.println("Problem playing file " + fileName);
//            System.out.println(e);
//        }
    }

    public void volume() {
    }

    @Override
    public void run() {
        try {
            //player.play();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    public void pause() {
        try {
            thread.suspend();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    public void stop() {
        try {
            thread.stop();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    public void resume() {
        try {
            thread.resume();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }
}

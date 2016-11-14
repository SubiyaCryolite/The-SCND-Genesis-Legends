/*************************************************************************
 *  Compilation:  javac -classpath .:jl1.0.jar MP3.java         (OS X)
 *                javac -classpath .;jl1.0.jar MP3.java         (Windows)
 *  Execution:    java -classpath .:jl1.0.jar MP3 filename.mp3  (OS X / Linux)
 *                java -classpath .;jl1.0.jar MP3 filename.mp3  (Windows)
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
package com.scnd_genesis.threads;

import com.scnd_genesis.LoginScreen;
import javazoom.jl.player.Player;

import java.io.InputStream;

public class ThreadMP3 implements Runnable {

    public static String[] maleHurt = {"audio/from_Ardentryst/male/attack/Pain4.mp3",
            "audio/from_Ardentryst/male/attack/death_16.mp3",
            "audio/from_Ardentryst/male/attack/Pain5.mp3",
            "audio/from_Ardentryst/male/attack/Pain7.mp3"};
    public static String[] maleAttacks = {"audio/from_Ardentryst/male/pain/death_11.mp3",
            "audio/from_Ardentryst/male/pain/death_14.mp3",
            "audio/from_Ardentryst/male/pain/death_17.mp3",
            "audio/from_Ardentryst/male/pain/Pain2.mp3",
            "audio/from_Ardentryst/male/pain/Pain6.mp3",
            "audio/from_Ardentryst/male/pain/death_13.mp3",
            "audio/from_Ardentryst/male/pain/death_15.mp3",
            "audio/from_Ardentryst/male/pain/death_18.mp3",
            "audio/from_Ardentryst/male/pain/Pain3.mp3",
            "audio/from_Ardentryst/male/pain/Pain8.mp3"};
    public static String[] femaleAttacks = {"audio/from_Ardentryst/female/attack/NYX_JUMP1.mp3",
            "audio/from_Ardentryst/female/attack/NYX_JUMP2.mp3",
            "audio/from_Ardentryst/female/attack/NYX_JUMP3.mp3",
            "audio/from_Ardentryst/female/attack/NYX_JUMP4.mp3",
            "audio/from_Ardentryst/female/attack/NYX_JUMP5.mp3",
            "audio/from_Ardentryst/female/attack/NYX_JUMP6.mp3"};
    public static String[] femaleHurt = {"audio/from_Ardentryst/female/pain/NYX_PAIN4.mp3",
            "audio/from_Ardentryst/female/pain/NYX_PAIN5.mp3",
            "audio/from_Ardentryst/female/pain/NYX_PAIN6.mp3"};
    private String filenameM;
    private ClassLoader classloader = getClass().getClassLoader();
    private Player player;
    private Thread t;
    private InputStream fin;

    public ThreadMP3() {
        //dummy, grant access to methods
    }

    public ThreadMP3(String filename, boolean loop) {
        try {
            filenameM = filename;
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    public static String maleAttack(int x) {
        return maleHurt[x];
    }

    public static String maleHurt(int x) {
        return maleAttacks[x];
    }

    public static String femaleAttack(int x) {
        return femaleHurt[x];
    }

    public static String femaleHurt(int x) {
        return femaleAttacks[x];
    }

    public static String tutorialSound() {
        return "audio/from_0AD/germanic_peace_1.mp3";
    }

    public static String soundBack() {
        return "audio/from_0AD/WeaponSwing.mp3";
    }

    public static String soundGameOver() {
        return "audio/from_0AD/gen_loss_track.mp3";
    }

    public static String soundNext() {
        return "audio/from_0AD/WeaponSwingHigh.mp3";
    }

    public static String enemyAttck() {
        return "audio/hitlow.mp3";
    }

    public static String furyAttck() {
        return "audio/hithard.mp3";
    }

    public static String playerAttack() {
        return "audio/hitlow.mp3";
    }

    public static String selectSound() {
        return "audio/menu-small-select.mp3";
    }

    public static String charSelectSound() {
        return "audio/menu-back.mp3";
    }

    public static String itemSound1() {
        return "audio/itembox_get.mp3";
    }

    public static String itemSound2() {
        return "audio/itembox_get.mp3";
    }

    public static String startUpSound() {
        return "audio/Ryan Reilly - Victory.mp3";
    }

    public static String flameSwoosh() {
        return "audio/flame_whoosh.mp3";
    }

    public static String menuMus() {
        return "audio/Doug Kaufman - The City Falls.mp3";
    }

    public static String winSound() {
        return "audio/Ryan Reilly - Victory.mp3";
    }

    public static String loseSound() {
        return "audio/Timothy Pinkham - Defeat.mp3";
    }

    public static String storySound() {
        return "audio/Ryan Reilly - Suspense.mp3";
    }

    public void close() {
        if (t != null) {
            t.stop();
            t = null;
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
        try {
            if (LoginScreen.getLoginScreen().soundStatus.equalsIgnoreCase("on")) {

                classloader = getClass().getClassLoader();
                fin = classloader.getResourceAsStream(filenameM);
                player = new Player(fin);

                // run in new thread to play in background
                t = new Thread(this);
                t.setPriority(1);
                t.setName("Music Thread");
                t.start();
            }
        } catch (Exception e) {
            System.out.println("Problem playing file " + filenameM);
            System.out.println(e);
        }
    }

    public void volume() {
    }

    @Override
    public void run() {
        try {
            player.play();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public void pause() {
        try {
            t.suspend();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public void stop() {
        try {
            t.stop();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public void resume() {
        try {
            t.resume();
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}

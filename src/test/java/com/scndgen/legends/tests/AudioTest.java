package com.scndgen.legends.tests;

import io.github.subiyacryolite.enginev1.AudioFilePlayer;
import org.junit.Test;

import java.io.InputStream;

/**
 * Created by ifung on 23/04/2017.
 */

public class AudioTest {
    @Test
    public void test() {
        try {
            AudioFilePlayer audioFilePlayer = new AudioFilePlayer();
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("audio/Doug Kaufman - Elvish theme.ogg");
            audioFilePlayer.play(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

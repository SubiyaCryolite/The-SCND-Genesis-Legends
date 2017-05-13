package com.scndgen.legends.tests;

import com.scndgen.legends.enums.AudioType;
import io.github.subiyacryolite.enginev1.AudioPlayback;
import org.junit.Test;

/**
 * Created by ifung on 23/04/2017.
 */

public class Player {

    @Test
    public void run() {
        AudioPlayback ap = new AudioPlayback("audio/Aleksi Aubry-Carlson - Battle Music.ogg", AudioType.MUSIC, false);
        ap.play();
    }
}

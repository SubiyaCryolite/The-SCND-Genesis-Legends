package com.scndgen.legends.tests;

import org.junit.Test;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by ifung on 23/04/2017.
 */

public class AudioTest {
    @Test
    public void test() {
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("audio/Aleksi Aubry-Carlson - Battle Music.ogg"); AudioInputStream in = AudioSystem.getAudioInputStream(inputStream)) {
            if (in != null) {
                AudioFormat baseFormat = in.getFormat();
                AudioFormat targetFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), 16, baseFormat.getChannels(), baseFormat.getChannels() * 2, baseFormat.getSampleRate(), false);
                AudioInputStream dataIn = AudioSystem.getAudioInputStream(targetFormat, in);
                byte[] buffer = new byte[4096];
                DataLine.Info info = new DataLine.Info(SourceDataLine.class, targetFormat);
                SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
                if (line != null) {
                    line.open();
                    line.start();
                    int nBytesRead = 0, nBytesWritten = 0;
                    while (nBytesRead != -1) {
                        nBytesRead = dataIn.read(buffer, 0, buffer.length);
                        if (nBytesRead != -1) {
                            nBytesWritten = line.write(buffer, 0, nBytesRead);
                        }
                    }
                    line.drain();
                    line.stop();
                    line.close();
                    dataIn.close();
                }
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace(System.err);
        }
    }
}

package io.github.subiyacryolite.enginev1;

import javazoom.spi.vorbis.sampled.file.VorbisAudioFileReader;

import javax.sound.sampled.*;
import javax.sound.sampled.DataLine.Info;
import java.io.IOException;
import java.io.InputStream;

import static javax.sound.sampled.AudioFormat.Encoding.PCM_SIGNED;

public class AudioFilePlayer {
    public void play(InputStream filePath) {
        try (final AudioInputStream in = createOggMp3(filePath)) {
            final AudioFormat outFormat = getOutFormat(in.getFormat());
            final Info info = new Info(SourceDataLine.class, outFormat);

            try (final SourceDataLine line =
                         (SourceDataLine) AudioSystem.getLine(info)) {

                if (line != null) {
                    line.open(outFormat);
                    line.start();
                    AudioInputStream inputMystream = AudioSystem.getAudioInputStream(outFormat, in);
                    stream(inputMystream, line);
                    line.drain();
                    line.stop();
                }
            }

        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private AudioFormat getOutFormat(AudioFormat inFormat) {
        final int ch = inFormat.getChannels();
        final float rate = inFormat.getSampleRate();
        return new AudioFormat(PCM_SIGNED, rate, 16, ch, ch * 2, rate, false);
    }

    private void stream(AudioInputStream in, SourceDataLine line)
            throws IOException {
        final byte[] buffer = new byte[4096];
        for (int n = 0; n != -1; n = in.read(buffer, 0, buffer.length)) {
            line.write(buffer, 0, n);
        }
    }

    private AudioInputStream createOggMp3(InputStream fileIn) throws IOException, Exception {
        AudioInputStream audioInputStream = null;
        AudioFormat targetFormat = null;
        try {
            AudioInputStream in = null;
            VorbisAudioFileReader vb = new VorbisAudioFileReader();
            in = vb.getAudioInputStream(fileIn);
            AudioFormat baseFormat = in.getFormat();
            targetFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    baseFormat.getSampleRate(),
                    16,
                    baseFormat.getChannels(),
                    baseFormat.getChannels() * 2,
                    baseFormat.getSampleRate(),
                    false);
            audioInputStream = AudioSystem.getAudioInputStream(targetFormat, in);
        } catch (UnsupportedAudioFileException ue) {
            System.out.println("\nUnsupported Audio");
        }
        return audioInputStream;
    }
}
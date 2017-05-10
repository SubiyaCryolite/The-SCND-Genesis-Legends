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


import com.scndgen.legends.enums.AudioType;
import com.scndgen.legends.state.GameState;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

public class AudioPlayback implements Runnable {

    private String fileName;
    private boolean playing;
    private boolean paused;
    private boolean looping;
    private Thread thread;
    private static final Set<AudioPlayback> heap = new HashSet<>();
    private AudioType audioType;
    private float volume;

    public AudioPlayback(String filename, AudioType audioType, boolean loop) {
        this.fileName = filename;
        this.looping = loop;
        this.audioType = audioType;
        heap.add(this);
        switch (audioType) {
            case MUSIC:
                setVolume(GameState.getInstance().getLogin().getMusicVolume());
                break;
            case VOICE:
                setVolume(GameState.getInstance().getLogin().getVoiceVolume());
                break;
            case SOUND:
                setVolume(GameState.getInstance().getLogin().getSoundVolume());
                break;
        }
    }


    public boolean isPlaying() {
        return thread.isAlive() || playing;
    }

    // play the MP3 file to the sound card
    public void play() {
        if (thread != null && (playing || paused))
            thread.stop();
        thread = new Thread(this);
        thread.setDaemon(true);
        thread.setName("Audio Playback - " + this.fileName);
        thread.start();
    }

    public static void volume(AudioType audioType, float volume) {
        heap.stream().forEach(elem -> {
            if (elem.getAudioType() == audioType) {
                elem.setVolume(volume);
            }
        });
    }

    @Override
    public void run() {
        playAudio();
    }

    public void togglePause() {
        paused = !paused;
        if(thread==null)return;
        if (!paused)
            thread.suspend();
        else
            thread.resume();
    }

    public void stop() {
        if(thread==null)return;
        if (thread.isAlive())
            thread.stop();
        heap.remove(this);
    }

    private void setVolume(float volume) {
        this.volume = volume;
        if (playing || paused) {
            //float control here
        }
    }

    private void playAudio() {
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName); AudioInputStream in = AudioSystem.getAudioInputStream(inputStream)) {
            if (in != null) {
                AudioFormat baseFormat = in.getFormat();
                AudioFormat targetFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), 16, baseFormat.getChannels(), baseFormat.getChannels() * 2, baseFormat.getSampleRate(), false);
                AudioInputStream dataIn = AudioSystem.getAudioInputStream(targetFormat, in);
                byte[] buffer = new byte[4096];
                DataLine.Info info = new DataLine.Info(SourceDataLine.class, targetFormat);
                SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
                if (line != null) {
                    playing = true;
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
            playing = false;

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace(System.err);
        }
        if (looping)
            playAudio();//call again
    }

    public AudioType getAudioType() {
        return audioType;
    }
}

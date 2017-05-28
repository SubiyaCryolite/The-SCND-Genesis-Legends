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
package io.github.subiyacryolite.enginev1;


import com.scndgen.legends.enums.AudioType;
import com.scndgen.legends.state.GameState;
import io.github.subiyacryolite.enginev1.ogg.OggData;
import io.github.subiyacryolite.enginev1.ogg.OggDecoder;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.openal.ALUtil;
import org.lwjgl.system.MemoryStack;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.openal.ALC10.*;
import static org.lwjgl.openal.ALC11.*;
import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.system.libc.LibCStdlib.free;

public class AudioPlayback implements Runnable {

    private String fileName;
    private boolean paused;
    private boolean looping;
    private boolean playingAudio;
    private Thread thread;
    private static final Set<AudioPlayback> heap = new HashSet<>();
    private AudioType audioType;
    private float volume;
    //========================================
    private int bufferNames;
    private int srcNames;
    private OggData ogg;
    //========================================
    private static long audioDevice;
    private static long audioContext;

    static {
        audioDevice = alcOpenDevice((ByteBuffer) null);
        if (audioDevice == NULL)
            throw new IllegalStateException("Failed to open the default device.");
        ALCCapabilities deviceCaps = ALC.createCapabilities(audioDevice);
        System.out.println("OpenALC10: " + deviceCaps.OpenALC10);
        System.out.println("OpenALC11: " + deviceCaps.OpenALC11);
        System.out.println("caps.ALC_EXT_EFX = " + deviceCaps.ALC_EXT_EFX);
        if (deviceCaps.OpenALC11) {
            List<String> devices = ALUtil.getStringList(NULL, ALC_ALL_DEVICES_SPECIFIER);
            if (devices == null)
                throw new IllegalStateException("Failed to open the default device.");
            else {
                for (int i = 0; i < devices.size(); i++)
                    System.out.println(i + ": " + devices.get(i));
            }
        }
        String defaultDeviceSpecifier = alcGetString(NULL, ALC_DEFAULT_DEVICE_SPECIFIER);
        System.out.println("Default device: " + defaultDeviceSpecifier);
        audioContext = alcCreateContext(audioDevice, (IntBuffer) null);
        alcMakeContextCurrent(audioContext);
        AL.createCapabilities(deviceCaps);
        System.out.println("ALC_FREQUENCY: " + alcGetInteger(audioDevice, ALC_FREQUENCY) + "Hz");
        System.out.println("ALC_REFRESH: " + alcGetInteger(audioDevice, ALC_REFRESH) + "Hz");
        System.out.println("ALC_SYNC: " + (alcGetInteger(audioDevice, ALC_SYNC) == ALC_TRUE));
        System.out.println("ALC_MONO_SOURCES: " + alcGetInteger(audioDevice, ALC_MONO_SOURCES));
        System.out.println("ALC_STEREO_SOURCES: " + alcGetInteger(audioDevice, ALC_STEREO_SOURCES));
    }

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

    private void checkForError() throws Exception {
        int result;
        if ((result = alGetError()) != AL_NO_ERROR) {
            throw new Exception(alErrorString(result));
        }
    }

    private String alErrorString(int err) {
        switch (err) {
            case AL_NO_ERROR:
                return "AL_NO_ERROR";
            case AL_INVALID_NAME:
                return "AL_INVALID_NAME";
            case AL_INVALID_ENUM:
                return "AL_INVALID_ENUM";
            case AL_INVALID_VALUE:
                return "AL_INVALID_VALUE";
            case AL_INVALID_OPERATION:
                return "AL_INVALID_OPERATION";
            case AL_OUT_OF_MEMORY:
                return "AL_OUT_OF_MEMORY";
            default:
                return null;
        }
    }

    private void loadAudioFile(InputStream in, int[] format, ByteBuffer[] data, int[] size, int[] freq) throws IOException {
        OggDecoder decoder = new OggDecoder();
        ogg = decoder.Data(in);
        format[0] = ogg.channels > 1 ? AL_FORMAT_STEREO16 : AL_FORMAT_MONO16;
        data[0] = ogg.data;
        freq[0] = ogg.rate;
        size[0] = ogg.data.capacity();
    }

    // play the MP3 file to the sound card
    public void play() {
        thread = new Thread(this);
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
        try {
            int[] format = new int[1];
            int[] size = new int[1];
            int[] freq = new int[1];
            ByteBuffer[] data = new ByteBuffer[1];
            try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName)) {
                alGetError();
                bufferNames = alGenBuffers();
                checkForError();
                srcNames = alGenSources();
                checkForError();

                loadAudioFile(inputStream, format, data, size, freq);
                alBufferData(bufferNames, format[0], data[0], freq[0]);
                checkForError();
                alSourcei(srcNames, AL_BUFFER, bufferNames);
                alSourcei(srcNames, AL_LOOPING, looping ? AL_TRUE : AL_FALSE);//AL_LOOPING, looping ? AL_TRUE : AL_FALSE
                alSourcef(srcNames, AL_PITCH, 1.0f);
                alSourcef(srcNames, AL_GAIN, volume / 100.0f);
                playingAudio = true;
                alSourcePlay(srcNames);
                checkForError();
                try (MemoryStack mem = MemoryStack.create()) {
                    IntBuffer audioState = mem.callocInt(1);
                    mem.push();
                    int state = methodWaitTillDone(audioState);
                    while (state == AL_PLAYING || state == AL_PAUSED) {
                        Thread.sleep(16);//wait till done
                    }
                }
            }
        } catch (Exception ex) {
            System.err.printf("Problem with [%s - %s] %s\n", srcNames, bufferNames, fileName);
            ex.printStackTrace(System.err);
        } finally {
            close();
        }
    }

    private int methodWaitTillDone(IntBuffer audioState) {
        alGetSourcei(srcNames, AL_SOURCE_STATE, audioState);
        return audioState.get(0);
    }

    private void setVolume(float volume) {
        this.volume = volume;
        if (playingAudio)
            alSourcef(srcNames, AL_GAIN, volume / 100.0f);
    }

    public AudioType getAudioType() {
        return audioType;
    }

    public void togglePause() {
        paused = !paused;
        if (paused)
            pause();
        else
            resume();
    }

    public void pause() {
        paused = true;
        alSourcePause(srcNames);
    }

    public void resume() {
        paused = false;
        alSourcePlay(srcNames);
    }


    public void stop() {
        alSourceStop(srcNames);
    }

    public void stop(int milliFadeTimeout) {
        float volumeDecrement = volume / milliFadeTimeout;
        new Thread(() -> {
            while (volume > 0.0f) {
                setVolume(volume - volumeDecrement);
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            alSourceStop(srcNames);
        }).start();

    }

    public void close() {
        heap.remove(this);
        alSourcei(srcNames, AL_BUFFER, 0);
        alSourceStop(srcNames);
        alDeleteSources(srcNames);
        alDeleteBuffers(bufferNames);
        free(ogg.data);
    }

    public static void closeAll() {
        alcDestroyContext(audioContext);
        alcCloseDevice(audioDevice);
    }
}

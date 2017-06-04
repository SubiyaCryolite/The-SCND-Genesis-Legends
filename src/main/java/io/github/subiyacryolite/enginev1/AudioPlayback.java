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
import com.scndgen.legends.state.State;
import org.apache.commons.io.IOUtils;
import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.stb.STBVorbisInfo;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.HashSet;
import java.util.Set;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.openal.ALC10.*;
import static org.lwjgl.openal.ALC11.ALC_MONO_SOURCES;
import static org.lwjgl.openal.ALC11.ALC_STEREO_SOURCES;
import static org.lwjgl.stb.STBVorbis.*;
import static org.lwjgl.system.MemoryUtil.*;
import static org.lwjgl.system.libc.LibCStdlib.free;

public class AudioPlayback {

    private static final Set<AudioPlayback> heap = new HashSet<>();
    private static final int[] sources = new int[24];
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
        for (int i = 0; i < sources.length; i++) {
            sources[i] = alGenSources();
        }
    }

    private static int currentBuffer() {
        for (int source : sources) {
            int state = alGetSourcei(source, AL_SOURCE_STATE);
            if (state == AL_PLAYING || state == AL_PAUSED)
                continue;
            return source;
        }
        return 0;
    }

    private String fileName;
    private boolean paused;
    private AudioType audioType;
    private boolean looping = false;
    private float volume = 0.0f;
    private int source;
    private int buffer;

    public AudioPlayback(String filename, AudioType audioType, boolean loop) {
        this.fileName = filename;
        this.looping = loop;
        this.audioType = audioType;
        heap.add(this);
        switch (audioType) {
            case MUSIC:
                setVolume(State.get().getLogin().getMusicVolume());
                break;
            case VOICE:
                setVolume(State.get().getLogin().getVoiceVolume());
                break;
            case SOUND:
                setVolume(State.get().getLogin().getSoundVolume());
                break;
        }
    }

    static ShortBuffer readVorbis(String resource, STBVorbisInfo info) throws IOException {
        ByteBuffer byteBuffer;
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(resource)) {
            byte[] bytes = IOUtils.toByteArray(inputStream);
            (byteBuffer = memAlloc(bytes.length)).put(bytes).rewind();
        } catch (IOException ex) {
            throw ex;
        }
        IntBuffer error = BufferUtils.createIntBuffer(1);
        long decoder = stb_vorbis_open_memory(byteBuffer, error, null);
        if (decoder == NULL) {
            free(byteBuffer);
            throw new RuntimeException("Failed to open Ogg Vorbis file. Error: " + error);
        }
        stb_vorbis_get_info(decoder, info);
        int channels = info.channels();
        int lengthSamples = stb_vorbis_stream_length_in_samples(decoder);
        ShortBuffer pcm = memAllocShort(lengthSamples);
        pcm.limit(stb_vorbis_get_samples_short_interleaved(decoder, channels, pcm) * channels);
        stb_vorbis_close(decoder);
        free(byteBuffer);
        return pcm;
    }

    public static void volume(AudioType audioType, float volume) {
        heap.stream().forEach(elem -> {
            if (elem.getAudioType() == audioType) {
                elem.setVolume(volume);
            }
        });
    }

    // play the MP3 file to the sound card
    public void play() {
        new Thread(() -> {
            try {
                source = currentBuffer();
                buffer = alGenBuffers();
                try (STBVorbisInfo info = STBVorbisInfo.malloc()) {
                    ShortBuffer pcm = readVorbis(fileName, info);
                    alBufferData(buffer, info.channels() == 1 ? AL_FORMAT_MONO16 : AL_FORMAT_STEREO16, pcm, info.sample_rate());
                    free(pcm);
                }
                alSourcei(source, AL_BUFFER, 0);//reset
                alSourcei(source, AL_BUFFER, buffer);
                alSourcei(source, AL_LOOPING, looping ? AL_TRUE : AL_FALSE);//AL_LOOPING, looping ? AL_TRUE : AL_FALSE
                alSourcef(source, AL_GAIN, volume / 100.0f);
                alSourcePlay(source);
                int state;
                do {
                    state = alGetSourcei(source, AL_SOURCE_STATE);
                    Thread.sleep(1);//wait till done
                } while (state == AL_PLAYING || state == AL_PAUSED);
            } catch (Exception ex) {
                System.err.printf("Problem with [%s - %s] %s\n", source, buffer, fileName);
                ex.printStackTrace(System.err);
            } finally {
                close();
            }
        }).start();
    }

    private void setVolume(float volume) {
        this.volume = volume;
        if (source > 0)
            alSourcef(source, AL_GAIN, volume / 100.0f);
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
        alSourcePause(source);
    }

    public void resume() {
        paused = false;
        alSourcePlay(source);
    }

    public void stop() {
        alSourceStop(source);
        looping = false;
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
            stop();
        }).start();

    }

    public void close() {
        heap.remove(this);
        alSourceStop(source);
        alDeleteBuffers(buffer);
    }


    public static void closeAll() {
        for (AudioPlayback audioPlayback : heap) {
            audioPlayback.stop();
        }
        for (int source : sources) {
            alDeleteSources(source);
        }
        alcDestroyContext(audioContext);
        alcCloseDevice(audioDevice);
    }
}

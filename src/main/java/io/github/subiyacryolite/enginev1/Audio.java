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

public class Audio {

    private static final Set<Audio> heap = new HashSet<>();
    //========================================
    private static long audioDevice;
    private static long audioContext;
    private boolean lockVolume;

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
    }

    private String fileName;
    private boolean paused;
    private AudioType audioType;
    private boolean looping = false;
    private float volume = 0.0f;
    private IntBuffer source;
    private IntBuffer buffer;

    public Audio(String filename, AudioType audioType, boolean loop) {
        this.fileName = filename;
        this.looping = loop;
        this.audioType = audioType;
        heap.add(this);
        switch (audioType) {
            case MUSIC:
                setVolume(State.get().getLogin().getMusicVolume(), false);
                break;
            case VOICE:
                setVolume(State.get().getLogin().getVoiceVolume(), false);
                break;
            case SOUND:
                setVolume(State.get().getLogin().getSoundVolume(), false);
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
                elem.setVolume(volume, false);
            }
        });
    }

    // play the MP3 file to the sound card
    public void play() {
        Thread thread = new Thread(() -> {
            try {
                source = memAllocInt(1);
                buffer = memAllocInt(1);
                alGenSources(source);
                alGenBuffers(buffer);
                try (STBVorbisInfo info = STBVorbisInfo.malloc()) {
                    ShortBuffer pcm = readVorbis(fileName, info);
                    alBufferData(buffer.get(0), info.channels() == 1 ? AL_FORMAT_MONO16 : AL_FORMAT_STEREO16, pcm, info.sample_rate());
                    free(pcm);
                }
                alSourcei(source.get(0), AL_BUFFER, 0);//reset
                alSourcei(source.get(0), AL_BUFFER, buffer.get(0));
                alSourcei(source.get(0), AL_LOOPING, looping ? AL_TRUE : AL_FALSE);//AL_LOOPING, looping ? AL_TRUE : AL_FALSE
                alSourcef(source.get(0), AL_GAIN, volume / 100.0f);
                alSourcePlay(source.get(0));
                int state;
                do {
                    state = alGetSourcei(source.get(0), AL_SOURCE_STATE);
                    Thread.sleep(1);//wait till done
                } while (state == AL_PLAYING || state == AL_PAUSED);
            } catch (Exception ex) {
                System.err.printf("Problem with [%s - %s] %s\n", source.get(0), buffer, fileName);
                ex.printStackTrace(System.err);
            } finally {
                close();
            }
        });
        thread.setDaemon(false);
        thread.start();
    }

    private void setVolume(float volume, boolean ignoreLock) {
        this.volume = volume;
        if (lockVolume)
            if (!ignoreLock)
                return;
        if (source == null) return;
        if (source.get(0) > 0)
            alSourcef(source.get(0), AL_GAIN, volume / 100.0f);
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
        if (source == null) return;
        alSourcePause(source.get(0));
    }

    public void resume() {
        paused = false;
        if (source == null) return;
        alSourcePlay(source.get(0));
    }

    public void stop() {
        if (source == null) return;
        alSourceStop(source.get(0));
        looping = false;
    }

    public void stop(int milliFadeTimeout) {
        lockVolume = true;
        float volumeDecrement = volume / milliFadeTimeout;
        new Thread(() -> {
            while (volume > 0.0f) {
                setVolume(volume - volumeDecrement, true);
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
        alSourceStop(source.get(0));
        alSourcei(source.get(0), AL_BUFFER, 0);
        alDeleteBuffers(buffer);
        free(buffer);
        alDeleteSources(source);
        free(source);
    }


    public static void closeAll() {
        System.out.println("Attempting to terminate all audio");
        for (Audio audio : heap) {
            audio.stop();
        }
        alcDestroyContext(audioContext);
        alcCloseDevice(audioDevice);
        System.out.println("Terminated all audio");
    }
}

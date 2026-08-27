/**************************************************************************

 The SCND Genesis: Legends is a fighting game based on THE SCND GENESIS,
 a webcomic created by Ifunga Ndana ((([<a href="https://www.scndgen.com">https://www.scndgen.com</a>]))).

 The SCND Genesis: Legends RMX  © 2017 Ifunga Ndana.

 The SCND Genesis: Legends is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 The SCND Genesis: Legends is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with The SCND Genesis: Legends. If not, see <<a href="http://www.gnu.org/licenses/">http://www.gnu.org/licenses/</a>>.

 **************************************************************************/
package io.github.subiyacryolite.enginev2;

import com.scndgen.legends.enums.AudioType;
import com.scndgen.legends.state.State;
import org.apache.commons.io.IOUtils;
import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.stb.STBVorbisInfo;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.lwjgl.openal.AL10.AL_BUFFER;
import static org.lwjgl.openal.AL10.AL_FALSE;
import static org.lwjgl.openal.AL10.AL_FORMAT_MONO16;
import static org.lwjgl.openal.AL10.AL_FORMAT_STEREO16;
import static org.lwjgl.openal.AL10.AL_GAIN;
import static org.lwjgl.openal.AL10.AL_LOOPING;
import static org.lwjgl.openal.AL10.AL_PAUSED;
import static org.lwjgl.openal.AL10.AL_PLAYING;
import static org.lwjgl.openal.AL10.AL_SOURCE_STATE;
import static org.lwjgl.openal.AL10.AL_STOPPED;
import static org.lwjgl.openal.AL10.AL_TRUE;
import static org.lwjgl.openal.AL10.alBufferData;
import static org.lwjgl.openal.AL10.alDeleteBuffers;
import static org.lwjgl.openal.AL10.alDeleteSources;
import static org.lwjgl.openal.AL10.alGenBuffers;
import static org.lwjgl.openal.AL10.alGenSources;
import static org.lwjgl.openal.AL10.alGetSourcef;
import static org.lwjgl.openal.AL10.alGetSourcei;
import static org.lwjgl.openal.AL10.alSourcePause;
import static org.lwjgl.openal.AL10.alSourcePlay;
import static org.lwjgl.openal.AL10.alSourceStop;
import static org.lwjgl.openal.AL10.alSourcef;
import static org.lwjgl.openal.AL10.alSourcei;
import static org.lwjgl.openal.ALC10.ALC_DEFAULT_DEVICE_SPECIFIER;
import static org.lwjgl.openal.ALC10.alcCloseDevice;
import static org.lwjgl.openal.ALC10.alcCreateContext;
import static org.lwjgl.openal.ALC10.alcDestroyContext;
import static org.lwjgl.openal.ALC10.alcGetString;
import static org.lwjgl.openal.ALC10.alcMakeContextCurrent;
import static org.lwjgl.openal.ALC10.alcOpenDevice;
import static org.lwjgl.stb.STBVorbis.stb_vorbis_close;
import static org.lwjgl.stb.STBVorbis.stb_vorbis_get_info;
import static org.lwjgl.stb.STBVorbis.stb_vorbis_get_samples_short_interleaved;
import static org.lwjgl.stb.STBVorbis.stb_vorbis_open_memory;
import static org.lwjgl.stb.STBVorbis.stb_vorbis_stream_length_in_samples;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * Main-thread OpenAL mixer: one device/context, cached PCM buffers, pooled sources.
 * OpenAL already mixes asynchronously; Java only kicks sources and ticks fades.
 */
public final class AudioEngine {
    private static final int SOURCE_POOL = 32;

    private static AudioEngine instance;

    private final long device;
    private final long context;
    private final EnumMap<AudioType, Float> buses = new EnumMap<>(AudioType.class);
    private final Map<String, Integer> buffers = new HashMap<>();
    private final ArrayDeque<Integer> freeSources = new ArrayDeque<>();
    private final LinkedHashMap<Integer, Voice> voices = new LinkedHashMap<>();
    private int nextVoiceId = 1;
    private boolean shutdown;

    public static void init() {
        if (instance != null && !instance.shutdown) {
            return;
        }
        instance = new AudioEngine();
    }

    public static AudioEngine get() {
        return instance;
    }

    public static void shutdown() {
        if (instance != null) {
            instance.destroy();
            instance = null;
        }
    }

    private AudioEngine() {
        buses.put(AudioType.MUSIC, 100f);
        buses.put(AudioType.VOICE, 100f);
        buses.put(AudioType.SOUND, 100f);
        try {
            var login = State.get().getLogin();
            buses.put(AudioType.MUSIC, (float) login.getMusicVolume());
            buses.put(AudioType.VOICE, (float) login.getVoiceVolume());
            buses.put(AudioType.SOUND, (float) login.getSoundVolume());
        } catch (Exception ignored) {
        }

        device = alcOpenDevice((ByteBuffer) null);
        if (device == NULL) {
            throw new IllegalStateException("Failed to open the default OpenAL device.");
        }
        ALCCapabilities deviceCaps = ALC.createCapabilities(device);
        context = alcCreateContext(device, (IntBuffer) null);
        if (context == NULL) {
            alcCloseDevice(device);
            throw new IllegalStateException("Failed to create OpenAL context.");
        }
        alcMakeContextCurrent(context);
        AL.createCapabilities(deviceCaps);
        System.out.println("OpenAL device: " + alcGetString(NULL, ALC_DEFAULT_DEVICE_SPECIFIER));

        IntBuffer generated = BufferUtils.createIntBuffer(SOURCE_POOL);
        alGenSources(generated);
        for (int i = 0; i < SOURCE_POOL; i++) {
            freeSources.add(generated.get(i));
        }
    }

    public void update(double deltaSeconds) {
        if (shutdown) {
            return;
        }
        double dt = Math.max(0d, deltaSeconds);
        List<Voice> snapshot = new ArrayList<>(voices.values());
        for (Voice voice : snapshot) {
            if (voice.fading) {
                voice.fadeElapsed += dt;
                float t = voice.fadeDuration <= 0f ? 1f : (float) Math.min(1d, voice.fadeElapsed / voice.fadeDuration);
                float gain = voice.fadeStartGain * (1f - t);
                alSourcef(voice.source, AL_GAIN, Math.max(0f, gain));
                if (t >= 1f) {
                    release(voice);
                    continue;
                }
            }
            int state = alGetSourcei(voice.source, AL_SOURCE_STATE);
            if (state == AL_STOPPED && !voice.looping) {
                release(voice);
            }
        }
    }

    void play(Audio clip) {
        if (clip == null || shutdown) {
            return;
        }
        stop(clip);
        int buffer = bufferFor(clip.fileName());
        if (buffer == 0) {
            return;
        }
        int source = acquireSource();
        if (source == 0) {
            return;
        }
        float gain = busGain(clip.getAudioType());
        alSourcei(source, AL_BUFFER, buffer);
        alSourcei(source, AL_LOOPING, clip.looping() ? AL_TRUE : AL_FALSE);
        alSourcef(source, AL_GAIN, gain);
        alSourcePlay(source);

        Voice voice = new Voice();
        voice.id = nextVoiceId++;
        voice.source = source;
        voice.handle = clip;
        voice.type = clip.getAudioType();
        voice.looping = clip.looping();
        voices.put(voice.id, voice);
        clip.attach(voice.id);
    }

    void stop(Audio clip) {
        Voice voice = voiceOf(clip);
        if (voice != null) {
            release(voice);
        }
    }

    void fadeOut(Audio clip, int milliFadeTimeout) {
        if (milliFadeTimeout <= 0) {
            stop(clip);
            return;
        }
        Voice voice = voiceOf(clip);
        if (voice == null) {
            return;
        }
        voice.lockBus = true;
        voice.fading = true;
        voice.fadeDuration = milliFadeTimeout / 1000f;
        voice.fadeElapsed = 0d;
        voice.fadeStartGain = alGetSourcef(voice.source, AL_GAIN);
    }

    void pause(Audio clip) {
        Voice voice = voiceOf(clip);
        if (voice != null) {
            alSourcePause(voice.source);
        }
    }

    void resume(Audio clip) {
        Voice voice = voiceOf(clip);
        if (voice != null) {
            alSourcePlay(voice.source);
        }
    }

    void togglePause(Audio clip) {
        Voice voice = voiceOf(clip);
        if (voice == null) {
            return;
        }
        int state = alGetSourcei(voice.source, AL_SOURCE_STATE);
        if (state == AL_PAUSED) {
            alSourcePlay(voice.source);
        } else if (state == AL_PLAYING) {
            alSourcePause(voice.source);
        }
    }

    void setBusVolume(AudioType type, float volume) {
        float clamped = Math.max(0f, Math.min(100f, volume));
        buses.put(type, clamped);
        float gain = clamped / 100f;
        for (Voice voice : voices.values()) {
            if (voice.type == type && !voice.lockBus) {
                alSourcef(voice.source, AL_GAIN, gain);
            }
        }
    }

    private float busGain(AudioType type) {
        return buses.getOrDefault(type, 100f) / 100f;
    }

    private Voice voiceOf(Audio clip) {
        if (clip == null || clip.voiceId() == 0) {
            return null;
        }
        return voices.get(clip.voiceId());
    }

    private int acquireSource() {
        Integer source = freeSources.pollFirst();
        if (source != null) {
            return source;
        }
        Voice steal = null;
        for (Voice voice : voices.values()) {
            if (!voice.looping) {
                steal = voice;
                break;
            }
        }
        if (steal == null && !voices.isEmpty()) {
            steal = voices.values().iterator().next();
        }
        if (steal == null) {
            return 0;
        }
        int reused = steal.source;
        alSourceStop(reused);
        alSourcei(reused, AL_BUFFER, 0);
        steal.source = 0;
        release(steal);
        return reused;
    }

    private void release(Voice voice) {
        if (voice == null) {
            return;
        }
        voices.remove(voice.id);
        if (voice.handle != null) {
            voice.handle.detach(voice.id);
            voice.handle = null;
        }
        if (voice.source != 0) {
            alSourceStop(voice.source);
            alSourcei(voice.source, AL_BUFFER, 0);
            alSourcei(voice.source, AL_LOOPING, AL_FALSE);
            freeSources.addLast(voice.source);
            voice.source = 0;
        }
    }

    private int bufferFor(String resource) {
        Integer cached = buffers.get(resource);
        if (cached != null) {
            return cached;
        }
        try {
            int buffer = decodeVorbis(resource);
            buffers.put(resource, buffer);
            return buffer;
        } catch (Exception ex) {
            System.err.println("Unable to load audio: " + resource);
            ex.printStackTrace(System.err);
            return 0;
        }
    }

    private static int decodeVorbis(String resource) throws Exception {
        String normalized = resource.startsWith("/") ? resource.substring(1) : resource;
        byte[] bytes;
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(normalized)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("Missing classpath resource: " + resource);
            }
            bytes = IOUtils.toByteArray(inputStream);
        }
        ByteBuffer encoded = BufferUtils.createByteBuffer(bytes.length);
        encoded.put(bytes).flip();

        IntBuffer error = BufferUtils.createIntBuffer(1);
        long decoder = stb_vorbis_open_memory(encoded, error, null);
        if (decoder == NULL) {
            throw new IllegalStateException("Failed to open Ogg Vorbis file. Error: " + error.get(0));
        }
        try (STBVorbisInfo info = STBVorbisInfo.malloc()) {
            stb_vorbis_get_info(decoder, info);
            int channels = info.channels();
            int lengthSamples = stb_vorbis_stream_length_in_samples(decoder);
            ShortBuffer pcm = BufferUtils.createShortBuffer(Math.max(1, lengthSamples * Math.max(1, channels)));
            int samples = stb_vorbis_get_samples_short_interleaved(decoder, channels, pcm);
            pcm.limit(samples * channels);
            stb_vorbis_close(decoder);
            decoder = NULL;

            int alBuffer = alGenBuffers();
            int format = channels == 1 ? AL_FORMAT_MONO16 : AL_FORMAT_STEREO16;
            alBufferData(alBuffer, format, pcm, info.sample_rate());
            return alBuffer;
        } finally {
            if (decoder != NULL) {
                stb_vorbis_close(decoder);
            }
        }
    }

    private void destroy() {
        if (shutdown) {
            return;
        }
        shutdown = true;
        List<Voice> snapshot = new ArrayList<>(voices.values());
        for (Voice voice : snapshot) {
            release(voice);
        }
        voices.clear();
        while (!freeSources.isEmpty()) {
            alDeleteSources(freeSources.removeFirst());
        }
        for (int buffer : buffers.values()) {
            alDeleteBuffers(buffer);
        }
        buffers.clear();
        alcMakeContextCurrent(NULL);
        alcDestroyContext(context);
        alcCloseDevice(device);
        System.out.println("Terminated OpenAL");
    }

    private static final class Voice {
        int id;
        int source;
        Audio handle;
        AudioType type;
        boolean looping;
        boolean lockBus;
        boolean fading;
        float fadeStartGain;
        float fadeDuration;
        double fadeElapsed;
    }
}

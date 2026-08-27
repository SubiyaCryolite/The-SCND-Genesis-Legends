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

/**
 * Handle to a voice on {@link AudioEngine}. OpenAL work stays on the game thread.
 */
public class Audio {

    private final String fileName;
    private final AudioType audioType;
    private final boolean looping;
    private int voiceId;

    public Audio(String filename, AudioType audioType, boolean loop) {
        this.fileName = filename;
        this.audioType = audioType;
        this.looping = loop;
    }

    public static void volume(AudioType audioType, float volume) {
        AudioEngine engine = AudioEngine.get();
        if (engine != null) {
            engine.setBusVolume(audioType, volume);
        }
    }

    public static void closeAll() {
        AudioEngine.shutdown();
    }

    public void play() {
        AudioEngine engine = AudioEngine.get();
        if (engine != null) {
            engine.play(this);
        }
    }

    public AudioType getAudioType() {
        return audioType;
    }

    public void togglePause() {
        AudioEngine engine = AudioEngine.get();
        if (engine != null) {
            engine.togglePause(this);
        }
    }

    public void pause() {
        AudioEngine engine = AudioEngine.get();
        if (engine != null) {
            engine.pause(this);
        }
    }

    public void resume() {
        AudioEngine engine = AudioEngine.get();
        if (engine != null) {
            engine.resume(this);
        }
    }

    public void stop() {
        AudioEngine engine = AudioEngine.get();
        if (engine != null) {
            engine.stop(this);
        }
    }

    public void stop(int milliFadeTimeout) {
        AudioEngine engine = AudioEngine.get();
        if (engine != null) {
            engine.fadeOut(this, milliFadeTimeout);
        }
    }

    public void close() {
        stop();
    }

    String fileName() {
        return fileName;
    }

    boolean looping() {
        return looping;
    }

    int voiceId() {
        return voiceId;
    }

    void attach(int id) {
        this.voiceId = id;
    }

    void detach(int id) {
        if (this.voiceId == id) {
            this.voiceId = 0;
        }
    }
}

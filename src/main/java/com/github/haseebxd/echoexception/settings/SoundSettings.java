package com.github.haseebxd.echoexception.settings;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import org.jetbrains.annotations.NotNull;

/**
 * Persistent settings for sound configuration.
 * Stores user preferences for sound notifications.
 */
@Service(Service.Level.APP)
@State(
        name = "EchoExceptionSoundSettings",
        storages = {@Storage("EchoExceptionSoundSettings.xml")}
)
public final class SoundSettings implements PersistentStateComponent<SoundSettings.State> {
    private State state = new State();

    public static class State {
        private boolean enabled = true;
        private int volume = 50;
        private String soundType = "Counter-Strike";

        public boolean isEnabled() {
            return enabled;
        }

        public int getVolume() {
            return volume;
        }

        public String getSoundType() {
            return soundType;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public void setVolume(int volume) {
            this.volume = volume;
        }

        public void setSoundType(String soundType) {
            this.soundType = soundType;
        }
    }

    @Override
    public @NotNull State getState() {
        return state;
    }

    @Override
    public void loadState(@NotNull State state) {
        this.state = state;
    }

    public boolean isEnabled() {
        return state.isEnabled();
    }

    public void setEnabled(boolean enabled) {
        state.setEnabled(enabled);
    }

    public int getVolume() {
        return state.getVolume();
    }

    public void setVolume(int volume) {
        state.setVolume(volume);
    }

    public String getSoundType() {
        return state.getSoundType();
    }

    public void setSoundType(String soundType) {
        state.setSoundType(soundType);
    }

    public static SoundSettings getInstance() {
        return ApplicationManager.getApplication().getService(SoundSettings.class);
    }
}
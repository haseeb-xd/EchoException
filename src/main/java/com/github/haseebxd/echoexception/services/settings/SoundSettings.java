package com.github.haseebxd.echoexception.services.settings;

import com.github.haseebxd.echoexception.sound.GameSoundType;
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
        private boolean soundEnabled = true;
        private boolean notificationEnabled = true;
        private int volume = 50;
        private GameSoundType soundType = GameSoundType.SATORU_GOJO;

        public boolean isSoundEnabled() {
            return soundEnabled;
        }

        public int getVolume() {
            return volume;
        }

        public void setSoundEnabled(boolean soundEnabled) {
            this.soundEnabled = soundEnabled;
        }

        public void setVolume(int volume) {
            this.volume = volume;
        }

        public GameSoundType getSoundType() {
            return soundType;
        }

        public void setSoundType(GameSoundType soundType) {
            this.soundType = soundType;
        }

        public boolean isNotificationEnabled() {
            return notificationEnabled;
        }

        public void setNotificationEnabled(boolean notificationEnabled) {
            this.notificationEnabled = notificationEnabled;
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

    public boolean isSoundEnabled() {
        return state.isSoundEnabled();
    }

    public void setSoundEnabled(boolean enabled) {
        state.setSoundEnabled(enabled);
    }

    public int getVolume() {
        return state.getVolume();
    }

    public void setVolume(int volume) {
        state.setVolume(volume);
    }

    public GameSoundType getSoundType() {
        return state.getSoundType();
    }

    public void setSoundType(GameSoundType soundType) {
        state.setSoundType(soundType);
    }

    public static SoundSettings getInstance() {
        return ApplicationManager.getApplication().getService(SoundSettings.class);
    }

    public boolean isNotificationEnabled() {
        return state.isNotificationEnabled();
    }

    public void setNotificationEnabled(boolean notificationEnabled) {
        state.setNotificationEnabled(notificationEnabled);
    }
}
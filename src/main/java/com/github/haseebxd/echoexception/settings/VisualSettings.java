package com.github.haseebxd.echoexception.settings;


import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import org.jetbrains.annotations.NotNull;

/**
 * Persistent settings for visual configuration.
 * Stores user preferences for visual notifications.
 */
@Service(Service.Level.APP)
@State(
        name = "EchoExceptionVisualSettings",
        storages = {@Storage("EchoExceptionVisualSettings.xml")}
)
public final class VisualSettings implements PersistentStateComponent<VisualSettings.State> {
    private State state = new State();

    public static class State {
        private boolean enabled = false;
        private String visualEffect = "Explosion";
        private int durationSeconds = 3;

        public int getDurationSeconds() {
            return durationSeconds;
        }

        public void setDurationSeconds(int durationSeconds) {
            this.durationSeconds = durationSeconds;
        }

        public String getVisualEffect() {
            return visualEffect;
        }

        public void setVisualEffect(String visualEffect) {
            this.visualEffect = visualEffect;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
    }

    @Override
    public @NotNull State getState() {
        return state;
    }

    @Override
    public void loadState(@NotNull State state) {
        this.state= state;
    }

    public boolean isEnabled() {
        return state.isEnabled();
    }

    public void setEnabled(boolean enabled) {
        state.setEnabled(enabled);
    }

    public String getVisualEffect() {
        return state.getVisualEffect();
    }

    public void setVisualEffect(String visualEffect) {
        state.setVisualEffect(visualEffect);
    }

    public int getDurationSeconds() {
        return state.getDurationSeconds();
    }

    public void setDurationSeconds(int durationSeconds) {
        state.setDurationSeconds(durationSeconds);
    }

    public static VisualSettings getInstance() {
        return ApplicationManager.getApplication().getService(VisualSettings.class);
    }
}
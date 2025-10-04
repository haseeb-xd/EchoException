package com.github.haseebxd.echoexception.services.core;

import com.github.haseebxd.echoexception.services.settings.SoundSettings;
import com.github.haseebxd.echoexception.sound.GameSoundType;
import com.github.haseebxd.echoexception.util.SoundPlayer;
import com.github.haseebxd.echoexception.sound.voices.VoicePack;
import com.github.haseebxd.echoexception.sound.voices.VoicePackFactory;
import com.intellij.openapi.diagnostic.Logger;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicBoolean;

public final class SoundTriggerService {
    private static final Logger LOG = Logger.getInstance(SoundTriggerService.class);
    private static final SoundTriggerService INSTANCE = new SoundTriggerService();
    private static final long DEBOUNCE_INTERVAL_MS = 6_000;

    private final SoundPlayer player = new SoundPlayer();
    private final AtomicLong lastSoundTime = new AtomicLong(0);
    private final AtomicBoolean isPlaying = new AtomicBoolean(false);

    private SoundTriggerService() {
        LOG.info("EchoException: SoundTriggerService instance created");
    }

    public static SoundTriggerService getInstance() {
        return INSTANCE;
    }

    public void playSound(Class<? extends Throwable> exceptionClass) {
        LOG.info("EchoException: playSound() called for: " + exceptionClass.getSimpleName());

        // Check debounce condition first
        long currentTime = System.currentTimeMillis();
        long lastTime = lastSoundTime.get();

        if (currentTime - lastTime < DEBOUNCE_INTERVAL_MS) {
            LOG.debug("EchoException: Sound request debounced. Time since last sound: " +
                    (currentTime - lastTime) + "ms");
            return;
        }

        // Atomic check-and-set to prevent race conditions
        if (!isPlaying.compareAndSet(false, true)) {
            LOG.debug("EchoException: Sound already playing, skipping");
            return;
        }

        try {
            // Update last sound time immediately to prevent other threads
            lastSoundTime.set(currentTime);

            // Check if sound and notifications are enabled
            boolean isSoundEnabled = SoundSettings.getInstance().isSoundEnabled();
            boolean isNotificationsEnabled = SoundSettings.getInstance().isNotificationEnabled();

            LOG.warn("EchoException: Sound enabled: " + isSoundEnabled);

            if (!isSoundEnabled) {
                LOG.debug("EchoException: Sound & Notifications are disabled by user settings.");
                return;
            }

            // Get sound settings
            GameSoundType type = SoundSettings.getInstance().getSoundType();
            int rawVolume = SoundSettings.getInstance().getVolume();
            
            int scaledVolume = 50 + (rawVolume * 50 / 100); // remap 0–100 to 50–100
            
            LOG.warn("EchoException: Sound type: " + type + ", Volume: " + scaledVolume);

            // Get voice pack
            VoicePack pack = VoicePackFactory.getVoicePack(type);
            LOG.warn("EchoException: Voice pack: " + (pack != null ? pack.getClass().getSimpleName() : "null"));

            if (pack == null) {
                LOG.error("EchoException: Voice pack is null!");
                return;
            }

            // Get sound path
            String path = pack.getSoundForException(exceptionClass);
            LOG.warn("EchoException: Sound file path: '" + path + "'");

            if (path != null && !path.isEmpty()) {
                LOG.warn("EchoException: Attempting to play sound: " + path);

                // Try to play the sound
                player.play(path, scaledVolume);
                LOG.warn("EchoException: Sound play method called");

                LOG.warn(String.format("EchoException: Sound play method completed for '%s' (%s)",
                        path, exceptionClass.getSimpleName()));
            } else {
                LOG.warn("EchoException: No sound path found for exception " + exceptionClass.getSimpleName());
            }
        } catch (Exception e) {
            LOG.error("EchoException: Exception in playSound()", e);
        } finally {
            // Reset playing flag after a short delay to allow sound to start
            // This prevents immediate subsequent calls but allows the debounce timer to work
            try {
                Thread.sleep(100); // Small delay to ensure sound starts
            } catch (InterruptedException ignored) {
                Thread.currentThread().interrupt();
            }
            isPlaying.set(false);
        }
    }

  // Method to reset debounce manually if needed
    public void resetDebounce() {
        lastSoundTime.set(0);
        isPlaying.set(false);
        LOG.debug("EchoException: Debounce reset manually");
    }
}
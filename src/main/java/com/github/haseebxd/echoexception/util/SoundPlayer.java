package com.github.haseebxd.echoexception.util;

import com.intellij.openapi.diagnostic.Logger;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class SoundPlayer {

    private static final Logger LOG = Logger.getInstance(SoundPlayer.class);

    public boolean play(String resourcePath, int volume) {
        LOG.info("EchoException: SoundPlayer.play() called with resourcePath: '" + resourcePath + "', volume: " + volume);

        try {
            // Try to get the resource URL
            URL soundURL = getClass().getClassLoader().getResource(resourcePath);
            LOG.info("EchoException: Resource URL: " + (soundURL != null ? soundURL.toString() : "null"));

            if (soundURL == null) {
                LOG.error("EchoException: Sound resource not found: " + resourcePath);

                // Try alternative class loader approaches
                soundURL = getClass().getResource("/" + resourcePath);
                LOG.info("EchoException: Alternative resource URL (with /): " + (soundURL != null ? soundURL.toString() : "null"));

                if (soundURL == null) {
                    soundURL = Thread.currentThread().getContextClassLoader().getResource(resourcePath);
                    LOG.info("EchoException: Thread context classloader URL: " + (soundURL != null ? soundURL.toString() : "null"));
                }

                if (soundURL == null) {
                    LOG.error("EchoException: Failed to find resource with all methods: " + resourcePath);
                    return false;
                }
            }

            LOG.info("EchoException: Loading audio stream from URL: " + soundURL);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundURL);
            LOG.info("EchoException: Audio stream created successfully");

            // Get audio format info
            AudioFormat format = audioStream.getFormat();
            LOG.info("EchoException: Audio format: " + format.toString());

            LOG.info("EchoException: Getting clip from audio system...");
            Clip clip = AudioSystem.getClip();
            LOG.info("EchoException: Clip created: " + clip.getClass().getSimpleName());

            LOG.info("EchoException: Opening clip with audio stream...");
            clip.open(audioStream);
            LOG.info("EchoException: Clip opened successfully");

            // Set volume control
            try {
                if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                    FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                    float range = volumeControl.getMaximum() - volumeControl.getMinimum();
                    float gain = (range * volume / 100f) + volumeControl.getMinimum();

                    LOG.info("EchoException: Volume control range: " + volumeControl.getMinimum() + " to " + volumeControl.getMaximum());
                    LOG.info("EchoException: Setting gain to: " + gain);

                    volumeControl.setValue(gain);
                    LOG.info("EchoException: Volume set successfully");
                } else {
                    LOG.warn("EchoException: MASTER_GAIN control not supported");

                    // Try alternative volume controls
                    Control[] controls = clip.getControls();
                    LOG.info("EchoException: Available controls: " + controls.length);
                    for (Control control : controls) {
                        LOG.info("EchoException: Control: " + control.getType() + " - " + control.getClass().getSimpleName());
                    }
                }
            } catch (Exception e) {
                LOG.warn("EchoException: Failed to set volume", e);
            }

            LOG.info("EchoException: Starting clip playback...");
            clip.start();
            LOG.info("EchoException: Clip started successfully");

            // Optional: Add a small delay to ensure playback starts
            try {
                Thread.sleep(100);
                LOG.info("EchoException: Clip is running: " + clip.isRunning());
                LOG.info("EchoException: Clip frame position: " + clip.getFramePosition());
                LOG.info("EchoException: Clip frame length: " + clip.getFrameLength());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            return true;

        } catch (UnsupportedAudioFileException e) {
            LOG.error("EchoException: Unsupported audio file format: " + resourcePath, e);
            return false;
        } catch (IOException e) {
            LOG.error("EchoException: IO error playing sound: " + resourcePath, e);
            return false;
        } catch (LineUnavailableException e) {
            LOG.error("EchoException: Audio line unavailable: " + resourcePath, e);
            return false;
        } catch (Exception e) {
            LOG.error("EchoException: Unexpected error while playing sound: " + resourcePath, e);
            return false;
        }
    }

    // Test method to check available audio resources
    public void listAudioResources() {
        LOG.info("EchoException: Testing audio system availability...");
        try {
            Mixer.Info[] mixers = AudioSystem.getMixerInfo();
            LOG.info("EchoException: Available audio mixers: " + mixers.length);
            for (Mixer.Info mixerInfo : mixers) {
                LOG.info("EchoException: Mixer: " + mixerInfo.getName() + " - " + mixerInfo.getDescription());
            }
        } catch (Exception e) {
            LOG.error("EchoException: Error listing audio resources", e);
        }
    }
}
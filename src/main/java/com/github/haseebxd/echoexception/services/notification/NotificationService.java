package com.github.haseebxd.echoexception.services.notification;

import com.github.haseebxd.echoexception.services.settings.SoundSettings;
import com.github.haseebxd.echoexception.sound.GameSoundType;
import com.github.haseebxd.echoexception.sound.voices.VoicePack;
import com.github.haseebxd.echoexception.sound.voices.VoicePackFactory;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Service for displaying exception notifications using IntelliJ notification balloons.
 */
public class NotificationService {
    private static final Logger LOG = Logger.getInstance(NotificationService.class);
    
    private static final NotificationGroup NOTIFICATION_GROUP = 
            NotificationGroupManager.getInstance().getNotificationGroup("EchoException Notifications");

    /**
     * Shows a notification for the detected exception.
     *
     * @param project the current project
     * @param exceptionClass the exception class that was detected
     */
    public static void showExceptionNotification(@Nullable Project project,
                                                 @NotNull Class<? extends Throwable> exceptionClass) {
        try {
            if (!SoundSettings.getInstance().isNotificationEnabled()) {
                LOG.debug("Notifications are disabled in settings");
                return;
            }

            String message = buildNotificationMessage(exceptionClass);
            showNotification(project, message);

        } catch (Exception e) {
            LOG.error("Failed to show exception notification for: " + exceptionClass.getSimpleName(), e);
        }
    }

    /**
     * Shows IntelliJ notification balloon.
     */
    private static void showNotification(@Nullable Project project, @NotNull String message) {
        Notification notification = NOTIFICATION_GROUP.createNotification(
                "Exception detected",
                message,
                NotificationType.WARNING
        );

        notification.notify(project);
        
        LOG.debug("Showing standard notification: " + message);
    }

    /**
     * Builds the notification message using the configured voice pack.
     */
    @NotNull
    private static String buildNotificationMessage(@NotNull Class<? extends Throwable> exceptionClass) {
        GameSoundType type = SoundSettings.getInstance().getSoundType();
        VoicePack pack = VoicePackFactory.getVoicePack(type);

        if (pack != null) {
            String line = pack.getNotificationForException(exceptionClass);
            if (line != null && !line.isEmpty()) {
                return line;
            }
        }

        // Fallback message
        return "An exception occurred: " + exceptionClass.getSimpleName();
    }

}
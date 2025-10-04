package com.github.haseebxd.echoexception.services.notification;

import com.github.haseebxd.echoexception.services.settings.SoundSettings;
import com.github.haseebxd.echoexception.sound.GameSoundType;
import com.github.haseebxd.echoexception.sound.voices.VoicePack;
import com.github.haseebxd.echoexception.sound.voices.VoicePackFactory;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.ui.JBColor;
import com.intellij.ui.awt.RelativePoint;
import com.intellij.util.ui.JBUI;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Service for displaying exception notifications as toast popups.
 */
public class NotificationService {
    private static final Logger LOG = Logger.getInstance(NotificationService.class);

    private static final int FADEOUT_TIME_MS = 4000;
    private static final int ANIMATION_CYCLE_MS = 200;

    /**
     * Shows a notification for the detected exception.
     *
     * @param project the current project (nullable)
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

            // Try to show in editor first
            if (project != null) {
                Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
                if (editor != null) {
                    showEditorToast(editor, message);
                    return;
                }
            }


            showFallbackToast(message);

        } catch (Exception e) {
            LOG.error("Failed to show exception notification for: " + exceptionClass.getSimpleName(), e);
        }
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

    /**
     * Shows a toast notification
     */
    private static void showEditorToast(@NotNull Editor editor, @NotNull String message) {
        // Create panel with a placeholder for close button
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(JBUI.Borders.empty(12, 16));
        contentPanel.setBackground(JBUI.CurrentTheme.NotificationInfo.backgroundColor());


        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setOpaque(false);

        // Icon
        JLabel iconLabel = new JLabel(AllIcons.General.NotificationError);
        iconLabel.setBorder(JBUI.Borders.emptyRight(8));
        leftPanel.add(iconLabel, BorderLayout.WEST);

        // Message
        JLabel messageLabel = new JLabel(formatMessageAsHtml(message));
        messageLabel.setForeground(JBUI.CurrentTheme.NotificationInfo.foregroundColor());
        leftPanel.add(messageLabel, BorderLayout.CENTER);

        contentPanel.add(leftPanel, BorderLayout.CENTER);

        Color backgroundColor = JBUI.CurrentTheme.NotificationInfo.backgroundColor();
        Color borderColor = JBUI.CurrentTheme.NotificationInfo.borderColor();

        Balloon balloon = JBPopupFactory.getInstance()
                .createBalloonBuilder(contentPanel)
                .setFadeoutTime(FADEOUT_TIME_MS)
                .setFillColor(backgroundColor)
                .setBorderColor(borderColor)
                .setBorderInsets(JBUI.insets(1))
                .setHideOnClickOutside(true)
                .setHideOnKeyOutside(true)
                .setHideOnAction(true)
                .setAnimationCycle(ANIMATION_CYCLE_MS)
                .setShadow(true)
                .createBalloon();


        JButton closeButton = createCloseButton();
        closeButton.addActionListener(e -> balloon.hide());
        contentPanel.add(closeButton, BorderLayout.EAST);


        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int screenWidth = gd.getDisplayMode().getWidth();
        int screenHeight = gd.getDisplayMode().getHeight();


        int xOffset = 350; // Distance from right edge
        int yOffset = 80;  // Distance from bottom edge

        Point fixedScreenPosition = new Point(screenWidth - xOffset, screenHeight - yOffset);

        balloon.show(RelativePoint.fromScreen(fixedScreenPosition), Balloon.Position.above);

        LOG.debug("Showing notification at fixed screen position: " + fixedScreenPosition);
    }

    /**
     * Shows a fallback toast notification in the bottom-right of the screen.
     * This is used when no editor is available.
     */
    private static void showFallbackToast(@NotNull String message) {
        JPanel contentPanel = createSimpleNotificationPanel(message);

        Balloon balloon = JBPopupFactory.getInstance()
                .createBalloonBuilder(contentPanel)
                .setFadeoutTime(FADEOUT_TIME_MS)
                .setFillColor(new Color(60, 63, 65))
                .setBorderColor(JBColor.GRAY)
                .setHideOnClickOutside(true)
                .setHideOnKeyOutside(true)
                .setAnimationCycle(ANIMATION_CYCLE_MS)
                .createBalloon();

        // Show in bottom-right corner of screen
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int screenWidth = gd.getDisplayMode().getWidth();
        int screenHeight = gd.getDisplayMode().getHeight();

        // Calculate bottom-right position with offset
        int xOffset = 20;
        int yOffset = 80;
        Point bottomRightPoint = new Point(screenWidth - xOffset, screenHeight - yOffset);

        balloon.show(RelativePoint.fromScreen(bottomRightPoint), Balloon.Position.above);

        LOG.debug("Showing fallback notification in bottom-right: " + message);
    }

    /**
     * Creates a styled notification panel with icon, message, and close button.
     * NOTE: This method is no longer used - panel is created inline in showEditorToast
     * so the close button can reference the balloon.
     */
    @NotNull
    @Deprecated
    private static JPanel createNotificationPanel(@NotNull String message) {
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(JBUI.Borders.empty(12, 16));
        contentPanel.setBackground(JBUI.CurrentTheme.NotificationInfo.backgroundColor());

        // Left side: Icon + Message
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setOpaque(false);

        // Icon
        JLabel iconLabel = new JLabel(AllIcons.General.NotificationError);
        iconLabel.setBorder(JBUI.Borders.emptyRight(8));
        leftPanel.add(iconLabel, BorderLayout.WEST);

        // Message
        JLabel messageLabel = new JLabel(formatMessageAsHtml(message));
        messageLabel.setForeground(JBUI.CurrentTheme.NotificationInfo.foregroundColor());
        leftPanel.add(messageLabel, BorderLayout.CENTER);

        contentPanel.add(leftPanel, BorderLayout.CENTER);

        // Right side: Close button (optional, can be removed if not needed)
        JButton closeButton = createCloseButton();
        contentPanel.add(closeButton, BorderLayout.EAST);

        return contentPanel;
    }

    /**
     * Creates a simple notification panel without a close button.
     */
    @NotNull
    private static JPanel createSimpleNotificationPanel(@NotNull String message) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(JBUI.Borders.empty(10, 15));
        panel.setBackground(new Color(60, 63, 65));

        JLabel label = new JLabel(message);
        label.setForeground(JBColor.WHITE);
        panel.add(label, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Creates a styled close button for the notification.
     */
    @NotNull
    private static JButton createCloseButton() {
        JButton closeButton = new JButton("×");
        closeButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        closeButton.setForeground(JBUI.CurrentTheme.NotificationInfo.foregroundColor());
        closeButton.setBackground(null);
        closeButton.setBorder(JBUI.Borders.empty(0, 8));
        closeButton.setOpaque(false);
        closeButton.setFocusable(false);
        closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
        closeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                closeButton.setForeground(JBUI.CurrentTheme.NotificationError.foregroundColor());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                closeButton.setForeground(JBUI.CurrentTheme.NotificationInfo.foregroundColor());
            }
        });

        return closeButton;
    }

    /**
     * Formats the message as HTML for better rendering.
     */
    @NotNull
    private static String formatMessageAsHtml(@NotNull String message) {
        String fontFamily = UIManager.getFont("Label.font").getFamily();
        return String.format(
                "<html><body style='font-family: %s; font-size: 13px;'>%s</body></html>",
                fontFamily,
                message
        );
    }
}
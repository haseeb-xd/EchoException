package com.github.haseebxd.echoexception.services.core;

import com.github.haseebxd.echoexception.achievements.AchievementManager;
import com.github.haseebxd.echoexception.services.notification.NotificationService;
import com.intellij.execution.process.ProcessAdapter;
import com.intellij.execution.process.ProcessEvent;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Listens to console output and detects Java exceptions.
 * When an exception is found, triggers sound and notification.
 */
public final class ExceptionConsoleListener extends ProcessAdapter {
    private static final Logger LOG = Logger.getInstance(ExceptionConsoleListener.class);
    
    // track last processed exception to prevent duplicate lines
    private static volatile String lastProcessedException = null;
    private static volatile long lastProcessedTime = 0;

    // Regex pattern to match exception names
    private static final Pattern EXCEPTION_PATTERN = Pattern.compile("\\b(\\w+Exception|\\w+Error)\\b");

    // Common Java exception packages to search
    private static final List<String> COMMON_EXCEPTION_PACKAGES = Arrays.asList(
            "java.lang.",
            "java.io.",
            "java.net.",
            "java.sql.",
            "java.util.",
            "java.util.concurrent.",
            "java.security.",
            "javax.xml.parsers.",
            "java.text."
    );

    @Nullable
    private final Project project;

    private ExceptionConsoleListener(@Nullable Project project) {
        this.project = project;
    }

    /**
     * Attaches this listener to a process handler.
     *
     * @param handler the process handler to attach to
     * @param project the current project
     */
    public static void attachTo(@NotNull ProcessHandler handler, @Nullable Project project) {
        ExceptionConsoleListener listener = new ExceptionConsoleListener(project);
        handler.addProcessListener(listener);

        if (LOG.isDebugEnabled()) {
            LOG.debug("ExceptionConsoleListener attached to: " + handler.getClass().getSimpleName());
        }
    }

    @Override
    public void onTextAvailable(@NotNull ProcessEvent event, @NotNull com.intellij.openapi.util.Key outputType) {
        String text = event.getText();

        if (isTextEmpty(text)) {
            return;
        }

        if (LOG.isTraceEnabled()) {
            LOG.trace("Processing console output: " + text.trim());
        }

        findAndHandleException(text);
    }

    @Override
    public void processTerminated(@NotNull ProcessEvent event) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Process terminated with exit code: " + event.getExitCode());
        }
    }

    private boolean isTextEmpty(@Nullable String text) {
        return text == null || text.trim().isEmpty();
    }

    private void findAndHandleException(@NotNull String text) {
        Matcher matcher = EXCEPTION_PATTERN.matcher(text);

        if (matcher.find()) {
            String exceptionName = matcher.group(1);

            // skip if we just processed this same exception within 3 seconds (for duplicate lines)
            long currentTime = System.currentTimeMillis();
            if (exceptionName.equals(lastProcessedException) && 
                (currentTime - lastProcessedTime) < 3000) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Skipping duplicate exception line: " + exceptionName);
                }
                return;
            }

            if (LOG.isDebugEnabled()) {
                LOG.debug("Found exception pattern: " + exceptionName);
            }

            // Update last processed exception and time
            lastProcessedException = exceptionName;
            lastProcessedTime = currentTime;
            
            resolveAndTriggerException(exceptionName);
        }
    }

    private void resolveAndTriggerException(@NotNull String exceptionName) {
        findExceptionClass(exceptionName)
                .ifPresent(this::triggerExceptionHandlers);
    }

    /**
     * Attempts to find the exception class by searching common packages.
     *
     * @param exceptionName the simple name of the exception
     * @return Optional containing the exception class if found
     */
    private Optional<Class<? extends Throwable>> findExceptionClass(@NotNull String exceptionName) {
        for (String pkg : COMMON_EXCEPTION_PACKAGES) {
            Optional<Class<? extends Throwable>> result = tryLoadClass(pkg + exceptionName);
            if (result.isPresent()) {
                return result;
            }
        }
        return Optional.empty();
    }

    /**
     * Attempts to load a class by its fully qualified name.
     *
     * @param fullyQualifiedName the full class name
     * @return Optional containing the exception class if it's a Throwable, empty otherwise
     */
    private Optional<Class<? extends Throwable>> tryLoadClass(@NotNull String fullyQualifiedName) {
        try {
            Class<?> clazz = Class.forName(fullyQualifiedName);

            if (Throwable.class.isAssignableFrom(clazz)) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Successfully loaded exception class: " + fullyQualifiedName);
                }
                return Optional.of(clazz.asSubclass(Throwable.class));
            }
        } catch (ClassNotFoundException e) {
            // Expected for non-existent classes
        } catch (ClassCastException e) {
            LOG.warn("Class found but not a Throwable: " + fullyQualifiedName, e);
        }

        return Optional.empty();
    }

    /**
     * Triggers sound and notification for the detected exception.
     *
     * @param exceptionClass the exception class to handle
     */
    private void triggerExceptionHandlers(@NotNull Class<? extends Throwable> exceptionClass) {
        try {
            SoundTriggerService.getInstance().playSound(exceptionClass);
            NotificationService.showExceptionNotification(project, exceptionClass);
            
            // Record achievement
            AchievementManager.getInstance().recordException(exceptionClass);

            if (LOG.isDebugEnabled()) {
                LOG.debug("Triggered handlers for: " + exceptionClass.getSimpleName());
            }
        } catch (Exception e) {
            LOG.error("Error triggering exception handlers for: " + exceptionClass.getSimpleName(), e);
        }
    }
}
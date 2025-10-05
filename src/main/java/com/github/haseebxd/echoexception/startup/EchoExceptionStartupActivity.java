package com.github.haseebxd.echoexception.startup;

import com.github.haseebxd.echoexception.services.core.ExceptionConsoleListener;
import com.intellij.execution.ExecutionListener;
import com.intellij.execution.ExecutionManager;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.ProjectActivity;
import com.intellij.util.messages.MessageBusConnection;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class EchoExceptionStartupActivity implements ProjectActivity {
    private static final Logger LOG = Logger.getInstance(EchoExceptionStartupActivity.class);

    @Override
    public @Nullable Object execute(@NotNull Project project, @NotNull Continuation<? super Unit> continuation) {
        LOG.debug("EchoException: EchoExceptionStartupActivity.execute() called for project: " + project.getName());

        try {
            MessageBusConnection connection = project.getMessageBus().connect();
            connection.subscribe(ExecutionManager.EXECUTION_TOPIC, new ExecutionListener() {
                @Override
                public void processStarted(@NotNull String executorId, @NotNull ExecutionEnvironment env, @NotNull ProcessHandler handler) {
                    LOG.debug("EchoException: processStarted called");
                    LOG.debug("EchoException: executorId: " + executorId);
                    LOG.debug("EchoException: ProcessHandler type: " + handler.getClass().getSimpleName());
                    LOG.debug("EchoException: ProcessHandler state: " + (handler.isProcessTerminated() ? "terminated" : "running"));
                    Project project = env.getProject();

                    ExceptionConsoleListener.attachTo(handler,project);
                }

                @Override
                public void processNotStarted(@NotNull String executorId, @NotNull ExecutionEnvironment env, @Nullable Throwable cause) {
                    LOG.debug("EchoException: processNotStarted called for executorId: " + executorId);
                    if (cause != null) {
                        LOG.debug("EchoException: Process failed to start", cause);
                    }
                }

                @Override
                public void processTerminating(@NotNull String executorId, @NotNull ExecutionEnvironment env, @NotNull ProcessHandler handler) {
                    LOG.debug("EchoException: processTerminating called for executorId: " + executorId);
                }

                @Override
                public void processTerminated(@NotNull String executorId, @NotNull ExecutionEnvironment env, @NotNull ProcessHandler handler, int exitCode) {
                    LOG.warn("EchoException: processTerminated called for executorId: " + executorId + " with exit code: " + exitCode);
                }
            });

            LOG.debug("EchoException: Successfully subscribed to ExecutionManager.EXECUTION_TOPIC");
        } catch (Exception e) {
            LOG.warn("EchoException: Failed to subscribe to ExecutionManager.EXECUTION_TOPIC", e);
        }

        return CompletableFuture.completedFuture(null);
    }
}
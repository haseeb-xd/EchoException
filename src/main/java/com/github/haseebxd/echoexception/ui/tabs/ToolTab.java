package com.github.haseebxd.echoexception.ui.tabs;

import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * Interface for tool tabs in the EchoException plugin.
 * Each tab should implement this interface to provide its title and content.
 */
public interface ToolTab {
        @NotNull
        String getTitle();

        @NotNull
        JPanel getContent(@NotNull Project project);
}

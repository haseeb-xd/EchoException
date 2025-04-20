package com.github.haseebxd.echoexception.toolWindow;



import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.content.ContentFactory;
import com.github.haseebxd.echoexception.MyBundle;
import com.github.haseebxd.echoexception.services.MyProjectService;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;


public class MyToolWindowFactory implements ToolWindowFactory {

    private static final Logger LOG = Logger.getInstance(MyToolWindowFactory.class);

    public MyToolWindowFactory() {
        LOG.warn("Don't forget to remove all non-needed sample code files with their corresponding registration entries in `plugin.xml`.");
    }

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        MyToolWindow myToolWindow = new MyToolWindow(toolWindow);
        ContentFactory contentFactory = ContentFactory.getInstance();
        contentFactory.createContent(myToolWindow.getContent(), null, false);
        toolWindow.getContentManager().addContent(contentFactory.createContent(myToolWindow.getContent(), null, false));
    }

    @Override
    public boolean shouldBeAvailable(@NotNull Project project) {
        return true;
    }

    public static class MyToolWindow {

        private final MyProjectService service;

        public MyToolWindow(ToolWindow toolWindow) {
            this.service = toolWindow.getProject().getService(MyProjectService.class);
        }

        public JPanel getContent() {
            JBPanel<JBPanel<?>> panel = new JBPanel<>();
            JBLabel label = new JBLabel(MyBundle.message("randomLabel", "?"));
            panel.add(label);

            JButton button = new JButton(MyBundle.message("shuffle"));
            button.addActionListener(e -> label.setText(MyBundle.message("randomLabel", service.getRandomNumber())));
            panel.add(button);

            return panel;
        }
    }
}


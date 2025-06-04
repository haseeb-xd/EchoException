package com.github.haseebxd.echoexception.toolWindow;


import com.github.haseebxd.echoexception.factory.ToolTabFactory;
import com.github.haseebxd.echoexception.tab.ToolTab;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

public class EchoExceptionToolWindowFactory implements ToolWindowFactory {

    private static final Logger LOG = Logger.getInstance(EchoExceptionToolWindowFactory.class);

    public EchoExceptionToolWindowFactory() {
        LOG.warn("Make sure all sample code is cleaned up before shipping.");
    }

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        ContentFactory contentFactory = ContentFactory.getInstance();

        for (ToolTab tab : ToolTabFactory.getTabs()) {
            Content content = contentFactory.createContent(tab.getContent(project), tab.getTitle(), false);
            toolWindow.getContentManager().addContent(content);
        }
    }

    @Override
    public boolean shouldBeAvailable(@NotNull Project project) {
        return true;
    }
}

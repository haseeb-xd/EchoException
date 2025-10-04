package com.github.haseebxd.echoexception.ui.setting;

import com.github.haseebxd.echoexception.services.core.SoundTriggerService;
import com.github.haseebxd.echoexception.services.settings.SoundSettings;
import com.github.haseebxd.echoexception.sound.GameSoundType;
import com.github.haseebxd.echoexception.ui.tabs.SoundTab;
import com.github.haseebxd.echoexception.ui.tabs.ToolTab;
import com.github.haseebxd.echoexception.ui.tabs.ToolTabFactory;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.project.Project;
import com.intellij.ui.components.JBTabbedPane;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.List;

public class EchoExceptionConfigurable implements Configurable {

    private JTabbedPane tabbedPane;
    private final Project project;
    private SoundTab soundTab;
    
    // Store original settings to detect changes
    private boolean originalSoundEnabled;
    private boolean originalNotificationEnabled;
    private int originalVolume;
    private GameSoundType originalSoundType;

    public EchoExceptionConfigurable(Project project) {
        this.project = project;
    }

    @Override
    public @Nls String getDisplayName() {
        return "Echo Exception";
    }

    @Override
    public @Nullable JComponent createComponent() {
        if (tabbedPane == null) {
            tabbedPane = new JBTabbedPane();
            List<ToolTab> tabs = ToolTabFactory.getTabs();
            for (ToolTab tab : tabs) {
                tabbedPane.addTab(tab.getTitle(), tab.getContent(project));

                if (tab instanceof SoundTab) {
                    this.soundTab = (SoundTab) tab;
                }
            }
            // Capture original settings when component is created
            captureOriginalSettings();
        }
        return tabbedPane;
    }

    @Override
    public boolean isModified() {
        // Only check for modifications if we have a SoundTab (other tabs don't have settings)
        if (soundTab == null) {
            return false;
        }
        
        // Check if any UI values have changed from original settings
        return originalSoundEnabled != soundTab.isSoundEnabled() ||
               originalNotificationEnabled != soundTab.isNotificationEnabled() ||
               originalVolume != soundTab.getVolume() ||
               !originalSoundType.equals(soundTab.getSoundType());
    }

    @Override
    public void apply() {
        // Only apply settings if we have a SoundTab (other tabs don't have settings to apply)
        if (soundTab == null) {
            return;
        }
        
        SoundSettings settings = SoundSettings.getInstance();
        
        // Apply the current UI state to settings
        boolean newSoundEnabled = soundTab.isSoundEnabled();
        boolean newNotificationEnabled = soundTab.isNotificationEnabled();
        int newVolume = soundTab.getVolume();
        GameSoundType newSoundType = soundTab.getSoundType();
        
        settings.setSoundEnabled(newSoundEnabled);
        settings.setNotificationEnabled(newNotificationEnabled);
        settings.setVolume(newVolume);
        settings.setSoundType(newSoundType);
        
        // Reset debounce when sound type changes
        if (!originalSoundType.equals(newSoundType)) {
            SoundTriggerService.getInstance().resetDebounce();
        }
        
        // Update original settings to reflect applied changes
        captureOriginalSettings();
    }

    @Override
    public void reset() {
        // Only reset settings if we have a SoundTab (other tabs don't have settings to reset)
        if (soundTab == null) {
            return;
        }
        
        SoundSettings settings = SoundSettings.getInstance();
        
        // Reset settings to original values
        settings.setSoundEnabled(originalSoundEnabled);
        settings.setNotificationEnabled(originalNotificationEnabled);
        settings.setVolume(originalVolume);
        settings.setSoundType(originalSoundType);
        
        // Reset the UI to reflect the original settings
        soundTab.resetToOriginalSettings();
    }

    @Override
    public void disposeUIResources() {
        tabbedPane = null;
        soundTab = null;
    }
    
    /**
     * Capture the original settings when the component is created
     */
    private void captureOriginalSettings() {
        SoundSettings settings = SoundSettings.getInstance();
        originalSoundEnabled = settings.isSoundEnabled();
        originalNotificationEnabled = settings.isNotificationEnabled();
        originalVolume = settings.getVolume();
        originalSoundType = settings.getSoundType();
    }
}

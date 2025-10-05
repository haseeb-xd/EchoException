package com.github.haseebxd.echoexception.ui.tabs;

import com.github.haseebxd.echoexception.services.core.SoundTriggerService;
import com.github.haseebxd.echoexception.services.settings.SoundSettings;
import com.github.haseebxd.echoexception.sound.GameSoundType;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.ui.components.JBCheckBox;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.components.JBSlider;
import com.intellij.util.ui.JBUI;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

/**
 * Sound configuration tab for the EchoException plugin.
 * This tab allows users to customize sound notifications for exceptions.
 */
public class SoundTab implements ToolTab {

    private static final Logger LOG = Logger.getInstance(SoundTab.class);

    private static final int DEFAULT_VOLUME = 70;
    private static final int SPACING = 20;
    private static final int BORDER_PADDING = 10;


    private JBCheckBox enableSoundCheckbox;
    private JBCheckBox enableNotificationCheckbox;
    private JBSlider volumeSlider;
    private ComboBox<String> soundTypeComboBox;
    private JBLabel volumeLabel;
    private JBLabel soundTypeLabel;



    private final SoundSettings soundSettings;

    public SoundTab() {
        this.soundSettings = SoundSettings.getInstance();
    }

    @Override
    public @NotNull String getTitle() {
        return "Sound";
    }

    @Override
    public @NotNull JPanel getContent(@NotNull Project project) {
        JBPanel<JBPanel<?>> panel = new JBPanel<>();
        panel.setLayout(new BorderLayout());
        panel.setBorder(JBUI.Borders.empty(BORDER_PADDING));


        JBPanel<JBPanel<?>> contentPanel = createContentPanel();
        initializeComponents(project);
        addComponentsToPanel(contentPanel);
        setupEventHandlers();
        loadSavedSettings();

        panel.add(contentPanel, BorderLayout.NORTH);
        return panel;
    }

    private JBPanel<JBPanel<?>> createContentPanel() {
        JBPanel<JBPanel<?>> contentPanel = new JBPanel<>();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(JBUI.Borders.empty());
        contentPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        return contentPanel;
    }

    private void initializeComponents(Project project) {
        enableSoundCheckbox = new JBCheckBox("Enable sound");
        enableSoundCheckbox.setAlignmentX(Component.LEFT_ALIGNMENT);

        enableNotificationCheckbox = new JBCheckBox("Enable notification");
        enableNotificationCheckbox.setAlignmentX(Component.LEFT_ALIGNMENT);

        volumeLabel = new JBLabel("Volume (" + DEFAULT_VOLUME + "/100)");
        volumeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        volumeSlider = new JBSlider(0, 100, DEFAULT_VOLUME);
        volumeSlider.setMaximumSize(new Dimension(Integer.MAX_VALUE, volumeSlider.getPreferredSize().height));

        soundTypeLabel = new JBLabel("Sound type");
        soundTypeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        soundTypeComboBox = new ComboBox<>(
                Arrays.stream(GameSoundType.values())
                        .map(GameSoundType::getDisplayName)
                        .toArray(String[]::new)
        );
        soundTypeComboBox.setSelectedItem(GameSoundType.SATORU_GOJO);
        soundTypeComboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, soundTypeComboBox.getPreferredSize().height));
    }

    private void addComponentsToPanel(JBPanel<JBPanel<?>> panel) {
        // Sound checkbox
        JBPanel<JBPanel<?>> soundPanel = new JBPanel<>(new FlowLayout(FlowLayout.LEFT, 0, 0));
        soundPanel.add(enableSoundCheckbox);
        panel.add(soundPanel);

        panel.add(Box.createVerticalStrut(5));

        // Notification checkbox
        JBPanel<JBPanel<?>> notificationPanel = new JBPanel<>(new FlowLayout(FlowLayout.LEFT, 0, 0));
        notificationPanel.add(enableNotificationCheckbox);
        panel.add(notificationPanel);

        panel.add(Box.createVerticalStrut(SPACING));

        JBPanel<JBPanel<?>> volumeLabelPanel = new JBPanel<>(new FlowLayout(FlowLayout.LEFT, 0, 0));
        volumeLabelPanel.setOpaque(false);
        volumeLabelPanel.add(volumeLabel);
        panel.add(volumeLabelPanel);

        panel.add(volumeSlider);
        panel.add(Box.createVerticalStrut(SPACING));

        JBPanel<JBPanel<?>> soundTypeLabelPanel = new JBPanel<>(new FlowLayout(FlowLayout.LEFT, 0, 0));
        soundTypeLabelPanel.setOpaque(false);
        soundTypeLabelPanel.add(soundTypeLabel);
        panel.add(soundTypeLabelPanel);

        panel.add(soundTypeComboBox);
        panel.add(Box.createVerticalStrut(SPACING));
    }



    private void setupEventHandlers() {
        enableSoundCheckbox.addActionListener(e -> {
            boolean enabled = enableSoundCheckbox.isSelected();
            updateComponentStates(enabled);
            // Settings will be applied through EchoExceptionConfigurable
        });

        enableNotificationCheckbox.addActionListener(e -> {
            // Settings will be applied through EchoExceptionConfigurable
        });

        volumeSlider.addChangeListener(e -> {
            int currentVolume = volumeSlider.getValue();
            volumeLabel.setText("Volume (" + currentVolume + "/100)");
            // Settings will be applied through EchoExceptionConfigurable
        });

        soundTypeComboBox.addActionListener(e -> {
            // Settings will be applied through EchoExceptionConfigurable
        });
    }

    private void updateComponentStates(boolean enabled) {
        volumeSlider.setEnabled(enabled);
        soundTypeComboBox.setEnabled(enabled);
        volumeLabel.setEnabled(enabled);
        soundTypeLabel.setEnabled(enabled);
    }

    private void loadSavedSettings() {
        enableSoundCheckbox.setSelected(soundSettings.isSoundEnabled());
        enableNotificationCheckbox.setSelected(soundSettings.isNotificationEnabled());
        volumeSlider.setValue(soundSettings.getVolume());
        soundTypeComboBox.setSelectedItem(soundSettings.getSoundType().getDisplayName());

        updateComponentStates(soundSettings.isSoundEnabled());
    }
    
    /**
     * Reset the UI to the original settings values
     */
    public void resetToOriginalSettings() {
        enableSoundCheckbox.setSelected(soundSettings.isSoundEnabled());
        enableNotificationCheckbox.setSelected(soundSettings.isNotificationEnabled());
        volumeSlider.setValue(soundSettings.getVolume());
        soundTypeComboBox.setSelectedItem(soundSettings.getSoundType().getDisplayName());
        
        // Update volume label
        int currentVolume = volumeSlider.getValue();
        volumeLabel.setText("Volume (" + currentVolume + "/100)");
        
        updateComponentStates(soundSettings.isSoundEnabled());
    }
    
    /**
     * Get current sound enabled state from UI
     */
    public boolean isSoundEnabled() {
        return enableSoundCheckbox.isSelected();
    }
    
    /**
     * Get current notification enabled state from UI
     */
    public boolean isNotificationEnabled() {
        return enableNotificationCheckbox.isSelected();
    }
    
    /**
     * Get current volume from UI
     */
    public int getVolume() {
        return volumeSlider.getValue();
    }
    
    /**
     * Get current sound type from UI
     */
    public GameSoundType getSoundType() {
        String selectedType = (String) soundTypeComboBox.getSelectedItem();
        return GameSoundType.fromDisplayName(selectedType);
    }
}
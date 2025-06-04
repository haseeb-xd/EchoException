package com.github.haseebxd.echoexception.tab;

import com.github.haseebxd.echoexception.settings.SoundSettings;
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

/**
 * Sound configuration tab for the EchoException plugin.
 * This tab allows users to customize sound notifications for exceptions.
 */
public class SoundTab implements ToolTab {

    private static final Logger LOG = Logger.getInstance(VisualTab.class);

    private static final String[] SOUND_TYPES = {"Counter-Strike", "Valorant: Sage","Naruto", "Mario"};
    private static final String DEFAULT_SOUND_TYPE = "Counter-Strike";
    private static final int DEFAULT_VOLUME = 50;
    private static final int SPACING = 20;
    private static final int BORDER_PADDING = 10;


    private JBCheckBox enableSoundCheckbox;
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
        return contentPanel;
    }

    private void initializeComponents(Project project) {

        enableSoundCheckbox = new JBCheckBox("Enable sound");
        enableSoundCheckbox.setAlignmentX(Component.LEFT_ALIGNMENT);


        volumeLabel = new JBLabel("Volume (" + DEFAULT_VOLUME + "/100)");
        volumeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        volumeSlider = new JBSlider(0, 100, DEFAULT_VOLUME);
        volumeSlider.setAlignmentX(Component.LEFT_ALIGNMENT);
        volumeSlider.setMaximumSize(new Dimension(Integer.MAX_VALUE, volumeSlider.getPreferredSize().height));


        soundTypeLabel = new JBLabel("Sound type");
        soundTypeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        soundTypeComboBox = new ComboBox<>(SOUND_TYPES);
        soundTypeComboBox.setSelectedItem(DEFAULT_SOUND_TYPE);
        soundTypeComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        soundTypeComboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, soundTypeComboBox.getPreferredSize().height));

    }

    private void addComponentsToPanel(JBPanel<JBPanel<?>> panel) {

        panel.add(enableSoundCheckbox);
        panel.add(Box.createVerticalStrut(SPACING));


        panel.add(volumeLabel);
        panel.add(volumeSlider);
        panel.add(Box.createVerticalStrut(SPACING));


        panel.add(soundTypeLabel);
        panel.add(soundTypeComboBox);
        panel.add(Box.createVerticalStrut(SPACING));

    }

    private void setupEventHandlers() {
        enableSoundCheckbox.addActionListener(e -> {
            boolean enabled = enableSoundCheckbox.isSelected();
            updateComponentStates(enabled);
            soundSettings.setEnabled(enabled);
        });


        volumeSlider.addChangeListener(e -> {
            soundSettings.setVolume(volumeSlider.getValue());
            int currentVolume = volumeSlider.getValue();
            volumeLabel.setText("Volume (" + currentVolume + "/100)");
        });

        soundTypeComboBox.addActionListener(e -> {
            soundSettings.setSoundType((String) soundTypeComboBox.getSelectedItem());
        });
    }

    private void updateComponentStates(boolean enabled) {
        volumeSlider.setEnabled(enabled);
        soundTypeComboBox.setEnabled(enabled);
        volumeLabel.setEnabled(enabled);
        soundTypeLabel.setEnabled(enabled);
    }

    private void loadSavedSettings() {
        enableSoundCheckbox.setSelected(soundSettings.isEnabled());
        volumeSlider.setValue(soundSettings.getVolume());
        soundTypeComboBox.setSelectedItem(soundSettings.getSoundType());

        updateComponentStates(soundSettings.isEnabled());
    }
}
package com.github.haseebxd.echoexception.tab;


import com.github.haseebxd.echoexception.settings.VisualSettings;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.ui.components.JBCheckBox;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBPanel;
import com.intellij.util.ui.JBUI;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

/**
 * Visual configuration tab for the EchoException plugin.
 * This tab allows users to customize visual effects for exceptions.
 */
public class VisualTab implements ToolTab {

    private static final Logger LOG = Logger.getInstance(VisualTab.class);

    private static final String[] VISUAL_EFFECTS = {"Fire", "Explosion", "Glitch", "Shockwave"};
    private static final int DEFAULT_DURATION = 3;
    private static final int SPACING = 20;
    private static final int BORDER_PADDING = 10;


    private JBCheckBox enableVisualsCheckbox;
    private ComboBox<String> visualEffectComboBox;
    private ComboBox<Integer> durationComboBox;
    private JBLabel visualEffectLabel;
    private JBLabel durationLabel;


    private final VisualSettings visualSettings;

    public VisualTab() {
        this.visualSettings = VisualSettings.getInstance();
    }

    @Override
    public @NotNull String getTitle() {
        return "Visual";
    }

    @Override
    public @NotNull JPanel getContent(@NotNull Project project) {
        JBPanel<JBPanel<?>> panel = new JBPanel<>();
        panel.setLayout(new BorderLayout());
        panel.setBorder(JBUI.Borders.empty(BORDER_PADDING));

        JBPanel<JBPanel<?>> contentPanel = createContentPanel();
        initializeComponents();
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

    private void initializeComponents() {
        enableVisualsCheckbox = new JBCheckBox("Enable visuals");
        enableVisualsCheckbox.setAlignmentX(Component.LEFT_ALIGNMENT);

        visualEffectLabel = new JBLabel("Visual effect");
        visualEffectLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        visualEffectComboBox = new ComboBox<>(VISUAL_EFFECTS);
        visualEffectComboBox.setSelectedItem(VISUAL_EFFECTS[0]);
        visualEffectComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        visualEffectComboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, visualEffectComboBox.getPreferredSize().height));

        durationLabel = new JBLabel("Duration (seconds)");
        durationLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        Integer[] durationOptions = {3,5,7,11};
        durationComboBox = new ComboBox<>(durationOptions);
        durationComboBox.setSelectedItem(DEFAULT_DURATION);
        durationComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        durationComboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, durationComboBox.getPreferredSize().height));


    }

    private void addComponentsToPanel(JBPanel<JBPanel<?>> panel) {
        panel.add(enableVisualsCheckbox);
        panel.add(Box.createVerticalStrut(SPACING));

        panel.add(visualEffectLabel);
        panel.add(visualEffectComboBox);
        panel.add(Box.createVerticalStrut(SPACING));

        panel.add(durationLabel);
        panel.add(durationComboBox);
    }

    private void setupEventHandlers() {
        enableVisualsCheckbox.addActionListener(e -> {
            boolean enabled = enableVisualsCheckbox.isSelected();
            updateComponentStates(enabled);
            visualSettings.setEnabled(enabled);
        });

        visualEffectComboBox.addActionListener(e -> {
            String selectedEffect = (String) visualEffectComboBox.getSelectedItem();
            visualSettings.setVisualEffect(selectedEffect);
        });

        durationComboBox.addActionListener(e -> {
            Integer selectedDuration = (Integer) durationComboBox.getSelectedItem();
            if (selectedDuration != null) {
                visualSettings.setDurationSeconds(selectedDuration);
            }
        });
    }

    private void updateComponentStates(boolean enabled) {
        visualEffectComboBox.setEnabled(enabled);
        durationComboBox.setEnabled(enabled);
        visualEffectLabel.setEnabled(enabled);
        durationLabel.setEnabled(enabled);
    }

    private void loadSavedSettings() {
        enableVisualsCheckbox.setSelected(visualSettings.isEnabled());
        visualEffectComboBox.setSelectedItem(visualSettings.getVisualEffect());
        durationComboBox.setSelectedItem(visualSettings.getDurationSeconds());

        updateComponentStates(visualSettings.isEnabled());
    }
}



package com.github.haseebxd.echoexception.ui.tabs;

import java.util.List;

/**
 * Factory class to create instances of tool tabs for the EchoException plugin.
 * This class provides a static method to retrieve a list of available tool tabs.
 */
public class ToolTabFactory {
    public static List<ToolTab> getTabs() {
        return List.of(
                new SoundTab(),
                new AchievementTab()
                // Add other tabs here as needed
        );
    }
}


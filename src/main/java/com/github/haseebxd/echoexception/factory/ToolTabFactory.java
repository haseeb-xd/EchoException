package com.github.haseebxd.echoexception.factory;

import com.github.haseebxd.echoexception.tab.SoundTab;
import com.github.haseebxd.echoexception.tab.ToolTab;
import com.github.haseebxd.echoexception.tab.VisualTab;

import java.util.List;

/**
 * Factory class to create instances of tool tabs for the EchoException plugin.
 * This class provides a static method to retrieve a list of available tool tabs.
 */
public class ToolTabFactory {
    public static List<ToolTab> getTabs() {
        return List.of(
                new SoundTab(),
                new VisualTab()
        );
    }
}


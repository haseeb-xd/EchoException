package com.github.haseebxd.echoexception.sound;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum GameSoundType {

//    COUNTER_STRIKE("Counter-Strike"),
    VALORANT_SAGE("Valorant: Sage"),
    SATORU_GOJO("Satoru Gojo");
//    NARUTO("Naruto");

    private final String displayName;

    GameSoundType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    private static final Map<String, GameSoundType> DISPLAY_NAME_MAP =
            Arrays.stream(values()).collect(Collectors.toMap(GameSoundType::getDisplayName, e -> e));


    public static GameSoundType fromDisplayName(String name) {
        return DISPLAY_NAME_MAP.getOrDefault(name, VALORANT_SAGE); // Default fallback
    }

}

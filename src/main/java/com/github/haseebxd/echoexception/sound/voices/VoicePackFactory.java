package com.github.haseebxd.echoexception.sound.voices;

import com.github.haseebxd.echoexception.sound.GameSoundType;

public class VoicePackFactory {
        public static VoicePack getVoicePack(GameSoundType type) {
            return switch (type) {
                case VALORANT_SAGE -> new ValorantSageVoice();
                case SATORU_GOJO -> new GojoVoice();
                default -> throw new IllegalArgumentException("Unsupported sound type: " + type);
            };
        }

}

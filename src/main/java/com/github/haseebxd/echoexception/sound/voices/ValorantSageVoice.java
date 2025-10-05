package com.github.haseebxd.echoexception.sound.voices;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ValorantSageVoice implements VoicePack{

    private static final Map<Class<? extends Throwable>, String> exceptionSoundMap = new HashMap<>();
    private static final Map<Class<? extends Throwable>, String> exceptionToastMap = new HashMap<>();

    static {
        exceptionSoundMap.put(NullPointerException.class, "voice-packs/valorant-sage/null-pointer-exception-sage.wav");
        exceptionSoundMap.put(ArrayIndexOutOfBoundsException.class, "voice-packs/valorant-sage/array-index-out-of-bounds-exception-sage.wav");
        exceptionSoundMap.put(ArithmeticException.class, "voice-packs/valorant-sage/arithmetic-exception-sage.wav");
        exceptionSoundMap.put(NumberFormatException.class, "voice-packs/valorant-sage/number-format-exception-sage.wav");
        exceptionSoundMap.put(IllegalArgumentException.class, "voice-packs/valorant-sage/illegal-argument-exception-sage.wav");
        exceptionSoundMap.put(IOException.class, "voice-packs/valorant-sage/io-exception-sage.wav");
        exceptionSoundMap.put(ClassNotFoundException.class, "voice-packs/valorant-sage/class-not-found-exception-sage.wav");
        exceptionSoundMap.put(StackOverflowError.class, "voice-packs/valorant-sage/stack-overflow-sage.wav");
        exceptionSoundMap.put(IllegalStateException.class, "voice-packs/valorant-sage/illegal-state-exception-sage.wav");

        // Notification messages
        exceptionToastMap.put(NullPointerException.class, "SAGE line.");
        exceptionToastMap.put(ArrayIndexOutOfBoundsException.class, "Index is out of bounds. Check your six.");
        exceptionToastMap.put(ArithmeticException.class, "Sage: Divide by zero? Let's not blow things up... yet.");
        exceptionToastMap.put(NumberFormatException.class, "That's not a number! Try aiming again.");
        exceptionToastMap.put(IllegalArgumentException.class, "Bad argument. Are you even trying?");
        exceptionToastMap.put(IOException.class, "IO failed. Sounds like a jammed comms line.");
        exceptionToastMap.put(ClassNotFoundException.class, "Missing class? Request backup immediately.");
        exceptionToastMap.put(StackOverflowError.class, "Stack overflow! Too many calculations. Retreat!");
        exceptionToastMap.put(IllegalStateException.class, "Illegal state? Looks like chaos out there.");
    }

    @Override
    public String getSoundForException(Class<? extends Throwable> exceptionType) {
        return exceptionSoundMap.getOrDefault(exceptionType, "voice-packs/cs/default_bang.wav");
    }

    @Override
    public String getNotificationForException(Class<? extends Throwable> exceptionType) {
        return exceptionToastMap.getOrDefault(exceptionType, "Something exploded. Keep it cool.");
    }




}

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
        exceptionToastMap.put(NullPointerException.class, "Null Pointer Exception. That's not something I can revive.");
        exceptionToastMap.put(ArrayIndexOutOfBoundsException.class, "Array Index Out Of Bounds Exception. You tried to push beyond your limits and failed.");
        exceptionToastMap.put(ArithmeticException.class, "Arithmetic Exception! Divide by zero again, I can't revive your logic every time");
        exceptionToastMap.put(NumberFormatException.class, "Number Format Exception. That’s not a number, no matter how much you believe in it.");
        exceptionToastMap.put(IllegalArgumentException.class, "Illegal Argument Exception. That input wasn’t meant to be… not even I can purify that.");
        exceptionToastMap.put(IOException.class, "IO Exception. I felt that disconnect. Something isn’t reaching through.");
        exceptionToastMap.put(ClassNotFoundException.class, "Class Not Found Exception. I can’t heal what doesn’t exist.");
        exceptionToastMap.put(StackOverflowError.class, "Stack Overflow Error. You pushed too hard. Even my barrier has limits.");
        exceptionToastMap.put(IllegalStateException.class, "Illegal State Exception. You're out of step… the rhythm is broken.");
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

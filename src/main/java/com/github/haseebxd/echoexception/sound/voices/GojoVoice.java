package com.github.haseebxd.echoexception.sound.voices;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GojoVoice implements VoicePack {

    private static final Map<Class<? extends Throwable>, String> EXCEPTION_SOUND_MAP = new HashMap<>();
    private static final Map<Class<? extends Throwable>, String> exceptionToastMap = new HashMap<>();

    static {
        EXCEPTION_SOUND_MAP.put(NullPointerException.class, "voice-packs/jujutsu-kaisen-gojo/null-pointer-exception-gojo.wav");
        EXCEPTION_SOUND_MAP.put(ArrayIndexOutOfBoundsException.class, "voice-packs/jujutsu-kaisen-gojo/array-index-out-of-bounds-exception-gojo.wav");
        EXCEPTION_SOUND_MAP.put(ArithmeticException.class, "voice-packs/jujutsu-kaisen-gojo/arithmetic-exception-gojo.wav");
        EXCEPTION_SOUND_MAP.put(NumberFormatException.class, "voice-packs/jujutsu-kaisen-gojo/number-format-exception-gojo.wav");
        EXCEPTION_SOUND_MAP.put(ClassNotFoundException.class, "voice-packs/jujutsu-kaisen-gojo/class-not-found-exception-gojo.wav");
        EXCEPTION_SOUND_MAP.put(IllegalStateException.class, "voice-packs/jujutsu-kaisen-gojo/illegal-state-exception-gojo.wav");
        EXCEPTION_SOUND_MAP.put(StackOverflowError.class, "voice-packs/jujutsu-kaisen-gojo/stack-overflow-gojo.wav");
        EXCEPTION_SOUND_MAP.put(IOException.class, "voice-packs/jujutsu-kaisen-gojo/io-exception-gojo.wav");
        EXCEPTION_SOUND_MAP.put(IllegalArgumentException.class, "voice-packs/jujutsu-kaisen-gojo/illegal-argument-exception-gojo.wav");
        EXCEPTION_SOUND_MAP.put(FileNotFoundException.class, "voice-packs/jujutsu-kaisen-gojo/io-exception-gojo.wav");


        exceptionToastMap.put(NullPointerException.class, "NullPointer huh? Keep your objects alive, rookie.");
        exceptionToastMap.put(ArrayIndexOutOfBoundsException.class, "Index is out of bounds. Check your six.");
        exceptionToastMap.put(ArithmeticException.class, "Divide by zero? Let's not blow things up... yet.");
        exceptionToastMap.put(NumberFormatException.class, "That's not a number! Try aiming again.");
        exceptionToastMap.put(IllegalArgumentException.class, "Bad argument. Are you even trying?");
        exceptionToastMap.put(IOException.class, "IO failed. Sounds like a jammed comms line.");
        exceptionToastMap.put(ClassNotFoundException.class, "Missing class? Request backup immediately.");
        exceptionToastMap.put(StackOverflowError.class, "Stack overflow! Too many calculations. Retreat!");
        exceptionToastMap.put(IllegalStateException.class, "Illegal state? Looks like chaos out there.");
    }

    @Override
    public String getSoundForException(Class<? extends Throwable> exceptionType) {
        return EXCEPTION_SOUND_MAP.getOrDefault(exceptionType, "voice-packs/cs/default_bang.wav");
    }

    @Override
    public String getNotificationForException(Class<? extends Throwable> exceptionType) {
        return exceptionToastMap.getOrDefault(exceptionType, "Something exploded. Keep it cool.");
    }
}
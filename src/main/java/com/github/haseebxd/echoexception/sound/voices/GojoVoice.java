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


        exceptionToastMap.put(NullPointerException.class, "Gojo: Null again. What are you! Blindfolded like me but without the power?");
        exceptionToastMap.put(ArrayIndexOutOfBoundsException.class, "Gojo: That index doesn’t exist. like your chances against me.");
        exceptionToastMap.put(ArithmeticException.class, "Gojo: Tried to flex on math and got snapped rookie move.");
        exceptionToastMap.put(NumberFormatException.class, "Gojo: You gave numbers a panic attack. Nice..");
        exceptionToastMap.put(IllegalArgumentException.class, "Gojo: That’s the best argument you’ve got? Pathetic.");
        exceptionToastMap.put(IOException.class, "Gojo: The system said 'no.' Even your files are scared of you.");
        exceptionToastMap.put(ClassNotFoundException.class, "Gojo: Class not found? Guess even your code knows what’s out of its league.");
        exceptionToastMap.put(StackOverflowError.class, "Gojo: Ah! Too deep for your own good? Even infinity has its limits.");
        exceptionToastMap.put(IllegalStateException.class, "Gojo: Code’s out of state, just like your sense of direction.");
        exceptionToastMap.put(FileNotFoundException.class, "Gojo: The system said 'no.' Even your files are scared of you.");
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
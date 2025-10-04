package com.github.haseebxd.echoexception.sound.voices;

public interface VoicePack {

    String getSoundForException(Class<? extends Throwable> exceptionType);

    String getNotificationForException(Class<? extends Throwable> exceptionType);


}

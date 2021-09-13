package com.backend.passthemon.utils.keyutils;

public class KeyUtil {
    public static String ChatKey(Integer channelId) {
        return "chatDto::channelId-" + channelId.toString();
    }

    public static String ChatKeyPrefix(){
        return "chatDto";
    }
}

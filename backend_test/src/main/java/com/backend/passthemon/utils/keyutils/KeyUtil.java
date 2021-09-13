package com.backend.passthemon.utils.keyutils;

public class KeyUtil {
    public static String ChannelKey(Integer author, Integer recipient){
        return "channel::" + author.toString() + "-" + recipient.toString();
    }

    public static String ChatKey(Integer channelId) {
        return "chatDto::channelId-" + channelId.toString();
    }

    public static String ChatKeyPrefix(){
        return "chatDto::channelId";
    }

    public static String UserEmailKey(String email) {
        return "user::email-"+email;
    }

    public static String UserIdKey(Integer id) {
        return "user::userId-" + id.toString();
    }
}

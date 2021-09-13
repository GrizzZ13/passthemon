package com.backend.passthemon.utils.wsutils;

import com.backend.passthemon.controller.WebSocketServer;

import java.util.concurrent.ConcurrentHashMap;

public class WebSocketUtil {
    private static final ConcurrentHashMap<Integer, WebSocketServer> websocketMap = new ConcurrentHashMap<>();
    private static Integer onlineCount = 0;

    public static void put(Integer userid, WebSocketServer webSocketServer){
        websocketMap.put(userid, webSocketServer);
        addOnlineCount();
    }

    public static void remove(Integer userid) {
        websocketMap.remove(userid);
        subOnlineCount();
    }

    public static Integer onlineCount() {
        return onlineCount;
    }

    public static boolean containsKey(Integer key) {
        return websocketMap.containsKey(key);
    }

    private static synchronized void addOnlineCount() {
        onlineCount++;
    }

    private static synchronized void subOnlineCount() {
        onlineCount--;
    }

    public static WebSocketServer get(Integer key){
        return websocketMap.get(key);
    }
}

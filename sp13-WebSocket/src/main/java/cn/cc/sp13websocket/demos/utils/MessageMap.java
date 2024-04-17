package cn.cc.sp13websocket.demos.utils;

import javax.websocket.Session;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description
 * @Author everforcc
 * @Date 2024-04-17 21:57
 * Copyright
 */
public class MessageMap {

    private static Map<String, Session> onlineSessionClientMap = new ConcurrentHashMap<>();

    public static Map<String, Session> getSessionMap(){
        return onlineSessionClientMap;
    }

}

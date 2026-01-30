package cn.cc.xiaoyu.server.handler;

import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;

public class XiaoyuChannelCache {

    public static Map<String, Channel> cache = new HashMap<>();

    public static Map<String,XiaoyuChannelHandler> handlerCache = new HashMap<>();

}

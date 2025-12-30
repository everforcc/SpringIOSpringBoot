package cn.cc.xiaoyu;

import cn.cc.xiaoyu.server.XiaoyuServer;

import javax.net.ssl.SSLException;

public class XiaoyuServerTest {

    public static void main(String[] args) {
       XiaoyuServer.start(9999);
    }


}

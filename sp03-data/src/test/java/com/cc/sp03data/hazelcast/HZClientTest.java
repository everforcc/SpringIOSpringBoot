/**
 * @Description
 * @Author everforcc
 * @Date 2022-11-01 17:18
 * Copyright
 */

package com.cc.sp03data.hazelcast;

import org.junit.Before;
import org.junit.Test;

public class HZClientTest {

    @Before
    public void init() {
        System.out.println("目前初始化写死到代码,直接去类里面改环境");
        HZServerDto currentServer = HZClientMap.currentServer;
        // 只有写入 map 的key 可以替换
        currentServer.setMapKey("test-init-map-key");
    }

    @Test
    public void tIMapWrite() {
        HZClientMap.writeStrWithMap();
    }

    @Test
    public void tIMapRead() {
        HZClientMap.readStrWithMap();
    }

}

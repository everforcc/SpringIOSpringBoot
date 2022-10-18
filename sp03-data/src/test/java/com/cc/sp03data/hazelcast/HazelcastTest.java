/**
 * @Description
 * @Author everforcc
 * @Date 2022-10-18 16:50
 * Copyright
 */

package com.cc.sp03data.hazelcast;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import org.junit.Before;
import org.junit.Test;

public class HazelcastTest {

    private static HazelcastInstance hz;

    /**
     * 初始化类型 1
     */
    //@Before
    public void init() {
        //        System.setProperty("hazelcast.local.localAddress", "127.0.0.1");
//        System.setProperty("hazelcast.local.localAddress", "127.0.0.1");
        Config config = new Config();
        config.getNetworkConfig().setPort(9230);
        config.getNetworkConfig().setPublicAddress("150.158.12.83");
        // Start the Hazelcast Client and connect to an already running Hazelcast Cluster on 127.0.0.1

        //HazelcastInstance hz = HazelcastClient.newHazelcastClient(config);
        hz = Hazelcast.newHazelcastInstance(config);
        System.out.println("初始化 Hazelcast 配置完成 - 1");
    }

    /**
     * 初始化类型 2
     */
    @Before
    public void init_2() {
        ClientConfig clientConfig = new ClientConfig();
        //clientConfig.setClusterName("dev");
        //clientConfig.getNetworkConfig().addAddress("10.90.0.1", "10.90.0.2:5702");
        clientConfig.getNetworkConfig().addAddress("150.158.12.83:9230");

        hz = HazelcastClient.newHazelcastClient(clientConfig);
        System.out.println("初始化 Hazelcast 配置完成 - 2");
    }

    /**
     * 测试map
     */
    @Test
    public void map() {

        // Get the Distributed Map from Cluster.
        IMap map = hz.getMap("my-distributed-map");
        //Standard Put and Get.
        map.put("key", "value");
        Object valueKey = map.get("key");
        System.out.println("valueKey: " + valueKey);
        //Concurrent Map methods, optimistic updating
        map.putIfAbsent("somekey", "somevalue");
        map.replace("key", "value", "newvalue");

        Object valueSomeKey = map.get("somekey");
        System.out.println("valueSomeKey: " + valueSomeKey);
        // Shutdown this Hazelcast client
        hz.shutdown();
    }

}

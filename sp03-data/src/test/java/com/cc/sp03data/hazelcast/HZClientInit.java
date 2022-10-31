/**
 * @Description
 * @Author everforcc
 * @Date 2022-10-31 20:43
 * Copyright
 */

package com.cc.sp03data.hazelcast;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

/**
 * 初始化HZ 客户端
 */
public class HZClientInit {

    // 客户端的参数
    private static final String clusterName = HZServerBuildCluster.clusterName;
    private static final String address_1 = "127.0.0.1:5701";
    private static final String address_2 = "127.0.0.1:5702";
    private static final String address_3 = "127.0.0.1:5703";

    private static final String address_cloud_ip = "150.158.12.83";
    private static final int address_cloud_port = 9230;

    private static HazelcastInstance hzClient;

    /**
     * 初始化类型 1
     */
    public static HazelcastInstance init() {
        //        System.setProperty("hazelcast.local.localAddress", "127.0.0.1");
//        System.setProperty("hazelcast.local.localAddress", "127.0.0.1");
        Config config = new Config();
        config.getNetworkConfig().setPort(address_cloud_port);
        config.getNetworkConfig().setPublicAddress(address_cloud_ip);
        // Start the Hazelcast Client and connect to an already running Hazelcast Cluster on 127.0.0.1

        //HazelcastInstance hz = HazelcastClient.newHazelcastClient(config);
        hzClient = Hazelcast.newHazelcastInstance(config);
        System.out.println("初始化 Hazelcast 配置完成 - 1");
        return hzClient;
    }

    /**
     * 初始化类型 2
     */
    public static HazelcastInstance init_2() {
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setClusterName(clusterName);
        clientConfig.getNetworkConfig().addAddress(address_1, address_2, address_3);
        hzClient = HazelcastClient.newHazelcastClient(clientConfig);
        System.out.println("初始化 Hazelcast 配置完成 - 2");
        return hzClient;
    }

}

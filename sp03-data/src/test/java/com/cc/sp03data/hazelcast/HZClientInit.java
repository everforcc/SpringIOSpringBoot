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

    // 蜗牛共享
    private static final String address_cloud_wn_ip = "150.158.12.83";
    private static final int address_cloud_wn_port = 9230;

    private static final String address_cloud_txy_address_1 = "43.143.228.164:5701";
    private static final String address_cloud_txy_address_2 = "43.143.228.164:5702";
    private static final String address_cloud_txy_address_3 = "43.143.228.164:5703";
    private static final String address_cloud_txy_clusterName = "txy-hello-world";

    private static HazelcastInstance hzClient;

    /**
     * 初始化一个
     */
    public static HazelcastInstance init(HZServerDto hzServerDto) {
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setClusterName(hzServerDto.getClusterName());
        clientConfig.getNetworkConfig().setAddresses(hzServerDto.getAddressList());
        hzClient = HazelcastClient.newHazelcastClient(clientConfig);
        System.out.println("初始化 Hazelcast 配置完成 - txy");
        return hzClient;
    }

    /**
     * 初始化类型 2
     */
    public static HazelcastInstance init_local() {
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setClusterName(clusterName);
        clientConfig.getNetworkConfig().addAddress(address_1, address_2, address_3);
        hzClient = HazelcastClient.newHazelcastClient(clientConfig);
        System.out.println("初始化 Hazelcast 配置完成 - local");
        return hzClient;
    }

//    /**
//     * 初始化类型
//     * 这种类型初始化会导致 map 可视化界面找不到数据?
//     */
//    public static HazelcastInstance init() {
//        //        System.setProperty("hazelcast.local.localAddress", "127.0.0.1");
////        System.setProperty("hazelcast.local.localAddress", "127.0.0.1");
//        Config config = new Config();
////        config.getNetworkConfig().setPort(address_cloud_txy_port);
////        config.getNetworkConfig().setPublicAddress(address_cloud_txy_ip);
//        config.setClusterName(address_cloud_txy_clusterName);
//        // Start the Hazelcast Client and connect to an already running Hazelcast Cluster on 127.0.0.1
//
//        //HazelcastInstance hz = HazelcastClient.newHazelcastClient(config);
//        hzClient = Hazelcast.newHazelcastInstance(config);
//        System.out.println("初始化 Hazelcast 配置完成");
//        return hzClient;
//    }

}

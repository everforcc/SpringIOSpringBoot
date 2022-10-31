/**
 * @Description
 * @Author everforcc
 * @Date 2022-10-25 10:13
 * Copyright
 */

package com.cc.sp03data.hazelcast;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

/**
 * 构件 HZ 集群
 * 服务端
 */
public class HZServerBuildCluster {

    /**
     * 集群名
     */
    public static final String clusterName = "hello-world";

    /**
     * 构建HZ集群
     *
     * @param clusterName 集群名
     */
    public static void initHazelcastInstanceCluster(String clusterName) {
        Config helloWorldConfig = new Config();
        helloWorldConfig.setClusterName(clusterName);
        HazelcastInstance hz = Hazelcast.newHazelcastInstance(helloWorldConfig);
        HazelcastInstance hz2 = Hazelcast.newHazelcastInstance(helloWorldConfig);
        HazelcastInstance hz3 = Hazelcast.newHazelcastInstance(helloWorldConfig);
    }


    public static void main(String[] args) {
        // 开启集群
        initHazelcastInstanceCluster(clusterName);
    }

}

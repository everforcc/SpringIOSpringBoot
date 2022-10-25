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

public class BuildACluster {

    public static void main(String[] args) {
        Config helloWorldConfig = new Config();
        helloWorldConfig.setClusterName("hello-world");

        HazelcastInstance hz = Hazelcast.newHazelcastInstance(helloWorldConfig);
        HazelcastInstance hz2 = Hazelcast.newHazelcastInstance(helloWorldConfig);
        HazelcastInstance hz3 = Hazelcast.newHazelcastInstance(helloWorldConfig);
    }

}

/**
 * @Description
 * @Author everforcc
 * @Date 2022-10-25 10:17
 * Copyright
 */

package com.cc.sp03data.hazelcast;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

public class DealData {

    /**
     * 用map写入数据
     */
    public static void writeDataWithMap() {
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setClusterName("hello-world");

        HazelcastInstance client = HazelcastClient.newHazelcastClient(clientConfig);
        IMap<Integer, String> map = client.getMap("my-distributed-map");

        int i = 0;

        map.put(i++, "John" + i++);
        map.put(i++, "Mary" + i++);
        map.put(i++, "Jane" + i++);
        map.put(i++, "Jane" + i++);
        map.put(i++, "Jane" + i++);
        map.put(i++, "Jane" + i++);
        map.put(i++, "Jane" + i++);
        map.put(i++, "Jane" + i++);
        map.put(i++, "Jane" + i++);

        // 关了，要不会一直存在
        client.shutdown();
    }

    /**
     * 用map读
     */
    public static void readDataWithMap() {
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setClusterName("hello-world");

        HazelcastInstance client = HazelcastClient.newHazelcastClient(clientConfig);

        IMap map = client.getMap("my-distributed-map");
        // 文档写的是
        // for (Map.Entry<String, String> entry : map.entrySet()) {
        for (Object entry : map.entrySet()) {
            System.out.println("entry.toString(): " + entry.toString());
        }

        client.shutdown();
    }

    /**
     * 命令行客户端
     */
    public static void readDataWithSql() {

    }

    public static void main(String[] args) {
        //writeDataWithMap();
        readDataWithMap();
    }

}

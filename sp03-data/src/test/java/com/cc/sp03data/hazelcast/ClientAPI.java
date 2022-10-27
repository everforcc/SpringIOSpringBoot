/**
 * @Description
 * @Author everforcc
 * @Date 2022-10-27 10:39
 * Copyright
 */

package com.cc.sp03data.hazelcast;

import com.alibaba.fastjson.JSONObject;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

public class ClientAPI {

    private static final String clusterName = "hello-world";
    private static final String address_1 = "127.0.0.1:5701";
    private static final String address_2 = "127.0.0.1:5702";
    private static final String address_3 = "127.0.0.1:5703";
    private static HazelcastInstance client = null;


    public static void main(String[] args) {
        // 初始化客户端
        init();

        // 写数据
        //addDataObj();

        // 读数据
        readDataObj();

        // 清除数据
        //clearDataObj();

        // 关闭
        shutDown();
    }


    /**
     * 第一部配置客户端
     */
    public static void init() {
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setClusterName(clusterName);
        clientConfig.getNetworkConfig().addAddress(address_1, address_2, address_3);
        client = HazelcastClient.newHazelcastClient(clientConfig);
    }

    public static void addDataObj() {
        IMap<String, Customer> mapCustomers = client.getMap("customers"); //creates the map proxy

        mapCustomers.put("1", new Customer("Joe", "Smith"));
        mapCustomers.put("2", new Customer("Ali", "Selam"));
        mapCustomers.put("3", new Customer("Avi", "Noyan"));
    }

    public static void addDataStr() {
        IMap<String, String> mapCustomers = client.getMap("customers"); //creates the map proxy

        mapCustomers.put("1", "a");
        mapCustomers.put("2", "b");
        mapCustomers.put("3", "c");
    }

    public static void readDataObj() {
        IMap<String, Customer> mapCustomers = client.getMap("customers"); //creates the map proxy

        Customer customer_1 = mapCustomers.get("1");
        System.out.println(customer_1);

        Customer customer_4 = mapCustomers.get("4");
        System.out.println(Objects.isNull(customer_4));
    }

    public static void clearDataObj() {
        IMap<String, Customer> mapCustomers = client.getMap("customers");
        mapCustomers.clear();
    }

    public static void shutDown() {
        client.shutdown();
    }

    /**
     * 对象需要序列化
     */
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class Customer implements Serializable {
        private String name;
        private String xxx;

        @Override
        public String toString() {
            return JSONObject.toJSONString(this);
        }
    }

}

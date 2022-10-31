/**
 * @Description
 * @Author everforcc
 * @Date 2022-10-31 20:49
 * Copyright
 */

package com.cc.sp03data.hazelcast;

import com.alibaba.fastjson.JSONObject;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;

/**
 * 用客户端处理map
 */
public class HZClientMap {

    // 拿到客户端
    private static HazelcastInstance hzClient = HZClientInit.init_2();
    // 拿到 hz 内map str 的 key
    private static final String hzMapStr = "my-distributed-map-str-2";
    // 拿到 hz 内map obj 的 key
    private static final String hzMapObj = "my-distributed-map-obj";


    public static void main(String[] args) {
        // 写数据
        //writeStrWithMap();
        // 读数据
        //readStrWithMap();

        // 读写对象
        //writeObjWithMap();
        readObjWithMap();

        // 关闭客户端
        shutDown();
    }

    /**
     * 用map写入 str
     */
    public static void writeStrWithMap() {
        IMap<String, String> map = hzClient.getMap(hzMapStr);
        for (int i = 0; i < 10; i++) {
            map.put("k" + i, "John" + i);
        }
    }

    /**
     * 用map读 str
     */
    public static void readStrWithMap() {
        // 必须要指定类型,否则 map.entrySet() 无法判断类型
        IMap<String, String> map = hzClient.getMap(hzMapStr);
        // 文档写的是
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            System.out.println("key: " + key + "\r\nvalue: " + value);
        }
    }

    /**
     * 用map写入 obj
     */
    public static void writeObjWithMap() {
        IMap<String, Customer> mapCustomers = hzClient.getMap(hzMapObj); //creates the map proxy
        for (int i = 0; i < 10; i++) {
            mapCustomers.put("k-" + i, new Customer("Joe-" + i, "Smith-" + i));
        }
    }

    /**
     * 用map读 obj
     */
    public static IMap<String, Customer> readObjWithMap() {
        IMap<String, Customer> mapCustomers = hzClient.getMap(hzMapObj); //creates the map proxy
        for (Map.Entry<String, Customer> entrySet : mapCustomers.entrySet()) {
            String keyStr = entrySet.getKey();
            Customer valueCustomer = entrySet.getValue();
            System.out.println("key: " + keyStr + "\r\nvalue: " + valueCustomer.toString());
        }
        return mapCustomers;
    }

    /**
     * 清空map数据
     */
    public static void clearDataObj(IMap iMap) {
        iMap.clear();
    }

    /**
     * 关闭 hz 客户端
     */
    public static void shutDown() {
        // 关了，要不会一直存在
        hzClient.shutdown();
    }

    /**
     * 测试 iMap 的api
     */
    public void imapApi() {

        // Get the Distributed Map from Cluster.
        IMap map = hzClient.getMap(hzMapStr);
        //Standard Put and Get.
        map.put("key", "value");
        Object valueKey = map.get("key");
        System.out.println("valueKey: " + valueKey);
        //Concurrent Map methods, optimistic updating
        map.putIfAbsent("somekey", "somevalue");
        map.replace("key", "value", "newvalue");

        Object valueSomeKey = map.get("somekey");
        System.out.println("valueSomeKey: " + valueSomeKey);
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

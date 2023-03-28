/**
 * @Description
 * @Author everforcc
 * @Date 2022-10-31 20:49
 * Copyright
 */

package com.cc.sp03data.hazelcast;

import com.alibaba.fastjson.JSONObject;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
//import com.hazelcast.map.IMap;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 用客户端处理map
 */
public class HZClientMap {

    // 通过这里来改变配置
    static final HZServerDto currentServer = HZServerDto.getTXYInstance();
    private static final HazelcastInstance client = HZClientInit.init(currentServer);

    /**
     * 用map写入 str
     */
    public static void writeStrWithMap() {
        System.out.printf("写入map:%s 数据中写入数据\n", currentServer.getMapKey());
        IMap<String, String> map = client.getMap(currentServer.getMapKey());
        for (int i = 0; i < 10; i++) {
            map.put(currentServer.getMapKey() + "k" + i, "John" + i);
        }
        // 三个构造
        //map.tryLock();
    }

    /**
     * 用map读 str
     */
    public static void readStrWithMap() {
        System.out.printf("从map: %s 中读取数据\n", currentServer.getMapKey());
        // 必须要指定类型,否则 map.entrySet() 无法判断类型
        IMap<String, String> map = client.getMap(currentServer.getMapKey());
        // 文档写的是
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            System.out.printf("key: %s\tvalue: %s\n", key, value);
        }
        System.out.println("读取数据结束");
    }

    /**
     * 用map写入 obj
     */
    public static void writeObjWithMap() {
        IMap<String, Customer> mapCustomers = client.getMap(currentServer.getMapKey()); //creates the map proxy
        for (int i = 0; i < 10; i++) {
            mapCustomers.put("k-" + i, new Customer("Joe-" + i, "Smith-" + i));
        }
    }

    /**
     * 用map读 obj
     */
    public static IMap<String, Customer> readObjWithMap() {
        IMap<String, Customer> mapCustomers = client.getMap(currentServer.getMapKey()); //creates the map proxy
        for (Map.Entry<String, Customer> entrySet : mapCustomers.entrySet()) {
            String keyStr = entrySet.getKey();
            Customer valueCustomer = entrySet.getValue();
            System.out.printf("key: %s\tvalue: %s\n", keyStr, valueCustomer.toString());
        }
        return mapCustomers;
    }

    public static void tMapLock() {
        try {
            String busiLock = "busiLock";
            IMap<String, String> mapLock = client.getMap(busiLock);
            boolean canLocked = mapLock.tryLock("key", 0, null, 2, TimeUnit.SECONDS);
            System.out.println("是否获取到锁: " + canLocked);
            if (canLocked) {
                try {
                    System.out.println("模拟业务逻辑开始");
                    Thread.sleep(3 * 1000);
                    System.out.println("模拟业务逻辑结束");
                } finally {
                    System.out.println("最后释放锁");
                    mapLock.unlock(busiLock);
                }
            } else {
                System.out.println("未获取到锁");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.err.println("获取锁失败" + e.getMessage());
        }
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
        client.shutdown();
        System.out.printf("关闭%s客户端\n", currentServer.getHzEnviroment());
    }

    /**
     * 测试 iMap 的api
     */
    public void imapApi() {

        // Get the Distributed Map from Cluster.
        IMap map = client.getMap(currentServer.getMapKey());
        //Standard Put and Get.
        map.put("key", "value");
        Object valueKey = map.get("key");
        System.out.printf("valueKey: %s\n", valueKey);
        //Concurrent Map methods, optimistic updating
        map.putIfAbsent("somekey", "somevalue");
        map.replace("key", "value", "newvalue");

        Object valueSomeKey = map.get("somekey");
        System.out.printf("valueSomeKey:  %s\n", valueSomeKey);
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

    public static void main(String[] args) {

        // 写数据
        writeStrWithMap();
        // 读数据
        readStrWithMap();

        // 读写对象
        //writeObjWithMap();
        //readObjWithMap();

        // 关闭客户端
        shutDown();
    }

}

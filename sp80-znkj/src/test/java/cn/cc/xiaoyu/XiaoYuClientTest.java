/**
 * @Description
 * @Author everforcc
 * @Date 2022-12-14 10:02
 * Copyright
 */

package cn.cc.xiaoyu;

import cn.cc.xiaoyu.client.XiaoYuClient;
import cn.cc.xiaoyu.client.instant.IXiaoYuClient;
import cn.cc.xiaoyu.client.instant.impl.XiaoYuClientImpl1;
import cn.cc.xiaoyu.client.instant.impl.XiaoYuClientImpl2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 1. 模拟充电桩发送请求
 */
public class XiaoYuClientTest {

    /**
     * 需要模拟多个设备登录
     *
     * @param args
     */
    public static void main(String[] args) {
//        thread2();
        threadPool();
    }

    public static void thread2() {
        IXiaoYuClient xiaoYuClient1 = new XiaoYuClientImpl1();
        IXiaoYuClient xiaoYuClient2 = new XiaoYuClientImpl2();
        // 启动线程来执行这个方法 XiaoYuClient.start
        new Thread(() -> XiaoYuClient.start(xiaoYuClient1)).start();
        new Thread(() -> XiaoYuClient.start(xiaoYuClient2)).start();
    }

    /**
     * 模拟多个设备登录
     */
    public static void threadPool() {
        int clientCount = 2; // 可配置的客户端数量

        // 创建固定大小的线程池
        ExecutorService threadPool = Executors.newFixedThreadPool(clientCount);

        // 批量启动客户端
        for (int i = 1; i <= clientCount; i++) {
            IXiaoYuClient client;
            if (i % 2 == 1) {
                client = new XiaoYuClientImpl1();
            } else {
                client = new XiaoYuClientImpl2();
            }

            // 提交任务到线程池执行
            threadPool.submit(() -> XiaoYuClient.start(client));
        }

        // 注意：如果主线程需要继续执行其他逻辑，可以不关闭线程池
        // 如果程序需要正常退出，则应该关闭线程池
        // threadPool.shutdown();
    }

}

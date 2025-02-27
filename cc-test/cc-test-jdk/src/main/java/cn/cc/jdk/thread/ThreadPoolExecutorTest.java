package cn.cc.jdk.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 测试非spring环境中使用线程池
 */
public class ThreadPoolExecutorTest {

    public static void main(String[] args) {
        threadPoolExecutor();
    }

    public static void threadPoolExecutor() {
        ExecutorService executorService = new ThreadPoolExecutor(5, 20, 60, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(512), // 使用有界队列，避免OOM
                new ThreadPoolExecutor.DiscardPolicy());
        int queue = 55;
        for (int i = 0; i < queue; i++) {
            final int temp = i;
            executorService.submit(new Thread(() -> {
                System.out.println("temp: " + temp);
            }));
        }

    }

}

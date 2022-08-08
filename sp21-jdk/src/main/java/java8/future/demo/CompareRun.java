/**
 * @Description
 * @Author everforcc
 * @Date 2022-08-08 17:16
 * Copyright
 */

package java8.future.demo;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 比较两种方式的异同
 */
public class CompareRun {

    /**
     * 使用普通线程启动
     */
    public static void threadRun() throws InterruptedException {
        long start = System.currentTimeMillis();

        // 等凉菜 -- 必须要等待返回的结果，所以要调用join方法
        Thread t1 = new ColdDishThread();
        t1.start();
        t1.join();

        // 等包子 -- 必须要等待返回的结果，所以要调用join方法
        Thread t2 = new BumThread();
        t2.start();
        t2.join();

        long end = System.currentTimeMillis();
        System.out.println("准备完毕时间：" + (end - start));
    }

    /**
     * 使用furure启动
     */
    public static void futureRun() throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();

        // 等凉菜
        Callable ca1 = () -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "凉菜准备完毕";
        };
        FutureTask<String> ft1 = new FutureTask<String>(ca1);
        new Thread(ft1).start();

        // 等包子 -- 必须要等待返回的结果，所以要调用join方法
        Callable ca2 = () -> {
            try {
                Thread.sleep(1000 * 3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "包子准备完毕";
        };
        FutureTask<String> ft2 = new FutureTask<String>(ca2);
        new Thread(ft2).start();

        System.out.println(ft1.get());
        System.out.println(ft2.get());

        long end = System.currentTimeMillis();
        System.out.println("准备完毕时间：" + (end - start));
    }

    public static void main(String[] args) {

        try {

            threadRun();
            futureRun();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}

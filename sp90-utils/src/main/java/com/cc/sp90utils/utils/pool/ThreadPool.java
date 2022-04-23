package com.cc.sp90utils.utils.pool;

import lombok.SneakyThrows;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPool {

    /**
     * TODO 线程数量
     * 如何选择线程池数量
     * @return
     */
    private static int threadSize(){
        int ncpus = Runtime.getRuntime().availableProcessors();
        //System.out.println("ncpus: " + ncpus);
        return ncpus;
    }


    /**
     * 获取线程池
     * @return
     */
    public static ExecutorService getPool(){
        return Executors.newFixedThreadPool(threadSize());
    }

    /**
     * 超时的时候向线程池中所有的线程发出中断(interrupted)。
     * @param executorService
     */
    @SneakyThrows
    public static void shutdown(ExecutorService executorService){
        executorService.shutdown();
//        if(!executorService.awaitTermination(awaitTime, TimeUnit.MILLISECONDS)){
//            executorService.shutdownNow();
//        }
    }

    /**
     * 判断线程是否执行完毕
     */
    public static void isEnd(ExecutorService executorService){
        while(true) {
            if(executorService.isTerminated()) {
                System.out.println("执行完毕");
                break;
            }else {
                //System.out.println("正在执行 >>> ");
                int threadCount = ((ThreadPoolExecutor)executorService).getActiveCount();
                if(threadCount < threadSize()) {
                    System.out.println("1.threadCount====" + threadCount);
                }
            }
        }
    }

}

package com.cc.sp90utils.utils.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
     * TODO 需要关闭，如何关闭
     * 获取线程池
     * @return
     */
    public static ExecutorService getPool(){
        return Executors.newFixedThreadPool(threadSize());
    }

}

package com.cc.sp91test.test.jdk.sync;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TLock {

    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                tlock("001-",lock);
            }
        });

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                tlock("002-",lock);
            }
        });

        thread.start();
        thread1.start();
    }

    public static void tlock(String name,Lock lock){

        if(lock.tryLock()){
            System.out.println(name + "获取到了锁");
            try {
                Thread.sleep(10 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
                System.out.println(name + "解锁");
            }
        }else {
            System.out.println(name + "没获取到");
        }
    }

}

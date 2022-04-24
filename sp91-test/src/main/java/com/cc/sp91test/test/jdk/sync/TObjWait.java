package com.cc.sp91test.test.jdk.sync;


// wait()
// notify()
// notifyAll();

// 通过调用某对象的wait()方法能让当前线程阻塞
// 通过调用某对象的notify()方法能唤醒一个正在等待该对象的线程
// 通过调用某对象的notifyAll()能唤醒所有正在等待该对象的线程

public class TObjWait {

    public static void main(String[] args) throws InterruptedException {
        

        final Object obj = new Object();

        // 母亲做菜线程
        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
                
                // 做菜
                System.out.println("开始炒菜");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                // 做完馒头发通知
                synchronized (obj)
                {
                    System.out.println("菜炒好了，开始吃吧");
                    //obj.notify();// 通知一个等在obj上的一个线程继续运行
                    obj.notifyAll();// 通知所有等在Obj上的线程继续运行
                }

            }
        });
        t.start();

        Thread t2 = new Thread(new Runnable() {

            @Override
            public void run() {
                
                synchronized (obj) {
                    System.out.println("大明:我要吃菜");
                    try {
                        obj.wait();
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }// 进入阻塞状态,直到其他线程发通知
                    System.out.println("大明:开始吃菜");
                }

            }
        });
        t2.start();

        synchronized (obj) {
            System.out.println("小明:我要吃菜");
            obj.wait();// 进入阻塞状态,直到其他线程发通知
            System.out.println("小明:开始吃菜");
        }
    }
}



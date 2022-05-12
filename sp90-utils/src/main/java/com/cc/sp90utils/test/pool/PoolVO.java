package com.cc.sp90utils.test.pool;

import com.cc.sp90utils.concurrent.ThreadPool;

public class PoolVO{

    private final static String lock = "PoolVO-锁";

    private int index;

    public int getIndex() {
        return index;
    }

    public PoolVO(int index) {
      this.index = index;
      //  this.t = create();
        if(1 == index){
            try {
                ThreadPool.getPool().execute(new PoolVONotify(true,lock));
                System.out.println("index " + index + ": 正在等待手动触发异常");
                //Thread.sleep(50 * 1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setException(){
        synchronized (this.lock) {
            System.out.println("this.notify(); 锁: " + lock);
            lock.notify();
        }
    }

    //public abstract T create();

    public static class PoolVONotify implements Runnable{

        boolean flag = true;
        String lock = null;

        public PoolVONotify(boolean flag, String lock) {
            this.flag = flag;
            this.lock = lock;
        }

        @Override
        public void run() {
            try {
                System.out.println("报错异常正在等待......");
                System.out.println("poolVO.wait(); --start 锁: " + lock);

                synchronized (lock) {
                    lock.wait();
                }

                System.out.println("poolVO.wait(); --end");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(flag) {
                throw new RuntimeException("其他线程触发该异常");
            }
        }
    }

}

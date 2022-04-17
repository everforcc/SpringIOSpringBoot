package com.cc.sp91test.test.jdk.timer.usertimer;

// public abstract class TimerTask
public abstract class UserTask implements Runnable{

    /**
     * 锁
     */
    final Object objLock = new Object();

    /**
     * 周期
     * 1秒 = 1000
     */
    int period;

    /**
     * 表明该任务的状态
     */
    int state = VIRGIN;

    /**
     * 任务还没开始
     */
    static final int VIRGIN = 0;

    /**
     * 该任务计划执行，如果是不重复的任务，那就还没有执行
     */
    static final int SCHEDULED   = 1;

    /**
     * 不重复的任务，正则或者已经执行，没有被取消
     */
    static final int EXECUTED    = 2;

    /**
     * 该任务已经被取消，
     */
    static final int CANCELLED   = 3;

    /**
     * 下次执行时间
     */
    long nextExecutionTime;

    public UserTask() {
    }

    /**
     * 取消任务
     * @return
     */
    public boolean cancel() {
        /**
         * 上锁
         */
        synchronized(objLock) {
            /**
             * 判断任务是否正则执行
             * 如果还没执行就取消，
             * 如果执行了，就改变任务状态并,返回false
             * TODO 这里其他状态为什么还要修改 state
             */
            boolean result = (state == SCHEDULED);
            state = CANCELLED;
            return result;
        }
    }

    /**
     *  需要用户继承来重写该方法
     */
    @Override
    public abstract void run();

    /*@Override
    public void run() {
        System.out.println("需要用户继承来重写该方法");
    }*/
}

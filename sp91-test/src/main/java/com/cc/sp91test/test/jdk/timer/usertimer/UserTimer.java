package com.cc.sp91test.test.jdk.timer.usertimer;

import java.util.Date;
import java.util.TimerTask;

/**
 * 自定义线程测试
 */
public class UserTimer {

    /**
     * 存放用户任务的 队列
     */
    private final UserQueue userQueue = new UserQueue();
    /**
     * 这个线程 是用来执行用户自定义线程的 线程
     */
    private final UserTaskThread userTaskThread = new UserTaskThread(userQueue);

    /**
     * TODO 还有几处地方没用，先跑完流程
     */
    boolean newTasksMayBeScheduled = true;

    public UserTimer() {
        userTaskThread.setName("线程名");
        userTaskThread.start();
    }

    // 执行任务
    public void excute(UserTask userTask,String params){
        userTaskThread.params = params;
    }

    public void excute(UserTask userTask, Date date,int period){
        if (period <= 0) {
            throw new IllegalArgumentException("执行 周期 不能为负数.");
        }
        schedule(userTask,date.getTime(),period);
    }

    private void schedule(UserTask userTask, long time,int period){
        if (time < 0) {
            throw new IllegalArgumentException("无效的时间");
        }

        /**
         * Constrain value of period sufficiently to prevent numeric overflow while still being effectively infinitely large.
         * 防止时间合理但是太大?
         */
        if (Math.abs(period) > (Long.MAX_VALUE >> 1)) {
            period >>= 1;
        }

        /**
         *  用队列加锁
         *  但是队列不能修改，修改后，锁就 进不去了啊
         */
        synchronized (userQueue){


            synchronized (userTask.objLock){
                userTask.nextExecutionTime = time;
                userTask.period = period;
                userTask.state = UserTask.SCHEDULED;
            }

            userQueue.add(userTask);
            if (userQueue.getMin() == userTask) {
                System.out.println("userQueue.notify();");
                userQueue.notify();
                // System.out.println("userQueue.notify();");
            }
        }

    }

    // 需要创建一个线程对象
    // 执行顺序
    // 执行线程的方法

    /**
     * 执行任务
     */
    public class UserTaskThread extends Thread {

        /**
         * 给这个线程传递参数
         */
        String params;

        /**
         * 周期,如果 为0则不循环
         */
        long period = 0;

        /**
         * 执行用户 任务线程的 队列
         */
        private UserQueue userQueue;

        public UserTaskThread(UserQueue userQueue) {

            this.userQueue = userQueue;
        }

        @Override
        public void run() {

            try {
                mainLoop();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // Someone killed this Thread, behave as if Timer cancelled
                synchronized(userQueue) {
                    //newTasksMayBeScheduled = false;
                    userQueue.clear();  // Eliminate obsolete references
                }
            }


        }

        private void mainLoop() throws InterruptedException {
            while (true){
                // super.run();
                //long currentTime = System.currentTimeMillis();
                System.out.println("线程: [" + this.getName() + "] 参数: [" + params + "] 等待队列进入");

                UserTask userTask;
                boolean taskFired;

                //
                /**
                 *  第一次进来前  newTasksMayBeScheduled true
                 *  userQueue empty true
                 */
                synchronized(userQueue) {

                    System.out.println("userQueue.isEmpty(): " + userQueue.isEmpty());
                    // TODO userQueue 的 其他状态的判断
                    if (userQueue.isEmpty()&& newTasksMayBeScheduled) {
                        System.out.println("userQueue.wait();");
                        userQueue.wait(); // Queue is empty and will forever remain; die
                    }
                    if (userQueue.isEmpty()) {
                        break; // Queue is empty and will forever remain; die
                    }
                    // 执行时间处理
                    long currentTime, executionTime;
                    userTask = userQueue.getMin();
                    synchronized(userTask.objLock) {
                        // TODO userTask 各种状态的判断
                        //
                        currentTime = System.currentTimeMillis();
                        executionTime = userTask.nextExecutionTime;

                        if (taskFired = (executionTime<=currentTime)) {
                            // TODO 是否重复执行
                            if (userTask.period == 0) { // Non-repeating, remove
                                userQueue.removeMin();
                                userTask.state = UserTask.EXECUTED;
                            } else {
                                // Repeating task, reschedule
                                // 重复的任务
                                userQueue.rescheduleMin(
                                        userTask.period<0 ? currentTime   - userTask.period
                                                : executionTime + userTask.period);
                            }
                        }
                    }
                    if (!taskFired) { // Task hasn't yet fired; wait
                        userQueue.wait(executionTime - currentTime);
                    }
                }
                if (taskFired) {  // Task fired; run it, holding no locks
                    userTask.run();
                }

            }
        }


    }

}

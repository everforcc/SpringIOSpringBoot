package com.cc.sp91test.test.jdk.timer.usertimer;

/**
 * 用来存放用户要执行的任务
 */
public class UserQueue {

    private UserTask[] taskQueue = new UserTask[128];

    private int size = 0;

    public UserQueue() {
    }

    public void add(UserTask userTask){
        taskQueue[1] = userTask;
        ++size;
    }

    public UserTask getMin(){
        return taskQueue[1];
    }

    public void removeMin(){
        taskQueue[1] = null;
        size = 0;
    }

    void rescheduleMin(long newTime) {
        taskQueue[1].nextExecutionTime = newTime;
        // TODO 排序
        //fixDown(1);
    }

    void clear() {
        // Null out task references to prevent memory leak
//        for (int i=1; i<=size; i++)
//            queue[i] = null;
//
//        size = 0;
        taskQueue[1] = null;
    }

    boolean isEmpty() {
        return size==0;
    }

}

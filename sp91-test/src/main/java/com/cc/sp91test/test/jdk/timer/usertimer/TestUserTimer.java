package com.cc.sp91test.test.jdk.timer.usertimer;

import java.util.Date;

public class TestUserTimer {

    public static void main(String[] args) {
        /**
         * 1. 声明对象，启动线程
         */
        UserTimer userTimer = new UserTimer();

        /**
         * 2. 给线程传递参数
         *
         * 参数列表[自定义的任务线程 继承，开始时间，周期]
         */
        userTimer.excute(new UserTask() {
            @Override
            public void run() {
                System.out.println("自定义UserTask()");
            }
        },new Date(),1000);
    }

}

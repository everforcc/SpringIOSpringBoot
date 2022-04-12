package com.cc.sp91test.test.jdk.timer;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by gxf on 2017/6/21.
 */
public class TestTimer {
    public static void main(String[] args) {
        Timer timer = new Timer();

        timer.schedule(new Task("参数"), new Date(), 1000);

        // scheduleAtFixedRate
        timer.schedule(new TimerTask() {
            private int count;
            @Override
            public void run() {
                System.out.println("new TimerTask(): " + count++);
            }
        }, new Date(), 1000);

    }
}

class Task extends TimerTask{

    private int count;

    private String name;

    public Task(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        /**
         * 输出后count 0,1,2,3,4
         */
        System.out.println(name + "Do work...: " + count++);
    }
}
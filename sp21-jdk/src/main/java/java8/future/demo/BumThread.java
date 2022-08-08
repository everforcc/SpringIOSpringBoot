/**
 * @Description
 * @Author everforcc
 * @Date 2022-08-08 17:15
 * Copyright
 */

package java8.future.demo;

public class BumThread extends Thread {

    @Override
    public void run() {
        try {
            Thread.sleep(1000 * 3);
            System.out.println("包子准备完毕");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
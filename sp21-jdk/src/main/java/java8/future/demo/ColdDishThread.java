/**
 * @Description
 * @Author everforcc
 * @Date 2022-08-08 17:16
 * Copyright
 */

package java8.future.demo;

public class ColdDishThread extends Thread {

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            System.out.println("凉菜准备完毕");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

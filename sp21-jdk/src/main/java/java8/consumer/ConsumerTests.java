/**
 * @Description
 * @Author everforcc
 * @Date 2022-07-11 13:12
 * Copyright
 */

package java8.consumer;

import java.util.function.Consumer;

public class ConsumerTests<E> {

    /**
     * 传入一个对象作为参数,然后执行对象的方法
     *
     * @param consumer
     */
    public void execute(Consumer<ConsumerTests<E>> consumer) {
        System.out.println("---- 1111 ----");
        //consumer.accept("123");
        consumer.accept(this);
        System.out.println("---- 2222 ----");
    }

    public void eMethod(E e) {
        System.out.println("e: " + e);
    }

    public static void main(String[] args) {
        new ConsumerTests<String>().execute(e -> {
            e.eMethod("123");
        });
    }

}

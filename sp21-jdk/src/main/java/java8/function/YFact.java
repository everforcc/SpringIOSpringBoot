/**
 * @Description
 * @Author everforcc
 * @Date 2022-07-11 12:55
 * Copyright
 */

package java8.function;

import java.util.function.Consumer;
import java.util.function.Function;

public class YFact {

    /**
     * 函数分析
     * 1. applu接收function的第一个参数,返回第二个参数
     *
     * @param ff  函数
     * @param <T> 参数类型
     * @return consumer
     */
    public static <T> Consumer<T> yConsumer(Function<Consumer<T>, Consumer<T>> ff) {
        // 全局 1.
        System.out.println("----[yCo]: ----a");
        // 1.  返回 R
        // 2.                执行consumer的方法

        // 原版
//        return ff.apply(t ->
//                yConsumer(ff).accept(t)
//        );

        // 但是这样也能达到效果,不知道原来什么意思
//        Consumer<T> tConsumer = t -> {
//            System.out.println("----[tConsumer]: " + t);
//        };
//        tConsumer.accept();
//        return ff.apply(tConsumer);

        return ff.apply(t ->
                yConsumer(ff).accept(t)
        );

        // 递归调用报错
//        Consumer<T> tempConsumer = yConsumer(ff);
//        return ff.apply(tempConsumer);

        // 递归调用报错
        // return ff.apply(yConsumer(ff));
    }

    /**
     * @param ff    函数
     * @param tData tConsumer 调用测试参数
     * @param <T>   参数类型
     * @return 返回 rConsumer
     */
    public static <T> Consumer<T> yConsumer(Function<Consumer<T>, Consumer<T>> ff, T tData) {
        // 全局 1.
        System.out.println("----[yCo]: ----a");

        Consumer<T> tConsumer = ff.apply(t ->
                yConsumer(ff).accept(t)
        );
        tConsumer.accept(tData);

        return tConsumer;

    }

    /**
     * 用 int 测试下执行流程
     */
    public static Consumer<Integer> yConsumerInteger(Function<Consumer<Integer>, Consumer<Integer>> ff) {
        // 全局 1.
        System.out.println("----[yCo]: ----a");
        // 1.  返回 R
        // 2.                执行consumer的方法
        //return ff.apply(t -> yConsumer(ff).accept(t));
        return ff.apply(t -> {
            int i = t + 2;
            System.out.println("----[iii]: ----" + i);
        });
    }

    /**
     * 1.1 原代码测试1
     */
    public void run_1() {
        // 情况 1
        // Consumer<String> consumer = yConsumer(f -> YFact::sout);
        Consumer<String> consumer = yConsumer(f -> ff -> TService.sout(ff, c -> {
            // 全局 3.
            System.out.println("----[con]: ----b");// + ff
        }));
        // 全局 2.
        consumer.accept("----c");
    }

    /**
     * 1.2 原代码测试2
     */
    public void run_2() {
        // 情况 1
        Consumer<String> consumer = yConsumer(f -> TService::sout);
        // 全局 2.
        consumer.accept("----c");
    }

    /**
     * 2.1 写死 int 类型测试
     */
    public void run_integer_1() {
        // 情况 1
        Consumer<Integer> consumer = yConsumerInteger(f -> TService::sout);
        // 全局 2.
        consumer.accept(11111);
    }

    /**
     * 3.1 对 trConsumer 两种实例化测试
     */
    public void run_data_1() {
        // 情况 1
        // Consumer<String> consumer = yConsumer(f -> YFact::sout);
        Consumer<String> consumer = yConsumer(f -> ff -> TService.sout(ff, c -> {
            // 全局 3.
            System.out.println("----[con]: ----b");// + ff
        }), "tData");
        // 全局 2.
        consumer.accept("----c");
    }

    public static void main(String[] args) {
        YFact yFact = new YFact();
        //yFact.run_1();
        yFact.run_data_1();
        // yFact.run_2();
        // yFact.run_integer_1();
    }

}

class TService {
    public static void sout(String str, Consumer<String> consumer) {
        // 全局 2.
        System.out.println("----[str-1]: " + str);
        consumer.accept(str);
    }

    public static void sout(String str) {
        // 全局 2.
        System.out.println("----[str-2]: " + str);
    }

    public static void sout(Integer integer) {
        System.out.println("----[Int]: " + integer);
    }
}

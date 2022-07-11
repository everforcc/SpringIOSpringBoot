/**
 * @Description
 * @Author everforcc
 * @Date 2022-07-11 15:47
 * Copyright
 */

package java8.function;

import java.util.function.Consumer;
import java.util.function.Function;

public class FunctionTests {

    public void functionInteger() {
        Function<Integer, Integer> integerFunction = i -> i * 2;
        Function<Integer, Integer> integerFunctionAndThen = i -> i * 2;
        // 执行第一个运算
        Integer result = integerFunction.apply(2);
        System.out.println(result);
        // 先执行前者,拿到结果再继续执行
        Integer andThen = integerFunction.andThen(integerFunctionAndThen).apply(2);
        System.out.println(andThen);
    }

    public void functionConsumer() {
        Function<Consumer<String>, Consumer<String>> consumerFunction = new Function<Consumer<String>, Consumer<String>>() {
            @Override
            public Consumer<String> apply(Consumer<String> stringConsumer) {
                stringConsumer.accept("tttt");
                return e -> {
                    System.out.println("---- b: " + e);
                };
            }
        };
        Consumer<String> rConsumer = consumerFunction.apply(e->{
            System.out.println("---- a: " + e);
        });
        rConsumer.accept("rrrr");
    }

    public static void main(String[] args) {
        FunctionTests functionTests = new FunctionTests();

        // 1。运算
        //functionTests.functionInteger();

        // 2. 方法
        functionTests.functionConsumer();

    }


}

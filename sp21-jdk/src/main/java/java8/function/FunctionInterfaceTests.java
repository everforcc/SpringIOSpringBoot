/**
 * @Description
 * @Author everforcc
 * @Date 2022-07-11 18:04
 * Copyright
 */

package java8.function;

import java.util.function.Consumer;
import java.util.function.Function;

public class FunctionInterfaceTests {

    public static <T> IRClass yConsumer(Function<IClass<T>, IRClass> ff) {
        // 情况1
//        IClass<T> iClass = null;
//        return ff.apply(t -> iClass.iMethod(t));

        IClass<T> iClass = null;
        return ff.apply(t -> {
            // 此时内部类随便写 ,结果都是返回 IRClass 接口,在调用的时候,用户自己定义实现类
            assert iClass != null;
            return iClass.iMethod(t);
        });

    //        IClass<T> iClass = null;
    //        return ff.apply(iClass);
    }

    /**
     * 此时函数右边定义的就是R的实现类
     */
    public void run_1() {
        // 情况 1
        // Consumer<String> consumer = yConsumer(f -> YFact::sout);
        IRClass irClass = yConsumer(f -> ff ->  {
            // return可以删除,这里是便于理解
            return e ->{
                System.out.println("用户自定义实现类" + e);
            };
        });
        // 全局 2.
        Consumer<String> tConsumer = irClass.iMethod("----c");
        tConsumer.accept("abc");
    }

    public static void main(String[] args) {
        FunctionInterfaceTests functionInterfaceTests = new FunctionInterfaceTests();
        functionInterfaceTests.run_1();
    }

}

interface IClass<T> {

    Consumer<T> iMethod(String s);

}

interface IRClass {

    Consumer<String> iMethod(String s);

}
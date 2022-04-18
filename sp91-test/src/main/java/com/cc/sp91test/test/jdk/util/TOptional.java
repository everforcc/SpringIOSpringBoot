package com.cc.sp91test.test.jdk.util;

import java.util.Optional;

public class TOptional {

    public static void main(String[] args) {

        /**
         * 1. of
         * T 类型，可以创建空对象
         */
        //tof();

        /**
         * 2. ofNullable
         */
        //tofNullable();

        /**
         * 3. 创建空 Optional
         */
        //tempty();

        /**
         * 4. 判断是否为空
         * 5. 先判断再 get
         */
        //tisPresent();

        /**
         * 6. ifPresent
         * 如果Optional实例有值则为其调用consumer，否则不做处理
         */
        //tifPresent();

        /**
         * 7. orElse
         * 如果有值则将其返回，否则返回指定的其它值。
         */
        //torElse();

        /**
         * 8. orElseThrow
         * 否则抛出异常
         */
        //torElseThrow();

        /**
         * 9. map
         * 如果有值，则对其执行调用mapping函数得到返回值。如果返回值不为null，则创建包含mapping返回值的Optional作为map方法返回值，否则返回空Optional。
         */
        //tmap();

        /**
         * 10. flatMap
         * 如果有值，为其执行mapping函数返回Optional类型返回值，
         * 否则返回空Optional。flatMap与map（Funtion）方法类似，
         * 区别在于flatMap中的mapper返回值必须是Optional。调用结束时，
         * flatMap不会对结果用Optional封装。
         */
        //tflatMap();

        /**
         * 11. tfilter
         */
        tfilter();

    }


    public static void tof(){
        //调用工厂方法创建Optional实例
        Optional<String> name = Optional.of("Sanaulla");
        System.out.println(name);
        //传入参数为null，抛出NullPointerException.
        Optional<String> someNull = Optional.of(null);
        System.out.println(someNull);
    }

    public static void tofNullable(){
        //下面创建了一个不包含任何值的Optional实例
        //例如，值为'null'
        Optional empty = Optional.ofNullable(null);
        System.out.println(empty);
    }

    public static void tempty(){
        Optional<String> empty = Optional.empty();
        System.out.println(empty);
    }

    public static void tisPresent(){
        Optional<String> name = Optional.of("Sanaulla");
        //isPresent方法用来检查Optional实例中是否包含值
        if (name.isPresent()) {
            //在Optional实例内调用get()返回已存在的值
            System.out.println(name.get());//输出Sanaulla
        }
    }

    public static void tifPresent(){
        Optional<String> optional = Optional.of("Sanaulla");
        //ifPresent方法接受lambda表达式作为参数。
        //lambda表达式对Optional的值调用consumer进行处理。
        optional.ifPresent(s -> {
            s = s + "S";
            System.out.println(s);
        });
        System.out.println(optional.get());
    }


    private static void torElse() {
        Optional<String> name = Optional.of("Sanaulla");
        Optional<String> empty = Optional.empty();
        //如果值不为null，orElse方法返回Optional实例的值。
        //如果为null，返回传入的消息。
        //输出：There is no value present!
        System.out.println(empty.orElse("There is no value present!"));
        //输出：Sanaulla
        System.out.println(name.orElse("There is some value!"));
    }

    private static void torElseThrow(){
        try {
            //orElseThrow与orElse方法类似。与返回默认值不同，
            //orElseThrow会抛出lambda表达式或方法生成的异常
            Optional<String> empty = Optional.empty();
            empty.orElseThrow(TRuntimeException::new);
        } catch (Throwable ex) {
            //输出: No value present in the Optional instance
            System.out.println(ex.getMessage());
        }
    }

    public static void tmap(){
        Optional<String> name = Optional.of("Sanaulla");
        //map方法执行传入的lambda表达式参数对Optional实例的值进行修改。
        //为lambda表达式的返回值创建新的Optional实例作为map方法的返回值。
        Optional<String> upperName = name.map((value) -> value.toUpperCase());
        System.out.println(upperName.orElse("No value found"));
    }

    public static void tflatMap(){
        Optional<String> name = Optional.of("Sanaulla");
        //flatMap与map（Function）非常类似，区别在于传入方法的lambda表达式的返回类型。
        //map方法中的lambda表达式返回值可以是任意类型，在map函数返回之前会包装为Optional。
        //但flatMap方法中的lambda表达式返回值必须是Optionl实例。
        Optional<String> upperName = name.flatMap((value) -> Optional.of(value.toUpperCase()));
        System.out.println(upperName.orElse("No value found"));//输出SANAULLA
    }

    public static void tfilter(){
        Optional<String> name = Optional.of("Sanaulla");
        //filter方法检查给定的Option值是否满足某些条件。
        //如果满足则返回同一个Option实例，否则返回空Optional。
        Optional<String> longName = name.filter((value) -> value.length() > 6);
        System.out.println(longName.orElse("The name is less than 6 characters"));//输出Sanaulla

        //另一个例子是Optional值不满足filter指定的条件。
        Optional<String> anotherName = Optional.of("Sana");
        Optional<String> shortName = anotherName.filter((value) -> value.length() > 6);
        //输出：name长度不足6字符
        System.out.println(shortName.orElse("The name is less than 6 characters"));
    }

    public static class TRuntimeException extends Throwable{
        public TRuntimeException() {
            super("自定义异常");
        }
    }

}

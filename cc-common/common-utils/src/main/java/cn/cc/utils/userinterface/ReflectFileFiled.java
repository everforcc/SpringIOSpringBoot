package cn.cc.utils.userinterface;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ReflectFileFiled {

    boolean use() default true;

    /**
     * 别名，有些接口的属性含有不能命名的字符，在这里处理下
     * @return
     */
    String alias() default "";

}

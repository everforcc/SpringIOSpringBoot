/**
 * @Description
 * @Author everforcc
 * @Date 2022-06-27 16:20
 * Copyright
 */

package cn.cc.authorization.annotation;

import java.lang.annotation.*;

// 接口权限
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Authorization {

    //权限
    String[] value();

}

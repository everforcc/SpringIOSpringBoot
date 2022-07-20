package com.cc.sp02thymeleaf.annotation;

import com.cc.sp02thymeleaf.config.validator.EnumsValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = EnumsValidator.class)
public @interface EnumsValited {

    String message() default "目前仅支持枚举 a,b,c";
    /* 这两个参数不写报错 */
    /* contains Constraint annotation, but does not contain a groups parameter. */
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}

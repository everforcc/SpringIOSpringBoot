/**
 * @Description
 * @Author everforcc
 * @Date 2022-07-19 16:58
 * Copyright
 */

package com.cc.sp02thymeleaf.config.validator;

import com.cc.sp02thymeleaf.annotation.EnumsValited;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
public class EnumsValidator implements ConstraintValidator<EnumsValited, Object> {

    private long[] longs = {1L, 2L, 3L};

    /**
     * 重写校验方法
     *
     * @param value   object to validate
     * @param context context in which the constraint is evaluated
     * @return 校验结果
     */
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value instanceof Long) {
            for (long l : longs) {
                if (l == Long.parseLong(String.valueOf(value))) {
                    return true;
                }
            }
        }
        // 需要在aop或者拦截器捕获
        log.error("信息不匹配: 【{}】", value);
        return false;
    }
}

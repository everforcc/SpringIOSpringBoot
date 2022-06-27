/**
 * @Description
 * @Author everforcc
 * @Date 2022-06-27 16:00
 * Copyright
 */

package com.cc.sp02thymeleaf.utils.impl;

import com.alibaba.fastjson.JSONObject;
import com.cc.sp02thymeleaf.dto.CustomUser;
import com.cc.sp02thymeleaf.utils.IToken;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * 简易工具类
 */
public class TokenJSON implements IToken {

    @Override
    public String userToString(CustomUser customUser) {
        return customUser.toString();
    }

    @Override
    public CustomUser stringToUser(String str) {
        return JSONObject.parseObject(str, CustomUser.class);
    }
}

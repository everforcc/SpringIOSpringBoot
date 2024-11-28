/**
 * @Description
 * @Author everforcc
 * @Date 2022-06-27 16:00
 * Copyright
 */

package cn.cc.authorization.utils.impl;

import cn.cc.authorization.dto.CustomUser;
import cn.cc.authorization.utils.IToken;
import com.alibaba.fastjson.JSONObject;

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

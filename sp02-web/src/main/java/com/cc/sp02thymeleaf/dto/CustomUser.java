/**
 * @Description
 * @Author everforcc
 * @Date 2022-06-27 16:02
 * Copyright
 */

package com.cc.sp02thymeleaf.dto;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomUser {

    private String name;
    private String pas;
    private HashSet<String> funcSet;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}

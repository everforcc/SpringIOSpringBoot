/**
 * @Description
 * @Author everforcc
 * @Date 2022-05-27 09:47
 * Copyright
 */

package com.cc.sp03data.dto;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MybatisUser {

    // id增长策略
    private Long id;
    private String name;
    private Integer age;
    private String email;

    // 乐观锁注解
    private Integer version;
    // 逻辑删除
    private Integer deleted;

    private Date createTime;
    private Date updateTime;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this, SerializerFeature.PrettyFormat);
    }
}

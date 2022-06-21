/**
 * @Description
 * @Author everforcc
 * @Date 2022-05-27 09:47
 * Copyright
 */

package cn.cc.sp04mybatisplus.dto;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

@Data
@TableName("cc_mybatis_plus_user")
public class MybatisPlusUser {

    // id增长策略
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private Integer age;
    private String email;

    // 乐观锁注解
    @Version
    private Integer version;

    // 逻辑删除
    @TableLogic
    private Integer deleted;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this, SerializerFeature.PrettyFormat);
    }
}

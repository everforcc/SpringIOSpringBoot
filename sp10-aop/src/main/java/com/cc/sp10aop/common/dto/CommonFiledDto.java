package com.cc.sp10aop.common.dto;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

//@Data 会重写toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommonFiledDto {

    private Integer userid;
    private String uuid;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Timestamp createTime;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Timestamp updateTime;
    private Integer effect;
    private Integer status;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

    public CommonFiledDto(Integer userid) {
        this.userid = userid;
        this.uuid = UUID.randomUUID().toString().replace("-", "");;
        this.createTime = getTime();
        this.updateTime = getTime();
        this.effect = 1;
        this.status = 1;
    }

    public static CommonFiledDto save(Object userid){
        return new CommonFiledDto( Integer.parseInt(userid.toString()));
    }

    public static Timestamp getTime(){
        return new Timestamp(new Date().getTime());
    }
}

package com.cc.sp90utils.dto;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.cc.sp90utils.commons.codec.JUUIDUtils;
import com.cc.sp90utils.enums.impl.StatusEnum;
import com.cc.sp90utils.i.valited.IUpdate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Date;

//@Data 会重写toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommonFiledDto {

    @NotNull(groups = {IUpdate.class},message = "id不允许为null")
    private int id;
    @NotNull(groups = {IUpdate.class},message = "uuid不允许为null")
    private String uuid;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Timestamp createTime;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Timestamp updateTime;
    private int createUserid;
    private int updateUserid;

    /**
     * 默认为有效，如果忘记写就是有效
     */
    private StatusEnum effect = StatusEnum.EFFECT;
    private StatusEnum status = StatusEnum.EFFECT;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

    public CommonFiledDto(Integer createUserid,Integer updateUserid) {
        this.createUserid = createUserid;
        this.updateUserid = updateUserid;
        this.uuid = JUUIDUtils.uuid32();
        this.createTime = nowTime();
        this.updateTime = nowTime();
        this.effect = StatusEnum.EFFECT;
        this.status = StatusEnum.EFFECT;
    }

    public CommonFiledDto(Integer updateUserid) {
        this.updateUserid = updateUserid;
        this.uuid = JUUIDUtils.uuid32();
        this.createTime = nowTime();
        this.updateTime = nowTime();
        this.effect = StatusEnum.EFFECT;
        this.status = StatusEnum.EFFECT;
    }

    public static CommonFiledDto save(Object userid){
        int createUserid = Integer.parseInt(userid.toString());
        return new CommonFiledDto(createUserid,createUserid);
    }

    public static CommonFiledDto update(Object userid){
        int updateUserid = Integer.parseInt(userid.toString());
        return new CommonFiledDto(updateUserid);
    }

    public static Timestamp nowTime(){
        return new Timestamp(new Date().getTime());
    }

}

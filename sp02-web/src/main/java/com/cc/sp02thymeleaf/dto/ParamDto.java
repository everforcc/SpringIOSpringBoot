/**
 * @Description
 * @Author everforcc
 * @Date 2022-05-25 09:41
 */

package com.cc.sp02thymeleaf.dto;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParamDto {

    private FieldEnum statusEnum;
    @NotEmpty
    private String name;
    private String description;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date date;

    public ParamDto(FieldEnum statusEnum, String name, String description) {
        this.statusEnum = statusEnum;
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}

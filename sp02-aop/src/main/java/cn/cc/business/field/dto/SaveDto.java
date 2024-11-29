package cn.cc.business.field.dto;

import cn.cc.aop.dto.CommonFieldDto;
import lombok.Data;

@Data
public class SaveDto extends CommonFieldDto {

    private int id;

    private String userName;

}
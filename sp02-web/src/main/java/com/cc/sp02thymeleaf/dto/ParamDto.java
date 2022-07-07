/**
 * @Description
 * @Author everforcc
 * @Date 2022-05-25 09:41
 */

package com.cc.sp02thymeleaf.dto;


import javax.validation.constraints.NotEmpty;

public class ParamDto {

    private FieldEnum statusEnum;
    @NotEmpty
    private String name;
    private String description;

    public ParamDto() {
    }

    public ParamDto(FieldEnum statusEnum, String name, String description) {
        this.statusEnum = statusEnum;
        this.name = name;
        this.description = description;
    }

    public FieldEnum getStatusEnum() {
        return statusEnum;
    }

    public void setStatusEnum(FieldEnum statusEnum) {
        this.statusEnum = statusEnum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}

package cn.cc.dto;

import lombok.Data;

/**
 * @Description : 车场角色关联表
 * @Author : GKL
 * @Date: 2024-05-06 09:38
 */
@Data
public class RuleRolePark {

    /**
     * 角色id
     */
    private int roleID;

    /**
     * 车场id
     */
    private int parkID;

}

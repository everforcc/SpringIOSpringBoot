package cn.cc.dto;

import lombok.Data;

/**
 * @Description : 角色规则合集，一个角色对应一系列规则，一个车厂对应一个角色
 * @Author : GKL
 * @Date: 2024-05-06 09:38
 */
@Data
public class RuleRole {

    /*
     * 1. 规则id
     * 2. 是否生效
     * 3. 生效时间 开始
     * 4. 生效时间 结束
     *
     * 需要校验规则完整性
     *
     */

    /**
     * 角色id
     */
    private int roleID;

    /**
     * 角色名，不能重复
     */
    private String roleName;

    /**
     * 角色排序
     */
    private int roleSort;

    /**
     * 角色状态
     */
    private String status;

}

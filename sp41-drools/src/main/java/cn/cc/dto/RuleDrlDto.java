package cn.cc.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Description : 规则
 * @Author : GKL
 * @Date: 2024-05-08 17:10
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RuleDrlDto {

    private int ruleId;

    /**
     * 包名
     */
    private String packages;

    /**
     * 文件名
     * todo 后缀名
     */
    private String fileName;

    /**
     * 规则
     */
    private String rule;

    /**
     * 备注
     */
    private String remark;

}

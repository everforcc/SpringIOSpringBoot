package cn.cc.dto.rule;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @Description : 计费规则基类，必须有一个长期规则，只能有一个临时规则
 * @Author : GKL
 * @Date: 2024-04-26 15:30
 */
//@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RuleBase {

    /**
     * 永久有效
     * 如果没有命中其他规则，
     * 有且只能有一个永久有效规则
     */
    private int forever;

    /**
     * 规则开始时间
     */
    private Date startTime;

    /**
     * 规则结束时间
     */
    private Date endTime;

}

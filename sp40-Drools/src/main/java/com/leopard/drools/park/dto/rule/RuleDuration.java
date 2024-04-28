package com.leopard.drools.park.dto.rule;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description : 时长收费
 * @Author : GKL
 * @Date: 2024-04-26 15:35
 */
@Data
public class RuleDuration extends RuleBase{

    /**
     * 收费价格
     */
    private BigDecimal fee;

    /**
     * 时长区间开始
     */
    private Double durationStart;

    /**
     * 时长区间结束
     */
    private Double durationEnd;

}

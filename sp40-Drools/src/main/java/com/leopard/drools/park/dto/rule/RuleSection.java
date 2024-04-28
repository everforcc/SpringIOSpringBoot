package com.leopard.drools.park.dto.rule;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description : 分时收费
 * @Author : GKL
 * @Date: 2024-04-26 15:36
 */
@Data
public class RuleSection extends RuleBase {

    /**
     * 收费价格
     */
    private BigDecimal fee;

    /**
     * 时长区间开始
     */
    private Date durationStart;

    /**
     * 时长区间结束
     */
    private Date durationEnd;

}

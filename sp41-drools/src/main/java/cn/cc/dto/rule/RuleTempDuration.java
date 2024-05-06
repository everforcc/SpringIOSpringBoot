package cn.cc.dto.rule;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description : 时长收费
 * @Author : GKL
 * @Date: 2024-04-26 15:35
 */
@Data
public class RuleTempDuration extends RuleBase{

    /**
     * {}元/
     */
    private BigDecimal fee;

    /**
     * /{}分钟
     */
    private Integer minute;

/*    *//**
     * 时长区间结束
     *//*
    private Double durationEnd;*/

}

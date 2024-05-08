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
     * 免费时长
     */
    public int freeMinute = 30;

    /**
     * 每多长时间计费一次
     */
    public int durationMinute = 30;

    public BigDecimal ruleDurationFee = new BigDecimal("2.5");

}

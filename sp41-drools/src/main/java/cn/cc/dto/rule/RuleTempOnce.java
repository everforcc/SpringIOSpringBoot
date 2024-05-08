package cn.cc.dto.rule;

import lombok.*;

import java.math.BigDecimal;

/**
 * @Description : 按次计费
 * @Author : GKL
 * @Date: 2024-04-26 15:30
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RuleTempOnce extends RuleBase{

    /**
     * 单次收费价格
     */
    public BigDecimal ruleOnceFee = new BigDecimal(222);

}

package cn.cc.dto.rule;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description : 按次计费
 * @Author : GKL
 * @Date: 2024-04-26 15:30
 */
@Data
public class RuleCount extends RuleBase{

    /**
     * 单次收费价格
     */
    private BigDecimal fee;

}

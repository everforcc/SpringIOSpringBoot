package cn.cc.dto.rule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description : 分时收费
 * @Author : GKL
 * @Date: 2024-04-26 15:36
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RuleTempSection extends RuleBase {

    /**
     * 收费价格
     */
    private BigDecimal fee;

    /**
     * 时长区间开始
     */
    private Integer durationStart;

    /**
     * 时长区间结束
     */
    private Integer durationEnd;

}

package cn.cc.lkl.dto.merchantupdate;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * V3TkbsOpenMerchantAddTermRequest 使用
 * 费率信息DTO
 */
@Data
public class FeeInfoDto {
    /**
     * 费率
     */
    private Double fee;

    /**
     * 费率封顶上限
     */
    @JsonProperty("top_fee")
    private Double topFee;

    /**
     * 费率类型（详见费率代码表）
     */
    @JsonProperty("fee_type")
    private String feeType;
}

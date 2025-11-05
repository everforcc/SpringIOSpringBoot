package cn.cc.lkl.dto.authrealname;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class V2MmsSmeMrchAuthStateQueryResponse {

    /**
     * 子商户号
     */
    @JsonProperty("subMerchantId")
    private String subMerchantId;

    /**
     * 微信: 枚举值：
     * AUTHORIZE_STATE_UNAUTHORIZED：未授权
     * AUTHORIZE_STATE_AUTHORIZED ：已授权
     * <p>
     * 支付宝: 枚举值：
     * AUTHORIZED：已确认
     * UNAUTHORIZED ：未确认
     * CLOSED：已销户
     * SMID_NOT_EXIST：smid不存在
     */
    @JsonProperty("checkResult")
    private String checkResult;

}

package cn.cc.lkl.dto.merchant;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 商户进件 出参
 */
@Data
public class V3TkbsMerchantEncryResponse {

    /**
     * * 必传	String	20	内部商户编号	拓客系统商户号，如：100130106
     */
    @JsonProperty("merchant_no")
    private String merchantNo;

    /**
     * * status	必传	String	80	商户状态	WAIT_AUDI：待审核
     */
    @JsonProperty("status")
    private String status;
    /**
     * * state	必传	String	20	进件标志	无实际意义，可忽略
     */
    @JsonProperty("state")
    private String state;

}

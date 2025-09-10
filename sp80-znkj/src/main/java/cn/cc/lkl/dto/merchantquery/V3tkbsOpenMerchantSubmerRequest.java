package cn.cc.lkl.dto.merchantquery;

import cn.cc.lkl.dto.LKLBaseRequest;
import cn.cc.lkl.enums.FunctionCodeEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 新子商户查询
 * https://o.lakala.com/p/#/document/detail?id=1079
 */
@Data
public class V3tkbsOpenMerchantSubmerRequest extends LKLBaseRequest {

    /**
     * 外部商户编号
     * 银联822商户号
     */
    @JsonProperty("merchant_no")
    private String merchantNo;

    /**
     * 开放平台鉴权机构
     */
    @JsonProperty("org_code")
    private String orgCode;

    @Override
    public FunctionCodeEnum getFunctionCode() {
        return FunctionCodeEnum.API_V3_TKBS_OPEN_MERCHANT_SUBMER;
    }
}

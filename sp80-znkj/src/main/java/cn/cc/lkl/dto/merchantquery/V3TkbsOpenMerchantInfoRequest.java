package cn.cc.lkl.dto.merchantquery;

import cn.cc.lkl.dto.LKLBaseRequest;
import cn.cc.lkl.enums.FunctionCodeEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Size;

/**
 * 获取商户信息
 * https://o.lakala.com/p/#/document/detail?id=1089
 */
@Data
public class V3TkbsOpenMerchantInfoRequest extends LKLBaseRequest {

    /**
     * 外部商户编号
     * 银联822商户号，外部商户编号与内部商户号二者至少选其一
     */
    @JsonProperty("merchant_no")
    private String merchantNo;

    /**
     * 内部商户编号
     * 拓客系统商户号，外部商户编号与内部商户号二者至少选其一
     */
    @JsonProperty("customer_no")
    private String customerNo;

    @Size(max = 32)
    @JsonProperty("org_code")
    private String orgCode;

    @Override
    public FunctionCodeEnum getFunctionCode() {
        return FunctionCodeEnum.API_V3_TKBS_OPEN_MERCHANT_INFO;
    }
}

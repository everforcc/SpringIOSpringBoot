package cn.cc.lkl.dto.merchant;

import cn.cc.lkl.dto.LKLBaseRequest;
import cn.cc.lkl.enums.FunctionCodeEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 电子合同查询
 * https://o.lakala.com/#/home/document/detail?id=293
 */
@Data
public class V3MmsOpenApiEcQStatusRequest extends LKLBaseRequest {

    private String version = "1.0";
    /**
     * 四方机构自定义订单编号
     */
    @JsonProperty("order_no")
    private String orderNo;
    /**
     * 机构号
     */
    @JsonProperty("org_code")
    private String orgCode;
    /**
     * 电子合同申请受理号
     */
    @JsonProperty("ec_apply_id")
    private String ecApplyId;

    @Override
    public FunctionCodeEnum gFunctionCode() {
        return FunctionCodeEnum.API_V3_MMS_OPEN_API_EC_Q_STATUS;
    }
}

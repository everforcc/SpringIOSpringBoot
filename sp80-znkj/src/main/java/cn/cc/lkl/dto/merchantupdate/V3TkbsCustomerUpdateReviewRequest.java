package cn.cc.lkl.dto.merchantupdate;

import cn.cc.lkl.dto.LKLBaseRequest;
import cn.cc.lkl.enums.FunctionCodeEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * https://o.lakala.com/p/#/document/detail?id=1060
 * 商户信息变更 -> 商户审核状态查询
 */
@Data
public class V3TkbsCustomerUpdateReviewRequest extends LKLBaseRequest {

    @JsonProperty("review_related_id")
    private String revieRelatedId;

    @JsonProperty("org_code")
    private String orgCode;

    @Override
    public FunctionCodeEnum gFunctionCode() {
        return FunctionCodeEnum.API_V3_TKBS_CUSTOMER_UPDATE_REVIEW;
    }
}

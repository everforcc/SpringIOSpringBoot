package cn.cc.lkl.dto.merchantencry;

import cn.cc.lkl.dto.LKLBaseRequest;
import cn.cc.lkl.enums.FunctionCodeEnum;
import lombok.Data;

/**
 * https://o.lakala.com/#/home/document/detail?id=289
 * 电子合同申请
 * /api/v3/mms/open_api/ec/apply
 */
@Data
public class V3MmsOpenApiEcApplyRequest extends LKLBaseRequest {
    @Override
    public FunctionCodeEnum getFunctionCode() {
        return null;
    }
}

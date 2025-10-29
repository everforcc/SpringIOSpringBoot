package cn.cc.lkl.dto.authsplitsplit;

import cn.cc.lkl.dto.LKLBaseRequestV2;
import cn.cc.lkl.enums.FunctionCodeEnum;
import lombok.Data;

/**
 * https://o.lakala.com/#/home/document/detail?id=385
 * 分账接收方详情查询
 */
@Data
public class V2MmsOpenApiLedgerQueryReceiverDetailRequest extends LKLBaseRequestV2 {

    private String version = "1.0";
    private String orderNo;
    private String orgCode;
    private String receiverNo;

    @Override
    public FunctionCodeEnum getFunctionCode() {
        return FunctionCodeEnum.API_V2_MMS_OPENAPI_LEDGER_QUERYRECEIVERDETAIL;
    }
}

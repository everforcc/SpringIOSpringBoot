package cn.cc.lkl.dto.authrealname;

import cn.cc.lkl.dto.LKLBaseRequestV2;
import cn.cc.lkl.enums.FunctionCodeEnum;

/**
 * https://o.lakala.com/#/home/document/detail?id=488
 * /v2/mms/sme/mrchAuthStateQuery
 */
public class V2MmsSmeMrchAuthStateQueryRequest extends LKLBaseRequestV2 {

    /**
     * 交易钱包类型	ALIPAY，WECHAT
     */
    private String tradeMode;
    /**
     * 子商户号
     */
    private String subMerchantId;
    /**
     * 商户号
     */
    private String merchantNo;

    @Override
    public FunctionCodeEnum getFunctionCode() {
        return FunctionCodeEnum.API_V2_MMS_SME_MRCHAUTHSTATEQUERY;
    }
}

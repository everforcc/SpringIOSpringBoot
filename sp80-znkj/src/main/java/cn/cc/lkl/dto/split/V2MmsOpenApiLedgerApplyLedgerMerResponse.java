package cn.cc.lkl.dto.split;

import lombok.Data;

/**
 * 商户分账业务开通申请请求结果
 * https://o.lakala.com/#/home/document/detail?id=379
 *
 * @author cc
 */
@Data
public class V2MmsOpenApiLedgerApplyLedgerMerResponse {

    /**
     * 接口版本号	547110502170558464
     */
    private String version;
    /**
     * 订单编号	2021020112000012345678
     */
    private String orderNo;
    /**
     * 机构代码	200669
     */
    private String orgCode;
    /**
     * 受理编号	548099616395186176
     */
    private Long applyId;

}

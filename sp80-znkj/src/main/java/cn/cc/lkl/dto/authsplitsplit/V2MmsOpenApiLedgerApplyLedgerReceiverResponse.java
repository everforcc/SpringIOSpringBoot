package cn.cc.lkl.dto.authsplitsplit;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * https://o.lakala.com/#/home/document/detail?id=382
 * 分账接收方创建申请 响应
 * /api/v2/mms/openApi/ledger/applyLedgerReceiver
 */
@Data
public class V2MmsOpenApiLedgerApplyLedgerReceiverResponse {

    /**
     * 接口版本号(回传)
     */
    @JsonProperty("version")
    private String version;
    /**
     * 订单编号(回传)
     */
    @JsonProperty("orderNo")
    private String orderNo;
    /**
     * 申请机构代码(回传)
     */
    @JsonProperty("orgCode")
    private String orgCode;
    /**
     * 接收方所属机构
     */
    @JsonProperty("orgId")
    private String orgId;
    /**
     * 接收方所属机构名称
     */
    @JsonProperty("orgName")
    private String orgName;
    /**
     * 接收方编号
     */
    @JsonProperty("receiverNo")
    private String receiverNo;

}

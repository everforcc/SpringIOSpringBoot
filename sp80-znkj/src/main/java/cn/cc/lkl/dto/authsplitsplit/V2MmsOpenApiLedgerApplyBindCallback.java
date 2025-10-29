package cn.cc.lkl.dto.authsplitsplit;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 分账关系绑定 回调
 * https://o.lakala.com/#/home/document/detail?id=387
 */
@Data
public class V2MmsOpenApiLedgerApplyBindCallback {


    /**
     * 申请编号	000000
     */
    @JsonProperty("applyId")
    private String applyId;

    /**
     * 操作类型	新增
     */
    @JsonProperty("optType")
    private String optType;
    /**
     * 商户号
     */
    @JsonProperty("merCupNo")
    private String merCupNo;
    /**
     * 商户号
     */
    @JsonProperty("merInnerNo")
    private String merInnerNo;
    /**
     * 接收方编号	SR2022021813005
     */
    @JsonProperty("receiverNo")
    private String receiverNo;
    /**
     * 审核状态编码	1:通过，2拒绝
     */
    @JsonProperty("auditStatus")
    private String auditStatus;
    /**
     * 审核状态
     */
    @JsonProperty("auditStatusText")
    private String auditStatusText;
    /**
     * 审核说明（拒绝时的具体原因）	合作协议信息不完整
     */
    @JsonProperty("remark")
    private String remark;
    /**
     * 附件
     */
    @JsonProperty("entrustFileName")
    private String entrustFileName;
    /**
     * 附件路径
     */
    @JsonProperty("entrustFilePath")
    private String entrustFilePath;


}

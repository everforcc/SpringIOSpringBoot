package cn.cc.lkl.dto.split;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 商户分账业务开通申请回调请求
 * 异步审核结果通知参数
 * 
 * @author cc
 */
@Data
public class V2MmsOpenApiLedgerApplyLedgerMerCallbackRequest {

    /**
     * 申请编号
     * String
     * 示例: 681201215598657536
     */
    @JsonProperty("applyId")
    private String applyId;

    /**
     * 商户号
     * String
     * 示例: 822*******
     */
    @JsonProperty("merCupNo")
    private String merCupNo;

    /**
     * 拉卡拉内部商户号
     * String
     * 示例: 4002021033012340711
     */
    @JsonProperty("merInnerNo")
    private String merInnerNo;

    /**
     * 审核状态
     * String
     * 1:通过, 2:拒绝
     * 示例: 1
     */
    @JsonProperty("auditStatus")
    private String auditStatus;

    /**
     * 审核状态文本
     * String
     * 示例: 审核通过
     */
    @JsonProperty("auditStatusText")
    private String auditStatusText;

    /**
     * 附件文件名称
     * String
     * 示例: 授权委托书.pdf
     */
    @JsonProperty("entrustFileName")
    private String entrustFileName;

    /**
     * 附件文件路径
     * String
     * 示例: G1/M00/00/16/CrFdEl0wGu6AHwGQAAAz1tt6luo194.jpg
     */
    @JsonProperty("entrustFilePath")
    private String entrustFilePath;

    /**
     * 回调通知地址
     * String
     * 示例: http://run.mocky.io/v3/b02c9448-20a2-4ff6-a678-38ecab30161d
     */
    @JsonProperty("retUrl")
    private String retUrl;

    /**
     * 备注
     * String
     * 示例: 审核通过
     */
    @JsonProperty("remark")
    private String remark;

    /**
     * 上传附件类型
     * String
     * 示例: SPLIT_ENTRUST_FILE
     */
    @JsonProperty("uploadAttachType")
    private String uploadAttachType;
}

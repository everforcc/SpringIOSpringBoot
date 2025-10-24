package cn.cc.lkl.dto.authrealname;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Set;

/**
 * 微信实名认证相应结果
 */
@Data
public class V2MmsOpenApiWechatRealNameQueryResponse {

    /**
     * 商户内部商户号
     */
    @JsonProperty("merInnerNo")
    private String merInnerNo;
    /**
     * 账户端子商户号
     */
    @JsonProperty("subMchId")
    private String subMchId;
    /**
     * 渠道号
     */
    @JsonProperty("channelId")
    private String channelId;
    /**
     * 从业机构号
     */
    @JsonProperty("receOrgNo")
    private String receOrgNo;
    /**
     * 申请编号
     */
    @JsonProperty("applymentId")
    private String applymentId;
    /**
     * 申请状态
     * APPLYMENT_STATE_FAIL：提交失败； APPLYMENT_STATE_COMMIT：已提交； APPLYMENT_STATE_WAITTING_FOR_AUDIT：审核中；APPLYMENT_STATE_EDITTING：编辑中；APPLYMENT_STATE_WAITTING_FOR_CONFIRM_CONTACT：待确认联系信息；APPLYMENT_STATE_WAITTING_FOR_CONFIRM_LEGALPERSON：待账户验证；APPLYMENT_STATE_PASSED：审核通过； APPLYMENT_STATE_REJECTED：审核驳回； APPLYMENT_STATE_FREEZED：已冻结； APPLYMENT_STATE_CANCELED：已作废。
     */
    @JsonProperty("applymentState")
    private String applymentState;
    /**
     * 认证状态
     * AUTHORIZE_STATE_UNAUTHORIZED：未授权AUTHORIZE_STATE_AUTHORIZED ：已授权
     */
    @JsonProperty("authorizeState")
    private String authorizeState;
    /**
     * 报备通道
     */
//    @JsonProperty("registerChannel")
    private Set<String> registerChannel;
    /**
     * 小程序码图片
     * 1、当申请单状态为APPLYMENT_STATE_WAITTING_FOR_CONFIRM_CONTACT、APPLYMENT_STATE_WAITTING_FOR_CONFIRM_LEGALPERSON、APPLYMENT_STATE_PASSED、APPLYMENT_STATE_FREEZED时，会返回二维码图片。2、可用img标签直接加载该图片。示例如下：<’img src=”data:image/png;base64,iVBORw0KGgoAAAANSU=” style=”display: block;”>
     */
    @JsonProperty("qrcodeData")
    private String qrcodeData;
    /**
     * 驳回参数
     */
    @JsonProperty("rejectParameter")
    private String rejectParameter;
    /**
     * 驳回原因
     */
    @JsonProperty("rejectReason")
    private String rejectReason;



}

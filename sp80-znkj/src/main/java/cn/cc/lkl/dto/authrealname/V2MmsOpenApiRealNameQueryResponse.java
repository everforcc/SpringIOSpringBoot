package cn.cc.lkl.dto.authrealname;

import lombok.Data;

/**
 * https://o.lakala.com/#/home/document/detail?id=345
 * 支付宝实名认证信息查询
 * /api/v2/mms/openApi/alipayRealNameQuery
 */
@Data
public class V2MmsOpenApiRealNameQueryResponse {

    /**
     * 拉卡拉内部商户号
     */
    private String merInnerNo;

    /**
     * 账户端子商户号
     */
    private String subMchId;

    /**
     * 渠道号
     */
    private String channelId;

    /**
     * 从业机构号
     */
    private String receOrgNo;

    /**
     * 申请编号
     */
    private String applymentId;

    /**
     * 申请状态
     * 取值说明:
     * APPLYMENT_STATE_FAIL：提交失败；
     * APPLYMENT_STATE_COMMIT：已提交；
     * 支付宝实名状态：
     * AUDITING：支付宝-审核中；
     * CONTACT_CONFIRM：支付宝-待联系人确认；
     * LEGAL_CONFIRM：支付宝-待法人确认；
     * AUDIT_PASS：支付宝-审核通过；
     * AUDIT_REJECT：支付宝-审核驳回；
     * AUDIT_FREEZE：支付宝-已冻结；
     * CANCELED：支付宝-已撤回；
     */
    private String applymentState;

    /**
     * 认证状态
     * 取值说明:
     * 支付宝认证状态：
     * UNAUTHORIZED：支付宝-未授权
     * AUTHORIZED：支付宝-已授权
     * CLOSED：支付宝-已销户
     * SMID_NOT_EXIST：支付宝-SMID不存在
     */
    private String authorizeState;

    /**
     * 报备通道
     */
    private String registerChannel;

    /**
     * 实名认证类型
     * 取值说明:
     * WXZF:微信
     * ZFBZF:支付宝
     */
    private String realNameType;

    /**
     * 小程序码链接
     * 说明：当申请单状态为CONTACT_CONFIRM、LEGAL_CONFIRM、AUDIT_PASS、AUDIT_FREEZE时，会返回此链接。
     */
    private String qrcodeData;

    /**
     * 驳回参数
     */
    private String rejectParameter;

    /**
     * 驳回原因
     */
    private String rejectReason;
}

//package cn.cc.lkl.dto.split;
//
//import cn.cc.lkl.dto.LKLBaseRequestV2;
//import cn.cc.lkl.enums.FunctionCodeEnum;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import lombok.Data;
//
//import java.util.List;
//
///**
// * 商户分账业务开通申请请求参数
// * https://o.lakala.com/#/home/document/detail?id=379
// *
// * @author cc
// */
//@Data
//public class V2MmsOpenApiLedgerApplyLedgerMerRequest extends LKLBaseRequestV2 {
//
//    /**
//     * 接口版本号
//     * 必传
//     * String 8位
//     * 取值: 1.0
//     */
//    @JsonProperty("version")
//    private String version;
//
//    /**
//     * 订单编号
//     * 必传
//     * String 32位
//     * 14位年月日时(24小时制)分秒+8位的随机数(不重复) 如:2021020112000012345678
//     */
//    @JsonProperty("orderNo")
//    private String orderNo;
//
//    /**
//     * 机构代码
//     * 必传
//     * String 32位
//     */
//    @JsonProperty("orgCode")
//    private String orgCode;
//
//    /**
//     * 拉卡拉内部商户号
//     * 可选
//     * String 32位
//     * 拉卡拉内部商户号和银联商户号必须传一个,都送以内部商户号为准
//     */
//    @JsonProperty("merInnerNo")
//    private String merInnerNo;
//
//    /**
//     * 银联商户号
//     * 可选
//     * String 32位
//     * 拉卡拉内部商户号和银联商户号必须传一个,都送以内部商户号为准
//     */
//    @JsonProperty("merCupNo")
//    private String merCupNo;
//
//    /**
//     * 联系手机号
//     * 必传
//     * String 32位
//     */
//    @JsonProperty("contactMobile")
//    private String contactMobile;
//
//    /**
//     * 最低分账比例(百分比,支持2位精度)
//     * 必传
//     * 数字 32位
//     * 如: 70或70.50
//     */
//    @JsonProperty("splitLowestRatio")
//    private String splitLowestRatio;
//
//    /**
//     * 分账结算委托书文件名称
//     * 必传
//     * String 64位
//     * 如: 分账结算委托书文件名称.pdf
//     */
//    @JsonProperty("splitEntrustFileName")
//    private String splitEntrustFileName;
//
//    /**
//     * 分账结算委托书文件路径
//     * 必传
//     * String 64位
//     * 调用商户入网接口上传附件后反馈的文件路径
//     */
//    @JsonProperty("splitEntrustFilePath")
//    private String splitEntrustFilePath;
//
//    /**
//     * 分账范围
//     * 非必传
//     * String 32位
//     * ALL: 全部交易分账(商户所有交易默认待分账)
//     * MARK: 标记交易分账(只有带分账标识交易待分账,其余交易正常结算)
//     * 默认: MARK
//     */
//    @JsonProperty("splitRange")
//    private String splitRange;
//
//    /**
//     * 分账依据
//     * 非必传
//     * String 32位
//     * TR: 交易分账
//     * BA: 余额分账
//     * 默认: TR交易分账
//     */
//    @JsonProperty("sepFundSource")
//    private String sepFundSource;
//
//    /**
//     * 电子合同编号
//     * 非必传
//     * String 32位
//     * 如果已经签署过电子合同此处上送电子合同编号即可,供审核人员复核使用
//     */
//    @JsonProperty("eleContractNo")
//    private String eleContractNo;
//
//    /**
//     * 分账发起方式
//     * 非必传
//     * String 32位
//     * AUTO: 自动规则分账
//     * POINTRULE: 指定规则分账
//     * MANUAL: 手动分账
//     * 不填默认MANUAL
//     */
//    @JsonProperty("splitLaunchMode")
//    private String splitLaunchMode;
//
//    /**
//     * 提款类型
//     * 非必传
//     * String 32位
//     * 01:主动提款
//     * 03:交易自动结算
//     * 不填默认01
//     */
//    @JsonProperty("settleType")
//    private String settleType;
//
//    /**
//     * 分账规则来源
//     * 条件必传
//     * String 32位
//     * MER: 商户分账规则
//     * PLATFORM: 平台分账规则
//     * 分账发起方式为自动规则或指定规则时必传
//     */
//    @JsonProperty("splitRuleSource")
//    private String splitRuleSource;
//
//    /**
//     * 回调通知地址
//     * 必传
//     * String 128位
//     * 请确保可以直接访问,避免接收不到我方通知结果
//     *
//     * 走统一回调逻辑
//     */
//    @JsonProperty("retUrl")
//    private String retUrl;
//
//    /**
//     * 附加资料
//     * 可选
//     * 集合
//     * 其它附加资料文件信息
//     */
//    @JsonProperty("attachments")
//    private List<AttachmentInfo> attachments;
//
//    @Override
//    public FunctionCodeEnum getFunctionCode() {
//        return FunctionCodeEnum.API_V2_MMS_OPENAPI_LEDGER_APPLYLEDGERMER;
//    }
//
//    /**
//     * 附加资料信息
//     */
//    @Data
//    public static class AttachmentInfo {
//        /**
//         * 附件类型编码
//         */
//        @JsonProperty("attachType")
//        private String attachType;
//
//        /**
//         * 附件名称
//         */
//        @JsonProperty("attachName")
//        private String attachName;
//
//        /**
//         * 附件路径
//         * 附件上传接口返回的信息
//         */
//        @JsonProperty("attachStorePath")
//        private String attachStorePath;
//    }
//
//}

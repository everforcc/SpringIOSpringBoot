package cn.cc.lkl.dto.scanpreorder;

import lombok.Data;

import java.util.List;

/**
 * V3Labs交易预下单回调接口
 */
@Data
public class V3LabsTransPreorderCallback {

    /**
     * 商户号
     */
    private String merchantNo;

    /**
     * 商户交易流水号
     */
    private String outTradeNo;

    /**
     * 拉卡拉交易流水号
     */
    private String tradeNo;

    /**
     * 拉卡拉对账单流水号
     */
    private String logNo;

    /**
     * 账户端交易订单号
     */
    private String accTradeNo;

    /**
     * 钱包类型
     * 微信：WECHAT 支付宝：ALIPAY 银联：UQRCODEPAY 翼支付: BESTPAY 苏宁易付宝: SUNING 数字人民币-DCPAY
     */
    private String accountType;

    /**
     * 结算商户号
     */
    private String settleMerchantNo;

    /**
     * 结算终端号
     */
    private String settleTermNo;

    /**
     * 交易状态
     * INIT-初始化 CREATE-下单成功 SUCCESS-交易成功 FAIL-交易失败 DEAL-交易处理中 UNKNOWN-未知状态 CLOSE-订单关闭 PART_REFUND-部分退款 REFUND-全部退款 REVOKED-订单撤销
     */
    private String tradeStatus;

    /**
     * 订单金额
     * 单位分，整数数字型字符
     */
    private String totalAmount;

    /**
     * 付款人实付金额
     * 单位分
     */
    private String payerAmount;

    /**
     * 账户端结算金额
     * 单位分，账户端应结订单金额=付款人实际发生金额+账户端优惠金额
     */
    private String accSettleAmount;

    /**
     * 商户侧优惠金额（账户端）
     * 单位分
     */
    private String accMdiscountAmount;

    /**
     * 账户端优惠金额
     * 单位分
     */
    private String accDiscountAmount;

    /**
     * 账户端其它优惠金额
     * 单位分
     */
    private String accOtherDiscountAmount;

    /**
     * 交易完成时间
     * 实际支付时间。yyyyMMddHHmmss
     */
    private String tradeTime;

    /**
     * 用户标识1
     * 微信sub_open_id, 支付宝buyer_logon_id（买家支付宝账号）
     */
    private String userId1;

    /**
     * 用户标识2
     * 微信openId,支付宝buyer_user_id,银联user_id
     */
    private String userId2;

    /**
     * 活动 ID
     * 在账户端商户后台配置的批次 ID
     */
    private String accActivityId;

    /**
     * 付款银行
     */
    private String bankType;

    /**
     * 银行卡类型
     * 00：借记 01：贷记 02：微信零钱 03：支付宝花呗 04：支付宝其他 05：数字货币 06：拉卡拉支付账户 99：未知
     */
    private String cardType;

    /**
     * 备注
     */
    private String remark;

    /**
     * 花呗分期支付信息
     */
    private HbFqPayInfo hbFqPayInfo;

    /**
     * 子商户号
     * 账户端子商户号
     */
    private String subMchId;

    /**
     * 合单信息
     */
    private List<OutSplitRspInfo> outSplitRspInfos;

    /**
     * 单品券优惠的商品优惠信息
     */
    private String discountGoodsDetail;

    // 回调地址
    private String notifyUrl;

    /**
     * 交易状态
     */
    private String tradeState;

    /**
     * 花呗分期支付信息
     */
    @Data
    public static class HbFqPayInfo {
        /**
         * 分期金额
         * 单位分，整数数字型字符
         */
        private String fqAmount;

        /**
         * 分期期数
         * 分期期数，整数数字型字符
         */
        private String userInstallNum;
    }

    /**
     * 合单信息
     */
    @Data
    public static class OutSplitRspInfo {
        /**
         * 子单拉卡拉流水号
         */
        private String subTradeNo;

        /**
         * 子单对账流水号
         */
        private String subLogNo;

        /**
         * 子单外部流水号
         */
        private String outSubTradeNo;

        /**
         * 子单商户号
         */
        private String merchantNo;

        /**
         * 子单终端号
         */
        private String termNo;

        /**
         * 子单金额
         * 单位：分
         */
        private String amount;
    }

    /**
     * 单品券优惠的商品优惠信息
     */
    @Data
    public static class DiscountGoodsDetail {
        /**
         * 商品id
         */
        private String goodsId;

        /**
         * 商品名称
         */
        private String goodsName;

        /**
         * 优惠金额
         */
        private String discountAmount;

        /**
         * 优惠id
         */
        private String voucherId;
    }
}

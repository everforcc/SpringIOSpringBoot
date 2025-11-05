package cn.cc.lkl.dto.scanpreorder;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * https://o.lakala.com/#/home/document/detail?id=112
 * 扫码被扫支付 响应
 * 
 * @author cc
 */
@Data
public class V3LabsTransMicropayResponse {

    /**
     * 是否需要发起查询
     * 必填
     * String 32位
     * 0=不需要 1=需要 当返回1时，代表订单处理中，商户需主动发起查询
     */
    @JsonProperty("need_query")
    private String needQuery;

    /**
     * 商户号
     * 必填
     * String 32位
     * 拉卡拉分配的商户号（请求接口中商户号）
     */
    @JsonProperty("merchant_no")
    private String merchantNo;

    /**
     * 商户交易流水号
     * 必填
     * String 32位
     * 请求报文中的商户交易流水号
     */
    @JsonProperty("out_trade_no")
    private String outTradeNo;

    /**
     * 拉卡拉交易流水号
     * 必填
     * String 32位
     * 拉卡拉交易流水号
     */
    @JsonProperty("trade_no")
    private String tradeNo;

    /**
     * 拉卡拉对账单流水号
     * 必填
     * String 14位
     * 拉卡拉对账单流水号
     */
    @JsonProperty("log_no")
    private String logNo;

    /**
     * 账户端交易订单号
     * 条件必填
     * String 32位
     * 账户端交易流水号
     */
    @JsonProperty("acc_trade_no")
    private String accTradeNo;

    /**
     * 钱包类型
     * 必填
     * String 16位
     * 微信：WECHAT 支付宝：ALIPAY 银联：UQRCODEPAY 翼支付: BESTPAY 数字货币：DCPAY
     */
    @JsonProperty("account_type")
    private String accountType;

    /**
     * 订单金额
     * 必填
     * String 12位
     * 单位分，整数数字型字符 订单金额=付款人实际发生金额+商户优惠金额+账户端优惠金额
     */
    @JsonProperty("total_amount")
    private String totalAmount;

    /**
     * 付款人实际发生金额
     * 必填
     * String 12位
     */
    @JsonProperty("payer_amount")
    private String payerAmount;

    /**
     * 账户端应结订单金额
     * 必填
     * String 12位
     * 应结订单金额，单位分，账户端应结订单金额=付款人实际发生金额+账户端优惠金额
     */
    @JsonProperty("acc_settle_amount")
    private String accSettleAmount;

    /**
     * 商户优惠金额（账户端）
     * 条件必填
     * String 12位
     * 账户端返回商户优惠金额，单位分
     */
    @JsonProperty("acc_mdiscount_amount")
    private String accMdiscountAmount;

    /**
     * 账户端优惠金额
     * 条件必填
     * String 12位
     * 账户端返回账户端优惠金额，单位分
     */
    @JsonProperty("acc_discount_amount")
    private String accDiscountAmount;

    /**
     * 账户端其它优惠金额
     * 条件必填
     * String 12位
     * 账户端返回账户端其它优惠金额，单位分
     */
    @JsonProperty("acc_other_discount_amount")
    private String accOtherDiscountAmount;

    /**
     * 交易完成时间
     * 必填
     * String 14位
     * 以账户端返回时间为准
     */
    @JsonProperty("trade_time")
    private String tradeTime;

    /**
     * 付款银行
     * 条件必填
     * String 128位
     * 付款银行
     */
    @JsonProperty("bank_type")
    private String bankType;

    /**
     * 银行卡类型
     * 条件必填
     * String 16位
     * 00：借记 01：贷记 02：微信零钱 03：支付宝花呗 04：支付宝其他 05：数字货币 06：拉卡拉支付账户 99：未知
     */
    @JsonProperty("card_type")
    private String cardType;

    /**
     * 备注
     * 条件必填
     * String 128位
     */
    @JsonProperty("remark")
    private String remark;

    /**
     * 账户端返回信息域
     * 条件必填
     * Object
     * 账户端返回信息域
     */
    @JsonProperty("acc_resp_fields")
    private Object accRespFields;
}

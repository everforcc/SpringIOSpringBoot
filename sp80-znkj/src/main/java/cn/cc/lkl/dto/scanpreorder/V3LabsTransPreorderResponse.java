package cn.cc.lkl.dto.scanpreorder;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 扫码预下单 响应
 * https://o.lakala.com/#/home/document/detail?id=110
 * 
 * @author cc
 */
@Data
public class V3LabsTransPreorderResponse {

    /**
     * 商户号
     * 必填
     * String 32位
     * 拉卡拉分配的商户号（请求接口中商户号）
     */
    @JsonProperty("merchant_no")
    private String merchantNo;

    /**
     * 商户请求流水号
     * 必填
     * String 32位
     * 请求报文中的商户请求流水号
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
     * 结算商户号
     * 必填
     * String 32位
     * 拉卡拉分配的商户号
     */
    @JsonProperty("settle_merchant_no")
    private String settleMerchantNo;

    /**
     * 结算终端号
     * 必填
     * String 32位
     * 拉卡拉分配的业务终端号
     */
    @JsonProperty("settle_term_no")
    private String settleTermNo;

    /**
     * 账户端返回信息域
     * 条件必填
     * Object
     * 账户端返回信息域
     * 不同场景下结构不同：
     * 支付宝(41-NATIVE): AlipayNativeRespFields
     * 支付宝(51-JSAPI): AlipayJsapiRespFields
     * 微信(71-小程序/51-JSAPI): WechatJsapiRespFields
     */
    @JsonProperty("acc_resp_fields")
    private Object accRespFields;

    /**
     * 支付宝(41-NATIVE)场景下返回acc_resp_fields域
     */
    @Data
    public static class AlipayNativeRespFields {
        /**
         * 二维码信息
         * 必填
         * String 256位
         * 商户可用此参数自定义去生成二维码后展示出来进行扫码支付
         */
        @JsonProperty("code")
        private String code;

        /**
         * 二维码图片内容
         * 必填
         * String 256位
         * 商户收款二维码图片。Base64编码，暂无
         */
        @JsonProperty("code_image")
        private String codeImage;

        /**
         * 子商户号
         * 条件必填
         * String 20位
         * 账户端子商户号
         */
        @JsonProperty("sub_mch_id")
        private String subMchId;
    }

    /**
     * 支付宝(51-JSAPI)场景下返回acc_resp_fields域
     */
    @Data
    public static class AlipayJsapiRespFields {
        /**
         * 预下单Id
         * 必填
         * String 32位
         * 预支付交易会话ID
         */
        @JsonProperty("prepay_id")
        private String prepayId;

        /**
         * 子商户号
         * 条件必填
         * String 20位
         * 账户端子商户号
         */
        @JsonProperty("sub_mch_id")
        private String subMchId;
    }

    /**
     * 微信(71-小程序)微信(51-JSAPI)场景下返回acc_resp_fields域
     */
    @Data
    public static class WechatJsapiRespFields {
        /**
         * 预下单Id
         * 必填
         * String 32位
         * 预支付交易会话ID
         */
        @JsonProperty("prepay_id")
        private String prepayId;

        /**
         * 支付签名信息
         * 必填
         * String 256位
         * 签名
         */
        @JsonProperty("pay_sign")
        private String paySign;

        /**
         * 小程序id
         * 必填
         * String 32位
         * 商户注册具有支付权限的小程序成功后即可获得小程序id
         */
        @JsonProperty("app_id")
        private String appId;

        /**
         * 时间戳
         * 必填
         * String 32位
         * 当前的时间
         */
        @JsonProperty("time_stamp")
        private String timeStamp;

        /**
         * 随机字符串
         * 必填
         * String 32位
         * 随机字符串
         */
        @JsonProperty("nonce_str")
        private String nonceStr;

        /**
         * 订单详情扩展字符串
         * 必填
         * String 128位
         */
        @JsonProperty("package")
        private String packageValue;

        /**
         * 签名方式
         * 必填
         * String 32位
         * 签名类型，支持RSA
         */
        @JsonProperty("sign_type")
        private String signType;

        /**
         * 子商户号
         * 条件必填
         * String 20位
         * 账户端子商户号
         */
        @JsonProperty("sub_mch_id")
        private String subMchId;
    }
}

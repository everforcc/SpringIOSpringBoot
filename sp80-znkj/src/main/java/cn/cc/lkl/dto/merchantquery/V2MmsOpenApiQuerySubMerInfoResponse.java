package cn.cc.lkl.dto.merchantquery;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * 商户报备结果 响应
 * https://o.lakala.com/#/home/document/detail?id=326
 */
@Data
public class V2MmsOpenApiQuerySubMerInfoResponse {

    /**
     * 机构代码（合作方在拉卡拉的标识）
     */
    @JsonProperty("orgCode")
    private String orgCode;

    /**
     * 订单号（请求的订单编号）
     */
    @JsonProperty("orderNo")
    private String orderNo;

    /**
     * 报备明细（报备信息集合）
     */
    @JsonProperty("list")
    private List<ReportInfo> list;

    /**
     * 报备信息
     */
    @Data
    public static class ReportInfo {
        /**
         * 内部商户号：400或500商户号
         */
        @JsonProperty("merInnerNo")
        private String merInnerNo;
        /**
         * 子商户号：合作方在拉卡拉的标识
         */
        @JsonProperty("subMchId")
        private String subMchId;
        /**
         * 交易子商户号：请求的订单编号
         */
        @JsonProperty("subMchIdBank")
        private String subMchIdBank;
        /**
         * 数币钱包ID
         */
        @JsonProperty("dcWalletId")
        private String dcWalletId;
        /**
         * 渠道号
         */
        @JsonProperty("channelNo")
        private String channelNo;
        /**
         * 从业机构号
         */
        @JsonProperty("receOrgNo")
        private String receOrgNo;
        /**
         * 报备渠道
         */
        @JsonProperty("registerChannel")
        private String registerChannel;
        /**
         * 报备类型
         * ZFBZF：支付宝
         * WXZF：微信
         * SNZF：苏宁钱包
         * YZF：翼支付
         * SZHB：数字货币
         * NUCC：互联互通
         * UNION：银联二维码
         */
        @JsonProperty("registerType")
        private String registerType;
        /**
         * 报备时间
         */
        @JsonProperty("registerTm")
        private String registerTm;
        /**
         * 报备状态
         */
        @JsonProperty("registerStatus")
        private String registerStatus;
        /**
         * 结果返回码
         */
        @JsonProperty("resultCode")
        private String resultCode;
        /**
         * 结果描述
         */
        @JsonProperty("resultMessage")
        private String resultMessage;
    }
}

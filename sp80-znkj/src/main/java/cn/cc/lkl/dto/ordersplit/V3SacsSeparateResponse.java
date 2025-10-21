package cn.cc.lkl.dto.ordersplit;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 订单分账 响应
 * https://o.lakala.com/#/home/document/detail?id=389
 * V3SacsSeparateCallback
 * @author cc
 */
@Data
public class V3SacsSeparateResponse {

    /**
     * 分账指令流水号
     * 必填
     * String 32位
     * 分账系统生成唯一流水
     */
    @JsonProperty("separate_no")
    private String separateNo;

    /**
     * 商户订单号
     * 必填
     * String 32位
     * 请求报文中的商户外部订单号
     */
    @JsonProperty("out_separate_no")
    private String outSeparateNo;

    /**
     * 分账状态
     * 条件必填
     * String 16位
     * PROCESSING: 处理中
     * ACCEPTED: 已受理
     * SUCCESS: 成功
     * FAIL: 失败
     */
    @JsonProperty("status")
    private String status;

    /**
     * 拉卡拉对账单流水号
     * 条件必填
     * String 14位
     * 请求透返
     */
    @JsonProperty("log_no")
    private String logNo;

    /**
     * 拉卡拉订单日期
     * 条件必填
     * String 8位
     * POSP日期, yyyyMMdd, 查清结算用
     */
    @JsonProperty("log_date")
    private String logDate;

    /**
     * 分账总金额
     * 条件必填
     * String 15位
     * 单位:分
     */
    @JsonProperty("total_amt")
    private String totalAmt;
}

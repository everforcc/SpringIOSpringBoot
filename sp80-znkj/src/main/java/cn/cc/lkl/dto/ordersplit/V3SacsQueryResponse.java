package cn.cc.lkl.dto.ordersplit;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * 分账结果查询 响应
 * https://o.lakala.com/#/home/document/detail?id=392
 * 
 * @author cc
 */
@Data
public class V3SacsQueryResponse {

    /**
     * 分账指令流水号
     * 必填
     * String 32位
     * 请求透返
     */
    @JsonProperty("separate_no")
    private String separateNo;

    /**
     * 商户分账指令流水号
     * 必填
     * String 32位
     * 请求透返
     */
    @JsonProperty("out_separate_no")
    private String outSeparateNo;

    /**
     * 指令类型
     * 必填
     * String 32位
     * SEPARATE:分账, CANCEL:分账撤销, FALLBACK:分账回退
     */
    @JsonProperty("cmd_type")
    private String cmdType;

    /**
     * 拉卡拉对账单流水号
     * 必填
     * String 14位
     */
    @JsonProperty("log_no")
    private String logNo;

    /**
     * 交易日期
     * 必填
     * String 8位
     * posp日期,yyyyMMdd,查清结算用
     */
    @JsonProperty("log_date")
    private String logDate;

    /**
     * 分账计算类型
     * 条件必填
     * String 2位
     * 0按照指定金额。1按照指定比例,默认0 (cmd_type为SEPARATE分账指令类型才有值)
     */
    @JsonProperty("cal_type")
    private String calType;

    /**
     * 分账日期
     * 条件必填
     * String 8位
     * yyyyMMdd
     */
    @JsonProperty("separate_date")
    private String separateDate;

    /**
     * 完成日期
     * 条件必填
     * String 8位
     * yyyyMMdd
     */
    @JsonProperty("finish_date")
    private String finishDate;

    /**
     * 发生总金额
     * 条件必填
     * String 15位
     * 单位:分
     */
    @JsonProperty("total_amt")
    private String totalAmt;

    /**
     * 分账状态
     * 必填
     * String 32位
     * ACCEPTED:已受理, PROCESSING:处理中, FAIL:失败, SUCCESS:成功,
     * (如果分账指令后有反向操作指令,则原分账指令会变更成以下的状态之一:)
     * CANCELING:撤销中, CANCELED:撤销成功, CANCEL_FAIL:撤销失败,
     * FALLBACKING:回退中, FALLBACK_END:回退结束
     */
    @JsonProperty("status")
    private String status;

    /**
     * 处理状态
     * 必填
     * String 32位
     * ACCEPTED:已受理, PROCESSING:处理中, FAIL:失败, SUCCESS:成功
     */
    @JsonProperty("final_status")
    private String finalStatus;

    /**
     * 分账前置规则ID
     * 条件必填
     * String 64位
     * 分账前置请求透返
     */
    @JsonProperty("front_rule_id")
    private String frontRuleId;

    /**
     * 实分金额
     * 条件必填
     * String 15位
     * 若该笔分账收取手续费,则该字段有值
     */
    @JsonProperty("actual_separate_amt")
    private String actualSeparateAmt;

    /**
     * 手续费金额
     * 条件必填
     * String 15位
     * 若该笔分账收取手续费,则该字段有值
     */
    @JsonProperty("total_fee_amt")
    private String totalFeeAmt;

    /**
     * 明细数据
     * 条件必填
     * List<DetailData>
     */
    @JsonProperty("detail_datas")
    private List<DetailData> detailDatas;

    /**
     * 明细数据内部类
     */
    @Data
    public static class DetailData {
        /**
         * 接收方商户号
         * 条件必填
         * String 32位
         */
        @JsonProperty("recv_merchant_no")
        private String recvMerchantNo;

        /**
         * 接收方编号
         * 条件必填
         * String 32位
         */
        @JsonProperty("recv_no")
        private String recvNo;

        /**
         * 分账金额
         * 必填
         * String 32位
         */
        @JsonProperty("amt")
        private String amt;
    }
}

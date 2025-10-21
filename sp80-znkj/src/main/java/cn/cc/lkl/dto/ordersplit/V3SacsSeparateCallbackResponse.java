package cn.cc.lkl.dto.ordersplit;

import lombok.Data;
import java.util.List;

/**
 * 分账结果通知
 * https://o.lakala.com/#/home/document/detail?id=393
 */
@Data
public class V3SacsSeparateCallbackResponse {

    /**
     * 分账指令流水号
     * 请求透返
     */
    private String separateNo;

    /**
     * 商户分账指令流水号
     * 请求透返
     */
    private String outSeparateNo;

    /**
     * 指令类型
     * SEPARATE：分账 CANCEL：分账撤销 FALLBACK：分账回退
     */
    private String cmdType;

    /**
     * 拉卡拉对账单流水号
     */
    private String logNo;

    /**
     * 交易日期
     * posp日期，yyyyMMdd，查清结算用
     */
    private String logDate;

    /**
     * 分账计算类型
     * 0 按照指定金额。1 按照指定比例，默认 0
     */
    private String calType;

    /**
     * 分账接收类型
     * 0 全部分账到商户本身。1 分账到多方，默认 1
     */
    private String separateType;

    /**
     * 分账日期
     * yyyyMMdd
     */
    private String separateDate;

    /**
     * 完成日期
     * yyyyMMdd
     */
    private String finishDate;

    /**
     * 发生总金额
     * 单位：分
     */
    private String totalAmt;

    /**
     * 分账状态
     * ACCEPTED:已受理, PROCESSING:处理中, FAIL:失败, SUCCESS:成功, CANCELING:撤销中, CANCELED:撤销成功, CANCEL_FAIL:撤销失败, FALLBACKING:回退中, FALLBACK_END:回退结束
     */
    private String status;

    /**
     * 处理状态
     * ACCEPTED:已受理, PROCESSING:处理中, FAIL:失败, SUCCESS:成功
     */
    private String finalStatus;

    /**
     * 明细数据
     */
    private List<DetailData> detailDatas;

    /**
     * 明细数据
     */
    @Data
    public static class DetailData {
        /**
         * 接收方商户号
         */
        private String recvMerchantNo;

        /**
         * 接收方编号
         */
        private String recvNo;

        /**
         * 分账金额
         */
        private String amt;
    }
}

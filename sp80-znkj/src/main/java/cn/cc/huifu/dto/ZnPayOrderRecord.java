package cn.cc.huifu.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 订单详情对象 zn_pay_order_record
 *
 * 园区云端
 * @author dmw
 * @date 2024-06-06
 */
@Data
// @TableName(value = "zn_pay_order_record")
public class ZnPayOrderRecord {

    /**
     * 主键
     */
    private Long id;

    /**
     * 创建人
     */
    private Long createId;
    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新人
     */
    private Long updateId;
    /**
     * 更新人
     */
    private String updateBy;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 备注
     */
    private String remark;


    private Long createDept;

    /**
     * 删除标记
     */
    private Integer delFlag;

    /**
     * 请求ID
     */
    private String reqSeqid;

    /**
     * 请求时间
     */
    private String reqDate;

    /**
     * 付款价格 单位:分
     */
    private Long transAmt;

    /**
     * 类型
     */
    private String tradeType;

    /**
     * 商品描述
     */
    private String goodsDesc;
    /**
     *
     * 认证类型
     * 1 汇付 历史数据为空
     * 2 拉卡拉
     */
    private String authType;
    /**
     * 汇付ID
     */
    private String huifuid;

    /**
     * 远程通知URL
     */
    private String notifyUrl;

    /**
     * 0 创建 1 支付成功 2支付失败
     */
    private Long status;

    /**
     * 阿里的QR
     */
    private String aliQrcode;

    /**
     * 其他数据
     */
    private String otherData;

    private String otherDataHfSeqId;

    /**
     * 分账数据
     */
    private String acctSplitInfo;
    /**
     * 返回的数据
     */
    private String returnData;
    /**
     * 支付金额
     */
    private Long payAmt;
    /**
     * 结算金额
     */
    private Long settlementAmt;
    /**
     * 支付时间
     */
    private Date endTime;
    /**
     * 入账时间
     */
    private String acctDate;

    private String lklSplitReq;
    private String lklSplitRes;
    private String lklSplitCallback;
    /**
     * 拉卡拉分账状态
     */
    private String lklSplitStatus;
    /**
     * 拉卡拉对账单流水号
     */
    private String lklLogNo;

}

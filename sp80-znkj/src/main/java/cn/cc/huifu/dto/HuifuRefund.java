package cn.cc.huifu.dto;

import lombok.Data;

import java.util.Date;

@Data
public class HuifuRefund {

    private int id;

    private String type;

    /**
     * 汇付请求id
     */
    private String hfSeqId;

    /**
     * 支付金额
     */
    private String amt;

    /**
     * 请求日期
     */
    private String reqDate;

    /**
     * 银行返回码
     */
    private String bankCode;

    /**
     * 返回说明
     */
    private String respDesc;

    /**
     * 响应值
     */
    private String response;

    private Date sysTime;

    private Date createTime;

    private Date updadteTime;


}

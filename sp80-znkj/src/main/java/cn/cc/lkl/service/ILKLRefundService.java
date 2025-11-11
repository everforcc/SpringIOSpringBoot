package cn.cc.lkl.service;

import cn.cc.lkl.dto.LKLCommonResponse;

public interface ILKLRefundService {

    public String refundZnkjBack(String separate_no, String totalAmt, String recvNo);

    public LKLCommonResponse refund(String merchantNo, String termNo, String outTradeNo, String refundAmount);

}

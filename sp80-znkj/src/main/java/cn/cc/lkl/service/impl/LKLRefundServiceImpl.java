package cn.cc.lkl.service.impl;

import cn.cc.config.JsonUtil;
import cn.cc.lkl.dto.LKLCommonResponse;
import cn.cc.lkl.service.ILKLRefundService;
import cn.cc.lkl.util.DateUtils;
import cn.cc.lkl.util.LKLPost;
import cn.cc.lkl.util.StringUtils;
import com.lkl.laop.sdk.exception.SDKException;
import com.lkl.laop.sdk.request.V3LabsRelationRefundRequest;
import com.lkl.laop.sdk.request.V3SacsFallbackRequest;
import com.lkl.laop.sdk.request.model.V3LabsTradeLocationInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class LKLRefundServiceImpl implements ILKLRefundService {

    @Override
    public String refundZnkjBack(String separate_no,String totalAmt,String recvNo) {
        V3SacsFallbackRequest request = new V3SacsFallbackRequest();
        request.setMerchantNo("8224910737200MK");

        // {"msg": "成功", "code": "SACS0000", "resp_data": {"log_no": "66202214531005", "status": "PROCESSING", "log_date": "20251105", "total_amt": "5", "separate_no": "20251105770188018265128900", "out_separate_no": "1183481366227066880"}, "resp_time": "20251105161339"}
        // {"msg": "成功", "code": "SACS0000", "resp_data": {"log_no": "66202219538494", "status": "PROCESSING", "log_date": "20251107", "total_amt": "10", "separate_no": "20251107770188018300920700", "out_separate_no": "1184192713902399488"}, "resp_time": "20251107152015"}
        // 8.2. znkj-res.json.resp_data..separate_no
        request.setOriginSeparateNo(separate_no);
        request.setOutSeparateNo(DateUtils.getTimeStampAndRandom());
        request.setTotalAmt(totalAmt);

        List<V3SacsFallbackRequest.OriginRecvData> originRecvDatas = new ArrayList<>();
        V3SacsFallbackRequest.OriginRecvData originRecvData = new V3SacsFallbackRequest.OriginRecvData();
        // 分出去的分账号和金额
        originRecvData.setRecvNo(recvNo);
        originRecvData.setAmt(totalAmt);

        originRecvDatas.add(originRecvData);
        request.setOriginRecvDatas(originRecvDatas);

        log.info("request: \r\n{}", JsonUtil.toJson(request));
        // {"req_data":{"merchant_no":"8224910737200MK","origin_separate_no":"20251105770188018259449600","out_separate_no":"2025110510573066807012","total_amt":"1","origin_recv_datas":[{"recv_no":"SR2024000165390","amt":"1"}]},"version":"3.0","req_time":"20251105105730"}
        //             {"merchant_no":"8224910737200MK","out_separate_no":"2025110715332561480457","total_amt":"3","origin_recv_datas":[{"recv_no":"SR2024000171654","amt":"3"}]}
//        String response = LKLSDK.httpPost(request);
        LKLCommonResponse response = LKLPost.httpPost(request);
        log.info("response: \r\n{}", JsonUtil.toJson(response));
        // {"code":"SACS0000","msg":"成功","resp_time":"20251105105730","resp_data":{"out_separate_no":"2025110510573066807012","total_amt":"1","origin_separate_no":"20251105770188018259449600","status":"PROCESSING","separate_no":"20251105770288018259546200"}}

        return JsonUtil.toJson(response);
    }

    @Override
    public LKLCommonResponse refund(String merchantNo, String termNo, String outTradeNo, String refundAmount)  {
        V3LabsRelationRefundRequest request = new V3LabsRelationRefundRequest();
        request.setMerchantNo(merchantNo);
        request.setTermNo(termNo);
        request.setOutTradeNo(StringUtils.getSerialNumber());
        request.setRefundAmount(refundAmount);
        request.setRefundReason("测试");
        // 原商户交易流水号， 订单号
        request.setOriginOutTradeNo(outTradeNo);
        // 原拉卡拉交易流水号
//        request.setOriginTradeNo("");
        // 原对账单流水号
//        request.setOriginLogNo("");
        V3LabsTradeLocationInfo locationInfo = new V3LabsTradeLocationInfo();
        locationInfo.setRequestIp("192.168.1.138");

        request.setLocationInfo(locationInfo);

        log.info("request: \r\n{}", JsonUtil.toJson(request));
        LKLCommonResponse response = LKLPost.httpPost(request);
        log.info("response: \r\n{}", JsonUtil.toJson(response));
        return response;
    }

}

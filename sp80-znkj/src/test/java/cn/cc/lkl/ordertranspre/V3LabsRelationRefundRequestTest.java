package cn.cc.lkl.ordertranspre;

import cn.cc.config.JsonUtil;
import cn.cc.lkl.LKLBaseProdTest;
import cn.cc.lkl.dto.LKLCommonResponse;
import cn.cc.lkl.util.DateUtils;
import cn.cc.lkl.util.LKLPost;
import cn.cc.lkl.util.StringUtils;
import com.lkl.laop.sdk.exception.SDKException;
import com.lkl.laop.sdk.request.V3LabsRelationRefundRequest;
import com.lkl.laop.sdk.request.V3SacsFallbackRequest;
import com.lkl.laop.sdk.request.model.V3LabsTradeLocationInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * https://o.lakala.com/#/home/document/detail?id=113
 * 扫码-退款交易
 */
@Slf4j
public class V3LabsRelationRefundRequestTest extends LKLBaseProdTest {

    /**
     * @throws SDKException
     */
    @Test
    public void test() throws SDKException {
        V3LabsRelationRefundRequest request = new V3LabsRelationRefundRequest();
        request.setMerchantNo("8224910594708XR");
        request.setTermNo("M9395646");
        request.setOutTradeNo(StringUtils.getSerialNumber());
        request.setRefundAmount("20");
        request.setRefundReason("测试");
        // 原商户交易流水号
        request.setOriginOutTradeNo("1183484517361524736");
        // 原拉卡拉交易流水号
//        request.setOriginTradeNo("");
        // 原对账单流水号
//        request.setOriginLogNo("");
        V3LabsTradeLocationInfo locationInfo = new V3LabsTradeLocationInfo();
        locationInfo.setRequestIp("10.176.1.188");

        request.setLocationInfo(locationInfo);

        log.info("request: \r\n{}", JsonUtil.toJson(request));
        LKLCommonResponse response = LKLPost.httpPost(request);
        log.info("response: \r\n{}", JsonUtil.toJson(response));
    }

    /**
     * znkj 退款
     *
     * @throws SDKException
     */
    @Test
    public void testZnkj() throws SDKException {
        V3LabsRelationRefundRequest request = new V3LabsRelationRefundRequest();
        request.setMerchantNo("8224910737200MK");
        request.setTermNo("N8905587");
        request.setOutTradeNo(StringUtils.getSerialNumber());
        request.setRefundAmount("5");
        request.setRefundReason("测试");
        // 原商户交易流水号， 订单号
        request.setOriginOutTradeNo("1183484517361524736");
        // 原拉卡拉交易流水号
//        request.setOriginTradeNo("");
        // 原对账单流水号
//        request.setOriginLogNo("");
        V3LabsTradeLocationInfo locationInfo = new V3LabsTradeLocationInfo();
        locationInfo.setRequestIp("10.176.1.188");

        request.setLocationInfo(locationInfo);

        log.info("request: \r\n{}", JsonUtil.toJson(request));
        LKLCommonResponse response = LKLPost.httpPost(request);
        log.info("response: \r\n{}", JsonUtil.toJson(response));
    }

    /**
     * 3. 订单分账回退
     * https://o.lakala.com/#/home/document/detail?id=391
     * 回退后可以退款
     *
     * @throws SDKException
     */
    @Test
    public void testZnkjBack() throws SDKException {
        V3SacsFallbackRequest request = new V3SacsFallbackRequest();
        request.setMerchantNo("8224910737200MK");

        // 8.2. znkj-res.json.resp_data..separate_no
        request.setOriginSeparateNo("20251105770188018265128900");
        request.setOutSeparateNo(DateUtils.getTimeStampAndRandom());
        request.setTotalAmt("2");

        List<V3SacsFallbackRequest.OriginRecvData> originRecvDatas = new ArrayList<>();
        V3SacsFallbackRequest.OriginRecvData originRecvData = new V3SacsFallbackRequest.OriginRecvData();
        // 分出去的分账号和金额
        originRecvData.setRecvNo("SR2024000165390");
        originRecvData.setAmt("2");

        originRecvDatas.add(originRecvData);
        request.setOriginRecvDatas(originRecvDatas);

        log.info("request: \r\n{}", JsonUtil.toJson(request));
        // {"req_data":{"merchant_no":"8224910737200MK","origin_separate_no":"20251105770188018259449600","out_separate_no":"2025110510573066807012","total_amt":"1","origin_recv_datas":[{"recv_no":"SR2024000165390","amt":"1"}]},"version":"3.0","req_time":"20251105105730"}
//        String response = LKLSDK.httpPost(request);
        LKLCommonResponse response = LKLPost.httpPost(request);
        log.info("response: \r\n{}", JsonUtil.toJson(response));
        // {"code":"SACS0000","msg":"成功","resp_time":"20251105105730","resp_data":{"out_separate_no":"2025110510573066807012","total_amt":"1","origin_separate_no":"20251105770188018259449600","status":"PROCESSING","separate_no":"20251105770288018259546200"}}

    }


}


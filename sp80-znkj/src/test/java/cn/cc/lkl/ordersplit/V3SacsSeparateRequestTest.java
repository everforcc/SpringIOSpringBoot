package cn.cc.lkl.ordersplit;

import cn.cc.config.JsonUtil;
import cn.cc.lkl.LKLBaseProdTest;
import cn.cc.lkl.dto.LKLCommonResponse;
import cn.cc.lkl.dto.ordersplit.V3SacsSeparateResponse;
import cn.cc.lkl.util.DateUtils;
import cn.cc.lkl.util.LKLPost;
import cn.cc.lkl.util.LoadFileUtil;
import com.lkl.laop.sdk.LKLSDK;
import com.lkl.laop.sdk.exception.SDKException;
import com.lkl.laop.sdk.request.V3SacsCancelRequest;
import com.lkl.laop.sdk.request.V3SacsFallbackRequest;
import com.lkl.laop.sdk.request.V3SacsQueryRequest;
import com.lkl.laop.sdk.request.V3SacsSeparateRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单分账
 * https://o.lakala.com/#/home/document/detail?id=389
 */
@Slf4j
public class V3SacsSeparateRequestTest extends LKLBaseProdTest {

    /**
     * 1. 订单分账
     *
     * @throws Exception
     */
    @Test
    public void testByJson() throws Exception {
        V3SacsSeparateRequest request = new V3SacsSeparateRequest();
        String json = LoadFileUtil.loadJsonFromResource("分账订单/znkj-req-11051519.json");
//        String reqData = JSONObject.parseObject(json).getString("req_data");
        request = JsonUtil.fromJson(json, V3SacsSeparateRequest.class);
        log.info("分账订单:{}", request);
        LKLCommonResponse lklCommonResponse = LKLPost.httpPost(request);
        log.info("分账订单结果:{}", lklCommonResponse);
        if (lklCommonResponse.resultSuccess()) {
            log.info("分账订单成功");
            V3SacsSeparateResponse response = JsonUtil.fromJson(lklCommonResponse.getRespData(), V3SacsSeparateResponse.class);
            log.info("分账订单结果:{}", response);
        } else {
            log.info("分账订单失败: {}", lklCommonResponse.getMsg());
        }
    }

//    @Test
//    public void test2() throws Exception {
//        V3SacsSeparateRequest request = new V3SacsSeparateRequest();
//        request.setMerchantNo(znPayOrderRecord.getLklMerchantNo());
//        request.setLogNo(logNo);
//        request.setLogDate(znPayOrderRecord.getCreateTime().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
//        request.setOutSeparateNo(String.valueOf(SnowFlakeUtil.getId()));
//        request.setTotalAmt(String.valueOf(znPayOrderRecord.getTransAmt()));
//
//        String acctSplitInfo = znPayOrderRecord.getAcctSplitInfo();
//        List<V3SacsSeparateRecvDatas> recvDatas = JSONUtil.toList(JSONUtil.parseArray(acctSplitInfo), V3SacsSeparateRecvDatas.class);
//        // lklPayConfig.getCallbackUrl()
//        request.setNotifyUrl("https://test-znyd.zgzhongnan.com/cc/system/lkl/open/split/notify");
//        request.setRecvDatas(recvDatas);
//
//        LKLCommonResponse lklCommonResponse = LKLPost.httpPost(request);
//        log.info("分账订单结果:{}", lklCommonResponse);
//        if (lklCommonResponse.resultSuccess()) {
//            log.info("分账订单成功");
//            V3SacsSeparateResponse response = JsonUtil.fromJson(lklCommonResponse.getRespData(), V3SacsSeparateResponse.class);
//            log.info("分账订单结果:{}", response);
//        } else {
//            log.info("分账订单失败: {}", lklCommonResponse.getMsg());
//        }
//    }

    /**
     * 分账回退
     *
     */

    /**
     * 2. 分账撤销
     * 撤销后可以执行第一步重新分账
     * <p>
     * https://o.lakala.com/#/home/document/detail?id=390
     *
     * @throws SDKException
     */
    @Test
    public void testZnkjCancel() throws SDKException {
        // {"merchant_no":"8224910737200MK","log_no":"66202212034710","log_date":"20251104","out_separate_no":"1183136932591443968","total_amt":"100","notify_url":"https://test-znyd.zgzhongnan.com/cc/system/lkl/open/split/notify","recv_datas":[{"recv_merchant_no":"8224910737200MK","separate_value":"70"},{"separate_value":"30"}]}
        // {"merchant_no":"8224910737200MK","log_no":"66202212034710","log_date":"20251104","out_separate_no":"1183136932591443968","total_amt":"100","notify_url":"https://test-znyd.zgzhongnan.com/cc/system/lkl/open/split/notify","recv_datas":[{"recv_merchant_no":"8224910737200MK","separate_value":"70"},{"separate_value":"30"}]}
        V3SacsCancelRequest request = new V3SacsCancelRequest();
        request.setMerchantNo("8224910737200MK");
        request.setOriginSeparateNo("20251104770188018246441600");
//        request.setOriginOutSeparateNo("1183136909157867520");
        request.setOutSeparateNo(DateUtils.getTimeStampAndRandom());
        request.setTotalAmt("100");

        log.info("request: \r\n{}", request.toBody());
//        String response = LKLSDK.httpPost(request);
        LKLCommonResponse response = LKLPost.httpPost(request);
        log.info("response: \r\n{}", response);


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

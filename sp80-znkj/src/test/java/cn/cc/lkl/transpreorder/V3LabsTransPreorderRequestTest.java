package cn.cc.lkl.transpreorder;

import cn.cc.config.JsonUtil;
import cn.cc.lkl.LKLBaseTest;
import cn.cc.lkl.dto.LKLCommonResponse;
import cn.cc.lkl.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lkl.laop.sdk.LKLSDK;
import com.lkl.laop.sdk.exception.SDKException;
import com.lkl.laop.sdk.request.V3LabsTransPreorderRequest;
import com.lkl.laop.sdk.request.model.V3LabsTradeLocationInfo;
import com.lkl.laop.sdk.request.model.V3LabsTradePreorderAlipayBus;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * https://o.lakala.com/#/home/document/detail?id=110
 * 聚合主扫
 */
@Slf4j
public class V3LabsTransPreorderRequestTest extends LKLBaseTest {


    @Test
    public void test() throws Exception {
        V3LabsTransPreorderRequest request = new V3LabsTransPreorderRequest();
        request.setMerchantNo("822290059430BF9");
        request.setTermNo("D9261076");

        request.setOutTradeNo(StringUtils.getSerialNumber());
        request.setAccountType("ALIPAY");
        request.setTransType("41");
        request.setTotalAmount("2");
        V3LabsTradeLocationInfo locationInfo = new V3LabsTradeLocationInfo();
        // 获取当前设备ip
        locationInfo.setRequestIp("192.168.1.188");
        // 非必填
//        locationInfo.setLocation("");
//        locationInfo.setBaseStation("");
        request.setLocationInfo(locationInfo);
        V3LabsTradePreorderAlipayBus accBusiFields = new V3LabsTradePreorderAlipayBus();
        accBusiFields.setUserId("232966729");
//        request.setAccBusiFields(accBusiFields);

        log.info("获取支付信息: {}", request.toBody());
        log.info("获取支付信息请求: \r\n{}", JsonUtil.toJson(request));
        try {
            String response = LKLSDK.httpPost(request);
            LKLCommonResponse lklCommonResponse = JSON.parseObject(response, LKLCommonResponse.class);
            log.info("获取支付信息结果: {}", JsonUtil.toJson(lklCommonResponse));
            if (!lklCommonResponse.resultSuccess()) {
                log.info("获取支付信息失败: {}", lklCommonResponse.getMsg());
                return;
            }
            log.info("获取支付信息成功: {}", lklCommonResponse.getRespData());
            Object respData = lklCommonResponse.getRespData();
            // respData 转 JSONObject
            JSONObject jsonObject = JSONObject.parseObject(respData.toString());
//            JSONObject jsonObject = JSONObject.parseObject();
            log.info("获取支付信息成功: {}", jsonObject);
        } catch (SDKException e) {
            e.printStackTrace();
        }
    }

}

package cn.cc.lkl.transpreorder;

import cn.cc.lkl.dto.LKLCommonResponse;
import cn.cc.lkl.sdk.config.LKLConfig;
import cn.cc.lkl.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lkl.laop.sdk.LKLSDK;
import com.lkl.laop.sdk.exception.SDKException;
import com.lkl.laop.sdk.request.V3LabsTransPreorderRequest;
import com.lkl.laop.sdk.request.model.V3LabsTradeAccBusiFieldInfo;
import com.lkl.laop.sdk.request.model.V3LabsTradeLocationInfo;
import com.lkl.laop.sdk.request.model.V3LabsTradePreorderAlipayBus;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

@Slf4j
public class V3LabsTransPreorderRequestTest {

    @Before
    public void pre() {
        try {
            LKLConfig.initSDK();
        } catch (SDKException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test(){
        V3LabsTransPreorderRequest request = new V3LabsTransPreorderRequest();
        request.setMerchantNo("8224910581208RL");
        request.setTermNo("A9363911");

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
        try {
            String response = LKLSDK.httpPost(request);
            LKLCommonResponse lklCommonResponse = JSON.parseObject(response, LKLCommonResponse.class);
            log.info("获取支付信息: {}", lklCommonResponse);
            if (!lklCommonResponse.isSuccess()) {
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

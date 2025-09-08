package cn.cc.lkl.merchant_encry;

import cn.cc.config.JsonUtil;
import cn.cc.lkl.dto.LKLCommonResponse;
import cn.cc.lkl.dto.merchantencry.V3TkbsMerchantEncryRequest;
import cn.cc.lkl.sdk.config.LKLConfig;
import cn.cc.lkl.util.LKLPost;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lkl.laop.sdk.exception.SDKException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 商户进件
 */
@Slf4j
public class LKLMerchantEncryTest {

    @Before
    public void pre() {
        try {
            LKLConfig.initSDK();
        } catch (SDKException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试商户进件请求
     */
    @Test
    public void merchantEncry() {

//        V3LabsTransMicropayRequest
        // 退款
//        V3LabsRelationRefundRequest

        String filePath = "C:/cc/code/gitee/SpringIOSpringBoot/sp80-znkj/docs/lkl/接口文档/test/V3TkbsMerchantEncryRequestTest.json";
        String json = null;
        try {
            json = new String(Files.readAllBytes(Paths.get(filePath)));
            // json转V3TkbsMerchantEncryRequest
            JSONObject jsonObject = JSON.parseObject(json);

            V3TkbsMerchantEncryRequest request = JsonUtil.fromJson(jsonObject.getString("req_data"), V3TkbsMerchantEncryRequest.class);

            LKLCommonResponse response4 = LKLPost.httpPostWithSm4(request, true, true);

            log.info("commonResponseDTO: \r\n{}", response4);
            log.info("isSuccess: \r\n{}", response4.isSuccess());
            log.info("getRespData: \r\n{}", response4.getRespData());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void merchantEncryOld() {
        //            log.info("request: \r\n{}", request);
//            // request 转json
//            String json2 = JSON.toJSONString(request);
//            log.info("json2: \r\n{}", json2);
//            // 对象转json的时候要恢复下划线，不要驼峰命名法
//            String json3 = JsonUtil.toJson(request);
//            log.info("json3: \r\n{}", json3);

//            String response4 = LKLSDK.httpPost(serverUrl + "/api/v3/tkbs/merchant_encry", LKLSDK.sm4Encrypt(request.toBody(), LKLConfigDemo.appId));
//            String sm4Decrypt = LKLSDK.sm4Decrypt(response4, LKLConfigDemo.appId);
//             解密
//            log.info("sm4Decrypt: \r\n{}", sm4Decrypt);
//            LKLCommonResponse LKLCommonResponse = JsonUtil.fromJson(sm4Decrypt, LKLCommonResponse.class);
    }

    /**
     * 测试响应
     */
    @Test
    public void testResponse() throws Exception {
        String response = "{\"code\":\"000000\",\"msg\":\"SUCCESS\",\"resp_data\":{\"merchant_no\":\"100143527\",\"status\":\"WAIT_AUDI\",\"state\":\"1\"}}";
        LKLCommonResponse LKLCommonResponse = JsonUtil.fromJson(response, LKLCommonResponse.class);
        log.info("commonResponseDTO: \r\n{}", LKLCommonResponse);
        String s = LKLCommonResponse.getRespData().toString();
        log.info("s: \r\n{}", s);
    }

}

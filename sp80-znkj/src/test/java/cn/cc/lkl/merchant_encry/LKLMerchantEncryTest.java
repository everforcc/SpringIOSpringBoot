package cn.cc.lkl.merchant_encry;

import cn.cc.config.JsonUtil;
import cn.cc.lkl.dto.CommonResponseDTO;
import cn.cc.lkl.dto.merchantencry.V3TkbsMerchantEncryRequest;
import cn.cc.lkl.sdk.config.LKLConfigDemo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lkl.laop.sdk.LKLSDK;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 商户进件
 */
@Slf4j
public class LKLMerchantEncryTest {

    /**
     * 测试商户进件请求
     */
    @Test
    public void merchantEncry() {

        String serverUrl = LKLConfigDemo.serverUrl;
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
//            log.info("request: \r\n{}", request);
//            // request 转json
//            String json2 = JSON.toJSONString(request);
//            log.info("json2: \r\n{}", json2);
//            // 对象转json的时候要恢复下划线，不要驼峰命名法
//            String json3 = JsonUtil.toJson(request);
//            log.info("json3: \r\n{}", json3);

            String response4 = LKLSDK.httpPost(serverUrl + "/api/v3/tkbs/merchant_encry", LKLSDK.sm4Encrypt(request.toBody(), LKLConfigDemo.appId));

            log.info("response4: \r\n{}", response4);
            String sm4Decrypt = LKLSDK.sm4Decrypt(response4, LKLConfigDemo.appId);
            log.info("sm4Decrypt: \r\n{}", sm4Decrypt);

            CommonResponseDTO commonResponseDTO = JsonUtil.fromJson(sm4Decrypt, CommonResponseDTO.class);
            log.info("commonResponseDTO: \r\n{}", commonResponseDTO);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 测试响应
     */
    @Test
    public void testResponse() throws Exception {
        String response = "{\"code\":\"000000\",\"msg\":\"SUCCESS\",\"resp_data\":{\"merchant_no\":\"100143527\",\"status\":\"WAIT_AUDI\",\"state\":\"1\"}}";
        CommonResponseDTO commonResponseDTO = JsonUtil.fromJson(response, CommonResponseDTO.class);
        log.info("commonResponseDTO: \r\n{}", commonResponseDTO);
        String s = commonResponseDTO.getRespData().toString();
        log.info("s: \r\n{}", s);
    }


}

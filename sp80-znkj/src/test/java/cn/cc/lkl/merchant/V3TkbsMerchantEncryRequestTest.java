package cn.cc.lkl.merchant;

import cn.cc.config.JsonUtil;
import cn.cc.lkl.LKLBaseTest;
import cn.cc.lkl.dto.LKLCommonResponse;
import cn.cc.lkl.dto.merchant.V3TkbsMerchantEncryRequest;
import cn.cc.lkl.dto.merchant.V3TkbsMerchantEncryResponse;
import cn.cc.lkl.util.DateUtils;
import cn.cc.lkl.util.LKLPost;
import cn.cc.lkl.util.LoadFileUtil;
import cn.cc.lkl.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 商户进件
 */
@Slf4j
public class V3TkbsMerchantEncryRequestTest extends LKLBaseTest {

    String filePath = "C:/cc/code/gitee/SpringIOSpringBoot/sp80-znkj/docs/lkl/接口文档/test/商户进件入参-gkl.json";

    /**
     * 测试商户进件请求
     */
    @Test
    public void merchantEncry() {
//        V3LabsTransMicropayRequest
        // 退款
//        V3LabsRelationRefundRequest
        String json = null;
        try {
            json = LoadFileUtil.loadJsonFromResource("进件/商户进件入参-gkl.json");
            // json转V3TkbsMerchantEncryRequest
            JSONObject jsonObject = JSON.parseObject(json);
            jsonObject.put("timestamp", DateUtils.getTimeStamp());
            jsonObject.put("req_id", StringUtils.getSerialNumber());
            V3TkbsMerchantEncryRequest request = JsonUtil.fromJson(jsonObject.getString("req_data"), V3TkbsMerchantEncryRequest.class);
//            request.setContractNo("QY20250916406862849");
            log.info("商户进件参数: \r\n{}", JsonUtil.toJson(request));
            LKLCommonResponse response4 = LKLPost.httpPost(request, true, true);

            log.info("商户进件返回: \r\n{}", JsonUtil.toJson(response4));
            log.info("commonResponseDTO: \r\n{}", response4);
            log.info("isSuccess: \r\n{}", response4.resultSuccess());
            log.info("getRespData: \r\n{}", response4.getRespData());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void res() {
        String json = LoadFileUtil.loadJsonFromResource("进件/商户进件出参-gkl.json");
        try {
            LKLCommonResponse response = JsonUtil.fromJson(json, LKLCommonResponse.class);
            log.info("response: \r\n{}", JsonUtil.toJson(response));
            log.info("response.getRespData().toString(): \r\n{}", response.getRespData().toString());
            V3TkbsMerchantEncryResponse v3TkbsMerchantEncryResponse = JsonUtil.fromJson(response.getRespData(), V3TkbsMerchantEncryResponse.class);
            log.info("response: \r\n{}", JsonUtil.toJson(v3TkbsMerchantEncryResponse));
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
        String response = LoadFileUtil.loadJsonFromResource("进件/商户进件出参-gkl.json");
        log.info("response: \r\n{}", response);
        LKLCommonResponse LKLCommonResponse = JsonUtil.fromJson(response, LKLCommonResponse.class);
        log.info("commonResponseDTO: \r\n{}", LKLCommonResponse);
        String s = LKLCommonResponse.getRespData().toString();
        log.info("s: \r\n{}", s);
    }

    /**
     * 测试驼峰命名法，转换为对象
     */
    @Test
    public void jsonToDto() {
        String json = null;
        try {
            json = new String(Files.readAllBytes(Paths.get(filePath)));
            // json转V3TkbsMerchantEncryRequest
            JSONObject jsonObject = JSON.parseObject(json);

            V3TkbsMerchantEncryRequest request = JsonUtil.fromJson(jsonObject.getString("req_data"), V3TkbsMerchantEncryRequest.class);
            log.info("request: \r\n{}", request);
            // request 转json
            String json2 = JSON.toJSONString(request);
            log.info("json2: \r\n{}", json2);
            // 对象转json的时候要恢复下划线，不要驼峰命名法
            String json3 = JsonUtil.toJson(request);
            log.info("json3: \r\n{}", json3);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

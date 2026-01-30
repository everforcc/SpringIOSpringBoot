package cn.cc.lkl.ordertranspre;

import cn.cc.config.JsonUtil;
import cn.cc.lkl.LKLBaseProdTest;
import cn.cc.lkl.dto.LKLCommonResponse;
import cn.cc.lkl.dto.scanpreorder.V3LabsTransPreorderResponse;
import cn.cc.lkl.util.LoadFileUtil;
import cn.cc.lkl.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lkl.laop.sdk.LKLSDK;
import com.lkl.laop.sdk.exception.SDKException;
import com.lkl.laop.sdk.request.V3LabsTransPreorderRequest;
import com.lkl.laop.sdk.request.model.V3LabsTradeLocationInfo;
import com.lkl.laop.sdk.request.model.V3LabsTradePreorderAlipayBus;
import com.lkl.laop.sdk.request.model.V3LabsTradePreorderWechatBus;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * https://o.lakala.com/#/home/document/detail?id=110
 * 聚合主扫
 */
@Slf4j
public class V3LabsTransPreorderRequestTest extends LKLBaseProdTest {


    /**
     * 支付宝
     *
     * @throws Exception
     */
    @Test
    public void testAli() throws Exception {
        V3LabsTransPreorderRequest request = new V3LabsTransPreorderRequest();
        request.setMerchantNo("8224910737200MK");
//        request.setMerchantNo(LKLConfigProd.merchantNo);
//        request.setTermNo(LKLConfigProd.termNo);
        request.setTermNo("N8905587");

        request.setOutTradeNo(StringUtils.getSerialNumber());
//        request.setAccountType("WECHAT");
        request.setAccountType("ALIPAY");
        request.setTransType("41");
//        request.setTransType("61");
        request.setTotalAmount("2");
        V3LabsTradeLocationInfo locationInfo = new V3LabsTradeLocationInfo();
        // 获取当前设备ip
        locationInfo.setRequestIp("192.168.1.188");
        // 非必填
//        locationInfo.setLocation("");
//        locationInfo.setBaseStation("");
        request.setLocationInfo(locationInfo);
        V3LabsTradePreorderAlipayBus accBusiFields = new V3LabsTradePreorderAlipayBus();
//        accBusiFields.setUserId("232966729");
        accBusiFields.setUserId("232966729");
        request.setAccBusiFields(accBusiFields);

        log.info("获取支付信息: {}", request.toBody());
//        log.info("获取支付信息请求: \r\n{}", JsonUtil.toJson(request));
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
            // respData 转 JS  merInnerNo: 4002021012659676355ONObject
            JSONObject jsonObject = JSONObject.parseObject(respData.toString());
//            JSONObject jsonObject = JSONObject.parseObject();
            log.info("获取支付信息成功: {}", jsonObject);
        } catch (SDKException e) {
            e.printStackTrace();
        }
    }

    /**
     * 小程序APPID：wxd1e456dc8095136a
     * 小程序秘钥：0cb3088bb6931328f769c6ed2f69d0ab
     * openId:
     * oQSRg7CFo36snwNJBzBVSTqbipwc
     */
    @Test
    public void testWx() throws Exception {
        V3LabsTransPreorderRequest request = new V3LabsTransPreorderRequest();
        request.setMerchantNo("8224910737200MK");
//        request.setMerchantNo(LKLConfigProd.merchantNo);
//        request.setTermNo(LKLConfigProd.termNo);
        request.setTermNo("N8905587");

        request.setOutTradeNo(StringUtils.getSerialNumber());
//        request.setAccountType("WECHAT");
        request.setAccountType("WECHAT");
        request.setTransType("71");
//        request.setTransType("61");
        request.setTotalAmount("2");
        V3LabsTradeLocationInfo locationInfo = new V3LabsTradeLocationInfo();
        // 获取当前设备ip
        locationInfo.setRequestIp("192.168.1.188");
        // 非必填
//        locationInfo.setLocation("");
//        locationInfo.setBaseStation("");
        request.setLocationInfo(locationInfo);
        V3LabsTradePreorderWechatBus accBusiFields = new V3LabsTradePreorderWechatBus();

//        accBusiFields.setTimeoutExpress("15");
        accBusiFields.setSubAppid("wxd1e456dc8095136a");
        accBusiFields.setUserId("oQSRg7BR4AKlY0JI-FmEkcJs9nwo");
        request.setAccBusiFields(accBusiFields);

        log.info("获取微信支付信息: {}", request.toBody());
//        log.info("获取支付信息请求: \r\n{}", JsonUtil.toJson(request));
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
            // respData 转 JS  merInnerNo: 4002021012659676355ONObject
            JSONObject jsonObject = JSONObject.parseObject(respData.toString());
//            JSONObject jsonObject = JSONObject.parseObject();
            log.info("获取支付信息成功: {}", jsonObject);
        } catch (SDKException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testWxJsapi() throws Exception {
        String json = LoadFileUtil.loadJsonFromResource("小程序/response.json");
        System.out.println(json);
//        V3LabsTransPreorderResponse v3LabsTransPreorderResponse = JsonUtil.fromJson(response.getRespData(), V3LabsTransPreorderResponse.class);
        String respData = JSONObject.parseObject(json).getString("resp_data");
        V3LabsTransPreorderResponse v3LabsTransPreorderResponse = JsonUtil.fromJson(respData, V3LabsTransPreorderResponse.class);
        System.out.println("v3LabsTransPreorderResponse:" + v3LabsTransPreorderResponse);
        Object accRespFields = v3LabsTransPreorderResponse.getAccRespFields();
//        V3LabsTransPreorderResponse.WechatJsapiRespFields wechatJsapiRespFields = JSONObject.parseObject(accRespFields, V3LabsTransPreorderResponse.WechatJsapiRespFields.class);
        // 将 accRespFields 转换为 V3LabsTransPreorderResponse.WechatJsapiRespFields
        System.out.println("accRespFields:" + JSONObject.toJSONString(accRespFields));
        V3LabsTransPreorderResponse.WechatJsapiRespFields wechatJsapiRespFields = JSONObject.parseObject(JSONObject.toJSONString(accRespFields), V3LabsTransPreorderResponse.WechatJsapiRespFields.class);
        String wechatJsapiRespFieldsStr = JSONObject.toJSONString(wechatJsapiRespFields);
        System.out.println("wechatJsapiRespFields:" + JSONObject.toJSONString(wechatJsapiRespFields));
        V3LabsTransPreorderResponse.WechatJsapiRespFields wechatJsapiRespFields2 = JSONObject.parseObject(wechatJsapiRespFieldsStr, V3LabsTransPreorderResponse.WechatJsapiRespFields.class);
        System.out.println("wechatJsapiRespFields2:" + JSONObject.toJSONString(wechatJsapiRespFields2));
    }

    @Test
    public void test111() throws Exception {
        String aliQrcode = "{\"appId\":\"wxd1e456dc8095136a\",\"nonceStr\":\"b5de040e307942559801b0242e8bd0c2\",\"paySign\":\"TXu5pm65ekKjAtYVQ6GOQChtkCQRFqPK4UwFcw+BIBDFbOa0hOY7n61Bjdl+e+TT/DDEZizegOwothzCobfHmnTCxS4qM9FfHbur6ZEhBjk4KgUJFYRD6B2Gw1jk8HcKUdCmkt6lrzpdfK73DHtXEV0XJBnqN/dvOj/SvL0TAIdnFi5Dky2mFjXNpHLyKYd7Ja8G6zUbWF28K3twe/oUGvz+Z/ds+6ewn9OkJN6KcmFe9Rp4qTtosa5h5bb+i1TIAFt+9A0FNxnByNni7Lk6xGzs9LuIKyYUcLsupslNysDCxrddRXaQ0H3RqUb3Y207I/CnU9fNMYF8f0CJVs7FlA==\",\"prepayId\":\"wx2120333650254290bbb942157a57c10000\",\"signType\":\"RSA\",\"subMchId\":\"822324396\",\"timeStamp\":\"1768998816\"}";
        V3LabsTransPreorderResponse.WechatJsapiRespFields wechatJsapiRespFields = JSONObject.parseObject(aliQrcode, V3LabsTransPreorderResponse.WechatJsapiRespFields.class);
        System.out.println("wechatJsapiRespFields:" + JSONObject.toJSONString(wechatJsapiRespFields));

    }

}

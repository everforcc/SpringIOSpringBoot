package cn.cc.lkl.sdk;

import com.alibaba.fastjson.JSONObject;
import com.lkl.laop.sdk.LKLSDK;
import com.lkl.laop.sdk.exception.SDKException;
import com.lkl.laop.sdk.request.V2MmsOpenApiUploadFileRequest;
import com.lkl.laop.sdk.request.V3LabsTransMicropayRequest;
import com.lkl.laop.sdk.request.model.V3LabsTradeLocationInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class LKLReqSDKDemo {

    public static void main(String[] args) {
        try {
            LKLConfigDemo.demoConfig4();
//            demoReq2();
            demoReqV2Upload();
        } catch (SDKException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void demoReqV2Upload() throws SDKException, IOException {

        V2MmsOpenApiUploadFileRequest commRequest = new V2MmsOpenApiUploadFileRequest();
        commRequest.setOrderNo((new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date()));
        commRequest.setVersion("1.0");
        commRequest.setOrgCode("1");
        commRequest.setAttType("SHOP_OUTSIDE_IMG");
        String fileBase64 = Base64.encodeBase64String(Files.readAllBytes(Paths.get("C:\\Users\\znkj\\Desktop\\temp\\百度.png")));
        commRequest.setAttContext(fileBase64);
        commRequest.setAttExtName("png");
        String response = LKLSDK.httpPost(commRequest);
        System.out.println("response:" + response);
    }

    /**
     * demo里面给的示例请求
     * 给的json，修改为SDK测试
     *
     * @throws SDKException
     */
    public static void demoReq2() throws SDKException {

        V3LabsTransMicropayRequest commRequest = new V3LabsTransMicropayRequest();
        String body = "{\n" +
                "    \"req_time\": \"20210907150256\",\n" +
                "    \"version\": \"3.0\",\n" +
                "    \"out_org_code\": \"OP00000003\",\n" +
                "    \"req_data\": {\n" +
                "        \"merchant_no\": \"822290070111135\",\n" +
                "        \"term_no\": \"29034705\",\n" +
                "        \"out_trade_no\": \"FD660E1FAA3A4470933CDEDAE1EC1D8E\",\n" +
                "        \"auth_code\": \"135178236713755038\",\n" +
                "        \"total_amount\": \"123\",\n" +
                "        \"location_info\": {\n" +
                "            \"request_ip\": \"10.176.1.192\",\n" +
                "            \"location\": \"+37.123456789,-121.123456789\"\n" +
                "        },\n" +
                "        \"out_order_no\": \"08F4542EEC6A4497BC419161747A92FA\"\n" +
                "    }\n" +
                "}";
//        commRequest = JSONObject.parseObject(body, V3LabsTransMicropayRequest.class);
        commRequest.setMerchantNo("00dfba8194c41b84cf");
        commRequest.setTermNo("29034705");
        commRequest.setOutTradeNo("FD660E1FAA3A4470933CDEDAE1EC1D8E");
        commRequest.setAuthCode("135178236713755038");
        commRequest.setTotalAmount("123");
//        JSONObject locationInfo = new JSONObject();
//        locationInfo.put("request_ip", "10.176.1.192");
//        locationInfo.put("location", "+37.123456789,-121.123456789");
        V3LabsTradeLocationInfo locationInfo = new V3LabsTradeLocationInfo();
        locationInfo.setRequestIp("10.176.1.192");
        locationInfo.setLocation("+37.123456789,-121.123456789");
        commRequest.setLocationInfo(locationInfo);
//        commRequest.out
        commRequest.setPayOrderNo("08F4542EEC6A4497BC419161747A92FA");
        System.out.println("commRequest.toBody():" + commRequest.toBody());
        try {
            String response = LKLSDK.httpPost(commRequest);
            System.out.println("response:" + response);
        } catch (SDKException e) {
            e.printStackTrace();
            String err = e.getMessage();
            log.error(err);
//            e.
            String errInfo = err;
            if (err.contains("code")) {
                // 从{截取到最后一位
                errInfo = err.substring(err.indexOf("{"), err.length() - 2);
                System.out.println("errInfo:" + errInfo);
                errInfo = err.substring(err.indexOf("{"), err.length() - 1);
                System.out.println("errInfo:" + errInfo);
                errInfo = err.substring(err.indexOf("{"), err.length());
                System.out.println("errInfo:" + errInfo);
                log.info("处理后的错误信息为: " + errInfo);
                JSONObject jsonObject = JSONObject.parseObject(errInfo);
                System.out.println("jsonObject:" + jsonObject);
                System.out.println("jsonObject.getString(\"code\"): " + jsonObject.getString("code"));
                errInfo = jsonObject.getString("msg");
                System.out.println("jsonObject.getString(\"msg\"): " + jsonObject.getString("msg"));
            }
            throw new RuntimeException("处理后的错误信息为: " + errInfo);
        } catch (Exception e) {
            System.out.println("e.getMessage(): " + e.getMessage());
            e.getCause().printStackTrace();
            System.out.println(e.getCause().getMessage());
        }
    }

}

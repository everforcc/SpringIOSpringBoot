package cn.cc.huifu.regist;


import cn.cc.huifu.config.PayConfig;
import com.huifu.bspay.sdk.opps.core.BasePay;
import com.huifu.bspay.sdk.opps.core.exception.BasePayException;
import com.huifu.bspay.sdk.opps.core.net.BasePayRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ZnHFRegist {

    public static void main(String[] args) {

        refundFlow("20250519");
    }

    public static Map<String, Object> refundFlow(String req_date) {
        try {
            BasePay.initWithMerConfig(PayConfig.getMerchantConfig());
        } catch (Exception e) {
            System.err.println("---");
            e.printStackTrace();
            System.err.println("---");
            System.err.println("初始化报错: " + e.getMessage());
        }
        Map<String, Object> paramsInfo = new HashMap<>();



//        Random rand = new Random();
        long randomNum = new Random().nextLong() % 1000000000000000000L + 1000000000000000000L;

        paramsInfo.put("req_seq_id", randomNum + "");
        paramsInfo.put("req_date", req_date);
        paramsInfo.put("name","蒋守业");
        paramsInfo.put("cert_type","00");
        paramsInfo.put("cert_no","410224198708033639");
        paramsInfo.put("cert_validity_type","0");
        paramsInfo.put("cert_begin_date","20160104");
        paramsInfo.put("cert_end_date","20360104");
        paramsInfo.put("cert_nationality","CHN");
        paramsInfo.put("mobile_no","18706828536");
        paramsInfo.put("email","623104270@qq.com");
        paramsInfo.put("login_name","jiangshouye");
        paramsInfo.put("sms_send_flag","Y");
//        paramsInfo.put("name","郭凯龙");
//        paramsInfo.put("cert_type","00");
//        paramsInfo.put("cert_no","41018219960126531X");
//        paramsInfo.put("cert_validity_type","0");
//        paramsInfo.put("cert_begin_date","20170619");
//        paramsInfo.put("cert_end_date","20270619");
//        paramsInfo.put("cert_nationality","CHN");
//        paramsInfo.put("mobile_no","15738573601");
//        paramsInfo.put("email","718497737@qq.com");
//        paramsInfo.put("login_name","everforcc");
//        paramsInfo.put("sms_send_flag","Y");


        // 3. 发起API调用
        Map<String, Object> response = null;
        try {
            response = BasePayRequest.requestBasePay("v2/user/basicdata/indv", paramsInfo, null, false);
        } catch (BasePayException e) {
            System.err.println("---");
            e.printStackTrace();
            System.err.println("---");
            System.err.println("返回报错: " + e.getMessage());
        }
        System.out.println(response);
        return response;
    }

}
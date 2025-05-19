package cn.cc.huifu.status;


import cn.cc.huifu.config.PayConfig;
import com.huifu.bspay.sdk.opps.core.BasePay;
import com.huifu.bspay.sdk.opps.core.exception.BasePayException;
import com.huifu.bspay.sdk.opps.core.net.BasePayRequest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 商户业务开通
 * https://api.huifu.com/v2/merchant/basicdata/status/query
 */
public class ZnHFMerchantIndvStatus {

    public static void main(String[] args) {


        Map<String,Object> map = new HashMap<String,Object>();
        Map<String,Object> agreement_info = new HashMap<String,Object>();
        Map<String,Object> sign_user_info = new HashMap<String,Object>();
        map.put("req_seq_id",new Random().nextLong() % 1000000000000000000L + 1000000000000000000L + "");
        map.put("req_date",new SimpleDateFormat("yyyyMMdd").format(new Date()));
        map.put("huifu_id","6666000168410819");
        map.put("upper_huifu_id","6666000151772004");
        agreement_info.put("agreement_type","0");
//        agreement_info.put("message_send_type","");
        map.put("agreement_info", agreement_info);
        sign_user_info.put("type","LEGAL");
        sign_user_info.put("name","冯双双");
        sign_user_info.put("cert_no","411081198610079089");
        sign_user_info.put("mobile_no","15936318171");
        map.put("sign_user_info", sign_user_info);
        refundFlow(map);
    }

    public static Map<String, Object> refundFlow(Map<String,Object> map) {
        try {
            BasePay.initWithMerConfig(PayConfig.getMerchantConfig());
        } catch (Exception e) {
            System.err.println("---");
            e.printStackTrace();
            System.err.println("---");
            System.err.println("初始化报错: " + e.getMessage());
        }
//        Map<String, Object> paramsInfo = new HashMap<>();




        // 3. 发起API调用
        Map<String, Object> response = null;
        try {
            response = BasePayRequest.requestBasePay("v2/merchant/busi/open", map, null, false);
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


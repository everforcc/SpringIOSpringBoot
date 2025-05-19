package cn.cc.huifu.open;


import cn.cc.huifu.config.PayConfig;
import com.alibaba.fastjson.JSONArray;
import com.huifu.bspay.sdk.opps.core.BasePay;
import com.huifu.bspay.sdk.opps.core.exception.BasePayException;
import com.huifu.bspay.sdk.opps.core.net.BasePayRequest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 *
 * @see <a href="https://paas.huifu.com/open/doc/api/#/shgl/shywkt/api_shjj_shywktxg_kyc">商户业务开通修改</a>
 *
 * https://api.huifu.com/v2/merchant/busi/modify
 */
public class ZnHFMerchantIndvModify {

    public static void main(String[] args) {
        Map<String,Object> map = new HashMap<String,Object>();

        map.put("req_seq_id",new Random().nextLong() % 1000000000000000000L + 1000000000000000000L + "");
        map.put("req_date",new SimpleDateFormat("yyyyMMdd").format(new Date()));
        map.put("huifu_id","6666000168410819");
//        map.put("upper_huifu_id","6666000151772004");


        // 2.5‰ 0.25%
        // {"resp_desc":"线下支付宝反扫支付费率不能低于渠道商/上级商户费率底价MAX(0.01,AMT*0.0025)","req_seq_id":"1067060048050371940","product_id":"PAYUN","req_date":"20250519","resp_code":"99999999","huifu_id":"6666000168410819"}
        // {"resp_desc":"线下支付宝反扫支付费率不能低于渠道商/上级商户费率底价MAX(0.01,AMT*0.0065)","req_seq_id":"1067060048050371940","product_id":"PAYUN","req_date":"20250519","resp_code":"99999999","huifu_id":"6666000168410819"}
        JSONArray ali_conf_list = new JSONArray();
        Map<String,Object> ali_conf1 = new HashMap<String,Object>();
        ali_conf1.put("pay_scene","1");//
        ali_conf1.put("fee_rate","0.25");
        ali_conf1.put("switch_state","1");
        ali_conf1.put("fee_min_amt","0.01");
        Map<String,Object> ali_conf2 = new HashMap<String,Object>();
        ali_conf2.put("pay_scene","2");
        //
        ali_conf2.put("fee_rate","0.65");
        ali_conf2.put("switch_state","1");
        ali_conf2.put("fee_min_amt","0.01");
        ali_conf_list.add(ali_conf1);
        ali_conf_list.add(ali_conf2);
        map.put("ali_conf_list", ali_conf_list.toString());
//        JSONArray ali_conf_list = new JSONArray();

        Map<String,Object> agreement_info = new HashMap<String,Object>();
        agreement_info.put("agreement_type","0");
        map.put("agreement_info", agreement_info);

        Map<String,Object> sign_user_info = new HashMap<String,Object>();
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
            response = BasePayRequest.requestBasePay("v2/merchant/busi/modify", map, null, false);
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


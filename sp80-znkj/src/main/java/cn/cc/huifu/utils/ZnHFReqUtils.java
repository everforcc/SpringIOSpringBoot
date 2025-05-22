package cn.cc.huifu.utils;


import cn.cc.huifu.config.PayConfig;
import cn.cc.huifu.constants.ZnHFReqParamsContants;
import com.huifu.bspay.sdk.opps.core.BasePay;
import com.huifu.bspay.sdk.opps.core.exception.BasePayException;
import com.huifu.bspay.sdk.opps.core.net.BasePayRequest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ZnHFReqUtils {

    public static Map<String, Object> flow(Map<String, Object> map,String url) {
        try {
            BasePay.initWithMerConfig(PayConfig.getMerchantConfig());
        } catch (Exception e) {
            System.err.println("---");
            e.printStackTrace();
            System.err.println("---");
            System.err.println("初始化报错: " + e.getMessage());
        }

        // 公共字段
        map.put("req_seq_id", ZnHFReqParamsContants.REQ_SEQ_ID);
        map.put("req_date", ZnHFReqParamsContants.REQ_DATE);

        // 3. 发起API调用
        Map<String, Object> response = null;
        try {
            response = BasePayRequest.requestBasePay(url, map, null, false);
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
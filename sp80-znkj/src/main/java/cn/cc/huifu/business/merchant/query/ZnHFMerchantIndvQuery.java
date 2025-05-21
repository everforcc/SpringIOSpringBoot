package cn.cc.huifu.business.merchant.query;


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
 * @see <a href="https://paas.huifu.com/open/doc/api/#/shgl/shjj/api_shjj_shxxxxcx_kyc">商户详细信息查询</a>
 *
 * https://api.huifu.com/v2/merchant/basicdata/query
 */
public class ZnHFMerchantIndvQuery {

    public static void main(String[] args) {
        Map<String,Object> map = new HashMap<String,Object>();

        map.put("req_seq_id",new Random().nextLong() % 1000000000000000000L + 1000000000000000000L + "");
        map.put("req_date",new SimpleDateFormat("yyyyMMdd").format(new Date()));
        map.put("huifu_id","6666000168410819");
//        map.put("upper_huifu_id","6666000151772004");
        refundFlow(map);
        // 结算配置
        // qry_settle_config_list
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
            response = BasePayRequest.requestBasePay("v2/merchant/basicdata/query", map, null, false);
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


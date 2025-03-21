package cn.cc.huifu.refund.refund;


import cn.cc.huifu.config.PayConfig;
import com.huifu.bspay.sdk.opps.core.BasePay;
import com.huifu.bspay.sdk.opps.core.exception.BasePayException;
import com.huifu.bspay.sdk.opps.core.net.BasePayRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ZnPayOrderRefund {

    public static void main(String[] args) {

        refundFlow("002900TOP4A250319200507P827ac1369bb00000","20250320","3.00", "6666000154267351");
    }

    public static Map<String, Object> refundFlow(String org_hf_seq_id, String req_date, String ord_amt, String huifu_id) {
//        PayConfig
        try {
            BasePay.initWithMerConfig(PayConfig.getMerchantConfig());
        } catch (Exception e) {
            System.err.println("---");
            e.printStackTrace();
            System.err.println("---");
            System.err.println("初始化报错: " + e.getMessage());
        }
        Map<String, Object> paramsInfo = new HashMap<>();
//        String org_hf_seq_id = "002900TOP1B250120112747P883ac139d1e00000";
//        String req_date = "20250120";
//        String ord_amt = "10.00";
        // 请求日期
        paramsInfo.put("req_date", req_date);
        // 请求流水号
        // zn_pay_order_record.other_data.req_seq_id

        Random rand = new Random();
        long randomNum = rand.nextLong() % 1000000000000000000L + 1000000000000000000L;

        paramsInfo.put("req_seq_id", randomNum + "");
        // 商户号
        // 6666000154267351
        // 6666000154267351
        // 6666000151824676
        paramsInfo.put("huifu_id", huifu_id);
        // 申请退款金额
        paramsInfo.put("ord_amt", ord_amt);
        // 原交易请求日期
        paramsInfo.put("org_req_date", req_date);
        // 原交易全局流水号
        // zn_pay_order_record.other_data.hf_seq_id
        paramsInfo.put("org_hf_seq_id", org_hf_seq_id);
        // 3. 发起API调用
        Map<String, Object> response = null;
        try {
            response = BasePayRequest.requestBasePay("v2/trade/payment/scanpay/refund", paramsInfo, null, false);
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
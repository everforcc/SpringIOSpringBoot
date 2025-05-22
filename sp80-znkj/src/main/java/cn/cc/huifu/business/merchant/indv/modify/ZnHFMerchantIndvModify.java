package cn.cc.huifu.business.merchant.indv.modify;


import cn.cc.huifu.business.merchant.indv.dto.HuiFuMerchantIndvDto;
import cn.cc.huifu.config.PayConfig;
import com.huifu.bspay.sdk.opps.core.BasePay;
import com.huifu.bspay.sdk.opps.core.exception.BasePayException;
import com.huifu.bspay.sdk.opps.core.net.BasePayRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * @see <a href="https://paas.huifu.com/open/doc/api/#/shgl/shjj/api_shjj_shjbxxxg_kyc">商户基本信息修改</a>
 * <p>
 * https://api.huifu.com/v2/merchant/basicdata/modify
 */
public class ZnHFMerchantIndvModify {

    public static void main(String[] args) {

        HuiFuMerchantIndvDto huiFuMerchantIndvDto = new HuiFuMerchantIndvDto();

        huiFuMerchantIndvDto.setUpperHuifuId("6666000151772004");
        huiFuMerchantIndvDto.setHuifuId("6666000168410819");

        // 结算信息

//线下经营-门头照 F22
        huiFuMerchantIndvDto.setStoreHeaderPic("68060757-03ef-3f60-9bdf-9d391811456b");
//线下经营-内景照 F24
        huiFuMerchantIndvDto.setStoreIndoorPic("8923b531-47e7-3d93-97b5-0cf60adc737e");
//线下经营-收银台 F105
        huiFuMerchantIndvDto.setStoreCashierDeskPic("30583964-ce18-34a6-aec2-24ac312287ba");

        refundFlow(huiFuMerchantIndvDto.toMap());
    }

    public static Map<String, Object> refundFlow(Map<String, Object> map) {
        try {
            BasePay.initWithMerConfig(PayConfig.getMerchantConfig());
        } catch (Exception e) {
            System.err.println("---");
            e.printStackTrace();
            System.err.println("---");
            System.err.println("初始化报错: " + e.getMessage());
        }

        // todo
        Map<String, Object> agreement_info = new HashMap<String, Object>();
        agreement_info.put("agreement_type", "0");
//        agreement_info.put("message_send_type","");
        map.put("agreement_info", agreement_info);

        //todo
        Map<String, Object> sign_user_info = new HashMap<String, Object>();
        sign_user_info.put("type", "LEGAL");
        sign_user_info.put("name", "冯双双");
        sign_user_info.put("cert_no", "411081198610079089");
        sign_user_info.put("mobile_no", "15936318171");
        map.put("sign_user_info", sign_user_info);

        Map<String, String> card_info = new HashMap<String, String>();
        card_info.put("card_type", "1");
        card_info.put("card_name", "冯双双");
        card_info.put("card_no", "6230522050043262873");
        card_info.put("area_id", "411000");
        card_info.put("cert_type", "00");
        card_info.put("cert_no", "411081198610079089");
        card_info.put("cert_validity_type", "0");
        card_info.put("cert_begin_date", "20151214");
        card_info.put("cert_end_date", "20351214");
        card_info.put("mp", "15936318171");
        map.put("card_info",card_info);

        // 结算信息
        Map<String, Object> settle_config = new HashMap<String, Object>();
        settle_config.put("settle_status","1");
        settle_config.put("settle_cycle","D1");
        settle_config.put("min_amt","0.00");
        settle_config.put("remained_amt","0.00");
        settle_config.put("settle_abstract","中南店掌柜业务");
        settle_config.put("settle_pattern","P0");
        // 和结算时间有关
        settle_config.put("settle_batch_no","100");

        // todo 节假日结算手续费率
        settle_config.put("fixed_ratio","0.25");
        settle_config.put("constant_amt","0.00");


        map.put("settle_config", settle_config);


        // 3. 发起API调用
        Map<String, Object> response = null;
        try {
            response = BasePayRequest.requestBasePay("v2/merchant/basicdata/modify", map, null, false);
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
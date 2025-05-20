package cn.cc.huifu.business.merchant.indv;


import cn.cc.huifu.business.merchant.indv.dto.HuiFuMerchantIndv;
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
 * @see <a href="https://paas.huifu.com/open/doc/api/#/shgl/shjj/api_shjj_grshjbxxrz_kyc">个人商户进件</a>
 * https://api.huifu.com/v2/merchant/basicdata/indv
 */
public class ZnHFMerchantIndvRegist {

    public static void main(String[] args) {

        HuiFuMerchantIndv huiFuMerchantIndv = new HuiFuMerchantIndv();
        huiFuMerchantIndv.setReqSeqId(new Random().nextLong() % 1000000000000000000L + 1000000000000000000L + "");
        huiFuMerchantIndv.setReqDate(new SimpleDateFormat("yyyyMMdd").format(new Date()));
        huiFuMerchantIndv.setUpperHuifuId("6666000151772004");
        huiFuMerchantIndv.setRegName("冯双双");
        huiFuMerchantIndv.setShortName("双橙便利店");
        huiFuMerchantIndv.setMcc("5311");
        huiFuMerchantIndv.setSceneType("OFFLINE");
        huiFuMerchantIndv.setProvId("410000");
        huiFuMerchantIndv.setAreaId("411000");
        huiFuMerchantIndv.setDistrictId("411081");
        huiFuMerchantIndv.setDetailAddr("禹州市颍川街道华夏大道人和嘉苑");
        huiFuMerchantIndv.setLegalCertNo("411081198610079089");
        huiFuMerchantIndv.setLegalCertBeginDate("20151214");
        huiFuMerchantIndv.setLegalCertValidityType("0");
        huiFuMerchantIndv.setLegalCertEndDate("20351214");
        huiFuMerchantIndv.setLegalAddr("河南省禹州市颍川办东关村崔庄9组");
        // 国徽面
        huiFuMerchantIndv.setLegalCertBackPic("06dbba25-f9dc-3a2b-b3e1-98e730525566");
        // 正面
        huiFuMerchantIndv.setLegalCertFrontPic("79b7fd5a-a009-3a75-bf06-dc1544d13d0e");
//        huiFuMerchantIndv.setOccupation();
        huiFuMerchantIndv.setContactMobileNo("15936318171");
        huiFuMerchantIndv.setContactEmail("2232806676@qq.com");
        Map<String, String> map = new HashMap<String, String>();
        map.put("card_name", "冯双双");
        map.put("card_no", "6230522050043262873");
        map.put("area_id", "411000");
        map.put("cert_type", "00");
        map.put("cert_no", "411081198610079089");
        map.put("cert_validity_type", "0");
        map.put("cert_begin_date", "20151214");
        map.put("cert_end_date", "20351214");
        map.put("mp", "15936318171");
        huiFuMerchantIndv.setCardInfo(map);
        // 结算卡正面
        huiFuMerchantIndv.setSettleCardFrontPic("8d88f358-a753-3670-b0a9-65f7b7a54dce");
//线下经营-门头照 F22
        huiFuMerchantIndv.setStoreHeaderPic("896ee35f-d91d-35af-9ca9-30cd0ac668ba");
//线下经营-内景照 F24
        huiFuMerchantIndv.setStoreIndoorPic("dcf8035e-68ba-3630-8e87-26f626b5e175");
//线下经营-收银台 F105
        huiFuMerchantIndv.setStoreCashierDeskPic("09c09fc2-d01a-30e8-9766-259c15b6649b");

        refundFlow(huiFuMerchantIndv.toMap());
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
//        Map<String, Object> paramsInfo = new HashMap<>();


        // 3. 发起API调用
        Map<String, Object> response = null;
        try {
            response = BasePayRequest.requestBasePay("v2/merchant/basicdata/indv", map, null, false);
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
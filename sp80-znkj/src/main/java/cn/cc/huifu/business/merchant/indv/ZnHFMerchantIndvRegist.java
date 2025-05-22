package cn.cc.huifu.business.merchant.indv;


import cn.cc.huifu.business.merchant.indv.dto.HuiFuMerchantIndvDto;
import cn.cc.huifu.constants.ZnHFURLContants;
import cn.cc.huifu.utils.ZnHFReqUtils;

import java.util.Map;

/**
 * @see <a href="https://paas.huifu.com/open/doc/api/#/shgl/shjj/api_shjj_grshjbxxrz_kyc">个人商户进件</a>
 * https://api.huifu.com/v2/merchant/basicdata/indv
 */
public class ZnHFMerchantIndvRegist {

    public static void main(String[] args) {

        HuiFuMerchantIndvDto huiFuMerchantIndvDto = new HuiFuMerchantIndvDto();

        huiFuMerchantIndvDto.setUpperHuifuId("6666000151772004");
        huiFuMerchantIndvDto.setRegName("冯双双");
        huiFuMerchantIndvDto.setShortName("双橙便利店");
        huiFuMerchantIndvDto.setMcc("5311");
        huiFuMerchantIndvDto.setSceneType("OFFLINE");
        huiFuMerchantIndvDto.setProvId("410000");
        huiFuMerchantIndvDto.setAreaId("411000");
        huiFuMerchantIndvDto.setDistrictId("411081");
        huiFuMerchantIndvDto.setDetailAddr("禹州市颍川街道华夏大道人和嘉苑");
        huiFuMerchantIndvDto.setLegalCertNo("411081198610079089");
        huiFuMerchantIndvDto.setLegalCertBeginDate("20151214");
        huiFuMerchantIndvDto.setLegalCertValidityType("0");
        huiFuMerchantIndvDto.setLegalCertEndDate("20351214");
        huiFuMerchantIndvDto.setLegalAddr("河南省禹州市颍川办东关村崔庄9组");
        // 国徽面
        huiFuMerchantIndvDto.setLegalCertBackPic("4610bef1-2df1-3f0a-ba22-32a4f1e9e50c");
        // 正面
        huiFuMerchantIndvDto.setLegalCertFrontPic("76ec9902-bbb5-37b3-9b64-0ea4e02124e2");
//        huiFuMerchantIndv.setOccupation();
        huiFuMerchantIndvDto.setContactMobileNo("15936318171");
        huiFuMerchantIndvDto.setContactEmail("2232806676@qq.com");
        HuiFuMerchantIndvDto.CardInfo cardInfo = new HuiFuMerchantIndvDto.CardInfo();

        cardInfo.setCardName("冯双双");
        cardInfo.setCardNo("6230522050043262873");
        cardInfo.setAreaId( "411000");
        cardInfo.setCertType("00");
        cardInfo.setCertNo("411081198610079089");
        cardInfo.setCertValidityType("0");
        cardInfo.setCertBeginDate("20151214");
        cardInfo.setCertEndDate("20351214");
        cardInfo.setMp("15936318171");
        huiFuMerchantIndvDto.setCardInfo(cardInfo);
        // 结算卡正面
        huiFuMerchantIndvDto.setSettleCardFrontPic("a5ca3709-22a6-3376-b0e0-2fdf51c23ee2");
//线下经营-门头照 F22
        huiFuMerchantIndvDto.setStoreHeaderPic("c71d64fe-0329-3282-9b84-a9deedf32a31");
//线下经营-内景照 F24
        huiFuMerchantIndvDto.setStoreIndoorPic("be544e2e-6257-3736-928c-6516a567437c");
//线下经营-收银台 F105
        huiFuMerchantIndvDto.setStoreCashierDeskPic("7cf4f5ce-4d00-321b-8bf7-b1d5770f9607");

        flowIndv(huiFuMerchantIndvDto.toMap());
    }

    public static Map<String, Object> flowIndv(Map<String, Object> map){
        return ZnHFReqUtils.flow(map, ZnHFURLContants.MERCHANT_BASICDATA_INDV);
    }

    /*public static Map<String, Object> refundFlow(Map<String, Object> map) {
        try {
            BasePay.initWithMerConfig(PayConfig.getMerchantConfig());
        } catch (Exception e) {
            System.err.println("---");
            e.printStackTrace();
            System.err.println("---");
            System.err.println("初始化报错: " + e.getMessage());
        }

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
    }*/

}
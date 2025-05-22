package cn.cc.huifu.business.merchant.indv.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 *    sign_user_info.put("name","冯双双");
 *         sign_user_info.put("cert_no","411081198610079089");
 *         sign_user_info.put("mobile_no","15936318171");
 */
@Data
public class HuiFuMerchantIndvDto {

//    private String reqSeqId;
//    private String reqDate;
    private String upperHuifuId;
    private String huifuId;
    private String regName;
    private String shortName;
    private String mcc;
    private String sceneType;
    private String provId;
    private String areaId;
    private String districtId;
    private String detailAddr;
    private String legalCertNo;
    private String legalCertBeginDate;
    private String legalCertValidityType;
    private String legalCertEndDate;
    private String legalAddr;
    private String legalCertBackPic;
    private String legalCertFrontPic;
    private String occupation;
    private String contactMobileNo;
    private String contactEmail;
    private CardInfo cardInfo;
    private String settleCardFrontPic;
    private String settleConfig;
    private String cashConfig;
    private String smsSendFlag;
    private String loginName;
    private String merUrl;
    private String merIcp;
    private String storeHeaderPic;
    private String storeIndoorPic;
    private String storeCashierDeskPic;
    private String extMerId;
    private String remarks;
    private String asyncReturnUrl;
    private String headOfficeFlag;
    private AgreementInfo agreementInfo;
    private SignUserInfo signUserInfo;

    @Data
    public static class AgreementInfo{
        private String agreementType;
    }

    @Data
    public static class SignUserInfo{
        private String type;
        private String name;
        private String certNo;
        private String mobileNo;
    }

    /**
     * 结算卡信息配置
     */
    @Data
    public static class CardInfo {
        private String cardName;
        private String cardNo;
        private String areaId;
        private String certType;
        private String certNo;
        private String certValidityType;
        private String certBeginDate;
        private String certEndDate;
        private String mp;
    }

    /**
     * 结算信息
     */
    @Data
    public static class SettleConfig{

        private String settleStatus;
        private String settleCycle;
        private String minAmt;
        private String remainedAmt;
        private String settleAbstract;
        private String settlePattern;
        private String settleBatchNo;
        private String fixedRatio;
        private String constantAmt;

    }

    /**
     * Convert HuiFuMerchantIndv object to Map<String, Object>
     * @return Map containing all fields of HuiFuMerchantIndv
     */
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
//        map.put("req_seq_id", reqSeqId);
//        map.put("req_date", reqDate);
        map.put("upper_huifu_id", upperHuifuId);
        map.put("huifu_id", huifuId);
        map.put("reg_name", regName);
        map.put("short_name", shortName);
        map.put("mcc", mcc);
        map.put("scene_type", sceneType);
        map.put("prov_id", provId);
        map.put("area_id", areaId);
        map.put("district_id", districtId);
        map.put("detail_addr", detailAddr);
        map.put("legal_cert_no", legalCertNo);
        map.put("legal_cert_begin_date", legalCertBeginDate);
        map.put("legal_cert_validity_type", legalCertValidityType);
        map.put("legal_cert_end_date", legalCertEndDate);
        map.put("legal_addr", legalAddr);
        map.put("legal_cert_back_pic", legalCertBackPic);
        map.put("legal_cert_front_pic", legalCertFrontPic);
        map.put("occupation", occupation);
        map.put("contact_mobile_no", contactMobileNo);
        map.put("contact_email", contactEmail);

        Map<String, String> cardInfoMap = new HashMap<String, String>();
        cardInfoMap.put("card_name", cardInfo.getCardName());
        cardInfoMap.put("card_no", cardInfo.getCardNo());
        cardInfoMap.put("area_id", cardInfo.getAreaId());
        cardInfoMap.put("cert_type", cardInfo.getCertType());
        cardInfoMap.put("cert_no", cardInfo.getCertType());
        cardInfoMap.put("cert_validity_type", cardInfo.getCertValidityType());
        cardInfoMap.put("cert_begin_date", cardInfo.getCertBeginDate());
        cardInfoMap.put("cert_end_date", cardInfo.getCertEndDate());
        cardInfoMap.put("mp", cardInfo.getMp());
        map.put("card_info", cardInfoMap);

        map.put("settle_card_front_pic", settleCardFrontPic);
        map.put("settle_config", settleConfig);
        map.put("cash_config", cashConfig);
        map.put("sms_send_flag", smsSendFlag);
        map.put("login_name", loginName);
        map.put("mer_url", merUrl);
        map.put("mer_icp", merIcp);
        map.put("store_header_pic", storeHeaderPic);
        map.put("store_indoor_pic", storeIndoorPic);
        map.put("store_cashier_desk_pic", storeCashierDeskPic);
        map.put("ext_mer_id", extMerId);
        map.put("remarks", remarks);
        map.put("async_return_url", asyncReturnUrl);
        map.put("head_office_flag", headOfficeFlag);

        if(Objects.nonNull(agreementInfo)) {
            Map<String, Object> agreement_info = new HashMap<String, Object>();
            agreement_info.put("agreement_type", agreementInfo.agreementType);
            map.put("agreement_info", agreement_info);
        }

        if(Objects.nonNull(signUserInfo)) {
            Map<String, Object> sign_user_info = new HashMap<String, Object>();
            sign_user_info.put("type", signUserInfo.type);
            sign_user_info.put("name", signUserInfo.name);
            sign_user_info.put("cert_no", signUserInfo.certNo);
            sign_user_info.put("mobile_no", signUserInfo.mobileNo);
            map.put("sign_user_info", sign_user_info);
        }

        return map;
    }

    public static void main(String[] args) {

        HuiFuMerchantIndvDto huiFuMerchantIndvDto = new HuiFuMerchantIndvDto();

        huiFuMerchantIndvDto.setUpperHuifuId("6666000151772004");
        huiFuMerchantIndvDto.setRegName("周森龙");
        huiFuMerchantIndvDto.setShortName("周森龙");
        huiFuMerchantIndvDto.setMcc("5812");
        huiFuMerchantIndvDto.setSceneType("OFFLINE");
        huiFuMerchantIndvDto.setProvId("410000");
        huiFuMerchantIndvDto.setAreaId("411000");
        huiFuMerchantIndvDto.setDistrictId("411081");
        huiFuMerchantIndvDto.setDetailAddr("河南省禹州市夏都办颖河大街老电视对面禹兴美食");
        huiFuMerchantIndvDto.setLegalCertNo("411081200201289157");
        huiFuMerchantIndvDto.setLegalCertBeginDate("20181214");
        huiFuMerchantIndvDto.setLegalCertValidityType("0");
        huiFuMerchantIndvDto.setLegalCertEndDate("20281214");
        huiFuMerchantIndvDto.setLegalAddr("河南省禹州市夏都办苗场村4组");
        // 国徽面
        huiFuMerchantIndvDto.setLegalCertBackPic("4610bef1-2df1-3f0a-ba22-32a4f1e9e50c");
        // 正面
        huiFuMerchantIndvDto.setLegalCertFrontPic("76ec9902-bbb5-37b3-9b64-0ea4e02124e2");
//        huiFuMerchantIndv.setOccupation();
        huiFuMerchantIndvDto.setContactMobileNo("17597998777");
        huiFuMerchantIndvDto.setContactEmail("1396134381@qq.com");
        HuiFuMerchantIndvDto.CardInfo cardInfo = new HuiFuMerchantIndvDto.CardInfo();

        cardInfo.setCardName("周森龙");
        cardInfo.setCardNo("6215340301446344060");
        cardInfo.setAreaId( "411000");
        cardInfo.setCertType("00");
        cardInfo.setCertNo("411081200201289157");
        cardInfo.setCertValidityType("0");
        cardInfo.setCertBeginDate("20181214");
        cardInfo.setCertEndDate("20281214");
        cardInfo.setMp("17597998777");
        huiFuMerchantIndvDto.setCardInfo(cardInfo);
        // 结算卡正面
        huiFuMerchantIndvDto.setSettleCardFrontPic("a5ca3709-22a6-3376-b0e0-2fdf51c23ee2");
//线下经营-门头照 F22
        huiFuMerchantIndvDto.setStoreHeaderPic("c71d64fe-0329-3282-9b84-a9deedf32a31");
//线下经营-内景照 F24
        huiFuMerchantIndvDto.setStoreIndoorPic("be544e2e-6257-3736-928c-6516a567437c");
//线下经营-收银台 F105
        huiFuMerchantIndvDto.setStoreCashierDeskPic("7cf4f5ce-4d00-321b-8bf7-b1d5770f9607");

    }

}
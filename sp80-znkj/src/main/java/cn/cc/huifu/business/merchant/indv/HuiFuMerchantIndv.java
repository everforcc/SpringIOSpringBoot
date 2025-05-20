package cn.cc.huifu.business.merchant.indv;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 *    sign_user_info.put("name","冯双双");
 *         sign_user_info.put("cert_no","411081198610079089");
 *         sign_user_info.put("mobile_no","15936318171");
 */
@Data
public class HuiFuMerchantIndv {

    private String reqSeqId;
    private String reqDate;
    private String upperHuifuId;
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
    private Map<String,String> cardInfo;
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

    /**
     * Convert HuiFuMerchantIndv object to Map<String, Object>
     * @return Map containing all fields of HuiFuMerchantIndv
     */
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("req_seq_id", reqSeqId);
        map.put("req_date", reqDate);
        map.put("upper_huifu_id", upperHuifuId);
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
        map.put("card_info", cardInfo);
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
        return map;
    }
}
package cn.cc.huifu.refund.dto;

import lombok.Data;

/**
 * 个人商户基本信息认证(KYC)请求DTO
 * 接口文档: https://paas.huifu.com/open/doc/api/#/shgl/shjj/api_shjj_grshjbxxrz_kyc
 */
@Data
public class PersonalMerchantKycDTO {
    
    /**
     * 请求日期 YYYYMMDD
     * 必填
     */
    private String reqDate;
    
    /**
     * 请求流水号
     * 必填，商户自定义
     */
    private String reqSeqId;
    
    /**
     * 汇付客户Id
     * 必填
     */
    private String huifuId;
    
    /**
     * 个人姓名
     * 必填
     */
    private String name;
    
    /**
     * 证件类型
     * 必填，默认00：身份证
     */
    private String certType;
    
    /**
     * 证件号码
     * 必填
     */
    private String certNo;
    
    /**
     * 证件有效期类型
     * 必填，0：有期限  1：长期
     */
    private String certValidityType;
    
    /**
     * 证件有效期开始日期
     * 必填，格式YYYYMMDD
     */
    private String certBeginDate;
    
    /**
     * 证件有效期截止日期
     * 条件必填，证件有效期类型为0时必填，格式YYYYMMDD
     */
    private String certEndDate;
    
    /**
     * 个人证件正面照片
     * 必填，要求1MB以内
     */
    private String identityFront;
    
    /**
     * 个人证件反面照片
     * 必填，要求1MB以内
     */
    private String identityBack;
    
    /**
     * 银行卡号
     */
    private String cardId;
    
    /**
     * 银行卡开户姓名
     */
    private String cardName;
    
    /**
     * 银行卡绑定手机号
     */
    private String cardMp;
    
    /**
     * 银行卡开户证件号
     */
    private String cardCertNo;
    
    /**
     * 个人商户公众号AppId
     */
    private String appId;
    
    /**
     * 个人商户微信OpenId
     */
    private String openId;
    
    /**
     * 授权类型
     */
    private String authType;
    
    /**
     * 外部应用ID
     */
    private String outSysId;
    
    /**
     * 设备信息
     */
    private DeviceInfo deviceInfo;
    
    /**
     * 银行卡信息
     */
    private BankCardInfo bankCardInfo;
    
    /**
     * 商户拓展信息
     */
    private MerchantExtInfo extendInfo;
    
    @Data
    public static class DeviceInfo {
        /**
         * 设备类型
         */
        private String deviceType;
        
        /**
         * 交易设备IP
         */
        private String deviceIp;
        
        /**
         * 交易设备GPS
         */
        private String deviceGps;
        
        /**
         * 交易设备IMEI
         */
        private String deviceImei;
        
        /**
         * 交易设备IMSI
         */
        private String deviceImsi;
        
        /**
         * 交易设备ICCID
         */
        private String deviceIccid;
        
        /**
         * 交易设备MAC
         */
        private String deviceMac;
        
        /**
         * 交易设备WIFIMAC
         */
        private String deviceWifiMac;
    }
    
    @Data
    public static class BankCardInfo {
        /**
         * 联行号
         */
        private String bankCode;
        
        /**
         * 银行名称
         */
        private String bankName;
        
        /**
         * 银行卡预留手机号
         */
        private String cardPhoneNo;
    }
    
    @Data
    public static class MerchantExtInfo {
        /**
         * 经营简称
         */
        private String shortName;
        
        /**
         * 商户一级分类
         */
        private String mccOne;
        
        /**
         * 商户二级分类
         */
        private String mccTwo;
    }
} 
package cn.cc.huifu.refund.dto;

import lombok.Data;

/**
 * 个人商户基本信息认证(KYC)响应DTO
 * 接口文档: https://paas.huifu.com/open/doc/api/#/shgl/shjj/api_shjj_grshjbxxrz_kyc
 */
@Data
public class PersonalMerchantKycRespDTO {
    
    /**
     * 返回码
     */
    private String respCode;
    
    /**
     * 返回信息
     */
    private String respMsg;
    
    /**
     * 请求日期
     */
    private String reqDate;
    
    /**
     * 请求流水号
     */
    private String reqSeqId;
    
    /**
     * 汇付客户Id
     */
    private String huifuId;
    
    /**
     * 交易状态
     * S-交易成功，F-交易失败，P-交易处理中
     */
    private String status;
    
    /**
     * 实名认证状态
     * P：认证中 S：已认证 F：认证失败
     */
    private String verifyStatus;
    
    /**
     * 交易返回信息
     */
    private String remark;
    
    /**
     * 商户认证信息
     */
    private MerchantInfo merchantInfo;
    
    @Data
    public static class MerchantInfo {
        /**
         * 商户认证状态
         */
        private String authStatus;
        
        /**
         * 商户类型
         */
        private String merchantType;
        
        /**
         * 商户审核信息
         */
        private String auditInfo;
        
        /**
         * 商户ID
         */
        private String merchantId;
    }
} 
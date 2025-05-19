package cn.cc.huifu.refund.controller;

import cn.cc.huifu.refund.dto.PersonalMerchantKycDTO;
import cn.cc.huifu.refund.dto.PersonalMerchantKycRespDTO;
import cn.cc.huifu.refund.service.PersonalMerchantKycService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 个人商户KYC认证控制器
 */
@RestController
@RequestMapping("/api/merchant/kyc")
public class MerchantKycController {
    
    private final PersonalMerchantKycService kycService;
    
    @Autowired
    public MerchantKycController(PersonalMerchantKycService kycService) {
        this.kycService = kycService;
    }
    
    /**
     * 执行个人商户KYC认证
     * @param dto 认证请求参数
     * @return 认证结果
     */
    @PostMapping("/auth")
    public PersonalMerchantKycRespDTO doKycAuth(@RequestBody PersonalMerchantKycDTO dto) {
        return kycService.doKycAuthentication(dto);
    }
    
    /**
     * 个人商户KYC认证演示接口
     * @param huifuId 汇付客户ID
     * @param name 姓名
     * @param certNo 证件号码
     * @return 认证结果
     */
    @GetMapping("/demo")
    public PersonalMerchantKycRespDTO kycDemo(
            @RequestParam String huifuId, 
            @RequestParam String name, 
            @RequestParam String certNo) {
        return kycService.kycAuthDemo(huifuId, name, certNo);
    }
    
    /**
     * 接口参数说明
     * @return 参数说明
     */
    @GetMapping("/help")
    public Map<String, Object> apiHelp() {
        Map<String, Object> result = new HashMap<>();
        
        // 基础请求参数
        Map<String, String> baseParams = new HashMap<>();
        baseParams.put("reqDate", "请求日期，格式YYYYMMDD");
        baseParams.put("reqSeqId", "请求流水号，商户自定义，不超过32位");
        
        // data参数下的数据结构
        Map<String, String> dataParams = new HashMap<>();
        dataParams.put("huifuId", "汇付客户Id");
        dataParams.put("name", "个人姓名");
        dataParams.put("certType", "证件类型，默认00：身份证");
        dataParams.put("certNo", "证件号码");
        dataParams.put("certValidityType", "证件有效期类型，0：有期限 1：长期");
        dataParams.put("certBeginDate", "证件有效期开始日期，格式YYYYMMDD");
        dataParams.put("certEndDate", "证件有效期截止日期，格式YYYYMMDD（certValidityType为0时必填）");
        dataParams.put("identityFront", "个人证件正面照片，base64编码");
        dataParams.put("identityBack", "个人证件反面照片，base64编码");
        
        // 可选参数
        Map<String, String> optionalParams = new HashMap<>();
        optionalParams.put("cardId", "银行卡号");
        optionalParams.put("cardName", "银行卡开户姓名");
        optionalParams.put("cardMp", "银行卡绑定手机号");
        optionalParams.put("cardCertNo", "银行卡开户证件号");
        optionalParams.put("appId", "个人商户公众号AppId");
        optionalParams.put("openId", "个人商户微信OpenId");
        optionalParams.put("authType", "授权类型");
        optionalParams.put("outSysId", "外部应用ID");
        
        // 设备信息参数
        Map<String, String> deviceInfo = new HashMap<>();
        deviceInfo.put("deviceType", "设备类型");
        deviceInfo.put("deviceIp", "交易设备IP");
        deviceInfo.put("deviceGps", "交易设备GPS");
        deviceInfo.put("deviceImei", "交易设备IMEI");
        deviceInfo.put("deviceImsi", "交易设备IMSI");
        deviceInfo.put("deviceIccid", "交易设备ICCID");
        deviceInfo.put("deviceMac", "交易设备MAC");
        deviceInfo.put("deviceWifiMac", "交易设备WIFIMAC");
        
        // 银行卡信息
        Map<String, String> bankCardInfo = new HashMap<>();
        bankCardInfo.put("bankCode", "联行号");
        bankCardInfo.put("bankName", "银行名称");
        bankCardInfo.put("cardPhoneNo", "银行卡预留手机号");
        
        // 商户拓展信息
        Map<String, String> merchantExtInfo = new HashMap<>();
        merchantExtInfo.put("shortName", "经营简称");
        merchantExtInfo.put("mccOne", "商户一级分类");
        merchantExtInfo.put("mccTwo", "商户二级分类");
        
        // 响应字段
        Map<String, String> responseFields = new HashMap<>();
        responseFields.put("respCode", "返回码");
        responseFields.put("respMsg", "返回信息");
        responseFields.put("reqDate", "请求日期");
        responseFields.put("reqSeqId", "请求流水号");
        responseFields.put("data.huifuId", "汇付客户ID");
        responseFields.put("data.status", "交易状态：S-成功，F-失败，P-处理中");
        responseFields.put("data.verifyStatus", "实名认证状态：P-认证中，S-已认证，F-认证失败");
        responseFields.put("data.remark", "交易返回信息");
        responseFields.put("data.merchantInfo", "商户认证信息对象");
        
        // 合并结果
        result.put("apiPath", "v2/merchant/basicdata/indv");
        result.put("description", "个人商户基本信息认证接口");
        result.put("baseParams", baseParams);
        result.put("dataParams", dataParams);
        result.put("optionalParams", optionalParams);
        result.put("deviceInfo", deviceInfo);
        result.put("bankCardInfo", bankCardInfo);
        result.put("merchantExtInfo", merchantExtInfo);
        result.put("responseFields", responseFields);
        result.put("requestStructure", "请求参数主要包含req_date, req_seq_id和data三个字段，详细参数位于data字段内");
        result.put("apiDoc", "https://paas.huifu.com/open/doc/api/#/shgl/shjj/api_shjj_grshjbxxrz_kyc");
        
        return result;
    }
} 
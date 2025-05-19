package cn.cc.huifu.refund.service;

import cn.cc.huifu.config.PayConfig;
import cn.cc.huifu.refund.dto.PersonalMerchantKycDTO;
import cn.cc.huifu.refund.dto.PersonalMerchantKycRespDTO;
import com.huifu.bspay.sdk.opps.core.BasePay;
import com.huifu.bspay.sdk.opps.core.exception.BasePayException;
import com.huifu.bspay.sdk.opps.core.net.BasePayRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 个人商户基本信息认证(KYC)服务
 */
@Service
public class PersonalMerchantKycService {
    
    private static final Logger log = LoggerFactory.getLogger(PersonalMerchantKycService.class);
    
    /**
     * 执行个人商户基本信息认证
     * @param dto 认证请求参数
     * @return 认证响应结果
     */
    public PersonalMerchantKycRespDTO doKycAuthentication(PersonalMerchantKycDTO dto) {
        log.info("开始个人商户KYC认证，请求参数：{}", dtoToString(dto));
        
        try {
            // 初始化商户配置
            BasePay.initWithMerConfig(PayConfig.getMerchantConfig());
        } catch (Exception e) {
            log.error("初始化商户配置失败: {}", e.getMessage(), e);
            PersonalMerchantKycRespDTO errorResp = new PersonalMerchantKycRespDTO();
            errorResp.setRespCode("FAILED");
            errorResp.setRespMsg("商户配置初始化失败: " + e.getMessage());
            return errorResp;
        }
        
        // 构建请求参数
        Map<String, Object> paramsMap = buildRequestParams(dto);
        
        // 发起API调用
        Map<String, Object> response = null;
        try {
            // 个人商户基本信息接口地址 - 使用新的接口路径
            response = BasePayRequest.requestBasePay("v2/merchant/basicdata/indv", paramsMap, null, false);
            log.info("个人商户KYC认证响应结果：{}", mapToString(response));
        } catch (BasePayException e) {
            log.error("个人商户KYC认证请求异常: {}", e.getMessage(), e);
            PersonalMerchantKycRespDTO errorResp = new PersonalMerchantKycRespDTO();
            errorResp.setRespCode("FAILED");
            errorResp.setRespMsg("请求异常: " + e.getMessage());
            return errorResp;
        }
        
        // 解析响应结果
        return parseResponse(response);
    }
    
    /**
     * 将DTO转换为字符串用于日志记录
     * 避免输出敏感信息
     */
    private String dtoToString(PersonalMerchantKycDTO dto) {
        if (dto == null) {
            return "null";
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("PersonalMerchantKycDTO{");
        sb.append("reqDate='").append(dto.getReqDate()).append('\'');
        sb.append(", reqSeqId='").append(dto.getReqSeqId()).append('\'');
        sb.append(", huifuId='").append(dto.getHuifuId()).append('\'');
        sb.append(", name='").append(maskSensitiveInfo(dto.getName())).append('\'');
        sb.append(", certNo='").append(maskSensitiveInfo(dto.getCertNo())).append('\'');
        sb.append(", certType='").append(dto.getCertType()).append('\'');
        sb.append('}');
        return sb.toString();
    }
    
    /**
     * 将响应Map转换为字符串用于日志记录
     */
    private String mapToString(Map<String, Object> map) {
        if (map == null) {
            return "null";
        }
        return map.toString();
    }
    
    /**
     * 掩码敏感信息
     */
    private String maskSensitiveInfo(String info) {
        if (info == null || info.length() <= 2) {
            return "***";
        }
        
        return info.substring(0, 1) + "***" + 
               (info.length() > 3 ? info.substring(info.length() - 1) : "");
    }
    
    /**
     * 构建请求参数
     * @param dto 请求DTO
     * @return 请求参数Map
     */
    private Map<String, Object> buildRequestParams(PersonalMerchantKycDTO dto) {
        Map<String, Object> paramsMap = new HashMap<>();
        
        // 请求日期，默认当前日期YYYYMMDD
        if (dto.getReqDate() == null || dto.getReqDate().isEmpty()) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            paramsMap.put("req_date", sdf.format(new Date()));
        } else {
            paramsMap.put("req_date", dto.getReqDate());
        }
        
        // 请求流水号，默认生成唯一标识
        if (dto.getReqSeqId() == null || dto.getReqSeqId().isEmpty()) {
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            paramsMap.put("req_seq_id", uuid);
        } else {
            paramsMap.put("req_seq_id", dto.getReqSeqId());
        }
        
        // 构建data参数
        Map<String, Object> dataMap = new HashMap<>();
        
        // 必填参数
        dataMap.put("huifu_id", dto.getHuifuId());
        dataMap.put("name", dto.getName());
        dataMap.put("cert_type", dto.getCertType() != null ? dto.getCertType() : "00");  // 默认身份证
        dataMap.put("cert_no", dto.getCertNo());
        dataMap.put("cert_validity_type", dto.getCertValidityType());
        dataMap.put("cert_begin_date", dto.getCertBeginDate());
        dataMap.put("identity_front", dto.getIdentityFront());
        dataMap.put("identity_back", dto.getIdentityBack());
        
        // 条件必填参数
        if ("0".equals(dto.getCertValidityType())) {
            // 有期限证件必须填写截止日期
            dataMap.put("cert_end_date", dto.getCertEndDate());
        }
        
        // 非必填参数
        if (dto.getCardId() != null && !dto.getCardId().isEmpty()) {
            dataMap.put("card_id", dto.getCardId());
        }
        
        if (dto.getCardName() != null && !dto.getCardName().isEmpty()) {
            dataMap.put("card_name", dto.getCardName());
        }
        
        if (dto.getCardMp() != null && !dto.getCardMp().isEmpty()) {
            dataMap.put("card_mp", dto.getCardMp());
        }
        
        if (dto.getCardCertNo() != null && !dto.getCardCertNo().isEmpty()) {
            dataMap.put("card_cert_no", dto.getCardCertNo());
        }
        
        if (dto.getAppId() != null && !dto.getAppId().isEmpty()) {
            dataMap.put("app_id", dto.getAppId());
        }
        
        if (dto.getOpenId() != null && !dto.getOpenId().isEmpty()) {
            dataMap.put("open_id", dto.getOpenId());
        }
        
        if (dto.getAuthType() != null && !dto.getAuthType().isEmpty()) {
            dataMap.put("auth_type", dto.getAuthType());
        }
        
        if (dto.getOutSysId() != null && !dto.getOutSysId().isEmpty()) {
            dataMap.put("out_sys_id", dto.getOutSysId());
        }
        
        // 设备信息
        if (dto.getDeviceInfo() != null) {
            Map<String, Object> deviceMap = new HashMap<>();
            PersonalMerchantKycDTO.DeviceInfo deviceInfo = dto.getDeviceInfo();
            
            if (deviceInfo.getDeviceType() != null) {
                deviceMap.put("device_type", deviceInfo.getDeviceType());
            }
            if (deviceInfo.getDeviceIp() != null) {
                deviceMap.put("device_ip", deviceInfo.getDeviceIp());
            }
            if (deviceInfo.getDeviceGps() != null) {
                deviceMap.put("device_gps", deviceInfo.getDeviceGps());
            }
            if (deviceInfo.getDeviceImei() != null) {
                deviceMap.put("device_imei", deviceInfo.getDeviceImei());
            }
            if (deviceInfo.getDeviceImsi() != null) {
                deviceMap.put("device_imsi", deviceInfo.getDeviceImsi());
            }
            if (deviceInfo.getDeviceIccid() != null) {
                deviceMap.put("device_iccid", deviceInfo.getDeviceIccid());
            }
            if (deviceInfo.getDeviceMac() != null) {
                deviceMap.put("device_mac", deviceInfo.getDeviceMac());
            }
            if (deviceInfo.getDeviceWifiMac() != null) {
                deviceMap.put("device_wifi_mac", deviceInfo.getDeviceWifiMac());
            }
            
            if (!deviceMap.isEmpty()) {
                dataMap.put("device_info", deviceMap);
            }
        }
        
        // 银行卡信息
        if (dto.getBankCardInfo() != null) {
            Map<String, Object> bankCardMap = new HashMap<>();
            PersonalMerchantKycDTO.BankCardInfo bankCardInfo = dto.getBankCardInfo();
            
            if (bankCardInfo.getBankCode() != null) {
                bankCardMap.put("bank_code", bankCardInfo.getBankCode());
            }
            if (bankCardInfo.getBankName() != null) {
                bankCardMap.put("bank_name", bankCardInfo.getBankName());
            }
            if (bankCardInfo.getCardPhoneNo() != null) {
                bankCardMap.put("card_phone_no", bankCardInfo.getCardPhoneNo());
            }
            
            if (!bankCardMap.isEmpty()) {
                dataMap.put("bank_card_info", bankCardMap);
            }
        }
        
        // 商户拓展信息
        if (dto.getExtendInfo() != null) {
            Map<String, Object> extendMap = new HashMap<>();
            PersonalMerchantKycDTO.MerchantExtInfo extendInfo = dto.getExtendInfo();
            
            if (extendInfo.getShortName() != null) {
                extendMap.put("short_name", extendInfo.getShortName());
            }
            if (extendInfo.getMccOne() != null) {
                extendMap.put("mcc_one", extendInfo.getMccOne());
            }
            if (extendInfo.getMccTwo() != null) {
                extendMap.put("mcc_two", extendInfo.getMccTwo());
            }
            
            if (!extendMap.isEmpty()) {
                dataMap.put("extend_info", extendMap);
            }
        }
        
        // 将data放入主参数Map
        paramsMap.put("data", dataMap);
        
        return paramsMap;
    }
    
    /**
     * 解析响应结果
     * @param response API响应Map
     * @return 响应DTO
     */
    private PersonalMerchantKycRespDTO parseResponse(Map<String, Object> response) {
        if (response == null) {
            PersonalMerchantKycRespDTO errorResp = new PersonalMerchantKycRespDTO();
            errorResp.setRespCode("FAILED");
            errorResp.setRespMsg("响应结果为空");
            return errorResp;
        }
        
        PersonalMerchantKycRespDTO respDTO = new PersonalMerchantKycRespDTO();
        
        // 基础响应信息
        respDTO.setRespCode(String.valueOf(response.getOrDefault("resp_code", "")));
        respDTO.setRespMsg(String.valueOf(response.getOrDefault("resp_msg", "")));
        respDTO.setReqDate(String.valueOf(response.getOrDefault("req_date", "")));
        respDTO.setReqSeqId(String.valueOf(response.getOrDefault("req_seq_id", "")));
        
        // 如果响应中包含data字段，则从data中获取详细信息
        if (response.containsKey("data")) {
            Object dataObj = response.get("data");
            if (dataObj instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<String, Object> dataMap = (Map<String, Object>) dataObj;
                
                respDTO.setHuifuId(String.valueOf(dataMap.getOrDefault("huifu_id", "")));
                respDTO.setStatus(String.valueOf(dataMap.getOrDefault("status", "")));
                respDTO.setVerifyStatus(String.valueOf(dataMap.getOrDefault("verify_status", "")));
                respDTO.setRemark(String.valueOf(dataMap.getOrDefault("remark", "")));
                
                // 商户信息
                if (dataMap.containsKey("merchant_info")) {
                    Object merchantInfoObj = dataMap.get("merchant_info");
                    if (merchantInfoObj instanceof Map) {
                        @SuppressWarnings("unchecked")
                        Map<String, Object> merchantInfoMap = (Map<String, Object>) merchantInfoObj;
                        
                        PersonalMerchantKycRespDTO.MerchantInfo merchantInfo = new PersonalMerchantKycRespDTO.MerchantInfo();
                        merchantInfo.setAuthStatus(String.valueOf(merchantInfoMap.getOrDefault("auth_status", "")));
                        merchantInfo.setMerchantType(String.valueOf(merchantInfoMap.getOrDefault("merchant_type", "")));
                        merchantInfo.setAuditInfo(String.valueOf(merchantInfoMap.getOrDefault("audit_info", "")));
                        merchantInfo.setMerchantId(String.valueOf(merchantInfoMap.getOrDefault("merchant_id", "")));
                        
                        respDTO.setMerchantInfo(merchantInfo);
                    }
                }
            }
        } else {
            // 如果没有data字段，尝试从主响应中获取
            respDTO.setHuifuId(String.valueOf(response.getOrDefault("huifu_id", "")));
            respDTO.setStatus(String.valueOf(response.getOrDefault("status", "")));
            respDTO.setVerifyStatus(String.valueOf(response.getOrDefault("verify_status", "")));
            respDTO.setRemark(String.valueOf(response.getOrDefault("remark", "")));
            
            // 商户信息
            if (response.containsKey("merchant_info")) {
                Object merchantInfoObj = response.get("merchant_info");
                if (merchantInfoObj instanceof Map) {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> merchantInfoMap = (Map<String, Object>) merchantInfoObj;
                    
                    PersonalMerchantKycRespDTO.MerchantInfo merchantInfo = new PersonalMerchantKycRespDTO.MerchantInfo();
                    merchantInfo.setAuthStatus(String.valueOf(merchantInfoMap.getOrDefault("auth_status", "")));
                    merchantInfo.setMerchantType(String.valueOf(merchantInfoMap.getOrDefault("merchant_type", "")));
                    merchantInfo.setAuditInfo(String.valueOf(merchantInfoMap.getOrDefault("audit_info", "")));
                    merchantInfo.setMerchantId(String.valueOf(merchantInfoMap.getOrDefault("merchant_id", "")));
                    
                    respDTO.setMerchantInfo(merchantInfo);
                }
            }
        }
        
        return respDTO;
    }
    
    /**
     * 示例：创建一个个人商户KYC认证演示方法
     */
    public PersonalMerchantKycRespDTO kycAuthDemo(String huifuId, String name, String certNo) {
        PersonalMerchantKycDTO dto = new PersonalMerchantKycDTO();
        
        // 设置基础必填信息
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        dto.setReqDate(sdf.format(new Date()));
        dto.setReqSeqId(UUID.randomUUID().toString().replaceAll("-", ""));
        dto.setHuifuId(huifuId);
        dto.setName(name);
        dto.setCertType("00"); // 身份证
        dto.setCertNo(certNo);
        dto.setCertValidityType("0"); // 有期限
        dto.setCertBeginDate(sdf.format(new Date()));
        
        // 设置证件有效期截止日期（当前日期加5年）
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 5);
        dto.setCertEndDate(sdf.format(calendar.getTime()));
        
        // 证件照片（示例值，实际使用时需要替换为真实的图片base64编码）
        dto.setIdentityFront("base64编码的身份证正面照片...");
        dto.setIdentityBack("base64编码的身份证反面照片...");
        
        // 其他可选信息
        dto.setCardId("6225888888888888");
        dto.setCardName(name);
        dto.setCardMp("13800138000");
        
        // 设备信息
        PersonalMerchantKycDTO.DeviceInfo deviceInfo = new PersonalMerchantKycDTO.DeviceInfo();
        deviceInfo.setDeviceType("1"); // 1-Android, 2-iOS
        deviceInfo.setDeviceIp("192.168.1.1");
        dto.setDeviceInfo(deviceInfo);
        
        // 执行认证
        return doKycAuthentication(dto);
    }
} 
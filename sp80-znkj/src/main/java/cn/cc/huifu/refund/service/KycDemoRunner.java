package cn.cc.huifu.refund.service;

import cn.cc.huifu.config.PayConfig;
import cn.cc.huifu.refund.dto.PersonalMerchantKycDTO;
import cn.cc.huifu.refund.dto.PersonalMerchantKycRespDTO;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * KYC认证演示程序，仅在特定配置启用时运行
 * 使用方式：在application.properties中设置 spring.profiles.active=kyc-demo
 */
@Component
@Profile("kyc-demo")
public class KycDemoRunner implements CommandLineRunner {

    private static final PersonalMerchantKycService kycService = new PersonalMerchantKycService();

//    public KycDemoRunner(PersonalMerchantKycService kycService) {
//        this.kycService = kycService;
//    }

    public static void main(String[] args) {
        try {
            run2(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(String... args) throws Exception {

    }


    public static void run2(String... args) throws Exception {
        System.out.println("=============================================");
        System.out.println("开始执行个人商户KYC认证演示 - v2/merchant/basicdata/indv");
        System.out.println("=============================================");
        
        try {
            // 构建演示数据
            PersonalMerchantKycDTO dto = buildDemoData();
            
            // 执行认证
            PersonalMerchantKycRespDTO respDTO = kycService.doKycAuthentication(dto);
            
            // 输出结果
            System.out.println("KYC认证响应结果:");
            System.out.println("返回码: " + respDTO.getRespCode());
            System.out.println("返回信息: " + respDTO.getRespMsg());
            System.out.println("请求日期: " + respDTO.getReqDate());
            System.out.println("请求流水号: " + respDTO.getReqSeqId());
            System.out.println("汇付客户ID: " + respDTO.getHuifuId());
            System.out.println("交易状态: " + respDTO.getStatus());
            System.out.println("认证状态: " + respDTO.getVerifyStatus());
            System.out.println("备注信息: " + respDTO.getRemark());
            
            if (respDTO.getMerchantInfo() != null) {
                System.out.println("商户信息:");
                System.out.println("  商户认证状态: " + respDTO.getMerchantInfo().getAuthStatus());
                System.out.println("  商户类型: " + respDTO.getMerchantInfo().getMerchantType());
                System.out.println("  商户审核信息: " + respDTO.getMerchantInfo().getAuditInfo());
                System.out.println("  商户ID: " + respDTO.getMerchantInfo().getMerchantId());
            }
        } catch (Exception e) {
            System.err.println("KYC认证演示执行异常: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("=============================================");
        System.out.println("个人商户KYC认证演示完成");
        System.out.println("=============================================");
    }
    
    /**
     * 构建演示数据
     */
    private static PersonalMerchantKycDTO buildDemoData() {
        PersonalMerchantKycDTO dto = new PersonalMerchantKycDTO();
        
        // 设置基础必填信息
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        dto.setReqDate(sdf.format(new Date()));
        dto.setReqSeqId(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32)); // 确保不超过32位
        
        // 设置汇付客户ID - 根据实际情况修改
        dto.setHuifuId("6666000154267351");
        
        // 个人身份信息
        dto.setName("张三");
        dto.setCertType("00"); // 身份证
        dto.setCertNo("110101199001011234");
        dto.setCertValidityType("0"); // 有期限
        dto.setCertBeginDate(sdf.format(new Date()));
        
        // 设置证件有效期截止日期（当前日期加10年）
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 10);
        dto.setCertEndDate(sdf.format(calendar.getTime()));
        
        // 证件照片 Base64编码
        // 实际情况下，应该读取实际照片文件并进行Base64编码
        String dummyImageBase64 = Base64.getEncoder().encodeToString(("证件照片Base64编码示例 - " + UUID.randomUUID().toString()).getBytes());
        dto.setIdentityFront(dummyImageBase64);
        dto.setIdentityBack(dummyImageBase64);
        
        // 银行卡信息
        dto.setCardId("6225888812345678");
        dto.setCardName("张三");
        dto.setCardMp("13800138000");
        dto.setCardCertNo(dto.getCertNo()); // 使用同样的证件号
        
        // 设备信息
        PersonalMerchantKycDTO.DeviceInfo deviceInfo = new PersonalMerchantKycDTO.DeviceInfo();
        deviceInfo.setDeviceType("1"); // 1-Android, 2-iOS
        deviceInfo.setDeviceIp("192.168.1.100");
        deviceInfo.setDeviceGps("39.9,116.3");
        deviceInfo.setDeviceImei("865166023949731");
        deviceInfo.setDeviceImsi("460011234567890");
        deviceInfo.setDeviceIccid("89860123456789012345");
        deviceInfo.setDeviceMac("00:11:22:33:44:55");
        deviceInfo.setDeviceWifiMac("AA:BB:CC:DD:EE:FF");
        dto.setDeviceInfo(deviceInfo);
        
        // 银行卡信息
        PersonalMerchantKycDTO.BankCardInfo bankCardInfo = new PersonalMerchantKycDTO.BankCardInfo();
        bankCardInfo.setBankCode("01030000");
        bankCardInfo.setBankName("中国农业银行");
        bankCardInfo.setCardPhoneNo("13800138000");
        dto.setBankCardInfo(bankCardInfo);
        
        // 商户拓展信息
        PersonalMerchantKycDTO.MerchantExtInfo extendInfo = new PersonalMerchantKycDTO.MerchantExtInfo();
        extendInfo.setShortName("张三店铺");
        extendInfo.setMccOne("2013");  // 示例分类代码
        extendInfo.setMccTwo("2013001");  // 示例分类代码
        dto.setExtendInfo(extendInfo);
        
        return dto;
    }
} 
package cn.cc.huifu.business.merchant.open;


import cn.cc.huifu.business.merchant.open.dto.HuiFuMerchantBusiOpenDto;
import cn.cc.huifu.constants.ZnHFURLContants;
import cn.cc.huifu.utils.ZnHFReqUtils;

import java.util.Map;

/**
 * @see <a href="https://paas.huifu.com/open/doc/api/#/shgl/shywkt/api_shjj_shywkt_kyc">商户业务开通</a>
 * <p>
 * https://api.huifu.com/v2/merchant/busi/open
 */
public class ZnHFMerchantIndvOpen {

    public static void main(String[] args) {
        HuiFuMerchantBusiOpenDto huiFuMerchantIndvDto = new HuiFuMerchantBusiOpenDto();

        huiFuMerchantIndvDto.setHuifuId("6666000168410819");
        huiFuMerchantIndvDto.setUpperHuifuId("6666000151772004");

        HuiFuMerchantBusiOpenDto.AgreementInfo agreement_info = new HuiFuMerchantBusiOpenDto.AgreementInfo();
        agreement_info.setAgreementType("0");
        huiFuMerchantIndvDto.setAgreementInfo(agreement_info);

        HuiFuMerchantBusiOpenDto.SignUserInfo signUserInfo = new HuiFuMerchantBusiOpenDto.SignUserInfo();
        signUserInfo.setType("LEGAL");
        signUserInfo.setName("冯双双");
        signUserInfo.setCertNo("411081198610079089");
        signUserInfo.setMobileNo("15936318171");
        huiFuMerchantIndvDto.setSignUserInfo(signUserInfo);

        flowBusiOpen(huiFuMerchantIndvDto.toMap());
    }

    public static Map<String, Object> flowBusiOpen(Map<String, Object> map) {
        return ZnHFReqUtils.flow(map, ZnHFURLContants.MERCHANT_BUSI_OPEN);
    }

//    public static Map<String, Object> refundFlow(Map<String,Object> map) {
//        try {
//            BasePay.initWithMerConfig(PayConfig.getMerchantConfig());
//        } catch (Exception e) {
//            System.err.println("---");
//            e.printStackTrace();
//            System.err.println("---");
//            System.err.println("初始化报错: " + e.getMessage());
//        }
////        Map<String, Object> paramsInfo = new HashMap<>();
//
//        // 3. 发起API调用
//        Map<String, Object> response = null;
//        try {
//            response = BasePayRequest.requestBasePay("v2/merchant/busi/open", map, null, false);
//        } catch (BasePayException e) {
//            System.err.println("---");
//            e.printStackTrace();
//            System.err.println("---");
//            System.err.println("返回报错: " + e.getMessage());
//        }
//        System.out.println(response);
//        return response;
//    }

}


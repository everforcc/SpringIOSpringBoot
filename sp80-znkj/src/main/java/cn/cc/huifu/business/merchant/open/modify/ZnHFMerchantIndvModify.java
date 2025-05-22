package cn.cc.huifu.business.merchant.open.modify;


import cn.cc.huifu.business.merchant.open.dto.HuiFuMerchantBusiOpenDto;
import cn.cc.huifu.config.PayConfig;
import cn.cc.huifu.constants.ZnHFURLContants;
import cn.cc.huifu.utils.ZnHFReqUtils;
import com.alibaba.fastjson.JSONArray;
import com.huifu.bspay.sdk.opps.core.BasePay;
import com.huifu.bspay.sdk.opps.core.exception.BasePayException;
import com.huifu.bspay.sdk.opps.core.net.BasePayRequest;

import java.util.*;

/**
 *
 * @see <a href="https://paas.huifu.com/open/doc/api/#/shgl/shywkt/api_shjj_shywktxg_kyc">商户业务开通修改</a>
 *
 * https://api.huifu.com/v2/merchant/busi/modify
 */
public class ZnHFMerchantIndvModify {

    public static void main(String[] args) {
        HuiFuMerchantBusiOpenDto huiFuMerchantBusiOpenDto = new HuiFuMerchantBusiOpenDto();
        huiFuMerchantBusiOpenDto.setHuifuId("6666000168410819");
        huiFuMerchantBusiOpenDto.setUpperHuifuId("6666000151772004");


        // 2.5‰ 0.25%
        // {"resp_desc":"线下支付宝反扫支付费率不能低于渠道商/上级商户费率底价MAX(0.01,AMT*0.0025)","req_seq_id":"1067060048050371940","product_id":"PAYUN","req_date":"20250519","resp_code":"99999999","huifu_id":"6666000168410819"}
        // {"resp_desc":"线下支付宝反扫支付费率不能低于渠道商/上级商户费率底价MAX(0.01,AMT*0.0065)","req_seq_id":"1067060048050371940","product_id":"PAYUN","req_date":"20250519","resp_code":"99999999","huifu_id":"6666000168410819"}

        List<HuiFuMerchantBusiOpenDto.AliConf> aliConfList = new ArrayList<>();
        HuiFuMerchantBusiOpenDto.AliConf aliConf = new HuiFuMerchantBusiOpenDto.AliConf();
        JSONArray ali_conf_list = new JSONArray();
        Map<String,Object> ali_conf1 = new HashMap<String,Object>();
        aliConf.setPayScene("1");//
        aliConf.setFeeRate("0.25");
        aliConf.setSwitchState("1");
        aliConf.setFeeMinAmt("0.01");

        HuiFuMerchantBusiOpenDto.AliConf aliConf2 = new HuiFuMerchantBusiOpenDto.AliConf();
        aliConf2.setPayScene("2");
        aliConf2.setFeeRate("0.65");
        aliConf2.setSwitchState("1");
        aliConf2.setFeeMinAmt("0.01");
        aliConfList.add(aliConf);
        aliConfList.add(aliConf2);
        huiFuMerchantBusiOpenDto.setAliConfList(aliConfList);

        List<HuiFuMerchantBusiOpenDto.WxConf> wxConfList = new ArrayList<>();
        HuiFuMerchantBusiOpenDto.WxConf wxConf = new HuiFuMerchantBusiOpenDto.WxConf();
        wxConf.setPayScene("1");
        wxConf.setFeeRate("0.25");
        wxConf.setSwitchState("1");
        wxConf.setFeeMinAmt("0.01");
        wxConfList.add(wxConf);
        huiFuMerchantBusiOpenDto.setWxConfList(wxConfList);

        HuiFuMerchantBusiOpenDto.AgreementInfo agreementInfo = new HuiFuMerchantBusiOpenDto.AgreementInfo();
        agreementInfo.setAgreementType("0");
        huiFuMerchantBusiOpenDto.setAgreementInfo(agreementInfo);


        HuiFuMerchantBusiOpenDto.SignUserInfo  signUserInfo = new HuiFuMerchantBusiOpenDto.SignUserInfo();
        signUserInfo.setType("LEGAL");
        signUserInfo.setName("冯双双");
        signUserInfo.setCertNo("411081198610079089");
        signUserInfo.setMobileNo("15936318171");
        huiFuMerchantBusiOpenDto.setSignUserInfo(signUserInfo);

        flowOpenModify(huiFuMerchantBusiOpenDto.toMap());
    }

    public static Map<String, Object> flowOpenModify(Map<String,Object> map) {
        return ZnHFReqUtils.flow(map, ZnHFURLContants.MERCHANT_BUSI_MODIFY);
    }


    public static Map<String, Object> refundFlow(Map<String,Object> map) {
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
            response = BasePayRequest.requestBasePay("v2/merchant/busi/modify", map, null, false);
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


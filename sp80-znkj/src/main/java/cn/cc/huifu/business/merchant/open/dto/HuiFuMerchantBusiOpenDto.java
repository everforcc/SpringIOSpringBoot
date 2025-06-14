package cn.cc.huifu.business.merchant.open.dto;

import com.alibaba.fastjson.JSONArray;
import lombok.Data;

import java.util.*;

/**
 * 商户业务开通
 * 修改
 */
@Data
public class HuiFuMerchantBusiOpenDto {

//    private String reqSeqId;
//    private String reqDate;
    private String huifuId;
    private String upperHuifuId;
    private AgreementInfo agreementInfo;
    private SignUserInfo signUserInfo;
    private List<WxConf> wxConfList;
    private List<AliConf> aliConfList;


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
     Map<String,Object> wx_conf1 = new HashMap<String,Object>();
     wx_conf1.put("pay_scene","1");
     wx_conf1.put("fee_rate","0.25");
     wx_conf1.put("switch_state","1");
     wx_conf1.put("fee_min_amt","0.01");
     */
    @Data
    public static class WxConf{
        private String payScene;
        private String feeRate;
        private String switchState;
        private String feeMinAmt;
    }

    /**
     Map<String,Object> ali_conf1 = new HashMap<String,Object>();
     ali_conf1.put("pay_scene","1");//
     ali_conf1.put("fee_rate","0.25");
     ali_conf1.put("switch_state","1");
     ali_conf1.put("fee_min_amt","0.01");
     */
    @Data
    public static class AliConf{
        private String payScene;
        private String feeRate;
        private String switchState;
        private String feeMinAmt;
    }


    // 生成一个toMap
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("huifu_id", huifuId);
        map.put("upper_huifu_id", upperHuifuId);

        Map<String,Object> agreement_info = new HashMap<String,Object>();
        agreement_info.put("agreement_type", agreementInfo.agreementType);
        map.put("agreement_info", agreement_info);

        Map<String,Object> sign_user_info = new HashMap<String,Object>();
        sign_user_info.put("type", signUserInfo.type);
        sign_user_info.put("name", signUserInfo.name);
        sign_user_info.put("cert_no", signUserInfo.certNo);
        sign_user_info.put("mobile_no", signUserInfo.mobileNo);
        map.put("sign_user_info", sign_user_info);

        JSONArray wx_conf_list = new JSONArray();
        if(Objects.nonNull(wxConfList)) {
            for (WxConf wxConf : wxConfList) {
                Map<String, Object> wx_conf = new HashMap<String, Object>();
                wx_conf.put("pay_scene", wxConf.payScene);
                wx_conf.put("fee_rate", wxConf.feeRate);
                wx_conf.put("switch_state", wxConf.switchState);
                wx_conf.put("fee_min_amt", wxConf.feeMinAmt);
                wx_conf_list.add(wx_conf);
            }
            map.put("wx_conf_list", wx_conf_list.toString());
        }

        if(Objects.nonNull(aliConfList)) {
            JSONArray ali_conf_list = new JSONArray();
            for (AliConf aliConf : aliConfList) {
                Map<String, Object> ali_conf = new HashMap<String, Object>();
                ali_conf.put("pay_scene", aliConf.payScene);
                ali_conf.put("fee_rate", aliConf.feeRate);
                ali_conf.put("switch_state", aliConf.switchState);
                ali_conf.put("fee_min_amt", aliConf.feeMinAmt);
                ali_conf_list.add(ali_conf);
            }
            map.put("ali_conf_list", ali_conf_list.toString());
        }

        return map;
    }



}

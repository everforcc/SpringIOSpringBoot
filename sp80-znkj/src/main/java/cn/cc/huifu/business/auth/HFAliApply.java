package cn.cc.huifu.business.auth;

import cn.cc.huifu.business.auth.dto.HFAliApplyDto;
import cn.cc.huifu.utils.ZnHFReqUtils;

/**
 * 支付宝实名认证
 */
public class HFAliApply {

    public static void main(String[] args) {
        HFAliApplyDto hfAliApplyDto = new HFAliApplyDto();
        hfAliApplyDto.setHuifuId("6666000168672654");
        HFAliApplyDto.ContactPersonInfo contactPersonInfo = new HFAliApplyDto.ContactPersonInfo();
        contactPersonInfo.setName("周森龙");
        contactPersonInfo.setMobile("17597998777");
        contactPersonInfo.setIdCardNumber("411081200201289157");
        hfAliApplyDto.setContactPersonInfo(contactPersonInfo);
        HFAliApplyDto.AuthIdentityInfo authIdentityInfo = new HFAliApplyDto.AuthIdentityInfo();
        authIdentityInfo.setBusinessType("0");
        hfAliApplyDto.setAuthIdentityInfo(authIdentityInfo);
        ZnHFReqUtils.flow(hfAliApplyDto.toMap(), "v2/merchant/busi/ali/realname/apply");
        // {"resp_desc":"基础参数校验失败:主体类型为小微商户时，【support_credentials-辅助证明材料信息】不能为空","resp_code":"00000001"}
    }

}

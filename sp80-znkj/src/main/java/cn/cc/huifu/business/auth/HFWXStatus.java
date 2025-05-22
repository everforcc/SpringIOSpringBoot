package cn.cc.huifu.business.auth;

import cn.cc.huifu.business.auth.dto.HFAliApplyDto;
import cn.cc.huifu.utils.ZnHFReqUtils;

/**
 * 微信实名认证
 */
public class HFWXStatus {

    public static void main(String[] args) {
        HFAliApplyDto hfAliApplyDto = new HFAliApplyDto();
        hfAliApplyDto.setHuifuId("6666000168672654");

        ZnHFReqUtils.flow(hfAliApplyDto.toMap(), "v2/merchant/busi/realname/query");
    }

}

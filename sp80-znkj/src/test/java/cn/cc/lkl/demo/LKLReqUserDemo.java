package cn.cc.lkl.demo;

import cn.cc.lkl.dto.LKLCommonResponse;
import cn.cc.lkl.dto.merchantencry.V3TkbsOrganizationParentCodeRequest;
import cn.cc.lkl.sdk.config.LKLConfig;
import cn.cc.lkl.util.LKLPost;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LKLReqUserDemo {

    public static void main(String[] args) {
        try {
            LKLConfig.initSDK();
            testOrganizationParentCode();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testOrganizationParentCode() {
        V3TkbsOrganizationParentCodeRequest commRequest = new V3TkbsOrganizationParentCodeRequest();
        commRequest.setParentCode("1");
        commRequest.setOrgCode("1951582");
        LKLCommonResponse lklCommonResponse = LKLPost.httpPost(commRequest);
        log.info("response: \r\n{}", lklCommonResponse.toString());
    }

}

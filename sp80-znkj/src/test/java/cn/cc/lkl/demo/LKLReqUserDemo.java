package cn.cc.lkl.demo;

import cn.cc.lkl.LKLBaseTest;
import cn.cc.lkl.dto.LKLCommonResponse;
import cn.cc.lkl.dto.merchant.V3TkbsOrganizationParentCodeRequest;
import cn.cc.lkl.util.LKLPost;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LKLReqUserDemo extends LKLBaseTest {

    public static void testOrganizationParentCode() {
        V3TkbsOrganizationParentCodeRequest commRequest = new V3TkbsOrganizationParentCodeRequest();
        commRequest.setParentCode("1");
        commRequest.setOrgCode("1951582");
        LKLCommonResponse lklCommonResponse = LKLPost.httpPost(commRequest);
        log.info("response: \r\n{}", lklCommonResponse.toString());
    }

}

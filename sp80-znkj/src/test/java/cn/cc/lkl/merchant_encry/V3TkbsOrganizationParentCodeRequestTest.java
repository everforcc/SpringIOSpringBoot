package cn.cc.lkl.merchant_encry;


import cn.cc.lkl.dto.LKLCommonResponse;
import cn.cc.lkl.dto.merchantencry.V3TkbsOrganizationParentCodeRequest;
import cn.cc.lkl.sdk.config.LKLConfig;
import cn.cc.lkl.util.LKLPost;
import com.lkl.laop.sdk.exception.SDKException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

@Slf4j
public class V3TkbsOrganizationParentCodeRequestTest {

    @Before
    public void pre() {
        try {
            LKLConfig.initSDK();
        } catch (SDKException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testOrganizationParentCode() {
        V3TkbsOrganizationParentCodeRequest commRequest = new V3TkbsOrganizationParentCodeRequest();
        commRequest.setParentCode("991000");
        commRequest.setOrgCode("1951582");
        LKLCommonResponse lklCommonResponse = LKLPost.httpPost(commRequest);
        log.info("response: \r\n{}", lklCommonResponse.toString());
    }

}

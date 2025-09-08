package cn.cc.lkl.merchantinfo;

import cn.cc.lkl.dto.LKLCommonResponse;
import cn.cc.lkl.dto.merchantinfo.V3TkbsOpenMerchantInfoRequest;
import cn.cc.lkl.sdk.config.LKLConfig;
import cn.cc.lkl.util.LKLPost;
import com.lkl.laop.sdk.exception.SDKException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

@Slf4j
public class V3TkbsOpenMerchantInfoRequestTest {

    @Before
    public void pre() {
        try {
            LKLConfig.initSDK();
        } catch (SDKException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testOpenMerchantInfo() {
        V3TkbsOpenMerchantInfoRequest request = new V3TkbsOpenMerchantInfoRequest();
        request.setOrgCode("200028");
//        request.setMerchantNo("822");
        request.setCustomerNo("100143716");
        LKLCommonResponse response = LKLPost.httpPostWithSm4(request, true, true);
        log.info("response:{}", response);
        log.info("response.isSuccess:{}", response.isSuccess());
        log.info("response.getRespData:{}", response.getRespData());
    }

}

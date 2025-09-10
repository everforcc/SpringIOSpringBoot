package cn.cc.lkl.authrealname;

import cn.cc.lkl.LKLBaseTest;
import cn.cc.lkl.util.LoadFileUtil;
import com.lkl.laop.sdk.LKLSDK;
import com.lkl.laop.sdk.exception.SDKException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * 支付宝实名认证
 */
@Slf4j
public class V2MmsOpenApiRealNameSaveAlipayContactInfoRequestTest extends LKLBaseTest {

    /**
     * 支付宝实名认证
     */
    @Test
    public void testSDKAliReq() throws SDKException {
        String json = LoadFileUtil.loadJsonFromResource("认证/ali_auth_cc.json");
        String response = LKLSDK.httpPost("https://test.wsmsd.cn/sit/api/v2/mms/openApi/realName/saveAlipayContactInfo", json);
        log.info("response: \r\n{}", response);
    }

    /**
     * 实名认证结果查询
     */
    @Test
    public void testSDKAliStatus() throws SDKException {
        String json = LoadFileUtil.loadJsonFromResource("认证/ali_result_cc.json");
        log.info("json: \r\n{}", json);
        String response = LKLSDK.httpPost("https://test.wsmsd.cn/sit/api/v2/mms/sme/mrchAuthStateQuery", json);
        log.info("response: \r\n{}", response);
    }

}

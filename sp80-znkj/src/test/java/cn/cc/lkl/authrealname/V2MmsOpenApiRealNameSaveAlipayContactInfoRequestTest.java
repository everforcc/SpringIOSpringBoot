package cn.cc.lkl.authrealname;

import cn.cc.config.JsonUtil;
import cn.cc.lkl.LKLBaseTest;
import cn.cc.lkl.dto.LKLCommonResponse;
import cn.cc.lkl.dto.LKLCommonResponseV2;
import cn.cc.lkl.dto.authrealname.V2MmsOpenApiRealNameQueryRequest;
import cn.cc.lkl.dto.authrealname.V2MmsOpenApiRealNameSaveAlipayContactInfoRequest;
import cn.cc.lkl.sdk.config.LKLConfig;
import cn.cc.lkl.util.LKLPost;
import cn.cc.lkl.util.LoadFileUtil;
import com.alibaba.fastjson.JSONObject;
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
    public void testSDKAliReq() throws Exception {
        String json = LoadFileUtil.loadJsonFromResource("认证/ali_auth_cc_req.json");
        String reqData = JSONObject.parseObject(json).getString("reqData");

        V2MmsOpenApiRealNameSaveAlipayContactInfoRequest request = JSONObject.parseObject(reqData, V2MmsOpenApiRealNameSaveAlipayContactInfoRequest.class);
        request.setOrderNo("20250916113212587854");
        request.setOrgCode(LKLConfig.orgCode);
        request.setContactType("LEGAL");
//        log.info("request: \r\n{}", JsonUtil.toJson(request));
        log.info("request: \r\n{}", request.toBody());

        LKLCommonResponseV2 response = LKLPost.httpPost(request);
        log.info("response: \r\n{}", JsonUtil.toJson(response));
    }

    /**
     * 实名认证状态查询
     */
    @Test
    public void testSDKAliStatus() throws SDKException {
        String json = LoadFileUtil.loadJsonFromResource("认证/ali_result_cc.json");

        log.info("json: \r\n{}", json);
        String response = LKLSDK.httpPost("https://test.wsmsd.cn/sit/api/v2/mms/sme/mrchAuthStateQuery", json);
        log.info("response: \r\n{}", response);
    }

    @Test
    public void testAliAuthMsg() throws Exception {
        V2MmsOpenApiRealNameQueryRequest request = new V2MmsOpenApiRealNameQueryRequest();
        request.setOrderNo("20250916113212587856");
        request.setOrgCode(LKLConfig.orgCode);
        request.setMerInnerNo("4002021012659676355");
        request.setSubMchId("808107443");

        log.info("request: \r\n{}", request.toBody());
        LKLCommonResponseV2 response = LKLPost.httpPost(request);
        log.info("response: \r\n{}", response);
        log.info("response: \r\n{}", JsonUtil.toJson(response));
    }

}

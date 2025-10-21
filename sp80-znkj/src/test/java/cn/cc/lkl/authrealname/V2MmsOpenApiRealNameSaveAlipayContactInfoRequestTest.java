package cn.cc.lkl.authrealname;

import cn.cc.config.JsonUtil;
import cn.cc.lkl.LKLBaseProdTest;
import cn.cc.lkl.dto.LKLCommonResponseV2;
import cn.cc.lkl.dto.authrealname.V2MmsOpenApiRealNameQueryRequest;
import cn.cc.lkl.dto.authrealname.V2MmsOpenApiRealNameSaveAlipayContactInfoRequest;
import cn.cc.lkl.sdk.config.LKLConfig;
import cn.cc.lkl.sdk.config.LKLConfigProd;
import cn.cc.lkl.util.DateUtils;
import cn.cc.lkl.util.LKLPost;
import cn.cc.lkl.util.LoadFileUtil;
import cn.cc.lkl.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.lkl.laop.sdk.LKLSDK;
import com.lkl.laop.sdk.exception.SDKException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * 支付宝实名认证
 * LKLBaseTest
 * LKLBaseProdTest
 */
@Slf4j
public class V2MmsOpenApiRealNameSaveAlipayContactInfoRequestTest extends LKLBaseProdTest {

    /**
     * 支付宝实名认证
     */
    @Test
    public void testSDKAliReq() throws Exception {
        String json = LoadFileUtil.loadJsonFromResource("认证/ali_auth_zqw_req.json");
        String reqData = JSONObject.parseObject(json).getString("reqData");

        V2MmsOpenApiRealNameSaveAlipayContactInfoRequest request = JSONObject.parseObject(reqData, V2MmsOpenApiRealNameSaveAlipayContactInfoRequest.class);

        request.setOrderNo(DateUtils.getTimeStampAndRandom());
        request.setMerInnerNo(LKLConfigProd.merInnerNo);
        request.setOrgCode(LKLConfigProd.orgCode);
        request.setContactType("LEGAL");

//        log.info("request: \r\n{}", JsonUtil.toJson(request));
        log.info("request: \r\n{}", request.toBody());

        LKLCommonResponseV2 response = LKLPost.httpPost(request);
        log.info("response: \r\n{}", JsonUtil.toJson(response));
    }

    /**
     * 实名认证状态查询
     * https://o.lakala.com/#/home/document/detail?id=488
     */
    @Test
    public void testSDKAliStatus() throws SDKException {
        String json = LoadFileUtil.loadJsonFromResource("认证/ali_result_zqw.json");
        JSONObject jsonObject = JSONObject.parseObject(json);
        jsonObject.put("timestamp", DateUtils.getTimeStamp());
        jsonObject.put("reqId", StringUtils.getUUID());
        json = jsonObject.toJSONString();
        log.info("json: \r\n{}", json);
        String response = LKLSDK.httpPost("https://s2.lakala.com/api/v2/mms/sme/mrchAuthStateQuery", json);
        log.info("response: \r\n{}", response);
    }

    @Test
    public void testAliAuthMsg() throws Exception {
        V2MmsOpenApiRealNameQueryRequest request = new V2MmsOpenApiRealNameQueryRequest();
        request.setMerInnerNo(LKLConfigProd.merInnerNo);

//        request.setSubMchId(LKLConfigProd.subMchId);
        request.setSubMchId("2088080069827109");
        request.setRealNameType("ZFBZF");

//        request.setOrderNo("2025102111303913582023");
        request.setOrderNo(DateUtils.getTimeStampAndRandom());
        request.setOrgCode(LKLConfigProd.orgCode);

        log.info("request: \r\n{}", request.toBody());

        LKLCommonResponseV2 response = LKLPost.httpPost(request);
        log.info("response: \r\n{}", JsonUtil.toJson(response));
    }

}

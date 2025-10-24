package cn.cc.lkl.authrealname;

import cn.cc.config.JsonUtil;
import cn.cc.lkl.LKLBaseProdTest;
import cn.cc.lkl.dto.LKLCommonResponseV2;
import cn.cc.lkl.dto.authrealname.V2MmsOpenApiRealNameSaveContactInfoRequest;
import cn.cc.lkl.dto.authrealname.V2MmsOpenApiWechatRealNameQueryRequest;
import cn.cc.lkl.dto.authrealname.V2MmsOpenApiWechatRealNameQueryResponse;
import cn.cc.lkl.sdk.config.LKLConfigProd;
import cn.cc.lkl.util.DateUtils;
import cn.cc.lkl.util.LKLPost;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * 微信实名认证
 *  结果查询会返回二维码
 *
 */
@Slf4j
public class V2MmsOpenApiRealNameSaveContactInfoRequestTest extends LKLBaseProdTest {

    /**
     * 1. 微信实名认证
     */
    @Test
    public void testWxReq() {
//        String json = LoadFileUtil.loadJsonFromResource("认证/ali_auth_zqw_req.json");
//        String reqData = JSONObject.parseObject(json).getString("reqData");
        V2MmsOpenApiRealNameSaveContactInfoRequest request = new V2MmsOpenApiRealNameSaveContactInfoRequest();
//        V2MmsOpenApiRealNameSaveContactInfoRequest request = JSONObject.parseObject(reqData, V2MmsOpenApiRealNameSaveContactInfoRequest.class);

        request.setOrderNo(DateUtils.getTimeStampAndRandom());
        request.setOrgCode(LKLConfigProd.orgCode);
        request.setMerInnerNo(LKLConfigProd.merInnerNo);
        request.setContactType("LEGAL");
        request.setContactPeriodBegin("2020-11-30");
        request.setContactPeriodEnd("2040-11-30");

        request.setName("庄乾威");
        request.setContactIdDocType("IDENTIFICATION_TYPE_IDCARD");
        request.setIdCardNumber("412326199011082195");
        request.setMobile("13022110823");

        log.info("request: \r\n{}", request.toBody());

        LKLCommonResponseV2 lklCommonResponse = LKLPost.httpPost(request);
        log.info("response: \r\n{}", JSONObject.toJSONString(lklCommonResponse));
    }

    /**
     * 2. 微信实名认证修改
     */
    @Test
    public void testWxUpdateReq() {
        // 微信实名状态修改
        // V2MmsOpenApiWechatRealNameModifyCommitRequest

    }

    /**
     * 4. 微信实名认证结果查询
     */
    @Test
    public void testWxResult() {
        V2MmsOpenApiWechatRealNameQueryRequest request = new V2MmsOpenApiWechatRealNameQueryRequest();
        request.setOrderNo(DateUtils.getTimeStampAndRandom());
        request.setMerInnerNo("4002025102484657818");
        request.setOrgCode(LKLConfigProd.orgCode);

        request.setSubMchId("821100958");

        log.info("request: \r\n{}", request.toBody());

        LKLCommonResponseV2 lklCommonResponse = LKLPost.httpPost(request);

        log.info("response: \r\n{}", JSONObject.toJSONString(lklCommonResponse));

        if (lklCommonResponse.resultSuccess()) {
            // 使用已注释掉的方式，这是最合适的解决方案
            V2MmsOpenApiWechatRealNameQueryResponse response = JsonUtil.fromJson(lklCommonResponse.getRespData(), V2MmsOpenApiWechatRealNameQueryResponse.class);
//            V2MmsOpenApiWechatRealNameQueryResponse response = JsonUtil.fromJson(lklCommonResponse.getRespData(), V2MmsOpenApiWechatRealNameQueryResponse.class);
            log.info("response: \r\n{}", JsonUtil.toJson(response));
        }

    }

}

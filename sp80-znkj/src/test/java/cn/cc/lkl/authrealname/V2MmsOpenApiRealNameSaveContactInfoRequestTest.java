package cn.cc.lkl.authrealname;

import cn.cc.lkl.LKLBaseTest;
import cn.cc.lkl.dto.LKLCommonResponseV2;
import cn.cc.lkl.dto.authrealname.V2MmsOpenApiRealNameSaveContactInfoRequest;
import cn.cc.lkl.dto.authrealname.V2MmsOpenApiWechatRealNameQueryRequest;
import cn.cc.lkl.dto.authrealname.V2MmsOpenApiWechatRealNameQueryResponse;
import cn.cc.lkl.sdk.config.LKLConfig;
import cn.cc.lkl.util.DateUtils;
import cn.cc.lkl.util.LKLPost;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * 微信实名认证
 */
@Slf4j
public class V2MmsOpenApiRealNameSaveContactInfoRequestTest extends LKLBaseTest {

    /**
     * 微信实名认证
     */
    @Test
    public void testWxReq(){
        V2MmsOpenApiRealNameSaveContactInfoRequest request = new V2MmsOpenApiRealNameSaveContactInfoRequest();
        request.setOrderNo(DateUtils.getTimeStampAndRandom());
        request.setOrgCode(LKLConfig.orgCode);
        request.setMerInnerNo(LKLConfig.merInnerNo);
        request.setContactType("LEGAL");
        request.setName("郭凯龙");
        request.setContactIdDocType("IDENTIFICATION_TYPE_IDCARD");
        request.setIdCardNumber("41018219960126531X");
        request.setContactPeriodBegin("2017-06-19");
        request.setContactPeriodEnd("2027-06-19");
        request.setMobile("15738573601");
        log.info("request: \r\n{}", request.toBody());
        LKLCommonResponseV2 lklCommonResponse = LKLPost.httpPost( request);
        log.info("response: \r\n{}", lklCommonResponse.toString());
        log.info("response: \r\n{}", JSONObject.toJSONString(lklCommonResponse));
    }

    /**
     * 微信实名认证结果查询
     */
    @Test
    public void testWxResult(){
        V2MmsOpenApiWechatRealNameQueryRequest request = new V2MmsOpenApiWechatRealNameQueryRequest();
        request.setOrderNo(DateUtils.getTimeStampAndRandom());
        request.setOrgCode(LKLConfig.orgCode);
        request.setMerInnerNo(LKLConfig.merInnerNo);
        request.setSubMchId("808107443");
        log.info("request: \r\n{}", request.toBody());
        LKLCommonResponseV2 lklCommonResponse = LKLPost.httpPost( request);
        log.info("response: \r\n{}", lklCommonResponse.toString());
        if(lklCommonResponse.resultSuccess()){
            String respData = lklCommonResponse.getRespData().toString();
            V2MmsOpenApiWechatRealNameQueryResponse response = JSONObject.parseObject(respData, V2MmsOpenApiWechatRealNameQueryResponse.class);
            log.info("response: \r\n{}", response.toString());
        }
    }

}

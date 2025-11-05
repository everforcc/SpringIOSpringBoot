package cn.cc.lkl.authrealname;

import cn.cc.config.JsonUtil;
import cn.cc.lkl.LKLBaseProdTest;
import cn.cc.lkl.dto.LKLCommonResponseV2;
import cn.cc.lkl.dto.authrealname.V2MmsOpenApiWechatRealNameQueryResponse;
import cn.cc.lkl.dto.authrealname.V2MmsSmeMrchAuthStateQueryRequest;
import cn.cc.lkl.dto.authrealname.V2MmsSmeMrchAuthStateQueryResponse;
import cn.cc.lkl.util.LKLPost;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * https://o.lakala.com/#/home/document/detail?id=488
 */
@Slf4j
public class V2MmsSmeMrchAuthStateQueryRequestTest extends LKLBaseProdTest {

    @Test
    public void test() {
        // /v2/mms/sme/mrchAuthStateQuery
        V2MmsSmeMrchAuthStateQueryRequest request = new V2MmsSmeMrchAuthStateQueryRequest();
        // ALIPAY，WECHAT
        request.setTradeMode("WECHAT");
        request.setSubMerchantId("823720682");
        request.setMerchantNo("822491059431B12");

        log.info("request: \r\n{}", request.toBody());

        LKLCommonResponseV2 lklCommonResponse = LKLPost.httpPost(request);
        log.info("response: \r\n{}", JSONObject.toJSONString(lklCommonResponse));
        // {"respData":{"subMerchantId":"2088080552126872","checkResult":"AUTHORIZED"},"retCode":"000000","retMsg":"成功"}
        // {"respData":{"subMerchantId":"823720682","checkResult":"AUTHORIZE_STATE_AUTHORIZED"},"retCode":"000000","retMsg":"成功"}

        V2MmsSmeMrchAuthStateQueryResponse v2MmsSmeMrchAuthStateQueryResponse = JsonUtil.fromJson(lklCommonResponse.getRespData(), V2MmsSmeMrchAuthStateQueryResponse.class);
        log.info("v2MmsSmeMrchAuthStateQueryResponse: {}", JsonUtil.toJson(v2MmsSmeMrchAuthStateQueryResponse));
    }


}

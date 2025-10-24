package cn.cc.lkl.merchant;

import cn.cc.config.JsonUtil;
import cn.cc.lkl.LKLBaseProdTest;
import cn.cc.lkl.LKLBaseTest;
import cn.cc.lkl.dto.LKLCommonResponseV2;
import cn.cc.lkl.dto.merchantquery.V2MmsOpenApiQuerySubMerInfoRequest;
import cn.cc.lkl.dto.merchantquery.V2MmsOpenApiQuerySubMerInfoResponse;
import cn.cc.lkl.sdk.config.LKLConfig;
import cn.cc.lkl.sdk.config.LKLConfigProd;
import cn.cc.lkl.util.DateUtils;
import cn.cc.lkl.util.LKLPost;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * 商户报备结果查询
 * https://o.lakala.com/#/home/document/detail?id=326
 */
@Slf4j
public class V2MmsOpenApiQuerySubMerInfoRequestTest extends LKLBaseProdTest {

    @Test
    public void test(){
        V2MmsOpenApiQuerySubMerInfoRequest request = new V2MmsOpenApiQuerySubMerInfoRequest();
        request.setOrderNo(DateUtils.getTimeStampAndRandom());
        request.setOrgCode(LKLConfigProd.orgCode);
//        request.setMerInnerNo(LKLConfigProd.merInnerNo);
        request.setMerCupNo("8224910581402AM");
        LKLCommonResponseV2 lklCommonResponse = LKLPost.httpPost(request);
        log.info("response: \r\n{}", lklCommonResponse.toString());
        if (!lklCommonResponse.resultSuccess()) {
            return;
        }

        V2MmsOpenApiQuerySubMerInfoResponse response = JsonUtil.fromJson(lklCommonResponse.getRespData(), V2MmsOpenApiQuerySubMerInfoResponse.class);
        log.info("response: \r\n{}", JsonUtil.toJson(response));
    }

}

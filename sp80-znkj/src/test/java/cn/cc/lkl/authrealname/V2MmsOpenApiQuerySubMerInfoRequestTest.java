package cn.cc.lkl.authrealname;

import cn.cc.lkl.LKLBaseTest;
import cn.cc.lkl.dto.LKLCommonResponseV2;
import cn.cc.lkl.dto.authrealname.V2MmsOpenApiQuerySubMerInfoRequest;
import cn.cc.lkl.sdk.config.LKLConfig;
import cn.cc.lkl.util.DateUtils;
import cn.cc.lkl.util.LKLPost;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * 商户报备结果查询
 */
@Slf4j
public class V2MmsOpenApiQuerySubMerInfoRequestTest extends LKLBaseTest {

    @Test
    public void test(){
        V2MmsOpenApiQuerySubMerInfoRequest request = new V2MmsOpenApiQuerySubMerInfoRequest();
        request.setOrderNo(DateUtils.getTimeStampAndRandom());
        request.setOrgCode(LKLConfig.orgCode);
        LKLCommonResponseV2 lklCommonResponse = LKLPost.httpPost(request);
        log.info("response: \r\n{}", lklCommonResponse.toString());
    }

}

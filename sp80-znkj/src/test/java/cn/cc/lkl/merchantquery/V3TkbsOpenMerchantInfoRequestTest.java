package cn.cc.lkl.merchantquery;

import cn.cc.config.JsonUtil;
import cn.cc.lkl.LKLBaseTest;
import cn.cc.lkl.dto.LKLCommonResponse;
import cn.cc.lkl.dto.merchantquery.V3TkbsOpenMerchantInfoRequest;
import cn.cc.lkl.util.LKLPost;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * 获取商户信息
 * https://o.lakala.com/p/#/document/detail?id=1089
 */
@Slf4j
public class V3TkbsOpenMerchantInfoRequestTest extends LKLBaseTest {

    @Test
    public void testOpenMerchantInfo() throws Exception {
        V3TkbsOpenMerchantInfoRequest request = new V3TkbsOpenMerchantInfoRequest();
        request.setOrgCode("1951582");
//        request.setMerchantNo("100143767");
        request.setCustomerNo("100143785");
        log.info("获取商户信息请求: {}", JsonUtil.toJson(request));
        LKLCommonResponse response = LKLPost.httpPost(request, true, true);
        log.info("获取商户信息结果: {}", JsonUtil.toJson(response));
        log.info("response.isSuccess:{}", response.resultSuccess());
        log.info("response.getRespData:{}", response.getRespData());
    }

}

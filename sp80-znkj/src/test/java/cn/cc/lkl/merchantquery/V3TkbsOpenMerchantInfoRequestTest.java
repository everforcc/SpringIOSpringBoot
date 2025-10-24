package cn.cc.lkl.merchantquery;

import cn.cc.config.JsonUtil;
import cn.cc.lkl.LKLBaseProdTest;
import cn.cc.lkl.dto.LKLCommonResponse;
import cn.cc.lkl.dto.merchantquery.V3TkbsOpenMerchantInfoRequest;
import cn.cc.lkl.dto.merchantquery.V3TkbsOpenMerchantInfoResponse;
import cn.cc.lkl.sdk.config.LKLConfigProd;
import cn.cc.lkl.util.LKLPost;
import com.lkl.laop.sdk.LKLSDK;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * 获取商户信息
 * https://o.lakala.com/p/#/document/detail?id=1089
 */
@Slf4j
public class V3TkbsOpenMerchantInfoRequestTest extends LKLBaseProdTest {

    @Test
    public void testOpenMerchantInfo() throws Exception {
        V3TkbsOpenMerchantInfoRequest request = new V3TkbsOpenMerchantInfoRequest();
        request.setOrgCode(LKLConfigProd.orgCode);
//        request.setMerchantNo("100143767");
        request.setCustomerNo("144173725");
        log.info("获取商户信息请求: {}", JsonUtil.toJson(request));
        LKLCommonResponse response = LKLPost.httpPost(request, true, true);
        log.info("获取商户信息结果: {}", JsonUtil.toJson(response));
        log.info("response.isSuccess:{}", response.resultSuccess());
        log.info("response.getRespData:{}", response.getRespData());

        V3TkbsOpenMerchantInfoResponse responseData = JsonUtil.fromJson(response.getRespData(), V3TkbsOpenMerchantInfoResponse.class);
        log.info("responseData.getRespData:{}", JsonUtil.toJson(responseData));
        V3TkbsOpenMerchantInfoResponse.Customer customer = responseData.getCustomer();
        log.info("term_no: {}, merchant_no:{}", customer.getTermNo(), customer.getMerchantNo());
    }

    @SneakyThrows
    @Test
    public void a(){
        String s = "2/4EiJJ4mMrMXkKQsr9md7X9e9NTEY3ie8wlFph2EZE=";
        String response = LKLSDK.sm4Decrypt(s, LKLConfigProd.appId);
        System.out.println(response);
    }

}

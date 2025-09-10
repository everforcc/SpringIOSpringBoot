package cn.cc.lkl.merchantquery;

import cn.cc.config.JsonUtil;
import cn.cc.lkl.LKLBaseTest;
import cn.cc.lkl.dto.LKLCommonResponse;
import cn.cc.lkl.dto.merchantquery.V3tkbsOpenMerchantSubmerRequest;
import cn.cc.lkl.sdk.config.LKLConfig;
import cn.cc.lkl.util.LKLPost;
import cn.cc.lkl.util.LoadFileUtil;
import com.alibaba.fastjson.JSONObject;
import com.lkl.laop.sdk.LKLSDK;
import com.lkl.laop.sdk.exception.SDKException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * 新子商户查询
 */
@Slf4j
public class V3tkbsOpenMerchantSubmerRequestTest extends LKLBaseTest {

    @Test
    public void testSDK() {
        String json = LoadFileUtil.loadJsonFromResource("商户查询/新子商户查询_cc.json");
        try {
            json = LKLSDK.sm4Encrypt(json, LKLConfig.appId);
            String response = LKLSDK.httpPost("https://test.wsmsd.cn/sit/api/v3/tkbs/open_merchant_submer", json);
            response = LKLSDK.sm4Decrypt(response, LKLConfig.appId);
            log.info("response: \r\n{}", response);
            // {"code":"000000","msg":"SUCCESS","resp_data":{"wx_list":[{"register_type":"微信","sub_merchant_no":"808107443","channel_id":"409391280","register_channel_name":"新网联"}]}}
        } catch (SDKException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUser() throws Exception {
        V3tkbsOpenMerchantSubmerRequest request = new V3tkbsOpenMerchantSubmerRequest();
        request.setMerchantNo("8224910581208RL");
        request.setOrgCode("1951582");
        LKLCommonResponse lklCommonResponse = LKLPost.httpPost(request, true, true);
        log.info("response: \r\n{}", lklCommonResponse.toString());
        log.info("response: \r\n{}", JsonUtil.toJson(lklCommonResponse));
        // {"code":"000000","msg":"SUCCESS","resp_data":{"wx_list":[{"register_type":"微信","sub_merchant_no":"808107443","channel_id":"409391280","register_channel_name":"新网联"}]}}
    }


}

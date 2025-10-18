package cn.cc.lkl.split;

import cn.cc.config.JsonUtil;
import cn.cc.lkl.LKLBaseTest;
import cn.cc.lkl.dto.LKLCommonResponseV2;
import cn.cc.lkl.dto.split.V2MmsOpenApiLedgerApplyLedgerMerCallbackRequest;
import cn.cc.lkl.util.LKLPost;
import cn.cc.lkl.util.LoadFileUtil;
import com.lkl.laop.sdk.request.V2MmsOpenApiLedgerApplyLedgerReceiverRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * 账户分账接收方申请
 */
@Slf4j
public class V2MmsOpenApiLedgerApplyLedgerReceiverRequestTest extends LKLBaseTest {

    @Test
    public void test() throws Exception {
        V2MmsOpenApiLedgerApplyLedgerReceiverRequest request = new V2MmsOpenApiLedgerApplyLedgerReceiverRequest();
        String json = LoadFileUtil.loadJsonFromResource("分账申请/分账接收方_demo_req.json");
        request = JsonUtil.fromJson(json, V2MmsOpenApiLedgerApplyLedgerReceiverRequest.class);
        log.info("账户分账接收方申请:{}", request);
        LKLCommonResponseV2 lklCommonResponse = LKLPost.httpPostV2(request);
        log.info("账户分账接收方申请结果:{}", lklCommonResponse);
    }

}

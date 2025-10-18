package cn.cc.lkl.split;

import cn.cc.config.JsonUtil;
import cn.cc.lkl.LKLBaseTest;
import cn.cc.lkl.dto.LKLCommonResponseV2;
import cn.cc.lkl.util.LKLPost;
import cn.cc.lkl.util.LoadFileUtil;
import com.alibaba.fastjson.JSONObject;
import com.lkl.laop.sdk.request.V2MmsOpenApiLedgerApplyBindRequest;
import com.lkl.laop.sdk.request.V2MmsOpenApiLedgerApplyLedgerMerRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * 分账关系绑定申请
 * https://o.lakala.com/#/home/document/detail?id=386
 */
@Slf4j
public class V2MmsOpenApiLedgerApplyBindRequestTest extends LKLBaseTest {

    @Test
    public void test() throws Exception {
        V2MmsOpenApiLedgerApplyBindRequest request = new V2MmsOpenApiLedgerApplyBindRequest();
        String json = LoadFileUtil.loadJsonFromResource("分账申请/分账接收方绑定_demo_req.json");
        String reqData = JSONObject.parseObject(json).getString("reqData");
        request = JsonUtil.fromJson(reqData, V2MmsOpenApiLedgerApplyBindRequest.class);
        log.info("分账关系绑定申请:{}", request);
        LKLCommonResponseV2 lklCommonResponse = LKLPost.httpPostV2(request);
        log.info("分账关系绑定申请 结果:{}", lklCommonResponse);
        if (lklCommonResponse.resultSuccess()) {
            log.info("分账关系绑定申请 成功");
        } else {
            log.info("分账关系绑定申请 失败: {}", lklCommonResponse.getRetMsg());
        }
    }

}

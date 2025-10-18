package cn.cc.lkl.split;

import cn.cc.config.JsonUtil;
import cn.cc.lkl.LKLBaseTest;
import cn.cc.lkl.dto.LKLCommonResponseV2;
import cn.cc.lkl.dto.split.V2MmsOpenApiLedgerApplyLedgerMerCallbackRequest;
import cn.cc.lkl.util.LKLPost;
import cn.cc.lkl.util.LoadFileUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.lkl.laop.sdk.request.V2MmsOpenApiLedgerApplyLedgerMerRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * 分账开通
 */
@Slf4j
public class V2MmsOpenApiLedgerApplyLedgerMerRequestTest extends LKLBaseTest {

    /**
     * 分账开通申请
     *
     * @throws Exception
     */
    @Test
    public void test() throws Exception {
        V2MmsOpenApiLedgerApplyLedgerMerRequest request = new V2MmsOpenApiLedgerApplyLedgerMerRequest();
        String json = LoadFileUtil.loadJsonFromResource("分账申请/分账申请_demo_req.json");
        String reqData = JSONObject.parseObject(json).getString("reqData");
        request = JsonUtil.fromJson(reqData, V2MmsOpenApiLedgerApplyLedgerMerRequest.class);
        log.info("分账申请:{}", request);
        LKLCommonResponseV2 lklCommonResponse = LKLPost.httpPostV2(request);
        log.info("分账开通结果:{}", lklCommonResponse);
        if (lklCommonResponse.resultSuccess()) {
            log.info("分账开通成功");
        } else {
            log.info("分账开通失败: {}", lklCommonResponse.getRetMsg());
        }
    }

    /**
     * 分账开通回调
     *
     * @throws Exception
     */
    @Test
    public void testCallBack() throws Exception {
        String json = LoadFileUtil.loadJsonFromResource("分账申请/分账申请_demo_callback.json");
        V2MmsOpenApiLedgerApplyLedgerMerCallbackRequest request = JsonUtil.fromJson(json, V2MmsOpenApiLedgerApplyLedgerMerCallbackRequest.class);
        String callbackJson = JSONObject.toJSONString(request, SerializerFeature.PrettyFormat);
        log.info("分账申请回调:{}", callbackJson);

    }

}

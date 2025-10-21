package cn.cc.lkl.ordersplit;

import cn.cc.config.JsonUtil;
import cn.cc.lkl.LKLBaseTest;
import cn.cc.lkl.dto.LKLCommonResponse;
import cn.cc.lkl.dto.ordersplit.V3SacsSeparateResponse;
import cn.cc.lkl.util.LKLPost;
import cn.cc.lkl.util.LoadFileUtil;
import com.alibaba.fastjson.JSONObject;
import com.lkl.laop.sdk.request.V3SacsSeparateRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * 订单分账
 * https://o.lakala.com/#/home/document/detail?id=389
 */
@Slf4j
public class V3SacsSeparateRequestTest extends LKLBaseTest {

    @Test
    public void test() throws Exception {
        V3SacsSeparateRequest request = new V3SacsSeparateRequest();
        String json = LoadFileUtil.loadJsonFromResource("分账订单/订单分账申请_demo_req.json");
        String reqData = JSONObject.parseObject(json).getString("req_data");
        request = JsonUtil.fromJson(reqData, V3SacsSeparateRequest.class);
        log.info("分账订单:{}", request);
        LKLCommonResponse lklCommonResponse = LKLPost.httpPost(request);
        log.info("分账订单结果:{}", lklCommonResponse);
        if (lklCommonResponse.resultSuccess()) {
            log.info("分账订单成功");
            V3SacsSeparateResponse response = JsonUtil.fromJson(lklCommonResponse.getRespData(), V3SacsSeparateResponse.class);
            log.info("分账订单结果:{}", response);
        } else {
            log.info("分账订单失败: {}", lklCommonResponse.getMsg());
        }
    }

}

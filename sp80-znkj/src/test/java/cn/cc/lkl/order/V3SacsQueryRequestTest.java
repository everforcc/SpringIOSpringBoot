package cn.cc.lkl.order;

import cn.cc.config.JsonUtil;
import cn.cc.lkl.LKLBaseTest;
import cn.cc.lkl.dto.LKLCommonResponse;
import cn.cc.lkl.dto.order.V3SacsQueryResponse;
import cn.cc.lkl.util.LKLPost;
import cn.cc.lkl.util.LoadFileUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.lkl.laop.sdk.request.V3SacsQueryRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * 订单分账结果查询
 * https://o.lakala.com/#/home/document/detail?id=392
 */
@Slf4j
public class V3SacsQueryRequestTest extends LKLBaseTest {

    @Test
    public void test() throws Exception {
        V3SacsQueryRequest request = new V3SacsQueryRequest();
        String json = LoadFileUtil.loadJsonFromResource("分账订单/订单分账结果查询_demo_req.json");
        String reqData = JSONObject.parseObject(json).getString("req_data");
        request = JsonUtil.fromJson(reqData, V3SacsQueryRequest.class);
        log.info("分账订单:{}", JSONObject.toJSONString(request, SerializerFeature.PrettyFormat));

        LKLCommonResponse lklCommonResponse = LKLPost.httpPost(request);
        log.info("分账订单结果:{}", lklCommonResponse);
        if (lklCommonResponse.resultSuccess()) {
            log.info("分账订单成功");
            log.info("分账订单结果:{}", lklCommonResponse.getRespData());
            V3SacsQueryResponse response = JsonUtil.fromJson(lklCommonResponse.getRespData(), V3SacsQueryResponse.class);
            log.info("分账订单结果:{}", response);
        } else {
            log.info("分账订单失败: {}", lklCommonResponse.getMsg());
        }

    }

    /**
     * 测试分账结果
     *
     * @throws Exception
     */
    @Test
    public void test2() throws Exception {
        String json = LoadFileUtil.loadJsonFromResource("分账订单/订单分账结果查询_demo_res.json");
        String reqData = JSONObject.parseObject(json).getString("resp_data");
        V3SacsQueryResponse response = JsonUtil.fromJson(reqData, V3SacsQueryResponse.class);
        log.info("分账订单结果:{}", JSONObject.toJSONString(response, SerializerFeature.PrettyFormat));
    }


}

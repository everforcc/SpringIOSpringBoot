package cn.cc.lkl.ordersplit;

import cn.cc.config.JsonUtil;
import cn.cc.lkl.LKLBaseTest;
import cn.cc.lkl.dto.LKLCommonResponse;
import cn.cc.lkl.dto.ordersplit.V3SacsQueryResponse;
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
    public void testResultQuestByJson() throws Exception {
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

    // 拉卡拉 回调分账 响应结果：
    // {"separate_no":"20251104770188018246441600","out_separate_no":"1183136909157867520","cmd_type":"SEPARATE","log_no":"66202212034710","log_date":"20251104","cal_type":"0","separate_type":"1","separate_date":"20251104","finish_date":"20251104","total_amt":"100","status":"SUCCESS","final_status":"SUCCESS","actual_separate_amt":"100","total_fee_amt":"0","detail_datas":[{"recv_merchant_no":"8224910737200MK","recv_no":"8224910737200MK","amt":"99"},{"recv_merchant_no":"","recv_no":"SR2024000165390","amt":"1"}]}
    @Test
    public void testResultQuest(){
        V3SacsQueryRequest request = new V3SacsQueryRequest();
        request.setMerchantNo("8224910737200MK");
        request.setSeparateNo("20251104770188018246441600");
//        request.setOutSeparateNo("1183136909157867520");
        log.info("request: \r\n{}", request.toBody());
        LKLCommonResponse response = null;
//            response = LKLSDK.httpPost(request);
        response = LKLPost.httpPost(request);
        log.info("response: \r\n{}", response);
    }

    /**
     * 测试分账结果
     *
     * @throws Exception
     */
    @Test
    public void testResultJson() throws Exception {
        String json = LoadFileUtil.loadJsonFromResource("分账订单/订单分账结果查询_demo_res.json");
        String reqData = JSONObject.parseObject(json).getString("resp_data");
        V3SacsQueryResponse response = JsonUtil.fromJson(reqData, V3SacsQueryResponse.class);
        log.info("分账订单结果:\r\n{}", JSONObject.toJSONString(response, SerializerFeature.PrettyFormat));
    }


}

package cn.cc.lkl.authsplit;

import cn.cc.lkl.LKLBaseProdTest;
import cn.cc.lkl.dto.LKLCommonResponseV2;
import cn.cc.lkl.sdk.config.LKLConfigProd;
import cn.cc.lkl.util.DateUtils;
import cn.cc.lkl.util.LKLPost;
import com.lkl.laop.sdk.request.V2MmsOpenApiLedgerApplyBindRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * 3. 分账关系绑定申请
 * https://o.lakala.com/#/home/document/detail?id=386
 */
@Slf4j
public class V2MmsOpenApiLedgerApplyBindRequestTest extends LKLBaseProdTest {

    @Test
    public void test() throws Exception {
        V2MmsOpenApiLedgerApplyBindRequest request = new V2MmsOpenApiLedgerApplyBindRequest();
//        String json = LoadFileUtil.loadJsonFromResource("分账申请/分账接收方绑定_demo_req.json");
//        String reqData = JSONObject.parseObject(json).getString("reqData");
//        request = JsonUtil.fromJson(reqData, V2MmsOpenApiLedgerApplyBindRequest.class);
        request.setVersion("1.0");
        request.setOrderNo(DateUtils.getTimeStampAndRandom());
        request.setOrgCode(LKLConfigProd.orgCode);
        request.setMerInnerNo("4002025102584686558");
        // todo
        request.setReceiverNo("todo");
        request.setEntrustFileName("");
        request.setEntrustFilePath("");
        request.setRetUrl("");


        log.info("分账关系绑定申请:{}", request);
        LKLCommonResponseV2 lklCommonResponse = LKLPost.httpPost(request);
        log.info("分账关系绑定申请 结果:{}", lklCommonResponse);
        if (lklCommonResponse.resultSuccess()) {
            log.info("分账关系绑定申请 成功");
        } else {
            log.info("分账关系绑定申请 失败: {}", lklCommonResponse.getRetMsg());
        }
    }

}

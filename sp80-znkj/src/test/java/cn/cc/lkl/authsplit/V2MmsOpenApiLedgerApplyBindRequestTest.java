package cn.cc.lkl.authsplit;

import cn.cc.config.JsonUtil;
import cn.cc.lkl.LKLBaseProdTest;
import cn.cc.lkl.dto.LKLCommonResponseV2;
import cn.cc.lkl.dto.authsplitsplit.V2MmsOpenApiLedgerApplyBindCallback;
import cn.cc.lkl.sdk.config.LKLConfigProd;
import cn.cc.lkl.util.DateUtils;
import cn.cc.lkl.util.LKLPost;
import com.lkl.laop.sdk.request.V2MmsOpenApiLedgerApplyBindRequest;
import com.lkl.laop.sdk.request.V2MmsOpenApiLedgerApplyUnBindRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * 3. 分账关系绑定申请
 * https://o.lakala.com/#/home/document/detail?id=386
 */
@Slf4j
public class V2MmsOpenApiLedgerApplyBindRequestTest extends LKLBaseProdTest {

    /**
     * 绑定
     * @throws Exception
     */
    @Test
    public void test() throws Exception {
        V2MmsOpenApiLedgerApplyBindRequest request = new V2MmsOpenApiLedgerApplyBindRequest();
//        String json = LoadFileUtil.loadJsonFromResource("分账申请/分账接收方绑定_demo_req.json");
//        String reqData = JSONObject.parseObject(json).getString("reqData");
//        request = JsonUtil.fromJson(reqData, V2MmsOpenApiLedgerApplyBindRequest.class);
        request.setVersion("1.0");
        request.setOrderNo(DateUtils.getTimeStampAndRandom());
        request.setOrgCode(LKLConfigProd.orgCode);
        request.setMerInnerNo("4002025102814755551");
        // todo
        request.setReceiverNo("SR2024000165390");
        request.setEntrustFileName("收款方与分账方合作协议.png");
        request.setEntrustFilePath("MMS/20251029/171837-769a0883b9ab44fdbe90c2d083165316.png");
        request.setRetUrl("https://test-znyd.zgzhongnan.com/cc/system/lkl/open/auth/split/bind");

        log.info("分账关系绑定申请:{}", request);
        LKLCommonResponseV2 lklCommonResponse = LKLPost.httpPost(request);
        log.info("分账关系绑定申请 结果:{}", lklCommonResponse);
        if (lklCommonResponse.resultSuccess()) {
            log.info("分账关系绑定申请 成功");
        } else {
            log.info("分账关系绑定申请 失败: {}", lklCommonResponse.getRetMsg());
        }
    }

    /**
     * 解绑
     */
    @Test
    public void testUnBind() {
        V2MmsOpenApiLedgerApplyUnBindRequest request = new V2MmsOpenApiLedgerApplyUnBindRequest();
        request.setVersion("1.0");
        request.setOrderNo(DateUtils.getTimeStampAndRandom());
        request.setOrgCode(LKLConfigProd.orgCode);
        request.setMerInnerNo("4002025102814755551");
        request.setReceiverNo("SR2024000165390");
        request.setEntrustFileName("收款方与分账方合作协议.png");
        request.setEntrustFilePath("MMS/20251029/171837-769a0883b9ab44fdbe90c2d083165316.png");
        request.setRetUrl("https://test-znyd.zgzhongnan.com/cc/system/lkl/open/auth/split/unbind");
        log.info("分账关系解绑申请:{}", request);
        LKLCommonResponseV2 lklCommonResponse = LKLPost.httpPost(request);
        log.info("分账关系解绑申请 结果:{}", lklCommonResponse);
        if (lklCommonResponse.resultSuccess()) {
            log.info("分账关系解绑申请 成功");
        } else {
            log.info("分账关系解绑申请 失败: {}", lklCommonResponse.getRetMsg());
        }
    }

    @Test
    public void testResult() {
        String body = "{\n" +
                "\"optType\":\"DEL\",\n" +
                "\"applyId\":547055274822594560,\n" +
                "\"merCupNo\":\"8222900581201PA\",\n" +
                "\"entrustFileName\":\"合作协议.pdf\",\n" +
                "\"auditStatus\":\"1\",\n" +
                "\"merInnerNo\":\"4002022021832894453\",\n" +
                "\"receiverNo\":\"SR2022021813005\",\n" +
                "\"remark\":\"通过\",\n" +
                "\"auditStatusText\":\"审核通过\",\n" +
                "\"entrustFilePath\":\"G1/M00/00/16/CrFdEl0wGu6AHwGQAAAz1tt6luo194.jpg\"\n" +
                "}\n";

        V2MmsOpenApiLedgerApplyBindCallback v2MmsOpenApiLedgerApplyBindCallback = JsonUtil.fromJson(body, V2MmsOpenApiLedgerApplyBindCallback.class);
        log.info("分账关系绑定申请结果:{}", JsonUtil.toJson(v2MmsOpenApiLedgerApplyBindCallback));
    }

}

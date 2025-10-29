package cn.cc.lkl.authsplit;

import cn.cc.config.JsonUtil;
import cn.cc.lkl.LKLBaseProdTest;
import cn.cc.lkl.dto.LKLCommonResponseV2;
import cn.cc.lkl.dto.authsplitsplit.V2MmsOpenApiLedgerApplyLedgerMerCallbackRequest;
import cn.cc.lkl.sdk.config.LKLConfigProd;
import cn.cc.lkl.util.DateUtils;
import cn.cc.lkl.util.LKLPost;
import cn.cc.lkl.util.LoadFileUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.lkl.laop.sdk.request.V2MmsOpenApiLedgerApplyLedgerMerRequest;
import com.lkl.laop.sdk.request.V2MmsOpenApiLedgerModifyLedgerMerRequest;
import com.lkl.laop.sdk.request.V2MmsOpenApiLedgerQueryLedgerMerRequest;
import com.lkl.laop.sdk.request.V2MmsOpenApiUploadFileRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * https://o.lakala.com/#/home/document/detail?id=379
 * 1. 分账开通
 */
@Slf4j
public class V2MmsOpenApiLedgerApplyLedgerMerRequestTest extends LKLBaseProdTest {

    /**
     * znkj 分账商户 只用操作一次
     * 分账开通申请
     * 填写中南科技的信息，上传分账协议
     * 先测试下不开通支付下能不能用
     *
     * @throws Exception
     */
    @Test
    public void test() throws Exception {
        V2MmsOpenApiLedgerApplyLedgerMerRequest request = new V2MmsOpenApiLedgerApplyLedgerMerRequest();
//        String json = LoadFileUtil.loadJsonFromResource("分账申请/分账申请_demo_req.json");
//        String reqData = JSONObject.parseObject(json).getString("reqData");
//        request = JsonUtil.fromJson(reqData, V2MmsOpenApiLedgerApplyLedgerMerRequest.class);

        request.setVersion("1.0");
        request.setOrderNo(DateUtils.getTimeStampAndRandom());
        // todo 看看这两个参数含义
        // 400和822都存过了
        // 822491048160FN1
        // 4002025102584686558
//        request.setMerInnerNo(LKLConfigProd.merInnerNo);
        request.setMerInnerNo("4002025102814755551");
        request.setOrgCode(LKLConfigProd.orgCode);
        // todo 公司手机号
        request.setContactMobile("15736841111");
        request.setSplitLowestRatio(BigDecimal.valueOf(70.00));
        request.setSplitEntrustFileName("清分结算授权委托书.pdf");
        request.setSplitEntrustFilePath("MMS/20251029/161644-0088b1a908a84b18b7b563e8e245649c.pdf");
        request.setSplitRange("MARK");
        request.setSepFundSource("TR");
        // todo 商户手机号
//        request.setEleContractNo("QY20251025612937473");
        request.setRetUrl("https://test-znyd.zgzhongnan.com/cc/system/lkl/open/auth/split/apply");

        // todo 带公章请求参数
        log.info("分账申请:{}", JsonUtil.toJson(request));
        LKLCommonResponseV2 lklCommonResponse = LKLPost.httpPost(request);
        log.info("分账开通结果:{}", JsonUtil.toJson(lklCommonResponse));
        // todo 带公章版
        // {"retCode":"000000","retMsg":"申请已受理，请等待审核结果","respData":{"version":"1.0","orderNo":"2025102916202252896326","orgCode":"986557","openAppid":"OP10001833","applyId":1035951242671771648}}

        if (lklCommonResponse.resultSuccess()) {
            log.info("分账开通成功");
        } else {
            log.info("分账开通失败: {}", lklCommonResponse.getRetMsg());
        }

    }

    @Test
    public void testUpdate() throws Exception {
        V2MmsOpenApiLedgerModifyLedgerMerRequest request = new V2MmsOpenApiLedgerModifyLedgerMerRequest();
        request.setVersion("1.0");
        request.setOrderNo(DateUtils.getTimeStampAndRandom());
        request.setMerInnerNo(LKLConfigProd.merInnerNo);
        request.setOrgCode(LKLConfigProd.orgCode);
        request.setContactMobile("17600260259");
        request.setSplitLowestRatio("70.00");
        request.setSplitEntrustFileName("清分结算授权委托书.pdf");
        request.setSplitEntrustFilePath("MMS/20251029/161644-0088b1a908a84b18b7b563e8e245649c.pdf");
        request.setSplitRange("MARK");
//        request.setSepFundSource("TR");
//        request.setEleContractNo("QY20251025612937473");
        request.setRetUrl("https://test-znyd.zgzhongnan.com/cc/system/lkl/open/auth/split/apply");
        log.info("分账申请:{}", request);
        LKLCommonResponseV2 lklCommonResponse = LKLPost.httpPost(request);
        log.info("分账开通结果:{}", JsonUtil.toJson(lklCommonResponse));
        if (lklCommonResponse.resultSuccess()) {
            log.info("分账开通成功");
        } else {
            log.info("分账开通失败: {}", lklCommonResponse.getRetMsg());
        }

    }

    @Test
    public void testResult() {
        V2MmsOpenApiLedgerQueryLedgerMerRequest request = new V2MmsOpenApiLedgerQueryLedgerMerRequest();
        request.setVersion("1.0");
        request.setOrderNo(DateUtils.getTimeStampAndRandom());
        request.setOrgCode(LKLConfigProd.orgCode);
        request.setMerInnerNo("4002025102814755551");
//        request.setMerInnerNo(LKLConfigProd.merInnerNo);
        log.info("分账查询:{}", JsonUtil.toJson(request));
        LKLCommonResponseV2 lklCommonResponse = LKLPost.httpPost(request);
        log.info("分账查询结果:{}", JsonUtil.toJson(lklCommonResponse));
        if (lklCommonResponse.resultSuccess()) {
            log.info("分账查询成功");
        } else {
            log.info("分账查询失败: {}", lklCommonResponse.getRetMsg());
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

    @Test
    public void upload() {
        V2MmsOpenApiUploadFileRequest request = new V2MmsOpenApiUploadFileRequest();
        request.setVersion("1.0");
        request.setOrderNo(DateUtils.getTimeStampAndRandom());
        request.setOrgCode(LKLConfigProd.orgCode);
//        request.setAttType("SPLIT_ENTRUST_FILE");
        request.setAttType("SPLIT_COOPERATION_FILE");
        request.setAttExtName("png");
        try {
            request.setAttContext(Base64.encodeBase64String(Files.readAllBytes(Paths.get("C:\\Users\\znkj\\Desktop\\分账上传文件\\收款方与分账方合作协议.png"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        LKLCommonResponseV2 lklCommonResponse = LKLPost.httpPost(request);
        if (lklCommonResponse.resultSuccess()) {
            log.info("上传成功: {}", JsonUtil.toJson(lklCommonResponse));
            // {"retCode":"000000","retMsg":"成功","respData":{"attType":"SPLIT_ENTRUST_FILE","orderNo":"2025102716101394030757","orgCode":"986557","attFileId":"MMS/20251027/161014-39c9ee5bca994de78b79326b34979471.pdf"}}
            // {"retCode":"000000","retMsg":"成功","respData":{"attType":"SPLIT_ENTRUST_FILE","orderNo":"2025102914184540830318","orgCode":"986557","attFileId":"MMS/20251029/141846-82907282796b46ba9930aee85fdeaa1a.pdf"}}
            // 全称 + 城市合伙人 版
            // {"retCode":"000000","retMsg":"成功","respData":{"attType":"SPLIT_ENTRUST_FILE","orderNo":"2025102915064019940465","orgCode":"986557","attFileId":"MMS/20251029/150641-9a8268d698934ca4b7233e303fd07ac4.pdf"}}
            // 盖章版
            // {"retCode":"000000","retMsg":"成功","respData":{"attType":"SPLIT_ENTRUST_FILE","orderNo":"2025102916164298412565","orgCode":"986557","attFileId":"MMS/20251029/161644-0088b1a908a84b18b7b563e8e245649c.pdf"}}
            // 收款方与分账方合作协议
            // {"retCode":"000000","retMsg":"成功","respData":{"attType":"SPLIT_COOPERATION_FILE","orderNo":"2025102917183666224347","orgCode":"986557","attFileId":"MMS/20251029/171837-769a0883b9ab44fdbe90c2d083165316.png"}}
            /**
             * json
             */
        } else {
            log.info("上传失败: {}", lklCommonResponse.getRetMsg());
        }

    }

}

package cn.cc.lkl.merchant;

import cn.cc.config.JsonUtil;
import cn.cc.lkl.LKLBaseTest;
import cn.cc.lkl.dto.LKLCommonResponse;
import cn.cc.lkl.dto.merchant.V3MmsOpenApiEcApplyCallbackResponse;
import cn.cc.lkl.dto.merchant.V3MmsOpenApiEcApplyRequest;
import cn.cc.lkl.dto.merchant.V3MmsOpenApiEcApplyResponse;
import cn.cc.lkl.util.LKLPost;
import cn.cc.lkl.util.LoadFileUtil;
import cn.cc.lkl.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * 1. 电子合同申请
 */
@Slf4j
public class V3MmsOpenApiEcApplyRequestTest extends LKLBaseTest {

    @Test
    public void test() throws Exception {
        // /api/v3/mms/open_api/ec/apply
        V3MmsOpenApiEcApplyRequest request = new V3MmsOpenApiEcApplyRequest();
        String json = LoadFileUtil.loadJsonFromResource("进件/合同-demo-cc-req.json");
        String reqData = JSONObject.parseObject(json).getString("req_data");
        request = JsonUtil.fromJson(reqData, V3MmsOpenApiEcApplyRequest.class);
        log.info("request: \r\n{}", request);
        log.info("request: \r\n{}", JsonUtil.toJson(request));
        request.setRetUrl("https://test-znyd.zgzhongnan.com/cc/lkl/res");
        request.setOrderNo(StringUtils.getUUID());
        request.setEcContentParameters(LoadFileUtil.loadJsonFromResource("进件/合同-cc-详细内容.json"));

        // 处理合同内容
//        String ecContentParameters = request.getEcContentParameters();
//        JSONObject jsonObject = JSONObject.parseObject(ecContentParameters);
//        log.info("jsonObject: \r\n{}", jsonObject);
        // {"A65":"是","B20":"6217898000003447322","A67":2025,"A68":9,"A69":12,"B24":"徐文","B46":"是","B25":"110106196508231851","B47":"专业化扫码支付","B26":"18504343826","B27":"徐文","B28":"yhws@psbc-ubank.com","B29":"110106196508231851","A1":"中南科技有限公司","A70":2023,"A71":5,"A72":5,"A51":"0%","A52":"0.01%","B30":"18269443753","B31":"拉卡拉科技有限公司","B10":"拉卡拉科技有限公司","B32":"朱有有","B33":"上海市黄浦区新码头街","B34":"18504343826","A58":"2","B13":"山东省临沂市","A59":"是","B14":"90000000MA1FPFRF44","A19":4.0,"B18":"朱有有","B19":"邮储银行联行行号","D1":"2023/05/05","B1":2023,"B2":5,"B3":"是","B8":"拉卡拉科技有限公司","B9":"未列入其他代码的食品商店","A61":"是","A64":"邮惠万家银行"}

        LKLCommonResponse response = LKLPost.httpPost(request);
        log.info("response: \r\n{}", response);
        log.info("response: \r\n{}", JsonUtil.toJson(response));
        // {"code":"000000","msg":"成功","resp_data":{"order_no":"e0a1d6f8dbca4ee99dddf8a805778d37","result_url":"https://jrt.wsmsd.cn/signature/s/3znie2iqu2xozxoq?rdm=1757576366888","ec_apply_id":1018546299873976320}}
        log.info("response: \r\n{}", response.resultSuccess());

        V3MmsOpenApiEcApplyResponse v3MmsOpenApiEcApplyResponse = JsonUtil.fromJson(response.getRespData().toString(), V3MmsOpenApiEcApplyResponse.class);
        log.info("v3MmsOpenApiEcApplyResponse: \r\n{}", v3MmsOpenApiEcApplyResponse);
        log.info("v3MmsOpenApiEcApplyResponse: \r\n{}", JsonUtil.toJson(v3MmsOpenApiEcApplyResponse));
        // V3MmsOpenApiEcApplyResponse(orderNo=e0a1d6f8dbca4ee99dddf8a805778d37, ecApplyId=1018546127349669888, resultUrl=https://jrt.wsmsd.cn/signature/s/x40hmllo9rwgg80f?rdm=1757576326875)

        // demo
        // {"order_no":"e0a1d6f8dbca4ee99dddf8a805778d37","ec_apply_id":1018546299873976320,"result_url":"https://jrt.wsmsd.cn/signature/s/3znie2iqu2xozxoq?rdm=1757576366888"}

        // cc
        // {"order_no":"2edc44af5d2b4febb4afeb01ca23c1a5","ec_apply_id":1018842419112112128,"result_url":"https://jrt.wsmsd.cn/signature/s/ufvievpq0bu86ycu?rdm=1757646970581"}
    }

    @Test
    public void callBack() throws Exception {
        V3MmsOpenApiEcApplyCallbackResponse v3MmsOpenApiEcApplyCallbackResponse = JsonUtil.fromJson(LoadFileUtil.loadJsonFromResource("进件/合同-签署-cc-回调-res.json"), V3MmsOpenApiEcApplyCallbackResponse.class);
        log.info("v3MmsOpenApiEcApplyCallbackResponse: \r\n{}", v3MmsOpenApiEcApplyCallbackResponse);
        log.info("v3MmsOpenApiEcApplyCallbackResponse: \r\n{}", JsonUtil.toJson(v3MmsOpenApiEcApplyCallbackResponse));
    }

}

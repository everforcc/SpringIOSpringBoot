package cn.cc.lkl.merchant;

import cn.cc.config.JsonUtil;
import cn.cc.lkl.LKLBaseProdTest;
import cn.cc.lkl.dto.LKLCommonResponse;
import cn.cc.lkl.dto.merchant.*;
import cn.cc.lkl.sdk.config.LKLConfigProd;
import cn.cc.lkl.util.LKLPost;
import cn.cc.lkl.util.LoadFileUtil;
import cn.cc.lkl.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * 1. 电子合同申请
 */
@Slf4j
public class V3MmsOpenApiEcApplyRequestTest extends LKLBaseProdTest {

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

    @Test
    public void testEC007() {
        V3MmsOpenApiEc007 v3MmsOpenApiEc007 = new V3MmsOpenApiEc007();
        String ecContentParameters = JSONObject.toJSONString(v3MmsOpenApiEc007, SerializerFeature.PrettyFormat);
        log.info(ecContentParameters);
        V3MmsOpenApiEcApplyRequest request = new V3MmsOpenApiEcApplyRequest();
        request.setRetUrl("https://test-znyd.zgzhongnan.com/cc/lkl/res");
        request.setOrderNo(StringUtils.getUUID());
        request.setEcContentParameters(ecContentParameters);

        log.info(request.toBody());
    }

    private static void gkl(V3MmsOpenApiEc007 v3MmsOpenApiEc007) {
        String userName = "郭凯龙";
        String phone = "15738573601";
        String mail = "718497737@qq.com";
//        String address = "河南省郑州市管城回族区紫辰路华祥国际大厦B座19楼";
        String IDCardNo = "41018219960126531X";
        String yhkNo = "6217002430077411446";
        String yhkAddress = "中国建设银行股份有限公司郑州滨河国际新城支行";
    }

    private static void jsy(V3MmsOpenApiEc007 v3MmsOpenApiEc007) {
        String userName = "蒋守业";
        String phone = "18706828536";
        String mail = "623104270@qq.com";
//        String address = "河南省郑州市管城回族区紫辰路华祥国际大厦B座19楼";
        String IDCardNo = "410224198708033639";
        String yhkNo = "6217853600054310213";
        String yhkAddress = "中国银行西安逸翠园支行";
    }

    /**
     * 测试小微
     */
    public static V3MmsOpenApiEc007 getV3MmsOpenApiEc007() {
        V3MmsOpenApiEc007 v3MmsOpenApiEc007 = new V3MmsOpenApiEc007();
        String userName = "蒋守业";
        String phone = "18706828536";
        String mail = "623104270@qq.com";
//        String address = "河南省郑州市管城回族区紫辰路华祥国际大厦B座19楼";
        String IDCardNo = "410224198708033639";
        String yhkNo = "6217853600054310213";
        String yhkAddress = "中国银行西安逸翠园支行";

        String companyName = "河南郑州中南科技食品公司2";
        String companyAddress = "河南省郑州市管城回族区紫辰路华祥国际大厦B座18楼";

        v3MmsOpenApiEc007.setA1(userName);
//        v3MmsOpenApiEc007.setB8(userName);
        v3MmsOpenApiEc007.setB13(companyAddress);

        // 对私
        v3MmsOpenApiEc007.setB17("是");
        v3MmsOpenApiEc007.setB18(userName);

        v3MmsOpenApiEc007.setB19(yhkAddress);
        v3MmsOpenApiEc007.setB20(yhkNo);

        v3MmsOpenApiEc007.setB24(userName);
        v3MmsOpenApiEc007.setB25(IDCardNo);
        v3MmsOpenApiEc007.setB26(phone);
        v3MmsOpenApiEc007.setB27(userName);
        v3MmsOpenApiEc007.setB28(mail);
        v3MmsOpenApiEc007.setB29(IDCardNo);
        v3MmsOpenApiEc007.setB30(phone);

        //公司信息
        v3MmsOpenApiEc007.setB31(companyName);
        v3MmsOpenApiEc007.setB32(userName);
        v3MmsOpenApiEc007.setB33(companyAddress);
        v3MmsOpenApiEc007.setB34(phone);

        v3MmsOpenApiEc007.setD1(yhkAddress);
        v3MmsOpenApiEc007.setD3(userName);
        v3MmsOpenApiEc007.setD6(userName);
        v3MmsOpenApiEc007.setD8(userName);
        v3MmsOpenApiEc007.setD10(userName);

        v3MmsOpenApiEc007.setE8(userName);

        return v3MmsOpenApiEc007;
    }

    @Test
    public void testEC007cc() {
        // "business_license_name": "河南郑州中南科技食品公司",
        V3MmsOpenApiEc007 v3MmsOpenApiEc007 = getV3MmsOpenApiEc007();
        String ecContentParameters = JsonUtil.toJson(v3MmsOpenApiEc007);
        log.info(ecContentParameters);
        V3MmsOpenApiEcApplyRequest request;
        String json = LoadFileUtil.loadJsonFromResource("进件/合同-demo-jsy-req.json");
        String reqData = JSONObject.parseObject(json).getString("req_data");
        request = JsonUtil.fromJson(reqData, V3MmsOpenApiEcApplyRequest.class);

        request.setRetUrl("https://test-znyd.zgzhongnan.com/cc/lkl/res");
        request.setOrderNo(StringUtils.getUUID());

        request.setEcTypeCode("EC007");
        request.setEcContentParameters(JsonUtil.toJson(v3MmsOpenApiEc007));

        log.info(request.toBody());

        LKLCommonResponse response = LKLPost.httpPost(request);
        log.info("response: \r\n{}", JsonUtil.toJson(response));
        log.info("response: \r\n{}", response.resultSuccess());

        V3MmsOpenApiEcApplyResponse v3MmsOpenApiEcApplyResponse = JsonUtil.fromJson(response.getRespData().toString(), V3MmsOpenApiEcApplyResponse.class);
        log.info("v3MmsOpenApiEcApplyResponse: \r\n{}", JsonUtil.toJson(v3MmsOpenApiEcApplyResponse));
    }

    @Test
    public void testEC007ccQuery() {
        V3MmsOpenApiEcQStatusRequest request = new V3MmsOpenApiEcQStatusRequest();
        request.setOrderNo(StringUtils.getUUID());
        request.setOrgCode(LKLConfigProd.orgCode);
        request.setEcApplyId("1033767775095672832");
        log.info("电子合同查询: \r\n:{}",request.toBody());

        LKLCommonResponse response = LKLPost.httpPost(request);

        log.info("response: \r\n{}", JsonUtil.toJson(response));

    }

}

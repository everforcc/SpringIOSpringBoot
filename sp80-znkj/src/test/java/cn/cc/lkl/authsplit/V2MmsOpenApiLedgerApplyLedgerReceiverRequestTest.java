package cn.cc.lkl.authsplit;

import cn.cc.config.JsonUtil;
import cn.cc.lkl.LKLBaseProdTest;
import cn.cc.lkl.dto.LKLCommonResponseV2;
import cn.cc.lkl.dto.authsplitsplit.V2MmsOpenApiLedgerApplyLedgerReceiverResponse;
import cn.cc.lkl.dto.authsplitsplit.V2MmsOpenApiLedgerQueryReceiverDetailRequest;
import cn.cc.lkl.sdk.config.LKLConfigProd;
import cn.cc.lkl.util.DateUtils;
import cn.cc.lkl.util.LKLPost;
import cn.cc.lkl.util.LoadFileUtil;
import com.alibaba.fastjson.JSON;
import com.lkl.laop.sdk.request.V2MmsOpenApiLedgerApplyLedgerReceiverRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * 2. 账户分账接收方申请
 * https://o.lakala.com/#/home/document/detail?id=382
 */
@Slf4j
public class V2MmsOpenApiLedgerApplyLedgerReceiverRequestTest extends LKLBaseProdTest {

    /**
     * 已通过
     * @throws Exception
     */
    @Test
    public void testReceive() throws Exception {
        V2MmsOpenApiLedgerApplyLedgerReceiverRequest request = new V2MmsOpenApiLedgerApplyLedgerReceiverRequest();
//        String json = LoadFileUtil.loadJsonFromResource("分账申请/分账接收方_demo_req.json");
//        request = JsonUtil.fromJson(json, V2MmsOpenApiLedgerApplyLedgerReceiverRequest.class);
        request.setVersion("1.0");
        request.setOrderNo(DateUtils.getTimeStampAndRandom());
        request.setOrgCode(LKLConfigProd.orgCode);
        request.setReceiverName("河南霄瞰信息科技有限公司");
        request.setContactMobile("17600260259");
        request.setLicenseNo("91410100MAEM8Y8Y8K");
        request.setLicenseName("河南霄瞰信息科技有限公司");
        request.setLegalPersonName("薄飞跃");
        request.setLegalPersonCertificateType("17");
        request.setLegalPersonCertificateNo("412721199204214617");
        request.setAcctNo("410126010190094109");
        request.setAcctName("河南霄瞰信息科技有限公司");
        request.setAcctTypeCode("57");
        request.setAcctCertificateType("17");
        request.setAcctCertificateNo("412721199204214617");
        request.setAcctOpenBankCode("313491099267");
        request.setAcctOpenBankName("中国银行股份有限公司郑州航海东路支行");
        request.setAcctClearBankCode("313491099267");

        log.info("账户分账接收方申请:{}", request);
        LKLCommonResponseV2 lklCommonResponse = LKLPost.httpPost(request);
        log.info("账户分账接收方申请结果:{}", lklCommonResponse);
        if (lklCommonResponse.resultSuccess()) {
            log.info("账户分账接收方申请成功");
        } else {
            log.info("账户分账接收方申请失败: {}", lklCommonResponse.getRetMsg());
        }
    }

    @Test
    public void testReceiveXw() throws Exception {
        V2MmsOpenApiLedgerApplyLedgerReceiverRequest request = new V2MmsOpenApiLedgerApplyLedgerReceiverRequest();
        String json = LoadFileUtil.loadJsonFromResource("分账申请/分账接收方_demo_req.json");
        request = JsonUtil.fromJson(json, V2MmsOpenApiLedgerApplyLedgerReceiverRequest.class);
//        request.setVersion("1.0");
//        request.setOrderNo(DateUtils.getTimeStampAndRandom());
//        request.setOrgCode(LKLConfigProd.orgCode);
//        request.setReceiverName("河南霄瞰信息科技有限公司");
//        request.setContactMobile("17600260259");
//        request.setLicenseNo("91410100MAEM8Y8Y8K");
//        request.setLicenseName("河南霄瞰信息科技有限公司");
//        request.setLegalPersonName("薄飞跃");
//        request.setLegalPersonCertificateType("17");
//        request.setLegalPersonCertificateNo("412721199204214617");
//        request.setAcctNo("410126010190094109");
//        request.setAcctName("河南霄瞰信息科技有限公司");
//        request.setAcctTypeCode("57");
//        request.setAcctCertificateType("17");
//        request.setAcctCertificateNo("412721199204214617");
//        request.setAcctOpenBankCode("313491099267");
//        request.setAcctOpenBankName("中国银行股份有限公司郑州航海东路支行");
//        request.setAcctClearBankCode("313491099267");

        log.info("账户分账接收方申请:{}", request);
        LKLCommonResponseV2 lklCommonResponse = LKLPost.httpPost(request);
        log.info("账户分账接收方申请结果:{}", JsonUtil.toJson(lklCommonResponse));
        if (lklCommonResponse.resultSuccess()) {
            log.info("账户分账接收方申请成功");
        } else {
            log.info("账户分账接收方申请失败: {}", lklCommonResponse.getRetMsg());
        }
    }

    @Test
    public void testReceiveMsg() throws Exception {
        V2MmsOpenApiLedgerQueryReceiverDetailRequest request = new V2MmsOpenApiLedgerQueryReceiverDetailRequest();
        request.setVersion("1.0");
        request.setOrderNo(DateUtils.getTimeStampAndRandom());
        request.setOrgCode(LKLConfigProd.orgCode);
        request.setReceiverNo("SR2024000165546");

        log.info("账户分账接收方查询:{}", request);
        LKLCommonResponseV2 lklCommonResponse = LKLPost.httpPost(request);
        log.info("账户分账接收方查询结果:{}", lklCommonResponse);
        if (lklCommonResponse.resultSuccess()) {
            log.info("账户分账接收方查询成功");
        } else {
            log.info("账户分账接收方查询失败: {}", lklCommonResponse.getRetMsg());
        }

    }

    @Test
    public void test() throws Exception {
        V2MmsOpenApiLedgerApplyLedgerReceiverRequest request = new V2MmsOpenApiLedgerApplyLedgerReceiverRequest();
//        String json = LoadFileUtil.loadJsonFromResource("分账申请/分账接收方_demo_req.json");
//        request = JsonUtil.fromJson(json, V2MmsOpenApiLedgerApplyLedgerReceiverRequest.class);
        request.setVersion("1.0");
        request.setOrderNo(DateUtils.getTimeStampAndRandom());
        request.setOrgCode(LKLConfigProd.orgCode);
        request.setReceiverName("中南科技");
        request.setContactMobile("17600260259");
        request.setLicenseNo("91410100MAEM8Y8Y8K");
        request.setLicenseName("河南霄瞰信息科技有限公司");
        request.setLegalPersonName("薄飞跃");
        request.setLegalPersonCertificateType("17");
        request.setLegalPersonCertificateNo("412721199204214617");
        request.setAcctNo("");
        request.setAcctName("");
        request.setAcctTypeCode("");
        request.setAcctCertificateType("");
        request.setAcctCertificateNo("");
        request.setAcctOpenBankCode("");
        request.setAcctOpenBankName("");
        request.setAcctClearBankCode("");



        log.info("账户分账接收方申请:{}", request);
        LKLCommonResponseV2 lklCommonResponse = LKLPost.httpPost(request);
        log.info("账户分账接收方申请结果:{}", lklCommonResponse);
        if (lklCommonResponse.resultSuccess()) {
            log.info("账户分账接收方申请成功");
        } else {
            log.info("账户分账接收方申请失败: {}", lklCommonResponse.getRetMsg());
        }
    }

    @Test
    public void testResurn() throws Exception {
        String json = "{\n" +
                "    \"retCode\":\"000000\",\n" +
                "    \"retMsg\":\"分账接收方创建成功\",\n" +
                "    \"respData\":{\n" +
                "        \"version\":\"1.0\",\n" +
                "        \"orderNo\":\"KFPT20230223182029463089445\",\n" +
                "        \"orgCode\":\"1950836\",\n" +
                "        \"orgId\":\"1950836\",\n" +
                "        \"orgName\":\"红星集团\",\n" +
                "        \"receiverNo\":\"SR2023022318013\"\n" +
                "    }\n" +
                "}\n";
        LKLCommonResponseV2 lklCommonResponseV2 = JSON.parseObject(json, LKLCommonResponseV2.class);
        V2MmsOpenApiLedgerApplyLedgerReceiverResponse response = JSON.parseObject(lklCommonResponseV2.getRespData().toString(), V2MmsOpenApiLedgerApplyLedgerReceiverResponse.class);
        log.info("账户分账接收方申请结果:{}", response);
    }

    @Test
    public void testMsg() throws Exception {
        // /v2/mms/openApi/ledger/queryReceiverDetail
        V2MmsOpenApiLedgerQueryReceiverDetailRequest request = new V2MmsOpenApiLedgerQueryReceiverDetailRequest();

    }

}

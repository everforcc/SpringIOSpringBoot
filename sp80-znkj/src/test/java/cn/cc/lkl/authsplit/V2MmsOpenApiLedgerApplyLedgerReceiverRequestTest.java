package cn.cc.lkl.authsplit;

import cn.cc.config.JsonUtil;
import cn.cc.lkl.LKLBaseProdTest;
import cn.cc.lkl.LKLBaseTest;
import cn.cc.lkl.dto.LKLCommonResponseV2;
import cn.cc.lkl.sdk.config.LKLConfigProd;
import cn.cc.lkl.util.DateUtils;
import cn.cc.lkl.util.LKLPost;
import cn.cc.lkl.util.LoadFileUtil;
import com.lkl.laop.sdk.request.V2MmsOpenApiLedgerApplyLedgerReceiverRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * 2. 账户分账接收方申请
 * https://o.lakala.com/#/home/document/detail?id=382
 */
@Slf4j
public class V2MmsOpenApiLedgerApplyLedgerReceiverRequestTest extends LKLBaseProdTest {

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

}

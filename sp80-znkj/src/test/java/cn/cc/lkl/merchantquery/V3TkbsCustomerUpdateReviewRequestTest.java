package cn.cc.lkl.merchantquery;

import cn.cc.lkl.dto.merchantupdate.V3TkbsCustomerUpdateReviewRequest;
import org.junit.Test;

/**
 * https://o.lakala.com/p/#/document/detail?id=1060
 * 商户信息变更 -> 商户审核状态查询
 */
public class V3TkbsCustomerUpdateReviewRequestTest {

    @Test
    public void test(){
        V3TkbsCustomerUpdateReviewRequest request = new V3TkbsCustomerUpdateReviewRequest();
        request.setRevieRelatedId("");
        request.setOrgCode("");
    }

}

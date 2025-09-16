package cn.cc.lkl.dto.authrealname;

import cn.cc.lkl.dto.LKLBaseRequestV2;
import cn.cc.lkl.enums.FunctionCodeEnum;
import lombok.Data;

/**
 * https://o.lakala.com/#/home/document/detail?id=345
 * 支付宝实名认证信息查询
 * /api/v2/mms/openApi/alipayRealNameQuery
 */
@Data
public class V2MmsOpenApiRealNameQueryRequest extends LKLBaseRequestV2 {

    /**
     * 接口版本号	1.0
     */
    private String version = "1.0";
    /**
     * 订单编号（便于后续跟踪排查问题及核对报文）	14位年月日时（24小时制）分秒+8位的随机数（不重复）如：2021020112000012345678
     */
    private String orderNo;
    /**
     * 机构代码
     */
    private String orgCode;
    /**
     * 拉卡拉内部商户号 4002021012659676355
     */
    private String merInnerNo;
    /**
     * 子商户号
     */
    private String subMchId;
    /**
     * 支付宝来源（建议传入，能具体定位用的渠道，仅支持拉卡拉渠道查询）
     */
    private String channelId;
    /**
     * 实名认证类型	:支付宝
     */
    private String realNameType = "ZFBZF";


    @Override
    public FunctionCodeEnum getFunctionCode() {
        return FunctionCodeEnum.API_V2_MMS_OPENAPI_ALIPAYREALNAMEQUERY;
    }
}

package cn.cc.lkl.dto.authrealname;

import cn.cc.lkl.dto.LKLBaseRequestV2;
import cn.cc.lkl.enums.FunctionCodeEnum;
import lombok.Data;

/**
 * https://o.lakala.com/#/home/document/detail?id=181
 * 微信实名认证结果查询
 * /sit/api/v2/mms/openApi/wechatRealNameQuery
 */
@Data
public class V2MmsOpenApiWechatRealNameQueryRequest extends LKLBaseRequestV2 {

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
     * 渠道号（建议传入，能具体定位用的渠道，仅支持拉卡拉渠道查询）
     */
    private String channelId;

    @Override
    public FunctionCodeEnum getFunctionCode() {
        return FunctionCodeEnum.API_V2_MMS_OPENAPI_WECHATREALNAMEQUERY;
    }
}

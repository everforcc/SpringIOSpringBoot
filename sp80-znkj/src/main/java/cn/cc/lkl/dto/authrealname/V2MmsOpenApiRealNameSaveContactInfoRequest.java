package cn.cc.lkl.dto.authrealname;

import cn.cc.lkl.dto.LKLBaseRequest;
import cn.cc.lkl.dto.LKLBaseRequestV2;
import cn.cc.lkl.enums.FunctionCodeEnum;
import lombok.Data;

import javax.validation.constraints.Size;

/**
 * https://o.lakala.com/#/home/document/detail?id=521
 * 微信实名联系人信息保存
 */
@Data
public class V2MmsOpenApiRealNameSaveContactInfoRequest extends LKLBaseRequestV2 {

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
     * 拉卡拉内部商户号	4002021012659676355
     */
    private String merInnerNo;
    /**
     * 联系人类型
     * LEGAL：经营者/法人
     * SUPER：经办人。 （经办人：经商户授权办理微信支付业务的人员）。
     */
    private String contactType;
    /**
     * 联系人名称
     */
    private String name;
    /**
     * 联系人证件类型	IDENTIFICATION_TYPE_IDCARD：中国大陆居民-身份证
     */
    private String contactIdDocType = "IDENTIFICATION_TYPE_IDCARD";
    /**
     * 联系人证件号码
     */
    private String idCardNumber;
    /**
     * 联系人证件有效期开始时间	1991-01-01
     */
    private String contactPeriodBegin;
    /**
     * 联系人证件有效期结束时间（格式YYYY-MM-DD）	1991-12-31 （长期：9999-12-31）
     */
    private String contactPeriodEnd;
    /**
     * 联系人手机号
     */
    @Size(min = 11, max = 11, message = "手机号长度11位")
    private String mobile;


    @Override
    public FunctionCodeEnum getFunctionCode() {
        return FunctionCodeEnum.API_V2_MMS_OPENAPI_REALNAME_SAVECONTACTINFO;
    }
}

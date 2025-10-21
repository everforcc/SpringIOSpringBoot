package cn.cc.lkl.dto.authrealname;

import cn.cc.lkl.dto.LKLBaseRequestV1;
import cn.cc.lkl.dto.LKLBaseRequestV2;
import cn.cc.lkl.enums.FunctionCodeEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 支付宝实名联系人信息保存
 * https://o.lakala.com/#/home/document/detail?id=343
 * <p>
 * /api/v2/mms/openApi/realName/saveAlipayContactInfo
 */
@Data
public class V2MmsOpenApiRealNameSaveAlipayContactInfoRequest extends LKLBaseRequestV1 {

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
     * SUPER：经办人。 （经办人：经商户授权办理支付业务的人员）。
     */
    private String contactType;

    /**
     * 联系人名称
     */
    @JsonProperty("name")
    private String name;

    /**
     * 联系人证件类型
     * RESIDENT：中国大陆居民-身份证
     */
    private String contactIdDocType = "RESIDENT";
    /**
     * 联系人证件号码
     */
    private String idCardNumber;
    /**
     * 联系人手机号
     */
    private String mobile;
    /**
     * 实名认证类型（默认ZFBZF）
     */
    private String realNameType;

    @Override
    public FunctionCodeEnum getFunctionCode() {
        return FunctionCodeEnum.API_V2_MMS_OPENAPI_REALNAME_SAVEALIPAYCONTACTINFO;
    }

}

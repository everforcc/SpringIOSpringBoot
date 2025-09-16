package cn.cc.lkl.dto.authrealname;

import cn.cc.lkl.dto.LKLBaseRequestV2;
import cn.cc.lkl.enums.FunctionCodeEnum;
import lombok.Data;

/**
 * 商户报备结果查询
 * https://o.lakala.com/#/home/document/detail?id=326
 */
@Data
public class V2MmsOpenApiQuerySubMerInfoRequest extends LKLBaseRequestV2 {

    /**
     * 字段名称	约束	字段类型	长度	字段描述	取值说明	原变量名
     * version	必传	String	8		1.0
     * orderNo	必传	String	32
     * orgCode	必传	String	32	机构代码 （合作方在拉卡拉的标识，请联系业务员）	1
     * merInnerNo	可传	String	32	拉卡拉内部商户号和银联商户号必须传一个，都送以内部商户号为准。	5002022050550024285
     * merCupNo	可传	String	32	拉卡拉内部商户号和银联商户号必须传一个，都送以内部商户号为准。	8222900581207ET
     * registerChannel	可选	String	8	报备渠道	UNIONPAY
     * registerType	可选	String	64	报备类型	WXZF
     * registerStatus	可选	String	64
     * subMchId	可选	String	64	子商户号
     */

    /**
     * 接口版本号
     */
    private String version = "1.0";
    /**
     * 订单编号,保证唯一（便于后续跟踪排查问题及核对报文）	14位年月日时（24小时制）分秒+8位的随机数（不重复）如：2021020112000012345678
     */
    private String orderNo;
    /**
     * 机构代码 （合作方在拉卡拉的标识，请联系业务员）
     */
    private String orgCode;
    /**
     * 拉卡拉内部商户号和银联商户号必须传一个，都送以内部商户号为准。
     */
    private String merInnerNo;
    /**
     * 拉卡拉内部商户号和银联商户号必须传一个，都送以内部商户号为准。	8222900581207ET
     */
    private String merCupNo;
    /**
     * 报备渠道	UNIONPAY
     */
    private String registerChannel;
    /**
     * 报备类型	WXZF
     */
    private String registerType;
    /**
     * 报备状态 SUCCESS：成功；FAIL：失败	SUCCESS
     */
    private String registerStatus;
    /**
     * 子商户号
     */
    private String subMchId;


    @Override
    public FunctionCodeEnum getFunctionCode() {
        return FunctionCodeEnum.API_V2_MMS_OPENAPI_QUERYSUBMERINFO;
    }

}

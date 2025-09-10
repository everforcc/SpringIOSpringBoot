package cn.cc.lkl.dto.authrealname;

import cn.cc.lkl.dto.LKLBaseRequest;
import cn.cc.lkl.enums.FunctionCodeEnum;
import lombok.Data;

/**
 * 支付宝实名联系人信息保存
 * https://o.lakala.com/#/home/document/detail?id=343
 *
 * /api/v2/mms/openApi/realName/saveAlipayContactInfo
 */
@Data
public class V2MmsOpenApiRealNameSaveAlipayContactInfoRequest extends LKLBaseRequest {

    // todo 具体字段

    @Override
    public FunctionCodeEnum getFunctionCode() {
        return FunctionCodeEnum.API_V2_MMS_OPENAPI_REALNAME_SAVEALIPAYCONTACTINFO;
    }

}

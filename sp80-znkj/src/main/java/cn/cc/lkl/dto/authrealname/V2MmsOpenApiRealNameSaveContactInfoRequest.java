package cn.cc.lkl.dto.authrealname;

import cn.cc.lkl.dto.LKLBaseRequest;
import cn.cc.lkl.enums.FunctionCodeEnum;

/**
 * https://o.lakala.com/#/home/document/detail?id=521
 * 微信实名联系人信息保存
 */
public class V2MmsOpenApiRealNameSaveContactInfoRequest extends LKLBaseRequest {

    // todo

    @Override
    public FunctionCodeEnum getFunctionCode() {
        return FunctionCodeEnum.API_V2_MMS_OPENAPI_REALNAME_SAVECONTACTINFO;
    }
}

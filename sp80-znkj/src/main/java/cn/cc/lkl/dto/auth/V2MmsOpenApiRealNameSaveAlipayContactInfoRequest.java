package cn.cc.lkl.dto.auth;

import cn.cc.lkl.dto.LKLBaseRequest;
import cn.cc.lkl.enums.FunctionCodeEnum;
import lombok.Data;

@Data
public class V2MmsOpenApiRealNameSaveAlipayContactInfoRequest extends LKLBaseRequest {
    @Override
    public FunctionCodeEnum getFunctionCode() {
        return null;
    }
}

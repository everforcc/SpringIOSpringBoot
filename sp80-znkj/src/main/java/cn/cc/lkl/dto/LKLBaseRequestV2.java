package cn.cc.lkl.dto;

import cn.cc.lkl.enums.FunctionCodeEnum;
import cn.cc.lkl.util.DateUtils;
import com.lkl.laop.sdk.utils.JsonUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * lkl基础请求类
 */
public abstract class LKLBaseRequestV2 {

    public abstract FunctionCodeEnum getFunctionCode();

    public String toBody() {
        Map<String, Object> param = new HashMap<>();
        param.put("reqTime", DateUtils.getTimeStamp());
        param.put("version", "2.0");
        param.put("reqData", this);
        return JsonUtils.toJSONString(param);
    }

}

package cn.cc.lkl.dto;

import cn.cc.lkl.enums.FunctionCodeEnum;
import cn.cc.lkl.util.DateUtils;
import cn.cc.lkl.util.StringUtils;
import com.lkl.laop.sdk.utils.JsonUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * lkl基础请求类
 */
public abstract class LKLBaseRequestV1 {

    public abstract FunctionCodeEnum getFunctionCode();

    public String toBody() {
        Map<String, Object> param = new HashMap<>();
        param.put("timestamp", new Date().getTime());
        param.put("ver", "1.0.0");
        param.put("reqId", StringUtils.getUUID());
        param.put("reqData", this);
        return JsonUtils.toJSONString(param);
    }


}

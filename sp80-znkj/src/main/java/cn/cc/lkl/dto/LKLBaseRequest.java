package cn.cc.lkl.dto;

import cn.cc.lkl.enums.FunctionCodeEnum;
import cn.cc.lkl.util.DateUtils;
import com.lkl.laop.sdk.utils.JsonUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * lkl基础请求类
 */
public abstract class LKLBaseRequest {

    public abstract FunctionCodeEnum getFunctionCode();

    public String toBody() {
        Map<String, Object> param = new HashMap<>();
        param.put("req_time", DateUtils.getTimeStamp());
        param.put("version", "3.0");
        param.put("req_data", this);
        return JsonUtils.toJSONString(param);
    }

}

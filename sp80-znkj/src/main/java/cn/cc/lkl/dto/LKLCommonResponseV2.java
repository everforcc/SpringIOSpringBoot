package cn.cc.lkl.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 公共响应参数DTO
 *
 * @author system
 * @since 2025-04-14
 */
@Data
public class LKLCommonResponseV2 {

    /**
     * 返回业务代码
     * 成功：BBS00000
     * 失败：其他错误码
     */
    @JsonProperty("retCode")
    private String retCode;

    /**
     * 返回业务代码描述
     * 对应code的详细描述信息
     */
    @JsonProperty("retMsg")
    private String retMsg;

    /**
     * 响应时间
     * 格式：yyyyMMddHHmmss
     * 示例：20250414152108
     */
    @JsonProperty("resp_time")
    private String respTime;

    /**
     * 响应数据
     * 具体数据格式参见各个接口的响应参数格式
     * jsonObj 和 jsonArray
     */
    @JsonProperty("respData")
    private Object respData;

    public boolean resultSuccess() {
        return "000000".equals(retCode);
    }

}

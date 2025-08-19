package cn.cc.lkl.dto;

import cn.cc.lkl.enums.FunctionCodeEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 公共请求参数DTO
 * extends V3CommRequest
 * @author system
 * @since 2025-04-14
 */
@Data
public class CommonRequestDTO {
    
    /**
     * 请求时间戳
     * 格式：yyyyMMddHHmmss
     * 示例：20250414152108
     */
    @NotBlank(message = "请求时间不能为空")
    @Size(min = 14, max = 14, message = "请求时间格式错误，应为14位数字")
    @JsonProperty("req_time")
    private String req_time;

    @JsonProperty("req_id")
    private String req_id;

    /**
     * 版本号
     * 当前版本：1.0
     */
    @NotBlank(message = "版本号不能为空")
    @Size(max = 8, message = "版本号长度不能超过8位")
    @JsonProperty("version")
    private String version;

    /**
     * 功能码
     * 用于标识具体的业务接口
     */
    @JsonProperty("function_code")
    private FunctionCodeEnum functionCode;
    
    /**
     * 请求参数
     * 具体参数格式参见各个接口的请求参数格式
     */
    @JsonProperty("req_data")
    private Object req_data;

    /**
     * 默认构造函数
     */
    public CommonRequestDTO() {
        this.version = "3.0";
        this.req_time = (new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date());
        this.req_id = String.valueOf(System.currentTimeMillis());
    }

    /**
     * 获取接口URL
     *
     * @return 接口URL
     */
    public String getApiUrl() {
        return functionCode != null ? functionCode.getCode() : null;
    }

}

package cn.cc.lkl.dto;

import cn.cc.lkl.enums.FunctionCodeEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 公共请求参数DTO
 * 
 * @author system
 * @since 2025-04-14
 */
@Data
public class CommonRequestDTO<T extends BaseRequest> {
    
    /**
     * 请求时间戳
     * 格式：yyyyMMddHHmmss
     * 示例：20250414152108
     */
    @NotBlank(message = "请求时间不能为空")
    @Size(min = 14, max = 14, message = "请求时间格式错误，应为14位数字")
    @JsonProperty("req_time")
    private String reqTime;
    
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
    private T functionCode;
    
    /**
     * 请求参数
     * 具体参数格式参见各个接口的请求参数格式
     */
    @JsonProperty("req_data")
    private Object reqData;
    
    /**
     * 默认构造函数
     */
    public CommonRequestDTO() {
        this.version = "1.0";
        this.reqTime = String.valueOf(new Date().getTime());
    }
    
    /**
     * 带参数的构造函数
     * 
     * @param reqTime 请求时间
     * @param functionCode 功能码
     * @param reqData 请求数据
     */
    public CommonRequestDTO(String reqTime, T functionCode, Object reqData) {
        this.reqTime = reqTime;
        this.version = "1.0";
        this.functionCode = functionCode;
        this.reqData = reqData;
    }
    
    /**
     * 获取接口URL
     * 
     * @return 接口URL
     */
    public String getApiUrl() {
        return functionCode != null ? functionCode.getFunctionCode().getCode() : null;
    }
    
    /**
     * 获取接口名称
     * 
     * @return 接口名称
     */
    public String getApiName() {
        return functionCode != null ? functionCode.getFunctionCode().getName() : null;
    }
}

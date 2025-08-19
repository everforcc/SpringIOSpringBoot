package cn.cc.lkl.dto;

import cn.cc.lkl.controller.ReturnCode;
import cn.cc.lkl.enums.FunctionCodeEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 公共响应参数DTO
 * 
 * @author system
 * @since 2025-04-14
 */
@Data
public class CommonResponseDTO<T extends FunctionCodeEnum> {
    
    /**
     * 返回业务代码
     * 成功：BBS00000
     * 失败：其他错误码
     */
    @JsonProperty("code")
    private String code;
    
    /**
     * 返回业务代码描述
     * 对应code的详细描述信息
     */
    @JsonProperty("msg")
    private String msg;
    
    /**
     * 响应时间
     * 格式：yyyyMMddHHmmss
     * 示例：20250414152108
     */
    @JsonProperty("resp_time")
    private String respTime;
    
    /**
     * 功能码
     * 用于标识具体的业务接口
     */
    @JsonProperty("function_code")
    private T functionCode;
    
    /**
     * 响应数据
     * 具体数据格式参见各个接口的响应参数格式
     */
    @JsonProperty("resp_data")
    private Object respData;
    
    /**
     * 默认构造函数
     */
    public CommonResponseDTO() {
    }
    
    /**
     * 带参数的构造函数
     * 
     * @param code 返回码
     * @param msg 返回消息
     * @param functionCode 功能码
     * @param respData 响应数据
     */
    public CommonResponseDTO(String code, String msg, T functionCode, Object respData) {
        this.code = code;
        this.msg = msg;
        this.functionCode = functionCode;
        this.respData = respData;
    }
    
    /**
     * 成功响应
     * 
     * @param functionCode 功能码
     * @param respData 响应数据
     * @param <T> 功能码类型
     * @return 成功响应对象
     */
    public static <T extends FunctionCodeEnum> CommonResponseDTO<T> success(T functionCode, Object respData) {
        return new CommonResponseDTO<>(ReturnCode.SUCCESS.getCode(),
                                     ReturnCode.SUCCESS.getDescription(), 
                                     functionCode,
                                     respData);
    }
    
    /**
     * 失败响应
     * 
     * @param returnCode 返回码枚举
     * @param functionCode 功能码
     * @param <T> 功能码类型
     * @return 失败响应对象
     */
    public static <T extends FunctionCodeEnum> CommonResponseDTO<T> fail(ReturnCode returnCode, T functionCode) {
        return new CommonResponseDTO<>(returnCode.getCode(), 
                                     returnCode.getDescription(), 
                                     functionCode,
                                     null);
    }
    
    /**
     * 失败响应（自定义消息）
     * 
     * @param returnCode 返回码枚举
     * @param customMsg 自定义消息
     * @param functionCode 功能码
     * @param <T> 功能码类型
     * @return 失败响应对象
     */
    public static <T extends FunctionCodeEnum> CommonResponseDTO<T> fail(ReturnCode returnCode, String customMsg, T functionCode) {
        return new CommonResponseDTO<>(returnCode.getCode(), 
                                     customMsg, 
                                     functionCode,
                                     null);
    }
    
    /**
     * 获取接口URL
     * 
     * @return 接口URL
     */
    public String getApiUrl() {
        return functionCode != null ? functionCode.getCode() : null;
    }
    
    /**
     * 获取接口名称
     * 
     * @return 接口名称
     */
    public String getApiName() {
        return functionCode != null ? functionCode.getName() : null;
    }
}

package cn.cc.lkl.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 业务返回码枚举
 * 
 * @author system
 * @since 2025-04-14
 */
@Getter
@AllArgsConstructor
public enum ReturnCode {
    
    /**
     * 成功
     * 响应成功
     */
    SUCCESS("BBS00000", "成功", "响应成功"),
    
    /**
     * 系统异常
     * 系统异常,不可预见的异常捕获(订单号生成异常、数据库操作失败)
     */
    SYSTEM_EXCEPTION("BBS00001", "系统异常", "系统异常,不可预见的异常捕获(订单号生成异常、数据库操作失败)"),
    
    /**
     * 网络请求失败
     * 网络请求失败,一般为DNS解析失败或TCP连接建立失败,以及非200响应
     */
    NETWORK_REQUEST_FAILED("BBS00100", "网络请求失败", "网络请求失败,一般为DNS解析失败或TCP连接建立失败,以及非200响应"),
    
    /**
     * 网络请求超时
     * 网络请求超时,连接时间或响应时间大于设置时间
     */
    NETWORK_REQUEST_TIMEOUT("BBS00101", "网络请求超时", "网络请求超时,连接时间或响应时间大于设置时间"),
    
    /**
     * 参数校验失败
     * 请求参数校验失败
     */
    PARAMETER_VALIDATION_FAILED("BBS11000", "参数校验失败", "请求参数校验失败"),
    
    /**
     * 验签失败
     * 验签失败
     */
    SIGNATURE_VERIFICATION_FAILED("BBS11200", "验签失败", "验签失败"),
    
    /**
     * 未查到参数
     * 未查到交易码参数
     */
    PARAMETER_NOT_FOUND("BBS11106", "未查到参数", "未查到交易码参数");
    
    /**
     * 返回码
     */
    private final String code;
    
    /**
     * 应答码简要描述
     */
    private final String description;
    
    /**
     * 应答码详细描述信息
     */
    private final String detailDescription;
    
    /**
     * 根据返回码获取枚举
     * 
     * @param code 返回码
     * @return 对应的枚举值，如果不存在则返回null
     */
    public static ReturnCode getByCode(String code) {
        for (ReturnCode returnCode : values()) {
            if (returnCode.getCode().equals(code)) {
                return returnCode;
            }
        }
        return null;
    }
    
    /**
     * 判断是否为成功码
     * 
     * @param code 返回码
     * @return true表示成功，false表示失败
     */
    public static boolean isSuccess(String code) {
        return SUCCESS.getCode().equals(code);
    }
    
    /**
     * 判断当前枚举是否为成功码
     * 
     * @return true表示成功，false表示失败
     */
    public boolean isSuccess() {
        return this == SUCCESS;
    }
}

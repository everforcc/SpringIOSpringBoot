package cn.cc.lkl.sdk.config;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * spring 初始化的时候用
 * lkl相关的参数
 */
@Component
@ConfigurationProperties(prefix = "lkl")
@Data
public class LKLPayConfig {

    /**
     * 开放平台鉴权机构
     */
    private String orgCode;

    /**
     * 接入方唯一编号
     */
    private String appId;

    /**
     * 商户证书序列号,和商户私钥对应
     */
    private String serialNo;

    /**
     * 商户私钥字符串,用于请求签名
     */
    private String priKeyStrLine;

    /**
     * 拉卡拉公钥证书字符串,用于验签
     */
    private String lklCerStrLine;

    /**
     * 拉卡拉开放平台服务地址
     */
    private String serverUrl;

    private String sm4Key;

    /**
     * 商户内部编号
     */
    private String merInnerNo;

    /**
     * 商户号
     */
    private String merchantNo;

    /**
     * 终端号
     */
    private String termNo;

    /**
     * 商户归属用户信息
     */
    private String userNo;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this, SerializerFeature.PrettyFormat);
    }
}

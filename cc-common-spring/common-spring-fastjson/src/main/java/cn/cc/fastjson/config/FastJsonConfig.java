package cn.cc.fastjson.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

/**
 * 所有请求都会经过 DispatcherServlet
 */
@Configuration
public class FastJsonConfig {

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 启用 FastJson
     */
    @Bean
    public FastJsonHttpMessageConverter fastJsonHttpMessageConverter() {
        // 利用这个测试引用这个模块是否生效
        JSON.DEFAULT_GENERATE_FEATURE |= SerializerFeature.DisableCircularReferenceDetect.getMask(); // 解决循环引用问题
        final FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        // 字符集
        converter.setDefaultCharset(StandardCharsets.UTF_8);
        // 字段排序
        converter.getFastJsonConfig().setFeatures(Feature.OrderedField);
        //
        converter.setSupportedMediaTypes(Collections.singletonList(
                MediaType.APPLICATION_JSON
        ));
        return converter;
    }

    /**
     * dto枚举字段 不匹配映射为null
     */
    @PostConstruct
    public void myObjectMapper() {
        // 解决enum不匹配问题 默认值为false
        objectMapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);
        //objectMapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE, true);
    }

}

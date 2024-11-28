package cn.cc.json.config;

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
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

@Configuration
public class FastJsonConfig implements WebMvcConfigurer {


    @Autowired
    private ObjectMapper objectMapper;

//    /**
//     * https://github.com/alibaba/fastjson/wiki/%E5%9C%A8-Spring-%E4%B8%AD%E9%9B%86%E6%88%90-Fastjson
//     */
//    @Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        converters.add(0, fastJsonHttpMessageConverter());
//    }

    /**
     * 启用 FastJson
     */
    @Bean
    public FastJsonHttpMessageConverter fastJsonHttpMessageConverter() {
//        JSON.DEFAULT_GENERATE_FEATURE |= SerializerFeature.DisableCircularReferenceDetect.getMask(); // 解决循环引用问题
        final FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        // 字符集
        converter.setDefaultCharset(StandardCharsets.UTF_8);
        // 字段排序
        converter.getFastJsonConfig().setFeatures(Feature.OrderedField);
        // converter.setDateFormat(); FastJsonConfig.setDateFormat(String)
        converter.getFastJsonConfig().setDateFormat("yyyy-MM-dd");
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

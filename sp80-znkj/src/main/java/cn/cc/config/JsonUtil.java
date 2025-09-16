package cn.cc.config;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;

public class JsonUtil {
    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        // 驼峰转下划线
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        // 忽略json中不存在的字段
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 忽略json中为null的字段（序列化时）
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // 忽略json中为null的字段（反序列化时）
        objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);

    }

    public static <T> T fromJson(String json, Class<T> clazz) throws Exception {
        return objectMapper.readValue(json, clazz);
    }

    public static <T> T fromJson(Object json, Class<T> clazz) {
        try {
            return objectMapper.readValue(JSONObject.toJSONString(json), clazz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 添加对象转JSON的方法
    public static String toJson(Object obj) throws Exception {
        return objectMapper.writeValueAsString(obj);
    }

}
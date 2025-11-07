package cn.cc.config;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;

import java.io.IOException;
import java.util.List;

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

    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
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
    public static String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static <T> List<T> parseToList(String text, Class<T> clazz) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, new Class[]{clazz});
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return (List)mapper.readValue(text, javaType);
        } catch (JsonParseException var4) {
        } catch (JsonMappingException var5) {
        } catch (IOException var6) {
        }

        return (List)null;
    }

}
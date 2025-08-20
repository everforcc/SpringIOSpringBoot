package cn.cc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * RedisTemplate 配置：
 * - bytes 模板（用于周位图/今日掩码的二进制存取，避免被 JSON 序列化污染）；
 * - string 模板（用于设备→组映射等字符串KV）；
 *
 * 为什么加 @Primary：
 * - Spring Boot 会自动提供一个 `stringRedisTemplate`（类型为 StringRedisTemplate，继承自 RedisTemplate<String,String>）；
 * - 我们自定义的 `redisStringTemplate` 与其同属一个父类型，按类型注入时将产生歧义；
 * - 加 @Primary 后，按类型注入时优先选择我们自定义的模板，统一序列化策略与行为；
 * - 若希望使用 Spring Boot 默认模板，可在注入处使用 @Qualifier("stringRedisTemplate").
 */
@Configuration
public class RedisConfig {

	@Bean
	public RedisTemplate<String, byte[]> redisBytesTemplate(RedisConnectionFactory factory) {
		RedisTemplate<String, byte[]> template = new RedisTemplate<>();
		template.setConnectionFactory(factory);
		template.setKeySerializer(RedisSerializer.string());
		template.setValueSerializer(RedisSerializer.byteArray());
		template.afterPropertiesSet();
		return template;
	}

	@Bean
	@Primary
	public RedisTemplate<String, String> redisStringTemplate(RedisConnectionFactory factory) {
		RedisTemplate<String, String> template = new RedisTemplate<>();
		template.setConnectionFactory(factory);
		template.setKeySerializer(RedisSerializer.string());
		template.setValueSerializer(RedisSerializer.string());
		template.setHashKeySerializer(RedisSerializer.string());
		template.setHashValueSerializer(RedisSerializer.string());
		template.afterPropertiesSet();
		return template;
	}
}



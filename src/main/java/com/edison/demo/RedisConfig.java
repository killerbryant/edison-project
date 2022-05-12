package com.edison.demo;

import java.time.Duration;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@EnableCaching
@Configuration
@SuppressWarnings("all")
public class RedisConfig {
	@Bean
    public CacheManager cacheManager(RedisConnectionFactory factory){
		// 通過Spring提供的RedisCacheConfiguration類，構造一個自己的Redis配置類，從該配置類中可以設定一些初始化的快取名稱空間
        // 及對應的預設過期時間等屬性，再利用RedisCacheManager中的builder.build()的方式生成cacheManager：
		// 生成一個預設配置，通過config物件即可對快取進行自定義配置，Duration.ofSeconds(120)設置緩存默認過期時間120秒，不快取空值
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(120)).disableCachingNullValues();
        // 解決使用@Cacheable，Redis資料庫value亂碼
        config = config.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.json()));
        // 使用自定義的快取配置初始化一個cacheManager
        RedisCacheManager cacheManager = RedisCacheManager.builder(factory).cacheDefaults(config).build();
        return cacheManager;
    }

    @Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory factory){
        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
        // 連接Redis伺服器
        redisTemplate.setConnectionFactory(factory);

        // 將Redis預設序列化方式轉換為json格式
        // 使用Jackson2JsonRedisSerializer來序列化和反序列化Redis的value值（預設使用JDK的序列化方式）
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        // redisTemplate.setDefaultSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        return redisTemplate;
    }
}
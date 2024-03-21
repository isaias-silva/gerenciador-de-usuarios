package com.zack.api.infra.caching;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

import static org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair.fromSerializer;


@Configuration


public class RedisConfig {
    private static final Logger logger = LoggerFactory.getLogger(RedisConfig.class);

    @Value("${spring.redis.host}")
    private String host;


    @Value("${spring.redis.port}")
    private String port;
    @Bean
    public RedisConnectionFactory lettuceConnectionFactory() {
        logger.info("cache redis on in: " +host+":"+port);
        return new LettuceConnectionFactory(new RedisStandaloneConfiguration(host,6379));
    }
    @Bean
    public RedisCacheConfiguration defaultCacheConfiguration() {
         return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(120))
                .disableCachingNullValues()
                .serializeValuesWith(fromSerializer(new GenericJackson2JsonRedisSerializer()));
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory){


        RedisTemplate<String,Object> redisTemplate=new RedisTemplate<>();

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));
        redisTemplate.setConnectionFactory(factory);

        return redisTemplate;
    }



}

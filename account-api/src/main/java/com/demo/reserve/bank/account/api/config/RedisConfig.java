package com.demo.reserve.bank.account.api.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.demo.reserve.bank.account.api.dto.AccountResponse;
import com.demo.reserve.bank.account.api.serializer.CustomBigDecimalSerializer;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
public class RedisConfig {
	
    @Value("${spring.data.redis.host}")
    private String redisHost;

    @Value("${spring.data.redis.port}")
    private int redisPort;
    
    /**
     * Creates a Redis template for storing and retrieving account responses.
     *
     * @param connectionFactory The Redis connection factory.
     * @return The Redis template for account responses.
     */
    @Bean
    RedisTemplate<String, List<AccountResponse>> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, List<AccountResponse>> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(createValueSerializer());

        return redisTemplate;
    }
    
    private RedisSerializer<Object> createValueSerializer() {
    	RedisSerializer<Object> jsonSerializer = new GenericJackson2JsonRedisSerializer(ObjectMapper());
    	return new CustomBigDecimalSerializer(jsonSerializer);
    }

	private ObjectMapper ObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        return objectMapper;
	}
	
    /**
     * Creates a Redis connection factory.
     *
     * @return The Redis connection factory.
     */
    @Bean
    RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration standaloneConfiguration = new RedisStandaloneConfiguration();
        standaloneConfiguration.setHostName(redisHost);
        standaloneConfiguration.setPort(redisPort);

        LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(standaloneConfiguration);
        connectionFactory.afterPropertiesSet();

        return connectionFactory;
    }
    
    
}
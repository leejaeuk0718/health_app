package healthApp_backend.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String redisHost;

    @Value("${spring.data.redis.port}")
    private int redisPort;

    @Bean
    public ObjectMapper objectMapper() {

        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.registerModule(new JavaTimeModule());

        objectMapper.disable(
                SerializationFeature.WRITE_DATES_AS_TIMESTAMPS
        );

        objectMapper.setVisibility(
                PropertyAccessor.ALL,
                JsonAutoDetect.Visibility.ANY
        );

        return objectMapper;
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {

        RedisStandaloneConfiguration configuration =
                new RedisStandaloneConfiguration();

        configuration.setHostName(redisHost);
        configuration.setPort(redisPort);

        return new LettuceConnectionFactory(configuration);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(
            RedisConnectionFactory connectionFactory
    ) {

        RedisTemplate<String, Object> redisTemplate =
                new RedisTemplate<>();

        redisTemplate.setConnectionFactory(connectionFactory);

        RedisSerializer<String> stringSerializer =
                RedisSerializer.string();

        RedisSerializer<Object> jsonSerializer =
                RedisSerializer.json();

        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(jsonSerializer);

        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(jsonSerializer);

        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }

    @Bean
    public CacheManager cacheManager(
            RedisConnectionFactory connectionFactory
    ) {

        RedisCacheConfiguration configuration =
                RedisCacheConfiguration.defaultCacheConfig()
                        .serializeKeysWith(
                                RedisSerializationContext
                                        .SerializationPair
                                        .fromSerializer(
                                                RedisSerializer.string()
                                        )
                        )
                        .serializeValuesWith(
                                RedisSerializationContext
                                        .SerializationPair
                                        .fromSerializer(
                                                RedisSerializer.json()
                                        )
                        );

        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(configuration)
                .build();
    }
}
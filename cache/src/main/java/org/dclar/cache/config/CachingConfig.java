package org.dclar.cache.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.net.UnknownHostException;

/**
 * Description:
 *
 * @author dclar
 */
@EnableCaching // 开启@Cache大门
@Configuration
// @CacheConfig 已经在yml中进行了配置，知道也可以通过这个annotation配置即可
public class CachingConfig {

    /**
     * 如果直接使用RestTemplate方式则自定义restTemplate。
     * 考虑
     * - key：String  {@link StringRedisSerializer}
     * - Value：Object  {@link org.springframework.core.serializer.DefaultSerializer}
     *
     * @param redisConnectionFactory
     * @return
     * @throws UnknownHostException
     */
    @Bean
    RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory)
            throws UnknownHostException {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        return template;
    }
}

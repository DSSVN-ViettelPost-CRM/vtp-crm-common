package vtp.crm.common.configuration.cache;


import one.util.streamex.StreamEx;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import vtp.crm.common.utils.Constants;

import java.time.Duration;

@EnableCaching
public class CommonRedisConfig {

    // prefix of redis key
    @Value("${caching.redis.default.key.prefix}")
    private String defaultCachePrefix;

    // time to live (minute)
    @Value("${caching.redis.default.ttl}")
    private Integer defaultTtl;

    @Bean(Constants.DEFAULT_CACHE_KEY_GENERATOR)
    public KeyGenerator cacheKeyGenerator() {
        return (target, method, params) -> {
            // user define cache name
            String[] cacheNamesDefined = method.getAnnotation(Cacheable.class) != null
                    ? method.getAnnotation(Cacheable.class).cacheNames()
                    : method.getAnnotation(CacheEvict.class).cacheNames();
            String cacheNames = ObjectUtils.isEmpty(cacheNamesDefined)
                    ? target.getClass().getSimpleName() + Constants.REDIS_KEY_DELIMITER + method.getName()
                    : StringUtils.join(cacheNamesDefined, Constants.REDIS_KEY_DELIMITER);

            // join with params value to be a cache key
            String paramsValue = ObjectUtils.isNotEmpty(params)
                    ? StreamEx.of(params).joining("_", Constants.REDIS_KEY_DELIMITER, "")
                    : "";
            return defaultCachePrefix + Constants.REDIS_KEY_DELIMITER + cacheNames + paramsValue;
        };
    }

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(defaultTtl))
                .disableKeyPrefix()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));

        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(cacheConfiguration)
                .build();
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }


}

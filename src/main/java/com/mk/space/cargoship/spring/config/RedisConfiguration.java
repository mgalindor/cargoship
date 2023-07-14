package com.mk.space.cargoship.spring.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@EnableCaching
@Configuration
@EnableRedisRepositories(basePackages = "com.mk.space.cargoship.adapters.sec.redis",
    considerNestedRepositories = true)
public class RedisConfiguration {
}

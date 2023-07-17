package com.mk.space.cargoship.spring.config;

import static org.springframework.data.redis.core.RedisKeyValueAdapter.EnableKeyspaceEvents.ON_STARTUP;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@EnableCaching
@Configuration
@EnableRedisRepositories(basePackages = "com.mk.space.cargoship.adapters.sec.redis",
        considerNestedRepositories = true, enableKeyspaceEvents = ON_STARTUP)
public class RedisConfiguration {
}

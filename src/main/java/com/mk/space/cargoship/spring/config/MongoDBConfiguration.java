package com.mk.space.cargoship.spring.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.mk.space.cargoship.adapters.sec.mongo",
        considerNestedRepositories = true)
public class MongoDBConfiguration {
}

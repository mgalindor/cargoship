package com.mk.space.cargoship.spring.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.stream.schema.registry.client.ConfluentSchemaRegistryClient;
import org.springframework.cloud.stream.schema.registry.client.SchemaRegistryClient;
import org.springframework.cloud.stream.schema.registry.client.config.SchemaRegistryClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({SchemaRegistryClientProperties.class})
public class KafkaConfiguration {

  @Bean
  public SchemaRegistryClient schemaRegistryClient(
      SchemaRegistryClientProperties schemaRegistryClientProperties) {
    ConfluentSchemaRegistryClient client = new ConfluentSchemaRegistryClient();
    client.setEndpoint(schemaRegistryClientProperties.getEndpoint());
    return client;
  }
}

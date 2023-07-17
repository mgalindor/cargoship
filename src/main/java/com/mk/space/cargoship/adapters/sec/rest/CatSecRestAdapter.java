package com.mk.space.cargoship.adapters.sec.rest;

import java.util.List;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.mk.space.cargoship.core.ports.sec.CatSecPort;
import com.mk.space.cargoship.spring.aspect.Loggable;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Loggable
@Component
public class CatSecRestAdapter implements CatSecPort {

  private final RestTemplate restTemplate;
  private final CatRestProperties catRestProperties;

  public CatSecRestAdapter(RestTemplateBuilder builder, CatRestProperties catRestProperties) {
    this.restTemplate = builder.build();
    this.catRestProperties = catRestProperties;
  }

  @Override
  @Cacheable(cacheNames = "catFactRestCache")
  public String catFact() {
    return restTemplate.getForObject(catRestProperties.getUrl(), CatFactResponse.class).data.get(0);
  }

  private record CatFactResponse(List<String> data) {
  }

}

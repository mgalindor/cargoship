package com.mk.space.cargoship.adapters.sec.rest;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("adapter.rest.cat")
public class CatRestProperties {

  private String url;
}

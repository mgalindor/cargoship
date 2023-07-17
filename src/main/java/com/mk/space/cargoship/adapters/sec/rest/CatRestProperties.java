package com.mk.space.cargoship.adapters.sec.rest;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties("adapter.rest.cat")
public class CatRestProperties {

  private String url;
}

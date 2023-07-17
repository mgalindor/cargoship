package com.mk.space.cargoship;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import com.mk.space.cargoship.core.annotations.Behavior;

@SpringBootApplication
@ComponentScan(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION,
        value = Behavior.class))
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

}

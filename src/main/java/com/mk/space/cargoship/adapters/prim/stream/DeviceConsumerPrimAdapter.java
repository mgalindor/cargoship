package com.mk.space.cargoship.adapters.prim.stream;

import com.mk.space.cargoship.adapters.schema.stream.Device;
import com.mk.space.cargoship.spring.aspect.Loggable;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Slf4j
@Loggable
@Component("deviceConsumer")
public class DeviceConsumerPrimAdapter implements Consumer<Device> {

  @Override
  public void accept(Device device) {
    log.debug("Catch Event [{}]",device);
  }
}

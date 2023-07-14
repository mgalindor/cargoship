package com.mk.space.cargoship.adapters.sec.stream;

import com.mk.space.cargoship.adapters.schema.stream.Device;
import com.mk.space.cargoship.core.ports.sec.DeviceSecPort;
import com.mk.space.cargoship.spring.aspect.Loggable;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Loggable
@Component
@RequiredArgsConstructor
public class DeviceSecProducerAdapter implements DeviceSecPort {

  private final StreamBridge streamBridge;

  @Override
  public void saveDevice(String deviceId, int value ){
    Device device = Device.newBuilder()
        .setDeviceId(deviceId)
        .setValue(value)
        .setDate(LocalDateTime.now())
        .build();
    streamBridge.send("deviceProducer",device);

  }

}

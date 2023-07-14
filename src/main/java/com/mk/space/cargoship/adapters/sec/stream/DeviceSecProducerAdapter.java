package com.mk.space.cargoship.adapters.sec.stream;

import java.time.LocalDateTime;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

import com.mk.space.cargoship.adapters.schema.stream.Device;
import com.mk.space.cargoship.core.ports.sec.DeviceSecPort;
import com.mk.space.cargoship.spring.aspect.Loggable;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Loggable
@Component
@RequiredArgsConstructor
public class DeviceSecProducerAdapter implements DeviceSecPort {

  private final StreamBridge streamBridge;

  @Override
  @Timed(value = "spring.cloud.function",
          extraTags = {"spring.cloud.function.definition", "deviceProducer"})
  public void saveDevice(String deviceId, int value) {
    Device device =
            Device.newBuilder().setDeviceId(deviceId).setValue(value).setDate(LocalDateTime.now())
                    .build();
    streamBridge.send("deviceProducer", device);
  }

}

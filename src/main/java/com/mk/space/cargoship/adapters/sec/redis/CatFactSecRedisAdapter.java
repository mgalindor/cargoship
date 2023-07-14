package com.mk.space.cargoship.adapters.sec.redis;

import com.mk.space.cargoship.core.ports.sec.CatFactPort;
import com.mk.space.cargoship.spring.aspect.Loggable;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Loggable
@Component
@RequiredArgsConstructor
public class CatFactSecRedisAdapter implements CatFactPort {

  private final CatFactRepository repository;

  @Override
  public String saveCatFact(String catFact) {
    String id = UUID.randomUUID().toString();
    repository.save(new CatFact(id, catFact));
    return id;
  }

  @RedisHash(value = "catFact", timeToLive = 2L)//2 seconds
  record CatFact(@Id String id, String catFact) {
  }

  public interface CatFactRepository extends CrudRepository<CatFact, String> {
  }
}
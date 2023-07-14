package com.mk.space.cargoship.adapters.sec.jpa;

import com.mk.space.cargoship.adapters.sec.jpa.entity.ClaimEntity;
import com.mk.space.cargoship.core.domain.ClaimDo;
import com.mk.space.cargoship.core.ports.sec.ClaimSecPort;
import com.mk.space.cargoship.spring.aspect.Loggable;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.mapstruct.Mapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

@Slf4j
@Loggable
@Component
@RequiredArgsConstructor
public class ClaimSecJpaAdapter implements ClaimSecPort {

  private final ClaimRepository claimRepository;
  private final ClaimMapper mapper;

  @Override
  public String createClaim(ClaimDo claim) {
    ClaimEntity entity = mapper.fromClaimDo(claim);
    entity.setCreatedDate(Instant.now());
    entity.setCreatedBy("SYSTEM");
    return claimRepository.save(entity).getId();
  }

  @Override
  @Cacheable(cacheNames = "claimCache", unless = "#result == null")
  public Optional<ClaimDo> findClaimById(String id) {
    return claimRepository.findById(id).map(mapper::fromClaimEntity);
  }

  public interface ClaimRepository extends JpaRepository<ClaimEntity, String> {
  }


  @Mapper
  public interface ClaimMapper {
    ClaimEntity fromClaimDo(ClaimDo claimDo);

    ClaimDo fromClaimEntity(ClaimEntity claimEntity);
  }
}


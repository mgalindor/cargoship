package com.mk.space.cargoship.core.ports.sec;

import java.util.Optional;

import com.mk.space.cargoship.core.domain.ClaimDo;

public interface ClaimSecPort {
  String createClaim(ClaimDo claim);

  Optional<ClaimDo> findClaimById(String id);

}

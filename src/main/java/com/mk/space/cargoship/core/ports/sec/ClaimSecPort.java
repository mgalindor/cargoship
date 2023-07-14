package com.mk.space.cargoship.core.ports.sec;

import com.mk.space.cargoship.core.domain.ClaimDo;

import java.util.Optional;

public interface ClaimSecPort {
  String createClaim(ClaimDo claim);

  Optional<ClaimDo> findClaimById(String id);

}

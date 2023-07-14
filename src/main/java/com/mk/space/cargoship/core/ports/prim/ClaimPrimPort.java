package com.mk.space.cargoship.core.ports.prim;

import com.mk.space.cargoship.core.domain.ClaimDo;

public interface ClaimPrimPort {
  String createClaim(ClaimDo claim);

  ClaimDo findClaimById(String id);

}

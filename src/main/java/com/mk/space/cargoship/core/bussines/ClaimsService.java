package com.mk.space.cargoship.core.bussines;

import com.mk.space.cargoship.core.annotations.Behavior;
import com.mk.space.cargoship.core.domain.ClaimDo;
import com.mk.space.cargoship.core.domain.DocumentDo;
import com.mk.space.cargoship.core.exceptions.ResourceNotFoundException;
import com.mk.space.cargoship.core.ports.prim.ClaimPrimPort;
import com.mk.space.cargoship.core.ports.sec.*;
import com.mk.space.cargoship.spring.aspect.Loggable;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Loggable
@Behavior
@RequiredArgsConstructor
public class ClaimsService implements ClaimPrimPort {

  private final ClaimSecPort claimSecPort;
  private final DocumentSecPort documentSecPort;
  private final CatSecPort catSecPort;
  private final CatFactPort catFactPort;
  private final DeviceSecPort deviceSecPort;


  @Override
  public String createClaim(ClaimDo claim) {
    String catFact = catSecPort.catFact();
    String claimId = claimSecPort.createClaim(claim);
    catFactPort.saveCatFact(catFact);

    documentSecPort.createDocument(DocumentDo.builder().claimId(claimId).catFact(catFact).build());

    deviceSecPort.saveDevice(claim.getProductType(), 1);

    return claimId;
  }

  @Override
  public ClaimDo findClaimById(String id) {
    return claimSecPort.findClaimById(id).orElseThrow(ResourceNotFoundException::new);
  }
}

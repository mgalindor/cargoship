package com.mk.space.cargoship.core.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DocumentDo {
  private String id;
  private String claimId;
  private String catFact;
}

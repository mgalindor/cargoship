package com.mk.space.cargoship.core.domain;

import java.io.Serializable;
import java.time.Instant;

import lombok.Data;

@Data
public class ClaimDo implements Serializable {
  private String id;
  private String policyId;
  private String productType;
  private String state;
  private Instant createdDate;
  private String createdBy;
}

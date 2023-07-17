package com.mk.space.cargoship.adapters.sec.jpa.entity;

import java.time.Instant;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tx_claims")
public class ClaimEntity {
  @Id
  @UuidGenerator
  private String id;
  private String policyId;
  private String productType;
  private String state;
  private Instant createdDate;
  private String createdBy;
}

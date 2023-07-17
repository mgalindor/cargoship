package com.mk.space.cargoship.adapters.prim.rest;

import org.mapstruct.Mapper;
import org.springframework.web.bind.annotation.*;

import com.mk.space.cargoship.core.domain.ClaimDo;
import com.mk.space.cargoship.core.ports.prim.ClaimPrimPort;
import com.mk.space.cargoship.spring.aspect.Loggable;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Loggable
@RestController
@RequestMapping("/claims")
@RequiredArgsConstructor
public class ClaimInRestAdapter {

  private final ClaimPrimPort service;
  private final ClaimsMapper mapper;

  @PostMapping
  public CreateClaimResponse createClaim(@RequestBody CreateClaimRequest createClaimRequest) {
    String id = service.createClaim(mapper.fromCreateClaimRequest(createClaimRequest));
    return new CreateClaimResponse(id);
  }

  @GetMapping("/{id}")
  public ClaimResponse findClaimById(@PathVariable String id) {
    return mapper.fromClaimDo(service.findClaimById(id));
  }


  @Mapper
  public interface ClaimsMapper {
    ClaimDo fromCreateClaimRequest(CreateClaimRequest createClaimRequest);

    ClaimResponse fromClaimDo(ClaimDo claimDo);
  }


  public record CreateClaimRequest(String policyId, String productType, String state) {
  }


  public record CreateClaimResponse(String id) {
  }


  public record ClaimResponse(String id, String policyId, String productType, String state) {
  }
}

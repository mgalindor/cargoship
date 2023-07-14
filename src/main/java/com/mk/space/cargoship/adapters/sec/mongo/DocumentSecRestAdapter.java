package com.mk.space.cargoship.adapters.sec.mongo;

import com.mk.space.cargoship.core.domain.DocumentDo;
import com.mk.space.cargoship.core.ports.sec.DocumentSecPort;
import com.mk.space.cargoship.spring.aspect.Loggable;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

@Slf4j
@Loggable
@Component
@RequiredArgsConstructor
public class DocumentSecRestAdapter implements DocumentSecPort {

  private final DocumentRepository documentRepository;

  @Override
  public void createDocument(DocumentDo documentDo) {
    documentRepository.save(
        DocumentDoc.builder().claimId(documentDo.getClaimId()).catFact(documentDo.getCatFact())
            .build());
  }

  private interface DocumentRepository extends MongoRepository<DocumentDoc, String> {
  }


  @Document
  @Builder
  public static class DocumentDoc {

    @Id
    private String id;
    @Indexed
    private String claimId;
    private String catFact;
  }
}

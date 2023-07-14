package com.mk.space.cargoship.core.ports.sec;

import com.mk.space.cargoship.core.domain.DocumentDo;

public interface DocumentSecPort {
  void createDocument(DocumentDo documentDo);
}

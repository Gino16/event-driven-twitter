package com.gino.microservices.reactiveelasticqueryservice.api;

import com.gino.microservices.elasticqueryservicecommon.model.ElasticQueryServiceRequestModel;
import com.gino.microservices.elasticqueryservicecommon.model.ElasticQueryServiceResponseModel;
import com.gino.microservices.reactiveelasticqueryservice.business.ElasticQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping(value = "/documents")
@RequiredArgsConstructor
public class ElasticDocumentController {

  private final ElasticQueryService elasticQueryService;

  @PostMapping(value = "/get-doc-by-text", produces = MediaType.TEXT_EVENT_STREAM_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public Flux<ElasticQueryServiceResponseModel> getDocumentByText(
      @RequestBody ElasticQueryServiceRequestModel requestModel) {
    return elasticQueryService.getDocumentByText(requestModel.getText()).log();
  }
}

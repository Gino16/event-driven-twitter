package com.gino.microservices.reactiveelasticqueryservice.business;

import com.gino.microservices.elasticqueryservicecommon.model.ElasticQueryServiceResponseModel;
import reactor.core.publisher.Flux;

public interface ElasticQueryService {

  Flux<ElasticQueryServiceResponseModel> getDocumentByText(String text);

}

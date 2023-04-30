package com.gino.microservices.reactiveelasticqueryservice.business.impl;

import com.gino.microservices.elasticmodel.model.index.impl.TwitterIndexModel;
import com.gino.microservices.elasticqueryservicecommon.model.ElasticQueryServiceResponseModel;
import com.gino.microservices.elasticqueryservicecommon.transformer.ElasticToResponseModelTransformer;
import com.gino.microservices.reactiveelasticqueryservice.business.ElasticQueryService;
import com.gino.microservices.reactiveelasticqueryservice.business.ReactiveElasticQueryClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class TwitterElasticQueryService implements ElasticQueryService {

  private final ReactiveElasticQueryClient<TwitterIndexModel> reactiveElasticQueryClient;
  private final ElasticToResponseModelTransformer elasticToResponseModelTransformer;

  @Override
  public Flux<ElasticQueryServiceResponseModel> getDocumentByText(String text) {
    return reactiveElasticQueryClient
        .getIndexModelByText(text)
        .map(elasticToResponseModelTransformer::getResponseModel);
  }
}

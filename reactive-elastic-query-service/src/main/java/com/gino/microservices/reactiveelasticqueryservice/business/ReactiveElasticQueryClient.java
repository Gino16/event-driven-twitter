package com.gino.microservices.reactiveelasticqueryservice.business;

import com.gino.microservices.elasticmodel.model.index.IndexModel;
import com.gino.microservices.elasticmodel.model.index.impl.TwitterIndexModel;
import reactor.core.publisher.Flux;

public interface ReactiveElasticQueryClient<T extends IndexModel> {
  Flux<TwitterIndexModel> getIndexModelByText(String text);
}

package com.gino.microservices.reactiveelasticqueryservice.business.impl;

import com.gino.microservices.configdata.config.ElasticQueryConfigData;
import com.gino.microservices.configdata.config.ElasticQueryServiceConfigData;
import com.gino.microservices.elasticmodel.model.index.impl.TwitterIndexModel;
import com.gino.microservices.reactiveelasticqueryservice.business.ReactiveElasticQueryClient;
import com.gino.microservices.reactiveelasticqueryservice.repository.ElasticQueryRepository;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@Service
public class TwitterReactiveElasticQueryClient implements ReactiveElasticQueryClient<TwitterIndexModel> {

  private final ElasticQueryRepository elasticQueryRepository;
  private final ElasticQueryServiceConfigData elasticQueryServiceConfigData;

  @Override
  public Flux<TwitterIndexModel> getIndexModelByText(String text) {
    return elasticQueryRepository
        .findByText(text)
        .delayElements(Duration.ofMillis(elasticQueryServiceConfigData.getBackPressureDelayMs()));
  }
}

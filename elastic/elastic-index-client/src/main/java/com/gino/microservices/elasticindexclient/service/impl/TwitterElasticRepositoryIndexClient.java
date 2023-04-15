package com.gino.microservices.elasticindexclient.service.impl;

import com.gino.microservices.elasticindexclient.repository.TwitterElasticsearchIndexRepository;
import com.gino.microservices.elasticindexclient.service.ElasticIndexClient;
import com.gino.microservices.elasticmodel.model.index.impl.TwitterIndexModel;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@ConditionalOnExpression("${elastic-config.is-repository}")
public class TwitterElasticRepositoryIndexClient implements ElasticIndexClient<TwitterIndexModel> {


  private final TwitterElasticsearchIndexRepository twitterElasticsearchIndexRepository;

  @Override
  public List<String> save(List<TwitterIndexModel> documents) {
    List<TwitterIndexModel> repositoryResponse = (List<TwitterIndexModel>) twitterElasticsearchIndexRepository.saveAll(
        documents);
    List<String> ids = repositoryResponse.stream().map(TwitterIndexModel::getId)
        .collect(Collectors.toList());
    log.info("Documents indexed successfully with type: {} and ids: {}",
        TwitterIndexModel.class.getName(), ids);
    return ids;
  }
}

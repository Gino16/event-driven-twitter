package com.gino.microservices.elasticqueryclient.service.impl;

import com.gino.microservices.commonutil.CollectionsUtil;
import com.gino.microservices.elasticmodel.model.index.impl.TwitterIndexModel;
import com.gino.microservices.elasticqueryclient.exception.ElasticQueryClientException;
import com.gino.microservices.elasticqueryclient.repository.TwitterElasticSearchQueryRepository;
import com.gino.microservices.elasticqueryclient.service.ElasticQueryClient;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
@RequiredArgsConstructor
@Slf4j
public class TwitterElasticRepositoryQueryClient implements ElasticQueryClient<TwitterIndexModel> {

  private final TwitterElasticSearchQueryRepository twitterElasticSearchQueryRepository;

  @Override
  public TwitterIndexModel getIndexModelById(String id) {
    Optional<TwitterIndexModel> searchResult = twitterElasticSearchQueryRepository.findById(id);
    log.info("Document with id {} retrieved successfully", searchResult.orElseThrow(() ->
        new ElasticQueryClientException("No document found at elasticsearch with id " + id)));
    return searchResult.get();
  }

  @Override
  public List<TwitterIndexModel> getIndexModelByText(String text) {
    List<TwitterIndexModel> searchResult = twitterElasticSearchQueryRepository.findByText(text);
    log.info("{} of documents with text {} retrieved successfully", searchResult.size(), text);
    return searchResult;
  }

  @Override
  public List<TwitterIndexModel> getAllIndexModels() {
    List<TwitterIndexModel> searchResult = CollectionsUtil.getInstance()
        .getListFromIterable(twitterElasticSearchQueryRepository.findAll());
    log.info("{} of documents retrieved successfully", searchResult.size());
    return searchResult;
  }
}

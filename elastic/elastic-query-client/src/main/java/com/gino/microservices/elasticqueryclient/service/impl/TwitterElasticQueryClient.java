package com.gino.microservices.elasticqueryclient.service.impl;

import com.gino.microservices.configdata.config.ElasticConfigData;
import com.gino.microservices.configdata.config.ElasticQueryConfigData;
import com.gino.microservices.elasticmodel.model.index.impl.TwitterIndexModel;
import com.gino.microservices.elasticqueryclient.exception.ElasticQueryClientException;
import com.gino.microservices.elasticqueryclient.service.ElasticQueryClient;
import com.gino.microservices.elasticqueryclient.util.ElasticQueryUtil;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TwitterElasticQueryClient implements ElasticQueryClient<TwitterIndexModel> {

  private final ElasticConfigData elasticConfigData;
  private final ElasticQueryConfigData elasticQueryConfigData;
  private final ElasticsearchOperations elasticsearchOperations;
  private final ElasticQueryUtil<TwitterIndexModel> elasticQueryUtil;


  @Override
  public TwitterIndexModel getIndexModelById(String id) {
    Query query = elasticQueryUtil.getSearchQueryById(id);
    SearchHit<TwitterIndexModel> searchedOne = elasticsearchOperations.searchOne(
        query, TwitterIndexModel.class, IndexCoordinates.of(elasticConfigData.getIndexName()));

    if (searchedOne == null) {
      log.error("No document found at elasticsearch with id: {}", id);
      throw new ElasticQueryClientException("No document found at elasticsearch with id: " + id);
    }
    log.info("Document with id {} retrieved successfully", searchedOne.getId());
    return searchedOne.getContent();
  }

  @Override
  public List<TwitterIndexModel> getIndexModelByText(String text) {
    return search(elasticQueryUtil.getSearchQueryByFieldText(elasticQueryConfigData.getTextField(),
        text), "{} of documents with text {} retrieved successfully", text);
  }

  @Override
  public List<TwitterIndexModel> getAllIndexModels() {
    return search(elasticQueryUtil.getSearchQueryForAll(), "{} of documents retrieved");
  }

  private List<TwitterIndexModel> search(Query query, String logMessage, Object... logParams) {
    SearchHits<TwitterIndexModel> searched = elasticsearchOperations.search(query,
        TwitterIndexModel.class, IndexCoordinates.of(elasticConfigData.getIndexName()));
    log.info(logMessage, searched.getTotalHits(), logParams);
    return searched.get().map(SearchHit::getContent).collect(Collectors.toList());
  }
}

package com.gino.microservices.elasticindexclient.service.impl;

import com.gino.microservices.configdata.config.ElasticConfigData;
import com.gino.microservices.elasticindexclient.client.util.ElasticIndexUtil;
import com.gino.microservices.elasticindexclient.service.ElasticIndexClient;
import com.gino.microservices.elasticmodel.model.index.impl.TwitterIndexModel;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@ConditionalOnExpression("not ${elastic-config.is-repository}")
public class TwitterElasticIndexClient implements ElasticIndexClient<TwitterIndexModel> {

  private final ElasticConfigData elasticConfigData;
  private final ElasticsearchOperations elasticsearchOperations;
  private final ElasticIndexUtil<TwitterIndexModel> elasticIndexUtil;

  @Override
  public List<String> save(List<TwitterIndexModel> documents) {
    List<IndexQuery> indexQueries = elasticIndexUtil.getIndexQueries(documents);
    List<String> documentIds = elasticsearchOperations.bulkIndex(
        indexQueries,
        IndexCoordinates.of(elasticConfigData.getIndexName())
    );
    log.info("Documents indexed successfully with type: {} and is: {}",
        TwitterIndexModel.class.getName(), documentIds);
    return documentIds;
  }
}

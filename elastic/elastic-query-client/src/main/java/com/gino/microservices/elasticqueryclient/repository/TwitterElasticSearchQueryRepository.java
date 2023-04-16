package com.gino.microservices.elasticqueryclient.repository;

import com.gino.microservices.elasticmodel.model.index.impl.TwitterIndexModel;
import java.util.List;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TwitterElasticSearchQueryRepository extends ElasticsearchRepository<TwitterIndexModel, String> {
  List<TwitterIndexModel> findByText(String text);
}

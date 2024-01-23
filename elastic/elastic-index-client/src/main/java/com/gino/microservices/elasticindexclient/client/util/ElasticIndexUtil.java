package com.gino.microservices.elasticindexclient.client.util;

import com.gino.microservices.elasticmodel.model.index.IndexModel;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Component;

@Component
public class ElasticIndexUtil<T extends IndexModel> {

  public List<IndexQuery> getIndexQueries(List<T> documents) {
    return documents.stream()
        .map(document -> new IndexQueryBuilder()
            .withId(document.getId())
            .withObject(document)
            .build())
        .collect(Collectors.toList());
  }
}

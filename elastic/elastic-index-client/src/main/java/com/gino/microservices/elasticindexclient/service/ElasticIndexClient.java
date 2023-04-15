package com.gino.microservices.elasticindexclient.service;

import com.gino.microservices.elasticmodel.model.index.IndexModel;
import java.util.List;

public interface ElasticIndexClient<T extends IndexModel> {
  List<String> save(List<T> documents);
}

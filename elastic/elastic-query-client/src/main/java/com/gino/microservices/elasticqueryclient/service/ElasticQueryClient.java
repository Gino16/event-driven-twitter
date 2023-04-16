package com.gino.microservices.elasticqueryclient.service;

import com.gino.microservices.elasticmodel.model.index.IndexModel;
import java.util.List;

public interface ElasticQueryClient<T extends IndexModel> {

  T getIndexModelById(String id);

  List<T> getIndexModelByText(String text);

  List<T> getAllIndexModels();

}

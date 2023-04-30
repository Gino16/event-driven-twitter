package com.gino.microservices.elasticqueryservice.business.impl;

import com.gino.microservices.elasticmodel.model.index.impl.TwitterIndexModel;
import com.gino.microservices.elasticqueryclient.service.ElasticQueryClient;
import com.gino.microservices.elasticqueryservice.business.ElasticQueryService;
import com.gino.microservices.elasticqueryservice.model.assembler.ElasticQueryServiceResponseModelAssembler;
import com.gino.microservices.elasticqueryservicecommon.model.ElasticQueryServiceResponseModel;
import com.gino.microservices.elasticqueryservicecommon.transformer.ElasticToResponseModelTransformer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TwitterElasticQueryService implements ElasticQueryService {

  private final ElasticToResponseModelTransformer elasticToResponseModelTransformer;

  private final ElasticQueryServiceResponseModelAssembler elasticQueryServiceResponseModelAssembler;

  private final ElasticQueryClient<TwitterIndexModel> elasticQueryClient;

  @Override
  public ElasticQueryServiceResponseModel getDocumentById(String id) {
    log.info("Querying elastic search by id {}", id);
    return elasticQueryServiceResponseModelAssembler.toModel(
        elasticQueryClient.getIndexModelById(id));
  }

  @Override
  public List<ElasticQueryServiceResponseModel> getDocumentByText(String text) {
    log.info("Querying elastic search by text: {}", text);

    return elasticQueryServiceResponseModelAssembler.toModels(
        elasticQueryClient.getIndexModelByText(text));
  }

  @Override
  public List<ElasticQueryServiceResponseModel> getAllDocuments() {
    log.info("Querying all documents");

    return elasticQueryServiceResponseModelAssembler.toModels(
        elasticQueryClient.getAllIndexModels());
  }
}

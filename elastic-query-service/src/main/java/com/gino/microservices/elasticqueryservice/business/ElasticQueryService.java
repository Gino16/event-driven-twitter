package com.gino.microservices.elasticqueryservice.business;

import com.gino.microservices.elasticqueryservicecommon.model.ElasticQueryServiceResponseModel;
import java.util.List;

public interface ElasticQueryService {
    ElasticQueryServiceResponseModel getDocumentById(String id);
    List<ElasticQueryServiceResponseModel> getDocumentByText(String text);
    List<ElasticQueryServiceResponseModel> getAllDocuments();
}

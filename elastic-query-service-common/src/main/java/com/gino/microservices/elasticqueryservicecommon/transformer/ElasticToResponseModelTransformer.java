package com.gino.microservices.elasticqueryservicecommon.transformer;

import com.gino.microservices.elasticmodel.model.index.impl.TwitterIndexModel;
import com.gino.microservices.elasticqueryservicecommon.model.ElasticQueryServiceResponseModel;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class ElasticToResponseModelTransformer {
    public ElasticQueryServiceResponseModel getResponseModel(TwitterIndexModel twitterIndexModel) {
        return ElasticQueryServiceResponseModel
                .builder()
                .id(twitterIndexModel.getId())
                .userId(twitterIndexModel.getUserId())
                .text(twitterIndexModel.getText())
                .createdAt(twitterIndexModel.getCreatedAt())
                .build();
    }

    public List<ElasticQueryServiceResponseModel> getResponseModel(List<TwitterIndexModel> twitterIndexModels) {
        return twitterIndexModels
                .stream()
                .map(this::getResponseModel)
                .collect(Collectors.toList());
    }
}

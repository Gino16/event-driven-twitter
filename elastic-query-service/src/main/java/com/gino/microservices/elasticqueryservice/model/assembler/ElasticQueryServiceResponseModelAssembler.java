package com.gino.microservices.elasticqueryservice.model.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.gino.microservices.elasticmodel.model.index.impl.TwitterIndexModel;
import com.gino.microservices.elasticqueryservice.api.ElasticDocumentController;
import com.gino.microservices.elasticqueryservicecommon.model.ElasticQueryServiceResponseModel;
import com.gino.microservices.elasticqueryservicecommon.transformer.ElasticToResponseModelTransformer;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ElasticQueryServiceResponseModelAssembler extends
    RepresentationModelAssemblerSupport<TwitterIndexModel, ElasticQueryServiceResponseModel> {

  private final ElasticToResponseModelTransformer elasticToResponseModelTransformer;

  public ElasticQueryServiceResponseModelAssembler(
      ElasticToResponseModelTransformer elasticToResponseModelTransformer) {
    super(ElasticDocumentController.class, ElasticQueryServiceResponseModel.class);
    this.elasticToResponseModelTransformer = elasticToResponseModelTransformer;
  }

  @Override
  public ElasticQueryServiceResponseModel toModel(TwitterIndexModel twitterIndexModel) {
    ElasticQueryServiceResponseModel responseModel = elasticToResponseModelTransformer.getResponseModel(
        twitterIndexModel);
    responseModel.add(
        linkTo(
            methodOn(ElasticDocumentController.class).getDocumentsById(twitterIndexModel.getId()))
            .withSelfRel()
    );
    responseModel.add(
        linkTo(ElasticDocumentController.class)
            .withRel("documents")
    );
    return responseModel;
  }

  public List<ElasticQueryServiceResponseModel> toModels(List<TwitterIndexModel> twitterIndexModels) {
        return twitterIndexModels.stream().map(this::toModel).collect(Collectors.toList());
  }
}

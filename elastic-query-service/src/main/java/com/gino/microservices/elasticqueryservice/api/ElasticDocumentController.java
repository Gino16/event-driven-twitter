package com.gino.microservices.elasticqueryservice.api;

import com.gino.microservices.elasticqueryservice.business.ElasticQueryService;
import com.gino.microservices.elasticqueryservice.model.ElasticQueryServiceRequestModel;
import com.gino.microservices.elasticqueryservice.model.ElasticQueryServiceResponseModel;
import com.gino.microservices.elasticqueryservice.model.ElasticQueryServiceResponseModelV2;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/documents")
@RequiredArgsConstructor
@Slf4j
public class ElasticDocumentController {

  private final ElasticQueryService elasticQueryService;

  @GetMapping("/v1")
  public @ResponseBody ResponseEntity<List<ElasticQueryServiceResponseModel>> getAllDocuments() {
    return ResponseEntity.ok(elasticQueryService.getAllDocuments());
  }

  @GetMapping("/v1/{id}")
  public @ResponseBody ResponseEntity<ElasticQueryServiceResponseModel> getDocumentsById(
      @PathVariable @NotEmpty String id) {
    return ResponseEntity.ok(elasticQueryService.getDocumentById(id));
  }

  @GetMapping("/v2/{id}")
  public @ResponseBody ResponseEntity<ElasticQueryServiceResponseModelV2> getDocumentsByIdV2(
      @PathVariable @NotEmpty String id) {
    return ResponseEntity.ok(getV2Model(elasticQueryService.getDocumentById(id)));
  }

  @PostMapping("/v1/get-document-by-text")
  public @ResponseBody ResponseEntity<List<ElasticQueryServiceResponseModel>> getDocumentsByText(
      @RequestBody @Valid ElasticQueryServiceRequestModel requestModel) {
    return ResponseEntity.ok(elasticQueryService.getDocumentByText(requestModel.getText()));
  }

  private ElasticQueryServiceResponseModelV2 getV2Model(
      ElasticQueryServiceResponseModel responseModel) {
    ElasticQueryServiceResponseModelV2 responseModelV2 = ElasticQueryServiceResponseModelV2
        .builder()
        .id(Long.parseLong(responseModel.getId()))
        .text(responseModel.getText())
        .userId(responseModel.getUserId())
        .createdAt(responseModel.getCreatedAt())
        .build();
    responseModelV2.add(responseModel.getLinks());
    return responseModelV2;
  }
}

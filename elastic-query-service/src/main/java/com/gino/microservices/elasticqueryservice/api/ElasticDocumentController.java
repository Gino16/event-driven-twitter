package com.gino.microservices.elasticqueryservice.api;

import com.gino.microservices.elasticqueryservice.model.ElasticQueryServiceRequestModel;
import com.gino.microservices.elasticqueryservice.model.ElasticQueryServiceResponseModel;
import java.util.List;
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
@Slf4j
public class ElasticDocumentController {

  @GetMapping("/")
  public @ResponseBody ResponseEntity<List<ElasticQueryServiceResponseModel>> getAllDocuments() {
    return ResponseEntity.ok(List.of());
  }

  @GetMapping("/{id}")
  public @ResponseBody ResponseEntity<ElasticQueryServiceResponseModel> getDocumentsById(
      @PathVariable String id) {
    return ResponseEntity.ok(ElasticQueryServiceResponseModel.builder().id(id).build());
  }

  @PostMapping("/get-document-by-text")
  public @ResponseBody ResponseEntity<List<ElasticQueryServiceResponseModel>> getDocumentsByText(
      @RequestBody ElasticQueryServiceRequestModel requestModel) {
    return ResponseEntity.ok(
        List.of(ElasticQueryServiceResponseModel.builder().text(requestModel.getText()).build()));
  }
}

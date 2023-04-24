package com.gino.microservices.elasticqueryservice.api;

import com.gino.microservices.elasticqueryservice.business.ElasticQueryService;
import com.gino.microservices.elasticqueryservice.model.ElasticQueryServiceRequestModel;
import com.gino.microservices.elasticqueryservice.model.ElasticQueryServiceResponseModel;
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

  @GetMapping({"/", ""})
  public @ResponseBody ResponseEntity<List<ElasticQueryServiceResponseModel>> getAllDocuments() {
    return ResponseEntity.ok(elasticQueryService.getAllDocuments());
  }

  @GetMapping("/{id}")
  public @ResponseBody ResponseEntity<ElasticQueryServiceResponseModel> getDocumentsById(
      @PathVariable @NotEmpty String id) {
    return ResponseEntity.ok(elasticQueryService.getDocumentById(id));
  }

  @PostMapping("/get-document-by-text")
  public @ResponseBody ResponseEntity<List<ElasticQueryServiceResponseModel>> getDocumentsByText(
      @RequestBody @Valid ElasticQueryServiceRequestModel requestModel) {
    return ResponseEntity.ok(elasticQueryService.getDocumentByText(requestModel.getText()));
  }
}

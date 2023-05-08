package com.gino.microservices.elasticqueryservice.api;

import com.gino.microservices.elasticqueryservice.business.ElasticQueryService;
import com.gino.microservices.elasticqueryservicecommon.model.ElasticQueryServiceRequestModel;
import com.gino.microservices.elasticqueryservicecommon.model.ElasticQueryServiceResponseModel;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping(value = "/documents")
@RequiredArgsConstructor
@Slf4j
public class ElasticDocumentController {

  private final ElasticQueryService elasticQueryService;

  @PostAuthorize("hasPermission(returnObject, 'READ')")
  @GetMapping({"/", ""})
  public @ResponseBody ResponseEntity<List<ElasticQueryServiceResponseModel>> getAllDocuments() {
    return ResponseEntity.ok(elasticQueryService.getAllDocuments());
  }

  @PreAuthorize("hasPermission(#id, 'ElasticQueryServiceResponseModel', 'READ')")
  @GetMapping("/{id}")
  public @ResponseBody ResponseEntity<ElasticQueryServiceResponseModel> getDocumentsById(
      @PathVariable @NotEmpty String id) {
    return ResponseEntity.ok(elasticQueryService.getDocumentById(id));
  }

  @PreAuthorize("hasRole('APP_USER_ROLE') || hasRole('APP_SUPER_USER_ROLE') || hasAuthority('SCOPE_APP_USER_ROLE')")
  @PostAuthorize("hasPermission(returnObject, 'READ')")
  @PostMapping("/get-document-by-text")
  public @ResponseBody ResponseEntity<List<ElasticQueryServiceResponseModel>> getDocumentsByText(
      @RequestBody @Valid ElasticQueryServiceRequestModel requestModel) {
    return ResponseEntity.ok(elasticQueryService.getDocumentByText(requestModel.getText()));
  }
}

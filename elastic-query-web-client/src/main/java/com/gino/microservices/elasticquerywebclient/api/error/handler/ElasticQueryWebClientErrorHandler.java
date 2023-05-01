package com.gino.microservices.elasticquerywebclient.api.error.handler;

import com.gino.microservices.elasticquerywebclient.model.ElasticQueryWebClientRequestModel;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ElasticQueryWebClientErrorHandler {

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<String> handle(AccessDeniedException e) {
    log.error("Access denied exception!", e);
    return ResponseEntity.status(HttpStatus.FORBIDDEN)
        .body("You are not authorized to access this resource");
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<String> handle(IllegalArgumentException e) {
    log.error("Illegal argument exception!", e);
    return ResponseEntity.badRequest().body("Illegal argument exception!" + e.getMessage());
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<String> handle(RuntimeException e) {
    log.error("Service runtime exception!", e);
    return ResponseEntity.badRequest().body("Service runtime exception!" + e.getMessage());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handle(Exception e) {
    log.error("Internal server error!", e);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("A server error occurred!");
  }

  @ExceptionHandler(BindException.class)
  public String handle(BindException e, Model model) {
    Map<String, String> errors = new HashMap<>();
    e.getBindingResult().getAllErrors().forEach(
        objectError -> errors.put(((FieldError) objectError).getField(),
            objectError.getDefaultMessage()));
    model.addAttribute("elasticQueryWebClientRequestModel",
        ElasticQueryWebClientRequestModel.builder().build());
    model.addAttribute("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
    model.addAttribute("error_description", errors);
    return "home";
  }
}

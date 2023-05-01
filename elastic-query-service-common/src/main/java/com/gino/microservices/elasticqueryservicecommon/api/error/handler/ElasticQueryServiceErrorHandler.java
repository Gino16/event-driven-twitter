package com.gino.microservices.elasticqueryservicecommon.api.error.handler;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
public class ElasticQueryServiceErrorHandler {

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<String> handle(AccessDeniedException e) {
    log.error("Access denied exception!", e);
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to access this resource");
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

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handle(MethodArgumentNotValidException e) {
    log.error("Method argument validation exception!", e);
    Map<String, String> errors = new HashMap<>();
    e.getBindingResult().getAllErrors().forEach(objectError -> errors.put(((FieldError) objectError).getField(), objectError.getDefaultMessage()));
    return ResponseEntity.badRequest().body(errors);
  }

}
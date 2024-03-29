package com.gino.microservices.elasticquerywebclient.exception;

public class ElasticQueryWebClientException extends RuntimeException{

  public ElasticQueryWebClientException() {
    super();
  }

  public ElasticQueryWebClientException(String message) {
    super(message);
  }

  public ElasticQueryWebClientException(String message, Throwable cause) {
    super(message, cause);
  }
}

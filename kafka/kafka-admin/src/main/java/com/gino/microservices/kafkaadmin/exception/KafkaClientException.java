package com.gino.microservices.kafkaadmin.exception;

public class KafkaClientException extends RuntimeException {

    public KafkaClientException() {
        super();
    }

    public KafkaClientException(String message) {
        super(message);
    }

    public KafkaClientException(String message, Throwable cause) {
        super(message, cause);
    }
}

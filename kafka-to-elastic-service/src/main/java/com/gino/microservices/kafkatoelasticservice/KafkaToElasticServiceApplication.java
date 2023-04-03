package com.gino.microservices.kafkatoelasticservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.gino.microservices")
public class KafkaToElasticServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(KafkaToElasticServiceApplication.class, args);
  }
}

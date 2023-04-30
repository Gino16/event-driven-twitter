package com.gino.microservices.reactiveelasticqueryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@SpringBootApplication
@ComponentScan(basePackages = "com.gino.microservices")
public class ReactiveElasticQueryServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(ReactiveElasticQueryServiceApplication.class, args);
  }
}

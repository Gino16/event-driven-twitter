package com.microservices.kafkaadmin.client;

import com.microservices.configserver.config.KafkaConfigData;
import com.microservices.configserver.config.RetryConfigData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaAdminClient {

  private final KafkaConfigData kafkaConfigData;
  private final RetryConfigData retryConfigData;
  private final AdminClient adminClient;
  private final RetryTemplate retryTemplate;

}

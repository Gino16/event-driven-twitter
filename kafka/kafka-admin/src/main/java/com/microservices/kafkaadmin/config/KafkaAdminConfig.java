package com.microservices.kafkaadmin.config;

import com.microservices.configserver.config.KafkaConfigData;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.admin.AdminClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;

@Configuration
@EnableRetry
@RequiredArgsConstructor
public class KafkaAdminConfig {

  private final KafkaConfigData kafkaConfigData;

  public AdminClient adminClient() {
    return AdminClient.create(Map.of(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, kafkaConfigData.getBootstrapServers()));
  }

}

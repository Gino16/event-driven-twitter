package com.gino.microservices.kafkaadmin.config;

import com.gino.microservices.configdata.KafkaConfigData;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.admin.AdminClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;

import java.util.Map;

@Configuration
@EnableRetry
@RequiredArgsConstructor
public class KafkaAdminConfig {

    private final KafkaConfigData kafkaConfigData;

    @Bean
    public AdminClient adminClient() {
        return AdminClient.create(Map.of(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, kafkaConfigData.getBootstrapServers()));
    }

}

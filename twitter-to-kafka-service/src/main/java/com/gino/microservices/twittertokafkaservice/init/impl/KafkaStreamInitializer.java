package com.gino.microservices.twittertokafkaservice.init.impl;

import com.gino.microservices.configdata.config.KafkaConfigData;
import com.gino.microservices.kafkaadmin.client.KafkaAdminClient;
import com.gino.microservices.twittertokafkaservice.init.StreamInitializer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaStreamInitializer implements StreamInitializer {

  private final KafkaConfigData kafkaConfigData;
  private final KafkaAdminClient kafkaAdminClient;

  @Override
  public void init() {
    kafkaAdminClient.createTopics();
    kafkaAdminClient.checkSchemaRegistry();
    log.info("Topics with name {} is ready for operations!",
        kafkaConfigData.getTopicNamesToCreate().toArray());
  }
}

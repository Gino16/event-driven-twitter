package com.gino.microservices.kafkatoelasticservice.consumer.impl;

import com.gino.microservices.configdata.config.KafkaConfigData;
import com.gino.microservices.kafkaadmin.client.KafkaAdminClient;
import com.gino.microservices.kafkatoelasticservice.consumer.KafkaConsumer;
import com.microservices.kafka.avro.model.TwitterAvroModel;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TwitterKafkaConsumer implements KafkaConsumer<Long, TwitterAvroModel> {

  private final KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;
  private final KafkaAdminClient kafkaAdminClient;

  private final KafkaConfigData kafkaConfigData;

  @Override
  @KafkaListener(id = "twitterTopicListener", topics = "${kafka-config.topic-name}")
  public void receive(
      @Payload List<TwitterAvroModel> messages,
      @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) List<Integer> keys,
      @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
      @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
    log.info(
        "{} number of message received with keys {}, partitions {} and offsets {}, sending it to elastic: Thread id: {}",
        messages.size(),
        keys.toString(),
        partitions.toString(),
        offsets.toString(),
        Thread.currentThread().getId());
  }

  @EventListener
  public void onAppStarted(ApplicationStartedEvent event) {
    kafkaAdminClient.checkTopicsCreated();
    log.info("Topics with name {} is ready for operations!", kafkaConfigData.getTopicNamesToCreate().toArray());
    kafkaListenerEndpointRegistry.getListenerContainer("twitterTopicListener").start();
  }
}
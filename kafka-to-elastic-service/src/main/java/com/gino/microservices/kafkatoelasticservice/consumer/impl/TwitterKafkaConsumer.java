package com.gino.microservices.kafkatoelasticservice.consumer.impl;

import com.gino.microservices.configdata.config.KafkaConfigData;
import com.gino.microservices.configdata.config.KafkaConsumerConfigData;
import com.gino.microservices.elasticindexclient.service.ElasticIndexClient;
import com.gino.microservices.elasticmodel.model.index.impl.TwitterIndexModel;
import com.gino.microservices.kafkaadmin.client.KafkaAdminClient;
import com.gino.microservices.kafkatoelasticservice.consumer.KafkaConsumer;
import com.gino.microservices.kafkatoelasticservice.transformer.AvroToElasticModelTransformer;
import com.microservices.kafka.avro.model.TwitterAvroModel;
import java.util.List;
import java.util.Objects;
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
  private final KafkaConsumerConfigData kafkaConsumerConfigData;
  private final AvroToElasticModelTransformer avroToElasticModelTransformer;
  private final ElasticIndexClient<TwitterIndexModel> elasticIndexClient;

  @Override
  @KafkaListener(id = "${kafka-consumer-config.consumer-group-id}", topics = "${kafka-config.topic-name}")
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
    List<TwitterIndexModel> twitterIndexModels = avroToElasticModelTransformer.getElasticModels(
        messages);
    List<String> documentIds = elasticIndexClient.save(twitterIndexModels);
    log.info("Documents save to elastic search with ids: {}", documentIds.toArray());
  }

  @EventListener
  public void onAppStarted(ApplicationStartedEvent event) {
    kafkaAdminClient.checkTopicsCreated();
    log.info("Topics with name {} is ready for operations!",
        kafkaConfigData.getTopicNamesToCreate().toArray());
    Objects.requireNonNull(kafkaListenerEndpointRegistry.getListenerContainer(
        kafkaConsumerConfigData.getConsumerGroupId())).start();
  }
}
